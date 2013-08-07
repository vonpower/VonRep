package ynugis.ui.confDialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SelectionClauseDialog extends Dialog {
	private Text sqlT;
	private String sql;
	
	private String[] fieldNames;

	public SelectionClauseDialog(Shell parent,String[] fieldNames) {
		super(parent);
		this.fieldNames=fieldNames;
	}

	protected Button createButton(Composite parent, int id, String label, boolean defaultButton) {
		return null;
	}

	protected Control createDialogArea(Composite parent) {
		Composite content = new Composite(parent, SWT.NONE);
		{
			GridLayout contentLayout = new GridLayout();
			contentLayout.numColumns = 6;
			contentLayout.marginTop = 20;
			contentLayout.marginLeft = 20;
			contentLayout.marginRight = 20;
			contentLayout.marginBottom = 20;
			contentLayout.horizontalSpacing = 8;
			contentLayout.verticalSpacing = 10;
			content.setLayout(contentLayout);
		}
		final List fieldList=new List(content,SWT.READ_ONLY);
		{
			GridData gd=new GridData(GridData.FILL_BOTH);
			gd.horizontalSpan=3;
			gd.minimumWidth=80;
			gd.minimumHeight=150;
			gd.verticalSpan=5;
			fieldList.setLayoutData(gd);
			if (fieldNames!=null) {
				for (int i = 0; i < fieldNames.length; i++) {
					fieldList.add(fieldNames[i]);
				}
			}
			fieldList.addSelectionListener(new SelectionAdapter(){
				public void widgetDefaultSelected(SelectionEvent e) {
					if(fieldList.getSelectionCount()==1){
						String[] seled=fieldList.getSelection();
						sqlT.append(seled[0]);
					}
				}});
		}
		String[] operators=new String[]{"=","<>","Like","<","<=","And",">",">=","Or","_","%","(",")","Not","Is"};
		for(int i=0;i<operators.length;i++){
			final int id=i;
			final Button op=new Button(content,SWT.NONE);
			GridData gd=new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan=1;
			gd.verticalSpan=1;
			op.setLayoutData(gd);
			op.setText(operators[i]);
			op.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e) {
					String[] operators=new String[]{"=","<>","Like","<","<=","And",">",">=","Or","_","%","(",")","Not","Is"};
					sqlT.append(operators[id]);
				}});
		}
		sqlT=new Text(content,SWT.MULTI|SWT.WRAP);
		{
			GridData gd=new GridData(GridData.FILL_BOTH);
			gd.horizontalSpan=6;
			gd.verticalSpan=3;
			gd.minimumHeight=50;
			sqlT.setLayoutData(gd);
		}
		return content;
	}

	protected void initializeBounds() {
		super.createButton((Composite) getButtonBar(), IDialogConstants.OK_ID,
				"确定", true);
		super.createButton((Composite) getButtonBar(),
				IDialogConstants.CANCEL_ID, "取消", true);
		super.initializeBounds();
	}
	
	protected void okPressed() {
		this.sql=sqlT.getText();
		super.okPressed();
	}

	public String getSql(){
		return sql;
	}
}
