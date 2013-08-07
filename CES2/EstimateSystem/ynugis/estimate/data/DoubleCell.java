/*
* @author 冯涛，创建日期：2003-11-25
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
package ynugis.estimate.data;
public class DoubleCell {
	private double data;
	 private String name;
	 
	 public double getdata(){
		 return data;
		 
	 }
	 public void setdata(double data){
		 this.data =data;
	 }
	 public String getname(){
		 return name;
	 }
	 public void setname(String name){
		 this.name=name;
	 }
	public DoubleCell(double data, String name) {
		this.data = data;
		this.name = name;
	}
}
