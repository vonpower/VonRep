/*
* @author 冯涛，创建日期：2003-11-25
* blog--http://apower.blogone.net
* QQ:17463776
* MSN:apower511@msn.com
* E-Mail:VonPower@Gmail.com
*/
package ynugis.ffsystem;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;

import org.eclipse.core.runtime.IProgressMonitor;

import com.FFT.DerivedFacetType;
import com.FFT.FFTDoc;
import com.esri.arcgis.geodatabase.IGeoDataset;

public class DerivedFacet extends Facet {

	

	public boolean canCalculate() {

		return (srcFiles != null);
	}

	public IGeoDataset calculate(CalculatorInfo calculInfo)
			throws UnknownHostException, IOException, Exception {
		return calculateLeafFacet(calculInfo);
	}

	public DerivedFacet(DerivedFacetType dft) throws Exception {
		super();
		name = dft.getName().getValue();
		setFormula(dft.getFormula().getValue());
		weight = dft.getWeight().getValue();

		int count = dft.getDataPathCount();
		srcFiles = new String[count];
		for (int i = 0; i < count; i++) {
			srcFiles[i] = dft.getDataPathAt(i).getValue();
		}

	}

	public DerivedFacet() {
		super();
	}
	public DerivedFacetType getAsDerivedFacetType() throws Exception
	{
		FFTDoc doc = new FFTDoc();
		DerivedFacetType dft=new DerivedFacetType(doc,"http://my.opera.com/VonPower/","", "DerivedFacet");
		dft.addName(name);
		dft.addWeight(Double.toString(weight));
		dft.addFormula(getFormula()+"");
		if(getSrcFiles()!=null&&getSrcFiles().length>0)
		{
		for(int i=0;i<getSrcFiles().length;i++)
		{
			dft.addDataPath(getSrcFiles()[i]);
		}
		}	
		return dft;
	}

	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		// TODO Auto-generated method stub
		
	}
	
}
