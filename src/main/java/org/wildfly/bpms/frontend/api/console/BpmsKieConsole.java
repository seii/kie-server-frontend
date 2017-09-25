package org.wildfly.bpms.frontend.api.console;

import org.kie.server.api.model.KieServerInfo;
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
    	return String.format("Name: %s%nLocation: %s%nServer ID: %s%nVersion: %s%nCapabilities: %s%n", status.getName(), status.getLocation(), status.getServerId(), status.getVersion(), status.getCapabilities());
    }

}
