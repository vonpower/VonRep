/*
 * @author 冯涛，创建日期：2003-10-11
 * blog--http://apower.blogone.net
 * QQ:17463776
 * MSN:apower511@msn.com
 * E-Mail:VonPower@Gmail.com
 */
package ynugis.geo;


public class Test {
/*
	public static void SA(IFeatureClass fclass, IEnvelope extent)
			throws Exception {
		ShpDistanceOp sop = new ShpDistanceOp();

		IFeature feature = fclass.getFeature(0);
		double functionScore = 0;
		double range = 0;
		String scoreStr = feature
				.getValue(feature.getFields().findField("作用分")).toString();
		functionScore = Double.valueOf(scoreStr).doubleValue();
		System.out.println("作用分:" + functionScore);

		String rangeStr = feature.getValue(
				feature.getFields().findField("作用半径")).toString();
		range = Double.valueOf(rangeStr).doubleValue();
		System.out.println("作用半径:" + range);

		IGeoDataset disResult = sop.doStraightLine(
				new IGeoDatasetProxy(fclass), range / 100000, 0.0005, GV
						.getDefaultTempFileDirectoryPath(), extent);
		
		 * IMapAlgebraOp mapAlgebraOp = new RasterMapAlgebraOp();
		 * mapAlgebraOp.bindRaster(disResult, "R1");
		 * 
		 * mapAlgebraOp.execute(functionScore+" * (1 - [R1] / 0.005)");
		 
	}

	public static Raster maSlope(String inPath, String inRaster,
			String tmpOutPath) throws IOException {
		IWorkspaceFactory rasWkspFact = new RasterWorkspaceFactory();
		IRasterWorkspace inRasWksp = new IRasterWorkspaceProxy(rasWkspFact
				.openFromFile(inPath, 0));
		RasterDataset inRDS = new RasterDataset(inRasWksp
				.openRasterDataset(inRaster));

		 create a RasterMapAlgebraOp operator 
		IMapAlgebraOp mapAlgebraOp = new RasterMapAlgebraOp();

		 Set output workspace 
		IRasterAnalysisEnvironment rasterAnalysisEnvironment = new IRasterAnalysisEnvironmentProxy(
				mapAlgebraOp);
		IWorkspaceFactory rasterWSF = new RasterWorkspaceFactory();
		IWorkspace workspace = rasterWSF.openFromFile(tmpOutPath, 0);
		rasterAnalysisEnvironment.setOutWorkspaceByRef(workspace);

		 bind a raster 
		mapAlgebraOp.bindRaster(inRDS, "R1");

		
		 * execute the CMap Algebra expression ot calculate slope of the input
		 * raster
		 
		return new Raster(mapAlgebraOp.execute("Slope([R1])"));
	}

	public static void nodata2Zero(IGeoDataset data,IWorkspace rasterWS) throws Exception, IOException
	{
//		 Create a RasterReclassOp operator
		IReclassOp reclassOp = new RasterReclassOp();
		
		
		// Set output workspace.Specify GRID's output filepath 
		IWorkspaceFactory rasterWSF = new RasterWorkspaceFactory();
		IWorkspace rasterWS = rasterWSF.openFromFile(aOutPath, 0);
		
		IRasterAnalysisEnvironment rasterEnv = (IRasterAnalysisEnvironment) reclassOp;
		rasterEnv.setOutWorkspaceByRef(rasterWS);

		// Create a StringRemap object and specify remap
		INumberRemap numRemap = new NumberRemap();

		//set nodata to 0
		numRemap.mapRange(0,0,0);
		data = reclassOp.reclassByRemap(data,
				new IRemapProxy(numRemap), true);
	
		
	}
	
	public static IGeoDataset calculate_E_Score(IFeatureClass inFeature,
			String output, double cellSize) throws Exception, IOException {
		
		double functionScore = 0;
		double range = 0;

		IMapAlgebraOp mapAlgebraOp = new RasterMapAlgebraOp();

		 Set output workspace 
		IRasterAnalysisEnvironment rasterAnalysisEnvironment = new IRasterAnalysisEnvironmentProxy(
				mapAlgebraOp);
		IWorkspaceFactory rasterWSF = new RasterWorkspaceFactory();
		IWorkspace workspace = rasterWSF.openFromFile(output, 0);
		rasterAnalysisEnvironment.setOutWorkspaceByRef(workspace);

		ShpDistanceOp sdo = new ShpDistanceOp();
		ISelectionSet selectionSet = inFeature.select(null,
				esriSelectionType.esriSelectionTypeIDSet,
				esriSelectionOption.esriSelectionOptionNormal, null);
		int Count = selectionSet.getCount();
		System.out.println("selectionSet.getCount():" + Count);

		IQueryFilter qf = new QueryFilter();

		FeatureClassDescriptor fcd = new FeatureClassDescriptor();

		int scoreIdx = inFeature.getFields().findField("作用分");
		int rangeIdx = inFeature.getFields().findField("作用半径");
		String scoreStr;
		String rangeStr;
		IGeoDataset disTemp;
		IGeoDataset result=null;
		//mapAlgebraOp.bindRaster(result, "result");
		for (int i = 0; i < Count; i++) {

			qf.setWhereClause("FID = " + i);

			fcd.createFromSelectionSet(selectionSet, qf, "作用分");

			scoreStr = inFeature.getFeature(i).getValue(scoreIdx).toString();
			functionScore = Double.valueOf(scoreStr).doubleValue();
			System.out.println("作用分:" + functionScore);

			rangeStr = inFeature.getFeature(i).getValue(rangeIdx).toString();
			range = Double.valueOf(rangeStr).doubleValue();
			System.out.println("作用半径:" + range);

			disTemp = sdo.doStraightLine(fcd.getAsIGeoDataset(), range,
					cellSize, "d:\\apower");

			mapAlgebraOp.bindRaster(disTemp, "R1");
			
			
			if(result==null)
			{
				result=mapAlgebraOp.execute(functionScore + " * ( 1 - [R1] / " + range+ " )");
				mapAlgebraOp.bindRaster(result, "R0");
				
				result=mapAlgebraOp.execute("CON(isNull([R0]) ,0 ,[R0])");
				//nodata2Zero(result,workspace);
				mapAlgebraOp.bindRaster(result, "R0");
				
			}else 
			{
				IGeoDataset temp=mapAlgebraOp.execute(+functionScore + " * (1 - [R1] / " + range+ ")");
				//nodata2Zero(temp,workspace);
				mapAlgebraOp.bindRaster(temp, "Rt");
				temp=mapAlgebraOp.execute("CON(isNull([Rt]) ,0 ,[Rt])");
				
				mapAlgebraOp.bindRaster(temp, "Rt");
				result=mapAlgebraOp.execute("[Rt] + [R0]");
				mapAlgebraOp.bindRaster(result, "R0");
				
			}
		}
	
		return result;
	}

	*//**
	 * @param args
	 * @throws Exception
	 * @throws UnknownHostException
	 *//*
	public static void main(String[] args) throws UnknownHostException,
			Exception {

		GisBean g = GisBean.getGisBeans();

		ShpDistanceOp sdo = new ShpDistanceOp();
		IFeatureClass FCRange = OpenFeatureClass("d:\\", "range");
		IFeatureClass FCSource = OpenFeatureClass("d:\\", "中学");
		IFeatureClass FCDest = OpenFeatureClass("d:\\", "ttt");
		
		
		
		
		CalculateEij.calculate_E_Score(FCSource,"d:\\apower",500,FCRange);
		
		IQueryFilter qf = new QueryFilter();
		qf.setWhereClause("FID = " + 1);
		ISelectionSet ss = FCSource.select(null,
				esriSelectionType.esriSelectionTypeIDSet,
				esriSelectionOption.esriSelectionOptionNormal, null);

		System.out.println(ss.getCount());

		FeatureClassDescriptor fcd = new FeatureClassDescriptor();
		fcd.createFromSelectionSet(ss, qf, "作用分");

		IFeature feature = FCSource.getFeature(0);
		double functionScore = 0;
		double range = 0;
		String scoreStr = feature
				.getValue(feature.getFields().findField("作用分")).toString();
		functionScore = Double.valueOf(scoreStr).doubleValue();
		System.out.println("作用分:" + functionScore);

		String rangeStr = feature.getValue(
				feature.getFields().findField("作用半径")).toString();
		range = Double.valueOf(rangeStr).doubleValue();
		System.out.println("作用半径:" + range);

		IGeoDataset disResult = sdo.doStraightLine(fcd.getAsIGeoDataset(),
				range, 500, GV.getDefaultTempFileDirectoryPath());

		IMapAlgebraOp mapAlgebraOp = new RasterMapAlgebraOp();

		 Set output workspace 
		IRasterAnalysisEnvironment rasterAnalysisEnvironment = new IRasterAnalysisEnvironmentProxy(
				mapAlgebraOp);
		IWorkspaceFactory rasterWSF = new RasterWorkspaceFactory();
		IWorkspace workspace = rasterWSF.openFromFile("d:\\apower", 0);
		rasterAnalysisEnvironment.setOutWorkspaceByRef(workspace);

		mapAlgebraOp.bindRaster(disResult, "R1");

		mapAlgebraOp.execute(functionScore + " * (1 - [R1] / " + range + ")");

		
	}

	private static void AddFields(IFeatureBuffer featureBuffer, IFeature feature)
			throws AutomationException, IOException {
		// Copy the attributes of the orig feature the new feature
		IRowBuffer rowBuffer = (IRowBuffer) featureBuffer;
		IFields fieldsNew = rowBuffer.getFields();

		IFields fields = feature.getFields();
		for (int i = 0; i <= fieldsNew.getFieldCount() - 1; i++) {
			IField field = fieldsNew.getField(i);
			if ((field.getType() != esriFieldType.esriFieldTypeGeometry)
					&& (field.getType() != esriFieldType.esriFieldTypeOID)) {
				int intFieldIndex = fields.findField(field.getName());
				if (intFieldIndex != -1) {
					featureBuffer.setValue(i, feature.getValue(intFieldIndex));
				}
			}
		}
	}

	private static IFeatureClass OpenFeatureClass(String stringWorkspace,
			String stringFeatureClass) throws UnknownHostException, IOException {
		// Create the workspace name object
		IWorkspaceName workspaceName = new WorkspaceName();
		workspaceName.setPathName(stringWorkspace);
		workspaceName
				.setWorkspaceFactoryProgID("esriDataSourcesFile.ShapefileWorkspaceFactory");

		// Create the feature class name object
		IDatasetName datasetName = new FeatureClassName();
		datasetName.setName(stringFeatureClass);
		datasetName.setWorkspaceNameByRef(workspaceName);

		// Open the feature class
		IName name = (IName) datasetName;

		// Return FeaureClass
		return new IFeatureClassProxy(name.open());
	}
*/
}
