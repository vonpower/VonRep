/*
 * @author 冯涛，创建日期：2006-5-22
 * blog--http://apower.blogone.net
 * QQ:17463776
 * MSN:apower511@msn.com
 * E-Mail:VonPower@Gmail.com
 */
package ynugis.estimate.core;

import java.util.HashMap;

import ynugis.estimate.core.calculator.CZCYCalculator;
import ynugis.estimate.core.calculator.CZSYCalculator;
import ynugis.estimate.core.calculator.FWCZCalculator;
import ynugis.estimate.core.calculator.FWMMCalculator;
import ynugis.estimate.core.calculator.GTCZCalculator;
import ynugis.estimate.core.calculator.LHJFCalculator;
import ynugis.estimate.core.calculator.SPFCSCalculator;
import ynugis.estimate.core.calculator.TDCRCalculator;
import ynugis.estimate.core.calculator.TDLY2;
import ynugis.estimate.core.calculator.TDLYCalculator;
import ynugis.estimate.core.calculator.TDZRCalculator;
import ynugis.estimate.core.calculator.VCalculator;
import ynugis.estimate.core.calculator.YDHFCalculator;
import ynugis.estimate.core.calculator.ZDCQCalculator;
import ynugis.estimate.data.ClassifyInfo;
import ynugis.estimate.data.PublicProperty;
import ynugis.estimate.data.Revise;
import ynugis.estimate.data.RevisePair;
import ynugis.utility.GV;

import com.esri.arcgis.geodatabase.DataStatistics;
import com.esri.arcgis.geodatabase.ICursorProxy;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IQueryFilter;
import com.esri.arcgis.geodatabase.QueryFilter;
import com.esri.arcgis.geometry.IArea;
import com.esri.arcgis.geometry.IAreaProxy;
import com.esri.arcgis.geometry.IPolygonProxy;
import com.esri.arcgis.interop.AutomationException;

public class EstimateRule {

	private IFeatureClass simplePoint;

	private IFeatureClass cellValue;

	private Revise classifyRevise;

	/**
	 * 
	 * @param inSimplePoint
	 * @param inCellValue
	 * @param inClassifyRevise
	 */
	public EstimateRule(IFeatureClass inSimplePoint, IFeatureClass inCellValue,
			Revise inClassifyRevise) {
		simplePoint = inSimplePoint;
		cellValue = inCellValue;
		classifyRevise = inClassifyRevise;
		initialData();
	}

	/**
	 * 样本点修正
	 * 
	 * @param revises
	 *            修正规则
	 * @throws Exception
	 */
	public void revise(Revise[] revises) throws Exception {
		if (revises == null)
			return;
		int len = revises.length;
		if (simplePoint == null || len == 0)
			return;

		for (int i = 0; i < revises.length; i++) {

			revises[i].tidy();
			new EstModify(simplePoint, revises[i], revises[i].getName())
					.modify();
		}

	}

	/**
	 * 样本点合理性检验、剔除
	 * 
	 * @param fields
	 *            进行检验的Field
	 * @param sd
	 *            方差
	 * 
	 */
	public void eliminate(String[] fields, int sd) {

		FillSampleRank fillSampleRank = new FillSampleRank();
		if (fields == null||fields.length==0) {
			fillSampleRank.allPassed(simplePoint);
			
			return;
		}
		int len = fields.length;
		for (int i = 0; i < len; i++) {
			fillSampleRank
					.checkRank(simplePoint, fields[i], classifyRevise, sd);
		}

	}

	public void calculateV(String name) throws AutomationException, Exception {

		VCalculator cal = null;
		if (name.equalsIgnoreCase("房屋出租租金资料")) {
			cal = new FWCZCalculator(simplePoint);

		} else if (name.equalsIgnoreCase("房屋买卖价格资料")) {
			cal = new FWMMCalculator(simplePoint);

		} else if (name.equalsIgnoreCase("城镇产业用地效益资料")) {
			cal = new CZCYCalculator(simplePoint);

		} else if (name.equalsIgnoreCase("城镇商业用地效益资料")) {
			cal = new CZSYCalculator(simplePoint);

		} else if (name.equalsIgnoreCase("柜台出租租金资料")) {
			cal = new GTCZCalculator(simplePoint);

		} else if (name.equalsIgnoreCase("联合建房资料")) {
			cal = new LHJFCalculator(simplePoint);

		} else if (name.equalsIgnoreCase("商品房出售价格资料")) {
			cal = new SPFCSCalculator(simplePoint);

		} else if (name.equalsIgnoreCase("土地使用权出让价格资料")) {
			cal = new TDCRCalculator(simplePoint);

		} else if (name.equalsIgnoreCase("土地联营入股资料1")) {
			cal = new TDLYCalculator(simplePoint);

		} else if (name.equalsIgnoreCase("土地联营入股资料2")) {
			cal = new TDLY2(simplePoint);

		} else if (name.equalsIgnoreCase("土地使用权转让价格资料")) {
			cal = new TDZRCalculator(simplePoint);

		} else if (name.equalsIgnoreCase(" 以地换房资料")) {
			cal = new YDHFCalculator(simplePoint);

		} else if (name.equalsIgnoreCase("征地拆迁开发土地资料")) {
			cal = new ZDCQCalculator(simplePoint);

		}else{
			throw new Exception(name + "找不到匹配的估价计算器！！！");
		}

		cal.execute();

	}

	/**
	 * 指数模型评估地价
	 * 
	 * @param pp
	 * @return 评估报告
	 */
	public String estimate_ExpModel() {
		String resultString = "";
		FillSampleRank fillSampleRank = new FillSampleRank();

		BusinessArithmaticMean bam = new BusinessArithmaticMean(simplePoint,
				classifyRevise);
		double[] result = new double[classifyRevise.getRevisePairs().length];
		RevisePair rp[] = classifyRevise.getRevisePairs();

		// 指数模型

		try {
			resultString += "指数模型计算过程:\n";
			// 计算土地质量指数
			double Yn[] = bam.getMean();
			double Xn[] = new double[Yn.length];
			classifyRevise.tidy();
			ClassifyInfo ci = new ClassifyInfo(classifyRevise);

			IQueryFilter qf = new QueryFilter();
			DataStatistics ds4cellValue;
			DataStatistics ds4simplePoint;

			String simpleStr = "";
			qf.addField("GRIDCODE");
			int cellCount = 0;
			int simplePointCount = 0;
			double area = 0;
			for (int i = 0; i < rp.length; i++) {

				ds4cellValue = new DataStatistics();
				ds4simplePoint = new DataStatistics();

				ds4cellValue.setField("GRIDCODE");
				ds4simplePoint.setField("样点所在级");

				simpleStr = "GRIDCODE >= " + rp[i].getStart() + " AND "
						+ "GRIDCODE < " + rp[i].getEnd();
				qf.setWhereClause(simpleStr);

				// System.out.println("WhereClause:"+simpleStr);
				ds4cellValue.setCursorByRef(new ICursorProxy(cellValue.search(
						qf, false)));
				qf.setWhereClause("样点所在级 = " + ci.ranks[i].rank);
				ds4simplePoint.setCursorByRef(new ICursorProxy(simplePoint
						.search(qf, false)));
				// 区段的单元格个数：
				cellCount = ds4cellValue.getStatistics().getCount();
				simplePointCount = ds4simplePoint.getStatistics().getCount();
				// 区段面积

				IArea a = new IAreaProxy(new IPolygonProxy(cellValue
						.getFeature(0).getShape()));
				area = cellCount * a.getArea();

				// 区段的平均单元分值
				ci.ranks[i].cellValueMean = ds4cellValue.getStatistics()
						.getMean()
						/ GV.multiple;
				System.out.println("ci.ranks[i].cellValueMean:"
						+ ci.ranks[i].cellValueMean);
				// 土地质量指数Xn
				ci.ranks[i].landQuality_Xn = ci.ranks[i].cellValueMean
						/ ci.rankCount;
				Xn[i] = ci.ranks[i].landQuality_Xn;
				ci.ranks[i].landIncomeMean_Yn = Yn[i];

				// 构造输出字符串
				resultString += "等级（区段）：" + rp[i].getReviseValue()
						+ "    平均单元分值:" + ci.ranks[i].cellValueMean
						+ "    土地质量指数Xn:" + ci.ranks[i].landQuality_Xn
						+ "    样点平均V值:" + ci.ranks[i].landIncomeMean_Yn
						+ "    区段单元格个数:" + cellCount + "    区段样点个数："
						+ simplePointCount + "    区段面积：" + area + "平方米\n";

			}

			fillSampleRank.getARPar(Xn, Yn, ci.rankCount);

			resultString += "AR模型计算结果：\n参数A:" + fillSampleRank.A + "\n参数r:"
					+ fillSampleRank.r + "\n参数R(相关性):" + fillSampleRank.R
					+ "\n参数a：" + fillSampleRank.a + "\n参数b:" + fillSampleRank.b
					+ "\n";
			// simpleStr = "";
			System.out.println("\nci.rankCount:" + ci.rankCount + "\nA:"
					+ fillSampleRank.A + "\nr:" + fillSampleRank.r + "\nR:"
					+ fillSampleRank.R);

			double limited = 0;
			double unlimited = 0;
			for (int i = 0; i < result.length; i++) {
				// 利用求得的A、r、R计算地价
				result[i] = fillSampleRank.A
						* (Math.pow((1 + fillSampleRank.r), Xn[i]));
				unlimited = getUnlimited(result[i]);
				// 商业用地最高出让年期为40年
				limited = getLimited(result[i]);

				resultString += "等级(区段)" + rp[i].getReviseValue() + "    收益:"
						+ result[i] + "    有限年期：" + limited + "    无限年期："
						+ unlimited + "\n";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}

	/**
	 * 算术平均模型评估地价
	 * 
	 * @return 评估报告
	 */
	public String estimate_AvgModel() {
		String resultString = "";

		BusinessArithmaticMean bam = new BusinessArithmaticMean(simplePoint,
				classifyRevise);
		double[] result = new double[classifyRevise.getRevisePairs().length];
		RevisePair rp[] = classifyRevise.getRevisePairs();
		try {
			resultString += "算术平均模型:\n";
			HashMap roadMap = bam.getMeanByRoad();
			result = bam.getMean();

			// 用对话框显示计算结果
			String str = "";
			for (int i = 0; i < result.length; i++) {
				str += "等级（区段）：" + rp[i].getReviseValue() + "   收益："
						+ result[i] + "  有限年期：" + getLimited(result[i])
						+ "  无限年期：" + getUnlimited(result[i]) + "\n";

			}
			if(roadMap!=null)
			{
			str += "算术平均模型 路线价 \n";
			Object roads[] = roadMap.keySet().toArray();
			String win = "";
			double limited = 0;
			double unlimited = 0;
			for (int i = 0; i < roads.length; i++) {
				win = roadMap.get(roads[i]).toString();
				unlimited = getUnlimited(Double.parseDouble(win));
				limited = getLimited(Double.parseDouble(win));

				str += roads[i].toString() + " :  V值（收益）：" + win + "  有限年期："
						+ limited + "  无限年期：" + unlimited + "\n";
			}
			}
			resultString += str;
			// MessageDialog.openInformation(getShell(), "指数模型", str);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultString;
	}

	/**
	 * 有限年期低价计算
	 * 
	 * @param income
	 * @return
	 */
	private double getLimited(double income) {
		return getUnlimited(income)
				* (1 - 1 / Math.pow((1 + PublicProperty.TDHYL.getdata()), PublicProperty.TDSYNX.getdata()));
	}

	/**
	 * 无限年期地价计算
	 * 
	 * @param income
	 * @return
	 */
	private double getUnlimited(double income) {
		return income / PublicProperty.TDHYL.getdata();
	}

	/**
	 * 初始化定级过程中会用到的一些数据（填写样本点资料中“样点所在级”字段）
	 * 
	 * @param cellValue
	 * @param simplePoint
	 * @param classifyRevise
	 */
	private void initialData() {

		FillSampleRank fillSampleRank = new FillSampleRank();
		fillSampleRank.fillFields(cellValue, simplePoint, classifyRevise);

	}

}
