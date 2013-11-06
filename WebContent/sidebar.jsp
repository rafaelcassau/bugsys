
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
	<div class="col-lg-2" id="sidebar">
		<div class="col-lg-12" id="sidebar-logo">
			<h3>BugSys</h3>
		</div>
		<ul class="col-lg-12" id="sidebar-menu">
			<li>
				<a id="linkDashboard" href="#">Dashboard 
					<span class="glyphicon glyphicon-dashboard pull-right"></span>
				</a>
			</li>
			
			<c:if test="${userSession.hasPermission}">
				<li>
					<a id="linkClients" href="#">Clientes
						<span class="glyphicon glyphicon-folder-open pull-right"></span>
					</a>
				</li>
			</c:if>
			
			<li>
				<a id="linkProjetcs" href="#">Projetos  
					<span class="glyphicon glyphicon-folder-open pull-right"></span>
				</a>
			</li>
			
			<c:if test="${userSession.hasPermission}">
				<li>
					<a id="linkWorkflows" href="#">Workflows 
						<span class="glyphicon glyphicon-refresh pull-right"></span>
					</a>
				</li>
			
				<li>
					<a id="linkUsers" href="#">Usu&aacute;rios 
						<span class="glyphicon glyphicon-user pull-right"></span>
					</a>
				</li>
			</c:if>

			<li>
				<a id="linkEvents" href="#">Ocorr&ecirc;ncias
					<span class="glyphicon glyphicon-signal pull-right"></span>
				</a>
			</li>
			
		</ul>
	</div>