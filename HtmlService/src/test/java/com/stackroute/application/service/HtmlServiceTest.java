package com.stackroute.application.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.stackroute.application.exception.ModelNotFoundException;
import com.stackroute.application.exception.ModelVariableNotFoundException;
import com.stackroute.application.model.HtmlModel;

public class HtmlServiceTest {
	
	
	@Test
	public void testProjectId() throws ModelNotFoundException, ModelVariableNotFoundException {
		HtmlModel HtmlModel = new HtmlModel();
		HtmlModel.setProjectID("12345");
		
		HtmlModel.setBuildStatus("success");
		HtmlService htmlService=new HtmlService();
		htmlService.put(HtmlModel);
		
		assertEquals(HtmlModel, htmlService.getHtmlReport());
	}
	

}
