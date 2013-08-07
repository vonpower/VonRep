/**
 * @author yddy,create date 2003-11-16
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.thread;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import ynugis.application.CESCORE;
import ynugis.ui.confDialog.WaitingSync;

import com.esri.arcgis.beans.map.MapBean;
import com.esri.arcgis.controls.MapControl;

public class LoadMxdfile implements Runnable,IRunnableWithProgress {
	private CESCORE	cescore;

	private String	file;

	public LoadMxdfile(String file, CESCORE core) {
		this.file = file;
		this.cescore = core;
	}

	public void run() {
		WaitingSync monitor=new WaitingSync("载入地图文件",false);
		monitor.open();
		try {
			MapBean mapc = cescore.getMapControl();
			boolean check = mapc.checkMxFile(file);
			if (check) {
				monitor.printinfo("载入地图文件");
				mapc.loadMxFile(file, new Integer(0), null);
				monitor.printinfo("载入地图完成");
			}else{
				monitor.printinfo("不合法文件");
				monitor.printinfo("文件检查失败");
			}
		} catch (Exception e) {
			monitor.printinfo("载入文件失败");
			e.printStackTrace();
		}finally{
			monitor.close();
		}
	}

	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		try {
			MapBean mapc = cescore.getMapControl();
			boolean check = mapc.checkMxFile(file);
			if (check) {
				monitor.beginTask("载入地图文件",1);
				mapc.loadMxFile(file, new Integer(0), null);
				monitor.setTaskName("载入地图完成");
				monitor.worked(1);
			}else{
				monitor.setTaskName("不合法文件");
			}
		} catch (Exception e) {
			monitor.setTaskName("载入文件失败");
			e.printStackTrace();
		}finally{
			monitor.done();
		}
	}
}
