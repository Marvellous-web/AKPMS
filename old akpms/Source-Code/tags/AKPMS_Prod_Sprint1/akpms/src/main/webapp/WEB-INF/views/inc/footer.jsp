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
<!-- Shadow Box End -->
<script type="text/javascript">
<!--
$('form').submit(function(){
    $(this).find('input[type=text], textarea').each(function(){
    	$(this).val($.trim($(this).val())); // trim value

        if(($(this).val().length == 0 || $(this).val() == ".") && $(this).hasClass('numbersOnly')){
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
	$.uniform.update();
}
function disabledButtons(){
	$('input:submit').attr("disabled", "disabled");
	$('input:button').attr("disabled", "disabled");
}
$(document).ready(function() {
  	$("textarea").limita({
		limit: 5000
	});
});
//-->
</script>
