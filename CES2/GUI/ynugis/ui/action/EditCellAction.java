/**
 * @author yddy,create date 2004-1-4 
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;

import ynugis.application.CESCORE;
import ynugis.ui.confDialog.ModifyCellDialog;
import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class EditCellAction extends Action {
	private Shell shell;
	private CESCORE core;
	private Tree tree;
	private MainFace face;
	public EditCellAction(MainFace mface){
		face=mface;
		this.setText("图层编辑");
		this.setToolTipText("修改图层单元格值");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_CELL));
		}
	}
	public EditCellAction(Shell shell,CESCORE core,Tree tree){
		this.shell=shell;
		this.core=core;
		this.tree=tree;
		this.setText("图层编辑");
	}
	public void run(){
		if (face!=null) {
			ModifyCellDialog modifyDialog = new ModifyCellDialog(face
					.getShell(), face.getCescore());
			modifyDialog.open();
		}else{
			ModifyCellDialog modifyDialog = new ModifyCellDialog(shell,core,tree);
			modifyDialog.open();
		}
	}
}
