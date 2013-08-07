package ynugis.estimate.core.calculator;

import java.io.IOException;

import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureCursor;
import com.esri.arcgis.interop.AutomationException;

/**
 * 房屋出租租金资料
 * @author Administrator
 *
 */
public class FWCZCalculator extends VCalculator {

	public FWCZCalculator(IFeatureClass inSimplePoint) {
		super(inSimplePoint);
		// TODO Auto-generated constructor stub
	}

	public void execute() throws AutomationException, IOException {
		 int vIdx = getVIdx();
			IFeatureCursor cursor = simplePoint.IFeatureClass_update(null, false);
			IFeature feature = cursor.nextFeature();
			//初始化公式变量
			double 月总租金=0,管理费率=0,结构重置价=0,维修费率=0,房屋成新度=0,保险费率=0,结构残置率=0,结构耐用年限=0,交易税费率=0,房屋还原率=0,房屋土地面=0,土地还原率=0,房屋建筑面=0;
			String 房屋结构="";
			double value = 0;
			while (feature != null) {
				//带入公式计算
				//([月租金]*12/[建筑面积]-(([月租金]*12)/[建筑面积]*{管理费率}+{[房屋结构]结构重置价}*{维修费率}+{[房屋结构]结构重置价}*[房屋成新度]*{保险费率}+{[房屋结构]结构重置价}*(1－{[房屋结构]结构残置率})/{[房屋结构]结构耐用年限}+([月租金]*12)/[建筑面积]*{交易税费率}+{[房屋结构]结构重置价}*[房屋成新度]*{房屋还原率})/{土地还原率}
				月总租金=Double.parseDouble((feature.getValue(getFieldsIdx("月总租金"))).toString());
				管理费率=getPPValue("管理费率");
				房屋结构=feature.getValue(getFieldsIdx("房屋结构")).toString();
				结构重置价=getPPValue(房屋结构+"结构重置价");
				维修费率=getPPValue("维修费率");
				房屋成新度=Double.parseDouble((feature.getValue(getFieldsIdx("房屋成新度"))).toString());
				保险费率=getPPValue("保险费率");
				结构残置率=getPPValue(房屋结构+"结构残置率");
				结构耐用年限=getPPValue(房屋结构+"结构耐用年限");
				交易税费率=getPPValue("交易税费率");
				房屋还原率=getPPValue("房屋还原率");
				房屋土地面=Double.parseDouble((feature.getValue(getFieldsIdx("房屋土地面"))).toString());
				//房屋建筑面=Double.parseDouble((feature.getValue(getFieldsIdx("房屋建筑面"))).toString());
				//土地还原率=getPPValue("土地还原率");
				value=月总租金*12/房屋土地面-(月总租金*12/房屋土地面*管理费率+结构重置价*维修费率+结构重置价*房屋成新度*保险费率+(结构重置价*(1-结构残置率))/结构耐用年限+月总租金*12/房屋土地面*交易税费率+结构重置价*房屋成新度*房屋还原率);
				//value=(月总租金*12/房屋建筑面-((月总租金*12)/房屋建筑面*管理费率+结构重置价*维修费率+结构重置价*房屋成新度*保险费率+(结构重置价*(1-结构残置率))/结构耐用年限+(月总租金*12)/房屋建筑面*交易税费率+结构重置价*房屋成新度*房屋还原率))/土地还原率;
				feature.setValue(vIdx, new Double(value));
				cursor.updateFeature(feature);
				feature = cursor.nextFeature();

			}
		
	}

}
