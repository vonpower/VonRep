package ynugis.geo;

import java.io.IOException;

import com.esri.arcgis.datasourcesraster.IRasterBand;
import com.esri.arcgis.datasourcesraster.IRasterBandCollection;
import com.esri.arcgis.datasourcesraster.IRasterBandCollectionProxy;
import com.esri.arcgis.datasourcesraster.IRasterStatistics;
import com.esri.arcgis.geodatabase.IPixelBlock;
import com.esri.arcgis.geodatabase.IRaster;
import com.esri.arcgis.geodatabase.IRasterCursor;
import com.esri.arcgis.interop.AutomationException;

public class Rester2TableRaster {
	private double[] rasterArray;

	private IRaster rr;

	private String[] rasterPer;

	private double cellSumNum;

	public Rester2TableRaster(IRaster ras) {
		super();
		// TODO Auto-generated constructor stub
		rr = ras;
	}

	public String[] getTableRaster() {
		rasterPer = new String[12];
		String strFieldName = "Value";
		cellSumNum = 0;
		try {
			IRasterBandCollection pRasBandCol = new IRasterBandCollectionProxy(
					rr);
			IRasterBand pRasBand = pRasBandCol.item(0);
			IRasterStatistics pRasStat = pRasBand.getStatistics();
			if (!pRasStat.isValid()) {
				pRasStat.recalculate();

			}
			double vMax = pRasStat.getMaximum();
			double vMin = pRasStat.getMinimum();
			if (vMax > 100) {
				rasterPer[11] = String.valueOf((int) vMax + 1);
			} else {
				rasterPer[11] = "101";
			}
			int classNum = (int) vMax % 10;
			if (classNum > 0) {
				classNum = (int) (vMax / 10) + 1;
			} else {
				classNum = (int) vMax / 10;
			}

			rasterArray = new double[11];
			
			try {
				int nodataCount = 0;
				int Count0_10 = 0;
				int Count10_20 = 0;
				int Count20_30 = 0;
				int Count30_40 = 0;
				int Count40_50 = 0;
				int Count50_60 = 0;
				int Count60_70 = 0;
				int Count70_80 = 0;
				int Count80_90 = 0;
				int Count90_100 = 0;
				int Count100_ = 0;
				int CountUnknown = 0;
				IRasterCursor rasterCur;
				rasterCur = rr.createCursor();
				
			do {
					IPixelBlock pixelBlock = rasterCur.getPixelBlock();
					int planeNum = pixelBlock.getPlanes();

					// System.out.println(planeNum);
					for (int u = 0; u < planeNum; u++) {
						for (int v = 0; v < pixelBlock.getWidth(); v++) {
							for (int w = 0; w < pixelBlock.getHeight(); w++) {
								double dblcellValue = Double.parseDouble(String
										.valueOf(pixelBlock.getVal(u, v, w)));
								
								  if(dblcellValue==-3.4028235E38) {
								  nodataCount++; }else
								  if(dblcellValue>=0&&dblcellValue<10) {
								  Count0_10++; } else
								  if(dblcellValue>=10&&dblcellValue<20) {
								  Count10_20++; } else
								  if(dblcellValue>=20&&dblcellValue<30) {
								  Count20_30++; } else
								  if(dblcellValue>=30&&dblcellValue<40) {
								  Count30_40++; } else
								  if(dblcellValue>=40&&dblcellValue<50) {
								  Count40_50++; } else
								  if(dblcellValue>=50&&dblcellValue<60) {
								  Count50_60++; } else
								  if(dblcellValue>=60&&dblcellValue<70) {
								  Count60_70++; } else
								  if(dblcellValue>=70&&dblcellValue<80) {
								  Count70_80++; } else
								  if(dblcellValue>=80&&dblcellValue<90) {
								  Count80_90++; }else
								  if(dblcellValue>=90&&dblcellValue<100) {
								  Count90_100++; }else if(dblcellValue>=100) {
								  Count100_++; }else { CountUnknown++; }
								  
								  
								 

								if (dblcellValue >= 0) {
									int intcell = (int) (dblcellValue / 10);
									if (dblcellValue >= 100) {
										rasterArray[10]++;

									} else {
										rasterArray[intcell]++;
									}
								}
							}
						}
					}
					

				}while (rasterCur.next());
				// 

				System.out.print("over!!!");
			} catch (AutomationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i = 0; i < 11; i++) {
				cellSumNum = cellSumNum + rasterArray[i];
			}
			for (int i = 0; i < 11; i++) {
				double doucellper = 10000 * (rasterArray[i] / cellSumNum);
				int intcellper = (int) Math.round(doucellper);
				double doucellper1 = (double) intcellper / 100;
				rasterPer[i] = String.valueOf(rasterArray[i]) + "-"
						+ String.valueOf(doucellper1);
			}
			return rasterPer;

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return null;
	}
}
