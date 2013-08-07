/**
 * @author yddy,create date 2003-11-3 
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.confDialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import ynugis.ffsystem.FFTable;

public class NameDialog extends Dialog {
	private FFTable fftable;
	private Text nameText;

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		GridLayout contentLay=new GridLayout();
		contentLay.marginTop=10;
		contentLay.marginRight=10;
		contentLay.marginBottom=10;
		contentLay.marginLeft=10;
		contentLay.numColumns=2;
		Composite content=new Composite(parent,SWT.NONE);
		content.setLayout(contentLay);
		new Label(content,SWT.NONE).setText("项目名称");
		nameText=new Text(content,SWT.BORDER);
		return content;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createButton(org.eclipse.swt.widgets.Composite, int, java.lang.String, boolean)
	 */
	protected Button createButton(Composite parent, int id, String label, boolean defaultButton) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#initializeBounds()
	 */
	protected void initializeBounds() {
		super.createButton((Composite)getButtonBar(),IDialogConstants.OK_ID,"确定",true);
		super.createButton((Composite)getButtonBar(),IDialogConstants.CANCEL_ID,"取消",true);
		super.initializeBounds();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		fftable.name=nameText.getText();
		super.okPressed();
	}

	public NameDialog(Shell parent,FFTable table) {
		super(parent);
		fftable=table;
	}
}
