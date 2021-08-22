package net.member.action;

import javax.servlet.http.*;

import net.member.db.MemberDAO;

public class MemberIDCheckAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		ActionForward forward = new ActionForward();
		
		String id =request.getParameter("MEMBER_ID");
		
		MemberDAO memberdao = new MemberDAO();
		int check=memberdao.confirmId(id);
		
		request.setAttribute("id", id);
		request.setAttribute("check", check);
		
		forward.setRedirect(false);
		forward.setPath("./member/member_idchk.jsp");
		return forward;
		
	}

}