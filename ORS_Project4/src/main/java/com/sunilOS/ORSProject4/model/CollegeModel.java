package com.sunilOS.ORSProject4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.CollegeBean;
import com.sunilOS.ORSProject4.bean.StudentBean;
import com.sunilOS.ORSProject4.utility.JDBCDataSource;



public class CollegeModel {

	private static long pk;
	private static Connection conn;
	private static PreparedStatement ps;
	private static ResultSet rs;
    private static Logger log = Logger.getLogger(CollegeModel.class);


	public static long nextPK() throws Exception {
      
		log.debug("CollegeModel nextPK method started");

		long pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("SELECT MAX(ID) FROM ST_COLLEGE");

			rs = ps.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
			rs.close();
		return pk + 1;
	}

	public static long add(CollegeBean bean) throws Exception 
{
		log.debug("CollegeModel add method started");
		
		try 
		{
		conn = JDBCDataSource.getConnection();
		pk = nextPK();
		
		conn.setAutoCommit(false);
		ps = conn.prepareStatement("INSERT INTO ST_COLLEGE VALUES (?,?,?,?,?,?,?,?,?,?)");

		ps.setLong(1, pk);
		ps.setString(2, bean.getCollegeName());
		ps.setString(3, bean.getAddress());
		ps.setString(4, bean.getState());
		ps.setString(5, bean.getCity());
		ps.setString(6, bean.getMobileNo());
		ps.setString(7, bean.getCreatedBy());
		ps.setString(8, bean.getModifiedBy());
		ps.setTimestamp(9, bean.getCreatedDateTime());
		ps.setTimestamp(10, bean.getModifiedDateTime());

		pk = ps.executeUpdate();
		
		conn.commit();
		}
	
		catch(SQLException e)
		{
			conn.rollback();
		}
		finally
		{
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
}

	public static void delete(CollegeBean bean) throws Exception 
	{
		log.debug("CollegeModel delete method started");
		try 
		{
		conn = JDBCDataSource.getConnection();
		ps = conn.prepareStatement("DELETE FROM ST_COLLEGE WHERE ID=?");

		ps.setLong(1, bean.getId());

		pk = ps.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			JDBCDataSource.closeConnection(conn);
		}
	}

	public static CollegeBean findByCollegeName(String collegeName) throws Exception {
		
		log.debug("CollegeModel findByCollegeName method started");
		
		CollegeBean bean = new CollegeBean();
		try
		{
		conn = JDBCDataSource.getConnection();
		ps = conn.prepareStatement("SELECT * FROM ST_COLLEGE WHERE COLLEGE_NAME = ?");

		ps.setString(1, collegeName);

		rs = ps.executeQuery();

		while (rs.next()) {
			bean.setId(rs.getLong(1));
			bean.setCollegeName(rs.getString(2));
			bean.setAddress(rs.getString(3));
			bean.setState(rs.getString(4));
			bean.setCity(rs.getString(5));
			bean.setMobileNo(rs.getString(6));
			bean.setCreatedBy(rs.getString(7));
			bean.setModifiedBy(rs.getString(8));
			bean.setCreatedDateTime(rs.getTimestamp(9));
			bean.setModifiedDateTime(rs.getTimestamp(10));
		}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public static CollegeBean findByPK(long f) throws Exception {
	
		log.debug("CollegeModel findByPK method started");

		CollegeBean bean = new CollegeBean();
		try 
		{
		conn = JDBCDataSource.getConnection();
		ps = conn.prepareStatement("SELECT * FROM ST_COLLEGE WHERE ID=?");

		ps.setLong(1, f);

		rs = ps.executeQuery();

		while (rs.next()) {
			bean.setId(rs.getLong(1));
			bean.setCollegeName(rs.getString(2));
			bean.setAddress(rs.getString(3));
			bean.setState(rs.getString(4));
			bean.setCity(rs.getString(5));
			bean.setMobileNo(rs.getString(6));
			bean.setCreatedBy(rs.getString(7));
			bean.setModifiedBy(rs.getString(8));
			bean.setCreatedDateTime(rs.getTimestamp(9));
			bean.setModifiedDateTime(rs.getTimestamp(10));
		
		}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public static void update(CollegeBean bean) throws Exception {
		
		log.debug("CollegeModel update method started");

		try
		
		{
		conn = JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		ps = conn.prepareStatement("UPDATE ST_COLLEGE SET COLLEGE_NAME=?,ADDRESS=?,STATE=?,CITY=?,MOBILE_NO=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");

		ps.setString(1, bean.getCollegeName());
		ps.setString(2, bean.getAddress());
		ps.setString(3, bean.getState());
		ps.setString(4, bean.getCity());
		ps.setString(5, bean.getMobileNo());
		ps.setString(6, bean.getCreatedBy());
		ps.setString(7, bean.getModifiedBy());
		ps.setTimestamp(8, bean.getCreatedDateTime());
		ps.setTimestamp(9, bean.getModifiedDateTime());
		ps.setLong(10, bean.getId());
	
		pk = ps.executeUpdate();
		conn.commit();
		}
		catch(SQLException e)
		{
			conn.rollback();
		}
		finally
		{
			JDBCDataSource.closeConnection(conn);
		}
	}
	
	public List list() throws Exception
	{
		log.debug("CollegeModel list() method started");

		return list (0 , 0);	
	}
	
	public List list(int pageNo, int pageSize) throws Exception
	{
		
		log.debug("CollegeModel list(pageNo, pageSize) method started");
		
		ArrayList collegeList = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COLLEGE");
		
		if(pageSize > 0)
		{
			pageNo = (pageNo-1) * pageSize;
			sql.append("LIMIT" + pageNo + "," + pageSize);
		}
		try {
		conn = JDBCDataSource.getConnection();
		ps = conn.prepareStatement(sql.toString());
		rs = ps.executeQuery();
		
		while(rs.next())
		{
			CollegeBean bean = new CollegeBean();
			 bean.setId(rs.getLong(1));
             bean.setCollegeName(rs.getString(2));
             bean.setAddress(rs.getString(3));
             bean.setState(rs.getString(4));
             bean.setCity(rs.getString(5));
             bean.setMobileNo(rs.getString(6));
             bean.setCreatedBy(rs.getString(7));
             bean.setModifiedBy(rs.getString(8));
             bean.setCreatedDateTime(rs.getTimestamp(9));
             bean.setModifiedDateTime(rs.getTimestamp(10));
             collegeList.add(bean);  
		}
		}
		catch(Exception e)
		{
			throw new Exception("Exception : Exception in getting list of College");
		}
		finally 
	    {
	    	JDBCDataSource.closeConnection(conn);
	    }	
		return collegeList;
	}
	
	public List search (CollegeBean bean) throws Exception
	{
		log.debug("CollegeModel search() method started");
		
		return search(bean , 0 , 0);
	}
	
	public List search (CollegeBean bean , int pageNo , int pageSize) throws Exception
	{
		
		log.debug("CollegeModel search(bean, pageNo, pageSize) method started");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COLLEGE WHERE 1=1");
		 if (bean != null) {
	            if (bean.getId() > 0) {
	                sql.append(" AND id = " + bean.getId());
	            }
	            if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {
	                sql.append(" AND COLLEGE_NAME like '" + bean.getCollegeName() + "%'");
	            }
	            if (bean.getAddress() != null && bean.getAddress().length() > 0) {
	                sql.append(" AND ADDRESS like '" + bean.getAddress() + "%'");
	            }
	            if (bean.getState() != null && bean.getState().length() > 0) {
	                sql.append(" AND STATE like '" + bean.getState() + "%'");
	            }
	            if (bean.getCity() != null && bean.getCity().length() > 0) {
	                sql.append(" AND CITY like '" + bean.getCity() + "%'");
	            }
	            if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
	                sql.append(" AND Mobile_NO = " + bean.getMobileNo());
	            }

	        }
	
		if (pageSize > 0) {
            // Calculate start record index
            pageNo = (pageNo - 1) * pageSize;

            sql.append(" LIMIT " + pageNo + ", " + pageSize);
            // sql.append(" limit " + pageNo + "," + pageSize);
        }

        ArrayList collegeList = new ArrayList();
        try {
    		conn = JDBCDataSource.getConnection();
        	ps = conn.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CollegeBean cb = new CollegeBean();
                cb.setId(rs.getLong(1));
                cb.setCollegeName(rs.getString(2));
                cb.setAddress(rs.getString(3));
                cb.setState(rs.getString(4));
                cb.setCity(rs.getString(5));
                cb.setMobileNo(rs.getString(6));
                cb.setCreatedBy(rs.getString(7));
                cb.setModifiedBy(rs.getString(8));
                cb.setCreatedDateTime(rs.getTimestamp(9));
                cb.setModifiedDateTime(rs.getTimestamp(10));
                collegeList.add(cb);
            }
        } catch (Exception e) {
            throw new Exception("Exception : Exception in search college " + e);
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return collegeList;
    }
	}
	
