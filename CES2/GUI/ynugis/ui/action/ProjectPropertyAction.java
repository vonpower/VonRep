/**
 * @author yddy,create date 2003-10-22 
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageRegistry;

import ynugis.ui.confDialog.propertyDialog;
import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class ProjectPropertyAction extends Action {
	private MainFace	face;
	

	public ProjectPropertyAction(MainFace m) {
		face = m;
		this.setText("查看");
		this.setToolTipText("查看当前项目信息");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_PROCHECK));
		}
	}
    public void run(){
    	propertyDialog u=new propertyDialog(face);
    	u.open();
//    	classify s=new classify(face.getShell());
//    	s.open();
////    	
//    	publicInfo a=new publicInfo(face.getShell());
//    	a.open();
  /*  	if (ProjectService.projectState == GIS.projectOLD){
    		AnalysisParameter ap = new AnalysisParameter(face.getShell());
    		ap.showWizard();
    	}else{
    		MessageDialog
			.openInformation(face.getShell(), "查看项目", "没有项目可供查看");
    	}*/
    }
}
