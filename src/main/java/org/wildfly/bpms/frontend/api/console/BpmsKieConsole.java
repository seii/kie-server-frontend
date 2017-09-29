package org.wildfly.bpms.frontend.api.console;

import java.util.ArrayList;
import java.util.List;

import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.KieContainerResourceFilter;
import org.kie.server.api.model.KieContainerResourceList;
import org.kie.server.api.model.KieContainerStatusFilter;
import org.kie.server.api.model.KieScannerResource;
import org.kie.server.api.model.KieScannerStatus;
import org.kie.server.api.model.KieServerInfo;
import org.kie.server.api.model.ReleaseId;
import org.kie.server.api.model.ReleaseIdFilter;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.api.model.ServiceResponse.ResponseType;
import org.kie.server.client.KieServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.bpms.frontend.api.BpmsKieServices;

public class BpmsKieConsole extends BpmsKieServices {

	private static Logger logger = LoggerFactory.getLogger(BpmsKieConsole.class);

	public BpmsKieConsole(KieServicesClient client) {
		super(client);
	}

	public String getServerStatusConsole() {
		logger.debug("Executing getServerStatusConsole");
		KieServerInfo status = super.getKieServicesClient().getServerInfo().getResult();
		return String.format("=====%nName: %s%nLocation: %s%nServer ID: %s%nVersion: %s%nCapabilities: %s%n", status.getName(),
				status.getLocation(), status.getServerId(), status.getVersion(), status.getCapabilities());
	}

	public String destroyContainerConsole(String containerId) {
		logger.debug("Entered destroyContainer");
		String result;

		ServiceResponse<Void> responseDispose = getKieServicesClient().disposeContainer(containerId);
		if (responseDispose.getType() == ResponseType.FAILURE) {
			logger.error("Error disposing {}.\nMessage: {}", containerId, responseDispose.getMsg());
			result = String.format("An error occurred while attempting to destroy container %s.%nThe error was: %s",
							containerId, responseDispose.getMsg());
		} else {
			logger.debug("Leaving destroyContainer");
			result = String.format("Successfully destroyed container %s", containerId);
		}
		
		return result;
	}

	public String createContainerConsole(String containerId, String groupId, String artifactId,
			String versionId) {
		logger.debug("Entering createContainer");
		KieContainerResource newContainer = new KieContainerResource();
		ReleaseId releaseId = new ReleaseId();
		releaseId.setGroupId(groupId);
		releaseId.setArtifactId(artifactId);
		releaseId.setVersion(versionId);
		newContainer.setReleaseId(releaseId);
		ServiceResponse<KieContainerResource> createResponse = getKieServicesClient().createContainer(containerId,
				newContainer);

		if (createResponse.getType() == ResponseType.FAILURE) {
			logger.error("Error creating {}.\nMessage: {}", containerId, createResponse.getMsg());
			return null;
		} else {
			logger.debug("Leaving createContainer");
			return parseKieContainerResourceToString(createResponse.getResult());
		}
	}
	
	public String listContainersConsole() {
    	logger.debug("Executing getAllContainers");
    	KieContainerResourceList resourceList = getKieServicesClient().listContainers().getResult();
    	List<KieContainerResource> containerList = resourceList.getContainers();
    	
    	return parseKieContainerResourceListToString(containerList);
    }

    /**
     * List all containers that match a filter. The filter may sort by Maven GAV values, or by the container status.
     * @param groupId Maven group ID
     * @param artifactId Maven artifact ID
     * @param versionId Maven version ID
     * @param statuses Comma separated list of statuses. Acceptable values are any upper or lower case form of
     * 					the values CREATING, STARTED, FAILED, DISPOSING, or STOPPED
     * @return List of matching containers
     */
    public String listContainersByFilterConsole(String groupId, String artifactId, String versionId,
    		String ...statuses) {
    	logger.debug("Entering listContainersByFilter");
    	ReleaseIdFilter releaseFilter;
    	
    	//If any of the Maven GAV values is null, the filter will accept any GAV value
    	if(groupId == null || artifactId == null || versionId == null) {
    		releaseFilter = new ReleaseIdFilter(null, null, null);
    	}else {
    		releaseFilter = new ReleaseIdFilter(groupId, artifactId, versionId);
    	}
    	StringBuilder sBuilder = new StringBuilder();
    	for(String status : statuses) {
    		sBuilder.append(status);
    		sBuilder.append(",");
    	}
    	sBuilder.trimToSize();
    	
    	//Remove the final comma
    	sBuilder.deleteCharAt(sBuilder.length());
    	KieContainerStatusFilter statusFilter = KieContainerStatusFilter.parseFromNullableString(sBuilder.toString());
    	KieContainerResourceFilter resourceFilter = new KieContainerResourceFilter(releaseFilter, statusFilter);
    	KieContainerResourceList resourceList = getKieServicesClient().listContainers(resourceFilter).getResult();
    	List<KieContainerResource> containerList = resourceList.getContainers();
    	
    	logger.debug("Exiting listContainersByFilter");
    	return parseKieContainerResourceListToString(containerList);
    }

    public String getContainerInfoConsole(String containerId) {
    	logger.debug("Executing getContainerInfo");
    	
    	return parseKieContainerResourceToString(getKieServicesClient().getContainerInfo(containerId).getResult());
    }

    public String getScannerInfoConsole(String containerId) {
    	logger.debug("Executing getScannerInfo");
    	KieScannerResource resource = getKieServicesClient().getScannerInfo(containerId).getResult();
    	return String.format("=====%nScanner Status: %s%nScanner Poll Interval: %s%n", resource.getStatus(),
    			resource.getPollInterval());
    }

    /**
     * Update the KieScanner for a given container.
     * @param containerId ID of the KIE Container
     * @param interval Interval (in seconds) between scan attempts for a new Maven artifact
     * @param scannerStatus Status of KIE Scanner. Allowed values are UNKNOWN, CREATED, STARTED, SCANNING, STOPPED, or DISPOSED.
     * @return Result of the update
     */
    public String updateScannerConsole(String containerId, String interval, String scannerStatus) {
    	logger.debug("Entering updateScanner");
    	KieScannerResource scannerResource;
    	
    	if(interval != null && Long.valueOf(interval) != 0) {
    		scannerResource = new KieScannerResource(KieScannerStatus.valueOf(scannerStatus), Long.valueOf(interval));
    	}else {
    		scannerResource = new KieScannerResource(KieScannerStatus.valueOf(scannerStatus));
    	}
    	
    	KieScannerResource resource = getKieServicesClient().updateScanner(containerId, scannerResource).getResult();
    	
    	logger.debug("Leaving updateScanner");
    	return String.format("=====%nScanner Status: %s%nScanner Poll Interval: %s%n", resource.getStatus(),
    			resource.getPollInterval());
    }

    public String updateReleaseIdConsole(String containerId, String groupId, String artifactId, String versionId) {
    	logger.debug("Entering updateReleaseId");
    	ReleaseId releaseId = new ReleaseId(groupId, artifactId, versionId);
    	ReleaseId resultId = getKieServicesClient().updateReleaseId(containerId, releaseId).getResult();
    	logger.debug("Leaving updateReleaseId");
    	return String.format("=====%nUpdates To Container ID %s:%nGroup ID from %s to %s%nArtifact ID from "
    			+ "%s to %s%nVersion ID from %s to %s%n", groupId, resultId.getGroupId(), artifactId,
    			resultId.getArtifactId(), versionId, resultId.getVersion());
    }
    
    private String parseKieContainerResourceListToString(List<KieContainerResource> containerList) {
    	logger.debug("Entering parseContainerListToString");
    	String result = null;
    	
    	for(KieContainerResource resource : containerList) {
	    	if(result == null) {
				result = String.format("=====%n");
			}
			
			result = result.concat(parseKieContainerResourceToString(resource));
    	}
    	
    	logger.debug("Leaving parseContainerListToString");
    	
    	return result;
    }
    
    private String parseKieContainerResourceToString(KieContainerResource resource) {
    	logger.debug("Executing parseKieContainerResourceToString");
    	return String.format("Container ID: %s%nContainer GAV: "
				+ "%s%nContainer Status: %s%nScanner Status: %s%n",
				resource.getContainerId(), resource.getReleaseId().toExternalForm(), resource.getStatus(),
				resource.getScanner().getStatus());
    }
}
