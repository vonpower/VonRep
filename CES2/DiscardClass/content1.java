

import java.util.ArrayList;
import java.util.List;

public class content1 {
	private List list=new ArrayList();
	public content1(){
		
    }
	public  List getnames(){
		if(list.isEmpty()){
		{Info1 f1=new Info1();
		f1.setname("���ػ�ԭ��");
		f1.setdata("");
		list.add(f1);}
		{
		 Info1 f2=new Info1();
		 f2.setdata("");
		 f2.setname("���ݻ�ԭ��");
		 list.add(f2);
		}
		{Info1 f3=new Info1();
		 f3.setdata("");
		 f3.setname("������Ϣ��");
		 list.add(f3);
			
		}
		{
			Info1 f4=new Info1();
			 f4.setdata("");
			 f4.setname("�ֻ�ṹ��������");
			 list.add(f4);
		}
		{
			Info1 f5=new Info1();
			 f5.setdata("");
			 f5.setname("�ֻ�ṹ���ü�");
			 list.add(f5);
		}
		{Info1 f6=new Info1();
		 f6.setdata("");
		 f6.setname("һ��ש��ṹ��������");
		 list.add(f6);
			
		}
		{
			Info1 f7=new Info1();
			 f7.setdata("");
			 f7.setname("һ��ש��ṹ������");
			 list.add(f7);
		}
		{
			Info1 f8=new Info1();
			 f8.setdata("");
			 f8.setname("һ��ש��ṹ���ü� (Ԫ/ƽ����)");
			 list.add(f8);
			 
		}
		{
			Info1 f9=new Info1();
			 f9.setdata("");
			 f9.setname("����ש��ṹ��������");
			 list.add(f9);
		}
		{
			Info1 f10=new Info1();
			 f10.setdata("");
			 f10.setname("����ש��ṹ������");
			 list.add(f10);
		}
		{
			Info1 f11=new Info1();
			 f11.setdata("");
			 f11.setname("����ש��ṹ���ü� (Ԫ/ƽ����)");
			 list.add(f11);
		}
		{
			Info1 f12=new Info1();
			 f12.setdata("");
			 f12.setname("שľ�ṹ��������");
			 list.add(f12);
		}
		{
			Info1 f13=new Info1();
			 f13.setdata("");
			 f13.setname("שľ�ṹ������");
			 list.add(f13);
		}
		{
			Info1 f14=new Info1();
			 f14.setdata("");
			 f14.setname("שľ�ṹ���ü�");
			 list.add(f14);
		}
		{
		Info1 f24=new Info1();
		 f24.setdata("");
		 f24.setname("����שľ�ṹ��������");
		 list.add(f24);}
		{
			Info1 f25=new Info1();
			 f25.setdata("");
			 f25.setname("����שľ�ṹ������");
			 list.add(f25);
		}
		{
			Info1 f26=new Info1();
			 f26.setdata("");
			 f26.setname("����שľ�ṹ���ü�");
			 list.add(f26);
		}
		{
			Info1 f27=new Info1();
			 f27.setdata("");
			 f27.setname("����שľ�ṹ��������");
			 list.add(f27);}
			{
				Info1 f28=new Info1();
				 f28.setdata("");
				 f28.setname("����שľ�ṹ������");
				 list.add(f28);
			}
			{
				Info1 f29=new Info1();
				 f29.setdata("");
				 f29.setname("����שľ�ṹ���ü�");
				 list.add(f29);
			}
			{
				Info1 f30=new Info1();
				 f30.setdata("");
				 f30.setname("��ľ�ṹ��������");
				 list.add(f30);}
				{
					Info1 f31=new Info1();
					 f31.setdata("");
					 f31.setname("��ľ�ṹ������");
					 list.add(f31);
				}
				{
					Info1 f32=new Info1();
					 f32.setdata("");
					 f32.setname("��ľ�ṹ���ü�");
					 list.add(f32);
				}	
		{
			Info1 f15=new Info1();
			 f15.setdata("");
			 f15.setname("���׽ṹ��������");
			 list.add(f15);
		}
		{
			Info1 f16=new Info1();
			 f16.setdata("");
			 f16.setname("���׽ṹ������");
			 list.add(f16);
		}
		{
			Info1 f17=new Info1();
			 f17.setdata("");
			 f17.setname("���׽ṹ���ü� (Ԫ/ƽ����)");
			 list.add(f17);
		}
		{
			Info1 f18=new Info1();
			 f18.setdata("");
			 f18.setname("����˰��");
			 list.add(f18);
		}
		{
			Info1 f19=new Info1();
			 f19.setdata("");
			 f19.setname("�������");
			 list.add(f19);
		}
		{
			Info1 f20=new Info1();
			 f20.setdata("");
			 f20.setname("ά�޷���");
			 list.add(f20);
		}
		{
			Info1 f21=new Info1();
			 f21.setdata("");
			 f21.setname("���շ���");
			 list.add(f21);
		}
		{
			Info1 f22=new Info1();
			 f22.setdata("");
			 f22.setname("��׼�ؼ۶�Ӧ����");
			 list.add(f22);
		}
		{
			Info1 f23=new Info1();
			 f23.setdata("");
			 f23.setname("����˰����");
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