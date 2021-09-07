package com.sunilOS.ORSProject4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.MarksheetBean;
import com.sunilOS.ORSProject4.bean.StudentBean;
import com.sunilOS.ORSProject4.exception.ApplicationException;
import com.sunilOS.ORSProject4.exception.DuplicateRecordException;
import com.sunilOS.ORSProject4.utility.JDBCDataSource;



public class MarksheetModel {

	private static long pk;
	private static Connection conn;
	private static PreparedStatement ps;
	private static ResultSet rs;
    private static Logger log = Logger.getLogger(MarksheetModel.class);

	
	public static long nextPK() throws Exception
	{
		log.debug("MarksheetModel nextPK method started");

		pk = 0;
		try {
		startConn();
		
		ps = conn.prepareStatement("SELECT MAX(ID) FROM ST_MARKSHEET");
		
		rs = ps.executeQuery();
		
		while(rs.next())
		{
			pk = rs.getInt(1);
		}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		rs.close();
		
		 return pk+1;
	}
	
	public static long add (MarksheetBean bean) throws Exception
	{
		
		log.debug("MarksheetModel add method started");

		
		StudentBean studentBean = StudentModel.findByPK(bean.getStudentId());
		bean.setName(studentBean.getFirstName() + "" + studentBean.getLastName());

		MarksheetBean duplicateMarksheet = findByRollNo(bean.getRollNo());
		

		if (duplicateMarksheet != null) 
		{
			throw new DuplicateRecordException("Roll Number already exist");
		}
		
		pk = nextPK();
		try
		{
		startConn();
		conn.setAutoCommit(false);
		ps = conn.prepareStatement("INSERT INTO ST_MARKSHEET VALUES (?,?,?,?,?,?,?,?,?,?,?)");
		
		ps.setLong(1,pk);
		ps.setString(2,bean.getRollNo());
		ps.setLong(3,bean.getStudentId());
		ps.setString(4,bean.getName());
		ps.setInt(5,bean.getPhysics());
		ps.setInt(6,bean.getChemistry());
		ps.setInt(7,bean.getMaths());
		ps.setString(8,bean.getCreatedBy());
		ps.setString(9,bean.getModifiedBy());
		ps.setTimestamp(10,bean.getCreatedDateTime());
		ps.setTimestamp(11,bean.getModifiedDateTime());
		
		ps.executeUpdate();
		conn.commit();
		}
		catch(SQLException e)
		{conn.rollback();}
		finally
		{
			JDBCDataSource.closeConnection(conn);
		}
		
		return pk;
	}
	
	public static void delete (MarksheetBean bean) throws Exception
	{	
		log.debug("MarksheetModel delete method started");

		try {
		startConn();
		ps = conn.prepareStatement("DELETE FROM ST_MARKSHEET WHERE ID=?");
		
		ps.setLong(1, bean.getId());
		
		pk = ps.executeUpdate();
		}
		catch (Exception e) 
		{e.getStackTrace();}
		finally
		{
			JDBCDataSource.closeConnection(conn);
		}
	}
	
	public static MarksheetBean findByRollNo (String rollNo) throws Exception
	{
		log.debug("MarksheetModel findByRollNo method started");

		MarksheetBean bean = null;
		try
		{
		startConn();
		ps = conn.prepareStatement("SELECT * FROM ST_MARKSHEET WHERE ROLL_NO=?");
		
		ps.setString(1,rollNo);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next())
		{
			bean = new MarksheetBean();
			bean.setId(rs.getLong(1));
			bean.setRollNo(rs.getString(2));
			bean.setStudentId(rs.getLong(3));
			bean.setName(rs.getString(4));
			bean.setPhysics(rs.getInt(5));
			bean.setChemistry(rs.getInt(6));
			bean.setMaths(rs.getInt(7));
			bean.setCreatedBy(rs.getString(8));
			bean.setModifiedBy(rs.getString(9));
			bean.setCreatedDateTime(rs.getTimestamp(10));
			bean.setModifiedDateTime(rs.getTimestamp(11));
		}
		}
		catch (Exception e) 
		{e.getStackTrace();}
		finally
		{
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	public static MarksheetBean findByPK (long f) throws Exception
	{
		log.debug("MarksheetModel findByPK method started");

		MarksheetBean bean = new MarksheetBean();
		try
		{
		startConn();
		ps = conn.prepareStatement("SELECT * FROM ST_MARKSHEET WHERE ID=?");
		
		ps.setLong(1,f);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next())
		{
			bean = new MarksheetBean();
			bean.setId(rs.getLong(1));
			bean.setRollNo(rs.getString(2));
			bean.setStudentId(rs.getLong(3));
			bean.setName(rs.getString(4));
			bean.setPhysics(rs.getInt(5));
			bean.setChemistry(rs.getInt(6));
			bean.setMaths(rs.getInt(7));
			bean.setCreatedBy(rs.getString(8));
			bean.setModifiedBy(rs.getString(9));
			bean.setCreatedDateTime(rs.getTimestamp(10));
			bean.setModifiedDateTime(rs.getTimestamp(11));
		}
		}
		catch (Exception e) 
		{e.getStackTrace();}
		finally
		{
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	public static void update (MarksheetBean bean) throws Exception
	{

		log.debug("MarksheetModel update method started");

		StudentModel sModel = new StudentModel();
		StudentBean studentbean = sModel.findByPK(bean.getStudentId());
		bean.setName(studentbean.getFirstName() + " " + studentbean.getLastName());
		
		try
		{
		startConn();
		conn.setAutoCommit(false);
		ps = conn.prepareStatement("UPDATE ST_MARKSHEET SET ROLL_NO=?,STUDENT_ID=?,NAME=?,PHYSICS=?,CHEMISTRY=?,MATHS=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
		
		ps.setString(1,bean.getRollNo());
		ps.setLong(2,bean.getStudentId());
		ps.setString(3,bean.getName());
		ps.setInt(4,bean.getPhysics());
		ps.setInt(5,bean.getChemistry());
		ps.setInt(6,bean.getMaths());
		ps.setString(7,bean.getCreatedBy());
		ps.setString(8,bean.getModifiedBy());
		ps.setTimestamp(9,bean.getCreatedDateTime());
		ps.setTimestamp(10,bean.getModifiedDateTime());
		ps.setLong(11,bean.getId());
		
		pk = ps.executeUpdate();
		conn.commit();
		}
		catch(SQLException e)
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
	
	public List search(MarksheetBean bean, int pageNo, int PageSize) throws Exception {
		
		log.debug("MarksheetModel search(bean, pageNo, pageSize) method started");

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_MARKSHEET WHERE 1=1");
		Connection conn = null;
		if (bean != null) {

			if (bean.getId() > 0) {
				
				sql.append(" AND ID=" + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME LIKE '"+ bean.getName() +"%'");
			}
			if(bean.getRollNo()!=null && bean.getRollNo().length()>0){
				sql.append(" AND ROLL_NO LIKE '"+bean.getRollNo()+"%'");
			}
			if(bean.getPhysics()!=0 && bean.getPhysics()>0){
				sql.append(" AND PHYSICS="+bean.getPhysics());
			}
			if(bean.getChemistry()!=0 && bean.getChemistry()>0){
				sql.append(" AND CHEMISTRY="+bean.getChemistry());
			}
			if(bean.getMaths()!=0 && bean.getMaths()>0){
				sql.append(" AND MATHS="+ bean.getPhysics());
			}

		}

		if (PageSize > 0) {
			pageNo = (pageNo - 1) * PageSize;
			sql.append(" LIMIT " + pageNo + "," + PageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDateTime(rs.getTimestamp(10));
				bean.setModifiedDateTime(rs.getTimestamp(11));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new Exception(
                    "Exception : Exception in search Marksheet");
		} 
		finally
		{
			JDBCDataSource.closeConnection(conn);
		}
	
		return list;
	}
	public List search(MarksheetBean bean) throws Exception{
		
		log.debug("MarksheetModel search method started");

		return search(bean,0,0);
	}
	
	/**
	 * Get List of Marksheet with pagination
	 *
	 * @return list : List of Marksheet
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws DatabaseException
	 */
	public List list(int pageNo,int pageSize) throws Exception{
	
		log.debug("MarksheetModel list(pageNo, pageSize) method started");

		Connection conn=null;
		MarksheetBean bean=null;
		ArrayList list=new ArrayList();
		StringBuffer sql=new StringBuffer(" SELECT * FROM ST_MARKSHEET WHERE 1=1");
		if(pageSize>0){
			pageNo=(pageNo-1)*pageSize;
			sql.append(" LIMIT "+pageNo+","+pageSize);
		}
		try {
			conn=JDBCDataSource.getConnection();
			PreparedStatement pstmt=conn.prepareStatement(sql.toString());
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				bean=new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDateTime(rs.getTimestamp(10));
				bean.setModifiedDateTime(rs.getTimestamp(11));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new Exception(
                    "Exception : Exception in search Marksheet");
		} 
		finally
		{
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
	public List list() throws Exception{
		
		log.debug("MarksheetModel list method started");

		return list(0,0);
	}
	
	/**
	 * Get Marksheet
	 *
	 * @param list
	 * @throws DatabaseException
	 */
	public List getMeritList(int pageNo,int pageSize) throws ApplicationException{
		
		log.debug("MarksheetModel getMeritList method started");

		ArrayList list=new ArrayList();
		StringBuffer sql=new StringBuffer("SELECT ID,ROLL_NO,STUDENT_ID,NAME,PHYSICS,CHEMISTRY,MATHS,(PHYSICS+CHEMISTRY+MATHS) AS TOTAL FROM ST_MARKSHEET WHERE PHYSICS>35 AND CHEMISTRY>35 AND MATHS>35 ORDER BY TOTAL DESC "); 
		if(pageSize>0){
			pageNo=(pageNo-1)*pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}
		try {
			startConn();
			ps=conn.prepareStatement(sql.toString());
			rs=ps.executeQuery();
			while(rs.next())
			{
				MarksheetBean bean=new MarksheetBean();
				 bean.setId(rs.getLong(1));
				 bean.setRollNo(rs.getString(2));
				 bean.setStudentId(rs.getLong(3));
				 bean.setName(rs.getString(4));
				 bean.setPhysics(rs.getInt(5));
				 bean.setChemistry(rs.getInt(6));
				 bean.setMaths(rs.getInt(7));
				 list.add(bean);	 
			}	
		} 
		catch (Exception e) {
			e.getStackTrace();
		}
		finally
		{
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
	
}
	

