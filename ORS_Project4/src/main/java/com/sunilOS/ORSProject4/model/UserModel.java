package com.sunilOS.ORSProject4.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.UserBean;
import com.sunilOS.ORSProject4.exception.ApplicationException;
import com.sunilOS.ORSProject4.exception.DuplicateRecordException;
import com.sunilOS.ORSProject4.exception.RecordNotFoundException;
import com.sunilOS.ORSProject4.utility.EmailBuilder;
import com.sunilOS.ORSProject4.utility.EmailMessage;
import com.sunilOS.ORSProject4.utility.EmailUtility;
import com.sunilOS.ORSProject4.utility.JDBCDataSource;



public class UserModel {

	private static long pk;
	private static Connection conn;
	private static PreparedStatement ps;
	private static ResultSet rs;
    private static Logger log = Logger.getLogger(UserModel.class);
	
	public static long nextPK() throws Exception
	{
		log.debug("UserModel nextPK method started");
		
		pk = 0;
		try
		{
		conn = JDBCDataSource.getConnection();
		ps = conn.prepareStatement("SELECT MAX(ID) FROM ST_USER");
		
		rs = ps.executeQuery();
		
		while(rs.next())
		{
			pk = rs.getInt(1);
		}
		}
		catch (SQLException e) {
			e.getStackTrace();
		}
		rs.close();
		
		 return pk+1;
	}
	
	public static long add (UserBean bean) throws Exception
	{
		log.debug("UserModel add method started");

	UserBean existbean = findByEmail(bean.getEmail());
	System.out.println("in find after register " + existbean);

		if(existbean != null)
		{
			throw new DuplicateRecordException("Login Id already exists");
		}		

		try
		{
		conn = JDBCDataSource.getConnection();
		pk = nextPK();
		conn.setAutoCommit(false);
		ps = conn.prepareStatement("INSERT INTO ST_USER VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
		ps.setLong(1,pk);
		ps.setString(2,bean.getFirstName());
		ps.setString(3,bean.getLastName());
		ps.setString(4,bean.getEmail());
		ps.setString(5,bean.getPassword());
		ps.setDate(6, new java.sql.Date(bean.getDob().getTime()));
		ps.setString(7,bean.getMobileNo());
		ps.setLong(8,bean.getRoleId());
		ps.setInt(9,bean.getUnSuccessfulLogin());
		ps.setString(10,bean.getGender());
		ps.setTimestamp(11,bean.getLastLogin());
		ps.setString(12,bean.getLock());
		ps.setString(13,bean.getRegisteredIP());
		ps.setString(14,bean.getLastLoginIP());
		ps.setString(15,bean.getCreatedBy());
		ps.setString(16,bean.getModifiedBy());
		ps.setTimestamp(17,bean.getCreatedDateTime());
		ps.setTimestamp(18,bean.getModifiedDateTime());
	
		ps.executeUpdate();
		conn.commit();
		ps.close();
		}
		catch (SQLException e)
		{conn.rollback();}
		finally
		{
			JDBCDataSource.closeConnection(conn);
		}
		
		System.out.println("primary key is " + pk);
		return pk;
	}
	
	public static void delete (UserBean bean) throws Exception
	{
		log.debug("UserModel delete method started");

		try
		{
		conn = JDBCDataSource.getConnection();
		ps = conn.prepareStatement("DELETE FROM ST_USER WHERE ID=?");
		
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
	
	public static UserBean findByEmail (String email) throws Exception
	{
		log.debug("UserModel findByEmail method started");

		UserBean bean = null;
		try
		{
		conn = JDBCDataSource.getConnection();
		ps = conn.prepareStatement("SELECT * FROM ST_USER WHERE EMAIL=?");

		ps.setString(1,email);
		
		rs = ps.executeQuery();

	while(rs.next())
		{
			bean = new UserBean();
			bean.setId(rs.getLong(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setEmail(rs.getString(4));
			bean.setPassword(rs.getString(5));
			bean.setDob(rs.getDate(6));
			bean.setMobileNo(rs.getString(7));
			bean.setRoleId(rs.getLong(8));
			bean.setUnSuccessfulLogin(rs.getInt(9));
			bean.setGender(rs.getString(10));
			bean.setLastLogin(rs.getTimestamp(11));
			bean.setLock(rs.getString(12));
			bean.setRegisteredIP(rs.getString(13));
			bean.setLastLoginIP(rs.getString(14));
			bean.setCreatedBy(rs.getString(15));
			bean.setModifiedBy(rs.getString(16));
			bean.setCreatedDateTime(rs.getTimestamp(17));
			bean.setModifiedDateTime(rs.getTimestamp(18));
			
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
	public static UserBean findByPK (long f) throws Exception
	{
		log.debug("UserModel findByPK method started");

		UserBean bean = new UserBean();
		try 
		{
		conn = JDBCDataSource.getConnection();
		ps = conn.prepareStatement("SELECT * FROM ST_USER WHERE ID=?");
		
		ps.setLong(1,f);
		
		rs = ps.executeQuery();
		
		while(rs.next())
		{
			bean.setId(rs.getLong(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setEmail(rs.getString(4));
			bean.setPassword(rs.getString(5));
			bean.setDob(rs.getDate(6));
			bean.setMobileNo(rs.getString(7));
			bean.setRoleId(rs.getLong(8));
			bean.setUnSuccessfulLogin(rs.getInt(9));
			bean.setGender(rs.getString(10));
			bean.setLastLogin(rs.getTimestamp(11));
			bean.setLock(rs.getString(12));
			bean.setRegisteredIP(rs.getString(13));
			bean.setLastLoginIP(rs.getString(14));
			bean.setCreatedBy(rs.getString(15));
			bean.setModifiedBy(rs.getString(16));
			bean.setCreatedDateTime(rs.getTimestamp(17));
			bean.setModifiedDateTime(rs.getTimestamp(18));
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
	public static void update (UserBean bean) throws Exception
	{
		log.debug("UserModel update method started");

		try
		{
		conn = JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		ps = conn.prepareStatement("UPDATE ST_USER SET FIRST_NAME=?,LAST_NAME=?,EMAIL=?,DOB=?,MOBILE_NO=?,ROLE_ID=?,UNSUCCESSFUL_LOGIN=?,GENDER=?,LAST_LOGIN=?,USER_LOCK=?,REGISTERED_IP=?,LAST_LOGIN_IP=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
		
		ps.setString(1,bean.getFirstName());
		ps.setString(2,bean.getLastName());
		ps.setString(3,bean.getEmail());
		ps.setDate(4, new java.sql.Date(bean.getDob().getTime()));
		ps.setString(5,bean.getMobileNo());
		ps.setLong(6,bean.getRoleId());
		ps.setInt(7,bean.getUnSuccessfulLogin());
		ps.setString(8,bean.getGender());
		ps.setTimestamp(9,bean.getLastLogin());
		ps.setString(10,bean.getLock());
		ps.setString(11,bean.getRegisteredIP());
		ps.setString(12,bean.getLastLoginIP());
		ps.setString(13,bean.getCreatedBy());
		ps.setString(14,bean.getModifiedBy());
		ps.setTimestamp(15,bean.getCreatedDateTime());
		ps.setTimestamp(16,bean.getModifiedDateTime());
		ps.setLong(17,bean.getId());
		
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
	
	public List search(UserBean bean) throws Exception {
		
		log.debug("UserModel search method started");

		return search(bean, 0, 0);
	}

	public List search(UserBean bean, int pageNo, int pageSize) throws ApplicationException {
		
		log.debug("UserModel search(bean, pageNo, pageSize) method started");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE 1=1");

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
			if (bean.getEmail() != null && bean.getEmail().length() > 0) {
				sql.append(" AND LOGIN like '" + bean.getEmail() + "%'");
			}
			if (bean.getPassword() != null && bean.getPassword().length() > 0) {
				sql.append(" AND PASSWORD like '" + bean.getPassword() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getDate() > 0) {
				sql.append(" AND DOB = " + bean.getDob());
			}
			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" AND MOBILE_NO = " + bean.getMobileNo());
			}
			if (bean.getRoleId() > 0) {
				sql.append(" AND ROLE_ID = " + bean.getRoleId());
			}
		}
		
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
		}
		
		ArrayList list = new ArrayList();
		try {
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setEmail(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setUnSuccessfulLogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastLogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredIP(rs.getString(13));
				bean.setLastLoginIP(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDateTime(rs.getTimestamp(17));
				bean.setModifiedDateTime(rs.getTimestamp(18));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search user");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

	/**
	 * Get List of User
	 *
	 * @return list : List of User
	 * @throws ApplicationException
	 */

	public List list() throws ApplicationException {
		
		log.debug("UserModel list method started");

		return list(0, 0);
	}

	/**
	 * Get List of User with pagination
	 *
	 * @return list : List of users
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws ApplicationException
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {
		
		log.debug("UserModel list(pageNo, pageSize) method started");

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_USER");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				UserBean bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setEmail(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setUnSuccessfulLogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastLogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredIP(rs.getString(13));
				bean.setLastLoginIP(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDateTime(rs.getTimestamp(17));
				bean.setModifiedDateTime(rs.getTimestamp(18));

				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;

	}
	

	public UserBean authenticate(String email, String password) throws ApplicationException {
	
		log.debug("UserModel authenticate method started");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE EMAIL = ? AND PASSWORD = ?");
		UserBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, email);
			ps.setString(2, password);
			rs = ps.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setEmail(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setUnSuccessfulLogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastLogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredIP(rs.getString(13));
				bean.setLastLoginIP(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDateTime(rs.getTimestamp(17));
				bean.setModifiedDateTime(rs.getTimestamp(18));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in get roles");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	
	public boolean forgotPassword (String email) throws Exception
	{
		log.debug("UserModel forgotPassword method started");

		UserBean user = findByEmail(email);
		boolean flag = false;
		
		if(user.equals(null))
		{
			throw new RecordNotFoundException("Email ID does not exist !");
		}
		
		HashMap <String , String> map = new HashMap<String, String>();
		map.put("email" , user.getEmail());
		map.put("password" , user.getPassword());
		map.put("firstName" , user.getFirstName());
		map.put("lastName" , user.getLastName());
		
		String message = EmailBuilder.getForgotPasswordMessage(map);
		
		EmailMessage msg = new EmailMessage();
		msg.setTo(email);
		msg.setSubject("Rays ORS password recovery");
		msg.setMessage(message);	
		msg.setMessageType(EmailMessage.HTML_MSG);
		EmailUtility.sendMail(msg);
		flag = true;
		
		return flag;
	}
	
	public boolean changePassword (Long id, String oldPassword, String newPassword) throws Exception
	{
		log.debug("UserModel changePassword method started");

		boolean flag = false;
		
		UserBean bean = findByPK(id);
		if(bean != null && bean.getPassword().equals(oldPassword))
		{
			bean.setPassword(newPassword);
			update(bean);
			flag = true;
		}
		
		HashMap<String, String> msgMap = new HashMap<String, String>();

		msgMap.put("email", bean.getEmail());
		msgMap.put("password", bean.getPassword());
		msgMap.put("firstName", bean.getFirstName());
		msgMap.put("lastName", bean.getLastName());
		
		String message = EmailBuilder.getChangePasswordMessage(msgMap);
		
		EmailMessage email = new EmailMessage();
		
		email.setTo(bean.getEmail());
		email.setSubject("SUNARYS ORS Password has been changed Successfully");
		email.setMessage(message);
		email.setMessageType(EmailMessage.HTML_MSG);
		
		EmailUtility.sendMail(email);
		
		return flag;
		
	}
	
	public long registerUser(UserBean bean) throws Exception 
	{
		log.debug("UserModel registerUser method started");

		pk  = add(bean);
		System.out.println("registerUser model");
		
		HashMap<String, String> regMap	= new HashMap<String, String>();
		regMap.put("email", bean.getEmail());
		regMap.put("password", bean.getPassword());
		
		String message = EmailBuilder.getUserRegistrationMessage(regMap);
		
		EmailMessage email = new EmailMessage();
		
		email.setTo(bean.getEmail());
		email.setSubject("Registration is successful for ORS Project-SunilOS");
		email.setMessage(message);
		email.setMessageType(EmailMessage.HTML_MSG);
		
		EmailUtility.sendMail(email);
		
		return pk;
	}

}

