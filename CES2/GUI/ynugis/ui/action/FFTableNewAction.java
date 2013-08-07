package ynugis.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageRegistry;

import ynugis.ffsystem.FFTable;
import ynugis.ui.confDialog.FFTDialogHelper;
import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class FFTableNewAction extends Action {
	private MainFace face;

	public FFTableNewAction(MainFace m) {
		face=m;
		this.setText("新建");
		this.setToolTipText("新建因素因子树");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_TOLFFT));
		}
	}
	public void run(){
		FFTDialogHelper dialogHelper=new FFTDialogHelper();
		dialogHelper.fillFFTable(face.getShell(),new FFTable());
	}
}
