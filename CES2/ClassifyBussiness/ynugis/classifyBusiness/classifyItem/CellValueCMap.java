/**
 * @(#) CellValueCMap.java
 */

package ynugis.classifyBusiness.classifyItem;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;

import ynugis.geo.Utility;
import ynugis.utility.GT;
import ynugis.utility.GV;

import com.esri.arcgis.carto.FeatureLayer;
import com.esri.arcgis.carto.GroupLayer;
import com.esri.arcgis.carto.IFeatureLayer;
import com.esri.arcgis.carto.IRasterLayer;
import com.esri.arcgis.datasourcesraster.IRasterBandCollection;
import com.esri.arcgis.datasourcesraster.IRasterBandCollectionProxy;
import com.esri.arcgis.datasourcesraster.Raster;
import com.esri.arcgis.datasourcesraster.RasterWorkspaceFactory;
import com.esri.arcgis.geoanalyst.IRasterAnalysisEnvironment;
import com.esri.arcgis.geoanalyst.IRasterAnalysisEnvironmentProxy;
import com.esri.arcgis.geoanalyst.IRasterConvertHelper;
import com.esri.arcgis.geoanalyst.RasterAnalysis;
import com.esri.arcgis.geoanalyst.RasterConvertHelper;
import com.esri.arcgis.geodatabase.Field;
import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureCursor;
import com.esri.arcgis.geodatabase.IGeoDataset;
import com.esri.arcgis.geodatabase.IGeoDatasetProxy;
import com.esri.arcgis.geodatabase.IRaster;
import com.esri.arcgis.geodatabase.IRasterDataset;
import com.esri.arcgis.geodatabase.IRasterDatasetProxy;
import com.esri.arcgis.geodatabase.IRasterProxy;
import com.esri.arcgis.geodatabase.IWorkspace;
import com.esri.arcgis.geodatabase.IWorkspaceFactory;
import com.esri.arcgis.spatialanalyst.IMapAlgebraOp;
import com.esri.arcgis.spatialanalyst.RasterMapAlgebraOp;

public class CellValueCMap extends CMap {

	private IRaster raster;

	private double multiple = GV.multiple;

	private IGeoDataset geoDataset;

	private IFeatureClass polygonFeatureClass;

	// private IRasterAnalysisEnvironment env;
	public CellValueCMap(IGeoDataset data) {

		try {
			setName("单元格分值图");
			geoDataset = data;
			raster = new Raster(data);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public IRaster getAsRaster() {
		return raster;
	}

	/**
	 * 获取整型的raster
	 * 
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public IRaster getAsIntegerRaster() throws Exception, IOException {
		IMapAlgebraOp mapAlgebraOp = new RasterMapAlgebraOp();
		// 赋值分析环境,设置临时目录
		IRasterAnalysisEnvironment rasterAnalysisEnvironment = new IRasterAnalysisEnvironmentProxy(
				mapAlgebraOp);
		IWorkspaceFactory rasterWSF = new RasterWorkspaceFactory();
		IWorkspace workspace = rasterWSF.openFromFile(GV
				.getDefaultTempFileDirectoryPath(), 0);
		rasterAnalysisEnvironment.setOutWorkspaceByRef(workspace);

		// 执行取整运算
		mapAlgebraOp.bindRaster(getAsGeoDataset(), "R0");
		String cmd = "Int([R0])";
		IGeoDataset temp = mapAlgebraOp.execute(cmd);

		// 将GeoDataset 转成Raster
		IRasterDataset rasterDataSet = new IRasterDatasetProxy(temp);
		IRasterBandCollection ib = new IRasterBandCollectionProxy(rasterDataSet);
		IRaster ret = new IRasterProxy(ib);

		return ret;

	}

	/**
	 * 获取放大了multiple倍的整型raster
	 * 
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public IRaster getAsIntegerRaster(double multiple) throws Exception,
			IOException {
		IMapAlgebraOp mapAlgebraOp = new RasterMapAlgebraOp();
		// 赋值分析环境,设置临时目录
		IRasterAnalysisEnvironment rasterAnalysisEnvironment = new IRasterAnalysisEnvironmentProxy(
				mapAlgebraOp);
		IWorkspaceFactory rasterWSF = new RasterWorkspaceFactory();
		IWorkspace workspace = rasterWSF.openFromFile(GV.getDefaultTempFileDirectoryPath(), 0);
		rasterAnalysisEnvironment.setOutWorkspaceByRef(workspace);

		// 执行取整运算
		mapAlgebraOp.bindRaster(getAsGeoDataset(), "R0");
		String cmd = "Int([R0] * " + multiple + ")";
		IGeoDataset temp = mapAlgebraOp.execute(cmd);

		// 将GeoDataset 转成Raster
		IRasterDataset rasterDataSet = new IRasterDatasetProxy(temp);
		IRasterBandCollection ib = new IRasterBandCollectionProxy(rasterDataSet);
		IRaster ret = new IRasterProxy(ib);

		return ret;

	}

	public IGeoDataset getAsGeoDataset() {

		return geoDataset;
	}

	/**
	 * 
	 * @return
	 */
	public IFeatureClass getAsPloygonFeatureClass() {

		if (polygonFeatureClass != null)
			return polygonFeatureClass;
		
		AssemblyFC afc=new AssemblyFC(Thread.currentThread(),false);
		afc.start();
		Thread.currentThread().suspend();
		
		
	/*	
		while(true)
		{
			
			if(afc.ok)
			{
				//System.out.println("OK lA~");
				break;
			}else
			{
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}*/
		
		return polygonFeatureClass;
	}
	
	/**
	 * 
	 * @return
	 */
	public IFeatureClass getAsPloygonFeatureClass(boolean format) {

		if (polygonFeatureClass != null)
			return polygonFeatureClass;
		
		AssemblyFC afc=new AssemblyFC(Thread.currentThread(),format);
		afc.start();
		Thread.currentThread().suspend();
		return polygonFeatureClass;
	}

	/*
	 * public IRaster getAsReclassRaster(double start[], double end[], int
	 * value[]) throws UnknownHostException, IOException { IGeoDataset temp =
	 * doReclassify(start, end, value); // 将GeoDataset 转成Raster IRasterDataset
	 * rasterDataSet = new IRasterDatasetProxy(temp); IRasterBandCollection ib =
	 * new IRasterBandCollectionProxy(rasterDataSet); IRaster ret = new
	 * IRasterProxy(ib);
	 * 
	 * return ret; }
	 */

	/*
	 * public IFeatureClass getAsFeatureClass() { IFeatureClass
	 * fc=Utility.Raster2Shp(env,geoDataset); return fc; }
	 */
	public void saveAsShapeFile(String path) throws UnknownHostException,
			IOException {
		Utility.FeatureClass2Shapefile(path, getAsPloygonFeatureClass(true));

	}

	public void saveAsMif(String path) throws UnknownHostException, IOException {
		// String wkspPath = GT.getFilePath(path);
		// String outName = GT.getFileName(path);
		Utility.FeatureClass2MIF(path, getAsPloygonFeatureClass(true));
	}

	public void saveAsTab(String path) throws UnknownHostException, IOException {
		// String wkspPath = GT.getFilePath(path);
		// String outName = GT.getFileName(path);
		Utility.FeatureClass2TAB(path, getAsPloygonFeatureClass(true));

	}

	/**
	 * 获取单元格分值图的“层组”
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
		ret.setName("单元格分值图");
		
		GT.timeRecordStart("k1");
		
		// 向组中添加Raster分值图层
		IRasterLayer rl = getRasterLayer(getAsGeoDataset());
		rl.setName(getName());
		rl.setVisible(false);
		rl.setName("计算结果");
		
		GT.timeRecordend("k1","向组中添加Raster分值图层");
		
		GT.timeRecordStart("k1");
		/*
		 * 添加FeatureClass 用以标注分数
		 * 
		 */
		
		IFeatureLayer fl = new FeatureLayer();
		fl.setFeatureClassByRef(getAsPloygonFeatureClass());
		
		GT.timeRecordend("k1","向组中添加单元格分值图层");
		
		fl.setName("单元格分值");
		fl.setVisible(true);

		
		ret.add(fl);
		ret.add(rl);
		return ret;

	}
	private class AssemblyFC extends Thread
	{	
		public boolean ok=false;
		private boolean flag=false;
		private Thread parentThread=null;
		
		public void run() {
			//parentThread.suspend();
			
			IRaster intRaster;
			try {
				/*//RasterDescriptor pRasDescriptor = new RasterDescriptor();
				//pRasDescriptor.create(getAsRaster(),null,"GRIDCODE");
				RasterConversionOp pConversionOp = new RasterConversionOp();
				IWorkspaceFactory pWSF = new ShapefileWorkspaceFactory();
				IWorkspace pWS=pWSF.openFromFile("c:\\temp", 0);
				IFeatureClass pOutFClass = new IFeatureClassProxy(pConversionOp.rasterDataToPolygonFeatureData(getAsGeoDataset(),pWS,"apower.shp",true));*/
				intRaster = getAsIntegerRaster(multiple);
				IRasterConvertHelper pRasConvertHelper = new RasterConvertHelper();

				IRasterAnalysisEnvironment pEnv = new RasterAnalysis();
				pEnv
						.setCellSize(
								com.esri.arcgis.geoanalyst.esriRasterEnvSettingEnum.esriRasterEnvValue,
								intRaster);
				
				IFeatureClass pOutFClass = pRasConvertHelper
						.toShapefile(
								new IGeoDatasetProxy(intRaster),
								//getAsGeoDataset(),
								com.esri.arcgis.geometry.esriGeometryType.esriGeometryPolygon,
								pEnv);
				if(flag)
				{
				
				int IDIdx = pOutFClass.findField("ID");
				pOutFClass.deleteField(pOutFClass.getFields().getField(IDIdx));
				// IField
				// cellValueFiled=pOutFClass.getFields().getField(pOutFClass.getFields().findField("GRIDCODE"));

				
				Field areaField = new Field();
				areaField.setName("单元格分值");
				areaField
						.setType(com.esri.arcgis.geodatabase.esriFieldType.esriFieldTypeDouble);
				pOutFClass.addField(areaField);

				// Now, add the buffer polygon as a feature
				// IFeatureClass newFC=getEmptyFeatureClass();
				// IFeatureCursor pFC = newFC.IFeatureClass_insert(true);

				IFeatureCursor cursor = pOutFClass
						.IFeatureClass_update(null, false);
				IFeature feature = cursor.nextFeature();
				int GCIdx = pOutFClass.findField("GRIDCODE");

				int scoreIdx = pOutFClass.findField("单元格分值");

				double d = 0;
				Object value = null;
				DecimalFormat df = new DecimalFormat("#.00");
				GT.timeRecordStart("k2");
				
				while (feature != null) {
					
					d = Double.parseDouble(feature.getValue(GCIdx).toString())
							/ multiple;
					
					// 将作用分截成两位
					d = Double.parseDouble(df.format(d));
					value = new Double(d);
					feature.setValue(scoreIdx, value);
					cursor.updateFeature(feature);
					
					feature = cursor.nextFeature();

				}
				
				GT.timeRecordend("k2","单元格分值截短");
				
			
				 
				pOutFClass.deleteField(pOutFClass.getFields().getField(GCIdx));
/*
				IField cellValueFiled = pOutFClass.getFields().getField(
						pOutFClass.findField("单元格分值"));

				IFieldEdit fe = new IFieldEditProxy(cellValueFiled);
				fe.setName("GRIDCODE");
				*/
				}
				polygonFeatureClass = pOutFClass;
				//notify thread has finished
				ok=true;
				parentThread.resume();
				//return polygonFeatureClass;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		public AssemblyFC(Thread t,boolean format) {
			parentThread=t;
			flag=format;
		}
			
		
		
	}
}
