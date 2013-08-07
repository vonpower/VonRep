package ynugis.ui.confDialog;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.esri.arcgis.carto.IEnumLayer;
import com.esri.arcgis.carto.ILayer;
import com.esri.arcgis.carto.ILayerEffects;
import com.esri.arcgis.carto.ILayerEffectsProxy;
import com.esri.arcgis.carto.IMap;
import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.system.UID;

public class transparentSetDialog extends Dialog {
	private Text text;

	private Combo combo;

	Double value;

	private IMap map;

	private ILayer layer;

	private ArrayList layers=new ArrayList();

	private IEnumLayer pEnumLayer;

	/**
	 * @param parent
	 */
	public transparentSetDialog(Shell parent, IMap mp) {
		super(parent);
		map = mp;
	}

	/**
	 * @param parent
	 * @param style
	 */
	public transparentSetDialog(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * get the map's and add them to combo renturn null
	 */
	private boolean getLayer() {
		int intlayerNum;
		try {
			intlayerNum=map.getLayerCount();
			if(intlayerNum==0){
				MessageDialog.openInformation(null,"地图出错","没有图层，请加入地图再操作，谢谢！！！");
				return false;
			}else{
				try {

					UID pid = new UID();
					pid.setValue("{6CA416B1-E160-11D2-9F4E-00C04F6BC78E}");
					pEnumLayer = map.getLayers(pid, true);
					pEnumLayer.reset();

					layer = pEnumLayer.next();
					
					while (layer != null) {

						//把获取的layer保存到layers中
						layers.add(layer);

						String strLayerName = layer.getName();
						combo.add(strLayerName);

						layer = pEnumLayer.next();
//						return true;
					}
					// }
					combo.select(0);
					return true;
				} catch (AutomationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (AutomationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		try {
//
//			UID pid = new UID();
//			pid.setValue("{6CA416B1-E160-11D2-9F4E-00C04F6BC78E}");
//			pEnumLayer = map.getLayers(pid, true);
//			pEnumLayer.reset();
//
//			layer = pEnumLayer.next();
//			
//			while (layer != null) {
//
//				//把获取的layer保存到layers中
//				layers.add(layer);
//
//				String strLayerName = layer.getName();
//				combo.add(strLayerName);
//
//				layer = pEnumLayer.next();
//			}
//			// }
//			combo.select(0);
//		} catch (AutomationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return false;
	}

	/**
	 * Makes the dialog visible.
	 * 
	 * @return
	 */
	public Double open() {
		Shell parent = getParent();
		final Shell shell = new Shell(parent, SWT.TITLE | SWT.BORDER
				| SWT.APPLICATION_MODAL);
		shell.setText("图层透明度管理");
		shell.setSize(227, 188);

		shell.addListener(SWT.Traverse, new Listener() {
			public void handleEvent(Event event) {
				if (event.detail == SWT.TRAVERSE_ESCAPE)
					event.doit = false;
			}
		});

		final Button button = new Button(shell, SWT.NONE);
		button.setText("确定");
		button.setBounds(34, 111, 55, 25);
		button.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				String strText=text.getText();
				if(strText==""){
					MessageDialog.openInformation(null,"输入提示","输入为空，请重新输入！");
					return;
				}
				int inttext = Integer.parseInt(text.getText());
				
				// String lyrName=combo.getItem()
//				System.out.println("获得的图层透明度:" + inttext);
				int intLyrIndex = combo.getSelectionIndex();
//				System.out.println("获得的图层数:" + intLyrIndex);
				try {
					ILayer lyr=(ILayer)layers.get(intLyrIndex);

							ILayerEffects pLayerEff = new ILayerEffectsProxy(
									lyr);
							if (pLayerEff.isSupportsTransparency()) {
								pLayerEff.setTransparency((short) inttext);
							}

				} catch (AutomationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				shell.dispose();
			}
		});

		final Button 取消 = new Button(shell, SWT.NONE);
		取消.setText("取消");
		取消.setBounds(125, 111, 55, 25);
		取消.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				shell.dispose();
			}
		});

		final Label label = new Label(shell, SWT.NONE);
		label.setText("请选择层:");
		label.setBounds(15, 17, 61, 13);

		combo = new Combo(shell, SWT.NONE);
		combo.setBounds(20, 32, 171, 21);

		final Label label_1 = new Label(shell, SWT.NONE);
		label_1.setText("请输入图层的透明度(%):");
		label_1.setBounds(15, 71, 134, 20);

		text = new Text(shell, SWT.BORDER);
		text.setBounds(148, 68, 45, 20);
		text.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				boolean b = ("0123456789".indexOf(e.text) >= 0);
				e.doit = b;
			}
		});
		boolean getLayersOkorNot;
		getLayersOkorNot=getLayer();
		if(!getLayersOkorNot){
			return value;
		}
		shell.open();

		Display display = parent.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		return value;
	}

}
