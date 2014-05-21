$(function() {
	$("#btnLogin").click(function(){
		if($("#login-form-header").valid()){
			$.getJSON("http://eiffel.itba.edu.ar/hci/service3/Account.groovy?method=SignIn&username=" + $("#username").val() + "&password=" + $("#password").val() + "&callback=?", function(result) {
				if(result.error === undefined)
				{
					$("#page").append("<div id='no_script_msg'>" + 
			    		"<p> El registro se ha completado satisfactoriamente. <br />Será redireccionado al inicio en 5 segundos. <br />Cuando lo desee, podrá iniciar sesión.</p> </div>");
					
					window.setTimeout(function(){
			        	window.location.href = "./index.html";
			    	}, 5000);
				}
				else
				{
					alert("ERROR");
				}
			});
		}
	});

	$("#login-form-header").validate({
		rules: {username: {
				required: true,
				minlength: 6,
				maxlength: 15
			},
			password: {
				required: true,
				minlength: 8,
				maxlength: 15
			}
		},
		messages: {
			username: {
				required: "Requerido",
				minlength: "El nombre de usuario debe tener al menos 6 caracteres.",
				maxlength: "El nombre de usuario debe tener menos de 15 caracteres."
			},
			password: {
				required: "Requerido",
				minlength: "La clave debe tener al menos 8 caracteres.",
				maxlength: "La clave debe tener menos de 15 caracteres."
			}
		}
	});
});