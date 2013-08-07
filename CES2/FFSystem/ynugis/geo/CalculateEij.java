/*
* @author 冯涛，创建日期：2003-10-16
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
package ynugis.geo;

import java.io.IOException;

import ynugis.utility.GT;

import com.esri.arcgis.geoanalyst.FeatureClassDescriptor;
import com.esri.arcgis.geoanalyst.IRasterAnalysisEnvironment;
import com.esri.arcgis.geoanalyst.IRasterAnalysisEnvironmentProxy;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IGeoDataset;
import com.esri.arcgis.geodatabase.IQueryFilter;
import com.esri.arcgis.geodatabase.ISelectionSet;
import com.esri.arcgis.geodatabase.QueryFilter;
import com.esri.arcgis.geodatabase.esriSelectionOption;
import com.esri.arcgis.geodatabase.esriSelectionType;
import com.esri.arcgis.spatialanalyst.IMapAlgebraOp;
import com.esri.arcgis.spatialanalyst.RasterMapAlgebraOp;

public class CalculateEij {
	private static final String FUNCTION_SCORE_FIELD="作用分";
	private static final String FUNCTION_RANGE_FIELD="作用半径";
	private static final String FUNCTION_RANK_FIELD="级别";
	
	/**
	 *  计算指定featureClass的作用分
	 * 
	 * @param inFeature
	 * @param output
	 * @param cellSize
	 * @param mask 可用来指定定级范围
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public static IGeoDataset calculate_E_Score(IFeatureClass inFeature,IRasterAnalysisEnvironment env
			) throws Exception, IOException {
		
		//定义表示功能分和作用半径的变量
		double functionScore = 0;
		double range = 0;
		
		FeatureClassDescriptor fcd;
		IMapAlgebraOp mapAlgebraOp = new RasterMapAlgebraOp();

		/* Set outputPath workspace */
		IRasterAnalysisEnvironment rasterAnalysisEnvironment = new IRasterAnalysisEnvironmentProxy(
				mapAlgebraOp);
		/*IWorkspaceFactory rasterWSF = new RasterWorkspaceFactory();
		IWorkspace workspace = rasterWSF.openFromFile(output, 0);
		rasterAnalysisEnvironment.setOutWorkspaceByRef(workspace);
		*/
		//用mask来界定栅格分析范围
		Utility.RasterAnalysisEnvCopy(env,rasterAnalysisEnvironment);
		
		/*if(mask!=null)
		{
			
			fcd=new FeatureClassDescriptor();
			fcd.createFromSelectionSet(mask.select(null,
					esriSelectionType.esriSelectionTypeIDSet,
					esriSelectionOption.esriSelectionOptionNormal, null), null, "FID");
			
			rasterAnalysisEnvironment.setMaskByRef(fcd.getAsIGeoDataset());
			//rasterAnalysisEnvironment.setExtent()
			rasterAnalysisEnvironment.setOutSpatialReferenceByRef(fcd.getAsIGeoDataset().getSpatialReference());
		}*/
		
		ShpDistanceOp sdo = new ShpDistanceOp(env);
		ISelectionSet selectionSet = inFeature.select(null,
				esriSelectionType.esriSelectionTypeIDSet,
				esriSelectionOption.esriSelectionOptionNormal, null);
		int Count = selectionSet.getCount();
		System.out.println("selectionSet.getCount():" + Count);

		IQueryFilter qf = new QueryFilter();

		

		//这里的功能分实际上是“作用分”
		int scoreIdx = inFeature.getFields().findField(FUNCTION_SCORE_FIELD);
		int rangeIdx = inFeature.getFields().findField(FUNCTION_RANGE_FIELD);
		String scoreStr;
		String rangeStr;
		IGeoDataset disTemp;
		IGeoDataset result=null;
		//mapAlgebraOp.bindRaster(result, "result");
		
		fcd=new FeatureClassDescriptor();
		
		//因为每一个feature的“功能分”和“作用半径”都可能不同，下面的程序用每一个feature生成
		//单独的raster，最后把这些raster叠加得到输入featureClass的总“作用分”：
		for (int i = 0; i < Count; i++) {

			qf.setWhereClause("FID = " + i);

			fcd.createFromSelectionSet(selectionSet, qf, "作用分");

			//获取当前feature的功能分和作用半径
			scoreStr = inFeature.getFeature(i).getValue(scoreIdx).toString();
			functionScore = Double.valueOf(scoreStr).doubleValue();
			System.out.println("作用分:" + functionScore);

			rangeStr = inFeature.getFeature(i).getValue(rangeIdx).toString();
			range = Double.valueOf(rangeStr).doubleValue();
			System.out.println("作用半径:" + range);

			disTemp = sdo.doStraightLine((IGeoDataset) fcd.getFeatureClass(), range);

			mapAlgebraOp.bindRaster(disTemp, "R1");
			
			
			if(result==null)
			{
				result=mapAlgebraOp.execute(functionScore + " * ( 1 - [R1] / " + range+ " )");
				mapAlgebraOp.bindRaster(result, "R0");
				//将[R0]定中的nodata格指定为“0”；
				result=mapAlgebraOp.execute("CON(isNull([R0]) ,0 ,[R0])");
				//nodata2Zero(result,workspace);
				mapAlgebraOp.bindRaster(result, "R0");
				
			}else 
			{
				IGeoDataset temp=mapAlgebraOp.execute(+functionScore + " * (1 - [R1] / " + range+ ")");
				//nodata2Zero(temp,workspace);
				mapAlgebraOp.bindRaster(temp, "Rt");
				
				//将[Rt]定中的nodata格指定为“0”；
				temp=mapAlgebraOp.execute("CON(isNull([Rt]) ,0 ,[Rt])");
				
				mapAlgebraOp.bindRaster(temp, "Rt");
				result=mapAlgebraOp.execute("[Rt] + [R0]");
				mapAlgebraOp.bindRaster(result, "R0");
				
			}
		}
	
		return result;
	}
	
	/**
	 * 计算指定的ESRI.SHAPEFILE文件中的featureClass的作用分
	 * 
	 * @param shpPath
	 * @param output
	 * @param cellSize
	 * @param mask
	 * @return 
	 * @throws Exception
	 * @throws IOException
	 */
	public static IGeoDataset calculate_E_Score(String shpPath,
			IRasterAnalysisEnvironment env) throws Exception, IOException {
	
		IFeatureClass fc=Utility.OpenFeatureClass(GT.getFilePath(shpPath),GT.getFileName(shpPath));
		return calculate_E_Score(fc,env);
	}


}
