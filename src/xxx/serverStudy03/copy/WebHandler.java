package xxx.serverStudy03.copy;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WebHandler extends DefaultHandler {

	private List<Entity> entities;
	private List<Mapping> mappings;
	private Entity entity ;
	private Mapping mapping ;
	private String tag; // 保存标签
	
	private boolean handleMapping = false;
	
	@Override
	public void startDocument() throws SAXException {
		entities = new ArrayList<Entity>();
		mappings = new ArrayList<Mapping>();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(qName.equals("servlet")) {
			entity = new Entity();
			handleMapping = false;
		}
		if(qName.equals("servlet-mapping")) {
			mapping = new Mapping();
			handleMapping = true;
		}
		tag = qName;
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String elements = new String(ch, start, length).trim();
		if(elements.length()>0 && tag != null) {
			
			if(handleMapping) { //处理mapping
				if(tag.equals("servlet-name")) {
					mapping.setName(elements);
				}
				if(tag.equals("url-pattern")) {
					mapping.addPatterns(elements);
				}
			} else { // 处理servlet
				if(tag.equals("servlet-name")) {
					entity.setName(elements);
				}
				if(tag.equals("servlet-class")) {
					entity.setClz(elements);
				}
			}
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equals("servlet")) {
			entities.add(entity);
		}
		if(qName.equals("servlet-mapping")) {
			mappings.add(mapping);
		}
		tag = null;
	}
	
	@Override
	public void endDocument() throws SAXException {
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public List<Mapping> getMappings() {
		return mappings;
	}
}
