

import java.io.IOException;
import java.net.UnknownHostException;

import com.esri.arcgis.geoanalyst.IRasterConvertHelper;
import com.esri.arcgis.geoanalyst.RasterConvertHelper;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IRaster;

public class rasterToFeatureclass {
	private IRaster rr;
	public rasterToFeatureclass(IRaster ras) {
		super();
		// TODO Auto-generated constructor stub
		rr=ras;
	}
	public IFeatureClass rascoventFC(){
		IFeatureClass fc=null;
		try {
			IRasterConvertHelper rch = new RasterConvertHelper();
//			fc=rch.toShapefile(geoDataset,com.esri.arcgis.geometry.esriGeometryType.esriGeometryPoint,env);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fc; 

	}

}
