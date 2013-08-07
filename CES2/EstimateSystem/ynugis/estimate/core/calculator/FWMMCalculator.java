package ynugis.estimate.core.calculator;

import java.io.IOException;

import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureCursor;
import com.esri.arcgis.interop.AutomationException;


/**
 * 房屋买卖价格资料
 *
 */
public class FWMMCalculator extends VCalculator {

	public FWMMCalculator(IFeatureClass inSimplePoint) {
		super(inSimplePoint);
	}

	public void execute() throws AutomationException, IOException {
		 int vIdx = getVIdx();
			IFeatureCursor cursor = simplePoint.IFeatureClass_update(null, false);
			IFeature feature = cursor.nextFeature();
			//初始化公式变量
			double 交易总价=0,结构重置价=0,房屋成新度=0,交易税费率=0,房屋建筑面=0;
			String 房屋结构="";
			double value = 0;
			while (feature != null) {
				//带入公式计算
				//([交易总价]-{[房屋结构]结构重置价}*[房屋成新度]-[交易总价]*{交易税费率})/[房屋建筑面]
				交易总价=Double.parseDouble((feature.getValue(getFieldsIdx("交易总价"))).toString());
				房屋结构=feature.getValue(getFieldsIdx("房屋结构")).toString();
				结构重置价=getPPValue(房屋结构+"结构重置价");
				房屋成新度=Double.parseDouble((feature.getValue(getFieldsIdx("房屋成新度"))).toString());
				交易税费率=getPPValue("交易税费率");
				房屋建筑面=Double.parseDouble((feature.getValue(getFieldsIdx("房屋建筑面"))).toString());
				
				value=(交易总价-结构重置价*房屋成新度-交易总价*交易税费率)/房屋建筑面;
				feature.setValue(vIdx, new Double(value));
				cursor.updateFeature(feature);
				feature = cursor.nextFeature();
	}
	}
}
