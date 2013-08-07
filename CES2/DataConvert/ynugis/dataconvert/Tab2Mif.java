/*
* @author 冯涛，创建日期：2003-10-31
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
package ynugis.dataconvert;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;

import ynugis.utility.GT;

public class Tab2Mif extends Convertor implements IRunnableWithProgress{

	public Tab2Mif(String[] srcfiles, String destPath) {
		super(srcfiles, destPath);
		
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



	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		
		
		
		String[] retStrArray;
		Runtime r=Runtime.getRuntime();
		Process p=null;
		String[] cmd=new String[3];
		//convert tools filepath 
		cmd[0]=ConvertEnvironment.getTab2Tab_FilePath();
		
		//启动进度条
		monitor.beginTask("开始转换TAB数据到MIF数据",srcFiles.length);
		
		retStrArray=new String[srcFiles.length];
		try {
			//p=r.exec(cmd);
			for(int i=0;i<srcFiles.length;i++)
			{
				//设置进度条
				monitor.setTaskName("开始转换"+srcFiles[i]);
				
				int index=srcFiles[i].lastIndexOf(File.separator);
				int strLen=srcFiles[i].length();
				String fileName=srcFiles[i].substring(index+1,strLen);
				String prefix=fileName.substring(0,fileName.indexOf(".")) ;
							
				
				//source datasource 
				cmd[1]=srcFiles[i];
				//destination datasource
				cmd[2]=destPath+prefix+".mif";
				
				//record Filepath of shapefile   
				retStrArray[i]=cmd[2];
				
				//Excute command 
				p=r.exec(cmd);
				
				//Write some infomation to .log file
				GT.writeLog(p.getInputStream());
				GT.writeLog(p.getErrorStream());
				convertedCount++;
				
				monitor.setTaskName(srcFiles[i]+"转换完成");
				monitor.worked(1);
				
			}
					
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		setResultPath(retStrArray);
		
		
	}

}
