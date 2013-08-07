/*
 * @author 冯涛，创建日期：2003-9-19
 * blog--http://apower.blogone.net
 * QQ:17463776
 * MSN:apower511@msn.com
 * E-Mail:VonPower@Gmail.com
 */
/**
 * 
 */
package ynugis.geo;

import java.io.IOException;
import java.net.UnknownHostException;

import ynugis.utility.GT;

import com.esri.arcgis.datasourcesfile.ShapefileWorkspaceFactory;
import com.esri.arcgis.geoanalyst.IRasterAnalysisEnvironment;
import com.esri.arcgis.geoanalyst.IRasterAnalysisEnvironmentProxy;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureWorkspace;
import com.esri.arcgis.geodatabase.IFeatureWorkspaceProxy;
import com.esri.arcgis.geodatabase.IGeoDataset;
import com.esri.arcgis.geodatabase.IGeoDatasetProxy;
import com.esri.arcgis.geodatabase.IWorkspace;
import com.esri.arcgis.geodatabase.IWorkspaceFactory;
import com.esri.arcgis.spatialanalyst.RasterDistanceOp;


/**
 * @author VonPower
 * 
 */
public class ShpDistanceOp {

	private RasterDistanceOp disOp = null;

	

	private IRasterAnalysisEnvironment rasEnv = null;

	public ShpDistanceOp(IRasterAnalysisEnvironment env) throws Exception {
		
		setRasEnv(env);
		
	}

	

	/**
	 * 打开指定(fullShpPath)的featureClass，以Geodataset的形式返回
	 * @param fullShpPath
	 * @return
	 * @throws UnknownHostException
	 * @throws Exception
	 */
	private IGeoDataset initialGeoDataset(String fullShpPath)
			throws UnknownHostException, Exception {
		// Get the directory from the inShapefilePath
		String pathToWorkspace;

		pathToWorkspace = GT.getFilePath(fullShpPath);

		// Get the shapefile name from the inShapefilePath
		String shapefileName;

		shapefileName = GT.getFileNameWithoutExtension(fullShpPath);

		// Open the shapefile's workspace
		IWorkspaceFactory shapeWorkspaceFactory = new ShapefileWorkspaceFactory();
		IWorkspace shapeWorkspace;

		shapeWorkspace = shapeWorkspaceFactory.openFromFile(pathToWorkspace, 0);

		// Failure here causes a run-time exception, but if IFeatureWorkspace
		// isn't
		// supported by a Shapefile Workspace, there are lots more bad problems
		// happening than this one.
		IFeatureWorkspace featureWorkspace = new IFeatureWorkspaceProxy(
				shapeWorkspace);

		IFeatureClass featureClass;

		featureClass = featureWorkspace.openFeatureClass(shapefileName);

		// Get the interface required for the operation. Again, this cast should
		// never fail.
		IGeoDataset geoDataset = new IGeoDatasetProxy(featureClass);
		
		return geoDataset;

	}

	/**
	 * @param center
	 * @param maxDistance
	 * @param cellSize
	 * @param output
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public IGeoDataset doStraightLine(IGeoDataset center, double maxDistance) throws Exception, IOException {

		// Create the workspace ,specify output GRID's filepath
		//IWorkspaceFactory wkspfactory = new RasterWorkspaceFactory();
		//IWorkspace wksp = new IWorkspaceProxy(wkspfactory.openFromFile(output,
		//		0));
		
		//Set AnalysisEnvironment 
		//getRasEnv().setOutWorkspaceByRef(wksp);
		//getRasEnv().setExtent(com.esri.arcgis.geoanalyst.esriRasterEnvSettingEnum.esriRasterEnvMaxOf,center.getExtent(),null);
		
		
		
		// Set GRID's CellSize
		//getDisOp().setCellSize(esriRasterEnvSettingEnum.esriRasterEnvValue,
		//		new Double(cellSize));
		
		
		
		IGeoDataset distanceResult = getDisOp().eucDistance(center,
				new Double(maxDistance), null);
  
		return distanceResult;

	}
	/*
	*//**
	 * @param center
	 * @param maxDistance
	 * @param cellSize
	 * @param output
	 * @return
	 * @throws IOException 
	 * @throws AutomationException 
	 * @throws Exception
	 * @throws IOException
	 *//*
	public IGeoDataset doStraightLine(IGeoDataset center, double maxDistance,
			double cellSize, String output,IEnvelope extent) throws AutomationException, IOException {

		// Create the workspace ,specify output GRID's filepath
		IWorkspaceFactory wkspfactory = new RasterWorkspaceFactory();
		IWorkspace wksp = new IWorkspaceProxy(wkspfactory.openFromFile(output,
				0));
		
		//Set AnalysisEnvironment 
		getRasEnv().setOutWorkspaceByRef(wksp);
		getRasEnv().setExtent(com.esri.arcgis.geoanalyst.esriRasterEnvSettingEnum.esriRasterEnvValue,extent,null);
		//getRasEnv().setOutSpatialReferenceByRef(isr);
		
		
		// Set GRID's CellSize
		getDisOp().setCellSize(esriRasterEnvSettingEnum.esriRasterEnvValue,
				new Double(cellSize));

		IGeoDataset distanceResult = getDisOp().eucDistance(center,
				new Double(maxDistance), null);

		return distanceResult;

	}
	*/
	/**
	 * @param shpFilePath
	 * @param maxDistance
	 * @param cellSize
	 * @param output
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public IGeoDataset doStraightLine(String shpFilePath, double maxDistance
			) throws Exception, IOException {

		//Create GeoDataSet from input ShapeFile
		IGeoDataset center=initialGeoDataset(shpFilePath);
		
		// Create the workspace ,specify output GRID's filepath
		/*IWorkspaceFactory wkspfactory = new RasterWorkspaceFactory();
		IWorkspace wksp = new IWorkspaceProxy(wkspfactory.openFromFile(output,
				0));
		getRasEnv().setOutWorkspaceByRef(wksp);
		
		// Set GRID's CellSize
		getDisOp().setCellSize(esriRasterEnvSettingEnum.esriRasterEnvValue,
				new Double(cellSize));*/

		
		return doStraightLine(center,maxDistance);

	}

	private RasterDistanceOp getDisOp() {
		if (disOp == null) {
			try {
				disOp = new RasterDistanceOp();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return disOp;
	}

	private void setDisOp(RasterDistanceOp disOp) {
		this.disOp = disOp;
	}

	public IRasterAnalysisEnvironment getRasEnv() {
		if (rasEnv == null) {
			try {
				rasEnv = new IRasterAnalysisEnvironmentProxy(getDisOp());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return rasEnv;
	}

	public void setRasEnv(IRasterAnalysisEnvironment inRasEnv) throws Exception, Exception {
		Utility.RasterAnalysisEnvCopy(inRasEnv,getRasEnv());
	}

	
}
