/*
 * @author 冯涛，创建日期：2004-1-12
 * blog--http://apower.blogone.net
 * QQ:17463776
 * MSN:apower511@msn.com
 * E-Mail:VonPower@Gmail.com
 */
package ynugis.estimate.data;

import java.util.HashSet;

public class ClassifyInfo {

	public EstimateRank ranks[];

	public int rankCount;

	public ClassifyInfo(Revise inR) {

		RevisePair rps[] = inR.getRevisePairs();
		ranks = new EstimateRank[rps.length];
		HashSet mySet = new HashSet();
		for (int i = 0; i < ranks.length; i++) {
			ranks[i]=new EstimateRank();
			ranks[i].rank = rps[i].getReviseValue();
			ranks[i].parentRank = (int) ranks[i].rank;
			mySet.add(new Integer(ranks[i].parentRank));
		}
		rankCount = mySet.size();

	}

	public class EstimateRank {
		public double rank;

		public int parentRank;

		public double cellValueMean;

		public double landQuality_Xn;

		public double landIncomeMean_Yn;
	}

}
