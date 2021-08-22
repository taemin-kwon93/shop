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
		if(check == 1){//�α��� ������ ���ϰ����� 1�� ����´�.
			session.setAttribute("id", id);
	
			if(memberdao.isAdmin(id)){//���������� Ȯ��
//System.out.println("������ �α���");
				forward.setRedirect(true);
				forward.setPath("./GoodsList.ag"); 
				return forward;
			}else{
//System.out.println("�Ϲ�ȸ�� �α���");
				forward.setRedirect(true);
				forward.setPath("./GoodsList.go?item=new_item"); 
				/*�Ϲ�ȸ�� �α��ν� item ���� new_item �� �ִ� ������.*/
/*System.out.println("�α��� �ϰ� ��ǰ����Ʈ�� �Ѿ�� forward�� : " + forward);*/ /*->net.member.action.ActionForward@43727390*/
				return forward;
			}
		}else if(check == 0){//��й�ȣ�� Ʋ�� ���
			
			response.setContentType("text/html; charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('��й�ȣ�� ��ġ���� �ʽ��ϴ�.');");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
			
		}else{//�� ���� ���
			
			response.setContentType("text/html; charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('���̵� �������� �ʽ��ϴ�.');");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
		}
		
		return null;
	}
}