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

public class FFTAhpMatrixDialog extends Dialog {
	private final int CALCBTN_ID = 1000;

	private final double INCISOR_CR = 0.1;

	private TreeItem treeItem;

	private HashMap orderMap;

	private Text[] matrixText;

	private Label[] weightLabel;

	private Label judgeLabel;
	
	private boolean calcType;

	public FFTAhpMatrixDialog(TreeItem item, HashMap map,boolean type) {
		super(new Shell());
		this.treeItem = item;
		this.orderMap = map;
		this.calcType=type;
	}

	protected Button createButton(Composite parent, int id, String label,
			boolean defaultButton) {
		return null;
	}

	protected Control createDialogArea(Composite parent) {
		int colCount = this.treeItem.getItemCount();
		int colLabelCount = colCount + 1;
		int cL, rL, c, r, i;
		this.matrixText = new Text[colCount * colCount];
		this.weightLabel = new Label[colCount];
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
		for (int iL = 0; iL < colLabelCount * colLabelCount; iL++) {
			cL = iL % colLabelCount;
			rL = (iL - cL) / colLabelCount;
			c = cL - 1;
			r = rL - 1;
			if (c >= 0 && r >= 0) {
				i = r * colCount + c;
				this.matrixText[i] = new Text(content, SWT.NONE);
				this.matrixText[i].addVerifyListener(new VerifyListener(){
					public void verifyText(VerifyEvent e) {
						if("0123456789.".indexOf(e.text)>=0){
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
				if(this.calcType){
					this.matrixText[i].addFocusListener(new SimpleAhpFocusListener(i));
				}else this.matrixText[i].addFocusListener(new ComplexAhpFocusListener(i));
				// this.matrixText[i].setSize(10,SWT.DEFAULT);
				// this.matrixText[i].setLayoutData(gridCenter);
				this.matrixText[i].setText("1.000");
				if(this.calcType){
					if (r>0)
						this.matrixText[i].setEditable(false);
				}else{
					if (r > c||r==c)
						this.matrixText[i].setEditable(false);
				}
				
			} else {
				if (cL + rL == 0)
					new Label(content, SWT.NONE);
				else {
					String itemName = this.getNameTreeItem(treeItem.getItem(rL
							+ cL - 1));
					Label label = new Label(content, SWT.NONE);
					label.setText(itemName);
					// label.setLayoutData(gridCenter);
				}
			}
		}
		new Label(content, SWT.NONE).setText("权重");
		for (int res = 0; res < colCount; res++) {
			this.weightLabel[res] = new Label(content, SWT.NONE);
			FFItem ffitem = (FFItem) treeItem.getItem(res).getData();
			this.weightLabel[res].setText(String.valueOf(ffitem.weight));
		}
		new Label(content, SWT.NONE).setText("判决");
		judgeLabel = new Label(content, SWT.NONE);
		judgeLabel.setText("待验证");
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
			this.calculateAHP();
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
		System.out.println("name:" + itemName + " prefix:" + prefixIdx);
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
		for (int i = 0; i < treeItem.getItemCount(); i++) {
			String itemName = this.getNameTreeItem(treeItem.getItem(i));
			itemName += GUI.PREFIX + matrixText[i].getText() + GUI.SUFFIX;
			treeItem.getItem(i).setText(itemName);
		}
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
	private void calculateAHP() {
		DecimalFormat format = new DecimalFormat("0.00");
		String formatted = "";
		int col = treeItem.getItemCount();
		double[] colTotal = new double[col];
		double[] weightList = new double[col];
		double weightTotal = 0;
		;
		for (int m = 0; m < col; m++) {
			double total = 0;
			for (int n = 0; n < col; n++) {
				String mnText = matrixText[m + n * col].getText();
				total += Double.parseDouble(mnText);
			}
			colTotal[m] = total;
		}
		for (int m = 0; m < col - 1; m++) {
			double weight = 0;
			for (int n = 0; n < col; n++) {
				String mnText = matrixText[m * col + n].getText();
				double subWeight = Double.parseDouble(mnText);
				subWeight /= colTotal[n];
				weight += subWeight;
			}
			weight /= col;
			formatted = format.format(weight);
			System.out.println(weight);
			weightLabel[m].setText(formatted);
			weightList[m] = Double.parseDouble(formatted);
			weightTotal += weightList[m];
		}
		formatted = format.format(1 - weightTotal);
		weightList[col - 1] = Double.parseDouble(formatted);
		System.out.println("last weight:" + weightList[col - 1]);
		weightLabel[col - 1].setText(formatted);
		if (isValidate(weightList)) {
			judgeLabel.setText("通过");
			System.out.println("Pass");
		} else
			judgeLabel.setText("未通过");
	}

	private boolean isValidate(double[] weights) {
		int col = treeItem.getItemCount();
		if (col < 3) {
			return true;
		}
		double total = 0;
		double CI, RI;
		for (int m = 0; m < col; m++) {
			double colTotal = 0;
			for (int n = 0; n < col; n++) {
				String mnText = matrixText[n + m * col].getText();
				colTotal += Double.parseDouble(mnText) * weights[n];
			}
			total += colTotal / weights[m];
		}
		total /= col;
		CI = (total - col) / (col - 1);
		RI = getRI(col);
		if ((CI / RI) < INCISOR_CR)
			return true;
		else
			return false;
	}

	/**
	 * statistics
	 */
	private double getRI(int columnCount) {
		double[] RIs = new double[] { 0, 0, 0.52, 0.89, 1.12, 1.26, 1.36, 1.41,
				1.46, 1.49, 1.52, 1.54, 1.56, 1.58, 1.59 };
		return RIs[columnCount - 1];
	}

	private class SimpleAhpFocusListener implements FocusListener {
		private int id;
		private String textBak;

		public SimpleAhpFocusListener(int position) {
			this.id = position;
		}

		public void focusGained(FocusEvent e) {
			Object source = e.getSource();
			if (source instanceof Text) {
				Text text = (Text) source;
				textBak=text.getText();
				text.selectAll();
			}
		}

		public void focusLost(FocusEvent e) {
			int col = treeItem.getItemCount();
			Object source = e.getSource();
			if (source instanceof Text) {
				if (id >= col)
					return;
				DecimalFormat format = new DecimalFormat("0.000");
				String formatted = "";
				Text text = (Text) source;
				String textStr = text.getText();
				if(textStr.equals("")||textStr.equals("0")){
					textStr=textBak;
				}
				double a, b, result;
				a = Double.parseDouble(textStr);
				for (int m = 0; m < id; m++) {
					String sTextStr = matrixText[m].getText();
					b = Double.parseDouble(sTextStr);
					result = a / b;
//					result = b / a;
					formatted = format.format(result);
					System.out.println("a " + a + " ;b " + b + " ;modified "
							+ (m * col + id) + " ," + (id * col + m));
					matrixText[m * col + id].setText(formatted);
					result = b / a;
//					result = a / b;
					formatted = format.format(result);
					matrixText[id * col + m].setText(formatted);
				}
			}
		}
	}

	private class ComplexAhpFocusListener implements FocusListener {
		private int id;
		private String textBak;
		public ComplexAhpFocusListener(int position) {
			this.id = position;
		}

		public void focusGained(FocusEvent e) {
			Object source = e.getSource();
			if (source instanceof Text) {
				Text text = (Text) source;
				textBak=text.getText();
				text.selectAll();
			}
		}

		public void focusLost(FocusEvent e) {
			int col = treeItem.getItemCount();
			Object source = e.getSource();
			if (source instanceof Text) {
				DecimalFormat format = new DecimalFormat("0.000");
				String formatted = "";
				Text text = (Text) source;
				String textStr = text.getText();
				if(textStr.equals("")||textStr.equals("0")){
					textStr=textBak;
				}
				double a, result;
				a = Double.parseDouble(textStr);
				int c = id % col;
				int r = (id - c) / col;
				result = 1 / a;
				formatted = format.format(result);
				matrixText[c * col + r].setText(formatted);
			}

		}

	}
//	private class 
}
