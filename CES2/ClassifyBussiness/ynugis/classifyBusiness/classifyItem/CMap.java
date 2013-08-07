/**
 * @(#) CMap.java
 */

package ynugis.classifyBusiness.classifyItem;

import java.io.IOException;

import com.esri.arcgis.carto.GroupLayer;
import com.esri.arcgis.carto.IRasterLayer;
import com.esri.arcgis.carto.RasterLayer;
import com.esri.arcgis.datasourcesraster.IRasterBandCollection;
import com.esri.arcgis.datasourcesraster.IRasterBandCollectionProxy;
import com.esri.arcgis.geodatabase.IGeoDataset;
import com.esri.arcgis.geodatabase.IRaster;
import com.esri.arcgis.geodatabase.IRasterDataset;
import com.esri.arcgis.geodatabase.IRasterDatasetProxy;
import com.esri.arcgis.geodatabase.IRasterProxy;

public abstract class CMap extends ClassifyItem {

	public CMap() {
		super();

	}

	protected IRasterLayer getRasterLayer(IGeoDataset data) throws Exception,
			IOException {
		IRasterLayer rasterLayers = new RasterLayer();
		IRasterDataset rasterDataSet = new IRasterDatasetProxy(data);
		IRasterBandCollection ib = new IRasterBandCollectionProxy(rasterDataSet);
		IRaster r = new IRasterProxy(ib);

		rasterLayers = new RasterLayer();
		rasterLayers.createFromRaster(r);

		return rasterLayers;

	}

	public abstract GroupLayer getGroupLayer() throws Exception, IOException;

}
