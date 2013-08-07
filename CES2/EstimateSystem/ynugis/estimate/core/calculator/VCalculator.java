/*
 * @author 冯涛，创建日期：2006-5-25
 * blog--http://apower.blogone.net
 * QQ:17463776
 * MSN:apower511@msn.com
 * E-Mail:VonPower@Gmail.com
 */
package ynugis.estimate.core.calculator;

import java.io.IOException;
import java.util.HashMap;

import ynugis.estimate.data.PublicProperty;

import com.esri.arcgis.geodatabase.Field;
import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureCursor;
import com.esri.arcgis.geodatabase.IFieldEdit;
import com.esri.arcgis.geodatabase.IFieldEditProxy;
import com.esri.arcgis.geodatabase.IFields;
import com.esri.arcgis.geodatabase.esriFieldType;
import com.esri.arcgis.interop.AutomationException;

public abstract class VCalculator {
	public static final String VFieldName = "V值";

	protected IFeatureClass simplePoint;

	protected HashMap ppMap;
	
	protected HashMap fieldsIdxMap;

	/**
	 * @param inSimplePoint
	 */
	public VCalculator(IFeatureClass inSimplePoint) {
		simplePoint = inSimplePoint;
		ppMap = PublicProperty.getHashMap();
		
		
	}

	/**
	 * 计算V值，并写入FeatureClass
	 * @throws AutomationException
	 * @throws IOException
	 */
	public void V2FeatureClass() throws AutomationException, IOException {
		int vIdx = getVIdx();
		IFeatureCursor cursor = simplePoint.IFeatureClass_update(null, false);
		IFeature feature = cursor.nextFeature();

		double value = 0;
		while (feature != null) {

			
			
			feature.setValue(vIdx, new Double(value));
			cursor.updateFeature(feature);
			feature = cursor.nextFeature();

		}

	}
	
	/**
	 * 计算V值，并写入FeatureClass
	 * template:
	 * 
	 * int vIdx = getVIdx();
		IFeatureCursor cursor = simplePoint.IFeatureClass_update(null, false);
		IFeature feature = cursor.nextFeature();
		//初始化公式变量
		double value = 0;
		while (feature != null) {
			//带入公式计算
			
			feature.setValue(vIdx, new Double(value));
			cursor.updateFeature(feature);
			feature = cursor.nextFeature();

		}
	 * @throws IOException 
	 * @throws AutomationException 
	 */
	abstract public void  execute() throws AutomationException, IOException;
	
	/**
	 * 获取Fields
	 * @param fieldName
	 * @return If field name IsNot Exist,return -1;
	 */
	protected int getFieldsIdx(String fieldName)
	{
		if(fieldsIdxMap==null)
		{
			fieldsIdxMap=new HashMap();
			try {
				IFields fields=simplePoint.getFields();
				for(int i=0;i<fields.getFieldCount();i++)
				{
					fieldsIdxMap.put(fields.getField(i).getName(),new Integer(i));
				}
				
			} catch (AutomationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Object o=fieldsIdxMap.get(fieldName);
		if(o!=null)return Integer.parseInt(o.toString());
		else return -1;
		
	}
	/**
	 * 获取属性表数值
	 * @param ppName
	 * @return
	 * @throws Exception 
	 */
	protected double getPPValue(String ppName){
		
		ppName=ppName.trim();
		if(!ppMap.containsKey(ppName))
		{
			System.out.println(ppName+" 在属性表中不存在！！");
			return 1;
		}
		return Double.parseDouble(ppMap.get(ppName).toString());
	}
	/**
	 * 获取V值字段的Index
	 * @return
	 * @throws AutomationException
	 * @throws IOException
	 */
	protected int getVIdx() throws AutomationException, IOException
	{
		int vIdx = simplePoint.getFields().findField(VFieldName);
		if (vIdx == -1) {
			Field field = new Field();
			IFieldEdit edit = new IFieldEditProxy(field);
			edit.setAliasName(VFieldName);
			edit.setName(VFieldName);
			edit.setType(esriFieldType.esriFieldTypeDouble);
			simplePoint.addField(field);
			vIdx = simplePoint.findField(VFieldName);

		}
		return vIdx;
	}
	

}
