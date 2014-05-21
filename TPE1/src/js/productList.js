var page_size = 4;
var gridType = false;
$(function() {

$.when(
	loadMenu(),
	loadProducts(false))
	.done(
		function(result1, result2, result3)
    	{
        	$("#list").click(
        		function()
        		{
        			loadProducts(false);
        	});
        	$("#grid").click(
        		function()
        		{
        			loadProducts(true);
        	});
    	}
    );
	
$("#cantPagina").change(function(){
  			page_size = $(this).val();
  			loadProducts(gridType);
  		});

	function loadProducts(grid)
	{
		gridType = grid;
		var html = "<p class='bold big error textCenter center'>No hay productos en la API para la categoria seleccionada</p>";
		$("#toolbar-list").hide();
	    $("#pagination-list").hide();
		$("#breadcrumbs-two").empty();
		$("#products-list").empty();
		var queryParams = parseQueryString();
		queryParams.each(
		function(key, value, pos)
		{
			$("#breadcrumbs-two").append("<li><a href='./index.html'>Inicio</a></li>");
			var id = $.base64('decode', value);
			var url = "http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductsBy";
			if(key.indexOf('sub') != -1)
			{
				url += "Subc";
				$.getJSON("http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetSubcategoryById&id=" + id + "&callback=?", 
			        function(result) {
			        	if(result.error === undefined)
			        	{
							$("#breadcrumbs-two").append("<li><a href='./product-list.html?categoryId=" + $.base64('encode', result.subcategory.category.id) + "'>" + result.subcategory.category.name + "</a></li>");
			        		$("#breadcrumbs-two").append("<li><a href='./product-list.html?subCategoryId=" + $.base64('encode', result.subcategory.id) + "'>" + result.subcategory.name + "</a></li>");
			        	}
			        }
			    );
			}
			else
			{
				url += "C";
				$.getJSON("http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetCategoryById&id=" + id + "&callback=?", 
			        function(result) {
			        	if(result.error === undefined)
			        	{
			        		$("#breadcrumbs-two").append("<li><a href='./product-list.html?categoryId=" + $.base64('encode', id) + "'>" + result.category.name + "</a></li>");
			        	}
			        }
			    );
			}

			url += "ategoryId&id=" + id;

			$.getJSON(url + "&page_size=" + page_size + "&sort_order=desc&callback=?", 
		        function(result) {
		            var products = result.products;
		            if(products != undefined && products.length > 0) 
	            	{
	            		html = '';
	            		$("#toolbar-list").show();
	            		$("#pagination-list").show();
			            for(var i = 0; i < products.length; i++)
			            {
			            	if(grid)
			            	{
			            		if((i%3) == 0)
				            	{
				            		if(i > 0) html += "</ul>";
				            		html += "<ul class='products-grid' >";
				            	}
				            	if((i%3) == 2)
				            	{
									html += getHTMLProduct(products[i], 1, false, true);
				            	}
				            	else
				            	{
									html += getHTMLProduct(products[i], 1, false, false);
				            	}
			            	}
			            	else
			            	{
								html += "<li class='item '> <a href='product.html?productId=" + $.base64('encode', products[i].id) + "' class='product-image' > " +
		                		"<img alt='product' src='" + products[i].imageUrl[0] + "'></a>" +
								"<div class='product-shop'>" + 
								"<div class='no-fix'>" +
								"<h2 class='product-name'><a href='product.html?productId=" + $.base64('encode', products[i].id) + "' >" + products[i].name + "</a></h2>" +
								"<div class='price-box'> <span class='regular-price' >" +
								"<span class='price'>" + products[i].price + "</span> </span> </div>" +
								" <div class='ratings'> " +
									" <div class='rating-box'> " +
								" <div class='rating' style='width:" + Math.floor(Math.random() * 101) + "%'></div> " +
								" </div> " + " </div> " +
								" <div class='btn-set'> " +
								" <div class='actions'><a class='btn-circle first-bg btn-active' href='./product.html?productId=" + $.base64('encode', products[i].id) + "'> " +
								"Comprar <i class='icon-shopping-cart'></i> </a> <a class='btn-circle first-bg-hover'> " +
								" <i class='icon-heart'></i> </a>  " +
								" </div>" + " </div> " + " <br><hr> " +
								"<div class='desc std'> Los productos no tienen comentarios en la API. </div> " +
								"</div> " + " </div> " + "</li>";
			            	}
			            }
			        }
	            	if(grid)
	            	{
		            	$("#products-list").hide();
		            	$("#products-list").empty();
		            	$("#products-grid-list").append(html);	
		            	$("#products-grid-list").show();
	            	}
	            	else
	            	{
	            		$("#products-list").empty();
	            		$("#products-list").append("<ol class='products-list' >" + html + "</ol>");
	            		$("#products-list").show();
	            		$("#products-grid-list").hide();
	            	}
	            	
		        }
		    );
		}
	);
	}

});