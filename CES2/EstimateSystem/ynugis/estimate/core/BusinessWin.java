/**
 * @author yddy,create date 2005-1-9
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.estimate.core;

import ynugis.estimate.data.PublicProperty;

import com.esri.arcgis.geodatabase.Field;
import com.esri.arcgis.geodatabase.IDataset;
import com.esri.arcgis.geodatabase.IDatasetProxy;
import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureCursor;
import com.esri.arcgis.geodatabase.IFieldEdit;
import com.esri.arcgis.geodatabase.IFieldEditProxy;
import com.esri.arcgis.geodatabase.IWorkspace;
import com.esri.arcgis.geodatabase.IWorkspaceEdit;
import com.esri.arcgis.geodatabase.IWorkspaceEditProxy;
import com.esri.arcgis.geodatabase.QueryFilter;
import com.esri.arcgis.geodatabase.esriFieldType;

public class BusinessWin {
	private final String[] fieldValues=new String[]{"简易","土木","钢混","三等砖木","二等砖木","一等砖木","二等砖混","一等砖混"};
	private final String fldWin="土地纯收益"; 
	private final String fldComp="房屋结构";
	private final String fldRent="月总租金";
	private final String fldAge="房屋成新度";
	private final String fldArea="房屋土地面";
	private IFeatureClass	featureClass;
	private int rentId;
	private int ageId;
	private int winId;
	private int areaId;

	private PublicProperty	publicProperty;

	public BusinessWin(IFeatureClass fcls, PublicProperty pp) {
		this.featureClass=fcls;
		this.publicProperty=pp;
	}

	public void calculateWin() throws Exception {
		rentId=featureClass.findField(fldRent);
//		compId=featureClass.findField(fldComp);
		ageId=featureClass.findField(fldAge);
		areaId=featureClass.findField(fldArea);
		IDataset dataset=new IDatasetProxy(featureClass);
		IWorkspace workspace=dataset.getWorkspace();
		IWorkspaceEdit edit=new IWorkspaceEditProxy(workspace);
		edit.startEditing(false);
		edit.startEditOperation();
		winId=insertField();
		if(winId<-1){return;}
//		this.calcSubgroupWin(null);
		for(int f=0;f<fieldValues.length;f++){
			this.calcSubgroupWin(fieldValues[f]);
		}
		edit.stopEditOperation();
		edit.stopEditing(true);
		System.out.println("business win com");
	}

	private void calcSubgroupWin(String component)throws Exception{
		IFeatureCursor cursor=getCursor(component);
		
		IFeature feature=cursor.nextFeature();
		double i,rlg,e1,e2,e3,e4,t,ihn;
//		String structure=feature.getValue(compId).toString();
		double manage=this.getManageRatio();
		double insure=this.getInsureRatio();
		double tax=this.getHouseTaxRatio();
		double revert=this.getHouseRevertRatio();
		double mainten=this.getMaintenanceRatio();
		double price=this.getResetPrice(component);
		double remain=this.getRemainValueRation(component);
		double limit=this.getTimelimitation(component);
		int count=0;
		while(feature!=null){
			//Rlg
			System.out.println("样点编号："+feature.getValue(2));
			String rlgStr=feature.getValue(rentId).toString();
			double rlgTotal=Double.parseDouble(rlgStr);
			double rlgArea=Double.parseDouble(feature.getValue(areaId).toString());
			rlg=rlgTotal*12/rlgArea;
			//rlg=rlgTotal;
			//E1
			e1=rlg*manage;
			//E2
			e2=price*mainten;
			//E3
			String degree;
			String oldDegreeStr=feature.getValue(ageId).toString();
			int degreeStart=oldDegreeStr.lastIndexOf(".");
			if(degreeStart==-1){
				degree=oldDegreeStr;
			}else{
				degree=oldDegreeStr.substring(degreeStart,oldDegreeStr.length());
			}
			double oldDegree=Double.parseDouble(degree);
			oldDegree/=10;
			double currentValue=price*oldDegree;
			e3=currentValue*insure;
			//E4
			e4=price*(1-remain)/limit;
			//T
			t=rlg*tax;
			//Ihn
			ihn=currentValue*revert;
			//I
			i=rlg-e1-e2-e3-e4-t-ihn;
			System.out.println("Vonpower :"+i+"  count:"+(count++));
//			System.out.println("winId:"+winId+" I:"+i);
			feature.setValue(winId,new Double(i));
			feature.store();
//			cursor.updateFeature(feature);
			feature=cursor.nextFeature();
		}
	}
	private int insertField() throws Exception {
		int fldCompId=featureClass.findField(fldComp);
		int fldRentId=featureClass.findField(fldRent);
		int fldAgeId=featureClass.findField(fldAge);
		//Modify by vonPower
		int fldAreaId=featureClass.findField(fldArea);
		// (||fldAreaId==-1)
		
		if(fldCompId==-1||fldRentId==-1||fldAgeId==-1||fldAreaId==-1){
			return -2;
		}
		if(featureClass.findField(fldWin)!=-1){
			return featureClass.findField(fldWin);
		}
		Field field = new Field();
		IFieldEdit edit=new IFieldEditProxy(field);
		edit.setAliasName(fldWin);
		edit.setName(fldWin);
		edit.setType(esriFieldType.esriFieldTypeDouble);
		featureClass.addField(field);
		return featureClass.findField(fldWin);
	}
	private IFeatureCursor getCursor(String fieldvalue)throws Exception{
		String where=fldComp+"<>\'\'";
		if(fieldvalue!=null){
			where=fldComp+"=\'"+fieldvalue+"\'";
		}
//		System.out.println(where);
		QueryFilter filter=new QueryFilter();
//		filter.addField(fldComp);
		filter.setWhereClause(where);
		IFeatureCursor cursor=featureClass.IFeatureClass_update(filter,true);
		return cursor;
	}
	
	private double getManageRatio(){
		return publicProperty.GLFL.getdata();
	}
	
	private double getMaintenanceRatio(){
		return publicProperty.WXFL.getdata();
	}
	
	private double getInsureRatio(){
		return publicProperty.BXFM.getdata();
	}
	
	private double getHouseTaxRatio(){
		return publicProperty.FCSF.getdata();
	}
	
	private double getHouseRevertRatio(){
		return publicProperty.FWHDL.getdata();
	}
	
	private double getResetPrice(String component){
		//modify by VonPower
		if(component.equals(fieldValues[0])||component.equals("简易结构")){
			return publicProperty.JYJGCZJ.getdata();
		}
		if(component.equals(fieldValues[1])){
			return publicProperty.TMJGCZJ.getdata();
		}
		if(component.equals(fieldValues[2])){
			return publicProperty.GHJGCZJ.getdata();
		}
		if(component.equals(fieldValues[3])){
			return publicProperty.SDZMJGCZJ.getdata();
		}
		if(component.equals(fieldValues[4])){
			return publicProperty.EDZMJGCZJ.getdata();
		}
		//只写“砖木”的，就算作“一等砖木”
		if(component.equals(fieldValues[5])||component.equals("砖木")){
			return publicProperty.YDZMJGCZJ.getdata();
		}
		if(component.equals(fieldValues[6])){
			return publicProperty.EDZHJGCZJ.getdata();
		}
		if(component.equals(fieldValues[7])){
			return publicProperty.YDZHJGCZJ.getdata();
		}
		
		return -1;
	}
	
	private double getRemainValueRation(String component){
		if(component.equals(fieldValues[0])){
			return publicProperty.JYJGCZL.getdata();
		}
		if(component.equals(fieldValues[1])){
			return publicProperty.TMJGCZL.getdata();
		}
		if(component.equals(fieldValues[2])){
			return publicProperty.GHJGCZL.getdata();
		}
		if(component.equals(fieldValues[3])){
			return publicProperty.SDZMJGCZL.getdata();
		}
		if(component.equals(fieldValues[4])){
			return publicProperty.EDZMJGCZL.getdata();
		}
		if(component.equals(fieldValues[5])||component.equals("砖木")){
			return publicProperty.YDZMJGCZL.getdata();
		}
		if(component.equals(fieldValues[6])){
			return publicProperty.EDZHJGCZL.getdata();
		}
		if(component.equals(fieldValues[7])){
			return publicProperty.YDZHJGCZL.getdata();
		}
		
		return -1;
	}
	
	private double getTimelimitation(String component){
		if(component.equals(fieldValues[0])){
			return publicProperty.JYJGNYNX.getdata();
		}
		if(component.equals(fieldValues[1])){
			return publicProperty.TMJGNYNX.getdata();
		}
		if(component.equals(fieldValues[2])){
			return publicProperty.GHJGNYNX.getdata();
		}
		if(component.equals(fieldValues[3])){
			return publicProperty.SDZMJGNYNX.getdata();
		}
		if(component.equals(fieldValues[4])){
			return publicProperty.EDZMJGNYNX.getdata();
		}
		if(component.equals(fieldValues[5])||component.equals("砖木")){
			return publicProperty.YDZMJGNYNX.getdata();
		}
		if(component.equals(fieldValues[6])){
			return publicProperty.EDZHJGNYNX.getdata();
		}
		if(component.equals(fieldValues[7])){
			return publicProperty.YDZHJGNYNX.getdata();
		}
		
		return -1;
	}
}
