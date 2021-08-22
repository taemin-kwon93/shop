package net.admin.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.order.db.AdminOrderDAO;
import net.member.db.MemberBean;
import net.member.db.MemberDAO;
import net.order.db.OrderBean;

public class AdminOrderDetailAction implements Action{
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
	throws Exception{
		MemberDAO memberdao=new MemberDAO();
		MemberBean member=new MemberBean();
		AdminOrderDAO orderdao=new AdminOrderDAO();
		OrderBean order=new OrderBean();
		
		String num=request.getParameter("num");//num의 출처, admin_order_list.jsp
//System.out.println("오류 발생 부분 num이 null로 나옴: "+num);
		order = orderdao.getOrderDetail(Integer.parseInt(num));
		member=memberdao.getMember(order.getORDER_MEMBER_ID());
		request.setAttribute("order", order);
		request.setAttribute("ordermember", member);
		
		ActionForward forward=new ActionForward();
		forward.setPath("./adminorder/admin_order_modify.jsp");
		return forward;
	 } 
}