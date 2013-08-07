package ynugis.ffsystem;

import java.io.IOException;
import java.net.UnknownHostException;

import ynugis.geo.Merge;

import com.esri.arcgis.geodatabase.IGeoDataset;

public abstract class Factor extends FFItem {
	/*final static int POLYGON_FACTOR=0;
	final static int NON_POLYGON_FACTOR=1;
	*/
	public IGeoDataset calculate(CalculatorInfo calculInfo) throws UnknownHostException, IOException, Exception {

		System.out.println("开始计算 "+name+" :");
		
		Merge m=new Merge(calculInfo.getEnv());
		int num=childCollection.size();
		IGeoDataset data[]=new IGeoDataset[num];
		double weight[]=new double[num];
		
		FFItem bf;
		for(int i=0;i<num;i++)
		{
			bf=((FFItem) childCollection.get(i));
			//计算子结点
			if(!bf.calculated)bf.calculate(calculInfo);
			
			data[i]=bf.getCalcuResult();
			weight[i]=bf.weight;
		}
		m.specifyDataLayers(data);
		calcuResult= m.mergeDataLayers(weight);
		calculated=true;
		return calcuResult;
	}

	public abstract boolean canCalculate();

	

}
