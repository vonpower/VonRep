/**
 * @author yddy,create date 2003-11-13
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.application;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;


import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Display;

import ynugis.classifyBusiness.CEManager;
import ynugis.classifyBusiness.classifyItem.BGCMap;
import ynugis.classifyBusiness.classifyItem.CMap;
import ynugis.classifyBusiness.classifyItem.ObstructCMap;
import ynugis.classifyBusiness.classifyItem.RangeCMap;
import ynugis.thread.LoadGroupLayer;
import ynugis.ui.initial.Measure;

import com.esri.arcgis.beans.TOC.TOCBean;
import com.esri.arcgis.beans.map.MapBean;
import com.esri.arcgis.beans.map.MapBeanGeneralPanel;
import com.esri.arcgis.beans.map.MapBeanMapPanel;
import com.esri.arcgis.beans.pagelayout.PageLayoutBean;
import com.esri.arcgis.beans.toolbar.ToolbarBean;
import com.esri.arcgis.carto.GroupLayer;
import com.esri.arcgis.carto.IBasicMap;
import com.esri.arcgis.carto.ILayer;
import com.esri.arcgis.controls.ControlsMapDownCommand;
import com.esri.arcgis.controls.ControlsMapFullExtentCommand;
import com.esri.arcgis.controls.ControlsMapLeftCommand;
import com.esri.arcgis.controls.ControlsMapPageDownCommand;
import com.esri.arcgis.controls.ControlsMapPageLeftCommand;
import com.esri.arcgis.controls.ControlsMapPageRightCommand;
import com.esri.arcgis.controls.ControlsMapPageUpCommand;
import com.esri.arcgis.controls.ControlsMapPanTool;
import com.esri.arcgis.controls.ControlsMapRefreshViewCommand;
import com.esri.arcgis.controls.ControlsMapRightCommand;
import com.esri.arcgis.controls.ControlsMapRotateTool;
import com.esri.arcgis.controls.ControlsMapUpCommand;
import com.esri.arcgis.controls.ControlsMapZoomInTool;
import com.esri.arcgis.controls.ControlsMapZoomOutTool;
import com.esri.arcgis.controls.ControlsMapZoomToLastExtentBackCommand;
import com.esri.arcgis.controls.ControlsMapZoomToLastExtentForwardCommand;
import com.esri.arcgis.controls.ControlsOpenDocCommand;
import com.esri.arcgis.controls.ControlsPageFocusNextMapCommand;
import com.esri.arcgis.controls.ControlsPageFocusPreviousMapCommand;
import com.esri.arcgis.controls.ControlsPageNewMapCommand;
import com.esri.arcgis.controls.ControlsPagePanTool;
import com.esri.arcgis.controls.ControlsPageZoom100PercentCommand;
import com.esri.arcgis.controls.ControlsPageZoomInFixedCommand;
import com.esri.arcgis.controls.ControlsPageZoomInTool;
import com.esri.arcgis.controls.ControlsPageZoomOutFixedCommand;
import com.esri.arcgis.controls.ControlsPageZoomOutTool;
import com.esri.arcgis.controls.ControlsPageZoomPageToLastExtentBackCommand;
import com.esri.arcgis.controls.ControlsPageZoomPageToLastExtentForwardCommand;
import com.esri.arcgis.controls.ControlsPageZoomPageWidthCommand;
import com.esri.arcgis.controls.ControlsPageZoomWholePageCommand;
import com.esri.arcgis.controls.ControlsRedoCommand;
import com.esri.arcgis.controls.ControlsSelectFeaturesTool;
import com.esri.arcgis.controls.ControlsSelectTool;
import com.esri.arcgis.controls.ControlsUndoCommand;
import com.esri.arcgis.controls.ITOCControlEvents;
import com.esri.arcgis.controls.ITOCControlEventsAdapter;
import com.esri.arcgis.controls.ITOCControlEventsOnBeginLabelEditEvent;
import com.esri.arcgis.controls.ITOCControlEventsOnDoubleClickEvent;
import com.esri.arcgis.controls.ITOCControlEventsOnEndLabelEditEvent;
import com.esri.arcgis.controls.ITOCControlEventsOnKeyDownEvent;
import com.esri.arcgis.controls.ITOCControlEventsOnKeyUpEvent;
import com.esri.arcgis.controls.ITOCControlEventsOnMouseDownEvent;
import com.esri.arcgis.controls.ITOCControlEventsOnMouseMoveEvent;
import com.esri.arcgis.controls.ITOCControlEventsOnMouseUpEvent;
import com.esri.arcgis.controls.MapControl;
import com.esri.arcgis.controls.PageLayoutControl;
import com.esri.arcgis.controls.TOCControl;
import com.esri.arcgis.controls.ToolbarControl;
import com.esri.arcgis.controls.ToolbarMenu;
import com.esri.arcgis.controls.esriControlsBorderStyle;
import com.esri.arcgis.controls.esriTOCControlItem;
import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.system.AoInitialize;
import com.esri.arcgis.system.EngineInitializer;
import com.esri.arcgis.system.ObjectCopy;
import com.esri.arcgis.system.esriLicenseExtensionCode;
import com.esri.arcgis.system.esriLicenseProductCode;
import com.esri.arcgis.system.esriLicenseStatus;
import com.esri.arcgis.systemUI.ICommand;
import com.esri.arcgis.systemUI.ITool;
import com.esri.arcgis.systemUI.IToolProxy;
import com.esri.arcgis.systemUI.esriCommandStyles;

public class CESCORE {

	private CEManager ceManager;

	private AoInitialize aoInitialize;

	private MapBean mapControl = null;

	private PageLayoutBean pageLayoutControl = null;

	private TOCBean tocControl = null;

	private ToolbarBean toolbarControlView = null;

	private ToolbarBean toolbarControlEdit = null;

	//private ToolbarMenu toolbarMenu = null;

	private GroupLayer bgmapGroupLayer;

	private GroupLayer rangeGroupLayer;

	private GroupLayer cellGroupLayer;

	private GroupLayer rankGroupLayer;

	private GroupLayer isolineGroupLayer;
	
	private GroupLayer barrierGroupLayer;
	
	private List groupLayers;

	public void initializeArcObject() throws IOException {
		aoInitialize = new AoInitialize();

		if (aoInitialize
				.isProductCodeAvailable(esriLicenseProductCode.esriLicenseProductCodeEngine) == esriLicenseStatus.esriLicenseAvailable) {
			System.out.println("check esriLicenseProductCodeEngine...");
			aoInitialize
					.initialize(esriLicenseProductCode.esriLicenseProductCodeEngine);
			System.out.print("done");
		} else {
			throw new IOException();
		}
		if (aoInitialize
				.isExtensionCodeAvailable(
						esriLicenseProductCode.esriLicenseProductCodeEngine,
						esriLicenseExtensionCode.esriLicenseExtensionCodeSpatialAnalyst) == esriLicenseStatus.esriLicenseAvailable) {
			System.out.println("check esriLicenseExtensionCodeSpatialAnalyst...");
			aoInitialize
					.checkOutExtension(esriLicenseExtensionCode.esriLicenseExtensionCodeSpatialAnalyst);
			System.out.print("done");
		} else {
			throw new IOException();
		}
	}

	

	public void initializeArcEngine() {
		
		EngineInitializer.initializeVisualBeans();
		/*AoInitialize aoInit;
		try {
			aoInit = new AoInitialize();
			aoInit.initialize(esriLicenseProductCode.esriLicenseProductCodeEngine);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}

	public synchronized void initializeAEControls() throws IOException,
			AutomationException {

		mapControl = new MapBean();
		pageLayoutControl = new PageLayoutBean();
		tocControl = new TOCBean();
		toolbarControlView = new ToolbarBean();
		toolbarControlEdit = new ToolbarBean();
		//toolbarMenu = new ToolbarMenu();
	}

	public void shutDownArcEngine() throws Exception {
		aoInitialize
				.checkInExtension(esriLicenseExtensionCode.esriLicenseExtensionCodeSpatialAnalyst);
		aoInitialize.shutdown();
	}

	public void configureAEControls(Frame mapFrame, MapBean mapControl,
			Frame pageFrame, PageLayoutBean pageLayoutControl,
			Frame tocFrame, TOCBean tocControl, Frame toolViewFrame,
			ToolbarBean toolbarControlView, Frame toolEditFrame,
			ToolbarBean toolbarControlEdit) {
		
		mapFrame.add(mapControl);
		pageFrame.add(pageLayoutControl);
		
		tocFrame.add(tocControl);
		
		toolEditFrame.add(toolbarControlEdit);
		
		toolViewFrame.add(toolbarControlView);
		try {
			mapControl.setBorderStyle(esriControlsBorderStyle.esriNoBorder);
			pageLayoutControl
					.setBorderStyle(esriControlsBorderStyle.esriNoBorder);
			tocControl.setBorderStyle(esriControlsBorderStyle.esriNoBorder);

		} catch (AutomationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void buddyAEControl(boolean map) throws Exception {
		// mapControl.setMapByRef(map);
		// pageLayoutControl.setPageLayoutByRef(map.getPageLayout());
		//		
		// tocControl.setBuddyControl(map);
		// toolbarMenu.setHook(tocControl);
		// toolbarControlView.setBuddyControl(map);
		// toolbarControlEdit.setBuddyControl(map);
		// toolbarMenu.setHook(tocControl);
		if (map) {
			System.out.println("buddy mapcontrol");
			toolbarControlView.setBuddyControl(mapControl);
			toolbarControlEdit.setBuddyControl(mapControl);
			tocControl.setBuddyControl(mapControl);

		} else {
			System.out.println("buddy pageLayout");
			toolbarControlView.setBuddyControl(pageLayoutControl);
			tocControl.setBuddyControl(pageLayoutControl);

			// toolbarControlEdit.setBuddyControl(pageLayoutControl);
		}
	}

	public void configureMapCommands(ToolbarBean toolbarControlView)
			throws IOException {
		toolbarControlView.initNew();
		toolbarControlView.addItem(new ControlsSelectTool(), 0, 0, false, 0, esriCommandStyles.esriCommandStyleIconOnly); //open
		toolbarControlView.addItem(new ControlsMapZoomInTool(), 0, -1, true, 0, esriCommandStyles.esriCommandStyleIconAndText); //ZoomIn
		toolbarControlView.addItem(new ControlsMapZoomOutTool(), 0, -1, false, 0, esriCommandStyles.esriCommandStyleIconAndText); //ZoomOut
		toolbarControlView.addItem(new ControlsMapPanTool(), 0, -1, false, 0, esriCommandStyles.esriCommandStyleIconAndText); //Pan
		toolbarControlView.addItem(new ControlsMapFullExtentCommand(), 0, -1, true, 20, esriCommandStyles.esriCommandStyleTextOnly); //FullExtent
		toolbarControlView.addItem(new ControlsMapZoomToLastExtentBackCommand(), 0, -1, false, 0, esriCommandStyles.esriCommandStyleTextOnly); //ZoomToLastExtentBackCommand
		toolbarControlView.addItem(new ControlsMapZoomToLastExtentForwardCommand(), 0, -1, false, 0,esriCommandStyles.esriCommandStyleTextOnly); //ZoomToLastExtentForwardCommand
		
		
		toolbarControlView.addItem(new ControlsMapRefreshViewCommand(),0,-1,false,20,esriCommandStyles.esriCommandStyleIconOnly);
		toolbarControlView.addItem(new ControlsMapRotateTool(),0,-1,false,20,esriCommandStyles.esriCommandStyleIconOnly);
		
		//add end
		toolbarControlView.addItem(new ControlsSelectFeaturesTool(), 0, -1,
				false, 20, esriCommandStyles.esriCommandStyleIconOnly);
		toolbarControlView.addItem(
				"esriControlCommands.ControlsFeatureSelectionMenu", 0, -1,
				false, 0, esriCommandStyles.esriCommandStyleIconOnly);
		toolbarControlView.addItem(new ControlsRedoCommand(),0, -1, false, 20,esriCommandStyles.esriCommandStyleIconOnly);
		toolbarControlView.addItem(new ControlsUndoCommand(),0, -1, false, 20,esriCommandStyles.esriCommandStyleIconOnly);
		toolbarControlView.addItem(new Measure(),0,-1,false,20,esriCommandStyles.esriCommandStyleTextOnly);
		
		try {
			buddyAEControl(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		toolbarControlView.addItem(new MeasureTool(),0,-1,false,20,esriCommandStyles.esriCommandStyleTextOnly);
	}

	public void configurePageLayoutCommands(ToolbarBean toolbarControlView)
			throws IOException {
		toolbarControlView.initNew();
		
		toolbarControlView.addItem(new ControlsPageZoomInTool(),0, -1, false, 0,esriCommandStyles.esriCommandStyleIconOnly);
		toolbarControlView.addItem(new ControlsPageZoomOutTool(),0, -1, false, 20,esriCommandStyles.esriCommandStyleIconOnly);
		toolbarControlView.addItem(new ControlsPageZoomInFixedCommand(),0, -1, false, 0,esriCommandStyles.esriCommandStyleIconOnly);
		toolbarControlView.addItem(new ControlsPageZoomOutFixedCommand(),0, -1, false, 0,esriCommandStyles.esriCommandStyleIconOnly);
		toolbarControlView.addItem(new ControlsPagePanTool(),0, -1, false, 20,esriCommandStyles.esriCommandStyleIconOnly);
		toolbarControlView.addItem(new ControlsPageZoomWholePageCommand(),0, -1, false, 20,esriCommandStyles.esriCommandStyleIconOnly);
		toolbarControlView.addItem(new ControlsPageZoomPageToLastExtentBackCommand(),0, -1, false, 0,esriCommandStyles.esriCommandStyleIconOnly);
		toolbarControlView.addItem(new ControlsPageZoomPageToLastExtentForwardCommand(),0, -1, false, 0,esriCommandStyles.esriCommandStyleIconOnly);
		toolbarControlView.addItem(new ControlsPageZoomPageWidthCommand(),0, -1, false, 20,esriCommandStyles.esriCommandStyleIconOnly);
		toolbarControlView.addItem(new ControlsPageZoom100PercentCommand(),0, -1, false, 20,esriCommandStyles.esriCommandStyleIconOnly);
		toolbarControlView.addItem(new ControlsPageNewMapCommand(),0, -1, false, 20,esriCommandStyles.esriCommandStyleIconOnly);
		toolbarControlView.addItem(new ControlsPageFocusNextMapCommand(),0, -1, false, 20,esriCommandStyles.esriCommandStyleIconOnly);
		toolbarControlView.addItem(new ControlsPageFocusPreviousMapCommand(),0, -1, false, 20,esriCommandStyles.esriCommandStyleIconOnly);
		toolbarControlView.addItem(new ControlsRedoCommand(),0, -1, false, 20,esriCommandStyles.esriCommandStyleIconOnly);
		toolbarControlView.addItem(new ControlsUndoCommand(),0, -1, false, 20,esriCommandStyles.esriCommandStyleIconOnly);
		try {
			buddyAEControl(false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
	
	}
	
	public void configureEditCommands(ToolbarBean toolbarControlEdit)
			throws IOException {
		//toolbarControlEdit.removeAll();
		toolbarControlEdit.initNew();
		toolbarControlEdit.addItem(new com.esri.arcgis.controls.ControlsNewFreeHandTool(),
				0, -1, false,
				20, esriCommandStyles.esriCommandStyleIconOnly);
		toolbarControlEdit.addItem(new com.esri.arcgis.controls.ControlsMapIdentifyTool(),
				0, -1, false,
				20, esriCommandStyles.esriCommandStyleIconOnly);
		
		toolbarControlEdit.addItem(new com.esri.arcgis.controls.ControlsEditingToolbar(),
				0, -1, false,
				20, esriCommandStyles.esriCommandStyleIconOnly);
		try {
			buddyAEControl(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @param withGroup,load
	 *            GroupLayer with parameter 'true',else 'false' after
	 *            initialization
	 * @throws Exception
	 * @usedfor initialize arcengine controls
	 */
	public void resetAEControls(boolean withGroup) throws Exception {
		mapControl.initNew();
		pageLayoutControl.initNew();
		tocControl.initNew();

		tocControl.addITOCControlEventsListener(new ITOCControlEventsAdapter(){
						public void onMouseDown(final ITOCControlEventsOnMouseDownEvent e)
						throws IOException, AutomationException {
					/*
					 * JDialog d=new JDialog(); d.show();
					 */
					
					Display.getDefault().asyncExec(new Runnable() {

						public void run() {

							try {
								int[] itemType = {0};
								IBasicMap[] map = {null};
								ILayer[] layer = {null};
								 Object[] unk={null};
				                 Object[] data={null};

								System.out.println("X:"+e.getX()+"Y:"+e.getY());
								tocControl.getTOCControl().hitTest(e.getY(), e.getX(), itemType,
										map, layer, unk, data);
								
								if(layer[0]==null)
								{
									System.out.println("U Click Nothing !!!");
									return;
								}
							
								switch(itemType[0])
								{
								case esriTOCControlItem.esriTOCControlItemLayer:
								{
									System.out.println("esriTOCControlItem.esriTOCControlItemLayer");
								}break;
								case esriTOCControlItem.esriTOCControlItemMap:
								{
									System.out.println("esriTOCControlItem.esriTOCControlItemMap");
								}break;
								case esriTOCControlItem.esriTOCControlItemNone:
								{
									System.out.println("esriTOCControlItem.esriTOCControlItemNone");
								}break;
								case esriTOCControlItem.esriTOCControlItemHeading:
								{
									System.out.println("esriTOCControlItem.esriTOCControlItemHeading");
								}break;
								case esriTOCControlItem.esriTOCControlItemLegendClass:
								{
									System.out.println("esriTOCControlItem.esriTOCControlItemLegendClass");
								}break;
								
								}
								
								

							} catch (AutomationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					});
					

				
				}

	
			
			
			
			
			
		});
		
		
		
		
		bgmapGroupLayer = null;
		rangeGroupLayer = null;
		cellGroupLayer = null;
		rankGroupLayer = null;
		isolineGroupLayer = null;
		barrierGroupLayer=null;
		
		/*
		 * new list of grouplayer
		 */
//		groupLayers=null;
		
		
		if (withGroup) {
			if (bgmapGroupLayer == null) {
//				groupLayers=new ArrayList();
				bgmapGroupLayer = new GroupLayer();
				bgmapGroupLayer.setName("������ͼ");
//				groupLayers.add(bgmapGroupLayer);
				rangeGroupLayer = new GroupLayer();
				rangeGroupLayer.setName("������Χ");
//				groupLayers.add(rangeGroupLayer);
				barrierGroupLayer=new GroupLayer();
				barrierGroupLayer.setName("���");
//				groupLayers.add(barrierGroupLayer);
				cellGroupLayer = new GroupLayer();
				cellGroupLayer.setName("��Ԫ���ֵ");
//				groupLayers.add(cellGroupLayer);
				rankGroupLayer = new GroupLayer();
				rankGroupLayer.setName("���ؼ���");
//				groupLayers.add(rankGroupLayer);
				isolineGroupLayer = new GroupLayer();
				isolineGroupLayer.setName("���ص�ֵ��");
//				groupLayers.add(isolineGroupLayer);
			}
			bgmapGroupLayer.clear();
			rangeGroupLayer.clear();
			cellGroupLayer.clear();
			rankGroupLayer.clear();
			isolineGroupLayer.clear();
			barrierGroupLayer.clear();
//			for(int k=0;k<groupLayers.toArray().length;k++){
//				GroupLayer gLayer=(GroupLayer)groupLayers.toArray()[k];
//				gLayer.clear();
//			}
			{
				if (this.getCEManager().getCurrentProjectCount() > 0) {
					BGCMap bgcMap = this.getCEManager().getProject().getBgMap();
					RangeCMap rangeMap = this.getCEManager().getProject()
							.getClassifyRange();
					ObstructCMap obsMap=this.getCEManager().getProject().getObstruct();
					CMap[] maps;
					List mapList=new ArrayList();
//					if (bgcMap != null) {
//						if (rangeMap != null) {
//							maps = new CMap[2];
//							maps[0] = bgcMap;
//							maps[1] = rangeMap;
//						} else {
//							maps = new CMap[1];
//							maps[0] = bgcMap;
//						}
					if(bgcMap!=null)mapList.add(bgcMap);
					if(rangeMap!=null)mapList.add(rangeMap);
					if(obsMap!=null)mapList.add(obsMap);
					
						// new ThreadHelper(new
						// LoadGroupLayer(maps,this)).start();
					int len=mapList.toArray().length;
						if (len>0) {
							maps=new CMap[len];
							for(int i=0;i<len;i++){
								maps[i]=(CMap)mapList.toArray()[i];
							}
							new ProgressMonitorDialog(null).run(false, false,
									new LoadGroupLayer(maps, this));
						}

//					}

				}
			}
		}
	}

	public synchronized void resetMapControl(IProgressMonitor monitor,
			int worked) throws Exception {
		int done = worked;
		if (monitor != null) {
			monitor.setTaskName("���µ�ͼ��ʾ��");
		}
		mapControl.initNew();
		pageLayoutControl.initNew();
		tocControl.initNew();
		if (monitor != null) {
			monitor.setTaskName("���ص�1���ͼ");
		}
		mapControl.addLayer(bgmapGroupLayer, 0);
		if (monitor != null) {
			monitor.setTaskName("���ص�2���ͼ");
			monitor.worked(++done);
		}
		mapControl.addLayer(rangeGroupLayer, 0);
		if (monitor != null) {
			monitor.setTaskName("���ص�3���ͼ");
			monitor.worked(++done);
		}
		System.out.println("zuge:"+barrierGroupLayer.getCount());
		mapControl.addLayer(barrierGroupLayer, 0);
		if (monitor != null) {
			monitor.setTaskName("���ص�4���ͼ");
			monitor.worked(++done);
		}
		mapControl.addLayer(isolineGroupLayer, 0);
		if (monitor != null) {
			monitor.setTaskName("���ص�5���ͼ");
			monitor.worked(++done);
		}
		mapControl.addLayer(cellGroupLayer, 0);
		if (monitor != null) {
			monitor.setTaskName("���ص�6���ͼ");
			monitor.worked(++done);
		}
		mapControl.addLayer(rankGroupLayer, 0);
//		for (int i = 0; i < groupLayers.toArray().length; i++) {
//			if (monitor != null) {
//				monitor.setTaskName("���ص�"+i+"���ͼ");
//			}
//			mapControl.addLayer((GroupLayer)groupLayers.toArray()[i], 0);
//			monitor.worked(++done);
//		}
		mapControl.setFullExtent(mapControl.getFullExtent());
		mapControl.layout();
		if (monitor != null) {
			monitor.worked(++done);
			monitor.setTaskName("���ص�ͼ����");
		}
	}

	/*public synchronized void resetMapControl(WaitingSync monitor)
			throws Exception {
		System.out.println("reset mc");
		if (monitor != null) {
			monitor.printinfo("���µ�ͼ��ʾ��");
		}
		mapControl.initNew();
		pageLayoutControl.initNew();
		tocControl.initNew();

		if (monitor != null) {
			monitor.printinfo("���ص�1���ͼ");
		}
		mapControl.addLayer(rangeGroupLayer, 0);
		if (monitor != null) {
			monitor.printinfo("���ص�2���ͼ");
		}
		mapControl.addLayer(isolineGroupLayer, 1);
		if (monitor != null) {
			monitor.printinfo("���ص�3���ͼ");
		}
		mapControl.addLayer(cellGroupLayer, 2);
		if (monitor != null) {
			monitor.printinfo("���ص�4���ͼ");
		}
		mapControl.addLayer(rankGroupLayer, 3);
		if (monitor != null) {
			monitor.printinfo("���ص�5���ͼ");
		}
		mapControl.addLayer(bgmapGroupLayer, 4);
		mapControl.layout();
		if (monitor != null) {
			monitor.printinfo("���ص�ͼ����");
		}

	}*/

	public void setDefaultTool(ToolbarBean toolbarControl, int index)
			throws IOException {
		ICommand command = toolbarControl.getItem(index).getCommand();
		ITool tool = new IToolProxy(command);
		if (toolbarControl.getBuddy() != null) {
			toolbarControl.setCurrentToolByRef(tool);
		}
	}

	public void MapToPage(MapBean source, PageLayoutBean destine) {

		try {
			System.out.println("COPYING Map to Page:"
					+ source.getActiveView().getFocusMap());
			ObjectCopy copy = new ObjectCopy();
			Object mapCopy = source.getMap();
			Object[] objectsOverWrite = { destine.getActiveView().getFocusMap() };
			copy.overwrite(copy.copy(mapCopy), objectsOverWrite);
			destine.setFullExtent(source.getFullExtent());
			destine.zoomToWholePage();
		} catch (IOException ioe) {
			System.out.println("COPY FAILED");
			ioe.printStackTrace();
		}
	}

	public void PageToMap(PageLayoutBean source, MapBean destine) {
		/*
		 * try { System.out.println("COPYING"); ObjectCopy copy = new
		 * ObjectCopy(); Object mapCopy=source.getActiveView().getFocusMap();
		 * Object mapCopyed = copy.copy(mapCopy); Object pageCopy =
		 * destine.getActiveView().getFocusMap(); Object[]
		 * objectsOverWrite={null}; objectsOverWrite[0]=pageCopy;
		 * copy.overwrite(mapCopyed,objectsOverWrite);
		 * destine.setFullExtent(source.getFullExtent());
		 * System.out.println("COPYED"); } catch (IOException ioe) {
		 * System.out.println("COPY FAILED"); ioe.printStackTrace(); }
		 */
	}

	/**
	 * @return Returns the ceManager.
	 * @desire
	 * @usedfor
	 */
	public synchronized CEManager getCEManager() {
		if (ceManager == null) {
			ceManager = new CEManager();
		}
		return ceManager;
	}

	/**
	 * @return Returns the mapControl.
	 * @desire
	 * @usedfor
	 */
	public synchronized MapBean getMapControl() {
		if (mapControl == null) {
			try {
				initializeAEControls();
			} catch (AutomationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return mapControl;
	}

	/**
	 * @return Returns the pageLayoutControl.
	 */
	public PageLayoutBean getPageLayoutControl() {
		if (pageLayoutControl == null) {
			try {
				initializeAEControls();
			} catch (AutomationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return pageLayoutControl;
	}

	/**
	 * @return Returns the tocControl.
	 */
	public TOCBean getTocControl() {
		if (tocControl == null) {
			try {
				initializeAEControls();
			} catch (AutomationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return tocControl;
	}

	/**
	 * @return Returns the toolbarControlEdit.
	 */
	public ToolbarBean getToolbarControlEdit() {
		if (toolbarControlEdit == null) {
			try {
				initializeAEControls();
			} catch (AutomationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return toolbarControlEdit;
	}

	/**
	 * @return Returns the toolbarControlView.
	 */
	public ToolbarBean getToolbarControlView() {
		if (toolbarControlView == null) {
			try {
				initializeAEControls();
			} catch (AutomationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return toolbarControlView;
	}

	/**
	 * @return Returns the toolbarMenu.
	 *//*
	public ToolbarMenu getToolbarMenu() {
		if (toolbarMenu == null) {
			try {
				initializeAEControls();
			} catch (AutomationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return toolbarMenu;
	}
*/
	/**
	 * @return Returns the bgmapGroupLayer.
	 */
	public GroupLayer getBgmapGroupLayer() {
		return bgmapGroupLayer;
	}

	/**
	 * @param bgmapGroupLayer
	 *            The bgmapGroupLayer to set.
	 */
	public void setBgmapGroupLayer(GroupLayer bgmapGroupLayer) {
		this.bgmapGroupLayer = bgmapGroupLayer;
	}

	/**
	 * @return Returns the cellGroupLayer.
	 */
	public GroupLayer getCellGroupLayer() {
		return cellGroupLayer;
	}

	/**
	 * @param cellGroupLayer
	 *            The cellGroupLayer to set.
	 */
	public void setCellGroupLayer(GroupLayer cellGroupLayer) {
		this.cellGroupLayer = cellGroupLayer;
	}

	/**
	 * @return Returns the landGroupLayer.
	 */
	public GroupLayer getRankGroupLayer() {
		return rankGroupLayer;
	}

	/**
	 * @param landGroupLayer
	 *            The landGroupLayer to set.
	 */
	public void setRankGroupLayer(GroupLayer rankGroupLayer) {
		this.rankGroupLayer = rankGroupLayer;
	}

	/**
	 * @return Returns the rangeGroupLayer.
	 */
	public GroupLayer getRangeGroupLayer() {
		return rangeGroupLayer;
	}

	/**
	 * @param rangeGroupLayer
	 *            The rangeGroupLayer to set.
	 */
	public void setRangeGroupLayer(GroupLayer rangeGroupLayer) {
		this.rangeGroupLayer = rangeGroupLayer;
	}

	public GroupLayer getBarrierGroupLayer() {
		return barrierGroupLayer;
	}

	public void setBarrierGroupLayer(GroupLayer barrierGroupLayer) {
		this.barrierGroupLayer = barrierGroupLayer;
	}

	/**
	 * @return Returns the isolineGroupLayer.
	 */
	public GroupLayer getIsolineGroupLayer() {
		return isolineGroupLayer;
	}

	/**
	 * @param isolineGroupLayer
	 *            The isolineGroupLayer to set.
	 */
	public void setIsolineGroupLayer(GroupLayer isolineGroupLayer) {
		this.isolineGroupLayer = isolineGroupLayer;
	}

	public List getGroupLayers() {
		return groupLayers;
	}

/*	public void setGroupLayers(List groupLayers) {
		this.groupLayers = groupLayers;
	}*/
/*	public void setGroupLayers(GroupLayer gLayer){
//		this.groupLayers.add(gLayer);
		try {
			if (groupLayers != null) {
				boolean added=false;
				GroupLayer layer = null;
				for (int i = 0; i < groupLayers.toArray().length; i++) {
					layer = (GroupLayer) groupLayers.toArray()[i];
					if (layer.getName().equals(gLayer.getName())) {
						layer = gLayer;
						added=true;
					}
				}
				if(!added)groupLayers.add(gLayer);
			}else{
				groupLayers=new ArrayList();
				groupLayers.add(gLayer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
