<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<%@ page import="net.admin.goods.db.*"%>
<%
	Collection list = (Collection) request.getAttribute("list");
	//������Ʈ �������� ���� list�� �޾ƿ�, AdminGoodsListAction 22�࿡�� list��� ��.
	Object obj[] = list.toArray();
	GoodsBean agb = null;
%>

<html>
<head>
<title>���θ�</title>
<script type="text/javascript">
function goodsdelete(goods_num){
	document.goodsform.action="./GoodsDelete.ag?goods_num="+goods_num;
	document.goodsform.submit();	
}
function goodsmodify(goods_num){
	document.goodsform.action="./GoodsModify.ag?goods_num="+goods_num;
	document.goodsform.submit();	
}
</script>
</head>
<body>
<table width="960" cellspacing="0" cellpadding="0" border="0"
	color="gray" align="center">
	<tr>
		<td colspan=2>
		<!-- ��ǰ ��� -->
		<table border="0" width="80%">
			<tr>
				<td height="22" bgcolor="#6699FF">
					<p align="center">
						<font size=2>��ǰ���</font>
					</p>
				</td>
			</tr>
			<tr>
				<td>
				<p align="right">
					<font size=2>
						<a href="./GoodsAdd.ag">��ǰ���</a>
					</font>
				</p>
				</td>
			</tr>
			<tr>
			<td>
			<form name=goodsform method="post">
			<table border="1">
			<tr>
				<td width="50">
				<p align="center"><font size=2>��ȣ</font></p>
				</td>
				<td width="141">
				<p align="center"><font size=2>ī�װ�</font></p>
				</td>
				<td width="100">
				<p align="center"><font size=2>����</font></p>
				</td>
				<td width="141">
				<p align="center"><font size=2>��ǰ��</font></p>
				</td>
				<td width="141">
				<p align="center"><font size=2>�ܰ�</font></p>
				</td>
				<td width="80">
				<p align="center"><font size=2>����</font></p>
				</td>
				<td width="141">
				<p align="center"><font size=2>�������</font></p>
				</td>
				<td width="100">
				<p align="center"><font size=2>&nbsp;</font></p>
				</td>
			</tr>
			<%
					for (int i = 0; i < list.size(); i++) {
					agb = (GoodsBean) obj[i];
			%>
			<tr>
			<td>
			<p align="center">
				<font size=2><%=agb.getGOODS_NUM()%></font>
			</p>
			</td>
			<td>
			<p align="center">
				<font size=2>
					<%=agb.getGOODS_CATEGORY()%>
				</font>
			</p>
			</td>
			<td>
			<p align="center"><img
			src="./upload/<%=agb.getGOODS_IMAGE().split(",")[0] %>"
			width="50" height="50" border="0"></p>
			</td>
			<td>
			<p align="center">
				<font size=2><%=agb.getGOODS_NAME()%></font>
			</p>
			</td>
			<td>
			<p align="center">
				<font size=2><%=agb.getGOODS_PRICE()%></font>
			</p>
			</td>
			<td>
			<p align="center">
				<font size=2><%=agb.getGOODS_AMOUNT()%></font>
			</p>
			</td>
			<td>
			<p align="center">
				<font size=2>
					<%=agb.getGOODS_DATE().substring(0,10) %>
				</font>
			</p>
			</td>
			<td>
			<p align="center">
			<a href="javascript:goodsmodify(<%=agb.getGOODS_NUM()%>);">
				<font size=2>����</font>
			</a>/
			<a href="javascript:goodsdelete(<%=agb.getGOODS_NUM()%>);">
				<font size=2>����</font>
			</a>
			</p>
			</td>
			</tr>
			<%
				}
			%>
			</table>
			</td>
			</tr>
			</form>
		</table>
		<!-- ��ǰ ��� -->
		</td>
	</tr>
</table>
</body>
</html>