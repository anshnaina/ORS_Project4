package com.sunilOS.ORSProject4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.CourseBean;
import com.sunilOS.ORSProject4.bean.SubjectBean;
import com.sunilOS.ORSProject4.exception.DuplicateRecordException;
import com.sunilOS.ORSProject4.utility.JDBCDataSource;

public class SubjectModel {

		private static long pk;
		private static Connection conn;
		private static PreparedStatement ps;
		private static ResultSet rs;
	    private static Logger log = Logger.getLogger(SubjectModel.class);


		public static long nextPK() throws Exception {
			
			log.debug("SubjectModel nextPK method started");

			long pk = 0;
			try {
				startConn();
				ps = conn.prepareStatement("SELECT MAX(ID) FROM ST_SUBJECT");

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

		public static long add(SubjectBean sb) throws Exception 
	{
			log.debug("SubjectModel add method started");

			conn = null;
			CourseModel cmodel =new CourseModel();
			CourseBean cBean = cmodel.findByPK(sb.getCourseId());
			sb.setCourseName(cBean.getCourseName());
			
			SubjectBean duplicateSubjectName=findBySubjectName(sb.getSubjectName());
			
			if(duplicateSubjectName!=null){
				throw new DuplicateRecordException("Subject name already exists");
				
			}
			
			try 
			{
			startConn();
			pk = nextPK();
			
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("INSERT INTO ST_SUBJECT VALUES (?,?,?,?,?,?,?,?,?)");

			ps.setLong(1, pk);
			ps.setString(2, sb.getSubjectName());
			ps.setString(3, sb.getDescription());
			ps.setString(4, sb.getCourseName());
			ps.setLong(5, sb.getCourseId());
			ps.setString(6, sb.getCreatedBy());
			ps.setString(7, sb.getModifiedBy());
			ps.setTimestamp(8, sb.getCreatedDateTime());
			ps.setTimestamp(9, sb.getModifiedDateTime());

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

		public static void delete(SubjectBean sb) throws Exception 
		{
			log.debug("SubjectModel delete method started");

			try 
			{
			startConn();
			ps = conn.prepareStatement("DELETE FROM ST_SUBJECT WHERE ID=?");

			ps.setLong(1, sb.getId());

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

		public static SubjectBean findBySubjectName(String name) throws Exception {
			
			log.debug("SubjectModel findBySubjectName method started");

			conn = null; 
			SubjectBean sb = null;
			try
			{
			startConn();
			ps = conn.prepareStatement("SELECT * FROM ST_SUBJECT WHERE SUBJECT_NAME=?");

			ps.setString(1, name);

			rs = ps.executeQuery();

			while (rs.next()) {
				sb = new SubjectBean();
				sb.setId(rs.getLong(1));
				sb.setSubjectName(rs.getString(2));
				sb.setDescription(rs.getString(3));
				sb.setCourseName(rs.getString(4));
				sb.setCourseId(rs.getLong(5));
				sb.setCreatedBy(rs.getString(6));
				sb.setModifiedBy(rs.getString(7));
				sb.setCreatedDateTime(rs.getTimestamp(8));
				sb.setModifiedDateTime(rs.getTimestamp(9));
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
			return sb;
		}

		public static SubjectBean findByPK(long f) throws Exception {
			
			log.debug("SubjectModel findByPK method started");

			SubjectBean sb = new SubjectBean();
			try 
			{
			startConn();
			ps = conn.prepareStatement("SELECT * FROM ST_SUBJECT WHERE ID=?");

			ps.setLong(1, f);

			rs = ps.executeQuery();

			while (rs.next()) {
				sb = new SubjectBean();
				sb.setId(rs.getLong(1));
				sb.setSubjectName(rs.getString(2));
				sb.setDescription(rs.getString(3));
				sb.setCourseName(rs.getString(4));
				sb.setCourseId(rs.getLong(5));
				sb.setCreatedBy(rs.getString(6));
				sb.setModifiedBy(rs.getString(7));
				sb.setCreatedDateTime(rs.getTimestamp(8));
				sb.setModifiedDateTime(rs.getTimestamp(9));
			
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
			return sb;
		}

		public static void update(SubjectBean sb) throws Exception {
			
			log.debug("SubjectModel update method started");

			
			CourseModel cModel = new CourseModel();
			CourseBean cBean = cModel.findByPK(sb.getCourseId());
			
			sb.setCourseName(cBean.getCourseName());

			SubjectBean beanExist=findBySubjectName(sb.getSubjectName());
			
			if(beanExist!=null&&beanExist.getId()!=sb.getId()){
				throw new DuplicateRecordException("Subject already exist");
			}
			
			try
			{
			startConn();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("UPDATE ST_SUBJECT SET SUBJECT_NAME=?,DESCRIPTION=?,COURSE_NAME=?,COURSE_ID=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATE_TIME=?,MODIFIED_DATE_TIME=? WHERE ID=?");


			ps.setString(1, sb.getSubjectName());
			ps.setString(2, sb.getDescription());
			ps.setString(3, sb.getCourseName());
			ps.setLong(4, sb.getCourseId());
			ps.setString(5, sb.getCreatedBy());
			ps.setString(6, sb.getModifiedBy());
			ps.setTimestamp(7, sb.getCreatedDateTime());
			ps.setTimestamp(8, sb.getModifiedDateTime());
			ps.setLong(9, sb.getId());
		
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

		public static void startConn() throws Exception {
			conn = JDBCDataSource.getConnection();
	 	}
		
		public List list() throws Exception
		{
			log.debug("SubjectModel list method started");

			return list (0 , 0);	
		}
		
		public List list(int pageNo, int pageSize) throws Exception
		{
			log.debug("SubjectModel list(pageNo, pageSize) method started");

			ArrayList subjectList = new ArrayList();
			StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT");
			
			if(pageSize > 0)
			{
				pageNo = (pageNo-1) * pageSize;
				sql.append("LIMIT" + pageNo + "," + pageSize);
			}
			try {
			startConn();
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				SubjectBean sb = new SubjectBean();
				sb.setId(rs.getLong(1));
				sb.setSubjectName(rs.getString(2));
				sb.setDescription(rs.getString(3));
				sb.setCourseName(rs.getString(4));
				sb.setCourseId(rs.getLong(5));
				sb.setCreatedBy(rs.getString(6));
				sb.setModifiedBy(rs.getString(7));
				sb.setCreatedDateTime(rs.getTimestamp(8));
				sb.setModifiedDateTime(rs.getTimestamp(9));
				subjectList.add(sb); 
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
			return subjectList;
		}
		
		public List search (SubjectBean sb) throws Exception
		{
			log.debug("SubjectModel search method started");

			return search(sb , 0 , 0);
		}
		
		public List search (SubjectBean sb , int pageNo , int pageSize) throws Exception
		{
			log.debug("SubjectModel search(bean, pageNo, pageSize) method started");

			StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE 1=1");
			if (sb != null) {
				if (sb.getId() > 0) {
					sql.append(" AND ID=" + sb.getId());
				}
				if (sb.getSubjectName() != null && sb.getSubjectName().length() > 0) {
					sql.append(" AND SUBJECT_NAME LIKE '" + sb.getSubjectName() + "%'");
				}
				if (sb.getCourseName() != null && sb.getCourseName().length() > 0) {
					sql.append(" AND COURSE_NAME LIKE '" + sb.getCourseName() + "%'");
				}
			}
		
			if (pageSize > 0) {
	            // Calculate start record index
	            pageNo = (pageNo - 1) * pageSize;

	            sql.append(" Limit " + pageNo + ", " + pageSize);
	            // sql.append(" limit " + pageNo + "," + pageSize);
	        }

	        ArrayList subjectList = new ArrayList();
	        try {
	        	startConn();
	        	ps = conn.prepareStatement(sql.toString());
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	            	sb = new SubjectBean();
					sb.setId(rs.getLong(1));
					sb.setSubjectName(rs.getString(2));
					sb.setDescription(rs.getString(3));
					sb.setCourseName(rs.getString(4));
					sb.setCourseId(rs.getLong(5));
					sb.setCreatedBy(rs.getString(6));
					sb.setModifiedBy(rs.getString(7));
					sb.setCreatedDateTime(rs.getTimestamp(8));
					sb.setModifiedDateTime(rs.getTimestamp(9));
					subjectList.add(sb);
	            }
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	} finally {
	            JDBCDataSource.closeConnection(conn);
	        }

	       
	        return subjectList;
	    }
}
		

