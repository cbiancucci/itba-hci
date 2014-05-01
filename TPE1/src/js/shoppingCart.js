$(function() {

$.when(
	loadMenu(),
	loadShoppingCart())
	.done(
		function(result1, result2)
    	{
    	}
    );

    function loadShoppingCart()
    {
		var shoppingCart = $.session.get('shoppingCart');
		var total = 0;
		if(shoppingCart !== undefined)
		{
			var productsCart = JSON.parse(shoppingCart);
			for(var key in productsCart)
			{
				var product = productsCart[key];
				var currentTotal = (parseInt(product["quantity"]) * parseInt(product["price"])).toFixed(2);
				var productURL = "./product.html?productId=" + $.base64('encode', product["id"]);
				total = parseInt(currentTotal) + parseInt(total);
				$('#shoppingCart-details').append("<tr class='first last odd' id='tr" + product["id"] + "'>");
                    $("#tr" + product["id"]).append("<td><a class='product-image' href='" + product["image"] + "'><img src='" + product["image"] + "' class='shopping-cart-image' ></a></td>");
                    $("#tr" + product["id"]).append("<td><h2 class='product-name'> <a href='" + productURL + "'>" + product["name"] + "</a> </h2></td>");                      
                    $("#tr" + product["id"]).append("<td class='a-center'><span class='cart-price'> <span class='price'>$" + product["price"] + "</span> </span></td>");
                    $("#tr" + product["id"]).append("<td class='a-center' id='td" + product["id"] + "'>");
                    	$("#td" + product["id"]).append("<div class='input-qty-box' id='qty-box-" + product["id"] + "'>");
                        $("#qty-box-" + product["id"]).append("<div class='input' id='qty-input-" + product["id"] + "'>");
                          $("#qty-input-" + product["id"]).append("<ul class='range' id='qty-range-" + product["id"] + "''>");
                            $("#qty-range-" + product["id"]).append("<li class='item minus'><a style=' cursor:pointer'>-</a></li>");
                            $("#qty-range-" + product["id"]).append("<li><input type='text' name='qty' id='quantity_wanted' class='input-text qty text' value='" + parseInt(product["quantity"]) + "' size='2' maxlength='3'></li>");
                            $("#qty-range-" + product["id"]).append("<li class='item plus'><a style=' cursor:pointer'>+</a></li>");
                          $("#qty-input-" + product["id"]).append("</ul>");
                        $("#qty-box-" + product["id"]).append("</div>");
                      $("#td" + product["id"]).append("</div></td>");
                    $("#tr" + product["id"]).append("<td class='a-center'><span class='cart-price'> <span class='price'>$" + currentTotal + "</span> </span></td>");
                    $("#tr" + product["id"]).append("<td class='a-center last'><a href='#' title='Remove item' class='btn-remove btn-remove2'><i class='icon-trash'></i></a></td>");
                $('#shoppingCart-details').append("</tr>");
			}
		}   
		$('#totalPrice').append(parseInt(total).toFixed(2));
		$('#subtotalPrice').append(parseInt(total).toFixed(2));	
    }
	
});