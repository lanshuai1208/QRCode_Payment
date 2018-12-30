<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.payment.factory.DAOFactory"%>
<%@ page import="java.util.* "%>
<%@page import="com.payment.vo.payhistory"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
<title>历史记录</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="css/index.css">
<script src="js/jquery-3.2.0.js"></script>
<script src="js/jquery-params.js"></script>
<script src="js/jquery-params.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/history.js"></script>

</head>
<body style="background-color: black;">
	<!-- 历史记录 -->
	<input id="kw" name="kw" type="hidden">

	<div class="container">
		<div class="row">
			<div class="col-sm-12">
				<%
					request.setCharacterEncoding("utf-8");
				%>
				<%
					try {
						String keyWord = request.getParameter("kw");
						//String keyWord = "admin";
						System.out.println("keyword:" + keyWord);
						if (keyWord == null) {
							keyWord = "";
						}
						List<payhistory> all = DAOFactory.getPayhistoryDAOInstance().findAll(keyWord);
						Iterator<payhistory> iter = all.iterator();
				%>
				<center>
					<table border = "-2"class="table" style = "color:white;">
						<tr>
							<td>payer</td>
							<td>accepter</td>
							<td>money</td>
							<td>time</td>
						</tr>
						<%
							while (iter.hasNext()) {
									payhistory ph = iter.next();
						%>
						<tr>
							<td><%=ph.getPayer()%></td>
							<td><%=ph.getAccepter()%></td>
							<td><%=ph.getMoney()%></td>
							<td><%=ph.getTime()%></td>
						</tr>
						<%
							}
						%>
					</table>
				</center>
				<%
					} catch (Exception e) {
						e.printStackTrace();
					}
				%>
			</div>
		</div>
	</div>
</body>
</html>