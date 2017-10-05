package org.wildfly.bpms.frontend.constants;

public class Constants {

	public static final String PROGRAM_IS_EXITING = "Program is exiting...";
	public static final String BASIC_HELP_MESSAGE = "This program expects a '-command' parameter to run a specific API call against an unmanaged KIE Server instance and a '-transport' parameter to specify which transport type (currently jms or rest) will be used for the API call. The '-help <type>' or '-? <type>' parameter may be used instead to gain information about which commands may be run.\nPossible types with help content are: job, kie, process, query, ui, and usertask.";
	public static final String VALID_JOB_COMMANDS = "schedulerequest\ncancelrequest\nrequeuerequest\ngetrequestsbystatus\ngetrequestsbybusinesskey\ngetrequestsbycommand\ngetrequestbyid";
	public static final String VALID_KIE_COMMANDS = "getserverstatus\ndestroycontainer\ncreatecontainer\nlistcontainers\nlistcontainersbyfilter\ngetcontainerinfo\ngetscannerinfo\nupdatescanner\nupdatereleaseid";
	public static final String VALID_PROCESS_COMMANDS = "getprocessdefinition\ngetreusablesubprocessdefinitions\ngetprocessvariabledefinitions\ngetservicetaskdefinitions\ngetassociatedentitydefinitions\ngetusertaskdefinitions\ngetusertaskinputdefinitions\ngetusertaskoutputdefinitions\nstartprocesswithvars\nstartprocess\nstartprocesswithkey\nstartprocesswithkeywithvars\nabortprocessinstance\nabortprocessinstances\ngetprocessinstancevariables\ngetprocessinstancevariablebytype\nisprocessfinished\nprocesscompletedsuccessfully\ngetprocessinstancevariables\nsignalprocessinstance\nsignalprocessinstances\nsignal\ngetavailablesignals\nsetprocessvariable\nsetprocessvariables\ngetprocessinstance\ngetprocessinstancewithvars\ncompleteworkitem\nabortworkitem\ngetworkitem\ngetworkitembyprocessinstances";
	public static final String VALID_QUERY_COMMANDS = "findprocessdefinitionsbycontaineridprocessid\nfindprocessdefinitionsbyid\nfindprocessdefinitions\nfindprocessdefinitionsbyfilter\findprocessdefinitionsbycontainerid\nfindprocessdefinitionswithsort\nfindprocessdefinitionswithsortwithfilter\nfindprocessdefinitionsbycontainerid\nfindprocessinstances\nfindprocessinstancesbycorrelationkey\nfindprocessinstancesbyprocessid\nfindprocessinstancesbyprocessname\nfindprocessinstancesbycontainerid\nfindprocessinstancesbystatus\nfindprocessinstancesbyinitiator\nfindprocessinstancesbyvariable\nfindprocessinstancesbyvariableandvalue\nfindprocessinstanceswithsort\nfindprocessinstancesbycorrelationkey\nfindprocessinstancesbyprocessid\nfindprocessinstancesbyprocessname\nfindprocessinstancesbycontainerid\nfindprocessinstancesbystatus\nfindprocessinstancesbyinitiator\nfindprocessinstancesbyvariable\nfindprocessinstancesbyvariableandvalue\nfindprocessinstancebyid\nfindprocessinstancebyidwithvars\nfindprocessinstancesbycorrelationkey\nfindnodeinstancebyworkitemid\nfindactivenodeinstances\nfindcompletednodeinstances\nfindnodeinstances\nfindvariablescurrentstate\nfindvariablehistory\nregisterquery\nreplacequery\nunregisterquery\ngetquery\ngetqueries\nquery\nquerywithorderby\nquerywithcolumnmapping\nquerywithparameters";
	public static final String VALID_UI_COMMANDS = "getprocessformbylanguage\ngetprocessform\ngettaskformbylanguage\ngettaskform\ngetprocessimage\ngetprocessinstanceimage";
	public static final String VALID_USER_TASK_COMMANDS = "activatetask\nclaimtask\ncompletetask\ncompleteautoprogress\ndelegatetask\nexittask\nfailtask\nforwardtask\nreleasetask\nresumetask\nskiptask\nstarttask\nstoptask\nsuspendtask\nnominatetask\nsettaskpriority\nsettaskexpirationdate\nsettaskskipable\nsettaskname\nsettaskdescription\nsavetaskcontent\ngettaskoutputcontentbytaskid\ngettaskinputcontentbytaskid\ndeletetaskcontent\naddtaskcomment\ndeletetaskcomment\ngettaskcommentsbytaskid\ngettaskcommentbyid\naddtaskattachment\ndeletetaskattachment\ngettaskattachmentbyid\ngettaskattachmentcontentbyid\ngettaskattachmentsbytaskid\ngettaskinstance\ngettaskinstancewithinputoutput\nfindtaskbyworkitemid\nfindtaskbyid\nfindtasksassignedasbusinessadministrator\nfindtasksassignedasbusinessadministratorwithstatus\nfindtasksassignedaspotentialowner\nfindtasksassignedaspotentialownerwithstatus\nfindtasksassignedaspotentialownerwithgroups\nfindtasksowned\findtasksownedwithstatuses\nfindtasksbystatusbyprocessinstanceid\nfindtasks\findtaskevents\findtasksbyvariable\nfindtasksbyvariableandvalue\nfindtasksassignedasbusinessadministrator\nfindtasksassignedasbusinessadministratorwithstatus\nfindtasksassignedaspotentialowners\nfindtasksassignedaspotentialownerwithstatus\nfindtasksassignedaspotentialownerwithgroups\nfindtasksowned\nfindtasksownedwithstatus\nfindtasksbystatusbyprocessinstanceid\nfindtasks\nfindtaskevents\nfindtasksbyvariable\nfindtasksbyvariableandvalue\nclaimstartcompletehumantask\ngetinputdata\ngetoutputdata";
	public static final String NO_HELP_FOR_THIS_COMMAND = "No help is available for this item, please try another";
	public static final String NOT_A_VALID_PARAMETER = "No valid parameter was specified.\nPossible values are: -help, -?, or -command";
	public static final String NO_TRANSPORT_VALUE_SPECIFIED = "No -transport value was specified, but this is required for running commands.";
	public static final String INVALID_TRANSPORT_VALUE_SPECIFIED = "Invalid transport value %s was specified.";
	public static final String SENDING_COMMAND = "Sending command to execute %s";
	public static final String FAILED_TO_SCHEDULE = "Failed to schedule the request, no request ID was assigned";
	public static final String SUCCESSFUL_SCHEDULE = "Successfully scheduled the request. ID %s has been generated.";
	public static final String NO_REQUEST_FOUND_BY_STATUS = "No requests were found to be in the specified statuses";
	public static final String NO_REQUEST_FOUND_BY_BUSINESS_KEY = "No requests were found to contain the specified business key";
	public static final String NO_REQUEST_FOUND_BY_COMMAND = "No requests were found to be using the specified command";
	public static final String NO_REQUEST_FOUND_BY_ID = "No requests were found using the specified ID";
	
	//Response types
	public static final String DATE_RESPONSE = "date (in MM-dd-yyyy format)";
	public static final String DATA_COMMA_SEPARATED_RESPONSE = "data (comma separated)";
	public static final String CONTAINER_ID_RESPONSE = "container ID";
	public static final String REQUEST_ID_RESPONSE = "request ID";
	public static final String PAGE_RESPONSE = "page number";
	public static final String PAGE_SIZE_RESPONSE = "page size";
	public static final String STATUSES_RESPONSE = "statuses (comma separated)";
	public static final String BUSINESS_KEY_RESPONSE = "business key";
	public static final String RESULTS_WITH_ERRORS_RESPONSE = "return results with errors? (true or false)";
	public static final String RESULTS_WITH_DATA_RESPONSE = "return results with data? (true or false)";
	
	
	private Constants() {
		
	}
}
