package org.wildfly.bpms.frontend.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.bpms.frontend.api.BpmsProcessServices;
import org.wildfly.bpms.frontend.api.BpmsQueryServices;
import org.wildfly.bpms.frontend.api.BpmsUIServices;
import org.wildfly.bpms.frontend.api.BpmsUserTaskServices;
import org.wildfly.bpms.frontend.api.console.BpmsJobConsole;
import org.wildfly.bpms.frontend.api.console.BpmsKieConsole;
import org.wildfly.bpms.frontend.api.console.BpmsProcessConsole;
import org.wildfly.bpms.frontend.api.console.BpmsQueryConsole;
import org.wildfly.bpms.frontend.api.console.BpmsUIConsole;
import org.wildfly.bpms.frontend.api.console.BpmsUserTaskConsole;
import org.wildfly.bpms.frontend.constants.Constants;
import org.wildfly.bpms.frontend.proxy.BPMSJMSProxy;
import org.wildfly.bpms.frontend.proxy.BPMSRestProxy;
import org.wildfly.bpms.frontend.proxy.BpmsProxyInterface;

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
					proxy = new BPMSRestProxy();
				}else if(transportType.equals("jms")) {
					proxy = new BPMSJMSProxy();
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
		
		switch(command) {
		//Job commands
			case "schedulerequest":
				userResponse = getResponses("command", "date (in MM-dd-yyyy format)",
						"data (comma separated)", "containerId");
				jobConsole.scheduleRequestConsole(userResponse.get(0), userResponse.get(1), userResponse.get(2).split(","),
						userResponse.get(3));
				break;
			case "cancelrequest":
				//jobServices.cancelRequest(requestId);
				break;
			case "requeuerequest":
				//jobServices.requeueRequest(requestId);
				break;
			case "getrequestsbystatus":
				//jobServices.getRequestsByStatus(statuses, page, pageSize)
				break;
			case "getrequestsbybusinesskey":
				//jobServices.getRequestsByBusinessKey(businessKey, page, pageSize)
				break;
			case "getrequestsbycommand":
				//jobServices.getRequestsByCommand(command, page, pageSize)
				break;
			case "getrequestbyid":
				//jobServices.getRequestById(requestId, withErrors, withData)
				break;
			//Kie commands
			case "getserverstatus":
				System.console().writer().println(kieConsole.getServerStatusConsole());
				break;
			case "destroycontainer":
				//TODO:
				break;
			case "createcontainer":
				//TODO:
				break;
			case "listcontainers":
				//TODO:
				break;
			case "listcontainersbyfilter":
				break;
			case "getcontainerinfo":
				//TODO:
				break;
			case "getscannerinfo":
				break;
			case "updatescanner":
				break;
			case "updatereleaseid":
				//TODO:
				break;
			//Process commands
			case "getprocessdefinition":
				userResponse = getResponses("process ID");
				System.console().writer().println(processConsole.getProcessDefinitionConsole(userResponse.get(0)));
				break;
			case "getreusablesubprocessdefinitions":
				break;
			case "getprocessvariabledefinitions":
				break;
			case "getservicetaskdefinitions":
				break;
			case "getassociatedentitydefinitions":
				break;
			case "getusertaskdefinitions":
				break;
			case "getusertaskinputdefinitions":
				break;
			case "getusertaskoutputdefinitions":
				break;
			case "startprocesswithvars":
				//TODO:
				break;
			case "startprocess":
				//TODO:
				break;
			case "startprocesswithkey":
				break;
			case "startprocesswithkeywithvars":
				break;
			case "abortprocessinstance":
				//TODO:
				break;
			case "abortprocessinstances":
				//TODO:
				break;
			case "getprocessinstancevariable":
				//TODO:
				break;
			case "getprocessinstancevariablebytype":
				break;
			case "isprocessfinished":
				//TODO:
				break;
			case "processcompletedsuccessfully":
				//TODO:
				break;
			case "getprocessinstancevariables":
				//TODO:
				break;
			case "signalprocessinstance":
				//TODO:
				break;
			case "signalprocessinstances":
				//TODO:
				break;
			case "signal":
				break;
			case "getavailablesignals":
				break;
			case "setprocessvariable":
				//TODO:
				break;
			case "setprocessvariables":
				//TODO:
				break;
			case "getprocessinstance":
				//TODO:
				break;
			case "getprocessinstancewithvars":
				//TODO:
				break;
			case "completeworkitem":
				break;
			case "abortworkitem":
				break;
			case "getworkitem":
				break;
			case "getworkitembyprocessinstances":
				break;
			//Query commands
			case "findprocessdefinitionsbycontaineridprocessid":
				break;
			case "findprocessdefinitionsbyid":
				//TODO:
				break;
			case "findprocessdefinitions":
				userResponse = getResponses("page", "page size");
				System.console().writer().println(queryConsole.findProcessDefinitionsConsole(userResponse.get(0),
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
				System.console().writer().println(uiConsole.getProcessImageConsole(userResponse.get(0)));
				break;
			case "getprocessinstanceimage":
				break;
			//User Task commands
			case "activatetask":
				userResponse = getResponses("task ID", "user ID");
				System.console().writer().println(String.format(Constants.SENDING_COMMAND, "activatetask"));
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
