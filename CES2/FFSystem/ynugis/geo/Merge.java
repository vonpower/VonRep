/*
 * @author 冯涛，创建日期：2003-10-16
 * blog--http://apower.blogone.net
 * QQ:17463776
 * MSN:apower511@msn.com
 * E-Mail:VonPower@Gmail.com
 */
package ynugis.geo;

import java.io.IOException;

import com.esri.arcgis.geoanalyst.IRasterAnalysisEnvironment;
import com.esri.arcgis.geoanalyst.IRasterAnalysisEnvironmentProxy;
import com.esri.arcgis.geodatabase.IGeoDataset;
import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.spatialanalyst.IMapAlgebraOp;
import com.esri.arcgis.spatialanalyst.RasterMapAlgebraOp;

public class Merge {

	// private variable definition
	private double cellSize = 0;

	private String outputPath = "";

	private IGeoDataset[] dataArray;

	private IMapAlgebraOp mapAlgebraOp;

	private IRasterAnalysisEnvironment rasterAnalysisEnvironment;

	/**
	 * setOutputPath(GV.getDefaultTempFileDirectoryPath()); setCellSize(50);
	 * 
	 * @throws Exception
	 * @throws IOException
	 */
	public Merge(IRasterAnalysisEnvironment env) throws Exception, IOException {
		super();
		initial();
		Utility.RasterAnalysisEnvCopy(env, rasterAnalysisEnvironment);
	}

	/**
	 * 
	 * 
	 * @param outputPath
	 * @param cellSize
	 * @throws Exception
	 * @throws IOException
	 */
	/*
	 * public Merge(String outputPath,double cellSize) throws Exception,
	 * IOException { super(); initial(); setOutputPath(outputPath);
	 * setCellSize(cellSize);
	 *  }
	 * 
	 * 
	 */

	/**
	 * 
	 * 
	 * @throws Exception
	 * @throws IOException
	 */
	private void initial() throws Exception, IOException {
		mapAlgebraOp = new RasterMapAlgebraOp();
		rasterAnalysisEnvironment = new IRasterAnalysisEnvironmentProxy(
				mapAlgebraOp);

	}

	/**
	 * 指定用于合并的数据层
	 * 
	 * @param data
	 * @throws Exception
	 */
	public void specifyDataLayers(IGeoDataset[] data) throws Exception {

		dataArray = data;

	}

	/**
	 * 合并（specifyDataLayers（）方法）所指定的数据层
	 * 
	 * @return
	 * @throws Exception
	 */
	public IGeoDataset mergeDataLayers() throws Exception {
		double[] foo = new double[dataArray.length];
		for (int i = 0; i < foo.length; i++) {
			foo[i] = 1;

		}

		return mergeDataLayers(foo);
	}

	public IGeoDataset mergeUseBigValue() throws AutomationException, IOException {
		IGeoDataset result;
		if (dataArray.length == 0)
			return null;
		if (dataArray.length == 1)
		{
			mapAlgebraOp.bindRaster(dataArray[0],"R1");
			return mapAlgebraOp.execute("CON(isNull([R1]) ,0 ,[R1])");
		}
		// 构造地图代数运算表达式
		result=dataArray[0];
		for (int i = 1; i < dataArray.length; i++) {

			mapAlgebraOp.bindRaster(result,"R1");
			mapAlgebraOp.bindRaster(dataArray[i],"R2");
			
			// 将[Rt]定中的nodata格指定为“0”；
			result=mapAlgebraOp.execute("CON(isNull([R1]) ,0 ,[R1])");
			dataArray[i]=mapAlgebraOp.execute("CON(isNull([R2]) ,0 ,[R2])");
			
			
			result=mapAlgebraOp.execute("CON([R2] > [R1] ,[R2] ,[R1])");

			
			
		}
		mapAlgebraOp.unbindRaster("R1");
		mapAlgebraOp.unbindRaster("R2");
		return result;
	}

	/**
	 * 按照所指定的权重（weights）合并所指定的数据层
	 * 
	 * @param weights
	 *            weights和当前所指定的数据层一一对应，表示每层的权重
	 * @return
	 * @throws Exception
	 */
	public IGeoDataset mergeDataLayers(double[] weights) throws Exception {
		IGeoDataset result;
		if (dataArray.length == 0)
			return null;
		if (dataArray.length == 1)
			return dataArray[0];

		if (dataArray.length != weights.length)
			throw new Exception("叠加数据个数和指定的权重个数不匹配！");

		String[] alias = new String[dataArray.length];

		// 构造地图代数运算表达式
		String commandStr = "";
		for (int i = 0; i < dataArray.length; i++) {
			if (i != 0)
				commandStr += " + ";

			alias[i] = "R" + i;

			mapAlgebraOp.bindRaster(dataArray[i], alias[i]);

			// 将[Rt]定中的nodata格指定为“0”；
			dataArray[i] = mapAlgebraOp.execute("CON(isNull([" + alias[i]
					+ "]) ,0 ,[" + alias[i] + "])");

			mapAlgebraOp.bindRaster(dataArray[i], alias[i]);
			commandStr += "[" + "R" + i + "]" + " * " + weights[i];
		}

		result = mapAlgebraOp.execute(commandStr);

		// 把单元格分值超过100的进行处理
		mapAlgebraOp.bindRaster(result, "R0");
		result = mapAlgebraOp
				.execute("CON([R0] > 100 ,98 + [R0] / 1000 ,[R0])");

		// 解除本次使用的GeoDataset绑定；
		for (int i = 0; i < alias.length; i++) {
			mapAlgebraOp.unbindRaster(alias[i]);

		}

		return result;
	}

	/*
	 * public double getCellSize() { return cellSize; }
	 * 
	 * public void setCellSize(double cellSize) throws Exception, IOException {
	 * this.cellSize = cellSize;
	 * rasterAnalysisEnvironment.setCellSize(esriRasterEnvSettingEnum.esriRasterEnvValue,
	 * new Double(cellSize)); }
	 * 
	 * public String getOutputPath() { return outputPath; }
	 * 
	 * public void setOutputPath(String output) throws Exception, IOException {
	 * this.outputPath = output; IWorkspaceFactory rasterWSF = new
	 * RasterWorkspaceFactory(); IWorkspace workspace =
	 * rasterWSF.openFromFile(getOutputPath(), 0);
	 * rasterAnalysisEnvironment.setOutWorkspaceByRef(workspace);
	 *  }
	 * 
	 */

}
