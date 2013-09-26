	
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
		<table class="table table-bgsys" id="tableUser">
			<thead>
				<tr>
					<th>Descrição</th>
					<th>Ações</th>
				</tr>
			</thead>
			<tbody>
			<!-- 
				<c:forEach items="${userList}" var="user">
					<tr>
						<td class="id-hidden">${user.id}</td>
					    <td>${user.username}</td>
					    <td>${user.employeeType.employeeType}</td>
						<td>${user.mail}</td>
						<td>
							<div class="btn-group">
							  <a class="btn btn-link editUser" href="<c:url value="/user/${user.id}"/>">
							  	 <span class="glyphicon glyphicon-edit"></span> Editar
							  </a>
							  <a data-toggle="modal" class="btn btn-link deleteUser" href="#">
							  	 <span class="glyphicon glyphicon-remove"></span> Excluir
							  </a>
							</div>
						</td>
					</tr>
				</c:forEach>
			-->
			
			</tbody>
		</table>
	</div>
			
   <%@ include file="/footer.jsp" %>
					
  <script type="text/javascript" src="<c:url value="/js/step.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/js/workflow.js"/>"></script>
  
  