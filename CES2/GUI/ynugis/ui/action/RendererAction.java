package ynugis.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageRegistry;

import ynugis.ui.confDialog.RendererDialog;
import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class RendererAction extends Action {
	private MainFace face;

	public RendererAction(MainFace m) {
		face=m;
		this.setText("着色");
		this.setToolTipText("设置图层着色方案");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_RENDER));
		}
	}
	public void run(){
		RendererDialog dialog=new RendererDialog(face.getCescore());
		dialog.open();
		
	}
}
