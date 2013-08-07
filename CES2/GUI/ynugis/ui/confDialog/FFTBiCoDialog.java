package ynugis.ui.confDialog;

import java.util.HashMap;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import ynugis.ffsystem.FFSystem;
import ynugis.ffsystem.FFTable;

public class FFTBiCoDialog extends Dialog {
	private final String FFT_FILE_EXT = "*.fft";

	private Tree dTree;

	private Tree oTree;

	private Button dirBtn;

	private Button fileRdo;

	private Button modelRdo;

	private Button newRdo;

	private List fftList;

	private HashMap tree2FFT;

	public FFTBiCoDialog() {
		super(new Shell());
		tree2FFT = new HashMap();
	}

	protected Button createButton(Composite parent, int id, String label,
			boolean defaultButton) {
		return null;
	}

	protected Control createDialogArea(Composite parent) {
		final Composite content = new Composite(parent, SWT.NONE);
		{
			GridLayout contentLay = new GridLayout();
			contentLay.marginTop = 10;
			contentLay.marginRight = 10;
			contentLay.marginBottom = 10;
			contentLay.marginLeft = 10;
			contentLay.numColumns = 1;
			contentLay.verticalSpacing = 10;
			content.setLayout(contentLay);
		}

		final Group loadGroup = new Group(content, SWT.NONE);
		{
			loadGroup.setText("打开因素因子树");
			final GridLayout groupLayout = new GridLayout();
			groupLayout.numColumns = 3;
			groupLayout.horizontalSpacing = 6;
			loadGroup.setLayout(groupLayout);
			loadGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}

		fileRdo = new Button(loadGroup, SWT.RADIO | SWT.NONE);
		{
			GridData grid = new GridData(GridData.FILL_HORIZONTAL);
			grid.horizontalSpan = 1;
			grid.verticalSpan = 1;
			fileRdo.setLayoutData(grid);
			fileRdo.setText("   从文件载入");
			fileRdo.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					if (fileRdo.getSelection()) {
						dirBtn.setEnabled(true);
						String[] items = fftList.getItems();
						if (items.length == 0) {
							return;
						}
						if (items.length > 0) {
							for (int i = 0; i < items.length; i++) {
								if (fftList.getData(items[i]) != null) {
									fftList.setData(items[i], null);
								}
							}
							fftList.removeAll();
						}
					} else {
						dirBtn.setEnabled(false);
					}
				}
			});
		}
		dirBtn = new Button(loadGroup, SWT.NONE);
		{
			GridData grid = new GridData(GridData.CENTER);
			grid.horizontalSpan = 1;
			grid.verticalSpan = 1;
			grid.horizontalAlignment=SWT.BEGINNING;
			dirBtn.setLayoutData(grid);
			dirBtn.setText("浏览");
			dirBtn.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					FileDialog xmlDialog = new FileDialog(getShell(), SWT.MULTI);
					xmlDialog
							.setFilterExtensions(new String[] { FFT_FILE_EXT });
					xmlDialog.setFilterNames(new String[] { "因素因子文件("
							+ FFT_FILE_EXT + ")" });
					xmlDialog.setText("装载因素因子树");
					String dir = xmlDialog.open();
					if (dir == null) {
						return;
					}
					dir = xmlDialog.getFilterPath();
					String[] names = xmlDialog.getFileNames();
					for (int i = 0; i < names.length; i++) {
						String xmlDir = dir + "\\" + names[i];
						FFTable fftable = null;
						try {
							fftable = FFSystem.createFFT(xmlDir);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						if (fftable != null) {
							String ffname = fftable.name;
							if (ffname != null) {
								if (fftList.getData(ffname) == null) {
									if (fftList.getData(ffname) == null) {
										fftList.add(ffname);
										fftList.setData(ffname, fftable);
									}
								}
							}
						}
					}
				}
			});
		}
		fftList = new List(loadGroup, SWT.SINGLE | SWT.V_SCROLL);
		{
			GridData grid = new GridData(GridData.FILL_VERTICAL);
			grid.horizontalSpan = 1;
			grid.verticalSpan = 3;
			grid.minimumWidth = 50;
			fftList.setLayoutData(grid);
			fftList.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					if (fftList.getSelectionCount() != 1)
						return;
					String sled = fftList.getSelection()[0];
					Object fftableObj = fftList.getData(sled);
					if (fftableObj instanceof FFTable) {
						if (oTree != null) {
							oTree.removeAll();
						}
						if (dTree != null) {
							dTree.removeAll();
						}
						tree2FFT.clear();
						initilalizeTrees((FFTable) fftableObj);
					}
				}
			});
		}
		modelRdo = new Button(loadGroup, SWT.RADIO | SWT.NONE);
		{
			GridData grid = new GridData(GridData.FILL_HORIZONTAL);
			grid.horizontalSpan = 2;
			grid.verticalSpan = 1;
			modelRdo.setLayoutData(grid);
			modelRdo.setText("   从模板载入");
			modelRdo.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					if (modelRdo.getSelection()) {
						String[] items = fftList.getItems();
						if (items.length > 0) {
							for (int i = 0; i < items.length; i++) {
								if (fftList.getData(items[i]) != null) {
									fftList.setData(items[i], null);
								}
							}
							fftList.removeAll();
						}
						// load fftable from fixed dir
						FFTable[] tables = FFSystem.getFFTableTemplates();
						if (tables != null) {
							if (tables.length > 0) {
								for (int i = 0; i < tables.length; i++) {
									FFTable fftable = tables[i];
									String ffname = fftable.name;
									if (ffname != null) {
										if (fftList.getData(ffname) == null) {
											fftList.add(ffname);
											fftList.setData(ffname, fftable);
										}
									}
								}
							}
						}
					} 
				}
			});
		}
		newRdo = new Button(loadGroup, SWT.RADIO | SWT.NONE);
		{
			GridData grid = new GridData(GridData.FILL_HORIZONTAL);
			grid.horizontalSpan = 2;
			grid.verticalSpan = 1;
			newRdo.setLayoutData(grid);
			newRdo.setText("   新建");
			newRdo.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					if (newRdo.getSelection()) {
						dirBtn.setEnabled(false);
						String[] items = fftList.getItems();
						if (items.length > 0) {
							for (int i = 0; i < items.length; i++) {
								if (fftList.getData(items[i]) != null) {
									fftList.setData(items[i], null);
								}
							}
							fftList.removeAll();
						}
						// new a fftable with dialog
						FFTable newfftable = new FFTable();
						FFTDialogHelper dialogHelper = new FFTDialogHelper();
						dialogHelper.fillFFTable(getShell(), newfftable);
						String ffname = newfftable.name;
						if (ffname == null) {
							return;
						}
						if (fftList.getData(ffname) == null) {
							fftList.add(ffname);
							fftList.setData(ffname, newfftable);
						}
					} else {
						dirBtn.setEnabled(true);
					}
				}
			});
		}

		final Composite treeCom = new Composite(content, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		treeCom.setLayout(gridLayout);
		final GridData gridData_1 = new GridData(GridData.FILL_BOTH);
		gridData_1.minimumWidth = 480;
		gridData_1.minimumHeight = 300;
		treeCom.setLayoutData(gridData_1);

		final Label oTreeLabel = new Label(treeCom, SWT.NONE);
		oTreeLabel.setText("评分操作区");

		final Label dTreeLabel = new Label(treeCom, SWT.NONE);
		dTreeLabel.setText("权重显示区");

		oTree = new Tree(treeCom, SWT.BORDER);
		oTree.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true,
				true));
		oTree.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				if (e.button > 1) {
					System.out.println("right click");
					TreeItem treeItem = oTree.getSelection()[0];
					Object objTree = treeItem.getData();
					System.out.println("right click:" + objTree);
					if (objTree != null) {
						System.out.println(tree2FFT.containsKey(treeItem));
						new FFTBicoMatrixDialog(treeItem, tree2FFT).open();
						if (fftList.getSelectionCount() != 1)
							return;
						String sled = fftList.getSelection()[0];
						Object fftableObj = fftList.getData(sled);
						if (fftableObj instanceof FFTable) {
							new FFTDialogHelper(true).growTree(dTree,
									(FFTable) fftableObj);
						}
					}
				}
			}
		});

		dTree = new Tree(treeCom, SWT.BORDER);
		dTree.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true,
				true));
		return content;
	}

	protected void initializeBounds() {
		super.createButton((Composite) getButtonBar(), IDialogConstants.OK_ID,
				"确定", true);
		super.createButton((Composite) getButtonBar(),
				IDialogConstants.CANCEL_ID, "取消", true);
		super.initializeBounds();
	}

	protected void okPressed() {
		if(fftList.getSelectionCount()==1){
			Object fftObj=fftList.getData(fftList.getSelection()[0]);
			if(fftObj==null){
				super.okPressed();
				return;
			}
			if(fftObj instanceof FFTable){
				new SaveFFTDialog((FFTable)fftObj).open();
			}
		}
		super.okPressed();
	}

	private void initilalizeTrees(FFTable table) {
		new FFTDialogHelper(false).growTree(oTree, table);
		new FFTDialogHelper(true).growTree(dTree, table);
	}
}
