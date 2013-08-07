package ynugis.estimate.core.calculator;

import java.io.IOException;

import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureCursor;
import com.esri.arcgis.interop.AutomationException;

/**
 * 征地拆迁开发土地资料
 * 
 */
public class ZDCQCalculator extends VCalculator {

	public ZDCQCalculator(IFeatureClass inSimplePoint) {
		super(inSimplePoint);
	}

	public void execute() throws AutomationException, IOException {
		int vIdx = getVIdx();
		IFeatureCursor cursor = simplePoint.IFeatureClass_update(null, false);
		IFeature feature = cursor.nextFeature();
		// 初始化公式变量
		double 土地取得费 = 0, 土地开发费 = 0, 税费 = 0, 利息 = 0, 利润 = 0, 土地增值收益率 = 0;
		double value = 0;
		while (feature != null) {
			// 带入公式计算
			// [土地取得费]+[土地开发费]+[税费]+[利息]+[利润]+([土地取得费]+[土地开发费]+[税费]+[利息]+[利润])*{土地增值收益率}
			土地取得费 = Double
					.parseDouble((feature.getValue(getFieldsIdx("土地取得费")))
							.toString());
			土地开发费 = Double
					.parseDouble((feature.getValue(getFieldsIdx("土地开发费")))
							.toString());
			税费 = Double.parseDouble((feature.getValue(getFieldsIdx("税费")))
					.toString());
			利息 = Double.parseDouble((feature.getValue(getFieldsIdx("利息")))
					.toString());
			利润 = Double.parseDouble((feature.getValue(getFieldsIdx("利润")))
					.toString());
			土地增值收益率 = getPPValue("土地增值收益率");

			value = 土地取得费 + 土地开发费 + 税费 + 利息 + 利润
					+ (土地取得费 + 土地开发费 + 税费 + 利息 + 利润) * 土地增值收益率;
			feature.setValue(vIdx, new Double(value));
			cursor.updateFeature(feature);
			feature = cursor.nextFeature();
		}
	}

}
