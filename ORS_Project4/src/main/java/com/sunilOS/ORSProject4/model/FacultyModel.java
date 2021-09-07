package com.sunilOS.ORSProject4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.CollegeBean;
import com.sunilOS.ORSProject4.bean.CourseBean;
import com.sunilOS.ORSProject4.bean.FacultyBean;
import com.sunilOS.ORSProject4.exception.ApplicationException;
import com.sunilOS.ORSProject4.exception.DuplicateRecordException;
import com.sunilOS.ORSProject4.utility.JDBCDataSource;

public class FacultyModel 
{
	private static long pk;
	private static Connection conn;
	private static PreparedStatement ps;
	private static ResultSet rs;
    private static Logger log = Logger.getLogger(FacultyModel.class);

	
	public static long nextPK() throws Exception
	{
		log.debug("FacultyModel nextPK method started");

		long pk = 0;
		try {
		conn = JDBCDataSource.getConnection();
		ps = conn.prepareStatement("SELECT MAX(ID) FROM ST_FACULTY");
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
	
public long add(FacultyBean facultyBean) throws Exception {
		
	log.debug("FacultyModel add method started");

		Connection conn=null;
		long pk = 0;
		
		CollegeBean collegeBean = CollegeModel.findByPK(facultyBean.getCollegeId());
		String collegeName = collegeBean.getCollegeName();
		facultyBean.setCollegeName(collegeName);
		
		CourseBean courseBean = CourseModel.findByPK(facultyBean.getCourseId());
		String courseName = courseBean.getCourseName();
		facultyBean.setCourseName(courseName);

		FacultyBean duplicataRole = findByEmail(facultyBean.getEmail());
		// Check if create Faculty already exist
		if (duplicataRole != null) {
			throw new DuplicateRecordException("Faculty already exists");
		}

		
		try {
			pk = nextPK();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);  
			ps = conn.prepareStatement("INSERT INTO ST_FACULTY VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
						ps.setLong(1, pk);
						ps.setLong(2, facultyBean.getCollegeId());
						ps.setString(3, facultyBean.getCollegeName());
						ps.setLong(4, facultyBean.getCourseId());
						ps.setString(5, facultyBean.getCourseName());
						ps.setString(6, facultyBean.getFirstName());
						ps.setString(7, facultyBean.getLastName());
						ps.setDate(8, new java.sql.Date(facultyBean.getJoiningDate().getTime()));
						ps.setString(9, facultyBean.getQualification());
						ps.setString(10, facultyBean.getEmail());
						ps.setString(11, facultyBean.getMobileNo());
						ps.setDate(12, new java.sql.Date(facultyBean.getDob().getTime()));
						ps.setString(13, facultyBean.getGender());
						ps.setString(14, facultyBean.getCreatedBy());
						ps.setString(15, facultyBean.getModifiedBy());
						ps.setTimestamp(16, facultyBean.getCreatedDateTime());
						ps.setTimestamp(17, facultyBean.getModifiedDateTime());
		
			pk = ps.executeUpdate();
		
			ps.close();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;

	}

		public static void delete(FacultyBean delBean) throws Exception 
		{
			log.debug("FacultyModel delete method started");

			try 
			{
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("DELETE FROM ST_FACULTY WHERE ID=?");

			ps.setLong(1, delBean.getId());

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

		public static FacultyBean findByEmail(String email) throws Exception {
			
			log.debug("FacultyModel findByEmail method started");

			FacultyBean bean =  null;
			try
			{
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("SELECT * FROM ST_FACULTY WHERE EMAIL = ?");

			ps.setString(1, email);

			rs = ps.executeQuery();

			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setCollegeName(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setFirstName(rs.getString(6));
				bean.setLastName(rs.getString(7));
				bean.setJoiningDate(rs.getDate(8));
				bean.setQualification(rs.getString(9));
				bean.setEmail(rs.getString(10));
				bean.setMobileNo(rs.getString(11));
				bean.setDob(rs.getDate(12));
				bean.setGender(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDateTime(rs.getTimestamp(16));
				bean.setModifiedDateTime(rs.getTimestamp(17));
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

		public static FacultyBean findByPK(long f) throws Exception {
			
			log.debug("FacultyModel findByPK method started");

			FacultyBean bean = null;
			try 
			{
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("SELECT * FROM ST_FACULTY WHERE ID=?");

			ps.setLong(1, f);

			rs = ps.executeQuery();

			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setCollegeName(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setFirstName(rs.getString(6));
				bean.setLastName(rs.getString(7));
				bean.setJoiningDate(rs.getDate(8));
				bean.setQualification(rs.getString(9));
				bean.setEmail(rs.getString(10));
				bean.setMobileNo(rs.getString(11));
				bean.setDob(rs.getDate(12));
				bean.setGender(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDateTime(rs.getTimestamp(16));
				bean.setModifiedDateTime(rs.getTimestamp(17));
			
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

		public static void update(FacultyBean facultyBean) throws Exception 
		{
			log.debug("FacultyModel update method started");

			FacultyBean beanexist=findByEmail(facultyBean.getEmail());
					
			CollegeModel collegemodel = new CollegeModel();
			CollegeBean collegeBean = collegemodel.findByPK(facultyBean.getCollegeId());
			String collegeName = collegeBean.getCollegeName();
			facultyBean.setCollegeName(collegeName);
			
			CourseModel courseModel = new CourseModel();
			CourseBean courseBean = courseModel.findByPK(facultyBean.getCourseId());
			String courseName = courseBean.getCourseName();
			facultyBean.setCourseName(courseName);
			
			if(beanexist!=null&&beanexist.getId()!=facultyBean.getId()){
				throw new DuplicateRecordException("Faculty is already exist");
		}
			try
			{
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("UPDATE ST_FACULTY SET COLLEGE_ID=?, COLLEGE_NAME=?, COURSE_ID=?, COURSE_NAME=?, FIRST_NAME=?, LAST_NAME=?, JOINING_DATE=?, QUALIFICATION=?, EMAIL=? , MOBILE_NO=?, DOB=?, GENDER=?, CREATED_BY=?, MODIFIED_BY=?, CREATED_DATE_TIME=?, MODIFIED_DATE_TIME=?  WHERE ID=?");

			ps.setLong(1, facultyBean.getCollegeId());
			ps.setString(2, facultyBean.getCollegeName());
			ps.setLong(3, facultyBean.getCourseId());
			ps.setString(4, facultyBean.getCourseName());
			ps.setString(5, facultyBean.getFirstName());
			ps.setString(6, facultyBean.getLastName());
			ps.setDate(7, new java.sql.Date(facultyBean.getJoiningDate().getTime()));
			ps.setString(8, facultyBean.getQualification());
			ps.setString(9, facultyBean.getEmail());
			ps.setString(10, facultyBean.getMobileNo());
			ps.setDate(11, new java.sql.Date(facultyBean.getDob().getTime()));
			ps.setString(12, facultyBean.getGender());
			ps.setString(13, facultyBean.getCreatedBy());
			ps.setString(14, facultyBean.getModifiedBy());
			ps.setTimestamp(15, facultyBean.getCreatedDateTime());
			ps.setTimestamp(16, facultyBean.getModifiedDateTime());
			ps.setLong(17, facultyBean.getId());
		
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
			log.debug("FacultyModel list method started");

			return list (0 , 0);	
		}
		
		public List list(int pageNo, int pageSize) throws Exception
		{
			log.debug("FacultyModel list(pageNo, pageSize) method started");

			ArrayList facultyList = new ArrayList();
			StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY");
			
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
				FacultyBean bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setCollegeName(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setFirstName(rs.getString(6));
				bean.setLastName(rs.getString(7));
				bean.setJoiningDate(rs.getDate(8));
				bean.setQualification(rs.getString(9));
				bean.setEmail(rs.getString(10));
				bean.setMobileNo(rs.getString(11));
				bean.setDob(rs.getDate(12));
				bean.setGender(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDateTime(rs.getTimestamp(16));
				bean.setModifiedDateTime(rs.getTimestamp(17));
				facultyList.add(bean); 
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
			return facultyList;
		}
		
		public List search (FacultyBean bean) throws Exception
		{
			log.debug("FacultyModel search(bean) method started");

			return search(bean , 0 , 0);
		}
		
		public List search (FacultyBean bean , int pageNo , int pageSize) throws Exception
		{
			log.debug("FacultyModel search(bean, pageNo, pageSize) method started");

	       
			ArrayList facultyList = new ArrayList();
			StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE 1=1");
			
			if (bean != null) 
			{
				if (bean.getId() > 0) 
				{
					sql.append(" AND ID = " + bean.getId());
				}
				if (bean.getFirstName() != null && bean.getFirstName().length() > 0) 
				{
					sql.append(" AND FIRST_NAME lIKE '" + bean.getFirstName() + "%'");
				}
				if (bean.getLastName() != null && bean.getLastName().length() > 0) 
				{
					sql.append(" AND LAST_NAME LIKE '" + bean.getLastName() + "%'");
				}
				if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) 
				{
					sql.append(" AND COLLEGE_NAME LIKE'" + bean.getCollegeName() + "%'");
				}
				if (bean.getCollegeId() > 0) 
				{
					sql.append(" AND COLLEGE_ID = " + bean.getCollegeId());
				}
				if (bean.getCourseName() != null && bean.getCourseName().length() > 0) 
				{
					sql.append(" AND COURSE_NAME LIKE '" + bean.getCourseName() + "%'");
				}
				if (bean.getCourseId() > 0) 
				{
					sql.append(" AND COURSE_ID LIKE " + bean.getCourseId());
				}
		
				if (pageSize > 0) 
				{
		            pageNo = (pageNo - 1) * pageSize;

		            sql.append(" LIMIT " + pageNo + ", " + pageSize);
		        }
			        try {
			        	conn = JDBCDataSource.getConnection();
			        	ps = conn.prepareStatement(sql.toString());
			            ResultSet rs = ps.executeQuery();
			            while (rs.next()) {
			            	bean = new FacultyBean();
							bean.setId(rs.getLong(1));
							bean.setCollegeId(rs.getLong(2));
							bean.setCollegeName(rs.getString(3));
							bean.setCourseId(rs.getLong(4));
							bean.setCourseName(rs.getString(5));
							bean.setFirstName(rs.getString(6));
							bean.setLastName(rs.getString(7));
							bean.setJoiningDate(rs.getDate(8));
							bean.setQualification(rs.getString(9));
							bean.setEmail(rs.getString(10));
							bean.setMobileNo(rs.getString(11));
							bean.setDob(rs.getDate(12));
							bean.setGender(rs.getString(13));
							bean.setCreatedBy(rs.getString(14));
							bean.setModifiedBy(rs.getString(15));
							bean.setCreatedDateTime(rs.getTimestamp(16));
							bean.setModifiedDateTime(rs.getTimestamp(17));
							facultyList.add(bean);
			            }
			        } catch (Exception e) {
			            e.printStackTrace();
			        } finally {
			            JDBCDataSource.closeConnection(conn);
			        }
		       
		    }
			return facultyList;
			}
			}
			
