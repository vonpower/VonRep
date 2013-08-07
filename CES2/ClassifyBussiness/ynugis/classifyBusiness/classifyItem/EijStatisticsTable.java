/**
 * @(#) EijStatisticsTable.java
 */

package ynugis.classifyBusiness.classifyItem;

import java.io.IOException;
import java.net.UnknownHostException;

import org.eclipse.swt.widgets.Shell;

import ynugis.ui.confDialog.RRDialog;
import ynugis.utility.GT;

/**
 * 作用分统计表
 * @author Administrator
 *
 */
public class EijStatisticsTable extends CTable
{

	RRDialog rrd;
	EijStatisticsDialog esd;
	public EijStatisticsTable(CellValueCMap inCellValueCMap) {
		super(inCellValueCMap);
		rrd=new RRDialog(new Shell(),cellValueMap.getAsRaster());
		esd=new EijStatisticsDialog(new Shell(),cellValueMap.getAsRaster());
	}

	

	public void saveAs(String path) throws UnknownHostException, IOException {
		String[] result=rrd.getRasterArray();
		String temp="";
		int index;
		int start=0;
		int end=10;
		double sum=0;
		for(int i=0;i<result.length;i++)
		{	
			index=result[i].indexOf("-");
			if(index==-1){
				continue;
			}
			if(end==110)
			{
				end=Integer.MAX_VALUE;
			}
			temp+=start+"-"+end+":\n单元格数量："+result[i].substring(0,index-1)+" 所占百分比： "+result[i].substring(index+1)+"\n";
			sum+=Double.parseDouble(result[i].substring(0,index-1));
			start+=10;
			end+=10;
		}
		temp+="－－－－－－－－－－－－－－－－\n"+"单元格总数："+sum+"\n";
		GT.write2Txt(path,temp);
	}
	
	public void show() {
		
		esd.open();
		
	}
	
}
