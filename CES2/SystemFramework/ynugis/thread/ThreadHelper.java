/**
 * @author yddy,create date 2003-11-17 
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.thread;

import org.eclipse.swt.widgets.Display;

public class ThreadHelper extends Thread {
	private Runnable runnable;
	public ThreadHelper(Runnable run){
		runnable=run;
	}
	public void run(){
		Display.getDefault().syncExec(runnable);
	}
}
