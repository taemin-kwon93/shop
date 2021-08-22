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
		/*request.getRequestURI() �Լ� = ������Ʈ + ���ϰ�α��� �����ɴϴ�.
		  ��)  http://localhost:8080/shop/GoodsList.go
		  [return]/shop/GoodsList.go�� �����ɴϴ� */
		String contextpath = request.getContextPath();
System.out.println(contextpath);
		/*request.getContextPath() �Լ� = ������Ʈ Path�� �����ɴϴ�.
		  (����. http://localhost:8080/shop/GoodsList.go )
	   	  [return] /shop, �� ������Ʈ �̸��� �����ɴϴ�.
		  ��ó: https://yi-chi.tistory.com/12 [2chi]*/
		String command = requestURI.substring(contextpath.length());
		/*/shop/GoodsList.go���� substring(�߸� ����/shop), ���ڸ� �߶���ϴ�.
		  ���� String command="/GoodsList.go"; */
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
/*System.out.println(forward);*/  /*42����, forward=null;*/
				forward = action.execute(request, response);
/*System.out.println(forward);*/ /*net.goods.action.ActionForward@2c5b5cce*/
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (forward != null) {
System.out.println("������ != null");
			if (forward.isRedirect()) {
System.out.println("������ isRedirect()");
				response.sendRedirect(forward.getPath());
			} else {
System.out.println("������ dispatcher");
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
				//������ Dispatcher�� ��û�� ���� ������ �ѱ�� �۾�
			}
		}//if(forward != null)
	}
}