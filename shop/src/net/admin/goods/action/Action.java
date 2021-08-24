package net.admin.goods.action;

import javax.servlet.http.*;

public interface Action {
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
	throws Exception;
	
	/* 이전 MVC패턴 '로그인,회원가입 프로젝트'에서는 String 타입을 리턴함으로써 진행될 방향을 설정했다. 
	 * LoginFormAction 클래스는 CommandAction으로 부터 Interface를 상속받고  HttpServletRequest, HttpServletResponse import해옴. 이후 오버라이드
	 * 
	 * public class LoginFormAction implements CommandAction{
	 * @Override 메소드를 오버라이드 해준다.
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		return "/logon2/loginForm.jsp";) ""안에 담기는 내용을 String타입으로 리턴한다.*/
}
