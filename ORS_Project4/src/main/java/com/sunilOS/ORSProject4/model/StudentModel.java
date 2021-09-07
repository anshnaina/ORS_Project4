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
import com.sunilOS.ORSProject4.exception.ApplicationException;
import com.sunilOS.ORSProject4.exception.DuplicateRecordException;
import com.sunilOS.ORSProject4.utility.JDBCDataSource;



public class StudentModel {

	private static long pk;
	private static Connection conn;
	private static PreparedStatement ps;
	private static ResultSet rs;
    private static Logger log = Logger.getLogger(StudentModel.class);

	
	public static long nextPK() throws Exception
	{
		log.debug("StudentModel nextPK method started");

		long pk = 0;
		try
		{
		startConn();
		ps = conn.prepareStatement("SELECT MAX(ID) FROM ST_STUDENT");
		
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
	
	public static long add (StudentBean bean) throws Exception
	{
		
		log.debug("StudentModel add method started");

		CollegeBean collegeBean = CollegeModel.findByPK(bean.getCollegeId());
		bean.setCollegeName(collegeBean.getCollegeName());
		
		StudentBean duplicateName = findByEmailId(bean.getEmail());

		if (duplicateName != null) {
			throw new DuplicateRecordException("Email already exists");
		}
		
		long pk = 0;
		try
		{
		pk = nextPK();
		startConn();
		conn.setAutoCommit(false);
		ps = conn.prepareStatement("INSERT INTO ST_STUDENT VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
		
		ps.setLong(1,pk);
		ps.setLong(2,bean.getCollegeId());
		ps.setString(3,bean.getCollegeName());
		ps.setString(4,bean.getFirstName());
		ps.setString(5,bean.getLastName());
		ps.setDate(6, new java.sql.Date(bean.getDob().getTime()));
		ps.setString(7,bean.getMobileNo());
		ps.setString(8,bean.getEmail());
		ps.setString(9,bean.getCreatedBy());
		ps.setString(10,bean.getModifiedBy());
		ps.setTimestamp(11,bean.getCreatedDateTime());
		ps.setTimestamp(12,bean.getModifiedDateTime());
		
		ps.executeUpdate();
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
	
	public static void delete (StudentBean bean) throws Exception
	{
		log.debug("StudentModel delete method started");

		try
		{
		startConn();
		ps = conn.prepareStatement("DELETE FROM ST_STUDENT WHERE ID=?");
		
		ps.setLong(1, bean.getId());
		
		pk = ps.executeUpdate();
		}
		catch(SQLException e)
		{e.getStackTrace();}
		finally
		{
			JDBCDataSource.closeConnection(conn);
		}
		}
	
	public static StudentBean findByEmailId (String email) throws Exception
	{
		log.debug("StudentModel findByEmailId method started");

		StudentBean bean = null;
		try
		{
		startConn();
		ps = conn.prepareStatement("SELECT * FROM ST_STUDENT WHERE EMAIL=?");
		
		ps.setString(1,email);
		
		rs = ps.executeQuery();
		
		while(rs.next())
		{
			bean = new StudentBean();
			bean.setId(rs.getLong(1));
			bean.setCollegeId(rs.getLong(2));	
			bean.setCollegeName(rs.getString(3));
            bean.setFirstName(rs.getString(4));
            bean.setLastName(rs.getString(5));
            bean.setDob(rs.getDate(6));
            bean.setMobileNo(rs.getString(7));
            bean.setEmail(rs.getString(8));
            bean.setCreatedBy(rs.getString(9));
            bean.setModifiedBy(rs.getString(10));
            bean.setCreatedDateTime(rs.getTimestamp(11));
            bean.setModifiedDateTime(rs.getTimestamp(12));
		}
		}
		catch(SQLException e)
		{e.getStackTrace();}
		finally
		{
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	public static StudentBean findByPK (long f) throws Exception
	{
		log.debug("StudentModel findByPK method started");

		StudentBean bean = new StudentBean();
		try
		{
		startConn();
		ps = conn.prepareStatement("SELECT * FROM ST_STUDENT WHERE ID=?");
		
		ps.setLong(1,f);
		
		rs = ps.executeQuery();
		
		while(rs.next())
		{
			bean.setId(rs.getLong(1));
			bean.setCollegeId(rs.getLong(2));	
			bean.setCollegeName(rs.getString(3));
            bean.setFirstName(rs.getString(4));
            bean.setLastName(rs.getString(5));
            bean.setDob(rs.getDate(6));
            bean.setMobileNo(rs.getString(7));
            bean.setEmail(rs.getString(8));
            bean.setCreatedBy(rs.getString(9));
            bean.setModifiedBy(rs.getString(10));
            bean.setCreatedDateTime(rs.getTimestamp(11));
            bean.setModifiedDateTime(rs.getTimestamp(12));
		}
		}
		catch(SQLException e)
		{e.getStackTrace();}
		finally
		{
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	public static void update (StudentBean bean) throws Exception
	{		
		log.debug("StudentModel update method started");

		// get College Name
			CollegeBean collegeBean = CollegeModel.findByPK(bean.getCollegeId());
			bean.setCollegeName(collegeBean.getCollegeName());

		
		try
		{
		startConn();
		conn.setAutoCommit(false);
		ps = conn.prepareStatement("UPDATE ST_STUDENT SET COLLEGE_ID=?,COLLEGE_NAME=?,FIRST_NAME=?,LAST_NAME=?,DATE_OF_BIRTH=?,MOBILE_NO=?,EMAIL=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
		
		ps.setLong(1,bean.getCollegeId());
		ps.setString(2,bean.getCollegeName());
		ps.setString(3,bean.getFirstName());
		ps.setString(4,bean.getLastName());
		ps.setDate(5, new java.sql.Date(bean.getDob().getTime()));
		ps.setString(6,bean.getMobileNo());
		ps.setString(7,bean.getEmail());
		ps.setString(8,bean.getCreatedBy());
		ps.setString(9,bean.getModifiedBy());
		ps.setTimestamp(10,bean.getCreatedDateTime());
		ps.setTimestamp(11,bean.getModifiedDateTime());
		ps.setLong(12,bean.getId());
		
		pk = ps.executeUpdate();
		conn.commit();
		}
		catch(Exception e)
		{conn.rollback();}
		finally 
		{
			JDBCDataSource.closeConnection(conn);
		}
		}
		
	
	public static void startConn() throws Exception
	{
		conn = JDBCDataSource.getConnection();
	}
	/**
	 * Search Student
	 *
	 * @param bean
	 *            : Search Parameters
	 * @throws ApplicationException
	 */

	public List search(StudentBean bean) throws ApplicationException {
	
		log.debug("StudentModel search method started");

		return search(bean, 0, 0);
	}

	/**
	 * Search Student with pagination
	 *
	 * @return list : List of Students
	 * @param bean
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 *
	 * @throws ApplicationException
	 */

	public List search(StudentBean bean, int pageNo, int pageSize) throws ApplicationException {
	
		log.debug("StudentModel search(bean, pageNo, pageSize) method started");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_STUDENT WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sql.append(" AND FIRST_NAME like '" + bean.getFirstName() + "%'");
			}
			if (bean.getLastName() != null && bean.getLastName().length() > 0) {
				sql.append(" AND LAST_NAME like '" + bean.getLastName() + "%'");
			}
			if (bean.getDob() != null && bean.getDob() != null) {
				sql.append(" AND DOB = " + bean.getDob());
			}
			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" AND MOBILE_NO like '" + bean.getMobileNo() + "%'");
			}
			if (bean.getEmail() != null && bean.getEmail().length() > 0) {
				sql.append(" AND EMAIL like '" + bean.getEmail() + "%'");
			}
			if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {
				sql.append(" AND COLLEGE_NAME = " + bean.getCollegeName());
			}

		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList list = new ArrayList();
		
		try {
			startConn();
			ps=conn.prepareStatement(sql.toString());
			rs=ps.executeQuery();
			
			while(rs.next()){
				 
				bean=new StudentBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));	
				bean.setCollegeName(rs.getString(3));
                bean.setFirstName(rs.getString(4));
                bean.setLastName(rs.getString(5));
                bean.setDob(rs.getDate(6));
                bean.setMobileNo(rs.getString(7));
                bean.setEmail(rs.getString(8));
                bean.setCreatedBy(rs.getString(9));
                bean.setModifiedBy(rs.getString(10));
                bean.setCreatedDateTime(rs.getTimestamp(11));
                bean.setModifiedDateTime(rs.getTimestamp(12));
                list.add(bean);
			}
		} catch (Exception e) {
			
			throw new ApplicationException("Exception : Exception in search Student");
			
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	/**
	 * Get List of Student
	 *
	 * @return list : List of Student
	 * @throws ApplicationException
	 */

	public List list() throws ApplicationException {
		
		log.debug("StudentModel list method started");

		return list(0, 0);
	}

	/**
	 * Get List of Student with pagination
	 *
	 * @return list : List of Student
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws DatabaseException
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {
	
		log.debug("StudentModel list(pageNo, pageSize) method started");

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_STUDENT");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}



		try {
			startConn();
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				StudentBean bean = new StudentBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setCollegeName(rs.getString(3));
				bean.setFirstName(rs.getString(4));
				bean.setLastName(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setEmail(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDateTime(rs.getTimestamp(11));
				bean.setModifiedDateTime(rs.getTimestamp(12));
				list.add(bean);
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting list of Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;

	}
		
}
