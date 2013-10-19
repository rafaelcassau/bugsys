
	var MemberModel = ( function ( jQ, store ) {
		
		var url = {
			base: '/bugsys/user'
		};
		
		var Members = [];
		
		var save = function( members ) {
			Members.push( members );
		};
		
		var commit = function() {
			store.store("member", Members);
		};
		
		var get = function( value, callback ) {
			 jQ.get( url.base + '/user', { 'name': value }, function( result ) {
				if ( callback )
					 callback( result );
			 });
		};
		
		var findByID = function ( id ) {
			var result = null;
			
			var membersObj = ( Members.length != 0 ) ? JSON.parse(Members) : 0;
			
			for ( var i = 0; i < membersObj.length; i++ ) {
				 if( membersObj[i].id == id ) {
					result = membersObj[i];
					break;
				 }
			}
			return result;
		};
		
		return {
			get      : get,
			save     : save,
			findByID : findByID, 
			commit   : commit
		};
		
	}) ( jQuery, SessionStorage );
	
	
	
	var MemberView = ( function( $, message, model ) {
		
		var el = {
			input : $("#member-input"),
			list  : $("#member-list-items")
		};
		
		var cssClass = {
			error : "input-error"
		};
		
		var init = function () {
			
			el.input.on('blur', function () { $(this).removeClass(cssClass.error); });
			
			bindAutoComplete();
		};
		
		var add = function ( member ) {
			
			model.save(JSON.stringify(member));
			
			appendMember(member);
			
			model.commit();
			
			el.input.val("");
		};
		
		var appendMember = function (member) {
			el.list.append('<li class="list-group-item"> ' + member.name + ' - ' + member.employeeType.employeeType + '<a href="#" data="' + member.id + '" class="close" aria-hidden="true">&times;</a> </li>');
		};
		
		var bindAutoComplete = function () {
			
			el.input.autocomplete({
		        source: function (request, response) {
		        	model.get( request.term, function( result ) {
		        	   response(
	                        $.map(result, function (item) {
	                        	if( model.findByID(item.id) == null ) {
		                            return {
		                            	value: item,
		                                label: item.name + ' - '+ item.employeeType.employeeType
		                            };
	                        	}
	                        })
	                    );						
					});
		        },
		        
		        focus: function (event, ui) {
		        	
		        	event.preventDefault();
		        	
		            this.value = ui.item.label;
		        },
		        
		        select: function (event, ui) {
		            
		        	event.preventDefault();
		            
		            this.value = ui.item.label;
		            
		            add(ui.item.value);
		        }
		    });
		};
		
		return {
			init: init
		};
		
	})( jQuery, toastr, MemberModel );

	 MemberView.init();
	 
