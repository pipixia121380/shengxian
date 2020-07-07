package shengxian;

import shengxian.comtrol.example.ExamplePlanManager;
import shengxian.comtrol.example.ExampleStepManager;
import shengxian.comtrol.example.ExampleUserManager;
import shengxian.itf.IPlanManager;
import shengxian.itf.IStepManager;
import shengxian.itf.IUserManager;

public class PersonPlanUtil {
	public static IPlanManager planManager=new ExamplePlanManager();//需要换成自行设计的实现类
	public static IStepManager stepManager=new ExampleStepManager();//需要换成自行设计的实现类
	public static IUserManager userManager=new ExampleUserManager();//需要换成自行设计的实现类
	
}
