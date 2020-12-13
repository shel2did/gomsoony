package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PasswordCtrl extends HttpServlet{
@Override

protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	req.getRequestDispatcher("/14Dataroom/DataPassword.jsp").forward(req, resp);

}
//패스워드를 입력한 후 submit했을때 요청처리
@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String idx = req.getParameter("idx");
	String mode = req.getParameter("mode");
	String pass = req.getParameter("pass");
	
	//패스워드 검증을 위해 model호출
	DataroomDAO dao = new DataroomDAO();
	boolean isCurrect = dao.isCurrectPassword(pass, idx);
	
	dao.close();
	
	
	req.setAttribute("PASS_CORRECT", isCurrect);
	req.getRequestDispatcher("/14Dataroom/PassMessage.jsp").forward(req, resp);
	}
}
