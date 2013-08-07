package ynugis.estimate.page;

//ijiaopackage ynugis.estimate.page;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class IndustryInfoPage1 extends WizardPage {

	private Text savePathText;
	private Text usetimeText;
	private Text revertText;
	private Button button;
	/**
	 * Create the wizard
	 */
	public IndustryInfoPage1() {
		super("wizardPage");
		setTitle("成本逼近法_2");
		setDescription("如果需要进行土地年期修正请设定以下参数：土地还原率，土地使用年期");
	}

	/**
	 * Create contents of the wizard
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		//
		setControl(container);

		revertText = new Text(container, SWT.BORDER);
		revertText.setBounds(108, 10, 275, 24);
		revertText.setEnabled(false);

		usetimeText = new Text(container, SWT.BORDER);
		usetimeText.setBounds(108, 56, 275, 25);
		usetimeText.setEnabled(false);

		final Label label_2 = new Label(container, SWT.NONE);
		label_2.setText("保存计算结果：");
		label_2.setBounds(10, 116, 80, 25);

		savePathText = new Text(container, SWT.BORDER);
		savePathText.setBounds(108, 111, 275, 25);

		button = new Button(container, SWT.NONE);
		button.setText("浏览");
		button.setBounds(395, 113, 40, 25);
		button.addSelectionListener(new SelectionListener(){

			public void widgetSelected(SelectionEvent e) {
				FileDialog fd=new FileDialog(getShell());
				fd.setFilterNames(new String[]{"文本文件（*.txt）"});
				fd.setFilterExtensions(new String[]{"*.txt"});
				String path=fd.open();
				if(path==null)return;
				savePathText.setText(path);				
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});

		final Button button1 = new Button(container, SWT.CHECK);
		button1.setText("土地还原率");
		button1.setBounds(5, 10, 85, 20);
		button1.addSelectionListener(new SelectionListener(){
           
			public void widgetSelected(SelectionEvent e) {
				
				revertText.setEnabled(true);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
			
		});
		

		final Button button2 = new Button(container, SWT.CHECK);
		button2.setText("土地使用年期");
		button2.setBounds(5, 60, 91, 20);
		button2.addSelectionListener(new SelectionListener(){

			public void widgetSelected(SelectionEvent e) {
				usetimeText.setEnabled(true);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
			
		});

		final Label label = new Label(container, SWT.NONE);
		label.setText("(%)");
		label.setBounds(395, 10, 35, 25);
	}
	public String getrevertValue(){
		String revertValue=revertText.getText();
		return revertValue;}
	
	public String getusetimeValue(){
		String usetimeValue=usetimeText.getText();
		return usetimeValue;}
	public String getResultText() {
		String savePath=savePathText.getText();
		return savePath;
	}
	
}
