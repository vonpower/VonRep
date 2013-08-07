


import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageRegistry;

import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class EstPubinfoAction extends Action {
	private MainFace face;
	public EstPubinfoAction(MainFace m) {
		face=m;
		this.setText("��������");
		this.setToolTipText("����������Ϣ");
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
