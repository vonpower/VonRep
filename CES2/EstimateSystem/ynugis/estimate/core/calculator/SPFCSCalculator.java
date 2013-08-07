package ynugis.estimate.core.calculator;

import java.io.IOException;

import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureCursor;
import com.esri.arcgis.interop.AutomationException;

/**
 *商品房出售价格资料
 *
 */
public class SPFCSCalculator extends VCalculator {

	public SPFCSCalculator(IFeatureClass inSimplePoint) {
		super(inSimplePoint);
	}

	public void execute() throws AutomationException, IOException {
		int vIdx = getVIdx();
		IFeatureCursor cursor = simplePoint.IFeatureClass_update(null, false);
		IFeature feature = cursor.nextFeature();
		//初始化公式变量
		double 总销售价格=0,同类建筑平均造价=0,房屋建筑总面积=0,开发利润=0,销售税费=0,资金利息=0,建筑密度=0,房屋占地面积=0;
		double value = 0;
		while (feature != null) {
			//带入公式计算
			//([总销售价格]-([同类建筑平均造价]*[房屋建筑总面积])-[开发利润]-[销售税费]-[资金利息])*([建筑密度]/[房屋占地面积])
			总销售价格=Double.parseDouble((feature.getValue(getFieldsIdx("总销售价格"))).toString());
			同类建筑平均造价=Double.parseDouble((feature.getValue(getFieldsIdx("同类建筑平均造价"))).toString());
			房屋建筑总面积=Double.parseDouble((feature.getValue(getFieldsIdx("房屋建筑总面积"))).toString());
			开发利润=Double.parseDouble((feature.getValue(getFieldsIdx("开发利润"))).toString());
			销售税费=Double.parseDouble((feature.getValue(getFieldsIdx("销售税费"))).toString());
			资金利息=Double.parseDouble((feature.getValue(getFieldsIdx("资金利息"))).toString());
			建筑密度=Double.parseDouble((feature.getValue(getFieldsIdx("建筑密度"))).toString());
			房屋占地面积=Double.parseDouble((feature.getValue(getFieldsIdx("房屋占地面积"))).toString());
			
			value=(总销售价格-(同类建筑平均造价*房屋建筑总面积)-开发利润-销售税费-资金利息)*(建筑密度/房屋占地面积);
			feature.setValue(vIdx, new Double(value));
			cursor.updateFeature(feature);
			feature = cursor.nextFeature();
}		
		
	}

}
