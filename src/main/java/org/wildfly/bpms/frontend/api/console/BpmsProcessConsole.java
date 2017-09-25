package org.wildfly.bpms.frontend.api.console;

import java.util.ArrayList;
import java.util.Map;

import org.kie.server.api.model.definition.ProcessDefinition;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.ProcessServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.bpms.frontend.api.BpmsProcessServices;

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
		ArrayList<String> varList = new ArrayList<>(1);
		for(String key : processVars.keySet()) {
			varList.add(key);
			if(processVars.get(key) == null) {
				varList.add("");
			}else {
				varList.add(processVars.get(key));
			}
		}
		
		String formattedVars = "";
		for(int i = 0; i < varList.size(); i += 2) {
			formattedVars = formattedVars.concat(varList.get(i) + "   " + varList.get(i + 1) + "%n");
		}
		return String.format("Process ID: %s%nProcess Name: %s%nFrom Container ID: %s%nProcess Version: %s%nProcess Variables: %n%s%n", processDef.getId(), processDef.getName(), processDef.getContainerId(), processDef.getVersion(), formattedVars);
	}

}
