/**
 * @author yddy,create date 2003-11-20
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.confDialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class WaitingSync {
	private WaitingDialog	monitor;
	
	private String information;
	
	private int open;
	
	private boolean withSync;
	
	public WaitingSync(String taskname) {
		this(taskname,true);
	}

	public WaitingSync(String taskname,boolean withSync) {
		if(withSync){
			final String task=taskname;
			if (taskname == null) {
				Display.getDefault().asyncExec(new Runnable(){
					public void run() {
						monitor = new WaitingDialog();
					}
				});
			} else {
				Display.getDefault().asyncExec(new Runnable(){
					public void run() {
						monitor = new WaitingDialog(task);
					}
				});
			}
		}else{
			if (taskname == null) {
				monitor = new WaitingDialog();
			} else {
				monitor = new WaitingDialog(taskname);
			}
		}
		this.withSync=withSync;
	}
	public int open(){
		if (withSync) {
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					open = monitor.open();
				}
			});
		}else{
			open=monitor.open();
		}
		return open;
	}
	
	public void close(){
		if (withSync) {
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					monitor.close();
				}
			});
		}else{
			monitor.close();
		}
	}
	
	public void printinfo(String info){
		this.information=info+"...\n";
		if (withSync) {
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					monitor.insertInfo(information);
				}
			});
		}else{
			monitor.insertInfo(information);
		}
	}
	private class WaitingDialog extends Dialog {
		private final String	DEFAULT_TASKNAME	= "正在处理中";

		private String			taskname;
		
		private Text infoText;
		
		public WaitingDialog(){
			this(new Shell());
		}
		public WaitingDialog(String name){
			this(new Shell(),name);
		}
		public WaitingDialog(Shell parentShell) {
			super(parentShell);
			this.taskname = DEFAULT_TASKNAME;
			this.setBlockOnOpen(false);
		}

		public WaitingDialog(Shell parentShell, String name) {
			this(parentShell);
			if (taskname != null) {
				this.taskname = name + DEFAULT_TASKNAME;
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.dialogs.Dialog#initializeBounds()
		 */
		protected void initializeBounds() {
			Button cancelBtn=super.createButton((Composite) getButtonBar(),
					IDialogConstants.CANCEL_ID, "取消", true);
			cancelBtn.setEnabled(false);
			super.initializeBounds();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.dialogs.Dialog#createButton(org.eclipse.swt.widgets.Composite,
		 *      int, java.lang.String, boolean)
		 */
		protected Button createButton(Composite parent, int id, String label,
				boolean defaultButton) {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
		 */
		protected Control createDialogArea(Composite parent) {
			GridLayout gridlay = new GridLayout();
			gridlay.numColumns = 1;
			gridlay.marginTop = 10;
			gridlay.marginRight = 10;
			gridlay.marginBottom = 10;
			gridlay.marginLeft = 10;
			gridlay.horizontalSpacing = 2;
			gridlay.verticalSpacing = 10;
			Composite content = new Composite(parent, SWT.NONE);
			content.setLayout(gridlay);
			new Label(content, SWT.NONE).setText(taskname);
			infoText = new Text(content,SWT.MULTI);
			infoText.setEditable(true);
			infoText.insert("请等待...\n");
			infoText.setVisible(true);
			infoText.setSize(200, 30);
			GridData gridData = new GridData(GridData.FILL_BOTH);
			gridData.horizontalSpan = 1;
			gridData.minimumHeight = 80;
			gridData.minimumWidth = 200;
			infoText.setLayoutData(gridData);
			return super.createDialogArea(parent);
		}
		public int open(){
			int open=super.open();
			return open;
		}
		/* (non-Javadoc)
		 * @see org.eclipse.jface.dialogs.Dialog#close()
		 */
		public boolean close() {
			System.out.println("close monitor");
			return super.close();
		}
		public void insertInfo(String info){
			infoText.insert(info);
		}
	}
}
