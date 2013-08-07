/*
 * @author ���Σ��������ڣ�2003-11-15
 * blog--http://apower.blogone.net
 * QQ:17463776
 * MSN:apower511@msn.com
 * E-Mail:VonPower@Gmail.com
 */
package ynugis.ffsystem;

import java.io.IOException;
import java.net.UnknownHostException;

public class FFTCalculator implements Runnable {

	final private FFItem item;

	final private CalculatorInfo calculInfo;

	public FFTCalculator(FFItem inItem, CalculatorInfo inCalculInfo) {
		item = inItem;
		calculInfo = inCalculInfo;

	}

	public void run() {

		// monitor.beginTask("�������Ӽ��㡭������",100);
		//WaitingSync ws=new WaitingSync("�������Ӽ��㡭������");
		Thread t=new Thread(new Runnable(){

			public void run() {
				try {
					
					//ws.open();
					long startTime=System.currentTimeMillis();
					item.calculate(calculInfo);
					long endTime=System.currentTimeMillis();
					System.out.println("Time consume:"+(startTime-endTime));
					// MessageDialog.openError(null,"����","������̳���,�����Դ�DEBUGģʽ�����鿴��");
					// monitor.done();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally
				{
					//ws.close();
				}
				
			}});
		
		t.start();
	}

}
