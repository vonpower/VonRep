package ynugis.ui.confDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.esri.arcgis.beans.map.MapBean;
import com.esri.arcgis.carto.FeatureLayer;
import com.esri.arcgis.carto.ILayer;
import com.esri.arcgis.controls.MapControl;
import com.esri.arcgis.datasourcesfile.ShapefileWorkspaceFactory;
import com.esri.arcgis.geodatabase.ICursor;
import com.esri.arcgis.geodatabase.IFields;
import com.esri.arcgis.geodatabase.IRow;
import com.esri.arcgis.geodatabase.ITable;
import com.esri.arcgis.geodatabase.IWorkspace;
import com.esri.arcgis.geodatabase.IWorkspaceEdit;
import com.esri.arcgis.geodatabase.IWorkspaceEditProxy;
import com.esri.arcgis.geodatabase.QueryFilter;
import com.esri.arcgis.geodatabase.TableSort;
import com.esri.arcgis.geodatabase.esriFieldType;
import com.esri.arcgis.geometry.IGeometry;
import com.esri.arcgis.geometry.IGeometryProxy;
import com.esri.arcgis.geometry.esriGeometryType;

public class PropDialog extends ApplicationWindow {
	private static int ALL_SELED=0;
	private static int ASK_SELED=1;
	
	private MapBean mapControl;
	private ITable fTable;
	private QueryFilter queryFilter;
	private String[] clauses;
	private String[] fieldStrs;
	private int nowSeled;
	
	private Text queryT;
	private Button queryedB;
	private Button queryBAsk;
	private Button queryBAll;
	private TableViewer tv;
	private DataModel data;
	public PropDialog(Shell parent,MapBean mapControl2,ILayer layer) {
		super(parent);
		this.setBlockOnOpen(true);
		this.mapControl=mapControl2;
		this.setPrivate(layer);
//		this.initTest();
	}

	protected Control createContents(Composite parent) {
		Composite content = new Composite(parent, SWT.NONE);
		{
			GridLayout contentLayout = new GridLayout();
			contentLayout.numColumns = 5;
			contentLayout.marginTop = 20;
			contentLayout.marginLeft = 20;
			contentLayout.marginRight = 20;
			contentLayout.marginBottom = 20;
			contentLayout.horizontalSpacing = 8;
			contentLayout.verticalSpacing = 10;
			content.setLayout(contentLayout);
		}
		Label queryL=new Label(content,SWT.NONE);
		{
			queryL.setText("当前查询条件");
		}
		queryT=new Text(content,SWT.NONE);
		{
			queryT.setEditable(false);
			GridData gd=new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan=1;
//			gd.minimumWidth=70;
			queryT.setLayoutData(gd);
			queryT.setText("FID>=0");
		}
		queryedB=new Button(content,SWT.NONE);
		{
			queryedB.setText("当前查询");
			queryedB.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e) {
					int bak=nowSeled;
					try {
						nowSeled = ASK_SELED;
						queryT.setText(clauses[nowSeled]);
						queryFilter.setWhereClause(clauses[nowSeled]);
						ICursor cursor = fTable.ITable_search(queryFilter,
								false);
						data=new DataModel();
						data.setMember(query(0, cursor, false));
						tv.setInput(data.getMember());
					} catch (Exception ex) {
						ex.printStackTrace();
						nowSeled = bak;
						queryT.setText(clauses[nowSeled]);
						try {
							queryFilter.setWhereClause(clauses[nowSeled]);
						}  catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}});
		}
		queryBAll=new Button(content,SWT.NONE);
		{
			queryBAll.setText("选择全部");
			queryBAll.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e) {
					int bak=nowSeled;
					try {
						nowSeled = ALL_SELED;
						queryT.setText(clauses[nowSeled]);
						queryFilter.setWhereClause(clauses[nowSeled]);
						ICursor cursor = fTable.ITable_search(queryFilter,
								false);
						data=new DataModel();
						data.setMember(query(0, cursor, false));
						tv.setInput(data.getMember());
					} catch (Exception ex) {
						ex.printStackTrace();
						nowSeled = bak;
						queryT.setText(clauses[nowSeled]);
						try {
							queryFilter.setWhereClause(clauses[nowSeled]);
						}  catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}});
		}
		queryBAsk=new Button(content,SWT.NONE);
		{
			queryBAsk.setText("新建查询");
			queryBAsk.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e) {
					SelectionClauseDialog selection=new SelectionClauseDialog(new Shell(),fieldStrs);
					if(selection.open()==Window.OK){
						String sql=selection.getSql();
						if(!sql.equals("")){
							int bak=nowSeled;
							try {
								nowSeled = ASK_SELED;
								clauses[nowSeled]=sql;
								queryT.setText(clauses[nowSeled]);
								queryFilter.setWhereClause(clauses[nowSeled]);
								ICursor cursor = fTable.ITable_search(queryFilter,
										false);
								data=new DataModel();
								data.setMember(query(0, cursor, false));
								tv.setInput(data.getMember());
							} catch (Exception ex) {
								ex.printStackTrace();
								nowSeled = bak;
								queryT.setText(clauses[nowSeled]);
								try {
									queryFilter.setWhereClause(clauses[nowSeled]);
								}  catch (Exception e1) {
									e1.printStackTrace();
								}
							}
						}
					};
				}});
		}
		
		Composite tableCom=new Composite(content,SWT.NONE);
		{
			GridData gd=new GridData(GridData.FILL_BOTH);
			gd.minimumHeight=200;
			gd.minimumWidth=500;
			gd.horizontalSpan=4;
			tableCom.setLayoutData(gd);
			FillLayout fl=new FillLayout();
			tableCom.setLayout(fl);
		}
		tv=new TableViewer(tableCom,SWT.MULTI|SWT.BORDER|SWT.FULL_SELECTION|SWT.V_SCROLL|SWT.H_SCROLL);
		{
			Table table = tv.getTable();
			table.setHeaderVisible(true);
			table.setLinesVisible(true);
			this.setTable(table, new TableLayout());
			tv.setContentProvider(new TvcProvider());
			tv.setLabelProvider(new TvlProvider());
			ICursor cursor=null;
			try {
				cursor = this.fTable.ITable_search(this.queryFilter, false);
				this.data=new DataModel();
				this.data.setMember(cursor);
//				tv.setInput(cursor);
				tv.setInput(this.data.getMember());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return content;
	}
	
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("属性表");
	}

	protected Point getInitialSize() {
		return new Point(800, 400);
	}

	private void setPrivate(ILayer seledLayer){
		try {
			nowSeled=0;
			clauses=new String[2];
			clauses[0]="FID>=0";
			clauses[1]="FID>=0";
		    queryFilter=new QueryFilter();
		    queryFilter.setWhereClause(clauses[nowSeled]);
		    
			
			ILayer layer = seledLayer;
			FeatureLayer featureLayer = new FeatureLayer(layer);
			ITable featureTable=featureLayer.getDisplayTable();
			this.fTable = featureTable;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private ICursor query(int col,ICursor data,boolean isAsc){
		try {
			String name = fTable.getFields().getField(col).getName();
			TableSort tableSort=new TableSort();
			tableSort.setTableByRef(this.fTable);
			tableSort.setCursorByRef(data);
			tableSort.setFields(name);
			tableSort.setAscending(name, isAsc);
			tableSort.sort(null);
			ICursor cursor = tableSort.getRows();
			return cursor;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private void setTable(Table table,TableLayout layout){
		table.setLayout(layout);
		int fieldCt=0;
		IFields fields=null;
		try {
			fields=this.fTable.getFields();
			fieldCt=fields.getFieldCount();
			String[] colProp=new String[fieldCt];
			CellEditor[] cellEditor=new CellEditor[fieldCt];
			boolean[] modify=new boolean[fieldCt];
			final int[] valueType=new int[fieldCt];
			fieldStrs=new String[fieldCt];
			String name="";
			TableColumn col;
			int fieldType;
			for(int i=0;i<fieldCt;i++){
				colProp[i]=new Integer(i).toString();
				
				
				name=fields.getField(i).getName();
				fieldStrs[i]=name;
				fieldType=fields.getField(i).getType();
				valueType[i]=fieldType;
				switch(fieldType){
				case esriFieldType.esriFieldTypeString :{
					cellEditor[i]=new TextCellEditor(table);
					modify[i]=true;
					break;
				}
				case esriFieldType.esriFieldTypeSmallInteger :{
					cellEditor[i]=new TextCellEditor(table);
					Text text=(Text)cellEditor[i].getControl();
					text.addVerifyListener(new ValueVerify());
					modify[i]=true;
					break;
				}
				case esriFieldType.esriFieldTypeDouble :{
					cellEditor[i]=new TextCellEditor(table);
					Text text=(Text)cellEditor[i].getControl();
					text.addVerifyListener(new ValueVerify());
					modify[i]=true;
					break;
				}
				case esriFieldType.esriFieldTypeInteger :{
					cellEditor[i]=new TextCellEditor(table);
					Text text=(Text)cellEditor[i].getControl();
					text.addVerifyListener(new ValueVerify());
					modify[i]=true;
					break;
				}
				default :{
					cellEditor[i]=null;
					modify[i]=false;
					break;
				}
				}
				
				layout.addColumnData(new ColumnWeightData(20));
				
				final int seledCol=i;
				col=new TableColumn(table,SWT.NONE);
				col.setText(name);
				col.setWidth(30);
				col.addSelectionListener(new SelectionAdapter(){
					boolean isAscent=true;
					public void widgetSelected(SelectionEvent e) {
						isAscent=!isAscent;
						tv.setSorter(new TvSorter(isAscent,seledCol,valueType));
					}});
			}
//			for (int i = 0; i < fieldCt; i++) {
//				table.getColumn(0).pack();
//			}
			table.pack();
			tv.setColumnProperties(colProp);
			tv.setCellEditors(cellEditor);
			tv.setCellModifier(new CellModifier(modify,valueType,this.fTable));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class ValueVerify implements VerifyListener{

		public void verifyText(VerifyEvent e) {
			e.doit="0123456789.".indexOf(e.text)>=0;
		}
		
	}
	private class CellModifier implements ICellModifier{
		private IWorkspaceEdit edit;
		private boolean[] canModify;
		private int[] valueType;
		private ITable table;
		private int oid=-1;
		public CellModifier(boolean[] modify,int[] type,ITable table){
			this.canModify=modify;
			this.valueType=type;
			this.table=table;
			for(int i=0;i<this.valueType.length;i++){
				if(this.valueType[i]==esriFieldType.esriFieldTypeOID){
					this.oid=i;
				}
			}
			this.setEdit();
		}

		public boolean canModify(Object element, String property) {
			int col=new Integer(property).intValue();
			return canModify[col];
		}

		public Object getValue(Object element, String property) {
			
			return "";
		}

		public void modify(Object element, String property, Object value) {
			if(value.toString().equals("")||this.oid<0)return;
			try {
				edit.startEditing(true);
				edit.startEditOperation();
				TableItem item=(TableItem)element;
				String[] column = (String[]) item.getData();
				
				int col=new Integer(property).intValue();
				column[col]=value.toString();
				QueryFilter qf=new QueryFilter();
				qf.setWhereClause("FID="+column[oid]);
				ICursor cursor=table.update(qf,false);
				IRow row=cursor.nextRow();
				row.setValue(col,value);
				row.store();
				edit.stopEditOperation();
				edit.stopEditing(true);
				tv.update(item.getData(),null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		private void setEdit(){
			try {
				File tempFile = new File(".");
				String tempPath = tempFile.getAbsolutePath();
				ShapefileWorkspaceFactory factory = new ShapefileWorkspaceFactory();
				IWorkspace space = factory.openFromFile(tempPath, 0);
				this.edit = new IWorkspaceEditProxy(space);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private class TvcProvider implements IStructuredContentProvider{

		public Object[] getElements(Object input) {
			if(input instanceof List){
				List data=(List)input;
				return data.toArray();
			}
			return null;
		}

		public void dispose() {
			
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

		}
		
	}
	private class TvlProvider implements ITableLabelProvider{

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int column) {
			String[] col=(String[])element;
			return col[column];
		}

		public void addListener(ILabelProviderListener listener) {
			
		}

		public void dispose() {
			
		}

		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {
			
		}
		
	}
	private class TvSorter extends ViewerSorter{
		private boolean isAscent;
		private int column;
		private int[] valueType;
		public TvSorter(boolean isAsc,int col,int[] type){
			this.isAscent=isAsc;
			this.column=col;
			this.valueType=type;
		}
		public int compare(Viewer viewer, Object e1, Object e2) {
			if(e1 instanceof String[]&&e2 instanceof String[]){
				String[] ss1=(String[])e1;
				String[] ss2=(String[])e2;
				String s1=ss1[this.column];
				String s2=ss2[this.column];
				int fieldType=0;
				try {
					fieldType=this.valueType[column];
					switch(fieldType){
					case esriFieldType.esriFieldTypeString :{
						return this.compare(s1,s2);
					}
					case esriFieldType.esriFieldTypeDouble :{
						Double d1=new Double(s1);
						Double d2=new Double(s2);
						return this.compare(d1,d2);
					}
					case esriFieldType.esriFieldTypeInteger :{
						Double d1=new Double(s1);
						Double d2=new Double(s2);
						return this.compare(d1,d2);
					}
					case esriFieldType.esriFieldTypeOID :{
						Double d1=new Double(s1);
						Double d2=new Double(s2);
						return this.compare(d1,d2);
					}
					case esriFieldType.esriFieldTypeRaster :{
						Double d1=new Double(s1);
						Double d2=new Double(s2);
						return this.compare(d1,d2);
					}
					case esriFieldType.esriFieldTypeSmallInteger :{
						Double d1=new Double(s1);
						Double d2=new Double(s2);
						return this.compare(d1,d2);
					}
					default:return 0;
					}
				}  catch (Exception e) {
					e.printStackTrace();
				}
				return 0;
			}
			return 0;
		}

		private int compare(Double d1,Double d2){
			if(isAscent){
				return d1.compareTo(d2);
			}else{
				return d2.compareTo(d1);
			}
		}
		private int compare(String s1,String s2){
			if(isAscent){
				return s1.compareTo(s2);
			}else{
				return s2.compareTo(s1);
			}
		}
	}
	private class DataModel{
		private List member;
		public DataModel(){
			member=new ArrayList();
		}
		public void setMember(ICursor c){
			ICursor cursor=c;
			IRow row=null;
			String[] col=null;
			try {
				
				int geoid=-1;
				String geotype="";
				IFields fields=cursor.getFields();
				
				int ct=fields.getFieldCount();
				for(int i=0;i<ct;i++){
					if(fields.getField(i).getType()==esriFieldType.esriFieldTypeGeometry){
						geoid=i;
						break;
					}
				}
				row=cursor.nextRow();
				if (geoid>=0) {
					IGeometry geometry=new IGeometryProxy(row.getValue(geoid));
					int gType=geometry.getGeometryType();
					switch(gType){
					case esriGeometryType.esriGeometryPoint:{
						geotype="Point";
						break;
					}
					case esriGeometryType.esriGeometryMultipoint:{
						geotype="Multipoint";
						break;
					}
					case esriGeometryType.esriGeometryLine:{
						geotype="Line";
						break;
					}
					case esriGeometryType.esriGeometryCircularArc:{
						geotype="CircularArc";
						break;
					}
					case esriGeometryType.esriGeometryEllipticArc:{
						geotype="EllipticArc";
						break;
					}
					case esriGeometryType.esriGeometryBezier3Curve:{
						geotype="Bezier3Curve";
						break;
					}
					case esriGeometryType.esriGeometryPath:{
						geotype="Path";
						break;
					}
					case esriGeometryType.esriGeometryPolyline:{
						geotype="Polyline";
						break;
					}
					case esriGeometryType.esriGeometryRing:{
						geotype="Ring";
						break;
					}
					case esriGeometryType.esriGeometryPolygon:{
						geotype="Polygon";
						break;
					}
					case esriGeometryType.esriGeometryEnvelope:{
						geotype="Envelope";
						break;
					}
					case esriGeometryType.esriGeometryAny:{
						geotype="Any";
						break;
					}
					case esriGeometryType.esriGeometryBag:{
						geotype="Bag";
						break;
					}
					//uncompleted
					default:{
						geotype="unknown";
						break;
					}
					}
				}
				while(row!=null){
//					rowList.add(row);
					col=new String[ct];
					for(int i=0;i<ct;i++){
						if(i==geoid){
							col[i]=geotype;
						}else{
							col[i]=row.getValue(i).toString();
						}
					}
					this.member.add(col);
					row=cursor.nextRow();
				}
				System.out.println("cursor to list");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		public List getMember(){
			return member;
		}
	}
}

