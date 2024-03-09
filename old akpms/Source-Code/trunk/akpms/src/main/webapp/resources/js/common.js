jQuery(function (){
    jQuery(".integerOnly").keydown(function(event) {
          // Allow: backspace, delete, tab , escape and enter
        if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 27 ||event.keyCode == 13||
             // Allow: Ctrl+A
            (event.keyCode == 65 && event.ctrlKey === true) ||
             // Allow: Ctrl+V
            (event.keyCode == 86 && event.ctrlKey === true) ||
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
});

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
            if ( event.shiftKey|| (event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 ) && (event.keyCode != 190) && (event.keyCode != 110) && (event.keyCode != 109) && (event.keyCode != 173)  && (event.keyCode != 189))
            {
       			event.preventDefault();
            }
        }

        if ($(this).val().indexOf('-') > -1) {
       		if (event.keyCode == 109 || event.keyCode == 173 || event.keyCode == 189)
       			event.preventDefault();
        }

        if ($(this).val().indexOf('.') > -1) {
            if (event.keyCode == 190 || event.keyCode == 110)
                event.preventDefault();
//           if($(this).val().substring($(this).val().indexOf('.')+1).length>1)
//        	   event.preventDefault();
        }
    });
});

jQuery(function (){
    jQuery(".numbersOnly").blur(function(event) {
    	if ($.trim($(this).val()).length == 0 || $.trim($(this).val())=='.' || $.trim($(this).val())=='-' || ($(this).val().indexOf('-') > 0 )) {
    		$(this).val('0');
    	}

    	if ($(this).val().indexOf('.') > -1) {
	    	if($(this).val().substring($(this).val().indexOf('.') + 1).length > 2 ){
	    		//$(this).val(parseFloat($(this).val()).toFixed(2));
	    		$(this).val($(this).val().substring(0,parseInt($(this).val().indexOf('.'))+3));
	    		//$(this).val('0.0');
	    		//alert("Value can be filled upto 2 decimal places.");
	    	}
    	}
    });
});

// to remove E in number only fields
jQuery(function (){
	$('.numbersOnly').each(function(){
		if ($.trim($(this).val()).length > 0){
    		$(this).val(parseFloat($(this).val(), 10));
		}
	});
});

jQuery(".numbersOnly").bind("contextmenu",function(e){
	return false;
});

jQuery(".integerOnly").bind("contextmenu",function(e){
	//return false;
});


function addFloats() {
	// Initialize output and "length" properties
	var length = 0;
	var output = 0;
	// Loop through all arguments supplied to this function (So: 1,4,6 in case
	// of add(1,4,6);)
	for ( var i = 0; i < arguments.length; i++) {
		// If the current argument's length as string is longer than the
		// previous one (or greater than 0 in case of the first argument))

		if ($.trim(arguments[i]) == "" || arguments[i].toString() == ""
				|| arguments[i].toString() == "."
				|| arguments[i].toString() == "-") {
			arguments[i] = 0;
		}

		if (arguments[i].toString().length > length) {
			// Set the current length to the argument's length (+1 is to account
			// for the decimal point taking 1 character.)
			length = arguments[i].toString().length + 1;
		}
		// Add the current character to the output with a precision specified by
		// the longest argument.
		output = parseFloat((output + parseFloat(arguments[i]))
				.toPrecision(length));
	}
	// Do whatever you with with the result, here. Usually, you'd 'return
	// output;'
	// alert(output);
	// console.log(output);
	return parseFloat(output).toFixed(2);
}


function addIntegers() {
	// Initialize output properties
	var output = 0;
	// Loop through all arguments supplied to this function (So: 1,4,6 in case of add(1,4,6);)
	for ( var i = 0; i < arguments.length; i++) {
		// If the current argument's length as string is longer than the previous one (or greater than 0 in case of the first argument))

		if (arguments[i].toString() == "" || arguments[i].toString() == "."
				|| arguments[i].toString() == "-") {
			arguments[i] = 0;
		}
		// Add the current character to the output with a precision specified by the longest argument.
		output = (parseInt(output) + parseInt(arguments[i]));
	}
	// Do whatever you with with the result, here. Usually, you'd 'return output;'
	//alert(output);
	//console.log(output);
	return output;
}

jQuery.fn.visible = function() {
    return this.css('visibility', 'visible');
};

jQuery.fn.invisible = function() {
    return this.css('visibility', 'hidden');
};

jQuery.fn.visibilityToggle = function() {
    return this.css('visibility', function(i, visibility) {
        return (visibility == 'visible') ? 'hidden' : 'visible';
    });
};
