/*
* @author 冯涛，创建日期：2004-1-8
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
package ynugis.estimate.page;

import java.io.IOException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;

import ynugis.geo.Utility;

import com.dataFormula.DataFormulaType;
import com.dataFormula.dataFormulaDoc;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFields;
import com.esri.arcgis.interop.AutomationException;

public class SamplePointPage extends WizardPage {

	private Combo dataCombo;
	private Text text;
	private List fieldList;
	private Text simplePointText;
	private IFeatureClass simplePointFeatureClass;
	//private IFeatureClass cellValueFeatureClass;
	DataFormulaType dft;
	/**
	 * Create the wizard
	 */
	public SamplePointPage() {
		super("SamplePointPage");
		setPageComplete(false);
		try {
			
			dataFormulaDoc dfd=new dataFormulaDoc();
			dft=new DataFormulaType(dfd.load("cesinfo\\dataFormula.xml"));
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	private void initialDataCombo() throws Exception {
		int count=dft.getDataCatalogCount();
		if(count==0)return;
		for(int i=0;i<count;i++)
		{
			dataCombo.add(dft.getDataCatalogAt(i).getName().getValue());
		}
		
	}

	/**
	 * Create contents of the wizard
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		final GridLayout gridLayout = new GridLayout();
		container.setLayout(gridLayout);
		//
		setControl(container);

		final Group group = new Group(container, SWT.NONE);
		group.setText("数据部分");
		final GridData gridData_3 = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData_3.widthHint = 322;
		gridData_3.heightHint = 305;
		
		group.setLayoutData(gridData_3);
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		group.setLayout(gridLayout_1);

		final Label label = new Label(group, SWT.NONE);
		label.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
		label.setText("请指定“样本点”数据的路径(ShapeFile文件)：");
		new Label(group, SWT.NONE);

		simplePointText = new Text(group, SWT.BORDER);
		simplePointText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dataChanged();
			}
		});
		simplePointText.setEnabled(false);
		final GridData gridData = new GridData(GridData.BEGINNING, GridData.FILL, false, false);
		gridData.widthHint = 434;
		simplePointText.setLayoutData(gridData);

		final Button button = new Button(group, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd=new FileDialog(getShell());
				fd.setFilterNames(new String[]{"样本数据点文件（ShapeFile）"});
				fd.setFilterExtensions(new String[]{"*.shp"});
				String path=fd.open();
				if(path==null)return;
				simplePointText.setText(path);
				try {
					simplePointFeatureClass=Utility.OpenFeatureClass(simplePointText.getText());
					initialFieldList(simplePointFeatureClass);
				} catch (Exception e1) {
					MessageDialog.openError(getShell(),"打开shp文件出错","打开指定的ShapeFile文件出错\n"+e1.getMessage());
					e1.printStackTrace();
				} 
				
			}
		});
		button.setLayoutData(new GridData(GridData.BEGINNING, GridData.END, false, false));
		button.setText("...");

		final Label fieldLabel = new Label(group, SWT.NONE);
		fieldLabel.setText("样本点字段：");
		new Label(group, SWT.NONE);

		fieldList = new List(group, SWT.V_SCROLL | SWT.BORDER | SWT.H_SCROLL);
		fieldList.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				String s=text.getText();
				text.setText(s+" "+fieldList.getItem(fieldList.getSelectionIndex()));
			}
		});
		fieldList.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
		new Label(group, SWT.NONE);
		
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

		final Label dataLabel = new Label(group, SWT.NONE);
		dataLabel.setText("请选择资料类型：");
		new Label(group, SWT.NONE);
		
		
		
		dataCombo = new Combo(group, SWT.NONE);
		
		dataCombo.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
		try {
			initialDataCombo();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		new Label(group, SWT.NONE);
		
		final Label formulaLable = new Label(group, SWT.NONE);
		formulaLable.setText("在下面的文本框内编辑资料计算公式：");
		new Label(group, SWT.NONE);
		
		
		

		final Label descriptionLable = new Label(group, SWT.NONE);
		descriptionLable.setText("计算方法未知");
		new Label(group, SWT.NONE);
		dataCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int idx=dataCombo.getSelectionIndex();
				try {
					String f=dft.getDataCatalogAt(idx).getFormula().getValue();
					descriptionLable.setText(dft.getDataCatalogAt(idx).getRemark().getValue());
					text.setText(f);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});

		text = new Text(group, SWT.BORDER | SWT.WRAP);
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dataChanged();
			}
		});
		final GridData gridData_4 = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData_4.heightHint = 68;
		text.setLayoutData(gridData_4);
		new Label(group, SWT.NONE);
		
		
	}
	protected void initialFieldList(IFeatureClass inFC) throws AutomationException, IOException {
		fieldList.removeAll();
		IFields fields=inFC.getFields();
		for(int i=0;i<fields.getFieldCount();i++)
		{
			fieldList.add(fields.getField(i).getName());
		}
		
		
	}


	public IFeatureClass getSimplePointFeatureClass() {
		return simplePointFeatureClass;
	}

/*	public IFeatureClass getCellValueFeatureClass() {
		return cellValueFeatureClass;
	}*/
	public String getFormula()
	{
		return text.getText();
	}
	public String getDataName()
	{
		return dataCombo.getItem(dataCombo.getSelectionIndex());
	}
	
	private void dataChanged()
	{
		if(getSimplePointFeatureClass()==null||getFormula().equals(""))
		{
			setPageComplete(false);
		}else 
		{
			setPageComplete(true);
		}
	}
	

}
