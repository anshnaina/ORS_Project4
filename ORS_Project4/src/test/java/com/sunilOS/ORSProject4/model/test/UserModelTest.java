package com.sunilOS.ORSProject4.model.test;

import java.util.ArrayList;

import com.sunilOS.ORSProject4.bean.UserBean;
import com.sunilOS.ORSProject4.model.UserModel;

public class UserModelTest {

	static UserBean bean = new UserBean();
	static UserModel um = new UserModel();
	
	public static void main(String[] args) throws Exception
	{
		//um.nextPK();
		//testAdd();
		//testDelete();
		testFindByEmail();
		//testFindByPK();		
		//testUpdate();
		//testSearch();
	}	
		
		
		public static void testAdd() throws Exception
		{
			long pk = um.nextPK();
			
			bean.setId(pk);
			bean.setFirstName("abc");
			bean.setLastName("def");
			bean.setEmail("dqw");
			bean.setPassword("gfewf");
			bean.setDob(null);
			bean.setMobileNo("cw");
			//bean.setRoleId("4");
			bean.setUnSuccessfulLogin(0);
			bean.setGender("ma");
			bean.setLastLogin(null);
			bean.setLock("fw");
			bean.setRegisteredIP("dw");
			bean.setLastLoginIP("dfw");
			bean.setCreatedBy("");
			bean.setModifiedBy("");
			bean.setCreatedDateTime(null);
			bean.setModifiedDateTime(null);
			
			um.add(bean);
		}
		
		public static void testDelete() throws Exception
		{
			
			bean.setId(2);
			um.delete(bean);
			System.out.println("Deleted");
		
		}
		
		
		public static void testFindByEmail() throws Exception
		{
			
			UserBean bean = um.findByEmail("pankul@gmail.com");
			
			System.out.print(bean.getId());
			System.out.print("\t"+bean.getFirstName());
			System.out.print("\t"+bean.getLastName());
			System.out.print("\t"+bean.getEmail());
			System.out.print("\t"+bean.getPassword());
			System.out.print("\t"+bean.getDob());
			System.out.print("\t"+bean.getMobileNo());
			System.out.print("\t"+bean.getRoleId());
			System.out.print("\t"+bean.getUnSuccessfulLogin());
			System.out.print("\t"+bean.getGender());
			System.out.print("\t"+bean.getLastLogin());
			System.out.print("\t"+bean.getLastLoginIP());
			System.out.print("\t"+bean.getCreatedBy());
			System.out.print("\t"+bean.getModifiedBy());
			System.out.print("\t"+bean.getCreatedDateTime());
			System.out.print("\t"+bean.getModifiedDateTime());
			
			
		}
		
		public static void testfindByPK() throws Exception
		{
			um.findByPK(1);
		}
		public static void testUpdate() throws Exception
		{
			
			bean.setFirstName("dnff");
			bean.setLastName("fqw");
			bean.setEmail("fqw");
			bean.setPassword("fqw");
			bean.setDob(null);
			bean.setMobileNo("fq");
			//bean.setRoleId("3");
			bean.setUnSuccessfulLogin(0);
			bean.setGender("fdq");
			bean.setLastLogin(null);
			bean.setLock("fqq1f");
			bean.setRegisteredIP("fqfq");
			bean.setLastLoginIP("fqd");
			bean.setCreatedBy("fqaq");
			bean.setModifiedBy("dqdq");
			bean.setCreatedDateTime(null);
			bean.setModifiedDateTime(null);
			bean.setId(1);
			
			um.update(bean);
		}
		public static void testSearch() throws Exception
		{
			ArrayList list = new ArrayList();
			um.search(bean);
		}
}
