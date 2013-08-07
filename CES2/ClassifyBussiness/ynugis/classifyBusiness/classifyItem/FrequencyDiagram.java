/**
 * @(#) FrequencyDiagram.java
 */

package ynugis.classifyBusiness.classifyItem;

import java.io.IOException;
import java.net.UnknownHostException;

import org.eclipse.swt.widgets.Shell;

import ynugis.ui.confDialog.FrequencyDialog;

public class FrequencyDiagram extends CDiagram
{

	private CellValueCMap cellValueMap;
	public FrequencyDiagram(CellValueCMap map) {
		cellValueMap=map;
		
	}

	

	public void show() {
		FrequencyDialog f=new FrequencyDialog(new Shell(),cellValueMap.getAsRaster());
		f.open();
	}


	public void saveAs(String path) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		
	}
	
}
