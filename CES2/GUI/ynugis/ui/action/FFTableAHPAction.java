package ynugis.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageRegistry;

import ynugis.ui.confDialog.FFTAhpDialog;
import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class FFTableAHPAction extends Action {
	private MainFace face;
	public FFTableAHPAction(MainFace m) {
		face=m;
		this.setText("层次分析法");
		this.setToolTipText("层次分析法设定因素因子权重");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_AHP));
		}
	}
	public void run(){
		FFTAhpDialog dlg=new FFTAhpDialog();
		dlg.open();
	}
}
