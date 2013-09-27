$(function() { 
		
	var newClient 	  = $("#new-client");
	var cancel	  	  = $("#cancel");
	var tableClient   = $("#tableClient");
	var persist	  	  = $("#persistClient");
	var editClient    = $(".editClient");
	var deleteClient  = $(".deleteClient");
	var modal_excluir = $('#modal-excluir');
	var search	      = $('#search');
	
	newClient.on("click", function() {
		redirectTo("/bugsys/client");
	});
	
	cancel.on("click", function() {
		redirectTo("/bugsys/client/list");
	});
	
	editClient.on('click', function() {
    	url = $(this).attr('href');
    	redirectTo(url);
    	return false;
	});
	
	/*
	 * Dados formulário de cliente
	 * */
	
	var idClient	  	= $("#idClient"),
		corporateName 	= $("#corporate"),
		fancyName	  	= $("#fancyName"),
		cnpj		  	= $("#cnpj"),
		ie			  	= $("#ie"),
		address		  	= $("#address"),
		phone		  	= $("#phone"),
		mobile		  	= $("#mobile"),
		idUser			= $("#idUser"),
		name			= $("#name"),
		mail			= $("#mail"),
		username	    = $("#username"),
		password	    = $("#password"),
		confirmPassword = $("#confirm"); 
	
	cnpj  .mask("00.000.000/0000-00");
	ie    .mask("000/0000000");
	phone .mask("(00) 0000-0000");
	mobile.mask("(00) 00000-0000");
	
	persist.on("click", function() {
		
		if(formIsValid()) {
			
			if (!cnpjIsValid(cnpj.val())) {
				toastr.error('clique aqui para fechar', 'O CNPJ informado é inválido!');
    			return;
			}
			
			if (!emailIsValid(mail.val())) {
    			toastr.error('clique aqui para fechar', 'O endereço de e-mail informado é inválido!');
    			return;
    		}
			
			if (password.val() != confirmPassword.val()){
				toastr.error("clique aqui para fechar!", "Senha e Confirmar Senha não conferem!");
				return false;
			}
			
			$.post('/bugsys/client/client', {
				 'idClient'	 : idClient.val(),
				 'corporate' : corporateName.val(),
				 'fancyName' : fancyName.val(),
				 'cnpj'      : cnpj.val(),
				 'ie' 		 : ie.val(),
				 'address' 	 : address.val(),
				 'phone' 	 : phone.val(),
				 'mobile' 	 : mobile.val(),
				 'idUser'    : idUser.val(),
				 'name'      : name.val(),
				 'mail'		 : mail.val(),
				 'username'  : username.val(),
				 'password'  : password.val()
				}, function(data){
				
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
	
	deleteClient.on('click', function() {
    	
    	var id      = $(this).closest('tr').children().html();
    	var cancel  = $('.cancel');
    	var confirm = $('.confirm');

    	modal_excluir.modal();

    	cancel.on('click', function() {
    		modal_excluir.hide();
    	});
    	
    	confirm.on('click', function() {
    		$.post('/bugsys/client/delete', { 'id': id }, 
			function(data) {
				
				var status   = data[0][0];
		    	var message  = data[0][1];
		    	
		    	if (status == 'success') {
		    		
		    		modal_excluir.hide();
		    		
		    		toastr.success('clique aqui para fechar!', 'Registro excluído com sucesso!');
		    		setTimeout(function(){
		    			redirectTo('/bugsys/client/list');
		    		}, 1500);
		    		
		    	} else {
		    		toastr.error('clique aqui para fechar!', message);
		    		return;
		    	}
			});
    	});
    });
	
	var oTable = tableClient.dataTable({
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