package shengxian;

import shengxian.comtrol.example.ExamplePlanManager;
import shengxian.comtrol.example.ExampleStepManager;
import shengxian.comtrol.example.ExampleUserManager;
import shengxian.itf.IPlanManager;
import shengxian.itf.IStepManager;
import shengxian.itf.IUserManager;

public class PersonPlanUtil {
	public static IPlanManager planManager=new ExamplePlanManager();//��Ҫ����������Ƶ�ʵ����
	public static IStepManager stepManager=new ExampleStepManager();//��Ҫ����������Ƶ�ʵ����
	public static IUserManager userManager=new ExampleUserManager();//��Ҫ����������Ƶ�ʵ����
	
}
