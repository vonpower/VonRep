/**
 * @author yddy,create date 2003-11-2 
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.confDialog;

import java.io.File;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import ynugis.ffsystem.BasicFacet;
import ynugis.ffsystem.DerivedFacet;
import ynugis.ffsystem.FFItem;
import ynugis.ffsystem.FFTConstant;
import ynugis.ffsystem.FFTable;
import ynugis.ui.action.FFTActionGroup;
import ynugis.utility.GT;

public class FFTDialog extends Dialog {
	private final int REFRESH=IDialogConstants.CANCEL_ID+200;
	
	private FFTable fftable;
	private Tree tree;
	private FFItem preItem;
	private String preDir="c:\\";
	
	private Text nameText;
	private Text weightText;
	private Label formulaLabel;
	private Button formulaLineRdo;
	private Button formulaExpRdo;
	private Text dirText;
	private List fileList;
	private Button okBtn;
	
	private String dir;
//	private String[] files;

	public FFTDialog(Shell parentShell,FFTable table) {
		super(parentShell);
		fftable=table;
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		//main panel
		GridLayout contentLayout=new GridLayout();
		contentLayout.numColumns=3;
		contentLayout.marginTop=10;
		contentLayout.marginLeft=10;
		contentLayout.horizontalSpacing=6;
		Composite content=new Composite(parent,SWT.BORDER);
		content.setLayout(contentLayout);
		// 1/2:left panel
		GridData treeData=new GridData(GridData.FILL_BOTH);
		treeData.horizontalSpan=2;
		treeData.minimumWidth=180;
		Composite treeCom=new Composite(content,SWT.BORDER);
		treeCom.setLayoutData(treeData);
		treeCom.setLayout(new FillLayout());
		tree=new Tree(treeCom,SWT.SINGLE);
//		TreeItem rootItem=new TreeItem(tree,SWT.NONE);
//		rootItem.setText(fftable.name);
//		rootItem.setData(fftable);
		initializeTree(tree,fftable);
		// 2/2:right panel
		GridData propData=new GridData(GridData.FILL_BOTH);
		propData.horizontalSpan=1;
		Composite propCom=new Composite(content,SWT.NONE);
		propCom.setLayoutData(propData);
		FillLayout propLay=new FillLayout(SWT.VERTICAL);
		propLay.spacing=10;
		propLay.marginHeight=3;
		propLay.marginWidth=3;
		propCom.setLayout(propLay);
		{
			GridLayout groupLay=new GridLayout();
			groupLay.numColumns=2;
			//group 1
			Group share4Group=new Group(propCom,SWT.SHADOW_IN);
			share4Group.setLayout(groupLay);
			share4Group.setText("必需属性");
			Label nameLabel=new Label(share4Group,SWT.NONE);
			nameLabel.setText("名称:");
			nameText=new Text(share4Group,SWT.BORDER);
			Label weightLabel=new Label(share4Group,SWT.NONE);
			weightLabel.setText("权重:");
			weightText=new Text(share4Group,SWT.BORDER);
			formulaLabel=new Label(share4Group,SWT.NONE);
			formulaLabel.setText("公式");
			formulaLabel.setEnabled(false);
			formulaLineRdo=new Button(share4Group,SWT.RADIO|SWT.NONE);
			formulaLineRdo.setText("距离公式");
			formulaLineRdo.setEnabled(false);
			new Label(share4Group,SWT.NONE);
			formulaExpRdo=new Button(share4Group,SWT.RADIO|SWT.NONE);
			formulaExpRdo.setText("指数公式");
			formulaExpRdo.setEnabled(false);
			//group 2
			Group share2Group=new Group(propCom,SWT.SHADOW_IN);
			share2Group.setLayout(groupLay);
			share2Group.setText("非必需属性");
			Label dirLabel=new Label(share2Group,SWT.NONE);
			dirLabel.setText("路径:");
			dirText=new Text(share2Group,SWT.BORDER);
			dirText.setEditable(false);
			
			GridData dirLay=new GridData();
			dirLay.horizontalAlignment=GridData.END;
			dirLay.horizontalSpan=1;
			Button delBtn=new Button(share2Group,SWT.NONE);
			delBtn.setLayoutData(dirLay);
			delBtn.setText("删除");
			delBtn.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e){
					if(fileList.getSelectionCount()>0){
						String[] sel=fileList.getSelection();
						for(int i=0;i<sel.length;i++){
							fileList.remove(sel[i]);
						}
					}
				}
			});
			Button dirBtn=new Button(share2Group,SWT.NONE);
			dirBtn.setLayoutData(dirLay);
			dirBtn.setText("设置");
			dirBtn.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e){
					FileDialog addFile=new FileDialog(getShell(),SWT.MULTI);
					addFile.setFilterNames(new String[]{"ShapeFile(*.shp)","all(*.*)"});
					addFile.setFilterExtensions(new String[]{"*.shp","*.*"});
					addFile.setFilterPath(preDir);
					dir=addFile.open();
					if(dir==null){
						return;
					}
					dir=addFile.getFilterPath();
					preDir=dir;
					dirText.setText(dir);
					String[] fileNames=addFile.getFileNames();
					String[] files=new String[fileNames.length];
//					fileList.removeAll();
					for(int i=0;i<fileNames.length;i++){
						files[i]=dir+File.separator+fileNames[i];
//						fileList.add(fileNames[i]);
						fileList.add(files[i]);
					}
					//for test
					{
						for(int i=0;i<fileNames.length;i++){
							System.out.println(fileNames[i]);
						}
					}
				}
			});
			
			/*
			 * modify listener
			 */
			nameText.addModifyListener(new ModifyListener(){

				public void modifyText(ModifyEvent e) {
					if(checkAll()==true){
						okBtn.setEnabled(true);
					}else{
						okBtn.setEnabled(false);
					}
				}});
			weightText.addModifyListener(new ModifyListener(){

				public void modifyText(ModifyEvent e) {
					if(checkAll()==true){
						okBtn.setEnabled(true);
					}else{
						okBtn.setEnabled(false);
					}
				}});
			
			GridData grid=new GridData(GridData.FILL_BOTH);
			grid.minimumHeight=50;
			grid.minimumWidth=100;
			grid.horizontalSpan=2;
			fileList=new List(share2Group,SWT.V_SCROLL|SWT.H_SCROLL);
			fileList.setLayoutData(grid);
			//group 3
			Group group=new Group(propCom,SWT.SHADOW_IN);
			group.setLayout(groupLay);
			group.setText("说明");
			GridData spa=new GridData(GridData.FILL_HORIZONTAL);
			spa.horizontalSpan=2;
			Text spaText=new Text(group,SWT.NONE);
			spaText.setLayoutData(spa);
			spaText.insert("只有因子和派生因子\n");
			Text spaText2=new Text(group,SWT.NONE);
			spaText2.setLayoutData(spa);
			spaText2.insert("需要设置地图文件\n");
		}
		//action on dialog
		{
			FFTActionGroup group=new FFTActionGroup(tree,this.getShell(),this);
			group.contextMenu();
		}
		return content;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createButton(org.eclipse.swt.widgets.Composite, int, java.lang.String, boolean)
	 * FINAL METHOD
	 */
	protected Button createButton(Composite parent, int id, String label, boolean defaultButton) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#initializeBounds()
	 * FINAL METHOD
	 */
	protected void initializeBounds() {
//		super.createButton((Composite)getButtonBar(),REFRESH,"刷新",false);
		okBtn=super.createButton((Composite)getButtonBar(),IDialogConstants.OK_ID,"确定",true);
		super.createButton((Composite)getButtonBar(),IDialogConstants.CANCEL_ID,"取消",true);
		super.initializeBounds();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
	 */
	protected void buttonPressed(int buttonId) {
		if(buttonId==REFRESH){
			if(tree.getSelectionCount()==1){
				TreeItem treeItem=tree.getSelection()[0];
				saveNode((FFItem)treeItem.getData());
			}
		}else if(buttonId==IDialogConstants.OK_ID){
			Object obj=tree.getSelection()[0].getData();
			if(obj!=null){
				this.saveNode(obj);
			}
			super.okPressed();
		}else if(buttonId==IDialogConstants.CANCEL_ID){
			super.cancelPressed();
		}
	}

	public void showNode(FFItem item){
		System.out.println("showing"+item.name);
		formulaLabel.setEnabled(false);
		formulaLineRdo.setEnabled(false);
		formulaExpRdo.setEnabled(false);
		nameText.setText("");
		weightText.setText("");
		dirText.setText("");
		fileList.removeAll();
		if(item instanceof FFTable){
			nameText.setText(item.name);
			return;
		}
		nameText.setText(item.name);
		weightText.setText(String.valueOf(item.weight));
		String[] files;
		if(item instanceof BasicFacet){
			BasicFacet basicFacet=(BasicFacet)item;
			files=basicFacet.getSrcFiles();
			if(files!=null){
				if(files.length>0){
					dir=GT.getFilePath(files[0]);
					dirText.setText(dir);
					fileList.removeAll();
					for(int i=0;i<files.length;i++){
						fileList.add(files[i]);
					}
				}
			}
			formulaLabel.setEnabled(true);
			formulaLineRdo.setEnabled(true);
			formulaExpRdo.setEnabled(true);
			formulaLineRdo.setSelection(false);
			formulaExpRdo.setSelection(false);
			int formula=basicFacet.getFormula();
			if(formula==FFTConstant.FORMULA_LINEAR_ATTENUATION){
				System.out.println("line set selected");
				formulaLineRdo.setSelection(true);
			}else if(formula==FFTConstant.FORMULA_EXPONENTIAL_ATTENUATION){
				formulaExpRdo.setSelection(true);
				System.out.println("exp set selected");
			}
		}else if(item instanceof DerivedFacet){
			DerivedFacet derivedFacet=(DerivedFacet)item;
			files=derivedFacet.getSrcFiles();
			if(files!=null){
				if(files.length>0){
					dir=GT.getFilePath(files[0]);
					dirText.setText(dir);
					fileList.removeAll();
					for(int i=0;i<files.length;i++){
						fileList.add(files[i]);
					}
				}
			}
			formulaLabel.setEnabled(true);
			formulaLineRdo.setEnabled(true);
			formulaExpRdo.setEnabled(true);
			formulaLineRdo.setSelection(false);
			formulaExpRdo.setSelection(false);
			int formula=derivedFacet.getFormula();
			if(formula==FFTConstant.FORMULA_LINEAR_ATTENUATION){
				formulaLineRdo.setSelection(true);
				System.out.println("line set selected");
			}else if(formula==FFTConstant.FORMULA_EXPONENTIAL_ATTENUATION){
				formulaExpRdo.setSelection(true);
				System.out.println("exp set selected");
			}
		}
	}
	public void saveNode(Object obj){
		FFItem cur=(FFItem)obj;
//		if(preItem!=null){
//			System.out.println("name:"+preItem.name);
//			preItem=cur;
//		}else{
//			preItem=cur;
//		}
		if(preItem==null){
			preItem=cur;
			return;
		}else{
			this.saveNode(preItem,cur);
			preItem=cur;
		}
	}
	private void saveNode(FFItem item,FFItem cur){
		if(nameText.getText().equals("")){
			return;
		}
		System.out.println("saving:"+nameText.getText());
		item.name=nameText.getText();
		if(!weightText.getText().equals("")){
			item.weight=Double.parseDouble(weightText.getText());
		}
		if(item instanceof BasicFacet){
			BasicFacet basicFacet=(BasicFacet)item;
			String[] fileNames=fileList.getItems();
			if(fileNames.length>0){
				basicFacet.specifySrcData(fileNames);
			}
			/*if(this.dir!=null){
				basicFacet.specifySrcData(files);
			}*/
			boolean formulaSelected=formulaLineRdo.getSelection();
			System.out.println("line Select:"+formulaSelected);
			if(formulaSelected){
				basicFacet.setFormula(FFTConstant.FORMULA_LINEAR_ATTENUATION);
			}else{
				basicFacet.setFormula(FFTConstant.FORMULA_EXPONENTIAL_ATTENUATION);
			}
		}else if(item instanceof DerivedFacet){
			DerivedFacet derivedFacet=(DerivedFacet)item;
			String[] fileNames=fileList.getItems();
			if(fileNames.length>0){
				derivedFacet.specifySrcData(fileNames);
			}
			/*if(this.dir!=null){
				derivedFacet.specifySrcData(files);
			}*/
			boolean formulaSelected=formulaLineRdo.getSelection();
			if(formulaSelected){
				derivedFacet.setFormula(FFTConstant.FORMULA_LINEAR_ATTENUATION);
			}else{
				derivedFacet.setFormula(FFTConstant.FORMULA_EXPONENTIAL_ATTENUATION);
			}
			
		}
		int sc=tree.getSelectionCount();
		if(sc==1){
			TreeItem treeItem=tree.getSelection()[0];
			treeItem.setText(cur.name);
		}
	}
	private void initializeTree(Tree fftree,FFTable table){
		new FFTDialogHelper().growTree(fftree,table);
	}
	
	private boolean checkAll(){
		if(nameText.getText().equals("")){
			return false;
		}
		String weightString=weightText.getText();
		if(tree.getSelectionCount()>0){
			Object obj=tree.getSelection()[0].getData();
			if(obj!=null){
				if(obj instanceof FFTable){
					weightString="0";
				}
			}
		}
		
		if(weightString.equals("")){
			return false;
		}
		try{
			Double.parseDouble(weightString);
			return true;
		}catch(Exception e){
			return false;
		}
	}
//	private void initializeTree(Tree fftree,FFTable table){
//		Object[] cldsObj=table.childCollection.toArray();
//		int cldLen=cldsObj.length;
//		if(cldLen==0){
//			TreeItem rootItem=new TreeItem(fftree,SWT.NONE);
//			rootItem.setText(fftable.name);
//			rootItem.setData(fftable);
//		}else if(cldLen>0){
//			for(int i=0;i<cldLen;i++){
//				FFItem item=(FFItem)cldsObj[i];
//				TreeItem cldItem=new TreeItem(fftree,SWT.NONE);
//				cldItem.setText(item.name);
//				cldItem.setData(item);
//				walkThroughFFTable(cldItem,item);
//			}
//		}
//	}
//	private void walkThroughFFTable(TreeItem parentItem,FFItem item){
//		Object[] cldsObj=item.childCollection.toArray();
//		int cldLen=cldsObj.length;
//		if(cldLen>0){
//			for(int i=0;i<cldLen;i++){
//				FFItem ffitem=(FFItem)cldsObj[i];
//				TreeItem cldItem=addTreeItem(parentItem,ffitem);
//				walkThroughFFTable(cldItem,ffitem);
//				parentItem.setExpanded(true);
//			}
//		}
//	}
//	private TreeItem addTreeItem(TreeItem parentItem,FFItem item){
//		TreeItem cldItem=new TreeItem(parentItem,SWT.NONE);
//		cldItem.setText(item.name);
//		cldItem.setData(item);
//		parentItem.setExpanded(true);
//		return cldItem;
//	}
}  //  @jve:decl-index=0:visual-constraint="9,6"
