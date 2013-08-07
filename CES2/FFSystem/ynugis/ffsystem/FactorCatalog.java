package ynugis.ffsystem;
/*
* @author 冯涛，创建日期：2003-11-25
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/

import com.FFT.FFTDoc;
import com.FFT.FactorCatalogType;

public class FactorCatalog extends Factor {

	public FactorCatalog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FactorCatalog(FactorCatalogType fct) throws Exception {
		super();
		name=fct.getName().getValue();
		weight=fct.getWeight().getValue();
		
		//加入基本因素
		int count=fct.getBasicFactorCount();
		if(count>0)
		{
			for(int i=0;i<count;i++)
			{
				addBaseFactor(new BasicFactor(fct.getBasicFactorAt(i)));
			}
			
		}
		
	}

	public BasicFactor getBasicFactor(int idx) {
		return (BasicFactor) childCollection.get(idx);
	}

	public void addBaseFactor(BasicFactor bf) {
		childCollection.add(bf);
	}
	public FactorCatalogType getAsFactorCatalogType() throws Exception
	{
		FFTDoc doc = new FFTDoc();
		FactorCatalogType fct=new FactorCatalogType(doc,"http://my.opera.com/VonPower/","", "FactorCatalog");
		fct.addName(name);
		fct.addWeight(Double.toString(weight));
		
		
		int count=childCollection.size();
		while(count>0)
		{
			
			fct.addBasicFactor(((BasicFactor)childCollection.get(count-1)).getAsBasicFactorType());
			count--;
		}
		return fct;
		
		
	}

	public boolean canCalculate() {
		int num=childCollection.size();
		
		
		for(int i=0;i<num;i++)
		{
			boolean b=((BasicFactor)childCollection.get(i)).canCalculate();
			if(!b)return false; 	
		}
		return true;
	}

}
