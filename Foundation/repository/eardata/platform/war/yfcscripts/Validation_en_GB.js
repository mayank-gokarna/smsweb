/*******************************************************************************
   IBM Confidential 
   OCO Source Materials 
   IBM Sterling Selling and Fullfillment Suite
   (c) Copyright IBM Corp. 2001, 2013 All Rights Reserved.
   The source code for this program is not published or otherwise divested of its trade secrets, 
   irrespective of what has been deposited with the U.S. Copyright Office. 
 *******************************************************************************/

	//------------------------------------------------------------------------------------
	//Please refer to localization guide to localize date and number validations
	//------------------------------------------------------------------------------------
	
	//------------------------------------------------------------------------------------
	// Number Validation.
	//------------------------------------------------------------------------------------
        yfcDecimalSeparator = ".";
        yfcGroupingSeparator = ",";
        yfcMinusSign = "-";

	function	yfcGetNumber(c) {
		var retVal =  c -'0';
		return Number(retVal);
		//alert("getNumber returning "+retVal +" for "+c);
	}
	function	yfcIsNumber(c) {
		//var diff = c - '0';
		var diff = parseInt(c);
		//alert("isNumber returning "+diff +" for "+c);
		if ( diff > 9 || diff < 0 || isNaN(diff) )
			return false;
		else
			return true;
	}


	//------------------------------------------------------------------------------------
	// Date Validation for en_GB locale
	//------------------------------------------------------------------------------------
	yfcDateFormat = "dd/MM/yyyy";
	yfcDisplayDateFormat = "dd/MM/yyyy";
	yfcTimeFormat = "HH:mm:ss";
	yfcHourMinuteTimeFormat = "HH:mm";
	yfcDateTimeFormat = "dd/MM/yyyy HH:mm:ss";
			

	//------------------------------------------------------------------------------------
	// Error Routines
	//------------------------------------------------------------------------------------

	/**
		YFC1001	- Invalid Date ( could not parse the string into a date).
		YFC1002	- Invalid Field ( parsed it but some values are not valid).
		YFC2001	- Invalid Number ( could not parse the string into a number).
		YFC2002 - Does not satisfy the conditions ( Integer between max & min).
		YFC2003 - Does not satisfy the conditions ( Decimal between max & min).
		YFC2004 - Data exceeds the limit (exceeds textarea maxlength).
	**/
	function	yfcGetErrorDescription(errorCode,args) {
		// ---------------------------------------------------------------------------
		// By Default the first argument is the 
		// value in error.
		// ---------------------------------------------------------------------------

		//Start Non-Translatable
		var YFC1001 = "YFC1001";
		var YFC10011 = "YFC10011";
		var YFC10012 = "YFC10012";
		var YFC1002 = "YFC1002";
		var YFC2001 = "YFC2001";
		var YFC2002 = "YFC2002";
		var YFC2003 = "YFC2003";
		var YFC2004 = "YFC2004";
		var YFC2005 = "YFC2005";
		var YFC2007 = "YFC2007";
		//End Non-Translatable

		if ( YFC1001 == errorCode ) {
			var invalidDateStr = YFCMSG201 ;
			return callFormat(invalidDateStr, args[0], yfcDisplayDateFormat);
		} else if ( YFC10011 == errorCode ) {
			var invalidDateTimeStr = YFCMSG202 ;
			return callFormat(invalidDateTimeStr, args[0], yfcDateTimeFormat);
		} else if ( YFC10012 == errorCode ) {
			var invalidTimeStr = YFCMSG203 ;
			return callFormat(invalidTimeStr, args[0], yfcTimeFormat);
		} else if ( YFC1002 == errorCode ) {
			var invalidFieldStr = YFCMSG204 ;
			return callFormat(invalidFieldStr, args[1], args[0]);
		} else if ( YFC2001 == errorCode ) {
			var invalidNumberStr = YFCMSG205 ;
			return callFormat(invalidNumberStr, args[0]);
		} else if ( YFC2002 == errorCode ) {
			// The second argument is the max value and the third is the min value
			var numberRangeStr = YFCMSG206 ;
			return callFormat(numberRangeStr, args[1], args[2]);
		} else if ( YFC2003 == errorCode ) {
			// The second argument is the max value and the third is the min value
			var decimalRangeStr = YFCMSG207 ;
			return callFormat(decimalRangeStr, args[1], args[2]);
		} else if ( YFC2004 == errorCode ) {
			var limitExceededStr = YFCMSG208 ;
			return callFormat(limitExceededStr, args[0]);
		} else if ( YFC2005 == errorCode ) {
			var invalidMailStr = YFCMSG209 ;
			return callFormat(invalidMailStr, args[0]);
		} else if( YFC2007 == errorCode ) {
			var enterLargerNumberStr = YFCMSG210 ;
			return callFormat(enterLargerNumberStr, args[0]);
		}
	}
	
	function callFormat(arg1, arg2, arg3) {
		if(parent && parent.format) {
			return parent.format(arg1, arg2, arg3);
		} else {
			return format(arg1, arg2, arg3);
		}
	}

	/*
	  TODO: Explore a more elegant fix. This will do for now.
	  The issue is in case of non-mb files, where we are using showModalDialog(). Here, we are unable to access the format() function defined in yfc.js for date and time widgets.
	  Defining it here as well. NOTE: Might not be needed at all in case of non-mb.
	*/
	function format() {
		var formatStr = arguments[0];
		var args = Array.prototype.slice.call(arguments, 1);
        return formatStr.replace(/\{(\d+)\}/g, function(m, i){
            return args[i];
        });
	}