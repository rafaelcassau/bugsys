
$(function (){
	
	var linkDashboard = $('#linkDashboard');
	var linkProjetcs  = $('#linkProjetcs');
	var linkWorkflows = $('#linkWorkflows');
	var linkUsers     = $('#linkUsers');
	var linkClients   = $('#linkClients');
	var linkEvents    = $('#linkEvents');
    
	linkDashboard.on('click', function() {
		redirectTo('/bugsys/dashboard/dashboard');
	});
	
	linkClients.on('click', function(){
		redirectTo('/bugsys/client/list');
	});
	
	linkProjetcs.on('click', function() {
		redirectTo('/bugsys/project/list');
	});

	linkWorkflows.on('click', function() {
		redirectTo('/bugsys/workflow/list');
	});
	
	linkUsers.on('click', function(){
		redirectTo('/bugsys/user/list');
	});

	linkEvents.on('click', function() {
		redirectTo('/bugsys/event/list');
	});
});
