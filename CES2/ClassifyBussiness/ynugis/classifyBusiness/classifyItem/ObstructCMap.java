/*
* @author 冯涛，创建日期：2003-3-15
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
package ynugis.classifyBusiness.classifyItem;

import java.io.IOException;
import java.net.UnknownHostException;

import ynugis.geo.Utility;
import ynugis.utility.GT;

import com.esri.arcgis.carto.FeatureLayer;
import com.esri.arcgis.carto.GroupLayer;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.interop.AutomationException;


public class ObstructCMap extends CMap {

	private String srcFile;

	private IFeatureClass fc;

	public ObstructCMap(String shpFile) throws UnknownHostException, IOException {
		srcFile = shpFile;
		fc = Utility.OpenFeatureClass(srcFile);

		setName(GT.getFileNameWithoutExtension(shpFile));

	}

	public ObstructCMap(IFeatureClass inFeatureClass) throws UnknownHostException,
			IOException {

		fc = inFeatureClass;
		setName("阻隔");
	}

	/**
	 * 获取定级范围的FeatureClass
	 * 
	 * @return 定级范围的FeatureClass
	 */
	public IFeatureClass getFeatureClass() {
		return fc;

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
	 * 获取阻隔的“层组”
	 * 
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public GroupLayer getGroupLayer() throws Exception, IOException {
		// 获取当前阻隔featureClass
		IFeatureClass ifc = getFeatureClass();
		GroupLayer ret = new GroupLayer();
		ret.setExpanded(false);
		// 设置层组的名称
		ret.setName("阻隔");

		FeatureLayer fl = new FeatureLayer();
		fl.setName(getName());
		fl.setFeatureClassByRef(ifc);
		ret.add(fl);

		return ret;

	}
	

}
