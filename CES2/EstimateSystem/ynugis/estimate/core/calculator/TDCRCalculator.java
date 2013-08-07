package ynugis.estimate.core.calculator;

import java.io.IOException;

import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureCursor;
import com.esri.arcgis.interop.AutomationException;

/**
 *土地使用权出让价格资料
 *
 */
public class TDCRCalculator extends VCalculator {

	public TDCRCalculator(IFeatureClass inSimplePoint) {
		super(inSimplePoint);
	}

	public void execute() throws AutomationException, IOException {
		int vIdx = getVIdx();
		IFeatureCursor cursor = simplePoint.IFeatureClass_update(null, false);
		IFeature feature = cursor.nextFeature();
		//初始化公式变量
		double 总价格=0,土地面积=0;
		double value = 0;
		while (feature != null) {
			//带入公式计算
			//[总价格]/[土地面积]
			总价格=Double.parseDouble((feature.getValue(getFieldsIdx("总价格"))).toString());
			土地面积=Double.parseDouble((feature.getValue(getFieldsIdx("土地面积"))).toString());
			
			value=总价格/土地面积;
			feature.setValue(vIdx, new Double(value));
			cursor.updateFeature(feature);
			feature = cursor.nextFeature();		
	}
	}
}
