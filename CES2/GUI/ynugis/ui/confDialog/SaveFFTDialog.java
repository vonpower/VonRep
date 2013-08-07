/**
 * @author yddy,create date 2003-11-7 
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.confDialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import ynugis.ffsystem.FFSystem;
import ynugis.ffsystem.FFTable;

public class SaveFFTDialog extends Dialog {
	private final String FFT_FILE_EXT="*.fft"; 
	
	private FFTable fftable;
	private String dir;
	private Text dirText;
	private Button dirBtn;
	private Button templateBtn;

	public SaveFFTDialog(Shell parent,FFTable table) {
			super(parent);
			fftable=table;
	}
	public SaveFFTDialog(FFTable table){
		super(new Shell());
		fftable=table;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		GridLayout contentLay=new GridLayout();
		contentLay.marginTop=10;
		contentLay.marginRight=10;
		contentLay.marginBottom=10;
		contentLay.marginLeft=10;
		contentLay.numColumns=4;
		Composite content=new Composite(parent,SWT.NONE);
		content.setLayout(contentLay);
		new Label(content,SWT.NONE).setText("文件名及路径");
		GridData dirLay=new GridData();
		dirLay.minimumWidth=50;
		dirText=new Text(content,SWT.BORDER);
		dirText.setLayoutData(dirLay);
		dirBtn=new Button(content,SWT.NONE);
		dirBtn.setText("保存到文件");
		dirBtn.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				dir=dirText.getText();
				if(dir.equals("")){
					FileDialog save=new FileDialog(getShell(),SWT.SAVE);
					save.setFileName(fftable.name);
					save.setFilterExtensions(new String[]{FFT_FILE_EXT});
					save.setFilterNames(new String[]{"因素因子文件("+FFT_FILE_EXT+")"});
					save.setText("保存因素因子树");
					String dirPath=save.open();
					if(dirPath==null){
						return;
					}
					dirText.setText(dirPath);
					//fftable saving
					try {
						FFSystem.saveFFT(fftable,dirPath);
					} catch (Exception e1) {
						e1.printStackTrace();
						MessageDialog.openError(getShell(),"保存","保存失败");
					}
				}else{
					//fftable saving
					try {
						FFSystem.saveFFT(fftable,dir);
					} catch (Exception e1) {
						e1.printStackTrace();
						MessageDialog.openError(getShell(),"保存","保存失败");
					}
				}
				MessageDialog.openInformation(getShell(),"保存","保存完成");
			}
		});
		templateBtn=new Button(content,SWT.NONE);
		templateBtn.setText("保存到模板");
		templateBtn.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				dir="template";
					//fftable saving
					try {
						FFSystem.saveASTemplate(fftable);
					} catch (Exception e1) {
						e1.printStackTrace();
						MessageDialog.openError(getShell(),"保存","保存失败");
					}
					MessageDialog.openInformation(getShell(),"保存","保存完成");
			}
		});
		return content;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createButton(org.eclipse.swt.widgets.Composite, int, java.lang.String, boolean)
	 */
	protected Button createButton(Composite parent, int id, String label, boolean defaultButton) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		if(dir!=null){
			super.okPressed();
		}else{
			MessageDialog.openError(getShell(),"保存","未指定保存路径或选择保存为模板");
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#initializeBounds()
	 */
	protected void initializeBounds() {
		super.createButton((Composite)getButtonBar(),IDialogConstants.OK_ID,"确定",true);
		super.createButton((Composite)getButtonBar(),IDialogConstants.CANCEL_ID,"取消",true);
		super.initializeBounds();
	}
}
