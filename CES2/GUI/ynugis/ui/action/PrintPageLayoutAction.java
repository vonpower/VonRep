package ynugis.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageRegistry;

import ynugis.ui.confDialog.PrintDialog;
import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class PrintPageLayoutAction extends Action {
	private MainFace face;

	public PrintPageLayoutAction(MainFace m) {
		face=m;
		this.setText("打印");
		this.setToolTipText("打印地图");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_PRINT));
		}
	}
	public void run(){
		PrintDialog print=new PrintDialog(face.getCescore().getMapControl());
		print.open();
		
	}
}
