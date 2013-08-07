package ynugis.estimate.core.calculator;

import java.io.IOException;

import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureCursor;
import com.esri.arcgis.interop.AutomationException;

/**
 * 柜台出租租金资料
 *
 */
public class GTCZCalculator extends VCalculator {

	public GTCZCalculator(IFeatureClass inSimplePoint) {
		super(inSimplePoint);
	}

	public void execute() throws AutomationException, IOException {
		int vIdx = getVIdx();
		IFeatureCursor cursor = simplePoint.IFeatureClass_update(null, false);
		IFeature feature = cursor.nextFeature();
		//初始化公式变量
		double 柜台年租金=0,商店年经营总费用=0,营业面积=0,总营业面积=0,土地面积=0,土地还原率=0;
		double value = 0;
		while (feature != null) {
			//带入公式计算
			//([柜台年租金]-商店年经营总费用*([营业面积]/[总营业面积]))/[土地面积]*([营业面积]/[总营业面积])*1/{土地还原率}
			柜台年租金=Double.parseDouble((feature.getValue(getFieldsIdx(" 柜台年租金"))).toString());
			营业面积=Double.parseDouble((feature.getValue(getFieldsIdx("营业面积"))).toString());
			总营业面积=Double.parseDouble((feature.getValue(getFieldsIdx("总营业面积"))).toString());
			土地面积=Double.parseDouble((feature.getValue(getFieldsIdx("土地面积"))).toString());
			土地还原率=getPPValue("土地还原率");
			value=(柜台年租金-商店年经营总费用*(营业面积/总营业面积))/土地面积*(营业面积/总营业面积)*1/土地还原率;
			feature.setValue(vIdx, new Double(value));
			cursor.updateFeature(feature);
			feature = cursor.nextFeature();
}
		
	}

}
