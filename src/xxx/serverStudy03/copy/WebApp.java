package xxx.serverStudy03.copy;

import java.lang.reflect.InvocationTargetException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class WebApp {
	
	private static WebContext webContext;

	
	static {
		try {
		    // 1. 得到SAX解析工厂
	        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
	        // 2. 让工厂生产一个sax解析器
	        SAXParser newSAXParser = saxParserFactory.newSAXParser();
	        // 3. 编写处理器
	        // 4. 加载处理器
	        WebHandler handler = new WebHandler();
	        // 5. 解析
	        newSAXParser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("xxx/serverStudy03/copy/web.xml"), handler);

	        webContext = new WebContext(handler.getEntities(), handler.getMappings());
		}catch(Exception e) {
			System.out.println("error in prase xml");
		}
	}
	
	// 通过url获取配置文件的servlet
	public static Servlet getServletFromUrl(String url) {
        
        String clz = webContext.getClz("/"+url);
        Servlet servlet = null;
		try {
			servlet = (Servlet) Class.forName(clz).getConstructor().newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return servlet;
	}

}
