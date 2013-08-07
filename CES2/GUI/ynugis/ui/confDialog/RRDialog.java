/**
 * @author yddy,create date 2005-12-27
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.confDialog;


import java.io.IOException;
import java.net.UnknownHostException;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import ynugis.geo.Rester2TableRaster;

import com.esri.arcgis.geoanalyst.INumberRemap;
import com.esri.arcgis.geoanalyst.IRasterDescriptor;
import com.esri.arcgis.geoanalyst.IReclassOp;
import com.esri.arcgis.geoanalyst.IRemap;
import com.esri.arcgis.geoanalyst.IRemapProxy;
import com.esri.arcgis.geoanalyst.NumberRemap;
import com.esri.arcgis.geoanalyst.RasterDescriptor;
import com.esri.arcgis.geoanalyst.RasterReclassOp;
import com.esri.arcgis.geodatabase.IGeoDataset;
import com.esri.arcgis.geodatabase.IGeoDatasetProxy;
import com.esri.arcgis.geodatabase.IRaster;
import com.esri.arcgis.geodatabase.IRasterProxy;

public class RRDialog extends Dialog {
	private String	input;

	private IRaster	rr;

	private double	cellNum;

	private Table	table;

	private List	lst2;

	private Text	txt1;

	private IRaster	pRas;
	
	String[] rasterArray;

	/**
	 * InputDialog constructor
	 * 
	 * @param parent
	 *            the parent
	 */
	public RRDialog(Shell parent, IRaster ras) {
		super(parent);
		rr = ras;
	}

	/**
	 * Opens the dialog and returns the input
	 * 
	 * @return String
	 */
	public IRaster getPRaster() {
		return pRas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		startRender();
		super.okPressed();
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
		GridLayout conLayout = new GridLayout();
		conLayout.horizontalSpacing = 8;
		conLayout.verticalSpacing = 8;
		conLayout.numColumns = 3;
		conLayout.marginTop = 30;
		conLayout.marginLeft = 30;
		conLayout.marginRight = 30;
		Composite content = new Composite(parent, SWT.NONE);
		content.setLayout(conLayout);
		GridData gdLabel = new GridData(GridData.FILL_HORIZONTAL);
		gdLabel.horizontalSpan = 3;
		Label label = new Label(content, SWT.NONE);
		label.setLayoutData(gdLabel);
		label.setText("栅格统计结果:");
		GridData gbTable = new GridData(GridData.FILL_BOTH);
		gbTable.horizontalSpan = 3;
		gbTable.minimumHeight = 200;
		gbTable.minimumWidth = 200;
		table = new Table(content, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(gbTable);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		TableColumn col1 = new TableColumn(table, SWT.NONE);
		col1.setText("作用分");
		col1.setWidth(70);
		TableColumn col2 = new TableColumn(table, SWT.NONE);
		col2.setText("单元个数");
		col2.setWidth(60);
		TableColumn col3 = new TableColumn(table, SWT.NONE);
		col3.setText("频率(%)");
		col3.setWidth(60);

		Label label2 = new Label(content, SWT.NONE);
		label2.setText("设定得着色方案:");

		new Label(content, SWT.NONE);

		Label label3 = new Label(content, SWT.NONE);
		label3.setText("输入要着色的范围:");
		GridData gdText = new GridData(GridData.FILL_HORIZONTAL);
		gdText.verticalSpan = 2;
		gdText.verticalAlignment = GridData.VERTICAL_ALIGN_CENTER;
		txt1 = new Text(content, SWT.BORDER);
		txt1.setLayoutData(gdText);
		txt1.setBounds(30, 270, 100, 20);
		txt1.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				boolean b = ("0123456789-".indexOf(e.text) >= 0);
				e.doit = b;
			}
		});

		Button btn1 = new Button(content, SWT.NONE);
		btn1.setText(">>");

		GridData gdList = new GridData(GridData.FILL_BOTH);
		gdList.verticalSpan = 2;
		gdList.minimumHeight = 50;
		lst2 = new List(content, SWT.BORDER);
		lst2.setLayoutData(gdList);

		Button btn2 = new Button(content, SWT.NONE);
		btn2.setText("<<");

		getStaticVal();

		btn1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				input = txt1.getText();
				int TextPosition1 = input.indexOf("-");
				double douLlatestInput = Double.parseDouble(input.substring(0,
						TextPosition1));
				int lstcount = lst2.getItemCount();
				if (lstcount > 0) {
					String strLstText = lst2.getItem(lst2.getItemCount() - 1);
					int TextPosition = strLstText.indexOf("-");
					double douLastInput = Double.parseDouble(strLstText
							.substring(TextPosition + 1, strLstText.length()));
					if (douLlatestInput > douLastInput) {
						MessageDialog.openInformation(null, "输入错误",
								"输入定级范围有重复，请重新输入");
						txt1.setText("");
						return;
					}
				}
				lst2.add(input);
				txt1.setText("");
			}
		});
		btn2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				int intlst2index = lst2.getFocusIndex();
				lst2.remove(intlst2index);
			}
		});
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

	public String[] getRasterArray()
	{
		if(rasterArray==null)
		{
			Rester2TableRaster tableRaster = new Rester2TableRaster(rr);
			rasterArray = tableRaster.getTableRaster();
		}
		return rasterArray;
	}
	private void getStaticVal() {
		
		String ra[];
		ra = getRasterArray();
		
		for (int i = 0; i < 11; i++) {
			String uu = "-";
			int strPosition = ra[i].indexOf(uu);
			cellNum = cellNum
					+ Double.parseDouble(ra[i].substring(0,
							strPosition - 1));
			TableItem item = new TableItem(table, 0);
			if (i == 0) {
				item.setText(0, String.valueOf(i) + "--"
						+ String.valueOf(i + 1) + "0");
			} else if (i < 10) {
				item.setText(0, String.valueOf(i) + "0--"
						+ String.valueOf(i + 1) + "0");
			} else {
				item.setText(0, "100" + "-" + ra[11]);
			}
			item.setText(1, ra[i].substring(0, strPosition - 1));
			item.setText(2, ra[i].substring(strPosition + 1,
					ra[i].length()));

		}
		TableItem item = new TableItem(table, 0);
		item.setText(0, "合计");
		item.setText(1, String.valueOf(cellNum));
		item.setText(2, "100");
	}

	private void startRender() {
		int numSize = lst2.getItemCount();
		String sFieldNum = "Value";
		try {
			IRasterDescriptor pRasDescriptor = new RasterDescriptor();
			pRasDescriptor.create(rr, null, sFieldNum);
			IReclassOp pReclassOp = new RasterReclassOp();
			INumberRemap pNumberRemap = new NumberRemap();
			for (int i = 0; i < numSize; i++) {
				String strlst2 = lst2.getItem(i);
				System.out.println(strlst2);
				int num = strlst2.indexOf("-");
				double getMinValue = Double.parseDouble(strlst2.substring(0,
						num));
				double getMaxValue = Double.parseDouble(strlst2.substring(
						num + 1, strlst2.length()));
				pNumberRemap.mapRange((int) getMinValue, (int) getMaxValue,
						(int) (getMinValue + getMaxValue) / 2);
			}
			IGeoDataset pGeodataset = new IGeoDatasetProxy(pRasDescriptor);
			IRemap pReMap = new IRemapProxy(pNumberRemap);
			IGeoDataset pOutdataset = pReclassOp.reclassByRemap(pGeodataset,
					pReMap, false);
			pRas = new IRasterProxy(pOutdataset);
			startRender2(pRas);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void startRender2(IRaster rast) {
		int numSize = lst2.getItemCount();
		String sFieldNum = "Value";
		try {
			IRasterDescriptor pRasDescriptor = new RasterDescriptor();
			pRasDescriptor.create(rr, null, sFieldNum);
			IReclassOp pReclassOp = new RasterReclassOp();
			INumberRemap pNumberRemap = new NumberRemap();
			for (int i = 0; i < numSize; i++) {
				String strlst2 = lst2.getItem(i);
				System.out.println(strlst2);
				int num = strlst2.indexOf("-");
				double getMinValue = Double.parseDouble(strlst2.substring(0,
						num));
				double getMaxValue = Double.parseDouble(strlst2.substring(
						num + 1, strlst2.length()));
				pNumberRemap.mapRange((int) getMinValue, (int) getMaxValue,
						(int) (getMinValue + getMaxValue) / 2);
			}
			IGeoDataset pGeodataset = new IGeoDatasetProxy(pRasDescriptor);
			IRemap pReMap = new IRemapProxy(pNumberRemap);
			IGeoDataset pOutdataset = pReclassOp.reclassByRemap(pGeodataset,
					pReMap, false);
			pRas = new IRasterProxy(pOutdataset);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
