
	var Storage = ( function ( localStorage ) {
		
		var store = function (base, data) {
			localStorage.setItem(base, JSON.stringify(data));
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
 		
 		var deleteStep = function ( stepID ) {
 			for(var i = 0; i < Steps.length; i++){
				if(Steps[i].id == stepID){
					Steps.splice(i, 1);
					break;
				}
			}
		};
 		
 		return {
 			save      : save,
 			refresh	  : refresh,
 			deleteStep: deleteStep,
 			getAll	  : getAll,
 			count     : count,
 			empty	  : empty,
 			commit    :commit
 		};
 		
 	})( Storage );
 	
 	
 	
	var Step = ( function ( $ , model) { 
		
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
				
				var step = { 
					id  : model.count(), 
					desc: el.input.val() 
				};
				
				model.save(step);
				
				el.list.append(addStep(step));
				
				el.input.val("");
				
			} else {
				el.input.addClass(cssClass.error);
			}
			
			model.commit();
		};
		
		var dataBind = function () {
			
			var steps = JSON.parse(model.getAll());
			
			if(steps) { 
		
				clearList();
				
				$.each( steps , function (index, step) {
					el.list.append(addStep(step));
					model.save(step);
				});
				
				model.commit();
			}
		};
		
		var remove = function () {
		
			model.deleteStep(this.getAttribute("id"));
			
			model.refresh();
			
			$(this).parent().remove();
			
			dataBind();
		};
		
		var clearList = function () {
			el.list.html("");
			model.empty();
		};

		var addStep = function (step) {
			return  $('<li class="list-group-item"> ' + step.desc + '<a href="#" id="' + step.id + '" class="close" aria-hidden="true">&times;</a> </li>');
		};
		
		//valid input
		var validInput = function () {
			return el.input.val() != '';
		};
		
		return {
			init: init
		};
		
	})( jQuery, StepModel );
	
	
	
	//Initialize
	window.onload = function() {
	
		Step.init();		
	
	};
	
	
	