	
	/*
	 * App.js contem recursos que serao utilizados em todo o sistema
	 *  
	 * */
	
	 function redirectTo(url) {
		 return window.location.href = url;
	 }
	 
	 function emailIsValid(email) {
			
		var validEmail=/^.+@.+\..{2,}$/;
		
		return validEmail.test(email);
	}
	 
	 function formIsValid() {
	    	var success = true;
	    	$('input[class != "id"]').each(
	    	   function(key, item) {
	    		 if($(item).val() == "") {
	    			 
	    			 $(item).addClass('input-error');
	    		
	    			 $(item).on('blur', function(){
	    				 $(this).removeClass('input-error');
	    			 });
	    			 
	    			 success = false;
	    		 } else {
	    			 $(item).removeClass('input-error');
	    		 }
	    	   }
	        );
	    	return success;
	     }
	
	 /*
	 * Configuração plugin de notificação toastr.js
	 *  
	 * */
	 
	 toastr.options = {
		    debug: false,
		    positionClass: 'toast-top-right',
		    onclick: function () {
		        toastr.clear();
		    }
		};
	 
	 function cnpjIsValid(cnpj) {
		 
		    cnpj = cnpj.replace(/[^\d]+/g,'');
		 
		    if(cnpj == '') return false;
		     
		    if (cnpj.length != 14)
		        return false;
		 
		    // Elimina CNPJs invalidos conhecidos
		    if (cnpj == "00000000000000" || 
		        cnpj == "11111111111111" || 
		        cnpj == "22222222222222" || 
		        cnpj == "33333333333333" || 
		        cnpj == "44444444444444" || 
		        cnpj == "55555555555555" || 
		        cnpj == "66666666666666" || 
		        cnpj == "77777777777777" || 
		        cnpj == "88888888888888" || 
		        cnpj == "99999999999999")
		        return false;
		         
		    // Valida DVs
		    tamanho = cnpj.length - 2
		    numeros = cnpj.substring(0,tamanho);
		    digitos = cnpj.substring(tamanho);
		    soma = 0;
		    pos = tamanho - 7;
		    for (i = tamanho; i >= 1; i--) {
		      soma += numeros.charAt(tamanho - i) * pos--;
		      if (pos < 2)
		            pos = 9;
		    }
		    resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
		    if (resultado != digitos.charAt(0))
		        return false;
		         
		    tamanho = tamanho + 1;
		    numeros = cnpj.substring(0,tamanho);
		    soma = 0;
		    pos = tamanho - 7;
		    for (i = tamanho; i >= 1; i--) {
		      soma += numeros.charAt(tamanho - i) * pos--;
		      if (pos < 2)
		            pos = 9;
		    }
		    resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
		    if (resultado != digitos.charAt(1))
		          return false;
		           
		    return true;
		}
	 
