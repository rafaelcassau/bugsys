$(function(){

	// botoões
	var newEvent 		= $('#new-event');
	var persistEvent	= $('#persistEvent');
	var cancel			= $('#cancel');
	
	// campos formulario
	var idEvent			= $('#idEvent');
	var project 		= $('#project');
	var eventType		= $('#eventType');
	var userResponsible = $('#userResponsible');
	var workflow		= $('#workflow');
	var step			= $('#step');
	var useCase			= $('#useCase');
	var currentStatus	= $('#currentStatus');
	var description		= $('#description');
	
	loadComboboxProject(project);
	
	newEvent.on('click', function(){
		redirectTo('/bugsys/event/event');
	});
	
	cancel.on('click', function() {
		redirectTo('/bugsys/event/list');
	});

	project.on('change', function() {
		
		var projectID =  $(this).val();
		
		if (projectID != '0') {
			
			$.get('/bugsys/event/getOptionsByProject',
				{'projectID': projectID},
				function(data) {
					
					listStatus 			= data[0][1];
					listSteps 			= data[1][1];
					listUsersProject 	= data[2][1];
					listUseCases 		= data[3][1];
					listEventType		= data[4][1];
					workflowValue 		= data[5][1];
					
					populateComboBoxUserResponsible(userResponsible, listUsersProject);
					workflow.val(workflowValue.title);
					populateComboBoxStep(step, listSteps);
					populateComboBoxUseCase(useCase, listUseCases);
					populateComboBoxStatus(currentStatus, listStatus);
					populateComboEventType(eventType, listEventType);
				}
			);
			
		} else {
			eventType.html('').attr('readOnly','readOnly').removeClass('input-error');
			userResponsible.html('').attr('readOnly','readOnly').removeClass('input-error');
			workflow.val('').attr('readOnly','readOnly').removeClass('input-error');
			step.html('').attr('readOnly','readOnly').removeClass('input-error');
			useCase.html('').attr('readOnly','readOnly').removeClass('input-error');
			currentStatus.html('').attr('readOnly','readOnly').removeClass('input-error');
		}
		
	});
	
	persistEvent.on('click', function() {
		
		if(formEventIsValid()) {
			
			$.post('/bugsys/event/event',{
				'id': idEvent.val(),
				'eventType': eventType.val(),
				'project': project.val(),
				'userResponsible': userResponsible.val(),
				'workflowTitle': workflow.val(),
				'step': step.val(),
				'useCase': useCase.val(),
				'currentStatus': currentStatus.val(),
				'description': description.val()
			}, 
			function(data) {
				
				var status =  data[0][0];
				var message = data[0][1];
				
				if (status == 'success') {
					
					if(idEvent.val() == "") {
						
						toastr.success('clique aqui para fechar!', 'Registro incluído com sucesso!');
						setTimeout(function(){
							redirectTo('/bugsys/event/list');
						}, 1500);
						
					} else {
						
						toastr.success('clique aqui para fechar!', 'Registro alterado com sucesso!');
						setTimeout(function() {
							redirectTo('/bugsys/event/list');
						}, 1500);
					}
					
				} else {
					toastr.error('clique aqui para fechar!', message);
					return;
				}
			});
		}
	});
	
	// Dados da Tabela
	var tableEvent      = $('#tableEvent');
	var	search	       = $('#search');
	var oTable = tableEvent.dataTable({
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

function loadComboboxProject(project) {
	
	var id = $('#idEvent').val();
	
	if (id != undefined) {
		
		if (id == "") {
			
			project.removeAttr('readOnly');
			
			$.get('/bugsys/event/populateComboProjectAdd',
					{},
					function(data) {
						
						var options = '<option value="0">Selecione</option>';
						
						for (var i = 0; i < data.length; i++) {
							options += '<option value="' + data[i].id + '">' + data[i].projectName + '</option>';
						}
						
						project.html(options);
					}
			);
		} else {
			
			$.get('/bugsys/event/populateComboProjectEdit',
					{'id' : id},
					function(data) {
						
						var options = '';
						
						for (var i = 0; i < data.length; i++) {
							options += '<option value="' + data[i].id + '">' + data[i].projectName + '</option>';
						}
						
						project.html(options);
					}
			);
		}
	}
	
}

function populateComboBoxUserResponsible(userResponsible, listUsersProject) {
	
	userResponsible.removeAttr('readOnly');
	
	var options = '<option value="0">Selecione</option>';
	
	for (var i = 0; i < listUsersProject.length; i++) {
		
		var id = listUsersProject[i].id;
		var nameAndFunction = listUsersProject[i].name + ' - ' + listUsersProject[i].employeeTypeString;
		
		options += '<option value="' + id + '">' + nameAndFunction + '</option>';
	}
	
	userResponsible.html(options);
}

function populateComboBoxStep(step, listSteps) {
	
	step.removeAttr('readOnly');
	
	var options = '<option value="0">Selecione</option>';
	
	for (var i = 0; i < listSteps.length; i++) {
		options += '<option value="' + listSteps[i].id + '">' + listSteps[i].title + '</option>';
	}
	
	step.html(options);
}

function populateComboBoxUseCase(useCase, listUseCases) {
	
	useCase.removeAttr('readOnly');
	
	var options = '<option value="0">Selecione</option>';
	
	for (var i = 0; i < listUseCases.length; i++) {
		
		var id = listUseCases[i].id;
		var codeAndName = listUseCases[i].code + ' - ' + listUseCases[i].name;
		
		options += '<option value="' + id + '">' + codeAndName + '</option>';
	}
	
	useCase.html(options);
}

function populateComboBoxStatus(currentStatus, listStatus) {
	
	currentStatus.removeAttr('readOnly');
	
	var options = '<option value="0">Selecione</option>';
	
	for (var i = 0; i < listStatus.length; i++) {
		options += '<option value="' + listStatus[i].id + '">' + listStatus[i].status + '</option>';
	}
	
	currentStatus.html(options);
}

function populateComboEventType(eventType, listEventType) {
	
	eventType.removeAttr('readOnly');
	
	var options = '<option value="0">Selecione</option>';
	
	for (var i = 0; i < listEventType.length; i++) {
		options += '<option value="' + listEventType[i].id + '">' + listEventType[i].eventType + '</option>';
	}
	
	eventType.html(options);
}

function formEventIsValid() {
	
	var success = true;
	$('[valid="valid"]').each(
	   function(key, item) {
		 if($(item).val() == "0" || $(item).val().trim() == "") {
			 
			 $(item).addClass('input-error');
		
			 $(item).on('blur', function(){ $(this).removeClass('input-error'); });
			 
			 success = false;

		 } else {
			 $(item).removeClass('input-error');
		 }
	   }
    );
	return success;
}
