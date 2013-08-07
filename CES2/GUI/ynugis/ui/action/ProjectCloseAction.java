/**
 * @author yddy,create date 2003-11-15 
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageRegistry;

import ynugis.classifyBusiness.ClassifyProject;
import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class ProjectCloseAction extends Action {
	private MainFace	face;
	ClassifyProject p;

	public ProjectCloseAction(MainFace m) {
		face = m;
		this.setText("关闭");
		this.setToolTipText("关闭项目");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_PROCLOSE));
		}
	}
	

	public void run() {
//		face.getCescore().getCEManager().createProject();
		if (face.getCescore().getCEManager().getCurrentProjectCount()==0){
			return;
		}else{
		 p=face.getCescore().getCEManager().getProject();
		 
		 face.getCesui().resetUI(null);
		 try {
			face.getCescore().resetAEControls(false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		face.getCescore().getCEManager().closeProject(p);
		
		}
		}
		//new CESUI().resetUI(null);
//		try {
//			new CESCORE().resetAEControls();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		p.close();

	}

