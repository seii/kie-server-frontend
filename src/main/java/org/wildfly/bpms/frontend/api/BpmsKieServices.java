package org.wildfly.bpms.frontend.api;

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

public class BpmsKieServices {
	
	private static Logger logger = LoggerFactory.getLogger(BpmsKieServices.class);
	
	private KieServicesClient kieServicesClient;

	private BpmsKieServices() {
		// Empty constructor
	}
	
	public BpmsKieServices(KieServicesClient client) {
		kieServicesClient = client;
	}
	
	protected KieServicesClient getKieServicesClient() {
		return kieServicesClient;
	}
    
    public KieServerInfo getServerStatus() {
    	logger.debug("Executing getServerStatus");
    	return getKieServicesClient().getServerInfo().getResult();
    }
    
    public void destroyContainer(String containerId) {
    	logger.debug("Entered destroyContainer");
    	ServiceResponse<Void> responseDispose = getKieServicesClient().disposeContainer(containerId);
        if (responseDispose.getType() == ResponseType.FAILURE) {
            logger.error("Error disposing {}.\nMessage: {}", containerId, responseDispose.getMsg());
        }else {
        	logger.debug("Leaving destroyContainer");
        }
    }
    
    public KieContainerResource createContainer(String containerId, ReleaseId releaseId) {
    	logger.debug("Entering createContainer");
    	KieContainerResource newContainer = new KieContainerResource();
    	ServiceResponse<KieContainerResource> createResponse = getKieServicesClient().createContainer(containerId, newContainer);
    	
        if(createResponse.getType() == ResponseType.FAILURE) {
            logger.error("Error creating {}.\nMessage: {}", containerId, createResponse.getMsg());
            return null;
        }else {
        	logger.debug("Leaving createContainer");
        	return createResponse.getResult();
        }
    }
    
    public KieContainerResourceList listContainers() {
    	logger.debug("Executing getAllContainers");
    	return getKieServicesClient().listContainers().getResult();
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
    public KieContainerResourceList listContainersByFilter(String groupId, String artifactId, String versionId, String ...statuses) {
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
    	logger.debug("Exiting listContainersByFilter");
    	return getKieServicesClient().listContainers(resourceFilter).getResult();
    }

    public KieContainerResource getContainerInfo(String containerId) {
    	logger.debug("Executing getContainerInfo");
    	return getKieServicesClient().getContainerInfo(containerId).getResult();
    }

    public KieScannerResource getScannerInfo(String containerId) {
    	logger.debug("Executing getScannerInfo");
    	return getKieServicesClient().getScannerInfo(containerId).getResult();
    }

    /**
     * Update the KieScanner for a given container.
     * @param containerId ID of the KIE Container
     * @param interval Interval (in seconds) between scan attempts for a new Maven artifact
     * @param scannerStatus Status of KIE Scanner. Allowed values are UNKNOWN, CREATED, STARTED, SCANNING, STOPPED, or DISPOSED.
     * @return Result of the update
     */
    public KieScannerResource updateScanner(String containerId, Long interval, String scannerStatus) {
    	logger.debug("Entering updateScanner");
    	KieScannerResource resource;
    	
    	if(interval != null) {
    		resource = new KieScannerResource(KieScannerStatus.valueOf(scannerStatus), interval);
    	}else {
    		resource = new KieScannerResource(KieScannerStatus.valueOf(scannerStatus));
    	}
    	
    	logger.debug("Leaving updateScanner");
    	return getKieServicesClient().updateScanner(containerId, resource).getResult();
    }

    public ReleaseId updateReleaseId(String containerId, String groupId, String artifactId, String versionId) {
    	logger.debug("Entering updateReleaseId");
    	ReleaseId releaseId = new ReleaseId(groupId, artifactId, versionId);
    	logger.debug("Leaving updateReleaseId");
    	return getKieServicesClient().updateReleaseId(containerId, releaseId).getResult();
    }

}
