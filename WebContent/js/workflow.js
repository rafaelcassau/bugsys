
;(function ( $ , StepModel) {
	
	var newWorkflow 	= $('#new-workflow');
	var persistWorkflow = $('#persistWorkflow');
	var edit			= $('.editWorkflow');
	var deleteWorkflow  = $('.deleteWorkflow');
	var cancel			= $('#cancel');
	var modal			= $('#modal-excluir');
	 
	
	newWorkflow.on('click', function() {
		redirectTo('/bugsys/workflow');
	});
	
	edit.on('click', function(){
		url = $(this).attr('href');
    	redirectTo(url);
    	return false;
	});
	
	cancel.on('click', function () {
		StepModel.empty();
		Storage  .drop();
		redirectTo('/bugsys/workflow/list');
	});
	
	
	/*
	 *  Dados formulário de workflow
	 * 
	 * */
	
	var id    	    = $("#id"),
		title       = $("#titulo"),
		description = $("#description");
	
	
	
	persistWorkflow.on('click', function() {
		
		if ( formIsValid() ) {
			
			if ( StepModel.count() == 0 ) {
				 toastr.error('clique aqui para fechar', 'É necessário cadastrar ao menos uma fase para o workflow!');
    			 return;
			}
			
			$.post('/bugsys/workflow/workflow/', { 
				   'id' 	     : id.val(),
				   'title' 		 : title.val(),
				   'description' : description.val()
				   }, 
				   function ( data ) {
				
				   var result = dataReturn(data);
				   
				   if ( result.status == "success" ) {
					   
					   if (id.val() == ""){
						   toastr.success('clique aqui para fechar!', 'Registro incluído com sucesso!');
				    		setTimeout( function() {
				    			redirectTo('/bugsys/workflow/list');
				    		}, 1500);
				    		
					   } else {
						   toastr.success('clique aqui para fechar!', 'Registro alterado com sucesso!');
				    		setTimeout(function() {
				    			redirectTo('/bugsys/workflow/list');
				    		}, 1500);
					   }
				   
				   } else {
					   toastr.error('clique aqui para fechar!', message);
			    	   return;
				   }
			});
			
		} else {
			return false;
		}
	});
	
	
	deleteWorkflow.on('click', function() {
		
		var id      = $(this).attr('href');
		var cancel  = $('.cancel');
    	var confirm = $('.confirm');
    	
    	modal.modal();

    	cancel.on('click', function() {
    		modal.hide();
    	});
    	
    	confirm.on('click', function() {
    		$.post('/bugsys/client/delete', 
    			{ 'id': id }, 
    			
    			function ( data ) {
				
					var result   = dataReturn(data);
			    	
			    	if (result.status == 'success') {
			    		
			    		modal.hide();
			    		
			    		toastr.success('clique aqui para fechar!', 'Registro excluído com sucesso!');
			    		setTimeout( function () {
			    			redirectTo('/bugsys/workflow/list');
			    		}, 1500);
			    		
			    	} else {
			    		toastr.error('clique aqui para fechar!', message);
			    		return;
			    	}
			});
    	});
	});
	
})( jQuery , StepModel );
