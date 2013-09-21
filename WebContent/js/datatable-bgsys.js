$.fn.dataTableExt.oPagination.four_button = 
	{
	    "fnInit": function ( oSettings, nPaging, fnCallbackDraw )
	    {
	    	var contentPaginate;
	
	        nPrevious = document.createElement( 'button' );
	        nNext     = document.createElement( 'button' );
	         
	        //nPrevious.appendChild(document.createTextNode( oSettings.oLanguage.oPaginate.sPrevious ) );
	        //nNext.appendChild(document.createTextNode( oSettings.oLanguage.oPaginate.sNext ) );
	       
	        contentPaginate = document.getElementById( oSettings.oLanguage.oPaginate.contentPaginate );

	        nPrevious.className = "btn btn-bgsys btn-blue-bgsys previous";
	        nNext.className     = "btn btn-bgsys btn-blue-bgsys next";
	       
	       	$(nPrevious).append('<span class="glyphicon glyphicon-chevron-left"></span>');
	       	$(nNext).append('<span class="glyphicon glyphicon-chevron-right"></span>');

			$(contentPaginate).append('<div class="btn-group"></div>');
			$(contentPaginate).find('.btn-group').append(nPrevious);
	        $(contentPaginate).find('.btn-group').append(nNext);
	        
	          
	        $(nPrevious).click( function() {
	            oSettings.oApi._fnPageChange( oSettings, "previous" );
	            fnCallbackDraw( oSettings );
	        } );
	          
	        $(nNext).click( function() {
	            oSettings.oApi._fnPageChange( oSettings, "next" );
	            fnCallbackDraw( oSettings );
	        } );
	      
	        /* Disallow text selection */
	        $(nPrevious).bind( 'selectstart', function () { return false; } );
	        $(nNext).bind( 'selectstart', function () { return false; } );
	    },
	     
	     
	    "fnUpdate": function ( oSettings, fnCallbackDraw )
	    {
	        if ( !oSettings.oLanguage.oPaginate.contentPaginate ) {
	        	  console.log('informe o a configuração contentPaginate!');
	              return;
	        }
	         
	        //gera contador da paginação
	        var count =  '<span class="paginate-bgsys">' + oSettings._iDisplayStart + ' - ' + oSettings._iDisplayEnd + ' de ' + oSettings.aiDisplay.length + '</span>';

	        /* Loop over each instance of the pager 
	        var an = oSettings.aanFeatures.p;*/

	        var paginate = document.getElementById(oSettings.oLanguage.oPaginate.contentPaginate);

	        $(paginate).find('.paginate-bgsys').remove();

	        $(paginate).prepend(count);

	        var an = $(paginate).find('button');

	        var attrDisabled = document.createAttribute("disabled");

	        for ( var i = 0, iLen = an.length ; i < iLen ; i++ )
	        {
	            var buttons = an;
			
			    if ( oSettings._iDisplayStart === 0 ) {
	            	buttons[0].setAttributeNode(attrDisabled);
	            }
	            else {
	                buttons[0].className = "btn btn-bgsys btn-blue-bgsys";
	                buttons[0].removeAttribute('disabled');
	            }
	              
	            if ( oSettings.fnDisplayEnd() == oSettings.fnRecordsDisplay() ) {
	            	if($(buttons[1]).attr("disabled")){
	            		buttons[1].setAttributeNode(attrDisabled);
	            	}
	                
	            }
	            else {
	                buttons[1].className = "btn btn-bgsys btn-blue-bgsys";
	                buttons[1].removeAttribute('disabled');
	            }
	        }
	    }
	};