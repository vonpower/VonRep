/**
 * @author yddy,create date 2003-11-7 
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageRegistry;

import ynugis.ui.confDialog.DataConvertDlg;
import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class TransferAction extends Action {
	private MainFace face;

	public TransferAction(MainFace m) {
		face = m;
		this.setText("地图转换");
		this.setToolTipText("地图转换");
		ImageRegistry imageRegistry = face.getCesui().getImageRegistry();
		if (imageRegistry != null) {
			this.setImageDescriptor(imageRegistry
					.getDescriptor(ImageProvider.ICON_TOLTRS));
		}
	}

	public void run() {
		DataConvertDlg d = new DataConvertDlg(face.getShell());
		d.open();

	}
}
