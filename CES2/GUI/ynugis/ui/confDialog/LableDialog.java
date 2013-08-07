/*
 * @author 冯涛，创建日期：2003-3-9
 * blog--http://apower.blogone.net
 * QQ:17463776
 * MSN:apower511@msn.com
 * E-Mail:VonPower@Gmail.com
 */
package ynugis.ui.confDialog;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.esri.arcgis.beans.map.MapBean;
import com.esri.arcgis.carto.BasicOverposterLayerProperties;
import com.esri.arcgis.carto.FeatureLayer;
import com.esri.arcgis.carto.IAnnotateLayerPropertiesCollection;
import com.esri.arcgis.carto.IAnnotateLayerPropertiesProxy;
import com.esri.arcgis.carto.IBasicOverposterLayerProperties;
import com.esri.arcgis.carto.IEnumLayer;
import com.esri.arcgis.carto.IFeatureLayer;
import com.esri.arcgis.carto.IFeatureLayerProxy;
import com.esri.arcgis.carto.IGeoFeatureLayer;
import com.esri.arcgis.carto.ILabelEngineLayerProperties;
import com.esri.arcgis.carto.ILayer;
import com.esri.arcgis.carto.ILineLabelPosition;
import com.esri.arcgis.carto.IMap;
import com.esri.arcgis.carto.LabelEngineLayerProperties;
import com.esri.arcgis.carto.LineLabelPlacementPriorities;
import com.esri.arcgis.carto.LineLabelPosition;
import com.esri.arcgis.controls.MapControl;
import com.esri.arcgis.geodatabase.IField;
import com.esri.arcgis.geodatabase.IFields;
import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.system.UID;

public class LableDialog extends Dialog {

	private List fieldList;

	private Text text;

	private Combo featureLayersCombo;

	protected Object result;

	protected Shell shell;

	private IMap map;

	private MapBean mapControl;

	private ILayer selectedLayer;

	private ArrayList layers = new ArrayList();

	private IEnumLayer pEnumLayer;

	// private boolean ctrlPressed=false;
	/**
	 * Create the dialog
	 * 
	 * @param parent
	 * @param style
	 */
	public LableDialog(Shell parent, int style) {
		super(parent, style);

	}

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 */
	public LableDialog(Shell parent, MapBean inMapControl) {
		this(parent, SWT.NONE);
		mapControl = inMapControl;
		try {
			map = mapControl.getMap();
		} catch (AutomationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Open the dialog
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return result;
	}

	/**
	 * Create contents of the dialog
	 */
	protected void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		shell.setLayout(gridLayout);
		shell.setSize(235, 199);
		shell.setText("标注对话框");

		final Label label = new Label(shell, SWT.NONE);
		label.setText("请选择要标注的图层：");

		featureLayersCombo = new Combo(shell, SWT.NONE);
		featureLayersCombo.setLayoutData(new GridData());

		featureLayersCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int idx = featureLayersCombo.getSelectionIndex();
				selectedLayer = (ILayer) layers.get(idx);

				IFeatureLayer featureLayer;
				try {
					featureLayer = new IFeatureLayerProxy(selectedLayer);
					IFields fields = featureLayer.getFeatureClass().getFields();
					IField field;
					fieldList.removeAll();
					for (int i = 0; i < fields.getFieldCount(); i++) {
						// 对Shape字段不进行处理
						if (i == 1)
							continue;

						field = fields.getField(i);
						// fieldList.add(field);
						fieldList.add(field.getName());

					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		try {
			assemblyFeatureCombo();
		} catch (AutomationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		fieldList = new List(shell, SWT.V_SCROLL | SWT.BORDER | SWT.H_SCROLL);

		fieldList.setLayoutData(new GridData(GridData.FILL, GridData.CENTER,
				false, false));
		fieldList.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {

				String str[] = fieldList.getSelection();

				/*
				 * String temp=""; for(int i=0;i<str.length;i++) { temp+="
				 * "+str[i]; }
				 */
				if (e.stateMask == SWT.CTRL && !text.getText().equals("")) {
					text.setText(text.getText() + " & \" \" &" + "[" + str[0]
							+ "]");
				} else if (e.stateMask == SWT.SHIFT
						&& !text.getText().equals("")) {
					text.setText(text.getText() + " & vbCrLf &" + "[" + str[0]
							+ "]");

				} else {
					text.setText("[" + str[0] + "]");
				}
			}
		});

		final Label label_1 = new Label(shell, SWT.WRAP);
		label_1.setLayoutData(new GridData(83, SWT.DEFAULT));
		label_1.setText("双击左边列表中想要标注的域(可以组合Ctrl ,Shift使用)");

		text = new Text(shell, SWT.BORDER);
		text.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false,
				false));

		final Label label_2 = new Label(shell, SWT.NONE);
		label_2.setText("<-标注域的名称");

		final Button lableBtn = new Button(shell, SWT.NONE);
		lableBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				if (selectedLayer == null)
					return;
				try {
					IFeatureLayer featureLayer = new IFeatureLayerProxy(
							selectedLayer);
					// GRIDCODE 是转换过程中自动生成的filed的NAME，该field就代表Raster的Cell Value
					// featureLayer.setDisplayField(text.getText());

					IGeoFeatureLayer pGeoFL = new FeatureLayer(featureLayer);
					IAnnotateLayerPropertiesCollection pAnnoLayerPropsColl = pGeoFL
							.getAnnotationProperties();
					pAnnoLayerPropsColl.clear();

					// 进行Label的相关设置：
					ILabelEngineLayerProperties pAnnoLayerProps;
					ILineLabelPosition pPosition = new LineLabelPosition();
					pPosition.setParallel(false);
					pPosition.setPerpendicular(true);
					LineLabelPlacementPriorities pPlacement = new LineLabelPlacementPriorities();

					IBasicOverposterLayerProperties pBas = new BasicOverposterLayerProperties();
					pBas
							.setFeatureType(com.esri.arcgis.geometry.esriGeometryType.esriGeometryPolygon);
					pBas.setLineLabelPlacementPriorities(pPlacement);
					pBas.setLineLabelPosition(pPosition);
					ILabelEngineLayerProperties pLabelEngine = new LabelEngineLayerProperties();
					pLabelEngine.setBasicOverposterLayerPropertiesByRef(pBas);
					//处理[GRIDCODE]:
					String VBACode=text.getText();
					if(VBACode.indexOf("[GRIDCODE]")!=-1)
					{	
						
						VBACode=VBACode.replaceAll("\\[GRIDCODE\\]","Round([GRIDCODE]/10000,2)");
						
					}
					
					pLabelEngine.setExpression(VBACode);
					pAnnoLayerProps = pLabelEngine;
					// 将设置的结果加入到label的标注集合中
					pAnnoLayerPropsColl.add(new IAnnotateLayerPropertiesProxy(
							pAnnoLayerProps));
					// 显示标志集合中的所有标注方案
					pGeoFL.setDisplayAnnotation(true);

					mapControl.getActiveView().refresh();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		lableBtn.setLayoutData(new GridData(GridData.FILL, GridData.CENTER,
				false, false));
		lableBtn.setText("标注");

		final Button CancelLableBtn = new Button(shell, SWT.NONE);
		CancelLableBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				if (selectedLayer == null)
					return;
				try {
					IFeatureLayer featureLayer = new IFeatureLayerProxy(
							selectedLayer);
					// GRIDCODE 是转换过程中自动生成的filed的NAME，该field就代表Raster的Cell Value
					// featureLayer.setDisplayField(text.getText());

					IGeoFeatureLayer pGeoFL = new FeatureLayer(featureLayer);
					IAnnotateLayerPropertiesCollection pAnnoLayerPropsColl = pGeoFL
							.getAnnotationProperties();
					pAnnoLayerPropsColl.clear();

					// 显示标志集合中的所有标注方案

					mapControl.getActiveView().refresh();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		CancelLableBtn.setLayoutData(new GridData(GridData.FILL,
				GridData.CENTER, false, false));
		CancelLableBtn.setText("取消标注");

		//
	}

	/**
	 * get the map's and add them to combo renturn null
	 * 
	 * @throws IOException
	 * @throws AutomationException
	 */
	private void assemblyFeatureCombo() throws AutomationException, IOException {

		UID pid = new UID();
		pid.setValue("{E156D7E5-22AF-11D3-9F99-00C04F6BC78E}");
		pEnumLayer = map.getLayers(pid, true);
		pEnumLayer.reset();

		ILayer tempLayer = pEnumLayer.next();

		while (tempLayer != null) {

			// 把获取的layer保存到layers中
			layers.add(tempLayer);

			String strLayerName = tempLayer.getName();
			featureLayersCombo.add(strLayerName);

			tempLayer = pEnumLayer.next();
		}
		featureLayersCombo.select(0);
		return;

	}

}
