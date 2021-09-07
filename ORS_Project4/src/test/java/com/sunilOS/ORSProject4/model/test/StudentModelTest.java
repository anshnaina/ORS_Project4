package com.sunilOS.ORSProject4.model.test;

import java.util.ArrayList;

import com.sunilOS.ORSProject4.bean.StudentBean;
import com.sunilOS.ORSProject4.model.StudentModel;

public class StudentModelTest {

	public static void main(String[] args) throws Exception
	{
		//nextPK();
		//testDelete();
		//testAdd();
		//testFindByPK();
		//testFindByEmailId();
		//testUpdate();
		testSearch();
		
	}	
		static StudentBean bean = new StudentBean();
		static StudentModel sm = new StudentModel();
		
		public static void testDelete() throws Exception
		{
			
			bean.setId(1);
			sm.delete(bean);
			System.out.println("Deleted");
		
		}
		
		public static void testAdd() throws Exception
		{
			long pk = sm.nextPK();
					
			bean.setId(pk);
			bean.setCollegeId(2);
			bean.setCollegeName("fw");
			bean.setFirstName("fjnmkfw");
			bean.setLastName("m;lv");
			bean.setDob(null);
			bean.setMobileNo("97779");
			bean.setEmail("rays@");
			bean.setCreatedBy("nk;lcas");
			bean.setModifiedBy("");
			bean.setCreatedDateTime(null);
			bean.setModifiedDateTime(null);
			sm.add(bean);
		}
		
		public static void testFindByEmailId() throws Exception
		{
			sm.findByEmailId("rays@");
		}
		
		public static void testFindByPK() throws Exception
		{
			sm.findByPK(2);
		}
		public static void testUpdate() throws Exception
		{
			bean.setCollegeId(1);
			bean.setCollegeName("abc");
			bean.setFirstName("ansh");
			bean.setLastName("naina");
			bean.setDob(null);
			bean.setMobileNo("97779");
			bean.setEmail("rays@");
			bean.setCreatedBy("nk;lcas");
			bean.setModifiedBy("");
			bean.setCreatedDateTime(null);
			bean.setModifiedDateTime(null);
			bean.setId(1);
			
			sm.update(bean);
		}
		public static void testSearch() throws Exception
		{
			ArrayList list = new ArrayList();
			list = (ArrayList) sm.search(bean);	
		}
}
