package shengxian.comtrol.example;

import java.sql.Connection;
import java.sql.SQLException;

import shengxian.util.BusinessException;
import shengxian.util.DBUtil;
import shengxian.util.DbException;
import shengxian.itf.IUserManager;
import shengxian.model.BeanUser;
import shengxian.util.BaseException;

public class ExampleUserManager implements IUserManager {

	@Override
	public BeanUser reg(String userid, String pwd,String pwd2) throws BaseException {
		// TODO Auto-generated method stub
		if(userid==null||"".equals(userid)){
			throw new BusinessException("用户名不能为空");
		}
		if(pwd==null||"".equals(pwd)){
			throw new BusinessException("密码不能为空");
		}
		if(!(pwd.equals(pwd2))){
			throw new BusinessException("密码不相同");
		}
		BeanUser bu=new BeanUser();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from tbl_user where user_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("用户名已存在");
			rs.close();
			pst.close();
			sql="insert into tbl_user(user_id,user_pwd,register_time) values(?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, userid);
			pst.setString(2, pwd);
			java.sql.Timestamp time=new java.sql.Timestamp(System.currentTimeMillis());
			pst.setTimestamp(3,time);
			bu.setUser_id(userid);
			bu.setUser_pwd(pwd);
			bu.setRegister_time(time);
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return bu;
	}

	
	@Override
	public BeanUser login(String userid, String pwd) throws BaseException {
		// TODO Auto-generated method stub
		BeanUser bu=new BeanUser();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from tbl_user where user_id=? and user_pwd=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			pst.setString(2,pwd);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("普通用户名不存在或密码错误");
			bu.setUser_id(rs.getString(1));
			bu.setUser_pwd(rs.getString(2));
			bu.setRegister_time(rs.getTimestamp(3));
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return bu;
	}

	@Override
	public BeanUser loginp(String userid, String pwd) throws BaseException {
		// TODO Auto-generated method stub
		BeanUser bu=new BeanUser();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from tbl_user where user_id=? and user_pwd=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			pst.setString(2,pwd);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("管理员名不存在或密码错误");
			bu.setUser_id(rs.getString(1));
			bu.setUser_pwd(rs.getString(2));
			bu.setRegister_time(rs.getTimestamp(3));
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return bu;
	}
	
	@Override
	public void changePwd(BeanUser user, String oldPwd, String newPwd,String newPwd2) throws BaseException {
		// TODO Auto-generated method stub
		if(!user.getUser_pwd().equals(oldPwd)) throw new BusinessException("原密码不正确");
		if(!(newPwd.equals(newPwd2))) throw new BusinessException("密码不相同");
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="update tbl_user set user_pwd=? where user_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, newPwd);
			pst.setString(2, user.getUser_id());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

}
