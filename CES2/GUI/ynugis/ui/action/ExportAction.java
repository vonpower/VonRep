/**
 * @author yddy,create date 2003-11-7 
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageRegistry;

import ynugis.application.CESCORE;
import ynugis.ui.confDialog.ExportDialog;
import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class ExportAction extends Action {
	private MainFace face;

	public ExportAction(MainFace m) {
		face = m;
		this.setText("导出地图");
		this.setToolTipText("导出地图");
		ImageRegistry imageRegistry = face.getCesui().getImageRegistry();
		if (imageRegistry != null) {
			this.setImageDescriptor(imageRegistry
					.getDescriptor(ImageProvider.ICON_TOLMAP));
		}
	}

	public void run() {
		CESCORE core = face.getCescore();

		ExportDialog ed = new ExportDialog(core, face.getShell());

		ed.open();

	}
}
