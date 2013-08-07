/*
* @author 冯涛，创建日期：2003-11-16
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
package ynugis.geo;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.UnknownHostException;

import ynugis.dataconvert.ConvertEnvironment;
import ynugis.utility.GT;
import ynugis.utility.GV;

import com.esri.arcgis.carto.IRasterLayer;
import com.esri.arcgis.carto.RasterLayer;
import com.esri.arcgis.datasourcesfile.ShapefileWorkspaceFactory;
import com.esri.arcgis.datasourcesraster.IRasterBandCollection;
import com.esri.arcgis.datasourcesraster.IRasterBandCollectionProxy;
import com.esri.arcgis.datasourcesraster.Raster;
import com.esri.arcgis.datasourcesraster.RasterWorkspace;
import com.esri.arcgis.datasourcesraster.RasterWorkspaceFactory;
import com.esri.arcgis.geoanalyst.IRasterAnalysisEnvironment;
import com.esri.arcgis.geoanalyst.RasterAnalysis;
import com.esri.arcgis.geoanalyst.RasterConvertHelper;
import com.esri.arcgis.geoanalyst.esriRasterEnvSettingEnum;
import com.esri.arcgis.geodatabase.FeatureClassName;
import com.esri.arcgis.geodatabase.FeatureDataConverter;
import com.esri.arcgis.geodatabase.IDataset;
import com.esri.arcgis.geodatabase.IDatasetName;
import com.esri.arcgis.geodatabase.IDatasetNameProxy;
import com.esri.arcgis.geodatabase.IDatasetProxy;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureClassName;
import com.esri.arcgis.geodatabase.IFeatureClassNameProxy;
import com.esri.arcgis.geodatabase.IFeatureDataConverter;
import com.esri.arcgis.geodatabase.IFeatureWorkspace;
import com.esri.arcgis.geodatabase.IFeatureWorkspaceProxy;
import com.esri.arcgis.geodatabase.IGeoDataset;
import com.esri.arcgis.geodatabase.IGeoDatasetProxy;
import com.esri.arcgis.geodatabase.IRaster;
import com.esri.arcgis.geodatabase.IRasterDataset;
import com.esri.arcgis.geodatabase.IRasterDatasetProxy;
import com.esri.arcgis.geodatabase.IRasterProxy;
import com.esri.arcgis.geodatabase.IWorkspace;
import com.esri.arcgis.geodatabase.IWorkspaceFactory;
import com.esri.arcgis.geodatabase.IWorkspaceName;
import com.esri.arcgis.geodatabase.IWorkspaceProxy;
import com.esri.arcgis.geodatabase.WorkspaceName;
import com.esri.arcgis.geometry.Envelope;
import com.esri.arcgis.geometry.IEnvelope;
import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.system.IName;

public class Utility {

	/**
	 * 打开ESRI.SHAPEFILE文件
	 * 
	 * @param stringWorkspace
	 * @param stringFeatureClass
	 * @return
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static IFeatureClass OpenFeatureClass(String workspacePath,
			String featureClassName) throws UnknownHostException, IOException {

		
			ShapefileWorkspaceFactory workspaceFactory = new ShapefileWorkspaceFactory();
			IFeatureWorkspace featureWorkspace = new IFeatureWorkspaceProxy(
					workspaceFactory.openFromFile(workspacePath, 0));
			
			IFeatureClass featureClass = featureWorkspace
					.openFeatureClass(featureClassName);

			return featureClass;
		

		/*
		 * Create the workspace name object IWorkspaceName workspaceName =
		 * new WorkspaceName(); workspaceName.setPathName(stringWorkspace);
		 * workspaceName
		 * .setWorkspaceFactoryProgID("esriDataSourcesFile.ShapefileWorkspaceFactory");
		 *  // Create the feature class name object IDatasetName datasetName =
		 * new FeatureClassName(); datasetName.setName(stringFeatureClass);
		 * datasetName.setWorkspaceNameByRef(workspaceName);
		 *  // Open the feature class IName name = (IName) datasetName;
		 *  // Return FeaureClass return new IFeatureClassProxy(name.open());
		 */
	}

	/**
	 * 打开ESRI.SHAPEFILE文件
	 * 
	 * @param stringWorkspace
	 * @param stringFeatureClass
	 * @return
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static IFeatureClass OpenFeatureClass(String shpFile)
			throws UnknownHostException, IOException {

		File f=new File(shpFile);
		if(!f.exists())
		{
			System.out.println("ShapeFile:"+shpFile+"\n不存在！！！");
			return null;
		}
		String workspacePath = GT.getFilePath(shpFile);
		String featureClassName = GT.getFileNameWithoutExtension(shpFile);
		
		return OpenFeatureClass(workspacePath, featureClassName);

		/*
		 * // Create the workspace name object IWorkspaceName workspaceName =
		 * new WorkspaceName(); workspaceName.setPathName(stringWorkspace);
		 * workspaceName
		 * .setWorkspaceFactoryProgID("esriDataSourcesFile.ShapefileWorkspaceFactory");
		 *  // Create the feature class name object IDatasetName datasetName =
		 * new FeatureClassName(); datasetName.setName(stringFeatureClass);
		 * datasetName.setWorkspaceNameByRef(workspaceName);
		 *  // Open the feature class IName name = (IName) datasetName;
		 *  // Return FeaureClass return new IFeatureClassProxy(name.open());
		 */
	}

	/**
	 * 把source的设置赋予desitination
	 * 
	 * @param source
	 * @param desitination
	 * @throws Exception
	 * @throws IOException
	 */
	public static void RasterAnalysisEnvCopy(IRasterAnalysisEnvironment source,
			IRasterAnalysisEnvironment desitination) throws Exception,
			IOException {

		desitination.setAsNewDefaultEnvironment();

		// set Cellsize
		int[] i = { 0 };
		double[] d = { 0 };
		source.getCellSize(i, d);
		desitination.setCellSize(i[0], new Double(d[0]));
		// set Mask
		desitination.setMaskByRef(source.getMask());
		// set OutWorkspacee
		desitination.setOutWorkspaceByRef(source.getOutWorkspace());

		// set OutSpatialReference
		desitination.setOutSpatialReferenceByRef(source
				.getOutSpatialReference());
		// set Extent
		int envType[] = new int[1];
		IEnvelope[] e = { new Envelope() };
		// TODO:
		source.getExtent(envType, e);

		desitination.setExtent(envType[0], e[0], null);

	}
	/*public static IRasterDataset shp2Raster(IRasterAnalysisEnvironment env,IGeoDataset geoDataset,String name)
	{
		IRasterDataset ret=null;
		//Create raster conversion operator and set up analysis environment,
	      // then use it to make the output raster.
	      try {
	    	  RasterConversionOp rasterConversionOp = new RasterConversionOp() ;
			// Again,an interface that has to be present on the object.
	        IRasterAnalysisEnvironment rasterAnalysisEnvironment = (IRasterAnalysisEnvironment) rasterConversionOp ;
	        RasterAnalysisEnvCopy(env,rasterAnalysisEnvironment);
	        rasterAnalysisEnvironment.setCellSize(com.esri.arcgis.geoanalyst.esriRasterEnvSettingEnum.esriRasterEnvValue,new Double(50));
	        
	        //如果文件存在就删除
	        String path=rasterAnalysisEnvironment.getOutWorkspace().getPathName()+File.separator+outRasterName;
	        File f=new File(path);
	        if(f.exists())
	        {
	        	if(!f.delete())System.out.println("apower faild....");
	        }
	        // All this to submit one command that does something useful.
	        // No need to assign the results to an IRasterDataset, since it will not be used.
	         
	        //rasterConversionOp.setDefaultOutputRasterPrefix(inPrefix);
	        
	        ret=rasterConversionOp.toRasterDataset(geoDataset, "GRID", rasterAnalysisEnvironment.getOutWorkspace(),name) ;
	         
	        
	        
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
		
	}*/
	public static IGeoDataset shp2Raster(IRasterAnalysisEnvironment env,IGeoDataset geoDataset) throws AutomationException, IOException
	{
		RasterConvertHelper rch;
		IRaster r=null;
		try {
			rch = new RasterConvertHelper();
			r=rch.toRaster1(geoDataset,"GRID",env);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IGeoDataset ged=new IGeoDatasetProxy(r);
		IRasterLayer rasterLayers = new RasterLayer();
		IRasterDataset rasterDataSet = new IRasterDatasetProxy(ged);
		IRasterBandCollection ib = new IRasterBandCollectionProxy(rasterDataSet);
		IRaster raster = new IRasterProxy(ib);

		rasterLayers = new RasterLayer();
		rasterLayers.createFromRaster( raster);
		
		return new IGeoDatasetProxy(r);
		

		
	}
	
	/**
	 * @param inFC
	 * @param cellSizeProvider
	 * @return
	 * @throws AutomationException
	 * @throws IOException
	 */
	public static IRaster shp2Raster(IFeatureClass inFC,Object cellSizeProvider) throws AutomationException, IOException
	{
		RasterConvertHelper rch;
		IRaster r=null;
		try {
			RasterAnalysis ra = new RasterAnalysis();
			ra.setAsNewDefaultEnvironment();
			// Cell Size
			ra.setCellSize(esriRasterEnvSettingEnum.esriRasterEnvValue, cellSizeProvider);
			// output File Path:
			IWorkspaceFactory wkspfactory = new RasterWorkspaceFactory();
			IWorkspace wksp = new IWorkspaceProxy(wkspfactory.openFromFile(
					GV.getDefaultTempFileDirectoryPath(), 0));
			ra.setOutWorkspaceByRef(wksp);
			
			
			IGeoDataset geoDataset=new IGeoDatasetProxy(inFC);
			
			rch = new RasterConvertHelper();
			r=rch.toRaster1(geoDataset,"GRID",ra);
			
			
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*IGeoDataset ged=new IGeoDatasetProxy(r);
		IRasterLayer rasterLayers = new RasterLayer();
		IRasterDataset rasterDataSet = new IRasterDatasetProxy(ged);
		IRasterBandCollection ib = new IRasterBandCollectionProxy(rasterDataSet);
		IRaster raster = new IRasterProxy(ib);

		rasterLayers = new RasterLayer();
		rasterLayers.createFromRaster( raster);*/
		
		return r;
		

		
	}
	public static IFeatureClass Raster2Shp(IRasterAnalysisEnvironment env,IGeoDataset geoDataset)
	{
		RasterConvertHelper rch;
		IFeatureClass fc=null;
		try {
			rch = new RasterConvertHelper();
			fc=rch.toShapefile(geoDataset,com.esri.arcgis.geometry.esriGeometryType.esriGeometryPoint,env);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fc;
	}
	public static boolean deleteShp(String path) {
		final String shpName = GT.getFileNameWithoutExtension(path);
		File shpDirectory = new File(GT.getFilePath(path));
		// 得到各种后缀的、名为shpName，的文件
		File[] files = shpDirectory.listFiles(new FileFilter() {

			public boolean accept(File arg0) {
				if (arg0.isDirectory())
					return false;
				String t = GT.getFileNameWithoutExtension(arg0
						.getAbsolutePath());
				
				return t.equals(shpName);
			}
		});

		// 删除各种后缀的、名为shpname，的文件
		for (int i = 0; i < files.length; i++) {
			if (!files[i].delete())
				return false;

		}
		return true;
	}
	/*public static void saveFeatureClass2Shp(final String path,final IFeatureClass srcFC) throws UnknownHostException, IOException
	{
		Thread t=new Thread(new Runnable(){

			public void run() {
				String filePath = GT.getFilePath(path);
				String fileName = GT.getFileName(path);
				
				
				String strShapeFieldName = "Shape";

				// Open the folder to contain the shapefile as a workspace
				IFeatureWorkspace pFWS = null;
				IWorkspaceFactory pWorkspaceFactory = null;
				try {
					pWorkspaceFactory = new ShapefileWorkspaceFactory();
					pFWS = new IFeatureWorkspaceProxy(pWorkspaceFactory.openFromFile(
							filePath, 0));

					

					// Create the shapefile (some parameters apply to geodatabase
					// options and can be defaulted as Nothing)
					pFWS.createFeatureClass(fileName,srcFC.getFields(), null, null,
							esriFeatureType.esriFTSimple, strShapeFieldName, "");
					
					
						IFeatureClass destFC = new IFeatureClassProxy(pFWS
								.openFeatureClass(fileName));
						int count=srcFC.featureCount(null);
						for(int i=0;i<count;i++)
						{
							IFeature destF=destFC.createFeature();
							IFeature srcF=srcFC.getFeature(i);
							destF.setShapeByRef(srcF.getShape());
							
							//fid和shape的field是不可编辑的，所以从第3个field开始赋值
							for(int j=2;j<srcF.getFields().getFieldCount();j++)
							{
								
								destF.setValue(j,srcF.getValue(j));
								
							}
							destF.store();
						}
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}});
		t.start();
		
	}*/
	/*private void dissolveRankMap(IFeatureLayer featureLayer) throws UnknownHostException, IOException
	{
		featureLayer.getFeatureClass();
		String dissolveField="";
		String sumString="";
		BasicGeoprocessor bg=new BasicGeoprocessor();
		bg.dissolve(new ITableProxy(featureLayer.getFeatureClass()),false,dissolveField,sumString,null);
	}*/
	
	public static void FeatureClass2Shapefile(String path,IFeatureClass inFeatureClass) throws UnknownHostException, IOException
	{
		String wkspPath = GT.getFilePath(path);
		String outName = GT.getFileName(path);
		IWorkspaceName shapeWorkName = new WorkspaceName();
	      shapeWorkName.setWorkspaceFactoryProgID("esriDataSourcesFile.ShapefileWorkspaceFactory");
	      shapeWorkName.setPathName(wkspPath);
	      IFeatureClassName shapeFeatClassName = new FeatureClassName();
	      IDatasetName dataSetName = new IDatasetNameProxy(shapeFeatClassName);
	      dataSetName.setName(outName);
	      dataSetName.setWorkspaceNameByRef(shapeWorkName);
	      IFeatureDataConverter featDataConv = new FeatureDataConverter();
	      IFeatureClass f=inFeatureClass;
	      
	      IDataset dataset = new IDatasetProxy(f);
	      IFeatureClassName covFeatClassName;
	      IName name;
	      name = dataset.getFullName();
	      covFeatClassName = new IFeatureClassNameProxy(name);

	      
	     featDataConv.convertFeatureClass(
	          covFeatClassName,
	          null,
	          null,
	          shapeFeatClassName,
	          null,
	          null,
	          null,
	          1000,
	          0
	          );
	     

	}
	public static void FeatureClass2MIF(String path,IFeatureClass inFeatureClass) throws UnknownHostException, IOException
	{
		String temp=path;
		if(temp.toLowerCase().endsWith(".mif"))
		{
			temp=temp.substring(0,temp.length()-4)+"2MIF"+".shp";
		}
		//先将定级单元格分值图保存成ShapeFile文件
		//saveAsShapeFile(temp);
		FeatureClass2Shapefile(temp,inFeatureClass);
		
		//使用工具org2org将shp转成Mif
		Runtime r=Runtime.getRuntime();
		Process p=null;
		String[] cmd=new String[5];
		//convert tools filepath 
		cmd[0]=ConvertEnvironment.getX2x_FilePath();
		cmd[1]="-f";
		cmd[2]="MapInfo File";
		
		//dstination datasource 
		if(!path.toLowerCase().endsWith(".mif"))path+=".mif";
		cmd[3]=path;
		//source datacource
		cmd[4]=temp;
			
		//Excute command 
		p=r.exec(cmd);
		 System.out.println(p.getInputStream().toString());
	     System.out.println(p.getErrorStream().toString());
	}
	public static void FeatureClass2TAB(String path,IFeatureClass inFeatureClass) throws UnknownHostException, IOException
	{
		String temp=path;
		if(temp.toLowerCase().endsWith(".tab"))
		{
			temp=temp.substring(0,temp.length()-4)+"2TAB"+".shp";
		}
		//先将定级单元格分值图保存成ShapeFile文件
		FeatureClass2Shapefile(temp,inFeatureClass);
		
		//使用工具org2org将shp转成Mif
		Runtime r=Runtime.getRuntime();
		Process p=null;
		String[] cmd=new String[5];
		//convert tools filepath 
		cmd[0]=ConvertEnvironment.getX2x_FilePath();
		cmd[1]="-f";
		cmd[2]="MapInfo File";
		
		//dstination datasource 
		if(!path.toLowerCase().endsWith(".tab"))path+=".tab";
		cmd[3]=path;
		//source datacource
		cmd[4]=temp;
			
		//Excute command 
		p=r.exec(cmd);
		 System.out.println(p.getInputStream());
	     System.out.println(p.getErrorStream());
		
		
	}
	public static void saveAs(IGeoDataset data,String path) throws UnknownHostException, IOException {
		String wkspPath = GT.getFilePath(path);
		String outName = GT.getFileName(path);
		IRaster raster=new Raster(data);
		IWorkspaceFactory wkspFactory;
		IWorkspace rasWksp;
		IRasterBandCollection rasBandColl;

		wkspFactory = new RasterWorkspaceFactory();
		rasWksp = new RasterWorkspace(wkspFactory.openFromFile("e:\\", 0));
		rasBandColl = new IRasterBandCollectionProxy(raster);
		// Raster format :"GRID" or "TIFF"
		rasBandColl.saveAs(outName, rasWksp, "GRID");
		
		
		

	}
	
}
