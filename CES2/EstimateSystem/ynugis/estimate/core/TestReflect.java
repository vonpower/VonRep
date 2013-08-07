/*
* @author 冯涛，创建日期：2004-1-8
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
package ynugis.estimate.core;

import java.util.HashMap;

import ynugis.estimate.data.PublicProperty;

public class TestReflect {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	/*	RevisePage revisePage=new RevisePage();
		Class revisePageClass=RevisePage.class;
		Method methods[]=revisePageClass.getMethods();
		String name;
		ArrayList reviseCollection=new ArrayList();
		for(int i=0;i<methods.length;i++)
		{
		name=methods[i].getReturnType().getName();
		
		if(name.equals("ynugis.estimate.data.Revise"))
		{
			try {
				System.out.println(name);
				reviseCollection.add(methods[i].invoke(revisePage,null));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
		Object o;
		for(int i=0;i<reviseCollection.size();i++)
		{	
			o=reviseCollection.get(i);
			System.out.println(((Revise)o).getReviseFieldName());
			
		}
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		int course=0;
		int offset=0;
		String str="[月租金]×12-（[月租金]×12×{管理费率}＋[房屋重置价]×{维修费率}＋{房屋重置价}×[房屋成新度]×{保险费率}＋{房屋重置价}×（1－{房屋残置率}）/{房屋耐用年限}＋[月租金]×12×{交易税费率}＋{房屋重置价}×[房屋成新度]×{房屋还原率}）";
		String ppKey="";
		Object v;
		HashMap map=PublicProperty.getHashMap();
		boolean flag=true;
		while(flag)
		{
			course=str.indexOf("{");
			offset=str.indexOf("}");
			if(course==-1)
			{
				flag=false;
				break;
			}
			ppKey=str.substring(course+1,offset);
			v=map.get(ppKey);
			if(v!=null)
			{
				str=str.replaceAll("\\{"+ppKey+"\\}", ((Double)v).doubleValue()+"");
			}
		}
		System.out.println(str);
	}

}
