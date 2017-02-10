<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>防御方案自动生成系统</title>
<script src="<%=basePath%>static/js/jquery-1.6.4.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="<%=basePath%>static/css/login/login.css" type="text/css"><nk>
<script type="text/javascript">
$(function(){
	//得到焦点
	$("#password").focus(function(){
		$("#left_hand").animate({
			left: "150",
			top: " -38"
		},{step: function(){
			if(parseInt($("#left_hand").css("left"))>140){
				$("#left_hand").attr("class","left_hand");
			}
		}}, 2000);
		$("#right_hand").animate({
			right: "-64",
			top: "-38px"
		},{step: function(){
			if(parseInt($("#right_hand").css("right"))> -70){
				$("#right_hand").attr("class","right_hand");
			}
		}}, 2000);
	});
	//失去焦点
	$("#password").blur(function(){
		$("#left_hand").attr("class","initial_left_hand");
		$("#left_hand").attr("style","left:100px;top:-12px;");
		$("#right_hand").attr("class","initial_right_hand");
		$("#right_hand").attr("style","right:-112px;top:-12px");
	});
	$(document).ready(function(){
		  $("#login_event").click(function(){
			  var userVerify=$("#username").val();
				var pwdVerify=$("#password").val();
				//alert(userVerify);
				if(userVerify && pwdVerify){
					$.ajax({
						 url:"../user/login", 
						data:{
							username : userVerify,
							password : pwdVerify
						},
						type:"post",
						success:function(res){
							//alert(res.url);
							//alert(res.back);
							if(res.back=='success'){
								window.location.href = "http://localhost:8080/defence/pages/systemmanager/" + res.url+".html";
							}else{
								alert(res.msg);
							}
						},
						error:function(res){
							alert('出错了');
						}
					});
				}
		  });
		});
});
</script>
</head>
<body>
<div class="top_div"></div>
<div style="background: rgb(255, 255, 255); margin: -100px auto auto; border: 1px solid rgb(231, 231, 231); border-image: none; width: 400px; height: 200px; text-align: center;">
    <div style="width: 165px; height: 96px; position: absolute;">
        <div class="tou"></div>
        <div class="initial_left_hand" id="left_hand"></div>
        <div class="initial_right_hand" id="right_hand"></div>
    </div>
    <p class="username">
        <span class="u_logo"></span>         
        <input class="ipt" id="username" type="text" placeholder="请输入用户名" value=""> 
    </p>
    <p class="password">
        <span class="p_logo"></span>         
        <input class="ipt" id="password" type="password" placeholder="请输入密码" value="">   
    </p>
    <div class="operate_all">
        <p class="operate_all_in">
            <span class="forget_password_out">
                <a class="forget_password_in" href="#" >忘记密码?</a>
            </span> 
            <span class="register_login_out">
                <a class="register_login_in_register" href="#">注册</a>  
                <a class="register_login_in_login" id="login_event"">登录</a> 
            </span>         
        </p>
    </div>
</div>
<div style="text-align:center;"></div>
<div class="bottom_bg">
    <div class="bottom">版权所有:胡潇云   地址:北京市海淀区学院路37号北京航空航天大学   邮编:100083   Email:xiaoyunhu1992@outlook.com</div>
</div>
</body>
</html>