/*
 * @author 冯涛，创建日期：2003-9-19
 * blog--http://apower.blogone.net
 * QQ:17463776
 * MSN:apower511@msn.com
 * E-Mail:VonPower@Gmail.com
 */
package ynugis.geo;


/**
 * @author Administrator
 * 
 */
public class Reclassify {

	public Reclassify() {
		super();
		// TODO Auto-generated constructor stub
	}
/*
	public IGeoDataset doReclassify(IGeoDataset data,String aOutPath) {
		// Create a raster descriptor and specify the field to be used for
		// reclassify
		IRasterDescriptor rasDescriptor = new RasterDescriptor();
		rasDescriptor.create(new IRasterProxy(data), null, "Value");

		// Create a RasterReclassOp operator
		IReclassOp reclassOp = new RasterReclassOp();

		// Set output workspace.Specify GRID's output filepath 
		IWorkspaceFactory rasterWSF = new RasterWorkspaceFactory();
		IWorkspace rasterWS = rasterWSF.openFromFile(aOutPath, 0);
		IRasterAnalysisEnvironment rasterEnv = (IRasterAnalysisEnvironment) reclassOp;
		rasterEnv.setOutWorkspaceByRef(rasterWS);

		// Create a StringRemap object and specify remap
		INumberRemap numRemap = new NumberRemap();

		numRemap.mapRange(0, 850, 10);
		numRemap.mapRange(851, 1800, 5);

		IGeoDataset reclassResult = reclassOp.reclassByRemap(distanceResult,
				new IRemapProxy(numRemap), false);

		IMapAlgebraOp mapAlgebraOp = new RasterMapAlgebraOp();

		 Set output workspace 
		IRasterAnalysisEnvironment rasterAnalysisEnvironment = new IRasterAnalysisEnvironmentProxy(
				mapAlgebraOp);
		IWorkspaceFactory rasterWSF2 = new RasterWorkspaceFactory();
		IWorkspace workspace = rasterWSF2.openFromFile("d:\\", 0);
		rasterAnalysisEnvironment.setOutWorkspaceByRef(workspace);

		mapAlgebraOp.bindRaster(distanceResult, "R1");
		mapAlgebraOp.bindRaster(reclassResult, "R2");
		mapAlgebraOp.execute("[R1] + [R2]");

		return reclassResult;

	}*/
}
