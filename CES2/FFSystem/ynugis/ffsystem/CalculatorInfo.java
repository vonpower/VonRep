/*
* @author 冯涛，创建日期：2003-3-15
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
package ynugis.ffsystem;

import ynugis.classifyBusiness.classifyItem.ObstructCMap;
import ynugis.classifyBusiness.classifyItem.RangeCMap;

import com.esri.arcgis.geoanalyst.IRasterAnalysisEnvironment;

/**
 * 用以传递单元格分值的相关计算信息
 * @author Administrator
 *
 */
public class CalculatorInfo {
	private IRasterAnalysisEnvironment env;
	private ObstructCMap obstruct;
	private RangeCMap range;
	
	public CalculatorInfo(IRasterAnalysisEnvironment inEnv,RangeCMap inRange)
	{
		env=inEnv;
		range=inRange;
	}
	public CalculatorInfo(IRasterAnalysisEnvironment inEnv,RangeCMap inRange,ObstructCMap obstructCMap)
	{
		env=inEnv;
		range=inRange;
		obstruct=obstructCMap;
	}
	
	public IRasterAnalysisEnvironment getEnv() {
		return env;
	}
	public void setEnv(IRasterAnalysisEnvironment env) {
		this.env = env;
	}
	public ObstructCMap getObstruct() {
		return obstruct;
	}
	public void setObstruct(ObstructCMap obstructCMap) {
		this.obstruct = obstructCMap;
	}
	public RangeCMap getRange() {
		return range;
	}
	public void setRange(RangeCMap range) {
		this.range = range;
	}
	
}
