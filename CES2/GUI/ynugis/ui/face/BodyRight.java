package ynugis.ui.face;

import java.awt.Frame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

public class BodyRight extends Composite {
	private Frame tocFrame;

	public BodyRight(Composite parent) {
		super(parent,SWT.NONE);
		setSize(new Point(300, 200));
		FillLayout layout=new FillLayout(SWT.FILL);
		layout.marginHeight=0;
		layout.marginWidth=0;
		setLayout(layout);
		createContent(this);
	}
	public BodyRight(MainFace face){
		super(face.getBodyForm(), SWT.EMBEDDED);
		setSize(new Point(300, 200));
		FillLayout layout=new FillLayout(SWT.FILL);
		layout.marginHeight=0;
		layout.marginWidth=0;
		setLayout(layout);
		createContent(this);
	}

	public Frame getTocFrame(){
		return tocFrame;
	}
	private void createContent(Composite p){
		tocFrame=SWT_AWT.new_Frame(p);
	}
}
