var i = 0;
function changeLogo() {
	var logo =["img/profile1.jpg", "img/profile2.jpg", "img/logo_sm.png"];
	$('#logo').attr('src', logo[i]);
	i++;
	if(i > 2) i = 0;
}

$(document).ready(function(){
	$('#logo').live('click', changeLogo);
});