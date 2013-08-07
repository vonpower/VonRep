/**
 * @author yddy,create date 2003-11-7 
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageRegistry;

import ynugis.ui.confDialog.LoadFFTDialog;
import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class FFTableDelphiAction extends Action {
	private MainFace face;

	public FFTableDelphiAction(MainFace m) {
		face=m;
		this.setText("特尔菲法");
		this.setToolTipText("特尔菲法设定因素因子权重");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_DELPHI));
		}
	}
	public void run(){
//		DialogHelper dialogHelper=new DialogHelper();
//		dialogHelper.fillFFTable(face.getShell(),new FFTable());
//		MessageDialog.openWarning(face.getShell(),"特尔菲法","罢工ing...");
		LoadFFTDialog loadDialog=new LoadFFTDialog(face.getShell());
		loadDialog.open();
	}
}
