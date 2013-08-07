package ynugis.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageRegistry;

import ynugis.ui.confDialog.FFTBiCoDialog;
import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class FFTableBicoAction extends Action {
	private MainFace face;
	public FFTableBicoAction(MainFace m) {
		face=m;
		this.setText("成对比较法");
		this.setToolTipText("成对比较法设定因素因子权重");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_BICO));
		}
	}
	public void run(){
		FFTBiCoDialog dialog=new FFTBiCoDialog();
		dialog.open();
	}
}
