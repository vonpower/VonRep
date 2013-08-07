package ynugis.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageRegistry;

import ynugis.classifyBusiness.ClassifyProject;
import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class MapNewAction extends Action{
	private MainFace face;

	public MapNewAction(MainFace m){
		face=m;
		this.setText("清空");
		this.setToolTipText("新建地图");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_BLANK));
		}
	}
	public void run(){
		try {
//			face.getCescore().getMapControl().initNew();
//			face.getCescore().getPageLayoutControl().initNew();
//			face.getCescore().getTocControl().initNew();
			ClassifyProject pro=face.getCescore().getCEManager().getProject();
			if(pro!=null){
				boolean open=MessageDialog.openConfirm(face.getShell(),"清空显示区","清空操作将销毁当前项目如果要保存项目信息,请在保存项目操作中完成此清空操作");
				if(!open){
					return;
				}
				face.getCescore().getCEManager().closeProject(pro);
			}
			face.getCescore().resetAEControls(false);
			face.getCesui().resetUI(null);
		} catch (Exception e){
			MessageDialog.openError(face.getShell(),"清空显示区","清空操作失败");
			e.printStackTrace();
		}
//		GisCommand.getCmd().MapToPage(GisBean.getGisBeans().getMapc(),GisBean.getGisBeans().getPagec());
	}
}
