/*
* @author 冯涛，创建日期：2004-1-8
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
package ynugis.estimate.page;

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
import org.eclipse.swt.widgets.Text;

public class CalculatePage extends WizardPage {

	private Button button;
	private Button avgButton;
	private Button expButton;
	private Text savePathText;
	private Button selectPathBtn;
	
	/**
	 * Create the wizard
	 */
	public CalculatePage() {
		super("CalculatePage");
		
	}

	/**
	 * Create contents of the wizard
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout());
		//container.setLayout(new FillLayout(SWT.VERTICAL));
		//
		setControl(container);

		final Group group_1 = new Group(container, SWT.NONE);
		group_1.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));
		final GridLayout gridLayout_1 = new GridLayout();
		group_1.setLayout(gridLayout_1);
		group_1.setText("评估模型的选择");

		expButton = new Button(group_1, SWT.CHECK);
		GridData gridData_3 = new GridData(GridData.BEGINNING, GridData.FILL, false, false);
		gridData_3.widthHint = 459;
		expButton.setLayoutData(gridData_3);
		expButton.setText("指数模型");

		avgButton = new Button(group_1, SWT.CHECK);
		avgButton.setLayoutData(new GridData(GridData.BEGINNING, GridData.FILL, false, false));
		avgButton.setText("算术平均模型");

		Button button_2 = new Button(group_1, SWT.CHECK);
		button_2.setEnabled(false);
		button_2.setText("多元线性模型");

		Button button_3 = new Button(group_1, SWT.CHECK);
		button_3.setEnabled(false);
		button_3.setText("生产函数模型");

		final Button button_4 = new Button(group_1, SWT.CHECK);
		button_4.setEnabled(false);
		button_4.setText("分级回归模型");

		final Group group = new Group(group_1, SWT.NONE);
		group.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		group.setLayout(gridLayout);
		group.setText("估价结果");
		
		button = new Button(group, SWT.CHECK);
		button.setLayoutData(new GridData());
		
		button.addSelectionListener(new SelectionAdapter() {
			

			public void widgetSelected(SelectionEvent e) {
				if(button.getSelection())
				{
					//savePathText.setEnabled(true);
					selectPathBtn.setEnabled(true);
					
				}else 
				{
					//savePathText.setEnabled(false);
					selectPathBtn.setEnabled(false);
				}
				
			}
		});
		button.setText("保存估价结果");

		

		savePathText = new Text(group, SWT.BORDER);
		savePathText.setEditable(false);
	
		savePathText.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, true, false));

		selectPathBtn = new Button(group, SWT.NONE);
		selectPathBtn.setEnabled(false);
		selectPathBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				
				FileDialog fd=new FileDialog(getShell());
				fd.setFilterNames(new String[]{"文本文件（*.txt）"});
				fd.setFilterExtensions(new String[]{"*.txt"});
				String path=fd.open();
				if(path==null)return;
				savePathText.setText(path);
				
				
				
			}
		});
		selectPathBtn.setText("...");
	}
	public Button getExpButton() {
		return expButton;
	}
	public Button getAvgButton() {
		return avgButton;
	}
	public Text getResultText() {
		return savePathText;
	}
	public Button getIsSaveButton() {
		return button;
	}

}
