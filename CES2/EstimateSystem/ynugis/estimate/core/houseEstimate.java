package ynugis.estimate.core;

import java.io.IOException;

import ynugis.estimate.data.PublicProperty;

import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureCursor;

public class houseEstimate {
	private String structure;
	double Vhg;
	double Vhc;
	double Newdegree;
	double s;
	double reprice;

	public houseEstimate() {
		super();}
	public void count(IFeatureClass fc,PublicProperty value){
		try {
			IFeatureClass a=fc;
			IFeatureCursor b=a.search(null,false);
			IFeature c=b.nextFeature();	
			while(c!=null){
				double Vhg=Double.parseDouble(c.getValue(a.findField("交易总价") ).toString());
				double s=Double.parseDouble(c.getValue(a.findField("房屋建筑") ).toString());
				double Newdegree=Double.parseDouble(c.getValue(a.findField("房屋成新度") ).toString());
				structure=String.valueOf(c.getValue(a.findField("房屋结构")));
				if(structure=="一等砖混"){
					double rePrice=Double.parseDouble(value.YDZHJGCZJ.toString());
					
				}
				if(structure=="二等转混"){
					double rePrice=Double.parseDouble(value.EDZMJGCZJ.toString());
				}
				if(structure=="一等砖木"){
					double rePrice=Double.parseDouble(value.YDZMJGCZJ.toString());
				}
				if(structure=="二等砖木"){
					double rePrice=Double.parseDouble(value.EDZMJGCZJ.toString());
				}
				if(structure=="三等砖木"){
					double rePrice=Double.parseDouble(value.SDZMJGCZJ.toString());
				}
				if(structure=="土木"){
					double rePrice=Double.parseDouble(value.TMJGCZJ.toString());
				}
				if(structure=="简易结构"){
					double rePrice=Double.parseDouble(value.JYJGCZJ.toString());
				}
			    Vhc= Newdegree*reprice;
				double interest=Double.parseDouble(value.JYSFL.toString());
				double T=Vhg*interest;
				double Vls=(Vhg-Vhc-T)/s;
				
				c.setValue(a.findField("样点土地价"),new Double(Vls));
				c=b.nextFeature();	
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	}
		


