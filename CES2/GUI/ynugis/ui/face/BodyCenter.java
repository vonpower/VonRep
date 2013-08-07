package ynugis.ui.face;

import java.awt.Frame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import ynugis.application.CESCORE;

import com.esri.arcgis.carto.IActiveView;

public class BodyCenter extends Composite {
	private CESCORE cescore;
	private Frame mapFrame;
	private Frame pageFrame;
	private TabFolder tabFolder;

	public BodyCenter(Composite parent) {
		super(parent, SWT.NONE);
		setSize(new Point(300, 200));
		FillLayout layout=new FillLayout(SWT.FILL);
		layout.marginHeight=0;
		layout.marginWidth=0;
		setLayout(layout);
		createContent(this);
	}
	public BodyCenter(MainFace face){
		super(face.getBodyForm(), SWT.NONE);
		cescore=face.getCescore();
		setSize(new Point(300, 200));
		FillLayout layout=new FillLayout(SWT.FILL);
		layout.marginHeight=0;
		layout.marginWidth=0;
		setLayout(layout);
		createContent(this);
	}

	public Frame getMapFrame(){
		return mapFrame;
	}
	public Frame getPageFrame(){
		return pageFrame;
	}
	private void createContent(Composite p){
		tabFolder=new TabFolder(p,SWT.NONE);
		createMap(tabFolder);
		createPage(tabFolder);
		tabFolder.addMouseListener(new MouseAdapter(){
			public void mouseDoubleClick(MouseEvent e){
				System.out.println("mouse double clicked");
			}
		});
		tabFolder.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				switch(tabFolder.getSelectionIndex()){
				case 0:setMapControl();break;
				case 1:setPageControl();break;
				}
				System.out.println("widgetselected");
			}
		});
	}
	private void createMap(TabFolder tab){
		TabItem mapTabItem=new TabItem(tab,SWT.NONE);
		mapTabItem.setText("数据视图");
		Composite mapAid=new Composite(tab,SWT.EMBEDDED);
		mapFrame=SWT_AWT.new_Frame(mapAid);
		mapTabItem.setControl(mapAid);
//		GV.setFrame(mapFrame);
	}
	private void createPage(TabFolder tab){
		TabItem pageTabItem=new TabItem(tab,SWT.NONE);
		pageTabItem.setText("图层视图");
		Composite pageAid=new Composite(tab,SWT.EMBEDDED);
		pageFrame=SWT_AWT.new_Frame(pageAid);
		pageTabItem.setControl(pageAid);
	}
	private void setMapControl(){
		System.out.println("map setted");
		IActiveView activeView=null;
		try {
			activeView=cescore.getPageLayoutControl().getActiveView();
			int layNum=activeView.getFocusMap().getLayerCount();
			if(layNum>0){
				cescore.PageToMap(cescore.getPageLayoutControl(),cescore.getMapControl());
			}
			System.out.println(activeView.getFocusMap().getLayerCount());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				cescore.buddyAEControl(true);
				cescore.configureMapCommands(cescore.getToolbarControlView());
				cescore.configureEditCommands(cescore.getToolbarControlEdit());
				cescore.getMapControl().layout();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private void setPageControl(){
		System.out.println("page setted");
		IActiveView activeView=null;
		try {
			activeView=cescore.getMapControl().getActiveView();
			int layNum=activeView.getFocusMap().getLayerCount();
			if(layNum>0){
				cescore.MapToPage(cescore.getMapControl(),cescore.getPageLayoutControl());
			}
			System.out.println(activeView.getFocusMap().getLayerCount());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				cescore.buddyAEControl(false);
				cescore.getToolbarControlEdit().removeAll();
				cescore.configurePageLayoutCommands(cescore.getToolbarControlView());
				cescore.getPageLayoutControl().layout();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
