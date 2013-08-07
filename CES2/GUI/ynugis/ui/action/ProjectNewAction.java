/**
 * @author yddy,create date 2003-10-15 Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageRegistry;

import ynugis.ui.confDialog.prjDialog;
import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class ProjectNewAction extends Action {
	private MainFace	face;

	public ProjectNewAction(MainFace m) {
		face = m;
		this.setText("新建");
		this.setToolTipText("新建项目");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_PRONEW));
		}
	}
	

	public void run() {
/*		if (ProjectService.projectState == GIS.projectOLD) {
			if (ProjectService.projectSvd == false) {
				MessageBox msgBox = new MessageBox(face.getShell(),
						SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL);
				msgBox.setMessage("已存在正在使用的项目,需要先保存当前项目信息吗");
				msgBox.setText("保存项目");
				int save = msgBox.open();
				if (save == SWT.YES) {
					System.out.println("SAVING");
					ProjectService.saveProject2XML(face.getShell());
				} else if (save == SWT.CANCEL) {
					return;
				}
			}
		}
		ProjectService.projectState = GIS.projectNEW;
		AnalysisParameter ap = new AnalysisParameter(face.getShell());
		ap.showWizard();*/
		if(face.getCescore().getCEManager().getProject()!=null)
		{
			MessageDialog.openError(face.getShell(),"Error","请先关闭当前项目");
			return ;
		}
		prjDialog u=new prjDialog(face);
		u.open();
	}
}
