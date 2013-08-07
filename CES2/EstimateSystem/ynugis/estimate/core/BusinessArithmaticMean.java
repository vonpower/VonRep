
/**
 * @author yddy,create date 2003-1-12 
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.estimate.core;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import ynugis.estimate.core.calculator.VCalculator;
import ynugis.estimate.data.Revise;
import ynugis.estimate.data.RevisePair;

import com.esri.arcgis.geodatabase.DataStatistics;
import com.esri.arcgis.geodatabase.ICursorProxy;
import com.esri.arcgis.geodatabase.IDataStatistics;
import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureCursor;
import com.esri.arcgis.geodatabase.QueryFilter;
import com.esri.arcgis.interop.AutomationException;

public class BusinessArithmaticMean {
	private IFeatureClass featureClass;

	private Revise revise;

	// 0－－表示样点合格
	// 1--表示不合格
	private String condition = "=0";

	private String fldEligibility = FillSampleRank.checkFieldStr;

	private String fldLevel = "样点所在级";

	private String fldWin = VCalculator.VFieldName;

	private String roadName = "样点坐落";

	private int fieldId;

	public BusinessArithmaticMean(IFeatureClass features, Revise revise) {
		this.featureClass = features;
		this.revise = revise;
		// this.fldLevel=revise.getReviseFieldName();
	}

	/**
	 * 算术平均法求道路价
	 * 
	 * @return
	 * @throws AutomationException
	 * @throws IOException
	 */
	public HashMap getMeanByRoad() throws AutomationException, IOException {

		HashMap ret = new HashMap();
		Set roadNames=new HashSet();
		
		fieldId = featureClass.findField(roadName);
		if (fieldId == -1) {

			return null;
		}
		int count = featureClass.featureCount(null);
		IFeature feature;
		//String roadName = "";

		for (int i = 0; i < count; i++) {// 获取所有道路名
			feature = featureClass.getFeature(i);
			//roadName = feature.getValue(fieldId).toString();
			//ret.keySet().add(feature.getValue(fieldId));
			roadNames.add(feature.getValue(fieldId));
		}

		Object[] roadNameArray =roadNames.toArray();
		double temp = 0;
		// 以道路名称为依据计算样点平均值
		QueryFilter filter;
		IFeatureCursor cursor;
		IDataStatistics ds;
		String where;
		
		for (int i = 0; i < roadNameArray.length; i++) {

			where = roadName + " = '" + roadNameArray[i].toString()
					+ "' and " + fldEligibility + condition;
			filter = new QueryFilter();
			// filter.addField(fldLevel);
			filter.setWhereClause(where);
			cursor = featureClass.IFeatureClass_update(filter,true);
			ds = new DataStatistics();
			ds.setCursorByRef(new ICursorProxy(cursor));
			ds.setField(fldWin);
			temp = ds.getStatistics().getMean();
			ret.put(roadNameArray[i], new Double(temp));
		}
		return ret;
	}

	public double[] getMeanByLevel(String meanFiledName) throws Exception {
		fldWin = meanFiledName;
		return getMean();
	}

	public double[] getMean() throws Exception {
		fieldId = featureClass.findField(fldLevel);
		if (fieldId == -1) {
			return null;
		}
		RevisePair[] pairs = revise.getRevisePairs();

		double[] result = new double[pairs.length];
		double[] pairValue = new double[pairs.length];
		for (int i = 0; i < pairs.length; i++) {
			result[i] = subLevelMean(pairs[i]);
			pairValue[i] = pairs[i].getReviseValue();
		}
		modifyResult(pairValue, result);
		DecimalFormat format = new DecimalFormat("#.00");
		for (int i = 0; i < pairs.length; i++) {
			String formatStr = format.format(result[i]);
			result[i] = Double.parseDouble(formatStr);
		}
		return result;
	}

	private double subLevelMean(RevisePair pair) throws Exception {
		// String where=fldLevel+"="+pair.getReviseValue();
		String where = fldLevel + "=" + pair.getReviseValue() + " and "
				+ fldEligibility + condition;
		QueryFilter filter = new QueryFilter();
		// filter.addField(fldLevel);
		filter.setWhereClause(where);
		IFeatureCursor cursor = featureClass.IFeatureClass_update(filter, true);
		IDataStatistics ds = new DataStatistics();
		ds.setCursorByRef(new ICursorProxy(cursor));
		ds.setField(fldWin);
		return ds.getStatistics().getMean();
	}

	private void modifyResult(double[] pairs, double[] result) {
		for (int i = 0; i < pairs.length; i++) {
			if ((pairs[i] * 10) % 10 == 0) {
				int intIndex = i;
				double total = 0;
				for (int j = i + 1; j < pairs.length; j++) {
					if ((pairs[j] * 10) % 10 == 0 || j == pairs.length - 1) {
						int count = j - i - 1;
						i = j - 1;
						if (count == 0) {
							break;
						}
						System.out.println("total:" + total + " mean:"
								+ (total / count) + " count" + count + " into:"
								+ intIndex);
						result[intIndex] = total / count;
						break;
					} else if ((pairs[j] * 10) % 10 > 0) {
						total += result[j];
					}
				}
			}
		}
	}


}
