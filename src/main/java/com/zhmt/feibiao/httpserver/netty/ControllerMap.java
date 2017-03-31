package com.zhmt.feibiao.httpserver.netty;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.zhmt.feibiao.httpserver.netty.mvc.Controller;

/**
 * controller mapping, configured in controller-mapping.xml
 * 
 *
 */
public class ControllerMap {
	// private static final Logger logger = LoggerFactory.getLogger(ControllerMap.class);
	private Map<String, Controller> controllerMap = new ConcurrentHashMap<String, Controller>();

	public Map<String, Controller> getControllerMap() {
		return controllerMap;
	}

	public void setControllerMap(Map<String, Controller> controllerMap) {
		this.controllerMap = controllerMap;
	}
}
