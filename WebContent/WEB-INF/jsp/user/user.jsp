
	<%@ include file="/header.jsp" %>
	
	<div class="col-lg-11" id="content-toolbar">
		<div class="col-lg-4 pull-left" id="content-toolbar-buttons-left">
			
			<a id="persistUser" data-toggle="modal" href="#" class="btn btn-bgsys btn-blue-bgsys">
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
			
				<form id="formUser" class="form-inline bgsys-form-inline">
			
		          	<input type="hidden" id="id" value="${user.id}">
				    
				    <div class="col-lg-6">
			          <label for="username">Usu&aacute;rio: </label>
			          <input valid="valid" type="text" class="form-control input-bgsys without-radius" id="username" value="${user.username}">
			    	</div>
			    	
			    	<div class="col-lg-3">
			          <label for="password">Senha: </label>
			          <input valid="valid" type="password" class="form-control input-bgsys without-radius" id="password" value="${user.password}">
			    	</div>
			    	
			    	<div class="col-lg-3">
			          <label for="confirm">Confirma&ccedil;&atilde;o: </label>
			          <input valid="valid" type="password" class="form-control input-bgsys without-radius" id="confirm" value="${user.password}">
			    	</div>
			    	
			    	<div class="col-lg-6">
			          <label for="name">Nome: </label>
			          <input valid="valid" type="text" class="form-control input-bgsys without-radius" id="name" value="${user.name}">
			    	</div>
			    	
			    	<div class="col-lg-6">
			          <label for="email">E-mail: </label>
			          <input valid="valid" type="text" class="form-control input-bgsys without-radius" id="mail" value="${user.mail}">
			    	</div>
			    	
					<div class="col-lg-6">
				        <label for="employeeType">Cargo: </label>
				        <w:select class="form-control input-bgsys without-radius" 
				        		  name="employeeType" 
				        		  id="employeeType"
				        		  items="${employeeTypeList}" 
				        		  value="id" 
				        		  var="employeeType" 
				        		  selected="${user.employeeType.id}">
			           		${employeeType.employeeType}
				        </w:select>
			    	 </div>
			    	
			  </form>
			</div>
		</div>
	</div>

	<%@ include file="/footer.jsp" %>
	
	<script type="text/javascript" src="<c:url value="/js/user.js"/>"></script>
	
	
	
	