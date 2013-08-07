package ynugis.estimate.wizard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import ynugis.estimate.page.IndustryInfoPage0;

public class PesistentData {
	
	String FILE_NAME="persistentData";
    String NAME="exeMapDir";
	String  value;
	String exePath;
	Properties dProp=new Properties();
	public void read() {
		Properties dProp=new Properties();
		File ppFile=new File(FILE_NAME);
		IndustryInfoPage0 page1=new IndustryInfoPage0();
		if(!ppFile.exists()){
			
			try {
				ppFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			try {
				dProp.load(new FileInputStream(FILE_NAME));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dProp.setProperty("E1",page1.getE1Value());
			dProp.setProperty("E2",page1.getE2Value());
			dProp.setProperty("E3",page1.getE3Value());
			dProp.setProperty("T1",page1.getT1Value());
			dProp.setProperty("T2",page1.getT2Value());
			dProp.setProperty("T3",page1.getT3Value());
			dProp.setProperty("T4",page1.getT4Value());
			dProp.setProperty("intest",page1.getinterestValue());
			dProp.setProperty("profit",page1.getprofitValue());
			dProp.setProperty("upinterest",page1.getupinterestValue());
			dProp.setProperty("Ed",page1.getEdValue());
			dProp.setProperty("period",page1.getperiodValue());
			
			
			try {
				dProp.store(new FileOutputStream(FILE_NAME),"lasttime inputData");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
	    public void write(){
	    
		
		
}
}
