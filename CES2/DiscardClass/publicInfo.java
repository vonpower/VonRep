




import java.util.Iterator;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import ynugis.ui.confDialog.ContentProvider;



public class publicInfo extends Dialog {
	private Combo combo;
	private Table table;
	public CellEditor[] cellEditor;
	private TableViewer tv;
	content1 con1=new content1();
	content2 con2=new content2();
	content3 con3=new content3();
	content4 con4=new content4();
	content5 con5=new content5();
	content6 con6=new content6();
	
	// private java.util.List content;
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public publicInfo(Shell parentShell) {
		super(parentShell);
		//content = new ArrayList();
		  
	}
	
	


	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FormLayout());

		 tv= new TableViewer(container, SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL);
		table = tv.getTable();
		final FormData formData = new FormData();
		formData.bottom = new FormAttachment(0, 310);
		formData.right = new FormAttachment(0, 380);
		formData.top = new FormAttachment(0, 45);
		formData.left = new FormAttachment(0, 5);
		table.setLayoutData(formData);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		TableColumn col1 = new TableColumn(table, SWT.NONE|SWT.READ_ONLY);
	
		col1.setAlignment(SWT.CENTER);
		col1.setText("字段名称");
		col1.setWidth(200);
	
		TableColumn col2 = new TableColumn(table, SWT.NONE);
		col2.setAlignment(SWT.CENTER);
		col2.setText("字段值");
		col2.setWidth(150);
		
//		 for (int i = 0, n = table.getColumnCount(); i < n; i++) {
//		      table.getColumn(i).pack();
//		    }
		tv.setColumnProperties(new String[] { "0", "1" });
		cellEditor = new CellEditor[2];
		cellEditor[0] = new TextCellEditor(tv.getTable());;
		
		cellEditor[1] = new TextCellEditor(tv.getTable());
		tv.setCellEditors(cellEditor);
		tv.setCellModifier(new CellModifier());
		tv.setContentProvider(new ContentProvider());
		tv.setLabelProvider(new LabelProvider());
		
	
//		



		final Button add_button = new Button(container, SWT.NONE);
		final FormData formData_1 = new FormData();
		formData_1.right = new FormAttachment(0, 295);
		formData_1.top = new FormAttachment(0, 325);
		formData_1.bottom = new FormAttachment(0, 355);
		formData_1.left = new FormAttachment(0, 230);
		add_button.setLayoutData(formData_1);
		add_button.setText("添加");
		add_button.addSelectionListener(new SelectionListener(){
            int i=1;
           
			public void widgetSelected(SelectionEvent e) {
				//String[] string = { ""+i , "" + i };
				
				
//				c.setname(string[0]);
//				
//				c.setdata(string[1]);
//				
//				i++;
				
				switch(combo.getSelectionIndex()){
				
				case 0:tv.add(con1);
					   con1.addtask(con1);
				
				       break;
//				case 1:con2.addtask(c);
//				       break;
//				case 2:con3.addtask(c);
//				       break;
//				case 3:con4.addtask(c);
//				      break;
//				case 4:con5.addtask(c);
//				      break;
//				case 5:con6.addtask(c);
//				      break;
//					
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}});

		final Button delete_button = new Button(container, SWT.NONE);
		final FormData formData_2 = new FormData();
		formData_2.left = new FormAttachment(0, 310);
		formData_2.right = new FormAttachment(0, 370);
		formData_2.bottom = new FormAttachment(add_button, 30, SWT.TOP);
		formData_2.top = new FormAttachment(add_button, 0, SWT.TOP);
		delete_button.setLayoutData(formData_2);
		delete_button.setText("删除");
		delete_button.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection s=(IStructuredSelection)tv.getSelection();
				if(s.isEmpty())
					MessageDialog.openInformation(null,"提示","请先选择");
				else
					for(Iterator it=s.iterator();it.hasNext();){
						Info1 o=(Info1)it.next();
						tv.remove(o);
						switch(combo.getSelectionIndex()){
						
						case 0:con1.remove(o);
						       break;
						case 1:con2.remove(o);
						       break;
						case 2:con3.remove(o);
						       break;
						case 3:con4.remove(o);
						      break;
						case 4:con5.remove(o);
						      break;
						case 5:con6.remove(o);
						      break;
							
						}
					}
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}});

		final Button lastbutton = new Button(container, SWT.NONE);
		final FormData formData_3 = new FormData();
		formData_3.left = new FormAttachment(0, 10);
		formData_3.bottom = new FormAttachment(add_button, 30, SWT.TOP);
		formData_3.right = new FormAttachment(0, 65);
		formData_3.top = new FormAttachment(add_button, 0, SWT.TOP);
		lastbutton.setLayoutData(formData_3);
		lastbutton.setText("上条记录");

		final Button nextbutton = new Button(container, SWT.NONE);
		final FormData formData_4 = new FormData();
		formData_4.bottom = new FormAttachment(lastbutton, 0, SWT.BOTTOM);
		formData_4.right = new FormAttachment(0, 140);
		formData_4.top = new FormAttachment(lastbutton, 0, SWT.TOP);
		formData_4.left = new FormAttachment(0, 80);
		nextbutton.setLayoutData(formData_4);
		nextbutton.setText("下条记录");

		final Button savebutton = new Button(container, SWT.NONE);
		final FormData formData_5 = new FormData();
		formData_5.right = new FormAttachment(0, 215);
		formData_5.bottom = new FormAttachment(add_button, 0, SWT.BOTTOM);
		formData_5.top = new FormAttachment(nextbutton, 0, SWT.TOP);
		formData_5.left = new FormAttachment(0, 155);
		savebutton.setLayoutData(formData_5);
		savebutton.setText("保存");

		final Label label = new Label(container, SWT.NONE);
		label.setAlignment(SWT.CENTER);
		final FormData formData_6 = new FormData();
		formData_6.bottom = new FormAttachment(0, 30);
		formData_6.right = new FormAttachment(lastbutton, 110, SWT.RIGHT);
		formData_6.top = new FormAttachment(0, 10);
		formData_6.left = new FormAttachment(lastbutton, 0, SWT.RIGHT);
		label.setLayoutData(formData_6);
		label.setText("选择相应的信息表");

		combo = new Combo(container, SWT.NONE);
		final FormData formData_7 = new FormData();
		formData_7.bottom = new FormAttachment(label, 21, SWT.TOP);
		formData_7.right = new FormAttachment(table, 0, SWT.RIGHT);
		formData_7.top = new FormAttachment(label, 0, SWT.TOP);
		formData_7.left = new FormAttachment(table, -170, SWT.RIGHT);
		combo.setLayoutData(formData_7);
		combo.setItems(new String[]{"公用属性信息表"," 地价指数信息表","容积率修正系数表","地价楼层修正系数表","宗地进深修正系数表","临街宽度修正系数表"});
	   //combo.select(0);
	    combo.addSelectionListener(new SelectionListener(){

			public void widgetSelected(SelectionEvent e) {
				if(combo.getSelectionIndex()==0){
					
					tv.setInput(con1.getnames());
				}
				if(combo.getSelectionIndex()==1){
					tv.setInput(con2.getnames());
				}
				if(combo.getSelectionIndex()==2){
					tv.setInput(con3.getnames());
				}
				if(combo.getSelectionIndex()==3){
					tv.setInput(con4.getnames());
				}
				if(combo.getSelectionIndex()==4){
					tv.setInput(con5.getnames());
				}
				if(combo.getSelectionIndex()==5){
					tv.setInput(con6.getnames());
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}});
		return container;
		
	}

	/**
	 * Create contents of the button bar
	 * @param parent
	 */
	protected void createButtonsForButtonBar(Composite parent) {
	}

	/**
	 * Return the initial size of the dialog
	 */
	protected Point getInitialSize() {
		return new Point(475, 417);
	}
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			return;
		}
		super.buttonPressed(buttonId);
	}
    
     
     class CellModifier implements ICellModifier {

		public boolean canModify(Object element, String property) {
			if(property.equals("0")){
				return false;
			}else
			return true;
		}

		public Object getValue(Object element, String property) {
			Info1 c = (Info1) element;
			if(property.equals("0")||property.equals("1")){
				return "";
			}
			return null;
		}

		public void modify(Object element, String property, Object value) {
			 if (element instanceof Item)
			      element = ((Item) element).getData();
              String s=(String) value;
			   Info1 p = (Info1) element;
			   if(s.equals("")){
				   if(property.equals("1")){
					   p.setdata("");
				   }
			   }else{
				   if(property.equals("1")){
					  String newValue = String.valueOf(value.toString());
					   p.setdata(newValue);
				   }
			   }
			   tv.refresh();
		}}
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("公共属性信息输入");
	}
    
}



