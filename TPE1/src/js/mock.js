$(function() {
	result = getJSON("http://hci.apiary-mock.com/hci/service3/Common.groovy=?method=GetAllStates&callback=?");
	alert(result);

	$.getJSON("http://hci.apiary-mock.com/hci/service3/Common.groovy=?method=GetAllStates&callback=?", function(result) {
		alert("asadsadsadasda");
	});
});