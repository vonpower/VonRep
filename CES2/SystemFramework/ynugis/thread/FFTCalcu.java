/**
 * @author yddy,create date 2003-12-21
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.thread;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;

import ynugis.ffsystem.CalculatorInfo;
import ynugis.ffsystem.FFItem;

public class FFTCalcu extends Thread {
	private IProgressMonitor	pmonitor;

	private int					total;

	private boolean				run;

	private String				name;
	
	private FFItem ffitem;
	
	private CalculatorInfo calc;

	public FFTCalcu(String task, int steps) {
		name = task;
		total = steps;
		run=true;
	}
	public FFTCalcu(String task,int steps,FFItem item,CalculatorInfo calcInfo){
		this(task,steps);
		ffitem=item;
		calc=calcInfo;
	}

	public void run() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {
					ProgressMonitorDialog pmd = new ProgressMonitorDialog(null);
					pmd.run(false, false, new PMonitor());
				} catch (Exception e) {
					pmonitor.done();
				}
			}
		});
//		while (run) {
//			try {
//				Thread.sleep(100);
//				now = 1;
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		try {
						
			long startTime=System.currentTimeMillis();
			ffitem.calculate(calc);
			long endTime=System.currentTimeMillis();
			System.out.println("Time consume:"+(endTime-startTime));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			close();
		}
	}
	
	public void close(){
		run=false;
	}

	private class PMonitor implements IRunnableWithProgress {

		public void run(IProgressMonitor monitor)
				throws InvocationTargetException, InterruptedException {
			pmonitor = monitor;
			if (run) {
				monitor.beginTask(name, total);
				while (run) {
//					if (now == 1) {
//						monitor.worked(now);
//						now = 0;
//					}
					Thread.sleep(300);
					monitor.worked(1);
				}
				monitor.done();
			}
		}

	}
}
