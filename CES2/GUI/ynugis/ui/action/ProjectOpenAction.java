/**
 * @author yddy,create date 2003-11-8 
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class ProjectOpenAction extends Action {
	private MainFace	face;
	private FileDialog dlg;
	
	
	public ProjectOpenAction(MainFace m) {
		face = m;
		this.setText("打开");
		this.setToolTipText("打开项目信息");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_PROOPEN));
		}
	}
	public void run(){
		if(face.getCescore().getCEManager().getProject()!=null)
		{
			MessageDialog.openError(face.getShell(),"Error","请先关闭当前项目");
			return ;
		}
		dlg=new FileDialog(face.getShell(),SWT.OPEN);
		//dlg.setFilterPath("c:/windows");
		dlg.setFilterNames(new String[]{"CES项目文件（*.ces)"});
		dlg.setFilterExtensions(new String[]{"*.ces"});
		String filename=dlg.open();
		if(filename!=null){
		face.getCescore().getCEManager().openProject(filename);
		face.getCesui().resetUI(face.getCescore().getCEManager().getProject());
		try {
			face.getCescore().resetAEControls(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
}
