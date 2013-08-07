


import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageRegistry;

import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class EstPubinfoAction extends Action {
	private MainFace face;
	public EstPubinfoAction(MainFace m) {
		face=m;
		this.setText("公共属性");
		this.setToolTipText("公共属性信息");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_ESTINFO));
		}
	}
	public void run(){
		publicInfo s=new publicInfo(face.getShell());
		s.open();
		
	}
}
