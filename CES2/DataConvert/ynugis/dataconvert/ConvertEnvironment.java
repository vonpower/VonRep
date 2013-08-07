/*
* @author 冯涛，创建日期：2003-10-31
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
package ynugis.dataconvert;

public class ConvertEnvironment {
	
	final public static int TAB=0;
	final public static int MIF=1;
	final public static int SHP=2;
	
	
	/**
	 * return Filepath of convert tools
	 * @return
	 */
	static public String getX2x_FilePath() {
		String x2x_FilePath;
		x2x_FilePath="tools\\x2x.exe";
		return x2x_FilePath;
	}
	/**
	 * @return
	 */
	static public String getTab2Tab_FilePath(){
		String tab2tab_FilePath;
		tab2tab_FilePath="tools\\tab2tab.exe";
		return tab2tab_FilePath;
		
	}
}
