package ynugis.ui.fileDialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class OpenFileShell extends FileShell {
	private final static int style=SWT.OPEN;
	public boolean setFilePath(Shell parent){
		FileDialog openFile=new FileDialog(parent,style);
		openFile.setFilterNames(new String[]{"map(*.mxd)","all(*.*)"});
		openFile.setFilterExtensions(new String[]{"*.mxd","*.*"});
		openFile.setFilterPath("e:\\");
		filePath=openFile.open();
		if(filePath==null){
			return false;
		}
		else{
			return true;
		}
	}
}
