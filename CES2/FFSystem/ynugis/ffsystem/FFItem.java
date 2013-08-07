package ynugis.ffsystem;
/*
* @author ���Σ��������ڣ�2003-11-25
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import ynugis.classifyBusiness.classifyItem.CellValueCMap;
import ynugis.classifyBusiness.classifyItem.EijStatisticsTable;
import ynugis.classifyBusiness.classifyItem.FrequencyDiagram;
import ynugis.classifyBusiness.classifyItem.RankCMap;

import com.esri.arcgis.geodatabase.IGeoDataset;

public abstract class FFItem {
	public String name;
	private RankCMap rankMap=null;
	

	public double weight;

	public ArrayList childCollection;

	/*
	 * private CellValueCMap cellValueCMap; private FrequencyDiagram
	 * frequencyDiagram; private EijStatisticsTable eijStatisticsTable;
	 */
	public boolean calculated = false;
 
	public IGeoDataset calcuResult;

	public abstract IGeoDataset calculate(CalculatorInfo calculInfo)
			throws UnknownHostException, IOException, Exception;

	public abstract boolean canCalculate();

	public String verifyWeight() {
		String ret="";
		
		Object o;
		double sum = 0;
		int count = childCollection.size();
		//���itemΪҶ�ӣ�Ȩ��ֻҪС�ڵ���1���ڵ���0����Ϊ�Ϸ�
		if (count == 0) {
			if (weight >= 0 && weight <= 1)
				return ret;
			else {
				ret=name+"->"+ret;
				return ret;
			}
		}
		for (int i = 0; i < count; i++) {
			o = childCollection.get(i);
			if (o instanceof FFItem) {
				String temp=((FFItem) o).verifyWeight();
				if (!temp.equals(""))
				{// �����ӽ���Ȩ��Ϊ���Ϸ�
					
					ret=name+"->"+temp;
					return ret;
				}
				sum = sum + ((FFItem) o).weight;

			}

		}
		DecimalFormat df = new DecimalFormat("#.00");
		sum = Double.parseDouble(df.format(sum));
		
		if (sum != 1) {
			ret=name+"->"+ret;
			return ret;
			
		} else
			return ret;

	}

	public IGeoDataset getCalcuResult() {

		return calcuResult;
	}

	public FFItem() {
		super();
		childCollection = new ArrayList();

	}

	public void removeChild(int idx) {
		childCollection.remove(idx);
	}

	public void removeChild(Object obj) {
		childCollection.remove(obj);
	}

	public CellValueCMap getCellValueCMap() {
		return calculated ? new CellValueCMap(getCalcuResult()) : null;
	}

	public EijStatisticsTable getEijStatisticsTable() {

		return new EijStatisticsTable(getCellValueCMap());
	}

	public FrequencyDiagram getFrequencyDiagram() {
		return new FrequencyDiagram(getCellValueCMap());
	}

	public RankCMap initialRankMap(double[] start,double[] end,int[] value) {
		rankMap=new RankCMap(getCellValueCMap(), start, end, value);
		return rankMap;
	}
	public RankCMap getRankMap()
	{
		return rankMap;
	}
}
