<%@ page import="com.payment.vo.payhistory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="com.payment.factory.DAOFactory"%>
<%@ page import="java.util.* "%>

<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
<title>二维码支付</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="css/index.css">
<script src="js/jquery-3.2.0.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/ajaxfileupload.js"></script>
<script src="js/index.js"></script>
</head>
<body style="background-color: black;">
	<!-- 导航栏 -->
	<nav class="navbar navbar-inverse" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="index.jsp">二维码现金支付</a>
			</div>
			<div>
				<ul id="unlogined" class="nav navbar-nav">
					<li><a href="#" data-toggle="modal" data-target="#login">登录</a></li>
					<li><a href="#" data-toggle="modal" data-target="#register">注册</a></li>
				</ul>
				<ul id="logined" style="display: none" class="nav navbar-nav">
					<li><a href="#"><span>欢迎，</span><span id="userid"
							name="userid"></span></a></li>
					<li><a href="#" onclick="klose();gather()">收款</a></li>
					<li><a href="#" onclick="klose();payOpen()">付款</a></li>
					<li><a href="#" onclick="klose();searchBalance()">查询余额</a></li>
					<li><a href="#" onclick="klose();jmp2History()">历史记录</a></li>
					<li><a href="#" onclick="logout()">退出登录</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<div class="modal fade" id="register" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">
						<span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;&nbsp;&nbsp;Register
					</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="input-group">
							<span class="input-group-addon">用户名</span> <input id="username1"
								type="text" class="form-control" placeholder="username">
						</div>
						<br>
						<div class="input-group">
							<span class="input-group-addon">
								密码&nbsp;&nbsp;&nbsp;&nbsp;</span> <input id="password1"
								type="password" class="form-control" placeholder="password">
						</div>
						<br>
						<div class="input-group" style="float: right">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">返回</button>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<button type="button" class="btn btn-primary"
								onclick="register()">确定</button>
						</div>
						<br> <br>
					</form>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	<div class="modal fade" id="login" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">
						<span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;&nbsp;&nbsp;Login
					</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="input-group">
							<span class="input-group-addon">用户名 </span> <input id="username"
								type="text" class="form-control" placeholder="username">
						</div>
						<br>
						<div class="input-group">
							<span class="input-group-addon">
								密码&nbsp;&nbsp;&nbsp;&nbsp;</span> <input id="password"
								type="password" class="form-control" placeholder="password">
						</div>
						<br>
						<div class="input-group" style="float: right">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">返回</button>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<button type="button" class="btn btn-primary" onclick="login()">确定</button>
						</div>
						<br> <br>
					</form>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-12" id="gatherDiv"
				style="margin: 0 auto; display: none">
				<img src="" id="gatherCode">
			</div>
			<div class="col-12 panel" id="payDiv"
				style="margin: 0 auto; display: none">
				<form style="background-color: black;">
					<div class="input-group">
						<input type="file" class="file" accept="image/png,image/jpg"
							capture="camera" />
					</div>
					<br>
					<div class="input-group">
						<span class="input-group-addon">金额</span> <input id="money"
							type="text" class="form-control" placeholder="">
					</div>
					<br>

					<div class="input-group" style="float: left">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="btn btn-primary" onclick="upload();">确定支付</button>
					</div>
					<br> <br>
				</form>
			</div>
			<div class="col-12" id="balanceDiv"
				style="margin: 0 auto; display: none; font: white;">
				<form class="form-horizontal" role="form">
					<div class="form-group">
						<label for="inputPassword" class="col-sm-2 control-label"
							style="color: white;"> 用户名</label>
						<div class="col-sm-10">
							<input class="form-control" id="balance_userid" type="text"
								disabled>
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="col-sm-2 control-label"
							style="color: white;"> 余额</label>
						<div class="col-sm-10">
							<input class="form-control" id="balance" type="text" disabled>
						</div>
					</div>
				</form>
			</div>
			<div class="col-12" id="historyDiv"
				style="margin: 0 auto; display: none">
				<!-- 历史记录 -->
				<%
					request.setCharacterEncoding("utf-8");
				%>
				<%
					try {
						String keyWord = request.getParameter("kw");
						System.out.println("keyword:" + keyWord);
						if (keyWord == null) {
							keyWord = "";
						}
						List<payhistory> all = DAOFactory.getPayhistoryDAOInstance().findAll(keyWord);
						Iterator<payhistory> iter = all.iterator();
				%>
				<table border="1">
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