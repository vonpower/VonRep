/*
* @author 冯涛，创建日期：2003-12-16
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
package ynugis.application;

import java.io.IOException;
import java.net.UnknownHostException;

import org.eclipse.jface.action.CoolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import ynugis.geo.Utility;

import com.esri.arcgis.geodatabase.IFeatureClass;

public class TestSWT extends ApplicationWindow {

	/**
	 * Create the application window
	 */
	public TestSWT() {
		super(null);
		createActions();
		addCoolBar(SWT.FLAT);
		addMenuBar();
		addStatusLine();
	}

	/**
	 * Create contents of the application window
	 * @param parent
	 */
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		final Button button = new Button(container, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				
				Thread t=new Thread(new Runnable(){

					public void run() {
						CESCORE cescore=new CESCORE();
						cescore.initializeArcEngine();
						try {
							cescore.initializeArcObject();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							IFeatureClass featureClass=Utility.OpenFeatureClass("E:\\ATest\\integration","房屋出租样点资料-计算_font_point.shp");
							for(int i=0;i<featureClass.featureCount(null);i++)
							{
								System.out.println("feature "+i+" : "+featureClass.getFeature(i).getValue(1));
								
							}
						} catch (UnknownHostException ee) {
							// TODO Auto-generated catch block
							ee.printStackTrace();
						} catch (IOException ee) {
							// TODO Auto-generated catch block
							ee.printStackTrace();
						}
						
					}});
				t.start();
				
			}
		});
		button.setText("button");
		button.setBounds(60, 160, 120, 30);
		//
		return container;
	}

	/**
	 * Create the actions
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Create the menu manager
	 * @return the menu manager
	 */
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");
		return menuManager;
	}

	/**
	 * Create the coolbar manager
	 * @return the coolbar manager
	 */
	protected CoolBarManager createCoolBarManager(int style) {
		CoolBarManager coolBarManager = new CoolBarManager(style);
		return coolBarManager;
	}

	/**
	 * Create the status line manager
	 * @return the status line manager
	 */
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		statusLineManager.setMessage(null, "");
		return statusLineManager;
	}

	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			TestSWT window = new TestSWT();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell
	 * @param newShell
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("New Application");
	}

	/**
	 * Return the initial size of the window
	 */
	protected Point getInitialSize() {
		return new Point(500, 375);
	}

}
