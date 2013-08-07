/**
 * @author yddy,create date 2003-10-14 
 * Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageRegistry;

import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class AboutAction extends Action {
	private MainFace face;

	public AboutAction(MainFace m) {
		face=m;
		this.setText("关于我们");
		this.setToolTipText("开发者信息");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_ABOUT));
		}
	}
	public void run(){
		MessageDialog.openInformation(face.getShell(),"开发团队","Coded by\n雨田\n云之恋\nvonpower\n飘过的8过(名称长度序)\nGISLAB@Yunnan University");
//		{
//			PicStore p=new PicStore(face.getShell(),null);
//			p.open();
//		}
	}
}
