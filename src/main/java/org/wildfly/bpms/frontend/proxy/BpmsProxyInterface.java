package org.wildfly.bpms.frontend.proxy;

import java.util.Properties;

import org.kie.server.client.KieServicesClient;
import org.slf4j.Logger;

public interface BpmsProxyInterface {

	Logger getLogger();
	
    KieServicesClient getKieServicesClient();
    
    String getContainerId();
    
    Properties retrieveProperties();

}
