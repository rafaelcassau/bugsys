	
	$(function() {
		
		var step_add		= $('#step-add');
		var step_input  	= $('#step-input');
		var step_list_items = $('#step-list-items');
		
		step_add.on('click', add);
		
		function add() {
			
			 if( validStep() ) {
				 
				 var count = countStep();
				 
				 var step = new Object();
				 step.id = count;
				 step.description = step_input.val();
				
				 step_list_items.append(newStep(step));
				 
			 } else {
				 step_input.addClass('input-error');
			 }
		};
		
		function newStep(step){
			var html = $('<li class="list-group-item"> ' + step.description + '<a href="#" id="' + step.id + '" class="close" aria-hidden="true">&times;</a> </li>');
				
				html.find('.close').on('click', function(ev, ui) { 
					
				});
			
				return html;
		};
		
		function validStep(){
			return step_input.val() != '';
		};
		
		function countStep(){
			return step_list_items.find("li").length + 1;
		}
	});
	
	