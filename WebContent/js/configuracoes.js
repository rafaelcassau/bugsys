
$( function () {
	
	var tabs = $('#tab-configuracoes a[href="#usuarios"]');
		tabs.tab('show');


	var templates = new Array();

	//"shown.bs.tab", 
	$('a[data-toggle="tab"]').each(function (key, el) {

		var self = $(el);
		var href = self.attr('href');

		var page = href.replace("#", "");

		if (!templates[page]) {

			$(href).load('../configuracoes/' + page + '.html', function (data) {
				templates[page] = data;
			});

			$(href).tab('show');
		};
	});
});