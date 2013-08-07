/**
 * @(#) RankCMap.java
 */

package ynugis.classifyBusiness.classifyItem;

import java.io.IOException;
import java.net.UnknownHostException;

import ynugis.geo.Utility;
import ynugis.utility.GV;

import com.esri.arcgis.carto.FeatureLayer;
import com.esri.arcgis.carto.GroupLayer;
import com.esri.arcgis.carto.IFeatureLayer;
import com.esri.arcgis.datasourcesraster.IRasterBandCollection;
import com.esri.arcgis.datasourcesraster.IRasterBandCollectionProxy;
import com.esri.arcgis.datasourcesraster.RasterWorkspaceFactory;
import com.esri.arcgis.geoanalyst.INumberRemap;
import com.esri.arcgis.geoanalyst.IRasterAnalysisEnvironment;
import com.esri.arcgis.geoanalyst.IRasterAnalysisEnvironmentProxy;
import com.esri.arcgis.geoanalyst.IRasterConvertHelper;
import com.esri.arcgis.geoanalyst.IRasterDescriptor;
import com.esri.arcgis.geoanalyst.IReclassOp;
import com.esri.arcgis.geoanalyst.IRemapProxy;
import com.esri.arcgis.geoanalyst.NumberRemap;
import com.esri.arcgis.geoanalyst.RasterAnalysis;
import com.esri.arcgis.geoanalyst.RasterConvertHelper;
import com.esri.arcgis.geoanalyst.RasterDescriptor;
import com.esri.arcgis.geoanalyst.RasterReclassOp;
import com.esri.arcgis.geodatabase.Field;
import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IGeoDataset;
import com.esri.arcgis.geodatabase.IGeoDatasetProxy;
import com.esri.arcgis.geodatabase.IRaster;
import com.esri.arcgis.geodatabase.IRasterDataset;
import com.esri.arcgis.geodatabase.IRasterDatasetProxy;
import com.esri.arcgis.geodatabase.IRasterProxy;
import com.esri.arcgis.geodatabase.IWorkspace;
import com.esri.arcgis.geodatabase.IWorkspaceFactory;
import com.esri.arcgis.geometry.IArea;
import com.esri.arcgis.geometry.IAreaProxy;
import com.esri.arcgis.geometry.IGeometry;
import com.esri.arcgis.geometry.IPolygon;
import com.esri.arcgis.geometry.IPolygonProxy;
import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.spatialanalyst.IMapAlgebraOp;
import com.esri.arcgis.spatialanalyst.RasterMapAlgebraOp;

public class RankCMap extends CMap {
	private int rankCount;

	//private double smooth = -50;

	private IFeatureLayer fl;

	private IFeatureClass featureClass;

	IRaster raster;

	public RankCMap(CellValueCMap cvm, double[] start, double[] end, int[] value) {

		try {
			setRankCount(start.length);
			setName("初步分级图");
			// 分级
			IGeoDataset temp = doReclassify(cvm.getAsRaster(), start, end,
					value);
			// 将分级结果(GeoDataset) 转成Raster
			IRasterDataset rasterDataSet = new IRasterDatasetProxy(temp);
			IRasterBandCollection ib = new IRasterBandCollectionProxy(
					rasterDataSet);

			raster = new IRasterProxy(ib);

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 获取整型的raster
	 * 
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public IRaster getAsIntegerRaster(IGeoDataset data) throws Exception,
			IOException {
		IMapAlgebraOp mapAlgebraOp = new RasterMapAlgebraOp();
		// 赋值分析环境,设置临时目录
		IRasterAnalysisEnvironment rasterAnalysisEnvironment = new IRasterAnalysisEnvironmentProxy(
				mapAlgebraOp);
		IWorkspaceFactory rasterWSF = new RasterWorkspaceFactory();
		IWorkspace workspace = rasterWSF.openFromFile(GV
				.getDefaultTempFileDirectoryPath(), 0);
		rasterAnalysisEnvironment.setOutWorkspaceByRef(workspace);

		// 执行取整运算
		mapAlgebraOp.bindRaster(data, "R0");
		String cmd = "Int([R0])";
		IGeoDataset temp = mapAlgebraOp.execute(cmd);

		// 将GeoDataset 转成Raster
		IRasterDataset rasterDataSet = new IRasterDatasetProxy(temp);
		IRasterBandCollection ib = new IRasterBandCollectionProxy(rasterDataSet);
		IRaster ret = new IRasterProxy(ib);

		return ret;

	}

	public void getRankArea(Short index) {

	}

	/**
	 * 获取土地分级图的“层组”
	 * 
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public GroupLayer getGroupLayer() throws Exception, IOException {
		// 获取当前工作底图的所有featureClass

		GroupLayer ret = new GroupLayer();
		ret.setExpanded(false);
		// 设置层组的名称
		ret.setName("土地分级图");
		// IRasterLayer rl = getRasterLayer(cellValueCMap.getAsGeoDataset());
		fl = getAsFeatureLayer(raster);
		fl.setName(getName());
		ret.add(fl);

		return ret;

	}

	public void saveAsShapeFile(String path) throws UnknownHostException, IOException {
		Utility.FeatureClass2Shapefile(path,getFeatureClass());
		
	/*	保存Raster
			System.out.println("Now saving...Please Waiting... ");
			Utility.saveFeatureClass2Shp(path,getAsPloygonFeatureClass() );
			System.out.println("Congratulations!!!Save Complete");
		
		*/
		

	}
	
	public void saveAsMif(String path) throws UnknownHostException, IOException {
		//String wkspPath = GT.getFilePath(path);
		//String outName = GT.getFileName(path);
		Utility.FeatureClass2MIF(path,getFeatureClass());
	}
	
	public void saveAsTab(String path) throws UnknownHostException, IOException {
//		String wkspPath = GT.getFilePath(path);
		//String outName = GT.getFileName(path);
		Utility.FeatureClass2TAB(path,getFeatureClass());

	}

	/**
	 * 将原来raster中start到end范围内的值重分类为value值
	 * 
	 * @param start
	 * @param end
	 * @param value
	 * @return
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private IGeoDataset doReclassify(IRaster inRst, double start[],
			double end[], int value[]) throws UnknownHostException, IOException {
		// Create a raster descriptor and specify the field to be used for
		// reclassify
		IRasterDescriptor rasDescriptor = new RasterDescriptor();
		rasDescriptor.create(inRst, null, "Value");

		// Create a RasterReclassOp operator
		IReclassOp reclassOp = new RasterReclassOp();

		// Set output workspace.Specify GRID's output filepath
		IWorkspaceFactory rasterWSF = new RasterWorkspaceFactory();
		IWorkspace rasterWS = rasterWSF.openFromFile(GV
				.getDefaultTempFileDirectoryPath(), 0);
		IRasterAnalysisEnvironment rasterEnv = (IRasterAnalysisEnvironment) reclassOp;
		rasterEnv.setOutWorkspaceByRef(rasterWS);

		// Create a StringRemap object and specify remap
		INumberRemap numRemap = new NumberRemap();

		// 将原来raster中start[i]到end[i]范围内的值指定为value[i]
		for (int i = 0; i < start.length; i++) {
			numRemap.mapRange(start[i], end[i], value[i]);
		}

		IGeoDataset reclassResult = reclassOp.reclassByRemap(
				new IGeoDatasetProxy(rasDescriptor), new IRemapProxy(numRemap),
				false);

		return reclassResult;

	}

	/**
	 * 返回一个polygon的FeatureClass 将Raster的单元格缩小multiple倍显示
	 * 
	 * @return
	 */
	public IFeatureLayer getAsFeatureLayer(IRaster intRaster) {

		// 将Raster缩小multiple倍显示

		IFeatureLayer ret = null;
		try {
			// intRaster = getAsIntegerRaster(multiple);

			// 使用RasterConvertHelper把Raster转换为FeatureClass
			IRasterConvertHelper pRasConvertHelper = new RasterConvertHelper();

			IRasterAnalysisEnvironment pEnv = new RasterAnalysis();
			pEnv
					.setCellSize(
							com.esri.arcgis.geoanalyst.esriRasterEnvSettingEnum.esriRasterEnvMaxOf,
							intRaster);

			IFeatureClass pOutFClass = pRasConvertHelper
					.toShapefile(
							new IGeoDatasetProxy(intRaster),
							com.esri.arcgis.geometry.esriGeometryType.esriGeometryPolygon,
							pEnv);

			Field areaField = new Field();
			areaField.setName("AREA");
			areaField
					.setType(com.esri.arcgis.geodatabase.esriFieldType.esriFieldTypeDouble);
			pOutFClass.addField(areaField);
			int count = pOutFClass.featureCount(null);
			IFeature tempF;
			for (int i = 0; i < count; i++) {
				tempF = pOutFClass.getFeature(i);
				IGeometry geometry = tempF.getShape();
				// 如果当前geometry是polygon，就QI出IArea，计算面积
				if (geometry.getGeometryType() == com.esri.arcgis.geometry.esriGeometryType.esriGeometryPolygon) {
					IPolygon polygon = new IPolygonProxy(tempF.getShape());
					// 对ploygon进行平滑处理
					//polygon.smooth(getSmooth());

					IArea area = new IAreaProxy(polygon);

					tempF.setValue(pOutFClass.getFields().findField("AREA"),
							new Double(area.getArea()));
					tempF.store();
					System.out.println("AREA:" + area.getArea());
				}

			}
			
			//把生成的featureClass保存下来	
			setFeatureClass(pOutFClass);

			// 将转出的featureClass放入featureLayer中
			ret = new FeatureLayer();
			ret.setFeatureClassByRef(pOutFClass);
			/*// GRIDCODE 是转换过程中自动生成的filed的NAME，该field就代表Raster的Cell Value
			ret.setDisplayField("GRIDCODE");

			IGeoFeatureLayer pGeoFL = new FeatureLayer(ret);
			IAnnotateLayerPropertiesCollection pAnnoLayerPropsColl = pGeoFL
					.getAnnotationProperties();
			pAnnoLayerPropsColl.clear();

			// 进行Label的相关设置：
			ILabelEngineLayerProperties pAnnoLayerProps;
			ILineLabelPosition pPosition = new LineLabelPosition();
			pPosition.setParallel(false);
			pPosition.setPerpendicular(true);
			LineLabelPlacementPriorities pPlacement = new LineLabelPlacementPriorities();

			IBasicOverposterLayerProperties pBas = new BasicOverposterLayerProperties();
			pBas
					.setFeatureType(com.esri.arcgis.geometry.esriGeometryType.esriGeometryPolygon);
			pBas.setLineLabelPlacementPriorities(pPlacement);
			pBas.setLineLabelPosition(pPosition);
			ILabelEngineLayerProperties pLabelEngine = new LabelEngineLayerProperties();
			pLabelEngine.setBasicOverposterLayerPropertiesByRef(pBas);
			// 显示当前ploygon的等级和面积,VBA
			String vbaStr = "\"等级：\" &"
					+ "[GRIDCODE] & vbCrLf & \"面积：\" & [AREA] & \"平方米\"";
			pLabelEngine.setExpression(vbaStr);
			System.out.println(vbaStr);
			pAnnoLayerProps = pLabelEngine;
			// 将设置的结果加入到label的标注集合中
			pAnnoLayerPropsColl.add(new IAnnotateLayerPropertiesProxy(
					pAnnoLayerProps));
			// 显示标志集合中的所有标注方案
			pGeoFL.setDisplayAnnotation(true);*/
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;

	}

	/*public double getSmooth() {
		return smooth;
	}

	public void setSmooth(double smooth) {
		this.smooth = smooth;
	}
*/
	public IFeatureClass getFeatureClass() throws AutomationException,
			IOException {
		if (featureClass == null)
			return getAsFeatureLayer(raster).getFeatureClass();
		return featureClass;
	}

	public void setFeatureClass(IFeatureClass featureClass) {
		this.featureClass = featureClass;
	}

	/**
	 *获取土地分级数目
	 * @return
	 */
	public int getRankCount() {
		return rankCount;
	}

	public void setRankCount(int rankCount) {
		this.rankCount = rankCount;
	}

}
