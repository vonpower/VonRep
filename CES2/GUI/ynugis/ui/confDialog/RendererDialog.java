package ynugis.ui.confDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import ynugis.application.CESCORE;

import com.esri.arcgis.beans.map.MapBean;
import com.esri.arcgis.carto.ClassBreaksRenderer;
import com.esri.arcgis.carto.FeatureLayer;
import com.esri.arcgis.carto.GroupLayer;
import com.esri.arcgis.carto.IFeatureIDSet;
import com.esri.arcgis.carto.IFeatureRenderer;
import com.esri.arcgis.carto.IGeoFeatureLayer;
import com.esri.arcgis.carto.ILayer;
import com.esri.arcgis.carto.ILegendClass;
import com.esri.arcgis.carto.ILegendGroup;
import com.esri.arcgis.carto.ILegendInfo;
import com.esri.arcgis.carto.ILegendItem;
import com.esri.arcgis.carto.IMap;
import com.esri.arcgis.carto.ISimpleRenderer;
import com.esri.arcgis.carto.LegendClass;
import com.esri.arcgis.carto.LegendGroup;
import com.esri.arcgis.carto.esriViewDrawPhase;
import com.esri.arcgis.controls.MapControl;
import com.esri.arcgis.display.IDisplay;
import com.esri.arcgis.display.IRgbColor;
import com.esri.arcgis.display.ISimpleLineSymbol;
import com.esri.arcgis.display.ISimpleMarkerSymbol;
import com.esri.arcgis.display.ISymbol;
import com.esri.arcgis.display.ISymbolProxy;
import com.esri.arcgis.display.RgbColor;
import com.esri.arcgis.display.SimpleFillSymbol;
import com.esri.arcgis.display.SimpleLineSymbol;
import com.esri.arcgis.display.SimpleMarkerSymbol;
import com.esri.arcgis.display.esriSimpleFillStyle;
import com.esri.arcgis.display.esriSimpleLineStyle;
import com.esri.arcgis.display.esriSimpleMarkerStyle;
import com.esri.arcgis.geodatabase.DataStatistics;
import com.esri.arcgis.geodatabase.ICursor;
import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureCursor;
import com.esri.arcgis.geodatabase.IFeatureDraw;
import com.esri.arcgis.geodatabase.IFeatureDrawProxy;
import com.esri.arcgis.geodatabase.IField;
import com.esri.arcgis.geodatabase.IFields;
import com.esri.arcgis.geodatabase.IQueryFilter;
import com.esri.arcgis.geodatabase.ITable;
import com.esri.arcgis.geodatabase.QueryFilter;
import com.esri.arcgis.geodatabase.esriDrawStyle;
import com.esri.arcgis.geometry.esriGeometryType;
import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.system.IStatisticsResults;
import com.esri.arcgis.system.ITrackCancel;
import com.esri.arcgis.system.esriDrawPhase;

public class RendererDialog extends Dialog {
	private MapBean mapControl;

	private FeatureLayer featureLayer;

	private CESCORE cescore;

	private Combo layerCombo;

	private Combo fieldCombo;

	private Text info;

	private Spinner spinner;

	private Table table;

	private List layers;

	private Label[] colorLabel;

	private double maximum, minimum;

	private final String FIELD_NAME = "GRIDCODE";

	private final int BUTTON_APPLY = 100;

	public RendererDialog(CESCORE cescore) {
		super(new Shell());
		this.cescore = cescore;
		this.mapControl = cescore.getMapControl();
		layers = new ArrayList();
	}

	protected Control createDialogArea(Composite parent) {
		final Composite content = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 15;
		gridLayout.numColumns = 2;
		gridLayout.marginTop = 20;
		gridLayout.marginRight = 10;
		gridLayout.marginBottom = 10;
		gridLayout.marginLeft = 10;
		content.setLayout(gridLayout);

		final Label layerLabel = new Label(content, SWT.NONE);
		layerLabel.setAlignment(SWT.RIGHT);
		final GridData gridData_1 = new GridData(GridData.END, GridData.CENTER,
				false, false);
		gridData_1.minimumWidth = 50;
		layerLabel.setLayoutData(gridData_1);
		layerLabel.setText("图层");

		layerCombo = new Combo(content, SWT.NONE);
		final GridData layerComboGD = new GridData(GridData.CENTER,
				GridData.CENTER, true, false);
		layerComboGD.minimumWidth = 150;
		layerComboGD.horizontalIndent = 10;
		layerCombo.setLayoutData(layerComboGD);

		final Label fieldLabel = new Label(content, SWT.NONE);
		fieldLabel.setAlignment(SWT.RIGHT);
		final GridData gridData_2 = new GridData(GridData.END, GridData.CENTER,
				false, false);
		gridData_2.minimumWidth = 50;
		fieldLabel.setLayoutData(gridData_2);
		fieldLabel.setText("列名");

		fieldCombo = new Combo(content, SWT.NONE);
		fieldCombo.setTextLimit(300);
		final GridData fieldComboGD = new GridData(GridData.CENTER,
				GridData.CENTER, true, false);
		fieldComboGD.minimumWidth = 150;
		fieldComboGD.horizontalIndent = 10;
		fieldCombo.setLayoutData(fieldComboGD);

		final Label infoLabel = new Label(content, SWT.NONE);
		infoLabel.setAlignment(SWT.RIGHT);
		final GridData gridData_i = new GridData(GridData.END, GridData.CENTER,
				false, false);
		gridData_i.minimumWidth = 50;
		fieldLabel.setLayoutData(gridData_i);
		infoLabel.setText("图层信息");

		info = new Text(content, SWT.BORDER | SWT.WRAP | SWT.MULTI
				| SWT.V_SCROLL);
		info.setEditable(false);
		final GridData gridData_6 = new GridData(GridData.FILL_BOTH);
		gridData_6.minimumHeight = 60;
		gridData_6.horizontalIndent = 15;
		info.setLayoutData(gridData_6);

		final Label levelLabel = new Label(content, SWT.NONE);
		final GridData gridData_4 = new GridData(GridData.END, GridData.CENTER,
				false, false);
		gridData_4.minimumWidth = 50;
		levelLabel.setLayoutData(gridData_4);
		levelLabel.setAlignment(SWT.RIGHT);
		levelLabel.setText("级数");

		spinner = new Spinner(content, SWT.BORDER);
		final GridData gridData_5 = new GridData(GridData.CENTER,
				GridData.CENTER, false, false);
		gridData_5.minimumWidth = 100;
		gridData_5.minimumHeight = 100;
		spinner.setLayoutData(gridData_5);
		spinner.setMinimum(1);
		spinner.setMaximum(100);
		spinner.setSelection(1);

		final Label renderLabel = new Label(content, SWT.NONE);
		final GridData gridData_3 = new GridData(GridData.BEGINNING,
				GridData.CENTER, false, false, 2, 1);
		gridData_3.minimumWidth = 50;
		renderLabel.setLayoutData(gridData_3);
		renderLabel.setText("着色方案");

		setLayers();

		table = new Table(content, SWT.BORDER | SWT.V_SCROLL | SWT.SINGLE
				| SWT.FULL_SELECTION | SWT.HIDE_SELECTION);
		table.setSize(360, 450);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		final GridData gridData = new GridData(GridData.FILL, GridData.FILL,
				true, true, 2, 1);
		gridData.minimumWidth = 350;
		gridData.minimumHeight = 150;
		table.setLayoutData(gridData);

		final TableColumn idCol = new TableColumn(table, SWT.CENTER);
		idCol.setWidth(50);
		idCol.setText("级别");

		final TableColumn symbolCol = new TableColumn(table, SWT.CENTER);
		symbolCol.setWidth(150);
		symbolCol.setText("符号(单击行,选择颜色)");

		final TableColumn rangeCol = new TableColumn(table, SWT.CENTER);
		rangeCol.setWidth(280);
		rangeCol.setText("范围(每段上限,例如:1~20,只需填20即可)");
		setTableControl();
		setListeners();
		setTypeInformation();
//		setFieldInformation();
		setFieldInfoStatistics();
		return content;
	}

	protected Button createButton(Composite parent, int id, String label,
			boolean defaultButton) {
		return null;
	}

	protected void initializeBounds() {
		super.createButton((Composite) getButtonBar(), IDialogConstants.OK_ID,
				"确定", true);
		super.createButton((Composite) getButtonBar(), BUTTON_APPLY, "应用",
				false);
		super.initializeBounds();
	}

	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			super.buttonPressed(buttonId);
		} else {
			setRender();
		}
	}

	private void setLayers() {
		try {
			IMap map = mapControl.getActiveView().getFocusMap();
			GroupLayer rankGroup = cescore.getRankGroupLayer();
			if (rankGroup != null) {
				String cellGroupName = cescore.getCellGroupLayer().getName();
				String rankGroupName = cescore.getRankGroupLayer().getName();
				String bgGroupName = cescore.getBgmapGroupLayer().getName();
				String rangeGroupName = cescore.getRangeGroupLayer().getName();
				String obstructGName=cescore.getBarrierGroupLayer().getName();
				String isolineGroupName = cescore.getIsolineGroupLayer()
						.getName();
				int gls = rankGroup.getCount();
				ILayer layer = null;
				for (int i = 0; i < gls; i++) {
					layer = rankGroup.getLayer(i);
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
				int layerNumInMapc = 0;
				layerNumInMapc = mapControl.getLayerCount();
				for (int i = 0; i < layerNumInMapc; i++) {
					layer = mapControl.getLayer(i);
					String layerName = layer.getName();
					if (layerName.equals(cellGroupName)
							|| layerName.equals(rankGroupName)
							|| layerName.equals(rangeGroupName)
							|| layerName.equals(isolineGroupName)
							|| layerName.equals(bgGroupName)
							||layerName.equals(obstructGName)) {
						continue;
					} else {
						layers.add(layer);
						layerCombo.add(layer.getName());
					}
				}
				if (layers.toArray().length == 0) {
					System.out.println("no layer");
					return;
				} else {
					layerCombo.select(0);
				}
			} else if (map.getLayerCount() == 0) {
				System.out.println("no layer");
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
			setFields();
		} catch (Exception e) {
			info.append("获取图层时出现错误\n");
			e.printStackTrace();
		}
	}

	private void setFields() {
		fieldCombo.removeAll();
		if (layers.toArray().length > 0) {
			try {
				Object objLayer = layers.toArray()[layerCombo
						.getSelectionIndex()];
				featureLayer = new FeatureLayer((ILayer) objLayer);
				IFields fields = featureLayer.getFeatureClass().getFields();
				IField field;
				int hasDefault = 0;
				for (int i = 0; i < fields.getFieldCount(); i++) {
					field = fields.getField(i);
					/*
					 * esriFieldTypeSmallInteger:0 esriFieldTypeInteger:1
					 * esriFieldTypeSingle:2 esriFieldTypeDouble:3
					 */
					if (field.getType() < 4) {
						fieldCombo.add(field.getName());
					}
					if (field.getName().equals(FIELD_NAME)) {
						hasDefault = fieldCombo.getItemCount()-1;
					}
				}
				fieldCombo.select(hasDefault);
			} catch (IOException e) {
				info.append("获取列信息时出现错误\n");
				e.printStackTrace();
			}
		}
	}

	private void setTableControl() {
		final int ID_COL = 0;
		final int SYM_COL = 1;
		final int RANGE_COL = 2;
		int initColor=255;
		int dec=20;
		table.removeAll();
		int num = spinner.getSelection();
		if(num>10){
			dec=5;
		}else if(num>50){
			dec=1;
		}
		TableEditor[] editors = new TableEditor[num];
		final Label[] colors = new Label[num];
		colorLabel = colors;
		for (int i = 0; i < num; i++) {
			final TableItem item = new TableItem(table, SWT.NONE);
			item.setText(ID_COL, String.valueOf(i + 1));
			editors[i] = new TableEditor(table);
			colors[i] = new Label(table, SWT.BORDER);
			{
				initColor-=dec;
				Color color=new Color(table.getDisplay(),new RGB(initColor,initColor,initColor));
				colors[i].setBackground(color);
			}
			colors[i].computeSize(SWT.DEFAULT, table.getItemHeight());
			editors[i].grabHorizontal = true;
			editors[i].minimumHeight = colors[i].getSize().y;
			editors[i].minimumWidth = colors[i].getSize().x;
			editors[i].setEditor(colors[i], item, SYM_COL);
			final int itemIndex = i;
			colors[i].addMouseListener(new MouseAdapter() {
				public void mouseUp(MouseEvent e) {
					ColorDialog colorDialog = new ColorDialog(table.getShell());
					RGB rgb = colorDialog.open();
					if (rgb == null)
						return;
					Color fgColor = new Color(table.getDisplay(), rgb);
					colors[itemIndex].setBackground(fgColor);
				}
			});

		}
		final TableEditor editor = new TableEditor(table);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		table.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent event) {
				Control old = editor.getEditor();
				if (old != null)
					old.dispose();
				Point pt = new Point(event.x, event.y);
				final TableItem item = table.getItem(pt);
				if (item == null)
					return;
				int col = -1;
				for (int i = 0; i < table.getColumnCount(); i++) {
					Rectangle rec = item.getBounds(i);
					if (rec.contains(pt)) {
						col = i;
						break;
					}
				}
				if (col == RANGE_COL) {
					final Text text = new Text(table, SWT.NONE);
					text.setForeground(item.getForeground());
					text.setText(item.getText(col));
					text.selectAll();
					text.setFocus();
					editor.minimumWidth = text.getBounds().width;
					editor.setEditor(text, item, col);
					final int c = col;
					text.addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent e) {
							item.setText(c, text.getText());
						}
					});
				}
			}
		});
	}

	private void setTypeInformation() {
		if (featureLayer != null) {
			try {
				switch (featureLayer.getShapeType()) {
				case esriGeometryType.esriGeometryPoint: { // set up a marker
					info.append("类型:esriGeometryPoint\n");
					break;
				}
				case esriGeometryType.esriGeometryPolyline: { // set up a line
					info.append("类型:esriGeometryPolyline\n");
					break;
				}
				case esriGeometryType.esriGeometryPolygon: { // setup a fill
					info.append("类型:esriGeometryPolygon\n");
					break;
				}
				}
			} catch (Exception e) {
				info.append("获取图层信息时出现错误\n");
				e.printStackTrace();
			}
		}
	}
/**
 * instood with faster arith
 */
/*	private void setFieldInformation() {
		try {
			if (fieldCombo.getItemCount() > 0) {
				String fieldName = fieldCombo.getItem(fieldCombo
						.getSelectionIndex());
				info.append("列名:" + fieldName);
				if (featureLayer.getShapeType() == esriGeometryType.esriGeometryPolygon) {
					ITable fTable = featureLayer;
					int fieldId = fTable.findField(fieldName);
					if (fieldId == -1) {
						info.append("\n未找到此列名" + fieldName);
						return;
					}
					IQueryFilter qf = new QueryFilter();
					qf.addField(fieldName);
					ICursor cursor = fTable.ITable_search(qf, true);
					IRow row = cursor.nextRow();
					double fieldValue = Double.parseDouble(row
							.getValue(fieldId).toString());
					double min = fieldValue;
					double max = min;
					while (row != null) {
						fieldValue = Double.parseDouble(row.getValue(fieldId)
								.toString());
						if (min > fieldValue)
							min = fieldValue;
						if (max < fieldValue)
							max = fieldValue;
						row = cursor.nextRow();
					}
					maximum = max;
					minimum = min;
					info.append("  最小值:" + min + "  最大值:" + max + "\n");
				} else {
					info.append("\n");
				}
			}
		} catch (Exception e) {
			info.append("获取列信息时出现错误\n");
			e.printStackTrace();
		}
	}*/

	private void setFieldInfoStatistics(){
		if(fieldCombo.getItemCount()>0){
			String fieldName=fieldCombo.getItem(fieldCombo.getSelectionIndex());
			try {
				if (featureLayer.getShapeType()==esriGeometryType.esriGeometryPolygon) {
					ITable fTable = featureLayer;
					int fieldId = fTable.findField(fieldName);
					if (fieldId == -1) {
						info.append("\n未找到此列名" + fieldName);
						return;
					}
					DataStatistics ds = new DataStatistics();
					IQueryFilter qf = new QueryFilter();
					qf.addField(fieldName);
					ICursor cursor = fTable.ITable_search(qf, true);
					ds.setCursorByRef(cursor);
					ds.setField(fieldName);
					IStatisticsResults result=ds.getStatistics();
					if(result==null){
						info.append("获取统计信息失败\n");
					}
					maximum=result.getMaximum();
					minimum=result.getMinimum();
					info.append("列名:"+fieldName+"  最小值:"+minimum+" ,最大值:"+maximum+"\n");
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	private void setListeners() {
		layerCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (layers.toArray().length > 0) {
					setFields();
					setTypeInformation();
				}
			}
		});
		fieldCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (layers.toArray().length > 0) {
//					setFieldInformation();
					setFieldInfoStatistics();
				}
			}
		});
		spinner.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (layers.toArray().length > 0) {
					table.removeAll();
					Control[] controls = table.getChildren();
					if (controls.length > 0) {
						for (int i = 0; i < controls.length; i++) {
							controls[i].dispose();
						}
					}
					setTableControl();
				}
			}
		});
	}

	private void setPointRender() {
		try {
			Color bgc = colorLabel[0].getBackground();
			IRgbColor rgb = new RgbColor();
			rgb.setRed(bgc.getRed());
			rgb.setGreen(bgc.getGreen());
			rgb.setBlue(bgc.getBlue());
			ISimpleMarkerSymbol pMarkerSym = new SimpleMarkerSymbol();
			pMarkerSym.setSize(6);
			pMarkerSym.setColor(rgb);
			pMarkerSym.setStyle(esriSimpleMarkerStyle.esriSMSCircle);
			ISymbol pSym = (ISymbol) pMarkerSym;
			IFeatureRenderer fr = new MySimpleRenderer();
			ISimpleRenderer sr = (ISimpleRenderer) fr;
			sr.setSymbolByRef(pSym);
			IGeoFeatureLayer pGeoFL = featureLayer;
			pGeoFL.setRendererByRef(fr);
			mapControl
					.refresh(esriViewDrawPhase.esriViewBackground, null, null);
		} catch (Exception e) {
			info.append("着色Point时出现错误\n");
			e.printStackTrace();
		}
	}

	private void setPolylineRender() {
		try {
			Color bgc = colorLabel[0].getBackground();
			IRgbColor rgb = new RgbColor();
			rgb.setRed(bgc.getRed());
			rgb.setGreen(bgc.getGreen());
			rgb.setBlue(bgc.getBlue());
			ISimpleLineSymbol pLineSymbol = new SimpleLineSymbol();
			pLineSymbol.setWidth(1.0);
			pLineSymbol.setColor(rgb);
			pLineSymbol.setStyle(esriSimpleLineStyle.esriSLSSolid);
			ISymbol pSym = (ISymbol) pLineSymbol;
			IFeatureRenderer fr = new MySimpleRenderer();
			ISimpleRenderer sr = (ISimpleRenderer) fr;
			sr.setSymbolByRef(pSym);
			IGeoFeatureLayer pGeoFL = featureLayer;
			pGeoFL.setRendererByRef(fr);
			mapControl
					.refresh(esriViewDrawPhase.esriViewBackground, null, null);
		} catch (Exception e) {
			info.append("着色Polyline时出现错误\n");
			e.printStackTrace();
		}
	}

	private void setPolygonRender() {
		try {
			System.out.println(featureLayer.getShapeType() + "to:"
					+ esriGeometryType.esriGeometryPolygon);
			if (featureLayer.getShapeType() == esriGeometryType.esriGeometryPolygon) {
				ClassBreaksRenderer cbr = new ClassBreaksRenderer();
				cbr
						.setField(fieldCombo.getItem(fieldCombo
								.getSelectionIndex()));
				cbr.setBreakCount(spinner.getSelection());
				for (int i = 0; i < spinner.getSelection(); i++) {
					String strBreak = table.getItem(i).getText(2);
					if(strBreak.equals(""))break;
					Color bgc = colorLabel[i].getBackground();
					RgbColor rgb = new RgbColor();
					rgb.setRed(bgc.getRed());
					rgb.setGreen(bgc.getGreen());
					rgb.setBlue(bgc.getBlue());
					SimpleFillSymbol sfs = new SimpleFillSymbol();
					sfs.setColor(rgb);
					sfs.setStyle(esriSimpleFillStyle.esriSFSSolid);
					cbr.setSymbol(i, new ISymbolProxy(sfs));
					cbr.setBreak(i, Double.parseDouble(strBreak));
				}
				featureLayer.setRendererByRef((IFeatureRenderer) cbr);
				mapControl.refresh(esriViewDrawPhase.esriViewGeography, null,
						null);
			}
		} catch (Exception e) {
			info.append("着色Polygon时出现错误\n");
			e.printStackTrace();
		}
	}

	private void setRender() {
		if (featureLayer == null) {
			info.append("没有图层\n");
			return;
		}
		if(fieldCombo.getItemCount()<1){
			info.append("没有有效的列\n");
			return;
		}
		try {
			switch (featureLayer.getShapeType()) {
			case esriGeometryType.esriGeometryPoint: {
				setPointRender();
				break;
			}
			case esriGeometryType.esriGeometryPolyline: {
				setPolylineRender();
				break;
			}
			case esriGeometryType.esriGeometryPolygon: {
				setPolygonRender();
				break;
			}
			}
		} catch (Exception e) {
			info.append("获取类型时出现错误\n");
			e.printStackTrace();
		}
	}
}

class MySimpleRenderer implements IFeatureRenderer, ILegendInfo,
		ISimpleRenderer {

	private ILegendGroup pLegendGroup;

	/**
	 * Default constructor creates lagend group and sets its properties.
	 */
	public MySimpleRenderer() {
		try {
			// renderer will have one legend group, with one legend class within
			// this group.
			// draw symbol will be stored and accessed from this legendclass
			// setup pLegendGroup
			pLegendGroup = new LegendGroup();
			ILegendClass pLegendClass = new LegendClass();
			// add a legend class to pLegendGroup
			pLegendGroup.addClass(pLegendClass);
			pLegendGroup.setVisible(true);
			pLegendGroup.setEditable(true);
		} catch (AutomationException e) {
			System.out.println("AutomationException " + e);
		} catch (IOException e) {
			System.out.println("IOException " + e);
		}
	}

	// IFeatureRenderer interface

	/**
	 * @see IFeatureRenderer#canRender
	 */
	public boolean canRender(IFeatureClass iFeatureClass, IDisplay iDisplay)
			throws IOException, AutomationException {
		if (iFeatureClass.getShapeType() == esriGeometryType.esriGeometryNull)
			return true;
		else
			return false;
	}

	/**
	 * @see IFeatureRenderer#prepareFilter
	 */
	public void prepareFilter(IFeatureClass iFeatureClass,
			IQueryFilter iQueryFilter) throws IOException, AutomationException {
		// nothing implemented
	}

	/**
	 * @see IFeatureRenderer#draw
	 */
	public void draw(IFeatureCursor iFeatureCursor, int drawPhase,
			IDisplay iDisplay, ITrackCancel iTrackCancel) throws IOException,
			AutomationException {
		// do not draw features if no display or wrong drawphase
		if (iDisplay == null || drawPhase != esriDrawPhase.esriDPGeography)
			return;
		// the draw symbol comes from pLegendGroup
		ISymbol pSym = pLegendGroup.esri_getClass(0).getSymbol();
		// do not draw features if symbol hasn't been set
		if (pSym == null)
			return;

		// loop through the features and draw them using the symbol
		IFeature pf = iFeatureCursor.nextFeature();
		while (pf != null) {
			IFeatureDraw pFD = new IFeatureDrawProxy(pf);
			pFD.draw(drawPhase, iDisplay, pSym, false, null,
					esriDrawStyle.esriDSNormal);
			pf = iFeatureCursor.nextFeature();
		}
	}

	/**
	 * @see IFeatureRenderer#getSymbolByFeature
	 */
	public ISymbol getSymbolByFeature(IFeature iFeature) throws IOException,
			AutomationException {
		ISymbol pSym = pLegendGroup.esri_getClass(0).getSymbol();
		return pSym;
	}

	/**
	 * @see IFeatureRenderer#isRenderPhase
	 */
	public boolean isRenderPhase(int drawPhase) throws IOException,
			AutomationException {
		if (drawPhase == esriDrawPhase.esriDPGeography)
			return true;
		else
			return false;
	}

	/**
	 * @see IFeatureRenderer#setExclusionSetByRef
	 */
	public void setExclusionSetByRef(IFeatureIDSet iFeatureIDSet)
			throws IOException, AutomationException {
		// exclusion not implemented for this renderer
	}

	// ILegendInfo interface

	/**
	 * @see ILegendInfo#getLegendGroupCount
	 */
	public int getLegendGroupCount() throws IOException, AutomationException {
		// returns 1 if pLegendGroup exists and symbol has been set
		if (pLegendGroup != null
				&& pLegendGroup.esri_getClass(0).getSymbol() != null)
			return 1;
		else
			return 0;
	}

	/**
	 * @see ILegendInfo#getLegendGroup
	 */
	public ILegendGroup getLegendGroup(int i) throws IOException,
			AutomationException {
		// returns legend group if a symbol has been set
		if (pLegendGroup.esri_getClass(0).getSymbol() != null)
			return pLegendGroup;
		else
			return null;
	}

	/**
	 * @see ILegendInfo#getLegendItem
	 */
	public ILegendItem getLegendItem() throws IOException, AutomationException {
		return null;
	}

	/**
	 * @see ILegendInfo#isSymbolsAreGraduated
	 */
	public boolean isSymbolsAreGraduated() throws IOException,
			AutomationException {
		return false;
	}

	/**
	 * @see ILegendInfo#setSymbolsAreGraduated
	 */
	public void setSymbolsAreGraduated(boolean b) throws IOException,
			AutomationException {
		// nothing implemented
	}

	// ISimpleRenderer interface

	/**
	 * @see ISimpleRenderer#getSymbol
	 */
	public ISymbol getSymbol() throws IOException, AutomationException {
		return pLegendGroup.esri_getClass(0).getSymbol();
	}

	/**
	 * @see ISimpleRenderer#setSymbolByRef
	 */
	public void setSymbolByRef(ISymbol iSymbol) throws IOException,
			AutomationException {
		pLegendGroup.esri_getClass(0).setSymbolByRef(iSymbol);
	}

	/**
	 * @see ISimpleRenderer#getLabel
	 */
	public String getLabel() throws IOException, AutomationException {
		return pLegendGroup.esri_getClass(0).getLabel();
	}

	/**
	 * @see ISimpleRenderer#setLabel
	 */
	public void setLabel(String s) throws IOException, AutomationException {
		pLegendGroup.esri_getClass(0).setLabel(s);

	}

	/**
	 * @see ISimpleRenderer#getDescription
	 */
	public String getDescription() throws IOException, AutomationException {
		return pLegendGroup.esri_getClass(0).getDescription();
	}

	/**
	 * @see ISimpleRenderer#setDescription
	 */
	public void setDescription(String s) throws IOException,
			AutomationException {
		pLegendGroup.esri_getClass(0).setDescription(s);
	}

}
