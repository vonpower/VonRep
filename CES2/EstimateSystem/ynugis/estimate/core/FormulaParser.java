package ynugis.estimate.core;

import java.io.IOException;
import java.util.HashMap;

import ynugis.estimate.data.PublicProperty;

import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.interop.AutomationException;

public class FormulaParser {

	private String fieldSymbolS = "[";

	private String fieldSymbolE = "]";

	private String ppSymbolS = "{";

	private String ppSymbolE = "}";

	private String formula;

	private IFeatureClass featureClass;
	
	//enum DataSrc {FeatureClassField,PublicProperty}

	public FormulaParser(String inFormula, IFeatureClass inFC) {
		formula = inFormula;
		featureClass = inFC;

	}
	public String trimPublicProperty(String inStr)
	{	
		int course=0;
		int offset=0;
		String str=inStr;
		String ppKey="";
		Object v;
		HashMap map=PublicProperty.getHashMap();
		boolean flag=true;
		while(flag)
		{
			course=str.indexOf(ppSymbolS);
			offset=str.indexOf(ppSymbolE);
			if(course==-1)break;
			ppKey=str.substring(course+1,offset);
			v=map.get(ppKey);
			if(v!=null)
			{
				str=str.replaceAll("\\"+ppSymbolS+ppKey+"\\"+ppSymbolE, ((Double)v).doubleValue()+"");
			}
			
			
		}
		
		
		return str;
	}
	public String trimField(String inStr,IFeature feature) throws AutomationException, IOException
	{	
		int course=0;
		int offset=0;
		String str=inStr;
		String filed="";
		Object v;
		boolean flag=true;
		while(flag)
		{
			course=str.indexOf(fieldSymbolS);
			offset=str.indexOf(fieldSymbolE);
			if(course==-1)break;
			filed=str.substring(course+1,offset);
			v=feature.getValue(feature.getFields().findField(filed));
			if(v!=null)
			{
				str=str.replaceAll("\\"+fieldSymbolS+filed+"\\"+fieldSymbolE, v.toString());
			}
			
			
		}
		
		
		return str;
	}
	/*
	public double getRetureValue() {
		String pureDigital="";
		double ret = 0;
		Stack<String> stack=new Stack<String>();
		int i=0;
		boolean record=false;
		String temp="";
		DataSrc dataSrc = null;
		while(i<formula.length())
		{
			if(formula.charAt(i)==fieldSymbolE)
			{
				String[] str=(String[]) stack.toArray();
				String token="";
				for(int m=0;m<str.length;m++)
				{
					token+=str[m];
				}
				pureDigital+=getFieldValue(String field);
				
				record=false;
			}else if(formula.charAt(i)==ppSymbolE)
			{
				String[] str=(String[]) stack.toArray();
				String token="";
				for(int m=0;m<str.length;m++)
				{
					token+=str[m];
				}
			}
			
			else if(formula.charAt(i)==fieldSymbolS)
			{
				record=true;
				dataSrc=DataSrc.FeatureClassField;
				
			}else if(formula.charAt(i)==ppSymbolS)
			{
				record=true;
				dataSrc=DataSrc.PublicProperty;
			}else 
			{
				pureDigital+=String.valueOf(formula.charAt(i));
			}
		
			if(record==true)
			{
				stack.push(String.valueOf(formula.charAt(i)));
			}
			
			i++;
		}

		return ret;
	}*/
}
