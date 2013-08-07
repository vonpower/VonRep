package ynugis.ui.confDialog;



import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import ynugis.classifyBusiness.classifyItem.CellValueCMap;
import ynugis.classifyBusiness.classifyItem.RankCMap;
import ynugis.ui.face.MainFace;

public class classify extends Dialog {
	private Text intervalText;
	private Text endText;
	private Text beginText;
	private Info info;

	private Table table;

	// private Table table;
	private MainFace face;

	public TableViewer tv;
	private DirectoryDialog saveasDlg;

	private static Composite container;

	private CellValueCMap map;

	public double[] up;

	public double[] down;

	public int[] s;
	
    public  Button button ;

	

	public CellEditor[] cellEditor;

	// TableItem item;
	/**
	 * Create the dialog
	 * 
	 * @param parentShell
	 */
	public classify(Shell parentShell,CellValueCMap inmap) {
		super(parentShell);
		
		info = new Info();
		map=inmap;

	}

	/**
	 * Create contents of the dialog
	 * 
	 * @param parent
	 */
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FormLayout());

		final Group group = new Group(container, SWT.NONE);
		group.setText("设定分值划分定级范围");
		final FormData formData = new FormData();
		formData.bottom = new FormAttachment(0, 223);
		formData.right = new FormAttachment(100, -9);
		formData.top = new FormAttachment(0, 5);
		formData.left = new FormAttachment(0, 5);
		group.setLayoutData(formData);
		group.setLayout(new FormLayout());

		tv = new TableViewer(group, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		table = tv.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		TableColumn col1 = new TableColumn(table, SWT.NONE);
		col1.setAlignment(SWT.CENTER);
		col1.setText("分值上限");
		col1.setWidth(90);
		TableColumn col2 = new TableColumn(table, SWT.NONE);
		col2.setAlignment(SWT.CENTER);
		col2.setText("分值下限");
		col2.setWidth(90);
		TableColumn col3 = new TableColumn(table, SWT.NONE);
		col3.setText("等级");
		col3.setWidth(110);
		tv.setColumnProperties(new String[] { "0", "1", "2" });
		cellEditor = new CellEditor[3];
		cellEditor[0] = new TextCellEditor(tv.getTable());
		cellEditor[1] = new TextCellEditor(tv.getTable());
		cellEditor[2] = new TextCellEditor(tv.getTable());
		tv.setCellEditors(cellEditor);
		tv.setCellModifier(new MyCellModifier());
		tv.setContentProvider(new TableViewerContentProvider());
		tv.setLabelProvider(new TableViewerLabelProvider());
		// tv.setInput();
		Text text1 = (Text) cellEditor[0].getControl();
		text1.addVerifyListener(new VerifyListener() {

			public void verifyText(VerifyEvent e) {
				e.doit = "0123456789".indexOf(e.text) >= 0;
			}
		});
		Text text2 = (Text) cellEditor[1].getControl();
		text2.addVerifyListener(new VerifyListener() {

			public void verifyText(VerifyEvent e) {
				e.doit = "0123456789".indexOf(e.text) >= 0;
			}
		});

		Text text3 = (Text) cellEditor[2].getControl();
		text3.addVerifyListener(new VerifyListener() {

			public void verifyText(VerifyEvent e) {
				e.doit = "0123456789".indexOf(e.text) >= 0;
			}
		});

		final Button addButton = new Button(group, SWT.NONE);
		final FormData formData_4 = new FormData();
		formData_4.bottom = new FormAttachment(table, 25, SWT.TOP);
		formData_4.right = new FormAttachment(0, 373);
		formData_4.top = new FormAttachment(table, 0, SWT.TOP);
		formData_4.left = new FormAttachment(0, 320);
		addButton.setLayoutData(formData_4);
		addButton.setText("添加");
	
		addButton.addSelectionListener(new SelectionListener() {
			int i = 1;
			
			public void widgetSelected(SelectionEvent e) {
				button.setEnabled(true);

				String[] string = { "0" + i, "" + i, "" + i };
				content c = new content();
				Double s;
				s = Double.valueOf(string[0]);
				c.setstart(s.doubleValue());
				Double f;
				f = Double.valueOf(string[1]);
				c.setend(f.doubleValue());
				Integer classfy;
				classfy = Integer.valueOf(string[2]);
				c.setclassfy(classfy.intValue());
				i++;
				tv.add(c);

			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		final Button deleteButton = new Button(group, SWT.NONE);
		final FormData formData_6 = new FormData();
		formData_6.bottom = new FormAttachment(0, 80);
		formData_6.right = new FormAttachment(addButton, 0, SWT.RIGHT);
		formData_6.top = new FormAttachment(0, 55);
		formData_6.left = new FormAttachment(addButton, 0, SWT.LEFT);
		deleteButton.setLayoutData(formData_6);
		deleteButton.setText("删除");
		deleteButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				int[] sellnices = table.getSelectionIndices();
				table.remove(sellnices);
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
//		up= new double[table.getItemCount()];
//		down= new double[table.getItemCount()];
//		s= new int[table.getItemCount()];
//		boolean v=true;
//		content c;
////		content c1;
////		
//		c = (content) tv.getElementAt(0);
//		  up[0] = c.getstart();
//		  down[0]=c.getend();
//			s[0]=c.getclassfy();
//			
//		for (int i = 1; i < table.getItemCount(); i++) {
//			c = (content) tv.getElementAt(i);
//			//c1=(content) tv.getElementAt(i-1);
//			up[i] = c.getstart();
//			down[i] = c.getend();
//			s[i] = c.getclassfy();}
		 button = new Button(group, SWT.NONE);
		final FormData tlayout = new FormData();
		tlayout.bottom = new FormAttachment(addButton, 185, SWT.TOP);
		tlayout.right = new FormAttachment(0, 295);
		tlayout.top = new FormAttachment(addButton, 0, SWT.TOP);
		tlayout.left = new FormAttachment(0, 10);
		table.setLayoutData(tlayout);
		

		
		final FormData formData_1 = new FormData();
		formData_1.bottom = new FormAttachment(0, 135);
		formData_1.right = new FormAttachment(deleteButton, 0, SWT.RIGHT);
		formData_1.top = new FormAttachment(0, 110);
		formData_1.left = new FormAttachment(deleteButton, -53, SWT.RIGHT);
		button.setLayoutData(formData_1);
		button.setText("保存");
		
			
		button.addSelectionListener(new SelectionListener(){

			public void widgetSelected(SelectionEvent e) {
				up= new double[table.getItemCount()];
				down= new double[table.getItemCount()];
				s= new int[table.getItemCount()];content c;
				c = (content) tv.getElementAt(0);
				  up[0] = c.getstart();
				  down[0]=c.getend();
					s[0]=c.getclassfy();
					for (int i = 1; i < table.getItemCount(); i++) {
						c = (content) tv.getElementAt(i);
						//c1=(content) tv.getElementAt(i-1);
						up[i] = c.getstart();
						down[i] = c.getend();
						s[i] = c.getclassfy();}
				Shell shell=new Shell();
				FileDialog saveDialog=new FileDialog(shell,SWT.SAVE);
		  		saveDialog.setFilterExtensions(new String[]{"*.shp","*.tab","*.mif"});
		  		saveDialog.setFileName("");
		  		saveDialog.setText("保存");
		  		saveDialog.open();
		  		String dir=saveDialog.getFileName();
		  		RankCMap r=new RankCMap(map,up,down,s);
		  		
		  		if(dir!=""){
		  			int a=dir.lastIndexOf(".");
		  		
		  			String sub=dir.substring(a);
		  			String path=saveDialog.getFilterPath();
		  			try {
		  				if(sub.toLowerCase().equals(".shp")){
						r.saveAsShapeFile(path+File.separator+dir);}
		  				if(sub.toLowerCase().equals(".tab")){
		  					r.saveAsTab(path+File.separator+dir);
		  				}
		  				if(sub.toLowerCase().equals(".mif")){
		  					r.saveAsMif(path+File.separator+dir);
		  				}
					} catch (UnknownHostException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		  			
		  		
		  			
		  			shell.close();
		  		}else{
		  			MessageDialog.openError(shell,"保存出错","文件名不存在!");
		  		}
//		        shell.close();
		      
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		
			
		});

		final Group group_1 = new Group(container, SWT.NONE);
		group_1.setText("等值线分值设定");
		final FormData formData_2 = new FormData();
		formData_2.bottom = new FormAttachment(100, -5);
		formData_2.right = new FormAttachment(0, 305);
		formData_2.top = new FormAttachment(0, 240);
		formData_2.left = new FormAttachment(group, 0, SWT.LEFT);
		group_1.setLayoutData(formData_2);
		group_1.setLayout(new FormLayout());

		final Label label = new Label(group_1, SWT.NONE);
		final FormData formData_3 = new FormData();
		formData_3.bottom = new FormAttachment(0, 36);
		formData_3.right = new FormAttachment(0, 55);
		formData_3.top = new FormAttachment(0, 16);
		formData_3.left = new FormAttachment(0, 5);
		label.setLayoutData(formData_3);
		label.setText("起始分数");

		final Label label_1 = new Label(group_1, SWT.NONE);
		final FormData formData_3_1 = new FormData();
		formData_3_1.bottom = new FormAttachment(0, 72);
		formData_3_1.top = new FormAttachment(0, 52);
		formData_3_1.right = new FormAttachment(0, 55);
		formData_3_1.left = new FormAttachment(0, 5);
		label_1.setLayoutData(formData_3_1);
		label_1.setText("结束分数");

		final Label label_2 = new Label(group_1, SWT.NONE);
		final FormData formData_3_2 = new FormData();
		formData_3_2.bottom = new FormAttachment(0, 108);
		formData_3_2.top = new FormAttachment(0, 88);
		formData_3_2.right = new FormAttachment(0, 55);
		formData_3_2.left = new FormAttachment(0, 5);
		label_2.setLayoutData(formData_3_2);
		label_2.setText("间隔分数");

		beginText = new Text(group_1, SWT.BORDER);
		final FormData formData_5 = new FormData();
		formData_5.bottom = new FormAttachment(label, 0, SWT.BOTTOM);
		formData_5.right = new FormAttachment(0, 145);
		formData_5.top = new FormAttachment(label, 0, SWT.TOP);
		formData_5.left = new FormAttachment(0, 65);
		beginText.setLayoutData(formData_5);
		beginText.addVerifyListener(new VerifyListener(){

			public void verifyText(VerifyEvent e) {
				boolean b = ("0123456789".indexOf(e.text) >= 0);
				e.doit = b;

			}
			
		});

		endText = new Text(group_1, SWT.BORDER);
		final FormData formData_5_1 = new FormData();
		formData_5_1.bottom = new FormAttachment(label_1, 0, SWT.BOTTOM);
		formData_5_1.top = new FormAttachment(label_1, 0, SWT.TOP);
		formData_5_1.right = new FormAttachment(beginText, 0, SWT.RIGHT);
		formData_5_1.left = new FormAttachment(beginText, 0, SWT.LEFT);
		endText.setLayoutData(formData_5_1);
		endText.addVerifyListener(new VerifyListener(){

			public void verifyText(VerifyEvent e) {
				boolean b = ("0123456789".indexOf(e.text) >= 0);
				e.doit = b;
				
			}});

		intervalText = new Text(group_1, SWT.BORDER);
		final FormData formData_5_2 = new FormData();
		formData_5_2.bottom = new FormAttachment(label_2, 0, SWT.BOTTOM);
		formData_5_2.top = new FormAttachment(label_2, 0, SWT.TOP);
		formData_5_2.right = new FormAttachment(endText, 0, SWT.RIGHT);
		formData_5_2.left = new FormAttachment(endText, 0, SWT.LEFT);
		intervalText.setLayoutData(formData_5_2);
		intervalText.addVerifyListener(new VerifyListener(){

			public void verifyText(VerifyEvent e) {
				boolean b = ("0123456789".indexOf(e.text) >= 0);
				e.doit = b;
				
			}});
		

		final Button overButton = new Button(group_1, SWT.NONE);
		final FormData formData_7 = new FormData();
		formData_7.bottom = new FormAttachment(0, 115);
		formData_7.right = new FormAttachment(100, -5);
		formData_7.top = new FormAttachment(intervalText, 0, SWT.TOP);
		formData_7.left = new FormAttachment(0, 240);
		overButton.setLayoutData(formData_7);
		overButton.setText("提交");
		overButton.addSelectionListener(new SelectionListener(){

			public void widgetSelected(SelectionEvent e) {
			    table.removeAll();
				double T1=Double.parseDouble(beginText.getText());
				double T2=Double.parseDouble(endText.getText());
				double T3=Double.parseDouble(intervalText.getText());
				if((T2-T1)%T3==0){
					int i=(int) ((T2-T1)/T3);
					double start1[]=new double[i];
					double end1[]=new double[i];
					int classfy[]=new int[i];
					for(int g=0;g<i;g++){
						
						start1[g]=T1+T3*g;
						end1[g]=start1[g]+T3;
						classfy[g]=g+1;
						content c=new content();
						c.setstart(start1[g]);
						c.setend(end1[g]);
						c.setclassfy(classfy[g]);
						tv.add(c);
					}
				}else{
					int i=(int) (((T2-T1)/T3)+1);
					double start1[]=new double[i];
					double end1[]=new double[i];
					int classfy[]=new int[i];
					for(int g=0;g<i;g++){
						
						start1[g]=T1+T3*g;
						end1[g]=start1[g]+T3;
						end1[i-1]=T2;
						classfy[g]=g+1;
						content c=new content();
						c.setstart(start1[g]);
						c.setend(end1[g]);
						
						c.setclassfy(classfy[g]);
						tv.add(c);
					
				}
			} 
				
				}

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
			
		});

		return container;
	}

	/**
	 * Create contents of the button bar
	 * 
	 * @param parent
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, "确定", true);
		createButton(parent, IDialogConstants.CANCEL_ID, "取消", false);
	}

	protected void buttonPressed(int buttonId) {

		if (buttonId == IDialogConstants.OK_ID) {
			
			try {
//				if(!(table.getItemCount()==0)){
//					super.buttonPressed(buttonId);
//				}
//				else{
					save();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		} else {
			super.buttonPressed(buttonId);
		}

	}

	/**
	 * Return the initial size of the dialog
	 */
	protected Point getInitialSize() {
		return new Point(420, 457);
	}

	private void save() throws IOException, IOException {
	
		up= new double[table.getItemCount()];
		down= new double[table.getItemCount()];
		s= new int[table.getItemCount()];
		boolean v=true;
		content c;
//		content c1;
//		
		c = (content) tv.getElementAt(0);
		  up[0] = c.getstart();
		  down[0]=c.getend();
			s[0]=c.getclassfy();
			
		for (int i = 1; i < table.getItemCount(); i++) {
			c = (content) tv.getElementAt(i);
			//c1=(content) tv.getElementAt(i-1);
			up[i] = c.getstart();
			down[i] = c.getend();
			s[i] = c.getclassfy();
			
			if (up[i] < down[i-1]) {
				
				
				v=false;
				Shell shell = new Shell();
				MessageBox messageBox = new MessageBox(shell);
				messageBox.setMessage("请检查分值设置！");
				messageBox.open();
				
			}
		}
		if(v){
			super.buttonPressed(IDialogConstants.OK_ID);
		}
		
	}

	private class MyCellModifier implements ICellModifier {
		private String tmp;

		public boolean canModify(Object element, String property) {
			return true;
		}

		public Object getValue(Object element, String property) {
			content c = (content) element;
			if (property.equals("0") || property.equals("1")
					|| property.equals("2")) {
				return "";
			}
			return null;
		}

		public void modify(Object element, String property, Object value) {
			tmp = (String) value;
			TableItem tableItem = (TableItem) element;
			content c = (content) tableItem.getData();
			if (tmp.equals("")) {

				if (property.equals("0")) {
					c.setstart(0);
				} else if (property.equals("1")) {
					c.setend(0);
				} else if (property.equals("2")) {
					c.setclassfy(0);
				} else {
					return;
				}
			} else {
				if (property.equals("0")) {

					Double newValue = Double.valueOf(value.toString());
					c.setstart(newValue.doubleValue());
				}
				if (property.equals("1")) {
					Double newValue = Double.valueOf(value.toString());
					c.setend(newValue.doubleValue());
				}
				if (property.equals("2")) {
					Integer newValue = Integer.valueOf(value.toString());
					c.setclassfy(newValue.intValue());
				}
			}

			tv.update(c, null);

		}

	}

	public double[] getDown() {
		return down;
	}

	public int[] getS() {
		return s;
	}

	public double[] getUp() {
		return up;
	}

}
