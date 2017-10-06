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
	public static final String COMMAND_RESPONSE = "command";
	public static final String DATE_RESPONSE = "date (in the format MM-dd-yyyy)";
	public static final String DATA_COMMA_SEPARATED_RESPONSE = "data (comma-separated)";
	public static final String CONTAINER_ID_RESPONSE = "container ID";
	public static final String REQUEST_ID_RESPONSE = "request ID";
	public static final String PAGE_RESPONSE = "page number";
	public static final String PAGE_SIZE_RESPONSE = "page size";
	public static final String STATUSES_COMMA_SEPARATED_RESPONSE = "statuses (comma-separated)";
	public static final String BUSINESS_KEY_RESPONSE = "business key";
	public static final String RESULTS_WITH_ERRORS_RESPONSE = "return results with errors? (true or false)";
	public static final String RESULTS_WITH_DATA_RESPONSE = "return results with data? (true or false)";
	public static final String GROUP_ID_RESPONSE = "group id";
	public static final String ARTIFACT_ID_RESPONSE = "artifact id";
	public static final String VERSION_ID_RESPONSE = "version id";
	public static final String PROCESS_DEFINITION_ID_RESPONSE = "process definition ID";
	public static final String INTERVAL_IN_SECONDS_RESPONSE = "interval in seconds (0 for none)";
	public static final String SCANNER_STATUS_RESPONSE = "scanner status";
	public static final String TASK_NAME_RESPONSE = "task name";
	public static final String PROCESS_NAME_RESPONSE = "process name";
	public static final String VARIABLES_OBJECT_RESPONSE = "variables (in the format \\\"key1=value1,key2=value2,...\\\")";
	public static final String PROCESS_INSTANCE_ID_RESPONSE = "process instance ID";
	public static final String PROCESS_INSTANCE_IDS_COMMA_SEPARATED_RESPONSE = "process instance IDs (comma-separated)";
	public static final String VARIABLE_NAME_RESPONSE = "variable name";
	public static final String SIGNAL_NAME_RESPONSE = "signal name";
	public static final String VARIABLE_DATA_TYPE_RESPONSE = "variable data type";
	public static final String EVENT_DATA_TYPE_RESPONSE = "event data type";
	public static final String VARIABLE_VALUE_RESPONSE = "variable value";
	public static final String EVENT_TYPE_RESPONSE = "event type";
	public static final String WORK_ITEM_ID_RESPONSE = "work item ID";
	public static final String RESULTS_RESPONSE = "results";
	public static final String SORT_BY_RESPONSE = "order by";
	public static final String SHOULD_SORT_RESPONSE = "whether results should be sorted (true or false)";
	public static final String QUERY_NAME_RESPONSE = "query name";
	public static final String MAPPER_RESPONSE = "mapper";
	public static final String BUILDER_RESPONSE = "builder";
	public static final String SOURCE_RESPONSE = "source";
	public static final String TARGET_RESPONSE = "target";
	public static final String EXPRESSION_RESPONSE = "expression";
	public static final String TASK_ID_RESPONSE = "task ID";
	public static final String USER_ID_RESPONSE = "user ID";
	public static final String INITIATOR_NAME_RESPONSE = "initiator name";
	public static final String RESULT_TYPE_RESPONSE  = "result type";
	public static final String RESPONSES_ASCENDING_RESPONSE = "should responses be shown ascending (true or false)";
	public static final String COLUMN_MAPPING_OBJECT_RESPONSE = "column mapping (in the format \"key1=value1,key2=value2,...\")";
	public static final String QUERY_PARAMETERS_TRI_OBJECT_RESPONSE = "query parameters (in the format \"column1:operator1:value,value,...:column2:operator2:value,value,...\")";
	public static final String QUERY_PARAMETERS_OBJECT_RESPONSE = "query parameters (in the format \"key1=value1,key2=value2,...\")";
	public static final String LANGUAGE_RESPONSE = "language";
	public static final String PARAMETERS_OBJECT_RESPONSE = "parameters (in the format \"key1=value1,key2=value2,...\")";
	public static final String TARGET_USER_ID_RESPONSE = "target user ID";
	public static final String TARGET_ENTITY_ID_RESPONSE = "target entity ID";
	public static final String POTENTIAL_OWNER_IDS_COMMA_SEPARATED_RESPONSE = "potential owner IDs (comma-separated)";
	public static final String PRIORITY_RESPONSE = "priority";
	public static final String SKIPPABLE_RESPONSE = "skippable? (true or false)";
	public static final String TASK_DESCRIPTION_RESPONSE = "task description";
	public static final String CONTENT_OBJECT_RESPONSE = "content (in the format \"key1=value1,key2=value2,...\")";
	public static final String CONTENT_ID_RESPONSE = "content ID";
	public static final String TEXT_RESPONSE = "text";
	public static final String USER_ADDING_COMMENT_RESPONSE = "user adding the comment";
	public static final String COMMENT_DATE_RESPONSE = "comment date (in the format MM-dd-yyyy)";
	public static final String COMMENT_ID_RESPONSE = "comment ID";
	public static final String ATTACHMENT_TYPE_RESPONSE = "attachment type";
	public static final String ATTACHMENT_ID_RESPONSE = "attachment ID";
	public static final String WITH_INPUTS_RESPONSE = "with inputs? (true or false)";
	public static final String WITH_OUTPUTS_RESPONSE = "with outputs? (true or false)";
	public static final String WITH_ASSIGNMENTS_RESPONSE = "with assignments? (true or false)";
	public static final String STATUS_RESPONSE = "status";
	public static final String DATA_OBJECT_RESPONSE = "data (in the format \"key1=value1,key2=value2,...\")";
	public static final String GROUPS_COMMA_SEPARATED_RESPONSE = "groups (comma-separated)";
	
	private Constants() {
		
	}
}
