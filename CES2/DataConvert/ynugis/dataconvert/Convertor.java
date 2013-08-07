/*
* @author 冯涛，创建日期：2003-10-31
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
package ynugis.dataconvert;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;

public abstract class Convertor implements IRunnableWithProgress{
	protected String[] srcFiles;
	protected String destPath;
	protected int srcFilesCount;
	protected int convertedCount;
	protected String[] resultPath;
	public Convertor(String[] srcfiles,String inDestPath) {
		super();
		File f=new File(inDestPath);
		if(!f.isDirectory())
		{
			f.mkdir();
		}
		
		destPath = inDestPath;
		srcFiles = srcfiles;
		srcFilesCount=srcfiles.length;
		convertedCount=0;
		
		
	}
	
	final public int getSrcFilesCount()
	{
		return srcFilesCount;
	}
	final public int getConvertedCount()
	{
		return convertedCount;
	}
	

	
	public String[] convert() {
		
		ProgressMonitorDialog pmd=new ProgressMonitorDialog(new Shell());
		try {
			pmd.run(false,false,this);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getResultPath();
	}
	public String[] getResultPath() {
		return resultPath;
	}

	public void setResultPath(String[] resultPath) {
		this.resultPath = resultPath;
	}

	

}
