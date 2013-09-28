$(function() { 
		
	var newUseCase 	  = $("#newUseCase");
	var cancel	  	  = $("#cancel");
	var tableUseCase  = $("#tableUseCase");
	var persist	  	  = $("#persistUseCase");
	var editUseCase   = $(".editUseCase");
	var deleteUseCase = $(".deleteUseCase");
	var modal_excluir = $('#modal-excluir');
	var search	      = $('#search');
	
	newUseCase.on("click", function() {
		redirectTo("/bugsys/usecase");
	});
	
	cancel.on("click", function() {
		redirectTo("/bugsys/usecase/list");
	});
	
	editUseCase.on('click', function() {
    	url = $(this).attr('href');
    	redirectTo(url);
    	return false;
	});
	
	/*
	 * Dados formulário de Casos de Uso
	 * */
	
	var id =   			$('#id');
	var code = 			$('#code');
	var name = 			$('#name');
	var description = 	$('#description');
	var projectID =     $('#projectID');
	
	persist.on('click', function() {
		
		if (formIsValid()) {
			
			$.post('/bugsys/usecase/usecase',{
				'id'          : id.val(),
				'code'        : code.val(),
				'name'        : name.val(),
				'description' : description.val(),
				'projectID'   : projectID.val()
			}, function(data) {
				
				var status   = data[0][0];
				var message  = data[0][1];
				
				if (status == 'success') {
					
					if (id.val() == "") {
						
						toastr.success('clique aqui para fechar!', 'Registro incluído com sucesso!');
						setTimeout(function(){
							redirectTo('/bugsys/usecase/list');
						}, 1500);
						
					} else {
						
						toastr.success('clique aqui para fechar!', 'Registro alterado com sucesso!');
						setTimeout(function(){
							redirectTo('/bugsys/usecase/list');
						}, 1500);
					}
					
				} else {
					toastr.error('clique aqui para fechar!', message);
				}
			});
		}
	});
		
	
	deleteUseCase.on('click', function() {
    	
    	var id      = $(this).closest('tr').children().html();
    	var cancel  = $('.cancel');
    	var confirm = $('.confirm');

    	modal_excluir.modal();

    	cancel.on('click', function() {
    		modal_excluir.hide();
    	});
    	
    	confirm.on('click', function() {
    		$.post('/bugsys/usecase/delete', { 'id': id }, 
			function(data) {
				
				var status   = data[0][0];
		    	var message  = data[0][1];
		    	
		    	if (status == 'success') {
		    		
		    		modal_excluir.hide();
		    		
		    		toastr.success('clique aqui para fechar!', 'Registro excluído com sucesso!');
		    		setTimeout(function(){
		    			redirectTo('/bugsys/usecase/list');
		    		}, 1500);
		    		
		    	} else {
		    		toastr.error('clique aqui para fechar!', message);
		    		return;
		    	}
			});
    	});
    });
	
	var oTable = tableUseCase.dataTable({
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
	
});