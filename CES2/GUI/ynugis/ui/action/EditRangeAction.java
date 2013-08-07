/**
 * @author yddy,create date 2003-12-6 
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.action;

import java.io.IOException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageRegistry;

import ynugis.ui.elementToSHP.elementToShape;
import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class EditRangeAction extends Action {
	private MainFace face;
	
	public EditRangeAction(MainFace mainface){
		face=mainface;
		this.setText("定级范围");
		this.setToolTipText("划定定级范围");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_RANGE));
		}
	}
	
	public void run(){
		elementToShape elementTSHP=new elementToShape(face.getShell(),face.getCescore());
		try {
			
			elementTSHP.TransIelementToGeodataSet();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
