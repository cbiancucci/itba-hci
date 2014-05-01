 	    $(function() {
        $.getJSON("http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetAllProducts&sort_order=desc&page_size=10&callback=?", function(result) {
            var products = result.products;
			var size = products.length;
			for(var i=0; i<5;i++){
				//$("#itemsact").append('<div class="span3"><a href="product.html" class="thumbnail"><img src="'+products[i].imageUrl[0]+'" alt="Image"></a><div class="productinfo">' + products[i].name + '<br><span>$' + products[i].price.toFixed(2) + '</span></div></div>');


				/*$("#itemsact").append('<div class="product_holder"><div class="product_holder_inside"><div class="image"><a href="./product.html"><img src="'+product[i].imageUrl[0]+'" alt="' + products[i].name +'" /></a>');

				$("#itemsact").append('<div class="pr_info"><div class="name"><a href="./product.html">' + products[i].name + '</a></div>');
				$("#itemsact").append('<div class="price">' + products[i].price.toFixed(2) + '</div>');
				$("#itemsact").append('<div class="cart"><a class="button" onclick="addToCart("47");"><span>Comprar</span></a></div></div></div>');*/
				

			}
			
			for(var i=0; i<5;i++){
				//$("#secitems").append('<div class="span3"><a href="product.html" class="thumbnail"><img src="'+products[i].imageUrl[0]+'" alt="Image"></a><div class="productinfo">' + products[i].name + '<br><span>$' + products[i].price.toFixed(2) + '</span></div></div>');

				$("#ofertas").append('<div class="product_holder"><div class="product_holder_inside"><div class="image"><a href="./product.html" class="thumbnail"><img src="'+ products[i].imageUrl[0] +'" alt="' + products[i].name +'" /></a><div class="pr_info"><div class="name"><a href="./product.html">' + products[i].name +  '</a></div><div class="price">$ ' + products[i].price + '</div><div class="cart"><a class="button"><span>Comprar</span></a></div> </div></div>');
/*
				$("#ofertas").append('<div class="product_holder"><div class="product_holder_inside"><div class="image"><a href="./product.html"><img src="'+ products[i].imageUrl[0] +'" alt="' + products[i].name +'" /></a></div>');
					
				$("#ofertas").append('<div class="pr_info"><div class="name"><a href="./product.html">' + products[i].name +  '</a></div>');

				$("#ofertas").append('<div class="price">$129.00</div><div class="cart"><a class="button" onclick=""><span>Comprar</span></a></div></div></div>');
*/
			}
           
        });
    });