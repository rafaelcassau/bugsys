
	<%@ include file="/header.jsp" %>
	
	<div class="col-lg-11" id="content-toolbar">
		<div class="col-lg-4 pull-left" id="content-toolbar-buttons-left">
			
			<a id="persistClient" data-toggle="modal" href="#" class="btn btn-bgsys btn-blue-bgsys">
				<span class="glyphicon glyphicon-ok"></span>
				Salvar
			</a>
			
			<a id="cancel" href="#" class="btn btn-bgsys btn-red-bgsys">
				<span class="glyphicon glyphicon-ban-circle"></span>
				Cancelar
			</a>

		</div>
		<div class="col-lg-3 pull-right" id="content-toolbar-buttons-right"></div>
	</div>
	<div class="col-lg-11" id="content-body">
		<div class="col-lg-12 content-form">
			<div class="col-lg-12 form">
			
				<div class="alert alert-danger fade in not-display" id="error-list"> </div>
			
				<form id="formClient" class="form-inline bgsys-form-inline">
			
		          	<input type="hidden" id="idClient" value="${client.id}">
				    
				    <div class="col-lg-6">
			          <label for="user">Empresa: </label>
			          <input valid="valid" type="text" maxlength="50" class="form-control input-bgsys without-radius" id="corporate" value="${client.corporateName}">
			    	</div>
			    	
			    	<div class="col-lg-6">
			          <label for="user">Nome Fantasia: </label>
			          <input valid="valid" type="text" maxlength="50" class="form-control input-bgsys without-radius" id="fancyName" value="${client.fancyName}">
			    	</div>
			    	
			    	<div class="col-lg-3">
			          <label for="password">CNPJ: </label>
			          <input valid="valid" type="text" class="form-control input-bgsys without-radius" id="cnpj" value="${client.CNPJ}">
			    	</div>
			    	
			    	<div class="col-lg-3">
			          <label for="confirm">I. Estadual: </label>
			          <input valid="valid" type="text" class="form-control input-bgsys without-radius" id="ie" value="${client.stateRegistration}">
			    	</div>
			    	
			    	<div class="col-lg-6">
			          <label for="name">Endereço: </label>
			          <input valid="valid" type="text" maxlength="50" class="form-control input-bgsys without-radius" id="address" value="${client.address}">
			    	</div>
			    	
			    	<div class="col-lg-3">
			          <label for="email">Tel. Comercial: </label>
			          <input valid="valid" type="text" class="form-control input-bgsys without-radius" id="phone" value="${client.phone}">
			    	</div>
			    	
			    	<div class="col-lg-3">
			          <label for="email">Celular: </label>
			          <input valid="valid" type="text" class="form-control input-bgsys without-radius" id="mobile" value="${client.mobile}">
			    	</div>
			    	
			    	<h3 class="col-lg-11 form-divider">Dados de Acesso </h3>
			    	
			    	<input type="hidden" id="idUser" value="${client.user.id}">
			   		
			   		<div class="col-lg-6">
			          <label for="name">Nome: </label>
			          <input valid="valid" type="text" maxlength="50" class="form-control input-bgsys without-radius" id="name" value="${client.user.name}">
			    	</div>
			    	
			    	<div class="col-lg-6">
			          <label for="email">E-mail: </label>
			          <input valid="valid" type="text" maxlength="50" class="form-control input-bgsys without-radius" id="mail" value="${client.user.mail}">
			    	</div>
			    	
			   		<div class="col-lg-6">
			          <label for="email">Usuário: </label>
			          <input valid="valid" type="text" maxlength="50" class="form-control input-bgsys without-radius" id="username" value="${client.user.username}">
			    	</div>
			    	
			    	<div class="col-lg-3">
			          <label for="email">Senha: </label>
			          <input valid="valid" type="password" maxlength="50" class="form-control input-bgsys without-radius" id="password" value="${client.user.password}">
			    	</div>
			    	
			    	<div class="col-lg-3">
			          <label for="email">Confirmação: </label>
			          <input valid="valid" type="password" maxlength="50" class="form-control input-bgsys without-radius" id="confirm" value="${client.user.password}">
			    	</div>
			   		
			     </form>
			</div>
		</div>
	</div>
    
	<%@ include file="/footer.jsp" %>
	
	<script type="text/javascript" src="<c:url value="/js/client.js"/>"></script>
	
	
	
	