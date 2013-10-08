	
	var LocalStorage = ( function ( localStorage ) {
		
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