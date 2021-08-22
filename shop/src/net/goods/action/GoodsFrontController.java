package net.goods.action;

import java.io.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.goods.action.GoodsDetailAction;
import net.goods.action.GoodsListAction;

public class GoodsFrontController extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		this.processRequest(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		this.processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String requestURI = request.getRequestURI();
System.out.println(requestURI);
		/*request.getRequestURI() 함수 = 프로젝트 + 파일경로까지 가져옵니다.
		  예)  http://localhost:8080/shop/GoodsList.go
		  [return]/shop/GoodsList.go를 가져옵니다 */
		String contextpath = request.getContextPath();
System.out.println(contextpath);
		/*request.getContextPath() 함수 = 프로젝트 Path만 가져옵니다.
		  (예시. http://localhost:8080/shop/GoodsList.go )
	   	  [return] /shop, 즉 프로젝트 이름을 가져옵니다.
		  출처: https://yi-chi.tistory.com/12 [2chi]*/
		String command = requestURI.substring(contextpath.length());
		/*/shop/GoodsList.go에서 substring(잘릴 글자/shop), 글자를 잘라냅니다.
		  따라서 String command="/GoodsList.go"; */
System.out.println(command);
		ActionForward forward = null;
		Action action = null;
		
		if (command.equals("/Goods_Detail.go")) {
			action = new GoodsDetailAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/GoodsList.go")) {
			action = new GoodsListAction();
			try {
/*System.out.println(forward);*/  /*42행쯤, forward=null;*/
				forward = action.execute(request, response);
/*System.out.println(forward);*/ /*net.goods.action.ActionForward@2c5b5cce*/
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (forward != null) {
System.out.println("포워드 != null");
			if (forward.isRedirect()) {
System.out.println("포워드 isRedirect()");
				response.sendRedirect(forward.getPath());
			} else {
System.out.println("포워드 dispatcher");
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
				//설정된 Dispatcher로 요청과 응답 권한을 넘기는 작업
			}
		}//if(forward != null)
	}
}