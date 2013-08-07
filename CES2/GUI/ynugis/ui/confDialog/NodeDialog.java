/**
 * @author yddy,create date 2003-11-3
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.confDialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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

import ynugis.ffsystem.BasicFacet;
import ynugis.ffsystem.DerivedFacet;
import ynugis.ffsystem.FFItem;
import ynugis.ffsystem.FFTConstant;
import ynugis.utility.GT;

public class NodeDialog extends Dialog {
	private FFTDialog dialog;
	private FFItem	ffitem;
	private Text nameText;
	private Text weightText;
	private Button formulaLineRdo;
	private Button formulaExpRdo;
	private Text dirText;
	private List fileList;
	private Button dirBtn;
	private Button okBtn;
	private Button delBtn;

	private boolean	dirMore;
	private String dir;
	private String[] files;

	public NodeDialog(Shell parentShell,FFItem item,boolean more) {
		super(parentShell);
		ffitem=item;
		dirMore=more;
	}

	public NodeDialog(Shell parentShell,FFTDialog fftd, FFItem item, boolean more) {
		this(parentShell,item,more);
//		ffitem = item;
//		dirMore = more;
		dialog=fftd;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		GridLayout contentLay = new GridLayout();
		contentLay.horizontalSpacing = 5;
		contentLay.numColumns = 2;
		contentLay.marginTop = 10;
		contentLay.marginRight = 10;
		contentLay.marginBottom = 10;
		contentLay.marginLeft = 10;
		Composite content = new Composite(parent, SWT.NONE);
		content.setLayout(contentLay);
		{
			GridLayout groupLay = new GridLayout();
			groupLay.numColumns = 2;
			// group 1
			Group share4Group = new Group(content, SWT.SHADOW_IN);
			share4Group.setLayout(groupLay);
			share4Group.setText("必需属性");
			Label nameLabel = new Label(share4Group, SWT.NONE);
			nameLabel.setText("名称:");
			nameText = new Text(share4Group, SWT.BORDER);
//			nameText.addVerifyListener(new VerifyListener(){
//				public void verifyText(VerifyEvent e) {
//					if(checkAll()==true){
//						okBtn.setEnabled(true);
//					}else{
//						okBtn.setEnabled(false);
//					}
//				}
//			});
			Label weightLabel = new Label(share4Group, SWT.NONE);
			weightLabel.setText("权重:");
			weightText = new Text(share4Group, SWT.BORDER);
//			weightText.addVerifyListener(new VerifyListener(){
//				public void verifyText(VerifyEvent e) {
//					if(checkAll()==true){
//						okBtn.setEnabled(true);
//					}else{
//						okBtn.setEnabled(false);
//					}
//				}
//			});
			if(dirMore){
			Label formulaLabel = new Label(share4Group, SWT.NONE);
			formulaLabel.setText("公式:");
			formulaLineRdo=new Button(share4Group,SWT.RADIO|SWT.NONE);
			formulaLineRdo.setText("距离公式");
			new Label(share4Group,SWT.NONE);
			formulaExpRdo=new Button(share4Group,SWT.RADIO|SWT.NONE);
			formulaExpRdo.setText("指数公式");
			}
			// group 2
			if (dirMore) {
				Group share2Group = new Group(content, SWT.SHADOW_IN);
				share2Group.setLayout(groupLay);
				share2Group.setText("叶节点必需属性");
				Label dirLabel = new Label(share2Group, SWT.NONE);
				dirLabel.setText("路径:");
				dirText = new Text(share2Group, SWT.BORDER);
				dirText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				dirText.setEditable(true);
				GridData dirLay = new GridData();
				dirLay.horizontalAlignment = GridData.END;
				dirLay.horizontalSpan = 1;
				delBtn=new Button(share2Group,SWT.NONE);
				delBtn.setLayoutData(dirLay);
				delBtn.setText("删除");
				dirBtn = new Button(share2Group, SWT.NONE);
				dirBtn.setLayoutData(dirLay);
				dirBtn.setText("设置");
//				dirBtn.addSelectionListener(new SelectionAdapter(){
//					public void widgetSelected(SelectionEvent e){
//						FileDialog addFile=new FileDialog(getShell(),SWT.MULTI);
//						addFile.setFilterNames(new String[]{"ShapeFile(*.shp)","all(*.*)"});
//						addFile.setFilterExtensions(new String[]{"*.shp","*.*"});
//						addFile.setFilterPath("e:\\");
//						dir=addFile.open();
//						if(dir==null){
//							return;
//						}
//						dirText.setText(dir);
//						String[] fileNames=addFile.getFileNames();
//						files=new String[fileNames.length];
//						for(int i=0;i<fileNames.length;i++){
//							files[i]=dir+"\\"+fileNames[i];
//						}
//						//for test
//						{
//							for(int i=0;i<fileNames.length;i++){
//								System.out.println(fileNames[i]);
//							}
//						}
//					}
//				});
				GridData grid=new GridData(GridData.FILL_BOTH);
				grid.minimumHeight=50;
				grid.minimumWidth=100;
				grid.horizontalSpan=2;
				fileList=new List(share2Group,SWT.V_SCROLL|SWT.H_SCROLL);
				fileList.setLayoutData(grid);
			}
		}
		initializeText();
		return content;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createButton(org.eclipse.swt.widgets.Composite,
	 *      int, java.lang.String, boolean) FINAL METHOD
	 */
	protected Button createButton(Composite parent, int id, String label,
			boolean defaultButton) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#initializeBounds() FINAL METHOD
	 */
	protected void initializeBounds() {
		okBtn=super.createButton((Composite) getButtonBar(), IDialogConstants.OK_ID,
				"确定", true);
//		System.out.println("okBtn:"+okBtn);
		super.createButton((Composite) getButtonBar(),
				IDialogConstants.CANCEL_ID, "取消", true);
		if(ffitem.name==null){
			okBtn.setEnabled(false);
		}
		super.initializeBounds();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
//	protected void okPressed() {
	private void initializeText(){
		System.out.println("initializeProperty");
		if(ffitem.name!=null){
			nameText.setText(ffitem.name);
			weightText.setText(String.valueOf(ffitem.weight));
		}
		if(ffitem instanceof BasicFacet){
			BasicFacet bt=(BasicFacet)ffitem;
			
			files=bt.getSrcFiles();
			System.out.println("files:"+files);
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
			int formula=bt.getFormula();
			if(formula==FFTConstant.FORMULA_LINEAR_ATTENUATION){
				formulaLineRdo.setSelection(true);
			}else if(formula==FFTConstant.FORMULA_EXPONENTIAL_ATTENUATION){
				formulaExpRdo.setSelection(true);
			}
		}else if(ffitem instanceof DerivedFacet){
			DerivedFacet df=(DerivedFacet)ffitem;
			files=df.getSrcFiles();
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
			int formula=df.getFormula();
			if(formula==FFTConstant.FORMULA_LINEAR_ATTENUATION){
				formulaLineRdo.setSelection(true);
			}else if(formula==FFTConstant.FORMULA_EXPONENTIAL_ATTENUATION){
				formulaExpRdo.setSelection(true);
			}
		}
		/*
		 * add listeners
		 */
		{
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
			if(dirMore){
//				formulaLineRdo.addSelectionListener(new SelectionAdapter(){
//					public void widgetSelected(SelectionEvent e){
//					}
//				});
//				formulaExpRdo.addSelectionListener(new SelectionAdapter(){
//					public void widgetSelected(SelectionEvent e){
//					}
//				});
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
				dirBtn.addSelectionListener(new SelectionAdapter(){
					public void widgetSelected(SelectionEvent e){
						FileDialog addFile=new FileDialog(getShell(),SWT.MULTI);
						addFile.setFilterNames(new String[]{"ShapeFile(*.shp)","all(*.*)"});
						addFile.setFilterExtensions(new String[]{"*.shp","*.*"});
//						addFile.setFilterPath("e:\\");
						dir=addFile.open();
						if(dir==null){
							return;
						}
						dir=addFile.getFilterPath();
						dirText.setText(dir);
						String[] fileNames=addFile.getFileNames();
						String[] files=new String[fileNames.length];
//						fileList.removeAll();
						for(int i=0;i<fileNames.length;i++){
							files[i]=dir+"\\"+fileNames[i];
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
			}
		}
	}
	private boolean checkAll(){
		if(nameText.getText().equals("")){
			return false;
		}
		String weightString=weightText.getText();
		System.out.println(weightString);
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
//	private void initializeText(){
	protected void okPressed() {
		ffitem.name=nameText.getText();
		ffitem.weight=Double.parseDouble(weightText.getText());
		if (dialog!=null) {
			/*
			 * show the current node info
			 */
			dialog.showNode(ffitem);
//			dialog.saveNode(ffitem);
			System.out.println("save from nodeDialog:"+ffitem.name);
			
		}
		if(ffitem instanceof BasicFacet){
			BasicFacet bt=(BasicFacet)ffitem;
			String[] fileNames=fileList.getItems();
			if(fileNames.length>0){
				bt.specifySrcData(fileNames);
			}
			/*if(dir!=null&&files!=null){
				if(files.length>0){
					bt.specifySrcData(files);
				}
			}*/
			boolean lineSelected=formulaLineRdo.getSelection();
			if(lineSelected){
				bt.setFormula(FFTConstant.FORMULA_LINEAR_ATTENUATION);
			}else{
				bt.setFormula(FFTConstant.FORMULA_EXPONENTIAL_ATTENUATION);
			}
		}else if(ffitem instanceof DerivedFacet){
			DerivedFacet df=(DerivedFacet)ffitem;
			String[] fileNames=fileList.getItems();
			if(fileNames.length>0){
				df.specifySrcData(fileNames);
			}
//			files=df.getSrcFiles();
			/*if(dir!=null&&files!=null){
				if(files.length>0){
					df.specifySrcData(files);
				}
			}*/
			boolean lineSelected=formulaLineRdo.getSelection();
			if(lineSelected){
				df.setFormula(FFTConstant.FORMULA_LINEAR_ATTENUATION);
			}else{
				df.setFormula(FFTConstant.FORMULA_EXPONENTIAL_ATTENUATION);
			}
		}
		super.okPressed();
	}
}
