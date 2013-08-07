/*
* @author 冯涛，创建日期：2003-11-25
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
package ynugis.ffsystem;


import com.FFT.BasicFactorType;
import com.FFT.FFTDoc;

public class BasicFactor extends Factor
{
	
	public BasicFactor(BasicFactorType bft) throws Exception
	{
		super();
		name=bft.getName().getValue();
		weight=bft.getWeight().getValue();
		

		//加入基本因子
		int count=bft.getBasicFacetCount();
		if(count>0)
		{
			for(int i=0;i<count;i++)
			{
				addBasicFacet(new BasicFacet(bft.getBasicFacetAt(i)));
			}
			
		}
		
		
	}
	public BasicFacet getBaseFacets(int idx)
	{
		return (BasicFacet)childCollection.get(idx);
	}
	
	public void addBasicFacet(BasicFacet bf )
	{
		childCollection.add(bf);
	}
	public BasicFactor() {
		super();
		// TODO Auto-generated constructor stub
	}
		
	public BasicFactorType getAsBasicFactorType() throws Exception
	{
		FFTDoc doc = new FFTDoc();
		BasicFactorType bft=new BasicFactorType(doc,"http://my.opera.com/VonPower/","", "BasicFactor");
		bft.addName(name);
		bft.addWeight(Double.toString(weight));
		
		
		int count=childCollection.size();
		while(count>0)
		{
			
			bft.addBasicFacet(((BasicFacet)childCollection.get(count-1)).getAsBasicFacetType());
			count--;
		}
		return bft;
		
		
	}
	
	
	
	
	public boolean canCalculate() {
		int num=childCollection.size();
		
		
		for(int i=0;i<num;i++)
		{
			boolean b=((BasicFacet)childCollection.get(i)).canCalculate();
			if(!b)return false; 	
		}
		return true;
	}
	
	
	
}
