jQuery(function (){
    jQuery(".integerOnly").keydown(function(event) {
          // Allow: backspace, delete, tab , escape and enter
        if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 27 ||event.keyCode == 13||
             // Allow: Ctrl+A
            (event.keyCode == 65 && event.ctrlKey === true) ||
             // Allow: home, end, left, right
            (event.keyCode >= 35 && event.keyCode <= 39)) {
                 // let it happen, don't do anything
                 return;
        }else {
            // Ensure that it is a number and stop the keypress
            if ( event.shiftKey|| (event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 ))
            {
       			event.preventDefault();
            }
        }
    });
})

jQuery(function (){
    jQuery(".numbersOnly").keydown(function(event) {
          // Allow: backspace, delete, tab , escape and enter
        if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 27 ||event.keyCode == 13||
             // Allow: Ctrl+A
            (event.keyCode == 65 && event.ctrlKey === true) ||
             // Allow: home, end, left, right
            (event.keyCode >= 35 && event.keyCode <= 39)) {
                 // let it happen, don't do anything
                 return;
        }else {
            // Ensure that it is a number and stop the keypress
            if ( event.shiftKey|| (event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 ) && (event.keyCode != 190) && (event.keyCode != 110))
            {
       			event.preventDefault();
            }
        }

        if ($(this).val().indexOf('.') > -1) {
            if (event.keyCode == 190 || event.keyCode == 110)
                event.preventDefault();
//           if($(this).val().substring($(this).val().indexOf('.')+1).length>1)
//        	   event.preventDefault();
        }
    });
})

jQuery(function (){
    jQuery(".numbersOnly").blur(function(event) {
    	if ($.trim($(this).val()).length == 0 || $.trim($(this).val())=='.') {
    		$(this).val('0');
    	}
    	if ($(this).val().indexOf('.') > -1) {
	    	if($(this).val().substring($(this).val().indexOf('.') + 1).length > 2 ){
	    		$(this).val(parseFloat($(this).val()).toFixed(2));
	    		//$(this).val('0.0');
	    		//alert("Value can be filled upto 2 decimal places.");
	    	}
    	}
    });
})

// to remove E in number only fields
jQuery(function (){
	$('.numbersOnly').each(function(){
		if ($.trim($(this).val()).length > 0){
    		$(this).val(parseFloat($(this).val(), 10));
		}
	});
})

jQuery(".numbersOnly").bind("contextmenu",function(e){
	return false;
});

jQuery(".integerOnly").bind("contextmenu",function(e){
	return false;
});
