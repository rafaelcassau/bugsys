
$(function (){
	
	var linkDashboard = $('#linkDashboard');
	var linkProjetcs  = $('#linkProjetcs');
	var linkWorkflows = $('#linkWorkflows');
	var linkUsers     = $('#linkUsers');
	var linkClients   = $('#linkClients');
	var linkReports   = $('#linkReports');
	var linkSettings  = $('#linkSettings');
    
	
	linkDashboard.on('click', function() {
		redirectTo('/bugsys/dashboard/dashboard');
	});
	
	linkClients.on('click', function(){
		redirectTo('/bugsys/client/list');
	});
	
	linkProjetcs.on('click', function() {
		redirectTo('bugsys/projects');
	});

	linkWorkflows.on('click', function() {
		redirectTo('bugsys/workflows');
	});
	
	linkUsers.on('click', function(){
		redirectTo('/bugsys/user/list');
	});

	linkReports.on('click', function() {
		redirectTo('bugsys/reports');
	});

	linkSettings.on('click', function() {
		redirectTo('bugsys/settings');
	});
});
