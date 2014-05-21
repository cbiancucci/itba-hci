$(function() {
	$("#date").mask("99/99/9999");
	$("#email").blur(function(){
		$("#username").val($("#email").val());
	});
$("#btnRegister").click(function(){
	$("#register-form").submit();
});
$("#register-form").validate({
		rules: {
			firstname: "required",
			lastname: "required",
			username: {
				required: true,
				minlength: 2
			},
			password: {
				required: true,
				minlength: 5
			},
			confirm_password: {
				required: true,
				minlength: 5,
				equalTo: "#password"
			},
			email: {
				required: true,
				email: true
			}
		},
		messages: {
			firstname: "Please enter your firstname",
			lastname: "Please enter your lastname",
			username: {
				required: "Please enter a username",
				minlength: "Your username must consist of at least 2 characters"
			},
			password: {
				required: "Please provide a password",
				minlength: "Your password must be at least 5 characters long"
			},
			confirm_password: {
				required: "Please provide a password",
				minlength: "Your password must be at least 5 characters long",
				equalTo: "Please enter the same password as above"
			},
			email: "Please enter a valid email address"
		}
	});

/*	$("#btnRegister").click(function(){
		var firstname = $("#firstname").val();
		var lastname = $("#lastname").val();
		var email = $("#email").val();
		var username = $("#username").val();
		var password = $("#password").val();
		var confirmPassword = $("#confirm_password").val();
		var gen = $("#genero").val();
		var dni = $("#dni").val();
		var birth = $("#date").val();
		alert("Te queres registrar.");
	});*/
});