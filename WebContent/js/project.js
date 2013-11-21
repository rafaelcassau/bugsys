
;(function ( $ , MemberModel, UCModel ) {
	
	/***
	 * Inputs Project
	 */ 
	var id               = $('#id'),
		projectName		 = $('#projectName'),
		startDate 		 = $('#startDate'),
		estimatedEndDate = $('#estimatedEndDate'),
		client           = $('#client'),
		workflow         = $('#workflow'),
		description		 = $('#description');
	
	description.on('keyup', function () {
        var maxLength = 250;
        var text = $(this).val();
        var textLength = text.length;
        if (textLength > maxLength) {
            $(this).val(text.substring(0, (maxLength)));
        } 
    });
	
	startDate       .mask("99/99/9999");
	estimatedEndDate.mask("99/99/9999");
	
	startDate.datepicker({
	    dateFormat: 'dd/mm/yy',
	    dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
	    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
	    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
	    monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
	    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
	    nextText: 'Próximo',
	    prevText: 'Anterior'
	});

	estimatedEndDate.datepicker({
		dateFormat: 'dd/mm/yy',
		dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
		dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
		dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
		monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
		monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
		nextText: 'Próximo',
		prevText: 'Anterior'
	});

	
	/*
	 * Actions
	 * */
	
	var newproject 	   = $("#new-project"),
		persistProject = $("#persistProject"),
		editProject	   = $(".editProject"),
		deleteProject  = $(".deleteProject"),
		modal		   = $('#modal-excluir'),
		tableProject   = $("#table-project"),
		cancel         = $("#cancel");
		search	       = $('#search');
	
	editProject.on('click', function(){
		UCModel.empty();
		MemberModel.empty();
		SessionStorage.drop();
		
		var id = $(this).closest('tr').children().html();
		
		$.get('/bugsys/project/populateUseCaseAndUsersProjectUpdateProject',
			  {'id' : id},
			  function(data) {
				
				  listUsersProject = data[0][1];
				  listUseCases     = data[1][1];
				  
				  UCModel.populate(listUseCases);
				  MemberModel.populate(listUsersProject);
				  
				  redirectTo('/bugsys/project/' + id);
			  }
		);
	});
	
	newproject.on('click', function(){
		UCModel.empty();
		MemberModel.empty();
		SessionStorage.drop();
		redirectTo("/bugsys/project/project");
	});
	
	cancel.on('click', function(){
		UCModel.empty();
		MemberModel.empty();
		SessionStorage.drop();
		redirectTo("/bugsys/project/list");
	});
	
	persistProject.on('click', function() {
		
		if(formIsValid()) {
			
			if (!afterTime(startDate.val(), estimatedEndDate.val())) {
				toastr.error('clique aqui para fechar', 'A data de inicio do projeto deve ser anterior a data de termino prevista!');
   			 	return;
			}
			
			if ( UCModel.count() == 0 ) {
				 toastr.error('clique aqui para fechar', 'É necessário cadastrar ao menos um caso de uso para o projeto!');
    			 return;
			};
			
			if ( MemberModel.count() == 0 ) {
				 toastr.error('clique aqui para fechar', 'É necessário cadastrar ao menos um membro para o projeto!');
    			 return;
			};
			
			$.post('/bugsys/project/project', {
				 'id'	 			: id.val(),
				 'projectName' 		: projectName.val(),
				 'startDate' 		: startDate.val(),
				 'estimatedEndDate' : estimatedEndDate.val(),
				 'client' 	 		: client.val(),
				 'workflow' 	    : workflow.val(),
				 'description' 	    : description.val(),
				 'membersProject'   : MemberModel.getAll(),
				 'useCases'      	: UCModel.getAll() 
				}, function(data) {
				
					var status   = data[0][0];
			    	var message  = data[0][1];
			    	
			    	if (status == 'success') {
			    		
			    		if (id.val() == "") {
				    		toastr.success('clique aqui para fechar!', 'Registro incluído com sucesso!');
				    		setTimeout(function(){
				    			redirectTo('/bugsys/project/list');
				    		}, 1500);
			    		} else {
			    			toastr.success('clique aqui para fechar!', 'Registro alterado com sucesso!');
				    		setTimeout(function() {
				    			redirectTo('/bugsys/project/list');
				    		}, 1500);
			    		}
			    	} else {
			    		toastr.error('clique aqui para fechar!', message);
			    		return;
			    	}
			}); 
		}
	});
	
	
	deleteProject.on('click', function() {
		
		var id      = $(this).attr('href');
		var cancel  = $('.cancel');
    	var confirm = $('.confirm');
    	
    	modal.modal();

    	cancel.on('click', function() {
    		modal.hide();
    	});
    	
    	confirm.on('click', function() {
    		$.post('/bugsys/project/delete', 
    			{ 'id': id }, 
    			
    			function ( data ) {
				
					var result   = dataReturn(data);
			    	
			    	if (result.status == 'success') {
			    		
			    		modal.hide();
			    		
			    		toastr.success('clique aqui para fechar!', 'Registro excluído com sucesso!');
			    		setTimeout( function () {
			    			redirectTo('/bugsys/project/list');
			    		}, 1500);
			    		
			    	} else {
			    		
			    		modal.hide();
			    		
			    		toastr.error('clique aqui para fechar!', result.message);
			    		setTimeout( function () {
			    			redirectTo('/bugsys/project/list');
			    		}, 1500);
			    	}
			});
    	});
	});
	
	
	var oTable = tableProject.dataTable({
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

})( jQuery , MemberModel, UCModel );

