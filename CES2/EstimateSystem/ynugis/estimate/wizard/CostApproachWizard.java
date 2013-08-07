package ynugis.estimate.wizard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import ynugis.estimate.page.IndustryInfoPage0;
import ynugis.estimate.page.IndustryInfoPage1;
import ynugis.utility.GT;

public class CostApproachWizard extends Wizard{
	private IndustryInfoPage0 page1;
	private IndustryInfoPage1 page2;
	double Ea;
	 double Ed;
	double T;
	double R1;
	double R2;
	double R3;
	double E1;
	double E2;
	double E3;
	double T1;
	double T1interest;
	double T2;
	double T3;
	double T4;
	double V;
	String result;
	String result1;
	String FILE_NAME="persistentData";
	//PesistentData p=new PesistentData();
	
	public void addPages() {
		page1=new IndustryInfoPage0();
		page2=new  IndustryInfoPage1();
		addPage(page1);
		addPage(page2);
		super.addPages();
		
	}
	public boolean canFinish(){
		if(this.getContainer().getCurrentPage()!=page2)
		return false;
		return super.canFinish();
		
	}

	public boolean performFinish() {
		
		if(!page1.getE1Value().equals("")&&page1.getE2Value().equals("")&&page1.getE3Value().equals("")){
		 E1=Double.parseDouble(page1.getE1Value());
		Ea=E1;}
		if(page1.getE1Value().equals("")&&!page1.getE2Value().equals("")&&page1.getE3Value().equals("")){
			E2=Double.parseDouble(page1.getE2Value());
			Ea=E2;
		}
		if(page1.getE1Value().equals("")&&page1.getE2Value().equals("")&&!page1.getE3Value().equals("")){
			E3=Double.parseDouble(page1.getE3Value());
			Ea=E3;
		}
		if(!page1.getE1Value().equals("")&&!page1.getE2Value().equals("")&&page1.getE3Value().equals("")){
		E1=Double.parseDouble(page1.getE1Value());
		E2=Double.parseDouble(page1.getE2Value());
	   
		 Ea=E1+E2;}
		if(!page1.getE1Value().equals("")&&page1.getE2Value().equals("")&&!page1.getE3Value().equals("")){
			E1=Double.parseDouble(page1.getE1Value());
			E3=Double.parseDouble(page1.getE3Value());
		   
			 Ea=E1+E3;}
		if(page1.getE1Value().equals("")&&!page1.getE2Value().equals("")&&!page1.getE3Value().equals("")){
			E2=Double.parseDouble(page1.getE2Value());
			E3=Double.parseDouble(page1.getE3Value());
		   
			 Ea=E2+E3;}
	
		if(!page1.getE1Value().equals("")&&!page1.getE2Value().equals("")&&!page1.getE3Value().equals("")){
		E1=Double.parseDouble(page1.getE1Value());
		E2=Double.parseDouble(page1.getE2Value());
		 E3=Double.parseDouble(page1.getE3Value());
		 Ea=E1+E2+E3;}
	    if(!page1.getEdValue().equals("")){
	     Ed=Double.parseDouble(page1.getEdValue());}
		if(!page1.getT1Value().equals("")&&!page1.getT1interestValue().equals("")&&page1.getT2Value().equals("")&&page1.getT3Value().equals("")&&page1.getT4Value().equals("")){
		T1=Double.parseDouble(page1.getT1Value());
		T1interest=Double.parseDouble(page1.getT1interestValue());
		T=T1*(T1interest/100);}
		if(page1.getT1Value().equals("")&&page1.getT1interestValue().equals("")&&!page1.getT2Value().equals("")&&page1.getT3Value().equals("")&&page1.getT4Value().equals("")){
			T2=Double.parseDouble(page1.getT2Value());
			T=T2;}
		if(page1.getT1Value().equals("")&&page1.getT1interestValue().equals("")&&page1.getT2Value().equals("")&&!page1.getT3Value().equals("")&&page1.getT4Value().equals("")){
			T3=Double.parseDouble(page1.getT3Value());
			T=T3;}
		if(page1.getT1Value().equals("")&&page1.getT1interestValue().equals("")&&page1.getT2Value().equals("")&&page1.getT3Value().equals("")&&!page1.getT4Value().equals("")){
			T4=Double.parseDouble(page1.getT4Value());
			T=T4;}
		if(!page1.getT1Value().equals("")&&!page1.getT1interestValue().equals("")&&!page1.getT2Value().equals("")&&page1.getT3Value().equals("")&&page1.getT4Value().equals("")){
		T1=Double.parseDouble(page1.getT1Value());
		T1interest=Double.parseDouble(page1.getT1interestValue());
		T2=Double.parseDouble(page1.getT2Value());
		T=T1*(T1interest/100)+T2;}
		if(!page1.getT1Value().equals("")&&!page1.getT1interestValue().equals("")&&page1.getT2Value().equals("")&&!page1.getT3Value().equals("")&&page1.getT4Value().equals("")){
			T1=Double.parseDouble(page1.getT1Value());
			T1interest=Double.parseDouble(page1.getT1interestValue());
			T3=Double.parseDouble(page1.getT3Value());
			T=T1*(T1interest/100)+T3;}
		if(!page1.getT1Value().equals("")&&!page1.getT1interestValue().equals("")&&page1.getT2Value().equals("")&&page1.getT3Value().equals("")&&!page1.getT4Value().equals("")){
			T1=Double.parseDouble(page1.getT1Value());
			T1interest=Double.parseDouble(page1.getT1interestValue());
			T4=Double.parseDouble(page1.getT4Value());
			T=T1*(T1interest/100)+T4;}
		if(page1.getT1Value().equals("")&&page1.getT1interestValue().equals("")&&!page1.getT2Value().equals("")&&!page1.getT3Value().equals("")&&page1.getT4Value().equals("")){
			T2=Double.parseDouble(page1.getT2Value());
			T3=Double.parseDouble(page1.getT3Value());
			T=T2+T3;}
		if(page1.getT1Value().equals("")&&page1.getT1interestValue().equals("")&&!page1.getT2Value().equals("")&&page1.getT3Value().equals("")&&!page1.getT4Value().equals("")){
			T2=Double.parseDouble(page1.getT2Value());
			T4=Double.parseDouble(page1.getT4Value());
			T=T2+T4;}
		if(page1.getT1Value().equals("")&&page1.getT1interestValue().equals("")&&page1.getT2Value().equals("")&&!page1.getT3Value().equals("")&&!page1.getT4Value().equals("")){
			T3=Double.parseDouble(page1.getT3Value());
			T4=Double.parseDouble(page1.getT4Value());
			T=T3+T4;}
		if(!page1.getT1Value().equals("")&&!page1.getT1interestValue().equals("")&&!page1.getT2Value().equals("")&&!page1.getT3Value().equals("")&&page1.getT4Value().equals("")){
		T1=Double.parseDouble(page1.getT1Value());
		T1interest=Double.parseDouble(page1.getT1interestValue());
		T2=Double.parseDouble(page1.getT2Value());
		T3=Double.parseDouble(page1.getT3Value());
		T=T1*(T1interest/100)+T2+T3;}
		if(!page1.getT1Value().equals("")&&!page1.getT1interestValue().equals("")&&!page1.getT2Value().equals("")&&!page1.getT3Value().equals("")&&!page1.getT4Value().equals("")){
			T1=Double.parseDouble(page1.getT1Value());
			T1interest=Double.parseDouble(page1.getT1interestValue());
			T2=Double.parseDouble(page1.getT2Value());
			T3=Double.parseDouble(page1.getT3Value());
			T4=Double.parseDouble(page1.getT4Value());
		T=T1*(T1interest/100)+T2+T3+T4;}
		if(!page1.getinterestValue().equals("")&&!page1.getperiodValue().equals("")&&!page1.getprofitValue().equals("")&&!page1.getupinterestValue().equals("")){
		double period=Double.parseDouble(page1.getperiodValue());
		double interest=Double.parseDouble(page1.getinterestValue());
		double profit=Double.parseDouble(page1.getprofitValue());
		double upinterest=Double.parseDouble(page1.getupinterestValue());
		
		R1=(Ea+T+Ed)*period*(interest/100);
	    R2=(Ea+T+Ed)*(profit/100);
	    R3=(Ea+Ed+T+R1+R2)*(upinterest/100);
		V=Ea+Ed+T+R1+R2+R3;
		}
		else {Shell shell=new Shell();
		MessageBox messageBox=new MessageBox(shell);
		messageBox.setMessage("请完整填写估价信息");
		messageBox.open();
		return false;
		}
		if(!page2.getrevertValue().equals("")&&!page2.getusetimeValue().equals("")){
			double r=Double.parseDouble(page2.getrevertValue());
			double n=Double.parseDouble(page2.getusetimeValue());
			double K=1-1/Math.pow(1+(r/100),n);
			double ReviseT=V*K;
			result="土地取得费"+Ea+"\n"+"土地开发费"+Ed+"\n"+"税费"+T+"\n"+"利息"+R1+"\n"+"利润"+R2+"\n"+"土地增值"+R3+"\n"+"土地价格"+V+"\n"+"年期修正系数"+K+"\n"+"修正后的价格"+ReviseT+"";
			System.out.println(result);
			if(!page2.getResultText().equals("")){
			String savPath=page2.getResultText();
			try {
				GT.write2Txt(savPath,result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
		else{
			result1="土地取得费"+Ea+"\n"+"土地开发费"+Ed+"\n"+"税费"+T+"\n"+"利息"+R1+"\n"+"利润"+R2+"\n"+"土地增值"+R3+"\n"+"土地价格"+V+"\n"+"";
			System.out.println(result1); 
			if(!page2.getResultText().equals("")){
				String savPath=page2.getResultText();
				try {
					GT.write2Txt(savPath,result1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}		
		}
		//p.read();
	Properties dProp=new Properties();
	File ppFile=new File(FILE_NAME);
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
			dProp.setProperty("T1interest",page1.getT1interestValue());
			
			
			try {
				dProp.store(new FileOutputStream(FILE_NAME),"lasttime inputData");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return true;
		
	
		
			}
		
	}


