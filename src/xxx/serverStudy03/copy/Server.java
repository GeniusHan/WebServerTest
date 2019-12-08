package xxx.serverStudy03.copy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/*
 *  * 使用serverSocket，与浏览器建立连接
 */
public class Server {

	private ServerSocket serverSocket;
	
	private boolean isRunning;
	
	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}
	
	// 启动服务
	public void start() {
		try {
			serverSocket = new ServerSocket(8888);	
			isRunning = true;
			receive();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("服务器启动失败。。。");
		}
	}
	
	//接收服务
	public void receive() {
		while(isRunning) {
			try {
				Socket client = serverSocket.accept();
				System.out.println("一个客户端建立了连接");
				
				new Thread(new Dispatcher(client)).start();
		
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("连接错误。。。");
			}
		}
	}
	
	//关闭服务
	public void stop() {
		isRunning = false;
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
