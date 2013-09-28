
	var Storage = ( function ( localStorage ) {
		
		var store = function (base, data) {
			localStorage.setItem(base, data);
		};

		var drop = function () {
			localStorage.clear();
		};
		
		var get = function (base){
			return localStorage.getItem(base);
		};
		
		var update = function (base, data){
			drop();
			store(base, data);
		};
		
		return {
			store : store,
			drop  : drop,
			get   : get,
			update: update
		};
		
	})( window.localStorage );

 	var StepModel = ( function ( storage ) {
 		
 		var Steps = [];
 		
 		var save = function(step){
 			Steps.push(step);
 		};
 		
 		var refresh = function(){
 			storage.update("steps", Steps);
 		};
 		
 		var getAll = function () {
 			return storage.get("steps");
 		};
 		
 		var count = function(){
 			return Steps.length;
 		};
 		
 		var empty = function(){
 			Steps.length = 0;
 		};
 		
		var commit = function (){
 			storage.store("steps", Steps);
 		};
 		
 		var deleteStep = function ( step ) {
 			for ( var i = 0; i < Steps.length; i++ ){
				if( Steps[i] == step ) {
					Steps.splice(i, 1);
					break;
				}
			}
		};
		
		var find = function ( step ) {
			var result = null;
			for ( var i = 0; i < Steps.length; i++ ) {
				if( Steps[i] == step ) {
					result = Steps[i];
					break;
				}
			}
			return result;
		};
 		
 		return {
 			save      : save,
 			refresh	  : refresh,
 			deleteStep: deleteStep,
 			getAll	  : getAll,
 			find      : find,
 			count     : count,
 			empty	  : empty,
 			commit    :commit
 		};
 		
 	})( Storage );
 	
 	
 	
	var Step = ( function ( $ , message, model) { 
		
		var el = {
			body  : $("body"),
			add   : $("#step-add"),
			input : $("#step-input"),
			list  : $("#step-list-items")
		};
		
		var cssClass = {
			error : "input-error"
		};
		
		//bind events
		var init = function () {
			el.add  .on('click', add);
			el.body .on('click', 'a.close', remove);
			el.input.on('blur', function () { $(this).removeClass(cssClass.error); });
			el.input.on('keypress', function (e) {
				if(e.keyCode == 13){
					add();
				}		
			});
			
			dataBind();
		};
		
		//add step
		var add = function () {
			
			if ( validInput() ) {
				
				if ( model.find( el.input.val() ) ) {
					message.error('clique aqui para fechar!', 'Já existe uma fase cadastrada com essa descrição!');
					return;
				}
				
				model.save(el.input.val());
				
				el.list.append(addStep(el.input.val()));
				
				el.input.val("");
				
			} else {
				el.input.addClass(cssClass.error);
			}
			
			model.commit();
		};
		
		var dataBind = function () {
			
			var steps = model.getAll();
			
			if(steps) { 
				
				clearList();
				
				var stepArray = steps.split(',');
				
				for ( var i = 0; i < stepArray.length; i++ ) {
					el.list.append(addStep(stepArray[i]));
					model.save(stepArray[i]);
				};

				model.commit();
			}
		};
		
		var remove = function () {
		
			model.deleteStep(this.getAttribute("data"));
			
			model.refresh();
			
			$(this).parent().remove();
			
			dataBind();
		};
		
		var clearList = function () {
			el.list.html("");
			model.empty();
		};

		var addStep = function (step) {
			return  $('<li class="list-group-item"> ' + step + '<a href="#" data="' + step + '" class="close" aria-hidden="true">&times;</a> </li>');
		};
		
		//valid input
		var validInput = function () {
			return el.input.val() != '';
		};
		
		return {
			init: init
		};
		
	})( jQuery, toastr, StepModel );
	
	
	
	//Initialize
	window.onload = function() {
		Step.init();		
	};
	
	//Out window
	window.unload = function () {
		StepModel.empty();
		Storage  .drop();
	};

	
	
	