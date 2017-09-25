package org.wildfly.bpms.frontend.api;

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

public class BpmsQueryServices {
	
	private static Logger logger = LoggerFactory.getLogger(BpmsQueryServices.class);
	
	private KieServicesClient kieServicesClient;
	private String containerId;

	private BpmsQueryServices() {
		// Empty constructor
	}
	
	public BpmsQueryServices(String containerId, KieServicesClient client) {
		kieServicesClient = client;
		this.containerId = containerId;
	}
	
	protected KieServicesClient getKieServicesClient() {
		return kieServicesClient;
	}
	
	protected String getContainerId() {
		return containerId;
	}

	public ProcessDefinition findProcessDefinitionsByContainerIdProcessId(String processId) {
    	logger.debug("Executing findProcessByContainerIdProcessId");
    	
    	if(getContainerId() != null) {
    		return getKieServicesClient().getServicesClient(QueryServicesClient.class)
            		.findProcessByContainerIdProcessId(getContainerId(), processId);
    	}else {
    		return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessByContainerIdProcessId(getContainerId(), processId);
    	}
    }

    public List<ProcessDefinition> findProcessDefinitionsById(String processId){
    	logger.debug("Executing findProcessDefinitionsById");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessesById(processId);
    }

    public List<ProcessDefinition> findProcessDefinitions(Integer page, Integer pageSize){
    	logger.debug("Executing findProcessDefinitions");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcesses(page, pageSize);
    }

    public List<ProcessDefinition> findProcessDefinitionsByFilter(String filter, Integer page, Integer pageSize){
    	logger.debug("Executing findProcessDefinitionsByFilter");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcesses(filter, page, pageSize);
    }

    public List<ProcessDefinition> findProcessDefinitionsByContainerId(Integer page, Integer pageSize){
    	logger.debug("Executing findProcessDefinitionsByContainerId");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessesByContainerId(getContainerId(), page, pageSize);
    }

    public List<ProcessDefinition> findProcessDefinitionsWithSort(Integer page, Integer pageSize, String sort, boolean sortOrder){
    	logger.debug("Executing findProcessDefinitionsWithSort");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcesses(page, pageSize, sort, sortOrder);
    }

    public List<ProcessDefinition> findProcessDefinitionsWithSortWithFilter(String filter, Integer page, Integer pageSize, String sort, boolean sortOrder){
    	logger.debug("Executing findProcessDefinitionsWithSortWithFilter");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcesses(filter, page, pageSize, sort, sortOrder);
    }

    public List<ProcessDefinition> findProcessDefinitionsByContainerIdWithSort(Integer page, Integer pageSize, String sort, boolean sortOrder){
    	logger.debug("Executing findProcessDefinitionsByContainerIdWithSort");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessesByContainerId(getContainerId(), page, pageSize, sort, sortOrder);
    }

    public List<ProcessInstance> findProcessInstances(Integer page, Integer pageSize){
    	logger.debug("Executing findProcessInstances");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstances(page, pageSize);
    }

    public List<ProcessInstance> findProcessInstancesByCorrelationKey(
    		CorrelationKey correlationKey, Integer page, Integer pageSize){
    	logger.debug("Executing findProcessInstancesByCorrelationKey");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByCorrelationKey(correlationKey, page, pageSize);
    }

    public List<ProcessInstance> findProcessInstancesByProcessId(
    		String processId, List<Integer> status, Integer page, Integer pageSize){
    	logger.debug("Executing findProcessInstancesByProcessId");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByProcessId(processId, status, page, pageSize);
    }

    public List<ProcessInstance> findProcessInstancesByProcessName(
    		String processName, List<Integer> status, Integer page, Integer pageSize){
    	logger.debug("Executing findProcessByContainerIdProcessId");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByProcessName(processName, status, page, pageSize);
    }

    public List<ProcessInstance> findProcessInstancesByContainerId(List<Integer> status, Integer page, Integer pageSize){
    	logger.debug("Executing findProcessByContainerIdProcessId");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByContainerId(getContainerId(), status, page, pageSize);
    }

    public List<ProcessInstance> findProcessInstancesByStatus(List<Integer> status, Integer page, Integer pageSize){
    	logger.debug("Executing findProcessInstancesByStatus");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByStatus(status, page, pageSize);
    }

    public List<ProcessInstance> findProcessInstancesByInitiator(
    		String initiator, List<Integer> status, Integer page, Integer pageSize){
    	logger.debug("Executing findProcessInstancesByInitiator");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByInitiator(initiator, status, page, pageSize);
    }

    public List<ProcessInstance> findProcessInstancesByVariable(
    		String variableName, List<Integer> status, Integer page, Integer pageSize){
    	logger.debug("Executing findProcessInstancesByVariable");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByVariable(variableName, status, page, pageSize);
    }

    public List<ProcessInstance> findProcessInstancesByVariableAndValue(
    		String variableName, String variableValue, List<Integer> status, Integer page, Integer pageSize){
    	logger.debug("Executing findProcessInstancesByVariableAndValue");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByVariableAndValue(variableName, variableValue, status, page, pageSize);
    }

    public List<ProcessInstance> findProcessInstancesWithSort(Integer page, Integer pageSize, String sort, boolean sortOrder){
    	logger.debug("Executing findProcessInstancesWithSort");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstances(page, pageSize, sort, sortOrder);
    }

    public List<ProcessInstance> findProcessInstancesByCorrelationKeyWithSort(
    		CorrelationKey correlationKey, Integer page, Integer pageSize, String sort, boolean sortOrder){
    	logger.debug("Executing findProcessInstancesByCorrelationKeyWithSort");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByCorrelationKey(correlationKey, page, pageSize, sort, sortOrder);
    }

    public List<ProcessInstance> findProcessInstancesByProcessIdWithSort(
    		String processId, List<Integer> status, Integer page, Integer pageSize, String sort, boolean sortOrder){
    	logger.debug("Executing findProcessInstancesByProcessIdWithSort");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByProcessId(processId, status, page, pageSize, sort, sortOrder);
    }

    public List<ProcessInstance> findProcessInstancesByProcessNameWithSort(
    		String processName, List<Integer> status, Integer page, Integer pageSize, String sort, boolean sortOrder){
    	logger.debug("Executing findProcessInstancesByProcessNameWithSort");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByProcessName(processName, status, page, pageSize, sort, sortOrder);
    }

    public List<ProcessInstance> findProcessInstancesByContainerIdWithSort(
    		List<Integer> status, Integer page, Integer pageSize, String sort, boolean sortOrder){
    	logger.debug("Executing findProcessInstancesByContainerIdWithSort");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByContainerId(getContainerId(), status, page, pageSize, sort, sortOrder);
    }

    public List<ProcessInstance> findProcessInstancesByStatusWithSort(
    		List<Integer> status, Integer page, Integer pageSize, String sort, boolean sortOrder){
    	logger.debug("Executing findProcessInstancesByStatusWithSort");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByStatus(status, page, pageSize, sort, sortOrder);
    }

    public List<ProcessInstance> findProcessInstancesByInitiatorWithSort(
    		String initiator, List<Integer> status, Integer page, Integer pageSize, String sort, boolean sortOrder){
    	logger.debug("Executing findProcessInstancesByInitiatorWithSort");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByInitiator(initiator, status, page, pageSize, sort, sortOrder);
    }

    public List<ProcessInstance> findProcessInstancesByVariableWithSort(
    		String variableName, List<Integer> status, Integer page, Integer pageSize, String sort, boolean sortOrder){
    	logger.debug("Executing findProcessInstancesByVariableWithSort");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByVariable(variableName, status, page, pageSize, sort, sortOrder);
    }

    public List<ProcessInstance> findProcessInstancesByVariableAndValueWithSort(
    		String variableName, String variableValue, List<Integer> status, Integer page,
    		Integer pageSize, String sort, boolean sortOrder){
    	logger.debug("Executing findProcessInstancesByVariableAndValueWithSort");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstancesByVariableAndValue(variableName, variableValue, status, page, pageSize, sort, sortOrder);
    }

    public ProcessInstance findProcessInstanceById(Long processInstanceId){
    	logger.debug("Executing findProcessInstanceById");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstanceById(processInstanceId);
    }

    public ProcessInstance findProcessInstanceByIdWithVars(Long processInstanceId, boolean withVars){
    	logger.debug("Executing findProcessInstanceById");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstanceById(processInstanceId, withVars);
    }

    public ProcessInstance findProcessInstanceByCorrelationKey(CorrelationKey correlationKey){
    	logger.debug("Executing findProcessInstanceByCorrelationKey");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcessInstanceByCorrelationKey(correlationKey);
    }

    public NodeInstance findNodeInstanceByWorkItemId(Long processInstanceId, Long workItemId){
    	logger.debug("Executing findNodeInstanceByWorkItemId");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findNodeInstanceByWorkItemId(processInstanceId, workItemId);
    }

    public List<NodeInstance> findActiveNodeInstances(Long processInstanceId, Integer page, Integer pageSize){
    	logger.debug("Executing findActiveNodeInstances");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findActiveNodeInstances(processInstanceId, page, pageSize);
    }

    public List<NodeInstance> findCompletedNodeInstances(Long processInstanceId, Integer page, Integer pageSize){
    	logger.debug("Executing findCompletedNodeInstances");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findCompletedNodeInstances(processInstanceId, page, pageSize);
    }

    public List<NodeInstance> findNodeInstances(Long processInstanceId, Integer page, Integer pageSize){
    	logger.debug("Executing findNodeInstances");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findNodeInstances(processInstanceId, page, pageSize);
    }

    public List<VariableInstance> findVariablesCurrentState(Long processInstanceId){
    	logger.debug("Executing findVariablesCurrentState");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findVariablesCurrentState(processInstanceId);
    }

    public List<VariableInstance> findVariableHistory(
    		Long processInstanceId, String variableName, Integer page, Integer pageSize){
    	logger.debug("Executing findVariableHistory");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findVariableHistory(processInstanceId, variableName, page, pageSize);
    }

    public void registerQuery(String queryName, String source, String expression, String target){
    	logger.debug("Executing registerQuery");
    	QueryDefinition query = QueryDefinition.builder().name(queryName).source(source)
    			.expression(expression).target(target).build();
    	getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.registerQuery(query);
    }

    public void replaceQuery(String queryName, String source, String expression, String target){
    	logger.debug("Executing replaceQuery");
    	QueryDefinition query = QueryDefinition.builder().name(queryName).source(source)
    			.expression(expression).target(target).build();
    	getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.replaceQuery(query);
    }

    public void unregisterQuery(String queryName){
    	logger.debug("Executing unregisterQuery");
    	getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.unregisterQuery(queryName);
    }

    public QueryDefinition getQuery(String queryName){
    	logger.debug("Executing getQuery");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.getQuery(queryName);
    }

    public List<QueryDefinition> getQueries(Integer page, Integer pageSize){
    	logger.debug("Executing getQueries");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.getQueries(page, pageSize);
    }

    public <T> List<T> query(String queryName, String mapper, Integer page, Integer pageSize, Class<T> resultType){
    	logger.debug("Executing query");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.query(queryName, mapper, page, pageSize, resultType);
    }

    public <T> List<T> queryWithOrderBy(
    		String queryName, String mapper, String orderBy, Integer page, Integer pageSize, Class<T> resultType){
    	logger.debug("Executing query");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.query(queryName, mapper, orderBy, page, pageSize, resultType);
    }

    public <T> List<T> queryWithColumnMapping(
    		String queryName, String mapper, String orderBy, boolean isAscending,
    		Map<String, String> columnMapping, QueryParam[] queryParameters, Integer page,
    		Integer pageSize, Class<T> resultType){
    	logger.debug("Executing query");
    	
    	QueryFilterSpec queryFilter = new QueryFilterSpec();
    	queryFilter.setOrderBy(orderBy);
    	queryFilter.setAscending(isAscending);
    	queryFilter.setColumnMapping(columnMapping);
    	queryFilter.setParameters(queryParameters);
    	
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.query(queryName, mapper, queryFilter, page, pageSize, resultType);
    }

    public <T> List<T> queryWithParameters(
    		String queryName, String mapper, String builder, Map<String, Object> parameters,
    		Integer page, Integer pageSize, Class<T> resultType){
    	logger.debug("Executing query");
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.query(queryName, mapper, builder, parameters, page, pageSize, resultType);
    }
}
