

import java.util.ArrayList;
import java.util.List;

public class content3 {
	private List list=new ArrayList();
	public content3(){
		
    }
	public  List getnames(){
		if(list.isEmpty()){
		Info1 f1=new Info1();
		f1.setname("容积率编码");
		f1.setdata("");
		list.add(f1);
		
		Info1 f2=new Info1();
		f2.setname("实际容积率区段");
		f2.setdata("");
		list.add(f2);
		}
		
    return list;
}
	public void addtask( Info1 task ){
        list.add(task);
}
	public void remove(Info1 task){
		list.remove(task);
	}
}