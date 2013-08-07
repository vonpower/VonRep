/**
 * @(#) CalculateItem.java
 */

package ynugis.classifyBusiness.classifyItem;


public abstract class ClassifyItem 
{
	private String name;
	
	public void setName(String inName )
	{
		name=inName;
		System.out.println(name);
	}
	
	public String getName()
	{
		return name;
	}
	
	
}
