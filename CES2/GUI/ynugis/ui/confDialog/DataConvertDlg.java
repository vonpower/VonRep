package ynugis.ui.confDialog;
import java.io.File;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import ynugis.dataconvert.ConvertEnvironment;
import ynugis.dataconvert.Mif2SimpleMiF;
import ynugis.dataconvert.SimpleMif2Shp;
import ynugis.dataconvert.Tab2Mif;

/*
 * @author 冯涛，创建日期：2003-11-6
 * blog--http://apower.blogone.net
 * QQ:17463776
 * MSN:apower511@msn.com
 * E-Mail:VonPower@Gmail.com
 */

public class DataConvertDlg extends TitleAreaDialog {

	private Text text;
	private List list;

	private Combo srcCombo;

	private int flag = 0;

	private String[] srcFiles;

	/**
	 * Create the dialog
	 * 
	 * @param parentShell
	 */
	public DataConvertDlg(Shell parentShell) {
		super(parentShell);
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

		final Group srcGroup = new Group(container, SWT.NONE);
		srcGroup.setText("原始数据");
		srcGroup.setBounds(4, 4, 321, 109);

		final Label srcLabel = new Label(srcGroup, SWT.NONE);
		srcLabel.setText("请选择原始数据的数据类型：");
		srcLabel.setBounds(15, 25, 160, 18);

		srcCombo = new Combo(srcGroup, SWT.READ_ONLY);
		srcCombo.setBounds(181, 16, 120, 30);
		srcCombo.add("TAB地图格式");
		srcCombo.add("MIF地图格式");
		srcCombo.select(0);

		final Button SpecifyBtn = new Button(srcGroup, SWT.NONE);
		SpecifyBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(getShell(), SWT.OPEN | SWT.MULTI);
				fd.setFilterPath("c:");
				String[] nameStr = null;
				String[] extensionStr = null;
				switch (srcCombo.getSelectionIndex()) {
				case 0: {
					flag = ConvertEnvironment.TAB;
					nameStr = new String[] { "TAB地图格式（*.TAB）" };
					extensionStr = new String[] { "*.tab" };
				}
					break;
				case 1: {
					flag = ConvertEnvironment.MIF;
					nameStr = new String[] { "MIF地图格式（*.MIF）" };
					extensionStr = new String[] { "*.mif" };

				}
					break;
				}

				fd.setFilterNames(nameStr);
				fd.setFilterExtensions(extensionStr);
				fd.open();
				String[] temp = fd.getFileNames();

				if (temp.length != 0) {
					list.removeAll();
					srcFiles = new String[temp.length];
					for (int i = 0; i < temp.length; i++) {
						srcFiles[i] = fd.getFilterPath() + File.separator
								+ temp[i];
						list.add(temp[i]);
					}

				}
				

			}
		});
		SpecifyBtn.setText("指定原始数据");
		SpecifyBtn.setBounds(101, 60, 120, 30);

		final Button startBtn = new Button(container, SWT.NONE);
		startBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				
				
				String[] results=null;
				
				File f=new File(text.getText());
				if(srcFiles==null)
				{
					MessageDialog.openInformation(getShell(),"空的源数据","请指定需要转换的数据文件！");
					return;
				}
				if(!f.isDirectory())
				{
					MessageDialog.openInformation(getShell(),"目录问题","指定的输出目录不存在！");
					return;
				}
				String destPath=text.getText();
				
				switch (flag) {
				case ConvertEnvironment.TAB: {
					//把TAb数据转换为SHP
					String mifPath=destPath+File.separator+"mif"+File.separator;
					String simpleMifPath=destPath+File.separator+"simpleMif"+File.separator;
					String shpPath=destPath+File.separator+"ShapeFiles"+File.separator;
					
					Tab2Mif t2m=new Tab2Mif(srcFiles,mifPath);
					
					String[] tt=t2m.convert();
					Mif2SimpleMiF m2sm=new Mif2SimpleMiF(tt,simpleMifPath);
					
 					SimpleMif2Shp sm2s=new SimpleMif2Shp(m2sm.convert(),shpPath);
					results=sm2s.convert();
				}
					break;
				case ConvertEnvironment.MIF: {
					//把Mif数据转换为SHP
					String simpleMifPath=destPath+File.separator+"simpleMif"+File.separator;
					String shpPath=destPath+File.separator+"ShapeFiles"+File.separator;
					
					File path=new File(simpleMifPath);
					if(!path.isDirectory())
					{
						path.mkdir();
					}
					Mif2SimpleMiF m2sm=new Mif2SimpleMiF(srcFiles,simpleMifPath);
					SimpleMif2Shp sm2s=new SimpleMif2Shp(m2sm.convert(),shpPath);
					results=sm2s.convert();

				}
					break;
				}
				if(!results.equals(null))MessageDialog.openInformation(getShell(),"好的～","数据转换已完成！");
				else MessageDialog.openWarning(getShell(),"嗯？","没有输出任何数据！？");
			}
		});
		startBtn.setBounds(100, 334, 120, 30);
		startBtn.setText("开始转换");

		final ListViewer listViewer = new ListViewer(container, SWT.V_SCROLL | SWT.MULTI | SWT.BORDER);
		//listViewer.setInput(new Object());
		list = listViewer.getList();
		list.setBounds(6, 143, 317, 119);

		final Label label = new Label(container, SWT.NONE);
		label.setText("待转换的数据文件：");
		label.setBounds(104, 119, 120, 22);

		final Label label_1 = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setBounds(6, 265, 319, 6);

		text = new Text(container, SWT.BORDER);
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				
				File f=new File(text.getText());
				
				if(f.isDirectory())startBtn.setEnabled(true);
				else startBtn.setEnabled(false);
			}
		});
		text.setBounds(5, 299, 272, 22);

		final Label label_2 = new Label(container, SWT.NONE);
		label_2.setText("输出路径：");
		label_2.setBounds(8, 277, 174, 17);

		final Button button = new Button(container, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				
				DirectoryDialog dd=new DirectoryDialog(getShell());
				dd.setText("目录指定");
				dd.setMessage("请指定数据转换的输出目录：");
				String path=dd.open();
				if(path!=null)
				{
					text.setText(path);
				}
			}
		});
		button.setText("...");
		button.setBounds(288, 299, 30, 19);
		setTitle("数据转换工具");
		setMessage("帮助您进行数据转换…………");
		//
		return area;
	}

	/**
	 * Create contents of the button bar
	 * 
	 * @param parent
	 */
	protected void createButtonsForButtonBar(Composite parent) {
	}

	/**
	 * Return the initial size of the dialog
	 */
	protected Point getInitialSize() {
		return new Point(338, 483);
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("CES数据转换工具");
	}

}
