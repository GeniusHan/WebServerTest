package xxx.serverStudy01.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebContext {

	private List<Entity> entities;
	private List<Mapping> mappings;
	
	// key:servlet-name \ value: servlet-class
	private Map<String, String> entityMap;
	// key:servlet-pattern \ value : servlet-name
	private Map<String, String> mappingMap;
	
	public WebContext(List<Entity> entities, List<Mapping> mappings) {
		
		this.entities = entities;
		this.mappings = mappings;
		
		entityMap = new HashMap<String, String>();
		mappingMap = new HashMap<String, String>();
		
		for(Entity entity : entities) {
			entityMap.put(entity.getName(), entity.getClz());
		}
		for(Mapping mapping : mappings) {
			for(String pattern : mapping.getPatterns()) {
				mappingMap.put(pattern, mapping.getName());
			}
		}
	}
	
	/*
	 * 通过URL的方式拿到类
	 */
	public String getClz(String pattern) {
		String name = mappingMap.get(pattern);
		return entityMap.get(name);
	}
	
}
