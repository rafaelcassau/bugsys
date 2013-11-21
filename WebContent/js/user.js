$(function () {

	var newUser          = $('#newUser');
	var cancel           = $('#cancel'); 
	var tableUsers       = $('#tableUser');
    var persistUser      = $('#persistUser');
    var modal_excluir    = $('#modal-excluir');
    var editUser		 = $('.editUser');
    var deleteUser		 = $('.deleteUser');
    var search			 = $('#search');
    
    // Dados do formulário de usuários
    
    var id				 = $('#id');
    var username         = $('#username');
    var password         = $('#password');
    var confirm      	 = $('#confirm');
    var name			 = $('#name');
    var mail			 = $('#mail');
    var employeeType     = $('#employeeType');
    
    newUser.on('click', function() {
    	redirectTo('/bugsys/user');
    });
    
    cancel.on('click', function() {
    	redirectTo('/bugsys/user/list');
    });
    
    editUser.on('click', function() {
    	url = $(this).attr('href');
    	redirectTo(url);
    	return false;
    });
    
    deleteUser.on('click', function() {
    	
    	var id      = $(this).closest('tr').children().html();
    	var cancel  = $('.cancel');
    	var confirm = $('.confirm');

    	modal_excluir.modal();

    	cancel.on('click', function() {
    		modal_excluir.hide();
    	});
    	
    	confirm.on('click', function() {
    		$.post('/bugsys/user/delete', { 'id': id }, 
			function(data) {
				
				var status   = data[0][0];
		    	var message  = data[0][1];
		    	
		    	if (status == 'success') {
		    		
		    		modal_excluir.hide();
		    		
		    		toastr.success('clique aqui para fechar!', 'Registro excluído com sucesso!');
		    		setTimeout(function(){
		    			redirectTo('/bugsys/user/list');
		    		}, 1500);
		    		
		    	} else {
		    		
		    		modal_excluir.hide();
		    		
		    		toastr.error('clique aqui para fechar!', message);
		    		setTimeout(function(){
		    			redirectTo('/bugsys/user/list');
		    		}, 1500);
		    	}
			});
    	});
    	
    });
    
    persistUser.on('click', function() {
    	
    	if (formIsValid()) {

    		if (!emailIsValid(mail.val())) {
    			toastr.error('clique aqui para fechar', 'O endereço de e-mail informado é inválido!');
    			return;
    		}
    		
    		if (password.val() != confirm.val()) {
    			toastr.error('clique aqui para fechar', 'Senha e Confirmar Senha não conferem!');
    			return;
    		}
    		
    		$.post('/bugsys/user/user', {
    				'id': id.val(),
    				'username': username.val(),
    				'password': password.val(),
    				'confirm': confirm.val(),
    				'name': name.val(),
    				'mail': mail.val(),
    				'employeeType' :employeeType.val()
    				
    			}, function(data) {
    				
    				var status   = data[0][0];
			    	var message  = data[0][1];
			    	
			    	if (status == 'success') {
			    		
			    		if(id.val() == ""){
				    		toastr.success('clique aqui para fechar!', 'Registro incluído com sucesso!');
				    		setTimeout(function(){
				    			redirectTo('/bugsys/user/list');
				    		}, 1500);
			    		}else{
			    			toastr.success('clique aqui para fechar!', 'Registro alterado com sucesso!');
				    		setTimeout(function(){
				    			redirectTo('/bugsys/user/list');
				    		}, 1500);
			    		}
			    		
			    	} else {
			    		toastr.error('clique aqui para fechar!', message);
			    		return;
			    	}
    			});
    	} 
    });
    
    var oTable = tableUsers.dataTable({
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


