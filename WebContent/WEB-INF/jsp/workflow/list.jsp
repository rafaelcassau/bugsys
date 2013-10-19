	
	<%@ include file="/header.jsp" %>
	
	<div class="col-lg-11" id="content-toolbar">
		<div class="col-lg-3 pull-left" id="content-toolbar-buttons-left">
			
			<a id="new-workflow" href="#" class="btn btn-bgsys btn-blue-bgsys">
				<span class="glyphicon glyphicon-plus-sign"></span>
				Novo workflow
			</a>
			
		</div>
		<div class="col-lg-3 pull-right" id="content-toolbar-buttons-right"></div>
	</div>
	<div class="col-lg-11" id="content-body">
		<table class="table table-bgsys" id="table-workflow">
			<thead>
				<tr>
					<th width="80%">Descrição</th>
					<th width="20%">Ações</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${workflowList}" var="workflow">
					<tr>
						<td class="id-hidden">${workflow.id}</td>
						<td>${workflow.title}</td>
						<td>
							<div class="btn-group">
							  <a data-toggle="modal" class="btn btn-link deleteWorkflow" href="${workflow.id}">
							  	 <span class="glyphicon glyphicon-remove"></span> Excluir
							  </a>
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<!-- Modal confirm delete workflow -->
	  <div class="modal fade" id="modal-excluir" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	      <div class="modal-content">
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	          <h4 class="modal-title">Confirmação</h4>
	        </div>
	        <div class="modal-body">
	          <p>Deseja realmente excluir o workflow selecionado?</p>
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
  <script type="text/javascript" src="<c:url value="/js/step.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/js/workflow.js"/>"></script>
  
  