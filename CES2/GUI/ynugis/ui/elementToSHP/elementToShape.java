package ynugis.ui.elementToSHP;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import ynugis.application.CESCORE;
import ynugis.classifyBusiness.classifyItem.RangeCMap;
import ynugis.geo.Utility;
import ynugis.utility.GT;
import ynugis.utility.GV;

import com.esri.arcgis.carto.IElement;
import com.esri.arcgis.carto.IGraphicsContainer;
import com.esri.arcgis.datasourcesfile.ShapefileWorkspaceFactory;
import com.esri.arcgis.geodatabase.Field;
import com.esri.arcgis.geodatabase.Fields;
import com.esri.arcgis.geodatabase.GeometryDef;
import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureClassProxy;
import com.esri.arcgis.geodatabase.IFeatureWorkspace;
import com.esri.arcgis.geodatabase.IFeatureWorkspaceProxy;
import com.esri.arcgis.geodatabase.IField;
import com.esri.arcgis.geodatabase.IFieldEdit;
import com.esri.arcgis.geodatabase.IFields;
import com.esri.arcgis.geodatabase.IFieldsEdit;
import com.esri.arcgis.geodatabase.IGeometryDef;
import com.esri.arcgis.geodatabase.IGeometryDefEdit;
import com.esri.arcgis.geodatabase.IWorkspaceFactory;
import com.esri.arcgis.geodatabase.esriFeatureType;
import com.esri.arcgis.geodatabase.esriFieldType;
import com.esri.arcgis.geometry.IGeometry;
import com.esri.arcgis.geometry.IGeometryProxy;
import com.esri.arcgis.geometry.IPolygon;
import com.esri.arcgis.geometry.IPolygonProxy;
import com.esri.arcgis.geometry.UnknownCoordinateSystem;
import com.esri.arcgis.geometry.esriGeometryType;

public class elementToShape {
	private Shell shell;

	private CESCORE cescore;

	private String filePath;

	private String fileName;

	private IPolygon pPolygon;

	private IFeatureClass featureClass;
	private File f;
	private File n;

	public elementToShape(Shell myshell, CESCORE core) {
		shell = myshell;
		cescore = core;
		filePath = GV.getDefaultTempFileDirectoryPath();
		fileName = "定级范围.shp";
	}


	public RangeCMap TransIelementToGeodataSet() throws IOException {
		// String filePath="d:\\";
		// EngineInitializer.initializeEngine();
		// initializeArcObjects();
		// String fileName="定级范围.shp";
		FileDialog saveDialog = new FileDialog(shell, SWT.SAVE);
		saveDialog.setFilterExtensions(new String[] { "*.SHP" });
		saveDialog.setFileName(fileName);
		saveDialog.setText("保存定级范围");
		String dir = saveDialog.open();
		if (dir != null) {
//			File file = new File(dir);
//			filePath = file.getAbsolutePath();
//			fileName = file.getName();
			fileName=GT.getFileName(dir);
			filePath=GT.getFilePath(dir);
			f=new File(filePath + File.separator + fileName);
			n=new File(filePath);
		} else {
			// this.create(filePath,fileName);
			 f = new File(filePath + File.separator + fileName);
			 n = new File(filePath);

//			if (!n.exists()) {
//				MessageDialog.openInformation(null, "文件错误", "文件路径不存在！");
//				return null;
//			}
//			if (f.exists())
//				Utility.deleteShp(filePath + File.separator + fileName);	
		}
		if (!n.exists()) {
			MessageDialog.openInformation(null, "文件错误", "文件路径不存在！");
			return null;
		}
		if (f.exists())
			Utility.deleteShp(filePath + File.separator + fileName);
//			System.out.println("delete shape:"+f.delete());
		IGraphicsContainer pGraphicsContainer = cescore.getMapControl()
				.getActiveView().getGraphicsContainer();
		pGraphicsContainer.reset();
		IElement pElement = pGraphicsContainer.next();
		if (pElement == null) {
			MessageDialog.openInformation(null, "文件错误", "定级范围不存在");
			return null;
		}
		this.create(filePath, fileName);
		IWorkspaceFactory shapeFileFactory = new ShapefileWorkspaceFactory();
		IFeatureWorkspace featureWksp = new IFeatureWorkspaceProxy(
				shapeFileFactory.openFromFile(filePath, 0));
		featureClass = new IFeatureClassProxy(featureWksp
				.openFeatureClass(fileName));

		while (pElement != null) {
			int i = pElement.getGeometry().getGeometryType();
			if (i == esriGeometryType.esriGeometryPolygon) {
				pPolygon = new IPolygonProxy(pElement.getGeometry());
				IFeature pFeature = featureClass.createFeature();
				IGeometry ipGeom = new IGeometryProxy(pPolygon);
				pFeature.setShapeByRef(ipGeom);
				pFeature.store();
				pElement = pGraphicsContainer.next();
			}
		}
		// FileDialog saveDialog=new FileDialog(shell,SWT.SAVE);
		// saveDialog.setFilterExtensions(new String[]{"*.SHP"});
		// saveDialog.setFileName(fileName);
		// saveDialog.setText("保存定级范围");
		// String dir=saveDialog.open();
		// if(dir!=null){
		// saveFile(dir);
		// }
		RangeCMap rangeCMap = new RangeCMap(filePath + File.separator + fileName);
		/*
		 * if(dir!=null){ try { rangeCMap.saveAs(dir); } catch (Exception e) {
		 * MessageDialog.openError(shell,"保存文件","请尝试改名保存或稍后再保存!"); } }
		 */
		return rangeCMap;
	}

	private void create(String folderName, String shapeName) {
		try {
			String strFolder = folderName;
			String strName = shapeName;
			String strShapeFieldName = "Shape";

			// Open the folder to contain the shapefile as a workspace
			IFeatureWorkspace pFWS = null;
			IWorkspaceFactory pWorkspaceFactory = null;
			pWorkspaceFactory = new ShapefileWorkspaceFactory();
			pFWS = new IFeatureWorkspaceProxy(pWorkspaceFactory.openFromFile(
					strFolder, 0));

			// Set up a simple fields collection
			IFields pFields = null;
			IFieldsEdit pFieldsEdit = null;
			pFields = new Fields();
			pFieldsEdit = (IFieldsEdit) pFields;

			IField pField = null;
			IFieldEdit pFieldEdit = null;

			// Make the shape field it will need a geometry definition, with a
			// spatial reference
			pField = new Field();
			pFieldEdit = (IFieldEdit) pField;
			pFieldEdit.setName(strShapeFieldName);
			pFieldEdit.setType(esriFieldType.esriFieldTypeGeometry);

			IGeometryDef pGeomDef = null;
			IGeometryDefEdit pGeomDefEdit = null;
			pGeomDef = new GeometryDef();
			pGeomDefEdit = (IGeometryDefEdit) pGeomDef;
			pGeomDefEdit.setGeometryType(esriGeometryType.esriGeometryPolygon);
			pGeomDefEdit
					.setSpatialReferenceByRef(new UnknownCoordinateSystem());

			pFieldEdit.setGeometryDefByRef(pGeomDef);
			pFieldsEdit.addField(pField);

			// Add another miscellaneous text field
			pField = new Field();
			pFieldEdit = (IFieldEdit) pField;
			pFieldEdit.setLength(30);
			pFieldEdit.setName("TextField");
			pFieldEdit.setType(esriFieldType.esriFieldTypeString);
			pFieldsEdit.addField(pField);

			// Create the shapefile (some parameters apply to geodatabase
			// options and can be defaulted as Nothing)
			pFWS.createFeatureClass(strName, pFields, null, null,
					esriFeatureType.esriFTSimple, strShapeFieldName, "");
		} catch (java.lang.Exception e) {
			System.out.println("Exception :" + e.getMessage());
		}
	}

	private void saveFile(String path) {
		String filePath = GT.getFilePath(path);
		String fileName = GT.getFileName(path);
		// EngineInitializer.initializeEngine();
		// initializeArcObjects();
		create(filePath, fileName);
		IWorkspaceFactory shapeFileFactory;
		try {
			shapeFileFactory = new ShapefileWorkspaceFactory();
			IFeatureWorkspace featureWksp = new IFeatureWorkspaceProxy(
					shapeFileFactory.openFromFile(filePath, 0));
			featureClass = new IFeatureClassProxy(featureWksp
					.openFeatureClass(fileName));
			IGraphicsContainer pGraphicsContainer = cescore.getMapControl()
					.getActiveView().getGraphicsContainer();
			pGraphicsContainer.reset();
			IElement pElement = pGraphicsContainer.next();
			while (pElement != null) {
				int i = pElement.getGeometry().getGeometryType();
				if (i == esriGeometryType.esriGeometryPolygon) {
					pPolygon = new IPolygonProxy(pElement.getGeometry());
					IFeature pFeature = featureClass.createFeature();
					IGeometry ipGeom = new IGeometryProxy(pPolygon);
					pFeature.setShapeByRef(ipGeom);
					pFeature.store();
					pElement = pGraphicsContainer.next();
				}
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	// private static void initializeArcObjects(){
	// try {
	// aoInit = new AoInitialize();
	//
	// if( aoInit.isProductCodeAvailable(
	// esriLicenseProductCode.esriLicenseProductCodeEngine ) ==
	// esriLicenseStatus.esriLicenseAvailable ){
	// aoInit.initialize( esriLicenseProductCode.esriLicenseProductCodeEngine );
	// }else if( aoInit.isProductCodeAvailable(
	// esriLicenseProductCode.esriLicenseProductCodeArcView ) ==
	// esriLicenseStatus.esriLicenseAvailable ){
	// aoInit.initialize( esriLicenseProductCode.esriLicenseProductCodeArcView
	// );
	// }else if( aoInit.isProductCodeAvailable(
	// esriLicenseProductCode.esriLicenseProductCodeArcEditor ) ==
	// esriLicenseStatus.esriLicenseAvailable ){
	// aoInit.initialize( esriLicenseProductCode.esriLicenseProductCodeArcEditor
	// );
	// }else if( aoInit.isProductCodeAvailable(
	// esriLicenseProductCode.esriLicenseProductCodeArcInfo ) ==
	// esriLicenseStatus.esriLicenseAvailable ){
	// aoInit.initialize( esriLicenseProductCode.esriLicenseProductCodeArcInfo
	// );
	// }else{
	// System.exit(0);
	// }
	// } catch ( Exception e ) {
	// System.out.println(" Program Exit: Unable initialize ArcObjects ");
	// System.exit(0);
	// } // end try - catch
	// } // end initializeArcObjects

	// private static AoInitialize aoInit;

}