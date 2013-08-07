/*
* @author 冯涛，创建日期：2006-5-25
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
package ynugis.estimate.core.calculator;

import java.io.IOException;

import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureCursor;
import com.esri.arcgis.interop.AutomationException;

public class TestCalculator extends VCalculator {

	public TestCalculator(IFeatureClass inSimplePoint) {
		super(inSimplePoint);
		
	}

	public void execute() throws AutomationException, IOException {
		int vIdx = getVIdx();
		IFeatureCursor cursor = simplePoint.IFeatureClass_update(null, false);
		IFeature feature = cursor.nextFeature();
		double 月总租金,管理费率,维修费率,房屋成新度,保险费率,交易税费率,房屋建筑面;
		String 房屋结构="";
		double value = 0;
		while (feature != null) {
			//带入公式计算
			//([月总租金]*12-([月总租金]*12*{管理费率}+{[房屋结构]结构重置价}*{维修费率}+{[房屋结构]结构重置价}*[房屋成新度]*{保险费率}+{[房屋结构]结构重置价}*(1－{[房屋结构]结构残置率})/{[房屋结构]结构耐用年限}+[月总租金]*12*{交易税费率}+{[房屋结构]结构重置价}*[房屋成新度]*{房屋还原率})/[房屋建筑面]"
			value=1000;
			feature.setValue(vIdx, new Double(value));
			cursor.updateFeature(feature);
			feature = cursor.nextFeature();

		}
		
	}

}
