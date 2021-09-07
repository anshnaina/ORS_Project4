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
import com.sunilOS.ORSProject4.bean.SubjectBean;
import com.sunilOS.ORSProject4.bean.TimeTableBean;
import com.sunilOS.ORSProject4.exception.ApplicationException;
import com.sunilOS.ORSProject4.exception.DuplicateRecordException;
import com.sunilOS.ORSProject4.utility.JDBCDataSource;

public class TimeTableModel {

	private static long pk;
	private static Connection conn;
	private static PreparedStatement ps;
	private static ResultSet rs;
    private static Logger log = Logger.getLogger(TimeTableModel.class);


	public static long nextPK() throws Exception {
		
		log.debug("TimeTableModel nextPK method started");

		long pk = 0;
		try {
			startConn();
			ps = conn.prepareStatement("SELECT MAX(ID) FROM ST_TIMETABLE");

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

	public static long add(TimeTableBean bean) throws Exception 
{
		log.debug("TimeTableModel add method started");

		conn = null;
		
		CourseModel Cmodel = new CourseModel();
		CourseBean Cbean = null;
		Cbean = Cmodel.findByPK(bean.getCourseId());
		bean.setCourseName(Cbean.getCourseName());
		

		SubjectModel Smodel = new SubjectModel();
		SubjectBean Sbean = Smodel.findByPK(bean.getSubjectId());
		bean.setSubjectName(Sbean.getSubjectName());
		
		
	
		TimeTableBean duplicateSubName=findBySubjectName(bean.getSubjectName());
		
		if(duplicateSubName!=null){
			throw new DuplicateRecordException("Subject already exists");
			
		}
		
		try 
		{
		startConn();
		pk = nextPK();
		
		conn.setAutoCommit(false);
		ps = conn.prepareStatement("INSERT INTO ST_TIMETABLE VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");

		ps.setLong(1, pk);
		ps.setLong(2, bean.getCourseId());
		ps.setString(3, bean.getCourseName());
		ps.setLong(4, bean.getSubjectId());
		ps.setString(5, bean.getSubjectName());
		ps.setString(6, bean.getSemester());
		ps.setDate(7, new java.sql.Date(bean.getExamDate().getTime()));
		ps.setString(8, bean.getExamTime());
		ps.setString(9, bean.getDescription());
		ps.setString(10, bean.getCreatedBy());
		ps.setString(11, bean.getModifiedBy());
		ps.setTimestamp(12, bean.getCreatedDateTime());
		ps.setTimestamp(13, bean.getModifiedDateTime());

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

	public static void delete(TimeTableBean tb) throws Exception 
	{
		log.debug("TimeTableModel delete method started");

		try 
		{
		startConn();
		ps = conn.prepareStatement("DELETE FROM ST_TIMETABLE WHERE ID=?");

		ps.setLong(1, tb.getId());

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

	public static TimeTableBean findBySubjectName(String subjectName) throws Exception {
		
		log.debug("TimeTableModel findBySubjectName method started");

		conn = null;
		TimeTableBean tb = null;
		
		try
		{
		startConn();
		ps = conn.prepareStatement("SELECT * FROM ST_TIMETABLE WHERE SUBJECT_NAME=?");

		ps.setString(1, subjectName);

		rs = ps.executeQuery();

		while (rs.next()) {
			tb = new TimeTableBean();
			tb.setId(rs.getLong(1));
			tb.setCourseId(rs.getLong(2));
			tb.setCourseName(rs.getString(3));
			tb.setSubjectId(rs.getLong(4));
			tb.setSubjectName(rs.getString(5));
			tb.setSemester(rs.getString(6));
			tb.setExamDate(rs.getDate(7));
			tb.setExamTime(rs.getString(8));
			tb.setDescription(rs.getString(9));
			tb.setCreatedBy(rs.getString(10));
			tb.setModifiedBy(rs.getString(11));
			tb.setCreatedDateTime(rs.getTimestamp(12));
			tb.setModifiedDateTime(rs.getTimestamp(13));
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
		return tb;
	}

	public static TimeTableBean findByPK(long f) throws Exception {
	
		log.debug("TimeTableModel findByPK method started");

		TimeTableBean tb = null;
		try 
		{
		startConn();
		ps = conn.prepareStatement("SELECT * FROM ST_TIMETABLE WHERE ID=?");

		ps.setLong(1, f);

		rs = ps.executeQuery();

		while (rs.next()) {
			tb = new TimeTableBean();
			tb.setId(rs.getLong(1));
			tb.setCourseId(rs.getLong(2));
			tb.setCourseName(rs.getString(3));
			tb.setSubjectId(rs.getLong(4));
			tb.setSubjectName(rs.getString(5));
			tb.setSemester(rs.getString(6));
			tb.setExamDate(rs.getDate(7));
			tb.setExamTime(rs.getString(8));
			tb.setDescription(rs.getString(9));
			tb.setCreatedBy(rs.getString(10));
			tb.setModifiedBy(rs.getString(11));
			tb.setCreatedDateTime(rs.getTimestamp(12));
			tb.setModifiedDateTime(rs.getTimestamp(13));
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
		return tb;
	}

	public static void update(TimeTableBean tBean) throws Exception {
		
		log.debug("TimeTableModel update method started");

		CourseBean cBean = CourseModel.findByPK(tBean.getCourseId());
		String courseName = cBean.getCourseName();
		tBean.setCourseName(courseName);
		
		SubjectBean sBean = SubjectModel.findByPK(tBean.getSubjectId());
		String subjectName = sBean.getSubjectName();
		tBean.setSubjectName(subjectName);
		
		TimeTableBean beanExist = findBySubjectName(tBean.getSubjectName());
		if(beanExist!=null&&beanExist.getId()!=tBean.getId()){
			throw new DuplicateRecordException("Subject already exists");
		}
		try
		{
		startConn();
		conn.setAutoCommit(false);
		ps = conn.prepareStatement("UPDATE ST_TIMETABLE SET COURSE_ID = ?, COURSE_NAME = ?, SUBJECT_ID = ?,SUBJECT_NAME = ?, SEMESTER = ?, EXAM_DATE = ?, EXAM_TIME = ?, DESCRIPTION = ?, CREATED_BY = ?, MODIFIED_BY = ?, CREATED_DATE_TIME = ?, MODIFIED_DATE_TIME = ? WHERE ID = ?");
		
		ps.setLong(1, tBean.getCourseId());
		ps.setString(2, tBean.getCourseName());
		ps.setLong(3, tBean.getSubjectId());
		ps.setString(4, tBean.getSubjectName());
		ps.setString(5, tBean.getSemester());
		ps.setDate(6, new java.sql.Date(tBean.getExamDate().getTime()));
		ps.setString(7, tBean.getExamTime());
		ps.setString(8, tBean.getDescription());
		ps.setString(9, tBean.getCreatedBy());
		ps.setString(10, tBean.getModifiedBy());
		ps.setTimestamp(11, tBean.getCreatedDateTime());
		ps.setTimestamp(12, tBean.getModifiedDateTime());
		ps.setLong(13, tBean.getId());
	
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
		log.debug("TimeTableModel list method started");

		return list (0 , 0);	
	}
	
	public List list(int pageNo, int pageSize) throws Exception
	{
		log.debug("TimeTableModel list(pageNo, pageSize) method started");

		ArrayList timeTablelist = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE");
		
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
			TimeTableBean tb = new TimeTableBean();
			tb.setId(rs.getLong(1));
			tb.setCourseId(rs.getLong(2));
			tb.setCourseName(rs.getString(3));
			tb.setSubjectId(rs.getLong(4));
			tb.setSubjectName(rs.getString(5));
			tb.setSemester(rs.getString(6));
			tb.setExamDate(rs.getDate(7));
			tb.setExamTime(rs.getString(8));
			tb.setDescription(rs.getString(9));
			tb.setCreatedBy(rs.getString(10));
			tb.setModifiedBy(rs.getString(11));
			tb.setCreatedDateTime(rs.getTimestamp(12));
			tb.setModifiedDateTime(rs.getTimestamp(13));
			timeTablelist.add(tb);
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
		return timeTablelist;
	}
	
	public List search (TimeTableBean tb) throws Exception
	{
		log.debug("TimeTableModel search method started");

		return search(tb , 0 , 0);
	}
	
	public List search (TimeTableBean tb , int pageNo , int pageSize) throws Exception
	{
		log.debug("TimeTableModel search(bean, pageNo, pageSize) method started");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE 1=1");
		if (tb != null) {
			if (tb.getId() > 0) {
				sql.append(" AND ID=" + tb.getId());
			}
			if (tb.getSubjectId() > 0) {
				sql.append(" AND SUBJECT_ID LIKE '" + tb.getSubjectId() + "%'");
			}
			if (tb.getCourseId() > 0) {
				sql.append(" AND COURSE_ID LIKE '" + tb.getCourseId() + "%'");
			}
			
			if ((tb.getExamDate() != null) && (tb.getExamDate().getDate() > 0)) {
				Date date = new Date(tb.getExamDate().getTime());
				sql.append(" AND EXAM_DATE LIKE '" + date+"'");
			}
		}
	
		if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;

            sql.append(" LIMIT " + pageNo + ", " + pageSize);
        }

        ArrayList list = new ArrayList();
        try {
        	startConn();
        	ps = conn.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	tb = new TimeTableBean();
				tb.setId(rs.getLong(1));
				tb.setCourseId(rs.getLong(2));
				tb.setCourseName(rs.getString(3));
				tb.setSubjectId(rs.getLong(4));
				tb.setSubjectName(rs.getString(5));
				tb.setSemester(rs.getString(6));
				tb.setExamDate(rs.getDate(7));
				tb.setExamTime(rs.getString(8));
				tb.setDescription(rs.getString(9));
				tb.setCreatedBy(rs.getString(10));
				tb.setModifiedBy(rs.getString(11));
				tb.setCreatedDateTime(rs.getTimestamp(12));
				tb.setModifiedDateTime(rs.getTimestamp(13));
				list.add(tb);
            }
            
            System.out.println("checking list size " + list.size());
        } catch (Exception e) {
        	e.printStackTrace();
        	} finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
	
	public static TimeTableBean checkByCourseName(long CourseId, java.util.Date ExamDate) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		TimeTableBean tbean = null;
		System.out.println("jjjj" + CourseId + ",,," + ExamDate );
		Date Exdate = new Date(ExamDate.getTime());

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE COURSE_ID=? " + "AND EXAM_DATE=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setDate(2, Exdate);
			rs = ps.executeQuery();

			while (rs.next()) {
				tbean = new TimeTableBean();
				tbean.setId(rs.getLong(1));
				tbean.setSubjectId(rs.getLong(2));
				tbean.setSubjectName(rs.getString(3));
				tbean.setCourseId(rs.getLong(4));
				tbean.setCourseName(rs.getString(5));
				tbean.setSemester(rs.getString(6));
				tbean.setExamDate(rs.getDate(7));
				tbean.setExamTime(rs.getString(8));
				tbean.setDescription(rs.getString(9));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in timeTable model checkByCourseName" + e.getMessage());
		}
		return tbean;
	}

	/**
	 * @param CourseId
	 * @param SubjectId
	 * @param ExamDAte
	 * @return tbean
	 * @throws ApplicationException
	 */
	public static TimeTableBean checkBySubjectName(long CourseId, long SubjectId, java.util.Date ExamDAte)
			throws ApplicationException {
		System.out
		.println("jjjj" + CourseId + "kj" + SubjectId + ",,," + ExamDAte );
		PreparedStatement ps = null;
		ResultSet rs = null;
		TimeTableBean tbean = null;
		Date ExDate = new Date(ExamDAte.getTime());
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_TIMETABLE WHERE COURSE_ID=? AND SUBJECT_ID=? AND" + " EXAM_DATE=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setLong(2, SubjectId);
			ps.setDate(3, ExDate);
			rs = ps.executeQuery();

			while (rs.next()) {
				tbean = new TimeTableBean();
				tbean.setId(rs.getLong(1));
				tbean.setSubjectId(rs.getLong(2));
				tbean.setSubjectName(rs.getString(3));
				tbean.setCourseId(rs.getLong(4));
				tbean.setCourseName(rs.getString(5));
				tbean.setSemester(rs.getString(6));
				tbean.setExamDate(rs.getDate(7));
				tbean.setExamTime(rs.getString(8));
				tbean.setDescription(rs.getString(9));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in timeTable model checkBySubjectName..." + e.getMessage());
		}
		return tbean;
	}

	/**
	 * @param CourseId
	 * @param SubjectId
	 * @param semester
	 * @param ExamDAte
	 * 
	 * 
	 */
	public static TimeTableBean checkBysemester(long courseId, long subjectId, String semester, java.util.Date ExamDAte)
			throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		TimeTableBean tbean = null;
System.out.println("jjkkk"+courseId+"jjj"+subjectId+"kk"+semester+"kk"+ExamDAte);
		Date ExDate = new Date(ExamDAte.getTime());

		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_TIMETABLE WHERE COURSE_ID=? AND SUBJECT_ID=? AND" + " SEMESTER=? AND EXAM_DATE=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(1, courseId);
			ps.setLong(2, subjectId);
			ps.setString(3, semester);
			ps.setDate(4, ExDate);
			rs = ps.executeQuery();

			while (rs.next()) {
				tbean = new TimeTableBean();
				tbean.setId(rs.getLong(1));
				tbean.setSubjectId(rs.getLong(2));
				tbean.setSubjectName(rs.getString(3));
				tbean.setCourseId(rs.getLong(4));
				tbean.setCourseName(rs.getString(5));
				tbean.setSemester(rs.getString(6));
				tbean.setExamDate(rs.getDate(7));
				tbean.setExamTime(rs.getString(8));
				tbean.setDescription(rs.getString(9));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in timeTable model checkBySubjectName" + e.getMessage());
		}
		return tbean;
	}

	/**
	 * @param ExamTime
	 * @param CourseId
	 * @param SubjectId
	 * @param semester
	 * @param ExamDAte
	 * @return tbean
	 * @throws ApplicationException
	 */
	public static TimeTableBean checkByExamTime(long courseId, long subjectId, String semester, java.util.Date ExamDAte,
			String ExamTime) throws ApplicationException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		TimeTableBean tbean = null;
		Date ExDate = new Date(ExamDAte.getTime());
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE COURSE_ID=? AND SUBJECT_ID=? AND"
				+ " SEMESTER=? AND EXAM_DATE=? AND EXAM_TIME=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(1, courseId);
			ps.setLong(2, subjectId);
			ps.setString(3, semester);
			ps.setDate(4, ExDate);
			ps.setString(5, ExamTime);
			rs = ps.executeQuery();

			while (rs.next()) {
				tbean = new TimeTableBean();
				tbean.setId(rs.getLong(1));
				tbean.setSubjectId(rs.getLong(2));
				tbean.setSubjectName(rs.getString(3));
				tbean.setCourseId(rs.getLong(4));
				tbean.setCourseName(rs.getString(5));
				tbean.setSemester(rs.getString(6));
				tbean.setExamDate(rs.getDate(7));
				tbean.setExamTime(rs.getString(8));
				tbean.setDescription(rs.getString(9));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in timeTable model checkByexamTime" + e.getMessage());
		}
		return tbean;
	}
}