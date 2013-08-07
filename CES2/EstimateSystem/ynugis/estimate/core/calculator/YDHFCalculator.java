package ynugis.estimate.core.calculator;

import java.io.IOException;

import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureCursor;
import com.esri.arcgis.interop.AutomationException;

/**
 * 以地换房资料
 *
 */
public class YDHFCalculator extends VCalculator {

	public YDHFCalculator(IFeatureClass inSimplePoint) {
		super(inSimplePoint);
	}

	public void execute() throws AutomationException, IOException {
		int vIdx = getVIdx();
		IFeatureCursor cursor = simplePoint.IFeatureClass_update(null, false);
		IFeature feature = cursor.nextFeature();
		//初始化公式变量
		double 取得房屋面积=0,交易总价=0,房屋交易税费=0,房屋面积=0,出让土地面积=0;
		double value = 0;
		while (feature != null) {
			//带入公式计算
			//([取得房屋面积]*([交易总价]-[房屋交易税费])/[房屋面积])/[出让土地面积]
			取得房屋面积=Double.parseDouble((feature.getValue(getFieldsIdx("取得房屋面积"))).toString());
			交易总价=Double.parseDouble((feature.getValue(getFieldsIdx("交易总价"))).toString());
			房屋交易税费=Double.parseDouble((feature.getValue(getFieldsIdx("房屋交易税费"))).toString());
			房屋面积=Double.parseDouble((feature.getValue(getFieldsIdx("房屋面积"))).toString());
			出让土地面积=Double.parseDouble((feature.getValue(getFieldsIdx("出让土地面积"))).toString());
			
			value=(取得房屋面积*(交易总价-房屋交易税费)/房屋面积)/出让土地面积;
			feature.setValue(vIdx, new Double(value));
			cursor.updateFeature(feature);
			feature = cursor.nextFeature();
}		
	}

}
