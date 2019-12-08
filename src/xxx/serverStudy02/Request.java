package xxx.serverStudy02;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *  #封装请求协议
 */
public class Request {

	//协议信息
	private String requestInfo;
	//请求方式
	private String method;
	//请求url
	private String url;
	//请求参数
	private String queryStr;
	
	private final String CRLF = "\r\n"; // 换行
	
	private Map<String, List<String>> parameterMap;
	
	public Request(InputStream is) {
		
		byte data[] = new byte[1024*1024];
		int len;
		try {
			len = is.read(data);
			this.requestInfo = new String(data, 0, len);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		//分解字符串
		parseRequestInfo();
		parameterMap = new HashMap<String, List<String>>();
		//fav=1&fav=2&uname=hmc&age=19&others=
		convertMap();
	}
	
	private void convertMap() {
		String keyValues[] = this.queryStr.split("&");
		for(String query : keyValues) {
			//再次分割
			String kv[] = query.split("=");
			kv = Arrays.copyOf(kv, 2);
			String key = kv[0];
			String value = kv[1]==null?null:decode(kv[1],"utf-8");
			//存储到map中
			if(!parameterMap.containsKey(key)) {
				parameterMap.put(key, new ArrayList<String>());
			}
			parameterMap.get(key).add(value);
		}
	}
	
	//处理中文
	private String decode(String value,String enc) {
		
		try {
			return java.net.URLDecoder.decode(value, enc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 通过key获取多个value
	public String[] getParameterValues(String key) {
		List<String> list = this.parameterMap.get(key);
		if(null==list || list.size()<1) {
			return null;
		}
		return list.toArray(new String[0]);
	}
	
	// 通过key拿第一条信息
	public String getParameter(String key) {
		String values[] = getParameterValues(key);
		return values==null ? null : values[0];
	}
	
	private void parseRequestInfo() {
		System.out.println("fenjiekaishi....");
		//--1、获取请求方式 --- GET/POST
		this.method = this.requestInfo.substring(0, this.requestInfo.indexOf("/")).toLowerCase().trim();
		//--2、获取请求URL
		int startIdx =  this.requestInfo.indexOf("/") + 1;
		int endtIdx =  this.requestInfo.indexOf("HTTP/");
		this.url = this.requestInfo.substring(startIdx, endtIdx);
		//--3、分解获取请求URL
		int queryIdx = this.url.indexOf("?");
		if(queryIdx>0) {
			String urlArray[] = this.url.split("\\?");
			this.url = urlArray[0];
			this.queryStr = urlArray[1];
		}
		//获取请求内容
		if(method.equals("post"))
		{
			String str = this.requestInfo.substring(this.requestInfo.lastIndexOf(CRLF)).trim();
			if(str.length()>0)
				this.queryStr += "&" + str;
		}
		System.out.println(this.method +  "...." + this.url +  "...." + this.queryStr);
	}
	
	public Request(Socket client) throws IOException {
		this(client.getInputStream());
	}

	public String getMethod() {
		return method;
	}

	public String getUrl() {
		return url;
	}

	public String getQueryStr() {
		return queryStr;
	}
	
	
}
