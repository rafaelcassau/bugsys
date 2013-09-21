<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Bugsys - Login</title>
	
		<link rel="stylesheet" type="text/css" href="<c:url value="/assets/bootstrap/css/bootstrap.css"/>">
		<link rel="stylesheet" type="text/css" href="<c:url value="/assets/icons/css/bootstrap-glyphicons.css"/>">
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/base.css"/>">
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/login.css"/>">
	
	</head>
	<body>
		<div class="container">
			<div class="col-lg-4" id="bgsys-logo">
				<h3>BugSys</h3>
			</div>
			<div class="col-lg-4" id="login-content">
				
				<div class="alert alert-danger fade in not-display" id="error-list"> </div>
			
				<form id="formLogin" action="<c:url value='/login/login'/>" method="post">
				  <fieldset>
				    <legend>Autenticar</legend>
				    <div class="form-group">
				      <label for="username">Usuário </label>
				      <input type="text" class="form-control input-bgsys without-radius" id="username" name="username" >
				    </div>
				    <div class="form-group">
				      <label for="password">Senha</label>
				      <input type="password" class="form-control input-bgsys without-radius" id="password" name="password" >
				    </div>
				    <a href="#" id="lnkLogin" class="btn btn-primary pull-right">Entrar</a>
				  </fieldset>
				</form>
			</div>
		</div>
	</body>
	
	<script type="text/javascript" src="<c:url value="/assets/jquery/jquery-1.10.2.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/assets/bootstrap/js/bootstrap.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/js/login.js"/>"></script>
	
</html>