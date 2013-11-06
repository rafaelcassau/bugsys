$(function(){
	

	var newEvent 		= $('#new-event');
	var persistEvent	= $('#persistEvent');
	var cancel			= $('#cancel');
	
	newEvent.on('click', function(){
		redirectTo('/bugsys/event/event');
	});
	
	cancel.on('click', function() {
		redirectTo('/bugsys/event/list');
	});

	persistEvent('click', function() {
		
		$.post('/bugsys/event/event',{
				'': 0
		   }, 
		   function(data){
		
		   }
		);
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