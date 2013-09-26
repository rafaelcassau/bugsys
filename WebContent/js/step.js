

	
	var Step = ( function ( $ , localStorage ) { 
		
		var array = [];
		
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
				
				var step = new Object();
				step.id = count();
				step.description = el.input.val();
				
				el.list.append(newStep(step));
				
				array.push(step);
				
				el.input.val("");
				
			} else {
				el.input.addClass(cssClass.error);
			}
			
			storage();
		};
		
		var dataBind = function () {
			
			var stepsHistory = JSON.parse(localStorage.getItem("steps"));
			
			clearList();
			
			if(stepsHistory){
				$.each( stepsHistory , function (index, step) {
					el.list.append(newStep(step));
					array.push(step);
				});
			}
		};
		
		//remove step
		var remove = function () {
		
			deleteStep(this.getAttribute("id"));
			
			updateStorage();
			
			$(this).parent().remove();
			
			dataBind();
		};
		
		var clearList = function () {
			el.list.html("");
			array.length = 0;
		};
		
		var clearStorage = function () {
			localStorage.clear();
		};
		
		//create new step
		var newStep = function (step) {
			return  $('<li class="list-group-item"> ' + step.description + '<a href="#" id="' + step.id + '" class="close" aria-hidden="true">&times;</a> </li>');
		};
		
		//valid input
		var validInput = function () {
			return el.input.val() != '';
		};
		
		//count step
		var count = function (){
			return array.length + 1;
		};
		
		
		var storage = function () {
			localStorage.setItem("steps", JSON.stringify(array));
		};
		
		var updateStorage = function () {
			clearStorage();
			storage();
		};

		var findStepByID = function (stepID) {
			for(var i = 0; i < array.length; i++){
				if(array[i].id == stepID) {
					return array[i];
				}
			}
		};
		
		var deleteStep = function(stepID){
			for(var i = 0; i < array.length; i++){
				if(array[i].id == stepID){
					array.splice(i, 1);
					break;
				}
			}
		};
		
		//public methods
		return {
			init: init,
			findStepByID: findStepByID,
			deleteStep: deleteStep,
			count: count,
			clearStorage: clearStorage
		};
		
	})( jQuery,  window.localStorage );
	
	//Initialize
	window.onload = function() {
		Step.init();		
	};
	
	
	