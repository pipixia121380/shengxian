package shengxian.model;

import java.sql.Timestamp;

public class BeanUser {
	public static BeanUser currentLoginUser=null;
	public String user_id,user_pwd;
	public Timestamp register_time;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	public Timestamp getRegister_time() {
		return register_time;
	}
	public void setRegister_time(Timestamp register_time) {
		this.register_time = register_time;
	}
	
}