package ynugis.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageRegistry;

import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class ExitAction extends Action {
	private MainFace face;

	public ExitAction(MainFace m) {
		face=m;
		this.setText("退出");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_EXIT));
		}
	}
	public void run(){
		face.close();
	}
}
