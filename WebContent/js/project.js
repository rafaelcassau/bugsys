
$(function() {
	
	/***
	 * Inputs Project
	 */ 
	var startDate = 		$('#startDate');
	var estimatedEndDate = 	$('#estimatedEndDate');
	var endDate = 			$('#endDate');
	
	startDate.datepicker({
	    dateFormat: 'dd/mm/yy',
	    dayNames: ['Domingo','Segunda','Ter�a','Quarta','Quinta','Sexta','S�bado'],
	    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
	    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','S�b','Dom'],
	    monthNames: ['Janeiro','Fevereiro','Mar�o','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
	    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
	    nextText: 'Pr�ximo',
	    prevText: 'Anterior'
	});

	estimatedEndDate.datepicker({
		dateFormat: 'dd/mm/yy',
		dayNames: ['Domingo','Segunda','Ter�a','Quarta','Quinta','Sexta','S�bado'],
		dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
		dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','S�b','Dom'],
		monthNames: ['Janeiro','Fevereiro','Mar�o','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
		monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
		nextText: 'Pr�ximo',
		prevText: 'Anterior'
	});

	endDate.datepicker({
		dateFormat: 'dd/mm/yy',
		dayNames: ['Domingo','Segunda','Ter�a','Quarta','Quinta','Sexta','S�bado'],
		dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
		dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','S�b','Dom'],
		monthNames: ['Janeiro','Fevereiro','Mar�o','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
		monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
		nextText: 'Pr�ximo',
		prevText: 'Anterior'
	});
	
	/***
	 * Cadastro de casos de uso
	 */
//	var useCaseAdd = 	$('#use-case-add');
//	var useCaseModal = 	$('#modal-use-case');
//	var saveUseCase =   $('.save-use-case');
//	var cancelUseCase = $('.cancel-use-case');
	
	/***
	 * dados caso de uso
	 */
//	
//	var useCaseID =   			$('#id');
//	var code = 					$('#code');
//	var name = 					$('#name');
//	var descriptionUseCase = 	$('#descriptionUseCase');
//	var projectID =     		$('#projectID');
	
//	useCaseAdd.on('click', function() {
//		useCaseModal.modal();
//	});
	
//	saveUseCase.on('click', function() {
//		
//		if (formUseCaseIsValid()) {
//			
//			$.post('/bugsys/usecase/usecase',{
//				'useCaseID'   			: useCaseID.val(),
//				'code'        			: code.val(),
//				'name'        			: name.val(),
//				'descriptionUseCase'	: descriptionUseCase.val(),
//				'projectID'   			: projectID.val()
//				
//			}, function(data) {
//				
//				var status   = data[0][0];
//				var message  = data[0][1];
//				
//				if (status == 'success') {
//					
//					if (id.val() == "") {
//						
//						toastr.success('clique aqui para fechar!', 'Registro inclu�do com sucesso!');
//						setTimeout(function(){
////							redirectTo('/bugsys/usecase/list');
//						}, 1500);
//						
//					} else {
//						
//						toastr.success('clique aqui para fechar!', 'Registro alterado com sucesso!');
//						setTimeout(function(){
////							redirectTo('/bugsys/usecase/list');
//						}, 1500);
//					}
//					
//				} else {
//					toastr.error('clique aqui para fechar!', message);
//				}
//			});
//		}
//		
//	});
	
//	cancelUseCase.on('click', function() {
//		useCaseModal.hide();
//	});
	
//	function formUseCaseIsValid() {
//    	var success = true;
//    	$('[usecase="usecase"]').each(
//    	   function(key, item) {
//    		 if($(item).val() == "") {
//    			 
//    			 $(item).addClass('input-error');
//    		
//    			 $(item).on('blur', function(){ $(this).removeClass('input-error'); });
//    			 
//    			 success = false;
//
//    		 } else {
//    			 $(item).removeClass('input-error');
//    		 }
//    	   }
//        );
//    	return success;
//     }
});