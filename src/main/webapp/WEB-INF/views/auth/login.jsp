<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <!--<![endif]-->
    <!-- BEGIN HEAD -->

    <head>
        <meta charset="utf-8" />
        <title>系统登陆</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <script type="text/javascript" src="//upcdn.b0.upaiyun.com/libs/jquery/jquery-1.10.2.min.js"></script>
    <!-- END HEAD -->
   
 	</head>
    <body class=" login">
        
        <!-- END LOGO -->
        <!-- BEGIN LOGIN -->
        <div class="content">
            <!-- BEGIN LOGIN FORM -->
            <form id="loginForm" class="login-form" action="${ctx}/auth/login" method="post">
                <h3 class="form-title font-green">登陆</h3>
                <div class="alert alert-danger display-hide">
                    <button class="close" data-close="alert"></button>
                    <span> 请输入用户名和密码. </span>
                </div>
                <div class="form-group">
                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                    <label class="control-label visible-ie8 visible-ie9">用户名</label>
                    <input class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off" placeholder="Username" name="userName" /> </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">密码</label>
                    <input class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off" placeholder="Password" name="passwd" /> </div>
                <div class="form-actions">
                    <button type="submit" class="btn green uppercase">登录</button>
                    <button type="button" onclick="login()">ajax登录</button>
                </div>
                
            </form>
            <!-- END LOGIN FORM -->
             
             
        </div>
        <script type="text/javascript">
        
        	function login(){
        		$.ajax({
        			type:"post",
        			data:$("#loginForm").serialize(),
        			url:"${ctx}/auth/ajaxLogin",
        			success:function(data){
        				alert(data);
        			},
        			error:function (xhr, ajaxOptions, thrownError){
        		        alert(xhr.status);
        		        alert(xhr.statusText);
        		        alert(xhr.responseText);
        		    }
        		});
        		
        	}
        </script>
    </body>

</html>