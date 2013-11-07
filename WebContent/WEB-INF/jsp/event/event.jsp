
	<%@ include file="/header.jsp" %>
	
	<div class="col-lg-11" id="content-toolbar">
		<div class="col-lg-4 pull-left" id="content-toolbar-buttons-left">
			
			<a id="persistEvent" data-toggle="modal" href="#" class="btn btn-bgsys btn-blue-bgsys">
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
			
				<form id="formEvent" class="form-inline bgsys-form-inline">
			
		          	<input type="hidden" id="idEvent" value="${event.id}">
				    
			    	<div class="col-lg-12">
				    	<div class="col-lg-10">
					        <label for="project">Projeto: </label>
					        
					       	<select id="project" name="project" class="form-control input-bgsys without-radius">
							
							</select>
							
			    	 	</div>
			    	</div>
			    	
			    	<div class="col-lg-12">
					    <div class="col-lg-10">
					        <label for="userResponsible">Usuário responsável: </label>
					        <w:select class="form-control input-bgsys without-radius" 
					        		  name="userResponsible" 
					        		  id="userResponsible"
					        		  items="${userResponsibleList}" 
					        		  value="id" 
					        		  var="userResponsible"
					        		  readOnly="readOnly" 
					        		  selected="${userResponsible.id}">
				           		${userResponsible.name}
					        </w:select>
			    	 	</div>
		    	 	</div>
			    	
			    	<div class="col-lg-12">
				    	<div class="col-lg-10">
				          <label for="workflow">Workflow: </label>
				          <input valid="valid" id="workflow" readOnly="readOnly" type="text" class="form-control input-bgsys without-radius" value="${workflow.title}">
				    	</div>
			    	</div>
			    	
			    	<div class="col-lg-12">
				    	<div class="col-lg-10">
					        <label for="step">Fases do Projeto: </label>
					        <w:select class="form-control input-bgsys without-radius" 
					        		  name="step" 
					        		  id="step"
					        		  items="${stepList}" 
					        		  value="id" 
					        		  var="step"
					        		  readOnly="readOnly" 
					        		  selected="${step.id}">
				           		${step.title}
					        </w:select>
			    	 	</div>
			    	</div>
			    	
			    	<div class="col-lg-12">
				    	<div class="col-lg-10">
					        <label for="useCase">Casos de Uso: </label>
					        <w:select class="form-control input-bgsys without-radius" 
					        		  name="useCase" 
					        		  id="useCase"
					        		  items="${useCaseList}" 
					        		  value="id" 
					        		  var="useCase"
					        		  readOnly="readOnly" 
					        		  selected="${useCase.id}">
				           		${useCase.name}
					        </w:select>
			    	 	</div>
			    	</div>
			    	
			    	<div class="col-lg-12">
				    	<div class="col-lg-10">
					        <label for="currentStatus">Status: </label>
					        <w:select class="form-control input-bgsys without-radius" 
					        		  name="currentStatus" 
					        		  id="currentStatus"
					        		  items="${currentStatusList}" 
					        		  value="id" 
					        		  var="currentStatus"
					        		  readOnly="readOnly" 
					        		  selected="${currentStatus.id}">
				           		${currentStatus.status}
					        </w:select>
			    	 	</div>
			    	</div>
			    	
			     </form>
			</div>
		</div>
	</div>
    
	<%@ include file="/footer.jsp" %>
	
	<script type="text/javascript" src="<c:url value="/js/event.js"/>"></script>
	
