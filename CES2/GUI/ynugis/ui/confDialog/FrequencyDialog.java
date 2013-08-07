package ynugis.ui.confDialog;


import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import ynugis.application.CESCORE;
import ynugis.classifyBusiness.classifyItem.CellValueCMap;
import ynugis.geo.Rester2TableRaster;

import com.esri.arcgis.geodatabase.IRaster;

public class FrequencyDialog extends Dialog {

	protected Object result;

	protected Shell shell;
	private Canvas canvas;
	private GC gC;
	private IRaster rr;
	private boolean cellPerL50;
	private CellValueCMap cMap;
	private CESCORE sCore;
	/**
	 * Create the dialog
	 * @param parent
	 * @param style
	 */
	public FrequencyDialog(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * Create the dialog
	 * @param parent
	 */
	public FrequencyDialog(Shell parent,IRaster ras) {
		this(parent, SWT.NONE);
//		cMap=map;
//		
//		rr=cMap.getAsRaster();
		rr=ras;
	}

	/**
	 * Open the dialog
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return result;
	}

	/**DrawingGraph
	 * 
	 */
	 private void drawGraphs(){
		  	GC gc = new GC(canvas);
		  	gC=gc;
		  	final boolean cellValueL100;
		  	
		  	Rester2TableRaster  GetTable=new Rester2TableRaster(rr);
		  	String[] sRasterTable;
		  	sRasterTable=GetTable.getTableRaster();
		  	if(sRasterTable[11]!="101"){
		  		cellValueL100=true;
		  		
		  	}else{
		  		cellValueL100=false;
		  	}
//			  System.out.println(eveCellV);
			for(int i=0;i<sRasterTable.length-1;i++){
				String uu="-";
				int strPosition=sRasterTable[i].indexOf(uu);
				double cellPer=Double.parseDouble(sRasterTable[i].substring(strPosition+1,sRasterTable[i].length()));
				if(cellPer>50){
					cellPerL50=true;
					}else{
						cellPerL50=false;	
					}
			}
			
		
				gc.setLineWidth(2);
				gc.drawLine(30,300,370,300);
				gc.drawLine(365,297,370,300);
			gc.drawLine(365,303,370,300);
			gc.drawString("统计百分比(%)",20,20);
			gc.drawLine(30,30,30,300);
			gc.drawLine(27,35,30,30);
			gc.drawLine(33,35,30,30);
			gc.drawString("分值",370,300);
			if(cellValueL100){
				for(int i=0;i<11;i++){
				
//				g2.drawRect(50+i*intDuan,(int)250-20*(cellSum[i]/cellTol),intDuan-10,(int)20*(cellSum[i]/cellTol));
				
					gc.drawString(String.valueOf(10*i),30+30*i,305);
					
					gc.drawLine(30+30*i,300,30+30*i,295);
			
				}
				gc.drawString(sRasterTable[11],30+30*11,305);
			}else{
				for(int i=0;i<11;i++){
					
//					g2.drawRect(50+i*intDuan,(int)250-20*(cellSum[i]/cellTol),intDuan-10,(int)20*(cellSum[i]/cellTol));
					
						gc.drawString(String.valueOf(10*i),30+30*i,305);
						
						gc.drawLine(30+30*i,300,30+30*i,295);
				
					}
			}
			if(cellPerL50){
				for(int i=0;i<11;i++){
					gc.drawLine(30,300-25*i,35,300-25*i);
					gc.drawString(String.valueOf((int)(10*i)),10,300-25*i);
				}
			}else{
				for(int i=0;i<11;i++){
					gc.drawLine(30,300-25*i,35,300-25*i);
					gc.drawString(String.valueOf((int)(5*i)),10,300-25*i);
				}
			}
			if(cellValueL100){
				if(cellPerL50){
					for(int i=0;i<11;i++){
						String uu="-";
						int strPosition=sRasterTable[i].indexOf(uu);
						double topYY=2*Double.parseDouble(sRasterTable[i].substring(strPosition+1,sRasterTable[i].length()));
						int topY1=(int)topYY;

						gc.setLineWidth(1);
						Color color=new Color(null,new RGB(200,100,100));
						gc.setBackground(color);
						gc.fillRectangle(30+i*30+5,300-topY1,30-10,topY1);
	
				}
					
				}else{
					for(int i=0;i<11;i++){
						String uu="-";
						int strPosition=sRasterTable[i].indexOf(uu);
						double topYY=4*Double.parseDouble(sRasterTable[i].substring(strPosition+1,sRasterTable[i].length()));
						int topY1=(int)topYY;

						gc.setLineWidth(1);
						Color color=new Color(null,new RGB(200,100,100));
						gc.setBackground(color);
						gc.fillRectangle(30+i*30+5,300-topY1,30-10,topY1);
	
				}
					
				}
			}else{
				if(cellPerL50){
					for(int i=0;i<10;i++){
						String uu="-";
						int strPosition=sRasterTable[i].indexOf(uu);
						double topYY=2*Double.parseDouble(sRasterTable[i].substring(strPosition+1,sRasterTable[i].length()));
						int topY1=(int)topYY;

						gc.setLineWidth(1);
						Color color=new Color(null,new RGB(200,100,100));
						gc.setBackground(color);
						gc.fillRectangle(30+i*30+5,300-topY1,30-10,topY1);
	
				}
					
				}else{
					for(int i=0;i<10;i++){
						String uu="-";
						int strPosition=sRasterTable[i].indexOf(uu);
						double topYY=4*Double.parseDouble(sRasterTable[i].substring(strPosition+1,sRasterTable[i].length()));
						int topY1=(int)topYY;

						gc.setLineWidth(1);
						Color color=new Color(null,new RGB(200,100,100));
						gc.setBackground(color);
						gc.fillRectangle(30+i*30+5,300-topY1,30-10,topY1);
	
				}
					
				}
			}
	  }
	/**
	 * Create contents of the dialog
	 */
	protected void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setSize(430, 470);
		shell.setText("直方图");

		canvas = new Canvas(shell, SWT.NONE);
		canvas.setBounds(0, 0, 400, 350);
		canvas.addPaintListener(new PaintListener() {
		      public void paintControl(PaintEvent e) {
		    	  drawGraphs();

		      }
		});
		
		final Button button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				 shell.close();
			}
		});
		button.setText("确定");
		button.setBounds(76, 375, 65, 30);

		final Button jpgBtn = new Button(shell, SWT.NONE);
		jpgBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				
				 storePicture();
			}
		});
		jpgBtn.setText("保存");
		jpgBtn.setBounds(150, 375, 65, 30);
		
		final Button button_1 = new Button(shell, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				 shell.close();
			}
		});
		button_1.setText("取消");
		button_1.setBounds(231, 375, 65, 30);
		//
	}
	private void storePicture(){
		Rectangle rect=this.canvas.getClientArea();
		Image image=new Image(this.canvas.getDisplay(),rect.width,rect.height);
		this.gC.copyArea(image,0,0);
		ImageLoader loader=new ImageLoader();
		loader.data=new ImageData[]{image.getImageData()};
		FileDialog path=new FileDialog(this.shell,SWT.SAVE);
		path.setFileName("频率图");
		path.setFilterNames(new String[]{"JPEG(*.jpeg)"});
		path.setFilterExtensions(new String[]{"*.jpeg"});
		String pathStr=path.open();
		if (pathStr!=null) {
			loader.save(pathStr, SWT.IMAGE_JPEG);
			MessageDialog.openInformation(shell,"保存图片","操作完成："+pathStr);
		}
	}
}
