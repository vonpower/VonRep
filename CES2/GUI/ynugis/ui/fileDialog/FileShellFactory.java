package ynugis.ui.fileDialog;

public class FileShellFactory {
	public static final String OPEN_FILE="open";
	public static final String ADD_FILE="add";
//	private MainFace face;
//	public FileShellFactory(MainFace m) {
//		face=m;
//	}
	public FileShell getFileShell(String type){
		if(type.equals("open")){
			return new OpenFileShell();
		}else if(type.equals("add")){
			return new AddFileShell();
		}else{
			return new FileShell();
		}
	}

}
