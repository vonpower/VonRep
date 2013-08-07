package ynugis.ui.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class MapGisAction extends Action {
	private MainFace face;

	public MapGisAction(MainFace m) {
		face=m;
		this.setText("格式转换");
		this.setToolTipText("转换");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_MAPGIS));
		}
	}
	public void run(){
		final String FILE_NAME="path.properties";
		final String NAME="exeMapDir";
		final String DEFAULT_VALUE="C:/mapgis65/program/W60_Conv.exe";
//		final String DEFAULT_VALUE="d:/windows/regedit.exe";
		String exePath=DEFAULT_VALUE;
		try {
			File ppFile=new File(FILE_NAME);
			if(!ppFile.exists()){
				ppFile.createNewFile();
				Properties dProp=new Properties();
				dProp.load(new FileInputStream(FILE_NAME));
				dProp.setProperty(NAME,DEFAULT_VALUE);
				dProp.store(new FileOutputStream(FILE_NAME),"Location of MapGis default");
			}
			Properties prop=new Properties();
			prop.load(new FileInputStream(FILE_NAME));
			if(prop.containsKey(NAME)){
				File exeFile=new File(prop.getProperty(NAME));
				if(!exeFile.exists()){
					exePath=getExeDir();
					if(exePath==null)return;
				}else exePath=prop.getProperty(NAME);
			}else{
				exePath=getExeDir();
				if(exePath==null)return;
			}
			prop.setProperty(NAME,exePath);
			prop.store(new FileOutputStream(FILE_NAME),"Location of MapGis");
			System.out.println("file exe:"+(new File(exePath).exists()));
			if (new File(exePath).exists()) {
				Runtime runtime = Runtime.getRuntime();
				runtime.exec(exePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private String getExeDir(){
		FileDialog dlg=new FileDialog(face.getShell(),SWT.OPEN);
		dlg.setFilterPath("c:");
		dlg.setFilterExtensions(new String[]{"*.exe"});
		dlg.setFilterNames(new String[]{"可执行文件(*.exe)"});
		return dlg.open();
	}
}
