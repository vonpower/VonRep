/**
 * @author yddy,create date 2005-1-8 
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import ynugis.classifyBusiness.ClassifyProject;
import ynugis.estimate.wizard.EstimateWizard;
import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class EstWinRevertAction extends Action {
	private MainFace face;
	public EstWinRevertAction(MainFace m){
		face=m;
		this.setText("土地估价向导...");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_BUS));
		}
	}
	public void run(){
		ClassifyProject p=face.getCescore().getCEManager().getProject();
		if(p!=null)
		{
		WizardDialog wd=new WizardDialog(new Shell(),new EstimateWizard(p));
		wd.setPageSize(600,400);
		
		wd.open();
		}else 
		{
			MessageDialog.openWarning(new Shell(),"警告","当前系统中不存在可用项目！");
		}
		
	}
}
