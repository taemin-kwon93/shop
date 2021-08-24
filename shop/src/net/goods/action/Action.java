package net.goods.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {

	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
	throws Exception;
	
	/* ���� MVC���� '�α���,ȸ������ ������Ʈ'������ String Ÿ���� ���������ν� ����� ������ �����ߴ�. 
	 * LoginFormAction Ŭ������ CommandAction���� ���� Interface�� ��ӹް�  HttpServletRequest, HttpServletResponse import�ؿ�. ���� �������̵�
	 * 
	 * public class LoginFormAction implements CommandAction{
	 * @Override �޼ҵ带 �������̵� ���ش�.
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		return "/logon2/loginForm.jsp";) ""�ȿ� ���� ������ StringŸ������ �����Ѵ�.*/
}