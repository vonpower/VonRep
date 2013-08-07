/**
 * @(#) RangeCMap.java
 */

package ynugis.classifyBusiness.classifyItem;

import java.io.IOException;
import java.net.UnknownHostException;

import ynugis.geo.Utility;
import ynugis.utility.GT;

import com.esri.arcgis.carto.FeatureLayer;
import com.esri.arcgis.carto.GroupLayer;
import com.esri.arcgis.datasourcesfile.ShapefileWorkspaceFactory;
import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureWorkspace;
import com.esri.arcgis.geodatabase.IFeatureWorkspaceProxy;
import com.esri.arcgis.geodatabase.IWorkspaceFactory;
import com.esri.arcgis.geodatabase.esriFeatureType;
import com.esri.arcgis.geometry.IArea;
import com.esri.arcgis.geometry.IAreaProxy;
import com.esri.arcgis.geometry.IGeometry;
import com.esri.arcgis.geometry.IPolygon;
import com.esri.arcgis.geometry.IPolygonProxy;
import com.esri.arcgis.interop.AutomationException;

/**
 * @author Administrator
 * 
 */
public class RangeCMap extends CMap {
	private String srcFile;

	private IFeatureClass fc;

	public RangeCMap(String shpFile) throws UnknownHostException, IOException {
		srcFile = shpFile;
		fc = Utility.OpenFeatureClass(srcFile);

		setName(GT.getFileNameWithoutExtension(shpFile));

	}

	public RangeCMap(IFeatureClass inFeatureClass) throws UnknownHostException,
			IOException {

		fc = inFeatureClass;
		setName("定级范围");
	}

	/**
	 * 获取定级范围的FeatureClass
	 * 
	 * @return 定级范围的FeatureClass
	 */
	public IFeatureClass getFeatureClass() {
		return fc;

	}

	/*
	 * 保存定级范围
	 * 
	 * @see ynugis.classifyBusiness.classifyItem.IClassifyItem#save(java.lang.String)
	 * 
	 * public void save(String path) throws UnknownHostException, IOException {
	 * String strFolder = GT.getFilePath(path); String strName =
	 * GT.getFileName(path); String strShapeFieldName="Shape";
	 * 
	 * IFeatureWorkspace pFWS = null; IWorkspaceFactory pWorkspaceFactory =
	 * null; pWorkspaceFactory = new ShapefileWorkspaceFactory(); pFWS = new
	 * IFeatureWorkspaceProxy(pWorkspaceFactory.openFromFile(strFolder, 0));
	 * 
	 * 
	 * pFWS.createFeatureClass(strName, fc.getFields(), null,
	 * null,esriFeatureType.esriFTSimple, strShapeFieldName, "");
	 *  }
	 */
	/**
	 * 获取定级范围面积
	 * 
	 * @return 定级范围面积
	 * @throws AutomationException
	 * @throws IOException
	 */
	public double getArea() throws AutomationException, IOException {
		int featureCount = getFeatureClass().featureCount(null);
		double area = 0;
		IArea temp;
		for (int i = 0; i < featureCount; i++) {
			IGeometry g = getFeatureClass().getFeature(i).getShape();
			IPolygon ploygon = new IPolygonProxy(g);
			temp = new IAreaProxy(ploygon);
			area += temp.getArea();

		}

		return area;

	}

	public void saveAs(String path) throws Exception {

		if (!Utility.deleteShp(path))
			throw new Exception("原本存在的地图文件(" + path + ")暂时不可修改");

		String strFolder = GT.getFilePath(path);
		String strName = GT.getFileName(path);
		String strShapeFieldName = "Shape";

		IFeatureWorkspace pFWS = null;
		IWorkspaceFactory pWorkspaceFactory = null;
		pWorkspaceFactory = new ShapefileWorkspaceFactory();
		pFWS = new IFeatureWorkspaceProxy(pWorkspaceFactory.openFromFile(
				strFolder, 0));

		IFeatureClass temp=pFWS.createFeatureClass(strName, fc.getFields(), null, null,
				esriFeatureType.esriFTSimple, strShapeFieldName, "");
		IFeature pFeature;
		
		for(int i=0;i<fc.featureCount(null);i++)
		{
			pFeature=temp.createFeature();
			
			pFeature.setShapeByRef(fc.getFeature(i).getShape());
			
			pFeature.store();
		}
		

		
	}

	

	public String getSrcFile() {
		if(srcFile==null)
		{
			try {
				srcFile=fc.getShapeFieldName();
			} catch (AutomationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return srcFile;
	}

	public void setSrcFile(String srcFile) {
		this.srcFile = srcFile;
	}

	/**
	 * 获取定级范围的“层组”
	 * 
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public GroupLayer getGroupLayer() throws Exception, IOException {
		// 获取当前定级范围featureClass
		IFeatureClass ifc = getFeatureClass();
		GroupLayer ret = new GroupLayer();
		ret.setExpanded(false);
		// 设置层组的名称
		ret.setName("定级范围");

		FeatureLayer fl = new FeatureLayer();
		fl.setName(getName());
		fl.setFeatureClassByRef(ifc);
		ret.add(fl);

		return ret;

	}
}
