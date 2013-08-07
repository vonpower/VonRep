package ynugis.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageRegistry;

import ynugis.ui.confDialog.LayerSelectionDialog;
import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class MapAttributeAction extends Action {
	private MainFace face;
	public MapAttributeAction(MainFace m) {
		face=m;
		this.setText("属性表");
		this.setToolTipText("图层属性");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_ATTR));
		}
	}
	public void run(){
		try {
			LayerSelectionDialog lsd = new LayerSelectionDialog(face.getShell(),face
					.getCescore());
			lsd.open();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
