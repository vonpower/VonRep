package ynugis.ui.elementToSHP;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import ynugis.utility.GV;

import com.esri.arcgis.datasourcesfile.ShapefileWorkspaceFactory;
import com.esri.arcgis.geoanalyst.FeatureClassDescriptor;
import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureBuffer;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureCursor;
import com.esri.arcgis.geodatabase.IFeatureWorkspace;
import com.esri.arcgis.geodatabase.IFeatureWorkspaceProxy;
import com.esri.arcgis.geodatabase.IField;
import com.esri.arcgis.geodatabase.IFields;
import com.esri.arcgis.geodatabase.IGeoDataset;
import com.esri.arcgis.geodatabase.IQueryFilter;
import com.esri.arcgis.geodatabase.ISelectionSet;
import com.esri.arcgis.geodatabase.ITable;
import com.esri.arcgis.geodatabase.IWorkspaceEdit;
import com.esri.arcgis.geodatabase.IWorkspaceEditProxy;
import com.esri.arcgis.geodatabase.IWorkspaceFactory;
import com.esri.arcgis.geodatabase.IWorkspaceProxy;
import com.esri.arcgis.geodatabase.QueryFilter;
import com.esri.arcgis.geodatabase.esriFeatureType;
import com.esri.arcgis.geodatabase.esriFieldType;
import com.esri.arcgis.geodatabase.esriSelectionOption;
import com.esri.arcgis.geodatabase.esriSelectionType;
import com.esri.arcgis.geometry.IGeometry;
import com.esri.arcgis.geometry.IPoint;
import com.esri.arcgis.geometry.IPointProxy;
import com.esri.arcgis.geometry.IPolyline;
import com.esri.arcgis.geometry.IPolylineProxy;
import com.esri.arcgis.geometry.esriGeometryType;
import com.esri.arcgis.interop.AutomationException;

public class ObstructCombine {
	private FeatureClassDescriptor fcd;
	private ISelectionSet sset;
	private IFeatureClass fc;
	private IFeatureWorkspace space;
	private IWorkspaceEdit editor;
	private int before;
	private int after;
	private int fromIndex;
	private int toIndex;
	private final double offset=0.1; 
	public ObstructCombine(){
		sset=getSelectionSet();
		fc=getFeatureClass();
		getIGeoDataset();
	}
	public ObstructCombine(FeatureClassDescriptor fcDescriptor,IFeatureClass featureClass){
		try {
			this.fcd = fcDescriptor;
			this.fc = featureClass;
			this.sset = fcDescriptor.getSelectionSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public IGeoDataset getIGeoDataset(){
		
		try {
			
			fcd=this.getFCD(GV.getDefaultTempFileDirectoryPath(),"obstructCombine",fcd.getFeatureClass().getFields(),fcd.getQueryFilter(),fcd.getFieldName());
			sset=fcd.getSelectionSet();
			int fcdType = fcd.getFeatureClass().getShapeType();
			int fcType = fc.getShapeType();
			System.out.println("fcdType:"+fcdType+"  fcType:"+fcType);
			IFeatureBuffer buffer=fcd.getFeatureClass().createFeatureBuffer();
			IFeature feature=fcd.getFeatureClass().getFeature(0);
			IFeature fcFt=null;
			IFeatureCursor cursor=fcd.getFeatureClass().IFeatureClass_insert(true);
			if (fcdType == esriGeometryType.esriGeometryPoint
					&& fcType == esriGeometryType.esriGeometryPolyline) {
				for (int i = 0; i < fc.featureCount(null); i++) {
					fcFt=fc.getFeature(i);
					IPolyline line = new IPolylineProxy(fcFt
							.getShapeCopy());
					IPoint point = line.getFromPoint();
					IPoint p=new IPointProxy(feature.getShapeCopy());
					p.setX(point.getX());
					p.setY(point.getY());
					insertFeature(p,fcFt, cursor, buffer);
				}
			}else if(fcType == esriGeometryType.esriGeometryPoint
					&& fcdType == esriGeometryType.esriGeometryPolyline){
				IPoint newPointF,newPointT;
				for (int i = 0; i < fc.featureCount(null); i++) {
					fcFt=fc.getFeature(i);
					IPolyline line = new IPolylineProxy(feature.getShapeCopy());
					newPointF=new IPointProxy(fcFt.getShapeCopy());
					newPointT=new IPointProxy(fcFt.getShapeCopy());
					newPointT.setX(newPointF.getX()+offset);
					newPointT.setY(newPointF.getY()+offset);
					line.setFromPoint(newPointF);
					line.setToPoint(newPointT);
					insertFeature(line,fcFt, cursor, buffer);
				}
			}else if(fcType==fcdType){
				if(fcType==esriGeometryType.esriGeometryPoint||fcType==esriGeometryType.esriGeometryPolyline){
					for (int i = 0; i < fc.featureCount(null); i++) {
						fcFt=fc.getFeature(i);
						insertFeature(fcFt.getShapeCopy(),fcFt, cursor, buffer);
					}
				}else return null;
			}else return null;
			cursor.flush();
			editor.stopEditOperation();
			editor.stopEditing(true);
			System.out.println("b:"+sset.getCount());
			fromIndex=sset.getCount();
			updateSelectionset();
			System.out.println("a;"+sset.getCount());
			toIndex=sset.getCount();
//			removeFeatures();
			return (IGeoDataset) fcd.getFeatureClass().getFeatureDataset();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void removeFeatures(){
		try {
			if (fromIndex == toIndex)
				return;
			if(fcd.getFeatureClass().featureCount(null)==0)return;
			ITable table=fcd.getFeatureClass().getFeature(0).getTable();
			QueryFilter filter=new QueryFilter();
			filter.addField("FID");
			filter.setWhereClause("FID>="+fromIndex+" AND FID<"+toIndex);
			System.out.println(table.rowCount(filter));
//			table.deleteSearchedRows(filter);
			ShapefileWorkspaceFactory factory=new ShapefileWorkspaceFactory();
			IFeatureWorkspace space=new IFeatureWorkspaceProxy(factory.openFromFile("c:\\",0));
			IWorkspaceEdit editor=new IWorkspaceEditProxy(space);
			editor.startEditing(false);
			editor.startEditOperation();
			IFeatureCursor featureCursor=fcd.getFeatureClass().search(filter,false);
			IFeature f=featureCursor.nextFeature();
			while(f!=null){
				System.out.println(f.getOID());
				f.delete();
				f=featureCursor.nextFeature();
			}
			editor.stopEditOperation();
			editor.stopEditing(true);
			System.out.println(filter.getWhereClause()+" :"+fcd.getFeatureClass().getFeature(0).getTable().rowCount(null));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private ISelectionSet getSelectionSet(){
		try {
			fcd=new FeatureClassDescriptor();
			ShapefileWorkspaceFactory factory = new ShapefileWorkspaceFactory();
			space = new IFeatureWorkspaceProxy(factory
					.openFromFile("E:\\ATest", 0));
			IFeatureClass featureClass=space.openFeatureClass("��ѧ.shp");
			QueryFilter filter=new QueryFilter();
			filter.addField("FID");
			ISelectionSet set=featureClass.select(filter,esriSelectionType.esriSelectionTypeSnapshot,esriSelectionOption.esriSelectionOptionNormal,new IWorkspaceProxy(space));
			fcd.createFromSelectionSet(set,filter,"FID");
			int count=fcd.getSelectionSet().getCount();
			before=count;
			System.out.println(count);
			return fcd.getSelectionSet();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	private IFeatureClass getFeatureClass(){
		try {
			ShapefileWorkspaceFactory factory = new ShapefileWorkspaceFactory();
			IFeatureWorkspace space = new IFeatureWorkspaceProxy(factory
					.openFromFile("E:\\ATest", 0));
			IFeatureClass featureClass = space.openFeatureClass("����վ��.shp");
			return featureClass;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	private void insertFeature(IGeometry geo,IFeature fromFt,IFeatureCursor tocursor,IFeatureBuffer buffer){
		try {
			int geoType=esriFieldType.esriFieldTypeGeometry;
			int oidType=esriFieldType.esriFieldTypeOID;
			int type,index;
			buffer.setShapeByRef(geo);
			IFields toFields=buffer.getFields();
			IFields fromFields=fromFt.getFields();
			IField fromField;
			for(int i=0;i<fromFields.getFieldCount();i++){
				fromField=fromFields.getField(i);
				type=fromField.getType();
				if(type!=geoType&&type!=oidType){
					index=toFields.findField(fromField.getName());
					if(index!=-1){
						buffer.setValue(index,fromFt.getValue(i));
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

	private void updateSelectionset(){
		try {
			before=sset.getCount();
			after=fc.featureCount(null);
			if(after==0)return;
			for (int i = before; i < before+after; i++) {
				sset.add(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private FeatureClassDescriptor getFCD(String folderName,String shapeName,IFields fields,IQueryFilter filter,String fieldName){
        try{
            String strFolder = folderName;
            String strName = shapeName;
            String strShapeFieldName= "Shape";
            strName=getNewShapefileName(strFolder,strName);
            //Open the folder to contain the shapefile as a workspace
            System.out.println("Open " + strFolder +" to contain the "+strName+" as a workspace");
            IFeatureWorkspace pFWS = null;
            IWorkspaceFactory pWorkspaceFactory = null;
            pWorkspaceFactory = new ShapefileWorkspaceFactory();
            pFWS = new IFeatureWorkspaceProxy(pWorkspaceFactory.openFromFile(strFolder, 0));

            
			editor=new IWorkspaceEditProxy(pFWS);
			editor.startEditing(false);
			editor.startEditOperation();
			
            //Create the shapefile (some parameters apply to geodatabase options and can be defaulted as Nothing)
            System.out.println("Create " + strName);
            IFeatureClass pFeatClass = null;
            pFeatClass = pFWS.createFeatureClass(strName,  fields, null, null,esriFeatureType.esriFTSimple, strShapeFieldName, "");
            IFeatureCursor cursor=pFeatClass.IFeatureClass_insert(true);
            int count=fcd.getFeatureClass().featureCount(null);
            IFeatureBuffer buffer=pFeatClass.createFeatureBuffer();
            IFeature feat=null;
            for(int i=0;i<count;i++){
            	feat=fcd.getFeatureClass().getFeature(i);
            	insertFeature(feat.getShapeCopy(),feat,cursor,buffer);
            }
            cursor.flush();
            FeatureClassDescriptor des=new FeatureClassDescriptor();
            ISelectionSet set=pFeatClass.select(filter,esriSelectionType.esriSelectionTypeSnapshot,esriSelectionOption.esriSelectionOptionNormal,new IWorkspaceProxy(pFWS));
			des.createFromSelectionSet(set,filter,fieldName);
			return des;
        }catch (java.lang.Exception e){
        	e.printStackTrace();
            System.out.println("Exception :"+ e.getMessage());
            return null;
        }
    }
	private String getNewShapefileName(String folder,String name){
		String newName=name;
		File file=new File(folder+"/"+newName+".shp");
		System.out.println("new FIle:"+file);
		if(file.exists()){
			int i=1;
			while(file.exists()){
				newName=name+i;
				file=new File(folder+"/"+newName+".shp");
				i++;
			}
		}
		return newName;
	}

}	

