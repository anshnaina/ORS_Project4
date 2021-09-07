package com.sunilOS.ORSProject4.model.test;

import java.util.ArrayList;

import com.sunilOS.ORSProject4.bean.RoleBean;
import com.sunilOS.ORSProject4.model.RoleModel;

public class RoleModelTest {

	public static void main(String[] args) throws Exception
	{
		//nextPK();
		//testDelete();
		//testAdd();
		//testFindByPK();
		//testFindByName();
		//testUpdate();
		testSearch();
		
	}	
		static RoleBean bean = new RoleBean();
		static RoleModel rm = new RoleModel();
		
		public static void testDelete() throws Exception
		{
			
			bean.setId(1);
			rm.delete(bean);
			System.out.println("Deleted");
		
		}
		
		public static void testAdd() throws Exception
		{
			long pk = rm.nextPK();
			bean.setId(pk);
			bean.setRoleName("ansh");
			bean.setDescription("dfwa");
			bean.setCreatedBy("bjlqw");
			bean.setModifiedBy("bgjds");
			bean.setCreatedDateTime(null);
			bean.setModifiedDateTime(null);
			rm.add(bean);
		}
		
		public static void testFindByName() throws Exception
		{
			rm.findByRoleName("few");
		}
		
		public static void testFindByPK() throws Exception
		{
			rm.findByPK(2);
		}
		public static void testUpdate() throws Exception
		{
			bean.setRoleName("ansh");
			bean.setDescription("dfwa");
			bean.setCreatedBy("bjlqw");
			bean.setModifiedBy("bgjds");
			bean.setCreatedDateTime(null);
			bean.setModifiedDateTime(null);
			bean.setId(1);
			rm.update(bean);
		}
		public static void testSearch() throws Exception
		{
			ArrayList list = new ArrayList();
			list = (ArrayList) rm.search(bean);
		}
}
