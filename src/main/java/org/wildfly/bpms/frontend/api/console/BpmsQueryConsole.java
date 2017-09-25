package org.wildfly.bpms.frontend.api.console;

import java.util.List;

import org.kie.server.api.model.definition.ProcessDefinition;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.QueryServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.bpms.frontend.api.BpmsQueryServices;

public class BpmsQueryConsole extends BpmsQueryServices {
	
	private static Logger logger = LoggerFactory.getLogger(BpmsQueryConsole.class);

	public BpmsQueryConsole(String containerId, KieServicesClient client) {
		super(containerId, client);
	}
	
	public List<ProcessDefinition> findProcessDefinitionsConsole(String page, String pageSize){
    	logger.debug("Executing findProcessDefinitionsConsole");
    	Integer pageInt = Integer.valueOf(page);
    	Integer pageSizeInt = Integer.valueOf(pageSize);
    	return getKieServicesClient().getServicesClient(QueryServicesClient.class)
        		.findProcesses(pageInt, pageSizeInt);
    }

}
