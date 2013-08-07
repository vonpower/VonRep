/**
 * @author yddy,create date 2003-12-30
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.confDialog;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

import ynugis.application.CESCORE;

import com.esri.arcgis.beans.map.MapBean;
import com.esri.arcgis.carto.GroupLayer;
import com.esri.arcgis.carto.IFeatureLayer;
import com.esri.arcgis.carto.IFeatureLayerProxy;
import com.esri.arcgis.carto.IFeatureSelection;
import com.esri.arcgis.carto.IFeatureSelectionProxy;
import com.esri.arcgis.carto.ILayer;
import com.esri.arcgis.carto.IMap;
import com.esri.arcgis.controls.MapControl;
import com.esri.arcgis.geodatabase.ICursor;
import com.esri.arcgis.geodatabase.IField;
import com.esri.arcgis.geodatabase.IFields;
import com.esri.arcgis.geodatabase.IRow;
import com.esri.arcgis.geodatabase.ISelectionSet;
import com.esri.arcgis.geodatabase.esriFieldType;

public class ModifyCellDialog extends Dialog {
	private final int amplify = 1;

	private final String FIELD_NAME = "GRIDCODE";
	
	private Tree tree;

	private CESCORE cescore;

	private MapBean mapControl;

	private GroupLayer cellGroup;

	private IRow[] rows;

	private int[] indexes;

	private int fieldID;

	private Shell shell;

	private List list;

	private Text text;

	private Combo layerCombo;

	private Combo propCombo;

	private Label layerinfo;

	private Label modifyinfo;

	private java.util.List layers;

	private java.util.List fieldList;
	
	private boolean flag=true;

	public ModifyCellDialog(MapBean mapc) {
		super(new Shell());
		mapControl = mapc;
	}

	public ModifyCellDialog(Shell shell, CESCORE core) {
		super(shell);
		layers = new ArrayList();
		fieldList = new ArrayList();
		this.shell = shell;
		mapControl = core.getMapControl();
//		cellGroup = core.getCellGroupLayer();
		cellGroup = core.getRankGroupLayer();
		cescore = core;
	}

	public ModifyCellDialog(Shell shell, CESCORE core,Tree tree) {
		super(shell);
		layers = new ArrayList();
		fieldList = new ArrayList();
		this.shell = shell;
		mapControl = core.getMapControl();
//		cellGroup = core.getCellGroupLayer();
		cellGroup=core.getRankGroupLayer();
		cescore = core;
		this.tree=tree;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createButton(org.eclipse.swt.widgets.Composite,
	 *      int, java.lang.String, boolean)
	 */
	protected Button createButton(Composite parent, int id, String label,
			boolean defaultButton) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		GridLayout contentLayout = new GridLayout();
		contentLayout.numColumns = 2;
		contentLayout.marginTop = 20;
		contentLayout.marginLeft = 20;
		contentLayout.marginRight = 20;
		contentLayout.marginBottom = 20;
		contentLayout.horizontalSpacing = 8;
		contentLayout.verticalSpacing = 10;
		Composite content = new Composite(parent, SWT.NONE);
		content.setLayout(contentLayout);
		GridData gdList = new GridData(GridData.FILL_BOTH);
		gdList.minimumHeight = 200;
		gdList.minimumWidth = 100;
		gdList.verticalSpan = 2;
		gdList.heightHint = 200;
		list = new List(content, SWT.MULTI | SWT.V_SCROLL);
		list.setLayoutData(gdList);
		list.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				text.setText("");
				if (list.getSelectionCount() > 0) {
					int[] indexs = list.getSelectionIndices();
					int index = indexs[0];
					int fieldId = propCombo.getSelectionIndex();
					try {
						double valueD = Double.parseDouble(rows[index].getValue(fieldId)
								.toString());
						valueD /= amplify;
//						String value = rows[index].getValue(fieldId).toString();
						String value=Double.toString(valueD);
						text.setText(value);
					} catch (Exception e1) {
						MessageDialog.openError(shell, "修改单元格值", "获取单元格值失败");
						e1.printStackTrace();
					}
				}
			}
		});
		GridData gdGroup = new GridData();
		gdGroup.minimumWidth=150;
		GridLayout glGroup = new GridLayout();
		glGroup.numColumns = 2;
		Group layerGroup = new Group(content, SWT.SHADOW_IN);
		layerGroup.setLayoutData(gdGroup);
		layerGroup.setLayout(glGroup);
		layerGroup.setText("图层列表");

		GridData gdLabel = new GridData(GridData.FILL_HORIZONTAL);
		gdLabel.horizontalSpan = 2;
		layerinfo = new Label(layerGroup, SWT.NONE);
		layerinfo.setLayoutData(gdLabel);
		new Label(layerGroup, SWT.NONE).setText("选择图层");
		GridData gdCombo = new GridData(GridData.FILL_HORIZONTAL);
		gdCombo.horizontalSpan = 1;
		layerCombo = new Combo(layerGroup, SWT.NONE);
		layerCombo.setLayoutData(gdCombo);
		layerCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// propCombo.removeAll();
				// list.removeAll();
				initPropCombo();
			}
		});

		Group editGroup = new Group(content, SWT.SHADOW_IN);
		editGroup.setLayoutData(gdGroup);
		editGroup.setLayout(glGroup);
		editGroup.setText("属性列表");
		modifyinfo = new Label(editGroup, SWT.NONE);
		modifyinfo.setLayoutData(gdLabel);
		new Label(editGroup, SWT.NONE).setText("选择字段");
		propCombo = new Combo(editGroup, SWT.NONE);
		propCombo.setLayoutData(gdCombo);
		propCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// list.removeAll();
				initList();
			}
		});
		new Label(editGroup, SWT.NONE).setText("修改数值");
		text = new Text(editGroup, SWT.BORDER);
		text.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				text.setText("");
				indexes = list.getSelectionIndices();
				System.out.println("text focused");
			}

			public void focusLost(FocusEvent e) {
				if (indexes == null) {
					return;
				}
				if (indexes.length == 0)
					return;
				if (text.getText().equals(""))
					return;
				if (indexes.length > 0) {
					int[] index = indexes;
					String value = text.getText();
					Double cellValue = null;
					try {
						double valueDouble = Double.parseDouble(value);
						valueDouble *= amplify;
						cellValue = new Double(valueDouble);
					} catch (Exception ex) {
						flag=false;
						modifyinfo.setText("格式错误,非数值");
					}
					if (cellValue == null)
						return;
					try {
						for (int i = 0; i < index.length; i++) {

							rows[index[i]].setValue(fieldID, cellValue);
							rows[index[i]].store();
						}
						initList();
					} catch (Exception e1) {
						modifyinfo.setText("修改单元格值失败");
						e1.printStackTrace();
					} finally {
						mapControl.layout();
						indexes = null;
					}
				}
				// modifyinfo.setText("");
				System.out.println("text focus lose");
			}
		});
		// initializeList();
		initLayerCombo();
		// initPropCombo();
		// initList();
		return content;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#initializeBounds()
	 */
	protected void initializeBounds() {
		super.createButton((Composite) getButtonBar(), IDialogConstants.OK_ID,
				"确定", true);
		super.createButton((Composite) getButtonBar(),
				IDialogConstants.CANCEL_ID, "取消", true);
		super.initializeBounds();
	}

	
	protected void okPressed() {
		if(flag)super.okPressed();
		flag=true;
	}

	private void initLayerCombo() {
		try {
			IMap map = mapControl.getActiveView().getFocusMap();
			if (cellGroup != null) {
				String cellGroupName=cescore.getCellGroupLayer().getName();
				String rankGroupName=cescore.getRankGroupLayer().getName();
				String bgGroupName=cescore.getBgmapGroupLayer().getName();
				String rangeGroupName=cescore.getRangeGroupLayer().getName();
				String obstructGroupName=cescore.getBarrierGroupLayer().getName();
				String isolineGroupName=cescore.getIsolineGroupLayer().getName();
				int gls = cellGroup.getCount();
				ILayer layer = null;
				for (int i = 0; i < gls; i++) {
					layer = cellGroup.getLayer(i);
					if (layer.isVisible()) {
						layers.add(layer);
						layerCombo.add(layer.getName());
						
					}
				}
				gls = cescore.getBgmapGroupLayer().getCount();
				for (int i = 0; i < gls; i++) {
					layer = cescore.getBgmapGroupLayer().getLayer(i);
					if (layer.isVisible()) {
						layers.add(layer);
						layerCombo.add(layer.getName());
					}
				}
				gls = cescore.getRangeGroupLayer().getCount();
				for (int i = 0; i < gls; i++) {
					layer = cescore.getRangeGroupLayer().getLayer(i);
					if (layer.isVisible()) {
						layers.add(layer);
						layerCombo.add(layer.getName());
					}
				}
				gls = cescore.getBarrierGroupLayer().getCount();
				for (int i = 0; i < gls; i++) {
					layer = cescore.getBarrierGroupLayer().getLayer(i);
					if (layer.isVisible()) {
						layers.add(layer);
						layerCombo.add(layer.getName());
					}
				}
				gls = cescore.getCellGroupLayer().getCount();
				for (int i = 0; i < gls; i++) {
					layer = cescore.getCellGroupLayer().getLayer(i);
					if (layer.isVisible()) {
						layers.add(layer);
						layerCombo.add(layer.getName());
					}
				}
				gls = cescore.getIsolineGroupLayer().getCount();
				for (int i = 0; i < gls; i++) {
					layer = cescore.getIsolineGroupLayer().getLayer(i);
					if (layer.isVisible()) {
						layers.add(layer);
						layerCombo.add(layer.getName());
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
						layerCombo.add(layer.getName());
					}
				}
				if (layers.toArray().length == 0) {
					layerinfo.setText("没有图层");
					return;
				} else {
					layerCombo.select(0);
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
						layerCombo.add(layer.getName());
					}
				}
				if (lc > 0) {
					layerCombo.select(0);
				}
			}
			layerinfo.setText("选择所选要素所在的图层");
			initPropCombo();
		} catch (Exception e) {
			layerinfo.setText("未获取到图层");
			e.printStackTrace();
		}

	}

	private void initPropCombo() {
		propCombo.removeAll();
		text.setText("");
		if (layers.toArray().length > 0) {
			Object obj = layers.toArray()[layerCombo.getSelectionIndex()];
			ILayer layer = (ILayer) obj;
			try {
				IFeatureLayer featureLayer = new IFeatureLayerProxy(layer);
				IFields fields = featureLayer.getFeatureClass().getFields();
				IField field;
				int hasDefault = 0;
				for (int i = 0; i < fields.getFieldCount(); i++) {
					field = fields.getField(i);
					fieldList.add(field);
					propCombo.add(field.getName());
					if (field.getName().equals(FIELD_NAME)) {
						hasDefault = i;
					}
				}
				propCombo.select(hasDefault);
				// fieldID=hasDefault;
				initList();
			} catch (IOException e) {
				modifyinfo.setText("未获取到属性");
				e.printStackTrace();
			}
		} else {
			modifyinfo.setText("没有图层");
			return;
		}
	}

	private void initList() {
		fieldID = propCombo.getSelectionIndex();
		list.removeAll();
		try {
			if (layers.toArray().length == 0) {
				return;
			}
			Object objl = layers.toArray()[layerCombo.getSelectionIndex()];
			ILayer layer = (ILayer) objl;
			int fieldId = propCombo.getSelectionIndex();
			IFeatureSelection featureSelection = null;
			ICursor[] cursors = new ICursor[1];
			IFeatureLayer featureLayer = new IFeatureLayerProxy(layer);
			featureSelection = new IFeatureSelectionProxy(featureLayer);
			ISelectionSet selectionSet = featureSelection.getSelectionSet();
			int selectionCount = selectionSet.getCount();
			if (selectionCount == 0) {
				modifyinfo.setText("没有要素被选择");
				return;
			}
			int type = selectionSet.getTarget().getFields().getField(fieldId)
					.getType();
			boolean editable = selectionSet.getTarget().getFields().getField(
					fieldId).isEditable();
			if (type != esriFieldType.esriFieldTypeDouble || !editable) {
				modifyinfo.setText("此字段值不能更改");
				return;
			}
			selectionSet.search(null, false, cursors);
			rows = new IRow[selectionCount];
			for (int i = 0; i < selectionCount; i++) {
				rows[i] = cursors[0].nextRow();
				double value = Double.parseDouble(rows[i].getValue(fieldId)
						.toString());
				value /= amplify;
				list.add(Double.toString(value));
			}
			modifyinfo.setText("选择要更改列,在下方框内填写更改的值");
		} catch (Exception e) {
			modifyinfo.setText("未获取到字段值");
		}
	}
/*
	protected void okPressed() {
		if(tree==null){
			super.okPressed();
		}
		try {
			TreeItem[] selection = tree.getSelection();
			if (layers.toArray().length > 0&&cellGroup.getCount()>1&&selection.length==1) {
				Object obj = layers.toArray()[layerCombo.getSelectionIndex()];
				ILayer layer = (ILayer) obj;
				IFeatureLayer featureLayer = new IFeatureLayerProxy(layer);
				IFeatureClass featureClass=featureLayer.getFeatureClass();
				for(int i=0;i<cellGroup.getCount();i++){
					layer=cellGroup.getLayer(i);
					if(layer.getName().equals("计算数据"))break;
				}
				TreeItem treeItem=selection[0];
				FFItem ffitem=(FFItem)treeItem.getData();
				IRasterLayer rasterLayer=new IRasterLayerProxy(layer);
				Utility.shp2Raster(featureClass,rasterLayer.getRaster());
			}
			super.okPressed();
		} catch (Exception e) {
			modifyinfo.setText("更新过程中出现错误");
		}
//		super.okPressed();
	}*/

	/*
	 * private void initializeList() { list.removeAll(); int selNum = 0; // try {
	 * try { selNum = mapControl.getActiveView().getFocusMap()
	 * .getSelectionCount(); if (selNum == 0) { flag = 2; message(); } } catch
	 * (Exception e) { flag = 1; message(); e.printStackTrace(); } if (flag > 0)
	 * return; rows = new IRow[selNum]; IFeatureSelection featureSelection =
	 * null; ICursor[] cursors = new ICursor[1]; try { ILayer layer =
	 * mapControl.getActiveView().getFocusMap().getLayer(0); IFeatureLayer
	 * featureLayer = new IFeatureLayerProxy(layer); featureSelection = new
	 * IFeatureSelectionProxy(featureLayer);
	 * featureSelection.getSelectionSet().search(null, false, cursors); } catch
	 * (Exception e) { flag = 3; message(); e.printStackTrace(); } if (flag > 0)
	 * return; try { fieldID =
	 * featureSelection.getSelectionSet().getTarget().findField( FIELD_NAME); if
	 * (fieldID == -1) { flag = 4; message(); } } catch (Exception e) { flag =
	 * 4; message(); } if (flag > 0) return; try { for (int i = 0; i < selNum;
	 * i++) { rows[i] = cursors[0].nextRow();
	 * list.add(rows[i].getValue(fieldID).toString()); } } catch (Exception e) {
	 * flag = 5; message(); } // } catch (Exception e) { // // TODO
	 * Auto-generated catch block // e.printStackTrace(); // }
	 *  }
	 * 
	 * private void message() { switch (flag) {
	 *//**
		 * 运作异常处理 1:未获取到图层
		 */
	/*
	 * case 1: {
	 *  }
	 *//**
		 * 2:没有要素被选中
		 */
	/*
	 * case 2: {
	 *  }
	 *//**
		 * 3:未能获取到要素
		 */
	/*
	 * case 3: {
	 *  }
	 *//**
		 * 4:未能获取到字段
		 */
	/*
	 * case 4: {
	 *  }
	 *//**
		 * 5:未能获取到字段值
		 */
	/*
	 * case 5: {
	 *  }
	 *//**
		 * 6:用于更改的值不合法
		 */
	/*
	 * case 6: {
	 *  }
	 *//**
		 * 7:更改值失败
		 */
	/*
	 * case 7: {
	 *  } } }
	 */
}
