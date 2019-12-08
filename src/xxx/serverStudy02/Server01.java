package xxx.serverStudy02;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*
 *  * 使用serverSocket，与浏览器建立连接
 */
public class Server01 {

	private ServerSocket serverSocket;
	
	public static void main(String[] args) {
		Server01 server = new Server01();
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
			InputStream is = client.getInputStream();
			byte data[] = new byte[1024*1024];
			int len = is.read(data);
			String inputInfo = new String(data, 0, len);
			System.out.println(inputInfo);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("连接错误。。。");
		}
	}
	
	//关闭服务
	public void stop() {
		
	}
}
