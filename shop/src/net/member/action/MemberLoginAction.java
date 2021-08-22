package net.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.db.MemberDAO;

public class MemberLoginAction implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		HttpSession session=request.getSession();
		ActionForward forward=new ActionForward();
		MemberDAO memberdao=new MemberDAO();
		
		String id=request.getParameter("MEMBER_ID");
		String pass=request.getParameter("MEMBER_PW");
		
		int check=memberdao.userCheck(id, pass);
		if(check == 1){//로그인 성공시 리턴값으로 1을 갖고온다.
			session.setAttribute("id", id);
	
			if(memberdao.isAdmin(id)){//관리자인지 확인
//System.out.println("관리자 로그인");
				forward.setRedirect(true);
				forward.setPath("./GoodsList.ag"); 
				return forward;
			}else{
//System.out.println("일반회원 로그인");
				forward.setRedirect(true);
				forward.setPath("./GoodsList.go?item=new_item"); 
				/*일반회원 로그인시 item 값에 new_item 을 주는 시작점.*/
/*System.out.println("로그인 하고 상품리스트로 넘어갈때 forward값 : " + forward);*/ /*->net.member.action.ActionForward@43727390*/
				return forward;
			}
		}else if(check == 0){//비밀번호가 틀린 경우
			
			response.setContentType("text/html; charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('비밀번호가 일치하지 않습니다.');");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
			
		}else{//그 외의 경우
			
			response.setContentType("text/html; charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('아이디가 존재하지 않습니다.');");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
		}
		
		return null;
	}
}