package ynugis.ui.action;

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

public class HelpAction extends Action {
	private MainFace face;

	public HelpAction(MainFace m) {
		face=m;
		this.setText("使用帮助");
		this.setToolTipText("如何使用本系统");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_HELP));
		}
	}
	public void run(){
		String path=new File("./help").getAbsolutePath();
		String url=path+File.separator+"index.htm";
		HelpBrowser helper=new HelpBrowser(url);
		helper.setBlockOnOpen(false);
		helper.open();
//		String url=path+File.separator+"index.chm";
//		try {
//			Runtime.getRuntime().exec("hh.exe",new String[]{url});
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
}
class HelpBrowser extends Window{
	private String path;
	public HelpBrowser(String htmlPath) {
		super(new Shell());
		this.path=htmlPath;
	}

	
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout(SWT.FILL));

		final Browser browser = new Browser(container, SWT.NONE);
		browser.setUrl(path);
		return container;
	}


	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("城镇土地定级估价系统使用帮助");
		newShell.setImage(new Image(null,"icons/help.gif"));
		newShell.setMinimumSize(500,650);
		newShell.setLayout(new FillLayout());
	}
	
}
