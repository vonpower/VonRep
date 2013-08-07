/*
* @author 冯涛，创建日期：2003-11-25
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
package ynugis.estimate.data;
public class RevisePair {
	private double start;
	private double end;
	private double reviseValue;
	
	public RevisePair(double start, double end, double reviseValue) {
		super();
		this.start = start;
		this.end = end;
		this.reviseValue = reviseValue;
	}
	/**
	 * @return Returns the end.
	 */
	public double getEnd() {
		return end;
	}
	/**
	 * @param end The end to set.
	 */
	public void setEnd(double end) {
		this.end = end;
	}
	/**
	 * @return Returns the reviseValue.
	 */
	public double getReviseValue() {
		return reviseValue;
	}
	/**
	 * @param reviseValue The reviseValue to set.
	 */
	public void setReviseValue(double reviseValue) {
		this.reviseValue = reviseValue;
	}
	/**
	 * @return Returns the start.
	 */
	public double getStart() {
		return start;
	}
	/**
	 * @param start The start to set.
	 */
	public void setStart(double start) {
		this.start = start;
	}
	/**
	 *整理revisePair 保证start小于等于end 
	 */
	public void tidy()
	{
		if(start>end)
		{
			double temp=start;
			start=end;
			end=temp;
		}
	}
	
	
}
