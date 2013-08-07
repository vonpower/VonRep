/*
* @author 冯涛，创建日期：2003-11-25
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
package ynugis.ffsystem;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;

import org.eclipse.core.runtime.IProgressMonitor;

import ynugis.geo.Merge;

import com.FFT.BasicFacetType;
import com.FFT.FFTDoc;
import com.esri.arcgis.geodatabase.IGeoDataset;

public class BasicFacet extends Facet
{
	//private DerivedFacet dfc;
	
	//private boolean haveDerivedFacet;
	
	
	public BasicFacet(BasicFacetType bft) throws Exception
	{
		super();
		name=bft.getName().getValue();
		weight=bft.getWeight().getValue();
		setFormula(bft.getFormula().getValue());
		//加入地图数据
		int count=bft.getDataPathCount();
		if(count>0)
		{
			srcFiles=new String[count];
			for(int i=0;i<count;i++)
			{
				srcFiles[i]=bft.getDataPathAt(i).getValue();
			}
		}
		//加入派生因子
		count=bft.getDerivedFacetCount();
		if(count>0)
		{
			for(int i=0;i<count;i++)
			{
				
				addDerivedFacet(new DerivedFacet(bft.getDerivedFacetAt(i)));
			}
			
		}
		
		
			
		
	}
	
	
	
	
	public DerivedFacet getDerivedFacets(int idx)
	{
		Object o=childCollection.get(idx);
		return (o instanceof DerivedFacet)?(DerivedFacet)o:null;
	}
	
	public void addDerivedFacet(DerivedFacet df)
	{
		childCollection.add(df);
	}

	public boolean canCalculate() {
		
		int num=childCollection.size();
		if(num==0)return (srcFiles!=null);
		
		for(int i=0;i<num;i++)
		{
			boolean b=((DerivedFacet)childCollection.get(i)).canCalculate();
			if(!b)return false; 	
		}
		return true;
		
	}

	public synchronized IGeoDataset calculate(CalculatorInfo calculInfo) throws UnknownHostException, IOException, Exception {
		
		System.out.println("开始计算 "+name+" :");
		if(srcFiles!=null)return calculateLeafFacet(calculInfo);
		//把当前派生因子的计算结果加权叠加
		Merge m=new Merge(calculInfo.getEnv());
		int num=childCollection.size();
		IGeoDataset data[]=new IGeoDataset[num];
		double weight[]=new double[num];
		
		DerivedFacet df;
		for(int i=0;i<num;i++)
		{
			df=((DerivedFacet) childCollection.get(i));
			//计算子结点
			if(!df.calculated)df.calculate(calculInfo);
			
			data[i]=df.getCalcuResult();
			weight[i]=df.weight;
		}
		m.specifyDataLayers(data);
		calcuResult= m.mergeDataLayers(weight);
		calculated=true;
		return calcuResult;
		
	}




	public BasicFacet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BasicFacetType getAsBasicFacetType() throws Exception
	{
		FFTDoc doc = new FFTDoc();
		BasicFacetType bft=new BasicFacetType(doc,"http://my.opera.com/VonPower/","", "BasicFacet");
		bft.addName(name);
		bft.addWeight(Double.toString(weight));
		bft.addFormula(getFormula()+"");
		if(getSrcFiles()!=null&&getSrcFiles().length>0)
		{
		for(int i=0;i<getSrcFiles().length;i++)
		{
			bft.addDataPath(getSrcFiles()[i]);
		}
		}
		int count=childCollection.size();
		while(count>0)
		{
			
			bft.addDerivedFacet(((DerivedFacet)childCollection.get(count-1)).getAsDerivedFacetType());
			count--;
		}
		return bft;
		
		
	}




	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		// TODO Auto-generated method stub
		
	}

}
