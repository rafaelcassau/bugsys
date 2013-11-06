
;(function ( $ , StepModel) {
	
	var newWorkflow 		= $('#new-workflow'),
	    persistWorkflow 	= $('#persistWorkflow'),
	    deleteWorkflow  	= $('.deleteWorkflow'),
	    visualizeWorkflow	= $('.visualizeWorkflow');
	    cancel				= $('#cancel'),
	    modal				= $('#modal-excluir'),
	    tableWorkflow   	= $('#table-workflow'),
		search	        	= $('#search');
	 
	
	newWorkflow.on('click', function() {
		StepModel.empty();
		SessionStorage.drop();
		redirectTo('/bugsys/workflow');
	});
	
	visualizeWorkflow.on('click', function(){
		
		StepModel.empty();
		SessionStorage.drop();
		
		var href  = $(this).attr('href');
		
		redirectTo(href);
	});
	
	cancel.on('click', function () {
		StepModel.empty();
		SessionStorage.drop();
		redirectTo('/bugsys/workflow/list');
	});
	
	
	/*
	 *  Dados formulário de workflow
	 * 
	 * */
	
	var id    	    = $("#id"),
		title       = $("#title"),
		description = $("#description");
	
	
	
	persistWorkflow.on('click', function() {
		
		if ( formIsValid() ) {
			
			if ( StepModel.count() == 0 ) {
				 toastr.error('clique aqui para fechar', 'É necessário cadastrar ao menos uma fase para o workflow!');
    			 return;
			}
			
			$.post('/bugsys/workflow/workflow', { 
				   'id' 	     : id.val(),
				   'title' 		 : title.val(),
				   'description' : description.val(),
				   'steps'       : StepModel.getAll()
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
					   toastr.error('clique aqui para fechar!', result.message);
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
    		$.post('/bugsys/workflow/delete', 
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
			    		toastr.error('clique aqui para fechar!', result.message);
			    		return;
			    	}
			});
    	});
	});
	
	var oTable = tableWorkflow.dataTable({
    	"bFilter": true,
        "bLengthChange": false,
        "sPaginationType": "four_button",
        "iDisplayLength" : 8,
        "oLanguage" : {
            "sProcessing":   "Processando...",
            "sLengthMenu":   "Mostrar _MENU_ registros",
            "sZeroRecords":  "Não foram encontrados resultados",
            "sInfo":         " ",
            "sInfoEmpty":    "Mostrando de 0 até 0 de 0 registros",
            "sInfoFiltered": "(filtrado de _MAX_ registros no total)",
            "sInfoPostFix":  "",
            "sSearch":       "Pesquisar: ",
            "sUrl":          "",
            "oPaginate": {
                "sPrevious": "Anterior",
                "sNext":     "Seguinte",
                "contentPaginate": "content-toolbar-buttons-right"
            }
        }
    });
	
	search.on('keypress', function(){
    	if(this.value.length >= 3){
    		oTable.fnFilter(this.value, 1, true);
    		oTable.fnDraw();
    	}
    });
	
})( jQuery , StepModel );
