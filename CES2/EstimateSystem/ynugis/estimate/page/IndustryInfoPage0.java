package ynugis.estimate.page;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class IndustryInfoPage0 extends WizardPage {

	private Text T1interestText;
	private Text periodText;
	private Text EdText;
	private Text upinterestText;
	private Text profitText;
	private Text interestText;
	private Text T4text;
	private Text T3text;
	private Text T2text;
	private Text T1text;
	private Text E3text;
	private Text E2text;
	private Text E1text;
	String FILE_NAME="persistentData";
	Properties dProp=new Properties();
	/**
	 * Create the wizard
	 */
	public IndustryInfoPage0() {
		super("wizardPage");
		setTitle("成本逼近法_1");
		setDescription("设置用于本次成本逼近法估价的参数");
		

		
	}
	
	/**
	 * Create contents of the wizard
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		//
		setControl(container);

		final Label label = new Label(container, SWT.NONE);
		label.setText("土地补偿费");
		label.setBounds(6, 17, 65, 17);

		final Label label_1 = new Label(container, SWT.NONE);
		label_1.setText("安置补助费");
		label_1.setBounds(6, 50, 66, 15);

		final Label label_2 = new Label(container, SWT.NONE);
		label_2.setText("青苗补偿费");
		label_2.setBounds(6, 83, 70, 16);

		final Label label_3 = new Label(container, SWT.NONE);
		label_3.setText("征地管理费");
		label_3.setBounds(288, 17, 65, 15);

		final Label label_4 = new Label(container, SWT.NONE);
		label_4.setText("耕地占用税");
		label_4.setBounds(6, 117, 64, 16);

		final Label label_5 = new Label(container, SWT.NONE);
		label_5.setText("造地费");
		label_5.setBounds(6, 147, 42, 17);

		final Label label_6 = new Label(container, SWT.NONE);
		label_6.setText("其他税费");
		label_6.setBounds(6, 185, 52, 12);

		
		try {
			dProp.load(new FileInputStream(FILE_NAME));}
			
				
			
		 catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		E1text = new Text(container, SWT.BORDER);
		E1text.setBounds(80, 15, 100, 21);
		 String E1Value="";
			
	     if(dProp.containsKey("E1")){
		   
         E1Value= dProp.getProperty("E1");}
	     E1text.setText(E1Value);
	   
		
		E2text = new Text(container, SWT.BORDER);
		E2text.setBounds(80, 45, 100, 21);
		String E2Value="";
		
	     if(dProp.containsKey("E2")){
		   
        E2Value= dProp.getProperty("E2");}
	     E2text.setText(E2Value);
		
		E3text = new Text(container, SWT.BORDER);
		E3text.setBounds(80, 80, 100, 21);
		String E3Value="";
		
	     if(dProp.containsKey("E3")){
		   
       E3Value= dProp.getProperty("E3");}
	     E3text.setText(E3Value);

		T1text = new Text(container, SWT.BORDER);
		T1text.setBounds(80, 150, 100, 21);
		String T1Value="";
		
	     if(dProp.containsKey("T1")){
		   
       T1Value= dProp.getProperty("T1");}
	     T1text.setText(T1Value);

		T2text = new Text(container, SWT.BORDER);
		T2text.setBounds(80, 115, 100, 21);
		String T2Value="";
		
	     if(dProp.containsKey("T2")){
		   
       T2Value= dProp.getProperty("T2");}
	     T2text.setText(T2Value);


		final Label label_7 = new Label(container, SWT.NONE);
		label_7.setText("（元/平方米）");
		label_7.setBounds(190, 14, 77, 20);

		final Label label_7_1 = new Label(container, SWT.NONE);
		label_7_1.setBounds(190, 48, 75, 20);
		label_7_1.setText("（元/平方米）");

		final Label label_7_1_1 = new Label(container, SWT.NONE);
		label_7_1_1.setBounds(190, 82, 78, 20);
		label_7_1_1.setText("（元/平方米）");

		final Label label_7_1_1_1 = new Label(container, SWT.NONE);
		label_7_1_1_1.setBounds(190, 116, 73, 20);
		label_7_1_1_1.setText("（元/平方米）");

		final Label label_7_1_1_2 = new Label(container, SWT.NONE);
		label_7_1_1_2.setBounds(190, 150, 75, 20);
		label_7_1_1_2.setText("（元/平方米）");

		T3text = new Text(container, SWT.BORDER);
		T3text.setBounds(80, 185, 100, 21);
		String T3Value="";
		
	     if(dProp.containsKey("T3")){
		   
      T3Value= dProp.getProperty("T3");}
	     T3text.setText(T3Value);


		final Label label_7_1_1_2_1 = new Label(container, SWT.NONE);
		label_7_1_1_2_1.setBounds(190, 184, 75, 20);
		label_7_1_1_2_1.setText("（元/平方米）");

		T4text = new Text(container, SWT.BORDER);
		T4text.setBounds(374, 10, 100, 21);
		String T4Value="";
		
	     if(dProp.containsKey("T4")){
		   
      T4Value= dProp.getProperty("T4");}
	     T4text.setText(T4Value);


		final Label label_8 = new Label(container, SWT.NONE);
		label_8.setText("开发利息率");
		label_8.setBounds(288, 50, 66, 17);

		final Label label_9 = new Label(container, SWT.NONE);
		label_9.setText("开发利润率");
		label_9.setBounds(288, 83, 65, 18);

		interestText = new Text(container, SWT.BORDER);
		interestText.setBounds(374, 43, 100, 22);
		String interestValue="";
		
	     if(dProp.containsKey("intest")){
		   
	     interestValue= dProp.getProperty("intest");}
	     interestText.setText(interestValue);


		profitText = new Text(container, SWT.BORDER);
		profitText.setBounds(374, 76, 100, 22);
		String profitValue="";
		
	     if(dProp.containsKey("profit")){
		   
	     profitValue= dProp.getProperty("profit");}
	     profitText.setText(profitValue);

		final Label label_10 = new Label(container, SWT.NONE);
		label_10.setText("土地增值收益率");
		label_10.setBounds(288, 117, 87, 17);

		upinterestText = new Text(container, SWT.BORDER);
		upinterestText.setBounds(374, 114, 100, 21);
		String upinterestValue="";
		
	     if(dProp.containsKey("upinterest")){
		   
	     upinterestValue= dProp.getProperty("upinterest");}
	     upinterestText.setText(upinterestValue);

		final Label label_11 = new Label(container, SWT.NONE);
		label_11.setText("土地开发费");
		label_11.setBounds(288, 147, 69, 17);

		EdText = new Text(container, SWT.BORDER);
		EdText.setBounds(374, 143, 100, 21);
		String EdValue="";
		
	     if(dProp.containsKey("Ed")){
		   
	     EdValue= dProp.getProperty("Ed");}
	     EdText.setText(EdValue);

		final Label label_12 = new Label(container, SWT.NONE);
		label_12.setText("开发周期");
		label_12.setBounds(288, 185, 59, 17);

		periodText = new Text(container, SWT.BORDER);
		periodText.setBounds(374, 179, 100, 21);
		String periodValue="";
		
	     if(dProp.containsKey("period")){
		   
	    periodValue= dProp.getProperty("period");}
	     periodText.setText( periodValue);
		

		final Label label_13 = new Label(container, SWT.NONE);
		label_13.setText("（元/平方米）");
		label_13.setBounds(475, 11, 80, 19);

		final Label label_13_1 = new Label(container, SWT.NONE);
		label_13_1.setBounds(475, 151, 80, 20);
		label_13_1.setText("（元/平方米）");

		final Label label_14 = new Label(container, SWT.NONE);
		label_14.setAlignment(SWT.CENTER);
		label_14.setText("*");
		label_14.setBounds(553, 12, 19, 10);

		T1interestText = new Text(container, SWT.BORDER);
		T1interestText.setBounds(573, 9, 70, 20);
		String T1interestValue="";
		
	     if(dProp.containsKey("T1interest")){
		   
	    	 T1interestValue= dProp.getProperty("T1interest");}
	     T1interestText.setText( T1interestValue);

		final Label label_15 = new Label(container, SWT.NONE);
		label_15.setText("(%)");
		label_15.setBounds(647, 11, 18, 15);

		final Label label_16 = new Label(container, SWT.NONE);
		label_16.setText("(%)");
		label_16.setBounds(484, 48, 30, 15);

		final Label label_17 = new Label(container, SWT.NONE);
		label_17.setText("(%)");
		label_17.setBounds(484, 75, 25, 20);

		final Label label_18 = new Label(container, SWT.NONE);
		label_18.setText("(%)");
		label_18.setBounds(484, 114, 25, 20);
	}
	public String getE1Value(){
		//Enumeration e = dProp.keys();
	//	File f=new File(dProp.getProperty("E1"));
//		while (e.hasMoreElements())
//        {
//            Object obj = e.nextElement();
//		 String E1Value="";
//		
//	     if(dProp.containsKey("E1")){
//		   
          String E1Value= E1text.getText();
	     
          //System.out.println(dProp.getProperty(obj.toString()));
	     //System.out.print(E1Value);
		return E1Value;}
	
	public String getE2Value(){
		String E2Value=E2text.getText();
		return E2Value;}
	
	public String getE3Value(){
		String E3Value=E3text.getText();
		return E3Value;}
	
	public String getT1Value(){
		String T1Value=T1text.getText();
		return T1Value;}
	
	public String getT2Value(){
		String T2Value=T2text.getText();
		return T2Value;}
	
	public String getT3Value(){
		String T3Value=T3text.getText();
		return T3Value;}
	
	public String getT4Value(){
		String T4Value=T4text.getText();
		return T4Value;}
	
	public String getinterestValue(){
		String interestValue=interestText.getText();
		return interestValue;}

	public String getprofitValue(){
		String profitValue=profitText.getText();
		return profitValue;}

	public String getupinterestValue(){
		String upinterestValue=upinterestText.getText();
		return upinterestValue;}
	
	public String getEdValue(){
		String EdValue=EdText.getText();
		return EdValue;}
	
	public String getperiodValue(){
		String periodValue=periodText.getText();
		return periodValue;}
	
	public String getT1interestValue(){
		String T1interestValue=T1interestText.getText();
		return T1interestValue;}


	
}
