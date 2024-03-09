<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>ARGUS::AKPMS</title>
<!--  <link href="../css/style.css" type="text/css" rel="stylesheet" />-->
<link rel="stylesheet" type="text/css" href="<c:url value="/static/resources/css/style.css"/>" />
<script type="text/javascript" src="<c:url value='/static/resources/js/jquery-1.8.2.min.js'/>"></script>
<script type="text/javascript" charset="utf-8">
	function chkVal(){
		if(($.trim( $('#u_n').val() )).length == 0 && ($.trim( $('#p_w').val() )).length == 0){
			$('#msg').text("Please enter your username/email and password.");
			return false;
		}

		if(($.trim( $('#u_n').val() )).length == 0 ){
			$('#msg').text("Please enter your username/email.");
			return false;
		}

		if(($.trim( $('#p_w').val() )).length == 0){
			$('#msg').text("Please enter your password.");
			return false;
		}

		if(($.trim( $('#u_n').val() )).length != $('#u_n').val().length){
			$('#msg').text("Either the username/email or the password is incorrect. Please try again.");
			return false;
		}

		return true;
	}
</script>
</head>
<body>
<!-- Main Login Container Start -->
<div id="login-container">
  <!-- Logo Start -->
  <div id="logo-container"><img src="<c:url value="/static/resources/img/logo.png"/>" alt="" /></div>
  <!-- Logo End -->
  <!-- Form Container Start -->

  <form name='f' action="<c:url value='j_spring_security_check' />"
		method='post' onsubmit="return chkVal();">
  <div id="login-form-container">
    <div class="login-form-outer">
      <div class="login-form-inner">
        <div class="login-txt">Login to your account</div>
        <a href="<c:url value='/forget' />" class="forgot-pass">Forgot Password ?</a>
        <div class="clr"></div>
        <div id="msg" class="error">
        	<p id="msgP" class="success">
        	<c:if test="${not empty param['error']}">
        		<span id="spnErr" class="error">${SPRING_SECURITY_LAST_EXCEPTION.message}</span>
        	</c:if>
        	${mailSend}</p>
        </div>
        <div class="user-name">
          <input type="text" title="Enter Your Username/email" name='j_username' id="u_n"  onfocus="if (this.value==this.defaultValue){this.value=''};" onblur="if (this.value==''){this.value=this.defaultValue};" />
        </div>
        <div class="password">
          <input type="password" title="Enter Your Password" name='j_password' id="p_w" onfocus="if (this.value==this.defaultValue){this.value=''};" onblur="if (this.value==''){this.value=this.defaultValue};" />
        </div>
        <div class="login-btn-container">
          <div class="checkbox-container">
          <!--<span class="checkbox-input">
            <input type="checkbox" />
            </span><span class="checkbox-txt"> Remember Me</span>-->
            	<jsp:useBean id="now" class="java.util.Date"/>
				<strong style="color: #6D6C6C;"><fmt:formatDate value="${now}" timeZone="PST" timeStyle="short"  dateStyle="medium"  pattern="E, MMM dd, yyyy HH:mm a z"/></strong>
          </div>
          <input type="submit" title="login" value="login" class="login-btn"/>
          <div class="clr"></div>
        </div>
      </div>
      <!-- Shadow Box Start -->
    </div>
    <!-- Shadow Box End -->
  </div>
  </form>
  <!-- Form Container End -->
</div>
<!-- Main Login Container End -->
</body>
</html>
