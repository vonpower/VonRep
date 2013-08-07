/*
* @author 冯涛，创建日期：2003-11-25
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
package ynugis.estimate.data;
import java.util.ArrayList;

public class Revise {
	private ArrayList revisePairList;
	private String name;
	private String reviseFieldName;
	public Revise(String reviseFieldName) {
		super();
		this.reviseFieldName = reviseFieldName;
		revisePairList = new ArrayList();
		setName(reviseFieldName);
	}
	

	public Revise(String reviseFieldName,String reviseName) {
		super();
		this.reviseFieldName = reviseFieldName;
		revisePairList = new ArrayList();
		setName(reviseName);
	}
	
	public void addRevisePair(RevisePair rp) {
		revisePairList.add(rp);
	}
	public void removeRevisePair(int idx)
	{
		revisePairList.remove(idx);
		
	}
	public int getRevisePairCount()
	{
		return revisePairList.size();
	}
	public RevisePair getRevisePair(int index) {
		Object o = revisePairList.get(index);
		if (o instanceof RevisePair)
			return (RevisePair) o;
		return null;
	}

	public RevisePair[] getRevisePairs() {
		Object[] os = revisePairList.toArray();
		RevisePair ret[] = new RevisePair[os.length];

		for (int i = 0; i < ret.length; i++) {
			if (os[i] instanceof RevisePair)
				ret[i]=(RevisePair)os[i];
		}
		return ret;
	}

	/**
	 * @return Returns the reviseFieldName.
	 */
	public String getReviseFieldName() {
		return reviseFieldName;
	}

	/**
	 * @param reviseFieldName The reviseFieldName to set.
	 */
	public void setReviseFieldName(String reviseFieldName) {
		this.reviseFieldName = reviseFieldName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 整理当前Revise中的所用RevisePair，保证所有的RevisePair的start都 小于等于end 
	 */
	public void tidy()
	{
		int count=revisePairList.size();
		if(count==0)return;
		RevisePair rp;
		for(int i=0;i<count;i++)
		{
			rp=(RevisePair)revisePairList.get(i);
			rp.tidy();
		}
		
	}
	public void multiple(double multiple)
	{
		int count=revisePairList.size();
		if(count==0)return;
		RevisePair rp;
		
		for(int i=0;i<count;i++)
		{
			rp=(RevisePair)revisePairList.get(i);
			rp.setStart(rp.getStart()*multiple);
			rp.setEnd(rp.getEnd()*multiple);
		}
	}
	
}
