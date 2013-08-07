/*
 * @author 冯涛，创建日期：2004-1-8
 * blog--http://apower.blogone.net
 * QQ:17463776
 * MSN:apower511@msn.com
 * E-Mail:VonPower@Gmail.com
 */
package ynugis.estimate.page;

import java.io.IOException;
import java.util.ArrayList;

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
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import ynugis.estimate.data.Revise;
import ynugis.estimate.data.RevisePair;

import com.esri.arcgis.geodatabase.IFields;
import com.esri.arcgis.interop.AutomationException;

public class RevisePage extends WizardPage {

	private Combo fieldCombo;

	private List fieldList;

	private TableViewer tableViewer;

	private Table table;

	private Revise[] revises = null;

	private ArrayList reviseAL = new ArrayList();

	/**
	 * Create the wizard
	 */
	public RevisePage() {
		super("RevisePage");

		// floorRevise=new Revise("样点所在","房屋楼层修正");
	}

	public void setFields(IFields fields) {

		try {
			initialFieldCombo(fields);
			
		} catch (AutomationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initialFieldCombo(IFields fields) throws AutomationException,
			IOException {
		fieldCombo.removeAll();
		int count = fields.getFieldCount();
		if (count == 0)
			return;
		for (int i = 0; i < count; i++) {
			// 如果为double型就添加到下拉框中
			if (fields.getField(i).getType() == com.esri.arcgis.geodatabase.esriFieldType.esriFieldTypeDouble) {
				fieldCombo.add(fields.getField(i).getName());
			}
		}

	}

	/**
	 * Create contents of the wizard
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new FillLayout());
		//
		setControl(container);
		/*
		 * list.add(getCapacityRevise().getName());
		 * list.add(getWeightRevise().getName());
		 * list.add(getDepthRevise().getName());
		 */

		final SashForm sashForm = new SashForm(container, SWT.VERTICAL);

		final SashForm sashForm_1 = new SashForm(sashForm, SWT.NONE);

		fieldCombo = new Combo(sashForm_1, SWT.NONE);
		fieldCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int idx = fieldCombo.getSelectionIndex();
				String f = fieldCombo.getItem(idx);
				if (fieldList.indexOf(f) == -1) {
					fieldList.add(f + "修正");
					reviseAL.add(new Revise(f));
				}

			}
		});

		sashForm_1.setWeights(new int[] { 1 });

		fieldList = new List(sashForm, SWT.BORDER);
		
		fieldList.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				int idx = fieldList.getSelectionIndex();
				Revise r = (Revise) reviseAL.get(idx);
				
				setDescription("当前选中的修正表为：『" + r.getReviseFieldName()
						+ "』,您可以在右边的表格中点击鼠标右键－》“添加”，来添加该表的修正规则。");
				tableViewer.setInput(r);
				table.setEnabled(true);
				tableViewer.refresh();

			}
		});
		
		
		Menu fieldListPopMenu = new Menu(getShell(), SWT.POP_UP);
		fieldList.setMenu(fieldListPopMenu);
		MenuItem delFieldItem = new MenuItem(fieldListPopMenu, SWT.PUSH);
		delFieldItem.setText("删除");
		delFieldItem.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent arg0) {
				fieldList.remove(fieldList.getSelectionIndices());
			}

			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		
	
		tableViewer = new TableViewer(sashForm, SWT.BORDER);
		tableViewer.setLabelProvider(new TableLabelProvider());
		tableViewer.setContentProvider(new ContentProvider());
		tableViewer.setCellModifier(new CellModifier());
		tableViewer.setInput(new Object());
		table = tableViewer.getTable();
		table.setEnabled(false);
		final GridData gridData_2 = new GridData(GridData.FILL, GridData.FILL,
				true, true, 2, 1);
		gridData_2.heightHint = 137;
		gridData_2.widthHint = 461;
		table.setLayoutData(gridData_2);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		// 设置表头
		TableColumn col1 = new TableColumn(table, SWT.NONE | SWT.READ_ONLY);

		col1.setAlignment(SWT.CENTER);
		col1.setText("起始值");
		col1.setWidth(100);

		TableColumn col2 = new TableColumn(table, SWT.NONE);
		col2.setAlignment(SWT.CENTER);
		col2.setText("终止值");
		col2.setWidth(100);

		TableColumn col3 = new TableColumn(table, SWT.NONE);
		col3.setAlignment(SWT.CENTER);
		col3.setText("修正系数");
		col3.setWidth(100);
		// 定义表格的列Alias
		tableViewer.setColumnProperties(new String[] { "起始值", "终止值", "修正系数" });
		CellEditor cellEditor[] = new CellEditor[3];
		cellEditor[0] = new TextCellEditor(tableViewer.getTable());
		cellEditor[1] = new TextCellEditor(tableViewer.getTable());
		cellEditor[2] = new TextCellEditor(tableViewer.getTable());
		tableViewer.setCellEditors(cellEditor);

		tableViewer.setCellModifier(new CellModifier());
		tableViewer.setLabelProvider(new TableLabelProvider());
		tableViewer.setContentProvider(new ContentProvider());
		tableViewer.setInput(new Object());

		// 使用弹出式菜单，为表格添加新的Row
		Menu tableViewPopMenu = new Menu(getShell(), SWT.POP_UP);

		MenuItem newRevisePairItem = new MenuItem(tableViewPopMenu, SWT.PUSH);
		newRevisePairItem.setText("添加");
		newRevisePairItem.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent arg0) {
				Revise r = (Revise) tableViewer.getInput();
				r.addRevisePair(new RevisePair(0, 0, 0));
				tableViewer.refresh();
			}

			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		MenuItem deleteRevisePairItem = new MenuItem(tableViewPopMenu, SWT.PUSH);
		deleteRevisePairItem.setText("删除");
		deleteRevisePairItem.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent arg0) {
				Revise r = (Revise) tableViewer.getInput();
				if (r.getRevisePairCount() == 0)
					return;
				r.removeRevisePair(r.getRevisePairCount() - 1);

				tableViewer.refresh();
			}

			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		table.setMenu(tableViewPopMenu);
		sashForm.setWeights(new int[] { 31, 59, 88 });

		/*
		 * 
		 * Table View Part
		 * 
		 * 
		 */

		/*
		 * 为表格添加修改功能
		 */

		VerifyListener numOnly = new VerifyListener() {

			public void verifyText(VerifyEvent arg0) {
				// 只可输入数字
				arg0.doit = "0123456789.".indexOf(arg0.text) >= 0;

			}

		};
		((Text) cellEditor[0].getControl()).addVerifyListener(numOnly);
		((Text) cellEditor[1].getControl()).addVerifyListener(numOnly);
		((Text) cellEditor[2].getControl()).addVerifyListener(numOnly);

		/*
		 * 设置tableView
		 */

	}

	/**
	 * @author Administrator 修改TableView中的cell
	 */
	class CellModifier implements ICellModifier {
		public boolean canModify(Object element, String property) {
			// 使得所有的cell都可以修改
			return true;
		}

		/*
		 * (non-Javadoc) 单表格单元处于修改状态时，表格单元的值
		 * 
		 * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object,
		 *      java.lang.String)
		 */
		public Object getValue(Object element, String property) {
			if(!(element instanceof RevisePair))return null;
			RevisePair rp = (RevisePair) element;

			if (property.equals("起始值")) {
				return Double.toString(rp.getStart());
			}
			if (property.equals("终止值")) {
				return Double.toString(rp.getEnd());
			}
			if (property.equals("修正系数")) {
				return Double.toString(rp.getReviseValue());
			}

			return "err";
		}

		/*
		 * (non-Javadoc) 把修改的值放到适当的数据结构中
		 * 
		 * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object,
		 *      java.lang.String, java.lang.Object)
		 */
		public void modify(Object element, String property, Object value) {
			if(element==null)return;
			TableItem tableItem = (TableItem) element;
			RevisePair rp = (RevisePair) tableItem.getData();
			if (property.equals("起始值")) {

				try {
					Double s = new Double(value.toString());
					rp.setStart(s.doubleValue());
				} catch (Exception e) {
					rp.setStart(0);
				}
			} else if (property.equals("终止值")) {
				try {
					Double e = new Double(value.toString());
					rp.setEnd(e.doubleValue());
				} catch (Exception e) {
					rp.setEnd(0);
				}
			} else if (property.equals("修正系数")) {
				try {
					Double e = new Double(value.toString());
					rp.setReviseValue(e.doubleValue());
				} catch (Exception e) {
					rp.setReviseValue(1);
				}

			} else {
				return;
			}
			tableViewer.refresh();

		}
	}

	/**
	 * @author Administrator 把contentProvider提供的数据显示出来
	 */
	class TableLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		public String getColumnText(Object element, int columnIndex) {
			if (!(element instanceof RevisePair)) {
				return "请选择要填写的修正表";
			}
			RevisePair rp = (RevisePair) element;

			if (columnIndex == 0)
				return "" + rp.getStart();
			if (columnIndex == 1)
				return "" + rp.getEnd();
			if (columnIndex == 2)
				return "" + rp.getReviseValue();
			return element.toString();
		}

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
	}

	/**
	 * @author Administrator 处理用setInput方法放入TableView中的数据
	 */
	class ContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof Revise) {
				return ((Revise) inputElement).getRevisePairs();
			}
			return new Object[] { "err", "err", "err" };
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	public Revise[] getRevises() {
		
		if(reviseAL.size()==0)return null;
		Object[] o=reviseAL.toArray();
		
		Revise[] revises=new Revise[o.length];
		for(int i=0;i<o.length;i++)
		{
			revises[i]=(Revise)o[i];
		}
		return revises;
	}

}
