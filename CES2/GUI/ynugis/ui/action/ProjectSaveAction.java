/**
 * @author yddy,create date 2003-10-15 Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.action;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.widgets.DirectoryDialog;

import ynugis.classifyBusiness.ClassifyProject;
import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class ProjectSaveAction extends Action {
	private MainFace	face;
	private DirectoryDialog savedlg;
	

	public ProjectSaveAction(MainFace m) {
		face = m;
		this.setText("保存");
		this.setToolTipText("保存项目信息");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_PROSAVE));
		}
	}

	public void run() {
		
		//face.getCescore().getCEManager().createProject();
		if(face.getCescore().getCEManager().getCurrentProjectCount()==0){
			return;
		}else{
		ClassifyProject p=face.getCescore().getCEManager().getProject();
		try {
			p.save();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
		
	
	}
	
	
	
	
	
}
