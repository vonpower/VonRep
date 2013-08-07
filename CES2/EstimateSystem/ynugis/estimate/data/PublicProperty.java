/*
* @author 冯涛，创建日期：2003-11-25
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
package ynugis.estimate.data;

import java.lang.reflect.Field;
import java.util.HashMap;

public class PublicProperty {
	// 土地还原率
	public static DoubleCell TDHYL = new DoubleCell(0.09, "土地还原率");

	// 房屋还原率
	public static DoubleCell FWHDL = new DoubleCell(0.05, "房屋还原率");

	// 银行利息率
	public static DoubleCell YHLXL = new DoubleCell(0.05, "银行利息率");

	// 钢混结构耐用年限
	public static DoubleCell GHJGNYNX = new DoubleCell(60, "钢混结构耐用年限");

	//钢混结构残置率
	public static DoubleCell GHJGCZL = new DoubleCell(0, "钢混结构残置率");
	
	// 钢混结构重置价
	public static DoubleCell GHJGCZJ = new DoubleCell(750, "钢混结构重置价");
	

	// 一等砖混结构耐用年限
	public static DoubleCell YDZHJGNYNX = new DoubleCell(50, "一等砖混结构耐用年限");

	// 一等砖混结构残置率
	public static DoubleCell YDZHJGCZL = new DoubleCell(0.03, "一等砖混结构残置率");

	// 一等砖混结构重置价 (元/平方米)
	public static DoubleCell YDZHJGCZJ = new DoubleCell(550, "一等砖混结构重置价");

	// 二等砖混结构耐用年限
	public static DoubleCell EDZHJGNYNX = new DoubleCell(50, "二等砖混结构耐用年限");

	// 二等砖混结构残置率
	public static DoubleCell EDZHJGCZL = new DoubleCell(0.03, "二等砖混结构残置率");

	// 二等砖混结构重置价 (元/平方米)
	public static DoubleCell EDZHJGCZJ = new DoubleCell(550, "二等砖混结构重置价");

	// 一等砖木结构耐用年限
	public static DoubleCell YDZMJGNYNX = new DoubleCell(40, "一等砖木结构耐用年限");

	// 一等砖木结构残置率
	public static DoubleCell YDZMJGCZL = new DoubleCell(0.06, "一等砖木结构残置率");

	// 一等砖木结构重置价
	public static DoubleCell YDZMJGCZJ = new DoubleCell(550, "一等砖木结构重置价");

	// 二等砖木结构耐用年限
	public static DoubleCell EDZMJGNYNX = new DoubleCell(40, "二等砖木结构耐用年限");

	// 二等砖木结构残置率
	public static DoubleCell EDZMJGCZL = new DoubleCell(0.06, "二等砖木结构残置率");

	// 二等砖木结构重置价
	public static DoubleCell EDZMJGCZJ = new DoubleCell(550, "二等砖木结构重置价");

	// 三等砖木结构耐用年限
	public static DoubleCell SDZMJGNYNX = new DoubleCell(40, "三等砖木结构耐用年限");

	// 三等砖木结构残置率
	public static DoubleCell SDZMJGCZL = new DoubleCell(0.06, "三等砖木结构残置率");

	// 三等砖木结构重置价
	public static DoubleCell SDZMJGCZJ = new DoubleCell(550, "三等砖木结构重置价");

	// 土木结构耐用年限
	public static DoubleCell TMJGNYNX = new DoubleCell(30, "土木结构耐用年限");

	// 土木结构残置率
	public static DoubleCell TMJGCZL = new DoubleCell(0.06, "土木结构残置率");

	// 土木结构重置价
	public static DoubleCell TMJGCZJ = new DoubleCell(250, "土木结构重置价");

	// 简易结构耐用年限
	public static DoubleCell JYJGNYNX = new DoubleCell(15, "简易结构耐用年限");

	// 简易结构残置率
	public static DoubleCell JYJGCZL = new DoubleCell(0.03, "简易结构残置率");

	// 简易结构重置价 (元/平方米)
	public static DoubleCell JYJGCZJ = new DoubleCell(80, "简易结构重置价");

	// 房产税费
	public static DoubleCell FCSF = new DoubleCell(0.1, "房产税费");

	// 管理费率
	public static DoubleCell GLFL = new DoubleCell(0.08, "管理费率");

	// 维修费率
	public static DoubleCell WXFL = new DoubleCell(0.08, "维修费率");

	// 保险费率
	public static DoubleCell BXFM = new DoubleCell(0.002, "保险费率");

	// 基准地价对应期日
	public static DoubleCell JZDJDYRQ = new DoubleCell(19811011, "基准地价对应期日");

	// 交易税费率
	public static DoubleCell JYSFL = new DoubleCell(0.176, "交易税费率");
	
	//土地增值收益率
	public static DoubleCell TDZZSYL = new DoubleCell(0.176, "土地增值收益率");
	
	//土地使用年限
	public static DoubleCell TDSYNX= new DoubleCell(40,"土地使用年限");
	public static HashMap getHashMap()
	{
		HashMap map=new HashMap();
		Class pp=PublicProperty.class;
		Field[] fields=pp.getFields();
		DoubleCell DCs[]=new DoubleCell[fields.length];
		for(int i=0;i<fields.length;i++)
		{
			try {
				DCs[i]=(DoubleCell)fields[i].get(pp);
				map.put(DCs[i].getname(),new Double(DCs[i].getdata()));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return map;
	}
	/**
	 * 
	 *//*
	public PublicProperty() {
		super();
	}*/

	

}
