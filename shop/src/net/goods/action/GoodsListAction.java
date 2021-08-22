package net.goods.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.goods.db.*;

public class GoodsListAction implements Action{
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response){
		ActionForward forward=new ActionForward();
		GoodsDAO goodsdao=new GoodsDAO();
		
		List itemList=null;
		String item=null;
		String price="";
		int count=1;
		int page=1;
		if(request.getParameter("page")!=null){
			page=Integer.parseInt(request.getParameter("page"));
		}
		
		item=request.getParameter("item");//이 item은 어디서 온걸까?
System.out.println("item의 출처 알아내기: " + item);
		//예상되는 흐름, WebContent.goods.goods_list.jsp <c:forEach var="item" items="${requestScope.itemList}">
		//
		
		if (request.getParameter("searchprice")==null || 
				request.getParameter("searchprice").equals("")) {
			itemList= goodsdao.item_List(item,page);
			count=goodsdao.getCount(item);
		} else {
			price=request.getParameter("searchprice");
			itemList= goodsdao.item_List(item,page,price);
			count=goodsdao.getCount(item, price);
		}
		
		int pageSize=12;
		int pageCount=count/pageSize+(count % pageSize==0?0:1);
		int startPage=(int)((page-1)/10)*10+1;
		int endPage=startPage+10-1;
		if (endPage>pageCount) endPage=pageCount;
		
		request.setAttribute("itemList", itemList);
		request.setAttribute("category", item);
System.out.println("category로 아이템을 리퀘스트 영역에 저장한다.: " + item);
		request.setAttribute("count", count);
		request.setAttribute("price", price);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		
		forward.setRedirect(false);
		forward.setPath("./goods/goods_list.jsp");
		return forward;
	}
}
