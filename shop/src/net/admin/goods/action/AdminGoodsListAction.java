
package net.admin.goods.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.goods.db.*;

public class AdminGoodsListAction implements Action {
	
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) {
		
		AdminGoodsDAO agoodsdao=new AdminGoodsDAO();
		GoodsBean agb=new GoodsBean();
		ActionForward forward=new ActionForward();
		
		Collection list=(Collection)agoodsdao.getGoodsList();
		//select * from goods order by goods_num 쿼리를 통해 등록된 내용들(goodslist)을 받아옴.
		
		request.setAttribute("list",list);//리퀘스트 영역에 list를 등록
		
		forward.setRedirect(false);//포워딩 하기 위해서 리다이렉트를 false로 저장
		forward.setPath("./admingoods/admin_goods_list.jsp");
		return forward;
	}
}
