package xxx.serverStudy03.copy;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Dispatcher implements Runnable{

	private Socket client;
	private Request request;
	private Response response;
	
	public Dispatcher(Socket client){
		this.client=client;
		try {
			request = new Request(client);
			response = new Response(client);
		} catch (Exception e) {
			e.printStackTrace();
			this.release();
		}	
		
	}
	
	@Override
	public void run() {
		try {
			Servlet servlet = WebApp.getServletFromUrl(request.getUrl());
			if(null!=servlet) {
				servlet.service(request, response);
				//关注状态码
				response.pushToBrowser(200);
			}else {
				//错误
//				InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("error.html");
//				response.println(new String(is.readAllBytes()));
				response.pushToBrowser(404);
			}
		}catch(Exception e) {
			try {
				response.println("wobuhao");
				response.pushToBrowser(500);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		release();
	}

	private void release()
	{
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
