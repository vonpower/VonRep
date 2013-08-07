/**
 * @author yddy,create date 2003-11-16 
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.thread;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.esri.arcgis.beans.map.MapBean;
import com.esri.arcgis.controls.MapControl;

public class LoadMapfile implements IRunnableWithProgress {
	private MapBean mapControl;
	private String[] names;
	private String path;
	
	public LoadMapfile(String path,String name,MapBean control){
		names=new String[1];
		names[0]=name;
		this.path=path;
		mapControl=control;
	}
	public LoadMapfile(String path,String[] names,MapBean control){
		this.names=names;
		this.path=path;
		mapControl=control;
	}
	/*public void run(){
		if(names==null||path==null){
			return;
		}
		if(names.length==0){
			return;
		}
		WaitingSync monitor=new WaitingSync(null,false);
		monitor.open();
		
		try {
			for (int i = 0; i < names.length; i++) {
				if (names[i] == null) {
					continue;
				}
				monitor.printinfo("载入第"+(i+1)+"层图层");
				if (isShapefile(names[i])) {
					mapControl.addShapeFile(path, names[i]);
				}else if (isLyrfile(names[i])) {
					mapControl.addLayerFromFile(path+"\\"+names[i],mapControl.getLayerCount());
				}
			}
			monitor.printinfo("载入图层完成");
		} catch (Exception e) {
			monitor.printinfo("载入图层失败");
			e.printStackTrace();
		}finally{
			monitor.close();
		}
	}*/
	private boolean isShapefile(String name){
		return name.toUpperCase().endsWith(".SHP");
	}
	private boolean isLyrfile(String name){
		return name.toUpperCase().endsWith(".LYR");
	}
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		if(names==null||path==null){
			return;
		}
		if(names.length==0){
			return;
		}
		monitor.beginTask("载入地图...",names.length);
		try {
			for (int i = 0; i < names.length; i++) {
				if (names[i] == null) {
					continue;
				}
				monitor.setTaskName("载入图层:"+path+names[i]);
				if (isShapefile(names[i])) {
					mapControl.addShapeFile(path, names[i]);
				}else if (isLyrfile(names[i])) {
					mapControl.addLayerFromFile(path+"\\"+names[i],mapControl.getLayerCount());
				}
				monitor.worked(i+1);
			}
			monitor.setTaskName("载入图层完成");
		} catch (Exception e) {
			monitor.setTaskName("载入图层失败");
			e.printStackTrace();
		}finally{
			monitor.done();
		}
	}
}
