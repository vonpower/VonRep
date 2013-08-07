package ynugis.estimate.core.calculator;

import java.io.IOException;

import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureCursor;
import com.esri.arcgis.interop.AutomationException;

/**
 * 土地联营入股资料(用土地入股取得的年收益)
 *
 */
public class TDLYCalculator extends VCalculator {

	public TDLYCalculator(IFeatureClass inSimplePoint) {
		super(inSimplePoint);
	}

	public void execute() throws AutomationException, IOException {
		int vIdx = getVIdx();
		IFeatureCursor cursor = simplePoint.IFeatureClass_update(null, false);
		IFeature feature = cursor.nextFeature();
		//初始化公式变量
		double 出地方纯收入=0,出地方面积=0,土地还原率=0;
		double value = 0;
		while (feature != null) {
			//带入公式计算
			//([出地方纯收入]/[出地方面积])*(1/{土地还原率})
			出地方纯收入=Double.parseDouble((feature.getValue(getFieldsIdx("出地方纯收入"))).toString());
			出地方面积=Double.parseDouble((feature.getValue(getFieldsIdx("出地方面积"))).toString());
			土地还原率=getPPValue("土地还原率");
			
			
			value=(出地方纯收入/出地方面积)*(1/土地还原率);
			feature.setValue(vIdx, new Double(value));
			cursor.updateFeature(feature);
			feature = cursor.nextFeature();
}				
	}

}
