package ynugis.estimate.page;

import java.io.IOException;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Spinner;

import com.esri.arcgis.geodatabase.IFields;
import com.esri.arcgis.interop.AutomationException;

public class EliminatePage extends WizardPage {

	private List destinationList;

	private List sourceList;

	private Spinner SDSpinner;

	/**
	 * Create the wizard
	 */
	public EliminatePage() {
		super("EliminatePage");
		setTitle("Wizard Page title");
		setDescription("Wizard Page description");
	}

	public void setFields(IFields fields) {
		initialSourceList(fields);
	}

	private void initialSourceList(IFields fields) {
		sourceList.removeAll();
		int count;
		try {
			count = fields.getFieldCount();
			if (count == 0)
				return;
			for (int i = 0; i < count; i++) {
				if (fields.getField(i).getType() == com.esri.arcgis.geodatabase.esriFieldType.esriFieldTypeDouble) {
					sourceList.add(fields.getField(i).getName());
				}
			}
		} catch (AutomationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		gridLayout.numColumns = 3;
		container.setLayout(gridLayout);
		//
		setControl(container);

		final Label label_2 = new Label(container, SWT.NONE);
		label_2.setText("请输入方差倍数：");
		new Label(container, SWT.NONE);

		SDSpinner = new Spinner(container, SWT.BORDER);
		final GridData gridData_1 = new GridData(GridData.END, GridData.CENTER, false, false);
		gridData_1.widthHint = 277;
		SDSpinner.setLayoutData(gridData_1);
		SDSpinner.setSelection(2);
		SDSpinner.setMinimum(2);
		SDSpinner.setMaximum(20);

		final Label label = new Label(container, SWT.NONE);
		label.setText("候选字段集合：");
		new Label(container, SWT.NONE);

		final Label label_1 = new Label(container, SWT.NONE);
		label_1.setLayoutData(new GridData());
		label_1.setText("检验字段集合：");

		sourceList = new List(container, SWT.BORDER);
		final GridData gridData = new GridData(GridData.FILL, GridData.FILL,
				false, false, 1, 2);
		gridData.heightHint = 130;
		sourceList.setLayoutData(gridData);

		final Button addButtom = new Button(container, SWT.NONE);
		addButtom.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
		addButtom.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String source[] = sourceList.getSelection();
				if (source.length == 0)
					return;
				for (int i = 0; i < source.length; i++) {
					destinationList.add(source[i]);
				}
			}
		});
		addButtom.setText("添加>>");

		destinationList = new List(container, SWT.BORDER);
		destinationList.setLayoutData(new GridData(GridData.FILL,
				GridData.FILL, false, false, 1, 2));
		final Button DelButton = new Button(container, SWT.NONE);
		DelButton.setLayoutData(new GridData(GridData.BEGINNING, GridData.END, false, true));
		DelButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				destinationList.remove(destinationList.getSelectionIndices());
			}
		});
		DelButton.setText("<<移除");
	}

	public String[] getCheckFields() {
		return destinationList.getItems();
	}

	public int getSDNum() {
		return SDSpinner.getDigits();
	}
}
