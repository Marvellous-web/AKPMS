<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Shadow Box Start -->
<div class="shadow-container">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
        	<td width="10%"><span class="left-shadow"></span></td>
            <td width="80%" class="mid-shadow">&nbsp;</td>
            <td width="10%"><span class="right-shadow"></span></td>
		</tr>
	</table>
</div>
<div class="popup2" id="akpms_popup" style="display: none;">
	<span class="button bClose"><span>X</span></span>
	<div class="popup-container" id="popupContainer"><img src="<c:url value="/static/resources/img/ajax-loader.gif"/>" alt="Loading..." /></div>
</div>
<!-- Shadow Box End -->
<script type="text/javascript">
<!--
$('form').submit(function(){
    $(this).find('input[type=text], textarea').each(function(){
    	$(this).val($.trim($(this).val())); // trim value

        if(($(this).val().length == 0 || $(this).val() == "."   || $.trim($(this).val())=='-' || ($(this).val().indexOf('-') > 0 ) ) && $(this).hasClass('numbersOnly')){
       		$(this).val('0');
        }
    });
});
function clearErrors(){
	$(".invalid").text("");
}
function resetForm(form){
	form.reset();
	// resolve E problem on reset
	$('.numbersOnly').each(function(){
		if ($.trim($(this).val()).length > 0){
    		$(this).val(parseFloat($(this).val(), 10));
		}
	});
	$(form).find('select').each(function() {
	    chkDropdown = $('#'+this.id).val($('#'+this.id).val());
	    $.uniform.update(chkDropdown);
	});
	
	$(".filename").text("No file selected");
	$.uniform.update();
}
function disabledButtons(){
	$('input:submit').attr("disabled", "disabled");
	$('input:button').attr("disabled", "disabled");
	$('input:reset').attr("disabled", "disabled");
}
$(document).ready(function() {
  	$("textarea").limita({
		limit: 5000
	});
});

if((readCookie("lt")=="true")||readCookie("lt")==null){
	window.location.href=contextPath+"/login";
}

function readCookie(name) {
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');
	for(var i=0;i < ca.length;i++) {
		var c = ca[i];
		while (c.charAt(0)==' ') c = c.substring(1,c.length);
		if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
	}
	return null;
}

function akpmsPopupHTML(url, x, y) {
	x = typeof x !== 'undefined' ? x : 'auto';
	y = typeof y !== 'undefined' ? y : 'auto';
	
	$('#akpms_popup').bPopup({
        content:'ajax', //'ajax', 'iframe' or 'image'
        contentContainer:'#popupContainer',
        loadUrl:url, //Uses jQuery.load()
        follow: [true, false], //x, y
        position: [x, y], //x, y
        amsl:50
    },function() {
		$('html, body').animate({scrollTop:0}, 'slow');
    });
}

function akpmsPopupWindow(url, name, height, width) {

	if (window.screen) {
		if(height == '100%'){
			height = window.screen.availHeight - 50;
		}

		if(width == '100%'){
			width = window.screen.availWidth - 50;
		}
	}

	if(name != undefined && name != ""){
		name = name.replace(" ", "_");
	}
	
	height = typeof height !== 'undefined' ? height : 600;
	width = typeof width !== 'undefined' ? width : 800;
	name = typeof name !== 'undefined' ? name : 'akpmsWindow';
	
	var opt = "height="+height+",width="+width+",left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,directories=no,status=yes";
	
	popupWindow = window.open(url,name, opt);
	return false;
}

function reloadFlexigrid(){
	$('#flex1').flexOptions().flexReload();
}
//-->
</script>
