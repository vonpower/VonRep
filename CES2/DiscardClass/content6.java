

import java.util.ArrayList;
import java.util.List;

public class content6 {
	private List list=new ArrayList();
	public content6(){
		
    }
	public  List getnames(){
		if(list.isEmpty()){
		Info1 f1=new Info1();
		f1.setname("临街宽度");
		f1.setdata("");
		list.add(f1);
		
		Info1 f2=new Info1();
		f2.setname("工业修正系数");
		f2.setdata("");
		list.add(f2);
		
		Info1 f3=new Info1();
		f3.setname("商业修正系数");
		f3.setdata("");
		list.add(f3);
		
		Info1 f4=new Info1();
		f4.setname("住宅修正系数");
		f4.setdata("");
		list.add(f4);}
    return list;
}
	public void addtask( Info1 task ){
        list.add(task);
}
	public void remove(Info1 task){
		list.remove(task);
	}
}
