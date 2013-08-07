/*
 * @author ���Σ��������ڣ�2003-11-29
 * blog--http://apower.blogone.net
 * QQ:17463776
 * MSN:apower511@msn.com
 * E-Mail:VonPower@Gmail.com
 */
package ynugis.ui.confDialog;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import ynugis.application.CESCORE;

import com.esri.arcgis.carto.IActiveView;
import com.esri.arcgis.carto.IEnumLayer;
import com.esri.arcgis.carto.IFeatureLayer;
import com.esri.arcgis.carto.IFeatureLayerProxy;
import com.esri.arcgis.carto.ILayer;
import com.esri.arcgis.geodatabase.IGeoDatasetProxy;
import com.esri.arcgis.geometry.Envelope;
import com.esri.arcgis.geometry.IEnvelope;
import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.output.ExportJPEG;
import com.esri.arcgis.output.IExport;
import com.esri.arcgis.system.UID;

public class ExportDialog extends TitleAreaDialog {

	private Text scaleText;

	private Combo layerCombo;

	private Text filePathText;

	private CESCORE core;

	private ArrayList layers = new ArrayList();

	private IEnumLayer pEnumLayer;

	/**
	 * Create the dialog
	 * 
	 * @param parentShell
	 */
	public ExportDialog(CESCORE core, Shell parentShell) {
		super(parentShell);
		this.core = core;

	}

	/**
	 * Create contents of the dialog
	 * 
	 * @param parent
	 */
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		filePathText = new Text(container, SWT.BORDER);
		filePathText.setBounds(177, 32, 245, 28);

		final Label label = new Label(container, SWT.NONE);
		label.setText("�����ļ�·��");
		label.setBounds(9, 41, 109, 28);

		final Button filePathBt = new Button(container, SWT.NONE);
		filePathBt.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(getShell(), SWT.OPEN | SWT.MULTI);
				fd.setFilterPath("c:");
				String[] nameStr = null;
				String[] extensionStr = null;

				nameStr = new String[] { "JPEGͼ���ļ���*.JPEG��" };
				extensionStr = new String[] { "*.jpg" };
				
				fd.setFilterNames(nameStr);
				fd.setFilterExtensions(extensionStr);
				fd.open();
				
				filePathText.setText(fd.getFilterPath()+File.separator+fd.getFileName());
				
			}
		});
		filePathBt.setText("...");
		filePathBt.setBounds(440, 33, 44, 30);

		layerCombo = new Combo(container, SWT.NONE);
		layerCombo.setBounds(176, 1, 109, 21);
		getLayer();
		final Label label_1 = new Label(container, SWT.NONE);
		label_1.setText("�����Χ������");
		label_1.setBounds(14, 1, 109, 28);

		final Label label_2 = new Label(container, SWT.NONE);
		label_2.setText("�Ŵ���");
		label_2.setBounds(9, 89, 109, 28);

		scaleText = new Text(container, SWT.BORDER);
		scaleText.setBounds(176, 81, 109, 28);
		setMessage("����ǰ��ʾ�ĵ�ͼ�����JPEG�ļ�");
		setTitle("CES��ͼ");
		//
		return area;
	}

	/**
	 * Create contents of the button bar
	 * 
	 * @param parent
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog
	 */
	protected Point getInitialSize() {
		return new Point(491, 257);
	}

	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			try {
				IActiveView view = core.getMapControl().getActiveView();
				IExport ep = new ExportJPEG();
				ep.setExportFileName(filePathText.getText());
				// set Resolution to 96 ,(PDF should set to 300 dpi)
				ep.setResolution(96);
				// set pixelBoundsEnvelope

				IEnvelope pixelBoundsEnv = new Envelope();
				int intLyrIndex = layerCombo.getSelectionIndex();
				if (intLyrIndex == -1) {
					MessageDialog.openError(getShell(), "����", "���ͼ��δָ����");
					return;
				}

				ILayer lyr = (ILayer) layers.get(intLyrIndex);
				IFeatureLayer fl = new IFeatureLayerProxy(lyr);
				IGeoDatasetProxy geoDataset = new IGeoDatasetProxy(fl
						.getFeatureClass());
				pixelBoundsEnv = geoDataset.getExtent();

				com.esri.arcgis.system.tagRECT rect = new com.esri.arcgis.system.tagRECT();
				rect.left = 0;
				rect.top = 0;
				rect.right = (int) (100 * Double.parseDouble(scaleText
						.getText()));
				rect.bottom = (int) (100 * Double.parseDouble(scaleText
						.getText()));
				ep.setPixelBounds(pixelBoundsEnv);
				int hDC = ep.startExporting();
				view.output(hDC, 96, rect, null, null);

				ep.finishExporting();
				ep.cleanup();

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return;
			
		}
		super.buttonPressed(buttonId);
	}

	/**
	 * get the map's and add them to combo renturn null
	 */
	private void getLayer() {
		//int intlayerNum;
		try {

			UID pid = new UID();
			pid.setValue("{E156D7E5-22AF-11D3-9F99-00C04F6BC78E}");
			if(core.getMapControl().getMap().getLayerCount()<=0)return;
			pEnumLayer = core.getMapControl().getMap().getLayers(pid, true);
			pEnumLayer.reset();
			ILayer layer;
			layer = pEnumLayer.next();

			while (layer != null) {

				// �ѻ�ȡ��layer���浽layers��
				layers.add(layer);

				String strLayerName = layer.getName();
				layerCombo.add(strLayerName);

				layer = pEnumLayer.next();
			}
			// }
			layerCombo.select(0);
		} catch (AutomationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
