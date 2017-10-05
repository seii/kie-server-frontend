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
import org.wildfly.bpms.frontend.util.FrontendUtil;

public class Main {

	private static Logger logger = LoggerFactory.getLogger(Main.class);

	private static BpmsProxyInterface proxy;

	private static BpmsJobConsole jobConsole;
	private static BpmsKieConsole kieConsole;
	private static BpmsProcessConsole processConsole;
	private static BpmsQueryConsole queryConsole;
	private static BpmsUIConsole uiConsole;
	private static BpmsUserTaskConsole userTaskConsole;
	
	protected Main() {
		super();
	}

	public static void main(String[] args) {
		Map<String, String> argMap = parseArguments(args);

		String command = argMap.get("command");
		String transportType = argMap.get("transport");
		String helpValue = null;

		if (argMap.get("help") != null) {
			helpValue = argMap.get("help");
			String message = parseHelp(helpValue);
			System.console().writer().println(message);
		} else if (argMap.get("?") != null) {
			helpValue = argMap.get("?");
			String message = parseHelp(helpValue);
			System.console().writer().println(message);
		} else if (argMap.isEmpty()) {
			String message = parseHelp(helpValue);
			System.console().writer().println(message);
		} else if (command != null) {
			if (transportType != null) {
				if (transportType.equals("rest")) {
					proxy = new BpmsRestProxy();
				} else if (transportType.equals("jms")) {
					proxy = new BpmsJmsProxy();
				} else {
					logger.error(Constants.INVALID_TRANSPORT_VALUE_SPECIFIED, transportType);
					exitWithError();
				}
			} else {
				logger.error(Constants.NO_TRANSPORT_VALUE_SPECIFIED);
				exitWithError();
			}

			initialize();

			executeCommand(command);
		} else {
			logger.error(Constants.NOT_A_VALID_PARAMETER);
			exitWithError();
		}
	}

	protected static void executeCommand(String command) {
		ArrayList<String> userResponse = new ArrayList<>(1);
		PrintWriter pw = System.console().writer();

		try {

			switch (command) {
			// Job commands
			case "schedulerequest":
				userResponse = getResponses("command", Constants.DATE_RESPONSE, Constants.DATA_COMMA_SEPARATED_RESPONSE,
						Constants.CONTAINER_ID_RESPONSE);
				pw.println(jobConsole.scheduleRequestConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2).split(","), userResponse.get(3)));
				break;
			case "cancelrequest":
				userResponse = getResponses(Constants.REQUEST_ID_RESPONSE);
				pw.println(jobConsole.cancelRequestConsole(userResponse.get(0)));
				break;
			case "requeuerequest":
				userResponse = getResponses("request ID");
				pw.println(jobConsole.requeueRequestConsole(userResponse.get(0)));
				break;
			case "getrequestsbystatus":
				userResponse = getResponses(Constants.PAGE_RESPONSE, Constants.PAGE_SIZE_RESPONSE, Constants.STATUSES_RESPONSE);
				pw.println(jobConsole.getRequestsByStatusConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2)));
				break;
			case "getrequestsbybusinesskey":
				userResponse = getResponses("page", "page size", Constants.BUSINESS_KEY_RESPONSE);
				pw.println(jobConsole.getRequestsByBusinessKeyConsole(userResponse.get(2), userResponse.get(0),
						userResponse.get(1)));
				break;
			case "getrequestsbycommand":
				userResponse = getResponses("page", "page size", "command");
				pw.println(jobConsole.getRequestsByCommandConsole(userResponse.get(2), userResponse.get(0),
						userResponse.get(1)));
				break;
			case "getrequestbyid":
				userResponse = getResponses("request ID", Constants.RESULTS_WITH_ERRORS_RESPONSE,
						Constants.RESULTS_WITH_DATA_RESPONSE);
				pw.println(jobConsole.getRequestByIdConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2)));
				break;
			// Kie commands
			case "getserverstatus":
				pw.println(kieConsole.getServerStatusConsole());
				break;
			case "destroycontainer":
				userResponse = getResponses("container ID");
				kieConsole.destroyContainer(userResponse.get(0));
				break;
			case "createcontainer":
				userResponse = getResponses("container ID", "group ID", "artifact ID", "version ID");
				pw.println(kieConsole.createContainerConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2), userResponse.get(3)));
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
				pw.println(
						kieConsole.updateScannerConsole(userResponse.get(0), userResponse.get(1), userResponse.get(2)));
				break;
			case "updatereleaseid":
				userResponse = getResponses("container ID", "group ID", "artifact ID", "version ID");
				pw.println(kieConsole.updateReleaseIdConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2), userResponse.get(3)));
				break;
			// Process commands
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
				pw.println(
						processConsole.getUserTaskOutputDefinitionsConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "startprocesswithvars":
				userResponse = getResponses("process name", "variables");
				pw.println(processConsole.startProcessWithVarsConsole(userResponse.get(0),
						userResponse.get(1).split(",")));
				break;
			case "startprocess":
				userResponse = getResponses("process name");
				pw.println(processConsole.startProcess(userResponse.get(0)));
				break;
			case "startprocesswithkey":
				// TODO: Need to find a way to supply a CorrelationKey from the console
				// userResponse = getResponses("", "");
				// pw.println(processConsole.startProcessWithKeyConsole(userResponse.get(0),
				// userResponse.get(1)));
				break;
			case "startprocesswithkeywithvars":
				// TODO: Need to find a way to supply a CorrelationKey from the console
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
				pw.println(processConsole.getProcessInstanceVariableByTypeConsole(userResponse.get(0),
						userResponse.get(1), userResponse.get(2)));
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
				pw.println(processConsole.signalProcessInstanceConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2)));
				break;
			case "signalprocessinstances":
				userResponse = getResponses("process instance IDs (comma-separated)", "signal name", "event data type");
				pw.println(processConsole.signalProcessInstancesConsole(userResponse.get(1), userResponse.get(2),
						userResponse.get(0)));
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
				pw.println(processConsole.setProcessVariableConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2)));
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
				pw.println(processConsole.completeWorkItemConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2)));
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
			// Query commands
			case "findprocessdefinitionsbycontaineridprocessid":
				userResponse = getResponses("process instance ID");
				pw.println(queryConsole.findProcessDefinitionByContainerIdProcessIdConsole(userResponse.get(0)));
				break;
			case "findprocessdefinitionsbyid":
				userResponse = getResponses("process instance IDs (comma-separated)");
				pw.println(queryConsole.findProcessDefinitionsByIdConsole(userResponse.get(0)));
				break;
			case "findprocessdefinitions":
				userResponse = getResponses("page", "page size");
				pw.println(queryConsole.findProcessDefinitionsConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "findprocessdefinitionsbyfilter":
				userResponse = getResponses("filter", "page", "page size");
				pw.println(queryConsole.findProcessDefinitionsByFilterConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2)));
				break;
			case "findprocessdefinitionsbycontainerid":
				userResponse = getResponses("page", "page size");
				pw.println(queryConsole.findProcessDefinitionsByContainerIdConsole(userResponse.get(0),
						userResponse.get(1)));
				break;
			case "findprocessdefinitionswithsort":
				userResponse = getResponses("page", "page size", "order by", "sort order");
				pw.println(queryConsole.findProcessDefinitionsWithSortConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2), userResponse.get(3)));
				break;
			case "findprocessdefinitionswithsortwithfilter":
				userResponse = getResponses("filter", "page", "page size", "order by", "sort order");
				pw.println(queryConsole.findProcessDefinitionsWithSortWithFilterConsole(userResponse.get(0),
						userResponse.get(1), userResponse.get(2), userResponse.get(3), userResponse.get(4)));
				break;
			case "findprocessdefinitionsbycontaineridwithsort":
				userResponse = getResponses("page", "page size", "order by", "sort order");
				pw.println(queryConsole.findProcessDefinitionsByContainerIdWithSortConsole(userResponse.get(0),
						userResponse.get(1), userResponse.get(2), userResponse.get(3)));
				break;
			case "findprocessinstances":
				userResponse = getResponses("page", "page size");
				pw.println(queryConsole.findProcessInstancesConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "findprocessinstancesbycorrelationkey":
				// userResponse = getResponses("", "", "");
				// pw.println(queryConsole.findProcessInstancesByCorrelationKeyConsole(correlationKey,
				// page, pageSize));
				// TODO: Need to find a way to supply a CorrelationKey from the console
				break;
			case "findprocessinstancesbyprocessid":
				userResponse = getResponses("process instance ID", "statuses (comma-separated)", "page", "page size");
				pw.println(queryConsole.findProcessInstancesByProcessIdConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2), userResponse.get(3)));
				break;
			case "findprocessinstancesbyprocessname":
				userResponse = getResponses("process name", "statuses (comma-seperated)", "page", "page size");
				pw.println(queryConsole.findProcessInstancesByProcessNameConsole(userResponse.get(0),
						userResponse.get(1), userResponse.get(2), userResponse.get(3)));
				break;
			case "findprocessinstancesbycontainerid":
				userResponse = getResponses("statuses (comma-separated)", "page", "page size");
				pw.println(queryConsole.findProcessInstancesByContainerIdConsole(userResponse.get(0),
						userResponse.get(1), userResponse.get(2)));
				break;
			case "findprocessinstancesbystatus":
				userResponse = getResponses("statuses (comma-separated)", "page", "page size");
				pw.println(queryConsole.findProcessInstancesByStatusConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2)));
				break;
			case "findprocessinstancesbyinitiator":
				userResponse = getResponses("initiator's name", "statuses (comma-separated)", "page", "page size");
				pw.println(queryConsole.findProcessInstancesByInitiatorConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2), userResponse.get(3)));
				break;
			case "findprocessinstancesbyvariable":
				userResponse = getResponses("variable name", "statuses (comma-separated)", "page", "page size");
				pw.println(queryConsole.findProcessInstancesByVariableConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2), userResponse.get(3)));
				break;
			case "findprocessinstancesbyvariableandvalue":
				userResponse = getResponses("variable name", "variable value", "statuses (comma-separated)", "page",
						"page size");
				pw.println(queryConsole.findProcessInstancesByVariableAndValueConsole(userResponse.get(0),
						userResponse.get(1), userResponse.get(2), userResponse.get(3), userResponse.get(4)));
				break;
			case "findprocessinstanceswithsort":
				userResponse = getResponses("page", "page size", "sort by", "sort order");
				pw.println(queryConsole.findProcessInstancesWithSortConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2), userResponse.get(3)));
				break;
			case "findprocessinstancesbycorrelationkeywithsort":
				// TODO: Need to find a way to supply a CorrelationKey from the console
				break;
			case "findprocessinstancesbyprocessidwithsort":
				userResponse = getResponses("process instance ID", "statuses (comma-separated)", "page", "page size",
						"sort by", "sort order");
				pw.println(queryConsole.findProcessInstancesByProcessIdWithSortConsole(userResponse.get(0),
						userResponse.get(1), userResponse.get(2), userResponse.get(3), userResponse.get(4),
						userResponse.get(5)));
				break;
			case "findprocessinstancesbyprocessnamewithsort":
				userResponse = getResponses("process instance name", "statuses (comma-separated)", "page", "page size",
						"sort by", "sort order");
				pw.println(queryConsole.findProcessInstancesByProcessNameWithSortConsole(userResponse.get(0),
						userResponse.get(1), userResponse.get(2), userResponse.get(3), userResponse.get(4),
						userResponse.get(5)));
				break;
			case "findprocessinstancesbycontaineridwithsort":
				userResponse = getResponses("statuses (comma-separated)", "page", "page size", "sort by", "sort order");
				pw.println(queryConsole.findProcessInstancesByContainerIdWithSortConsole(userResponse.get(0),
						userResponse.get(1), userResponse.get(2), userResponse.get(3), userResponse.get(4)));
				break;
			case "findprocessinstancesbystatuswithsort":
				userResponse = getResponses("statuses (comma-separated)", "page", "page size", "sort by", "sort order");
				pw.println(queryConsole.findProcessInstancesByStatusWithSortConsole(userResponse.get(0),
						userResponse.get(1), userResponse.get(2), userResponse.get(3), userResponse.get(4)));
				break;
			case "findprocessinstancesbyinitiatorwithsort":
				userResponse = getResponses("initiator name", "statuses (comma-separated", "page", "page size",
						"sort by", "sort order");
				pw.println(queryConsole.findProcessInstancesByInitiatorWithSortConsole(userResponse.get(0),
						userResponse.get(1), userResponse.get(2), userResponse.get(3), userResponse.get(4),
						userResponse.get(5)));
				break;
			case "findprocessinstancesbyvariablewithsort":
				userResponse = getResponses("variable name", "statuses (comma-separated)", "page", "page size",
						"sort by", "sort order");
				pw.println(queryConsole.findProcessInstancesByVariableWithSortConsole(userResponse.get(0),
						userResponse.get(1), userResponse.get(2), userResponse.get(3), userResponse.get(4),
						userResponse.get(5)));
				break;
			case "findprocessinstancesbyvariableandvaluewithsort":
				userResponse = getResponses("variable name", "variable value", "statuses (comma-separated)", "page",
						"page size", "sort by", "sort order");
				pw.println(queryConsole.findProcessInstancesByVariableAndValueWithSortConsole(userResponse.get(0),
						userResponse.get(1), userResponse.get(2), userResponse.get(3), userResponse.get(4),
						userResponse.get(5), userResponse.get(6)));
				break;
			case "findprocessinstancebyid":
				userResponse = getResponses("process instance ID");
				pw.println(queryConsole.findProcessInstanceByIdConsole(userResponse.get(0)));
				break;
			case "findprocessinstancebyidwithvars":
				userResponse = getResponses("process instance ID");
				pw.println(queryConsole.findProcessInstanceByIdWithVarsConsole(userResponse.get(0)));
				break;
			case "findprocessinstancebycorrelationkey":
				// TODO: Need to find a way to supply a CorrelationKey from the console
				break;
			case "findnodeinstancebyworkitemid":
				userResponse = getResponses("process instance ID", "work item ID");
				pw.println(queryConsole.findNodeInstanceByWorkItemIdConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "findactivenodeinstances":
				userResponse = getResponses("process instance ID", "page", "page size");
				pw.println(queryConsole.findActiveNodeInstancesConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2)));
				break;
			case "findcompletednodeinstances":
				userResponse = getResponses("process instance ID", "page", "page size");
				pw.println(queryConsole.findCompletedNodeInstancesConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2)));
				break;
			case "findnodeinstances":
				userResponse = getResponses("process instance ID", "page", "page size");
				pw.println(queryConsole.findNodeInstancesConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2)));
				break;
			case "findvariablescurrentstate":
				userResponse = getResponses("process instance ID");
				pw.println(queryConsole.findVariablesCurrentStateConsole(userResponse.get(0)));
				break;
			case "findvariablehistory":
				userResponse = getResponses("process instance ID", "variable name", "page", "page size");
				pw.println(queryConsole.findVariableHistoryConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2), userResponse.get(3)));
				break;
			case "registerquery":
				userResponse = getResponses("query name", "source", "expression", "target");
				pw.println(queryConsole.registerQueryConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2), userResponse.get(3)));
				break;
			case "replacequery":
				userResponse = getResponses("query name", "source", "expression", "target");
				pw.println(queryConsole.replaceQueryConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2), userResponse.get(3)));
				break;
			case "unregisterquery":
				userResponse = getResponses("query name");
				pw.println(queryConsole.unregisterQueryConsole(userResponse.get(0)));
				break;
			case "getquery":
				userResponse = getResponses("query name");
				pw.println(queryConsole.getQueryConsole(userResponse.get(0)));
				break;
			case "getqueries":
				userResponse = getResponses("page", "page size");
				pw.println(queryConsole.getQueriesConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "query":
				userResponse = getResponses("query name", "mapper", "page", "page size", "result type");
				pw.println(queryConsole.queryConsole(userResponse.get(0), userResponse.get(1), userResponse.get(2),
						userResponse.get(3), userResponse.get(4)));
				break;
			case "querywithorderby":
				userResponse = getResponses("query name", "mapper", "order by", "page", "page size", "result type");
				pw.println(queryConsole.queryWithOrderByConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2), userResponse.get(3), userResponse.get(4), userResponse.get(5)));
				break;
			case "querywithcolumnmapping":
				userResponse = getResponses("query name", "mapper", "order by",
						"should responses be shown ascending (true or false)",
						"column mapping (in the format \"key1=value1,key2=value2,...\")",
						"query parameters (in the format \"column1:operator1:value,"
								+ "value,...:column2:operator2:value,value,...\")",
						"page", "page size", "result type");
				pw.println(queryConsole.queryWithColumnMappingConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2), userResponse.get(3), userResponse.get(4), userResponse.get(5),
						userResponse.get(6), userResponse.get(7), userResponse.get(8)));
				break;
			case "querywithparameters":
				userResponse = getResponses("query name", "mapper", "builder",
						"query parameters (in the format \"key1=value1,key2=value2,...\")", "page", "page size",
						"result type");
				pw.println(queryConsole.queryWithParametersConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2), userResponse.get(3), userResponse.get(4), userResponse.get(5),
						userResponse.get(6)));
				break;
			// UI commands
			case "getprocessformbylanguage":
				userResponse = getResponses("process instance ID", "language");
				pw.println(uiConsole.getProcessFormByLanguageConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "getprocessform":
				userResponse = getResponses("process instance ID");
				pw.println(uiConsole.getProcessFormConsole(userResponse.get(0)));
				break;
			case "gettaskformbylanguage":
				userResponse = getResponses("task ID", "language");
				pw.println(uiConsole.getTaskFormByLanguageConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "gettaskform":
				userResponse = getResponses("task ID");
				pw.println(uiConsole.getTaskFormConsole(userResponse.get(0)));
				break;
			case "getprocessimage":
				userResponse = getResponses("process ID");
				pw.println(uiConsole.getProcessImageConsole(userResponse.get(0)));
				break;
			case "getprocessinstanceimage":
				userResponse = getResponses("process instance ID");
				pw.println(uiConsole.getProcessInstanceImageConsole(userResponse.get(0)));
				break;
			// User Task commands
			case "activatetask":
				userResponse = getResponses("task ID", "user ID");
				pw.println(userTaskConsole.activateTaskConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "claimtask":
				userResponse = getResponses("task ID", "user ID");
				pw.println(userTaskConsole.claimTaskConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "completetask":
				userResponse = getResponses("task ID", "user ID",
						"parameters (in the format \"key1=value1,key2=value2,...\")");
				pw.println(userTaskConsole.completeTaskConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2)));
				break;
			case "completeautoprogress":
				userResponse = getResponses("task ID", "user ID",
						"parameters (in the format \"key1=value1,key2=value2,...\")");
				pw.println(userTaskConsole.completeAutoProgressConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2)));
				break;
			case "delegatetask":
				userResponse = getResponses("task ID", "user ID", "target user ID");
				pw.println(userTaskConsole.delegateTaskConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2)));
				break;
			case "exittask":
				userResponse = getResponses("task ID", "user ID");
				pw.println(userTaskConsole.exitTaskConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "failtask":
				userResponse = getResponses("task ID", "user ID",
						"parameters (in the format \"key1=value1,key2=value2,...\")");
				pw.println(
						userTaskConsole.failTaskConsole(userResponse.get(0), userResponse.get(1), userResponse.get(2)));
				break;
			case "forwardtask":
				userResponse = getResponses("task ID", "user ID", "target entity ID");
				pw.println(userTaskConsole.forwardTaskConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2)));
				break;
			case "releasetask":
				userResponse = getResponses("task ID", "user ID");
				pw.println(userTaskConsole.releaseTaskConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "resumetask":
				userResponse = getResponses("task ID", "user ID");
				pw.println(userTaskConsole.resumeTaskConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "skiptask":
				userResponse = getResponses("task ID", "user ID");
				pw.println(userTaskConsole.skipTaskConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "starttask":
				userResponse = getResponses("task ID", "user ID");
				pw.println(userTaskConsole.startTaskConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "stoptask":
				userResponse = getResponses("task ID", "user ID");
				pw.println(userTaskConsole.stopTaskConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "suspendtask":
				userResponse = getResponses("task ID", "user ID");
				pw.println(userTaskConsole.suspendTaskConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "nominatetask":
				userResponse = getResponses("task ID", "user ID", "potential owner IDs (comma-separated)");
				pw.println(userTaskConsole.nominateTaskConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2)));
				break;
			case "settaskpriority":
				userResponse = getResponses("task ID", "priority");
				pw.println(userTaskConsole.setTaskPriorityConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "settaskexpirationdate":
				userResponse = getResponses("task ID", "date (in the format MM-dd-yyyy)");
				pw.println(userTaskConsole.setTaskExpirationDateConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "settaskskipable":
				userResponse = getResponses("task ID", "skippable? (true or false)");
				pw.println(userTaskConsole.setTaskSkipableConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "settaskname":
				userResponse = getResponses("task ID", "task name");
				pw.println(userTaskConsole.setTaskNameConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "settaskdescription":
				userResponse = getResponses("task ID", "task description");
				pw.println(userTaskConsole.setTaskDescriptionConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "savetaskcontent":
				userResponse = getResponses("task ID", "content (in the format \"key1=value1,key2=value2,...\")");
				pw.println(userTaskConsole.saveTaskContentConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "gettaskoutputcontentbytaskid":
				userResponse = getResponses("task ID");
				pw.println(userTaskConsole.getTaskOutputContentByTaskIdConsole(userResponse.get(0)));
				break;
			case "gettaskinputcontentbytaskid":
				userResponse = getResponses("task ID");
				pw.println(userTaskConsole.getTaskInputContentByTaskIdConsole(userResponse.get(0)));
				break;
			case "deletetaskcontent":
				userResponse = getResponses("task ID", "content ID");
				pw.println(userTaskConsole.deleteTaskContentConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "addtaskcomment":
				userResponse = getResponses("task ID", "text", "user adding the comment",
						"comment date (in format MM-dd-yyyy)");
				pw.println(userTaskConsole.addTaskCommentConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2), userResponse.get(3)));
				break;
			case "deletetaskcomment":
				userResponse = getResponses("task ID", "comment ID");
				pw.println(userTaskConsole.deleteTaskCommentConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "gettaskcommentsbytaskid":
				userResponse = getResponses("task ID");
				pw.println(userTaskConsole.getTaskCommentsByTaskIdConsole(userResponse.get(0)));
				break;
			case "gettaskcommentbyid":
				userResponse = getResponses("task ID", "comment ID");
				pw.println(userTaskConsole.getTaskCommentByIdConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "addtaskattachment":
				userResponse = getResponses("task ID", "user ID", "task name", "attachment type");
				pw.println(userTaskConsole.addTaskAttachmentConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2), userResponse.get(3)));
				break;
			case "deletetaskattachment":
				userResponse = getResponses("task ID", "attachment ID");
				pw.println(userTaskConsole.deleteTaskAttachmentConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "gettaskattachmentbyid":
				userResponse = getResponses("task ID", "attachment ID");
				pw.println(userTaskConsole.getTaskAttachmentByIdConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "gettaskattachmentcontentbyid":
				userResponse = getResponses("task ID", "attachment ID");
				pw.println(
						userTaskConsole.getTaskAttachmentContentByIdConsole(userResponse.get(0), userResponse.get(1)));
				break;
			case "gettaskattachmentsbytaskid":
				userResponse = getResponses("task ID");
				pw.println(userTaskConsole.getTaskAttachmentsByTaskIdConsole(userResponse.get(0)));
				break;
			case "gettaskinstance":
				userResponse = getResponses("task ID");
				pw.println(userTaskConsole.getTaskInstanceConsole(userResponse.get(0)));
				break;
			case "gettaskinstancewithinputoutput":
				userResponse = getResponses("task ID", "with inputs? (true or false)", "with outputs? (true or false)",
						"with assignments? (true or false)");
				pw.println(userTaskConsole.getTaskInstanceWithInputOutputConsole(userResponse.get(0),
						userResponse.get(1), userResponse.get(2), userResponse.get(3)));
				break;
			case "findtaskbyworkitemid":
				userResponse = getResponses("work item ID");
				pw.println(userTaskConsole.findTaskByWorkItemIdConsole(userResponse.get(0)));
				break;
			case "findtaskbyid":
				userResponse = getResponses("task ID");
				pw.println(userTaskConsole.findTaskByIdConsole(userResponse.get(0)));
				break;
			case "findtasksassignedasbusinessadministrator":
				userResponse = getResponses("user ID", "page", "page size");
				pw.println(userTaskConsole.findTasksAssignedAsBusinessAdministratorConsole(userResponse.get(0),
						userResponse.get(1), userResponse.get(2)));
				break;
			case "findtasksassignedasbusinessadministratorwithstatus":
				userResponse = getResponses("user ID", "status", "page", "page size");
				pw.println(userTaskConsole.findTasksAssignedAsBusinessAdministratorWithStatusConsole(
						userResponse.get(0), userResponse.get(1), userResponse.get(2), userResponse.get(3)));
				break;
			case "findtasksassignedaspotentialowner":
				userResponse = getResponses("user ID", "page", "page size");
				pw.println(userTaskConsole.findTasksAssignedAsPotentialOwnerConsole(userResponse.get(0),
						userResponse.get(1), userResponse.get(2)));
				break;
			case "findtasksassignedaspotentialownerwithstatus":
				userResponse = getResponses("user ID", "status", "page", "page size");
				pw.println(userTaskConsole.findTasksAssignedAsPotentialOwnerWithStatusConsole(userResponse.get(0),
						userResponse.get(1), userResponse.get(2), userResponse.get(3)));
				break;
			case "findtasksassignedaspotentialownerwithgroups":
				userResponse = getResponses("user ID", "groups (comma-separated)", "page", "page size");
				pw.println(userTaskConsole.findTasksAssignedAsPotentialOwnerWithGroupsConsole(userResponse.get(0),
						userResponse.get(1), userResponse.get(2), userResponse.get(3), userResponse.get(4)));
				break;
			case "findtasksowned":
				userResponse = getResponses("user ID", "page", "page size");
				pw.println(userTaskConsole.findTasksOwnedConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2)));
				break;
			case "findtasksownedwithstatuses":
				userResponse = getResponses("user ID", "status", "page", "page size");
				pw.println(userTaskConsole.findTasksOwnedWithStatusesConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2), userResponse.get(3)));
				break;
			case "findtasksbystatusbyprocessinstanceid":
				userResponse = getResponses("process instance ID", "status", "page", "page size");
				pw.println(userTaskConsole.findTasksByStatusByProcessInstanceIdConsole(userResponse.get(0),
						userResponse.get(1), userResponse.get(2), userResponse.get(3)));
				break;
			case "findtasks":
				userResponse = getResponses("user ID", "page", "page size");
				pw.println(userTaskConsole.findTasksConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2)));
				break;
			case "findtaskevents":
				userResponse = getResponses("task ID", "page", "page size");
				pw.println(userTaskConsole.findTaskEventsConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2)));
				break;
			case "findtasksbyvariable":
				userResponse = getResponses("user ID", "variable name", "status", "page", "page size");
				pw.println(userTaskConsole.findTasksByVariableConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2), userResponse.get(3), userResponse.get(4)));
				break;
			case "findtasksbyvariableandvalue":
				userResponse = getResponses("user ID", "variable name", "variable value", "status", "page",
						"page size");
				pw.println(userTaskConsole.findTasksByVariableAndValueConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2), userResponse.get(3), userResponse.get(4), userResponse.get(5)));
				break;
			case "findtasksassignedasbusinessadministratorwithsort":
				userResponse = getResponses("user ID", "page", "page size", "sort by", "sort order");
				pw.println(userTaskConsole.findTasksAssignedAsBusinessAdministratorWithSortConsole(userResponse.get(0),
						userResponse.get(1), userResponse.get(2), userResponse.get(3), userResponse.get(4)));
				break;
			case "findtasksassignedasbusinessadministratorwithstatuswithsort":
				userResponse = getResponses("user ID", "status", "page", "page size", "sort by", "sort order");
				pw.println(userTaskConsole.findTasksAssignedAsBusinessAdministratorWithStatusWithSortConsole(
						userResponse.get(0), userResponse.get(1), userResponse.get(2), userResponse.get(3),
						userResponse.get(4), userResponse.get(5)));
				break;
			case "findtasksassignedaspotentialowners":
				userResponse = getResponses("user ID", "page", "page size");
				pw.println(userTaskConsole.findTasksAssignedAsPotentialOwnerConsole(userResponse.get(0),
						userResponse.get(1), userResponse.get(2)));
				break;
			/*
			 * case "findtasksassignedaspotentialownerwithstatuswithsort": userResponse =
			 * getResponses(); pw.println(userTaskConsole); break;
			 */
			case "findtasksassignedaspotentialownerwithgroupswithsort":
				userResponse = getResponses("user ID", "groups (comma-separated)", "status", "page", "page size",
						"sort by", "sort order");
				pw.println(userTaskConsole.findTasksAssignedAsPotentialOwnerWithGroupsWithSortConsole(
						userResponse.get(0), userResponse.get(1), userResponse.get(2), userResponse.get(3),
						userResponse.get(4), userResponse.get(5), userResponse.get(6)));
				break;
			case "findtasksownedwithsort":
				userResponse = getResponses("user ID", "page", "page size", "sort by", "sort order");
				pw.println(userTaskConsole.findTasksOwnedWithSortConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2), userResponse.get(3), userResponse.get(4)));
				break;
			case "findtasksownedwithstatus":
				userResponse = getResponses("user ID", "status", "page", "page size", "sort by", "sort order");
				pw.println(userTaskConsole.findTasksOwnedWithStatusConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2), userResponse.get(3), userResponse.get(4), userResponse.get(5)));
				break;
			case "findtasksbystatusbyprocessinstanceidwithsort":
				userResponse = getResponses("process instance ID", "status", "page", "page size", "sort by",
						"sort order");
				pw.println(userTaskConsole.findTasksByStatusByProcessInstanceIdWithSortConsole(userResponse.get(0),
						userResponse.get(1), userResponse.get(2), userResponse.get(3), userResponse.get(4),
						userResponse.get(5)));
				break;
			case "findtaskswithsort":
				userResponse = getResponses("user ID", "page", "page size", "sort by", "sort order");
				pw.println(userTaskConsole.findTasksWithSortConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2), userResponse.get(3), userResponse.get(4)));
				break;
			case "findtaskeventswithsort":
				userResponse = getResponses("task ID", "page", "page size", "sort by", "sort order");
				pw.println(userTaskConsole.findTaskEventsWithSortConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2), userResponse.get(3), userResponse.get(4)));
				break;
			case "findtasksbyvariablewithsort":
				userResponse = getResponses("user ID", "variable name", "status", "page", "page size", "sort by",
						"sort order");
				pw.println(userTaskConsole.findTasksByVariableWithSortConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2), userResponse.get(3), userResponse.get(4), userResponse.get(5),
						userResponse.get(6)));
				break;
			case "findtasksbyvariableandvaluewithsort":
				userResponse = getResponses("user ID", "variable name", "variable value", "status", "page", "page size",
						"sort by", "sort order");
				pw.println(userTaskConsole.findTasksByVariableAndValueWithSortConsole(userResponse.get(0),
						userResponse.get(1), userResponse.get(2), userResponse.get(3), userResponse.get(4),
						userResponse.get(5), userResponse.get(6), userResponse.get(7)));
				break;
			case "claimstartcompletehumantask":
				userResponse = getResponses("task ID", "user ID",
						"data (in the format \"key1=value1,key2=value2,...\")");
				pw.println(userTaskConsole.claimStartCompleteHumanTaskConsole(userResponse.get(0), userResponse.get(1),
						userResponse.get(2)));
				break;
			case "getinputdata":
				userResponse = getResponses("task ID");
				pw.println(userTaskConsole.getInputDataConsole(userResponse.get(0)));
				break;
			case "getoutputdata":
				userResponse = getResponses("task ID");
				pw.println(userTaskConsole.getOutputDataConsole(userResponse.get(0)));
				break;
			default:
				logger.error("Invalid command {} was entered, please enter a valid command.", command);
				exitWithError();
				break;
			}
		} catch (BpmsFrontendException e) {
			//Exceptions caught in other classes/methods generate an error message using data available
			//	to them, which they then throw as a BpmsFrontendException. Log that error here.
			logger.error(e.getMessage());
		}
	}

	protected static String parseHelp(String helpValue) {
		String message;

		try {
			switch (helpValue) {
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
		} catch (NullPointerException e) {
			message = Constants.BASIC_HELP_MESSAGE;
		}

		return message;
	}

	protected static ArrayList<String> getResponses(String... prompts) {
		ArrayList<String> promptList = new ArrayList<>(1);
		for (String prompt : prompts) {
			promptList.add(prompt);
		}

		Map<String, String> commandParams = generateParamKeys(promptList.stream().toArray(String[]::new));
		ArrayList<String> userResponses = askUser(commandParams.keySet().stream().toArray(String[]::new));

		return userResponses;
	}

	protected static ArrayList<String> askUser(String[] questions) {
		ArrayList<String> responses = new ArrayList<String>(1);

		for (int i = 0; i < questions.length; i++) {
			String response;
			try {
				response = FrontendUtil.readLine("Enter %s", questions[i]);
				
				if (response == null) {
					responses.add("");
				} else {
					responses.add(response);
				}
			} catch (BpmsFrontendException e) {
				logger.error(String.format("Encountered an error while trying to read from the console. "
						+ "The error is: %s%n", e.getMessage()));
			}
			
		}

		return responses;
	}

	protected static Map<String, String> generateParamKeys(String... params) {
		HashMap<String, String> paramMap = new HashMap<>();

		for (String param : params) {
			paramMap.put(param, "");
		}

		return paramMap;
	}

	protected static void initialize() {
		if (proxy != null) {
			jobConsole = new BpmsJobConsole(proxy.getKieServicesClient());
			kieConsole = new BpmsKieConsole(proxy.getKieServicesClient());
			processConsole = new BpmsProcessConsole(proxy.getContainerId(), proxy.getKieServicesClient());
			queryConsole = new BpmsQueryConsole(proxy.getContainerId(), proxy.getKieServicesClient());
			uiConsole = new BpmsUIConsole(proxy.getContainerId(), proxy.getKieServicesClient());
			userTaskConsole = new BpmsUserTaskConsole(proxy.getContainerId(), proxy.getKieServicesClient());
		} else {
			logger.error("Unable to initiate the specified transport type!");
			exitWithError();
		}
	}

	private static Map<String, String> parseArguments(String[] args) {
		logger.debug("Entering parseArguments");

		Map<String, String> argMap = new HashMap<>();
		for (int i = 0; i < args.length; i++) {
			// Look for arguments with the format "-option value"
			if (args[i].startsWith("-")) {
				String key = args[i].substring(1);
				if ((i + 1) <= args.length && !args[i + 1].startsWith("-") && args[i + 1] != null
						&& args[i + 1] instanceof String) {
					argMap.put(key, args[i + 1]);
					i++;
				}
			}
		}

		logger.debug("Leaving parseArguments");
		return argMap;
	}

	protected static void exitWithError() {
		logger.error(Constants.PROGRAM_IS_EXITING);
		System.exit(-1);
	}

	protected static BpmsJobConsole getJobConsole() {
		return jobConsole;
	}

	protected static BpmsKieConsole getKieConsole() {
		return kieConsole;
	}

	protected static BpmsProcessConsole getProcessConsole() {
		return processConsole;
	}

	protected static BpmsQueryConsole getQueryConsole() {
		return queryConsole;
	}

	protected static BpmsUIConsole getUiConsole() {
		return uiConsole;
	}

	protected static BpmsUserTaskConsole getUserTaskConsole() {
		return userTaskConsole;
	}
	
	protected static void setProxy(BpmsProxyInterface proxy) {
		Main.proxy = proxy;
	}
	
}
