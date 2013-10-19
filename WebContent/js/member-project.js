
	var MemberModel = ( function ( jQ, storage ) {
		
		var url = {
			base: '/bugsys/project'
		};
		
		var Members = [];
		
		var save = function( members ) {
			Members.push( members );
		};
		
		var commit = function() {
			storage.store("member", Members);
		};
		
		var empty = function () {
			Members.length = 0;
		};
		
		var refresh = function(){
 			storage.update("member", Members);
 		};
		
		var getByName = function( value, callback ) {
			 jQ.get( url.base + '/findUserByNameJSON', { 'name': value }, function( result ) {
				if ( callback )
					 callback( result );
			 });
		};
		
		var getMembersByID = function( value, callback ) {
			 jQ.get( url.base + '/getMembersPopulateAutoCompleteJSON', { 'membersProject': value }, function( result ) {
				if ( callback )
					 callback( result );
			 });
		};
		
		var getAll = function () {
			return storage.get("member");
		};
		
		var deleteMember = function ( id ) {
			
			if( Members.length > 0 ){
				
	 			for ( var i = 0; i <= Members.length; i++ ){
					if( Members[i] == id ) {
						Members.splice(i, 1);
						break;
					}
				}
			};
		};
		
		var find = function ( id ) {
			
			var result = null;
			
			if ( id != undefined ) {
				
				for ( var i = 0; i <= Members.length; i++ ) {
					 if( Members[i] == id ) {
						result = Members[i];
						break;
					 }
				}
			} 
			else {
				result = Members;
			}
			
			return result;
		};
		
		return {
			getByName      : getByName,
			save           : save,
			find	       : find, 
			commit         : commit,
			empty	       : empty,
			refresh		   : refresh,
			deleteMember   : deleteMember,
			getAll		   : getAll,
			getMembersByID : getMembersByID
			
		};
		
	}) ( jQuery, SessionStorage );
	
	
	
	var MemberView = ( function( $, message, model ) {
		
		var el = {
			body  : $("body"),
			input : $("#member-input"),
			list  : $("#member-list-items")
		};
		
		var cssClass = {
			error : "input-error"
		};
		
		var init = function () {
			
			el.input.on('blur', function () { $(this).removeClass(cssClass.error); });
			el.body .on('click', 'a.close', remove);
			
			bindAutoComplete();
			
			bind();
		};
		
		var add = function ( member ) {
			
			model.save(	member.id );
			
			appendMember(member);
			
			model.commit();
			
			el.input.val("");
		};
		
		var bind = function () {
			
			var members = model.getAll();
			
			if ( members ) {
			
				for ( var i = 0; i <= members.length; i++ ) {
					model.getMembersByID(members[i], function (member) {
						add(member);
					});
				};
			}
		};
		
		var remove = function () {

			model.deleteMember(this.getAttribute("data"));
			
			model.refresh();
			
			$(this).parent().remove();
			
		};
		
		
		var bindAutoComplete = function () {
			
			el.input.autocomplete({
		        source: function (request, response) {
		        	model.getByName( request.term, function( result ) {
		        	   response(
	                        $.map(result, function (item) {
	                        	
	                        	if( model.find( item.id ) == null ) {
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
		
		var clearlist = function () {
			el.list.html("");
			model.empty();
		};
		var appendMember = function (member) {
			el.list.append('<li class="list-group-item"> ' + member.name + ' - ' + member.employeeType.employeeType + '<a href="#" data="' + member.id + '" class="close" aria-hidden="true">&times;</a> </li>');
		};
		
		return {
			init: init
		};
		
	})( jQuery, toastr, MemberModel );

	 MemberView.init();
	 
	 