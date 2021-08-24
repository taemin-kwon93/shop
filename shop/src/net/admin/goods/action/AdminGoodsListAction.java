
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
		//select * from goods order by goods_num ������ ���� ��ϵ� �����(goodslist)�� �޾ƿ�.
		
		request.setAttribute("list",list);//������Ʈ ������ list�� ���
		
		forward.setRedirect(false);//������ �ϱ� ���ؼ� �����̷�Ʈ�� false�� ����
		forward.setPath("./admingoods/admin_goods_list.jsp");
		return forward;
	}
}
