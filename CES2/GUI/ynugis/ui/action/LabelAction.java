package ynugis.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageRegistry;

import ynugis.ui.confDialog.LableDialog;
import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class LabelAction extends Action {
	private MainFace face;

	public LabelAction(MainFace m) {
		face=m;
		this.setText("标注");
		this.setToolTipText("标注图层");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_LABEL));
		}
	}
	public void run(){
			LableDialog ld=new LableDialog(face.getShell(),face.getCescore().getMapControl());
			ld.open();
	}
}
