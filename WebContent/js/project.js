
;(function ( $ , MemberModel, UCModel ) {
	
	/***
	 * Inputs Project
	 */ 
	var startDate 		 = $('#startDate'),
		estimatedEndDate = $('#estimatedEndDate'),
		nameProject		 = $('#projectName'),
		description		 = $('#description');
	
	
	startDate       .mask("99/99/9999");
	endDate         .mask("99/99/9999");
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
		cancel         = $("#cancel");
	
	
	newproject.on('click', function(){
		redirectTo("/bugsys/project/project");
	});
	
	cancel.on('click', function(){
		redirectTo("/bugsys/project/list");
	});
	
	persistProject.on('click', function() {
		
		if(formIsValid()) {
			
			if ( UCModel.count() == 0 ){
				 toastr.error('clique aqui para fechar', 'É necessário cadastrar ao menos um caso de uso para o projeto!');
    			 return;
			};
			
			if ( MemberModel.count() == 0 ){
				 toastr.error('clique aqui para fechar', 'É necessário cadastrar ao menos um membro para o projeto!');
    			 return;
			};
			
			$.post('/bugsys/project/project', {
				 'id'	 			: id.val(),
				 'projectName' 		: projectName.val(),
				 'startDate' 		: startDate.val(),
				 'estimatedEndDate' : estimatedEndDate.val(),
				 'endDate' 		 	: endDate.val(),
				 'client' 	 		: client.val(),
				 'workflow' 	    : workflow.val(),
				 'description' 	    : description.val(),
				 'membersProject'   : JSON.parse(MemberModel.getAll()),
				 'useCases'      	: JSON.parse("[" + UCModel.getAll() + "]") 
				}, function(data) {
				
					var status   = data[0][0];
			    	var message  = data[0][1];
			    	
			    	if (status == 'success') {
			    		
			    		if (idClient.val() == "") {
				    		toastr.success('clique aqui para fechar!', 'Registro incluído com sucesso!');
				    		setTimeout(function(){
				    			redirectTo('/bugsys/client/list');
				    		}, 1500);
			    		} else {
			    			toastr.success('clique aqui para fechar!', 'Registro alterado com sucesso!');
				    		setTimeout(function() {
				    			redirectTo('/bugsys/client/list');
				    		}, 1500);
			    		}
			    	} else {
			    		toastr.error('clique aqui para fechar!', message);
			    		return;
			    	}
			}); 
		}
	});

})( jQuery , MemberModel, UCModel );

