package net.admin.goods.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminGoodsFrontController extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.processRequest(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		ActionForward forward=null;
		Action action=null;
		
		if(command.equals("/GoodsAddAction.ag")){
			action= new AdminGoodsAddAction();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		//관리자 로그인후 List목록 띄움
		}else if(command.equals("/GoodsList.ag")){
			action=new AdminGoodsListAction();
			try {
System.out.println("AGFC_관리자 로그인");
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/GoodsAdd.ag")){
			forward=new ActionForward();
			forward.setRedirect(false);
			forward.setPath("./admingoods/admin_goods_write.jsp");
			
		}else if(command.equals("/GoodsDelete.ag")){
			action=new AdminGoodsDeleteAction();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/GoodsModify.ag")){
			action=new AdminGoodsModifyForm();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/GoodsModifyAction.ag")){
			action=new AdminGoodsModifyAction();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(forward.isRedirect()){
System.out.println("AGFC_Redirect");
			response.sendRedirect(forward.getPath());			
		}else{		
System.out.println("AGFC_Dispatcher");
			RequestDispatcher dispatcher=
				request.getRequestDispatcher(forward.getPath());
			dispatcher.forward(request, response);			
		}
	}
}