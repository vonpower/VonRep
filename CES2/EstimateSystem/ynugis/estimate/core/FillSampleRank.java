package ynugis.estimate.core;

import java.io.IOException;

import ynugis.estimate.data.Revise;
import ynugis.estimate.data.RevisePair;

import com.esri.arcgis.geodatabase.DataStatistics;
import com.esri.arcgis.geodatabase.Field;
import com.esri.arcgis.geodatabase.ICursorProxy;
import com.esri.arcgis.geodatabase.IDataStatistics;
import com.esri.arcgis.geodatabase.IDataset;
import com.esri.arcgis.geodatabase.IDatasetProxy;
import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureCursor;
import com.esri.arcgis.geodatabase.IFieldEdit;
import com.esri.arcgis.geodatabase.IFieldEditProxy;
import com.esri.arcgis.geodatabase.IQueryFilter;
import com.esri.arcgis.geodatabase.ISpatialFilter;
import com.esri.arcgis.geodatabase.IWorkspace;
import com.esri.arcgis.geodatabase.IWorkspaceEdit;
import com.esri.arcgis.geodatabase.IWorkspaceEditProxy;
import com.esri.arcgis.geodatabase.QueryFilter;
import com.esri.arcgis.geodatabase.SpatialFilter;
import com.esri.arcgis.geodatabase.esriFieldType;
import com.esri.arcgis.geodatabase.esriSpatialRelEnum;
import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.system.IStatisticsResults;

public class FillSampleRank {
	static public String checkFieldStr="样点是否合";
	public double A;

	public double r;

	public double R;
	
	public double a;
	
	public double b;
	/**
	 * 
	 * this pragram is useed fill a field in the featureclass
	 * 
	 * @param fc
	 */

	public void fillFields(IFeatureClass RankFC, IFeatureClass SampleFC,
			Revise inClassify) {
		/*featCls1 = RankFC;
		featCls2 = SampleFC;
		classify = inClassify;*/
		inClassify.tidy();
		RevisePair[] rp = inClassify.getRevisePairs();
		int ArrayLength = rp.length;

		try {

			IDataset pDataset = new IDatasetProxy(SampleFC);
			IWorkspace pWorkspace = pDataset.getWorkspace();
			IWorkspaceEdit pWorkspaceEdit = new IWorkspaceEditProxy(pWorkspace);
			pWorkspaceEdit.startEditing(true);
			pWorkspaceEdit.startEditOperation();

			IFeatureCursor featCur = SampleFC.IFeatureClass_update(null, false);
			int SampleRankIndex = SampleFC.findField("样点所在级");
			IFeature feature = featCur.nextFeature();

			while (feature != null) {
				ISpatialFilter spatialFilter = new SpatialFilter();
				spatialFilter.setGeometryByRef(feature.getShape());
				spatialFilter.setGeometryField("SHAPE");
				spatialFilter
						.setSpatialRel(esriSpatialRelEnum.esriSpatialRelWithin);
				IFeatureCursor featCur1 = RankFC.search(spatialFilter, false);

				int GRIDCODEIndex = RankFC.findField("GRIDCODE");
				IFeature feature1 = featCur1.nextFeature();
				Object obRank = null;
				while (feature1 != null) {
					obRank = feature1.getValue(GRIDCODEIndex);

					feature1 = featCur1.nextFeature();
				}
				
				Object CellRank = null;
				if (obRank != null) {
					double cellValue = Double.parseDouble(obRank.toString());
					for (int j = 0; j < ArrayLength; j++) {
						if (cellValue <= rp[j].getEnd()
								&& cellValue > rp[j].getStart()) {
							double celldouRank = rp[j].getReviseValue();
							CellRank = new Double(celldouRank);
						}
					}
					//						
				}
				//						
				if (CellRank != null) {
					feature.setValue(SampleRankIndex, CellRank);
				} else {
					feature.setValue(SampleRankIndex, new Double(0));
				}
				featCur.updateFeature(feature);
				//						
				feature = featCur.nextFeature();
				//System.out.println(i++);
			}
			//				
			pWorkspaceEdit.stopEditOperation();
			pWorkspaceEdit.stopEditing(true);
			// System.out.println("线程已结束!");
		} catch (AutomationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void checkRank(IFeatureClass SampleFC, String fieldName,
			Revise inClassify, int MultSD) {
		IFeatureClass featCls1=SampleFC;
		inClassify.tidy();
		RevisePair[] rp= inClassify.getRevisePairs();
		int ArrayLength = rp.length;
		//featCls1 = SampleFC;
		double douRank;
		double intRank;
		
		for (int i = 0; i < ArrayLength; i++) {
			
			/*checkSample(fieldName, rp[i].getReviseValue(), multiSTD);
			System.out.println("运行的循环次数:" + (i + 1));*/
			douRank=rp[i].getReviseValue();
			intRank=douRank;
			try {
				IDataset pDataset = new IDatasetProxy(featCls1);
				IWorkspace pWorkspace = pDataset.getWorkspace();
				IWorkspaceEdit pWorkspaceEdit = new IWorkspaceEditProxy(
						pWorkspace);
				pWorkspaceEdit.startEditing(true);
				pWorkspaceEdit.startEditOperation();
				IQueryFilter pQFilter = new QueryFilter();
				if ((int) douRank < intRank) {
					pQFilter.setWhereClause("样点所在级=" + intRank);
				} else {
					pQFilter.setWhereClause("样点所在级>=" + (int) intRank
							+ " AND " + "样点所在级<" + (int) (intRank + 1));
				}
				IDataStatistics pData = new DataStatistics();
				IFeatureCursor pfeatCur = featCls1.search(pQFilter, false);
				pData.setCursorByRef(new ICursorProxy(pfeatCur));
				pData.setField(fieldName);
				IStatisticsResults pStatResults = pData.getStatistics();
				double douMean = pStatResults.getMean();
				double douSTDE = pStatResults.getStandardDeviation();
				int fieldIndex = featCls1.findField(fieldName);
				int SampleFitnessIndex = getCheckFieldIdx(SampleFC);
				
				pfeatCur = featCls1.IFeatureClass_update(pQFilter, false);
				IFeature feature = pfeatCur.nextFeature();
				while (feature != null) {
					double douperRankValue = Double.parseDouble(feature
							.getValue(fieldIndex).toString());
					if (douperRankValue >= douMean + MultSD * douSTDE
							|| douperRankValue <= douMean - MultSD
									* douSTDE) {
						//HARDCODE: 0 mean pass,all pass!!!
						feature.setValue(SampleFitnessIndex, new Double(0));
					} else {
						feature.setValue(SampleFitnessIndex, new Double(0));
					}
					pfeatCur.updateFeature(feature);
					feature = pfeatCur.nextFeature();
				}
				pWorkspaceEdit.stopEditOperation();
				pWorkspaceEdit.stopEditing(true);
				System.out.println("线程已结束！");
			} catch (AutomationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		

		}
		System.out.println("样本点检验结束!!");
	}

	/**
	 * 将所有样本点置为合格
	 */
	public void allPassed(IFeatureClass featCls1)
	{
		
			int SampleFitnessIndex;
			try {
				SampleFitnessIndex = featCls1.findField(checkFieldStr);
				if(SampleFitnessIndex==-1)
				{
					getCheckFieldIdx(featCls1);
					return ;
				}
				featCls1.deleteField(featCls1.getFields().getField(SampleFitnessIndex));
				getCheckFieldIdx(featCls1);
				
			} catch (AutomationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	public int getCheckFieldIdx(IFeatureClass fc) throws AutomationException, IOException
	{
		int vIdx = fc.getFields().findField(checkFieldStr);
		if (vIdx == -1) {
			Field field = new Field();
			IFieldEdit edit = new IFieldEditProxy(field);
			edit.setAliasName(checkFieldStr);
			edit.setName(checkFieldStr);
			edit.setType(esriFieldType.esriFieldTypeDouble);
			fc.addField(field);
			vIdx = fc.findField(checkFieldStr);

		}
		return vIdx;
		
	}
	public  double logg(double Value, double Base) {
		System.out.println(Math.log(Base));
		return Math.log(Value) / Math.log(Base);
		
	}

	public void getARPar(double[] Xin, double[] Yin,int rankCount) {
		double[] ARModel = new double[3];

		int n = Xin.length;
		//int n=rankCount;
		
//>>>>>>> 1.25//
		double sumXLogY = 0;
		double sumX = 0;
		double sumXX = 0;
		double sumLogY = 0;
		double sumLogYY = 0;
//<<<<<<< FillSampleRank.java
//		for (int i = 0; i < Xin.length; i++) {
//=======
		double LogY=0;
		for (int i = 0; i < Yin.length; i++) {
//>>>>>>> 1.25
			if (Yin[i] == 0)
				continue;
			LogY = logg(Yin[i], 10);
			sumLogY = LogY+ sumLogY;
			sumLogYY = logg((Yin[i] * Yin[i]), 10) + sumLogYY;
			sumX = sumX + Xin[i];
			sumXX = sumXX + Xin[i] * Xin[i];
			sumXLogY = sumXLogY + Xin[i] * LogY;
			
		}

		double bV = (n * sumXLogY - sumX * sumLogY) / (n * sumXX - sumX * sumX);
		double aV = (sumLogY - bV * sumX) / n;
		double RN = (n * sumXLogY - sumX * sumLogY);
		// double RD = Math
		// .sqrt(((n * sumXX - sumX * sumX) * (n * sumLogYY - sumLogY
		// * sumLogY)));
		double RD = Math
				.sqrt((n * sumXX - sumX * sumX) * (n * sumLogYY - sumLogYY));

		
		ARModel[0] = Math.pow(10, aV);
		ARModel[1] = Math.pow(10, bV) - 1;
		ARModel[2] = RN / RD;
		A = ARModel[0];
		r = ARModel[1];
		R = ARModel[2];
		a=aV;
		b=bV;
		

	}

}
