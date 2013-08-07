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
import ynugis.estimate.wizard.CostApproachWizard;
import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class EstCostApproachAction extends Action {
	private MainFace face;
	public EstCostApproachAction(MainFace m){
		face=m;
		this.setText("成本逼近法向导...");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_IND));
		}
	}
	public void run(){
		ClassifyProject p=face.getCescore().getCEManager().getProject();
		if(p!=null)
		{
		WizardDialog wd=new WizardDialog(new Shell(),new CostApproachWizard());
		
		wd.open();
		}else 
		{
			MessageDialog.openWarning(new Shell(),"警告","当前系统中不存在可用项目！");
		}
	}
}
