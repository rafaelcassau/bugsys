
	<%@ include file="/header.jsp" %>
	
	<div class="col-lg-11" id="content-toolbar">
		<div class="col-lg-4 pull-left" id="content-toolbar-buttons-left">
			
			<a id="persistProject" data-toggle="modal" href="#" class="btn btn-bgsys btn-blue-bgsys">
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
			
				<form id="formProject" class="form-inline bgsys-form-inline">
			
		          	<input type="hidden" id="id" value="${project.id}">
				    
				    <div class="col-lg-12">
			          <label for="projectName">Nome do Projeto: </label>
			          <input valid="valid" type="text" maxlength="50" class="form-control input-bgsys without-radius" id="projectName" value="${project.projectName}">
			    	</div>
			    	
			    	<div class="col-lg-6">
			          <label for="startDate">Data de Inicio: </label>
			          <input valid="valid" type="text" class="form-control input-bgsys without-radius" id="startDate" value="${project.startDateString}">
			    	</div>

			    	<div class="col-lg-6">
			          <label for="estimatedEndDate">Data de Termino Estimada: </label>
			          <input valid="valid" type="text" class="form-control input-bgsys without-radius" id="estimatedEndDate" value="${project.estimatedEndDateString}">
			    	</div>

			    	<div class="col-lg-6">
			    		<label for="client">Cliente: </label>
				        <w:select class="form-control input-bgsys without-radius" 
				        		  name="client" 
				        		  id="client"
				        		  items="${clientList}" 
				        		  value="id" 
				        		  var="client" 
				        		  selected="${project.client.id}">
			           		${client.fancyName}
				        </w:select>
			    	</div>

			    	<div class="col-lg-6">
			    		<label for="workflow">Workflow: </label>
				        <w:select class="form-control input-bgsys without-radius" 
				        		  name="workflow" 
				        		  id="workflow"
				        		  items="${workflowList}" 
				        		  value="id" 
				        		  var="workflow" 
				        		  selected="${project.workflow.id}">
			           		${workflow.title}
				        </w:select>
			    	</div>
			    	
			    	<div class="col-lg-12">
			          <label for="description">Descrição: </label>
			          <textarea id="description" class="form-control input-bgsys without-radius" rows="3">${project.description}</textarea>
			    	</div>
			    	
			    	
			    	<div class="col-lg-12">
				    	<div class="panel panel-primary use-case-panel">
				    	
						  <div class="panel-heading">
						      <div class="clearfix">
						   		Casos de Uso
							  	<div class="col-lg-1 pull-right">
						  	        <a href="#" id="use-case-add" class="btn btn-primary">
							            <span class="glyphicon glyphicon-plus"></span>
							        </a>
								</div>
							</div>
						  </div>
						
						  <div class="panel-body">
						    <div class="row">
							 
					        </div>
					    
					    	<div class="row use-case-list panel-pad">
						    	<!-- <ul id="use-case-list-items" class="list-group"></ul> -->
						    	<table class="table table-bordered">
						    	  <thead>
						    	     <tr>
						    	        <th width="20%">Código</th>
						    	        <th width="30%">Nome</th>
						    	        <th width="35%">Descrição</th>
						    	        <th width="15%">Ações</th>
						    	     </tr>
						    	  </thead>
						    	  <tbody id="use-case-list-items">
						    	   
						    	  </tbody>
						    	</table>
					    	</div>
					    	
						  </div>
						</div>
			    	</div>
			    	
			    	<div class="col-lg-12">
				    	<div class="panel panel-primary">
				    	  <div class="panel-heading">Membros da Equipe</div>
							  <div class="panel-body panel-pad ">
							     <div class="row">
							     	   <input type="text" class="form-control input-bgsys without-radius" name="member" id="member-input" />
							     </div>
						    	<div class="row member-list">
						    	    <br/>
							    	<ul id="member-list-items" class="list-group">
							    	</ul>
						    	</div>
							  </div>
						</div>
			    	</div>
				 </form>
			</div>
		</div>
	</div>
	
	
	 <!-- Modal cadastro de caso de uso -->
	  <div class="modal fade" id="modal-use-case" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	      <div class="modal-content">
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	          <h4 class="modal-title">Caso de Uso</h4>
	        </div>
	        <div class="modal-body">
	        	
	        	<form id="formUseCase" class="form-inline bgsys-form-inline">
		          	
		          	<input type="hidden" id="useCaseID" value="${useCase.id}">
		          	<input type="hidden" id="flag-isedit">
				    
			          <label for="code">Código: </label>
			          <input usecase="usecase" type="text" maxlength="50" class="form-control input-bgsys without-radius" id="code" value="${useCase.code}">
	
			          <label for="name">Nome: </label>
			          <input usecase="usecase" type="text" maxlength="50" class="form-control input-bgsys without-radius" id="name" value="${useCase.name}">
			    	
			          <label for="descriptionUseCase">Descrição: </label>
			          <textarea usecase="usecase" id="descriptionUseCase" class="form-control input-bgsys without-radius" rows="3">${useCase.description}</textarea>
			  </form>
	          
	        </div>
	        <div class="modal-footer">
	          <button type="button" class="btn btn-default cancel-use-case" data-dismiss="modal">Cancelar</button>
	          <button id="save-use-case" type="button" class="btn btn-primary save-use-case">Salvar</button>
	        </div>
	      </div><!-- /.modal-content -->
	    </div><!-- /.modal-dialog -->
	  </div><!-- /.modal -->
	

	<%@ include file="/footer.jsp" %>
	
	<script type="text/javascript" src="<c:url value="/assets/storage/sessionstorage.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/js/member-project.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/js/use-case-project.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/js/project.js"/>"></script>
	
	
	
	