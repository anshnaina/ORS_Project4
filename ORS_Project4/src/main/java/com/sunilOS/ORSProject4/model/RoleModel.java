package com.sunilOS.ORSProject4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.RoleBean;
import com.sunilOS.ORSProject4.exception.DuplicateRecordException;
import com.sunilOS.ORSProject4.utility.JDBCDataSource;


public class RoleModel {
	
	private static long pk;
	private static Connection conn;
	private static PreparedStatement ps;
	private static ResultSet rs;
    private static Logger log = Logger.getLogger(RoleModel.class);

	
	public static long nextPK() throws Exception
	{
		log.debug("RoleModel nextPK method started");

		pk = 0;
		try
		{
		conn = JDBCDataSource.getConnection();
		ps = conn.prepareStatement("SELECT MAX(ID) FROM ST_ROLE");
		
		rs = ps.executeQuery();
		
		while(rs.next())
		{
			pk = rs.getInt(1);
		}
		}
		catch(SQLException e)
		{e.getStackTrace();}
		rs.close();
		 return pk+1;
	}
	
	public static long add (RoleBean bean) throws Exception
	{
		log.debug("RoleModel add method started");

		Connection conn=null;
		
		
//		RoleBean duplicateRole=findByRoleName(bean.getRoleName());
//		if(duplicateRole!=null){
//			throw new DuplicateRecordException("Role already exist");
//			
//		}
		
		pk = nextPK();
		try
		{
		conn = JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		ps = conn.prepareStatement("INSERT INTO ST_ROLE VALUES (?,?,?,?,?,?,?)");
		
		ps.setLong(1,pk);
		ps.setString(2,bean.getRoleName());
		ps.setString(3,bean.getDescription());
		ps.setString(4,bean.getCreatedBy());
		ps.setString(5,bean.getModifiedBy());
		ps.setTimestamp(6,bean.getCreatedDateTime());
		ps.setTimestamp(7,bean.getModifiedDateTime());
		
		pk = ps.executeUpdate();
		conn.commit();
		}
		catch(Exception e)
		{conn.rollback();}
		finally
		{
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}
	
	public static void delete (RoleBean bean) throws Exception
	{
		
		log.debug("RoleModel delete method started");

		try
		{
		conn = JDBCDataSource.getConnection();
		ps = conn.prepareStatement("DELETE FROM ST_ROLE WHERE ID=?");
		
		ps.setLong(1, bean.getId());
		
		pk = ps.executeUpdate();
		}
		catch(Exception e)
		{e.getStackTrace();}
		finally
		{
			JDBCDataSource.closeConnection(conn);
		}
	}
	
	public static RoleBean findByRoleName (String roleName) throws Exception
	{
		log.debug("RoleModel findByRoleName method started");

		RoleBean bean = new RoleBean();
		
		try
		{
		conn = JDBCDataSource.getConnection();
		ps = conn.prepareStatement("SELECT * FROM ST_ROLE WHERE ROLE_NAME=?");
		
		ps.setString(1,roleName);
		
		rs = ps.executeQuery();
		
		while(rs.next())
		{
			bean.setId(rs.getLong(1));
			bean.setRoleName(rs.getString(2));
			bean.setDescription(rs.getString(3));
			bean.setCreatedBy(rs.getString(4));
			bean.setModifiedBy(rs.getString(5));
			bean.setCreatedDateTime(rs.getTimestamp(6));
			bean.setModifiedDateTime(rs.getTimestamp(7));
		}
		}
		catch(Exception e)
		{e.getStackTrace();}
		finally
		{
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	
	public RoleBean findByPK (long roleId) throws Exception
	{
		log.debug("RoleModel findByPK method started");

		RoleBean bean = new RoleBean();
		try
		{
		conn = JDBCDataSource.getConnection();
		ps = conn.prepareStatement("SELECT * FROM ST_ROLE WHERE ID=?");
		
		ps.setLong(1, roleId);
		
		rs = ps.executeQuery();
		
		while(rs.next())
		{
			bean.setId(rs.getLong(1));
			bean.setRoleName(rs.getString(2));
			bean.setDescription(rs.getString(3));
			bean.setCreatedBy(rs.getString(4));
			bean.setModifiedBy(rs.getString(5));
			bean.setCreatedDateTime(rs.getTimestamp(6));
			bean.setModifiedDateTime(rs.getTimestamp(7));
		}
		}
		catch(Exception e)
		{e.getStackTrace();}
		finally
		{
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	public static void update (RoleBean bean) throws Exception
	{
		log.debug("RoleModel update method started");

		try
		{
		conn = JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		ps = conn.prepareStatement("UPDATE ST_ROLE SET ROLE_NAME=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
		
		ps.setString(1,bean.getRoleName());
		ps.setString(2,bean.getDescription());
		ps.setString(3,bean.getCreatedBy());
		ps.setString(4,bean.getModifiedBy());
		ps.setTimestamp(5,bean.getCreatedDateTime());
		ps.setTimestamp(6,bean.getCreatedDateTime());
		ps.setLong(7, bean.getId());
		
		ps.executeUpdate();
		conn.commit();
		}
		catch(SQLException e)
		{conn.rollback();}
		finally
		{
			JDBCDataSource.closeConnection(conn);
		}
		}
	
	 public List list() throws Exception {
			
		 log.debug("RoleModel list method started");

	        return list(0, 0);
	    }
	 
	 public List list(int pageNo, int pageSize) throws Exception {
		 
			log.debug("RoleModel list(pageNo, pageSize) method started");

	        ArrayList roleList = new ArrayList();
	        StringBuffer sql = new StringBuffer("SELECT * FROM ST_ROLE");
	        // if page size is greater than zero then apply pagination
	        if (pageSize > 0) {
	            // Calculate start record index
	            pageNo = (pageNo - 1) * pageSize;
	            sql.append(" LIMIT " + pageNo + "," + pageSize);
	        }

	        Connection conn = null;

	        try {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                RoleBean bean = new RoleBean();
	                bean.setId(rs.getLong(1));
	                bean.setRoleName(rs.getString(2));
	                bean.setDescription(rs.getString(3));
	                bean.setCreatedBy(rs.getString(4));
	                bean.setModifiedBy(rs.getString(5));
	                bean.setCreatedDateTime(rs.getTimestamp(6));
	                bean.setModifiedDateTime(rs.getTimestamp(7));
	                roleList.add(bean);
	            }
	            rs.close();
	        } catch (Exception e) {
	            throw new Exception(
	                    "Exception : Exception in getting list of Role");
	        } 
	        finally 
	        {
	    		JDBCDataSource.closeConnection(conn);
	    	}	        
	        return roleList;

	    }
	 
	   /**
	     * Search Role
	     *
	     * @param bean
	     *            : Search Parameters
	     * @throws ApplicationException
	     */

	    public List search(RoleBean bean) throws Exception {
			
	    	log.debug("RoleModel search method started");

	        return search(bean, 0, 0);
	    }

	    /**
	     * Search Role with pagination
	     *
	     * @return list : List of Roles
	     * @param bean
	     *            : Search Parameters
	     * @param pageNo
	     *            : Current Page No.
	     * @param pageSize
	     *            : Size of Page
	     *
	     * @throws ApplicationException
	     */

	    public List search(RoleBean bean, int pageNo, int pageSize) throws Exception {
	    	
			log.debug("RoleModel search(bean, pageNo, pageSize) method started");

	        StringBuffer sql = new StringBuffer("SELECT * FROM ST_ROLE WHERE 1=1");

	        if (bean != null) {
	            if (bean.getId() > 0) {
	                sql.append(" AND ID = " + bean.getId());
	            }
	            if (bean.getRoleName() != null && bean.getRoleName().length() > 0) {
	                sql.append(" AND ROLE_NAME LIKE '" + bean.getRoleName() + "%'");
	            }
	            if (bean.getDescription() != null
	                    && bean.getDescription().length() > 0) {
	                sql.append(" AND DESCRIPTION LIKE '" + bean.getDescription()+ "%'");
	            }

	        }

	        // if page size is greater than zero then apply pagination
	        if (pageSize > 0) {
	            // Calculate start record index
	            pageNo = (pageNo - 1) * pageSize;

	            sql.append(" Limit " + pageNo + ", " + pageSize);
	            // sql.append(" limit " + pageNo + "," + pageSize);
	        }

	        ArrayList roleList = new ArrayList();
	        Connection conn = null;
	        try {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                bean = new RoleBean();
	                bean.setId(rs.getLong(1));
	                bean.setRoleName(rs.getString(2));
	                bean.setDescription(rs.getString(3));
	                bean.setCreatedBy(rs.getString(4));
	                bean.setModifiedBy(rs.getString(5));
	                bean.setCreatedDateTime(rs.getTimestamp(6));
	                bean.setModifiedDateTime(rs.getTimestamp(7));
	                roleList.add(bean);
	            }
	            rs.close();
	        } catch (Exception e) {
	            throw new Exception(
	                    "Exception : Exception in search Role");
	        } finally
	        {
				JDBCDataSource.closeConnection(conn);
			}

	        return roleList;
	    }
}
	
