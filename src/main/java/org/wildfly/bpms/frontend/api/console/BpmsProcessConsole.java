package org.wildfly.bpms.frontend.api.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.internal.process.CorrelationKey;
import org.kie.server.api.model.definition.AssociatedEntitiesDefinition;
import org.kie.server.api.model.definition.ProcessDefinition;
import org.kie.server.api.model.definition.ServiceTasksDefinition;
import org.kie.server.api.model.definition.SubProcessesDefinition;
import org.kie.server.api.model.definition.TaskInputsDefinition;
import org.kie.server.api.model.definition.TaskOutputsDefinition;
import org.kie.server.api.model.definition.UserTaskDefinition;
import org.kie.server.api.model.definition.UserTaskDefinitionList;
import org.kie.server.api.model.definition.VariablesDefinition;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.instance.WorkItemInstance;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.ProcessServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.bpms.frontend.api.BpmsProcessServices;
import org.wildfly.bpms.frontend.exception.BpmsFrontendException;
import org.wildfly.bpms.frontend.util.FrontendUtil;

public class BpmsProcessConsole extends BpmsProcessServices {
	
	private static Logger logger = LoggerFactory.getLogger(BpmsProcessConsole.class);

	public BpmsProcessConsole(String containerId, KieServicesClient client) {
		super(containerId, client);
	}
	
	public String getProcessDefinitionConsole(String processId) {
		logger.debug("Executing getProcessDefinitionConsole");
		ProcessDefinition processDef = getKieServicesClient().getServicesClient(ProcessServicesClient.class)
				.getProcessDefinition(getContainerId(), processId);
		Map<String, String> processVars = processDef.getProcessVariables();
		String formattedVars = parseMapToString(processVars);
		
		return String.format("=====%nProcess ID: %s%nProcess Name: %s%nFrom Container ID: %s%nProcess Version: %s%nProcess Variables: %n%s%n", processDef.getId(), processDef.getName(), processDef.getContainerId(), processDef.getVersion(), formattedVars);
	}
	
	public String getReusableSubProcessDefinitionsConsole(String processId) {
		SubProcessesDefinition procDef = getKieServicesClient().getServicesClient(ProcessServicesClient.class)
				.getReusableSubProcessDefinitions(getContainerId(), processId);
		
		String result = null;
		for(String process : procDef.getSubProcesses()) {
			if(result == null) {
				result = String.format("=====%n");
			}
			
			result = result.concat(String.format("Process Definition: %n%s%n", process));
		}
		
		return result;
	}
	
	public String getProcessVariableDefinitionsConsole(String processId) {
		VariablesDefinition varDef = getKieServicesClient().getServicesClient(ProcessServicesClient.class)
				.getProcessVariableDefinitions(getContainerId(), processId);
		
		Map<String, String> processVars = varDef.getVariables();
		String formattedVars = parseMapToString(processVars);
		
		return String.format("=====%nProcess Variables: %n%s%n", formattedVars);
	}
	
	public String getServiceTaskDefinitionsConsole(String processId) {
		ServiceTasksDefinition serviceDef = getKieServicesClient().getServicesClient(ProcessServicesClient.class)
				.getServiceTaskDefinitions(getContainerId(), processId);
		
		Map<String, String> processVars = serviceDef.getServiceTasks();
		String formattedVars = parseMapToString(processVars);
		
		return String.format("=====%nService Tasks: %n%s%n", formattedVars);
	}
	
	public String getAssociatedEntityDefinitionsConsole(String processId) {
		AssociatedEntitiesDefinition entityDef = getKieServicesClient().getServicesClient(ProcessServicesClient.class)
				.getAssociatedEntityDefinitions(getContainerId(), processId);
		
		String formattedVars = parseMapToStringWithArray(entityDef.getAssociatedEntities());
		
		return String.format("=====%nAssociated Entity Definitions: %n%s%n", formattedVars);
	}
	
	public String getUserTaskDefinitionsConsole(String processId) {
		UserTaskDefinitionList defList = getKieServicesClient().getServicesClient(ProcessServicesClient.class)
				.getUserTaskDefinitions(getContainerId(), processId);
		List<UserTaskDefinition> userTaskDef = defList.getItems();
		
		String userTaskStr = "=====%n";
		for(UserTaskDefinition userTaskItem : userTaskDef) {
			String userTaskInputItems = parseMapToString(userTaskItem.getTaskInputMappings());
			String userTaskOutputItems = parseMapToString(userTaskItem.getTaskOutputMappings());
			userTaskStr = userTaskStr.concat(String.format("Task Name: %s%nPriority: %s%nCreated By: %s%nComment: %s%nTask Input Items: %s%nTask Output Items: %s%n", userTaskItem.getName(), userTaskItem.getPriority(), userTaskItem.getCreatedBy(), userTaskItem.getComment(), userTaskInputItems, userTaskOutputItems));
		}
		
		return userTaskStr;
	}
	
	public String getUserTaskInputDefinitionsConsole(String processId, String taskName) {
		TaskInputsDefinition taskInputDef = getKieServicesClient().getServicesClient(ProcessServicesClient.class)
				.getUserTaskInputDefinitions(getContainerId(), processId, taskName);
		
		String formattedVars = parseMapToString(taskInputDef.getTaskInputs());
		
		return String.format("=====%nTask Input Definitions: %n%s%n", formattedVars);
	}
	
	public String getUserTaskOutputDefinitionsConsole(String processId, String taskName) {
		TaskOutputsDefinition taskOutputDef = getKieServicesClient().getServicesClient(ProcessServicesClient.class)
				.getUserTaskOutputDefinitions(getContainerId(), processId, taskName);
		
		String formattedVars = parseMapToString(taskOutputDef.getTaskOutputs());
		
		return String.format("=====%nTask Output Definitions: %n%s%n", formattedVars);
	}

    /**
     * Method allows invocation of target process using the given inputMap to pass process variables
     * 
     * @param processName
     * @param variables
     *            Must not be null
     * @return
     * @throws BpmsFrontendException
     */
    public String startProcessWithVarsConsole(String processName, String variables) throws BpmsFrontendException
    {
    	logger.debug("Entering startProcessWithVars");
    	
        try {
        	Map<String, Object> variableMap = FrontendUtil.parseStringToMapStringObject(variables, ",", "=");
        	
        	Long processId = getKieServicesClient().getServicesClient(ProcessServicesClient.class)
            		.startProcess(getContainerId(), processName, variableMap);
        	
        	logger.debug("Leaving startProcessWithVars");
            return String.format("Process successfully started with ID %s%n", String.valueOf(processId));
        } catch (RuntimeException e) {
            String errorMessage = "Error starting process with name: " + processName + " error: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new BpmsFrontendException(errorMessage);
        } catch (Exception e) {
            String errorMessage = "Error creating environment DTO: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new BpmsFrontendException(errorMessage);
        }
    }
    
    public String startProcessConsole(String processName) throws BpmsFrontendException {
    	logger.debug("Entering startProcess");
    	
        try {
        	logger.debug("Leaving startProcess");
        	Long processId = getKieServicesClient().getServicesClient(ProcessServicesClient.class)
            		.startProcess(getContainerId(), processName);
            return String.format("Process successfully started with ID %s%n", String.valueOf(processId));
        } catch (RuntimeException e) {
            String errorMessage = "Error starting process with name: " + processName + " error: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new BpmsFrontendException(errorMessage);
        } catch (Exception e) {
            String errorMessage = "Error creating environment DTO: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new BpmsFrontendException(errorMessage);
        }
    }
    
    //TODO: Need to find a way to supply a CorrelationKey from the console
    public String startProcessWithKeyConsole(String processName, CorrelationKey correlationKey) throws BpmsFrontendException {
    	logger.debug("Entering startProcessWithKey");
    	
        try {
        	logger.debug("Leaving startProcessWithKey");
        	Long processId = getKieServicesClient().getServicesClient(ProcessServicesClient.class)
            		.startProcess(getContainerId(), processName, correlationKey);
            return String.format("Process successfully started with ID %s%n", String.valueOf(processId));
        } catch (RuntimeException e) {
            String errorMessage = "Error starting process with name: " + processName + " error: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new BpmsFrontendException(errorMessage);
        } catch (Exception e) {
            String errorMessage = "Error creating environment DTO: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new BpmsFrontendException(errorMessage);
        }
    }
    
    public String startProcessWithKeyWithVarsConsole(String processName, CorrelationKey correlationKey, Map<String, Object> variables)
    		throws BpmsFrontendException {
    	logger.debug("Entering startProcessWithKeyWithVars");
    	
        try {           
        	logger.debug("Leaving startProcessWithKeyWithVars");
        	Long processId = getKieServicesClient().getServicesClient(ProcessServicesClient.class)
            		.startProcess(getContainerId(), processName, correlationKey, variables);
            return String.format("Process successfully started with ID %s%n", String.valueOf(processId));
        } catch (RuntimeException e) {
            String errorMessage = "Error starting process with name: " + processName + " error: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new BpmsFrontendException(errorMessage);
        } catch (Exception e) {
            String errorMessage = "Error creating environment DTO: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new BpmsFrontendException(errorMessage);
        }
    }
    
    public String abortProcessInstanceConsole(String processInstanceId) {
    	logger.debug("Executing abortProcessInstance");
    	getKieServicesClient().getServicesClient(ProcessServicesClient.class)
    		.abortProcessInstance(getContainerId(), Long.valueOf(processInstanceId));
    	return String.format("Calling API to abort process instance with ID %s", processInstanceId);
    }
    
    public String abortProcessInstancesConsole(String processInstanceIds) {
    	logger.debug("Executing abortProcessInstances");
    	List<Long> idList = new ArrayList<>(1);
    	
    	String[] idStrings = processInstanceIds.split(",");
    	
    	for(int i = 0; i < idStrings.length; i++) {
    		idList.add(Long.valueOf(idStrings[i]));
    	}
    	
    	getKieServicesClient().getServicesClient(ProcessServicesClient.class)
    		.abortProcessInstances(getContainerId(), idList);
    	
    	return String.format("Calling API to abort process IDs %s%n", processInstanceIds);
    }
    
    public String getProcessInstanceVariableConsole(String processInstanceId, String variableName) {
    	logger.debug("Executing getProcessInstanceVariable");
    	Object variable = getKieServicesClient().getServicesClient(ProcessServicesClient.class)
        		.getProcessInstanceVariable(getContainerId(), Long.valueOf(processInstanceId), variableName);
    	return String.format("Retrieved process instance variable %s of type %s%n", variable.getClass().toString(), variable.toString());
    }
    
    public <T> String getProcessInstanceVariableByTypeConsole(String processInstanceId, String variableName, String type) {
    	logger.debug("Entering getProcessInstanceVariableByType");
    	Class<T> typeClass;
    	T typeObj = null;
    	
		try {
			typeClass = (Class<T>) Class.forName(type);
			typeObj = getKieServicesClient().getServicesClient(ProcessServicesClient.class)
	        		.getProcessInstanceVariable(getContainerId(), Long.valueOf(processInstanceId), variableName, typeClass);
			logger.debug("Leaving getProcessInstanceVariableByType");
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
		}
    	
    	return String.format("Retrieved process variable of type %s from process ID %s", typeObj.getClass().getSimpleName(), processInstanceId);
    }
    
    /**
     * Check to see if the process has completed or aborted
     * 
     * @param processId
     * @return
     */
    public boolean isProcessFinishedConsole(String processId)
    {
    	logger.debug("Entering isProcessFinished");
    	ProcessInstance process = getKieServicesClient().getServicesClient(ProcessServicesClient.class)
    			.getProcessInstance(getContainerId(), Long.valueOf(processId));
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
    public boolean processCompletedSuccessfullyConsole(String processId)
    {
    	logger.debug("Entering processCompletedSuccessfully");
    	ProcessInstance process = getKieServicesClient().getServicesClient(ProcessServicesClient.class)
    			.getProcessInstance(getContainerId(), Long.valueOf(processId));
    	int status = process.getState();
    	logger.debug("Leaving processCompletedSuccessfully");
        return status == org.kie.api.runtime.process.ProcessInstance.STATE_COMPLETED;
    }
    
    public String getProcessInstanceVariablesConsole(String processInstanceId) {
    	logger.debug("Executing getProcessInstanceVariables");
    	Map<String, Object> variableMap = getKieServicesClient().getServicesClient(ProcessServicesClient.class)
        		.getProcessInstanceVariables(getContainerId(), Long.valueOf(processInstanceId));
    	
    	ArrayList<String> varList = new ArrayList<>(1);
		for(String key : variableMap.keySet()) {
			varList.add(key);
			if(variableMap.get(key) == null) {
				varList.add("(not populated)");
			}else {
				varList.add(String.valueOf(variableMap.get(key)));
			}
		}
		
		String formattedVars = "";
		for(int i = 0; i < varList.size(); i += 2) {
			formattedVars = formattedVars.concat(varList.get(i) + "   " + varList.get(i + 1) + "%n");
		}
		
    	return String.format("Retrieve process variables for ID %s:%n%s%n", processInstanceId, formattedVars);
    }
    
    public String signalProcessInstanceConsole(String processInstanceId, String signalName, String event) throws BpmsFrontendException {
    	logger.debug("Executing signalProcessInstance");
    	String result;
    	
    	try {
	    	getKieServicesClient().getServicesClient(ProcessServicesClient.class)
	        		.signalProcessInstance(getContainerId(), Long.valueOf(processInstanceId), signalName, Class.forName(event).newInstance());
	    	result = String.format("Calling API to signal process instance ID %s with signal %s", processInstanceId, signalName);
    	} catch (RuntimeException e) {
            String errorMessage = "Error signalling process with id: " + processInstanceId + " signal: " +
            		signalName + " error: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new BpmsFrontendException(errorMessage);
        } catch (ClassNotFoundException e) {
			String errorMessage = String.format("Unable to locate class %s. Error is:%n%s%n", event, e.getMessage());
			logger.error(errorMessage);
			throw new BpmsFrontendException(errorMessage);
		} catch (InstantiationException e) {
        	String errorMessage = String.format("Error instantiating class %s. Error is:%n%s%n", event, e.getMessage());
			logger.error(errorMessage);
			throw new BpmsFrontendException(errorMessage);
		} catch (IllegalAccessException e) {
			String errorMessage = String.format("Error accessing class %s. Error is:%n%s%n", event, e.getMessage());
			logger.error(errorMessage);
			throw new BpmsFrontendException(errorMessage);
		}
    	
    	return result;
    }
    
    public String signalProcessInstancesConsole(String signalName, String event, String processInstanceIds) throws BpmsFrontendException {
    	logger.debug("Executing signalProcessInstances");
    	String result;
    	
    	try {
    		List<Long> idList = new ArrayList<>(1);
    		String[] idStrings = processInstanceIds.split(",");
    		for(int i = 0; i < idStrings.length; i++) {
    			idList.add(Long.valueOf(idStrings[i]));
    		}
    		
	    	getKieServicesClient().getServicesClient(ProcessServicesClient.class)
	        		.signalProcessInstances(getContainerId(), idList, signalName,
	        				Class.forName(event).newInstance());
	    	result = String.format("Calling API to signal the processes %s with signal %s%n", processInstanceIds, signalName);
    	} catch (RuntimeException e) {
            String errorMessage = "Error signalling list of processes using signal: " +
            		signalName + " error: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new BpmsFrontendException(errorMessage);
        } catch (InstantiationException e) {
        	String errorMessage = String.format("Error instantiating class %s. Error is:%n%s%n", event, e.getMessage());
			logger.error(errorMessage);
			throw new BpmsFrontendException(errorMessage);
		} catch (IllegalAccessException e) {
			String errorMessage = String.format("Error accessing class %s. Error is:%n%s%n", event, e.getMessage());
			logger.error(errorMessage);
			throw new BpmsFrontendException(errorMessage);
		} catch (ClassNotFoundException e) {
			String errorMessage = String.format("Unable to locate class %s. Error is:%n%s%n", event, e.getMessage());
			logger.error(errorMessage);
			throw new BpmsFrontendException(errorMessage);
		}
    	
    	return result;
    }
    
    public String signalConsole(String signalName, String event) throws BpmsFrontendException {
    	logger.debug("Executing signal");
    	String result;
    	
    	try {
    		Object eventObj = Class.forName(event).newInstance();
	    	getKieServicesClient().getServicesClient(ProcessServicesClient.class)
	        		.signal(getContainerId(), signalName, eventObj);
	    	result = String.format("Calling API to signal all processes with signal %s%n", signalName);
    	} catch (RuntimeException e) {
            String errorMessage = "Error sending signal: " + signalName + " error: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new BpmsFrontendException(errorMessage);
    	} catch (InstantiationException e) {
        	String errorMessage = String.format("Error instantiating class %s. Error is:%n%s%n", event, e.getMessage());
			logger.error(errorMessage);
			throw new BpmsFrontendException(errorMessage);
		} catch (IllegalAccessException e) {
			String errorMessage = String.format("Error accessing class %s. Error is:%n%s%n", event, e.getMessage());
			logger.error(errorMessage);
			throw new BpmsFrontendException(errorMessage);
		} catch (ClassNotFoundException e) {
			String errorMessage = String.format("Unable to locate class %s. Error is:%n%s%n", event, e.getMessage());
			logger.error(errorMessage);
			throw new BpmsFrontendException(errorMessage);
		}
    	
    	return result;
    }
    
    public String getAvailableSignalsConsole(String processInstanceId) {
    	logger.debug("Executing getAvailableSignals");
    	List<String> signalList = getKieServicesClient().getServicesClient(ProcessServicesClient.class)
        		.getAvailableSignals(getContainerId(), Long.valueOf(processInstanceId));
    	
    	String formattedString = "";
    	for(String signal : signalList) {
    		formattedString = formattedString.concat(signal + ",");
    	}
    	
    	return String.format("=====%nList of available signals:%n%s%n", formattedString);
    }
    
    public String setProcessVariableConsole(String processInstanceId, String variableId, String value) throws BpmsFrontendException {
    	logger.debug("Executing setProcessVariable");
    	
    	try {
    		Object valueObj = Class.forName(value).newInstance();
			getKieServicesClient().getServicesClient(ProcessServicesClient.class)
    		.setProcessVariable(getContainerId(), Long.valueOf(processInstanceId), variableId, valueObj);
    	} catch (RuntimeException e) {
            String errorMessage = String.format("Error setting process variable %s for process ID %s. Error is:%n%s%n", variableId, processInstanceId);
            logger.error(errorMessage, e);
            throw new BpmsFrontendException(errorMessage);
    	} catch (InstantiationException e) {
    		String errorMessage = String.format("Error instantiating process variable %s for process ID %s. Error is:%n%s%n", variableId, processInstanceId);
			logger.error(errorMessage);
			throw new BpmsFrontendException(errorMessage);
		} catch (IllegalAccessException e) {
			String errorMessage = String.format("Error accessing process variable %s for process ID %s. Error is:%n%s%n", variableId, processInstanceId);
			logger.error(errorMessage);
			throw new BpmsFrontendException(errorMessage);
		} catch (ClassNotFoundException e) {
			String errorMessage = String.format("Unable to locate a required class%n. Error is:%n%s%n", e.getMessage());
			logger.error(errorMessage);
			throw new BpmsFrontendException(errorMessage);
		}
    	
    	return String.format("Calling API to set process variable %s for process ID %s", variableId, processInstanceId);
    }
    
    //<pre>variables</pre> parameter should be in the format "key1=value1,key2=value2", etc.
    public String setProcessVariablesConsole(String processInstanceId, String variables) throws BpmsFrontendException {
    	logger.debug("Entering setProcessVariables");
    	
    	Map<String, Object> variableMap = new HashMap<>(1);
    	String[] varStringPairs = variables.split(",");
    	String result = "";
    	String[] keyValue;
    	
    	for(int i = 0; i < varStringPairs.length; i++) {
    		Object value;
			try {
				keyValue = varStringPairs[i].split("=");
				result = result.concat(String.format("%s, ", keyValue[0]));
				if(keyValue[1] == null || keyValue[1].isEmpty()) {
					keyValue[1] = "";
				}
				value = Class.forName(keyValue[1]).newInstance();
			} catch (InstantiationException e) {
	    		String errorMessage = String.format("Error instantiating process variables for process ID %s. Error is:%n%s%n", processInstanceId);
				logger.error(errorMessage);
				throw new BpmsFrontendException(errorMessage);
			} catch (IllegalAccessException e) {
				String errorMessage = String.format("Error accessing process variables for process ID %s. Error is:%n%s%n", processInstanceId);
				logger.error(errorMessage);
				throw new BpmsFrontendException(errorMessage);
			} catch (ClassNotFoundException e) {
				String errorMessage = String.format("Unable to locate a required class%n. Error is:%n%s%n", e.getMessage());
				logger.error(errorMessage);
				throw new BpmsFrontendException(errorMessage);
			}
    		variableMap.put(keyValue[0], value);
    	}
    	
    	getKieServicesClient().getServicesClient(ProcessServicesClient.class)
		.setProcessVariables(getContainerId(), Long.valueOf(processInstanceId), variableMap);
    	
    	logger.debug("Leaving setProcessVariables");
    	
    	return String.format("Calling API to set process variables %s for process ID %s", result, processInstanceId);
    }
    
    public String getProcessInstanceConsole(String processInstanceId) {
    	logger.debug("Executing getProcessInstance");
    	ProcessInstance instance = getKieServicesClient().getServicesClient(ProcessServicesClient.class)
        		.getProcessInstance(getContainerId(), Long.valueOf(processInstanceId));
    	return String.format("=====%nProcess ID: %s%nProcess Parent ID: %s%n Process Name: %s%n"
    			+ "Correlation Key: %s%nContainer ID: %s%n", instance.getProcessId(),
    			instance.getParentId().toString(), instance.getProcessName(), instance.getCorrelationKey(),
    			instance.getContainerId());
    }
    
    //There is no reason to use this method unless you want variables returned, so the "withVars" parameter
    //	is hard-coded to "true". If variables should not be returned, use <pre>getProcessInstanceConsole</pre>.
    public String getProcessInstanceWithVarsConsole(String processInstanceId) {
    	logger.debug("Executing getProcessInstanceWithVars");
    	ProcessInstance instance = getKieServicesClient().getServicesClient(ProcessServicesClient.class)
        		.getProcessInstance(getContainerId(), Long.valueOf(processInstanceId), Boolean.TRUE);
    	Map<String, Object> variableMap = instance.getVariables();
    	String result = "";
    	
    	for(String key : variableMap.keySet()) {
    		result = result.concat(String.format("%s,"));
    	}
    	
    	result = result.replace(result.charAt(result.length()), "\n".charAt(0));
    	
    	return String.format("=====%nProcess ID: %s%nProcess Parent ID: %s%n Process Name: %s%n"
    			+ "Correlation Key: %s%nContainer ID: %s%nVariables: %s%n", instance.getProcessId(),
    			instance.getParentId().toString(), instance.getProcessName(), instance.getCorrelationKey(),
    			instance.getContainerId(), result);
    }
    
    public String completeWorkItemConsole(String processInstanceId, String id, String results) throws BpmsFrontendException {
    	logger.debug("Executing completeWorkItem");
    	Map<String, Object> resultMap = new HashMap<>(1);
    	String[] stringPairs = results.split(",");
    	String[] keyValue;
    	for(int i = 0; i < stringPairs.length; i++) {
    		keyValue = stringPairs[i].split("=");
    		Object value;
			try {
				value = Class.forName(keyValue[1]).newInstance();
			} catch (InstantiationException e) {
	    		String errorMessage = String.format("Error completing work item for work item ID %s. Error is:%n%s%n", id);
				logger.error(errorMessage);
				throw new BpmsFrontendException(errorMessage);
			} catch (IllegalAccessException e) {
				String errorMessage = String.format("Error accessing work item for work item ID %s. Error is:%n%s%n", id);
				logger.error(errorMessage);
				throw new BpmsFrontendException(errorMessage);
			} catch (ClassNotFoundException e) {
				String errorMessage = String.format("Unable to locate a required class%n. Error is:%n%s%n", e.getMessage());
				logger.error(errorMessage);
				throw new BpmsFrontendException(errorMessage);
			}
			
    		resultMap.put(keyValue[0], value);
    	}
    	
    	getKieServicesClient().getServicesClient(ProcessServicesClient.class)
		.completeWorkItem(getContainerId(), Long.valueOf(processInstanceId), Long.valueOf(id), resultMap);
    	
    	return String.format("=====%nCalling API to complete work item ID %s%n", id);
    }
    
    public String abortWorkItemConsole(String processInstanceId, String id) {
    	logger.debug("Executing abortWorkItem");
    	getKieServicesClient().getServicesClient(ProcessServicesClient.class)
        		.abortWorkItem(getContainerId(), Long.valueOf(processInstanceId), Long.valueOf(id));
    	
    	return String.format("=====%nCalling API to abort work item ID %s%n", id);
    }
    
    public String getWorkItemConsole(String processInstanceId, String id) {
    	logger.debug("Executing getWorkItem");
    	WorkItemInstance instance = getKieServicesClient().getServicesClient(ProcessServicesClient.class)
        		.getWorkItem(getContainerId(), Long.valueOf(processInstanceId), Long.valueOf(id));
    	return String.format("=====%nWork Item ID: %s%nWork Item Name: %s%nProcess Instance ID: %s%nContainer ID: %s%n",
    			instance.getId(), instance.getName(), instance.getProcessInstanceId(), instance.getContainerId());
    }
    
    public String getWorkItemByProcessInstancesConsole(String processInstanceId) {
    	logger.debug("Executing getWorkItemByProcessInstances");
    	List<WorkItemInstance> instanceList = getKieServicesClient().getServicesClient(ProcessServicesClient.class)
        		.getWorkItemByProcessInstance(getContainerId(), Long.valueOf(processInstanceId));
    	
    	String result = "";
    	for(WorkItemInstance instance : instanceList) {
    		result = result.concat(String.format("*****%n"));
    		result = result.concat(String.format("Work Item ID: %s%nWork Item Name: %s%n"
    				+ "Process Instance ID: %s%nContainer ID: %s%n", instance.getId(), instance.getName(),
    				instance.getProcessInstanceId(), instance.getContainerId()));
    	}
    	
    	return String.format("=====%n%s%n", result);
    }

    private String parseMapToString(Map<String, String> stringMap) {
    	ArrayList<String> varList = new ArrayList<>(1);
		for(String key : stringMap.keySet()) {
			varList.add(key);
			if(stringMap.get(key) == null) {
				varList.add("(not populated)");
			}else {
				varList.add(stringMap.get(key));
			}
		}
		
		String formattedVars = "";
		for(int i = 0; i < varList.size(); i += 2) {
			formattedVars = formattedVars.concat(varList.get(i) + "   " + varList.get(i + 1) + "%n");
		}
		
		return formattedVars;
    }
    
    private String parseMapToStringWithArray(Map<String, String[]> stringMap) {
    	ArrayList<String> varList = new ArrayList<>(1);
		for(String key : stringMap.keySet()) {
			varList.add(key);
			if(stringMap.get(key) == null) {
				varList.add("(not populated)");
			}else {
				String[] strArray = stringMap.get(key);
				String resultStr = "";
				
				for(int i = 0; i < strArray.length; i++) {
					resultStr = resultStr.concat(String.format("%s,", strArray[i]));
				}
				resultStr.replace(resultStr.charAt(resultStr.length()), "".charAt(0));
				resultStr.trim();
				
				varList.add(resultStr);
			}
		}
		
		String formattedVars = "";
		for(int i = 0; i < varList.size(); i += 2) {
			formattedVars = formattedVars.concat(varList.get(i) + "   " + varList.get(i + 1) + "%n");
		}
		
		return formattedVars;
    }
}
