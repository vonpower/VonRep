/*
 * @author 冯涛，创建日期：2003-11-25
 * blog--http://apower.blogone.net
 * QQ:17463776
 * MSN:apower511@msn.com
 * E-Mail:VonPower@Gmail.com
 */
package ynugis.ffsystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import ynugis.geo.Merge;
import ynugis.geo.ShpDistanceOp;
import ynugis.geo.Utility;
import ynugis.ui.elementToSHP.Feat2FC;

import com.esri.arcgis.geoanalyst.FeatureClassDescriptor;
import com.esri.arcgis.geoanalyst.IRasterAnalysisEnvironment;
import com.esri.arcgis.geoanalyst.IRasterAnalysisEnvironmentProxy;
import com.esri.arcgis.geodatabase.Field;
import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureCursor;
import com.esri.arcgis.geodatabase.IGeoDataset;
import com.esri.arcgis.geodatabase.IGeoDatasetProxy;
import com.esri.arcgis.geodatabase.IQueryFilter;
import com.esri.arcgis.geodatabase.ISelectionSet;
import com.esri.arcgis.geodatabase.ISpatialFilter;
import com.esri.arcgis.geodatabase.QueryFilter;
import com.esri.arcgis.geodatabase.SpatialFilter;
import com.esri.arcgis.geodatabase.esriSelectionOption;
import com.esri.arcgis.geodatabase.esriSelectionType;
import com.esri.arcgis.geodatabase.esriSpatialRelEnum;
import com.esri.arcgis.geometry.IProximityOperator;
import com.esri.arcgis.geometry.IProximityOperatorProxy;
import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.spatialanalyst.IMapAlgebraOp;
import com.esri.arcgis.spatialanalyst.RasterMapAlgebraOp;

public abstract class Facet extends FFItem {

	protected String[] srcFiles;

	private double[] ranks;

	private double[] scores;

	private double[] ranges;

	private int formula = FFTConstant.FORMULA_LINEAR_ATTENUATION;

	public void specifySrcData(String[] inSourceFiles) {
		srcFiles = inSourceFiles;
	}

	public void removeSrcData() {
		srcFiles = null;
	}

	protected IGeoDataset calculateLeafFacet(CalculatorInfo calculInfo)
			throws Exception {
		System.out.println("开始计算 " + name + " :");
		if (!canCalculate()) {
			System.out.println("当前leaf因子不可计算（可能缺少计算所需的数据）");
			return null;
		}
		IGeoDataset data[] = new IGeoDataset[srcFiles.length];
		// data= new IGeoDataset[srcFiles.length];
		for (int i = 0; i < srcFiles.length; i++) {

			data[i] = calculateEij(Utility.OpenFeatureClass(srcFiles[i]),
					calculInfo);
		}
		// 把当前DerivedFacet标识为以计算
		calculated = true;
		Merge m = new Merge(calculInfo.getEnv());
		m.specifyDataLayers(data);
		calcuResult = m.mergeDataLayers();

		calculated = true;
		return calcuResult;

	}

	protected IGeoDataset calculateEij(IFeatureClass inFeature,
			CalculatorInfo calculInfo) throws Exception, IOException {

		// 定义表示功能分和作用半径的变量
		double functionScore = 0;
		double range = 0;

		IMapAlgebraOp mapAlgebraOp = new RasterMapAlgebraOp();
		IMapAlgebraOp mapAlgebraOpTest = new RasterMapAlgebraOp();
		
		// 赋值分析环境
		IRasterAnalysisEnvironment rasterAnalysisEnvironment = new IRasterAnalysisEnvironmentProxy(
				mapAlgebraOp);
		Utility.RasterAnalysisEnvCopy(calculInfo.getEnv(),
				rasterAnalysisEnvironment);
		
//		 赋值分析环境2
		IRasterAnalysisEnvironment rasterAnalysisEnvironmentTest = new IRasterAnalysisEnvironmentProxy(
				mapAlgebraOpTest);
		Utility.RasterAnalysisEnvCopy(calculInfo.getEnv(),
				rasterAnalysisEnvironmentTest);

		// 找出当前FeatureClass包含的等级
		Set rankSet = new HashSet();
		ArrayList FunctionScoreList = new ArrayList();
		ArrayList RangeList = new ArrayList();

		int rankIdx = inFeature.getFields().findField(
				FFTConstant.FUNCTION_RANK_FIELD);
		int scoreIdx = inFeature.getFields().findField(
				FFTConstant.FUNCTION_SCORE_FIELD);
		int rangeIdx = inFeature.getFields().findField(
				FFTConstant.FUNCTION_RANGE_FIELD);
		String rankStr = FFTConstant.FUNCTION_RANK_FIELD;

		/*
		 * int rankIdx = 2; int scoreIdx = 3; int rangeIdx = 4; String rankStr =
		 * inFeature.getFields().getField(2).getName();
		 */
		int featrueCount = inFeature.featureCount(null);
		for (int i = 0; i < featrueCount; i++) {
			if (!rankSet.contains(inFeature.getFeature(i).getValue(rankIdx))) {// 如果发现新等级，就加入新的作用分和作用分
				FunctionScoreList.add(inFeature.getFeature(i)
						.getValue(scoreIdx));
				RangeList.add(inFeature.getFeature(i).getValue(rangeIdx));
			}
			rankSet.add(inFeature.getFeature(i).getValue(rankIdx));

		}

		setRanks(doubleSort(rankSet.toArray()));
		setScores(doubleSort(FunctionScoreList.toArray()));
		setRanges(doubleSort(RangeList.toArray()));

		// ShpDistanceOp用于完成直线距离分析功能
		ShpDistanceOp sdo = new ShpDistanceOp(calculInfo.getEnv());

		IQueryFilter qf = new QueryFilter();
		//建立空间过滤，选取在当前区域内，当前计算等级的样本点（线）
		ISpatialFilter spatialFilter = new SpatialFilter();
		spatialFilter.setSpatialRel(esriSpatialRelEnum.esriSpatialRelContains);
		
		Feat2FC f2f=new Feat2FC(calculInfo.getRange().getFeatureClass());
		IFeatureClass rangeFeatureClass[]=f2f.getFeatureClasses();
		IGeoDataset disTemp;
		IGeoDataset result = null;

		FeatureClassDescriptor simplePointfcd;

		// 因为每一个等级（Rank）的feature的“功能分”和“作用半径”都可能不同，下面的程序用每一个feature生成
		// 单独的raster，最后把这些raster叠加得到输入featureClass的总“作用分”：

		// 分等级（Rank）来计算就可以完成同级取高分，不同级叠加的记分规则
		// 计算公式
		String cmdStr = null;
		ISelectionSet selectionSet;
		boolean existObstruct = false;
		IFeatureClass rangeFC = calculInfo.getRange().getFeatureClass();
		int rangeCount = rangeFC.featureCount(null);
		IFeatureClass obstruct = null;
		if (calculInfo.getObstruct() != null && rangeCount > 1) {
			// 存在阻隔 标识
			existObstruct = true;
			obstruct = calculInfo.getObstruct().getFeatureClass();

		}

		for (int i = 0; i < getRanks().length; i++) {

			qf.setWhereClause(rankStr + " = " + getRanks()[i]);

			System.out.println(qf.getWhereClause());
			System.out.println("ranks:" + getRanks()[i]);

			// 获取当前Rank的feature的功能分和作用半径
			selectionSet = inFeature.select(qf,
					esriSelectionType.esriSelectionTypeIDSet,
					esriSelectionOption.esriSelectionOptionNormal, null);

			simplePointfcd = new FeatureClassDescriptor();
			// simplePointfcd.create(inFeature,qf,null);

			simplePointfcd.createFromSelectionSet(selectionSet, null,
					FFTConstant.FUNCTION_SCORE_FIELD);

			System.out.println("Selection Set count :"
					+ selectionSet.getCount());

			// IFeatureClass tempFeature=simplePointfcd.getFeatureClass();

			// System.out.println("Rank:"+ranks[i]+"
			// ,包含feature数目"+tempFeature.featureCount(null));

			functionScore = getScores()[getRanks().length - i - 1];
			range = getRanges()[getRanks().length - i - 1];
			System.out.println(" 功能分:" + functionScore);
			System.out.println("作用半径:" + range);

			// *******************************************************************
			if (range == 0) {// 如果是面状因子（影响半径为0），就直接转换为Raster并返回

				FeatureClassDescriptor tempFcd = new FeatureClassDescriptor();
				tempFcd.create(inFeature, null,
						FFTConstant.FUNCTION_SCORE_FIELD);
				result = Utility.shp2Raster(calculInfo.getEnv(), tempFcd);
				mapAlgebraOp.bindRaster(result, "R0");

				// 将[R0]定中的nodata格指定为“0”；
				return mapAlgebraOp.execute("CON(isNull([R0]) ,0 ,[R0])");
			}
			// *******************************************************************

			// 构造计算公式
			if (getFormula() == FFTConstant.FORMULA_LINEAR_ATTENUATION) {
				// 直线衰减计算公式
				cmdStr = functionScore + " * ( 1 - [R1] / " + range + " )";

			} else if (getFormula() == FFTConstant.FORMULA_EXPONENTIAL_ATTENUATION) {
				// 指数衰减计算公式
				cmdStr = "Pow(" + functionScore + " , 1 - [R1] / " + range
						+ " )";
			}
			IGeoDataset obstructDataset=null;
			if (existObstruct) {
				// 考虑阻隔的作用分计算
				FeatureClassDescriptor rangeFCD = new FeatureClassDescriptor();
				IFeature rangeFeature[] = new IFeature[rangeCount];
				IQueryFilter rangeQF1 = new QueryFilter();
				IQueryFilter rangeQF2 = new QueryFilter();

				for (int m = 0; m < rangeCount; m++) {
					rangeFeature[m] = rangeFC.getFeature(m);
				}
				IFeatureCursor calcuRange;
				IFeatureCursor noCalcuRange;
				IGeoDataset dataset[] = new IGeoDataset[rangeCount];
				for (int m = 0; m < rangeCount; m++) {
					
					
					// 得到当前计算区域
					rangeQF1.setWhereClause("FID = " + m);
					ISelectionSet set = rangeFC.select(rangeQF1,
							esriSelectionType.esriSelectionTypeIDSet,
							esriSelectionOption.esriSelectionOptionNormal, null);
					// simplePointfcd.create(inFeature,qf,null);

					rangeFCD.createFromSelectionSet(set, null,
							"FID");
					
					
					// calcuRange = rangeFC.search(rangeQF,true);

					// 得到当前非计算区域
					rangeQF2.setWhereClause("FID <> " + m);
					// rangeFCD.create(rangeFC, rangeQF, "FID");
					noCalcuRange = rangeFC.search(rangeQF2, false);

					// 初始化阻隔

					initialObstruct(obstruct, noCalcuRange, inFeature, qf);

					/*// 将阻隔加入当前要计算的样点集
					ObstructCombine oc = new ObstructCombine(simplePointfcd,
							obstruct);*/
					// 以当前计算范围为遮罩
					System.out.println("Set count: "+set.getCount());
					IFeatureClass mask=rangeFeatureClass[m];
					
					sdo.getRasEnv().setMaskByRef(new IGeoDatasetProxy(mask));
					sdo.getRasEnv().setExtent(com.esri.arcgis.geoanalyst.esriRasterEnvSettingEnum.esriRasterEnvValue,
							(new IGeoDatasetProxy(mask)).getExtent(), null);
					
					obstructDataset = calcuObstruct(obstruct, sdo,mapAlgebraOp, new Merge(calculInfo.getEnv()));
						
					//return obstructDataset;
			
					// dataset[m] = sdo.doStraightLine(oc.getIGeoDataset(),
					// range);
					spatialFilter.setGeometryByRef(rangeFeature[m].getShape());
					spatialFilter.setWhereClause(qf.getWhereClause());
					
					simplePointfcd.createFromSelectionSet(simplePointfcd.getSelectionSet(),spatialFilter,FFTConstant.FUNCTION_SCORE_FIELD);
					disTemp = sdo.doStraightLine((IGeoDataset) simplePointfcd
							.getFeatureClass(), range);
					
					//Utility.saveAs(disTemp,"e:\\raster"+m);
					

					mapAlgebraOpTest.bindRaster(disTemp, "R1");
					disTemp = mapAlgebraOpTest.execute(cmdStr);
					
					mapAlgebraOpTest.bindRaster(disTemp, "Rx");
					disTemp=mapAlgebraOpTest.execute("CON(isNull([Rx]) , 0 , [Rx])");
					
					mapAlgebraOpTest.unbindRaster("Rx");
					mapAlgebraOpTest.unbindRaster("R1");
					
					// 用阻隔计算结果和当前等级样点按同级取高分的原则叠加
					if (obstructDataset != null) {
						
						mapAlgebraOpTest.bindRaster(obstructDataset, "obstructDataset");
						obstructDataset=mapAlgebraOpTest.execute("CON(isNull([obstructDataset]) , 0 , [obstructDataset])");
						mapAlgebraOpTest.bindRaster(obstructDataset, "obstructDataset");
						//return obstructDataset;
						mapAlgebraOpTest.bindRaster(disTemp, "disTemp");
						dataset[m] = mapAlgebraOpTest.execute("CON([disTemp] > [obstructDataset] , [disTemp] , [obstructDataset])");
						//dataset[m]=obstructDataset;
						mapAlgebraOpTest.unbindRaster("obstructDataset");
						mapAlgebraOpTest.unbindRaster("disTemp");
					} else {
						dataset[m] = disTemp;
					}
					// mapAlgebraOp.unbindRaster("R1");
					
				}
				Merge m = new Merge(calculInfo.getEnv());
				m.specifyDataLayers(dataset);

				// disTemp = m.MergeDataLayers();

				// mapAlgebraOp.bindRaster(disTemp, "R1");
				//
				if (result == null) {
					result = m.mergeDataLayers();
					// mapAlgebraOp.bindRaster(result, "R0");
					// 将[R0]定中的nodata格指定为“0”；
					// result = mapAlgebraOp.execute("CON(isNull([R0]) ,0
					// ,[R0])");

					// mapAlgebraOp.bindRaster(result, "R0");

				} else {
					IGeoDataset temp = m.mergeDataLayers();
					// nodata2Zero(temp,workspace);
					// mapAlgebraOp.bindRaster(temp, "Rt");

					// 将[Rt]定中的nodata格指定为“0”；
					// temp = mapAlgebraOp.execute("CON(isNull([Rt]) ,0
					// ,[Rt])");
					// mapAlgebraOp.unbindRaster("Rt");
					mapAlgebraOpTest.bindRaster(result, "Result");
					mapAlgebraOpTest.bindRaster(temp, "Rtemp");
					
					result = mapAlgebraOpTest.execute("[Rtemp] + [Result]");
					
					mapAlgebraOpTest.unbindRaster("Result");
					mapAlgebraOpTest.unbindRaster("Rtemp");
					// mapAlgebraOp.bindRaster(result, "R0");
				}

			} else {
				// 不考虑阻隔的作用分计算
				IGeoDataset gd=new IGeoDatasetProxy( simplePointfcd.getFeatureClass());
				disTemp = sdo.doStraightLine(gd,
						range);

				mapAlgebraOp.bindRaster(disTemp, "R1");

				if (result == null) {
					result = mapAlgebraOp.execute(cmdStr);
					mapAlgebraOp.bindRaster(result, "R0");
					// 将[R0]定中的nodata格指定为“0”；
					result = mapAlgebraOp.execute("CON(isNull([R0]) ,0 ,[R0])");
					// nodata2Zero(result,workspace);
					// 作用分大于100的作为100看待
					// mapAlgebraOp.bindRaster(result, "R0");
					// result = mapAlgebraOp.execute("CON([R0] > 100 ,100
					// ,[R0])");

					mapAlgebraOp.bindRaster(result, "R0");

				} else {
					IGeoDataset temp = mapAlgebraOp.execute(cmdStr);
					// nodata2Zero(temp,workspace);
					mapAlgebraOp.bindRaster(temp, "Rt");

					// 将[Rt]定中的nodata格指定为“0”；
					temp = mapAlgebraOp.execute("CON(isNull([Rt]) ,0 ,[Rt])");

					mapAlgebraOp.bindRaster(temp, "Rt");
					result = mapAlgebraOp.execute("[Rt] + [R0]");
					// 作用分大于100的作为100看待
					// mapAlgebraOp.bindRaster(result, "R0");
					// result = mapAlgebraOp.execute("CON([R0] > 100,100
					// ,[Rt])");

					mapAlgebraOp.bindRaster(result, "R0");

				}
			}
		
		}

		return result;

	}

	private IGeoDataset calcuObstruct(IFeatureClass obstruct,
			ShpDistanceOp sdo, IMapAlgebraOp mapAlgebraOp, Merge merge)
			throws Exception {
		IQueryFilter qf = new QueryFilter();
		//qf.setWhereClause(FFTConstant.FUNCTION_RANGE_FIELD + " <> 0");

		ISelectionSet selectionSet;

		FeatureClassDescriptor fcd = new FeatureClassDescriptor();
		// simplePointfcd.create(inFeature,qf,null);

		int count = obstruct.featureCount(null);
		if (count == 0)
			return null;
		ArrayList al = new ArrayList();
		double range = 0;
		double score = 0;
		IGeoDataset temp;
		String cmdStr = "";

		for (int i = 0; i < count; i++) {
			range = Double.parseDouble(obstruct.getFeature(i).getValue(
					obstruct.findField(FFTConstant.FUNCTION_RANGE_FIELD))
					.toString());
			score = Double.parseDouble(obstruct.getFeature(i).getValue(
					obstruct.findField(FFTConstant.FUNCTION_SCORE_FIELD))
					.toString());
			if (range == 0 || score == 0) {
				continue;
			}
			qf.setWhereClause("FID = " + i);
			//System.out.println("FID = " + i);
			selectionSet = obstruct.select(qf,
					esriSelectionType.esriSelectionTypeIDSet,
					esriSelectionOption.esriSelectionOptionNormal, null);
			System.out.println("selectionSet.getCount():"+selectionSet.getCount());
			fcd.createFromSelectionSet(selectionSet, null,
					"FID");
			IGeoDataset gd=new IGeoDatasetProxy(fcd.getFeatureClass());
			temp = sdo.doStraightLine(gd, range);
								
			mapAlgebraOp.bindRaster(temp, "temp1");

			// 构造计算公式
			if (getFormula() == FFTConstant.FORMULA_LINEAR_ATTENUATION) {
				// 直线衰减计算公式
				cmdStr = score + " * ( 1 - [temp1] / " + range + " )";

			} else if (getFormula() == FFTConstant.FORMULA_EXPONENTIAL_ATTENUATION) {
				// 指数衰减计算公式
				cmdStr = "Pow(" + score + " , 1 - [temp1] / " + range + " )";
			}
			al.add(mapAlgebraOp.execute(cmdStr));

		}
		IGeoDataset data[] = new IGeoDataset[al.size()];
		for (int i = 0; i < data.length; i++) {
			data[i] = (IGeoDataset) al.get(i);
		}
		
		merge.specifyDataLayers(data);
		return merge.mergeDataLayers();
		//return merge.mergeUseBigValue();
	}

	/**
	 * 把非计算区域中距离阻隔最近的样本点的属性，处理以后，赋给阻隔
	 * （处理过程：将阻隔看作样本点，以距离阻隔最近的样本点衰减到阻隔的“作用分”，作为阻隔的“作用分”，
	 * 以距离阻隔最近的样本点的“作用半径”减去该样本点与阻隔的距离，作为”作用半径“）
	 * 
	 * @param obstruct
	 *            阻隔
	 * @param calcuRange
	 *            计算区域
	 * @param noCalcuRange
	 *            非计算区域
	 * @param simplePoint
	 *            样本点
	 */
	private void initialObstruct(IFeatureClass obstruct,
			IFeatureCursor noCalcuRange, IFeatureClass simplePoint,
			IQueryFilter simpleQF) {
		try {
			

			int obstructScoreIdx = obstruct
					.findField(FFTConstant.FUNCTION_SCORE_FIELD);
			int obstructRangeIdx = obstruct
					.findField(FFTConstant.FUNCTION_RANGE_FIELD);

			if (obstructScoreIdx == -1) {// 添加FFTConstant.FUNCTION_SCORE_FIELD
				Field f = new Field();
				f.setName(FFTConstant.FUNCTION_SCORE_FIELD);
				f
						.setType(com.esri.arcgis.geodatabase.esriFieldType.esriFieldTypeDouble);
				obstruct.addField(f);
				obstructScoreIdx = obstruct
						.findField(FFTConstant.FUNCTION_SCORE_FIELD);
			}
			if (obstructRangeIdx == -1) {// 添加FFTConstant.FUNCTION_RANGE_FIELD
				Field f = new Field();
				f.setName(FFTConstant.FUNCTION_RANGE_FIELD);
				f
						.setType(com.esri.arcgis.geodatabase.esriFieldType.esriFieldTypeDouble);
				obstruct.addField(f);
				obstructRangeIdx = obstruct
						.findField(FFTConstant.FUNCTION_RANGE_FIELD);
			}

			// 建立空间过滤，选取在当前区域内，当前计算等级的样本点（线）
			ISpatialFilter spatialFilter = new SpatialFilter();
			spatialFilter.setSpatialRel(esriSpatialRelEnum.esriSpatialRelContains);
			spatialFilter.setWhereClause(simpleQF.getWhereClause());

			// IFeatureCursor noCalcuCursor = noCalcuRange.search(null, true);
			IFeatureCursor simplePointCursor;
			IFeatureCursor obstructCursor;
			IFeature spFeature;
			IFeature oFeature;
			IFeature rangeFeature = noCalcuRange.nextFeature();

			while (rangeFeature != null) {// 遍历非计算区域的所有polygon

				spatialFilter.setGeometryByRef(rangeFeature.getShape());
				System.out.println(simpleQF.getWhereClause());
				System.out.println("range Fid:"+rangeFeature.getValue(0));
				
				
				obstructCursor = obstruct.IFeatureClass_update(null, false);
				oFeature = obstructCursor.nextFeature();
				while (oFeature != null) {// 遍历所有阻隔feature，为每个阻隔的“作用分”、“作用半径”赋值
					// 阻隔属性置零
					oFeature.setValue(obstructScoreIdx, new Double(0));
					oFeature.setValue(obstructRangeIdx, new Double(0));
					// obstructCursor.updateFeature(oFeature);
					System.out.println("Obstruct ID: "+oFeature.getOID());
					double minDistance = Double.MAX_VALUE;
					double currentDistance = 0;
					int nearestFid = -1;

					IProximityOperator po = new IProximityOperatorProxy(
							oFeature.getShape());
					
					simplePointCursor = simplePoint.search(spatialFilter, true);
					spFeature = simplePointCursor.nextFeature();
					while (spFeature != null) {// 遍历因素因子样本点，查找离当前阻隔最近的样本点
						// double distance=featureDistance(oFeature,spFeature);
						currentDistance = po.returnDistance(spFeature
								.getShape());
						if (currentDistance < minDistance) {
							nearestFid = spFeature.getOID();
							minDistance = currentDistance;

						}
						spFeature = simplePointCursor.nextFeature();
					}

					double score = 0;
					double range = 0;
					double tempScore = 0;
					double tempRange = 0;
					if (-1 != nearestFid) {
						tempRange = Double
								.parseDouble(simplePoint
										.getFeature(nearestFid)
										.getValue(
												simplePoint
														.findField(FFTConstant.FUNCTION_RANGE_FIELD))
										.toString());
						// tempRange是最近的点的作用范围，minDistance是最近的点到阻隔的距离
						range = tempRange - minDistance;
						if (range > 0) {
							tempScore = Double
									.parseDouble(simplePoint
											.getFeature(nearestFid)
											.getValue(
													simplePoint
															.findField(FFTConstant.FUNCTION_SCORE_FIELD))
											.toString());

							if (getFormula() == FFTConstant.FORMULA_LINEAR_ATTENUATION) {
								// 直线衰减计算公式
								score = tempScore
										* (1 - minDistance / tempRange);
							} else if (getFormula() == FFTConstant.FORMULA_EXPONENTIAL_ATTENUATION) {
								// 指数衰减计算公式
								score = Math.pow(tempScore, 1 - minDistance
										/ tempRange);
							}

							oFeature.setValue(obstructScoreIdx, new Double(
									score));
							oFeature.setValue(obstructRangeIdx, new Double(
									range));
							
						}
					}
					obstructCursor.updateFeature(oFeature);
					oFeature = obstructCursor.nextFeature();

				}
				rangeFeature = noCalcuRange.nextFeature();
			}

		} catch (AutomationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public abstract boolean canCalculate();

	public String[] getSrcFiles() {
		return srcFiles;
	}

	/**
	 * FORMULA_EXPONENTIAL_ATTENUATION=0 FORMULA_LINEAR_ATTENUATION=1
	 * 
	 * @return
	 */
	public int getFormula() {
		return formula;
	}

	/**
	 * FORMULA_EXPONENTIAL_ATTENUATION=0 FORMULA_LINEAR_ATTENUATION=1
	 * 
	 * @param formula
	 */
	public void setFormula(int formula) {
		this.formula = formula;
	}

	private double[] doubleSort(Object[] inDoubleArray) {
		double ret[] = new double[inDoubleArray.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = Double.parseDouble(inDoubleArray[i].toString());
		}
		// 冒泡一组：
		double temp;
		for (int i = 0; i < ret.length; i++) {
			for (int j = 0; j < ret.length - i - 1; j++) {
				if (ret[j] > ret[j + 1]) {
					temp = ret[j];
					ret[j] = ret[j + 1];
					ret[j + 1] = temp;
				}

			}

		}

		return ret;
	}

	public void setRanks(double[] ranks) {
		this.ranks = ranks;
	}

	public double[] getRanks() {
		return ranks;
	}

	public void setScores(double[] scores) {
		this.scores = scores;
	}

	public double[] getScores() {
		return scores;
	}

	public void setRanges(double[] ranges) {
		this.ranges = ranges;
	}

	public double[] getRanges() {
		return ranges;
	}

}
