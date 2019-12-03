package xxx.serverStudy01;

import java.io.IOException;

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
		// 加载文件返回文件的输入流
        //InputStream is = XmlParseUtils.class.getClassLoader().getResourceAsStream("users.xml");
        //XmlParseHandler handler = new XmlParseHandler();
        // 1. 得到SAX解析工厂
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        // 2. 让工厂生产一个sax解析器
        SAXParser newSAXParser = saxParserFactory.newSAXParser();
        // 3. 编写处理器
        // 4. 加载处理器
        PHandler handler = new PHandler();
        // 5. 解析
        newSAXParser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("xxx/serverStudy01/p.xml"), handler);



	}

}

class PHandler extends DefaultHandler {
	
	@Override
	public void startDocument() throws SAXException {
		System.out.println("-----解析开始--------");
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		System.out.println(qName + "-->解析开始");
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String elements = new String(ch, start, length).trim();
		if(elements.length()>0) {
			System.out.println("内容为：" + elements);
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		System.out.println(qName + "-->解析结束");
	}
	
	@Override
	public void endDocument() throws SAXException {
		System.out.println("-----解析结束--------");
	}
}
