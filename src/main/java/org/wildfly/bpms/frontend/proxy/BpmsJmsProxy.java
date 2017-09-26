package org.wildfly.bpms.frontend.proxy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BpmsJmsProxy implements BpmsProxyInterface
{
    private static Logger logger = LoggerFactory.getLogger(BpmsJmsProxy.class);
    
    private String initialContextFactoryName;
    private String connectionFactoryName;
    private String requestQueueJndi;
    private String responseQueueJndi;

    private KieServicesConfiguration conf;
    private KieServicesClient kieServicesClient;
    
    private String username;
    private String password;
    private String remotingURL;
    private String containerId;
    
    private Queue requestQueue;
    private Queue responseQueue;
    private ConnectionFactory connectionFactory;
    
    private Properties configurationProps;

	@Override
	public Logger getLogger() {
		return logger;
	}
	
	public BpmsJmsProxy(String username, String password, String remotingURL, String containerId)
	{
	    this.username = username;
	    this.password = password;
	    this.remotingURL = remotingURL;
	    this.containerId = containerId;
	    init();
	}
	
	public BpmsJmsProxy()
	{
		configurationProps = retrieveProperties();
		
		if(configurationProps != null) {
		    username = configurationProps.getProperty("username");
		    password = configurationProps.getProperty("password");
		    remotingURL = configurationProps.getProperty("jms.remoting.url");
		    containerId = configurationProps.getProperty("container.id");
		    init();
		}else {
			logger.error("Properties file was found to be null");
		}
	}
	
	private void init() 
	{    
		Properties env = new Properties();
	    env.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactoryName);
	    env.put(Context.PROVIDER_URL, remotingURL);
	    env.put(Context.SECURITY_PRINCIPAL, username);
	    env.put(Context.SECURITY_CREDENTIALS, password);
	    InitialContext context;
	    
		try {
			context = new InitialContext(env);
		    requestQueue = (Queue) context.lookup(requestQueueJndi);
		    responseQueue = (Queue) context.lookup(responseQueueJndi);
		    connectionFactory = (ConnectionFactory) context.lookup(connectionFactoryName);	
		    context.close();
		} catch (Exception e) {
			logger.error("Constructor::BPMSJMSProxy()", e);
		}
	}

	@Override
	public KieServicesClient getKieServicesClient() {
		if (kieServicesClient == null) {
	        conf = KieServicesFactory.newJMSConfiguration(connectionFactory, requestQueue, responseQueue, username, password);
	        conf.setMarshallingFormat(MarshallingFormat.JSON);
	        kieServicesClient = KieServicesFactory.newKieServicesClient(conf);
		}
	    return kieServicesClient; 
	}

	@Override
	public String getContainerId() {
		return containerId;
	}
	
	/**
	 * Attempt to retrieve a properties file from the same directory which the JAR launches from,
	 * then fall back to internal defaults if that fails
	 */
	public Properties retrieveProperties() {
		logger.debug("Entering retrieveProperties");
		Properties prop = new Properties();
		
		try {

			Path path = Paths.get("./BPMSJMSProxy.properties");

			//Check whether properties file outside of JAR exists
			if (path.toFile().exists()) {
				prop.load(new FileInputStream("./BPMSJMSProxy.properties"));
			}else {
				InputStream testUrl = this.getClass().getResourceAsStream("/BPMSJMSProxy.properties");
				prop.load(testUrl);
				testUrl.close();
			}
		} catch (IOException e1) {
			logger.error(e1.getMessage());
		}
		
		logger.debug("Leaving retrieveProperties");
		return prop;
	}
}
