package ynugis.classifyBusiness.classifyItem;

import java.io.IOException;

import com.esri.arcgis.datasourcesraster.IRasterBand;
import com.esri.arcgis.datasourcesraster.IRasterBandCollection;
import com.esri.arcgis.datasourcesraster.IRasterBandCollectionProxy;
import com.esri.arcgis.datasourcesraster.IRasterStatistics;
import com.esri.arcgis.geodatabase.IPixelBlock;
import com.esri.arcgis.geodatabase.IRaster;
import com.esri.arcgis.geodatabase.IRasterCursor;
import com.esri.arcgis.interop.AutomationException;

public class fromResterGetTableRaster {
	private double[] rasterArray;
	private IRaster rr;
	private String[] rasterPer;
	private double cellSumNum;
	public fromResterGetTableRaster(IRaster ras) {
		super();
		// TODO Auto-generated constructor stub
		rr=ras;
	}
	
	public String[] getTableRaster(){
		rasterPer=new String[12];
		String strFieldName="Value";
		cellSumNum=0;
		try {
			IRasterBandCollection pRasBandCol=new IRasterBandCollectionProxy(rr);
			IRasterBand pRasBand=pRasBandCol.item(0);
			IRasterStatistics pRasStat=pRasBand.getStatistics();
			if(!pRasStat.isValid()){
				pRasStat.recalculate();
				
			}
			double vMax=pRasStat.getMaximum();
			double vMin=pRasStat.getMinimum();
			if(vMax>100){
				rasterPer[11]=String.valueOf((int)vMax+1);
			}else{
				rasterPer[11]="101";
			}
			int classNum=(int)vMax%10;
			if(classNum>0){
				classNum=(int)(vMax/10)+1;
			}else{
				classNum=(int)vMax/10;
			}
			
			rasterArray=new double[11]; 
			IRasterCursor rasterCur;
			  try {
				  
				rasterCur = rr.createCursor();
				do{
					IPixelBlock pixelBlock=rasterCur.getPixelBlock();
		  		  int planeNum=pixelBlock.getPlanes();
		  		 
//		  		  System.out.println(planeNum);
		  		  for(int u=0;u<planeNum;u++){
		  			  for(int v=0;v<pixelBlock.getWidth();v++){
		  				  for(int w=0;w<pixelBlock.getHeight();w++){
		  					double dblcellValue=Double.parseDouble(String.valueOf(pixelBlock.getVal(u,v,w)));
//		  					
		  					if(dblcellValue>0){
		  						int intcell=(int) (dblcellValue/10);
		  						if(dblcellValue>=100){
	  						    rasterArray[10]++;
	  						    
		  						}else{
		  							rasterArray[intcell]++;
		  						}
		  					}
		  				  }
		  			  } 
		  		 
		  		  }
		  		  rasterCur.next();
				}while(rasterCur.next());
			} catch (AutomationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i=0;i<11;i++){
				cellSumNum=cellSumNum+rasterArray[i];
			}
			for(int i=0;i<11;i++ ){
				double doucellper=10000*(rasterArray[i]/cellSumNum);
				int intcellper=(int)Math.round(doucellper);
				double doucellper1=(double)intcellper/100;
				rasterPer[i]=String.valueOf(rasterArray[i])+"-"+String.valueOf(doucellper1);
			}
			return rasterPer;
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		return null;
	}
}
