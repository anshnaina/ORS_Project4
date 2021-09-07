package com.sunilOS.ORSProject4.model.test;

import java.util.ArrayList;

import com.sunilOS.ORSProject4.bean.CollegeBean;
import com.sunilOS.ORSProject4.model.CollegeModel;

public class CollegeModelTest {

	static CollegeBean bean = new CollegeBean();
	static CollegeModel cm = new CollegeModel();
	
	public static void main(String[] args) throws Exception {
		//cm.nextPK();
		//testDelete();
		//testAdd();
		// testFindByPK();
		//testFindByName();
		//testUpdate();
		 testSearch();

	}

	public static void testAdd() throws Exception 
	{
		long pk = cm.nextPK();

		bean.setId(pk);
		bean.setCollegeName("Ayan");
		bean.setAddress("nfls");
		bean.setState("fpjdfas");
		bean.setCity("fas");
		bean.setMobileNo("fggww");
		bean.setCreatedBy("gengsrbhe");
		bean.setModifiedBy("glkfse");
		bean.setCreatedDateTime(null);
		bean.setModifiedDateTime(null);

		cm.add(bean);
	}
	
	public static void testDelete() throws Exception {

		bean.setId(1);
		cm.delete(bean);
		System.out.println("Deleted");

	}

	public static void testFindByName() throws Exception {
		cm.findByCollegeName("Ayan");
	}

	public static void testFindByPK() throws Exception {
		cm.findByPK(1);
	}

	public static void testUpdate() throws Exception {
		bean.setCollegeName("Harsha");
		bean.setAddress("asfcas");
		bean.setState("fvfsw");
		bean.setCity("fasqs");
		bean.setMobileNo("fggdaww");
		bean.setCreatedBy("gerbhe");
		bean.setModifiedBy("gdqae");
		bean.setCreatedDateTime(null);
		bean.setModifiedDateTime(null);
		bean.setId(15);

		cm.update(bean);
	}

	public static void testSearch() throws Exception {
		ArrayList list = new ArrayList();
		list = (ArrayList) cm.search(bean);
	}

}


