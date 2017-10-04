package org.wildfly.bpms.frontend.api.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.internal.process.CorrelationKey;
import org.kie.server.api.model.definition.ProcessDefinition;
import org.kie.server.api.model.definition.QueryDefinition;
import org.kie.server.api.model.definition.QueryFilterSpec;
import org.kie.server.api.model.definition.QueryParam;
import org.kie.server.api.model.instance.NodeInstance;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.instance.VariableInstance;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.QueryServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.bpms.frontend.api.BpmsQueryServices;
import org.wildfly.bpms.frontend.exception.BpmsFrontendException;

public class BpmsQueryConsole extends BpmsQueryServices {
	
	private static Logger logger = LoggerFactory.getLogger(BpmsQueryConsole.class);

	public BpmsQueryConsole(String containerId, KieServicesClient client) {
		super(containerId, client);
	}
	
	public String findProcessDefinitionByContainerIdProcessIdConsole(String processId) {
    	logger.debug("Executing findProcessByContainerIdProcessId");
    	
    	ProcessDefinition def = null;
    	
    	if(getContainerId() != null) {
    		def = getKieServicesClient().getServicesClient(QueryServicesClient.class)
            		.findProcessByContainerIdProcessId(getContainerId(), processId);
    	}else {
    		def = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessByContainerIdProcessId(getContainerId(), processId);
    	}
    	
    	return parseProcessDefinitionToString(def);
    }

	public String findProcessDefinitionsByIdConsole(String processId){
    	logger.debug("Executing findProcessDefinitionsById");
    	String result = "";
    	
    	List<ProcessDefinition> processDefList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessesById(processId);
    	
    	result = parseProcessDefinitionListToString(processDefList);
    	return result;
    }
	
	public String findProcessDefinitionsConsole(String page, String pageSize){
    	logger.debug("Executing findProcessDefinitionsConsole");
    	Integer pageInt = Integer.valueOf(page);
    	Integer pageSizeInt = Integer.valueOf(pageSize);
    	List<ProcessDefinition> defList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcesses(pageInt, pageSizeInt);
    	
    	return parseProcessDefinitionListToString(defList);
    }

    public String findProcessDefinitionsByFilterConsole(String filter, String page, String pageSize){
    	logger.debug("Executing findProcessDefinitionsByFilter");
    	
    	List<ProcessDefinition> processDefList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcesses(filter, Integer.valueOf(page), Integer.valueOf(pageSize));
    	return parseProcessDefinitionListToString(processDefList);
    }

    public String findProcessDefinitionsByContainerIdConsole(String page, String pageSize){
    	logger.debug("Executing findProcessDefinitionsByContainerId");
    	
    	List<ProcessDefinition> processDefList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessesByContainerId(getContainerId(), Integer.valueOf(page), Integer.valueOf(pageSize));
    	return parseProcessDefinitionListToString(processDefList);
    }

    public String findProcessDefinitionsWithSortConsole(String page, String pageSize, String sort, String sortOrder){
    	logger.debug("Executing findProcessDefinitionsWithSort");
    	
    	List<ProcessDefinition> processDefList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcesses(Integer.valueOf(page), Integer.valueOf(pageSize), sort, Boolean.valueOf(sortOrder));
    	return parseProcessDefinitionListToString(processDefList);
    }

    public String findProcessDefinitionsWithSortWithFilterConsole(String filter, String page,
    		String pageSize, String sort, String sortOrder){
    	logger.debug("Executing findProcessDefinitionsWithSortWithFilter");
    	
    	List<ProcessDefinition> processDefList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcesses(filter, Integer.valueOf(page), Integer.valueOf(pageSize), sort, Boolean.valueOf(sortOrder));
    	return parseProcessDefinitionListToString(processDefList);
    }

    public String findProcessDefinitionsByContainerIdWithSortConsole(String page, String pageSize,
    		String sort, String sortOrder){
    	logger.debug("Executing findProcessDefinitionsByContainerIdWithSort");
    	
    	List<ProcessDefinition> processDefList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessesByContainerId(getContainerId(), Integer.valueOf(page), Integer.valueOf(pageSize), sort, Boolean.valueOf(sortOrder));
    	return parseProcessDefinitionListToString(processDefList);
    }

    public String findProcessInstancesConsole(String page, String pageSize){
    	logger.debug("Executing findProcessInstances");
    	
    	List<ProcessInstance> processInstanceList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstances(Integer.valueOf(page), Integer.valueOf(pageSize));
    	return parseProcessInstanceListToString(processInstanceList);
    }

    public String findProcessInstancesByCorrelationKeyConsole(
    		CorrelationKey correlationKey, String page, String pageSize){
    	logger.debug("Executing findProcessInstancesByCorrelationKey");
    	
    	List<ProcessInstance> processInstanceList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByCorrelationKey(correlationKey, Integer.valueOf(page), Integer.valueOf(pageSize));
    	return parseProcessInstanceListToString(processInstanceList);
    }

    public String findProcessInstancesByProcessIdConsole(
    		String processId, String statuses, String page, String pageSize){
    	logger.debug("Executing findProcessInstancesByProcessId");
    	
    	List<Integer> statusList = new ArrayList<>(1);
    	String[] statusInts = statuses.split(",");
    	
    	for(int i = 0; i < statusInts.length; i++) {
    		statusList.add(Integer.valueOf(statusInts[i]));
    	}
    	
    	List<ProcessInstance> processInstanceList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByProcessId(processId, statusList, Integer.valueOf(page), Integer.valueOf(pageSize));
    	return parseProcessInstanceListToString(processInstanceList);
    }

    public String findProcessInstancesByProcessNameConsole(
    		String processName, String statuses, String page, String pageSize){
    	logger.debug("Executing findProcessByContainerIdProcessId");
    	
    	List<Integer> statusList = new ArrayList<>(1);
    	String[] statusInts = statuses.split(",");
    	
    	for(int i = 0; i < statusInts.length; i++) {
    		statusList.add(Integer.valueOf(statusInts[i]));
    	}
    	
    	List<ProcessInstance> processInstanceList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByProcessName(processName, statusList, Integer.valueOf(page), Integer.valueOf(pageSize));
    	return parseProcessInstanceListToString(processInstanceList);
    }

    public String findProcessInstancesByContainerIdConsole(String statuses, String page, String pageSize){
    	logger.debug("Executing findProcessByContainerIdProcessId");
    	
    	List<Integer> statusList = new ArrayList<>(1);
    	String[] statusInts = statuses.split(",");
    	
    	for(int i = 0; i < statusInts.length; i++) {
    		statusList.add(Integer.valueOf(statusInts[i]));
    	}
    	
    	List<ProcessInstance> processInstanceList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByContainerId(getContainerId(), statusList, Integer.valueOf(page), Integer.valueOf(pageSize));
    	return parseProcessInstanceListToString(processInstanceList);
    }

    public String findProcessInstancesByStatusConsole(String statuses, String page, String pageSize){
    	logger.debug("Executing findProcessInstancesByStatus");
    	
    	List<Integer> statusList = new ArrayList<>(1);
    	String[] statusInts = statuses.split(",");
    	
    	for(int i = 0; i < statusInts.length; i++) {
    		statusList.add(Integer.valueOf(statusInts[i]));
    	}
    	
    	List<ProcessInstance> processInstanceList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByStatus(statusList, Integer.valueOf(page), Integer.valueOf(pageSize));
    	return parseProcessInstanceListToString(processInstanceList);
    }

    public String findProcessInstancesByInitiatorConsole(
    		String initiator, String statuses, String page, String pageSize){
    	logger.debug("Executing findProcessInstancesByInitiator");
    	
    	List<Integer> statusList = new ArrayList<>(1);
    	String[] statusInts = statuses.split(",");
    	
    	for(int i = 0; i < statusInts.length; i++) {
    		statusList.add(Integer.valueOf(statusInts[i]));
    	}
    	
    	List<ProcessInstance> processInstanceList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByInitiator(initiator, statusList, Integer.valueOf(page), Integer.valueOf(pageSize));
    	return parseProcessInstanceListToString(processInstanceList);
    }

    public String findProcessInstancesByVariableConsole(
    		String variableName, String statuses, String page, String pageSize){
    	logger.debug("Executing findProcessInstancesByVariable");
    	
    	List<Integer> statusList = new ArrayList<>(1);
    	String[] statusInts = statuses.split(",");
    	
    	for(int i = 0; i < statusInts.length; i++) {
    		statusList.add(Integer.valueOf(statusInts[i]));
    	}
    	
    	List<ProcessInstance> processInstanceList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByVariable(variableName, statusList, Integer.valueOf(page), Integer.valueOf(pageSize));
    	return parseProcessInstanceListToString(processInstanceList);
    }

    public String findProcessInstancesByVariableAndValueConsole(
    		String variableName, String variableValue, String statuses, String page, String pageSize){
    	logger.debug("Executing findProcessInstancesByVariableAndValue");
    	
    	List<Integer> statusList = new ArrayList<>(1);
    	String[] statusInts = statuses.split(",");
    	
    	for(int i = 0; i < statusInts.length; i++) {
    		statusList.add(Integer.valueOf(statusInts[i]));
    	}
    	
    	List<ProcessInstance> processInstanceList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByVariableAndValue(variableName, variableValue, statusList, Integer.valueOf(page), Integer.valueOf(pageSize));
    	return parseProcessInstanceListToString(processInstanceList);
    }

    public String findProcessInstancesWithSortConsole(String page, String pageSize, String sort, String sortOrder){
    	logger.debug("Executing findProcessInstancesWithSort");
    	
    	List<ProcessInstance> processInstanceList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstances(Integer.valueOf(page), Integer.valueOf(pageSize), sort, Boolean.valueOf(sortOrder));
    	return parseProcessInstanceListToString(processInstanceList);
    }

    public String findProcessInstancesByCorrelationKeyWithSortConsole(
    		CorrelationKey correlationKey, String page, String pageSize, String sort, String sortOrder){
    	logger.debug("Executing findProcessInstancesByCorrelationKeyWithSort");
    	
    	List<ProcessInstance> processInstanceList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByCorrelationKey(correlationKey, Integer.valueOf(page), Integer.valueOf(pageSize), sort, Boolean.valueOf(sortOrder));
    	return parseProcessInstanceListToString(processInstanceList);
    }

    public String findProcessInstancesByProcessIdWithSortConsole(
    		String processId, String statuses, String page, String pageSize, String sort, String sortOrder){
    	logger.debug("Executing findProcessInstancesByProcessIdWithSort");
    	
    	List<Integer> statusList = new ArrayList<>(1);
    	String[] statusInts = statuses.split(",");
    	
    	for(int i = 0; i < statusInts.length; i++) {
    		statusList.add(Integer.valueOf(statusInts[i]));
    	}
    	
    	List<ProcessInstance> processInstanceList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByProcessId(processId, statusList, Integer.valueOf(page), Integer.valueOf(pageSize), sort, Boolean.valueOf(sortOrder));
    	return parseProcessInstanceListToString(processInstanceList);
    }

    public String findProcessInstancesByProcessNameWithSortConsole(
    		String processName, String statuses, String page, String pageSize, String sort, String sortOrder){
    	logger.debug("Executing findProcessInstancesByProcessNameWithSort");
    	
    	List<Integer> statusList = new ArrayList<>(1);
    	String[] statusInts = statuses.split(",");
    	
    	for(int i = 0; i < statusInts.length; i++) {
    		statusList.add(Integer.valueOf(statusInts[i]));
    	}
    	
    	List<ProcessInstance> processInstanceList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByProcessName(processName, statusList, Integer.valueOf(page), Integer.valueOf(pageSize), sort, Boolean.valueOf(sortOrder));
    	return parseProcessInstanceListToString(processInstanceList);
    }

    public String findProcessInstancesByContainerIdWithSortConsole(
    		String statuses, String page, String pageSize, String sort, String sortOrder){
    	logger.debug("Executing findProcessInstancesByContainerIdWithSort");
    	
    	List<Integer> statusList = new ArrayList<>(1);
    	String[] statusInts = statuses.split(",");
    	
    	for(int i = 0; i < statusInts.length; i++) {
    		statusList.add(Integer.valueOf(statusInts[i]));
    	}
    	
    	List<ProcessInstance> processInstanceList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByContainerId(getContainerId(), statusList, Integer.valueOf(page), Integer.valueOf(pageSize), sort, Boolean.valueOf(sortOrder));
    	return parseProcessInstanceListToString(processInstanceList);
    }

    public String findProcessInstancesByStatusWithSortConsole(
    		String statuses, String page, String pageSize, String sort, String sortOrder){
    	logger.debug("Executing findProcessInstancesByStatusWithSort");
    	
    	List<Integer> statusList = new ArrayList<>(1);
    	String[] statusInts = statuses.split(",");
    	
    	for(int i = 0; i < statusInts.length; i++) {
    		statusList.add(Integer.valueOf(statusInts[i]));
    	}
    	
    	List<ProcessInstance> processInstanceList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByStatus(statusList, Integer.valueOf(page), Integer.valueOf(pageSize), sort, Boolean.valueOf(sortOrder));
    	return parseProcessInstanceListToString(processInstanceList);
    }

    public String findProcessInstancesByInitiatorWithSortConsole(
    		String initiator, String statuses, String page, String pageSize, String sort, String sortOrder){
    	logger.debug("Executing findProcessInstancesByInitiatorWithSort");
    	
    	List<Integer> statusList = new ArrayList<>(1);
    	String[] statusInts = statuses.split(",");
    	
    	for(int i = 0; i < statusInts.length; i++) {
    		statusList.add(Integer.valueOf(statusInts[i]));
    	}
    	
    	List<ProcessInstance> processInstanceList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByInitiator(initiator, statusList, Integer.valueOf(page), Integer.valueOf(pageSize), sort, Boolean.valueOf(sortOrder));
    	return parseProcessInstanceListToString(processInstanceList);
    }

    public String findProcessInstancesByVariableWithSortConsole(
    		String variableName, String statuses, String page, String pageSize, String sort, String sortOrder){
    	logger.debug("Executing findProcessInstancesByVariableWithSort");
    	
    	List<Integer> statusList = new ArrayList<>(1);
    	String[] statusInts = statuses.split(",");
    	
    	for(int i = 0; i < statusInts.length; i++) {
    		statusList.add(Integer.valueOf(statusInts[i]));
    	}
    	
    	List<ProcessInstance> processInstanceList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByVariable(variableName, statusList, Integer.valueOf(page), Integer.valueOf(pageSize), sort, Boolean.valueOf(sortOrder));
    	return parseProcessInstanceListToString(processInstanceList);
    }

    public String findProcessInstancesByVariableAndValueWithSortConsole(
    		String variableName, String variableValue, String statuses, String page,
    		String pageSize, String sort, String sortOrder){
    	logger.debug("Executing findProcessInstancesByVariableAndValueWithSort");
    	
    	List<Integer> statusList = new ArrayList<>(1);
    	String[] statusInts = statuses.split(",");
    	
    	for(int i = 0; i < statusInts.length; i++) {
    		statusList.add(Integer.valueOf(statusInts[i]));
    	}
    	
    	List<ProcessInstance> processInstanceList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByVariableAndValue(variableName, variableValue, statusList, Integer.valueOf(page), Integer.valueOf(pageSize),
        				sort, Boolean.valueOf(sortOrder));
    	return parseProcessInstanceListToString(processInstanceList);
    }

    public String findProcessInstanceByIdConsole(String processInstanceId){
    	logger.debug("Executing findProcessInstanceById");
    	
    	ProcessInstance process = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstanceById(Long.valueOf(processInstanceId));
    	return parseProcessInstanceToString(process);
    }

    //There is no reason to use this method unless you want variables returned, so the "withVars" parameter
    //	is hard-coded to "true". If variables should not be returned, use <pre>findProcessInstanceByIdConsole</pre>.
    public String findProcessInstanceByIdWithVarsConsole(String processInstanceId){
    	logger.debug("Executing findProcessInstanceById");
    	
    	ProcessInstance process = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstanceById(Long.valueOf(processInstanceId), Boolean.TRUE);
    	return parseProcessInstanceToString(process);
    }

    public String findProcessInstanceByCorrelationKeyConsole(CorrelationKey correlationKey){
    	logger.debug("Executing findProcessInstanceByCorrelationKey");
    	
    	ProcessInstance process = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstanceByCorrelationKey(correlationKey);
    	return parseProcessInstanceToString(process);
    }

    public String findNodeInstanceByWorkItemIdConsole(String processInstanceId, String workItemId){
    	logger.debug("Executing findNodeInstanceByWorkItemId");
    	
    	NodeInstance instance = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findNodeInstanceByWorkItemId(Long.valueOf(processInstanceId), Long.valueOf(workItemId));
    	return parseNodeInstanceToString(instance);
    }

    public String findActiveNodeInstancesConsole(String processInstanceId, String page, String pageSize){
    	logger.debug("Executing findActiveNodeInstances");
    	
    	List<NodeInstance> instanceList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findActiveNodeInstances(Long.valueOf(processInstanceId), Integer.valueOf(page), Integer.valueOf(pageSize));
    	return parseNodeInstanceListToString(instanceList);
    }

    public String findCompletedNodeInstancesConsole(String processInstanceId, String page, String pageSize){
    	logger.debug("Executing findCompletedNodeInstances");
    	
    	List<NodeInstance> instanceList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findCompletedNodeInstances(Long.valueOf(processInstanceId), Integer.valueOf(page), Integer.valueOf(pageSize));
    	return parseNodeInstanceListToString(instanceList);
    }

    public String findNodeInstancesConsole(String processInstanceId, String page, String pageSize){
    	logger.debug("Executing findNodeInstances");
    	
    	List<NodeInstance> instanceList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findNodeInstances(Long.valueOf(processInstanceId), Integer.valueOf(page), Integer.valueOf(pageSize));
    	return parseNodeInstanceListToString(instanceList);
    }

    public String findVariablesCurrentStateConsole(String processInstanceId){
    	logger.debug("Executing findVariablesCurrentState");
    	
    	List<VariableInstance> instanceList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findVariablesCurrentState(Long.valueOf(processInstanceId));
    	return parseVariableInstanceListToString(instanceList);
    }

    public String findVariableHistoryConsole(
    		String processInstanceId, String variableName, String page, String pageSize){
    	logger.debug("Executing findVariableHistory");
    	
    	List<VariableInstance> instanceList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findVariableHistory(Long.valueOf(processInstanceId), variableName, Integer.valueOf(page), Integer.valueOf(pageSize));
    	return parseVariableInstanceListToString(instanceList);
    }

    public String registerQueryConsole(String queryName, String source, String expression, String target){
    	logger.debug("Executing registerQuery");
    	QueryDefinition query = QueryDefinition.builder().name(queryName).source(source)
    			.expression(expression).target(target).build();
    	getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.registerQuery(query);
    	
    	return String.format("Calling API to register query name %s%n", queryName);
    }

    public String replaceQueryConsole(String queryName, String source, String expression, String target){
    	logger.debug("Executing replaceQuery");
    	QueryDefinition query = QueryDefinition.builder().name(queryName).source(source)
    			.expression(expression).target(target).build();
    	getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.replaceQuery(query);
    	
    	return String.format("Calling API to replace query name: %s%n", queryName);
    }

    public String unregisterQueryConsole(String queryName){
    	logger.debug("Executing unregisterQuery");
    	getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.unregisterQuery(queryName);
    	
    	return String.format("Calling API to unregister query name: %s%n", queryName);
    }

    public String getQueryConsole(String queryName){
    	logger.debug("Executing getQuery");
    	
    	QueryDefinition def = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.getQuery(queryName);
    	return String.format("=====%nQuery Name: %s%nQuery Expression: %s%n", def.getName(), def.getExpression());
    }

    public String getQueriesConsole(String page, String pageSize){
    	logger.debug("Executing getQueries");
    	String result = "";
    	
    	List<QueryDefinition> defList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.getQueries(Integer.valueOf(page), Integer.valueOf(pageSize));
    	
    	for(QueryDefinition def : defList) {
    		result = result.concat(String.format("=====%nQuery Name: %s%nQuery Expression: %s%n",
    				def.getName(), def.getExpression()));
    	}
    	
    	return result;
    }

    public <T> String queryConsole(String queryName, String mapper, String page, String pageSize, String resultType) throws BpmsFrontendException{
    	logger.debug("Executing query");
    	String result = "";
    	Class<T> resultClass;
		try {
			resultClass = (Class<T>) Class.forName(resultType);
			
			List<T> typeList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
	        		.query(queryName, mapper, Integer.valueOf(page), Integer.valueOf(pageSize), resultClass);
			
			if(typeList == null || typeList.isEmpty()) {
				result = String.format("Calling API for named query: %s%n", queryName);
			}else {
				result = "Query returned no results";
			}
		} catch (ClassNotFoundException e) {
			String errorMessage = String.format("Unable to locate class %s. Error is:%n%s%n", resultType, e.getMessage());
			logger.error(errorMessage);
			throw new BpmsFrontendException(errorMessage);
		}
		
		logger.debug("Leaving getProcessInstanceVariableByType");
    	return result;
    }

    public <T> String queryWithOrderByConsole(
    		String queryName, String mapper, String orderBy, String page, String pageSize, String resultType) throws BpmsFrontendException{
    	logger.debug("Executing query");
    	
    	String result = "";
    	Class<T> resultClass;
		try {
			resultClass = (Class<T>) Class.forName(resultType);
			
			List<T> typeList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
	        		.query(queryName, mapper, orderBy, Integer.valueOf(page), Integer.valueOf(pageSize), resultClass);
			
			if(typeList == null || typeList.isEmpty()) {
				result = String.format("Calling API for named query %s ordered by %s%n", queryName, orderBy);
			}else {
				result = "Query returned no results";
			}
		} catch (ClassNotFoundException e) {
			String errorMessage = String.format("Unable to locate class %s. Error is:%n%s%n", resultType, e.getMessage());
			logger.error(errorMessage);
			throw new BpmsFrontendException(errorMessage);
		}
		
		logger.debug("Leaving getProcessInstanceVariableByType");
    	return result;
    }

    public <T> String queryWithColumnMappingConsole(
    		String queryName, String mapper, String orderBy, String isAscending,
    		String columnMapping, String queryParameters, String page,
    		String pageSize, String resultType) throws BpmsFrontendException{
    	logger.debug("Executing query");
    	
    	String result = "";
    	Class<T> resultClass;
    	String[] columnKeys = columnMapping.split(",");
    	Map<String, String> columnMappingMap = new HashMap<>(1);
    	
    	for(int i = 0; i < columnKeys.length; i++) {
    		String[] keyValue = columnKeys[i].split("=");
    		
    		if(keyValue[1] == null) {
    			keyValue[1] = "";
    		}
    		
    		columnMappingMap.put(keyValue[0], keyValue[1]);
    	}
    	
    	String[] queryParamStrings = queryParameters.split(":");
    	List<QueryParam> queryParamList = new ArrayList<>(1);
    	
    	for(int i = 0; i < queryParamStrings.length - 2; i += 3) {
    		QueryParam param = new QueryParam();
    		param.setColumn(queryParamStrings[i]);
    		param.setOperator(queryParamStrings[i+1]);
    		
    		String[] valueStrings = queryParamStrings[i+2].split(",");
    		List<String> valueList = new ArrayList<>(1);
    		
    		for(int j = 0; j < valueStrings.length - 1; j++) {
    			if(valueStrings[i] == null) {
    				valueStrings[i] = "";
    			}
    			
    			valueList.add(valueStrings[i]);
    		}
    		
    		param.setValue(valueList);
    	}
    	
    	QueryParam[] queryParamArray = queryParamList.stream().toArray(QueryParam[]::new);
    	
		try {
			resultClass = (Class<T>) Class.forName(resultType);
			
			QueryFilterSpec queryFilter = new QueryFilterSpec();
	    	queryFilter.setOrderBy(orderBy);
	    	queryFilter.setAscending(Boolean.valueOf(isAscending));
	    	queryFilter.setColumnMapping(columnMappingMap);
	    	queryFilter.setParameters(queryParamArray);
	    	
	    	List<T> typeList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
	        		.query(queryName, mapper, queryFilter, Integer.valueOf(page), Integer.valueOf(pageSize), resultClass);
			
			if(typeList == null || typeList.isEmpty()) {
				result = String.format("Calling API for named query with column mapping: %s%n", queryName);
			}else {
				result = "Query returned no results";
			}
		} catch (ClassNotFoundException e) {
			String errorMessage = String.format("Unable to locate class %s. Error is:%n%s%n", resultType, e.getMessage());
			logger.error(errorMessage);
			throw new BpmsFrontendException(errorMessage);
		}
		
		logger.debug("Leaving getProcessInstanceVariableByType");
    	return result;
    }

    public <T> String queryWithParametersConsole(
    		String queryName, String mapper, String builder, String parameters,
    		String page, String pageSize, String resultType) throws BpmsFrontendException{
    	logger.debug("Executing query");
    	
    	String result = "";
    	Class<T> resultClass;
    	Map<String, Object> paramMap = new HashMap<>(1);
    	String[] paramArray = parameters.split(",");
    	String currentClassName = "";
    	
		try {
			resultClass = (Class<T>) Class.forName(resultType);
			
			for(int i = 0; i < paramArray.length; i++) {
				String[] keyValue = paramArray[i].split("=");
				Object value = Class.forName(keyValue[1]).newInstance();
				currentClassName = keyValue[1];
				paramMap.put(keyValue[0], value);
			}
			
			List<T> typeList = getKieServicesClient().getServicesClient(QueryServicesClient.class)
	        		.query(queryName, mapper, builder, paramMap, Integer.valueOf(page), Integer.valueOf(pageSize), resultClass);
			
			if(typeList == null || typeList.isEmpty()) {
				result = String.format("Calling API for named query with parameters %s%n", queryName);
			}else {
				result = "Query returned no results";
			}
		} catch (ClassNotFoundException e) {
			String errorMessage = String.format("Unable to locate class %s. Error is:%n%s%n", resultType, e.getMessage());
			logger.error(errorMessage);
			throw new BpmsFrontendException(errorMessage);
		} catch (InstantiationException e) {
        	String errorMessage = String.format("Error instantiating class %s. Error is:%n%s%n", currentClassName, e.getMessage());
			logger.error(errorMessage);
			throw new BpmsFrontendException(errorMessage);
		} catch (IllegalAccessException e) {
			String errorMessage = String.format("Error accessing class %s. Error is:%n%s%n", currentClassName, e.getMessage());
			logger.error(errorMessage);
			throw new BpmsFrontendException(errorMessage);
		}
		
    	return result;
    }
    
    private String parseProcessDefinitionListToString(List<ProcessDefinition> processDefList) {
    	logger.debug("Executing parseProcessDefinitionListToString");
    	String result = "";
    	
    	for(ProcessDefinition process : processDefList) {
    		result = result.concat(parseProcessDefinitionToString(process));
    	}
    	
    	return result;
    }
    
    private String parseProcessDefinitionToString(ProcessDefinition process) {
    	logger.debug("Executing parseProcessDefinitionToString");
    	return String.format("=====%nProcess Definition ID: %s%nProcess Name: %s%n"
				+ "Process Version: %s%nContainer ID: %s%n", process.getId(), process.getName(), process.getVersion(),
				process.getContainerId());
    }
    
    private String parseProcessInstanceListToString(List<ProcessInstance> processList) {
    	logger.debug("parseProcessInstanceListToString");
    	String result = "";
    	
    	for(ProcessInstance process: processList) {
    		result = result.concat(parseProcessInstanceToString(process));
    	}
    	
    	return result;
    }
    
    private String parseProcessInstanceToString(ProcessInstance process) {
    	logger.debug("Executing parseProcessInstanceToString");
    	
    	String variables = "";
    	Map<String, Object> processMap = process.getVariables();
    	
    	if(processMap == null || processMap.isEmpty()) {
    		variables = variables.concat("(No variables defined)");
    	}else {
    		for(String key : processMap.keySet()) {
        		variables = variables.concat(String.format("%s=%s%n", key, processMap.get(key)));
        	}
    	}
    	
    	return String.format("=====%nProcess Instance ID: %s%nProcess Name: %s%nParent Process Instance ID: "
    			+ "%s%nProcess Version: %s%nContainer ID: %s%nDate initiated: %s%nVariables: %s%n",
    			process.getId(), process.getProcessName(), process.getParentId(), process.getProcessVersion(),
    			process.getContainerId(), process.getDate().toString(), variables);
    }
    
    private String parseNodeInstanceListToString(List<NodeInstance> instanceList) {
    	logger.debug("Executing parseNodeInstanceListToString");
    	String result = "";
    	
    	for(NodeInstance instance : instanceList) {
    		result = result.concat(parseNodeInstanceToString(instance));
    	}
    	
    	return result;
    }
    
    private String parseNodeInstanceToString(NodeInstance instance) {
    	logger.debug("Executing parseNodeInstanceToString");
    	return String.format("=====%nNode ID: %s%nNode Name: %s%nNode Type: %s%nProcess Instance Id: "
    			+ "%s%nWork Item ID: %s%nContainer ID: %s%nDate initiated: %s%nCompleted: %s%n",
    			instance.getNodeId(), instance.getName(), instance.getNodeType(),
    			String.valueOf(instance.getProcessInstanceId()), String.valueOf(instance.getWorkItemId()),
    			instance.getContainerId(), instance.getDate(), String.valueOf(instance.getCompleted()));
    }
    
    private String parseVariableInstanceListToString(List<VariableInstance> instanceList) {
    	logger.debug("parseVariableInstanceListToString");
    	String result = "";
    	
    	for(VariableInstance instance : instanceList) {
    		result = result.concat(parseVariableInstanceToString(instance));
    	}
    	
    	return result;
    }
    
    private String parseVariableInstanceToString(VariableInstance instance) {
    	logger.debug("Executing pasreVariableInstanceToString");
    	return String.format("=====%nVariable Name: %s%nVariable Value: %s%nProcess Instance ID: %s%n",
    			instance.getVariableName(), instance.getValue(), instance.getProcessInstanceId());
    }
}
