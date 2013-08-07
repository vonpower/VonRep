package ynugis.estimate.core.calculator;

import java.io.IOException;

import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureCursor;
import com.esri.arcgis.interop.AutomationException;

/**
 *联合建房资料
 *
 */
public class LHJFCalculator extends VCalculator {

	public LHJFCalculator(IFeatureClass inSimplePoint) {
		super(inSimplePoint);
	}

	public void execute() throws AutomationException, IOException {
		int vIdx = getVIdx();
		IFeatureCursor cursor = simplePoint.IFeatureClass_update(null, false);
		IFeature feature = cursor.nextFeature();
		//初始化公式变量
		double 单位面积造价=0,建设税费=0,出地方分成建筑面积=0,容积率=0,出资方分成建筑面积=0;
		double value = 0;
		while (feature != null) {
			//带入公式计算
			//(([单位面积造价]+[建设税费])*[出地方分成建筑面积]*[容积率])/[出资方分成建筑面积]
			单位面积造价=Double.parseDouble((feature.getValue(getFieldsIdx("单位面积造价"))).toString());
			建设税费=Double.parseDouble((feature.getValue(getFieldsIdx("建设税费"))).toString());
			出地方分成建筑面积=Double.parseDouble((feature.getValue(getFieldsIdx("出地方分成建筑面积"))).toString());
			容积率=Double.parseDouble((feature.getValue(getFieldsIdx("容积率"))).toString());
			出资方分成建筑面积=Double.parseDouble((feature.getValue(getFieldsIdx("出资方分成建筑面积"))).toString());
			
			
			value=((单位面积造价+建设税费)*出地方分成建筑面积*容积率)/出资方分成建筑面积;
			feature.setValue(vIdx, new Double(value));
			cursor.updateFeature(feature);
			feature = cursor.nextFeature();
}		
		
	}

}
