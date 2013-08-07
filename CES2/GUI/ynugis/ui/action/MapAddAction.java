package ynugis.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.resource.ImageRegistry;

import ynugis.thread.LoadMapfile;
import ynugis.ui.face.MainFace;
import ynugis.ui.fileDialog.FileShell;
import ynugis.ui.fileDialog.FileShellFactory;
import ynugis.ui.image.ImageProvider;

public class MapAddAction extends Action {
	private MainFace face;

	public MapAddAction(MainFace m) {
		face=m;
		this.setText("添加");
		this.setToolTipText("添加图层");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_LAYER));
		}
	}
	public void run(){
		System.out.println("Will add dialog:");
		String filePath;
		String[] fileNames;
		FileShellFactory fileShellFactory=new FileShellFactory();
		FileShell fileShell=fileShellFactory.getFileShell(FileShellFactory.ADD_FILE);
		if(fileShell.setFilePath(face.getShell())){
			filePath=fileShell.getFilePath();
			fileNames=fileShell.getFileNames();
		}else{
			return;
		}
		try {
			new ProgressMonitorDialog(null).run(false,false,new LoadMapfile(filePath,fileNames,face.getCescore().getMapControl()));
			face.setStatus("添加图层完成");
		} catch (Exception e) {
			e.printStackTrace();
			face.setStatus("添加图层失败");
		} 
	}
}
