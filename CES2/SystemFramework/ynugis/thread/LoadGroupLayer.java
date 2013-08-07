/**
 * @author yddy,create date 2003-11-16
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.thread;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;

import ynugis.application.CESCORE;
import ynugis.classifyBusiness.classifyItem.BGCMap;
import ynugis.classifyBusiness.classifyItem.CMap;
import ynugis.classifyBusiness.classifyItem.CellValueCMap;
import ynugis.classifyBusiness.classifyItem.ObstructCMap;
import ynugis.classifyBusiness.classifyItem.RangeCMap;
import ynugis.classifyBusiness.classifyItem.RankCMap;

import com.esri.arcgis.carto.GroupLayer;

public class LoadGroupLayer implements IRunnableWithProgress {
	private CMap[]	maps;

	private CESCORE	cescore;

	private Shell shell;
	
	public LoadGroupLayer(CMap inmap, CESCORE incore) {
		maps = new CMap[1];
		maps[0] = inmap;
		cescore = incore;
	}

	public LoadGroupLayer(CMap[] inmaps, CESCORE incore) {
		maps = inmaps;
		cescore = incore;
	}

	public LoadGroupLayer(CMap[] inmaps,CESCORE incore,Shell shell){
		this(inmaps,incore);
		this.shell=shell;
	}
	
	
/*	public void run() {
		if (maps == null || cescore == null) {
			return;
		}
		if (maps.length == 1 && maps[0] == null) {
			return;
		}
		if(shell==null){
			shell=new Shell();
		}
		WaitingSync monitor=new WaitingSync(null,false);
		monitor.open();
		try {
			for (int i = 0; i < maps.length; i++) {
				monitor.printinfo("获取第"+(i+1)+"组地图");
				GroupLayer groupLayer = maps[i].getGroupLayer();
				if (groupLayer != null) {
					if (maps[i] instanceof BGCMap) {
						groupLayer.setName("工作底图");
						cescore.setBgmapGroupLayer(groupLayer);
					} else if (maps[i] instanceof RangeCMap) {
						groupLayer.setName("定级范围");
						cescore.setRangeGroupLayer(groupLayer);
					} else if (maps[i] instanceof IsolineCMap) {
						groupLayer.setName("单元格分值");
						cescore.setIsolineGroupLayer(groupLayer);
					} else if (maps[i] instanceof CellValueCMap) {
						groupLayer.setName("土地级别");
						cescore.setCellGroupLayer(groupLayer);
					} else if (maps[i] instanceof RankCMap) {
						groupLayer.setName("因素等值线");
						cescore.setRankGroupLayer(groupLayer);
					}
				}
			}
			monitor.printinfo("获取地图结束");
			cescore.resetMapControl(monitor);
			
		} catch (Exception e) {
			monitor.printinfo("获取地图失败");
			e.printStackTrace();
		}finally{
			monitor.close();
		}
	}*/

	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		if (maps == null || cescore == null) {
			return;
		}
		if (maps.length == 1 && maps[0] == null) {
			return;
		}
		if(shell==null){
			shell=new Shell();
		}
		monitor.beginTask("获取地图...",maps.length+6);
		System.out.println(maps.length);
		try {
			int i;
			for (i = 0; i < maps.length; i++) {
				monitor.setTaskName("获取第 "+(i+1)+" 组地图");
				System.out.println(maps.length);
				GroupLayer groupLayer = maps[i].getGroupLayer();
				System.out.println("获取第 "+(i+1)+" 组地图");
				if (groupLayer != null) {
					if (maps[i] instanceof BGCMap) {
						groupLayer.setName("工作底图");
						cescore.setBgmapGroupLayer(groupLayer);
					} else if (maps[i] instanceof RangeCMap) {
						groupLayer.setName("定级范围");
						cescore.setRangeGroupLayer(groupLayer);
					} else if (maps[i] instanceof ObstructCMap) {
						System.out.println("zuge map:"+maps[i]);
						groupLayer.setName("阻隔");
						cescore.setBarrierGroupLayer(groupLayer);
					}
					/*else if (maps[i] instanceof IsolineCMap) {
						groupLayer.setName("因素等值线");
						cescore.setIsolineGroupLayer(groupLayer);
					}*/
					else if (maps[i] instanceof CellValueCMap) {
						groupLayer.setName("单元格分值");
						cescore.setCellGroupLayer(groupLayer);
					} else if (maps[i] instanceof RankCMap) {
						groupLayer.setName("土地级别");
						cescore.setRankGroupLayer(groupLayer);
					}
//					if (maps[i] instanceof BGCMap) {
//						groupLayer.setName("工作底图");
//						cescore.setGroupLayers(groupLayer);
//					} else if (maps[i] instanceof RangeCMap) {
//						groupLayer.setName("定级范围");
//						cescore.setGroupLayers(groupLayer);
//					} else if(maps[i] instanceof ObstructCMap){
//						groupLayer.setName("阻隔");
//						cescore.setGroupLayers(groupLayer);
//					}else if (maps[i] instanceof IsolineCMap) {
//						groupLayer.setName("因素等值线");
//						cescore.setGroupLayers(groupLayer);
//					} else if (maps[i] instanceof CellValueCMap) {
//						groupLayer.setName("单元格分值");
//						cescore.setGroupLayers(groupLayer);
//					} else if (maps[i] instanceof RankCMap) {
//						groupLayer.setName("土地级别");
//						cescore.setGroupLayers(groupLayer);
//					}
					monitor.worked(i+1);
				}
			}
			monitor.setTaskName("地图载入完成");
			cescore.resetMapControl(monitor,i);
			
		} catch (Exception e) {
			monitor.setTaskName("获取地图失败");
			e.printStackTrace();
		}finally{
			monitor.done();
		}
	}
}
