package ynugis.estimate.core.calculator;

import java.io.IOException;

import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureCursor;
import com.esri.arcgis.interop.AutomationException;

/**
 *土地联营入股资料(用合同规定的资本投入情况和分成比例)
 *
 */
public class TDLY2 extends VCalculator {

	public TDLY2(IFeatureClass inSimplePoint) {
		super(inSimplePoint);
	}

	public void execute() throws AutomationException, IOException {
		int vIdx = getVIdx();
		IFeatureCursor cursor = simplePoint.IFeatureClass_update(null, false);
		IFeature feature = cursor.nextFeature();
		//初始化公式变量
		double 出资方总金额=0,出地方面积=0,出地方利润比例=0;
		double value = 0;
		while (feature != null) {
			//带入公式计算
			//[出资方总金额]*[出地方利润比例]*(1/[出地方面积])
			出资方总金额=Double.parseDouble((feature.getValue(getFieldsIdx("出资方总金额"))).toString());
			出地方利润比例=Double.parseDouble((feature.getValue(getFieldsIdx("出地方利润比例"))).toString());
			出地方面积=Double.parseDouble((feature.getValue(getFieldsIdx("出地方面积"))).toString());
			
			value=出资方总金额*出地方利润比例*(1/出地方面积);
			feature.setValue(vIdx, new Double(value));
			cursor.updateFeature(feature);
			feature = cursor.nextFeature();
}						
	}

}
