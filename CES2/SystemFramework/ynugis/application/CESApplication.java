/**
 * @author yddy,create date 2003-11-13
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.application;

import java.io.File;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import ynugis.ui.face.MainFace;
import ynugis.utility.GT;
import ynugis.utility.GV;

public class CESApplication {
	private static boolean show=false;

	public static void main(String[] args) {
		show=true;
		/*
		 * DO NOT remove this sentence,otherwise you will die!!!
		 */
		System.out.println(Display.getDefault());
		new Thread(new Runnable(){
			public void run(){
				Display display=new Display();
				int sh=display.getBounds().height;
				int sw=display.getBounds().width;
				Image image=new Image(display,"icons/splash.bmp");
				int h=image.getBounds().height;
				int w=image.getBounds().width;
				Shell shell=new Shell(SWT.ON_TOP);
				shell.setLayout(new FillLayout());
				int offsetH=(int)((sh-h)/2);
				int offsetW=(int)((sw-w)/2);
				shell.setBounds(offsetW,offsetH,w,h);
				Label label=new Label(shell,SWT.NONE);
				label.setImage(image);
				shell.open();
				while(!shell.isDisposed()&&show==true){
					if(!display.readAndDispatch())
						display.sleep();
				}
				if(!shell.isDisposed()){
					shell.dispose();
				}
			}
		}).start();
		/*
		 * initialize ArcEngine
		 */
		CESCORE cescore=null;
		MainFace mf = null;
		try {
			cescore=new CESCORE();
			cescore.initializeArcEngine();
			cescore.initializeArcObject();
			cescore.initializeAEControls();
			//cescore.buddyAEControl(true);
			cescore.configureEditCommands(cescore.getToolbarControlEdit());
			cescore.configureMapCommands(cescore.getToolbarControlView());
			//cescore.setDefaultTool(cescore.getToolbarControlView(), 4);
		} catch (Exception e) {
			/*
			 * start application without AE controls
			 */
			try {
				mf=new MainFace(null);
				mf.setBlockOnOpen(true);
				show=false;
				mf.open();
				Display.getCurrent().dispose();
				System.exit(0);
			} catch (Exception e1) {
				MessageDialog.openError(new Shell(),"初始化","初始化失败");
				e1.printStackTrace();
				System.exit(0);
			}
		}
		/*
		 * Go on with initializing UI
		 */
		try {
			if(mf==null){
				mf = new MainFace(cescore);
			}
		} catch (Exception e) {
			/*
			 * stop starting application
			 */
//			MessageDialog.openError(new Shell(),"初始化","初始化失败");
			e.printStackTrace();
			System.exit(0);
		}
		if (mf != null) {
			mf.setBlockOnOpen(true);
			show=false;
			mf.open();
			Display.getCurrent().dispose();
			try {
				//remove temporary Files(Include f )
				File f=new File(GV.getDefaultTempFileDirectoryPath());
				GT.delAll(f);
				//rebuild f 
				f.mkdir();
				
				cescore.shutDownArcEngine();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				
				System.exit(0);
			}
		}
	}

}