
;(function ( $ , StepModel) {
	
	var newWorkflow 	= $('#new-workflow');
	var persistWorkflow = $('#persistWorkflow'); 
	var cancel			= $('#cancel');
	
	newWorkflow.on('click', function() {
		redirectTo('/bugsys/workflow');
	});
	
	cancel.on('click', function () {
		StepModel.empty();
		Storage  .drop();
		redirectTo('/bugsys/workflow/list');
	});
	
	persistWorkflow.on('click', function() {
		
		if ( formIsValid() ) {
			
		}
		
	});
	
})( jQuery , StepModel );
