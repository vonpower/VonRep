package ynugis.estimate.core.calculator;

import java.io.IOException;

import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureCursor;
import com.esri.arcgis.interop.AutomationException;

/**
 *城镇产业用地效益资料
 *
 */
public class CZCYCalculator extends VCalculator {

	public CZCYCalculator(IFeatureClass inSimplePoint) {
		super(inSimplePoint);
	}

	public void execute() throws AutomationException, IOException {
		int vIdx = getVIdx();
		IFeatureCursor cursor = simplePoint.IFeatureClass_update(null, false);
		IFeature feature = cursor.nextFeature();
		//初始化公式变量
		double 年产值=0,工资总额=0,利润额=0,税后留利=0,流动资金=0,固定资金=0,银行利息率=0,非土地资产纯收益=0,土地还原率=0,用地面积=0;
		double value = 0;
		while (feature != null) {
			//带入公式计算
			//((([年产值]-[工资总额]-[利润额]-[税后留利]-([流动资金]+[固定资金])*{银行利息率})-非土地资产纯收益)/{土地还原率})/[用地面积]
			年产值=Double.parseDouble((feature.getValue(getFieldsIdx("年产值"))).toString());
			工资总额=Double.parseDouble((feature.getValue(getFieldsIdx("工资总额"))).toString());
			利润额=Double.parseDouble((feature.getValue(getFieldsIdx("利润额"))).toString());
			税后留利=Double.parseDouble((feature.getValue(getFieldsIdx("税后留利"))).toString());
			流动资金=Double.parseDouble((feature.getValue(getFieldsIdx("流动资金"))).toString());
			固定资金=Double.parseDouble((feature.getValue(getFieldsIdx("固定资金"))).toString());
			用地面积=Double.parseDouble((feature.getValue(getFieldsIdx("用地面积"))).toString());
			银行利息率=getPPValue("银行利息率");
			土地还原率=getPPValue("土地还原率");
			
			
			value=(((年产值-工资总额-利润额-税后留利-(流动资金+固定资金)*银行利息率)-非土地资产纯收益)/土地还原率)/用地面积;
			feature.setValue(vIdx, new Double(value));
			cursor.updateFeature(feature);
			feature = cursor.nextFeature();
}						
	}

}
