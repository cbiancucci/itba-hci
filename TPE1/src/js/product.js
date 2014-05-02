$(function() {

$.when(
	loadMenu(),
	loadProduct())
	.done(
		function(result1, result2)
    	{
    		$.when(
				loadProductsByCategoryId(result2.categoryId, 8, -1, 'productos-destacados'),
    			loadRelatedProducts(result2.subCategoryId))
				.done(
					function(result1)
			    	{
			        	initializeCarrousel('productos-destacados');
			    	}
		    );
    	}
    );
	
function loadRelatedProducts(id)
{

	$.getJSON("http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductsBySubcategoryId&id=" + id + "&page_size=5&callback=?", 
        function(result) {
        	var products = result.products;
        	for(var i = 0; i < products.length; i++)
        	{
        		var productURL = "./product.html?productId=" + $.base64('encode', products[i].id);
                $("#related-products").append("<tr> <td><img src='" + products[i].imageUrl[0] + "' class='mrg20 related-image' alt='" + products[i].name + "''></td>" + 
                "<td><a href='" + productURL + "'><br>" + 
                products[i].name + "<br> </a><b>$" + products[i].price + "</b></td> </tr>");
            }
        }
	);
}

	function loadProduct()
	{

		var deferred = new jQuery.Deferred();

		var queryParams = parseQueryString();
		queryParams.each(
		function(key, value, pos)
		{
			$.getJSON("http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductById&id=" + $.base64('decode', value) + "&callback=?", 
		        function(result) {
		        	if (result.hasOwnProperty('error'))
		        	{
		        		$("#product-error").append("<p class='bold big error textCenter center'>El ID del producto no es valido.</p>");
		        		$("#primary_block").hide();
		        		$("#product-error").show();
		        		deferred.reject(error);
		        	}
		        	else
		        	{
		        		var product = result.product;
						$("#wrap").append("<a href='" + product.imageUrl[0] + "' class='productPretty'> <img alt='" + 
							product.name+ "' src='" + product.imageUrl[0] + "' /> </a>");
						for(var i = 0; i < product.imageUrl.length; i++)
						{
							$("#product-images").append("<li><a href='" + product.imageUrl[i] + "' class='productPretty' > <img alt='" + 
								product.name + "' src='" + product.imageUrl[i] + "' /> </a></li>");
						}
						$("#product-name").append(product.name);
						$("#our_price_display").append("$" + product.price);
						$("#product-rating").append("<div style='width:" + Math.floor(Math.random() * 101)
							 + "%' class='rating'></div>");
						$("#btnShoppingCart").click(
							function ()
							{

								var productCart = {
									id: product.id,
									name: product.name,
									price: product.price,
									image: product.imageUrl[0],
									quantity: $("#quantity_wanted").val()
								};

								var shoppingCart = $.session.get("shoppingCart");

								var cart = {};
								var exists = false;
								var j = 0;
								if(shoppingCart !== undefined)
								{
									var productsCart = JSON.parse(shoppingCart);
									for(var key in productsCart)
									{
										var temp = productsCart[key];
										if(temp["id"] == productCart["id"])
										{
											var cant = 0;
											cant += (parseInt(temp["quantity"]) + parseInt(productCart["quantity"]));
											temp["quantity"] = cant;
											exists = true;
										}
										cart[j] = temp;
										j++;
									}
								}

								if(!exists)
								{
									cart[j] = productCart;
								}

								$.session.set("shoppingCart", JSON.stringify(cart));

								window.location.href = "./shopping-cart.html";
							}
						);
						$("#product-error").hide();
		        		$("#primary_block").show();
		        		$(".productPretty").prettyPhoto();	
		        		deferred.resolve({categoryId:product.category.id, subCategoryId: product.subcategory.id});
		        	}
				}
		    );
		}
	);
	return deferred.promise();
	}

});