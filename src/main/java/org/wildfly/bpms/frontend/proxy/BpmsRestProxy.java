package org.wildfly.bpms.frontend.proxy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BpmsRestProxy implements BpmsProxyInterface
{
    private static Logger logger = LoggerFactory.getLogger(BpmsRestProxy.class);
    
	private String bpmsUserName;
    private String bpmsPassword;
    private String kieServerURL;
    private String containerId;
    
    private KieServicesClient kieServicesClient;
    
    private Properties config;

    public BpmsRestProxy()
    {
        config = retrieveProperties();
        
        if(config != null) {
	    	this.bpmsUserName = config.getProperty("username");
	        this.bpmsPassword = config.getProperty("password");
	        this.kieServerURL = config.getProperty("kie.server.url");
	        this.containerId = config.getProperty("container.id");
        }else {
        	logger.error("Properties file was found to be null");
        }
    }
    
    public BpmsRestProxy(String bpmsUserName, String bpmsPassword, String bpmsURL, String containerId)
    {
        super();
    	this.bpmsUserName = bpmsUserName;
        this.bpmsPassword = bpmsPassword;
        this.kieServerURL = bpmsURL;
        this.containerId = containerId;
    }

	@Override
    public KieServicesClient getKieServicesClient()
    {
    	if (kieServicesClient == null) {
	    	KieServicesConfiguration configuration = KieServicesFactory.newRestConfiguration(kieServerURL, bpmsUserName, bpmsPassword);
	    	configuration.setMarshallingFormat(MarshallingFormat.JSON);
	        kieServicesClient = KieServicesFactory.newKieServicesClient(configuration);
    	}
        return kieServicesClient;
    }

	@Override
	public Logger getLogger() {
		return BpmsRestProxy.logger;
	}

	@Override
	public String getContainerId() {
		return this.containerId;
	}
	
	/**
	 * Attempt to retrieve a properties file from the same directory which the JAR launches from,
	 * then fall back to internal defaults if that fails
	 */
	public Properties retrieveProperties() {
		logger.debug("Entering retrieveProperties");
		Properties prop = new Properties();
		
		try {

			Path path = Paths.get("./BPMSRestProxy.properties");

			//Check whether properties file outside of JAR exists
			if (path.toFile().exists()) {
				prop.load(new FileInputStream("./BPMSRestProxy.properties"));
			}else {
				InputStream testUrl = this.getClass().getResourceAsStream("/BPMSRestProxy.properties");
				prop.load(testUrl);
				testUrl.close();
			}
		} catch (IOException e1) {
			logger.error(e1.getMessage());
		}
		
		logger.debug("Leaving retrieveProperties");
		return prop;
	}
	
	public void setKieServicesClient(KieServicesClient client) {
		this.kieServicesClient = client;
	}
}
