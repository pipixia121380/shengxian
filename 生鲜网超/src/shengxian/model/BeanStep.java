package shengxian.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class BeanStep {
	public String step_name;
	public Timestamp plan_begin_time,plan_end_time,real_begin_time,real_end_time;
	public int step_id,plan_id,step_order;
	public static final String[] tblStepTitle={"序号","名称","计划开始时间","计划完成时间","实际开始时间","实际完成时间"};
	/**
	 * 请自行根据javabean的设计修改本函数代码，col表示界面表格中的列序号，0开始
	 */
	public String getCell(int col){
		if(col==0) return step_order+"";
		else if(col==1) return step_name;
		else if(col==2) return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(plan_begin_time);
		else if(col==3) return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(plan_end_time);
		else if(col==4)
			if(real_begin_time!=null) 
				return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(real_begin_time);
			else return null;
		else if(col==5) 
			if(real_end_time!=null) 
				return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(real_end_time);
			else return null;
		else return "";
	}
	public String getStep_name() {
		return step_name;
	}
	public void setStep_name(String step_name) {
		this.step_name = step_name;
	}
	public Timestamp getPlan_begin_time() {
		return plan_begin_time;
	}
	public void setPlan_begin_time(Timestamp plan_begin_time) {
		this.plan_begin_time = plan_begin_time;
	}
	public Timestamp getPlan_end_time() {
		return plan_end_time;
	}
	public void setPlan_end_time(Timestamp plan_end_time) {
		this.plan_end_time = plan_end_time;
	}
	public Timestamp getReal_begin_time() {
		return real_begin_time;
	}
	public void setReal_begin_time(Timestamp real_begin_time) {
		this.real_begin_time = real_begin_time;
	}
	public Timestamp getReal_end_time() {
		return real_end_time;
	}
	public void setReal_end_time(Timestamp real_end_time) {
		this.real_end_time = real_end_time;
	}
	public int getStep_id() {
		return step_id;
	}
	public void setStep_id(int step_id) {
		this.step_id = step_id;
	}
	public int getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(int plan_id) {
		this.plan_id = plan_id;
	}
	public int getStep_order() {
		return step_order;
	}
	public void setStep_order(int step_order) {
		this.step_order = step_order;
	}
}
