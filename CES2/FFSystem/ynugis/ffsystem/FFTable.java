package ynugis.ffsystem;
/*
* @author 冯涛，创建日期：2003-11-25
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/

import java.io.IOException;
import java.net.UnknownHostException;

import ynugis.geo.Merge;

import com.FFT.FFTDoc;
import com.FFT.FFTableType;
import com.esri.arcgis.geodatabase.IGeoDataset;

public class FFTable extends FFItem
{
	
	//private int classifyType;
	public FFTable() {
		super();
		weight=1;
		
	}
	public FFTable(FFTableType fft) throws Exception {
		super();
		name=fft.getName().getValue();
		//weight=0;
		
		//加入因素类别
		int count=fft.getFactorCatalogCount();
		if(count>0)
		{
			for(int i=0;i<count;i++)
			{
				addFactorCatalog(new FactorCatalog(fft.getFactorCatalogAt(i)));
			}
			
		}
		
		
	}
	
	public void addFactorCatalog(FactorCatalog infc )
	{
		childCollection.add(infc);
	}
	public FactorCatalog getFactorCatalog(int index)
	{
		return (FactorCatalog)childCollection.get(index);
	}
	
	public IGeoDataset calculate(CalculatorInfo calculInfo) throws UnknownHostException, IOException, Exception {
		
		System.out.println("开始计算 "+name+" :");
		
		Merge m=new Merge(calculInfo.getEnv());
		int num=childCollection.size();
		IGeoDataset data[]=new IGeoDataset[num];
		double weight[]=new double[num];
		
		FactorCatalog df;
		for(int i=0;i<num;i++)
		{
			df=((FactorCatalog) childCollection.get(i));
			//计算子结点
			if(!df.calculated)
				df.calculate(calculInfo);
			
			data[i]=df.getCalcuResult();
			weight[i]=df.weight;
		}
		m.specifyDataLayers(data);
		calcuResult= m.mergeDataLayers(weight);
		calculated=true;
		return calcuResult;
		
	}


	public boolean canCalculate() {
		int num=childCollection.size();
		
		
		for(int i=0;i<num;i++)
		{
			boolean b=((FactorCatalog)childCollection.get(i)).canCalculate();
			if(!b)return false; 	
		}
		return true;
	}
	public FFTableType getAsFFTableType() throws Exception
	{
		FFTDoc doc = new FFTDoc();
		FFTableType fft=new FFTableType(doc,"http://my.opera.com/VonPower/","", "FFTable");
		fft.addName(name);
		
		
		
		int count=childCollection.size();
		while(count>0)
		{
			
			fft.addFactorCatalog(((FactorCatalog)childCollection.get(count-1)).getAsFactorCatalogType());
			count--;
		}
		return fft;
		
	}
	 
}
