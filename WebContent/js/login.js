
$(function () {
	
	var username   = $('#username');
	var password   = $('#password');
	var lnkLogar   = $('#lnkLogin');
	var errorList  = $('#error-list');
	
	lnkLogar.on('click', function() {
		login();
	});
	
	username.on("keypress", function(e){
		if(e.keyCode == 13){
			login();
		}
	});
	
	password.on("keypress", function(e){
		if(e.keyCode == 13){
			login();
		}
	});
	
	username.on('blur', function(){
		$(this).removeClass("input-error");
	});
	
	password.on('blur', function(){
		$(this).removeClass("input-error");
	});
	
	function formIsValid() {
		return (username.val().trim() != '') && (password.val().trim() != '');
	}
	
	function login() {
		if (formIsValid()) {
			$.post("/bugsys/login/login", { 
					username: username.val(), 
					password: password.val() 
			    } , function(data) {
			    	
				    	var status   = data[0][0];
				    	var message  = data[0][1];
				    	
				    	errorList.html("");
				    	
				    	if (status == "error") {
				    		errorList.append(message);
				    		errorList.show();
				    		return;
				    	}
				     
				    	redirectTo('/bugsys/dashboard/dashboard');
			    	}
			    );
		} else {
			username.addClass("input-error");
			password.addClass("input-error");
		}
	}
});

function redirectTo(url) {
	 return window.location.href = url;
}