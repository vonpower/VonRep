package ynugis.ffsystem;
/*
* @author 冯涛，创建日期：2003-11-25
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import ynugis.utility.GV;

import com.FFT.FFTDoc;
import com.FFT.FFTableType;


public class FFSystem
{
	public final static int HTML_FORMAT=0;
	public final static int RTF_FORMAT=1;
	public final static int FO_FORMAT=2;
	//private ClassifyType classifyType;
	private static String getFFTTemplatePath()
	{
		return "FFTTemplate\\";
	}
	/**
	 * 获取系统模板路径中的所有因素因子表
	 * @return
	 */
	public static FFTable[] getFFTableTemplates()
	{
		File f=new File(getFFTTemplatePath());
		//获取模板路径下的所用FFT模板
		String[] xmlFiles=f.list(new FilenameFilter(){

			public boolean accept(File arg0, String arg1) {
				
				return arg1.toUpperCase().endsWith(".FFT");
			}});
		
		//使用获取的FFT模板初始化tables
		FFTable[] tables=new FFTable[xmlFiles.length];
		for(int i=0;i<xmlFiles.length;i++)
		{
			try {
				tables[i]=createFFT(f.getAbsolutePath()+File.separator+xmlFiles[i]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return tables;
		
	}
	public static FFTable createFFT(String xmlPath) throws Exception
	{
		
		FFTDoc doc = new FFTDoc();
		FFTableType root = new FFTableType(doc.load(xmlPath));
		
		
		return new FFTable(root);
	}
	
	
	/**
	 * 导出FFT
	 * @param fft
	 * @param filePath
	 * @param exportType HTML_FORMAT=0,RTF_FORMAT=1,FO_FORMAT=2;
	 * 
	 */
	public static void FFTExport(FFTable fft,String filePath,int exportType)
	{
		FFTDoc doc = new FFTDoc();
		
		try {
			FFTableType root=fft.getAsFFTableType();
			
			DOMSource source = new DOMSource(root.getDomNode().getOwnerDocument());
		      StreamResult result = new StreamResult(new FileOutputStream(filePath));

			
			TransformerFactory transFactory = TransformerFactory.newInstance();
			String xslPath="";
			switch(exportType)
			{
			case HTML_FORMAT:xslPath= GV.getFFTViewHTMLPath();break;
			case RTF_FORMAT:xslPath= GV.getFFTViewRTFPath();break;
			case FO_FORMAT:xslPath= GV.getFFTViewFOPath();break;
			default: return;
			
			}
			
			
			javax.xml.transform.Transformer transformer = transFactory.newTransformer(
					new javax.xml.transform.stream.StreamSource(xslPath)
					);

		     
			
			transformer.transform(source, result);


			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	public static void saveFFT(FFTable fft,String xmlPath) throws Exception
	{
		
		FFTDoc doc = new FFTDoc();
		
		
		
		doc.save(xmlPath, fft.getAsFFTableType());
	}
	/**
	 * 将指定FFT作为模板保存
	 * @param fft
	 * @throws Exception 
	 */
	public static void saveASTemplate(FFTable fft) throws Exception
	{
		
		saveFFT(fft,getFFTTemplatePath()+File.separator+fft.name+".fft");
		
	}
}
