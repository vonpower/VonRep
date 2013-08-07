/*
* @author 冯涛，创建日期：2003-11-6
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
package ynugis.dataconvert;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;

public class Tab2Shp extends Convertor {

	public Tab2Shp(String[] srcfiles, String destPath) {
		super(srcfiles, destPath);
		// TODO Auto-generated constructor stub
	}

	

	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		String mifPath=destPath+File.separator+"temp"+File.separator+"mif";
		String simpleMifPath=destPath+File.separator+"temp"+File.separator+"simpleMif";
		String shpPath=destPath+File.separator+"ShapeFiles";
		File f=new File(mifPath);
		if(!f.isDirectory())
		{
			f.mkdir();
		}
		
		Tab2Mif t2m=new Tab2Mif(srcFiles,mifPath);
		f=new File(simpleMifPath);
		if(!f.isDirectory())
		{
			f.mkdir();
		}
		Mif2SimpleMiF m2m=new Mif2SimpleMiF(t2m.convert(),simpleMifPath);
		SimpleMif2Shp sm2s=new SimpleMif2Shp(m2m.convert(),shpPath);
		
		setResultPath(sm2s.convert());
		
	}

}
