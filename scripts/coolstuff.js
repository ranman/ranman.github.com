var i = 0;
function changebg() {
	var bg =  ["img/spacetile1.jpg", 
               "img/spacetile2.jpg", 
               "img/spacetile3.jpg",
               "img/spacetile4.jpg",
               "img/spacetile5.jpg",
               "img/spacetile6.jpg",
               "img/spacetile7.jpg",
               "img/spacetile8.jpg",
               "img/spacetile9.jpg",
               "img/spacetile10.jpg"];
	$('body').css("background", "url("+bg[i]+")")
	i++;
	if(i > 9) i = 0;
}

$(document).ready(function(){
    $('#logo').live('click', changebg());
});
