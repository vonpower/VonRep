/*
* @author 冯涛，创建日期：2003-11-25
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
package ynugis.ffsystem;


import com.FFT.FFTDoc;
import com.FFT.FFTableType;
import com.FFT.FactorCatalogType;

public class FFTHelperTree
{
	private FFTableType root;
	private String treePath;
	private FFTDoc doc;
	public FFTHelperTree(FFTableType ftree) {
		
		root = ftree;
	}
	public FFTHelperTree(String inTreePath) {
		
		treePath=inTreePath;
		doc = new FFTDoc();
		root = new FFTableType(doc.load(treePath));
		
	}
	public void save(String fileName)
	{
		doc.save(treePath, root);
	}
	public void addFactorCatalogType(FactorCatalogType fct)
	{
		root.addFactorCatalog(fct);
	}
	
	
	
	
}
