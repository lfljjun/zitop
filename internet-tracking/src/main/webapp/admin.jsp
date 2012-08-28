<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />

<title>中国电信-拨测与评测平台</title>
<link href="${pageContext.request.contextPath}/static/css/login.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="${pageContext.request.contextPath}/static/js/png.js" type="text/javascript"></script>
<style>
<!--
.loginBorderCenter{behavior:url(${pageContext.request.contextPath}/static/js/iepngfix.htc);}
body {background-image:url(${pageContext.request.contextPath}/static/images/bg_login.jpg); background-repeat:repeat-x;}
-->
</style>
<script type="text/javascript">
      if (top != window)   
      top.location.href = window.location.href; 
</script>
</head>
<body>
<div class="adminLogin">
   <div class="loginBorder">
     <div class="loginBorderTop"><img src="${pageContext.request.contextPath}/static/images/loginBorderTop.png" /></div>
	 <div class="loginBorderCenter">
	 <!--login -->
	 <form name="f" action="<%=request.getContextPath() %>/j_spring_security_check" method="post">
	   <div class="logo"><img src="${pageContext.request.contextPath}/static/images/logo.gif" /></div>
	   <div class="TypeWrong">
	   		<%
	   		 String type =request.getParameter("__errtype");
	   		 if("1".equalsIgnoreCase(type))
	   		 {
	   			 out.println("验证码有误");
	   		 }
	   		 else if("2".equalsIgnoreCase(type))
	   		 {
	   			 out.println("用户名或密码错误");
	   		 }
	   		 else if("3".equalsIgnoreCase(type))
	   		 {
	   			 out.println("用户名或密码有误");
	   		 }
	   		 String j_username= request.getParameter("j_username");
	   		%> 
	   </div>
	   <div class="loginName">用户名</div>
	   <div class="loginType"><input type='text' name='j_username' class="loginInput" value="<%=j_username==null?"":j_username %>"/> </div>
	   <div class="loginName">密&nbsp;&nbsp;&nbsp;码</div>
	   <div class="loginType"><input type='password' name='j_password' class="loginInput"/></div>
	   <div class="loginName">验证码</div>
	   <div class="loginType"><input  class="loginYzm" name="validateCode" type="text" /><i>输入以下字符</i></div>
	   <div class="loginYzmimg">
	   <a href="javascript:reloadcode();">
	   <img width="99px" height="33px" height id="captcha_img" src="<%=request.getContextPath() %>/captcha" /><a href="javascript:reloadcode();">点击换图</a>
	   <script language="JavaScript">
			function reloadcode(){
				var verify=document.getElementById('captcha_img');
				verify.setAttribute('src','<%=request.getContextPath() %>/captcha?'+Math.random());
			}
		</script>
	   </a>
	   </div>
	   <div class="loginName">
	   </div>
	   <!-- 
	   <div class="loginType"><input type="checkbox" name="_spring_security_remember_me"/>记住我，两周内不再登录</div>
	    -->
	   <div class="loginType"><button class="btn" type="submit" >登&nbsp;&nbsp;&nbsp;录</button></div>
	   <div class="loginType" style="margin-top:15px;"><a href="register.htm"></a></div>
	 </form>
	 <!--end login -->
	 </div>
	 <div class="loginBorderBottom"><img src="${pageContext.request.contextPath}/static/images/loginBorderBottom.png" /></div>
   </div>
   
   <div class="footer">Copyright &copy; 2011 &nbsp;All Rights Reserved</div>

</div>

</body>
</html>


