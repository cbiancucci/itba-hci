$(function() {

	$.getJSON("http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetAllCategories&callback=?", function(result) {

		$("#menu").append("<ul>");
		$("#menu").append("<ul><li><a href=''><span class='home_icon'></span></a></li></ul>");

		$.each(result.categories, function () {

			var catid = this.id;
			var name = this.name;
	
			var htmlid = catid + name;

			$("#menu").append("<ul><li><a href='' id='" + htmlid + "'>" +  name + "</a></li></ul>");

			$.getJSON("http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetAllSubcategories&id=" + catid + "&callback=?", function (result) {

				$("#menu").append("<div><ul>");
				$.each(result.subcategories, function() {

					var subid = this.id;
					var namesub = this.name;

					$("#" + htmlid).append("<li><a href=''>" + namesub + "</a></li>");

				});

				$("#menu").append("</ul></div>");

			});
		});

/*
		$.each(result.categories, function () {

			var catid = this.id;
			var name = this.name;
	
			$("#menu").append("<li><a href=''>" +  name + "</a></li>");

			$.getJSON("http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetAllSubcategories&id=" + catid + "&callback=?", function (result) {
			
				$.each(result.subcategories, function () {

					var namesub = this.name;
					var subcatid = this.id;

					$("#menu").append("<div><ul><li><a href=''>" + namesub + "</a></li>");

				});
			});

			

		});

		*/

	});
});
/*
$(function () {

	$.getJSON("http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetAllCategories&callback=?", function (result) {

		$.each(result.categories, function () {

			var name = this.name;
			var catid = this.id
			var gender = this.attributes[1].values;
			var age = this.attributes[0].values;

			$.each(gender, function () {
				var auxgender = this;
				
				$.each(age, function () {
					var id = "#" + auxgender + this;
					$(id).append('<li class="dropdown-submenu"><a href="category.html?gender=' + auxgender + '&age=' + this + '&category=' +catid+'-'+ name + '">' + name + '</a><ul class="dropdown-menu" id="' + name + '"></ul></li>');
				});
        	});


			$.getJSON("http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetAllSubcategories&id=" + this.id + "&callback=?", function (result) {
			
				$.each(result.subcategories, function () {

					var namesub = this.name;
					nameaux = "#" + name;
					var gender = this.attributes[0].values;
					var age = this.attributes[1].values;
					var subcatid = this.id;

					$.each(gender, function () {
						var auxgender = this;
						
						$.each(age, function () {
							var parentid = "#" + this + auxgender;

							$(parentid).children().children(nameaux).append('<li><a href="">' + namesub + '</a>');
								url = $(parentid).children("li").children("a").attr('href') + "&subcategory=" + subcatid +"-"+namesub;

							$(parentid).children().children(nameaux).children("li").children("a").last().attr('href', url);
						});
					});

				});
			});
		});
	});
});
*/