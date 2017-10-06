package org.wildfly.bpms.frontend.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.wildfly.bpms.frontend.constants.Constants;
import org.wildfly.bpms.frontend.exception.BpmsFrontendException;
import org.wildfly.bpms.frontend.proxy.BpmsRestProxy;
import org.wildfly.bpms.frontend.util.FrontendUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Main.class, FrontendUtil.class})
@PowerMockIgnore("javax.management.*")
public class MainTest {

	@Before
	public void setUp() throws Exception {
		Main.setProxy(null);		
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testParseHelpBasic() {
		String message = Main.parseHelp("");
		assertEquals(message, Constants.BASIC_HELP_MESSAGE);
	}
	
	@Test
	public void testParseHelpJob() {
		String message = Main.parseHelp("job");
		assertEquals(message, "Valid job commands:\n" + Constants.VALID_JOB_COMMANDS);
	}
	
	@Test
	public void testParseHelpKie() {
		String message = Main.parseHelp("kie");
		assertEquals(message, "Valid kie commands:\n" + Constants.VALID_KIE_COMMANDS);
	}
	
	@Test
	public void testParseHelpProcess() {
		String message = Main.parseHelp("process");
		assertEquals(message, "Valid process commands:\n" + Constants.VALID_PROCESS_COMMANDS);
	}
	
	@Test
	public void testParseHelpQuery() {
		String message = Main.parseHelp("query");
		assertEquals(message, "Valid query commands:\n" + Constants.VALID_QUERY_COMMANDS);
	}
	
	@Test
	public void testParseHelpUi() {
		String message = Main.parseHelp("ui");
		assertEquals(message, "Valid ui commands:\n" + Constants.VALID_UI_COMMANDS);
	}
	
	@Test
	public void testParseHelpUserTask() {
		String message = Main.parseHelp("usertask");
		assertEquals(message, "Valid usertask commands:\n" + Constants.VALID_USER_TASK_COMMANDS);
	}
	
	@Test
	public void testParseHelpDefault() {
		String message = Main.parseHelp("nonsense");
		assertEquals(message, Constants.NO_HELP_FOR_THIS_COMMAND);
	}
	
	@Test
	public void testParseHelpNull() {
		String message = Main.parseHelp(null);
		assertEquals(message, Constants.BASIC_HELP_MESSAGE);
	}

	@Test
	public void testGetResponses() throws BpmsFrontendException {
		String mockResponse = "testAnswer";
		PowerMockito.mockStatic(FrontendUtil.class);
		PowerMockito.when(FrontendUtil.readLine(Mockito.anyString(), Mockito.anyString())).thenReturn(mockResponse);
		ArrayList<String> responseList = Main.getResponses("question1?", "question2?");
		assertTrue(responseList.size() == 2 && responseList.get(0).equals("testAnswer") &&
				responseList.get(1).equals("testAnswer"));
	}

	@Test
	public void testAskUser() throws BpmsFrontendException {
		String mockResponse = "testAnswer";
		PowerMockito.mockStatic(FrontendUtil.class);
		PowerMockito.when(FrontendUtil.readLine(Mockito.anyString(), Mockito.anyString())).thenReturn(mockResponse);
		ArrayList<String> responseList = Main.askUser(new String[] {"testString1", "testString2"});
		assertTrue(responseList.size() == 2 && responseList.get(0).equals("testAnswer") &&
				responseList.get(1).equals("testAnswer"));
	}

	@Test
	public void testGenerateParamKeys() {
		Map<String, String> testMap = Main.generateParamKeys("test1", "test2", "test3");
		assertTrue(testMap.size() == 3 && testMap.containsKey("test1") && testMap.containsKey("test2") &&
				testMap.containsKey("test3"));
	}
	
	@Test
	public void testInitializeFail() {
		PowerMockito.mockStatic(System.class);
		
		Main.initialize();
		
		PowerMockito.verifyStatic(System.class);
		
		System.exit(-1);
	}

	@Test
	public void testInitializeSuccess() {
		//Setting the proxy to a non-null value is required for success
		BpmsRestProxy proxyMock = PowerMockito.mock(BpmsRestProxy.class);
		Main.setProxy(proxyMock);
		
		Main.initialize();
		assertTrue(Main.getJobConsole() != null && Main.getKieConsole() != null &&
				Main.getProcessConsole() != null && Main.getQueryConsole() != null &&
				Main.getUiConsole() != null && Main.getUserTaskConsole() != null);
	}
	
	@Test
	public void testExitWithError() {
		PowerMockito.mockStatic(System.class);

        Main.exitWithError();

        PowerMockito.verifyStatic(System.class);

        System.exit(-1);
	}

}
