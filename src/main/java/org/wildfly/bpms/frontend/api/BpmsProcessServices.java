package org.wildfly.bpms.frontend.api;

import java.util.List;
import java.util.Map;

import org.kie.internal.process.CorrelationKey;
import org.kie.server.api.model.definition.AssociatedEntitiesDefinition;
import org.kie.server.api.model.definition.ProcessDefinition;
import org.kie.server.api.model.definition.ServiceTasksDefinition;
import org.kie.server.api.model.definition.SubProcessesDefinition;
import org.kie.server.api.model.definition.TaskInputsDefinition;
import org.kie.server.api.model.definition.TaskOutputsDefinition;
import org.kie.server.api.model.definition.UserTaskDefinitionList;
import org.kie.server.api.model.definition.VariablesDefinition;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.instance.WorkItemInstance;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.ProcessServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.bpms.frontend.exception.BPMSFrontendException;

public class BpmsProcessServices {
	
	private static Logger logger = LoggerFactory.getLogger(BpmsProcessServices.class);
	
	private KieServicesClient kieServicesClient;
	private String containerId;

	private BpmsProcessServices() {
		// Empty constructor
	}
	
	public BpmsProcessServices(String containerId, KieServicesClient client) {
		kieServicesClient = client;
		this.containerId = containerId;
	}
	
	protected KieServicesClient getKieServicesClient() {
		return kieServicesClient;
	}
	
	protected String getContainerId() {
		return containerId;
	}

	public ProcessDefinition getProcessDefinition(String processId) {
		return getKieServicesClient().getServicesClient(ProcessServicesClient.class)
				.getProcessDefinition(getContainerId(), processId);
	}
	
	public SubProcessesDefinition getReusableSubProcessDefinitions(String processId) {
		return getKieServicesClient().getServicesClient(ProcessServicesClient.class)
				.getReusableSubProcessDefinitions(getContainerId(), processId);
	}
	
	public VariablesDefinition getProcessVariableDefinitions(String processId) {
		return getKieServicesClient().getServicesClient(ProcessServicesClient.class)
				.getProcessVariableDefinitions(getContainerId(), processId);
	}
	
	public ServiceTasksDefinition getServiceTaskDefinitions(String processId) {
		return getKieServicesClient().getServicesClient(ProcessServicesClient.class)
				.getServiceTaskDefinitions(getContainerId(), processId);
	}
	
	public AssociatedEntitiesDefinition getAssociatedEntityDefinitions(String processId) {
		return getKieServicesClient().getServicesClient(ProcessServicesClient.class)
				.getAssociatedEntityDefinitions(getContainerId(), processId);
	}
	
	public UserTaskDefinitionList getUserTaskDefinitions(String processId) {
		return getKieServicesClient().getServicesClient(ProcessServicesClient.class)
				.getUserTaskDefinitions(getContainerId(), processId);
	}
	
	public TaskInputsDefinition getUserTaskInputDefinitions(String processId, String taskName) {
		return getKieServicesClient().getServicesClient(ProcessServicesClient.class)
				.getUserTaskInputDefinitions(getContainerId(), processId, taskName);
	}
	
	public TaskOutputsDefinition getUserTaskOutputDefinitions(String processId, String taskName) {
		return getKieServicesClient().getServicesClient(ProcessServicesClient.class)
				.getUserTaskOutputDefinitions(getContainerId(), processId, taskName);
	}

    /**
     * Method allows invocation of target process using the given inputMap to pass process variables
     * 
     * @param processName
     * @param variables
     *            Must not be null
     * @return
     * @throws BPMSFrontendException
     */
    public Long startProcessWithVars(String processName, Map<String, Object> variables) throws BPMSFrontendException
    {
    	logger.debug("Entering startProcessWithVars");
    	
        try {
        	logger.debug("Leaving startProcessWithVars");
            return getKieServicesClient().getServicesClient(ProcessServicesClient.class)
            		.startProcess(getContainerId(), processName, variables);
        } catch (RuntimeException e) {
            String errorMessage = "Error starting process with name: " + processName + " error: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new BPMSFrontendException(errorMessage);
        } catch (Exception e) {
            String errorMessage = "Error creating environment DTO: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new BPMSFrontendException(errorMessage);
        }
    }
    
    public Long startProcess(String processName) throws BPMSFrontendException {
    	logger.debug("Entering startProcess");
    	
        try {
        	logger.debug("Leaving startProcess");
            return getKieServicesClient().getServicesClient(ProcessServicesClient.class)
            		.startProcess(getContainerId(), processName);
        } catch (RuntimeException e) {
            String errorMessage = "Error starting process with name: " + processName + " error: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new BPMSFrontendException(errorMessage);
        } catch (Exception e) {
            String errorMessage = "Error creating environment DTO: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new BPMSFrontendException(errorMessage);
        }
    }
    
    public Long startProcessWithKey(String processName, CorrelationKey correlationKey) throws BPMSFrontendException {
    	logger.debug("Entering startProcessWithKey");
    	
        try {
        	logger.debug("Leaving startProcessWithKey");
            return getKieServicesClient().getServicesClient(ProcessServicesClient.class)
            		.startProcess(getContainerId(), processName, correlationKey);
        } catch (RuntimeException e) {
            String errorMessage = "Error starting process with name: " + processName + " error: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new BPMSFrontendException(errorMessage);
        } catch (Exception e) {
            String errorMessage = "Error creating environment DTO: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new BPMSFrontendException(errorMessage);
        }
    }
    
    public Long startProcessWithKeyWithVars(String processName, CorrelationKey correlationKey, Map<String, Object> variables)
    		throws BPMSFrontendException {
    	logger.debug("Entering startProcessWithKeyWithVars");
    	
        try {           
        	logger.debug("Leaving startProcessWithKeyWithVars");
            return getKieServicesClient().getServicesClient(ProcessServicesClient.class)
            		.startProcess(getContainerId(), processName, correlationKey, variables);
        } catch (RuntimeException e) {
            String errorMessage = "Error starting process with name: " + processName + " error: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new BPMSFrontendException(errorMessage);
        } catch (Exception e) {
            String errorMessage = "Error creating environment DTO: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new BPMSFrontendException(errorMessage);
        }
    }
    
    public void abortProcessInstance(Long processInstanceId) {
    	logger.debug("Executing abortProcessInstance");
    	getKieServicesClient().getServicesClient(ProcessServicesClient.class)
    		.abortProcessInstance(getContainerId(), processInstanceId);
    }
    
    public void abortProcessInstances(List<Long> processInstanceIds) {
    	logger.debug("Executing abortProcessInstances");
    	getKieServicesClient().getServicesClient(ProcessServicesClient.class)
    		.abortProcessInstances(getContainerId(), processInstanceIds);
    }
    
    public Object getProcessInstanceVariable(Long processInstanceId, String variableName) {
    	logger.debug("Executing getProcessInstanceVariable");
    	return getKieServicesClient().getServicesClient(ProcessServicesClient.class)
        		.getProcessInstanceVariable(getContainerId(), processInstanceId, variableName);
    }
    
    public <T> T getProcessInstanceVariableByType(Long processInstanceId, String variableName, Class<T> type) {
    	logger.debug("Executing getProcessInstanceVariableByType");
    	return getKieServicesClient().getServicesClient(ProcessServicesClient.class)
        		.getProcessInstanceVariable(getContainerId(), processInstanceId, variableName, type);
    }
    
    /**
     * Check to see if the process has completed or aborted
     * 
     * @param processId
     * @return
     */
    public boolean isProcessFinished(long processId)
    {
    	logger.debug("Entering isProcessFinished");
    	ProcessInstance process = getKieServicesClient().getServicesClient(ProcessServicesClient.class)
    			.getProcessInstance(getContainerId(), processId);
    	int status = process.getState();
    	logger.debug("Leaving isProcessFinished");
        return (status == org.kie.api.runtime.process.ProcessInstance.STATE_ABORTED
                || status == org.kie.api.runtime.process.ProcessInstance.STATE_COMPLETED);
    }

    /**
     * Check that the process completed and was not aborted
     * 
     * @param processId
     * @return
     */
    public boolean processCompletedSuccessfully(long processId)
    {
    	logger.debug("Entering processCompletedSuccessfully");
    	ProcessInstance process = getKieServicesClient().getServicesClient(ProcessServicesClient.class)
    			.getProcessInstance(getContainerId(), processId);
    	int status = process.getState();
    	logger.debug("Leaving processCompletedSuccessfully");
        return status == org.kie.api.runtime.process.ProcessInstance.STATE_COMPLETED;
    }
    
    public Map<String, Object> getProcessInstanceVariables(Long processInstanceId) {
    	logger.debug("Executing getProcessInstanceVariables");
    	return getKieServicesClient().getServicesClient(ProcessServicesClient.class)
        		.getProcessInstanceVariables(getContainerId(), processInstanceId);
    }
    
    public void signalProcessInstance(Long processInstanceId, String signalName, Object event) throws BPMSFrontendException {
    	logger.debug("Executing signalProcessInstance");
    	try {
	    	getKieServicesClient().getServicesClient(ProcessServicesClient.class)
	        		.signalProcessInstance(getContainerId(), processInstanceId, signalName, event);
    	} catch (RuntimeException e) {
            String errorMessage = "Error signalling process with id: " + processInstanceId + " signal: " +
            		signalName + " error: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new BPMSFrontendException(errorMessage);
        }
    }
    
    public void signalProcessInstances(List<Long> processInstanceId, String signalName, Object event) throws BPMSFrontendException {
    	logger.debug("Executing signalProcessInstances");
    	
    	try {
	    	getKieServicesClient().getServicesClient(ProcessServicesClient.class)
	        		.signalProcessInstances(getContainerId(), processInstanceId, signalName, event);
    	} catch (RuntimeException e) {
            String errorMessage = "Error signalling list of processes using signal: " +
            		signalName + " error: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new BPMSFrontendException(errorMessage);
        }
    }
    
    public void signal(String signalName, Object event) throws BPMSFrontendException {
    	logger.debug("Executing signal");
    	
    	try {
	    	getKieServicesClient().getServicesClient(ProcessServicesClient.class)
	        		.signal(getContainerId(), signalName, event);
    	} catch (RuntimeException e) {
            String errorMessage = "Error sending signal: " + signalName + " error: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new BPMSFrontendException(errorMessage);
        }
    }
    
    public List<String> getAvailableSignals(Long processInstanceId) {
    	logger.debug("Executing getAvailableSignals");
    	return getKieServicesClient().getServicesClient(ProcessServicesClient.class)
        		.getAvailableSignals(getContainerId(), processInstanceId);
    }
    
    public void setProcessVariable(Long processInstanceId, String variableId, Object value) {
    	logger.debug("Executing setProcessVariable");
    	getKieServicesClient().getServicesClient(ProcessServicesClient.class)
        		.setProcessVariable(getContainerId(), processInstanceId, variableId, value);
    }
    
    public void setProcessVariables(Long processInstanceId, Map<String, Object> variables) {
    	logger.debug("Executing setProcessVariables");
    	getKieServicesClient().getServicesClient(ProcessServicesClient.class)
        		.setProcessVariables(getContainerId(), processInstanceId, variables);
    }
    
    public ProcessInstance getProcessInstance(Long processInstanceId) {
    	logger.debug("Executing getProcessInstance");
    	return getKieServicesClient().getServicesClient(ProcessServicesClient.class)
        		.getProcessInstance(getContainerId(), processInstanceId);
    }
    
    public ProcessInstance getProcessInstanceWithVars(Long processInstanceId, boolean withVars) {
    	logger.debug("Executing getProcessInstanceWithVars");
    	return getKieServicesClient().getServicesClient(ProcessServicesClient.class)
        		.getProcessInstance(getContainerId(), processInstanceId, withVars);
    }
    
    public void completeWorkItem(Long processInstanceId, Long id, Map<String, Object> results) {
    	logger.debug("Executing completeWorkItem");
    	getKieServicesClient().getServicesClient(ProcessServicesClient.class)
        		.completeWorkItem(getContainerId(), processInstanceId, id, results);
    }
    
    public void abortWorkItem(Long processInstanceId, Long id) {
    	logger.debug("Executing abortWorkItem");
    	getKieServicesClient().getServicesClient(ProcessServicesClient.class)
        		.abortWorkItem(getContainerId(), processInstanceId, id);
    }
    
    public WorkItemInstance getWorkItem(Long processInstanceId, Long id) {
    	logger.debug("Executing getWorkItem");
    	return getKieServicesClient().getServicesClient(ProcessServicesClient.class)
        		.getWorkItem(getContainerId(), processInstanceId, id);
    }
    
    public List<WorkItemInstance> getWorkItemByProcessInstances(Long processInstanceId) {
    	logger.debug("Executing getWorkItemByProcessInstances");
    	return getKieServicesClient().getServicesClient(ProcessServicesClient.class)
        		.getWorkItemByProcessInstance(getContainerId(), processInstanceId);
    }
}
