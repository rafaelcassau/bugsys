
$(function () {

	var newWorkflow 	= $('#new-workflow');
	var persistWorkflow = $('#persistWorkflow'); 
	var cancel			= $('#cancel');
	
	
	newWorkflow.on('click', function() {
		redirectTo('/bugsys/workflow');
	});
	
	cancel.on('click', function(){
		redirectTo('/bugsys/workflow/list');
	});
	
	
});
