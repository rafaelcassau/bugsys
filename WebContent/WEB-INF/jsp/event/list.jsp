	
	<%@ include file="/header.jsp" %>
	
	<div class="col-lg-11" id="content-toolbar">
		<div class="col-lg-3 pull-left" id="content-toolbar-buttons-left">
			
			<a id="new-event" href="#" class="btn btn-bgsys btn-blue-bgsys">
				<span class="glyphicon glyphicon-plus-sign"></span>
				Nova ocorrência
			</a>
			
		</div>
		<div class="col-lg-3 pull-right" id="content-toolbar-buttons-right"></div>
	</div>
	<div class="col-lg-11" id="content-body">
		<table class="table table-bgsys" id="tableEvent">
			<thead>
				<tr>
					<th width="15%">Tipo</th>
					<th width="15%">Status</th>
					<th width="15%">Projeto</th>
					<th width="15%">Responsável</th>
					<th width="20%">Fase</th>
					<th width="20%">Ações</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${eventList}" var="event">
					<tr>
						<td class="id-hidden">${event.id}</td>
					    <td>${event.eventType.eventType}</td>
					    <td>${event.currentStatus.status}</td>
					    <td>${event.project.projectName}</td>
					    <td>${event.userResponsible.name}</td>
					    <td>${event.stepWorkflow.title}</td>
						<td>
							<div class="btn-group">
							  <a class="btn btn-link editEvent" href="<c:url value="/event/${event.id}"/>">
							  	 <span class="glyphicon glyphicon-edit"></span> Editar
							  </a>
							</div>
						</td>
					</tr>
				</c:forEach>
			
			</tbody>
		</table>
	</div>
	
	 <!-- Modal confirm delete event -->
	  <div class="modal fade" id="modal-excluir" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	      <div class="modal-content">
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	          <h4 class="modal-title">Confirmação</h4>
	        </div>
	        <div class="modal-body">
	          <p>Deseja realmente excluir a ocorrência selecionada?</p>
	        </div>
	        <div class="modal-footer">
	          <button type="button" class="btn btn-default cancel" data-dismiss="modal">Cancelar</button>
	          <button type="button" class="btn btn-primary confirm">Confirmar</button>
	        </div>
	      </div><!-- /.modal-content -->
	    </div><!-- /.modal-dialog -->
	  </div><!-- /.modal -->
 					
  <%@ include file="/footer.jsp" %>
  
  <script type="text/javascript" src="<c:url value="/js/event.js"/>"></script>
					
	