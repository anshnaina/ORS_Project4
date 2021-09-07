package com.sunilOS.ORSProject4.model.test;

import java.util.ArrayList;

import com.sunilOS.ORSProject4.bean.MarksheetBean;
import com.sunilOS.ORSProject4.model.MarksheetModel;

public class MarksheetModelTest {
	
	static MarksheetBean bean = new MarksheetBean();
	static MarksheetModel mm = new MarksheetModel();
	
	public static void main(String[] args) throws Exception
	{
		//nextPK();
		//testDelete();
		//testAdd();
		//testFindByPK();
		//testFindByRollNo();
		//testUpdate();
		testSearch();
		
	}	
		
		
		public static void testDelete() throws Exception
		{
			
			bean.setId(1);
			mm.delete(bean);
			System.out.println("Deleted");
		
		}
		
		public static void testAdd() throws Exception
		{
			long pk = mm.nextPK();
			
			bean.setId(pk);
			bean.setRollNo("vs");
			bean.setStudentId(1);
			bean.setName("fw");
			bean.setPhysics(67);
			bean.setChemistry(90);
			bean.setMaths(80);
			bean.setCreatedBy("fw");
			bean.setModifiedBy("fw1q");
			bean.setCreatedDateTime(null);
			bean.setModifiedDateTime(null);
			
			mm.add(bean);
		}
		
		public static void testFindByRollNo() throws Exception
		{
			mm.findByRollNo("ansh123");
		}
		
		public static void testFindByPK() throws Exception
		{
			mm.findByPK(2);
		}
		public static void testUpdate() throws Exception
		{
			
			bean.setRollNo("ansh123");
			bean.setStudentId(1);
			bean.setName("ffas");
			bean.setPhysics(67);
			bean.setChemistry(90);
			bean.setMaths(80);
			bean.setCreatedBy("fwdfw");
			bean.setModifiedBy("ffsww1q");
			bean.setCreatedDateTime(null);
			bean.setModifiedDateTime(null);
			bean.setId(1);
			
			mm.update(bean);
		}
		
		public static void testSearch() throws Exception 
		{
			ArrayList list = new ArrayList();
			list = (ArrayList) mm.search(bean);
		}
}
	