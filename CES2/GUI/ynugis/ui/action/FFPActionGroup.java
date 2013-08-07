/**
 * @author yddy,create date 2003-11-8 
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.action;




import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import ynugis.application.CESCORE;
import ynugis.classifyBusiness.ClassifyProject;
import ynugis.classifyBusiness.classifyItem.CMap;
import ynugis.classifyBusiness.classifyItem.ObstructCMap;
import ynugis.classifyBusiness.classifyItem.RangeCMap;
import ynugis.classifyBusiness.classifyItem.RankCMap;
import ynugis.ffsystem.BasicFacet;
import ynugis.ffsystem.BasicFactor;
import ynugis.ffsystem.CalculatorInfo;
import ynugis.ffsystem.DerivedFacet;
import ynugis.ffsystem.FFItem;
import ynugis.ffsystem.FFSystem;
import ynugis.ffsystem.FFTable;
import ynugis.ffsystem.FactorCatalog;
import ynugis.thread.FFTCalcu;
import ynugis.thread.LoadGroupLayer;
import ynugis.ui.confDialog.BrowerDialog;
import ynugis.ui.confDialog.NodeDialog;
import ynugis.ui.confDialog.RRDialog;
import ynugis.ui.confDialog.classify;

import com.esri.arcgis.carto.IRasterLayer;
import com.esri.arcgis.carto.RasterLayer;
import com.esri.arcgis.geoanalyst.IRasterAnalysisEnvironment;
import com.esri.arcgis.geodatabase.IRaster;


public class FFPActionGroup {
	private final int ACTION_NUM=21;
	//计算后&table元素&可以使用的功能
	private final int[] enableTable=new int[]{0,1,2,3,5,6,7,8,9,10,11,12,15,16,18,19,20};
    //计算后&catalog元素&可以使用的功能
	private final int[] enableCata=new int[]{0,1,2,3,5,6,7,8,9,11,12,18,19,20};
    //计算后&factor元素&可以使用的功能
	private final int[] enableFactor=new int[]{0,1,2,3,4,5,6,7,8,9,11,12,13,14,17,18,19,20};
    //计算后&facet元素&可以使用的功能
	private final int[] enableFacet=new int[]{0,1,2,3,5,6,7,8,9,11,12,18,19,20};
	//计算前&能够计算时&可以使用的功能
	private final int[] commonTrue=new int[]{0,6,20};
	//计算前&不能计算时&可以使用的功能
	private final int[] commonFalse=new int[]{6,20};
	private Tree fftree;
	private Shell shell;
	private CESCORE cescore;
	private ClassifyProject classifyProject;
	private Action[] itemActions;

	public FFPActionGroup(Shell myshell,CESCORE core,Tree tree) {
		shell=myshell;
		cescore=core;
		fftree=tree;
		itemActions=new Action[ACTION_NUM];
		itemActions[0]=new caculateAction();
		itemActions[1]=new functionValueAction();
		itemActions[2]=new cellValueAction();
		itemActions[3]=new freqenceAction();
		itemActions[4]=new factorValueAction();
		itemActions[5]=new classificationAction();
		itemActions[6]=new propertyAction();
		itemActions[7]=new save2ShapefileAction();
		itemActions[8]=new save2MifAction();
		itemActions[9]=new exportStatisticAction();
		itemActions[10]=new correctAction();
		
		itemActions[11]=new save2FreqAction();
		itemActions[12]=new saveAs2FreqAction();
		itemActions[13]=new save2FactorAction();
		itemActions[14]=new saveAs2FactorAction();
		itemActions[15]=new save2ClassifyAction();
		itemActions[16]=new saveAs2ClassifyAction();
		
		itemActions[17]=new excel2FactorAction();
		/*
		 * added ext
		 */
		itemActions[18]=new cellValueColor();
		itemActions[19]=new save2TabAction();
		itemActions[20]=new browserFFTAction();
	}
	public void contextMenu() {
		MenuManager mm =new MenuManager();
		
		MenuManager function =new MenuManager("作用分统计");
		function.add(itemActions[1]);
		function.add(itemActions[9]);
		
		MenuManager cell =new MenuManager("单元格分值图");
		cell.add(itemActions[2]);
		cell.add(itemActions[7]);
		cell.add(itemActions[8]);
		cell.add(itemActions[19]);
		
		MenuManager freq =new MenuManager("频率图");
		freq.add(itemActions[3]);
		//freq.add(itemActions[11]);
		//freq.add(itemActions[12]);
		
		MenuManager factor =new MenuManager("因素作用分值图");
		//factor.add(itemActions[4]);
		
		//将等值线图功能换为土地级别图
		//factor.add(itemActions[18]);
		  factor.add(itemActions[5]);
		  
		//factor.add(itemActions[13]);
		//factor.add(itemActions[14]);
		//factor.add(itemActions[17]);
		
		MenuManager classify =new MenuManager("初步土地级别图");
		classify.add(itemActions[5]);
		//classify.add(itemActions[15]);
//		classify.add(itemActions[16]);
		//classify.add(itemActions[10]);
		
		
		mm.add(itemActions[0]);
		mm.add(new Separator());
		mm.add(function);
		mm.add(cell);
		mm.add(freq);
		mm.add(factor);
		mm.add(classify);
		mm.add(new Separator());
		mm.add(itemActions[6]);
		mm.add(itemActions[20]);
		
		Menu m = mm.createContextMenu(fftree);
		fftree.setMenu(m);
		fftree.addMouseListener(new MouseAdapter(){
			public void mouseDown(MouseEvent e) {
				System.out.println("Mouse Down");
				if(classifyProject==null){
					int proNum=cescore.getCEManager().getCurrentProjectCount();
					if(proNum==0){
						setMenuEnable(null);
						return;
					}
					classifyProject=cescore.getCEManager().getProject();
				}
				TreeItem[] selection = fftree.getSelection();
				if(selection.length==0){
					setMenuEnable(null);
				}else if (selection.length == 1) {
	                Object nodeTypeObj=selection[0].getData();
					if(nodeTypeObj instanceof FFTable){
						FFTable ffTable=(FFTable)nodeTypeObj;
						if(ffTable.calculated){
							setMenuEnable(enableTable);
						}else{
							if(ffTable.canCalculate()){
								setMenuEnable(commonTrue);
							}else{
								setMenuEnable(commonFalse);
							}
						}
					}else if(nodeTypeObj instanceof FactorCatalog){
						FactorCatalog fg=(FactorCatalog)nodeTypeObj;
						if(fg.calculated){
							setMenuEnable(enableCata);
						}else{
							if(fg.canCalculate()){
								setMenuEnable(commonTrue);
							}else{
								setMenuEnable(commonFalse);
							}
						}
					}else if(nodeTypeObj instanceof BasicFactor){
						BasicFactor br=(BasicFactor)nodeTypeObj;
						if(br.calculated){
							setMenuEnable(enableFactor);
						}else{
							if(br.canCalculate()){
								setMenuEnable(commonTrue);
							}else{
								setMenuEnable(commonFalse);
							}
						}
					}else if(nodeTypeObj instanceof BasicFacet){
						BasicFacet bt=(BasicFacet)nodeTypeObj;
						if(bt.calculated){
							setMenuEnable(enableFacet);
						}else{
							if(bt.canCalculate()){
								setMenuEnable(commonTrue);
							}else{
								setMenuEnable(commonFalse);
							}
						}
					}else if(nodeTypeObj instanceof DerivedFacet){
						DerivedFacet dt=(DerivedFacet)nodeTypeObj;
						if(dt.calculated){
							setMenuEnable(enableFacet);
						}else{
							if(dt.canCalculate()){
								setMenuEnable(commonTrue);
							}else{
								setMenuEnable(commonFalse);
							}
						}
					}else{
						return;
					}
				} else {
					System.out.println("Multiselection");
				}
				super.mouseDown(e);
			}
			private void setMenuEnable(int[] enableIndex){
				for(int i=0;i<ACTION_NUM;i++){
					itemActions[i].setEnabled(false);
				}
				if(enableIndex==null){
					return;
				}
				for(int i=0;i<enableIndex.length;i++){
					itemActions[enableIndex[i]].setEnabled(true);
				}
			}
			
		});
	}
	/**
	 * #0:计算
	 * shared by FFTable,FactorCatalog,BasicFactor,BasicFacet(&DerivedFacet)
	 */
	private class caculateAction extends Action{
		public caculateAction(){
			this.setText("计算");
		}
		public void run(){
			TreeItem[] selection = fftree.getSelection();
			if (selection.length == 1) {
				TreeItem treeItem=selection[0];
				FFItem ffitem=(FFItem)treeItem.getData();
				try {
					IRasterAnalysisEnvironment env = classifyProject.getRasterAnalysisEnvironment();
					ObstructCMap obs=classifyProject.getObstruct();
					RangeCMap ran=classifyProject.getClassifyRange();
//					new ProgressMonitorDialog(null).run(false,false,new FFTCalculator(ffitem,env));
//					new Thread(new FFTCalculator(ffitem,env)).start();
					new FFTCalcu("计算",100,ffitem,new CalculatorInfo(env,ran,obs)).start();
					
				} catch(InvocationTargetException ite){
					String error=ite.getCause().toString();
					System.out.println("throwed by calc "+error);
				} catch (Exception e) {
					MessageDialog.openError(shell,"计算","计算过程中出现错误,请查看日志了解原因");
					e.printStackTrace();
				}
			} else {
				System.out.println("Multiselection");
			}
		}
	}
	/**
	 * #1:作用分统计
	 * shared by FFTable,FactorCatalog,BasicFactor,BasicFacet(&DerivedFacet)
	 */
	private class functionValueAction extends Action{
		public functionValueAction(){
			this.setText("作用分统计");
		}
		public void run(){
			TreeItem[] selection = fftree.getSelection();
			if (selection.length == 1) {
				TreeItem treeItem=selection[0];
				FFItem ffitem=(FFItem)treeItem.getData();
				ffitem.getEijStatisticsTable().show();
			} else {
				System.out.println("Multiselection");
			}
		}
	}
	/**
	 * #2:单元格分值图
	 * shared by FFTable,FactorCatalog,BasicFactor,BasicFacet(&DerivedFacet)
	 */
	private class cellValueAction extends Action{
		public cellValueAction(){
			this.setText("单元格分值图");
		}
		public void run(){
			TreeItem[] selection = fftree.getSelection();
			if (selection.length == 1) {
				TreeItem treeItem=selection[0];
				FFItem ffitem=(FFItem)treeItem.getData();
				{
					CMap map=ffitem.getCellValueCMap();
					if(map!=null&&cescore!=null){
						try {
							new ProgressMonitorDialog(null).run(false,false,new LoadGroupLayer(map,cescore));
						} catch (Exception e) {
							MessageDialog.openError(shell,"获取单元格分值图","未能获取到分值图");
							e.printStackTrace();
						}
					}
				}
			} else {
				System.out.println("Multiselection");
			}
		}
	}
	/**
	 * #3:频率图
	 * shared by FFTable,FactorCatalog,BasicFactor,BasicFacet(&DerivedFacet)
	 */
	private class freqenceAction extends Action{
		public freqenceAction(){
			this.setText("频率图");
		}
		public void run(){
			TreeItem[] selection = fftree.getSelection();
			if (selection.length == 1) {
				TreeItem treeItem=selection[0];
				FFItem ffitem=(FFItem)treeItem.getData();
				ffitem.getFrequencyDiagram().show();
			} else {
				System.out.println("Multiselection");
			}
		}
	}
	/**
	 * #4:因素作用分值图
	 * only by BasicFactor
	 */
	private class factorValueAction extends Action{
		public factorValueAction(){
			this.setText("因素作用分值图");
		}
		public void run(){
			TreeItem[] selection = fftree.getSelection();
			if (selection.length == 1) {
				TreeItem treeItem=selection[0];
				FFItem ffitem=(FFItem)treeItem.getData();
				if(ffitem instanceof BasicFactor){
//					BasicFactor factor=(BasicFactor)ffitem;
					{
//						CMap map=factor.getIsolineCMap();
						CMap map=ffitem.getRankMap();
						if(map!=null&&cescore!=null){
							try {
								new ProgressMonitorDialog(null).run(false,false,new LoadGroupLayer(map,cescore));
							} catch (Exception e) {
								MessageDialog.openError(shell,"获取因素作用分值图","未能获取到分值图");
								e.printStackTrace();
							}
						}
					}
				}
			} else {
				System.out.println("Multiselection");
			}
		}
	}
	/**
	 * #5:初步土地级别图
	 * only by FFTable
	 */
	private class classificationAction extends Action{
		public classificationAction(){
			this.setText("因素等值线图&&土地级别图");
		}
		public void run(){
			TreeItem[] selection = fftree.getSelection();
			if (selection.length == 1) {
				TreeItem treeItem=selection[0];
				FFItem ffitem=(FFItem)treeItem.getData();
//				if(ffitem instanceof FFTable){
				if(ffitem!=null){
//					FFTable table=(FFTable)ffitem;
					{
						classify classfiyDialog=new classify(null,ffitem.getCellValueCMap());
						int open=0;
						open=classfiyDialog.open();
						if(open!=Window.OK){
							return;
						}
						double[] arg1=classfiyDialog.getUp();
						double[] arg2=classfiyDialog.getDown();
						int[] arg3=classfiyDialog.getS();
						CMap map=ffitem.initialRankMap(arg1,arg2,arg3);
						if(map!=null&&cescore!=null){
							try {
								new ProgressMonitorDialog(null).run(false,false,new LoadGroupLayer(map,cescore));
							} catch (Exception e) {
								MessageDialog.openError(shell,"获取土地级别图","未能获取到土地级别图");
								e.printStackTrace();
							}
						}
					}
				}
			} else {
				System.out.println("Multiselection");
			}
		}
	}
	/**
	 * #6:节点属性
	 * shared by FFTable,FactorCatalog,BasicFactor,BasicFacet(&DerivedFacet)
	 */
	private class propertyAction extends Action{

		public propertyAction() {
			this.setText("节点属性");
		}
		public void run() {
			TreeItem[] selection = fftree.getSelection();
			if (selection.length == 1) {
				TreeItem treeItem=selection[0];
				FFItem ffitem=(FFItem)treeItem.getData();
				NodeDialog propNode=null;
				if(ffitem instanceof FactorCatalog){
					propNode=new NodeDialog(shell,ffitem,false);
				}else if(ffitem instanceof BasicFactor){
					propNode=new NodeDialog(shell,ffitem,false);
				}else if(ffitem instanceof BasicFacet){
					propNode=new NodeDialog(shell,ffitem,true);					
				}else if(ffitem instanceof DerivedFacet){
					propNode=new NodeDialog(shell,ffitem,true);
				}
				if(propNode==null){
					return;
				}
				int dialog=propNode.open();
				if(dialog!=0){
					return;
				}
				treeItem.setText(ffitem.name);
			} else {
				System.out.println("Multiselection");
			}
		}
	}
	/**
	 * #7:保存为ShapeFile_单元格
	 * shared by FFTable,FactorCatalog,BasicFactor,BasicFacet(&DerivedFacet)
	 */
	private class save2ShapefileAction extends Action{
		public save2ShapefileAction(){
			this.setText("保存为ShapeFile");
		}
		public void run(){
			TreeItem[] selection = fftree.getSelection();
			if (selection.length == 1) {
				TreeItem treeItem=selection[0];
				FFItem ffitem=(FFItem)treeItem.getData();
				FileDialog saveDialog=new FileDialog(shell,SWT.SAVE);
				saveDialog.setFilterExtensions(new String[]{"*.shp"});
				String dir=saveDialog.open();
				if(dir==null){
					return;
				}
				try {
					ffitem.getCellValueCMap().saveAsShapeFile(dir);
				} catch (Exception e) {
					MessageDialog.openError(shell,"保存分值图","保存分值图出现错误");
					e.printStackTrace();
				}
			} else {
				System.out.println("Multiselection");
			}
		}
	}
	/**
	 * #8:保存为Mif_单元格
	 * shared by FFTable,FactorCatalog,BasicFactor,BasicFacet(&DerivedFacet)
	 */
	private class save2MifAction extends Action{
		public save2MifAction(){
			this.setText("保存为Mif");
		}
		public void run(){
			TreeItem[] selection = fftree.getSelection();
			if (selection.length == 1) {
				TreeItem treeItem=selection[0];
				FFItem ffitem=(FFItem)treeItem.getData();
				FileDialog saveDialog=new FileDialog(shell,SWT.SAVE);
				saveDialog.setFilterExtensions(new String[]{"*.mif"});
				String dir=saveDialog.open();
				if(dir==null){
					return;
				}
				try {
					ffitem.getCellValueCMap().saveAsMif(dir);
				} catch (Exception e) {
					MessageDialog.openError(shell,"保存分值图","保存分值图出现错误");
					e.printStackTrace();
				}
			} else {
				System.out.println("Multiselection");
			}
		}
	}
	/**
	 * #9:导出统计结果
	 * shared by FFTable,FactorCatalog,BasicFactor,BasicFacet(&DerivedFacet)
	 */
	private class exportStatisticAction extends Action{
		public exportStatisticAction(){
			this.setText("导出统计结果");
		}
		public void run(){
			TreeItem[] selection = fftree.getSelection();
			if (selection.length == 1) {
				TreeItem treeItem=selection[0];
				FFItem ffitem=(FFItem)treeItem.getData();
				ffitem.getEijStatisticsTable().setName(treeItem.getText());
				
				
				
				FileDialog saveDialog=new FileDialog(shell,SWT.SAVE);
				saveDialog.setFilterExtensions(new String[]{".xls"});
				
				String dir=saveDialog.open();
				if(dir==null){
					return;
				}
				try {
					ffitem.getEijStatisticsTable().saveAs(dir);
				} catch (Exception e) {
					MessageDialog.openError(shell,"保存","保存过程中出现错误");
					e.printStackTrace();
				}
			} else {
				System.out.println("Multiselection");
			}
		}
	}
	/**
	 * #10:修正
	 * only by FFTable
	 */
	private class correctAction extends Action{
		public correctAction(){
			this.setText("修正");
		}
		public void run(){
			TreeItem[] selection = fftree.getSelection();
			if (selection.length == 1) {
				TreeItem treeItem=selection[0];
				FFItem ffitem=(FFItem)treeItem.getData();
				if(ffitem instanceof FFTable){
					FFTable table=(FFTable)ffitem;
				}
			} else {
				System.out.println("Multiselection");
			}
		}
	}
	/**
	 * #11:保存_频率
	 * shared by FFTable,FactorCatalog,BasicFactor,BasicFacet(&DerivedFacet)
	 */
	private class save2FreqAction extends Action{
		public save2FreqAction(){
			this.setText("保存");
		}
		public void run(){
			TreeItem[] selection = fftree.getSelection();
			if (selection.length == 1) {
				TreeItem treeItem=selection[0];
				FFItem ffitem=(FFItem)treeItem.getData();
					try {
						String dir=classifyProject.getTempDirectory();
						ffitem.getFrequencyDiagram().saveAs(dir);
					} catch (Exception e) {
						MessageDialog.openError(shell,"保存","保存过程中出现错误");
						e.printStackTrace();
					}
			} else {
				System.out.println("Multiselection");
			}
		}
	}
	/**
	 * #12:保存到_频率
	 * shared by FFTable,FactorCatalog,BasicFactor,BasicFacet(&DerivedFacet)
	 */
	private class saveAs2FreqAction extends Action{
		public saveAs2FreqAction(){
			this.setText("保存到");
		}
		public void run(){
			TreeItem[] selection = fftree.getSelection();
			if (selection.length == 1) {
				TreeItem treeItem=selection[0];
				FFItem ffitem=(FFItem)treeItem.getData();
				FileDialog saveDialog=new FileDialog(shell,SWT.SAVE);
				String dir=saveDialog.open();
				if(dir==null){
					return;
				}
				try {
					ffitem.getFrequencyDiagram().saveAs(dir);
				} catch (Exception e) {
					MessageDialog.openError(shell,"保存","保存过程中出现错误");
					e.printStackTrace();
				}
			} else {
				System.out.println("Multiselection");
			}
		}
	}
	/**
	 * #13:保存_分值
	 * only by BasicFactor
	 */
	private class save2FactorAction extends Action{
		public save2FactorAction(){
			this.setText("保存");
		}
		public void run(){
			TreeItem[] selection = fftree.getSelection();
			if (selection.length == 1) {
				TreeItem treeItem=selection[0];
				FFItem ffitem=(FFItem)treeItem.getData();
				if(ffitem instanceof BasicFactor){
					BasicFactor factor=(BasicFactor)ffitem;
					try {
						String dir=classifyProject.getTempDirectory();
//						factor.getIsolineCMap().saveAs(dir);
						ffitem.getRankMap().saveAsShapeFile(dir);
					} catch (Exception e) {
						MessageDialog.openError(shell,"保存","保存过程中出现错误");
						e.printStackTrace();
					}
				}
			} else {
				System.out.println("Multiselection");
			}
		}
	}
	/**
	 * #14:保存到_分值
	 * only by BasicFactor
	 */
	private class saveAs2FactorAction extends Action{
		public saveAs2FactorAction(){
			this.setText("saveAs2FactorAction");
		}
		public void run(){
			TreeItem[] selection = fftree.getSelection();
			if (selection.length == 1) {
				TreeItem treeItem=selection[0];
				FFItem ffitem=(FFItem)treeItem.getData();
				if(ffitem instanceof BasicFactor){
					BasicFactor factor=(BasicFactor)ffitem;
					FileDialog saveDialog=new FileDialog(shell,SWT.SAVE);
					String dir=saveDialog.open();
					if(dir==null){
						return;
					}
					try {
						factor.getCellValueCMap().saveAsShapeFile(dir);
					} catch (Exception e) {
						MessageDialog.openError(shell,"保存","保存过程中出现错误");
						e.printStackTrace();
					}
				}
			} else {
				System.out.println("Multiselection");
			}
		}
	}
	/**
	 * #15:保存_级别
	 * only by FFTable
	 */
	private class save2ClassifyAction extends Action{
		public save2ClassifyAction(){
			this.setText("保存");
		}
		public void run(){
			TreeItem[] selection = fftree.getSelection();
			if (selection.length == 1) {
				TreeItem treeItem=selection[0];
				FFItem ffitem=(FFItem)treeItem.getData();
				if(ffitem instanceof FFTable){
					FFTable table=(FFTable)ffitem;
					try {
						String dir=classifyProject.getTempDirectory();
						table.getRankMap().saveAsShapeFile(dir);
					} catch (Exception e) {
						MessageDialog.openError(shell,"保存","保存过程中出现错误");
						e.printStackTrace();
					}
				}
			} else {
				System.out.println("Multiselection");
			}
		}
	}
	/**
	 * #16:保存到_级别
	 * only by FFTable
	 */
	private class saveAs2ClassifyAction extends Action{
		public saveAs2ClassifyAction(){
			this.setText("保存到");
		}
		public void run(){
			TreeItem[] selection = fftree.getSelection();
			if (selection.length == 1) {
				TreeItem treeItem=selection[0];
				FFItem ffitem=(FFItem)treeItem.getData();
				if(ffitem instanceof FFTable){
					FFTable table=(FFTable)ffitem;
					
					classify classfiyDialog=new classify(null,table.getCellValueCMap());
					int open=0;
					open=classfiyDialog.open();
					if(open!=Window.OK){
						return;
					}
					double[] arg1=classfiyDialog.getUp();
					double[] arg2=classfiyDialog.getDown();
					int[] arg3=classfiyDialog.getS();
					RankCMap map=table.initialRankMap(arg1,arg2,arg3);
					
					
					FileDialog saveDialog=new FileDialog(shell,SWT.SAVE);
					saveDialog.setFilterExtensions(new String[]{".shp"});
					String dir=saveDialog.open();
					if(dir==null){
						return;
					}
					try {
						map.saveAsShapeFile(dir);
					} catch (Exception e) {
						MessageDialog.openError(shell,"保存","保存过程中出现错误");
						e.printStackTrace();
					}
				}
			} else {
				System.out.println("Multiselection");
			}
		}
	}
	/**
	 * #17:导出到Excel表_因素
	 * only by BasicFactor
	 */
	private class excel2FactorAction extends Action{
		public excel2FactorAction(){
			this.setText("导出到Excel表");
		}
		public void run(){
			TreeItem[] selection = fftree.getSelection();
			if (selection.length == 1) {
				TreeItem treeItem=selection[0];
				FFItem ffitem=(FFItem)treeItem.getData();
				if(ffitem instanceof FFTable){
					FFTable table=(FFTable)ffitem;
				}
			} else {
				System.out.println("Multiselection");
			}
		}
	}
	/**
	 * #18:单元格分值图着色
	 * change to 等值线图
	 * only by BasicFactor
	 */
	private class cellValueColor extends Action{
		public cellValueColor(){
			this.setText("等值线图");
		}
		public void run(){
			TreeItem[] selection = fftree.getSelection();
			if (selection.length == 1) {
				TreeItem treeItem=selection[0];
				FFItem ffitem=(FFItem)treeItem.getData();
				ffitem.getCellValueCMap();
				try {
					if(!ffitem.calculated){
						MessageDialog.openInformation(shell,"加载等值线图","没有图层可以加载,请先计算");
					}else if(ffitem.calculated){
						//Dialog: color,parm:ffitem.getCellValueCMap()
//						rasterRenderDialog renderDialog=new rasterRenderDialog(shell,ffitem.getCellValueCMap().getAsRaster());
//						IRaster ras=renderDialog.open();
						RRDialog rrDialog=new RRDialog(shell,ffitem.getCellValueCMap().getAsRaster());
						int open=rrDialog.open();
						if(open!=Window.OK){
							return;
						}
						IRaster ras=rrDialog.getPRaster();
						IRasterLayer rasterLayers = new RasterLayer();
						rasterLayers.createFromRaster(ras);
						cescore.getIsolineGroupLayer().add(rasterLayers);
						new ProgressMonitorDialog(null).run(false,false,new IRunnableWithProgress(){
							public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
								monitor.beginTask("获取地图...",6);
								try {
									cescore.resetMapControl(monitor,0);
								} catch (Exception e) {
									monitor.setTaskName("更新地图失败");
									e.printStackTrace();
								}finally{
									monitor.done();
								}
							}
						});
						
					}
				} catch (Exception e) {
					MessageDialog.openError(shell,"获取等值线图","获取等值线图出错");
					e.printStackTrace();
				}
			} else {
				System.out.println("Multiselection");
			}
		}
	}
	
	/**
	 * @author Administrator
	 *	保存为Tab_单元格
	 */
	private class save2TabAction extends Action{
		public save2TabAction(){
			this.setText("保存为Tab");
		}
		public void run(){
			TreeItem[] selection = fftree.getSelection();
			if (selection.length == 1) {
				TreeItem treeItem=selection[0];
				FFItem ffitem=(FFItem)treeItem.getData();
				FileDialog saveDialog=new FileDialog(shell,SWT.SAVE);
				saveDialog.setFilterExtensions(new String[]{"*.tab"});
				String dir=saveDialog.open();
				if(dir==null){
					return;
				}
				try {
					ffitem.getCellValueCMap().saveAsTab(dir);
				} catch (Exception e) {
					MessageDialog.openError(shell,"保存分值图","保存分值图出现错误");
					e.printStackTrace();
				}
			} else {
				System.out.println("Multiselection");
			}
		}
	}
		/**
		 * @author Administrator
		 *	查看FFT
		 */
		private class browserFFTAction extends Action{
			public browserFFTAction(){
				this.setText("查看FFT");
			}
			public void run(){
				TreeItem[] selection = fftree.getSelection();
				if (selection.length == 1) {
					TreeItem treeItem=selection[0];
					FFItem ffitem=(FFItem)treeItem.getData();
					if(ffitem instanceof FFTable)
					{
						FFTable fft=(FFTable)ffitem;
						String tempHtmlPath=cescore.getCEManager().getProject().getTempDirectory()+File.separator+"gen.html";
						
						FFSystem.FFTExport(fft,tempHtmlPath,0);
						//FFSystem.FFTExport(fft,"c:\\haha.rtf",1);
						//FFSystem.FFTExport(fft,"c:\\haha.pdf",2);
						
						BrowerDialog window = new BrowerDialog(tempHtmlPath);
						window.setBlockOnOpen(true);
						window.open();
					}
				}
					
					
				
			}
		
	}
	
}
