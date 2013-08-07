package ynugis.utility;

import java.io.File;



/*
* @author 冯涛，创建日期：2005-9-6
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/



/**
 * 
 * This Class Support Global Variant for our System
 * @author Administrator
 *
 */
public class GV {
	
	public static double multiple=10000;
	public static double polygonSmooth=0.1;
	/**
	 * return Filepath of .log file
	 * @return
	 */
	static public String getLog_FilePath(){
		String LogFilePath;
		LogFilePath=".\\ynugis.log";
		return LogFilePath;
	}
	
	
	
	/**
	 * @return
	 */
	static public String getDefaultTempFileDirectoryPath(){
		String TempFileDirectoryPath;
		TempFileDirectoryPath=".\\Temp\\";
		File f=new File(TempFileDirectoryPath);
		if(f.isDirectory())return TempFileDirectoryPath;
		else
		{
			if(f.mkdir())
			return TempFileDirectoryPath;
			return null;
		}
		//return TempFileDirectoryPath;
		
	}
	/**
	 * @return .\\XML数据和数据说明\\类型_因素因子.xml
	 */
	static public String getClassifyTypeFactorXMLPath()
	{
		String ClassifyTypeFactorPath;
		ClassifyTypeFactorPath=".\\XML数据和数据说明\\类型_因素因子.xml";
		return ClassifyTypeFactorPath;
		
	}
	
	/**
	 * @return .\\XML数据和数据说明\\类型_因素因子.xsd
	 */
	static public String getClassifyTypeFactorSchemaPath()
	{
		String ClassifyTypeFactorSchemaPath;
		ClassifyTypeFactorSchemaPath=".\\XML数据和数据说明\\类型_因素因子.xsd";
		return ClassifyTypeFactorSchemaPath;
		
	}
	
	/**
	 * @return
	 */
	static public String getClassifyProjectSchemaPath()
	{
		String ClassifyProjectSchemaPath;
		ClassifyProjectSchemaPath=".\\XML数据和数据说明\\城镇土地定级项目.xsd";
		return ClassifyProjectSchemaPath;
		
	}
	
	/**
	 * get XSLT file's path for FFT_XML's Display
	 */
	static public String getFFTViewHTMLPath()
	{
		String xsltPath;
		xsltPath=".\\FFTTemplate\\FFTViewHTML.xslt";
		return xsltPath;
		
	}
	
	/**
	 * get XSLT file's path for FFT_XML's Display
	 */
	static public String getFFTViewRTFPath()
	{
		String xsltPath;
		xsltPath=".\\FFTTemplate\\FFTViewRTF.xslt";
		return xsltPath;
		
	}
	
	/**
	 * get XSLT file's path for FFT_XML's Display
	 */
	static public String getFFTViewFOPath()
	{
		String xsltPath;
		xsltPath=".\\FFTTemplate\\FFTViewFO.xsl";
		return xsltPath;
		
	}


	
	
}
	


