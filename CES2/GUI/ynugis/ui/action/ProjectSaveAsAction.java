/**
 * @author yddy,create date 2003-10-22 
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;

import ynugis.classifyBusiness.ClassifyProject;
import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class ProjectSaveAsAction extends Action {
	private MainFace	face;
	private DirectoryDialog saveasDlg;

	public ProjectSaveAsAction(MainFace m) {
		face = m;
		this.setText("另存为");
		this.setToolTipText("保存项目信息");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_PROSAVE2));
		}
	}
	
	public void run() {
		
		if(face.getCescore().getCEManager().getProject()==null)return;
		
		
		Shell shell=new Shell();
		saveasDlg=new DirectoryDialog(shell,SWT.OPEN);
		saveasDlg.setMessage("请选择一个保存路径");
		saveasDlg.setText("另存为");
		saveasDlg.open();	
		if(!saveasDlg.getFilterPath().equals("")){
		try {
			save();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}else{
			
		}
			
	/*	if (ProjectService.projectState == GIS.projectOLD) {
			ProjectService.saveProject2XML(face.getShell());
		}else{
			MessageDialog
			.openInformation(face.getShell(), "保存项目", "没有项目可以保存");
		}*/
	}
	private void save() throws Exception{
		
		ClassifyProject p=face.getCescore().getCEManager().getProject();
		
		p.saveAs(saveasDlg.getFilterPath());
}
}
