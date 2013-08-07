package ynugis.ui.confDialog;

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import ynugis.classifyBusiness.ClassifyProject;
import ynugis.classifyBusiness.classifyItem.BGCMap;
import ynugis.classifyBusiness.classifyItem.ObstructCMap;
import ynugis.classifyBusiness.classifyItem.RangeCMap;
import ynugis.ui.face.MainFace;


public class prjDialog extends TitleAreaDialog {
	private Text barrier_text;
	private MainFace face;

	private List list;

	private Text rangeText;

	private String dir;

	//private Combo combo;

	private Text nameText;

	private Text workText;

	private Text temporaryText;

	private Text unitText;

	private Composite container;

	private Button finishbutton;
	private String[] filenames;
	private int i;
    private FileDialog BGdlg;
    private String filename;
  
  
	// private String validDir;

	/**
	 * Create the dialog
	 * 
	 * @param parentShell
	 */
	public prjDialog(MainFace face) {
		super(face.getShell());
		this.face=face;
	}

	/**
	 * Create contents of the dialog
	 * 
	 * @param parent
	 */
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		container = new Composite(area, SWT.NONE);
		//container.setFont(SWTResourceManager.getFont("", 11, SWT.NONE));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Label label_2 = new Label(container, SWT.NONE);
		label_2.setText("项目名称");
		label_2.setBounds(13, 15, 65, 20);

		final Label label_3 = new Label(container, SWT.NONE);
		label_3.setText("背景地图数据文件");
		label_3.setBounds(13, 224, 97, 20);

		final Label label_4 = new Label(container, SWT.NONE);
		label_4.setText("工作路径");
		label_4.setBounds(13, 85, 95, 20);

		final Label label_5 = new Label(container, SWT.NONE);
		label_5.setText("临时路径");
		label_5.setBounds(13, 120, 95, 20);

		final Label label_6 = new Label(container, SWT.NONE);
		label_6.setText("定级单元大小（m）");
		label_6.setBounds(13, 155, 102, 20);

		unitText = new Text(container, SWT.BORDER);
		unitText.setBounds(121, 149, 362, 20);
		unitText.addVerifyListener(new VerifyListener() {

			public void verifyText(VerifyEvent e) {
				boolean b = ("0123456789".indexOf(e.text) >= 0);
				e.doit = b;

			}

		});

		workText = new Text(container, SWT.BORDER);
		workText.setBounds(121, 77, 262, 20);
		//workText.setEditable(false);
		workText.addModifyListener(new ModifyListener(){

			public void modifyText(ModifyEvent e) {
				temporaryText.setText(workText.getText()+"\\temp");
				String path=temporaryText.getText();
				File dir=new File(path);
				 if (!dir.exists()) {            
		                dir.mkdir();
		                
		            }  
				 dir.deleteOnExit();

			}});
		

		temporaryText = new Text(container, SWT.BORDER);
		temporaryText.setBounds(121, 112, 362, 20);
		temporaryText.setEnabled(false);
        
		
		

		nameText = new Text(container, SWT.BORDER);
		nameText.setBounds(121, 9, 361, 20);

		rangeText = new Text(container, SWT.BORDER);
		rangeText.setBounds(121, 39, 260, 20);
		rangeText.setEditable(false);
		
		

		final Button addButton = new Button(container, SWT.NONE);
		addButton.setBounds(411, 359, 72, 25);
		addButton.setText("添加");
		addButton.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				Shell shell = new Shell();
				BGdlg = new FileDialog(shell, SWT.None | SWT.MULTI);
				BGdlg.setText("打开文件");
				BGdlg.setFilterExtensions(new String[] { "*.shp" });
				filename = BGdlg.open();
				filenames = BGdlg.getFileNames();
				System.out.println(filename == null ? "" : filename);
				for ( i = 0; i < filenames.length; i++)

				{
					System.out.println(BGdlg.getFilterPath() +"\\"+ filenames[i]);
					list.add(BGdlg.getFilterPath() +"\\"+ filenames[i]);
					
					
				}
				// list.s
				// BGmapdataText.get

			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}

		});
		
	    Button removeButton = new Button(container, SWT.NONE);
		removeButton.setText("删除");
	
		removeButton.setBounds(329, 359, 74, 25);
		removeButton.addSelectionListener(new SelectionListener(){

			public void widgetSelected(SelectionEvent e) {
				
				 	list.remove(list.getSelectionIndices());
				
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});

		final Button workbutton = new Button(container, SWT.NONE);
		workbutton.setText("选择路径");
		workbutton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				Shell shell = new Shell();
				DirectoryDialog dlg1 = new DirectoryDialog(shell);
				dlg1.setText("目录");
				dlg1.setMessage("请选择一个目录");
				dir = dlg1.open();
				if (dir != null){
					System.out.println(dir);
				workText.setText(dir);}

			else{
				workText.setText("");
			}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}

		});
		workbutton.setBounds(394, 75, 90, 20);



		final Label label_7 = new Label(container, SWT.NONE);
		label_7.setText("定级地图范围路径");
		label_7.setBounds(13, 44, 106, 20);

		final Button rangebutton = new Button(container, SWT.NONE);
		rangebutton.setBounds(393, 38, 90, 20);
		rangebutton.setText("选择路径");
		rangebutton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				Shell shell = new Shell();
				FileDialog dlg = new FileDialog(shell, SWT.None );
				dlg.setFilterExtensions(new String[] { "*.shp" });
				String filename = dlg.open();
				
				dlg.setText("打开文件");

			
				if (filename != null){
					System.out.println(filename);
				rangeText.setText(filename);

			}else{
				rangeText.setText("");
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}

		});

		list = new List(container, SWT.BORDER|SWT.V_SCROLL|SWT.MULTI);
		list.setBounds(121, 218, 365, 133);

		final Button barrierButton = new Button(container, SWT.NONE);
		barrierButton.setText("选择路径");
		barrierButton.setBounds(395, 185, 90, 20);

		final Label label = new Label(container, SWT.NONE);
		label.setText("阻隔层路径");
		label.setBounds(13, 188, 89, 21);

		barrier_text = new Text(container, SWT.BORDER);
		barrier_text.setBounds(121, 185, 262, 20);
		barrierButton.addSelectionListener(new SelectionListener(){

			public void widgetSelected(SelectionEvent e) {
				Shell shell = new Shell();
				FileDialog dlg = new FileDialog(shell, SWT.None );
				dlg.setFilterExtensions(new String[] { "*.shp" });
				String filename = dlg.open();
				
				dlg.setText("打开文件");

			
				if (filename != null){
					System.out.println(filename);
					barrier_text.setText(filename);

			}else{
				barrier_text.setText("");
				}
			
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}});
		

		

		//
		return area;
	}

	/**
	 * Create contents of the button bar
	 * 
	 * @param parent
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		finishbutton = createButton(parent, IDialogConstants.OK_ID, "确定", true);
		

		createButton(parent, IDialogConstants.CANCEL_ID, "取消", false);
	}

	/**
	 * Return the initial size of the dialog
	 */
	protected Point getInitialSize() {
		return new Point(500, 529);
	}

	protected void buttonPressed(int buttonId) {
		
		if (buttonId == IDialogConstants.OK_ID) {
			
			try {
				if(!workText.getText().equals("")&&!temporaryText.getText().equals("")&&!unitText.getText().equals("")&&!nameText.getText().equals("")){
					
					save();
					face.getCesui().resetUI(face.getCescore().getCEManager().getProject());
					 face.getCescore().resetAEControls(true);
					super.buttonPressed(buttonId);
					
				    }
				else{
					Shell shell=new Shell();
					MessageBox messageBox=new MessageBox(shell);
					messageBox.setMessage("请填写信息");
					messageBox.open();
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			}
			
			return;
		}
		super.buttonPressed(buttonId);

	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("新建项目");
	}

	private void save() throws IOException, Exception {
		ClassifyProject p=face.getCescore().getCEManager().createProject();
	
		p.setBgMap(new BGCMap(list.getItems()));
        
		double cell;
		cell=Double.parseDouble(unitText.getText());
		
		p.setCellSize(cell);

        
        if(!rangeText.getText().equals("")){
		p.setClassifyRange(new RangeCMap(rangeText.getText()));}
        
		
		p.setProjectName(nameText.getText());
		p.setTempDirectory(temporaryText.getText());
	
	
		p.setWorkspace(workText.getText());
		if(!barrier_text.getText().equals("")){
			p.setObstruct(new ObstructCMap(barrier_text.getText()));
		}

	}
}
