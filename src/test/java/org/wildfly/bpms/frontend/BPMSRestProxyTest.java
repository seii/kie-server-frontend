//TODO: Update test
/*package org.wildfly.bpms.frontend;

import java.util.HashMap;
import java.util.Map;

import org.wildfly.bpms.frontend.proxy.BPMSRestProxy;


public class BPMSRestProxyTest
{
	public static final String KIE_SERVER = "http://localhost:8080/kie-server/services/rest/server";
	public static final String CONTAINER_ID = "testContainer";
	
	public static void main(String [] args)
	{
		try {
			
			javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
					new javax.net.ssl.HostnameVerifier(){

					    public boolean verify(String hostname,
					            javax.net.ssl.SSLSession sslSession) {
					        return true;
					    }
					});
			
			BPMSRestProxy proxy = new BPMSRestProxy("bpmsAdmin", "jboss_1234", KIE_SERVER, CONTAINER_ID);
			BPMSRestProxyTest test = new BPMSRestProxyTest();

			long processId = test.test2(proxy, "org.kie.uhg.AsyncMessageProcess");
			System.out.println(processId);

			test.signalProcessInstance(proxy, processId, "WaitForCallbackEvent", "WaitForCallbackEvent.callbackEventData."+processId);
			
			processId = test.test2(proxy, "org.kie.uhg.MainProcess2");
			System.out.println(processId);
			
			Thread.sleep(100l); // REST has slower response
			processId = processId + 2l;
			test.signalProcessInstance(proxy, processId, "WaitForCallbackEvent", "WaitForCallbackEvent.callbackEventData."+processId);

			long processId = test.test1(proxy, "org.kie.uhg.MainProcess");
			System.out.println(processId);
			processId = test.test2(proxy, "org.kie.uhg.MainProcess");
			System.out.println(processId);
			processId = test.test2(proxy, "org.kie.uhg.HumanTask");
			System.out.println(processId);

//			test.claimTask(5l);
//			test.claimTask(6l);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public BPMSRestProxyTest() {}
	
	public long test1(BPMSRestProxy proxy, String processID)
	{
		long processInstanceID = -1l;
		try {
			Map<String, Object> callParams = new HashMap<>();
			callParams.put("correlationID", "1234567J");
			callParams.put("syncCommand", "org.wildfly.bpms.workitemhandlers.JSONParserCommand");
			callParams.put("inputJSON", "{\"syncCommand\":{\"className\":\"org.wildfly.bpms.workitemhandlers.AppConfigCommand\"}}");
			processInstanceID = proxy.startProcess(processID, callParams);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return processInstanceID;
    }
	
	public long test2(BPMSRemoteProxy proxy, String processID)
	{
		long processInstanceID = -1l;
		try {
			Map<String, Object> callParams = new HashMap<>();
			callParams.put("correlationID", "1234567L");
			callParams.put("sendMessageCommand", "org.wildfly.bpms.workitemhandlers.ASyncMessageCall");
			callParams.put("processCallbackCommand", "org.wildfly.bpms.workitemhandlers.ASyncCallback");
			processInstanceID = proxy.startProcess(processID, callParams);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return processInstanceID;
    }

	public void claimTask(Long taskId)
	{
		try {
			BPMSRestProxy proxy = new BPMSRestProxy("approver1", "jboss_1234", KIE_SERVER, CONTAINER_ID);
			Map<String, Object> data = new HashMap<>();
			data.put("FirstName", "David");
			data.put("LastName", "Tse");
			data.put("PhoneNumber", "Approved");
			data.put("Address", "12501 Whitewater Drive");
			data.put("Email", "dtse@redhat.com");

			proxy.claimStartCompleteHumanTask(taskId, "approver1", data);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public void signalProcessInstance(BPMSRemoteProxy proxy, long pid, String signal, String signalData)
	{
		try {
			proxy.signalProcessInstance(pid, signal, signalData);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
*/