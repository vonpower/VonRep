package ynugis.ui.face;

import java.awt.Frame;

import org.eclipse.jface.action.CoolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ArmEvent;
import org.eclipse.swt.events.ArmListener;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import ynugis.application.CESCORE;
import ynugis.application.CESUI;
import ynugis.ui.action.AboutAction;
import ynugis.ui.action.EditCellAction;
import ynugis.ui.action.EditRangeAction;
import ynugis.ui.action.EstCostApproachAction;
import ynugis.ui.action.EstInfoAction;
import ynugis.ui.action.EstWinRevertAction;
import ynugis.ui.action.ExitAction;
import ynugis.ui.action.ExportAction;
import ynugis.ui.action.FFTableAHPAction;
import ynugis.ui.action.FFTableBicoAction;
import ynugis.ui.action.FFTableDelphiAction;
import ynugis.ui.action.FFTableNewAction;
import ynugis.ui.action.HelpAction;
import ynugis.ui.action.LabelAction;
import ynugis.ui.action.MapAddAction;
import ynugis.ui.action.MapAttributeAction;
import ynugis.ui.action.MapGisAction;
import ynugis.ui.action.MapNewAction;
import ynugis.ui.action.MapOpenAction;
import ynugis.ui.action.MapSeqAction;
import ynugis.ui.action.MapTransparenceAction;
import ynugis.ui.action.PrintPageLayoutAction;
import ynugis.ui.action.ProjectCloseAction;
import ynugis.ui.action.ProjectNewAction;
import ynugis.ui.action.ProjectOpenAction;
import ynugis.ui.action.ProjectPropertyAction;
import ynugis.ui.action.ProjectSaveAction;
import ynugis.ui.action.ProjectSaveAsAction;
import ynugis.ui.action.RendererAction;
import ynugis.ui.action.TransferAction;

public class MainFace extends ApplicationWindow {
	private final int estMenuId=4;
	private final int estMenuLen=3;
	private CESCORE cescore;

	private CESUI cesui;

	private SashForm bodyForm;

	private BodyLeft bodyLeft;

	private BodyCenter bodyCenter;

	private BodyRight bodyRight;

	private Frame toolbarViewFrame;

	private Frame toolbarEditFrame;

	private ExitAction exit;

	private MapOpenAction mapOpen;

	private MapNewAction mapNew;

	private MapAddAction mapAdd;

	private MapSeqAction mapSeq;

	private MapTransparenceAction mapTrs;
	
	private MapAttributeAction mapAttr;

	private PrintPageLayoutAction mapPrint;

	private RendererAction mapRender;

	private LabelAction mapLabel;

	private EditRangeAction editRge;

	private EditCellAction editCell;

	private FFTableNewAction newTable;

	private FFTableDelphiAction delphiTable;

	private FFTableAHPAction ahpTable;

	private FFTableBicoAction bicoTable;

	private TransferAction fileTrans;

	private ExportAction toolDir;

	private MapGisAction toolMapgis;

	private ProjectNewAction proNew;

	private ProjectOpenAction proOpen;

	private ProjectSaveAction proSave;

	private ProjectSaveAsAction proSav2;

	private ProjectPropertyAction proInfo;

	private ProjectCloseAction proClose;

	// private EstPubinfoAction estInfo;

	private EstWinRevertAction estBus;

	private EstCostApproachAction estInd;

	
	
	private EstInfoAction estInfo;

	public MainFace(CESCORE core) throws Exception {
		super(null);
		cescore = core;
		cesui = new CESUI();
		exit = new ExitAction(this);

		mapOpen = new MapOpenAction(this);
		mapNew = new MapNewAction(this);
		mapAdd = new MapAddAction(this);
		mapSeq = new MapSeqAction(this);
		mapTrs = new MapTransparenceAction(this);
		mapPrint = new PrintPageLayoutAction(this);
		mapRender = new RendererAction(this);
		mapLabel = new LabelAction(this);
		mapAttr=new MapAttributeAction(this);

		editRge = new EditRangeAction(this);
		editCell = new EditCellAction(this);

		newTable = new FFTableNewAction(this);
		delphiTable = new FFTableDelphiAction(this);
		ahpTable = new FFTableAHPAction(this);
		bicoTable = new FFTableBicoAction(this);

		fileTrans = new TransferAction(this);
		toolDir = new ExportAction(this);
		toolMapgis = new MapGisAction(this);

		proNew = new ProjectNewAction(this);
		proOpen = new ProjectOpenAction(this);
		proSave = new ProjectSaveAction(this);
		proSav2 = new ProjectSaveAsAction(this);
		proInfo = new ProjectPropertyAction(this);
		proClose = new ProjectCloseAction(this);

		// estInfo=new EstPubinfoAction(this);
		estBus = new EstWinRevertAction(this);
		estInd = new EstCostApproachAction(this);
		estInfo=new EstInfoAction(this);

		this.addMenuBar();
		this.addCoolBar(SWT.NONE);
		this.addStatusLine();
	}

	protected MenuManager createMenuManager() {
		MenuManager mainMenu = new MenuManager("");
		MenuManager fileMenu = new MenuManager("文件");
		
		mainMenu.add(fileMenu);
		fileMenu.add(mapOpen);
		fileMenu.add(mapNew);
		fileMenu.add(mapAdd);
		fileMenu.add(exit);
		
		MenuManager editMenu = new MenuManager("图层操作");
		mainMenu.add(editMenu);
		editMenu.add(editRge);
		editMenu.add(editCell);
		editMenu.add(mapSeq);
		editMenu.add(mapTrs);
		editMenu.add(mapPrint);
		editMenu.add(mapRender);
		editMenu.add(mapLabel);
		editMenu.add(mapAttr);
		MenuManager toolMenu = new MenuManager("工具");
		mainMenu.add(toolMenu);

		toolMenu.add(fileTrans);
		toolMenu.add(toolDir);
		toolMenu.add(toolMapgis);
		MenuManager tableMenu = new MenuManager("因素因子");
		toolMenu.add(tableMenu);
		{
			tableMenu.add(newTable);
			tableMenu.add(new Separator());
			tableMenu.add(delphiTable);
			tableMenu.add(ahpTable);
			tableMenu.add(bicoTable);
		}
		MenuManager projMenu = new MenuManager("项目");
		mainMenu.add(projMenu);
		projMenu.add(proNew);
		projMenu.add(proOpen);
		projMenu.add(new Separator());
		projMenu.add(proSave);
		projMenu.add(proSav2);
		projMenu.add(proClose);

		projMenu.add(new Separator());

		projMenu.add(proInfo);

		MenuManager estMenu = new MenuManager("估价系统");
		mainMenu.add(estMenu);
		//estMenu.add(estBus);
		estMenu.add(estInd);
		estMenu.add(new Separator());
		estMenu.add(estInfo);
		MenuManager helpMenu = new MenuManager("帮助");
		mainMenu.add(helpMenu);
		helpMenu.add(new HelpAction(this));
		helpMenu.add(new AboutAction(this));
		return mainMenu;
	}

	protected CoolBarManager createCoolBarManager(int style) {
		CoolBarManager coolBar = new CoolBarManager(style);
		ToolBarManager toolBar = new ToolBarManager(SWT.FLAT | SWT.WRAP);
		toolBar.add(mapOpen);
		toolBar.add(mapNew);
		toolBar.add(mapAdd);
		toolBar.add(new Separator());
		toolBar.add(mapSeq);
		toolBar.add(mapTrs);
		toolBar.add(mapPrint);
		toolBar.add(mapRender);
		toolBar.add(mapLabel);
		toolBar.add(mapAttr);
		toolBar.add(new Separator());
		// toolBar.add(delphiTable);
		toolBar.add(newTable);
		toolBar.add(fileTrans);
		toolBar.add(toolDir);
		toolBar.add(editCell);
		toolBar.add(new Separator());
		toolBar.add(proNew);
		toolBar.add(proOpen);
		toolBar.add(proInfo);

		// toolBar.add(exit);
		coolBar.add(toolBar);
		return coolBar;
	}

	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLine = new StatusLineManager();
		return statusLine;
	}

	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout());
		bodyForm = new SashForm(container, SWT.NONE | SWT.HORIZONTAL);
		GridLayout sashFormGrid = new GridLayout();
		sashFormGrid.numColumns = 3;
		bodyForm.setLayout(sashFormGrid);
		// reconstruct
		bodyLeft = new BodyLeft(this);
		bodyLeft.setVisible(false);
		bodyCenter = new BodyCenter(this);
		bodyRight = new BodyRight(this);
		// for test
		bodyForm.setWeights(new int[] { 4, 14, 2 });
		// reconstruct
		{
			CoolBar parentBar = this.getCoolBarManager().getControl();
			Composite toolAid = new Composite(parentBar, SWT.EMBEDDED);
			CoolItem toolItem = new CoolItem(parentBar, SWT.NONE);
			toolItem.setControl(toolAid);
			toolbarViewFrame = SWT_AWT.new_Frame(toolAid);
			GridLayout toolbarEditLayout = new GridLayout();
			toolbarEditLayout.numColumns = 2;
			Composite toolbarEditCom = new Composite(parentBar, SWT.EMBEDDED);
			toolbarEditCom.setLayout(new FillLayout(SWT.HORIZONTAL));

			CoolItem toolbarEditItem = new CoolItem(parentBar, SWT.NONE);
			toolbarEditItem.setControl(toolbarEditCom);
			toolbarEditFrame = SWT_AWT.new_Frame(toolbarEditCom);
			toolItem.setMinimumSize(600, 21);
			toolbarEditItem.setMinimumSize(300, 21);
			this.getCoolBarManager().getControl().addControlListener(
					new ControlAdapter() {
						public void controlResized(ControlEvent e) {
							MainFace.this.getShell().layout();
						}
					});
//			tip = new Shell(SWT.ON_TOP|SWT.TOOL);
//			tip.setLayout(new FillLayout());
//			tip.setSize(100, 50);
//
//			Text tipText = new Text(tip, SWT.MULTI | SWT.WRAP);
//			tipText.setText("hoho");
//			tip.open();
//			tip.setVisible(false);
//			
//			getShell().forceFocus();
			final Menu estMenu=this.getMenuBarManager().getMenu().getItem(estMenuId).getMenu();
			estMenu.addMenuListener(new MenuListener(){
				public void menuHidden(MenuEvent e) {
					getStatusLineManager().setMessage("");
				}
				public void menuShown(MenuEvent e) {
				}});
			
			for(int i=0;i<estMenuLen;i++){
				final MenuItem item=estMenu.getItem(i);
				final int id=i;
				item.addArmListener(new ArmListener(){
					public void widgetArmed(ArmEvent e) {
						getStatusLineManager().setMessage(estInfo.info[id]);
					}});
				
			}
		}// reconstructed
		if (cescore != null) {
			cesui.initializeUI(bodyForm, bodyLeft, bodyCenter, bodyRight,
					toolbarViewFrame, toolbarEditFrame);
			cesui.fullFrameWithAEControls(cescore);
			cesui.getImageRegistry();
		} else {
			arcengineFailure(bodyForm.getShell());
		}
		return bodyForm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.ApplicationWindow#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		System.out.println(Display.getCurrent());
		Display display = Display.getCurrent();
		int sh = display.getBounds().height;
		int sw = display.getBounds().width;
		int offsetH = (int) ((sh - 480) / 2);
		int offsetW = (int) ((sw - 640) / 2);
		shell.setBounds(offsetW, offsetH, 640, 480);
		shell.setText("土地定级估价系统");
		shell.setFocus();
		shell.setImage(new Image(null, "icons/logo.gif"));
	}

	/**
	 * @return Returns the bodyForm.
	 */
	public SashForm getBodyForm() {
		return bodyForm;
	}

	/**
	 * @return Returns the bodyLeft.
	 */
	public BodyLeft getBodyLeft() {
		return bodyLeft;
	}

	/**
	 * @return Returns the bodyCenter.
	 */
	public BodyCenter getBodyCenter() {
		return bodyCenter;
	}

	/**
	 * @return Returns the bodyRight.
	 */
	public BodyRight getBodyRight() {
		return bodyRight;
	}

	/**
	 * @return Returns the toolbarEditFrame.
	 */
	public Frame getToolbarEditFrame() {
		return toolbarEditFrame;
	}

	/**
	 * @return Returns the toolbarViewFrame.
	 */
	public Frame getToolbarViewFrame() {
		return toolbarViewFrame;
	}

	/**
	 * @return Returns the cescore.
	 */
	public CESCORE getCescore() {
		return cescore;
	}

	/**
	 * @return Returns the cesui.
	 */
	public CESUI getCesui() {
		return cesui;
	}

	private void arcengineFailure(Shell shell) {
		MessageDialog.openError(shell, "初始化控件",
				"ArcEngine初始化失败,请检查是否被正确安装或License是否有效");
	}

}
