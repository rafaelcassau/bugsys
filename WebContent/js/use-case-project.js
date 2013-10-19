
	var UCModel = ( function ( jQ, storage ) { 
		
		var url = {
			base: '/bugsys/project'
		};
		
		var UCases = [];
		
		var save = function( members ) {
			UCases.push( members );
		};
		
		var commit = function() {
			storage.store("ucases", UCases);
		};
		
		var empty = function () {
			UCases.length = 0;
		};
		
		var refresh = function (){
 			storage.update("ucases", UCases);
 		};
		
		var getAll = function () {
			return storage.get("ucases");
		};
		
		var deleteUC = function ( code ) {
			
			if( UCases.length > 0 ){
				
	 			for ( var i = 0; i <= UCases.length; i++ ){
	 				
	 				var ucase = JSON.parse(UCases[i]);
	 				
					if( ucase.code == code ) {
						UCases.splice(i, 1);
						break;
					}
				}
			};	
		};
		
		var findByCode = function (code) {
			
			var result = null;
			
			var ucases = UCases;
			
			if ( ucases.length > 0 ) {
				jQ.map( ucases, function ( item ) {
					var ucase = JSON.parse(item);
					if ( ucase.code == code ) {
						result = ucase;
					}
				});				
			} 
			
			return result;
		};
		
		return {
			save           : save,
			commit         : commit,
			empty	       : empty,
			refresh		   : refresh,
			getAll		   : getAll,
			findByCode	   : findByCode,
			deleteUC       : deleteUC
		};
		
	}) ( jQuery, SessionStorage );
	
	var UCaseView = ( function( $, message, model ) {
		
		var el = {
			body       : $("body"),
			list       : $("#use-case-list-items"),
			save       : $("#save-use-case"),
			newuc      : $("#use-case-add"),
			ucmodal    : $("#modal-use-case"),
			flagIsEdit : $("#flag-isedit")
		};
		
		var input = {
			code        : $("#code"),
			name        : $("#name"),
			description : $("#descriptionUseCase")
		};
		
		var cssClass = {
			error : "input-error"
		};
		
		var init = function () {
			
			el.body .on('click','a.close-uc', remove);
			el.body .on('click','a.edit-uc' , edit);
			el.save .on('click', add);
			el.newuc.on('click', newuc);
			
			el.ucmodal.on('show.bs.modal', cleanFields);
			el.ucmodal.on('hide.bs.modal', cleanFields);
			
			bind();
		};
		
		var newuc = function(){
			el.ucmodal.modal();
		};
		
		var add = function () {
			
			if ( formIsValid () ) {
				
				var ucByCode = model.findByCode( input.code.val() );
				
				if ( ucByCode != null && ucByCode.code != el.flagIsEdit.attr("uc-code") ) {
					
					message.error("Já existe um caso de uso como código informado!");
					
				} else {
					
					var ucase 		      = {};
						ucase.code 		  = input.code.val(),
						ucase.name 		  = input.name.val(),
						ucase.description = input.description.val();
					
					if ( el.flagIsEdit.val() != "true" ) {
						
						model.save(JSON.stringify(ucase));
						
						el.list.append(append(ucase));
						
						model.commit();
						
					} else {
						
						model.deleteUC(el.flagIsEdit.attr("uc-code"));
						
						el.body.find("a.edit-uc[data=" + el.flagIsEdit.attr("uc-code") + "]").parent()
							   .parent()
							   .remove();
						
						model.save(JSON.stringify(ucase));
						
						el.list.append(append(ucase));
						
						model.commit();
			            
                       el.ucmodal.modal('hide');
					}
				}
			}
		};
		
		var edit = function () { 
			
			el.flagIsEdit.val("true");
			el.flagIsEdit.attr("uc-code", this.getAttribute("data"));
			
			//Open modal
			el.ucmodal.modal();
			
			var ucase = model.findByCode(this.getAttribute("data"));
			
			input.code		 .val(ucase.code);
			input.name		 .val(ucase.name);
			input.description.val(ucase.description);
		};
		
		var remove = function() {
		
			model.deleteUC(this.getAttribute("data"));
			
			model.refresh();
			
			$(this).parent().parent().remove();
		};
		
		
		var bind = function (){
			
			var ucases = model.getAll();
			
			var uc = JSON.parse("[" + ucases + "]");
			
			if ( ucases ) {
				$.map(uc, function ( ucase ) {
				   el.list.append(append(ucase));
				   model.save(JSON.stringify(ucase));
				});
				
				model.commit();
			}
		};
		
		var append = function ( ucase ) {
			return $("<tr>" +
					   "<td>" + ucase.code        + "</td>" +
					   "<td>" + ucase.name        + "</td>" +
					   "<td>" + ucase.description + "</td>" +
					   "<td> " +
					   		"<a href='#' data='" + ucase.code + "' class='edit-uc'> Editar  </a>" +
					   		" | " +
					   		"<a href='#' data='" + ucase.code + "' class='close-uc'> Excluir </a>" +
					   "</td> " +
					 "</tr>");
		};
		
		var cleanFields = function() {
			$('[usecase="usecase"]').each( function(key, item) {
	    		 $(item).val("");
	    	});
		};
		
		var formIsValid = function () {
			
			var success = true;
			
	    	$('[usecase="usecase"]').each( function(key, item) {
	    		 
	    		 if( $(item).val() == "" ) {
	    			 
	    			 $(item).addClass('input-error');
	    		
	    			 $(item).on('blur', function() { $(this).removeClass('input-error'); } );

	    			 success = false;

	    		 } else { 
	    			 $(item).removeClass('input-error');
	    		 }
	    	   }
	        );
	    	return success;
		};
				
		return {
			init: init
		};
		
	})( jQuery, toastr, UCModel );

	UCaseView.init();
	 
	 