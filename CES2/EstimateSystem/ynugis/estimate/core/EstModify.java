package ynugis.estimate.core;

import java.io.File;
import java.text.DecimalFormat;

import ynugis.estimate.data.Revise;
import ynugis.estimate.data.RevisePair;

import com.esri.arcgis.datasourcesfile.ShapefileWorkspaceFactory;
import com.esri.arcgis.geodatabase.ICursor;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureWorkspace;
import com.esri.arcgis.geodatabase.IFeatureWorkspaceProxy;
import com.esri.arcgis.geodatabase.IRow;
import com.esri.arcgis.geodatabase.ISelectionSet;
import com.esri.arcgis.geodatabase.IWorkspaceProxy;
import com.esri.arcgis.geodatabase.QueryFilter;
import com.esri.arcgis.geodatabase.esriSelectionOption;
import com.esri.arcgis.geodatabase.esriSelectionType;

/**
 * @author yddy,create date 2003-1-9
 *
 * @Blog : http://yddysy.blogcn.net
 */
public class EstModify {
	private IFeatureClass featureClass;
	private String fieldRead;
	private String fieldWrite;
	private Revise revise;
	
	/**
	 * @classfor
	 * @param fcls FeatureClass that will be modified
	 * @param revise Rules for modification
	 * @param fieldW Name of the field that will be modified 
	 */
	public EstModify(IFeatureClass fcls, Revise revise,String fieldW) {
		featureClass=fcls;
		this.revise=revise;
		revise.tidy();
		this.fieldRead=revise.getReviseFieldName();
//		this.fieldRead=fieldW;
		fieldWrite=fieldW;
	}
/*public EstModify(MapControl mapControl){
	try {
		
		ShapefileWorkspaceFactory factory = new ShapefileWorkspaceFactory();
		IFeatureWorkspace space = new IFeatureWorkspaceProxy(factory
				.openFromFile("E:\\ATest", 0));
		featureClass=space.openFeatureClass("中学.shp");
		fieldRead="作用分";
		fieldWrite="test";
		ICursor cursor=getSelection(29,100,space);
		if(cursor==null){
			return;
		}
		int fieldId=cursor.findField(fieldRead);
		if(fieldId==-1){
			return;
		}
		//modify cell values in each selectionSet
		modifyCell(fieldId,cursor,0.6);
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
*/
	/**
	 * @throws Exception
	 * @usedfor Modification action,the only public method beside constructor
	 */
	public void modify()throws Exception {
		//workspace
		ShapefileWorkspaceFactory factory = new ShapefileWorkspaceFactory();
		File file = new File(".");
		String path = file.getAbsolutePath();
		IFeatureWorkspace space = new IFeatureWorkspaceProxy(factory
				.openFromFile(path.substring(path
						.lastIndexOf(File.separator)), 0));
		int pairCount=revise.getRevisePairs().length;
		//revisepair
		RevisePair pair;
		for(int i=0;i<pairCount;i++){
			pair=revise.getRevisePair(i);
			if(pair==null){
				return;
			}
			//selectionSet for each pair
			ICursor cursor=getSelection(pair.getStart(),pair.getEnd(),space);
			if(cursor==null){
				return;
			}
			int fieldId=cursor.findField(fieldRead);
			if(fieldId==-1){
				return;
			}
			//modify cell values in each selectionSet
			modifyCell(fieldId,cursor,pair.getReviseValue());
		}
	}
	
	/**
	 * @param frId The index of given revise field
	 * @param cursor The cursor of selectionSet of a pair
	 * @param ratio Revise value
	 * @throws Exception
	 * @desire getSelection
	 * @usedfor modify the value of cells in selectionSet
	 */
	private void modifyCell(int frId,ICursor cursor,double ratio)throws Exception{
		int fwId=cursor.findField(fieldWrite);
		IRow row = cursor.nextRow();
		DecimalFormat format=new DecimalFormat("#.00");
		while(row!=null){
			double readValue=Double.parseDouble(row.getValue(fwId).toString());
			String formatStr=format.format(readValue*ratio);
			Double writeValue=new Double(formatStr);
			System.out.println("modify before:"+readValue+" after:"+writeValue);
			row.setValue(fwId,writeValue);
			row.store();
//			cursor.updateRow(row);
			row=cursor.nextRow();
		}
	}
	
	/**
	 * @param start The minimum double value of the interzone
	 * @param end The Maximum double value of the interzone
	 * @param space The workspace a query need
	 * @return The cursor of selectionSet
	 * @throws Exception
	 * @desire Constructor,modify
	 * @usedfor Select the rows of the feature with specified condition
	 */
	private ICursor getSelection(double start,double end,IFeatureWorkspace space)throws Exception{
		QueryFilter filter = new QueryFilter();
		String whereClause;
		whereClause = fieldRead+">="+start+" and "+fieldRead+"<"+end;
		if(start==end){
			whereClause=fieldRead+"="+start;
		}
		filter.addField(fieldRead);
		filter.setWhereClause(whereClause);
		ISelectionSet selection = featureClass.select(filter,
				esriSelectionType.esriSelectionTypeHybrid,
				esriSelectionOption.esriSelectionOptionNormal,
				new IWorkspaceProxy(space));
		ICursor[] cursors = new ICursor[1];
		selection.search(null, false, cursors);
		return cursors[0];
	}
	/*public void testPrepare() {

		try {
			IFeatureClass fc = Utility.OpenFeatureClass("D:\\grid\\test.shp");
			QueryFilter filter = new QueryFilter();
			String fieldName = "GRIDCODE";
			String whereClause = "GRIDCODE>1 and GRIDCODE<15";
			filter.addField(fieldName);
			filter.setWhereClause(whereClause);
			ShapefileWorkspaceFactory factory = new ShapefileWorkspaceFactory();
			File file = new File(".");
			String path = file.getAbsolutePath();
			IFeatureWorkspace space = new IFeatureWorkspaceProxy(factory
					.openFromFile(path.substring(path
							.lastIndexOf(File.separator)), 0));
			ISelectionSet selection = fc.select(filter,
					esriSelectionType.esriSelectionTypeHybrid,
					esriSelectionOption.esriSelectionOptionNormal,
					new IWorkspaceProxy(space));
			System.out.println(selection.getCount());
			ICursor[] cursors = new ICursor[1];
			selection.search(null, false, cursors);
			IRow row = cursors[0].nextRow();
			int fId = row.getFields().findField(fieldName);
			System.out.println(row.getValue(fId));
			String s = "99" + row.getValue(fId).toString();
			row.setValue(fId, s);
			row.store();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}*/
}
