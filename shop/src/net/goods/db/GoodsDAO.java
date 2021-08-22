package net.goods.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class GoodsDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	//DB����
	public GoodsDAO(){
		try{
			Context initCtx=new InitialContext();
			Context envCtx=(Context)initCtx.lookup("java:comp/env");
			DataSource ds=(DataSource)envCtx.lookup("jdbc/OracleDB");
			con=ds.getConnection();
		}catch(Exception ex){
			System.out.println("DB ���� ���� : " + ex);
			return;
		}
	}
	
	//List����, ��ǰ ����Ʈ �̾ƿ���
	public List item_List(String item, int page) {
		List itemList = new ArrayList();
		
		int startnum=page*12-11;
		int endnum=page*12;
		
		try {
			StringBuffer findQuery = new StringBuffer();
			
			findQuery.append("SELECT * FROM (SELECT GOODS_NUM,");
			findQuery.append("GOODS_CATEGORY, GOODS_NAME, ");
			findQuery.append("GOODS_CONTENT,GOODS_PRICE,GOODS_IMAGE,");
			findQuery.append("GOODS_BEST,GOODS_DATE, rownum r FROM ");
			findQuery.append("GOODS WHERE ");
			
			if (item.equals("new_item")) {
				findQuery.append("GOODS_DATE>=GOODS_DATE-7");
			}else if (item.equals("hit_item")) { 
				findQuery.append("GOODS_BEST=1 ");
			}else{
				findQuery.append("GOODS_CATEGORY=? ");
			}
			findQuery.append("ORDER BY GOODS_NUM DESC) ");
			findQuery.append("WHERE r>=? AND r<=? ");		
			
			pstmt = con.prepareStatement(findQuery.toString(), 
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY );
			
			if (item.equals("new_item") || item.equals("hit_item")){
				pstmt.setInt(1, startnum);
				pstmt.setInt(2, endnum);
			}else {
				pstmt.setString(1, item);
				pstmt.setInt(2, startnum);
				pstmt.setInt(3, endnum);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				GoodsBean goodsbean = new GoodsBean();
				goodsbean.setGOODS_NUM(rs.getInt("GOODS_NUM"));
				goodsbean.setGOODS_CATEGORY(
						rs.getString("GOODS_CATEGORY"));
				goodsbean.setGOODS_NAME(rs.getString("GOODS_NAME"));
				goodsbean.setGOODS_PRICE(rs.getInt("GOODS_PRICE"));
				
				StringTokenizer st=new StringTokenizer(
						rs.getString("GOODS_IMAGE"),",");
				String firstImg=st.nextToken();					 					
				goodsbean.setGOODS_IMAGE(firstImg);				
				goodsbean.setGOODS_BEST(rs.getInt("GOODS_BEST"));
								
				itemList.add(goodsbean);
			} 		
				
			rs.close();			
			pstmt.close();
			
			return itemList;
		} catch(SQLException e) {
			e.printStackTrace(); 
		}

		return itemList;
	}
	
	//�������ǿ� �´� ������ �̾ƿ���
	public List item_List(String item, int page, String searchprice) {
		List itemList=new ArrayList();
		int startnum=page*12-11;
		int endnum=page*12;
		
		int firstprice=0;
		int secondprice=0;
		
		if(searchprice.equals("1~3")){
			firstprice=1;
			secondprice=29999;
		} else if (searchprice.equals("3~5")) {
			firstprice=30000;
			secondprice=49999;
		} else if (searchprice.equals("5~7")) {
			firstprice=50000;
			secondprice=69999;
		} else if (searchprice.equals("7~10")) {
			firstprice=70000;
			secondprice=99999;
		} else {
			firstprice=100000;
			secondprice=999999;
		}
		
		try {
			StringBuffer findQuery = new StringBuffer();
			
			findQuery.append("SELECT * FROM (SELECT GOODS_NUM, ");
			findQuery.append("GOODS_CATEGORY, GOODS_NAME, ");
			findQuery.append("GOODS_CONTENT,GOODS_PRICE,GOODS_IMAGE,");
			findQuery.append("GOODS_BEST, rownum r FROM GOODS WHERE ");
			if (item.equals("new_item")){
				findQuery.append("GOODS_DATE>=GOODS_DATE-7");
			}else if (item.equals("hit_item")) { 
				findQuery.append("GOODS_BEST=1 ");
			}else {
				findQuery.append("GOODS_CATEGORY=? ");
			}
			findQuery.append(" AND (GOODS_PRICE BETWEEN ? AND ? ) ");
			findQuery.append("ORDER BY GOODS_NUM DESC) ");
			findQuery.append("WHERE r>=? AND r<=? ");		
			
			pstmt = con.prepareStatement(findQuery.toString(), 
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY );
			
			if (item.equals("new_item") || item.equals("hit_item")) {
				pstmt.setInt(1, firstprice);
				pstmt.setInt(2, secondprice);
				pstmt.setInt(3, startnum);
				pstmt.setInt(4, endnum);
			} else {
				pstmt.setString(1, item);
				pstmt.setInt(2, firstprice);
				pstmt.setInt(3, secondprice);
				pstmt.setInt(4, startnum);
				pstmt.setInt(5, endnum);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				GoodsBean goodsbean = new GoodsBean();
				goodsbean.setGOODS_NUM(rs.getInt("GOODS_NUM"));
				goodsbean.setGOODS_CATEGORY(
						rs.getString("GOODS_CATEGORY"));
				goodsbean.setGOODS_NAME(rs.getString("GOODS_NAME"));
				goodsbean.setGOODS_PRICE(rs.getInt("GOODS_PRICE"));
				
				StringTokenizer st=new StringTokenizer(
						rs.getString("GOODS_IMAGE"),",");
				String firstimg=st.nextToken();
				
				goodsbean.setGOODS_IMAGE(firstimg);
				goodsbean.setGOODS_BEST(rs.getInt("GOODS_BEST"));
				
				itemList.add(goodsbean);
			} 		
			
			rs.close();			
			pstmt.close();
				
			return itemList;
		} catch(SQLException e){
			e.printStackTrace();
		}
			
		return itemList;
	}
	
	public GoodsBean findDetail(int goods_num, String item, String price,String direction) {
		GoodsBean goods=new GoodsBean();
		
		int firstprice=0;
		int secondprice=0;
		
		if(price.equals("1~3")) {
			firstprice=1;
			secondprice=29999;
		} else if (price.equals("3~5")) {
			firstprice=30000;
			secondprice=49999;
			
		} else if (price.equals("5~7")) {
			firstprice=50000;
			secondprice=69999;
		} else if (price.equals("7~10")) {
			firstprice=70000;
			secondprice=99999;
		} else if (price.equals("10")){
			firstprice=100000;
			secondprice=999999;
		}
		
		StringBuffer dQuery = new StringBuffer();
		if(direction.equals("next")){
			dQuery.append("SELECT GOODS_NUM,GOODS_CATEGORY,");
			dQuery.append("GOODS_IMAGE,GOODS_NAME FROM GOODS ");
			dQuery.append("WHERE GOODS_NUM>? AND ");
			if(item.equals("new_item")) {
				dQuery.append("GOODS_DATE>=GOODS_DATE-7");
			} else if (item.equals("hit_item")) {
				dQuery.append(" GOODS_BEST=1 ");
			} else {
				dQuery.append(" GOODS_CATEGORY=? ");			
			}
			if (!price.equals("no")) {
				dQuery.append(" AND (GOODS_PRICE BETWEEN ? AND ? ) ");
			}
		}else if(direction.equals("prev")){
			dQuery.append(
			"SELECT GOODS_NUM,GOODS_CATEGORY,GOODS_IMAGE,");
			dQuery.append(
			"GOODS_NAME FROM GOODS WHERE GOODS_NUM<? AND ");
			if(item.equals("new_item")) {
				dQuery.append("GOODS_DATE>=GOODS_DATE-7");
			} else if (item.equals("hit_item")) {
				dQuery.append(" GOODS_BEST=1 ");
			} else {
				dQuery.append(" GOODS_CATEGORY=? ");			
			}
			if (!price.equals("no")) {
				dQuery.append(" AND (GOODS_PRICE BETWEEN ? AND ? ) ");
			}
			dQuery.append("ORDER BY GOODS_NUM DESC ");
		}
		
		try {
			pstmt = con.prepareStatement(dQuery.toString(), 
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY );
			
			if (item.equals("new_item") || item.equals("hit_item")){
				if (price.equals("no")) {
					pstmt.setInt(1, goods_num);
				} else {
					pstmt.setInt(1, goods_num);
					pstmt.setInt(2, firstprice);
					pstmt.setInt(3, secondprice);
				}
			} else {
				if (price.equals("no")) {
					pstmt.setInt(1, goods_num);
					pstmt.setString(2, item);
				} else{
					pstmt.setInt(1, goods_num);
					pstmt.setString(2, item);
					pstmt.setInt(3, firstprice);
					pstmt.setInt(4, secondprice);
				}
			}
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				goods.setGOODS_NUM(rs.getInt("GOODS_NUM"));
				goods.setGOODS_CATEGORY(
						rs.getString("GOODS_CATEGORY"));
				goods.setGOODS_NAME(rs.getString("GOODS_NAME"));
				StringTokenizer st=new StringTokenizer(
						rs.getString("GOODS_IMAGE"),",");
				goods.setGOODS_IMAGE(st.nextToken());
			}
			
			rs.close();			
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return goods;	
	}
	
	//��ǰ �󼼺���
	public GoodsBean findDetailList(int goods_num, String item){
		GoodsBean goods=new GoodsBean();
		
		try {
			StringBuffer dQuery = new StringBuffer();
		
			dQuery.append("SELECT *");		
			dQuery.append(" FROM GOODS WHERE GOODS_NUM=?  AND ");
			
			if(item.equals("new_item")) {
				dQuery.append("GOODS_DATE>=GOODS_DATE-7");
			} else if (item.equals("hit_item")) {
				dQuery.append("GOODS_BEST=1 ");
			} else {
				dQuery.append("GOODS_CATEGORY=? ");	
			}
			
			pstmt = con.prepareStatement(dQuery.toString(), 
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY );
			if (item.equals("new_item") || item.equals("hit_item")){
				pstmt.setInt(1, goods_num);
			} else {
				pstmt.setInt(1, goods_num);
				pstmt.setString(2, item);
			}
			
			rs = pstmt.executeQuery();			
			
			if (rs.next()) {	
				goods.setGOODS_NUM(rs.getInt("GOODS_NUM"));
				goods.setGOODS_CATEGORY(
						rs.getString("GOODS_CATEGORY"));
				goods.setGOODS_NAME(rs.getString("GOODS_NAME"));
				goods.setGOODS_CONTENT(
						rs.getString("GOODS_CONTENT"));
				goods.setGOODS_SIZE(rs.getString("GOODS_SIZE"));
				goods.setGOODS_COLOR(rs.getString("GOODS_COLOR"));
				goods.setGOODS_AMOUNT(rs.getInt("GOODS_AMOUNT"));
				goods.setGOODS_PRICE(rs.getInt("GOODS_PRICE"));
				goods.setGOODS_IMAGE(rs.getString("GOODS_IMAGE"));
				goods.setGOODS_BEST(rs.getInt("GOODS_BEST"));					
			}
			
			rs.close();			
			pstmt.close();
//System.out.println("Goods find detail");
			return goods;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int getCount(String item) {
		int count=0;
		
		StringBuffer findQuery = new StringBuffer();
		
		findQuery.append("SELECT COUNT(GOODS_NUM) FROM GOODS WHERE ");
		
		if (item.equals("new_item")) {
			findQuery.append("GOODS_DATE>=GOODS_DATE-7");
		} else if (item.equals("hit_item")) { 
			findQuery.append("GOODS_BEST=? ");
		}else {
			findQuery.append("GOODS_CATEGORY=?");
		}
		
		try{
			pstmt=con.prepareStatement(findQuery.toString());
			if (item.equals("new_item")){
			}else if (item.equals("hit_item")) {
				pstmt.setInt(1,1);
			}else{
				pstmt.setString(1, item);
			}
			
			rs=pstmt.executeQuery();
			rs.next();
			
			count=rs.getInt(1);
			
			rs.close();
			pstmt.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return count;
	}
	
	public int getCount(String item, String searchprice) {
		int count=0;
		int firstprice=0;
		int secondprice=0;
		
		if(searchprice.equals("1~3")) {
			firstprice=1;
			secondprice=29999;
		} else if (searchprice.equals("3~5")) {
			firstprice=30000;
			secondprice=49999;
		} else if (searchprice.equals("5~7")) {
			firstprice=50000;
			secondprice=69999;
		} else if (searchprice.equals("7~10")) {
			firstprice=70000;
			secondprice=99999;
		} else {
			firstprice=100000;
			secondprice=999999;
		}
		
		StringBuffer findQuery = new StringBuffer();
		
		findQuery.append("SELECT COUNT(GOODS_NUM) FROM GOODS WHERE ");
		if (item.equals("new_item")) {
			findQuery.append("GOODS_DATE>=GOODS_DATE-7");
		} else if (item.equals("hit_item")) { 
			findQuery.append("GOODS_BEST=? ");
		}else {
			findQuery.append("GOODS_CATEGORY=?");
		}
		findQuery.append(" and (goods_price between ? and ?)");
		
		try
		{
			pstmt=con.prepareStatement(findQuery.toString());
			
			if(item.equals("new_item")){
				pstmt.setInt(1, firstprice);
				pstmt.setInt(2, secondprice);
			}else if (item.equals("hit_item")) {
				pstmt.setInt(1, 1);
				pstmt.setInt(2, firstprice);
				pstmt.setInt(3, secondprice);
			}else{
				pstmt.setString(1, item);
				pstmt.setInt(2, firstprice);
				pstmt.setInt(3, secondprice);
			}
			
			rs=pstmt.executeQuery();
			rs.next();
			count=rs.getInt(1);
			
			rs.close();
			pstmt.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return count;
	}
}