$(function() {
	$("#date").mask("99/99/9999");
	$("#email").blur(function(){
		$("#username").val($("#email").val());
	});
});