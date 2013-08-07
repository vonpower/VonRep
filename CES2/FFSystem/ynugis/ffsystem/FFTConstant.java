/*
* @author 冯涛，创建日期：2003-11-7
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
package ynugis.ffsystem;

public class FFTConstant {
	public static final String FUNCTION_SCORE_FIELD="作用分";
	public static final String FUNCTION_RANGE_FIELD="作用半径";
	public static final String FUNCTION_RANK_FIELD="等级";
	/**
	 * Eij=POW(F*,(1-di/d))
	 */
	public static final int FORMULA_EXPONENTIAL_ATTENUATION=0;
	/**
	 * Eij=F * (1 - di/d)
	 */
	public static final int FORMULA_LINEAR_ATTENUATION=1;
	
	/**
	 * 综合定级
	 */
	public static final int INTEGRATION_CLASSIFY=0;
	/**
	 * 商业用地定级
	 */
	public static final int BUSINESS_CLASSIFY=1;
	/**
	 * 工业用地定级
	 */
	public static final int INDUSTRY_CLASSIFY=2;
	/**
	 * 住宅用地定级
	 */
	public static final int RESIDENCE_CLASSIFY=3;
	
	
}
