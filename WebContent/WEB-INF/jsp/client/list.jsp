	
	<%@ include file="/header.jsp" %>
	
	<div class="col-lg-11" id="content-toolbar">
		<div class="col-lg-3 pull-left" id="content-toolbar-buttons-left">
			
			<a id="new-client" href="#" class="btn btn-bgsys btn-blue-bgsys">
				<span class="glyphicon glyphicon-plus-sign"></span>
				Novo cliente
			</a>
			
		</div>
		<div class="col-lg-3 pull-right" id="content-toolbar-buttons-right"></div>
	</div>
	<div class="col-lg-11" id="content-body">
		<table class="table table-bgsys" id="tableClient">
			<thead>
				<tr>
					<th>Nome Fantasia</th>
					<th>Nome Responsável</th>
					<th>Telefone</th>
					<th>Ações</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${clientList}" var="client">
					<tr>
						<td class="id-hidden">${client.id}</td>
					    <td>${client.fancyName}</td>
					    <td>${client.user.name}</td>
						<td>${client.phone}</td>
						<td>
							<div class="btn-group">
							  <a class="btn btn-link editClient" href="<c:url value="/client/${client.id}"/>">
							  	 <span class="glyphicon glyphicon-edit"></span> Editar
							  </a>
							  <a data-toggle="modal" class="btn btn-link deleteClient" href="${client.id}">
							  	 <span class="glyphicon glyphicon-remove"></span> Excluir
							  </a>
							</div>
						</td>
					</tr>
				</c:forEach>
			
			</tbody>
		</table>
	</div>
	
	 <!-- Modal confirm delete client -->
	  <div class="modal fade" id="modal-excluir" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	      <div class="modal-content">
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	          <h4 class="modal-title">Confirmação</h4>
	        </div>
	        <div class="modal-body">
	          <p>Deseja realmente excluir o cliente selecionado?</p>
	        </div>
	        <div class="modal-footer">
	          <button type="button" class="btn btn-default cancel" data-dismiss="modal">Cancelar</button>
	          <button type="button" class="btn btn-primary confirm">Confirmar</button>
	        </div>
	      </div><!-- /.modal-content -->
	    </div><!-- /.modal-dialog -->
	  </div><!-- /.modal -->
 					
  <%@ include file="/footer.jsp" %>
  
  <script type="text/javascript" src="<c:url value="/js/client.js"/>"></script>
					
	