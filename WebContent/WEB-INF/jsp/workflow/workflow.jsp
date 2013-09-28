
	<%@ include file="/header.jsp" %>
	
	<div class="col-lg-11" id="content-toolbar">
		<div class="col-lg-4 pull-left" id="content-toolbar-buttons-left">
			
			<a id="persistWorkflow" data-toggle="modal" href="#" class="btn btn-bgsys btn-blue-bgsys">
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
			
				<form id="formWorkflow" class="form-inline bgsys-form-inline">
		
		          	<input type="hidden" id="id" value="${workflow.id}">
	
				    <div class="col-lg-6">
			          <label for="user">Título: </label>
			          <input id="title" valid="valid" type="text" class="form-control input-bgsys without-radius" value="${workflow.title}"/>
			    	</div>
				    
				    <div class="col-lg-12">
			          <label for="user">Descrição: </label>
			          <textarea id="description" valid="valid" class="form-control input-bgsys without-radius" rows="3">${workflow.description}</textarea>
			    	</div>
			    	
			    	<div class="col-lg-12">
				    	<div class="panel panel-primary workflow-step-panel">
						  <div class="panel-heading">Fases</div>
						  <div class="panel-body">
						     <div class="row">
						     	<div class="input-group">
							      <input id="step-input" type="text" class="form-control input-bgsys without-radius">
							      <span class="input-group-btn">
							        <button id="step-add" class="btn btn-primary" type="button">
							             &nbsp;<span class="glyphicon glyphicon-plus"></span>&nbsp;
							        </button>
							      </span>
							    </div><!-- /input-group -->
					        </div>
					    	<div class="row step-list">
						    	<ul id="step-list-items" class="list-group"></ul>
					    	</div>
						  </div>
						</div>
			    	</div>
			    	
			  </form>
			</div>
		</div>
	</div>

	<%@ include file="/footer.jsp" %>
	
	<script type="text/javascript" src="<c:url value="/js/step.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/js/workflow.js"/>"></script>
	
	
	
	