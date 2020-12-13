package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.PagingUtil;

public class ListCtrl extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//req.getRequestDispatcher("/14Dataroom/DataList.jsp").forward(req, resp);
		//DAO객체 생성 및 커넥션풀을 통한 DB연결
		DataroomDAO dao = new DataroomDAO();
		//컨트롤러(서블릿) 및 View(JSP)로 전달할 파라미터를 저장하기 위한 맵 컬렉션
		Map param = new HashMap();
		String addQueryString = "";
		
		String searchColumn = req.getParameter("searchColumn");
		String searchWord = req.getParameter("searchWord");
		if(searchColumn!=null) {
			//검색어가 있는경우 파라미터를 Map에 저장하고, 쿼리스트링을 만들어준다.
			addQueryString=
					String.format("searchColumn=%s&searchWord=%s&",searchColumn, searchWord);
			param.put("Column", searchColumn);
			param.put("Word", searchWord);
		}
		//테이블의 전체 레코드수를 카운트
		int totalRecordCount = dao.getTotalRecordCount(param);
		//전체 레코드수를 Map에 저장, 차후View로 전달한다.
		param.put("totalCount", totalRecordCount);
		//페이지처리 추가부분///////////////////////////
		//페이지수 계산을 위한 페이지설정값 가져오기
		/* web.xml의 컨텍스트 초기화 파라미터를 서블릿에서 가져오기 위해 
		 * application 내장객체를 메소드를 통해 얻어와서 값을 얻어온다. */
		ServletContext application = this.getServletContext();
		int pageSize = 
				Integer.parseInt(application.getInitParameter("PAGE_SIZE"));
		int blockPage = 
				Integer.parseInt(application.getInitParameter("BLOCK_PAGE"));
		
		int totalPage = 
				(int)Math.ceil((double)totalRecordCount/pageSize);
		//전체 페이지수를 계산한다.
		System.out.println("전체레코드수:"+totalRecordCount);
		System.out.println("전체페이지수:"+totalPage);
		
		//현재 페이지번호를 설정한다. 최초진입시에는 무조건 1로 설정한다.
		int nowPage = (req.getParameter("nowPage")==null
				||req.getParameter("nowPage").equals(""))?1:Integer.parseInt(req.getParameter("nowPage"));
		
		//한페이지에 출력할 게시물의 구간을 정하기 위해 start, end값 계산
		int start = (nowPage-1) * pageSize + 1;
		int end = nowPage * pageSize;
		System.out.println("start,end:"+start+", "+end);
		
		param.put("start", start);
		param.put("end", end);
		param.put("totalPage", totalPage);
		param.put("nowPage", nowPage);
		param.put("totalRecordCount", totalRecordCount);
		param.put("pageSize", pageSize);
		
		String pagingImg = PagingUtil.pagingBS4(totalRecordCount, pageSize, blockPage, nowPage, "../DataRoom/DataList?"+addQueryString);
		
		param.put("pagingImg", pagingImg);
		/////////////////////////////
		//테이블의 전체레코드를 가져와서 List컬렉션에 저장한다.
		//페이지처리X
		//List<DataroomDTO> lists=dao.selectList(param);
		//페이지처리O
		List<DataroomDTO> lists=dao.selectListPage(param);
		
		dao.close();
		//데이터를 request영역에 저장한다.
		req.setAttribute("test", "매핑확인용");
		req.setAttribute("lists", lists);
		req.setAttribute("map", param);
		//영역에 저장된데이터를 View쪽으로 전달하기 위해 포워드한다.
		req.getRequestDispatcher("/14Dataroom/DataList.jsp").forward(req, resp);
		
		
	}
	/* 만약 게시판 리스트쪽에서 post방식으로 요청이 들어오더라도
	 * 처리는 doGet()에서 처리할 수 있도록 모든 요청을 토스한다.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
}
