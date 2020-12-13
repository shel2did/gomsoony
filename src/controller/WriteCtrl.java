package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import util.FileUtil;

public class WriteCtrl extends HttpServlet{
	@Override
	/* 글쓰기 페이지로 진입했을때의 요청을 처리한다. 글쓰기 폼으로 이동(location)하는것은
	 get방식이므로 doGet()에서 처리한다. */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/* 글쓰기 페이지로 진입할때는 다른 처리없이 포워드만 하면 된다. */
		req.getRequestDispatcher("/14Dataroom/DataWrite.jsp").forward(req, resp);
	}
	/* 글 작성을 완료한 후 서버로 전송(submit)할떄의 요청을 처리한다. 게시판에 글을 쓸때는
	 post방식으로 처리하게 된다. */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		//한글처리
		req.setCharacterEncoding("UTF-8");
		
		//request객체와 물리적 경로를 매개변수로 upload()를 호출한다.
		MultipartRequest mr = 
				FileUtil.upload(req,req.getServletContext().getRealPath("/Upload"));
		
		int sucOrFail;
		
		if(mr!=null) {
			/* 파일업로드가 완료되면 나머지 폼값을 받기위해 mr참조변수를 이용한다. */
			String name = mr.getParameter("name");
			String title = mr.getParameter("title");
			String pass = mr.getParameter("pass");
			String content = mr.getParameter("content");
			//서버에 저장된 실제파일명을 가져온다.
			String attachedfile = 
			mr.getFilesystemName("attachedfile");
			
			DataroomDTO dto = new DataroomDTO();
			dto.setAttachedfile(attachedfile);
			dto.setContent(content);
			dto.setTitle(title);
			dto.setName(name);
			dto.setPass(pass);
			
			//DAO객체생성 및 연결...insert처리
			DataroomDAO dao = new DataroomDAO();
			//파일업로드 성공및 insert성공시_
			
			
			//sucOrFail = dao.insert(dto);
			/*페이지  처리를 위한 데이터 입력	 */
			
			sucOrFail = 1;
			for(int i=1;i<=100; i++) {
				dto.setTitle("자료실 "+i+"번째 포스팅");
				dao.insert(dto);
			}
			
			dao.close();
			
		}else {
			sucOrFail=-1;
		}
		if(sucOrFail==1) {
			resp.sendRedirect("../DataRoom/DataList");
		}else {
			//실패시 글쓰기 페이지로 이동
			req.getRequestDispatcher("/14Dataroom/DataWrite.jsp").forward(req, resp);
		}
	};
		
}
