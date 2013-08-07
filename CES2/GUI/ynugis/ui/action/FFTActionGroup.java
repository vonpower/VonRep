/**
 * @author yddy,create date 2003-11-3
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import ynugis.ffsystem.BasicFacet;
import ynugis.ffsystem.BasicFactor;
import ynugis.ffsystem.DerivedFacet;
import ynugis.ffsystem.FFItem;
import ynugis.ffsystem.FFTable;
import ynugis.ffsystem.FactorCatalog;
import ynugis.ui.confDialog.FFTDialog;
import ynugis.ui.confDialog.NodeDialog;

public class FFTActionGroup {
	private final int IB_ROOT=0;
	private final int IB_CATALOG=1;
	private final int IB_FACTOR=2;
	private final int IB_FACET=3;
	private final int ID_FACET=4;
	private final String SB_CATALOG="类别";
	private final String SB_FACTOR="因素";
	private final String SB_FACET="因子";
	private final String SD_FACET="派生因子";
	
	private final boolean LIMITED=true;
	
	private Shell shell;
	private Tree	fftTree;
	private FFTDialog fftDialog;
	
	private MenuManager mmL2;
	
	private insertAction insertBCG ;
	private insertAction insertBFR ;
	private insertAction insertBFE ;
	private insertAction insertDFE;
	
	private deleteAction delete;
	private propertyAction property;

	public FFTActionGroup(Tree tree,Shell myshell,FFTDialog dialog) {
		fftTree = tree;
		shell=myshell;
		fftDialog=dialog;
	}

	public void contextMenu() {
		MenuManager mm = new MenuManager();
		mmL2=new MenuManager("插入");
		
		insertBCG = new insertAction(IB_CATALOG);
		insertBFR = new insertAction(IB_FACTOR);
		insertBFE = new insertAction(IB_FACET);
		insertDFE = new insertAction(ID_FACET);
		delete=new deleteAction();
		property=new propertyAction();
		mmL2.add(insertBCG);
		mmL2.add(insertBFR);
		mmL2.add(insertBFE);
		mmL2.add(insertDFE);
		mm.add(mmL2);
		mm.add(delete);
		mm.add(property);
		
		Menu m = mm.createContextMenu(fftTree);
		fftTree.setMenu(m);
		fftTree.addSelectionListener(new fftSelectionListener());
	}

	private class fftSelectionListener extends SelectionAdapter{
		/* (non-Javadoc)
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		public void widgetSelected(SelectionEvent e) {
			System.out.println("Selection Node");
			TreeItem[] selection = fftTree.getSelection();
			int selectedNum = selection.length;
			if (selectedNum != 1) {
				setMenuEnabled(IB_ROOT);
				return;
			}
			if (selectedNum == 1) {
				
				Object nodeTypeObj=selection[0].getData();
				/*
				 * save the previous node info
				 */
				fftDialog.saveNode(nodeTypeObj);
				
				System.out.println("show node being");
				/*
				 * show the current node info
				 */
				fftDialog.showNode((FFItem)nodeTypeObj);
				
				if(nodeTypeObj instanceof FFTable){
					setMenuEnabled(IB_CATALOG);
				}else if(nodeTypeObj instanceof FactorCatalog){
					setMenuEnabled(IB_FACTOR);
				}else if(nodeTypeObj instanceof BasicFactor){
					setMenuEnabled(IB_FACET);
				}else if(nodeTypeObj instanceof BasicFacet){
					setMenuEnabled(ID_FACET);
				}else if(nodeTypeObj instanceof DerivedFacet){
					/*
					 * limited derived facet will true
					 * and all menu items will be disabled
					 */
					if(LIMITED){
						setMenuEnabled(IB_ROOT);
					}else{
						setMenuEnabled(ID_FACET);
					}
				}else{
					return;
				}
			}
		}
		private void setMenuEnabled(int index){
			insertBCG.setEnabled(false);
			insertBFR.setEnabled(false);
			insertBFE.setEnabled(false);
			insertDFE.setEnabled(false);
			delete.setEnabled(true);
			property.setEnabled(true);
			switch(index){
				case IB_ROOT:{
					break;
				}
				case IB_CATALOG:{
					insertBCG.setEnabled(true);
					delete.setEnabled(false);
					property.setEnabled(false);
					break;
				}
				case IB_FACTOR:{
					insertBFR.setEnabled(true);
					break;
				}
				case IB_FACET:{
					insertBFE.setEnabled(true);
					break;
				}
				case ID_FACET:{
					insertDFE.setEnabled(true);
					break;
				}
			}
		}
	}
	private class insertAction extends Action {
		private int type;
		public insertAction(int type) {
			this.type=type;
			switch(this.type){
				case IB_CATALOG:{
					this.setText(SB_CATALOG);
					break;
				}
				case IB_FACTOR:{
					this.setText(SB_FACTOR);
					break;
				}
				case IB_FACET:{
					this.setText(SB_FACET);
					break;
				}
				case ID_FACET:{
					this.setText(SD_FACET);
					break;
				}
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.action.Action#run()
		 */
		public void run() {
			TreeItem[] selection = fftTree.getSelection();
			if (selection.length == 1) {
				System.out.println("Node:" + selection[0].getText());
				insertNode(selection[0],this.type);
			} else {
				System.out.println("Multiselection");
			}
			super.run();
		}

		private void insertNode(TreeItem item,int insertType){
			NodeDialog addNode=null;
			FFItem ffitem=null;
			switch(insertType){
				case IB_CATALOG:{
					FactorCatalog factorCatalog=new FactorCatalog();
					ffitem=factorCatalog;
					addNode=new NodeDialog(shell,factorCatalog,false);
					break;
				}
				case IB_FACTOR:{
					BasicFactor basicFactor=new BasicFactor();
					ffitem=basicFactor;
					addNode=new NodeDialog(shell,basicFactor,false);
					break;
				}
				case IB_FACET:{
					BasicFacet basicFacet=new BasicFacet();
					ffitem=basicFacet;
					addNode=new NodeDialog(shell,basicFacet,true);
					break;
				}
				case ID_FACET:{
					DerivedFacet derivedFacet=new DerivedFacet();
					ffitem=derivedFacet;
					addNode=new NodeDialog(shell,derivedFacet,true);
					break;
				}
			}
			if(addNode==null){
				return;
			}
			int response=addNode.open();
			//ok:response==0
			//other:response==1
			if(response==1){
				return;
			}
			TreeItem newItem=new TreeItem(item,SWT.NONE);
			newItem.setText(ffitem.name);
			newItem.setData(ffitem);
			item.setExpanded(true);
			/*
			 * add node to fftable
			 */
			Object nodeTypeObj=item.getData();
			if(nodeTypeObj instanceof FFTable){
				FFTable ffTable=(FFTable)nodeTypeObj;
				FactorCatalog factorCatalog=(FactorCatalog)ffitem;
				ffTable.addFactorCatalog(factorCatalog);
			}else if(nodeTypeObj instanceof FactorCatalog){
				FactorCatalog fg=(FactorCatalog)nodeTypeObj;
				BasicFactor basicFactor=(BasicFactor)ffitem;
				fg.addBaseFactor(basicFactor);
			}else if(nodeTypeObj instanceof BasicFactor){
				BasicFactor br=(BasicFactor)nodeTypeObj;
				BasicFacet basicFacet=(BasicFacet)ffitem;
				br.addBasicFacet(basicFacet);
			}else if(nodeTypeObj instanceof BasicFacet){
				BasicFacet bt=(BasicFacet)nodeTypeObj;
				DerivedFacet derivedFacet=(DerivedFacet)ffitem;
				bt.addDerivedFacet(derivedFacet);
			}else{
				return;
			}
		}
	}
	private class deleteAction extends Action{

		public deleteAction() {
			this.setText("删除");
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.action.Action#run()
		 */
		public void run() {
			TreeItem[] selection = fftTree.getSelection();
			if (selection.length == 1) {
				TreeItem treeItem=selection[0];
				FFItem ffitem=(FFItem)treeItem.getData();
				TreeItem treeItemParent=treeItem.getParentItem();
				Object nodeObj=treeItemParent.getData();
				if(nodeObj instanceof FFTable){
					FFTable table=(FFTable)nodeObj;
					table.removeChild(ffitem);
				}else{
					FFItem item=(FFItem)nodeObj;
					item.removeChild(ffitem);
				}
				treeItem.dispose();
			} else {
				System.out.println("Multiselection");
			}
		}
	
	}
	private class propertyAction extends Action{

		public propertyAction() {
			this.setText("属性");
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.action.Action#run()
		 */
		public void run() {
			TreeItem[] selection = fftTree.getSelection();
			if (selection.length == 1) {
				TreeItem treeItem=selection[0];
				FFItem ffitem=(FFItem)treeItem.getData();
				NodeDialog propNode=null;
				if(ffitem instanceof FactorCatalog){
					propNode=new NodeDialog(shell,fftDialog,ffitem,false);
				}else if(ffitem instanceof BasicFactor){
					propNode=new NodeDialog(shell,fftDialog,ffitem,false);
				}else if(ffitem instanceof BasicFacet){
					propNode=new NodeDialog(shell,fftDialog,ffitem,true);					
				}else if(ffitem instanceof DerivedFacet){
					propNode=new NodeDialog(shell,fftDialog,ffitem,true);
				}
				if(propNode==null){
					return;
				}
				int dialog=propNode.open();
				if(dialog!=0){
					return;
				}
				treeItem.setText(ffitem.name);
			} else {
				System.out.println("Multiselection");
			}
		}
	
	}
}
