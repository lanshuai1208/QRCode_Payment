function login() {
	$.ajax({
		type : "POST", // 传数据的方式
		url : "login", // servlet地址

		data : "username=" + $("#username").val() + "&password="
				+ $("#password").val(), // 传的数据 form表单 里面的数据
		success : function(result) { 
										
			if (result == "") {
				alert("用户名和密码不匹配");
			} else {
				document.getElementById("logined").style.display = "block";
				document.getElementById("unlogined").style.display = "none";
				$('#myModal').modal('hide');
				$(".modal-backdrop").remove();
				$("body").removeClass('modal-open');
				$("#userid").text(result);
				$("#temp").val(result);
			}
		}
	});
}
function logout() {
	document.getElementById("logined").style.display = "none";
	document.getElementById("unlogined").style.display = "block";
	klose();
}
function register(){
	$.ajax({
		type: "post",
		url: "register",
		data : "username=" + $("#username1").val() + "&password=" + $("#password1").val(),
		success : function(result){
			if(result){
				alert("注册成功");
			}else{
				alert("注册失败");
			}
		}
	});
}
function gather(){
	$.ajax({
		type:"post",
		url:"gather",
		data:"userid=" + $("#userid").text(),
		success:function(result){
			$("#gatherCode").attr('src',result),
			document.getElementById("gatherDiv").style.display="block";
		}
	});
}
function klose() {
	document.getElementById("gatherDiv").style.display = "none";
	document.getElementById("payDiv").style.display = "none";
	document.getElementById("balanceDiv").style.display = "none";
	document.getElementById("historyDiv").style.display = "none";
}
function payOpen(){
	document.getElementById("payDiv").style.display = "block";
}
function upload(){
	$.ajax({
		type:"post",
		url:"upload",
		data :"money=" + $("#money").val() + "&userid=" + $("#userid").text(),
		success :function(result){
			if(result == "out"){
				alert("二维码已失效");
			}else{
				$("#gatherCode").attr('src',result);
				document.getElementById("gatherDiv").style.display = "block";
			}
			
		}
	});
}
function haha(){
	alert("二维码已失效");
}
function searchBalance(){
	document.getElementById("balanceDiv").style.display = "block";
	var userid = $("#userid").text();
	$.ajax({
		type:"post",
		url:"searchBalance",
		data : "userid=" + userid,
		success: function(result){
			$("#balance_userid").val(userid);
			$("#balance").val(result);
		}
	});
}
function historyOpen(){
	alert("into historyDiv");
	document.getElementById("historyDiv").style.display = "block";
}
function jmp2History(){
	var kw = $("#userid").text();
	url = "history.jsp?kw=" + kw;
	window.location.href = url;
}