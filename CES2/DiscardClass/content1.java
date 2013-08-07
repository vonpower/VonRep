

import java.util.ArrayList;
import java.util.List;

public class content1 {
	private List list=new ArrayList();
	public content1(){
		
    }
	public  List getnames(){
		if(list.isEmpty()){
		{Info1 f1=new Info1();
		f1.setname("土地还原率");
		f1.setdata("");
		list.add(f1);}
		{
		 Info1 f2=new Info1();
		 f2.setdata("");
		 f2.setname("房屋还原率");
		 list.add(f2);
		}
		{Info1 f3=new Info1();
		 f3.setdata("");
		 f3.setname("银行利息率");
		 list.add(f3);
			
		}
		{
			Info1 f4=new Info1();
			 f4.setdata("");
			 f4.setname("钢混结构耐用年限");
			 list.add(f4);
		}
		{
			Info1 f5=new Info1();
			 f5.setdata("");
			 f5.setname("钢混结构重置价");
			 list.add(f5);
		}
		{Info1 f6=new Info1();
		 f6.setdata("");
		 f6.setname("一等砖混结构耐用年限");
		 list.add(f6);
			
		}
		{
			Info1 f7=new Info1();
			 f7.setdata("");
			 f7.setname("一等砖混结构残置率");
			 list.add(f7);
		}
		{
			Info1 f8=new Info1();
			 f8.setdata("");
			 f8.setname("一等砖混结构重置价 (元/平方米)");
			 list.add(f8);
			 
		}
		{
			Info1 f9=new Info1();
			 f9.setdata("");
			 f9.setname("二等砖混结构耐用年限");
			 list.add(f9);
		}
		{
			Info1 f10=new Info1();
			 f10.setdata("");
			 f10.setname("二等砖混结构残置率");
			 list.add(f10);
		}
		{
			Info1 f11=new Info1();
			 f11.setdata("");
			 f11.setname("二等砖混结构重置价 (元/平方米)");
			 list.add(f11);
		}
		{
			Info1 f12=new Info1();
			 f12.setdata("");
			 f12.setname("砖木结构耐用年限");
			 list.add(f12);
		}
		{
			Info1 f13=new Info1();
			 f13.setdata("");
			 f13.setname("砖木结构残置率");
			 list.add(f13);
		}
		{
			Info1 f14=new Info1();
			 f14.setdata("");
			 f14.setname("砖木结构重置价");
			 list.add(f14);
		}
		{
		Info1 f24=new Info1();
		 f24.setdata("");
		 f24.setname("二等砖木结构耐用年限");
		 list.add(f24);}
		{
			Info1 f25=new Info1();
			 f25.setdata("");
			 f25.setname("二等砖木结构残置率");
			 list.add(f25);
		}
		{
			Info1 f26=new Info1();
			 f26.setdata("");
			 f26.setname("二等砖木结构重置价");
			 list.add(f26);
		}
		{
			Info1 f27=new Info1();
			 f27.setdata("");
			 f27.setname("三等砖木结构耐用年限");
			 list.add(f27);}
			{
				Info1 f28=new Info1();
				 f28.setdata("");
				 f28.setname("三等砖木结构残置率");
				 list.add(f28);
			}
			{
				Info1 f29=new Info1();
				 f29.setdata("");
				 f29.setname("三等砖木结构重置价");
				 list.add(f29);
			}
			{
				Info1 f30=new Info1();
				 f30.setdata("");
				 f30.setname("土木结构耐用年限");
				 list.add(f30);}
				{
					Info1 f31=new Info1();
					 f31.setdata("");
					 f31.setname("土木结构残置率");
					 list.add(f31);
				}
				{
					Info1 f32=new Info1();
					 f32.setdata("");
					 f32.setname("土木结构重置价");
					 list.add(f32);
				}	
		{
			Info1 f15=new Info1();
			 f15.setdata("");
			 f15.setname("简易结构耐用年限");
			 list.add(f15);
		}
		{
			Info1 f16=new Info1();
			 f16.setdata("");
			 f16.setname("简易结构残置率");
			 list.add(f16);
		}
		{
			Info1 f17=new Info1();
			 f17.setdata("");
			 f17.setname("简易结构重置价 (元/平方米)");
			 list.add(f17);
		}
		{
			Info1 f18=new Info1();
			 f18.setdata("");
			 f18.setname("房产税费");
			 list.add(f18);
		}
		{
			Info1 f19=new Info1();
			 f19.setdata("");
			 f19.setname("管理费率");
			 list.add(f19);
		}
		{
			Info1 f20=new Info1();
			 f20.setdata("");
			 f20.setname("维修费率");
			 list.add(f20);
		}
		{
			Info1 f21=new Info1();
			 f21.setdata("");
			 f21.setname("保险费率");
			 list.add(f21);
		}
		{
			Info1 f22=new Info1();
			 f22.setdata("");
			 f22.setname("基准地价对应期日");
			 list.add(f22);
		}
		{
			Info1 f23=new Info1();
			 f23.setdata("");
			 f23.setname("交易税费率");
			 list.add(f23);
		}}
		return list;
	}
	public void addtask( content1 task ){
	        list.add(task);
	        
	
}
	public void remove(Info1 task){
		list.remove(task);
	}
}