package org.wildfly.bpms.frontend.api.console;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
import org.wildfly.bpms.frontend.api.BpmsUserTaskServices;
import org.wildfly.bpms.frontend.exception.BpmsFrontendException;
import org.wildfly.bpms.frontend.util.FrontendUtil;

public class BpmsUserTaskConsole extends BpmsUserTaskServices {

	private static Logger logger = LoggerFactory.getLogger(BpmsUserTaskConsole.class);

	public BpmsUserTaskConsole(String containerId, KieServicesClient client) {
		super(containerId, client);
	}

	public String activateTaskConsole(String taskId, String userId) {
		logger.debug("Executing activateTask");
		Long taskIdLong = Long.valueOf(taskId);
		getKieServicesClient().getServicesClient(UserTaskServicesClient.class).activateTask(getContainerId(),
				taskIdLong, userId);
		return String.format("Calling API to activate task console with task ID %s%n", taskId);
	}

	public String claimTaskConsole(String taskId, String userId) {
		logger.debug("Executing claimTask");
		getKieServicesClient().getServicesClient(UserTaskServicesClient.class).claimTask(getContainerId(),
				Long.valueOf(taskId), userId);
		return String.format("Calling API for to claim task ID %s%n", taskId);
	}

	public String completeTaskConsole(String taskId, String userId, String params) throws BpmsFrontendException {
		logger.debug("Executing completeTask");

		Map<String, Object> paramsMap = FrontendUtil.parseStringToMapStringObject(params, ",", "=");

		getKieServicesClient().getServicesClient(UserTaskServicesClient.class).completeTask(getContainerId(),
				Long.valueOf(taskId), userId, paramsMap);

		return String.format("Calling API to complete task ID %s%n", taskId);
	}

	public String completeAutoProgressConsole(String taskId, String userId, String params) throws BpmsFrontendException {
		logger.debug("Executing completeAutoProgress");

		Map<String, Object> paramsMap = FrontendUtil.parseStringToMapStringObject(params, ",", "=");

		getKieServicesClient().getServicesClient(UserTaskServicesClient.class).completeAutoProgress(getContainerId(),
				Long.valueOf(taskId), userId, paramsMap);

		return String.format("Calling API to complete task ID %s%n", taskId);
	}

	public String delegateTaskConsole(String taskId, String userId, String targetUserId) {
		logger.debug("Executing delegateTask");
		getKieServicesClient().getServicesClient(UserTaskServicesClient.class).delegateTask(getContainerId(),
				Long.valueOf(taskId), userId, targetUserId);
		return String.format("Calling API to delegate task ID %s to user %s%n", taskId, targetUserId);
	}

	public String exitTaskConsole(String taskId, String userId) {
		logger.debug("Executing exitTask");
		getKieServicesClient().getServicesClient(UserTaskServicesClient.class).exitTask(getContainerId(),
				Long.valueOf(taskId), userId);
		return String.format("Calling API to exit task ID %s%n", taskId);
	}

	public String failTaskConsole(String taskId, String userId, String params) throws BpmsFrontendException {
		logger.debug("Executing failTask");

		Map<String, Object> paramsMap = FrontendUtil.parseStringToMapStringObject(params, ",", "=");

		getKieServicesClient().getServicesClient(UserTaskServicesClient.class).failTask(getContainerId(),
				Long.valueOf(taskId), userId, paramsMap);
		return String.format("Calling API to fail task ID %s%n", taskId);
	}

	public String forwardTaskConsole(String taskId, String userId, String targetEntityId) {
		logger.debug("Executing forwardTask");
		getKieServicesClient().getServicesClient(UserTaskServicesClient.class).forwardTask(getContainerId(),
				Long.valueOf(taskId), userId, targetEntityId);
		return String.format("Calling API to forward task ID %s to entity %s%n", taskId, targetEntityId);
	}

	public String releaseTaskConsole(String taskId, String userId) {
		logger.debug("Executing releaseTask");
		getKieServicesClient().getServicesClient(UserTaskServicesClient.class).releaseTask(getContainerId(),
				Long.valueOf(taskId), userId);
		return String.format("Calling API to release task ID %s%n", taskId);
	}

	public String resumeTaskConsole(String taskId, String userId) {
		logger.debug("Executing resumeTask");
		getKieServicesClient().getServicesClient(UserTaskServicesClient.class).resumeTask(getContainerId(),
				Long.valueOf(taskId), userId);

		return String.format("Calling API to resume task ID %s%n", taskId);
	}

	public String skipTaskConsole(String taskId, String userId) {
		logger.debug("Executing skipTask");
		getKieServicesClient().getServicesClient(UserTaskServicesClient.class).skipTask(getContainerId(),
				Long.valueOf(taskId), userId);

		return String.format("Calling API to skip task ID %s%n", taskId);
	}

	public String startTaskConsole(String taskId, String userId) {
		logger.debug("Executing startTask");
		getKieServicesClient().getServicesClient(UserTaskServicesClient.class).startTask(getContainerId(),
				Long.valueOf(taskId), userId);

		return String.format("Calling API to start task ID %s%n", taskId);
	}

	public String stopTaskConsole(String taskId, String userId) {
		logger.debug("Executing stopTask");
		getKieServicesClient().getServicesClient(UserTaskServicesClient.class).stopTask(getContainerId(),
				Long.valueOf(taskId), userId);

		return String.format("Calling API to stop task ID %s%n", taskId);
	}

	public String suspendTaskConsole(String taskId, String userId) {
		logger.debug("Executing suspendTask");
		getKieServicesClient().getServicesClient(UserTaskServicesClient.class).suspendTask(getContainerId(),
				Long.valueOf(taskId), userId);

		return String.format("Calling API to suspend task ID %s%n", taskId);
	}

	public String nominateTaskConsole(String taskId, String userId, String potentialOwners) {
		logger.debug("Executing nominateTask");

		String[] potentialOwnerStrings = potentialOwners.split(",");
		List<String> potentialOwnerList = Arrays.asList(potentialOwnerStrings);

		getKieServicesClient().getServicesClient(UserTaskServicesClient.class).nominateTask(getContainerId(),
				Long.valueOf(taskId), userId, potentialOwnerList);

		return String.format("Calling API to nominate %s for task ID %s%n", potentialOwners, taskId);
	}

	public String setTaskPriorityConsole(String taskId, String priority) {
		logger.debug("Executing setTaskPriority");
		getKieServicesClient().getServicesClient(UserTaskServicesClient.class).setTaskPriority(getContainerId(),
				Long.valueOf(taskId), Integer.valueOf(priority));

		return String.format("Calling API to set priority %s for task ID %s%n", priority, taskId);
	}

	public String setTaskExpirationDateConsole(String taskId, String date) throws BpmsFrontendException {
		logger.debug("Executing setTaskExpirationDate");

		Date expirationDate = FrontendUtil.parseStringToDate(date);

		getKieServicesClient().getServicesClient(UserTaskServicesClient.class).setTaskExpirationDate(getContainerId(),
				Long.valueOf(taskId), expirationDate);

		return String.format("Calling API to set expiration date %s for task ID %s%n", date, taskId);
	}

	public String setTaskSkipableConsole(String taskId, String skipable) {
		logger.debug("Executing setTaskSkipable");
		getKieServicesClient().getServicesClient(UserTaskServicesClient.class).setTaskSkipable(getContainerId(),
				Long.valueOf(taskId), Boolean.valueOf(skipable));

		return String.format("Calling API to mark task ID %s as skippable%n", taskId);
	}

	public String setTaskNameConsole(String taskId, String name) {
		logger.debug("Executing setTaskName");
		getKieServicesClient().getServicesClient(UserTaskServicesClient.class).setTaskName(getContainerId(),
				Long.valueOf(taskId), name);

		return String.format("Calling API to set name %s for task ID %s%n", name, taskId);
	}

	public String setTaskDescriptionConsole(String taskId, String description) {
		logger.debug("Executing setTaskDescription");
		getKieServicesClient().getServicesClient(UserTaskServicesClient.class).setTaskDescription(getContainerId(),
				Long.valueOf(taskId), description);

		return String.format("Calling API to set description %s for task ID %s%n", description, taskId);
	}

	public String saveTaskContentConsole(String taskId, String values) throws BpmsFrontendException {
		logger.debug("Executing saveTaskContent");

		Map<String, Object> valuesMap = FrontendUtil.parseStringToMapStringObject(values, ",", "=");

		Long result = getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
				.saveTaskContent(getContainerId(), Long.valueOf(taskId), valuesMap);

		return String.format("Saved task content for task ID %s with result %s", taskId, String.valueOf(result));
	}

	public String getTaskOutputContentByTaskIdConsole(String taskId) {
		logger.debug("Executing getTaskOutputContentByTaskId");
		String result = "=====%nTask Output Content%n";
		Map<String, Object> resultMap = getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
				.getTaskOutputContentByTaskId(getContainerId(), Long.valueOf(taskId));

		for (String key : resultMap.keySet()) {
			result = result.concat(String.format("Key: %s%nValue: %s%n", key, resultMap.get(key).toString()));
		}

		return result;
	}

	public String getTaskInputContentByTaskIdConsole(String taskId) {
		logger.debug("Executing getTaskInputContentByTaskId");
		String result = "=====%nTask Input Content%n";
		Map<String, Object> resultMap = getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
				.getTaskInputContentByTaskId(getContainerId(), Long.valueOf(taskId));

		for (String key : resultMap.keySet()) {
			result = result.concat(String.format("Key: %s%nValue: %s%n", key, resultMap.get(key).toString()));
		}

		return result;
	}

	public String deleteTaskContentConsole(String taskId, String contentId) {
		logger.debug("Executing deleteTaskContent");
		getKieServicesClient().getServicesClient(UserTaskServicesClient.class).deleteTaskContent(getContainerId(),
				Long.valueOf(taskId), Long.valueOf(contentId));

		return String.format("Calling API to delete content from task ID %s%n", taskId);
	}

	public String addTaskCommentConsole(String taskId, String text, String addedBy, String addedOn)
			throws BpmsFrontendException {
		logger.debug("Executing addTaskComment");

		Date addedOnDate = FrontendUtil.parseStringToDate(addedOn);

		Long callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
				.addTaskComment(getContainerId(), Long.valueOf(taskId), text, addedBy, addedOnDate);

		return String.format("Added task comment to task ID %s with result %s%n", taskId, String.valueOf(callResult));
	}

	public String deleteTaskCommentConsole(String taskId, String commentId) {
		logger.debug("Executing deleteTaskComment");
		getKieServicesClient().getServicesClient(UserTaskServicesClient.class).deleteTaskComment(getContainerId(),
				Long.valueOf(taskId), Long.valueOf(commentId));

		return String.format("Calling API to delete task comment %s from task ID %s%n", commentId, taskId);
	}

	public String getTaskCommentsByTaskIdConsole(String taskId) {
		logger.debug("Executing getTaskCommentsByTaskId");
		List<TaskComment> resultMap = getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
				.getTaskCommentsByTaskId(getContainerId(), Long.valueOf(taskId));

		return parseTaskCommentListToString(resultMap);
	}

	public String getTaskCommentByIdConsole(String taskId, String commentId) {
		logger.debug("Executing getTaskCommentById");
		TaskComment comment = getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
				.getTaskCommentById(getContainerId(), Long.valueOf(taskId), Long.valueOf(commentId));

		return parseTaskCommentToString(comment);
	}

	public String addTaskAttachmentConsole(String taskId, String userId, String name, String attachment)
			throws BpmsFrontendException {
		logger.debug("Executing addTaskAttachment");

		Object attachObj = FrontendUtil.createObjFromClassName(attachment);

		Long callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
				.addTaskAttachment(getContainerId(), Long.valueOf(taskId), userId, name, attachObj);

		return String.format("Added task attachment to task ID %s with result %s%n", taskId,
				String.valueOf(callResult));
	}

	public String deleteTaskAttachmentConsole(String taskId, String attachmentId) {
		logger.debug("Executing deleteTaskAttachment");
		getKieServicesClient().getServicesClient(UserTaskServicesClient.class).deleteTaskAttachment(getContainerId(),
				Long.valueOf(taskId), Long.valueOf(attachmentId));

		return String.format("Calling API to delete task attachment %s from task ID %s%n", attachmentId, taskId);
	}

	public String getTaskAttachmentByIdConsole(String taskId, String attachmentId) {
		logger.debug("Executing getTaskAttachmentById");
		TaskAttachment callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
				.getTaskAttachmentById(getContainerId(), Long.valueOf(taskId), Long.valueOf(attachmentId));

		return parseTaskAttachmentToString(callResult);
	}

	public String getTaskAttachmentContentByIdConsole(String taskId, String attachmentId) {
		logger.debug("Executing getTaskAttachmentContentById");
		Object callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
				.getTaskAttachmentContentById(getContainerId(), Long.valueOf(taskId), Long.valueOf(attachmentId));

		return String.format("=====%nTask Attachment for task ID %s:%n%s%n", taskId, callResult);
	}

	public String getTaskAttachmentsByTaskIdConsole(String taskId) {
		logger.debug("Executing getTaskAttachmentsByTaskId");
		List<TaskAttachment> callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
				.getTaskAttachmentsByTaskId(getContainerId(), Long.valueOf(taskId));

		return parseTaskAttachmentListToString(callResult);
	}

	public String getTaskInstanceConsole(String taskId) {
		logger.debug("Executing getTaskInstance");
		TaskInstance callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class).getTaskInstance(getContainerId(),
				Long.valueOf(taskId));
		
		return parseTaskInstanceToString(callResult);
	}

	public String getTaskInstanceWithInputOutputConsole(String taskId, String withInputs, String withOutputs,
			String withAssignments) {
		logger.debug("Executing getTaskInstanceWithInputOutput");
		TaskInstance callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class).getTaskInstance(getContainerId(),
				Long.valueOf(taskId), Boolean.valueOf(withInputs), Boolean.valueOf(withOutputs),
				Boolean.valueOf(withAssignments));
		
		return parseTaskInstanceToString(callResult);
	}

	public String findTaskByWorkItemIdConsole(String workItemId) {
		logger.debug("Executing findTaskByWorkItemId");
		TaskInstance callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
				.findTaskByWorkItemId(Long.valueOf(workItemId));
		
		return parseTaskInstanceToString(callResult);
	}

	public String findTaskByIdConsole(String taskId) {
		logger.debug("Executing findTaskById");
		TaskInstance callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
				.findTaskById(Long.valueOf(taskId));
		
		return parseTaskInstanceToString(callResult);
	}

	public String findTasksAssignedAsBusinessAdministratorConsole(String userId, String page, String pageSize) {
		logger.debug("Executing findTasksAssignedAsBusinessAdministrator");
		List<TaskSummary> callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
				.findTasksAssignedAsBusinessAdministrator(userId, Integer.valueOf(page), Integer.valueOf(pageSize));
		
		return parseTaskSummaryListToString(callResult);
	}

	public String findTasksAssignedAsBusinessAdministratorWithStatusConsole(String userId, String status,
			String page, String pageSize) {
		logger.debug("Executing findTasksAssignedAsBusinessAdministratorWithStatus");

		String[] statusStrings = status.split(",");
		List<String> statusList = Arrays.asList(statusStrings);

		List<TaskSummary> callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
				.findTasksAssignedAsBusinessAdministrator(userId, statusList, Integer.valueOf(page),
						Integer.valueOf(pageSize));
		
		return parseTaskSummaryListToString(callResult);
	}

	public String findTasksAssignedAsPotentialOwnerConsole(String userId, String page, String pageSize) {
		logger.debug("Executing findTasksAssignedAsPotentialOwner");
		List<TaskSummary> callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
				.findTasksAssignedAsPotentialOwner(userId, Integer.valueOf(page), Integer.valueOf(pageSize));
		
		return parseTaskSummaryListToString(callResult);
	}

	public String findTasksAssignedAsPotentialOwnerWithStatusConsole(String userId, String status, String page,
			String pageSize) {
		logger.debug("Executing findTasksAssignedAsPotentialOwnerWithStatus");
		List<TaskSummary> callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class).findTasksAssignedAsPotentialOwner(
				userId, FrontendUtil.parseStringToList(status, ","), Integer.valueOf(page), Integer.valueOf(pageSize));
		
		return parseTaskSummaryListToString(callResult);
	}

	public String findTasksAssignedAsPotentialOwnerWithGroupsConsole(String userId, String groups, String status,
			String page, String pageSize) {
		logger.debug("Executing findTasksAssignedAsPotentialOwnerWithGroups");
		List<TaskSummary> callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class).findTasksAssignedAsPotentialOwner(
				userId, FrontendUtil.parseStringToList(groups, ","), FrontendUtil.parseStringToList(status, ","),
				Integer.valueOf(page), Integer.valueOf(pageSize));
		
		return parseTaskSummaryListToString(callResult);
	}

	public String findTasksOwnedConsole(String userId, String page, String pageSize) {
		logger.debug("Executing findTasksOwned");
		List<TaskSummary> callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class).findTasksOwned(userId,
				Integer.valueOf(page), Integer.valueOf(pageSize));
		
		return parseTaskSummaryListToString(callResult);
	}

	public String findTasksOwnedWithStatusesConsole(String userId, String status, String page, String pageSize) {
		logger.debug("Executing findTasksOwned");
		List<TaskSummary> callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class).findTasksOwned(userId,
				FrontendUtil.parseStringToList(status, ","), Integer.valueOf(page), Integer.valueOf(pageSize));
		
		return parseTaskSummaryListToString(callResult);
	}

	public String findTasksByStatusByProcessInstanceIdConsole(String processInstanceId, String status, String page,
			String pageSize) {
		logger.debug("Executing findTasksByStatusByProcessInstanceId");
		List<TaskSummary> callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
				.findTasksByStatusByProcessInstanceId(Long.valueOf(processInstanceId),
						FrontendUtil.parseStringToList(status, ","), Integer.valueOf(page), Integer.valueOf(pageSize));
		
		return parseTaskSummaryListToString(callResult);
	}

	public String findTasksConsole(String userId, String page, String pageSize) {
		logger.debug("Executing findTasks");
		List<TaskSummary> callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class).findTasks(userId,
				Integer.valueOf(page), Integer.valueOf(pageSize));
		
		return parseTaskSummaryListToString(callResult);
	}

	public String findTaskEventsConsole(String taskId, String page, String pageSize) {
		logger.debug("Executing findTaskEvents");
		List<TaskEventInstance> callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
				.findTaskEvents(Long.valueOf(taskId), Integer.valueOf(page), Integer.valueOf(pageSize));
		
		return parseTaskEventInstanceListToString(callResult);
	}

	public String findTasksByVariableConsole(String userId, String variableName, String status, String page,
			String pageSize) {
		logger.debug("Executing findTasksByVariable");
		List<TaskSummary> callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class).findTasksByVariable(userId,
				variableName, FrontendUtil.parseStringToList(status, ","), Integer.valueOf(page),
				Integer.valueOf(pageSize));
		
		return parseTaskSummaryListToString(callResult);
	}

	public String findTasksByVariableAndValueConsole(String userId, String variableName, String variableValue,
			String status, String page, String pageSize) {
		logger.debug("Executing findTasksByVariableAndValue");
		List<TaskSummary> callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class).findTasksByVariableAndValue(
				userId, variableName, variableValue, FrontendUtil.parseStringToList(status, ","), Integer.valueOf(page),
				Integer.valueOf(pageSize));
		
		return parseTaskSummaryListToString(callResult);
	}

	public String findTasksAssignedAsBusinessAdministratorWithSortConsole(String userId, String page,
			String pageSize, String sort, String sortOrder) {
		logger.debug("Executing findTasksAssignedAsBusinessAdministratorWithSort");
		List<TaskSummary> callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
				.findTasksAssignedAsBusinessAdministrator(userId, Integer.valueOf(page), Integer.valueOf(pageSize),
						sort, Boolean.valueOf(sortOrder));
		
		return parseTaskSummaryListToString(callResult);
	}

	public String findTasksAssignedAsBusinessAdministratorWithStatusWithSortConsole(String userId, String status,
			String page, String pageSize, String sort, String sortOrder) {
		logger.debug("Executing findTasksAssignedAsBusinessAdministratorWithStatusWithSort");
		List<TaskSummary> callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
				.findTasksAssignedAsBusinessAdministrator(userId, FrontendUtil.parseStringToList(status, ","),
						Integer.valueOf(page), Integer.valueOf(pageSize), sort, Boolean.valueOf(sortOrder));
		
		return parseTaskSummaryListToString(callResult);
	}

	public String findTasksAssignedAsPotentialOwnerWithSortConsole(String userId, String page, String pageSize,
			String sort, String sortOrder) {
		logger.debug("Executing findTasksAssignedAsPotentialOwnerWithSort");
		List<TaskSummary> callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class).findTasksAssignedAsPotentialOwner(
				userId, Integer.valueOf(page), Integer.valueOf(pageSize), sort, Boolean.valueOf(sortOrder));
		
		return parseTaskSummaryListToString(callResult);
	}

	public String findTasksAssignedAsPotentialOwnerWithStatusConsole(String userId, String status, String page,
			String pageSize, String sort, String sortOrder) {
		logger.debug("Executing findTasksAssignedAsPotentialOwnerWithStatus");
		List<TaskSummary> callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class).findTasksAssignedAsPotentialOwner(
				userId, FrontendUtil.parseStringToList(status, ","), Integer.valueOf(page), Integer.valueOf(pageSize),
				sort, Boolean.valueOf(sortOrder));
		
		return parseTaskSummaryListToString(callResult);
	}

	public String findTasksAssignedAsPotentialOwnerWithGroupsWithSortConsole(String userId, String groups,
			String status, String page, String pageSize, String sort, String sortOrder) {
		logger.debug("Executing findTasksAssignedAsPotentialOwnerWithGroupsWithSort");
		List<TaskSummary> callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class).findTasksAssignedAsPotentialOwner(
				userId, FrontendUtil.parseStringToList(groups, ","), FrontendUtil.parseStringToList(status, ","),
				Integer.valueOf(page), Integer.valueOf(pageSize), sort, Boolean.valueOf(sortOrder));
		
		return parseTaskSummaryListToString(callResult);
	}

	public String findTasksOwnedWithSortConsole(String userId, String page, String pageSize, String sort,
			String sortOrder) {
		logger.debug("Executing findTasksOwnedWithSort");
		List<TaskSummary> callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class).findTasksOwned(userId,
				Integer.valueOf(page), Integer.valueOf(pageSize), sort, Boolean.valueOf(sortOrder));
		
		return parseTaskSummaryListToString(callResult);
	}

	public String findTasksOwnedWithStatusConsole(String userId, String status, String page, String pageSize,
			String sort, String sortOrder) {
		logger.debug("Executing findTasksOwnedWithStatus");
		List<TaskSummary> callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class).findTasksOwned(userId,
				FrontendUtil.parseStringToList(status, ","), Integer.valueOf(page), Integer.valueOf(pageSize));
		
		return parseTaskSummaryListToString(callResult);
	}

	public String findTasksByStatusByProcessInstanceIdWithSortConsole(String processInstanceId, String status,
			String page, String pageSize, String sort, String sortOrder) {
		logger.debug("Executing findTasksByStatusByProcessInstanceIdWithSort");
		List<TaskSummary> callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
				.findTasksByStatusByProcessInstanceId(Long.valueOf(processInstanceId),
						FrontendUtil.parseStringToList(status, ","), Integer.valueOf(page), Integer.valueOf(pageSize),
						sort, Boolean.valueOf(sortOrder));
		
		return parseTaskSummaryListToString(callResult);
	}

	public String findTasksWithSortConsole(String userId, String page, String pageSize, String sort,
			String sortOrder) {
		logger.debug("Executing findTasksWithSort");
		List<TaskSummary> callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class).findTasks(userId,
				Integer.valueOf(page), Integer.valueOf(pageSize), sort, Boolean.valueOf(sortOrder));
		
		return parseTaskSummaryListToString(callResult);
	}

	public String findTaskEventsWithSortConsole(String taskId, String page, String pageSize, String sort,
			String sortOrder) {
		logger.debug("Executing findTaskEventsWithSort");
		List<TaskEventInstance> callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class).findTaskEvents(
				Long.valueOf(taskId), Integer.valueOf(page), Integer.valueOf(pageSize), sort,
				Boolean.valueOf(sortOrder));
		
		return parseTaskEventInstanceListToString(callResult);
	}

	public String findTasksByVariableWithSortConsole(String userId, String variableName, String status, String page,
			String pageSize, String sort, String sortOrder) {
		logger.debug("Executing findTasksByVariableWithSort");
		List<TaskSummary> callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class).findTasksByVariable(userId,
				variableName, FrontendUtil.parseStringToList(status, ","), Integer.valueOf(page),
				Integer.valueOf(pageSize), sort, Boolean.valueOf(sortOrder));
		
		return parseTaskSummaryListToString(callResult);
	}

	public String findTasksByVariableAndValueWithSortConsole(String userId, String variableName,
			String variableValue, String status, String page, String pageSize, String sort, String sortOrder) {
		logger.debug("Executing findTasksByVariableAndValueWithSort");
		List<TaskSummary> callResult = getKieServicesClient().getServicesClient(UserTaskServicesClient.class).findTasksByVariableAndValue(
				userId, variableName, variableValue, FrontendUtil.parseStringToList(status, ","), Integer.valueOf(page),
				Integer.valueOf(pageSize), sort, Boolean.valueOf(sortOrder));
		
		return parseTaskSummaryListToString(callResult);
	}

	public String claimStartCompleteHumanTaskConsole(String taskId, String userId, String data) throws BpmsFrontendException {
		logger.debug("Entering claimStartCompleteHumanTask");

		Map<String, Object> dataMap = FrontendUtil.parseStringToMapStringObject(data, ",", "=");

		UserTaskServicesClient taskClient = getKieServicesClient().getServicesClient(UserTaskServicesClient.class);
		taskClient.claimTask(getContainerId(), Long.valueOf(taskId), userId);
		taskClient.startTask(getContainerId(), Long.valueOf(taskId), userId);
		taskClient.completeTask(getContainerId(), Long.valueOf(taskId), userId, dataMap);
		logger.debug("Leaving claimStartCompleteHumanTask");

		return String.format("Calling API to claim, start, and complete task ID %s%n", taskId);
	}

	public String getInputDataConsole(String taskId) {
		logger.debug("Entering getInputData");
		String result = "=====%n";

		UserTaskServicesClient taskClient = getKieServicesClient().getServicesClient(UserTaskServicesClient.class);
		TaskInstance taskInstance = taskClient.findTaskById(Long.valueOf(taskId));
		Map<String, Object> callResult = taskInstance.getInputData();

		for (String key : callResult.keySet()) {
			result = result.concat(String.format("Key: %s%nValue: %s%n", key, callResult.get(key)));
		}

		logger.debug("Leaving getInputData");
		return result;
	}

	public String getOutputDataConsole(String taskId) {
		logger.debug("Entering getOutputData");
		String result = "";

		UserTaskServicesClient taskClient = getKieServicesClient().getServicesClient(UserTaskServicesClient.class);
		TaskInstance taskInstance = taskClient.findTaskById(Long.valueOf(taskId));
		Map<String, Object> callResult = taskInstance.getOutputData();

		for (String key : callResult.keySet()) {
			result = result.concat(String.format("Key: %s%nValue: %s%n", key, callResult.get(key)));
		}

		logger.debug("Leaving getOutputData");
		return result;
	}

	private String parseTaskCommentListToString(List<TaskComment> commentList) {
		logger.debug("Executing parseTaskCommentListToString");
		String result = "";

		for (TaskComment comment : commentList) {
			result = result.concat(parseTaskCommentToString(comment));
		}

		return result;
	}

	private String parseTaskCommentToString(TaskComment comment) {
		logger.debug("Executing parseTaskCommentToString");
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		return String.format("=====%nVariable Name: %s%nVariable Value: %s%nProcess Instance ID: %s%n", comment.getId(),
				df.format(comment.getAddedAt()), comment.getAddedBy(), comment.getText());
	}

	private String parseTaskAttachmentListToString(List<TaskAttachment> attachmentList) {
		logger.debug("Executing parseTaskAttachmentListToString");
		String result = "";

		for (TaskAttachment comment : attachmentList) {
			result = result.concat(parseTaskAttachmentToString(comment));
		}

		return result;
	}

	private String parseTaskAttachmentToString(TaskAttachment attachment) {
		logger.debug("Entering parseTaskAttachmentToString");
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		return String.format(
				"=====%nAttachment ID: %s%nAttachment Name: %s%nAttachment Added By: "
						+ "%s%nAdded At: %s%nContent Type: %s%nSize: %s%n",
				String.valueOf(attachment.getId()), attachment.getName(), attachment.getAddedBy(),
				df.format(attachment.getAddedAt()), attachment.getContentType(), String.valueOf(attachment.getSize()));
	}

	private String parseTaskInstanceToString(TaskInstance instance) {
		logger.debug("Entering parseTaskInstanceToString");
		return String.format(
				"=====%nTask ID: %s%nTask Name: %s%nProcess Instance ID: %s%nParent Process Instance ID: "
				+ "%s%nContainer ID: %s%nActual Owner: %s%n",
				String.valueOf(instance.getId()), instance.getName(), String.valueOf(instance.getProcessInstanceId()),
				String.valueOf(instance.getParentId()), instance.getContainerId(), instance.getActualOwner());
	}
	
	private String parseTaskSummaryListToString(List<TaskSummary> summaryList) {
		logger.debug("Executing parseTaskSummaryListToString");
		String result = "";

		for (TaskSummary summary : summaryList) {
			result = result.concat(parseTaskSummaryToString(summary));
		}

		return result;
	}
	
	private String parseTaskSummaryToString(TaskSummary summary) {
		logger.debug("Entering parseTaskSummaryToString");
		return String.format(
				"=====%nTask ID: %s%nTask Name: %s%nProcess Instance ID: %s%nParent Process Instance ID: "
				+ "%s%nContainer ID: %s%nActual Owner: %s%n",
				String.valueOf(summary.getId()), summary.getName(), String.valueOf(summary.getProcessInstanceId()),
				String.valueOf(summary.getParentId()), summary.getContainerId(), summary.getActualOwner());
	}
	
	private String parseTaskEventInstanceListToString(List<TaskEventInstance> eventList) {
		logger.debug("Executing parseTaskEventInstanceListToString");
		String result = "";

		for (TaskEventInstance eventInstance : eventList) {
			result = result.concat(parseTaskEventInstanceToString(eventInstance));
		}

		return result;
	}
	
	private String parseTaskEventInstanceToString(TaskEventInstance eventInstance) {
		logger.debug("Entering parseTaskEventInstanceToString");
		return String.format(
				"=====%nTask ID: %s%nTask Type: %s%nProcess Instance ID: %s%n",
				String.valueOf(eventInstance.getId()), eventInstance.getType(), String.valueOf(eventInstance.getProcessInstanceId()));
	}
}
