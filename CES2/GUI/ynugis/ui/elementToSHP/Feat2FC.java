package ynugis.ui.elementToSHP;

import java.io.File;
import java.io.IOException;

import ynugis.utility.GV;

import com.esri.arcgis.datasourcesfile.ShapefileWorkspaceFactory;
import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureBuffer;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureCursor;
import com.esri.arcgis.geodatabase.IFeatureWorkspace;
import com.esri.arcgis.geodatabase.IFeatureWorkspaceProxy;
import com.esri.arcgis.geodatabase.IField;
import com.esri.arcgis.geodatabase.IFields;
import com.esri.arcgis.geodatabase.IWorkspaceFactory;
import com.esri.arcgis.geodatabase.esriFeatureType;
import com.esri.arcgis.geodatabase.esriFieldType;
import com.esri.arcgis.interop.AutomationException;

public class Feat2FC {
	private IFeatureClass featureClass;

	public Feat2FC(IFeatureClass fcls) {
		this.featureClass = fcls;
	}

	public IFeatureClass[] getFeatureClasses() {
		try {
			String folder = GV.getDefaultTempFileDirectoryPath();
			String fName = "feat2fc";
			int fCount = featureClass.featureCount(null);
			IFeatureCursor cursor = null;
			IFeatureBuffer buffer = null;
			IFeature feat = null;
			IFeatureClass[] fclses = new IFeatureClass[fCount];
			for (int i = 0; i < fCount; i++) {
				feat = featureClass.getFeature(i);
				fclses[i] = this.getFC(folder, fName, this.featureClass);
				cursor = fclses[i].IFeatureClass_insert(true);
				buffer = fclses[i].createFeatureBuffer();
				insertFeature(feat, cursor, buffer);
				cursor.flush();
			}
			return fclses;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private IFeatureClass getFC(String folderName, String shapeName,
			IFeatureClass fcls) {
		try {
			String strFolder = folderName;
			String strName = shapeName;
			String strShapeFieldName = "Shape";
			strName = getNewShapefileName(strFolder, strName);
			// Open the folder to contain the shapefile as a workspace
			System.out.println("Open " + strFolder + " to contain the "
					+ strName + " as a workspace");
			IFeatureWorkspace pFWS = null;
			IWorkspaceFactory pWorkspaceFactory = null;
			pWorkspaceFactory = new ShapefileWorkspaceFactory();
			pFWS = new IFeatureWorkspaceProxy(pWorkspaceFactory.openFromFile(
					strFolder, 0));

			// Create the shapefile (some parameters apply to geodatabase
			// options and can be defaulted as Nothing)
			System.out.println("Create " + strName);
			IFeatureClass pFeatClass = null;
			pFeatClass = pFWS.createFeatureClass(strName, fcls.getFields(),
					null, null, esriFeatureType.esriFTSimple,
					strShapeFieldName, "");

			return pFeatClass;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			System.out.println("Exception :" + e.getMessage());
			return null;
		}
	}

	private String getNewShapefileName(String folder, String name) {
		String newName = name;
		File file = new File(folder + "/" + newName + ".shp");
		System.out.println("new FIle:" + file);
		if (file.exists()) {
			int i = 1;
			while (file.exists()) {
				newName = name + i;
				file = new File(folder + "/" + newName + ".shp");
				i++;
			}
		}
		return newName;
	}

	private void insertFeature(IFeature fromFt, IFeatureCursor tocursor,
			IFeatureBuffer buffer) {
		try {
			int geoType = esriFieldType.esriFieldTypeGeometry;
			int oidType = esriFieldType.esriFieldTypeOID;
			int type, index;
			buffer.setShapeByRef(fromFt.getShapeCopy());
			IFields toFields = buffer.getFields();
			IFields fromFields = fromFt.getFields();
			IField fromField;
			for (int i = 0; i < fromFields.getFieldCount(); i++) {
				fromField = fromFields.getField(i);
				type = fromField.getType();
				if (type != geoType && type != oidType) {
					index = toFields.findField(fromField.getName());
					if (index != -1) {
						buffer.setValue(index, fromFt.getValue(i));
					}
				}
			}
			tocursor.insertFeature(buffer);
		} catch (AutomationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
