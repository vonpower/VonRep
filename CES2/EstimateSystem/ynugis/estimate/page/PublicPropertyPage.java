/*
* @author 冯涛，创建日期：2003-11-25
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
package ynugis.estimate.page;
import java.lang.reflect.Field;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import ynugis.estimate.data.DoubleCell;
import ynugis.estimate.data.PublicProperty;

public class PublicPropertyPage extends WizardPage {

	private PublicProperty publicProperty;
	private Table table;
	private TableViewer tableViewer;
	/**
	 * Create the wizard
	 */
	public PublicPropertyPage() {
		super("PublicPropertyPage");
		
		publicProperty=new PublicProperty();
	}

	/**
	 * Create contents of the wizard
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new FillLayout());
		//
		setControl(container);

		tableViewer = new TableViewer(container, SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL);
		table = tableViewer.getTable();
		
		tableViewer.setLabelProvider(new TableLabelProvider());
		tableViewer.setContentProvider(new ContentProvider());
		tableViewer.setCellModifier(new CellModifier());
		tableViewer.setInput(publicProperty);
		
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		// 设置表头
		TableColumn col1 = new TableColumn(table, SWT.NONE|SWT.READ_ONLY);
		
		col1.setAlignment(SWT.CENTER);
		col1.setText("公共属性名称");
		col1.setWidth(100);
	
		TableColumn col2 = new TableColumn(table, SWT.NONE);
		col2.setAlignment(SWT.CENTER);
		col2.setText("公共属性值");
		col2.setWidth(50);
		
		/*
		 * 为表格添加修改功能
		 */
		// 定义表格的列Alias
		tableViewer.setColumnProperties(new String[] { "公共属性名称", "公共属性值"});
		CellEditor[] cellEditor = new CellEditor[2];
		cellEditor[0] =null;
		cellEditor[1] = new TextCellEditor(tableViewer.getTable());
		Text t1 = (Text) cellEditor[1].getControl();
		t1.addVerifyListener(new VerifyListener() {

			public void verifyText(VerifyEvent arg0) {
				// 只可输入数字
				arg0.doit = "0123456789.".indexOf(arg0.text) >= 0;

			}

		});
		tableViewer.setCellEditors(cellEditor);
		tableViewer.refresh();
		
	}
	
	class TableLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object element, int columnIndex) {
			if(columnIndex==0)
			{
				return ((DoubleCell)element).getname();
			}
			if(columnIndex==1)
			{
				return Double.toString(((DoubleCell)element).getdata());
			}
			return element.toString();
		}
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
	}
	/**
	 * @author VonPower 处理用setInput方法放入TableView中的数据
	 */
	class ContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			PublicProperty pp=(PublicProperty)inputElement;
			Field[] fields=pp.getClass().getFields();
			DoubleCell DCs[]=new DoubleCell[fields.length];
			for(int i=0;i<fields.length;i++)
			{
				try {
					DCs[i]=(DoubleCell)fields[i].get(pp);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			return DCs;
		}
		public void dispose() {
		}
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}
	class CellModifier implements ICellModifier {
		public boolean canModify(Object element, String property) {
			return true;
		}
		public Object getValue(Object element, String property) {
			//DoubleCell dc = (DoubleCell)element;
			
			if(property.equals("公共属性值"))
			{
				return "";
			}
			return null;
		}
		public void modify(Object element, String property, Object value) {
			TableItem tableItem = (TableItem) element;
			DoubleCell dc = (DoubleCell) tableItem.getData();
			if(property.equals("公共属性值"))
			{
				try{
					Double d=new Double(value.toString());
					dc.setdata(d.doubleValue());
				}catch(Exception e)
				{
					dc.setdata(0);
				}
			}
			tableViewer.refresh();
		
		}
	}
	public PublicProperty getPublicProperty() {
		return publicProperty;
	}

}
