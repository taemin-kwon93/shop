package net.member.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	/* 클래스 블럭에서 전부 사용할수 있게
	 * Connction, PreparedStatement, ResultSet을 선언했다. */
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	
	public MemberDAO() {
		/* MemberDAO 객체를 선언하면 try구문 안에 로직이 실행된다. */
		try{
			Context initCtx=new InitialContext();/*Context객체를 생성, tomcat에 
			naming service 메모리 영역에 접근하기 위해서는 Context객체를 생성해야 한다.*/
			Context envCtx=(Context)initCtx.lookup("java:comp/env");/*initCtx를 이용해 lookup메소드를 실행하면,
			메모리에 접근하는 객체를 만들 수 있다.*/
			DataSource ds=(DataSource)envCtx.lookup("jdbc/OracleDB");/*
			위의 내용들이 선행되면, META-INF내에 context.xml에 설정한
			Resource name="jdbc/OracleDB" 에 맞춰 소스에 설정된 내용을 받아온다.*/
			con=ds.getConnection();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public boolean insertMember(MemberBean mb) throws SQLException{
		String sql=null;
		
		try{
			sql="insert into member values "+
				"(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mb.getMEMBER_ID());
			pstmt.setString(2, mb.getMEMBER_PW());
			pstmt.setString(3, mb.getMEMBER_NAME());
			pstmt.setInt(4, mb.getMEMBER_JUMIN1());
			pstmt.setInt(5, mb.getMEMBER_JUMIN2());
			pstmt.setString(6, mb.getMEMBER_EMAIL());
			pstmt.setString(7, mb.getMEMBER_EMAIL_GET());
			pstmt.setString(8, mb.getMEMBER_MOBILE());
			pstmt.setString(9, mb.getMEMBER_PHONE());
			pstmt.setString(10, mb.getMEMBER_ZIPCODE());
			pstmt.setString(11, mb.getMEMBER_ADDR1());
			pstmt.setString(12, mb.getMEMBER_ADDR2());
			pstmt.setInt(13, mb.getMEMBER_ADMIN());
			pstmt.setTimestamp(14, mb.getMEMBER_JOIN_DATE());
			pstmt.executeUpdate();
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(pstmt != null){ pstmt.close();}

		}
		
		return false;
	}
	
	public int userCheck(String id, String pw) throws SQLException{
		String sql=null;
		int x=-1;
		
		try{
			sql="select MEMBER_PW from member where MEMBER_ID=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			if(rs.next()){
				String memberpw=rs.getString("MEMBER_PW");
				
				if(memberpw.equals(pw)){
					x=1;
				}else{
					x=0;
				}
			}else{
				x=-1;
			}
			
			return x;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(pstmt != null){ pstmt.close(); }
			if(rs != null){ rs.close(); }
			
		}
		
		return -1;
	}
	
	public int confirmId(String id) throws SQLException{
		String sql=null;
		int x=-1;
		
		try{
			sql="select MEMBER_ID from member where MEMBER_ID=? ";
			
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				x=1;
			}else{
				x=-1;
			}
			
			return x;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(pstmt != null){ pstmt.close(); }
			if(rs != null){ rs.close(); }
			
		}
		
		return -1;
	}
	
	public MemberBean getMember(String id) throws SQLException{
		MemberBean member=null;
		String sql=null;
		
		try{
			sql="select * from member where MEMBER_ID=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				member=new MemberBean();
				
				member.setMEMBER_ID(rs.getString("MEMBER_ID"));
				member.setMEMBER_NAME(rs.getString("MEMBER_NAME"));
				member.setMEMBER_JUMIN1(rs.getInt("MEMBER_JUMIN1"));
				member.setMEMBER_JUMIN2(rs.getInt("MEMBER_JUMIN2"));
				member.setMEMBER_EMAIL(rs.getString("MEMBER_EMAIL"));
				member.setMEMBER_EMAIL_GET(
						rs.getString("MEMBER_EMAIL_GET"));
				member.setMEMBER_MOBILE(
						rs.getString("MEMBER_MOBILE"));
				member.setMEMBER_PHONE(
						rs.getString("MEMBER_PHONE"));
				member.setMEMBER_ZIPCODE(
						rs.getString("MEMBER_ZIPCODE"));
				member.setMEMBER_ADDR1(rs.getString("MEMBER_ADDR1"));
				member.setMEMBER_ADDR2(rs.getString("MEMBER_ADDR2"));
				
				return member;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(pstmt != null){ pstmt.close(); }
			if(rs != null){ rs.close(); }
			
		}
		
		return null;
	}
	
	public void updateMember(MemberBean mb) throws SQLException{
		String sql=null;
		
		try{
			sql="update member set MEMBER_PW=?,MEMBER_NAME=?,"+
			"MEMBER_EMAIL=?,MEMBER_EMAIL_GET=?,MEMBER_MOBILE=?,"+
			"MEMBER_PHONE=?,MEMBER_ZIPCODE=?,MEMBER_ADDR1=?,"+
			"MEMBER_ADDR2=? where MEMBER_ID=?";
			
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mb.getMEMBER_PW());
			pstmt.setString(2, mb.getMEMBER_NAME());
			pstmt.setString(3, mb.getMEMBER_EMAIL());
			pstmt.setString(4, mb.getMEMBER_EMAIL_GET());
			pstmt.setString(5, mb.getMEMBER_MOBILE());
			pstmt.setString(6, mb.getMEMBER_PHONE());
			pstmt.setString(7, mb.getMEMBER_ZIPCODE());
			pstmt.setString(8, mb.getMEMBER_ADDR1());
			pstmt.setString(9, mb.getMEMBER_ADDR2());
			pstmt.setString(10, mb.getMEMBER_ID());			
			pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(pstmt != null){ pstmt.close();}
			
		}
	}
	
	public int deleteMember(String id, String pw) throws SQLException{
		String sql=null;
		int x=-1;
		
		try{
			sql="select MEMBER_PW from member where MEMBER_ID=?";
			
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				String memberpw=rs.getString("MEMBER_PW");
				if(memberpw.equals(pw)){
					sql="delete from member where MEMBER_ID=?";
					pstmt=con.prepareStatement(sql);
					pstmt.setString(1, id);
					pstmt.executeUpdate();
					x=1;
				}else{
					x=0;
				}
				
				return x;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(pstmt != null){ pstmt.close(); }
			
		}
		
		return -1;
	}	
	
	//이름, 주민번호 이용해서 Id 찾을때 사용하는 메소드
	//SQL select로 뽑아오는 쿼리가 관건이다.
	public MemberBean findId(String name, String jumin1, String jumin2)
	throws SQLException{
		String sql=null;
		MemberBean member=new MemberBean();
		
		try{
			sql="select MEMBER_ID, MEMBER_PW, MEMBER_JUMIN1,"+
				"MEMBER_JUMIN2 from member where MEMBER_NAME=?";
			
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, name);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				String memberjumin1=rs.getString("MEMBER_JUMIN1");
				String memberjumin2=rs.getString("MEMBER_JUMIN2");
				
				if(memberjumin1.equals(jumin1) && 
						memberjumin2.equals(jumin2)){
					member.setMEMBER_ID(
							rs.getString("MEMBER_ID"));
					member.setMEMBER_PW(
							rs.getString("MEMBER_PW"));
					
					return member;
				}
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(pstmt != null){ pstmt.close(); }
			if(rs != null){ rs.close(); }
			
		}
		
		return null;
	}
	
	//DB에서 MEMBER_ADMIN의 숫자 0은 일반회원을 뜻하고 1은 관리자를 뜻한다.
	public boolean isAdmin(String id){
		String sql="select MEMBER_ADMIN from MEMBER where MEMBER_ID=?";
		//멤버 테이블에서 ID값을 이용해 MEMBER_ADMIN값을 꺼내오는 내용을 String타입 sql에 저장한다.
		int member_admin=0;//기본적으로 member_admin에 일반회원 번호 0을 넣는다.
		
		try {
			pstmt=con.prepareStatement(sql);//저장된 쿼리문을 실행하여 pstmt에 담고
			pstmt.setString(1, id);//?표가 있던 자리에 id값을 넣는다.
			rs=pstmt.executeQuery();//쿼리 실행의 결과를 저장한다.
			rs.next();
			
			member_admin=rs.getInt("MEMBER_ADMIN");//쿼리 결과의 값을 int타입 변수, member_admin에 저장
			
			if(member_admin==1){//member_admin이 1이면 true 리턴.
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public List searchZipcode(String searchdong){
		String sql="select * from zipcode where dong like ?";
		List zipcodeList=new ArrayList();
		
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "%"+searchdong+"%");
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				String sido=rs.getString("sido");
				String gugun=rs.getString("gugun");
				String dong=rs.getString("dong");  
				String ri=rs.getString("ri"); 
				String bunji=rs.getString("bunji");
				if(ri == null) ri="";
				if(bunji == null) bunji="";
				
				String zipcode=rs.getString("zipcode");
				String addr=sido+ " "+gugun+ " "+dong+ " "+ri+ " "+bunji;
				
				zipcodeList.add(zipcode+","+addr);
			}
			
			return zipcodeList;
		} catch (SQLException e){
			e.printStackTrace();
		}
		
		return null;
	}
}