package ynugis.classifyBusiness.classifyItem;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import ynugis.geo.Rester2TableRaster;

import com.esri.arcgis.geodatabase.IRaster;
/**
 * This class demonstrates how to create your own dialog classes. It allows users
 * to input a String
 */
public class EijStatisticsDialog extends Dialog {
  private String message;
  private String input;
  private IRaster rr;
  private String[] strVal;
  private int intCelVal[]=new int[11];
  private double cellNum;
  private Table table;
  /**
   * InputDialog constructor
   * 
   * @param parent the parent
   */
  public EijStatisticsDialog (Shell parent,IRaster ras) {
    // Pass the default styles here
    this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
    rr=ras;
  }

  /**
   * InputDialog constructor
   * 
   * @param parent the parent
   * @param style the style
   */
  public EijStatisticsDialog (Shell parent, int style) {
    // Let users override the default styles
    super(parent, style);
    setText("作用分统计表");
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
  public String open() {
    // Create the dialog window
    Shell shell = new Shell(getParent(), getStyle());
    shell.setText(getText());
    shell.setSize(260,320);
    createContents(shell);
//    shell.pack();
    shell.open();
    Display display = getParent().getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    // Return the entered value, or null
    return input;
  }
  public void  getStaticVal(){
//	  IRasterCursor rasterCur;
//	  try {
//		rasterCur = rr.createCursor();
//		do{
//			IPixelBlock pixelBlock=rasterCur.getPixelBlock();
//  		  int planeNum=pixelBlock.getPlanes();
////  		  System.out.println(cellNum);
////  		  System.out.println(planeNum*pixelBlock.getWidth()*pixelBlock.getHeight());
//  		  for(int u=0;u<planeNum;u++){
//  			  for(int v=0;v<pixelBlock.getWidth();v++){
//  				  for(int w=0;w<pixelBlock.getHeight();w++){
//  					  double dblcellValue=Double.parseDouble(String.valueOf(pixelBlock.getVal(u,v,w)));
////  					System.out.println("dblcellValue:"+dblcellValue);
//  					if(dblcellValue>=0){
//  						cellNum++;
//  						int intcell=(int) (dblcellValue/10);
////  					System.out.println(intcell);
//  					if(intcell>10){
//  						intCelVal[10]++;
////  						MessageDialog.openError(new Shell(),"统计分值表出错","获取的分值有大于100的单元格!");
////  						MessageDialog.openInformation(shell,"统计分值表出错","获取的分值有大于100的单元格!");
//  					}else{
//  						intCelVal[intcell]++;
//  					}
//  					}
////  					int intcell=(int) (dblcellValue/10);
//////  					System.out.println(intcell);
////  					if(intcell>10){
////  						intCelVal[10]++;
//////  						MessageDialog.openError(new Shell(),"统计分值表出错","获取的分值有大于100的单元格!");
//////  						MessageDialog.openInformation(shell,"统计分值表出错","获取的分值有大于100的单元格!");
////  					}else if(intcell==10){
////  						intCelVal[9]++;
////  					}else{
////  						intCelVal[intcell]++;
////  					}
////  					System.out.println(intcell);
////  					intCelVal[intcell]++;
//  					
//  				  }
//  			  } 
//  		 
//  		  }
//  		  rasterCur.next();
//		}while(rasterCur.next());
//	} catch (AutomationException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	for(int i=0;i<11;i++){
//		double doucellper=10000*(intCelVal[i]/cellNum);
//		int intcellper=(int)Math.round(doucellper);
////		System.out.println(intcellper);
//		double doucellper1=(double)intcellper/100;
////		System.out.println(doucellper1);
//		TableItem item=new TableItem(table,0);
//		if(i==0){
//			item.setText(0,String.valueOf(i)+"--"+String.valueOf(i+1)+"0");
//			}else if(i<10){
//				item.setText(0,String.valueOf(i)+"0--"+String.valueOf(i+1)+"0");
//			}else{
//				item.setText(0,">100");
//			}
//		item.setText(1,String.valueOf(intCelVal[i]));
//		item.setText(2,String.valueOf(doucellper1));
//		
//	}
//	TableItem item= new TableItem(table,0) ;
//	item.setText(0,"合计");
//	item.setText(1,String.valueOf(cellNum));
//	item.setText(2,"100");
//	
	  String[]rasterArray;
//		if(!TableExist[0]){
			
	  Rester2TableRaster tableRaster = new Rester2TableRaster(rr);
		rasterArray = tableRaster.getTableRaster();	
	  
	  //fromResterGetTableRaster tableRaster=new fromResterGetTableRaster(rr);
		//	rasterArray=tableRaster.getTableRaster();
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
  /**
   * Creates the dialog's contents
   * 
   * @param shell the dialog window
   */
  private void createContents(final Shell shell) {
	Label lbShow=new Label(shell,SWT.NONE);
	lbShow.setText("作用分统计表:");
	lbShow.setBounds(30,30,100,20);
    table= new Table(shell,SWT.MULTI|SWT.FULL_SELECTION);
    table.setHeaderVisible(true);
    table.setLinesVisible(true);
    table.setBounds(30,50,190,200);
    TableColumn col1=new TableColumn(table,SWT.NONE);
    col1.setText("作用分");
    col1.setWidth(70);
    TableColumn col2=new TableColumn(table,SWT.NONE);
    col2.setText("单元个数");
    col2.setWidth(60);
    TableColumn col3=new TableColumn(table,SWT.NONE);
    col3.setText("频率(%)");
    col3.setWidth(60);
    Button ok = new Button(shell, SWT.PUSH);
    ok.setText("保存");
    ok.setBounds(60,260,50,20);
    getStaticVal();
    ok.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
    	FileDialog saveDialog=new FileDialog(shell,SWT.SAVE);
  		saveDialog.setFilterExtensions(new String[]{"*.xls"});
  		saveDialog.setFileName("");
  		saveDialog.setText("保存作用分统计表");
  		String dir=saveDialog.open();
  		if(dir!=null){
  			saveFile(dir);
  			shell.close();
  		}else{
  			MessageDialog.openError(shell,"保存出错","文件名不存在!");
  		}
//        shell.close();
      }
    });

    // Create the cancel button and add a handler
    // so that pressing it will set input to null
    Button cancel = new Button(shell, SWT.PUSH);
    cancel.setText("取消");
    cancel.setBounds(150,260,50,20);
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
  private void saveFile(String path){
//	  String filePath=GT.getFilePath(path);
//	  String fileName=GT.getFileName(path);
	  HSSFWorkbook wb=new HSSFWorkbook();
	  HSSFSheet sheet=wb.createSheet("sheet 1");
	  sheet.setDefaultColumnWidth((short)10);
	  HSSFRow row=sheet.createRow((short)0);
	  for(int i=0;i<3;i++){
		  HSSFCell cell=row.createCell((short)i);
		  cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		  if(i==0){
			  cell.setCellValue("作用分");
		  }else if(i==1){
			  cell.setCellValue("单元个数");
		  }else{
			  cell.setCellValue("频率(%)");
		  }
	  }
	  for(int i=0;i<12;i++){
		  HSSFRow row1=sheet.createRow((short)(i+1));
		  for(int j=0;j<3;j++){
			  HSSFCell cell=row1.createCell((short)j);
//			  TableColumn tableColum=table.getColumn(i);
			  cell.setEncoding(HSSFCell.ENCODING_UTF_16);
//			  cell.setCellValue(table.getItem(i).getText(j));
			  if(j==0){
				  cell.setCellValue(table.getItem(i).getText(j)); 
			  }else{
			  cell.setCellValue(Double.parseDouble(table.getItem(i).getText(j)));
			  }
		  }
	  }
	try {
		FileOutputStream fileOut=new FileOutputStream(path);
		try {
			wb.write(fileOut);
			fileOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}  
 
 
