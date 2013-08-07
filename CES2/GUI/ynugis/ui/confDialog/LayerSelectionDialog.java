package ynugis.ui.confDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import ynugis.application.CESCORE;
import ynugis.excel.GisTable;

import com.esri.arcgis.beans.map.MapBean;
import com.esri.arcgis.carto.FeatureLayer;
import com.esri.arcgis.carto.ILayer;
import com.esri.arcgis.carto.IMap;
import com.esri.arcgis.controls.MapControl;
import com.esri.arcgis.geodatabase.ITable;

public class LayerSelectionDialog extends Dialog {
	private MapBean mapControl;
	private CESCORE cescore;
	private Combo layerC;
	private Text layerinfo;
	private List layers=new ArrayList();

	public LayerSelectionDialog(Shell parent,CESCORE core) {
		super(parent);
		this.cescore=core;
		this.mapControl=core.getMapControl();
	}

	protected Button createButton(Composite parent, int id, String label, boolean defaultButton) {
		return null;
	}

	protected Control createDialogArea(Composite parent) {
		Composite content = new Composite(parent, SWT.NONE);
		{
			GridLayout contentLayout = new GridLayout();
			contentLayout.numColumns = 2;
			contentLayout.marginTop = 20;
			contentLayout.marginLeft = 20;
			contentLayout.marginRight = 20;
			contentLayout.marginBottom = 20;
			contentLayout.horizontalSpacing = 8;
			contentLayout.verticalSpacing = 10;
			content.setLayout(contentLayout);
		}
		layerC=new Combo(content,SWT.READ_ONLY);
		{
			GridData gd=new GridData(GridData.FILL_HORIZONTAL);
			gd.minimumWidth=80;
			layerC.setLayoutData(gd);
			layerC.setText("Layer");
			layerC.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e) {
					
					int seledIndex=layerC.getSelectionIndex();
					System.out.println("seled index:"+seledIndex);
					if(seledIndex!=-1){
						layerinfo.setText("选定图层："+layerC.getItem(seledIndex));
					}
				}});
		}
		final Button layerB=new Button(content,SWT.NONE);
		{
			layerB.setText("属性表");
			layerB.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e) {
					int seledIndex=layerC.getSelectionIndex();
					ILayer seledLayer=null;
					if(seledIndex!=-1){
						seledLayer=(ILayer)layers.toArray()[seledIndex];
						layerinfo.setText("选定图层："+layerC.getItem(seledIndex));
						new PropDialog(getShell(),mapControl,seledLayer).open();
					}
				}});
		}
		
		final Button exportB=new Button(content,SWT.NONE);
		{
			exportB.setText("导出");
			exportB.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e) {
					int seledIndex=layerC.getSelectionIndex();
					ILayer seledLayer=null;
					if(seledIndex!=-1){
						seledLayer=(ILayer)layers.toArray()[seledIndex];
						FeatureLayer featureLayer;
						try {
							featureLayer = new FeatureLayer(seledLayer);
							ITable featureTable=featureLayer.getDisplayTable();
							FileDialog saveDlg=new FileDialog(getShell(),SWT.SAVE);
							saveDlg.setFileName("noname");
							saveDlg.setFilterExtensions(new String[]{".xls"});
							saveDlg.setFilterNames(new String[]{"Excel文件("+".xls"+")"});
							saveDlg.setText("保存到Excel文件");
							String dirPath=saveDlg.open();
							if(dirPath==null||dirPath=="")return;
							export2Xls(featureTable,dirPath);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				}

				private void export2Xls(final ITable featureTable, final String dirPath) {
						
					try {
						// TODO Auto-generated method stub
						System.out.println("Export start!");
							GisTable gt=new GisTable(dirPath);
						//写入属性表头
						for(int i=0;i<featureTable.getFields().getFieldCount();i++)
						{
							gt.writeCell(featureTable.getFields().getField(i).getName(), 0, i);
						}
						//写入属性内容
						for(int c=0;c<featureTable.getFields().getFieldCount();c++)
						{
						for(int r=0;r<featureTable.rowCount(null);r++)
						{
							
							gt.writeCell(featureTable.getRow(r).getValue(c).toString(),r+1,c);
						}
						}
						gt.save();
						System.out.println("Export end!");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					new Thread(new Runnable(){

							public void run() {
								try {
								// TODO Auto-generated method stub
								System.out.println("Export start!");
									GisTable gt=new GisTable(dirPath);
								//写入属性表头
								for(int i=0;i<featureTable.getFields().getFieldCount();i++)
								{
									gt.writeCell(featureTable.getFields().getField(i).getName(), 0, i);
								}
								//写入属性内容
								for(int c=0;c<featureTable.getFields().getFieldCount();c++)
								{
								for(int r=0;r<featureTable.rowCount(null);r++)
								{
									
									gt.writeCell(featureTable.getRow(r).getValue(c).toString(),r+1,c);
								}
								}
								gt.save();
								System.out.println("Export end!");
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}});
						
						
					
					
					/*
					for(int i=2;i<featureTable.getFields().getFieldCount();i++)
					{
						u.writeCell(featureTable.getFields().getField(i).getName(), 0, i);
					for(int j=1;j<R.shpFC.featureCount(null);j++){
			            p=new IPointProxy(featureTable.getRow(j)
						
						u.writeCell( p.getX(),j, 0);
						u.writeCell( p.getY(),j, 1);
						u.writeCell( R.shpFC.getFeature(j-1).getValue(i).toString(),j,i);
						
					}
						
					}*/
					
				}});
		}
		
		layerinfo=new Text(content,SWT.READ_ONLY);
		{
			GridData gd=new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan=2;
			layerinfo.setLayoutData(gd);
			layerinfo.setText("");
		}
		initLayerCombo();
		return content;
	}

	protected void initializeBounds() {
		super.createButton((Composite) getButtonBar(), IDialogConstants.OK_ID,
				"确定", true);
		super.createButton((Composite) getButtonBar(),
				IDialogConstants.CANCEL_ID, "取消", true);
		super.initializeBounds();
	}
	private void initLayerCombo() {
		try {
			
			IMap map = mapControl.getActiveView().getFocusMap();
			if (cescore.getCellGroupLayer() != null) {
				String cellGroupName=cescore.getCellGroupLayer().getName();
				String rankGroupName=cescore.getRankGroupLayer().getName();
				String bgGroupName=cescore.getBgmapGroupLayer().getName();
				String rangeGroupName=cescore.getRangeGroupLayer().getName();
				String obstructGroupName=cescore.getBarrierGroupLayer().getName();
				String isolineGroupName=cescore.getIsolineGroupLayer().getName();
				int gls = cescore.getCellGroupLayer().getCount();
				ILayer layer = null;
				for (int i = 0; i < gls; i++) {
					layer = cescore.getCellGroupLayer().getLayer(i);
					if (layer.isVisible()) {
						layers.add(layer);
						layerC.add(layer.getName());
						
					}
				}
				gls = cescore.getBgmapGroupLayer().getCount();
				for (int i = 0; i < gls; i++) {
					layer = cescore.getBgmapGroupLayer().getLayer(i);
					if (layer.isVisible()) {
						layers.add(layer);
						layerC.add(layer.getName());
					}
				}
				gls = cescore.getRangeGroupLayer().getCount();
				for (int i = 0; i < gls; i++) {
					layer = cescore.getRangeGroupLayer().getLayer(i);
					if (layer.isVisible()) {
						layers.add(layer);
						layerC.add(layer.getName());
					}
				}
				gls = cescore.getBarrierGroupLayer().getCount();
				for (int i = 0; i < gls; i++) {
					layer = cescore.getBarrierGroupLayer().getLayer(i);
					if (layer.isVisible()) {
						layers.add(layer);
						layerC.add(layer.getName());
					}
				}
				gls = cescore.getCellGroupLayer().getCount();
				for (int i = 0; i < gls; i++) {
					layer = cescore.getCellGroupLayer().getLayer(i);
					if (layer.isVisible()) {
						layers.add(layer);
						layerC.add(layer.getName());
					}
				}
				gls = cescore.getIsolineGroupLayer().getCount();
				for (int i = 0; i < gls; i++) {
					layer = cescore.getIsolineGroupLayer().getLayer(i);
					if (layer.isVisible()) {
						layers.add(layer);
						layerC.add(layer.getName());
					}
				}
				int layerNumInMapc=0;
				layerNumInMapc=mapControl.getLayerCount();
				for(int i=0;i<layerNumInMapc;i++){
					layer=mapControl.getLayer(i);
					String layerName=layer.getName();
					if(layerName.equals(cellGroupName)||layerName.equals(rankGroupName)||layerName.equals(rangeGroupName)||layerName.equals(isolineGroupName)||layerName.equals(bgGroupName)||layerName.equals(obstructGroupName)){
						continue;
					}else{
						layers.add(layer);
						layerC.add(layer.getName());
					}
				}
				if (layers.toArray().length == 0) {
					layerinfo.setText("没有图层");
					return;
				} else {
					layerC.select(0);
				}
			} else if (map.getLayerCount() == 0) {
				layerinfo.setText("没有图层");
				return;
			} else {
				ILayer layer;
				int lc = mapControl.getLayerCount();
				for (int i = 0; i < lc; i++) {
					layer = mapControl.getLayer(i);
					if (layer.isVisible()) {
						layers.add(layer);
						layerC.add(layer.getName());
					}
				}
				if (lc > 0) {
					layerC.select(0);
				}
			}
		} catch (Exception e) {
			layerinfo.setText("未获取到图层");
			e.printStackTrace();
		}

	}
}
