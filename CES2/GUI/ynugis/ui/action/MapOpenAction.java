package ynugis.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.resource.ImageRegistry;

import ynugis.thread.LoadMxdfile;
import ynugis.ui.face.MainFace;
import ynugis.ui.fileDialog.FileShell;
import ynugis.ui.fileDialog.FileShellFactory;
import ynugis.ui.image.ImageProvider;

public class MapOpenAction extends Action {
	private MainFace	face;

	public MapOpenAction(MainFace m) {
		face = m;
		this.setText("打开");
		this.setToolTipText("打开地图文件");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_OPEN));
		}
	}

	public void run() {
		String filePath;
		FileShellFactory fileShellFactory = new FileShellFactory();
		FileShell fileShell = fileShellFactory
				.getFileShell(FileShellFactory.OPEN_FILE);
		if (fileShell.setFilePath(face.getShell())) {
			filePath = fileShell.getFilePath();
		} else {
			return;
		}
		face.setStatus("正在加载地图...");
		try {
			new ProgressMonitorDialog(null).run(false,false,new LoadMxdfile(filePath, face.getCescore()));
			face.setStatus("已加载:" + filePath);
		} catch (Exception e) {
			e.printStackTrace();
			face.setStatus("加载地图失败");
		}
		
	}
}
