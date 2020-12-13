package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MemberDAO;

public class SimpleMVC extends HttpServlet {
	@Override
	/* 클라이언트의 요청이 get방식이든 post방식이든 하나의 메소드에
	 * 처리하기 위해 processRequest()메소드를 정의하고 모든 요청을
	 * 처리하도록 구현한다.
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(req, resp);
	}
	/* doGet()혹은 doPost()의 요청을 그대로 전달받기 위해서는 아래와같이
	 메소드를 정의해야 한다. request, response객체를 매개변수로 사용하고
	 두가지에 대한 예외를 반드시 처리해야한다(서블릿 제작시 규칙) */
	public void processRequest(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		String type = req.getParameter("type");
		//파라미터를 통해 요청을 분석한다.
		Object resultObj = null;
		if(type==null) {
			resultObj = "파라미터가 없어요";
		}else if(type.equals("greeting")){
			/* 서블릿 클래스에서 application내장객체를 사용하기 위해 
			getServletContext()를 이용해서 얻어온다. */
			ServletContext application = this.getServletContext();
			String drv = application.getInitParameter("JDBCDriver");
			String url = application.getInitParameter("ConnectionURL");
			
			MemberDAO dao = new MemberDAO(drv, url);
			String id = req.getParameter("id");
			String pw = req.getParameter("pw");
			
			boolean isMember = dao.isMember(id, pw);
			
			if(isMember==true) {
				resultObj = "회원님 안뇽";
			}else {
				resultObj = "회원이 아니시군";
			}
			
		}else if(type.equals("date")) {
			resultObj=new java.util.Date();
		}else {
			resultObj = "허얼??";
		}
		//결과를 request영역에 저장한다.
		req.setAttribute("result", resultObj);
		//뷰로 포워드 한다.
		RequestDispatcher dis=req.getRequestDispatcher("/13Servlet/SimpleMVC.jsp");
		dis.forward(req, resp);
		
		
	}
}
