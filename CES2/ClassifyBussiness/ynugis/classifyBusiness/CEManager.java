/*
* @author 冯涛，创建日期：2003-10-31
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
package ynugis.classifyBusiness;

import java.util.ArrayList;

public class CEManager  {

	public ArrayList projectCollection;
	
	public CEManager() {
		projectCollection=new ArrayList();
		
	}
	/**
	 * 创建并获取一个ClassifyProject实例
	 * @return 创建并获取一个
	 * 
	 */
	public ClassifyProject createProject() {
		//TODO:在CES系统中暂时只支持一个项目
		
		ClassifyProject cp=new ClassifyProject();
		projectCollection.add(cp);
		return cp;
	}

	

	/**
	 * 从CEManeger中删除指定项目
	 * @param cp 指定项目
	 * @return 
	 */
	public boolean closeProject(ClassifyProject cp) {
		
		cp.close();
		projectCollection=new ArrayList();
		return true;
		
	}
	
	/**
	 * 获取当前CEManager管理的项目数量
	 * @return
	 */
	public int getCurrentProjectCount()
	{
		return projectCollection.size();
	}
	/**
	 * 获取CEManager中项目的实例（index = 0）
	 * @return ClassifyProject
	 */
	public ClassifyProject getProject()
	{
		if(projectCollection.size()==0)return null;
		return (ClassifyProject) projectCollection.get(0);
	}
	
	/**
	 * 获取CEManager中指定索引的项目的实例
	 * @param 项目索引
	 * @return
	 */
	public ClassifyProject getProject(int idx)
	{
		Object o=projectCollection.get(idx);
		if( o instanceof ClassifyProject)
		{
			return (ClassifyProject)o;
		}
		return null;
	}
	
	
	/**
	 * 打开项目文件（.xml格式）
	 * @param xmlPath .xml格式项目文件的路径
	 * @return 
	 */
	public ClassifyProject openProject(String xmlPath)
	{
		ClassifyProject cp=new ClassifyProject(xmlPath);
		projectCollection.add(cp);
		return cp;
		
	}
	protected void finalize() throws Throwable {
		//关闭项目集合中的所用项目
		System.out.println("finalize be run...");
		for(int i=0;i<projectCollection.size();i++)
		{
			((ClassifyProject)projectCollection.get(i)).close();
		}
		super.finalize();
	}
	
}
