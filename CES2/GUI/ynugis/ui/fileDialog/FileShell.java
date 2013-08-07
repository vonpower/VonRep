package ynugis.ui.fileDialog;

import org.eclipse.swt.widgets.Shell;

public class FileShell {
	protected String filePath;
	protected String[] fileNames;
	public boolean setFilePath(Shell parent){
		return false;
	}
	public String getFilePath(){
		return filePath;
	}
	public String[] getFileNames(){
		return fileNames;
	}
}
