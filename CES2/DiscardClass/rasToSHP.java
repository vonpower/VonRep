

import java.io.IOException;
import java.net.UnknownHostException;

import com.esri.arcgis.carto.BasicOverposterLayerProperties;
import com.esri.arcgis.carto.FeatureLayer;
import com.esri.arcgis.carto.IAnnotateLayerPropertiesCollection;
import com.esri.arcgis.carto.IAnnotateLayerPropertiesProxy;
import com.esri.arcgis.carto.IBasicOverposterLayerProperties;
import com.esri.arcgis.carto.IFeatureLayer;
import com.esri.arcgis.carto.IGeoFeatureLayer;
import com.esri.arcgis.carto.ILabelEngineLayerProperties;
import com.esri.arcgis.carto.ILineLabelPosition;
import com.esri.arcgis.carto.LabelEngineLayerProperties;
import com.esri.arcgis.carto.LineLabelPlacementPriorities;
import com.esri.arcgis.carto.LineLabelPosition;
import com.esri.arcgis.geoanalyst.IRasterAnalysisEnvironment;
import com.esri.arcgis.geoanalyst.IRasterConvertHelper;
import com.esri.arcgis.geoanalyst.RasterAnalysis;
import com.esri.arcgis.geoanalyst.RasterConvertHelper;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IGeoDatasetProxy;
import com.esri.arcgis.geodatabase.IRaster;

public class rasToSHP {
	

	private static final int esriRasterEnvMaxOf = 0;
	
	private IRaster Ras;
	private IFeatureLayer newFeatLayer;
	public rasToSHP(IRaster rr) {
		super();
		// TODO Auto-generated constructor stub
		Ras=rr;
	}
	public  IFeatureLayer rasToSHape(int intsouxiao){
//		IFeatureLayer newFeatLayer;
		IFeatureClass pOutFClass;
		try {
			IRasterConvertHelper pRasConvertHelper=new RasterConvertHelper();
			IRasterAnalysisEnvironment pEnv=new RasterAnalysis();
			pEnv.setCellSize(esriRasterEnvMaxOf,Ras);
			pOutFClass=pRasConvertHelper.toShapefile(new IGeoDatasetProxy(Ras),com.esri.arcgis.geometry.esriGeometryType.esriGeometryPolygon,pEnv);
			newFeatLayer=new FeatureLayer(); 
			newFeatLayer.setFeatureClassByRef(pOutFClass);
			newFeatLayer.setDisplayField("GRIDCODE");
			IGeoFeatureLayer pGeoFL=new FeatureLayer(newFeatLayer);
			IAnnotateLayerPropertiesCollection pAnnoLayerPropsColl=pGeoFL.getAnnotationProperties();
			pAnnoLayerPropsColl.clear();
////			pAnnoLayerPropsColl.clear();
			ILabelEngineLayerProperties pAnnoLayerProps;
			ILineLabelPosition pPosition=new LineLabelPosition();
			pPosition.setParallel(false);
			pPosition.setPerpendicular(true);
			LineLabelPlacementPriorities pPlacement=new LineLabelPlacementPriorities();
			IBasicOverposterLayerProperties pBas=new BasicOverposterLayerProperties();
//			
			pBas.setFeatureType(com.esri.arcgis.geometry.esriGeometryType.esriGeometryPolygon);
			pBas.setLineLabelPlacementPriorities(pPlacement);
			pBas.setLineLabelPosition(pPosition);
			ILabelEngineLayerProperties pLabelEngine=new LabelEngineLayerProperties();
			pLabelEngine.setBasicOverposterLayerPropertiesByRef(pBas);
			pLabelEngine.setExpression("[GRIDCODE]"+" / "+intsouxiao);
			pAnnoLayerProps=pLabelEngine;
			System.out.println("应该标注了");
			pAnnoLayerPropsColl.add(new IAnnotateLayerPropertiesProxy(pAnnoLayerProps));
			pGeoFL.setDisplayAnnotation(true);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pOutFClass=null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pOutFClass=null;
		}
		

		return newFeatLayer;
	}
}
