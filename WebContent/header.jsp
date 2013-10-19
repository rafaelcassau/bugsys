
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://waffle.codehaus.org" prefix="w" %>  

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
		<title>BugSys</title>
	
		<link rel="stylesheet" type="text/css" href="<c:url value="/assets/bootstrap/css/bootstrap.css"/>">
		<link rel="stylesheet" type="text/css" href="<c:url value="/assets/datatable/css/jquery.dataTables.css"/>">
		<link rel="stylesheet" type="text/css" href="<c:url value="/assets/jquery/css/jquery-ui-1.10.3.custom.min.css"/>">
		<link rel="stylesheet" type="text/css" href="<c:url value="/assets/icons/css/bootstrap-glyphicons.css"/>">
		<link rel="stylesheet" type="text/css" href="<c:url value="/assets/notification/toastr.css"/>">
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/user-info.css"/>">
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/sidebar.css"/>">
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/base.css"/>">
	</head>
	
	<body>
		<span class="loading">
		  <img alt="teste" src="<c:url value="/img/loading.gif"/>">
		</span>	
		<div class="row" id="wrapper">
			<div class="row" id="layout">
		
			<%@ include file="/sidebar.jsp" %>
			
			<div class="col-lg-10" id="content">
				<header class="col-lg-12" id="content-header">
					<h3 class="col-lg-1 content-header-title current-action">
						<c:out value="${userSession.functionality.functionality}"></c:out>
					</h3>
					<div class="col-lg-3 pull-right" id="content-header-user-info">
						<ul class="user-info">
							<!-- NOTIFICATION -->
							<%@ include file="/notification.jsp" %>
							
							<!-- USER INFO -->
							<li class="user">
								<a href="#" data-toggle="dropdown">
									<c:out value="${userSession.user.username}"></c:out>
									<span class="caret"></span>
								</a>
								<ul class="dropdown-menu">
							      <li><a href="#">Prefer&ecirc;ncias</a></li>
							      <li><a href="#">Configura&ccedil;&otilde;es</a></li>
							      <li><a href="<c:url value='/login/logout'/>">Sair</a></li>
							    </ul>
							</li>
						</ul>
					</div>
				</header>
