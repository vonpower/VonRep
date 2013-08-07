/**
 * @author yddy,create date 2003-12-5 
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageRegistry;

import ynugis.application.CESCORE;
import ynugis.ui.confDialog.transparentSetDialog;
import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

import com.esri.arcgis.carto.IMap;

public class MapTransparenceAction extends Action {
	private MainFace	face;

	public MapTransparenceAction(MainFace mainface) {
		this.face=mainface;
		this.setText("透明度");
		this.setToolTipText("设置图层的透明度");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_TRSP));
		}
	}
	
	public void run(){
		CESCORE cescore=face.getCescore();
		try {
			IMap pMap=cescore.getMapControl().getActiveView().getFocusMap();
			transparentSetDialog setDialog=new transparentSetDialog(face.getShell(),pMap);
			setDialog.open();
			cescore.getMapControl().getActiveView().refresh();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
