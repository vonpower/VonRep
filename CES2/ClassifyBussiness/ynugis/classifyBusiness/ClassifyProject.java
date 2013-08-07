/*
 * @author 冯涛，创建日期：2003-10-31
 * blog--http://apower.blogone.net
 * QQ:17463776
 * MSN:apower511@msn.com
 * E-Mail:VonPower@Gmail.com
 */
package ynugis.classifyBusiness;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import ynugis.classifyBusiness.classifyItem.BGCMap;
import ynugis.classifyBusiness.classifyItem.ObstructCMap;
import ynugis.classifyBusiness.classifyItem.RangeCMap;
import ynugis.ffsystem.FFSystem;
import ynugis.ffsystem.FFTable;
import ynugis.utility.GT;

import com.FFT.FFTDoc;
import com.FFT.FFTableType;
import com.esri.arcgis.datasourcesraster.RasterWorkspaceFactory;
import com.esri.arcgis.geoanalyst.IRasterAnalysisEnvironment;
import com.esri.arcgis.geoanalyst.RasterAnalysis;
import com.esri.arcgis.geoanalyst.esriRasterEnvSettingEnum;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IGeoDataset;
import com.esri.arcgis.geodatabase.IGeoDatasetProxy;
import com.esri.arcgis.geodatabase.IWorkspace;
import com.esri.arcgis.geodatabase.IWorkspaceFactory;
import com.esri.arcgis.geodatabase.IWorkspaceProxy;
import com.esri.arcgis.geometry.IEnvelope;
import com.projectInfo.ClassifyProjectType;
import com.projectInfo.projectInfoDoc;

public class ClassifyProject implements IClassifySpecification {

	private String projectName = null;

	private String workspace = null;

	private String tempDirectory = null;

	private String projectDescription = null;

	private RangeCMap classifyRange = null;
	
	private ObstructCMap obstruct=null;
	
	private BGCMap bgMap = null;

	private ArrayList FFTCollection = null;

	private double cellSize;

	// private double areaPresicion;
	// private IRasterAnalysisEnvironment projectEnv;

	public ClassifyProject() {
		super();
		FFTCollection = new ArrayList();

	}

	public ClassifyProject(String xmlPath) {
		FFTCollection = new ArrayList();
		try {
			initialUseXML(xmlPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 使用指定的XML文件完成 项目初始化
	 * 
	 * @param strings
	 * @throws Exception
	 */
	private void initialUseXML(String strings) {
		try {
			projectInfoDoc doc = new projectInfoDoc();
			ClassifyProjectType root = new ClassifyProjectType(doc
					.load(strings));

			// 设置项目基本信息
			setProjectName(root.getprojectName().getValue());
			setWorkspace(root.getworkspace().getValue());
			setTempDirectory(root.gettempDirectory().getValue());
			setProjectDescription(root.getprojectDescription().getValue());
			setCellSize(root.getcellSize().doubleValue());

			// 设置定级范围
			if (root.getclassifyRangePathCount() > 0) {
				setClassifyRange(new RangeCMap(root.getclassifyRangePath()
						.toString()));
			}
			
			//设置阻隔
			if(root.getobstructPathCount()>0)
			{
				setObstruct(new ObstructCMap(root.getobstructPath().toString()));
			}
			
			// 设置工作底图
			String bgFiles[] = new String[root.getbgMapPathCount()];
			for (int i = 0; i < bgFiles.length; i++) {
				bgFiles[i] = root.getbgMapPathAt(i).toString();
			}
			setBgMap(new BGCMap(bgFiles));

			// 初始化FFT集合
			int c = root.getFFTableCount();
			FFTDoc fftDoc = new FFTDoc();
			String xmlFile = "";
			while (c > 0) {
				xmlFile = root.getFFTableAt(--c).toString();
				addFFT(new FFTable(new FFTableType(fftDoc.load(xmlFile))));

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 定级项目另存为...
	 * 
	 * @throws Exception
	 */
	public void saveAs(String xmlPath) throws Exception {
		projectInfoDoc doc = new projectInfoDoc();
		ClassifyProjectType root = new ClassifyProjectType(doc, "", "", "ClassifyProject");
		//doc.setRootElementName("", "ClassifyProject");
		// Set Xml data:

		root.addprojectName(getProjectName());
		root.addworkspace(getWorkspace());
		root.addtempDirectory(getTempDirectory());
		root.addprojectDescription(getProjectDescription());
		root.addcellSize(Double.toString(getCellSize()));
		// TODO:HARDCODE

		if (getClassifyRange() != null) {
			// 保存定级范围
			// String
			// rangePath=getWorkspace()+File.separator+getClassifyRange().getName()+".shp";

			// getClassifyRange().saveAs(rangePath);
			root.addclassifyRangePath(getClassifyRange().getSrcFile());

		}
		
		if(getObstruct()!=null)
		{
			root.addobstructPath(getObstruct().getSrcFile());
		}
		
		// 保存工作底图路径
		for (int i = 0; i < getBgMap().getSrcFiles().length; i++) {
			root.addbgMapPath(getBgMap().getSrcFiles()[i]);
		}

		if (getFFTCount() > 0) {
			// 保持FFT集合
			String FFTPath = getWorkspace() + File.separator + "FFTs"
					+ File.separator;
			String FFTFullPath="";
			File f = new File(FFTPath);
			if (!f.isDirectory())
				f.mkdir();

			FFTable tempFFT = null;
			
			//把当前项目中包含得所有FFT保存在当前项目工作路径下的FFT文件夹下
			for (int i = 0; i < getFFTCount(); i++) {
				tempFFT = getFFTAt(i);

				if (!tempFFT.name.equals("")) {
					FFTFullPath = FFTPath + tempFFT.name + ".fft";
				} else {
					FFTFullPath = FFTPath + "未命名" + i + ".fft";
				}
				
				
				FFSystem.saveFFT(tempFFT, FFTFullPath);
				//记录在CES文件中
				root.addFFTable(FFTFullPath);
				
				
			}
			
			
		}
		// TODO:保存项目其他定级制品

		// Write XML 2 disk
		doc.save(xmlPath, root);

	}

	/**
	 * 保存当前定级项目
	 * 
	 * @throws Exception
	 */
	public void save() throws Exception {

		// 保存的路径：工作目录＋项目名称
		String xmlPath = getWorkspace() + File.separator + getProjectName()
				+ ".ces";
		saveAs(xmlPath);

	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getTempDirectory() {
		File f=new File(tempDirectory);
		if(!f.isDirectory())
		{
			f.mkdir();
		}
		
		return tempDirectory;
	}

	public void setTempDirectory(String tempDirectory) {
		this.tempDirectory = tempDirectory;
	}

	public String getWorkspace() {
		return workspace;
	}

	public void setWorkspace(String workspace) {
		this.workspace = workspace;
	}

	/**
	 * 返回项目中的所有FFTable
	 * 
	 * @return FFTable数组
	 */
	public FFTable[] getAllFFT() {
		int count = getFFTCount();
		if (count == 0)
			return null;
		FFTable[] ret = new FFTable[count];
		for (int i = 0; i < count; i++) {
			ret[i] = (FFTable) FFTCollection.get(i);
		}
		return ret;

	}
	/**
	 * 返回项目中的所有计算完成的FFTable
	 * 
	 * @return FFTable数组
	 */
	public FFTable[] getAllCalculatedFFT() {
		FFTable[] ffts=getAllFFT();
		ArrayList list=new ArrayList();
		for(int i=0;i<ffts.length;i++)
		{
			if(ffts[i].calculated)list.add(ffts[i]);
		}
		FFTable[] ret=new FFTable[list.size()];
		for(int i=0;i<ret.length;i++)
		{
			ret[i]=(FFTable) list.get(i);
		}
		return ret;

	}

	public int getFFTCount() {
		return FFTCollection.size();
	}

	public FFTable getFFTAt(int idx) {
		Object o = FFTCollection.get(idx);

		return (o instanceof FFTable) ? (FFTable) o : null;

	}

	/*
	 * public void startAClassifyProcess() {
	 * 
	 *  }
	 * 
	 * public void endAClassifyProcess() { // TODO Auto-generated method stub
	 *  }
	 */
	public void addFFT(FFTable inFFT) {
		FFTCollection.add(inFFT);
	}

	public void deleteFFT(int idx) {
		FFTCollection.remove(idx);
	}

	/**
	 * 清空当前项目的FFT集合
	 * 
	 * @return
	 */
	public boolean deleteAllFFT() {
		return FFTCollection.removeAll(FFTCollection);
	}

	/**
	 * 删除指定的FFT
	 * 
	 * @param dest
	 * @return
	 */
	public boolean deleteFFT(FFTable dest) {
		return FFTCollection.remove(dest);
	}

	public BGCMap getBgMap() {
		return bgMap;
	}

	public void setBgMap(BGCMap bgMap) {
		this.bgMap = bgMap;
	}

	public RangeCMap getClassifyRange() {
		return classifyRange;
	}

	public void setClassifyRange(RangeCMap inClassifyRange) {
		classifyRange = inClassifyRange;
	}

	/*
	 * public double getAreaPresicion() { return areaPresicion; }
	 * 
	 * 
	 * 
	 * public void setAreaPresicion(double inAreaPresicion) {
	 * areaPresicion=inAreaPresicion; }
	 */

	public double getCellSize() {
		return cellSize;
	}

	public void setCellSize(double inCellSize) {
		cellSize = inCellSize;

	}

	public IRasterAnalysisEnvironment getRasterAnalysisEnvironment()
			throws Exception, IOException {
		// rasterAnalysisEnvironment=new
		RasterAnalysis ra = new RasterAnalysis();
		ra.setAsNewDefaultEnvironment();
		// Cell Size
		ra.setCellSize(esriRasterEnvSettingEnum.esriRasterEnvValue, new Double(
				getCellSize()));
		// output File Path:
		IWorkspaceFactory wkspfactory = new RasterWorkspaceFactory();
		IWorkspace wksp = new IWorkspaceProxy(wkspfactory.openFromFile(
				getTempDirectory(), 0));
		ra.setOutWorkspaceByRef(wksp);
		// Use current ClassifyRange to set Extent & Out Spatial Reference
		IFeatureClass tempFc = getClassifyRange().getFeatureClass();
		IGeoDataset geoDataset = new IGeoDatasetProxy(tempFc);
		ra.setOutSpatialReferenceByRef(geoDataset.getSpatialReference());

		// set Analysis Mask
		ra.setMaskByRef(geoDataset);
		IEnvelope envProvider = geoDataset.getExtent();
		/*
		System.out.println("Feature_Extent_Name:" + tempFc.toString());
		System.out.println("SR_Name:"
				+ geoDataset.getSpatialReference().getName());
		System.out.println("XMax:" + envProvider.getXMax());
		System.out.println("XMin:" + envProvider.getXMin());
		System.out.println("YMax:" + envProvider.getYMax());
		System.out.println("XMin:" + envProvider.getYMin());*/
		Object snapProvider = null;
		ra
				.setExtent(
						com.esri.arcgis.geoanalyst.esriRasterEnvSettingEnum.esriRasterEnvValue,
						envProvider, snapProvider);

		// ra.setExtent(com.esri.arcgis.geoanalyst.esriRasterEnvSettingEnum.esriRasterEnvValue,tempFc.getExtension(),null);
		return ra;
	}

	
	public boolean close() {
		// 删除临时文件夹下面的临时文件
		String temp = getTempDirectory();
		File f = new File(temp);
		
		try {
			GT.delAll(f);
			f.mkdir();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public ObstructCMap getObstruct() {
		return obstruct;
	}

	public void setObstruct(ObstructCMap obstruct) {
		this.obstruct = obstruct;
	}

}
