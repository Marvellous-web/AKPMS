<%@page import="net.tanesha.recaptcha.ReCaptcha"%>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>ARGUS</title>
<!-- <link href="css/style.css" type="text/css" rel="stylesheet" /> -->
<link rel="stylesheet" type="text/css"
	href="<c:url value="/static/resources/css/style.css"/>" />
<script type="text/javascript"
		src="<c:url value='/static/resources/js/jquery-1.8.2.min.js'/>"></script>
</head>
<body>
	<!-- Main Login Container Start -->
	<div id="login-container">
		<!-- Logo Start -->
		<div id="logo-container">
			<img src="<c:url value="/static/resources/img/logo.png"/>" alt="" />
		</div>
		<!-- Logo End -->
		<!-- Form Container Start -->
		<form:form commandName="user" method="post">
			<div id="login-form-container">
				<div class="login-form-outer">
					<div class="login-form-inner">
						<!-- <div class="login-txt">Login to your account</div>
        					<a href="#" class="forgot-pass">Forgot Password ?</a> -->
						<div class="clr"></div>
						<div class="error">
							${error}
						</div>

						<div class="user-name">
							<form:input path="email"
								onfocus="if (this.value==this.defaultValue){this.value=''};"
								onblur="if (this.value==''){this.value=this.defaultValue};"
								title="Enter Your Username/Email" value="Username/Email"/>

						</div>
						<div
							style="padding: 15px 0px 0px; margin: 0px auto; width: 300px;">
							<%
								ReCaptcha c = ReCaptchaFactory.newReCaptcha((String)request.getAttribute("publicKey"),
										(String)request.getAttribute("privateKey"), false);
									out.print(c.createRecaptchaHtml(null, null));
							%>
						</div>
						<div style="padding: 15px 0 0; text-align: center;">
							<input class="login-btn" type="submit" title="OK" value="OK" onclick="javascript:disabledButtons();this.form.submit();"/>
							<input class="login-btn" type="button" title="Cancel" value="Cancel" onclick="javascript:redirectLogin();" />
							<div class="clr"></div>
						</div>
					</div>
					<!-- Shadow Box Start -->
				</div>
				<!-- Shadow Box End -->
			</div>
		</form:form>
		<!-- Form Container End -->

	</div>
	<!-- Main Login Container End -->
	<script type="text/javascript">
		var contextPath = "<c:out value="${pageContext.request.contextPath}" />";

		function redirectLogin(){
			window.location.href = contextPath + "/login";
		}
		function disabledButtons(){
			$('input:submit').attr("disabled", "disabled");
			$('input:button').attr("disabled", "disabled");
		}

		$(function() {
		    $('input[type=text]').each(function() {
		        $.data(this, 'default', this.value);
		    }).css("color","gray")
		    .focus(function() {
		        if (!$.data(this, 'edited')) {
		            this.value = "";
		            $(this).css("color","black");
		        }
		    }).change(function() {
		        $.data(this, 'edited', this.value != "");
		    }).blur(function() {
		        if (!$.data(this, 'edited')) {
		            this.value = $.data(this, 'default');
		            $(this).css("color","gray");
		        }
		    });
		});
	</script>
</body>
</html>
