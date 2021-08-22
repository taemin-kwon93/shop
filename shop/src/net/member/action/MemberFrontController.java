package net.member.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//�ð��Ǹ� properties ���� ���� �Ʒ��� ���ǵ��� �����غ���.

public class MemberFrontController extends HttpServlet { 
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		/* service�� doget dopost�� ��ü�Ѵ�. */
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		ActionForward forward = null;
		Action action = null;
		
		//Ŭ���̾�Ʈ�� �Է��� URL���� ���� ���ǹ��� �����Ѵ�.
		if (command.equals("/MemberLogin.me")) {
			forward = new ActionForward();
			forward.setRedirect(false);//false ���� ��� ������, forwading�Ѵ�.
			forward.setPath("./member/member_login.jsp");
//System.out.println("�α��� URL�Է½� forward ��"+forward);
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
		
		//����ó�� ������
		
		//MemberLogin.me -> member_login.jsp -> MemberLoginAction.me
		else if (command.equals("/MemberLoginAction.me")) {
//System.out.println("jsp�κ��� ���� �޾ƿ�.");
//System.out.println("ID: "+request.getParameter("MEMBER_ID"));
			action = new MemberLoginAction();
			try {
System.out.println("�α��� �õ�");
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/MemberJoinAction.me")) {//��û URL�� MemberJoinAction.me�̸�,
			action = new MemberJoinAction();//MemberJoinAction��ü �����ؼ� action�� ��´�.
			try {
				forward = action.execute(request, response);/*ȸ�����Խ� MemberJoinAction()Ŭ������
				ActionForwardŸ���� ������ �޴� execute�޼ҵ�� null�� �����Ѵ�.*/
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
System.out.println("�α��� URL�Է½� forward ��"+forward);
		}else {
		if (forward.isRedirect()) {//ActionForward�� isRedirect�� true��
			response.sendRedirect(forward.getPath());//��ο� ���� Redirect�Ѵ�.
System.out.println("�����̷�Ʈ �۾� �Ϸ�");
			//�α��� �ÿ� �����̷�Ʈ�� �Ѵ�.
		} else {//ActionForward�� isRedirect�� false�� forward�Ѵ�.
			RequestDispatcher dispatcher =
					request.getRequestDispatcher(forward.getPath());
			dispatcher.forward(request, response);
System.out.println("������ �۾� �Ϸ�");
//http://localhost:8083/shop/MemberLogin.me URL�Է¹����� ������ ����
//forward()[�����ϱ�]�� Ŭ���̾�Ʈ�� ��û�ϸ鼭 ������ �����͸� �״�� �����Ѵ�.
//�������� �Ǵ��� �ּҰ� ������� �ʴ´�. (���� request������ �����ϰ� ��)
		}
	}
	}
}