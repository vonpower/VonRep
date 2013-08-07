package ynugis.ui.confDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;

import ynugis.ffsystem.FFItem;
import ynugis.ui.initial.GUI;

public class FFTBicoMatrixDialog extends Dialog {
	private final int CALCBTN_ID = 1000;

	private TreeItem treeItem;

	private HashMap orderMap;

	private Text[] matrixText;

	private Label[] weightLabel;

	public FFTBicoMatrixDialog(TreeItem item, HashMap map) {
		super(new Shell());
		this.treeItem = item;
		this.orderMap = map;
	}

	protected Button createButton(Composite parent, int id, String label,
			boolean defaultButton) {
		return null;
	}

	protected Control createDialogArea(Composite parent) {
		int rowCount=this.treeItem.getItemCount();
		int colCount = this.getCompareTimes(rowCount);
		int colLabelCount = colCount + 1;
		int cL, rL, c, r, i;
		this.matrixText = new Text[rowCount * colCount];
		this.weightLabel = new Label[rowCount];
		final Composite content = new Composite(parent, SWT.NONE);
		{

			GridLayout contentLay = new GridLayout();
			contentLay.makeColumnsEqualWidth = true;
			contentLay.marginTop = 50;
			contentLay.marginRight = 50;
			contentLay.marginBottom = 50;
			contentLay.marginLeft = 50;
			contentLay.numColumns = colLabelCount;
			contentLay.verticalSpacing = 3;
			contentLay.horizontalSpacing = 2;
			content.setLayout(contentLay);
		}
		GridData gridCenter = new GridData(GridData.FILL, GridData.FILL, true,
				true);
		gridCenter.horizontalAlignment = SWT.CENTER;
		gridCenter.minimumWidth = 8;
		for (int iL = 0; iL < (rowCount+1) * colLabelCount; iL++) {
			cL = iL % colLabelCount;
			rL = (iL - cL) / colLabelCount;
			c = cL - 1;
			r = rL - 1;
			if (c >= 0 && r >= 0) {
				i = r * colCount + c;
				this.matrixText[i] = new Text(content, SWT.NONE);
				this.matrixText[i].addVerifyListener(new VerifyListener(){
					public void verifyText(VerifyEvent e) {
						if("01".indexOf(e.text)>=0){
							e.doit=true;
							return;
						}else{
							try {
								Double.parseDouble(e.text);
								e.doit = true;
								return;
							} catch (Exception ex) {
								e.doit=false;
								return;
							}
						}
					}
				});
				this.matrixText[i].addFocusListener(new TextFocusListener(i,colCount));
				this.matrixText[i].setText("0.000");

			} else {
				if (cL + rL == 0)
					new Label(content, SWT.NONE);
				else if(rL>0){
					String itemName = this.getNameTreeItem(treeItem.getItem(rL
							+ cL - 1));
					Label label = new Label(content, SWT.NONE);
					label.setText(itemName);
					// label.setLayoutData(gridCenter);
				}else if(cL>0){
					new Label(content,SWT.NONE).setText("第"+cL+"次");
				}
			}
		}
		new Label(content, SWT.NONE).setText("权重");
		for (int res = 0; res < rowCount; res++) {
			this.weightLabel[res] = new Label(content, SWT.NONE);
			FFItem ffitem = (FFItem) treeItem.getItem(res).getData();
			this.weightLabel[res].setText(String.valueOf(ffitem.weight));
		}
		this.setWeightMatrix();
		return content;
	}

	protected void initializeBounds() {
		super.createButton((Composite) getButtonBar(), CALCBTN_ID, "计算", false);
		super.createButton((Composite) getButtonBar(), IDialogConstants.OK_ID,
				"确定", true);
		super.createButton((Composite) getButtonBar(),
				IDialogConstants.CANCEL_ID, "取消", true);
		super.initializeBounds();
	}

	protected void buttonPressed(int buttonId) {
		if (buttonId == CALCBTN_ID) {
			this.calculateBiCo();
		} else if (buttonId == IDialogConstants.OK_ID) {
			this.setWeightTree();
			this.setWeightFFT();
			super.okPressed();
		} else if (buttonId == IDialogConstants.CANCEL_ID) {
			super.cancelPressed();
		}
	}

	/**
	 * pick-up the name from text of the treeitem
	 */
	private String getNameTreeItem(TreeItem item) {
		String itemName = item.getText();
		int prefixIdx = itemName.lastIndexOf(GUI.PREFIX);
		if (prefixIdx != -1) {
			itemName = itemName.substring(0, prefixIdx);
		}
		return itemName;
	}

	/**
	 * connet matrix with treeitem reset the texts of the treeitem's children
	 */
	private void setWeightTree() {
		List matrix = new ArrayList();
		for (int i = 0; i < matrixText.length; i++) {
			matrix.add(matrixText[i].getText());
		}
		if (orderMap.containsKey(treeItem))
			orderMap.remove(treeItem);
		orderMap.put(treeItem, matrix);
	}

	/**
	 * update matrixtext with values been setted
	 */
	private void setWeightMatrix() {
		if (orderMap.containsKey(treeItem)) {
			List matrix = (List) orderMap.get(treeItem);
			Object[] objText = matrix.toArray();
			for (int i = 0; i < objText.length; i++) {
				matrixText[i].setText(objText[i].toString());
			}
		}
	}

	/**
	 * reset weight value of ffitem
	 */
	private void setWeightFFT() {
		FFItem ffItem = null;
		String value = null;
		double weight;
		for (int i = 0; i < treeItem.getItemCount(); i++) {
			ffItem = (FFItem) treeItem.getItem(i).getData();
			value = weightLabel[i].getText();
			weight = Double.parseDouble(value);
			ffItem.weight = weight;
		}
	}

	/**
	 * calc weight in way of ahp reset weight labels
	 */
	private void calculateBiCo() {
		DecimalFormat format = new DecimalFormat("0.00");
		String formatted = "";
		int rowCount=treeItem.getItemCount();
		int colCount=this.getCompareTimes(rowCount);
		double[] eachTimes=new double[rowCount];
		for(int r=0;r<rowCount;r++){
			double times=0;
			for(int c=0;c<colCount;c++){
				String text=matrixText[r*colCount+c].getText();
				times+=Double.parseDouble(text);
			}
			eachTimes[r]=times/colCount;
			formatted=format.format(eachTimes[r]);
			weightLabel[r].setText(formatted);
		}
		
	}

	private int getCompareTimes(int items){
		int times=0;
		while(items>0){
			times+=--items;
		}
		return times;
	}
private class TextFocusListener implements FocusListener{
	private int position;
	private int colCount;
	private String bak;
	public TextFocusListener(int p,int c){
		this.position=p;
		this.colCount=c;
	}
	public void focusGained(FocusEvent e) {
		bak=((Text)e.getSource()).getText();
	}

	public void focusLost(FocusEvent e) {
		String text=((Text)e.getSource()).getText();
		if(text.equals("")){
			((Text)e.getSource()).setText(bak);
		}
		if(Double.parseDouble(text)==0)return;
		int rowCount=treeItem.getItemCount();
		int colP=position%colCount;
		for(int i=0;i<rowCount;i++){
			int sameCol=i*colCount+colP;
			if(sameCol!=position){
				matrixText[sameCol].setText("0.000");
			}
		}
	}
	
}
}
