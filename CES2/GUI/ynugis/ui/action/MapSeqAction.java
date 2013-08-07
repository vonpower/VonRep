/**
 * @author yddy,create date 2003-12-4 
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.action;

import java.io.IOException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import ynugis.ui.face.MainFace;
import ynugis.ui.image.ImageProvider;

import com.esri.arcgis.beans.map.MapBean;
import com.esri.arcgis.controls.MapControl;

public class MapSeqAction extends Action {
	private MainFace face;

	public MapSeqAction(MainFace m) {
		face=m;
		this.setText("排序");
		this.setToolTipText("改变图层顺序");
		ImageRegistry imageRegistry=face.getCesui().getImageRegistry();
		if(imageRegistry!=null){
			this.setImageDescriptor(imageRegistry.getDescriptor(ImageProvider.ICON_SEQ));
		}
	}
	public void run(){
		Shell s=new Shell();
		s.setSize(400,400);
		s.setLayout(new FillLayout());
//		new SeqDialog(s,face.getCescore().getMapControl()).open();
		new SeqShell(face.getCescore().getMapControl()).open();
	}
	private class SeqShell{
		private MapBean mc;
		private List layerList;
		private Label infoLabel;
		public SeqShell(MapBean bean){
			mc=bean;
		}
		public void open(){
			final Shell s=new Shell();
			s.setSize(400,400);
			s.setText("图层管理");
			s.setImage(new Image(null, "icons/seq.gif"));
			s.setLayout(new FillLayout());
			
			Composite left=new Composite(s,SWT.NONE);
			FillLayout leftLay=new FillLayout();
			leftLay.marginHeight=20;
			leftLay.marginWidth=20;
			leftLay.type=SWT.VERTICAL;
			left.setLayout(leftLay);
			
			Composite r=new Composite(s,SWT.NONE);
			GridLayout rLay=new GridLayout();
			rLay.numColumns=1;
			rLay.marginTop=20;
			rLay.marginLeft=2;
			rLay.verticalSpacing=20;
			rLay.makeColumnsEqualWidth=true;
			r.setLayout(rLay);
			
			layerList=new List(left,SWT.V_SCROLL|SWT.BORDER);
			
			Button upBtn=new Button(r,SWT.NONE);
			GridData gd=new GridData();
			gd.verticalSpan=1;
			upBtn.setLayoutData(gd);
			upBtn.setText("上移");
			upBtn.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent event){
					layerUp();
				}
			});
			Button downBtn=new Button(r,SWT.NONE);
			GridData gd2=new GridData();
			gd2.verticalSpan=1;
			downBtn.setLayoutData(gd2);
			downBtn.setText("下移");
			downBtn.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent event){
					layerDown();
				}
			});
			Button topBtn=new Button(r,SWT.NONE);
			GridData gd3=new GridData();
			gd3.verticalSpan=1;
			topBtn.setLayoutData(gd3);
			topBtn.setText("上移至顶");
			topBtn.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent event){
					layerUp2Top();
				}
			});
			Button btmBtn=new Button(r,SWT.NONE);
			GridData gd4=new GridData();
			gd4.verticalSpan=1;
			btmBtn.setLayoutData(gd4);
			btmBtn.setText("下移至底");
			btmBtn.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent event){
					layerDown2Bottom();
				}
			});
			Button delBtn=new Button(r,SWT.NONE);
			GridData gd5=new GridData();
			gd5.verticalSpan=1;
			delBtn.setLayoutData(gd5);
			delBtn.setText("删除");
			delBtn.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent event){
					layerDel();
				}
			});
			infoLabel=new Label(r,SWT.NONE);
			
			initializeLayerList();
			infoLabel.setText("现有图层数："+layerList.getItemCount());
			
			Button closeBtn=new Button(r,SWT.NONE);
			GridData gd6=new GridData();
			gd6.verticalSpan=1;
			gd6.verticalIndent=50;
			closeBtn.setLayoutData(gd6);
			closeBtn.setText("关闭");
			closeBtn.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent event){
					s.close();
				}
			});
			s.open();
		}
		private void initializeLayerList(){
			int count=0;
			try {
				count=mc.getLayerCount();
				System.out.println("num of layer:"+count);
				if(count>0){
					for(int i=0;i<count;i++){
						layerList.add(mc.getLayer(i).getName());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private void layerUp(){
			if(layerList.getSelectionCount()==0){
				return;
			}
			int index=layerList.getSelectionIndex();
			String from;
			String to;
			if(index>0){
				from=layerList.getItem(index);
				to=layerList.getItem(index-1);
				infoLabel.setText("移动图层："+from);
				try {
					mc.moveLayerTo(index,index-1);
					layerList.setItem(index-1,from);
					layerList.setItem(index,to);
					layerList.setSelection(index-1);
					mc.layout();
				} catch (IOException e) {
					infoLabel.setText("移动失败");
					e.printStackTrace();
				}
			}
		}
		
		private void layerDown(){
			if(layerList.getSelectionCount()==0){
				return;
			}
			int index=layerList.getSelectionIndex();
			String from;
			String to;
			if(index<layerList.getItemCount()-1){
				from=layerList.getItem(index);
				to=layerList.getItem(index+1);
				infoLabel.setText("移动图层："+from);
				try {
					mc.moveLayerTo(index,index+1);
					layerList.setItem(index+1,from);
					layerList.setItem(index,to);
					layerList.setSelection(index+1);
					mc.layout();
				} catch (IOException e) {
					infoLabel.setText("移动失败");
					e.printStackTrace();
				}
			}
		}
		
		private void layerUp2Top(){
			if(layerList.getSelectionCount()==0){
				return;
			}
			int index=layerList.getSelectionIndex();
			String from;
			String to;
			if(index>0){
				from=layerList.getItem(index);
				to=layerList.getItem(0);
				infoLabel.setText("移动图层："+from);
				try {
					mc.moveLayerTo(index,0);
					layerList.setItem(0,from);
					layerList.setItem(index,to);
					layerList.setSelection(0);
					mc.layout();
				} catch (IOException e) {
					infoLabel.setText("移动失败");
					e.printStackTrace();
				}
			}
		}
		
		private void layerDown2Bottom(){
			if(layerList.getSelectionCount()==0){
				return;
			}
			int index=layerList.getSelectionIndex();
			int bottom=layerList.getItemCount()-1;
			String from;
			String to;
			if(index<bottom){
				from=layerList.getItem(index);
				to=layerList.getItem(bottom);
				infoLabel.setText("移动图层："+from);
				try {
					mc.moveLayerTo(index,bottom);
					layerList.setItem(bottom,from);
					layerList.setItem(index,to);
					layerList.setSelection(bottom);
					mc.layout();
				} catch (IOException e) {
					infoLabel.setText("移动失败");
					e.printStackTrace();
				}
			}
		}
		
		private void layerDel(){
			if(layerList.getSelectionCount()==0){
				return;
			}
			int index=layerList.getSelectionIndex();
			try {
				mc.deleteLayer(index);
				layerList.remove(index);
				infoLabel.setText("现有图层数："+layerList.getItemCount());
			} catch (Exception e) {
				infoLabel.setText("删除操作失败");
				e.printStackTrace();
			}
		}
		
	}
	private class SeqDialog extends Dialog{
		private MapControl mc;
		private List layerList;
		private Label infoLabel;
		public SeqDialog(Shell shell,MapControl mapControl){
			super(shell);
			mc=mapControl;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
		 */
		protected Control createDialogArea(Composite parent) {
			GridLayout contentLayout=new GridLayout();
			contentLayout.numColumns=2;
			contentLayout.marginTop=10;
			contentLayout.marginLeft=10;
			contentLayout.verticalSpacing=10;
			contentLayout.horizontalSpacing=15;
			Composite content=new Composite(parent,SWT.NONE);
//			content.setLayout(contentLayout);
			FillLayout fl=new FillLayout(SWT.HORIZONTAL);
			fl.marginHeight=20;
			fl.marginWidth=20;
			content.setLayout(fl);
			
			Composite left=new Composite(content,SWT.NONE);
			left.setLayout(new FillLayout());
			
//			Label leftLabel=new Label(content,SWT.NONE);
//			leftLabel.setText("图层列表");
//			Label rightLabel=new Label(content,SWT.NONE);
//			rightLabel.setText("操作");
			
			Composite right=new Composite(content,SWT.NONE);
			GridLayout grid=new GridLayout();
			grid.numColumns=1;
			right.setLayout(grid);
			
//			GridData listData=new GridData(GridData.FILL_BOTH);
//			listData.horizontalSpan=1;
//			listData.verticalSpan=5;
//			listData.minimumWidth=200;
//			listData.minimumHeight=100;
//			listCom.setLayoutData(listData);
//			listCom.setLayout(new FillLayout(SWT.VERTICAL));
			
			layerList=new List(left,SWT.V_SCROLL|SWT.FLAT);
//			layerList.setLayoutData(listData);
//			layerList.setSize(200,300);
			
			Button upBtn=new Button(right,SWT.NONE);
			GridData gd=new GridData();
			gd.verticalSpan=1;
			upBtn.setLayoutData(gd);
			upBtn.setText("上移");
			upBtn.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent event){
					layerUp();
				}
			});
			Button downBtn=new Button(right,SWT.NONE);
			GridData gd2=new GridData();
			gd2.verticalSpan=1;
			downBtn.setLayoutData(gd2);
			downBtn.setText("下移");
			downBtn.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent event){
					layerDown();
				}
			});
			Button topBtn=new Button(right,SWT.NONE);
			GridData gd3=new GridData();
			gd3.verticalSpan=1;
			topBtn.setLayoutData(gd3);
			topBtn.setText("上移至顶");
			topBtn.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent event){
					layerUp2Top();
				}
			});
			Button btmBtn=new Button(right,SWT.NONE);
			GridData gd4=new GridData();
			gd4.verticalSpan=1;
			btmBtn.setLayoutData(gd4);
			btmBtn.setText("下移至底");
			btmBtn.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent event){
					layerDown2Bottom();
				}
			});
			Button delBtn=new Button(right,SWT.NONE);
			GridData gd5=new GridData();
			gd5.verticalSpan=1;
			gd5.minimumHeight=23;
			delBtn.setLayoutData(gd5);
			delBtn.setText("删除");
			delBtn.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent event){
					layerDel();
				}
			});
			infoLabel=new Label(right,SWT.NONE);
//			GridData infoData=new GridData();
//			infoData.horizontalSpan=2;
//			infoLabel.setLayoutData(infoData);
			
			initializeLayerList();
			infoLabel.setText("现有图层数："+layerList.getItemCount());
			
			return content;
		}
		
		
		
		/* (non-Javadoc)
		 * @see org.eclipse.jface.dialogs.Dialog#createButton(org.eclipse.swt.widgets.Composite, int, java.lang.String, boolean)
		 */
		protected Button createButton(Composite parent, int id, String label, boolean defaultButton) {
			return null;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.dialogs.Dialog#initializeBounds()
		 */
		protected void initializeBounds() {
			super.createButton((Composite)getButtonBar(),IDialogConstants.OK_ID,"关闭",true);
			super.initializeBounds();
		}

		private void initializeLayerList(){
			int count=0;
			try {
				count=mc.getLayerCount();
				System.out.println("num of layer:"+count);
				if(count>0){
					for(int i=0;i<count;i++){
						layerList.add(mc.getLayer(i).getName());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private void layerUp(){
			if(layerList.getSelectionCount()==0){
				return;
			}
			int index=layerList.getSelectionIndex();
			String from;
			String to;
			if(index>0){
				from=layerList.getItem(index);
				to=layerList.getItem(index-1);
				infoLabel.setText("移动图层："+from);
				try {
					mc.moveLayerTo(index,index-1);
					layerList.setItem(index-1,from);
					layerList.setItem(index,to);
					layerList.setSelection(index-1);
					mc.layout();
				} catch (IOException e) {
					infoLabel.setText("移动失败");
					e.printStackTrace();
				}
			}
		}
		
		private void layerDown(){
			if(layerList.getSelectionCount()==0){
				return;
			}
			int index=layerList.getSelectionIndex();
			String from;
			String to;
			if(index<layerList.getItemCount()-1){
				from=layerList.getItem(index);
				to=layerList.getItem(index+1);
				infoLabel.setText("移动图层："+from);
				try {
					mc.moveLayerTo(index,index+1);
					layerList.setItem(index+1,from);
					layerList.setItem(index,to);
					layerList.setSelection(index+1);
					mc.layout();
				} catch (IOException e) {
					infoLabel.setText("移动失败");
					e.printStackTrace();
				}
			}
		}
		
		private void layerUp2Top(){
			if(layerList.getSelectionCount()==0){
				return;
			}
			int index=layerList.getSelectionIndex();
			String from;
			String to;
			if(index>0){
				from=layerList.getItem(index);
				to=layerList.getItem(0);
				infoLabel.setText("移动图层："+from);
				try {
					mc.moveLayerTo(index,0);
					layerList.setItem(0,from);
					layerList.setItem(index,to);
					layerList.setSelection(0);
					mc.layout();
				} catch (IOException e) {
					infoLabel.setText("移动失败");
					e.printStackTrace();
				}
			}
		}
		
		private void layerDown2Bottom(){
			if(layerList.getSelectionCount()==0){
				return;
			}
			int index=layerList.getSelectionIndex();
			int bottom=layerList.getItemCount()-1;
			String from;
			String to;
			if(index<bottom){
				from=layerList.getItem(index);
				to=layerList.getItem(bottom);
				infoLabel.setText("移动图层："+from);
				try {
					mc.moveLayerTo(index,bottom);
					layerList.setItem(bottom,from);
					layerList.setItem(index,to);
					layerList.setSelection(bottom);
					mc.layout();
				} catch (IOException e) {
					infoLabel.setText("移动失败");
					e.printStackTrace();
				}
			}
		}
		
		private void layerDel(){
			if(layerList.getSelectionCount()==0){
				return;
			}
			int index=layerList.getSelectionIndex();
			try {
				mc.deleteLayer(index);
				layerList.remove(index);
				infoLabel.setText("现有图层数："+layerList.getItemCount());
			} catch (Exception e) {
				infoLabel.setText("删除操作失败");
				e.printStackTrace();
			}
		}
		
	}
}
