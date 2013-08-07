package ynugis.ui.fileDialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class AddFileShell extends FileShell {
	private final static int style=SWT.MULTI;
	public boolean setFilePath(Shell parent){
		FileDialog addFile=new FileDialog(parent,style);
		addFile.setFilterNames(new String[]{"ShapeFile(*.shp)","Layer File(*.lyr)","all(*.*)"});
		addFile.setFilterExtensions(new String[]{"*.shp","*.lyr","*.*"});
		addFile.setFilterPath("e:\\");
		addFile.open();
		filePath=addFile.getFilterPath();
		fileNames=addFile.getFileNames();
		if(filePath==null){
			return false;
		}
		else{
			return true;
		}
	}
}
