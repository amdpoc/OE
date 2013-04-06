<%@ page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />

<link href="css/ui.css" rel="stylesheet">
<link href="css/ui-local.css" rel="stylesheet">

<link href="css/login.css" rel="stylesheet" type="text/css" />
<link href="css/ui-responsive.css" rel="stylesheet">

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

<link rel="apple-touch-icon" href="images/eSignIcon.png"/>
<meta content="text/html; charset=iso-8859-1" http-equiv="Content-Type" />
<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;"/>
<meta content="Amdocs Order Entry" name="keywords" />
<meta content="Amdocs Order Entry" name="description" />
<title>Amdocs Order Entry</title>

</head>

<body onload='document.loginForm.j_username.focus();'>

	<div class="container">
        <div class="span4 offset4">
        <form id="loginForm" name="loginForm" class="loginForm selected="true" action="j_spring_security_check" method="post" target="_self">
			<fieldset>
			<legend>Order Entry</legend>
            <span id="successmessage" class="successmessage help-block">
                <c:choose>
                    <c:when test="${not empty param.loggedout}">
                        You have been successfully logged out.
                    </c:when>
                    <c:otherwise>
                        <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION.message}">
                            Login failed due to: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </span>
            <label>User Name</label>
            <input id="usernameField" type="text" name="j_username" autocorrect="off" autocapitalize="off" value="<c:out value="${SPRING_SECURITY_LAST_USERNAME}"/>" class="input-block-level" placeholder="User Name">
            <label>User Password</label>
            <input id="passwordField" type="password" name="j_password" autocorrect="off" autocapitalize="off" class="input-block-level" placeholder="Password">
            <div class="span3">
                <button class="btn btn-primary" type="submit">Sign in</button>
            </div>
        </form>
        </div>
    </div>

    <!--div id="home" class="current">

		<div id="loginContent">
			<form id="loginForm" name="loginForm" selected="true" action="j_spring_security_check" method="post" target="_self">
                <input id="usernameField" type="text" name="j_username" autocorrect="off" autocapitalize="off" value="<c:out value="${SPRING_SECURITY_LAST_USERNAME}"/>" class="input-block-level" placeholder="User Name">
                <input id="passwordField" type="password" name="j_password" autocorrect="off" autocapitalize="off" class="input-block-level" placeholder="Password">
                <button class="btn btn-primary" type="submit">Sign in</button>
            </form>
			    
		</div>
	</div-->

	<!--div id="mainLogo">Order Entry</div-->
	<div class="poweredBy">Powered by<br><img src="images/amdocs logo grey bkg.jpg"></img></div>
	<!--div class="companyLogo">&nbsp;</div-->
</body>
	