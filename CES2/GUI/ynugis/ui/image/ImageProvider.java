/**
 * @author yddy,create date 2003-10-15 Blog : http://yddysy.blogcn.net
 */
package ynugis.ui.image;

import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.widgets.Display;

public class ImageProvider {
	/**
	 *   清空地图图层
	 */
	public static final String ICON_BLANK="new";
	/**
	 * 打开MXD文件
	 */
	public static final String ICON_OPEN="open";
	/**
	 * 保存地图
	 */
	public static final String ICON_SAVE="save";
	/**
	 * 添加图层
	 */
	public static final String ICON_LAYER="addlayer";
	/**
	 * 应用程序图标
	 */
	public static final String ICON_LOGO="logo";
	/**
	 * 退出系统
	 */
	public static final String ICON_EXIT="exit";
	/**
	 * 关于我们
	 */
	public static final String ICON_ABOUT="about";
	/**
	 * 新建项目
	 */
	public static final String ICON_PRONEW="proNew";
	/**
	 * 打开项目
	 */
	public static final String ICON_PROOPEN="proOpen";
	/**
	 * 检查项目
	 */
	public static final String ICON_PROCHECK="proCheck";
	/**
	 * 关闭项目
	 */
	public static final String ICON_PROCLOSE="proClose";
	/**
	 * 保存项目
	 */
	public static final String ICON_PROSAVE="proSave";
	/**
	 * 保存项目至指定路径
	 */
	public static final String ICON_PROSAVE2="proSave2";
	/**
	 * 新建因素因子树
	 */
	public static final String ICON_TOLFFT="toolfft";
	/**
	 * 地图输出
	 */
	public static final String ICON_TOLMAP="toolmap";
	/**
	 * 地图格式转换
	 */
	public static final String ICON_TOLTRS="tooltrs";
	/**
	 * 图层透明度设置
	 */
	public static final String ICON_TRSP="trans";
	/**
	 * 图层顺序设置
	 */
	public static final String ICON_SEQ="seq";
	/**
	 * 图层属性表
	 */
	public static final String ICON_ATTR="attr";
	/**
	 * 公共属性信息
	 */
	public static final String ICON_ESTINFO="estInfo";
	/**
	 * 定级范围
	 */
	public static final String ICON_RANGE="range";
	/**
	 * 单元格值
	 */
	public static final String ICON_CELL="cell";
	/**
	 * 商业用地估价
	 */
	public static final String ICON_BUS="estBus";
	/**
	 * 居住用地估价
	 */
	public static final String ICON_DWL="estDwl";
	/**
	 * 工业用地估价
	 */
	public static final String ICON_IND="estInd";
	/**
	 * 地图打印
	 */
	public static final String ICON_PRINT="mapPrint";
	/**
	 * 着色
	 */
	public static final String ICON_RENDER="mapRender";
	/**
	 * MapGis调用
	 */
	public static final String ICON_MAPGIS="mapgis";
	/**
	 * 标注图层
	 */
	public static final String ICON_LABEL="label";
	/**
	 * 因数因子--特尔菲法
	 */
	public static final String ICON_DELPHI="fftd";
	/**
	 * 因数因子--层次分析法
	 */
	public static final String ICON_AHP="ffta";
	/**
	 * 因数因子--成对比较法
	 */
	public static final String ICON_BICO="fftb";
	/**
	 * 使用帮助
	 */
	public static final String ICON_HELP="help";
	private final String[] imageNames=new String[]{"attr","help","ffta","fftb","fftd","label","mapgis","mapRender","mapPrint","estBus","estDwl","estInd","cell","range","estInfo","seq","trans","about","tooltrs","toolmap","toolfft","new","open","save","addlayer","logo","exit","proNew","proOpen","proCheck","proClose","proSave","proSave2"};
	private ImageRegistry	imageRegistry;


	public ImageProvider() throws Exception{
		if(imageRegistry==null){
			System.out.println(Display.getDefault());
			imageRegistry = new ImageRegistry(Display.getDefault());
			String gifName;
			for(int i=0;i<imageNames.length;i++){
				gifName=imageNames[i];
				imageRegistry.put(gifName, ImageDescriptor.createFromURL(new URL(
				"file:icons/"+gifName+".gif")));
			}
		}
	}

	/**
	 * @return Returns the imageRegistry.
	 */
	public ImageRegistry getImageRegistry() {
		return imageRegistry;
	}

}
