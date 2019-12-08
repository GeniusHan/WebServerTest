package xxx.serverStudy03.copy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/*
 *  * 使用serverSocket，与浏览器建立连接
 */
public class Server07 {

	private ServerSocket serverSocket;
	
	public static void main(String[] args) {
		Server07 server = new Server07();
		server.start();
	}
	
	// 启动服务
	public void start() {
		try {
			serverSocket = new ServerSocket(8888);	
			receive();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("服务器启动失败。。。");
		}
	}
	
	//接收服务
	public void receive() {
		try {
			Socket client = serverSocket.accept();
			System.out.println("一个客户端建立了连接");
			Request request = new Request(client);
			
			Response response = new Response(client);
		
			Servlet servlet = WebApp.getServletFromUrl(request.getUrl());
			if(null!=servlet) {
				servlet.service(request, response);
				//关注状态码
				response.pushToBrowser(200);
			}else {
				//错误
				response.pushToBrowser(404);
			}	
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("连接错误。。。");
		}
	}
	
	//关闭服务
	public void stop() {
		
	}
}
