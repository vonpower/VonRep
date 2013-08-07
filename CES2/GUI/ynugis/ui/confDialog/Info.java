package ynugis.ui.confDialog;



import java.util.ArrayList;
import java.util.List;

public class Info {
	private List list;
	
	public List getinfo() {
		list = new ArrayList();
		{
			content info1 = new content();
			info1.setstart(0);
			info1.setend(0);
			info1.setclassfy(0);
			list.add(info1);

			return list;

		}
//		
//	}

}
}