$(function() {
    var gender = GetURLParameter('gender');
    var age = GetURLParameter('age');
    var category =  GetURLParameter('category');
    var subcategory =  GetURLParameter('subcategory');
    var page = GetURLParameter('page');

    if(typeof page == "undefined") {
        page = 1;
    } 

    var url= "category.html?";
    var section = "Productos";

    if(typeof gender !== "undefined") {
        section = gender;
        url += "gender=" + gender;
        $(".breadcrumbs").append(' / <a href="/'+url+'">'+gender+'</a> ');
    }    
    if(typeof age !== "undefined") {
        section = age;
        url += "&age=" + age;
        $(".breadcrumbs").append(' / <a href="/'+url+'">'+age+'</a> ');
    }   
    if(typeof category !== "undefined") {
        section = category;
        url += "&category=" + category;
        $(".breadcrumbs").append(' / <a href="/'+url+'">'+category.split('-')[1]+'</a> ');
    }   
    if(typeof subcategory !== "undefined") {
        section = subcategory;
        url += "&subcategory=" + subcategory ;
        $(".breadcrumbs").append(' / <a href="/'+url+'">'+subcategory.split('-')[1]+'</a> ');
    }    


    $(".category-title").append(section.split('-')[1]);

    if(typeof subcategory !== "undefined") {
        url = "http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductsBySubcategoryId&id="+ subcategory.split('-')[0] +"&page_size=10&page="+ page +"&callback=?";

    }
    else if(typeof category !== "undefined") {
        url = "http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductsByCategoryId&id="+ category.split('-')[0] +"&page_size=10&page="+ page +"&callback=?";

    }
    else{
        return;
    }
alert(url);

    $.getJSON(url, function(result) {
        $.each( result.products , function() {
            $("#products").append('<div class="itemshow"><a href="product.html" class="thumbnail"><img src="'+this.imageUrl[0]+'" alt="Image"></a><div class="productinfo">' + this.name + '<br><span>$' + this.price.toFixed(2) + '</span></div></div>');
        }); 
        $.each( result.filters , function() {
            $("#filters").append('<h4>' + this.name+'</h4><select class="form-control smallDropdown" id="' + this.name+'"></select>');
            var name= "#" + this.name;
            $.each( this.values , function() {
                $(name).append('<option value="' + this+'">' + this+'</option>');
            });
        });
    });
});