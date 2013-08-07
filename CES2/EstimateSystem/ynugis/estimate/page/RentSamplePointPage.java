/*
* @author 冯涛，创建日期：2004-1-8
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
package ynugis.estimate.page;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import ynugis.geo.Utility;

import com.esri.arcgis.geodatabase.IFeatureClass;

public class RentSamplePointPage extends WizardPage {

	private Spinner SDSpinner;
	private Button landIncomeCheckBtn;
	private Button rentCheckBtn;
	private Text simplePointText;
	private IFeatureClass simplePointFeatureClass;
	private IFeatureClass cellValueFeatureClass;
	/**
	 * Create the wizard
	 */
	public RentSamplePointPage() {
		super("wizardPage");
		setTitle("房屋买卖样本点");
		setDescription("请指定“房屋买卖样本点”数据，并设定样本点的检验和剔除的相关规则");
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
		gridData_3.widthHint = 456;
		gridData_3.heightHint = 67;
		
		group.setLayoutData(gridData_3);
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		group.setLayout(gridLayout_1);

		final Label label = new Label(group, SWT.NONE);
		label.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, true, true));
		label.setText("请指定“样本点”数据的路径(ShapeFile文件)：");
		new Label(group, SWT.NONE);

		simplePointText = new Text(group, SWT.BORDER);
		simplePointText.setEnabled(false);
		final GridData gridData = new GridData(GridData.FILL, GridData.END, true, true);
		gridData.widthHint = 408;
		simplePointText.setLayoutData(gridData);

		final Button button = new Button(group, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd=new FileDialog(getShell());
				fd.setFilterNames(new String[]{"买卖样本数据点文件（ShapeFile）"});
				fd.setFilterExtensions(new String[]{"*.shp"});
				String path=fd.open();
				if(path==null)return;
				simplePointText.setText(path);
				try {
					simplePointFeatureClass=Utility.OpenFeatureClass(simplePointText.getText());
				} catch (Exception e1) {
					MessageDialog.openError(getShell(),"打开shp文件出错","打开指定的ShapeFile文件出错\n"+e1.getMessage());
					e1.printStackTrace();
				} 
				
			}
		});
		button.setLayoutData(new GridData(GridData.BEGINNING, GridData.END, false, false));
		button.setText("...");

		final Group group_1 = new Group(container, SWT.NONE);
		group_1.setText("剔除规则");
		final GridData gridData_1 = new GridData(GridData.FILL, GridData.FILL, false, true);
		gridData_1.heightHint = 58;
		group_1.setLayoutData(gridData_1);
		final GridLayout gridLayout_2 = new GridLayout();
		gridLayout_2.numColumns = 2;
		group_1.setLayout(gridLayout_2);

		final Label label_2 = new Label(group_1, SWT.NONE);
		label_2.setLayoutData(new GridData(171, SWT.DEFAULT));
		label_2.setText("请输入方差倍数：");

		SDSpinner = new Spinner(group_1, SWT.BORDER);
		SDSpinner.setSelection(2);
		SDSpinner.setMaximum(20);
		SDSpinner.setMinimum(2);
		SDSpinner.setLayoutData(new GridData(30, SWT.DEFAULT));
		rentCheckBtn = new Button(group_1, SWT.CHECK);
		rentCheckBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			}
		});
		final GridData gridData_2 = new GridData(GridData.BEGINNING, GridData.CENTER, false, true);
		gridData_2.widthHint = 198;
		rentCheckBtn.setLayoutData(gridData_2);
		rentCheckBtn.setText("对样本点的交易价进行检验并剔除");
		new Label(group_1, SWT.NONE);

		landIncomeCheckBtn = new Button(group_1, SWT.CHECK);
		landIncomeCheckBtn.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, true));
		landIncomeCheckBtn.setText("对样点的土地价进行样点检验并剔除");
		new Label(group_1, SWT.NONE);
		
		
	}
	public Button getRentCheckBtn() {
		return rentCheckBtn;
	}
	public Button getLandIncomeCheckBtn() {
		return landIncomeCheckBtn;
	}

	public IFeatureClass getSimplePointFeatureClass() {
		return simplePointFeatureClass;
	}

	/*public IFeatureClass getCellValueFeatureClass() {
		return cellValueFeatureClass;
	}*/
	public Spinner getSDSpinner() {
		return SDSpinner;
	}

}
