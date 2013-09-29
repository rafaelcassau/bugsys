
	<%@ include file="/header.jsp" %>
	
	<div class="col-lg-11" id="content-toolbar">
		<div class="col-lg-4 pull-left" id="content-toolbar-buttons-left">
			
			<a id="persistUseCase" data-toggle="modal" href="#" class="btn btn-bgsys btn-blue-bgsys">
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
			
				<form id="formUseCase" class="form-inline bgsys-form-inline">
			
		          	<input type="hidden" id="id" value="${useCase.id}">
		          	<input type="hidden" id="projectID" value="2">
				    
			    	<div class="col-lg-3">
			          <label for="code">Código: </label>
			          <input valid="valid" type="text" class="form-control input-bgsys without-radius" id="code" value="${useCase.code}">
			    	</div>

				    <div class="col-lg-9">
			          <label for="name">Nome: </label>
			          <input valid="valid" type="text" class="form-control input-bgsys without-radius" id="name" value="${useCase.name}">
			    	</div>
			    	
			    	<div class="col-lg-12">
			          <label for="description">Descrição: </label>
			          <textarea id="description" class="form-control input-bgsys without-radius" rows="3">${useCase.description}</textarea>
			    	</div>
			    	
			  </form>
			</div>
		</div>
	</div>

	<%@ include file="/footer.jsp" %>
	
	<script type="text/javascript" src="<c:url value="/js/use-case.js"/>"></script>
	
	
	
	