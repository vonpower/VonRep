/**
 * @author yddy,create date 2003-11-7 
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.confDialog;

import java.util.HashMap;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import ynugis.ffsystem.FFItem;
import ynugis.ffsystem.FFTable;
import ynugis.ui.initial.GUI;

public class FFTDialogHelper {
	
 	private boolean withWeight;
 	private HashMap tree2FFTable;
	public FFTDialogHelper(){
		this.withWeight=false;
	}
	public FFTDialogHelper(boolean with){
		this.withWeight=with;
	}
	
	
	public FFTDialogHelper(boolean with,HashMap map){
		this(with);
		this.tree2FFTable=map;
		this.tree2FFTable.clear();
	}
	public FFTable fillFFTable(Shell shell,FFTable table){
		FFTable fftable=table;
		if(fftable.name==null){
			NameDialog nameDialog=new NameDialog(shell,fftable);
			int nd=nameDialog.open();
			if(nd!=Window.OK){
				return null;
			}
		}
		if(fftable.equals("")){
			return null;
		}
		FFTDialog fftDialog=new FFTDialog(shell,fftable);
		int fd=fftDialog.open();
		if(fd!=Window.OK){
			return null;
		}
		SaveFFTDialog saveFFT=new SaveFFTDialog(shell,fftable);
		saveFFT.open();
		return fftable;
	}
	public void growTree(Tree fftree,FFTable fftable){
//		Object[] cldsObj=fftable.childCollection.toArray();
//		int cldLen=cldsObj.length;
		fftree.removeAll();
		TreeItem rootItem=new TreeItem(fftree,SWT.NONE);
		rootItem.setText(fftable.name);
		rootItem.setData(fftable);
		/*if(cldLen>0){
			for(int i=0;i<cldLen;i++){
				FFItem item=(FFItem)cldsObj[i];
				TreeItem cldItem=new TreeItem(rootItem,SWT.NONE);
				cldItem.setText(item.name);
				cldItem.setData(item);
				walkThroughFFTable(cldItem,item);
			}
			rootItem.setExpanded(true);
		}*/
		walkThroughFFTable(rootItem,fftable);
		if(tree2FFTable!=null)tree2FFTable.put(rootItem,fftree);
	}
	public boolean isWithWeight(){
		return this.withWeight;
	}
	private void walkThroughFFTable(TreeItem parentItem,FFItem item){
		Object[] cldsObj=item.childCollection.toArray();
		int cldLen=cldsObj.length;
		if(cldLen>0){
			TreeItem cldItem;
			for(int i=0;i<cldLen;i++){
				FFItem ffitem=(FFItem)cldsObj[i];
//				TreeItem cldItem=addTreeItem(parentItem,ffitem);
				if(this.withWeight){
					cldItem=addTreeItemWithWeight(parentItem,ffitem);
				}else{
					cldItem=addTreeItem(parentItem,ffitem);
				}
				walkThroughFFTable(cldItem,ffitem);
//				parentItem.setExpanded(true);
			}
//			parentItem.setExpanded(true);
		}
	}
	private TreeItem addTreeItem(TreeItem parentItem,FFItem item){
		TreeItem cldItem=new TreeItem(parentItem,SWT.NONE);
		cldItem.setText(item.name);
		cldItem.setData(item);
		parentItem.setExpanded(true);
		if(tree2FFTable!=null)tree2FFTable.put(cldItem,item);
		return cldItem;
	}
	private TreeItem addTreeItemWithWeight(TreeItem parentItem,FFItem item){
		TreeItem cldItem=new TreeItem(parentItem,SWT.NONE);
		cldItem.setText(item.name+GUI.PREFIX+item.weight+GUI.SUFFIX);
		cldItem.setData(item);
		parentItem.setExpanded(true);
		if(tree2FFTable!=null)tree2FFTable.put(cldItem,item);
		return cldItem;
	}
}
