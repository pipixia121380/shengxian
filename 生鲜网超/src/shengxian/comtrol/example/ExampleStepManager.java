package shengxian.comtrol.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import shengxian.itf.IStepManager;
import shengxian.model.BeanPlan;
import shengxian.model.BeanStep;
import shengxian.model.BeanUser;
import shengxian.util.BaseException;
import shengxian.util.BusinessException;
import shengxian.util.DBUtil;
import shengxian.util.DbException;

public class ExampleStepManager implements IStepManager {

	@Override
	public void add(BeanPlan plan, String name, String planstartdate,
			String planfinishdate) throws BaseException {
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select max(step_order) from tbl_step where plan_id=? ";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1,plan.getPlan_id());
			java.sql.ResultSet rs=pst.executeQuery();
			int a=0;
			if(rs.next()) 
			a=rs.getInt(1);
			sql="insert into tbl_step(plan_id,step_order,step_name,plan_begin_time,plan_end_time) values(?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, plan.getPlan_id());
			pst.setInt(2, a+1);
			pst.setString(3, name);
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			pst.setTimestamp(4,new Timestamp(format.parse(planstartdate).getTime()));
			pst.setTimestamp(5,new Timestamp(format.parse(planfinishdate).getTime()));
			pst.execute();
			sql="update tbl_plan set step_count=step_count+1 where plan_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, plan.getPlan_id());
			pst.execute();
			pst.close();
		} catch (Exception e) {
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

	@Override
	public List<BeanStep> loadSteps(BeanPlan plan) throws BaseException {
		List<BeanStep> result=new ArrayList<BeanStep>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select step_id,plan_id,step_order,step_name,plan_begin_time,plan_end_time,real_begin_time,real_end_time from tbl_step where plan_id=? order by step_order";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, plan.getPlan_id());
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()) {
			BeanStep p=new BeanStep();
			p.setStep_id(rs.getInt(1));
			p.setPlan_id(rs.getInt(2));
			p.setStep_order(rs.getInt(3));
			p.setStep_name(rs.getString(4));
			p.setPlan_begin_time(rs.getTimestamp(5));
			p.setPlan_end_time(rs.getTimestamp(6));
			p.setReal_begin_time(rs.getTimestamp(7));
			p.setReal_end_time(rs.getTimestamp(8));
		    result.add(p);
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
	public void deleteStep(BeanStep step) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql="delete from tbl_step where step_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, step.getStep_id());
			pst.execute();
			sql="update tbl_step set step_order=0-step_order where plan_id=? and step_order>?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, step.getPlan_id());
			pst.setInt(2,step.getStep_order());
			pst.execute();
			sql="update tbl_step set step_order=-1-step_order where plan_id=? and step_order<0";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, step.getPlan_id());
			pst.execute();
			sql="update tbl_plan set step_count=step_count-1 where plan_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, step.getPlan_id());
			pst.execute();
			pst.close();
			conn.commit();
		}catch (SQLException e) {
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

	@Override
	public void startStep(BeanStep step) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql="update tbl_step set real_begin_time=now() where step_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, step.getStep_id());
			pst.execute();
			sql="update tbl_plan set start_step_count=start_step_count+1 where plan_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, step.getPlan_id());
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

	@Override
	public void finishStep(BeanStep step) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql="update tbl_step set real_end_time=now() where step_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, step.getStep_id());
			pst.execute();
			sql="update tbl_plan set finished_step_count=finished_step_count+1 where plan_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, step.getPlan_id());
			pst.execute();
			sql="update tbl_plan set start_step_count=start_step_count-1 where plan_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, step.getPlan_id());
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

	@Override
	public void moveUp(BeanStep step) throws BaseException {
		// TODO Auto-generated method stub
		int st=step.getStep_order();
		if(st==1)
			throw new BusinessException("第一条无法上移");
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="update tbl_step set step_order=0-step_order where step_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, step.getStep_id());
			pst.execute();
			sql="update tbl_step set step_order=step_order+1 where step_order=? and plan_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, st-1);
			pst.setInt(2, step.getPlan_id());
			pst.execute();
			sql="update tbl_step set step_order=-1-step_order where step_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, step.getStep_id());
			pst.execute();
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

	@Override
	public void moveDown(BeanStep step) throws BaseException {
		// TODO Auto-generated method stub
		int st=step.getStep_order();
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select max(step_order) from tbl_step where plan_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, step.getPlan_id());
			java.sql.ResultSet rs=pst.executeQuery();
			rs.next();
			if(st==rs.getInt(1))
			throw new BusinessException("最后一条无法下移");
			rs.close();
			sql="update tbl_step set step_order=0-step_order where step_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, step.getStep_id());
			pst.execute();
			sql="update tbl_step set step_order=step_order-1 where step_order=? and plan_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, st+1);
			pst.setInt(2, step.getPlan_id());
			pst.execute();
			sql="update tbl_step set step_order=1-step_order where step_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, step.getStep_id());
			pst.execute();
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