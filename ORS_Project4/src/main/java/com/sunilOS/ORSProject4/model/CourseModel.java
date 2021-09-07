	package com.sunilOS.ORSProject4.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.CourseBean;
import com.sunilOS.ORSProject4.exception.DuplicateRecordException;
import com.sunilOS.ORSProject4.utility.JDBCDataSource;

public class CourseModel 
{

	private static long pk;
	private static Connection conn;
	private static PreparedStatement ps;
	private static ResultSet rs;
    private static Logger log = Logger.getLogger(CourseModel.class);

	public static long nextPK() throws Exception
	{
		log.debug("CourseModel nextPK method started");
		
		long pk = 0;
		try {
		conn = JDBCDataSource.getConnection();
		ps = conn.prepareStatement("SELECT MAX(ID) FROM ST_COURSE");
		rs = ps.executeQuery();
		
		while(rs.next())
		{
		pk = rs.getLong(1);
		}
		}
		catch (Exception e) 
		{
			System.out.println(e.getStackTrace());
		}
		finally
		{
			JDBCDataSource.closeConnection(conn);
		}
		return pk+1;
	}
	
	public static long add(CourseBean bean) throws Exception 
	{
		log.debug("CourseModel add method started");
			Connection conn=null;
			
			CourseBean beanExist=findByCourseName(bean.getCourseName());
			
			if(beanExist!=null&&beanExist.getId()!=bean.getId()){
				throw new DuplicateRecordException("Course already exists");
				
			}
			
			try 
			{
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("INSERT INTO ST_COURSE VALUES(?,?,?,?,?,?,?,?)");

			ps.setLong(1, pk);

			ps.setString(2, bean.getCourseName());

			ps.setString(3, bean.getCourseDescription());

			ps.setString(4, bean.getCourseDuration());

			ps.setString(5, bean.getCreatedBy());

			ps.setString(6, bean.getModifiedBy());

			ps.setTimestamp(7, bean.getCreatedDateTime());

			ps.setTimestamp(8, bean.getModifiedDateTime());
			
			ps.executeUpdate();
			conn.commit();

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

		public static void delete(CourseBean bean) throws Exception 
		{
			log.debug("CourseModel delete method started");

			try 
			{
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("DELETE FROM ST_COURSE WHERE ID=?");

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

		public static CourseBean findByCourseName(String courseName) throws Exception 
		{
			log.debug("CourseModel findByCourseName method started");

			CourseBean bean = new CourseBean();
			try
			{
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("SELECT * FROM ST_COURSE WHERE COURSE_NAME = ?");

			ps.setString(1, courseName);

			rs = ps.executeQuery();

			while (rs.next()) {
				
				bean = new CourseBean();

				bean.setId(rs.getLong(1));

				bean.setCourseName(rs.getString(2));

				bean.setCourseDescription(rs.getString(3));

				bean.setCourseDuration(rs.getString(4));

				bean.setCreatedBy(rs.getString(5));

				bean.setModifiedBy(rs.getString(6));

				bean.setCreatedDateTime(rs.getTimestamp(7));

				bean.setModifiedDateTime(rs.getTimestamp(8));
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

		public static CourseBean findByPK(long f) throws Exception {
			
			log.debug("CourseModel findByPK method started");

			CourseBean bean = null;
			try 
			{
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("SELECT * FROM ST_COURSE WHERE ID=?");

			ps.setLong(1, f);

			rs = ps.executeQuery();

			while (rs.next()) {
				bean = new CourseBean();

				bean.setId(rs.getLong(1));

				bean.setCourseName(rs.getString(2));

				bean.setCourseDescription(rs.getString(3));

				bean.setCourseDuration(rs.getString(4));

				bean.setCreatedBy(rs.getString(5));

				bean.setModifiedBy(rs.getString(6));

				bean.setCreatedDateTime(rs.getTimestamp(7));

				bean.setModifiedDateTime(rs.getTimestamp(8));
			
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

		public static void update(CourseBean bean) throws Exception {

			log.debug("CourseModel update method started");

			
			try
			{
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("UPDATE ST_COURSE SET COURSE_NAME=?, COURSE_DESCRIPTION=?, COURSE_DURATION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");

			ps.setString(1, bean.getCourseName());

			ps.setString(2, bean.getCourseDescription());

			ps.setString(3, bean.getCourseDuration());

			ps.setString(4, bean.getCreatedBy());

			ps.setString(5, bean.getModifiedBy());

			ps.setTimestamp(6, bean.getCreatedDateTime());

			ps.setTimestamp(7, bean.getModifiedDateTime());

			ps.setLong(8, bean.getId());
		
			ps.executeUpdate();
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
			log.debug("CourseModel list() method started");

			return list (0 , 0);	
		}
		
		public List list(int pageNo, int pageSize) throws Exception
		{
			log.debug("CourseModel list(pageNo, pageSize) method started");

			ArrayList courseList = new ArrayList();
			StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE");
			
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
				CourseBean bean = new CourseBean();

				bean.setId(rs.getLong(1));

				bean.setCourseName(rs.getString(2));

				bean.setCourseDescription(rs.getString(3));

				bean.setCourseDuration(rs.getString(4));

				bean.setCreatedBy(rs.getString(5));

				bean.setModifiedBy(rs.getString(6));

				bean.setCreatedDateTime(rs.getTimestamp(7));

				bean.setModifiedDateTime(rs.getTimestamp(8));
				courseList.add(bean); 
			}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally 
		    {
		    	JDBCDataSource.closeConnection(conn);
		    }	
			return courseList;
		}
		
		public List search (CourseBean bean) throws Exception
		{
			log.debug("CourseModel search method started");

			return search(bean , 0 , 0);
		}
		
		public List search (CourseBean bean , int pageNo , int pageSize) throws Exception
		{
			log.debug("CourseModel search(bean, pageNo, pageSize) method started");

			ArrayList courseList = new ArrayList();
			StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE 1=1");
			
			if (bean != null) {

				if (bean.getId() > 0) {

					sql.append(" AND id =" + bean.getId());

				}

				if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {

					sql.append(" AND COURSE_NAME like '" + bean.getCourseName() + "%'");

				}

				if (bean.getCourseDescription() != null && bean.getCourseDescription().length() > 0) {

					sql.append(" AND COURSE_DESCRIPTION like '" + bean.getCourseDescription() + "%'");

				}

				if (bean.getCourseDuration() != null && bean.getCourseDuration().length() > 0) {
					
					sql.append(" AND COURSE_DURATION like '" + bean.getCourseDuration() + "%'");
					
				}

			}

			// if page size is greater than zero then apply pagination

			if (pageSize > 0) {

				// calculate start record index

				pageNo = (pageNo - 1) * pageSize;

				sql.append(" LIMIT " + pageNo + "," + pageSize);

			}
			        try {
			        	conn = JDBCDataSource.getConnection();
			        	ps = conn.prepareStatement(sql.toString());
			            ResultSet rs = ps.executeQuery();
			            while (rs.next()) {
			            	bean = new CourseBean();

							bean.setId(rs.getLong(1));

							bean.setCourseName(rs.getString(2));

							bean.setCourseDescription(rs.getString(3));

							bean.setCourseDuration(rs.getString(4));

							bean.setCreatedBy(rs.getString(5));

							bean.setModifiedBy(rs.getString(6));

							bean.setCreatedDateTime(rs.getTimestamp(7));

							bean.setModifiedDateTime(rs.getTimestamp(8));
							
							courseList.add(bean);
			            }
			        } catch (Exception e) {
			            e.printStackTrace();
			        } finally {
			            JDBCDataSource.closeConnection(conn);
			        }
		
			return courseList;
			}
}

