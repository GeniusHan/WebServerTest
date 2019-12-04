package xxx.serverStudy01;

import java.io.IOException;
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

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

        // 1. 得到SAX解析工厂
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        // 2. 让工厂生产一个sax解析器
        SAXParser newSAXParser = saxParserFactory.newSAXParser();
        // 3. 编写处理器
        // 4. 加载处理器
        PersonHandler handler = new PersonHandler();
        // 5. 解析
        newSAXParser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("xxx/serverStudy01/p.xml"), handler);


        List<Person> persons = handler.getPersons();
        
        for(Person p : persons) {
        	System.out.println(p.toString());
        }
	}

}

class PersonHandler extends DefaultHandler {
	
	private List<Person> persons;
	private Person person ;
	private String tag; // 保存标签
	
	public List<Person> getPersons() {
		return persons;
	}

	@Override
	public void startDocument() throws SAXException {
		persons = new ArrayList<Person>();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(qName.equals("person")) {
			person = new Person();
		}
		tag = qName;
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String elements = new String(ch, start, length).trim();
		if(elements.length()>0 && tag != null) {
			// TODO 利用反射获取get、set方法
			if(tag.equals("name")) {
				person.setName(elements);
			}
			if(tag.equals("age")) {
				person.setAge(Integer.valueOf(elements));
			}
			//System.out.println(tag + "内容为：" + elements);
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("person")) {
			persons.add(person);
		}
		tag = null;
	}
	
	@Override
	public void endDocument() throws SAXException {
	}
}
