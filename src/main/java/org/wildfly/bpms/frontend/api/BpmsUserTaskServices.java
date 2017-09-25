package org.wildfly.bpms.frontend.api;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.kie.server.api.model.instance.TaskAttachment;
import org.kie.server.api.model.instance.TaskComment;
import org.kie.server.api.model.instance.TaskEventInstance;
import org.kie.server.api.model.instance.TaskInstance;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.UserTaskServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BpmsUserTaskServices {
	
	private static Logger logger = LoggerFactory.getLogger(BpmsUserTaskServices.class);
	
	private KieServicesClient kieServicesClient;
	private String containerId;

	private BpmsUserTaskServices() {
		// Empty constructor
	}
	
	public BpmsUserTaskServices(String containerId, KieServicesClient client) {
		kieServicesClient = client;
		this.containerId = containerId;
	}
	
	protected KieServicesClient getKieServicesClient() {
		return kieServicesClient;
	}
	
	protected String getContainerId() {
		return containerId;
	}

	public void activateTask(Long taskId, String userId){
    	logger.debug("Executing activateTask");
    	getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.activateTask(getContainerId(), taskId, userId);
    }

    public void claimTask(Long taskId, String userId){
    	logger.debug("Executing claimTask");
    	getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.claimTask(getContainerId(), taskId, userId);
    }

    public void completeTask(Long taskId, String userId, Map<String, Object> params){
    	logger.debug("Executing completeTask");
    	getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.completeTask(getContainerId(), taskId, userId, params);
    }

    public void completeAutoProgress(Long taskId, String userId, Map<String, Object> params){
    	logger.debug("Executing completeAutoProgress");
    	getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.completeAutoProgress(getContainerId(), taskId, userId, params);
    }

    public void delegateTask(Long taskId, String userId, String targetUserId){
    	logger.debug("Executing delegateTask");
    	getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.delegateTask(getContainerId(), taskId, userId, targetUserId);
    }

    public void exitTask(Long taskId, String userId){
    	logger.debug("Executing exitTask");
    	getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.exitTask(getContainerId(), taskId, userId);
    }

    public void failTask(Long taskId, String userId, Map<String, Object> params){
    	logger.debug("Executing failTask");
    	getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.failTask(getContainerId(), taskId, userId, params);
    }

    public void forwardTask(Long taskId, String userId, String targetEntityId){
    	logger.debug("Executing forwardTask");
    	getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.forwardTask(getContainerId(), taskId, userId, targetEntityId);
    }

    public void releaseTask(Long taskId, String userId){
    	logger.debug("Executing releaseTask");
    	getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.releaseTask(getContainerId(), taskId, userId);
    }

    public void resumeTask(Long taskId, String userId){
    	logger.debug("Executing resumeTask");
    	getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.resumeTask(getContainerId(), taskId, userId);
    }

    public void skipTask(Long taskId, String userId){
    	logger.debug("Executing skipTask");
    	getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.skipTask(getContainerId(), taskId, userId);
    }

    public void startTask(Long taskId, String userId){
    	logger.debug("Executing startTask");
    	getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.startTask(getContainerId(), taskId, userId);
    }

    public void stopTask(Long taskId, String userId){
    	logger.debug("Executing stopTask");
    	getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.stopTask(getContainerId(), taskId, userId);
    }

    public void suspendTask(Long taskId, String userId){
    	logger.debug("Executing suspendTask");
    	getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.suspendTask(getContainerId(), taskId, userId);
    }

    public void nominateTask(Long taskId, String userId, List<String> potentialOwners){
    	logger.debug("Executing nominateTask");
    	getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.nominateTask(getContainerId(), taskId, userId, potentialOwners);
    }

    public void setTaskPriority(Long taskId, int priority){
    	logger.debug("Executing setTaskPriority");
    	getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.setTaskPriority(getContainerId(), taskId, priority);
    }

    public void setTaskExpirationDate(Long taskId, Date date){
    	logger.debug("Executing setTaskExpirationDate");
    	getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.setTaskExpirationDate(getContainerId(), taskId, date);
    }

    public void setTaskSkipable(Long taskId, boolean skipable){
    	logger.debug("Executing setTaskSkipable");
    	getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.setTaskSkipable(getContainerId(), taskId, skipable);
    }

    public void setTaskName(Long taskId, String name){
    	logger.debug("Executing setTaskName");
    	getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.setTaskName(getContainerId(), taskId, name);
    }

    public void setTaskDescription(Long taskId, String description){
    	logger.debug("Executing setTaskDescription");
    	getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.setTaskDescription(getContainerId(), taskId, description);
    }

    public Long saveTaskContent(Long taskId, Map<String, Object> values){
    	logger.debug("Executing saveTaskContent");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.saveTaskContent(getContainerId(), taskId, values);
    }

    public Map<String, Object> getTaskOutputContentByTaskId(Long taskId){
    	logger.debug("Executing getTaskOutputContentByTaskId");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.getTaskOutputContentByTaskId(getContainerId(), taskId);
    }

    public Map<String, Object> getTaskInputContentByTaskId(Long taskId){
    	logger.debug("Executing getTaskInputContentByTaskId");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.getTaskInputContentByTaskId(getContainerId(), taskId);
    }

    public void deleteTaskContent(Long taskId, Long contentId){
    	logger.debug("Executing deleteTaskContent");
    	getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.deleteTaskContent(getContainerId(), taskId, contentId);
    }

    public Long addTaskComment(Long taskId, String text, String addedBy, Date addedOn){
    	logger.debug("Executing addTaskComment");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.addTaskComment(getContainerId(), taskId, text, addedBy, addedOn);
    }

    public void deleteTaskComment(Long taskId, Long commentId){
    	logger.debug("Executing deleteTaskComment");
    	getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.deleteTaskComment(getContainerId(), taskId, commentId);
    }

    public List<TaskComment> getTaskCommentsByTaskId(Long taskId){
    	logger.debug("Executing getTaskCommentsByTaskId");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.getTaskCommentsByTaskId(getContainerId(), taskId);
    }

    public TaskComment getTaskCommentById(Long taskId, Long commentId){
    	logger.debug("Executing getTaskCommentById");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.getTaskCommentById(getContainerId(), taskId, commentId);
    }

    public Long addTaskAttachment(Long taskId, String userId, String name, Object attachment){
    	logger.debug("Executing addTaskAttachment");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.addTaskAttachment(getContainerId(), taskId, userId, name, attachment);
    }

    public void deleteTaskAttachment(Long taskId, Long attachmentId){
    	logger.debug("Executing deleteTaskAttachment");
    	getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.deleteTaskAttachment(getContainerId(), taskId, attachmentId);
    }

    public TaskAttachment getTaskAttachmentById(Long taskId, Long attachmentId){
    	logger.debug("Executing getTaskAttachmentById");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.getTaskAttachmentById(getContainerId(), taskId, attachmentId);
    }

    public Object getTaskAttachmentContentById(Long taskId, Long attachmentId){
    	logger.debug("Executing getTaskAttachmentContentById");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.getTaskAttachmentContentById(getContainerId(), taskId, attachmentId);
    }

    public List<TaskAttachment> getTaskAttachmentsByTaskId(Long taskId){
    	logger.debug("Executing getTaskAttachmentsByTaskId");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.getTaskAttachmentsByTaskId(getContainerId(), taskId);
    }

    public TaskInstance getTaskInstance(Long taskId){
    	logger.debug("Executing getTaskInstance");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.getTaskInstance(getContainerId(), taskId);
    }

    public TaskInstance getTaskInstanceWithInputOutput(Long taskId, boolean withInputs, boolean withOutputs, boolean withAssignments){
    	logger.debug("Executing getTaskInstanceWithInputOutput");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.getTaskInstance(getContainerId(), taskId, withInputs, withOutputs, withAssignments);
    }

    public TaskInstance findTaskByWorkItemId(Long workItemId){
    	logger.debug("Executing findTaskByWorkItemId");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTaskByWorkItemId(workItemId);
    }

    public TaskInstance findTaskById(Long taskId){
    	logger.debug("Executing findTaskById");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTaskById(taskId);
    }

    public List<TaskSummary> findTasksAssignedAsBusinessAdministrator(String userId, Integer page, Integer pageSize){
    	logger.debug("Executing findTasksAssignedAsBusinessAdministrator");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTasksAssignedAsBusinessAdministrator(userId, page, pageSize);
    }

    public List<TaskSummary> findTasksAssignedAsBusinessAdministratorWithStatus(String userId, List<String> status,
    		Integer page, Integer pageSize){
    	logger.debug("Executing findTasksAssignedAsBusinessAdministratorWithStatus");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTasksAssignedAsBusinessAdministrator(userId, status, page, pageSize);
    }

    public List<TaskSummary> findTasksAssignedAsPotentialOwner(String userId, Integer page, Integer pageSize){
    	logger.debug("Executing findTasksAssignedAsPotentialOwner");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTasksAssignedAsPotentialOwner(userId, page, pageSize);
    }

    public List<TaskSummary> findTasksAssignedAsPotentialOwnerWithStatus(String userId, List<String> status,
    		Integer page, Integer pageSize){
    	logger.debug("Executing findTasksAssignedAsPotentialOwnerWithStatus");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTasksAssignedAsPotentialOwner(userId, status, page, pageSize);
    }

    public List<TaskSummary> findTasksAssignedAsPotentialOwnerWithGroups(String userId, List<String> groups,
    		List<String> status, Integer page, Integer pageSize){
    	logger.debug("Executing findTasksAssignedAsPotentialOwnerWithGroups");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTasksAssignedAsPotentialOwner(userId, groups, status, page, pageSize);
    }

    public List<TaskSummary> findTasksOwned(String userId, Integer page, Integer pageSize){
    	logger.debug("Executing findTasksOwned");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTasksOwned(userId, page, pageSize);
    }

    public List<TaskSummary> findTasksOwnedWithStatuses(String userId, List<String> status, Integer page, Integer pageSize){
    	logger.debug("Executing findTasksOwned");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTasksOwned(userId, status, page, pageSize);
    }

    public List<TaskSummary> findTasksByStatusByProcessInstanceId(Long processInstanceId, List<String> status,
    		Integer page, Integer pageSize){
    	logger.debug("Executing findTasksByStatusByProcessInstanceId");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTasksByStatusByProcessInstanceId(processInstanceId, status, page, pageSize);
    }

    public List<TaskSummary> findTasks(String userId, Integer page, Integer pageSize){
    	logger.debug("Executing findTasks");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTasks(userId, page, pageSize);
    }

    public List<TaskEventInstance> findTaskEvents(Long taskId, Integer page, Integer pageSize){
    	logger.debug("Executing findTaskEvents");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTaskEvents(taskId, page, pageSize);
    }

    public List<TaskSummary> findTasksByVariable(String userId, String variableName, List<String> status,
    		Integer page, Integer pageSize){
    	logger.debug("Executing findTasksByVariable");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTasksByVariable(userId, variableName, status, page, pageSize);
    }

    public List<TaskSummary> findTasksByVariableAndValue(String userId, String variableName, String variableValue,
    		List<String> status, Integer page, Integer pageSize){
    	logger.debug("Executing findTasksByVariableAndValue");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTasksByVariableAndValue(userId, variableName, variableValue, status, page, pageSize);
    }

    public List<TaskSummary> findTasksAssignedAsBusinessAdministratorWithSort(String userId, Integer page, Integer pageSize,
    		String sort, boolean sortOrder){
    	logger.debug("Executing findTasksAssignedAsBusinessAdministratorWithSort");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTasksAssignedAsBusinessAdministrator(userId, page, pageSize, sort, sortOrder);
    }

    public List<TaskSummary> findTasksAssignedAsBusinessAdministratorWithStatusWithSort(String userId, List<String> status, Integer page,
    		Integer pageSize, String sort, boolean sortOrder){
    	logger.debug("Executing findTasksAssignedAsBusinessAdministratorWithStatusWithSort");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTasksAssignedAsBusinessAdministrator(userId, status, page, pageSize, sort, sortOrder);
    }

    public List<TaskSummary> findTasksAssignedAsPotentialOwnerWithSort(String userId, Integer page, Integer pageSize, String sort,
    		boolean sortOrder){
    	logger.debug("Executing findTasksAssignedAsPotentialOwnerWithSort");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTasksAssignedAsPotentialOwner(userId, page, pageSize, sort, sortOrder);
    }

    public List<TaskSummary> findTasksAssignedAsPotentialOwnerWithStatus(String userId, List<String> status, Integer page,
    		Integer pageSize, String sort, boolean sortOrder){
    	logger.debug("Executing findTasksAssignedAsPotentialOwnerWithStatus");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTasksAssignedAsPotentialOwner(userId, status, page, pageSize, sort, sortOrder);
    }

    public List<TaskSummary> findTasksAssignedAsPotentialOwnerWithGroupsWithSort(String userId, List<String> groups, List<String> status,
    		Integer page, Integer pageSize, String sort, boolean sortOrder){
    	logger.debug("Executing findTasksAssignedAsPotentialOwnerWithGroupsWithSort");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTasksAssignedAsPotentialOwner(userId, groups, status, page, pageSize, sort, sortOrder);
    }

    public List<TaskSummary> findTasksOwnedWithSort(String userId, Integer page, Integer pageSize, String sort, boolean sortOrder){
    	logger.debug("Executing findTasksOwnedWithSort");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTasksOwned(userId, page, pageSize, sort, sortOrder);
    }

    public List<TaskSummary> findTasksOwnedWithStatus(String userId, List<String> status, Integer page, Integer pageSize,
    		String sort, boolean sortOrder){
    	logger.debug("Executing findTasksOwnedWithStatus");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTasksOwned(userId, status, page, pageSize);
    }

    public List<TaskSummary> findTasksByStatusByProcessInstanceIdWithSort(Long processInstanceId, List<String> status,
    		Integer page, Integer pageSize, String sort, boolean sortOrder){
    	logger.debug("Executing findTasksByStatusByProcessInstanceIdWithSort");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTasksByStatusByProcessInstanceId(processInstanceId, status, page, pageSize, sort, sortOrder);
    }

    public List<TaskSummary> findTasksWithSort(String userId, Integer page, Integer pageSize, String sort, boolean sortOrder){
    	logger.debug("Executing findTasksWithSort");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTasks(userId, page, pageSize, sort, sortOrder);
    }

    public List<TaskEventInstance> findTaskEventsWithSort(Long taskId, Integer page, Integer pageSize, String sort, boolean sortOrder){
    	logger.debug("Executing findTaskEventsWithSort");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTaskEvents(taskId, page, pageSize, sort, sortOrder);
    }

    public List<TaskSummary> findTasksByVariableWithSort(String userId, String variableName, List<String> status,
    		Integer page, Integer pageSize, String sort, boolean sortOrder){
    	logger.debug("Executing findTasksByVariableWithSort");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTasksByVariable(userId, variableName, status, page, pageSize, sort, sortOrder);
    }

    public List<TaskSummary> findTasksByVariableAndValueWithSort(String userId, String variableName, String variableValue,
    		List<String> status, Integer page, Integer pageSize, String sort, boolean sortOrder){
    	logger.debug("Executing findTasksByVariableAndValueWithSort");
    	return getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.findTasksByVariableAndValue(userId, variableName, variableValue, status, page, pageSize, sort, sortOrder);
    }
    
    public void claimStartCompleteHumanTask(Long taskId, String userId, Map<String, Object> data)
    {
    	logger.debug("Entering claimStartCompleteHumanTask");
    	UserTaskServicesClient taskClient = getKieServicesClient().getServicesClient(UserTaskServicesClient.class);
    	taskClient.claimTask(getContainerId(), taskId, userId);
    	taskClient.startTask(getContainerId(), taskId, userId);
    	taskClient.completeTask(getContainerId(), taskId, userId, data);
    	logger.debug("Leaving claimStartCompleteHumanTask");
    }
    
    public Map<String, Object> getInputData(Long taskId)
    {
    	logger.debug("Entering getInputData");
    	UserTaskServicesClient taskClient = getKieServicesClient().getServicesClient(UserTaskServicesClient.class);
    	TaskInstance taskInstance = taskClient.findTaskById(taskId);
    	logger.debug("Leaving getInputData");
    	return taskInstance.getInputData();
    }

    public Map<String, Object> getOutputData(Long taskId)
    {
    	logger.debug("Entering getOutputData");
    	UserTaskServicesClient taskClient = getKieServicesClient().getServicesClient(UserTaskServicesClient.class);
    	TaskInstance taskInstance = taskClient.findTaskById(taskId);
    	logger.debug("Leaving getOutputData");
    	return taskInstance.getOutputData();
    }
}
