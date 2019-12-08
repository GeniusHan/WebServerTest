package xxx.serverStudy03.copy;

public class LoginServlet implements Servlet {

	@Override
	public void service(Request request, Response response) {
		//关注内容
		response.println("<html>");
		response.println("<head>");
		response.println("<title>");
		response.println("服务器响应成功bbb");
		response.println("</title>");
		response.println("</head>");
		response.println("<body>");
		response.println("welcome..." + request.getParameter("uname"));
		response.println("</body>");
		response.println("</html>");
	}

}
