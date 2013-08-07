





import java.io.IOException;
import java.net.UnknownHostException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import ynugis.application.CESCORE;
import ynugis.classifyBusiness.classifyItem.CellValueCMap;
import ynugis.geo.Rester2TableRaster;

import com.esri.arcgis.carto.IMap;
import com.esri.arcgis.carto.IRasterLayer;
import com.esri.arcgis.datasourcesraster.RasterWorkspaceFactory;
import com.esri.arcgis.geoanalyst.INumberRemap;
import com.esri.arcgis.geoanalyst.IRasterAnalysisEnvironment;
import com.esri.arcgis.geoanalyst.IRasterAnalysisEnvironmentProxy;
import com.esri.arcgis.geoanalyst.IRasterDescriptor;
import com.esri.arcgis.geoanalyst.IReclassOp;
import com.esri.arcgis.geoanalyst.IRemap;
import com.esri.arcgis.geoanalyst.IRemapProxy;
import com.esri.arcgis.geoanalyst.NumberRemap;
import com.esri.arcgis.geoanalyst.RasterDescriptor;
import com.esri.arcgis.geoanalyst.RasterReclassOp;
import com.esri.arcgis.geodatabase.IGeoDataset;
import com.esri.arcgis.geodatabase.IGeoDatasetProxy;
import com.esri.arcgis.geodatabase.IRaster;
import com.esri.arcgis.geodatabase.IRasterProxy;
import com.esri.arcgis.geodatabase.IWorkspace;
import com.esri.arcgis.geodatabase.IWorkspaceFactory;

	/**
	 * This class demonstrates how to create your own dialog classes. It allows users
	 * to input a String
	 */
	public class rasterRenderDialog extends Dialog {
	  private String message;
	  private String input;
	  private IRaster rr;
	  private IRasterLayer rasLayer;
	  private int intCelVal[]=new int[11];
	  private double cellNum;
	  private Table table;
	  private List lst2;
	  private Text txt1;
	  private CellValueCMap cellValueCMap;
	  private CESCORE cESCORE;
	  private IMap map;
	  private IRaster pRas;
	  /**
	   * InputDialog constructor
	   * 
	   * @param parent the parent
	   */
//	  public rasterRenderDialog(Shell parent,CellValueCMap mapp,CESCORE core) {
//	    // Pass the default styles here
//	    this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
//	    cESCORE=core;
//	    cellValueCMap=mapp;
//	    rr=cellValueCMap.getAsRaster();
//	    try {
//			rr=rasLayer.getRaster();
//		} catch (AutomationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	  }
	  public rasterRenderDialog(Shell parent,IRaster ras) {
		    // Pass the default styles here
		    this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
//		    cESCORE=core;
//		    cellValueCMap=mapp;
//		    rr=cellValueCMap.getAsRaster();
//		    try {
//				rr=ras.getRaster();
//			} catch (AutomationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		    map=ras;
//			try {
//				rasLayer = new RasterLayer(ras.getLayer(0));
//				 rr=rasLayer.getRaster();;
//			} catch (AutomationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		    rr=ras;
		    
		  }

	  
	  
	  /**
	   * InputDialog constructor
	   * 
	   * @param parent the parent
	   * @param style the style
	   */
	  public rasterRenderDialog(Shell parent, int style) {
	    // Let users override the default styles
	    super(parent, style);
	    setText("栅格图分级着色");
	    setMessage("Please enter a value:");
	  }

	  /**
	   * Gets the message
	   * 
	   * @return String
	   */
	  public String getMessage() {
	    return message;
	  }

	  /**
	   * Sets the message
	   * 
	   * @param message the new message
	   */
	  public void setMessage(String message) {
	    this.message = message;
	  }

	  /**
	   * Gets the input
	   * 
	   * @return String
	   */
	  public String getInput() {
	    return input;
	  }

	  /**
	   * Sets the input
	   * 
	   * @param input the new input
	   */
	  public void setInput(String input) {
	    this.input = input;
	  }

	  /**
	   * Opens the dialog and returns the input
	   * 
	   * @return String
	   */
	  public IRaster open() {
	    // Create the dialog window
	    Shell shell = new Shell(getParent(), getStyle());
	    shell.setText(getText());
	    shell.setSize(300,450);
	    createContents(shell);
//	    shell.pack();
	    shell.open();
	    Display display = getParent().getDisplay();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch()) {
	        display.sleep();
	      }
	    }
	    // Return the entered value, or null
	    return pRas;
	  }
	  
	  private void startRender2(IRaster rast){
		  int numSize=lst2.getItemCount();
		  String sFieldNum ="Value";
		  try {
			IRasterDescriptor pRasDescriptor = new RasterDescriptor();
			pRasDescriptor.create(rr,null,sFieldNum);
			
			IReclassOp pReclassOp = new RasterReclassOp();
			IWorkspaceFactory pWSF = new RasterWorkspaceFactory();
			IWorkspace pWS = pWSF.openFromFile("c:\\temp", 0);
			IRasterAnalysisEnvironment pEnv=new IRasterAnalysisEnvironmentProxy(pReclassOp);
			pEnv.setOutWorkspaceByRef(pWS);
			INumberRemap pNumberRemap = new NumberRemap();
			for(int i=0;i<numSize;i++){
				String strlst2=lst2.getItem(i);
				System.out.println(strlst2);
				int num=strlst2.indexOf("-");
				double getMinValue=Double.parseDouble(strlst2.substring(0,num));
				double getMaxValue=Double.parseDouble(strlst2.substring(num+1,strlst2.length()));
				pNumberRemap.mapRange((int)getMinValue,(int)getMaxValue,(int)(getMinValue+getMaxValue)/2);
				
			}
			IGeoDataset pGeodataset=new IGeoDatasetProxy(pRasDescriptor);
			IRemap pReMap=new IRemapProxy(pNumberRemap);
			IGeoDataset pOutdataset = pReclassOp.reclassByRemap(pGeodataset, pReMap, false);
			pRas = new IRasterProxy(pOutdataset);
			
//			rasLayer.createFromRaster(pRas);
//			ILayer pLayer=new ILayerProxy(rasLayer);
////			map=cESCORE.getMap();
//			map.addLayer(pLayer);
			} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
	  private void startRender1(){
		  int numSize=lst2.getItemCount();
		  String sFieldNum ="Value";
		  try {
			IRasterDescriptor pRasDescriptor = new RasterDescriptor();
			pRasDescriptor.create(rr,null,sFieldNum);
			
			IReclassOp pReclassOp = new RasterReclassOp();
//			IWorkspaceFactory pWSF = new RasterWorkspaceFactory();
//			IWorkspace pWS = pWSF.openFromFile("c:\\temp", 0);
			IRasterAnalysisEnvironment pEnv=new IRasterAnalysisEnvironmentProxy(pReclassOp);
//			pEnv.setOutWorkspaceByRef(pWS);
			INumberRemap pNumberRemap = new NumberRemap();
			for(int i=0;i<numSize;i++){
				String strlst2=lst2.getItem(i);
				System.out.println(strlst2);
				int num=strlst2.indexOf("-");
				double getMinValue=Double.parseDouble(strlst2.substring(0,num));
				double getMaxValue=Double.parseDouble(strlst2.substring(num+1,strlst2.length()));
				pNumberRemap.mapRange((int)getMinValue,(int)getMaxValue,(int)(getMinValue+getMaxValue)/2);
				
			}
			IGeoDataset pGeodataset=new IGeoDatasetProxy(pRasDescriptor);
			IRemap pReMap=new IRemapProxy(pNumberRemap);
			IGeoDataset pOutdataset = pReclassOp.reclassByRemap(pGeodataset, pReMap, false);
			pRas = new IRasterProxy(pOutdataset);
			
//			rasLayer.createFromRaster(pRas);
			startRender2(pRas);
			} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
//	  private void startRender(){
//		  int numSize=lst2.getItemCount();
//		  System.out.println(numSize);
//
//			try {
//				IRasterBandCollection pBandCol=new IRasterBandCollectionProxy(rr);
//				IRasterBand pBand=pBandCol.item(0);
//				boolean TableExist[]=new boolean[1];
////				TableExist[0]=true;
//				pBand.hasTable(TableExist);
//				if(!TableExist[0]){
//					System.out.println("TableExist[0]是false的");
//					Rester2TableRaster tableRaster=new Rester2TableRaster(rr);
////					rr=tableRaster.getTableRaster(rr);
////					pBandCol=new IRasterBandCollectionProxy(rr);
//					pBand=pBandCol.item(0);
////					return;
//				}
//				ITable pTable=new ITableProxy(pBand.getAttributeTable());
//				int NumOfValues=pTable.rowCount(null);
//				System.out.println(NumOfValues);
//				
////				try {
////					IRasterDescriptor pRasDescriptor=new RasterDescriptor();
////					pRasDescriptor.create(ras,null,strFieldName);
////					IReclassOp pReclassOp=new RasterReclassOp();
////					IRasterAnalysisEnvironment pEnv=new IRasterAnalysisEnvironmentProxy(pReclassOp);
////					INumberRemap pNumberRemap=new NumberRemap();
////					pNumberRemap.mapRange(0,0.5,0);
////					
////					for(int i=0;i<classNum;i++){
////						if(i==0){
////						pNumberRemap.mapRange(0.5,(double)(i+1)*5,(int)(i+1)*5);
////						}else{
////						pNumberRemap.mapRange((double)i*5,(double)(i+1)*5,(int)(i+1)*5);
////						}
////					}
////					IGeoDataset geoDataset=new IGeoDatasetProxy(pRasDescriptor);
////					IRemap preMap=new IRemapProxy(pNumberRemap);
////					IGeoDataset pOutdata=pReclassOp.reclassByRemap(geoDataset,preMap,false);
////					IRaster pOutRaster=new IRasterProxy(pOutdata);
//				
//				int fieldIndex=pTable.findField("Value");
//				IRandomColorRamp pRamp=new RandomColorRamp();
//				pRamp.setSize(numSize);
//				pRamp.setSeed(100);
//				boolean pRampbool[]=new boolean[1];
//				pRampbool[0]=true;
//				pRamp.createRamp(pRampbool);
//				IRasterUniqueValueRenderer pUVRen=new RasterUniqueValueRenderer();
//				IRasterRenderer pRasRen=new IRasterRendererProxy(pUVRen);
//				pRasRen.setRasterByRef(rr);
//				pRasRen.update();
//				
//				pUVRen.setHeadingCount(1);
//				pUVRen.setClassCount(0,numSize);
//				for(int i=0;i<numSize;i++){
//					System.out.println("开始规类了");
//					pUVRen.setHeading(0,String.valueOf(i)+"类");
//
//					
//					String strlst2=lst2.getItem(i);
//					System.out.println(strlst2);
//					int num=strlst2.indexOf("-");
//					double getMinValue=Double.parseDouble(strlst2.substring(0,num));
//					double getMaxValue=Double.parseDouble(strlst2.substring(num+1,strlst2.length()));
//
//					pUVRen.setField("Value");
//					IRgbColor pColor=new RgbColor();
//					pColor.setBlue(255);
//					pColor.setGreen(255-i*40);
//					pColor.setRed(0+i*40);
////					pColor.setTransparency(getTranparentNum);
////					pColor.setRGB(100-i*25);
//					for(int j=0;j<NumOfValues;j++){
//						IRow pRow=pTable.getRow(j);
//						Object LabelValue=pRow.getValue(fieldIndex);
//						double cellValue=Double.parseDouble(String.valueOf(LabelValue));
//						
//						
//						if(cellValue<getMaxValue&&cellValue>=getMinValue){
//							pUVRen.addValue(0,i,LabelValue);
//							pUVRen.setLabel(0,i,String.valueOf(LabelValue));
////							pUVRen.setLabel(i,j,String.valueOf((getMinValue+getMaxValue)/2));
//							ISimpleFillSymbol pFSymbol=new SimpleFillSymbol();
//							pFSymbol.setColor(pColor);
//						
//							pUVRen.setSymbol(0,i,(ISymbol) pFSymbol);
//							
//						}
//
//					}
//
//				}
//				for(int i=0;i<numSize;i++){
//					System.out.println(pUVRen.getValueCount(0,i));
//				}
//				
//				pUVRen.setUseDefaultSymbol(false);
//				
//				pRasRen.update();
//				rasLayer=new RasterLayer();
//				rasLayer.setRendererByRef(pRasRen);
//				IMap pMap=cESCORE.getMapControl().getActiveView().getFocusMap();
//				pMap.addLayer(rasLayer);
//				cESCORE.getMapControl().getActiveView().refresh();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		




//	  }

	  /**
	   * Creates the dialog's contents
	   * 
	   * @param shell the dialog window
	   */
	  private void createContents(final Shell shell) {
//	    shell.setLayout(new GridLayout(2, true));

	    // Show the message
	    Label label = new Label(shell, SWT.NONE);
	    label.setText("栅格统计结果:");
	    label.setBounds(40,10,100,20);
	    table= new Table(shell,SWT.MULTI|SWT.FULL_SELECTION);
	    table.setHeaderVisible(true);
	    table.setLinesVisible(true);
	    table.setBounds(40,30,190,190);
	    TableColumn col1=new TableColumn(table,SWT.NONE);
	    col1.setText("作用分");
	    col1.setWidth(70);
	    TableColumn col2=new TableColumn(table,SWT.NONE);
	    col2.setText("单元个数");
	    col2.setWidth(60);
	    TableColumn col3=new TableColumn(table,SWT.NONE);
	    col3.setText("频率(%)");
	    col3.setWidth(60);

	    Button btn1=new Button(shell,SWT.NONE);
	    btn1.setText(">>");
	    btn1.setBounds(140,255,20,20);
	    Button btn2=new Button(shell,SWT.NONE);
	    btn2.setText("<<");
	    btn2.setBounds(140,280,20,20);
	    Label label2=new Label(shell,SWT.NONE);
	    label2.setText("设定得着色方案:");
	    label2.setBounds(170,225,100,20);
	    lst2=new List(shell,SWT.NONE);
	    lst2.setBounds(170,245,100,60);
	    
	    getStaticVal();
//	   
	    
	    Label label3=new Label(shell,SWT.NONE);
	    label3.setText("输入要着色的范围:");
	    label3.setBounds(30,250,100,20);
	    txt1=new Text(shell,SWT.NONE);
	    txt1.setBounds(30,270,100,20);
	    txt1.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				boolean b = ("0123456789-".indexOf(e.text) >= 0);
				e.doit = b;
			}
		});
	    Button ok = new Button(shell, SWT.PUSH);
	    ok.setText("开始着色");
	    ok.setBounds(80,375,40,20);
	    ok.setText("OK");
//	    data = new GridData(GridData.FILL_HORIZONTAL);
//	    ok.setLayoutData(data);
	    btn1.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent event) {
		        input = txt1.getText();
		        int TextPosition1=input.indexOf("-");
		        double douLlatestInput=Double.parseDouble(input.substring(0,TextPosition1));
		        int lstcount=lst2.getItemCount();
		        if(lstcount>0){
		        	String strLstText=lst2.getItem(lst2.getItemCount()-1);
		        	int TextPosition=strLstText.indexOf("-");
		        	double douLastInput=Double.parseDouble(strLstText.substring(TextPosition+1,strLstText.length()));
		        	if(douLlatestInput>douLastInput){
		        		MessageDialog.openInformation(null,"输入错误","输入定级范围有重复，请重新输入");
		        		txt1.setText("");
		        		return;
		        	}
		        }
		        lst2.add(input);
		        txt1.setText("");
		      }
		    });
	    btn2.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent event) {
		        int intlst2index=lst2.getFocusIndex();
		        lst2.remove(intlst2index);
		      }
		    });
	    ok.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent event) {
	    	startRender1();
	    	
	        shell.close();
	      }
	    });

	    // Create the cancel button and add a handler
	    // so that pressing it will set input to null
	    Button cancel = new Button(shell, SWT.PUSH);
	    cancel.setText("取消");
	    cancel.setBounds(180,375,40,20);
//	    data = new GridData(GridData.FILL_HORIZONTAL);
//	    cancel.setLayoutData(data);
	    cancel.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent event) {
	        input = null;
	        shell.close();
	      }
	    });

	    // Set the OK button as the default, so
	    // user can type input and press Enter
	    // to dismiss
	    shell.setDefaultButton(ok);
	  }
	  public void  getStaticVal(){
//		  IRasterCursor rasterCur;
//		  try {
//			  
//			rasterCur = rr.createCursor();
//			do{
//				IPixelBlock pixelBlock=rasterCur.getPixelBlock();
//	  		  int planeNum=pixelBlock.getPlanes();
//	  		  cellNum=planeNum*pixelBlock.getHeight()*pixelBlock.getWidth();
////	  		  System.out.println(planeNum);
//	  		  for(int u=0;u<planeNum;u++){
//	  			  for(int v=0;v<pixelBlock.getWidth();v++){
//	  				  for(int w=0;w<pixelBlock.getHeight();w++){
//	  					double dblcellValue=Double.parseDouble(String.valueOf(pixelBlock.getVal(u,v,w)));
////	  					
//	  					if(dblcellValue>0){
//	  						int intcell=(int) (dblcellValue/10);
////	  					
//	  					if(intcell>10){
//	  						intCelVal[10]++;
////	  				
//	  					}else if(intcell==10){
//	  						intCelVal[9]++;
//	  					}else{
//	  						intCelVal[intcell]++;
//	  					}
//	  					}else{
//	  						cellNum--;
//	  					}
//	  					
//	  				  }
//	  			  } 
//	  		 
//	  		  }
//	  		  rasterCur.next();
//			}while(rasterCur.next());
//		} catch (AutomationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		  	IRasterBandCollection pBandCol;
//			try {
//				pBandCol = new IRasterBandCollectionProxy(rr);
//				IRasterBand pBand=pBandCol.item(0);
//				boolean TableExist[]=new boolean[1];
////				TableExist[0]=true;
//				pBand.hasTable(TableExist);
				String[]rasterArray;
//				if(!TableExist[0]){
					
					Rester2TableRaster tableRaster=new Rester2TableRaster(rr);
					rasterArray=tableRaster.getTableRaster();
					for(int i=0;i<11;i++){
						String uu="-";
						int strPosition=rasterArray[i].indexOf(uu);
						cellNum=cellNum+Double.parseDouble(rasterArray[i].substring(0,strPosition-1));
						TableItem item=new TableItem(table,0);
						if(i==0){
							item.setText(0,String.valueOf(i)+"--"+String.valueOf(i+1)+"0");
							}else if(i<10){
								item.setText(0,String.valueOf(i)+"0--"+String.valueOf(i+1)+"0");
							}else{
								item.setText(0,"100"+"-"+rasterArray[11]);
							}
						item.setText(1,rasterArray[i].substring(0,strPosition-1));
						item.setText(2,rasterArray[i].substring(strPosition+1,rasterArray[i].length()));
						
					}
					TableItem item= new TableItem(table,0) ;
					item.setText(0,"合计");
					item.setText(1,String.valueOf(cellNum));
					item.setText(2,"100");
				}
			
			
			
			
		 
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
		
		
		
	  }
//	} 


