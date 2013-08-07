package ynugis.ui.face;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Tree;

import ynugis.application.CESCORE;
import ynugis.classifyBusiness.ClassifyProject;
import ynugis.ffsystem.FFTable;
import ynugis.ui.action.FFPActionGroup;
import ynugis.ui.confDialog.FFTDialogHelper;
import ynugis.ui.confDialog.LoadFFTDialog;


public class BodyLeft extends Composite {
	private final int PANEL_GRID=GridData.FILL_BOTH;

	private CESCORE cescore;
	private Group treeGrowGroup;
	private Tree fftree;
	private List treeList;

	public BodyLeft(Composite parent) {
		super(parent, SWT.NONE);
		setSize(new Point(300, 200));
		GridLayout layout = new GridLayout();
		layout.marginTop = 5;
		layout.marginLeft = 0;
		layout.verticalSpacing = 20;
		layout.numColumns = 2;
		setLayout(layout);
		createContent(this);
	}
	public BodyLeft(MainFace face){
		super(face.getBodyForm(), SWT.NONE);
		cescore=face.getCescore();
//		setSize(new Point(300, 200));
		GridLayout layout = new GridLayout();
		layout.marginTop = 5;
		layout.marginLeft = 0;
		layout.verticalSpacing = 20;
		layout.numColumns = 2;
		setLayout(layout);
		createContent(this);
	}

	private void createContent(final Composite p) {
		Group treeListGroup=new Group(p,SWT.NONE);
		{
			GridData grid=new GridData(PANEL_GRID);
//			grid.minimumHeight=100;
//			grid.minimumWidth=PANEL_WIDTH-3;
			grid.horizontalSpan=2;
			treeListGroup.setLayoutData(grid);
			treeListGroup.setText("因素因子列表");
			
			GridLayout gridLay=new GridLayout();
			gridLay.marginTop=5;
			gridLay.marginRight=5;
			gridLay.marginBottom=5;
			gridLay.marginLeft=5;
			gridLay.horizontalSpacing=10;
			gridLay.numColumns=2;
			treeListGroup.setLayout(gridLay);
		}
		Button addTreeBtn=new Button(treeListGroup,SWT.NONE);
		{
			GridData grid=new GridData(GridData.CENTER);
			addTreeBtn.setLayoutData(grid);
			addTreeBtn.setText("添加");
			addTreeBtn.addSelectionListener(new SelectionAdapter(){
				
				public void widgetSelected(SelectionEvent e){
					LoadFFTDialog loadDialog=new LoadFFTDialog(getShell());
					int re=loadDialog.open();
					if(re!=Window.OK){
						return;
					}
					FFTable[] fftables=loadDialog.getFFTables();
					if(fftables==null){
						return;
					}
					
					if(cescore.getCEManager().getCurrentProjectCount()==0){
						return;
					}
					ClassifyProject project=cescore.getCEManager().getProject();
					for(int i=0;i<fftables.length;i++){
						if(fftables[i].name!=null){
							String ffname=fftables[i].name;
							if(treeList.getData(ffname)==null){
								treeList.add(ffname);
								treeList.setData(ffname,fftables[i]);
								project.addFFT(fftables[i]);
							}
						}
					}
				}
			});
		}
		Button delTreeBtn=new Button(treeListGroup,SWT.NONE);
		{
			GridData grid=new GridData(GridData.CENTER);
			delTreeBtn.setLayoutData(grid);
			delTreeBtn.setText("删除");
			delTreeBtn.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e){
					String[] selected=treeList.getSelection();
					if(selected.length==1){
						if(cescore.getCEManager().getCurrentProjectCount()==0){
							return;
						}
						ClassifyProject project=cescore.getCEManager().getProject();
						String ffname=selected[0];
						if(treeList.getData(ffname)!=null){
							project.deleteFFT((FFTable)treeList.getData(ffname));
							treeList.setData(ffname,null);
							treeList.remove(ffname);
							fftree.removeAll();
							
						}
					}
				}
			});
		}
		treeList=new List(treeListGroup,SWT.SINGLE|SWT.BORDER);
		{
			GridData grid=new GridData(GridData.FILL_BOTH);
			grid.horizontalSpan=2;
//			grid.minimumWidth=PANEL_WIDTH-3;
//			grid.minimumHeight=80;
			treeList.setLayoutData(grid);
			treeList.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e){
					String[] selected=treeList.getSelection();
					int selectedNum=selected.length;
					if(selectedNum==1){
						Object fftableObj=treeList.getData(selected[0]);
						if(fftableObj instanceof FFTable){
							if(fftree!=null){
								fftree.removeAll();
							}
							new FFTDialogHelper().growTree(fftree,(FFTable)fftableObj);
						}
					}
				}
			});
		}
		treeGrowGroup=new Group(p,SWT.NONE);
		{
			GridData grid=new GridData(PANEL_GRID);
//			grid.minimumHeight=150;
//			grid.minimumWidth=PANEL_WIDTH;
			grid.horizontalSpan=2;
			treeGrowGroup.setLayoutData(grid);
			treeGrowGroup.setText("因素因子树属性");
			
			GridLayout gridLay=new GridLayout();
			gridLay.marginTop=5;
			gridLay.marginRight=5;
			gridLay.marginBottom=5;
			gridLay.marginLeft=5;
			gridLay.numColumns=1;
			treeGrowGroup.setLayout(gridLay);
		}
		fftree=new Tree(treeGrowGroup,SWT.BORDER);
		{
			GridData grid=new GridData(GridData.FILL_BOTH);
//			grid.minimumHeight=140;
//			grid.minimumWidth=PANEL_WIDTH;
			fftree.setLayoutData(grid);
		}
		FFPActionGroup ffpActionGroup=new FFPActionGroup(getShell(),cescore,fftree);
		ffpActionGroup.contextMenu();
	}
	public void initializeText(FFTable[] fftables){
		if(fftables==null){
			return;
		}
		String[] names=treeList.getItems();
		if(names.length>0){
			for(int i=0;i<names.length;i++){
				treeList.setData(names[i],null);
			}
			treeList.removeAll();
		}
		for(int i=0;i<fftables.length;i++){
			if(fftables[i].name==null){
				continue;
			}
			if(fftables[i].name.equals("")){
				continue;
			}
			treeList.add(fftables[i].name);
			treeList.setData(fftables[i].name,fftables[i]);
		}
		fftree.removeAll();
	}
	
	public void initialNew(){
		String item;
		for(int i=0;i<treeList.getItemCount();i++){
			item=treeList.getItem(i);
			if(treeList.getData(item)!=null){
				treeList.setData(item,null);
			}
		}
		treeList.removeAll();
		fftree.removeAll();
	}
}
