package shengxian.comtrol.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import shengxian.itf.IPlanManager;
import shengxian.model.BeanPlan;
import shengxian.model.BeanUser;
import shengxian.util.BaseException;
import shengxian.util.BusinessException;
import shengxian.util.DBUtil;
import shengxian.util.DbException;

public class ExamplePlanManager implements IPlanManager {

	@Override
	public BeanPlan addPlan(String name) throws BaseException {
		// TODO Auto-generated method stub
		BeanPlan bp=new BeanPlan();
		if(name==null||"".equals(name))
			throw new BusinessException("计划名未提供");
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select max(plan_order) from tbl_plan where user_id=? ";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,BeanUser.currentLoginUser.getUser_id());
			java.sql.ResultSet rs=pst.executeQuery();
			int a=0;
			if(rs.next()) 
			a=rs.getInt(1);
			rs.close();
			pst.close();
			sql="insert into tbl_plan(user_id,plan_order,plan_name,create_time,step_count,start_step_count,finished_step_count) values(?,?,?,?,0,0,0)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUser_id());
			pst.setInt(2, a+1);
			pst.setString(3, name);
			java.sql.Timestamp time=new java.sql.Timestamp(System.currentTimeMillis());
			pst.setTimestamp(4,time);
			bp.setUser_id(BeanUser.currentLoginUser.getUser_id());
			bp.setPlan_order(a+1);
			bp.setPlan_name(name);
			bp.setCreate_time(time);
			bp.setStep_count(0);
			bp.setStart_step_count(0);
			bp.setFinished_step_count(0);
			pst.execute();
			pst.close();
			sql="select max(plan_id) from tbl_plan where user_id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,BeanUser.currentLoginUser.getUser_id());
			rs=pst.executeQuery();
			if(rs.next()) {
				bp.setPlan_id(rs.getInt(1));
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return bp;
	}

	@Override
	public List<BeanPlan> loadAll() throws BaseException {
		List<BeanPlan> result=new ArrayList<BeanPlan>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select plan_id,plan_order,plan_name,step_count,finished_step_count from tbl_plan where user_id=? order by plan_order";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,BeanUser.currentLoginUser.getUser_id());
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()) {
			BeanPlan bp=new BeanPlan();
			bp.setPlan_id(rs.getInt(1));
			bp.setPlan_order(rs.getInt(2));
			bp.setPlan_name(rs.getString(3));
			bp.setStep_count(rs.getInt(4));
			bp.setFinished_step_count(rs.getInt(5));
		    result.add(bp);
			}
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
		return result;
	}

	@Override
	public void deletePlan(BeanPlan plan) throws BaseException {
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql="select count(*) from tbl_step where plan_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1,plan.getPlan_id());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next())
				if(rs.getInt(1)>0)
				throw new BusinessException("存在步骤,无法删除");
			rs.close();
			sql="delete from tbl_plan where user_id=? and plan_order=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUser_id());
			pst.setInt(2,plan.getPlan_order());
			pst.execute();
			sql="update tbl_plan set plan_order=0-plan_order where user_id=? and plan_order>?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUser_id());
			pst.setInt(2,plan.getPlan_order());
			pst.execute();
			sql="update tbl_plan set plan_order=-1-plan_order where user_id=? and plan_order<0";
			pst=conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUser_id());
			pst.execute();
			pst.close();
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
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
