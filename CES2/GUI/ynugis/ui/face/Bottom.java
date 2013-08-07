package ynugis.ui.face;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class Bottom extends Composite {
	private Label operation;
	private Label point;
	private Label other;

	public Bottom(Composite parent, int style) {
		super(parent, style);
		this.setLayout(new FillLayout(SWT.HORIZONTAL));
		new Label(parent,SWT.SEPARATOR);
		operation=new Label(parent,SWT.SHADOW_IN);
		operation.setText("Operation");
		operation.setData(this);
		new Label(parent,SWT.SEPARATOR);
		point=new Label(parent,SWT.SHADOW_IN);
		point.setText("Point");
		point.setData(this);
		new Label(parent,SWT.SEPARATOR);
		other=new Label(parent,SWT.SHADOW_IN);
		other.setText("Other");
		other.setData(this);
	}
}
