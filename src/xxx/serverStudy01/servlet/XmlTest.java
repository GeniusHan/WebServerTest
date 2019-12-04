package xxx.serverStudy01.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 *   *熟悉XML的解析流程
 */
public class XmlTest {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {

        // 1. 得到SAX解析工厂
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        // 2. 让工厂生产一个sax解析器
        SAXParser newSAXParser = saxParserFactory.newSAXParser();
        // 3. 编写处理器
        // 4. 加载处理器
        WebHandler handler = new WebHandler();
        // 5. 解析
        newSAXParser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("xxx/serverStudy01/servlet/web.xml"), handler);

        WebContext webContext = new WebContext(handler.getEntities(), handler.getMappings());
        
        String clz = webContext.getClz("/reg");
        
        Servlet servlet = (Servlet) Class.forName(clz).getConstructor().newInstance();
        
        servlet.service();
        
	}

}

class WebHandler extends DefaultHandler {
	
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
