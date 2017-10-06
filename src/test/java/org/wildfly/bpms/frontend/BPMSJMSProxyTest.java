//TODO: Update test
/*package org.wildfly.bpms.frontend;

import java.util.HashMap;
import java.util.Map;

import org.wildfly.bpms.frontend.proxy.BPMSJMSProxy;
import org.wildfly.bpms.frontend.proxy.BPMSRemoteProxy;



public class BPMSJMSProxyTest
{	
	public static void main(String [] args)
	{
		try {
			BPMSRemoteProxy proxy = new BPMSJMSProxy();
			BPMSJMSProxyTest test = new BPMSJMSProxyTest();
			
//			long processId = test.test2(proxy, "org.kie.uhg.SyncMessageProcessRetry");
//			System.out.println(processId);

			long processId = test.test2(proxy, "org.kie.uhg.AsyncMessageProcess");
			System.out.println(processId);

			test.signalProcessInstance(proxy, processId, "WaitForCallbackEvent", "WaitForCallbackEvent.callbackEventData."+processId);
			
			processId = test.test2(proxy, "org.kie.uhg.MainProcess2");
			System.out.println(processId);
			processId = processId + 2l;
			test.signalProcess(proxy, processId, "WaitForCallbackEvent", "WaitForCallbackEvent.callbackEventData."+processId);
			
			//System.out.println(processId);

			long processId = test.test1(proxy, "org.kie.uhg.MainProcess");
			System.out.println(processId);
			processId = test.test2(proxy, "org.kie.uhg.MainProcess");
			System.out.println(processId);
			processId = test.test2(proxy, "org.kie.uhg.HumanTask");
			System.out.println(processId);
/
//			test.claimStartCompleteHumanTask(8l);
//			test.claimStartCompleteHumanTask(9l);
//
//			test.claimHumanTask(2l);
//			test.startHumanTask(2l);
//			test.completeHumanTask(2l);

		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public BPMSJMSProxyTest() {}
	
	public long test1(BPMSRemoteProxy proxy, String processID)
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

	public long test3(BPMSRemoteProxy proxy, String processID)
	{
		long processInstanceID = -1l;
		try {
			Map<String, Object> callParams = new HashMap<>();
			callParams.put("correlationID", "123456789L");
			callParams.put("syncCommand", "org.wildfly.bpms.workitemhandlers.SyncCall1Command");
			processInstanceID = proxy.startProcess(processID, callParams);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return processInstanceID;
    }

	public void claimStartCompleteHumanTask(long taskId)
	{
		try {
			BPMSRemoteProxy proxy = new BPMSJMSProxy();
			Map<String, Object> data = new HashMap<>();
			data.put("FirstName", "David");
			data.put("LastName", "Tse");
			data.put("PhoneNumber", "1 (845) 459-9970");
			data.put("Address", "12501 Whitewater Drive, Minnetonka MN");
			data.put("Email", "dtse@redhat.com");

			proxy.claimStartCompleteHumanTask(taskId, "approver1", data);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public void claimTask(long taskId)
	{
		try {
			BPMSRemoteProxy proxy = new BPMSJMSProxy();
			proxy.claimTask(taskId, "approver1");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public void startHumanTask(long taskId)
	{
		try {
			BPMSRemoteProxy proxy = new BPMSJMSProxy();
			proxy.startTask(taskId, "approver1");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public void completeTask(long taskId)
	{
		try {
			BPMSRemoteProxy proxy = new BPMSJMSProxy();
			Map<String, Object> data = new HashMap<>();
			data.put("FirstName", "David");
			data.put("LastName", "Tse");
			data.put("PhoneNumber", "1 (845) 459-9970");
			data.put("Address", "12501 Whitewater Drive, Minnetonka MN");
			data.put("Email", "dtse@redhat.com");

			proxy.completeTask(taskId, "approver1", data);
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