package net.member.action;

import javax.servlet.http.*;


public interface Action {
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
	throws Exception;
	/*�������� String Ÿ���� ���������ν� ����� ������ �����ߴ�. 
	 * (����. HttpServletRequest, HttpServletResponse import�ؿ���
	 *
	 * public class LoginFormAction implements CommandAction{
	 * LoginFormAction Ŭ������ CommandAction���� ���� Interface�� ��ӹ޴´�.
	 * 
	 * @Override �޼ҵ带 �������̵� ���ش�.
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		return "/logon2/loginForm.jsp";) ""�ȿ� ���� ������ StringŸ������ �����Ѵ�.*/
}
