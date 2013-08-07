/*
 * @author 冯涛，创建日期：2003-10-31
 * blog--http://apower.blogone.net
 * QQ:17463776
 * MSN:apower511@msn.com
 * E-Mail:VonPower@Gmail.com
 */
package ynugis.dataconvert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;

public class Mif2SimpleMiF extends Convertor {

	public Mif2SimpleMiF(String[] srcfiles, String destPath) {
		super(srcfiles, destPath);
		// TODO Auto-generated constructor stub
	}

	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		String temp = "";
		MIFClipper mc = null;
		// 启动进度条
		monitor.beginTask("开始裁减MIF数据", srcFiles.length);

		for (int i = 0; i < srcFilesCount; i++) {
			try {

				// 设置进度条
				monitor.setTaskName("开始裁减" + srcFiles[i]);
				
				mc = new MIFClipper(srcFiles[i], destPath);
				String tt = mc.clipMIFFile();
				temp += tt;
				convertedCount++;

				monitor.setTaskName(srcFiles[i] + "裁减完成");

				monitor.worked(1);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		setResultPath(temp.split(","));

	}

}
