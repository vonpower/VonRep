package ynugis.ui.confDialog;

import java.awt.Frame;
import java.io.IOException;
import java.text.DecimalFormat;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import ynugis.application.CESCORE;
import ynugis.ui.fileDialog.FileShell;
import ynugis.ui.fileDialog.FileShellFactory;

import com.esri.arcgis.beans.map.MapBean;
import com.esri.arcgis.beans.pagelayout.PageLayoutBean;
import com.esri.arcgis.carto.esriPageFormID;
import com.esri.arcgis.controls.MapControl;
import com.esri.arcgis.controls.PageLayoutControl;
import com.esri.arcgis.controls.esriControlsMousePointer;
import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.output.IPrinter;

public class PrintDialog extends Dialog {
	private PageLayoutBean pageLayoutControl;

	private MapBean mapControl;
	
	private Text filePathText;

	private Combo pSizeCombo;

	private Combo pMappingCombo;

	private Button portBtn;

	private Button scapeBtn;

	private Text overlayText;

	private Label pageCount2Label;

	private Text startPageText;

	private Text endPageText;

	private Label printerName2Label;

	private Label printerPaper2Label;

	private Label printerOrient2Label;

	public PrintDialog(MapBean mapControl) {
		super(new Shell());
		this.pageLayoutControl = new PageLayoutBean();
		this.mapControl = mapControl;

	}

	public PrintDialog(MapBean mapControl,
			PageLayoutBean pageLayoutControl) {
		super(new Shell());
		this.pageLayoutControl = pageLayoutControl;
		new CESCORE().MapToPage(mapControl, pageLayoutControl);
	}

	protected Control createDialogArea(Composite parent) {
		// TODO Auto-generated method stub
		final Composite content = new Composite(parent, SWT.NONE);
		{
			GridLayout contentLay = new GridLayout();
			contentLay.marginTop = 10;
			contentLay.marginRight = 10;
			contentLay.marginBottom = 10;
			contentLay.marginLeft = 10;
			contentLay.numColumns = 2;
			contentLay.verticalSpacing = 20;
			contentLay.horizontalSpacing = 20;
			content.setLayout(contentLay);
			content.setSize(650, 450);
		}
		GridData panelGD = new GridData(GridData.FILL_BOTH);
		panelGD.minimumHeight = 430;
		panelGD.minimumWidth = 300;
		final Composite topCom = new Composite(content, SWT.NONE);
		{
			GridData topGD = new GridData();
			topGD.minimumHeight = 20;
			topGD.minimumWidth = 650;
			topGD.horizontalSpan = 2;
			topCom.setLayoutData(topGD);
			GridLayout topGL=new GridLayout();
			topGL.marginLeft=0;
			topGL.marginRight=0;
			topGL.numColumns=3;
			topCom.setLayout(topGL);
			filePathText=new Text(topCom,SWT.READ_ONLY|SWT.BORDER);
			{
				GridData loadGD=new GridData(GridData.FILL_HORIZONTAL);
				loadGD.horizontalSpan=2;
				loadGD.minimumWidth=450;
				filePathText.setLayoutData(loadGD);
			}
			final Button loadBtn = new Button(topCom, SWT.BORDER);
			{
				GridData loadGD=new GridData(GridData.FILL_HORIZONTAL);
				loadGD.horizontalSpan=1;
				loadBtn.setLayoutData(loadGD);
				loadBtn.setText("从文件打开(*.MXD)");
				loadBtn.forceFocus();
				loadBtn.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						try {
							String filePath;
							FileShellFactory fileShellFactory = new FileShellFactory();
							FileShell fileShell = fileShellFactory
									.getFileShell(FileShellFactory.OPEN_FILE);
							if (fileShell.setFilePath(new Shell())) {
								filePath = fileShell.getFilePath();
								filePathText.setText(filePath);
							} else {
								return;
							}
							if (pageLayoutControl.checkMxFile(filePath)) {
								pageLayoutControl
										.setMousePointer(esriControlsMousePointer.esriPointerHourglass);
								pageLayoutControl.loadMxFile(filePath, null);
								pageLayoutControl
										.setMousePointer(esriControlsMousePointer.esriPointerDefault);
							} else {
								MessageDialog.openInformation(null, "加载地图",
										"地图加载失败");
								return;
							}
							// Update page display
							pSizeCombo.select(pageLayoutControl.getPage()
									.getFormID());
							pMappingCombo.select(pageLayoutControl.getPage()
									.getPageToPrinterMapping());
							if (pageLayoutControl.getPage().getOrientation() == 1)
								portBtn.setSelection(true);
							else
								scapeBtn.setSelection(true);

							// Zoom to whole page
							pageLayoutControl.zoomToWholePage();
							// Update printer page display
							updatePrintPageDisplay();
						} catch (Exception ex) {
							// TODO: handle exception
							ex.printStackTrace();
						}
					}
				});
			}
			/*final Button transBtn=new Button(topCom,SWT.BORDER);
			{
				transBtn.setText("从数据视图打开");
				transBtn.addSelectionListener(new SelectionAdapter(){
					public void widgetSelected(SelectionEvent e) {
						try {
							new CESCORE().MapToPage(mapControl,
									pageLayoutControl);
							pageLayoutControl.zoomToWholePage();
						} catch (Exception ex) {
							// TODO: handle exception
						}
					}
				});
			}*/
		}
		final Composite leftCom = new Composite(content, SWT.EMBEDDED);
		{
			leftCom.setLayout(new FillLayout(SWT.FILL));
			leftCom.setLayoutData(panelGD);
			Frame pageLayoutFrame = SWT_AWT.new_Frame(leftCom);
			pageLayoutFrame.add(pageLayoutControl);
		}
		final Composite rightCom = new Composite(content, SWT.NONE);
		{
			FillLayout rightLayout = new FillLayout(SWT.FILL);
			rightCom.setLayout(rightLayout);
			rightCom.setLayoutData(panelGD);
			GridLayout groupLayout = new GridLayout();
			groupLayout.numColumns = 2;
			groupLayout.verticalSpacing=5;
			final Group pageSizeGroup = new Group(rightCom, SWT.SHADOW_IN);
			{
				pageSizeGroup.setLayout(groupLayout);
				pageSizeGroup.setText("页面");
				GridData singleGD = new GridData(GridData.FILL_BOTH);
				singleGD.horizontalSpan = 2;
				singleGD.verticalIndent=5;
				GridData doubleGD = new GridData(GridData.FILL_BOTH);
				doubleGD.horizontalSpan = 1;
				doubleGD.verticalIndent=5;
				final Label pSizeLabel = new Label(pageSizeGroup, SWT.NONE);
				{
					pSizeLabel.setLayoutData(singleGD);
					pSizeLabel.setText("页尺寸");
				}
				pSizeCombo = new Combo(pageSizeGroup, SWT.READ_ONLY);
				{
					pSizeCombo.setLayoutData(singleGD);
					pSizeCombo.add("Letter - 8.5in x 11in. ");
					pSizeCombo.add("Legal - 8.5in x 14in.");
					pSizeCombo.add("Tabloid - 11in x 17in.");
					pSizeCombo.add("C - 17in x 22in.");
					pSizeCombo.add("D - 22in x 34in.");
					pSizeCombo.add("E - 34in x 44in.");
					pSizeCombo.add("A5 - 148mm x 210mm.");
					pSizeCombo.add("A4 - 210mm x 297mm.");
					pSizeCombo.add("A3 - 297mm x 420mm.");
					pSizeCombo.add("A2 - 420mm x 594mm.");
					pSizeCombo.add("A1 - 594mm x 841mm.");
					pSizeCombo.add("A0 - 841mm x 1189mm.");
					pSizeCombo.add("Custom Page Size.");
					pSizeCombo.add("Same as Printer Form.");
					pSizeCombo.select(7);
					pSizeCombo.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							try {
								pageLayoutControl.getPage().setFormID(
										pSizeCombo.getSelectionIndex());
								updatePrintPageDisplay();
							} catch (AutomationException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
				}
				final Label pMappingLabel = new Label(pageSizeGroup, SWT.NONE);
				{
					pMappingLabel.setLayoutData(singleGD);
					pMappingLabel.setText("映射");
				}
				pMappingCombo = new Combo(pageSizeGroup, SWT.READ_ONLY);
				{
					pMappingCombo.setLayoutData(singleGD);
					pMappingCombo.add("0: Crop");
					pMappingCombo.add("1: Scale");
					pMappingCombo.add("2: Tile");
					pMappingCombo.select(0);
					pMappingCombo.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							try {
								pageLayoutControl.getPage()
										.setPageToPrinterMapping(
												pMappingCombo
														.getSelectionIndex());
								updatePrintPageDisplay();
							} catch (AutomationException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
				}
				portBtn = new Button(pageSizeGroup, SWT.RADIO | SWT.NONE);
				{
					portBtn.setLayoutData(doubleGD);
					portBtn.setText("Portrait");
					portBtn.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							try {
								if (pageLayoutControl.getPage().getFormID() != esriPageFormID.esriPageFormSameAsPrinter)
									pageLayoutControl.getPage().setOrientation(
											(short) 1);
								updatePrintPageDisplay();
							} catch (Exception ex) {
								// TODO: handle exception
								ex.printStackTrace();
							}
						}
					});
				}
				scapeBtn = new Button(pageSizeGroup, SWT.RADIO | SWT.NONE);
				{
					scapeBtn.setLayoutData(doubleGD);
					scapeBtn.setText("Landscape");
					scapeBtn.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							try {
								if (pageLayoutControl.getPage().getFormID() != esriPageFormID.esriPageFormSameAsPrinter)
									pageLayoutControl.getPage().setOrientation(
											(short) 2);
								updatePrintPageDisplay();
							} catch (Exception ex) {
								// TODO: handle exception
								ex.printStackTrace();
							}
						}
					});
				}
				final Label pageCountLabel = new Label(pageSizeGroup, SWT.NONE);
				{
					pageCountLabel.setLayoutData(doubleGD);
					pageCountLabel.setText("页面数");
				}
				pageCount2Label = new Label(pageSizeGroup, SWT.NONE);
				{
					pageCount2Label.setLayoutData(doubleGD);
					pageCount2Label.setText("0");
				}
			}
			final Group printerGroup = new Group(rightCom, SWT.SHADOW_IN);
			{
				printerGroup.setLayout(groupLayout);
				printerGroup.setText("打印机");
				GridData doubleGrid = new GridData(GridData.FILL_BOTH);
				doubleGrid.horizontalSpan = 1;

				final Label printerNameLabel = new Label(printerGroup, SWT.NONE);
				{
					printerNameLabel.setLayoutData(doubleGrid);
					printerNameLabel.setText("名称");
				}
				printerName2Label = new Label(printerGroup, SWT.NONE);
				{
					printerName2Label.setLayoutData(doubleGrid);
				}
				final Label printerPaperLabel = new Label(printerGroup,
						SWT.NONE);
				{
					printerPaperLabel.setLayoutData(doubleGrid);
					printerPaperLabel.setText("纸张");
				}
				printerPaper2Label = new Label(printerGroup, SWT.NONE);
				{
					printerPaper2Label.setLayoutData(doubleGrid);
				}
				final Label printerOrientLabel = new Label(printerGroup,
						SWT.NONE);
				{
					printerOrientLabel.setLayoutData(doubleGrid);
					printerOrientLabel.setText("朝向");
				}
				printerOrient2Label = new Label(printerGroup, SWT.NONE);
				{
					printerOrient2Label.setLayoutData(doubleGrid);
				}
			}
			final Group printGroup = new Group(rightCom, SWT.SHADOW_IN);
			{
				GridLayout printLayout = new GridLayout();
				printLayout.numColumns = 4;
				printLayout.verticalSpacing=6;
				printGroup.setLayout(printLayout);
				printGroup.setText("打印");
				GridData oneGD = new GridData(GridData.FILL_BOTH);
				oneGD.horizontalSpan = 4;
				oneGD.verticalIndent=10;
				GridData twoGD = new GridData(GridData.FILL_BOTH);
				twoGD.horizontalSpan = 2;
				twoGD.verticalIndent=10;
				GridData fourGD = new GridData(GridData.FILL_BOTH);
				fourGD.horizontalSpan = 1;
				fourGD.verticalIndent=10;
				final Label overlayLabel = new Label(printGroup, SWT.NONE);
				{
					overlayLabel.setLayoutData(twoGD);
					overlayLabel.setText("重叠");
				}
				overlayText = new Text(printGroup, SWT.BORDER);
				{
					overlayText.setLayoutData(twoGD);
					overlayText.setText("0");
					overlayText.addFocusListener(new FocusAdapter() {
						public void focusGained(FocusEvent e) {
							updatePrintPageDisplay();
						}
					});
				}
				final Label startPageLabel = new Label(printGroup, SWT.NONE);
				{
					startPageLabel.setLayoutData(fourGD);
					startPageLabel.setText("从");
				}
				startPageText = new Text(printGroup, SWT.BORDER);
				{
					startPageText.setLayoutData(fourGD);
					startPageText.setText("0");
				}
				final Label endPageLabel = new Label(printGroup, SWT.NONE);
				{
					endPageLabel.setLayoutData(fourGD);
					endPageLabel.setText("到");
				}
				endPageText = new Text(printGroup, SWT.BORDER);
				{
					endPageText.setLayoutData(fourGD);
					endPageText.setText("0");
				}
				final Button printBtn = new Button(printGroup, SWT.BORDER);
				{
					printBtn.setLayoutData(oneGD);
					printBtn.setText("准备打印");
					printBtn.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							try {
								if (pageLayoutControl.getPrinter() != null)
									pageLayoutControl
											.setMousePointer(esriControlsMousePointer.esriPointerHourglass);
								IPrinter printer = pageLayoutControl
										.getPrinter();
								if (printer.getPaper().getOrientation() != pageLayoutControl
										.getPage().getOrientation()) {
									printer.getPaper().setOrientation(
											pageLayoutControl.getPage()
													.getOrientation());
									updatePrintingDisplay();
								}
								Short pageStart = new Short(startPageText
										.getText());
								Short pageEnd = new Short(endPageText.getText());
								Double overLap = new Double(overlayText
										.getText());
								pageLayoutControl.printPageLayout(pageStart
										.shortValue(), pageEnd.shortValue(),
										overLap.doubleValue());
								pageLayoutControl
										.setMousePointer(esriControlsMousePointer.esriPointerDefault);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					});
				}
			}

		}

		try {
			pageLayoutControl.getActiveView().refresh();
			new CESCORE().MapToPage(mapControl,pageLayoutControl);
			pageLayoutControl.zoomToWholePage();
			updatePrintPageDisplay();
			updatePrintingDisplay();
		} catch (AutomationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}

	protected void initializeBounds() {
		super.createButton((Composite)getButtonBar(),IDialogConstants.OK_ID,"关闭",true);
		super.initializeBounds();
	}

	protected Button createButton(Composite parent, int id, String label, boolean defaultButton) {
		return null;
	}

	private void updatePrintPageDisplay() {
		try {
			// Determine the number of pages
			int pageCount;
			Double value = new Double(overlayText.getText());
			pageCount = pageLayoutControl.getPrinterPageCount(value
					.doubleValue());
			Integer pageCountInt = new Integer(pageCount);
			pageCount2Label.setText(pageCountInt.toString());
			// Validate start and end pages
			int pageStart, pageEnd;
			pageStart = new Integer(startPageText.getText()).intValue();
			pageEnd = new Integer(endPageText.getText()).intValue();
			if (pageStart < 1 || pageStart > pageCount) {
				startPageText.setText("1");
			}
			if (pageEnd < 1 || pageEnd > pageCount) {
				endPageText.setText(pageCountInt.toString());
			}
		} catch (IOException ex) {
			System.out.println("Exception in updatePrintingDisplay " + ex);
			ex.printStackTrace();
		} catch (NumberFormatException ex) {
			System.out.println("Exception in updatePrintPageDisplay " + ex);
			ex.printStackTrace();
		}

	}

	private void updatePrintingDisplay() {
		try {
			if (pageLayoutControl.getPrinter() != null) {
				// Get IPrinter interface through the PageLayoutControl's
				// printer
				IPrinter printer = pageLayoutControl.getPrinter();
				// Determine the orientation of the printer's paper
				if (printer.getPaper().getOrientation() == 1)
					printerOrient2Label.setText("Portrait");
				else
					printerOrient2Label.setText("Landscape");
				// Determine the printer name
				printerName2Label.setText(printer.getPaper().getPrinterName());
				// Determine the printer's paper size
				double width[] = new double[1], height[] = new double[1];
				printer.getPaper().queryPaperSize(width, height);
				// Format to three decimals
				DecimalFormat format = new DecimalFormat("###.000");
				String widthStr = format.format(width[0]);
				String heightStr = format.format(height[0]);
				printerPaper2Label.setText(widthStr + " by " + heightStr
						+ " Inches ");

			}
		} catch (IOException ex) {
			System.out.println("Exception in updatePrintingDisplay " + ex);
		}

	}
}
