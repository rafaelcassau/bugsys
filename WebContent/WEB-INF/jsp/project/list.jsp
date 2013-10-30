	
	<%@ include file="/header.jsp" %>
	
	<div class="col-lg-11" id="content-toolbar">
		<div class="col-lg-3 pull-left" id="content-toolbar-buttons-left">
			
			<a id="new-project" href="#" class="btn btn-bgsys btn-blue-bgsys">
				<span class="glyphicon glyphicon-plus-sign"></span>
				Novo projeto
			</a>
			
		</div>
		<div class="col-lg-3 pull-right" id="content-toolbar-buttons-right"></div>
	</div>
	<div class="col-lg-11" id="content-body">
		<table class="table table-bgsys" id="table-project">
			<thead>
				<tr>
					<th width="20%">Nome</th>
					<th width="20%">Data de Inicio</th>
					<th width="20%">Data Estimada de Termino</th>
					<th width="20%">Cliente</th>
					<th width="20%">Ações</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${projectList}" var="project">
					<tr>
						<td class="id-hidden">${project.id}</td>
						<td>${project.projectName}</td>
						<td>${project.startDateString}</td>
						<td>${project.estimatedEndDateString}</td>
						<td>${project.client.fancyName}</td>
						<td>
							<div class="btn-group">
							  <a class="btn btn-link editProject" href="<c:url value="/project/${project.id}"/>">
							  	 <span class="glyphicon glyphicon-edit"></span> Editar
							  </a>
							  <a data-toggle="modal" class="btn btn-link deleteProject" href="${project.id}">
							  	 <span class="glyphicon glyphicon-remove"></span> Excluir
							  </a>
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<!-- Modal confirm delete project -->
	  <div class="modal fade" id="modal-excluir" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	      <div class="modal-content">
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	          <h4 class="modal-title">Confirmação</h4>
	        </div>
	        <div class="modal-body">
	          <p>Deseja realmente excluir o projeto selecionado?</p>
	        </div>
	        <div class="modal-footer">
	          <button type="button" class="btn btn-default cancel" data-dismiss="modal">Cancelar</button>
	          <button type="button" class="btn btn-primary confirm">Confirmar</button>
	        </div>
	      </div><!-- /.modal-content -->
	    </div><!-- /.modal-dialog -->
	  </div><!-- /.modal -->
			
   <%@ include file="/footer.jsp" %>
					
  <script type="text/javascript" src="<c:url value="/assets/storage/sessionstorage.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/js/member-project.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/js/use-case-project.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/js/project.js"/>"></script>
  
  