/*
 * @author 冯涛，创建日期：2003-11-25
 * blog--http://apower.blogone.net
 * QQ:17463776
 * MSN:apower511@msn.com
 * E-Mail:VonPower@Gmail.com
 */
package ynugis.estimate.page;

import java.text.DecimalFormat;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import ynugis.classifyBusiness.ClassifyProject;
import ynugis.classifyBusiness.classifyItem.CellValueCMap;
import ynugis.estimate.data.Revise;
import ynugis.estimate.data.RevisePair;
import ynugis.ffsystem.FFTable;

public class DataSpecifyPage extends WizardPage {
	private Table table;

	private Combo combo;

	private Revise classifyRevise;

	private TableViewer tableViewer;

	private String[] classifyNum;

	private CellValueCMap cellValueMap;

	private ClassifyProject project;

	/**
	 * Create the wizard
	 */
	public DataSpecifyPage(ClassifyProject inP) {
		super("DataSpecifyPage");
		project = inP;
		
		classifyRevise = new Revise("codegrid");
		classifyRevise.addRevisePair(new RevisePair(77, 100, 1));
		classifyRevise.addRevisePair(new RevisePair(81, 100, 1.1));
		classifyRevise.addRevisePair(new RevisePair(77, 81, 1.2));
		classifyRevise.addRevisePair(new RevisePair(69, 77, 2));
		classifyRevise.addRevisePair(new RevisePair(52, 69, 3));
		classifyRevise.addRevisePair(new RevisePair(26, 52, 4));
		classifyRevise.addRevisePair(new RevisePair(0,26 , 5));
		

		classifyNum = new String[100];
		double seed = 1;
		DecimalFormat df = new DecimalFormat("#.0");
		for (int i = 0; i < classifyNum.length; i++) {
			classifyNum[i] = df.format(seed);
			seed += 0.1;
		}

	}

	/**
	 * Create contents of the wizard
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);
		//
		setControl(container);

		final Label label = new Label(container, SWT.NONE);
		final GridData gridData = new GridData(GridData.CENTER,
				GridData.CENTER, false, false);
		gridData.widthHint = 224;
		label.setLayoutData(gridData);
		label.setText("请选择本次估价使用的单元格分值图：");

		combo = new Combo(container, SWT.NONE);
		combo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dataChanged();
			}
		});
		combo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				System.out.println("combo.getSelectionIndex()  -   "+combo.getSelectionIndex());
				//combo.select(e.);
			}
		});

		final GridData gridData_1 = new GridData(GridData.CENTER,
				GridData.BEGINNING, false, false);
		gridData_1.widthHint = 223;
		combo.setLayoutData(gridData_1);
		

		final Label label_1 = new Label(container, SWT.SEPARATOR
				| SWT.HORIZONTAL);
		label_1.setLayoutData(new GridData(GridData.FILL, GridData.CENTER,
				false, false, 2, 1));

		final Label label_2 = new Label(container, SWT.NONE);
		label_2.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER,
				false, false, 2, 1));
		label_2.setText("请指定估价的分级、分段规则：（在表格中点右键选“添加”，来添加新的分级、分段规则）");
		initialTableView(container);

		// 将当前可用的单元格分值图放入combo中
		if(project!=null)
		{
		FFTable ffts[] = project.getAllCalculatedFFT();
		if (ffts != null) {
			for (int i = 0; i < ffts.length; i++) {
				
					combo.add(ffts[i].name + "： "
							+ ffts[i].getCellValueCMap().getName());
					combo.setData(i + "", ffts[i].getCellValueCMap());
				
			}
			combo.select(0);

		}
		}

	}

	/**
	 * 创建并初始化TableView
	 * 
	 * @param container
	 */
	private void initialTableView(Composite container) {
		tableViewer = new TableViewer(container, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL);

		table = tableViewer.getTable();
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
		col1.setText("等级区段");
		col1.setWidth(100);

		TableColumn col2 = new TableColumn(table, SWT.NONE);
		col2.setAlignment(SWT.CENTER);
		col2.setText("起始分数");
		col2.setWidth(100);

		TableColumn col3 = new TableColumn(table, SWT.NONE);
		col3.setAlignment(SWT.CENTER);
		col3.setText("终止分数");
		col3.setWidth(100);

		/*
		 * 为表格添加修改功能
		 */
		// 定义表格的列Alias
		tableViewer
				.setColumnProperties(new String[] { "等级区段", "起始分数", "终止分数" });
		CellEditor[] cellEditor = new CellEditor[3];
		cellEditor[0] = new ComboBoxCellEditor(tableViewer.getTable(),
				classifyNum, SWT.READ_ONLY);
		cellEditor[1] = new TextCellEditor(tableViewer.getTable());
		cellEditor[2] = new TextCellEditor(tableViewer.getTable());
		Text t1 = (Text) cellEditor[1].getControl();
		t1.addVerifyListener(new VerifyListener() {

			public void verifyText(VerifyEvent arg0) {
				// 只可输入数字
				arg0.doit = "0123456789.".indexOf(arg0.text) >= 0;

			}

		});
		Text t2 = (Text) cellEditor[2].getControl();
		t2.addVerifyListener(new VerifyListener() {

			public void verifyText(VerifyEvent arg0) {
				// 只可输入数字
				arg0.doit = "0123456789.".indexOf(arg0.text) >= 0;
			}

		});

		/*
		 * 设置tableView
		 */
		tableViewer.setCellEditors(cellEditor);

		tableViewer.setCellModifier(new CellModifier());
		tableViewer.setLabelProvider(new TableLabelProvider());
		tableViewer.setContentProvider(new ContentProvider());
		tableViewer.setInput(getClassifyRevise());

		// 使用弹出式菜单，为表格添加新的Row
		Menu popMenu = new Menu(getShell(), SWT.POP_UP);

		MenuItem newRevisePairItem = new MenuItem(popMenu, SWT.PUSH);
		newRevisePairItem.setText("添加");
		newRevisePairItem.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent arg0) {
				classifyRevise.addRevisePair(new RevisePair(0, 0, 0));
				tableViewer.refresh();
			}

			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		MenuItem deleteRevisePairItem = new MenuItem(popMenu, SWT.PUSH);
		deleteRevisePairItem.setText("删除");
		deleteRevisePairItem.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent arg0) {
				if(classifyRevise.getRevisePairCount()==0)return;
				classifyRevise.removeRevisePair(classifyRevise.getRevisePairCount()-1);
				tableViewer.refresh();
			}

			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		table.setMenu(popMenu);

	}

	/**
	 * @return Returns the classifyRevise.
	 */
	public Revise getClassifyRevise() {

		return classifyRevise;
	}

	/**
	 * @param classifyRevise
	 *            The classifyRevise to set.
	 */
	public void setClassifyRevise(Revise classifyRevise) {
		this.classifyRevise = classifyRevise;
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
			RevisePair rp = (RevisePair) element;

			if (property.equals("等级区段")) {
				return new Integer(0);
			}
			if (property.equals("起始分数")) {
				return Double.toString(rp.getStart());
			}
			if (property.equals("终止分数")) {
				return Double.toString(rp.getEnd());
			}

			return "";
		}

		/*
		 * (non-Javadoc) 把修改的值放到适当的数据结构中
		 * 
		 * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object,
		 *      java.lang.String, java.lang.Object)
		 */
		public void modify(Object element, String property, Object value) {
			TableItem tableItem = (TableItem) element;
			RevisePair rp = (RevisePair) tableItem.getData();
			if (property.equals("等级区段")) {
				Integer comboIdx = (Integer) value;

				rp.setReviseValue(Double.parseDouble(classifyNum[comboIdx
						.intValue()]));
			} else if (property.equals("起始分数")) {

				try {
					Double s = new Double(value.toString());
					rp.setStart(s.doubleValue());
				} catch (Exception e) {
					rp.setStart(0);
				}
			} else if (property.equals("终止分数")) {
				try {
					Double e = new Double(value.toString());
					rp.setEnd(e.doubleValue());
				} catch (Exception e) {
					rp.setEnd(0);
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
				return "错误";
			}
			RevisePair rp = (RevisePair) element;

			if (columnIndex == 0)
				return "" + rp.getReviseValue();
			if (columnIndex == 1)
				return "" + rp.getStart();
			if (columnIndex == 2)
				return "" + rp.getEnd();
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

	public CellValueCMap getCellValueMap() {
		Object o = combo.getData(combo.getSelectionIndex() + "");
		if (o instanceof CellValueCMap) {
			setCellValueMap((CellValueCMap) o);
		}

		return cellValueMap;
	}

	public void setCellValueMap(CellValueCMap cellValueMap) {
		this.cellValueMap = cellValueMap;
	}
	private void dataChanged()
	{
		/*if(getCellValueMap()==null||getClassifyRevise()==null)
		{
			setPageComplete(false);
		}else 
		{
			setPageComplete(true);
		}*/
	}
}
