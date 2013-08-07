/**
 * @author yddy,create date 2003-11-8 
 * @Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.confDialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import ynugis.ffsystem.FFSystem;
import ynugis.ffsystem.FFTable;


public class LoadFFTDialog extends Dialog {
	private final String FFT_FILE_EXT="*.fft";
	private FFTable[] fftables;
	private List backFFTList;
	private List useFFTList;
	private Button dirBtn;
	private Button fileRdo;
	private Button modelRdo;
	private Button newRdo;

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		Composite content=new Composite(parent,SWT.NONE);
		{
			GridLayout contentLay=new GridLayout();
			contentLay.marginTop=10;
			contentLay.marginRight=10;
			contentLay.marginBottom=10;
			contentLay.marginLeft=10;
			contentLay.numColumns=3;
			contentLay.verticalSpacing=10;
			content.setLayout(contentLay);
		}
		
		fileRdo=new Button(content,SWT.RADIO|SWT.NONE);
		{
			GridData grid=new GridData(GridData.FILL_HORIZONTAL);
			grid.horizontalSpan=2;
			fileRdo.setLayoutData(grid);
			fileRdo.setText("   从文件载入");
			fileRdo.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e){
					if(fileRdo.getSelection()){
						dirBtn.setEnabled(true);
//						backFFTList.removeAll();
						String[] items=backFFTList.getItems();
						if(items.length==0){
							return;
						}
						if(items.length>0){
							for(int i=0;i<items.length;i++){
								if(backFFTList.getData(items[i])!=null){
									backFFTList.setData(items[i],null);
								}
							}
							backFFTList.removeAll();
						}
					}else{
						dirBtn.setEnabled(false);
					}
				}
			});
		}
		dirBtn=new Button(content,SWT.NONE);
		{
			GridData grid=new GridData(GridData.END);
			grid.horizontalSpan=1;
			dirBtn.setLayoutData(grid);
			dirBtn.setText("浏览");
			dirBtn.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e){
					FileDialog xmlDialog=new FileDialog(getShell(),SWT.MULTI);
					xmlDialog.setFilterExtensions(new String[]{FFT_FILE_EXT});
					xmlDialog.setFilterNames(new String[]{"因素因子文件("+FFT_FILE_EXT+")"});
					xmlDialog.setText("装载因素因子树");
					String dir=xmlDialog.open();
					if(dir==null){
						return;
					}
					dir=xmlDialog.getFilterPath();
					String[] names=xmlDialog.getFileNames();
//					System.out.println("fileName:"+names[0]);
					for(int i=0;i<names.length;i++){
						String xmlDir=dir+"\\"+names[i];
						FFTable fftable=null;
						try {
							fftable=FFSystem.createFFT(xmlDir);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
//						System.out.println("table:"+i+fftable);
						if(fftable!=null){
							String ffname=fftable.name;
//							System.out.println("tableName:"+i+ffname);
							if(ffname!=null){
								if(useFFTList.getData(ffname)==null){
									if(backFFTList.getData(ffname)==null){
										backFFTList.add(ffname);
										backFFTList.setData(ffname,fftable);
									}
								}
							}
						}
					}
				}
			});
		}
		modelRdo=new Button(content,SWT.RADIO|SWT.NONE);
		{
			GridData grid=new GridData(GridData.FILL_HORIZONTAL);
			grid.horizontalSpan=3;
			modelRdo.setLayoutData(grid);
			modelRdo.setText("   从模板载入");
			modelRdo.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e){
					if(modelRdo.getSelection()){
						dirBtn.setEnabled(false);
//						backFFTList.removeAll();
						String[] items=backFFTList.getItems();
						if(items.length>0){
							for(int i=0;i<items.length;i++){
								if(backFFTList.getData(items[i])!=null){
									backFFTList.setData(items[i],null);
								}
							}
							backFFTList.removeAll();
						}
						//load fftable from fixed dir
						FFTable[] tables=FFSystem.getFFTableTemplates();
						if(tables!=null){
							if(tables.length>0){
								for(int i=0;i<tables.length;i++){
									FFTable fftable=tables[i];
									String ffname=fftable.name;
									if(ffname!=null){
										if(useFFTList.getData(ffname)==null){
											if(backFFTList.getData(ffname)==null){
												backFFTList.add(ffname);
												backFFTList.setData(ffname,fftable);
											}
										}
									}
								}
							}
						}
					}else{
						dirBtn.setEnabled(true);
					}
				}
			});
		}
		newRdo=new Button(content,SWT.RADIO|SWT.NONE);
		{
			GridData grid=new GridData(GridData.FILL_HORIZONTAL);
			grid.horizontalSpan=3;
			newRdo.setLayoutData(grid);
			newRdo.setText("   新建");
			newRdo.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e){
					if(newRdo.getSelection()){
						dirBtn.setEnabled(false);
//						backFFTList.removeAll();
						String[] items=backFFTList.getItems();
						if(items.length>0){
							for(int i=0;i<items.length;i++){
								if(backFFTList.getData(items[i])!=null){
									backFFTList.setData(items[i],null);
								}
							}
							backFFTList.removeAll();
						}
						//new a fftable with dialog
						FFTable newfftable=new FFTable();
						FFTDialogHelper dialogHelper=new FFTDialogHelper();
						dialogHelper.fillFFTable(getShell(),newfftable);
						String ffname=newfftable.name;
						if(ffname==null){
							return;
						}
						if(useFFTList.getData(ffname)==null){
							if(backFFTList.getData(ffname)==null){
								backFFTList.add(ffname);
								backFFTList.setData(ffname,newfftable);
							}
						}
					}else{
						dirBtn.setEnabled(true);
					}
				}
			});
		}
		Label leftL=new Label(content,SWT.NONE);
		{
			GridData grid=new GridData(GridData.CENTER);
			grid.horizontalAlignment=GridData.HORIZONTAL_ALIGN_CENTER;
			leftL.setText("可用因素因子树");
			leftL.setLayoutData(grid);
		}
		new Label(content,SWT.NONE);
		Label rightL=new Label(content,SWT.NONE);
		{
			GridData grid=new GridData(GridData.CENTER);
			rightL.setText("即将使用的树");
			rightL.setLayoutData(grid);
		}
		backFFTList=new List(content,SWT.MULTI|SWT.V_SCROLL|SWT.BORDER);
		{
			GridData grid=new GridData(GridData.FILL_BOTH);
			grid.horizontalSpan=1;
			grid.verticalSpan=5;
			grid.minimumWidth=100;
//			grid.minimumHeight=150;
			backFFTList.setLayoutData(grid);
		}
		Button multiRightBtn=new Button(content,SWT.NONE);
		{
			GridData grid=new GridData(GridData.CENTER);
			grid.horizontalSpan=1;
			grid.verticalSpan=1;
			grid.minimumWidth=15;
			multiRightBtn.setLayoutData(grid);
			multiRightBtn.setText(" > ");
			multiRightBtn.setToolTipText("多项右移");
			multiRightBtn.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e){
					String[] selected=backFFTList.getSelection();
					if(selected.length>0){
						moveFFTable(backFFTList,useFFTList,selected);
					}
				}
			});
		}
		useFFTList=new List(content,SWT.MULTI|SWT.V_SCROLL|SWT.BORDER);
		{
			GridData grid=new GridData(GridData.FILL_BOTH);
			grid.horizontalSpan=1;
			grid.verticalSpan=5;
			grid.minimumWidth=100;
//			grid.minimumHeight=150;
			useFFTList.setLayoutData(grid);
		}
		Button multiLeftBtn=new Button(content,SWT.NONE);
		{
			GridData grid=new GridData(GridData.CENTER);
			grid.horizontalSpan=1;
			grid.verticalSpan=1;
			grid.minimumWidth=15;
			multiLeftBtn.setLayoutData(grid);
			multiLeftBtn.setText(" < ");
			multiLeftBtn.setToolTipText("多项左移");
			multiLeftBtn.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e){
					String[] selected=useFFTList.getSelection();
					if(selected.length>0){
						moveFFTable(useFFTList,backFFTList,selected);
					}
				}
			});
		}
		Button allRightBtn=new Button(content,SWT.NONE);
		{
			GridData grid=new GridData(GridData.CENTER);
			grid.horizontalSpan=1;
			grid.verticalSpan=1;
			grid.minimumWidth=15;
			allRightBtn.setLayoutData(grid);
			allRightBtn.setText(">>");
			allRightBtn.setToolTipText("全选并右移");
			allRightBtn.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e){
					String[] selected=backFFTList.getItems();
					if(selected.length>0){
						moveFFTable(backFFTList,useFFTList,selected);
					}
				}
			});
		}
		Button allLeftBtn=new Button(content,SWT.NONE);
		{
			GridData grid=new GridData(GridData.CENTER);
			grid.horizontalSpan=1;
			grid.verticalSpan=1;
			grid.minimumWidth=15;
			allLeftBtn.setLayoutData(grid);
			allLeftBtn.setText("<<");
			allLeftBtn.setToolTipText("全选并左移");
			allLeftBtn.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e){
					String[] selected=useFFTList.getItems();
					if(selected.length>0){
						moveFFTable(useFFTList,backFFTList,selected);
					}
				}
			});
		}
		Button editBtn=new Button(content,SWT.NONE);
		{
			GridData grid=new GridData(GridData.CENTER);
			grid.horizontalSpan=1;
			grid.verticalSpan=1;
			grid.minimumWidth=15;
			editBtn.setLayoutData(grid);
			editBtn.setText("#>");
			editBtn.setToolTipText("编辑并右移");
			editBtn.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e){
					String[] selected=backFFTList.getSelection();
					if(selected.length>0){
						for(int i=0;i<selected.length;i++){
							FFTable fftable=(FFTable)backFFTList.getData(selected[i]);
							if(fftable==null){
								continue;
							}
							FFTDialog fftDialog=new FFTDialog(getShell(),fftable);
							int fd=fftDialog.open();
							if(fd!=Window.OK){
								continue;
							}
							
							boolean fftok=moveFFTable(backFFTList,useFFTList,new String[]{selected[i]});
							if(fftok){
								SaveFFTDialog saveFFT=new SaveFFTDialog(getShell(),fftable);
								saveFFT.open();
							}
						}
//						moveFFTable(useFFTList,backFFTList,selected);
					}
				}
			});
		}

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
		super.createButton((Composite)getButtonBar(),IDialogConstants.OK_ID,"确定",true);
		super.createButton((Composite)getButtonBar(),IDialogConstants.CANCEL_ID,"取消",true);
		super.initializeBounds();
	}

	public LoadFFTDialog(Shell parent) {
		super(parent);
	}

	/**
	 * @return Returns the fftables.
	 */
	public FFTable[] getFFTables() {
		return fftables;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		String[] selected=useFFTList.getItems();
		if(selected.length>0){
			fftables=new FFTable[selected.length];
			for(int i=0;i<selected.length;i++){
				Object obj=useFFTList.getData(selected[i]);
				if(obj!=null){
					fftables[i]=(FFTable)obj;
				}
			}
		}
		super.okPressed();
	}

	private boolean moveFFTable(List from,List to,String[] items){
		boolean validate=true;
		for(int i=0;i<items.length;i++){
			String ffname=items[i];
			Object obj=from.getData(ffname);
			if(obj!=null){
				FFTable table=(FFTable)obj;
				String verify=table.verifyWeight();
				if(!verify.equals("")){
					String invailedWeightString=verify;
					MessageDialog.openError(getShell(),"权重检查","[ "+invailedWeightString+" ]权重值不合法,请重新检查");
					validate=false;
					continue;
				}
				if(to.getData(ffname)==null){
					to.add(ffname);
					to.setData(ffname,obj);
					from.setData(ffname,null);
				}else{
					to.add(ffname);
				}
				from.remove(ffname);
			}
		}
		return validate;
	}
}
