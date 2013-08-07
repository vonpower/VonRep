package ynugis.ui.confDialog;

import java.io.IOException;
import java.net.UnknownHostException;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import ynugis.classifyBusiness.CEManager;
import ynugis.classifyBusiness.ClassifyProject;
import ynugis.classifyBusiness.classifyItem.BGCMap;
import ynugis.classifyBusiness.classifyItem.ObstructCMap;
import ynugis.classifyBusiness.classifyItem.RangeCMap;
import ynugis.ui.face.MainFace;


public class propertyDialog extends TitleAreaDialog {

	private Text barrierText;
	private List list;
	private Text rangeText;
	private Text unitText;
	private Label nameLabel;
	private Label workLabel;
	private Shell newShell;
	private String[] filenames;
	private int i;
    private FileDialog BGdlg;
    private String filename;
    private MainFace face;
	
	/**
	 * Create the dialog
	 * @param face
	 * @throws Exception 
	 * @throws IOException 
	 */
	public propertyDialog(MainFace face)  {
		super(face.getShell());
		this.face=face;
		
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	
	protected Control createDialogArea(Composite parent) {
		
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		
       
		TabFolder tabFolder=new TabFolder(container,SWT.NONE);
		tabFolder.setBounds(18, 9,337,359);

	

		
		

		TabItem tabItem_2= new TabItem(tabFolder, SWT.NONE);
		tabItem_2.setText("摘要");

		final Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tabItem_2.setControl(composite_1);

		final Group group_3 = new Group(composite_1, SWT.NONE);
		group_3.setBounds(0, 0, 330, 83);

		final Label label_9 = new Label(group_3, SWT.NONE);
		label_9.setText("项目名称：");
		label_9.setBounds(5, 17, 67, 20);

		final Label label_10 = new Label(group_3, SWT.NONE);
		label_10.setText("工作路径：");
		label_10.setBounds(5, 54, 61, 25);

		nameLabel = new Label(group_3, SWT.NONE);
		nameLabel.setBounds(91, 15, 229, 20);

		workLabel = new Label(group_3, SWT.NONE);
		workLabel.setBounds(91, 52, 229, 25);

		final Group group_4 = new Group(composite_1, SWT.NONE);
		group_4.setBounds(-3, 84, 332, 252);

		final Label label_15 = new Label(group_4, SWT.NONE);
		label_15.setText("背景地图数据文件：");
		label_15.setBounds(5, 146, 111, 20);

		unitText = new Text(group_4, SWT.BORDER);
		unitText.setBounds(120, 19, 201, 25);

		final Label label_16 = new Label(group_4, SWT.NONE);
		label_16.setText("定级单元大小（m）：");
		label_16.setBounds(5, 22, 114, 20);

		final Label label = new Label(group_4, SWT.NONE);
		label.setText("定级地图范围路径：");
		label.setBounds(5, 69, 110, 20);

		rangeText = new Text(group_4, SWT.BORDER);
		rangeText.setBounds(120, 62, 163, 25);

		list = new List(group_4, SWT.V_SCROLL|SWT.H_SCROLL|SWT.BORDER);
		list.setBounds(120, 140, 201, 70);
			
			
		
		
		

		final Button deletebutton = new Button(group_4, SWT.NONE);
		deletebutton.setText("删除");
		deletebutton.setBounds(261, 222, 62, 20);
		deletebutton.addSelectionListener(new SelectionListener(){

			public void widgetSelected(SelectionEvent e) {
				list.remove(list.getSelectionIndices());
				
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}});

		final Button button = new Button(group_4, SWT.NONE);
		button.setText("浏览");
		button.setBounds(289, 60, 34, 28);
		button.addSelectionListener(new SelectionListener(){

			public void widgetSelected(SelectionEvent e) {
				Shell shell = new Shell();
				FileDialog dlg = new FileDialog(shell, SWT.None );
				dlg.setFilterExtensions(new String[] { "*.shp" });
				filename = dlg.open();
				
				dlg.setText("打开文件");
				if (filename != null){
				rangeText.setText(filename);}

			//}else{
			//	rangeText.setText("");
			//	}
			

			}

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}});

		final Button addbutton = new Button(group_4, SWT.NONE);
		addbutton.setBounds(189, 222,62, 20);
		addbutton.setText("添加");
        addbutton.addSelectionListener(new SelectionListener(){

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
				
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}}); 

		final Label label_15_1 = new Label(group_4, SWT.NONE);
		label_15_1.setBounds(5, 104, 101, 20);
		label_15_1.setText("阻隔层路径：");

		barrierText = new Text(group_4, SWT.BORDER);
		barrierText.setBounds(120, 100, 163, 25);

		final Button barrierButton = new Button(group_4, SWT.NONE);
		barrierButton.setBounds(289, 101, 34, 28);
		barrierButton.setText("浏览");
		barrierButton.addSelectionListener(new SelectionListener(){

			public void widgetSelected(SelectionEvent e) {
				Shell shell = new Shell();
				FileDialog dlg = new FileDialog(shell, SWT.None );
				dlg.setFilterExtensions(new String[] { "*.shp" });
				filename = dlg.open();
				
				dlg.setText("打开文件");
				if (filename != null){
				barrierText.setText(filename);}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
			
		});
		

		try {
			initial();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    
		
	    
		return area;
	}

	/**
	 * Create contents of the button bar
	 * @param parent
	 */

	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID,"确定",
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				"取消", false);
	}

	/**
	 * Return the initial size of the dialog
	 * @throws IOException 
	 * @throws IOException 
	 */
protected void buttonPressed(int buttonId) {
		
		if (buttonId == IDialogConstants.OK_ID) {
			
			
					try {
						if(!workLabel.getText().equals("")&&!unitText.getText().equals("")&&list.getItemCount()!=0){
						save();
						//super.buttonPressed(buttonId);
						}
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}if(face.getCescore().getCEManager().getCurrentProjectCount()!=0){
					face.getCesui().resetUI(face.getCescore().getCEManager().getProject());
					 try {
						face.getCescore().resetAEControls(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
					}
					

	}
		super.buttonPressed(buttonId);
}


	protected Point getInitialSize() {
		return new Point(375,555);
	}
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("查看");
		
		
		
	}
	private void initial() throws IOException, Exception {
		
		CEManager p=face.getCescore().getCEManager();
		if (p.getCurrentProjectCount()>0){
			ClassifyProject u=p.getProject(0);
			nameLabel.setText(u.getProjectName());
			workLabel.setText(u.getWorkspace());
			String cell;
			cell=String.valueOf(u.getCellSize());
			unitText.setText(cell);
			if(u.getClassifyRange()!=null){
			rangeText.setText(u.getClassifyRange().getSrcFile());}
            list.setItems(u.getBgMap().getSrcFiles());
            if(u.getObstruct()!=null){
            	barrierText.setText(u.getObstruct().getSrcFile());
            }
        
		
		}
	}
	private void save() throws UnknownHostException, IOException{
		try{
		}catch(Exception ee)
		{
			ee.printStackTrace();
		}
		if(face.getCescore().getCEManager().getCurrentProjectCount()==0){
			return;
		}else{
		ClassifyProject p=face.getCescore().getCEManager().getProject();
		p.setProjectName(nameLabel.getText());
		p.setWorkspace(workLabel.getText());

		double cell;
		cell=Double.parseDouble(unitText.getText());
		p.setCellSize(cell);
		if(!rangeText.getText().equals("")){
			p.setClassifyRange(new RangeCMap(rangeText.getText()));}
		if(!barrierText.getText().equals("")){
			p.setObstruct(new ObstructCMap(barrierText.getText()));
		}
		
		p.setBgMap(new BGCMap(list.getItems()));}
	}
}

		


