package xxx.serverStudy03.copy;

public class RegisterServlet implements Servlet {

	@Override
	public void service(Request request, Response response) {
		//关注内容
		response.println("<html>");
		response.println("<head>");
		response.println("<title>");
		response.println("注册成功");
		response.println("</title>");
		response.println("</head>");
		response.println("<body>");
		response.println("register success..." + request.getParameter("uname"));
		response.println("</body>");
		response.println("</html>");
	}

}
