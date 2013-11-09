
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
					        <w:select class="form-control input-bgsys without-radius" 
					        		  valid="valid"
					        		  name="project" 
					        		  id="project"
					        		  items="${projectList}" 
					        		  value="id" 
					        		  var="project"
					        		  readOnly="readOnly" 
					        		  selected="${event.project.id}">
				           		${project.projectName}
					        </w:select>
			    	 	</div>
		    	 	</div>
			    	
			    	<div class="col-lg-12">
					    <div class="col-lg-10">
					        <label for="eventType">Tipo de ocorrência: </label>
					        <w:select class="form-control input-bgsys without-radius" 
					        		  valid="valid"
					        		  name="eventType" 
					        		  id="eventType"
					        		  items="${eventTypeList}" 
					        		  value="id" 
					        		  var="eventType"
					        		  readOnly="readOnly" 
					        		  selected="${event.eventType.id}">
				           		${eventType.eventType}
					        </w:select>
			    	 	</div>
		    	 	</div>
		    	 	
			    	<div class="col-lg-12">
					    <div class="col-lg-10">
					        <label for="userResponsible">Usuário responsável: </label>
					        <w:select class="form-control input-bgsys without-radius" 
					        		  valid="valid"
					        		  name="userResponsible" 
					        		  id="userResponsible"
					        		  items="${userResponsibleList}" 
					        		  value="id" 
					        		  var="userResponsible"
					        		  readOnly="readOnly" 
					        		  selected="${event.userResponsible.id}">
				           		${userResponsible.name}
					        </w:select>
			    	 	</div>
		    	 	</div>
			    	
			    	<div class="col-lg-12">
				    	<div class="col-lg-10">
				          <label for="workflow">Workflow: </label>
				          <input id="workflow" readOnly="readOnly" type="text" class="form-control input-bgsys without-radius" value="${workflow.title}">
				    	</div>
			    	</div>
			    	
			    	<div class="col-lg-12">
				    	<div class="col-lg-10">
					        <label for="step">Fases do Projeto: </label>
					        <w:select class="form-control input-bgsys without-radius"
					        	      valid="valid"
					        		  name="step" 
					        		  id="step"
					        		  items="${stepList}" 
					        		  value="id" 
					        		  var="step"
					        		  readOnly="readOnly" 
					        		  selected="${event.stepWorkflow.id}">
				           		${step.title}
					        </w:select>
			    	 	</div>
			    	</div>
			    	
			    	<div class="col-lg-12">
				    	<div class="col-lg-10">
					        <label for="useCase">Casos de Uso: </label>
					        <w:select class="form-control input-bgsys without-radius" 
					        		  valid="valid"
					        		  name="useCase" 
					        		  id="useCase"
					        		  items="${useCaseList}" 
					        		  value="id" 
					        		  var="useCase"
					        		  readOnly="readOnly" 
					        		  selected="${event.useCase.id}">
				           		${useCase.name}
					        </w:select>
			    	 	</div>
			    	</div>
			    	
			    	<div class="col-lg-12">
				    	<div class="col-lg-10">
					        <label for="currentStatus">Status: </label>
					        <w:select class="form-control input-bgsys without-radius" 
					        		  valid="valid"
					        		  name="currentStatus" 
					        		  id="currentStatus"
					        		  items="${currentStatusList}" 
					        		  value="id" 
					        		  var="currentStatus"
					        		  readOnly="readOnly" 
					        		  selected="${event.currentStatus.id}">
				           		${currentStatus.status}
					        </w:select>
			    	 	</div>
			    	</div>
			    	
			    	<div class="col-lg-12">
			    		<c:forEach items="${event.descriptionEventList}" var="description">
					    	<div style="border-bottom:1px solid; border-color: #ddd" class="col-lg-10">
				    			<h4>${description.userDescription.name}</h4>
				    			<small>${description.description}</small> <br />
				    			<span>${description.creationDate}</span>
					    	</div>
			    		</c:forEach>
			    	</div>
			    	
			    	<div class="col-lg-12">
			          	<label for="description">Descrição da Ocorrência: </label>
			          	<textarea valid="valid" id="description" class="form-control input-bgsys without-radius" rows="3"></textarea>
			    	</div>
			    	
			     </form>
			</div>
		</div>
	</div>
    
	<%@ include file="/footer.jsp" %>
	
	<script type="text/javascript" src="<c:url value="/js/event.js"/>"></script>
	
