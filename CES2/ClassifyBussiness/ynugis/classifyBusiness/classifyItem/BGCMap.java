/*
 * @author 冯涛，创建日期：2003-11-1
 * blog--http://apower.blogone.net
 * QQ:17463776
 * MSN:apower511@msn.com
 * E-Mail:VonPower@Gmail.com
 */
package ynugis.classifyBusiness.classifyItem;

import java.io.IOException;
import java.net.UnknownHostException;

import ynugis.geo.Utility;

import com.esri.arcgis.carto.FeatureLayer;
import com.esri.arcgis.carto.GroupLayer;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IGeoDataset;
import com.esri.arcgis.geodatabase.IGeoDatasetProxy;
import com.esri.arcgis.geometry.ISpatialReference;
import com.esri.arcgis.interop.AutomationException;

public class BGCMap extends CMap {
	// private GroupLayer BGLayers;
	private String[] srcFiles;

	//private String srcFilesPath = "";

	public BGCMap(String[] files) {
		srcFiles = files;
		//setSrcFilesPath(GT.getFilePath(files[0]));

	}

	/**
	 * 获取第一个底图的空间参考
	 * 
	 * @return
	 * @throws IOException
	 * @throws AutomationException
	 */
	public ISpatialReference getSpatailRef() throws AutomationException,
			IOException {
		if (getFeatureClass().length == 0)
			return null;

		IGeoDataset geoDataset;

		geoDataset = new IGeoDatasetProxy(getFeatureClass()[0]);
		
		return geoDataset.getSpatialReference();

	}
/*
	*//**
	 * Auto select ALL ESRI_SHAPEFILE in "filePath"
	 * 
	 * @param filesPath
	 *//*
	public BGCMap(String filesPath) {
		File f = new File(filesPath);
		String[] temp = f.list(new FilenameFilter() {

			public boolean accept(File arg0, String arg1) {

				return arg1.toUpperCase().endsWith(".SHP");
			}

		});
		srcFiles = temp;
		setSrcFilesPath(filesPath);

	}
*/
	/**
	 * 获取当前工作底图的所有featureClass
	 * 
	 * @return
	 */
	public IFeatureClass[] getFeatureClass() {
		IFeatureClass[] ifc = new IFeatureClass[srcFiles.length];
		for (int i = 0; i < ifc.length; i++) {

			try {
				ifc[i] = Utility.OpenFeatureClass(srcFiles[i]);

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ifc;
	}

	public GroupLayer getGroupLayer() throws Exception, IOException {
		// 获取当前工作底图的所有featureClass
		IFeatureClass[] ifc = getFeatureClass();
		GroupLayer ret = new GroupLayer();
		ret.setExpanded(false);
		// 设置层组的名称
		ret.setName(getName());

		FeatureLayer fl;
		for (int i = 0; i < ifc.length; i++) {
			fl = new FeatureLayer();
			fl.setFeatureClassByRef(ifc[i]);
			fl.setName("工作底图("+i+")");
			ret.add(fl);
		}
		return ret;

	}

	public String[] getSrcFiles() {
		return srcFiles;
	}

	public void setSrcFiles(String[] srcFiles) {
		this.srcFiles = srcFiles;
	}

	/*public String getSrcFilesPath() {
		return srcFilesPath;
	}

	public void setSrcFilesPath(String srcFilesPath) {
		this.srcFilesPath = srcFilesPath;
	}*/

	public void saveAs(String path) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub

	}

	
}
