package net.member.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//시간되면 properties 파일 만들어서 아래의 조건들을 변형해보자.

public class MemberFrontController extends HttpServlet { 
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		/* service가 doget dopost를 대체한다. */
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		ActionForward forward = null;
		Action action = null;
		
		//클라이언트가 입력한 URL값에 따라 조건문을 실행한다.
		if (command.equals("/MemberLogin.me")) {
			forward = new ActionForward();
			forward.setRedirect(false);//false 값을 줬기 때문에, forwading한다.
			forward.setPath("./member/member_login.jsp");
//System.out.println("로그인 URL입력시 forward 값"+forward);
		} else if (command.equals("/MemberJoin.me")) {
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("./member/member_join.jsp");
		} else if (command.equals("/MemberFind.me")) {
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("./member/member_find.jsp");
		} else if (command.equals("/MemberOut.me")) {
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("./member/member_out.jsp");
		} else if (command.equals("/Zipcode.me")) {
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("./member/member_zipcode.jsp");
		}
		
		//로직처리 시작점
		
		//MemberLogin.me -> member_login.jsp -> MemberLoginAction.me
		else if (command.equals("/MemberLoginAction.me")) {
//System.out.println("jsp로부터 값을 받아옴.");
//System.out.println("ID: "+request.getParameter("MEMBER_ID"));
			action = new MemberLoginAction();
			try {
System.out.println("로그인 시도");
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/MemberJoinAction.me")) {//요청 URL이 MemberJoinAction.me이면,
			action = new MemberJoinAction();//MemberJoinAction객체 생성해서 action에 담는다.
			try {
				forward = action.execute(request, response);/*회원가입시 MemberJoinAction()클래스의
				ActionForward타입의 리턴을 받는 execute메소드는 null을 리턴한다.*/
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/MemberModifyAction_1.me")) {
			action = new MemberModifyAction_1();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/MemberModifyAction_2.me")) {
			action = new MemberModifyAction_2();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/MemberDeleteAction.me")) {
			action = new MemberDeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/MemberIDCheckAction.me")) {
			action = new MemberIDCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/MemberFindAction.me")) {
			action = new MemberFindAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/MemberZipcodeAction.me")) {
			action = new MemberZipcodeAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(forward == null) {
System.out.println("로그인 URL입력시 forward 값"+forward);
		}else {
		if (forward.isRedirect()) {//ActionForward에 isRedirect가 true면
			response.sendRedirect(forward.getPath());//경로에 따라서 Redirect한다.
System.out.println("리다이렉트 작업 완료");
			//로그인 시에 리다이렉트를 한다.
		} else {//ActionForward에 isRedirect가 false면 forward한다.
			RequestDispatcher dispatcher =
					request.getRequestDispatcher(forward.getPath());
			dispatcher.forward(request, response);
System.out.println("포워드 작업 완료");
//http://localhost:8083/shop/MemberLogin.me URL입력받으면 포워드 실행
//forward()[전달하기]는 클라이언트가 요청하면서 전송한 데이터를 그대로 유지한다.
//포워딩이 되더라도 주소가 변경되지 않는다. (같은 request영역을 공유하게 됨)
		}
	}
	}
}