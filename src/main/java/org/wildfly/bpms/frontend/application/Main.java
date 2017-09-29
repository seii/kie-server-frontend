package org.wildfly.bpms.frontend.application;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.bpms.frontend.api.console.BpmsJobConsole;
import org.wildfly.bpms.frontend.api.console.BpmsKieConsole;
import org.wildfly.bpms.frontend.api.console.BpmsProcessConsole;
import org.wildfly.bpms.frontend.api.console.BpmsQueryConsole;
import org.wildfly.bpms.frontend.api.console.BpmsUIConsole;
import org.wildfly.bpms.frontend.api.console.BpmsUserTaskConsole;
import org.wildfly.bpms.frontend.constants.Constants;
import org.wildfly.bpms.frontend.exception.BpmsFrontendException;
import org.wildfly.bpms.frontend.proxy.BpmsJmsProxy;
import org.wildfly.bpms.frontend.proxy.BpmsProxyInterface;
import org.wildfly.bpms.frontend.proxy.BpmsRestProxy;

public class Main {
	
	private static Logger logger = LoggerFactory.getLogger(Main.class);
	
	private static BpmsProxyInterface proxy = null;
	
	private static BpmsJobConsole jobConsole;
    private static BpmsKieConsole kieConsole;
    private static BpmsProcessConsole processConsole;
    private static BpmsQueryConsole queryConsole;
    private static BpmsUIConsole uiConsole;
    private static BpmsUserTaskConsole userTaskConsole;

	public static void main(String[] args) {
		Map<String, String> argMap = parseArguments(args);
		
		String command = argMap.get("command");
		String transportType = argMap.get("transport");
		String helpValue = null;
		
		if(argMap.get("help") != null) {
			helpValue = argMap.get("help");
			String message = parseHelp(helpValue);
			System.console().writer().println(message);
		}else if(argMap.get("?") != null) {
			helpValue = argMap.get("?");
			String message = parseHelp(helpValue);
			System.console().writer().println(message);
		}else if(argMap.isEmpty()) {
			String message = parseHelp(helpValue);
			System.console().writer().println(message);
		}else if(command != null) {
			if(transportType != null) {
				if(transportType.equals("rest")) {
					proxy = new BpmsRestProxy();
				}else if(transportType.equals("jms")) {
					proxy = new BpmsJmsProxy();
				}else {
					logger.error("Invalid transport value {} was specified.", transportType);
					exit();
				}
			}else {
				logger.error(Constants.NO_TRANSPORT_VALUE_SPECIFIED);
				exit();
			}
			
			initialize();
			
			executeCommand(command);
		}else {
			logger.error(Constants.NOT_A_VALID_PARAMETER);
			exit();
		}
	}
	
	private static void executeCommand(String command) {
		ArrayList<String> userResponse = new ArrayList<>(1);
		PrintWriter pw = System.console().writer();
		
		try {
		
			switch(command) {
			//Job commands
				case "schedulerequest":
					userResponse = getResponses("command", "date (in MM-dd-yyyy format)",
							"data (comma separated)", "container ID");
					pw.println(jobConsole.scheduleRequestConsole(userResponse.get(0),
							userResponse.get(1), userResponse.get(2).split(","), userResponse.get(3)));
					break;
				case "cancelrequest":
					userResponse = getResponses("request ID");
					pw.println(jobConsole.cancelRequestConsole(userResponse.get(0)));
					break;
				case "requeuerequest":
					userResponse = getResponses("request ID");
					pw.println(jobConsole.requeueRequestConsole(userResponse.get(0)));
					break;
				case "getrequestsbystatus":
					userResponse = getResponses("page", "page size", "statuses (comma separated)");
					pw.println(jobConsole.getRequestsByStatusConsole(userResponse.get(0), userResponse.get(1),
							userResponse.get(2).split(",")));
					break;
				case "getrequestsbybusinesskey":
					userResponse = getResponses("page", "page size", "business key");
					pw.println(jobConsole.getRequestsByBusinessKeyConsole(userResponse.get(2),
							userResponse.get(0), userResponse.get(1)));
					break;
				case "getrequestsbycommand":
					userResponse = getResponses("page", "page size", "command");
					pw.println(jobConsole.getRequestsByCommandConsole(userResponse.get(2), userResponse.get(0), userResponse.get(1)));
					break;
				case "getrequestbyid":
					userResponse = getResponses("request ID", "return results with errors? (true or false)", "return results with data? (true or false)");
					pw.println(jobConsole.getRequestByIdConsole(userResponse.get(0), userResponse.get(1), userResponse.get(2)));
					break;
				//Kie commands
				case "getserverstatus":
					pw.println(kieConsole.getServerStatusConsole());
					break;
				case "destroycontainer":
					userResponse = getResponses("container ID");
					kieConsole.destroyContainer(userResponse.get(0));
					break;
				case "createcontainer":
					userResponse = getResponses("container ID", "group ID", "artifact ID", "version ID");
					pw.println(kieConsole.createContainerConsole(userResponse.get(0), userResponse.get(1), userResponse.get(2),
							userResponse.get(3)));
					break;
				case "listcontainers":
					pw.println(kieConsole.listContainersConsole());
					break;
				case "listcontainersbyfilter":
					userResponse = getResponses("group ID", "artifact ID", "version ID", "statuses (comma separated)");
					pw.println(kieConsole.listContainersByFilterConsole(userResponse.get(0), userResponse.get(1),
							userResponse.get(2), userResponse.get(3).split(",")));
					break;
				case "getcontainerinfo":
					userResponse = getResponses("container ID");
					pw.println(kieConsole.getContainerInfoConsole(userResponse.get(0)));
					break;
				case "getscannerinfo":
					userResponse = getResponses("container ID");
					pw.println(kieConsole.getScannerInfoConsole(userResponse.get(0)));
					break;
				case "updatescanner":
					userResponse = getResponses("container ID", "interval in seconds (0 for none)", "scanner status");
					pw.println(kieConsole.updateScannerConsole(userResponse.get(0), userResponse.get(1), userResponse.get(2)));
					break;
				case "updatereleaseid":
					userResponse = getResponses("container ID", "group ID", "artifact ID", "version ID");
					pw.println(kieConsole.updateReleaseIdConsole(userResponse.get(0), userResponse.get(1),
							userResponse.get(2), userResponse.get(3)));
					break;
				//Process commands
				case "getprocessdefinition":
					userResponse = getResponses("process definition ID");
					pw.println(processConsole.getProcessDefinitionConsole(userResponse.get(0)));
					break;
				case "getreusablesubprocessdefinitions":
					userResponse = getResponses("process definition ID");
					pw.println(processConsole.getReusableSubProcessDefinitionsConsole(userResponse.get(0)));
					break;
				case "getprocessvariabledefinitions":
					userResponse = getResponses("process definition ID");
					pw.println(processConsole.getProcessVariableDefinitionsConsole(userResponse.get(0)));
					break;
				case "getservicetaskdefinitions":
					userResponse = getResponses("process definition ID");
					pw.println(processConsole.getServiceTaskDefinitionsConsole(userResponse.get(0)));
					break;
				case "getassociatedentitydefinitions":
					userResponse = getResponses("process definition ID");
					pw.println(processConsole.getAssociatedEntityDefinitionsConsole(userResponse.get(0)));
					break;
				case "getusertaskdefinitions":
					userResponse = getResponses("process definition ID");
					pw.println(processConsole.getUserTaskDefinitionsConsole(userResponse.get(0)));
					break;
				case "getusertaskinputdefinitions":
					userResponse = getResponses("process definition ID", "task name");
					pw.println(processConsole.getUserTaskInputDefinitionsConsole(userResponse.get(0), userResponse.get(1)));
					break;
				case "getusertaskoutputdefinitions":
					userResponse = getResponses("process definition ID", "task name");
					pw.println(processConsole.getUserTaskOutputDefinitionsConsole(userResponse.get(0), userResponse.get(1)));
					break;
				case "startprocesswithvars":
					userResponse = getResponses("process name", "variables");
					pw.println(processConsole.startProcessWithVarsConsole(userResponse.get(0), userResponse.get(1).split(",")));
					break;
				case "startprocess":
					userResponse = getResponses("process name");
					pw.println(processConsole.startProcess(userResponse.get(0)));
					break;
				case "startprocesswithkey":
					//TODO: Need to find a way to supply a CorrelationKey from the console
					//userResponse = getResponses("", "");
					//pw.println(processConsole.startProcessWithKeyConsole(userResponse.get(0), userResponse.get(1)));
					break;
				case "startprocesswithkeywithvars":
					//TODO: Need to find a way to supply a CorrelationKey from the console
					break;
				case "abortprocessinstance":
					userResponse = getResponses("process instance ID");
					pw.println(processConsole.abortProcessInstanceConsole(userResponse.get(0)));
					break;
				case "abortprocessinstances":
					userResponse = getResponses("process instance IDs (comma-separated)");
					pw.println(processConsole.abortProcessInstancesConsole(userResponse.get(0)));
					break;
				case "getprocessinstancevariable":
					userResponse = getResponses("process instance ID", "variable name");
					pw.println(processConsole.getProcessInstanceVariableConsole(userResponse.get(0), userResponse.get(1)));
					break;
				case "getprocessinstancevariablebytype":
					userResponse = getResponses("process instance ID", "variable name", "variable data type");
					pw.println(processConsole.getProcessInstanceVariableByTypeConsole(
							userResponse.get(0), userResponse.get(1), userResponse.get(2)));
					break;
				case "isprocessfinished":
					userResponse = getResponses("process instance ID");
					pw.println(processConsole.isProcessFinishedConsole(userResponse.get(0)));
					break;
				case "processcompletedsuccessfully":
					userResponse = getResponses("process instance ID");
					pw.println(processConsole.processCompletedSuccessfullyConsole(userResponse.get(0)));
					break;
				case "getprocessinstancevariables":
					userResponse = getResponses("process instance ID");
					pw.println(processConsole.getProcessInstanceVariablesConsole(userResponse.get(0)));
					break;
				case "signalprocessinstance":
					userResponse = getResponses("process instance ID", "signal name", "event type");
					pw.println(processConsole.signalProcessInstanceConsole(userResponse.get(0),
							userResponse.get(1), userResponse.get(2)));
					break;
				case "signalprocessinstances":
					userResponse = getResponses("process instance IDs (comma-separated)", "signal name", "event data type");
					pw.println(processConsole.signalProcessInstancesConsole(userResponse.get(1),
							userResponse.get(2), userResponse.get(0)));
					break;
				case "signal":
					userResponse = getResponses("signal name", "event data type");
					pw.println(processConsole.signalConsole(userResponse.get(0), userResponse.get(1)));
					break;
				case "getavailablesignals":
					userResponse = getResponses("process instance ID");
					pw.println(processConsole.getAvailableSignalsConsole(userResponse.get(0)));
					break;
				case "setprocessvariable":
					userResponse = getResponses("process instance ID", "variable name", "variable value");
					pw.println(processConsole.setProcessVariableConsole(userResponse.get(0),
							userResponse.get(1), userResponse.get(2)));
					break;
				case "setprocessvariables":
					userResponse = getResponses("process instance ID",
							"variables (in the format key1=value1,key2=value2, etc.)");
					pw.println(processConsole.setProcessVariablesConsole(userResponse.get(0), userResponse.get(1)));
					break;
				case "getprocessinstance":
					userResponse = getResponses("process instance ID");
					pw.println(processConsole.getProcessInstanceConsole(userResponse.get(0)));
					break;
				case "getprocessinstancewithvars":
					userResponse = getResponses("process instance ID");
					pw.println(processConsole.getProcessInstanceWithVarsConsole(userResponse.get(0)));
					break;
				case "completeworkitem":
					userResponse = getResponses("process instance ID", "work item ID", "results");
					pw.println(processConsole.completeWorkItemConsole(userResponse.get(0),
							userResponse.get(1), userResponse.get(2)));
					break;
				case "abortworkitem":
					userResponse = getResponses("process instance ID", "work item ID");
					pw.println(processConsole.abortWorkItemConsole(userResponse.get(0), userResponse.get(1)));
					break;
				case "getworkitem":
					userResponse = getResponses("process instance ID", "work item ID");
					pw.println(processConsole.getWorkItemConsole(userResponse.get(0), userResponse.get(1)));
					break;
				case "getworkitembyprocessinstances":
					userResponse = getResponses("process instance IDs (comma-separated)");
					pw.println(processConsole.getWorkItemByProcessInstancesConsole(userResponse.get(0)));
					break;
				//Query commands
				case "findprocessdefinitionsbycontaineridprocessid":
					break;
				case "findprocessdefinitionsbyid":
					//TODO:
					break;
				case "findprocessdefinitions":
					userResponse = getResponses("page", "page size");
					pw.println(queryConsole.findProcessDefinitionsConsole(userResponse.get(0),
							userResponse.get(1)));
					break;
				case "findprocessdefinitionsbyfilter":
					break;
				case "findprocessdefinitionsbycontainerid":
					//TODO:
					break;
				case "findprocessdefinitionswithsort":
					break;
				case "findprocessdefinitionswithsortwithfilter":
					break;
				case "findprocessdefinitionsbycontaineridwithsort":
					break;
				case "findprocessinstances":
					//TODO:
					break;
				case "findprocessinstancesbycorrelationkey":
					break;
				case "findprocessinstancesbyprocessid":
					//TODO:
					break;
				case "findprocessinstancesbyprocessname":
					//TODO:
					break;
				case "findprocessinstancesbycontainerid":
					//TODO:
					break;
				case "findprocessinstancesbystatus":
					break;
				case "findprocessinstancesbyinitiator":
					break;
				case "findprocessinstancesbyvariable":
					break;
				case "findprocessinstancesbyvariableandvalue":
					break;
				case "findprocessinstanceswithsort":
					break;
				case "findprocessinstancesbycorrelationkeywithsort":
					break;
				case "findprocessinstancesbyprocessidwithsort":
					break;
				case "findprocessinstancesbyprocessnamewithsort":
					break;
				case "findprocessinstancesbycontaineridwithsort":
					break;
				case "findprocessinstancesbystatuswithsort":
					break;
				case "findprocessinstancesbyinitiatorwithsort":
					break;
				case "findprocessinstancesbyvariablewithsort":
					break;
				case "findprocessinstancesbyvariableandvaluewithsort":
					break;
				case "findprocessinstancebyid":
					//TODO:
					break;
				case "findprocessinstancebyidwithvars":
					//TODO:
					break;
				case "findprocessinstancebycorrelationkey":
					break;
				case "findnodeinstancebyworkitemid":
					break;
				case "findactivenodeinstances":
					break;
				case "findcompletednodeinstances":
					break;
				case "findnodeinstances":
					break;
				case "findvariablescurrentstate":
					break;
				case "findvariableshistory":
					break;
				case "registerquery":
					break;
				case "replacequery":
					break;
				case "unregisterquery":
					break;
				case "getquery":
					break;
				case "getqueries":
					break;
				case "query":
					break;
				case "querywithorderby":
					break;
				case "querywithcolumnmapping":
					break;
				case "querywithparameters":
					break;
				//UI commands
				case "getprocessformbylanguage":
					break;
				case "getprocessform":
					break;
				case "gettaskformbylanguage":
					break;
				case "gettaskform":
					break;
				case "getprocessimage":
					userResponse = getResponses("process ID");
					pw.println(uiConsole.getProcessImageConsole(userResponse.get(0)));
					break;
				case "getprocessinstanceimage":
					break;
				//User Task commands
				case "activatetask":
					userResponse = getResponses("task ID", "user ID");
					pw.println(String.format(Constants.SENDING_COMMAND, "activatetask"));
					userTaskConsole.activateTaskConsole(userResponse.get(0), userResponse.get(1));
					break;
				case "claimtask":
					//TODO:
					break;
				case "completetask":
					//TODO:
					break;
				case "completeautoprogress":
					break;
				case "delegatetask":
					//TODO:
					break;
				case "exittask":
					//TODO:
					break;
				case "failtask":
					break;
				case "forwardtask":
					break;
				case "releasetask":
					//TODO:
					break;
				case "resumetask":
					//TODO:
					break;
				case "skiptask":
					break;
				case "starttask":
					//TODO:
					break;
				case "stoptask":
					//TODO:
					break;
				case "suspendtask":
					//TODO:
					break;
				case "nominatetask":
					break;
				case "settaskpriority":
					break;
				case "settaskexpirationdate":
					//TODO:
					break;
				case "settaskskipable":
					break;
				case "settaskname":
					break;
				case "settaskdescription":
					break;
				case "savetaskcontent":
					break;
				case "gettaskoutputcontentbytaskid":
					//TODO:
					break;
				case "gettaskinputcontentbytaskid":
					//TODO:
					break;
				case "deletetaskcontent":
					break;
				case "addtaskcomment":
					//TODO:
					break;
				case "deletetaskcomment":
					//TODO:
					break;
				case "gettaskcommentsbytaskid":
					//TODO:
					break;
				case "gettaskcommentbyid":
					break;
				case "addtaskattachment":
					break;
				case "deletetaskattachment":
					break;
				case "gettaskattachmentbyid":
					break;
				case "gettaskattachmentcontentbyid":
					break;
				case "gettaskattachmentsbytaskid":
					break;
				case "gettaskinstance":
					//TODO:
					break;
				case "gettaskinstancewithinputoutput":
					//TODO:
					break;
				case "findtaskbyworkitemid":
					break;
				case "findtaskbyid":
					//TODO:
					break;
				case "findtasksassignedasbusinessadministrator":
					break;
				case "findtasksassignedasbusinessadministratorwithstatus":
					break;
				case "findtasksassignedaspotentialowner":
					break;
				case "findtasksassignedaspotentialownerwithstatus":
					break;
				case "findtasksassignedaspotentialownerwithgroups":
					break;
				case "findtasksowned":
					break;
				case "findtasksownedwithstatuses":
					break;
				case "findtasksbystatusbyprocessinstanceid":
					break;
				case "findtasks":
					//TODO:
					break;
				case "findtaskevents":
					break;
				case "findtasksbyvariable":
					break;
				case "findtasksbyvariableandvalue":
					break;
				case "findtasksassignedasbusinessadministratorwithsort":
					break;
				case "findtasksassignedasbusinessadministratorwithstatuswithsort":
					break;
				case "findtasksassignedaspotentialowners":
					break;
				case "findtasksassignedaspotentialownerwithstatuswithsort":
					break;
				case "findtasksassignedaspotentialownerwithgroupswithsort":
					break;
				case "findtasksownedwithsort":
					break;
				case "findtasksownedwithstatus":
					break;
				case "findtasksbystatusbyprocessinstanceidwithsort":
					break;
				case "findtaskswithsort":
					break;
				case "findtaskeventswithsort":
					break;
				case "findtasksbyvariablewithsort":
					break;
				case "findtasksbyvariableandvaluewithsort":
					break;
				case "claimstartcompletehumantask":
					//TODO:
					break;
				case "getinputdata":
					//TODO:
					break;
				case "getoutputdata":
					//TODO:
					break;
				default:
					logger.error("Invalid command {} was entered, please enter a valid command.", command);
					exit();
					break;
			}
		} catch(BpmsFrontendException e) {
			logger.error(e.getMessage());
		}
	}

	private static String parseHelp(String helpValue) {
		String message;
		
		try{
			switch(helpValue) {
				case "":
					message = Constants.BASIC_HELP_MESSAGE;
					break;
				case "job":
					message = "Valid job commands:\n" + Constants.VALID_JOB_COMMANDS;
					break;
				case "kie":
					message = "Valid kie commands:\n" + Constants.VALID_KIE_COMMANDS;
					break;
				case "process":
					message = "Valid process commands:\n" + Constants.VALID_PROCESS_COMMANDS;
					break;
				case "query":
					message = "Valid query commands:\n" + Constants.VALID_QUERY_COMMANDS;
					break;
				case "ui":
					message = "Valid ui commands:\n" + Constants.VALID_UI_COMMANDS;
					break;
				case "usertask":
					message = "Valid usertask commands:\n" + Constants.VALID_USER_TASK_COMMANDS;
					break;
				default:
					message = Constants.NO_HELP_FOR_THIS_COMMAND;
					break;
			}
		}catch(NullPointerException e) {
			message = Constants.BASIC_HELP_MESSAGE;
		}
		
		return message;
	}
	
	private static ArrayList<String> getResponses(String ...prompts) {
		ArrayList<String> promptList = new ArrayList<>(1);
		for(String prompt : prompts) {
			promptList.add(prompt);
		}
		
		Map<String, String> commandParams = generateParamKeys(promptList.stream().toArray(String[]::new));
		ArrayList<String> userResponses = askUser(commandParams.keySet().stream().toArray(String[]::new));
		
		return userResponses;
	}
	
	private static ArrayList<String> askUser(String[] questions) {
		ArrayList<String> responses = new ArrayList<String>(1);
		
		for(int i = 0; i < questions.length; i++) {
			String response = System.console().readLine("Enter {}", questions[i]);
			if(response == null) {
				responses.add("");
			}else {
				responses.add(response);
			}
		}
		
		return responses;
	}
	
	private static Map<String, String> generateParamKeys(String ...params) {
		HashMap<String, String> paramMap = new HashMap<>();
		
		for(String param : params) {
			paramMap.put(param, "");
		}
		
		return paramMap;
	}
	
	private static void initialize() {
		if(proxy != null) {
			jobConsole = new BpmsJobConsole(proxy.getKieServicesClient());
	        kieConsole = new BpmsKieConsole(proxy.getKieServicesClient());
	        processConsole = new BpmsProcessConsole(proxy.getContainerId(), proxy.getKieServicesClient());
	        queryConsole = new BpmsQueryConsole(proxy.getContainerId(), proxy.getKieServicesClient());
	        uiConsole = new BpmsUIConsole(proxy.getContainerId(), proxy.getKieServicesClient());
	        userTaskConsole = new BpmsUserTaskConsole(proxy.getContainerId(), proxy.getKieServicesClient());
		}else {
			logger.error("Unable to initiate the specified transport type!");
			exit();
		}
	}

	private static Map<String, String> parseArguments(String[] args) {
		logger.debug("Entering parseArguments");
		
		Map<String, String> argMap = new HashMap<>();
		for(int i = 0; i < args.length; i++) {
			//Look for arguments with the format "-option value"
			if(args[i].startsWith("-")) {
				String key = args[i].substring(1);
				if((i + 1) <= args.length && !args[i + 1].startsWith("-")
						&& args[i + 1] != null && args[i + 1] instanceof String) {
					argMap.put(key, args[i + 1]);
					i++;
				}
			}
		}
		
		logger.debug("Leaving parseArguments");
		return argMap;
	}
	
	private static void exit() {
		logger.error(Constants.PROGRAM_IS_EXITING);
		System.exit(-1);
	}

}
