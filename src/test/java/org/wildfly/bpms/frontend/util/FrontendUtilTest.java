package org.wildfly.bpms.frontend.util;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.wildfly.bpms.frontend.exception.BpmsFrontendException;

public class FrontendUtilTest {

	@Test
	public void testParseStringToList() {
		String testString = "value1,value2";
		List<String> testResultList = FrontendUtil.parseStringToList(testString, ",");
		assertTrue(!testResultList.isEmpty() && testResultList.size() == 2 && testResultList.get(0).equals("value1") &&
				testResultList.get(1).equals("value2"));
	}

	@Test
	public void testParseStringToDate() throws BpmsFrontendException {
		String testString = "01-01-2000";
		Date testDate = FrontendUtil.parseStringToDate(testString);
		assertTrue(testDate != null);
	}

	@Test
	public void testParseStringToMapStringObject() throws BpmsFrontendException {
		String testString = "key1=String,key2=String";
		Map<String, Object> testResult = FrontendUtil.parseStringToMapStringObject(testString, ",", "=");
		assertTrue(!testResult.isEmpty() && testResult.size() == 2);
	}

	@Test
	public void testCreateObjFromClassName() throws BpmsFrontendException {
		Object testObj = 0L;
		Object testResult = FrontendUtil.createObjFromClassName("Long");
		assertTrue(testResult != null && testResult.equals(testObj));
	}

}
