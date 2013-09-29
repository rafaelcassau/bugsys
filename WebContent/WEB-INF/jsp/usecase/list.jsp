	
	<%@ include file="/header.jsp" %>
	
	<div class="col-lg-11" id="content-toolbar">
		<div class="col-lg-3 pull-left" id="content-toolbar-buttons-left">
			
			<a id="newUseCase" href="#" class="btn btn-bgsys btn-blue-bgsys">
				<span class="glyphicon glyphicon-plus-sign"></span>
				Novo caso de uso
			</a>
			
		</div>
		<div class="col-lg-3 pull-right" id="content-toolbar-buttons-right"></div>
	</div>
	<div class="col-lg-11" id="content-body">
		<table class="table table-bgsys" id="tableUseCase">
			<thead>
				<tr>
					<th width="30%">Nome</th>
					<th width="20%">Código</th>
					<th width="30%">Projeto</th>
					<th width="20%">Ações</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${useCaseList}" var="useCase">
					<tr>
						<td class="id-hidden">${useCase.id}</td>
					    <td>${useCase.name}</td>
						<td>${useCase.code}</td>
					    <td>${useCase.project.projectName}</td>
						<td>
							<div class="btn-group">
							  <a class="btn btn-link editUseCase" href="<c:url value="/usecase/${useCase.id}"/>">
							  	 <span class="glyphicon glyphicon-edit"></span> Editar
							  </a>
							  <a data-toggle="modal" class="btn btn-link deleteUseCase" href="#">
							  	 <span class="glyphicon glyphicon-remove"></span> Excluir
							  </a>
							</div>
						</td>
					</tr>
				</c:forEach>
			
			</tbody>
		</table>
	</div>
			
 
	 <!-- Modal confirm delete useCase -->
	  <div class="modal fade" id="modal-excluir" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	      <div class="modal-content">
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	          <h4 class="modal-title">Confirmação</h4>
	        </div>
	        <div class="modal-body">
	          <p>Deseja realmente excluir o caso de uso selecionado?</p>
	        </div>
	        <div class="modal-footer">
	          <button type="button" class="btn btn-default cancel" data-dismiss="modal">Cancelar</button>
	          <button type="button" class="btn btn-primary confirm">Confirmar</button>
	        </div>
	      </div><!-- /.modal-content -->
	    </div><!-- /.modal-dialog -->
	  </div><!-- /.modal -->
					
  <%@ include file="/footer.jsp" %>
					
  <script type="text/javascript" src="<c:url value="/js/use-case.js"/>"></script>
  
  