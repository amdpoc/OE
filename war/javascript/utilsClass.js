jQuery.fn.fullHeight = function()
{
    var windowH = $(window).height();
    var newHeight = windowH > 485 ? windowH - 85 : 400;
    var navBarH = $('.navbar').height() || 0;
    //console.log("**** fullHeight :::: windowH = ", windowH, " :: navBarH = ", navBarH, " :: newHeight = ", newHeight);
    $(this).css('min-height', newHeight + "px");
    $(this).css('max-height', newHeight + "px");
    $(this).height(newHeight + "px");
    $(this).children('.panelBody').css('max-height',(newHeight - 100) + 'px');
    $(this).children('.panelBody').css('min-height',(newHeight - 100) + 'px');
}

UtilsClass = function()
{
    this.init();
};

UtilsClass.prototype.init = function()
{
	var that = this;
	this.elMask = $("#mask");
	this.elMaskLoader = $("#loader");
	this.iScrollOptions = {checkDOMChanges:true,hScrollbar:false,scrollbarColor:'rgba(0,0,0,0.5)',desktopCompatibility:true,shrinkScrollbar:false,bounce:true};
    this.oModalDialog = modalDialog;
    $(window).resize(that.setFullHeight);
};

UtilsClass.prototype.NOTE =  
{
	creationDate:"Creation Date"
	,salesRep:"User"
	,noteType: "Note Type"	
	,callResults:"Call Results"
	,noteText:"Notes"
	//,office:"Office"
};

UtilsClass.prototype.MESSAGES = 
{
	noKGenData:"No kGen data found.<br>Please use \"New Envelope\" to create a new DocuSign envelope",
	custNotAssignedToRep:"Please confirm the Customer ID.<br>The products are not assigned to you.<br><br>Please use \"New Envelope\" to create<br>a new DocuSign envelope",
	changesLost:"If you made any pending changes - they will be lost.",
	notInPersonDocuSignConfirm:"Your document has been successfully sent to DocuSign.",
	inPersonDocuSignSuccess:"Billing Agreement was successfully signed for ",
	inPersonDocuSignDecline:"DocuSign was declined for ",
	generateContractError:"A system problem has occurred that will require you\nto contact the Business Support Center (BSC) for assistance.\nPlease reference this error: GENERATE_BILLING_AGREEMENT_ERROR.",
	convertPDFError:"A system problem has occurred that will require you\nto contact the Business Support Center (BSC) for assistance.\nPlease reference this error: CONVERT_PDF_ERROR.",
	generatingContract:"... generating Billing Agreement",
	requestingImage:"... requesting Billing Agreement image",
	loadingImage:"... loading Billing Agreement image",
	generatingROP:"... generating Record of Payment",
	requestingROPImage:"... requesting Record of Payment image",
	loadingROPImage:"... loading Record of Payment image"
};

UtilsClass.prototype.ADOCS =
{
    iMaxAttempts:40,
    iInterval:3000
}

UtilsClass.prototype.setFullHeight = function()
{
    $('.full-height').fullHeight();
};

UtilsClass.prototype.renderUserName = function(name)
{
    if (name && name != "")
    {
        $('#user_name').text(name);
    }
};

UtilsClass.prototype.showConfirmCancelAction = function(sURL)
{
    var that = this;
    this.oModalDialog.renderTitle("Leaving eSign Tab",{});
    var sMsg = this.MESSAGES.changesLost + "<br/><br/>Do you want to proceed?";
    var aBodyHTML = [];
    aBodyHTML.push(	'<div id="modal_body">');
    aBodyHTML.push(		'<div id="warn_text">' + sMsg + '</div>');
    aBodyHTML.push(		'<div id="footer">');
    aBodyHTML.push(			'<div class="ui-state-active ui-corner-all4 fleft btn-icon no_lm" id="cancelESign_ok">');
    aBodyHTML.push(				'<span class="ui-icon ui-icon-check"></span> OK</div>');
    aBodyHTML.push(			'<div class="ui-state-highlight ui-corner-all4 fleft btn-icon" id="cancelESign_cancel">');
    aBodyHTML.push(				'<span class="ui-icon ui-icon-cancel"></span>Cancel</div>');
    aBodyHTML.push(		'</div>');

    aBodyHTML.push(	'</div>');
    this.oModalDialog.renderBody(aBodyHTML);

    var oDialogStyle = {top:"260px",left:"310px",width:"400px",height:"auto",zIndex:5};
    this.oModalDialog.showDialog(oDialogStyle);

    $('#cancelESign_ok')
        .click(function()
        {
            that.elLoader.show();
            window.location = sURL;
        }
    );

    $('#cancelESign_cancel')
        .click(function()
        {
            that.oModalDialog.hideDialog();
        }
    );
};

UtilsClass.prototype.showLoadingMask = function(el)
{
    var oStyle =
    {
        width:"100%",
        height:"100%",
        top:0,
        left:0
    };

    if (el)
    {
        oStyle =
            {
                width:el.width(),
                height:el.height(),
                top:el.offset().top,
                left:el.offset().left
            }
    }
    //this.elMaskLoader.show().animate(oStyle,100);
    this.elMaskLoader.show();
};

UtilsClass.prototype.hideLoadingMask = function()
{
    var oStyle =
        {
            width:"100%",
            height:"100%",
            top:0,
            left:0
        }
    this.elMaskLoader.css(oStyle).hide();
};

UtilsClass.prototype.showMask = function()
{
	console.log("util :: show mask");
	this.elMask.show();
};

UtilsClass.prototype.hideMask = function()
{
	console.log("util :: hide mask");
	this.elMask.hide();
};

UtilsClass.prototype.createSelect = function(el, optionArray) {
    for (var i = 0, iL = optionArray.length; i < iL; i++)
    {
        $("<option value='" + optionArray[i] + "'>" + optionArray[i] + "</option>").appendTo(el);
    }
};
UtilsClass.prototype.createSelectWithValues = function(el, optionArray, valueArray) {
    for (var i = 0, iL = optionArray.length; i < iL; i++)
    {
        $("<option value='" + valueArray[i] + "'>" + optionArray[i] + "</option>").appendTo(el);
    }
};

UtilsClass.prototype.isSecondDateBigger = function(sDate1,sDate2) 
{
	//var jsDate1 = this.getJSFromFormattedDate(sDate1);
	var jsDate1 = new Date(sDate1);
	var jsDate2 = new Date(sDate2);
	return jsDate2 >= jsDate1;
};

UtilsClass.prototype.compareTimes = function(start, end) {
    var status = true;
    var hourS = parseInt(start);
    var hourE = parseInt(end);
    if (hourS > hourE) {
        status = false;
    }
    if (hourS == hourE) {
        var minsS = parseInt(start.substring(start.indexOf(':') + 1, 20));
        var minsE = parseInt(end.substring(end.indexOf(':') + 1, 20));
        if (minsS > minsE) {
            status = false;
        }
    }
    return status;
};
UtilsClass.prototype.fixTime24 = function(value) {
    if (value && value.length) {
        var val = value;
        //alert("val -> '" + val + "' \n val.substr(0,2) = " + val.substr(0,2));
        var hour = parseInt(val.substr(0,2),10);
        var mins = parseInt(val.substring(val.indexOf(':') + 1, 20));
        if (isNaN(hour)) hour = 0;
        if (isNaN(mins)) mins = 0;
        if (val.indexOf('pm') > -1 && hour != 12) hour += 12;
        hour %= 24;
        mins %= 60;
        if (mins < 10) mins = '0' + mins;
        value = hour + ':' + mins;
    }
    return value;
};
UtilsClass.prototype.requireValue = function(value) {
    value = this.removeWhiteSpace(value);
    return value.length;
};
UtilsClass.prototype.requireLength = function(name, value, min, max) {
    var len = value.length;
    if (min > -1 && len < min) {
        return 'The ' + name + ' field must be at least ' + min + ' characters long - it is currently ' + len + ' characters long.';   //error.textArea.atleast
    }
    if (max > -1 && len > max) {
        return 'The "' + name + '" field must be no more than ' + max + ' characters long - it is currently ' + len + ' characters long.';  //error.textArea.nomore
    }
    return false;
};
UtilsClass.prototype.replaceSpecialAndBadCharacters = function(inputText) {
    inputText = inputText.replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/&/g, "&amp;").replace(/"/g, "&quot;");
    inputText = inputText.replace(/'/g, "&#39;");
    inputText = inputText.replace(/[\"\'][\s]*javascript:(.*)[\"\']/gi, "\"\"");
    inputText = inputText.replace(/script(.*)/gi, "");
    inputText = inputText.replace(/eval\((.*)\)/gi, "");
    return inputText;
};

UtilsClass.prototype.decodeHTMLEntities = function(inputText) 
{
    inputText = inputText.replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace(/&amp;/g, "&").replace(/&quot;/g, "\"").replace(/&apos;/g, "'");
    return inputText;
};

UtilsClass.prototype.charConvert = function(s)
{
    var chars = ["©","Û","®","ž","Ü","Ÿ","Ý","$","Þ","%","¡","ß","¢","à","£","á","À","¤","â","Á","¥","ã","Â","¦","ä","Ã","§","å","Ä","¨","æ","Å","©","ç","Æ","ª","è","Ç","«","é","È","¬","ê","É","­","ë","Ê","®","ì","Ë","¯","í","Ì","°","î","Í","±","ï","Î","²","ð","Ï","³","ñ","Ð","´","ò","Ñ","µ","ó","Õ","¶","ô","Ö","·","õ","Ø","¸","ö","Ù","¹","÷","Ú","º","ø","Û","»","ù","Ü","@","¼","ú","Ý","½","û","Þ","€","¾","ü","ß","¿","ý","à","‚","À","þ","á","ƒ","Á","ÿ","å","„","Â","æ","…","Ã","ç","†","Ä","è","‡","Å","é","ˆ","Æ","ê","‰","Ç","ë","Š","È","ì","‹","É","í","Œ","Ê","î","Ë","ï","Ž","Ì","ð","Í","ñ","Î","ò","‘","Ï","ó","’","Ð","ô","“","Ñ","õ","”","Ò","ö","•","Ó","ø","–","Ô","ù","—","Õ","ú","˜","Ö","û","™","×","ý","š","Ø","þ","›","Ù","ÿ","œ","Ú"];
	var codes = ["&copy;","&#219;","&reg;","&#158;","&#220;","&#159;","&#221;","&#36;","&#222;","&#37;","&#161;","&#223;","&#162;","&#224;","&#163;","&#225;","&Agrave;","&#164;","&#226;","&Aacute;","&#165;","&#227;","&Acirc;","&#166;","&#228;","&Atilde;","&#167;","&#229;","&Auml;","&#168;","&#230;","&Aring;","&#169;","&#231;","&AElig;","&#170;","&#232;","&Ccedil;","&#171;","&#233;","&Egrave;","&#172;","&#234;","&Eacute;","&#173;","&#235;","&Ecirc;","&#174;","&#236;","&Euml;","&#175;","&#237;","&Igrave;","&#176;","&#238;","&Iacute;","&#177;","&#239;","&Icirc;","&#178;","&#240;","&Iuml;","&#179;","&#241;","&ETH;","&#180;","&#242;","&Ntilde;","&#181;","&#243;","&Otilde;","&#182;","&#244;","&Ouml;","&#183;","&#245;","&Oslash;","&#184;","&#246;","&Ugrave;","&#185;","&#247;","&Uacute;","&#186;","&#248;","&Ucirc;","&#187;","&#249;","&Uuml;","&#64;","&#188;","&#250;","&Yacute;","&#189;","&#251;","&THORN;","&#128;","&#190;","&#252","&szlig;","&#191;","&#253;","&agrave;","&#130;","&#192;","&#254;","&aacute;","&#131;","&#193;","&#255;","&aring;","&#132;","&#194;","&aelig;","&#133;","&#195;","&ccedil;","&#134;","&#196;","&egrave;","&#135;","&#197;","&eacute;","&#136;","&#198;","&ecirc;","&#137;","&#199;","&euml;","&#138;","&#200;","&igrave;","&#139;","&#201;","&iacute;","&#140;","&#202;","&icirc;","&#203;","&iuml;","&#142;","&#204;","&eth;","&#205;","&ntilde;","&#206;","&ograve;","&#145;","&#207;","&oacute;","&#146;","&#208;","&ocirc;","&#147;","&#209;","&otilde;","&#148;","&#210;","&ouml;","&#149;","&#211;","&oslash;","&#150;","&#212;","&ugrave;","&#151;","&#213;","&uacute;","&#152;","&#214;","&ucirc;","&#153;","&#215;","&yacute;","&#154;","&#216;","&thorn;","&#155;","&#217;","&yuml;","&#156;","&#218;"];
	for (x = 0; x < chars.length; x++)
	{
	   for (i = 0; i < s.length; i++)
	   {
		   s[i] = s[i].replace(chars[x], codes[x]);
	   }
	}
	return s;
};

UtilsClass.prototype.replaceNonStandardChars = function(s)
{
	var aStr = s.split('');
    var iL = aStr.length;
    var aReturn = [];

    for (var i = 0; i < iL; i++) 
    {
    	var iC = aStr[i].charCodeAt();
    	if (iC > 127) 
    	{
    		aReturn.push('&#' + iC + ';');
    	} 
    	else 
    	{
    		aReturn.push(aStr[i]);
    	}
   }
   return aReturn.join('');
};

UtilsClass.prototype.replaceNonStandardCharsToHex = function(s)
{
	var aStr = s.split('');
    var iL = aStr.length;
    var aReturn = [];

    for (var i = 0; i < iL; i++) 
    {
    	var iASCII = aStr[i].charCodeAt();
    	if (iASCII > 127) 
    	{
    		var sHex = iASCII.toString(16).toUpperCase();
    		while (sHex.length < 4)
    		{
    			sHex = '0' + sHex;
    		}
    		aReturn.push("\\u" + sHex);
    	} 
    	else 
    	{
    		aReturn.push(aStr[i]);
    	}
   }
   console.log("aReturn.join('') = " + aReturn.join(""));
   return aReturn.join('');
};

UtilsClass.prototype.setError = function (errorMsg) {
    alert(errorMsg);
};

UtilsClass.prototype.setDWR = function()
{
	var that = this;
	dwr.engine.setErrorHandler(function(err)
		{
			that.dwrErrorHandler(err);
		}
	);
	dwr.engine.setTextHtmlHandler(function()
	    {
            window.alert("Your session has expired, please login again.");
            window.location.href = 'logout.htm';
        }
    );
};

UtilsClass.prototype.dwrErrorHandler = function(errorMsg) 
{
    var sError;
	if (errorMsg)
    {
		sError = "Error: " + errorMsg;
    }
    else
    {
        sError ="A system problem has occurred that will require you to contact the Business Support Center (BSC) for assistance.  Please reference this error: DWR SERVICE";//error.generic.dwr    
    }
    alert(sError);
    $("#loader").hide();
};

UtilsClass.prototype.trim = function (value) {
    return value.replace(/^\s*/, "").replace(/\s*$/, "");
};
UtilsClass.prototype.removeWhiteSpace = function(value) {
    return value.replace(/^\s*|\s*$/g, '');
};
UtilsClass.prototype.formatMoney = function(money) 
{
	var mTmp = new Number(money);
    return "$" + mTmp.toFixed(2);
};

UtilsClass.prototype.formatMoneyEditable = function(money) {
    var mTmp = new Number(money);
    return mTmp.toFixed(2);
};
UtilsClass.prototype.formatDate = function(date){
    var formatedDate;
    var curr_month = date.getMonth();
    curr_month++;
    if(curr_month < 10)
        formatedDate = "0" + curr_month + "/";
    else
        formatedDate = curr_month + "/";
    var curr_date = date.getDate();
    if(curr_date < 10)
        formatedDate = formatedDate + "0" + curr_date + "/" + date.getFullYear();
    else
        formatedDate = formatedDate + curr_date + "/" + date.getFullYear();
    return formatedDate;
};

UtilsClass.prototype.getFormattedTime = function(oJSDate)
{
	var sH = oJSDate.getHours();
	var sM = oJSDate.getMinutes();
	if (sM < 10)
	{
		sM = "0" + sM;
	}
	return sH + ":" + sM;
};

UtilsClass.prototype.getJSFromFormattedDate = function(sFormattedDate)
{
	//console.log("sFormattedDate = " + sFormattedDate );
	var iMonth = parseInt(sFormattedDate.substr(0,2),10) - 1;
	var iDay = parseInt(sFormattedDate.substr(3,2),10);
	var iYear = parseInt(sFormattedDate.substr(6,4),10);
	var tmpDate = new Date(iYear,iMonth,iDay);
	return tmpDate;
};

UtilsClass.prototype.getDay = function(date){
    var day = date.getDay();
    return this.days[day];
};
UtilsClass.prototype.getMonth= function(date){
    var month = date.getMonth();
    return this.months[month];
};

UtilsClass.prototype.formatDateToTitle= function(date)
{
  return this.getDay(date) + ",&nbsp;" + this.getMonth(date) + " "  + date.getFullYear() ;
};

UtilsClass.prototype.checkNumber= function(val)
{
	var sVal = new String(val);
	val = sVal.replace(/[^\d]*/gi,'');
    return val;
};

UtilsClass.prototype.checkFloatNumber= function(val)
{
	var sVal = new String(val);
	var match = sVal.match(/^\d*\.?\d{0,2}/);
	if (match !== null) 
	{
	    return match[0];
	}
	else
	{
		return parseFloat(sVal);
	}
};


UtilsClass.prototype.checkPhoneNumber= function(elId){
    var el = document.getElementById(elId);
    var strPass = el.value;
    if (strPass.length >= 3) {
        strPass = strPass.replace(/[^0-9]/, '');
        el.value = strPass;
        if (strPass.length >= 3) {
            var nextId = el.getAttribute("next");
            if (nextId != "") {
                (document.getElementById(nextId)).focus();
                return;
            }
        }
    }
    el.focus();
};

UtilsClass.prototype.htmlEntityDecode = function(s)
{
	if (s)
	{
		console.log("htmlEntityDecode :: s = ",s);
		var ta = document.createElement("textarea");
		ta.innerHTML = s.replace(/</g,"&lt;").replace(/>/g,"&gt;");
		return ta.value;
	}
	return "";
};

UtilsClass.prototype.validateEmail = function(email)
{
	var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
	//console.log(reg.test(email));
	return reg.test(email);
};

UtilsClass.prototype.validateTimeFormat = function(sTime)
{
	var re = new RegExp("^([0-1][0-9])(:([0-5][0-9])[ap]m)$");
	return re.test(sTime);
};

UtilsClass.prototype.plusOneHour = function(sTime)
{
    var hour = parseInt(sTime.substr(0,2),10) + 1;
    var mins = sTime.substring(sTime.indexOf(':') + 1, 5);
    var dayPart = sTime.indexOf('pm') > -1 ? 'pm' : 'am';

    if (hour == 12)
    {
    	dayPart = dayPart == 'am' ? 'pm' : 'am';
    }
    
    hour = hour < 10 ? '0' + hour : hour;    
    return (hour + ":" + mins + dayPart);
};

/* KEY CODES */
UtilsClass.prototype.isDigit = function(keyCode)
{
	return keyCode >= 48 && keyCode <= 57;
};

UtilsClass.prototype.isDigitFromChar = function(keyChar)
{
	return ("0123456789").indexOf(keyChar) > -1;
};

UtilsClass.prototype.isRemoveKey = function(keyCode)
{
	// *** backspace:8; delete:46
	return keyCode == 8 || keyCode == 46;
};

UtilsClass.prototype.isFunctionalKey = function(keyCode)
{
	// *** backspace:8; tab:9; enter:13; end:35; home:36; arrows:37-40; delete:46
	return keyCode == 8 || keyCode == 9 || keyCode == 13 || (keyCode >= 35 && keyCode <= 40) || keyCode == 46;
};

// *** ARRAY
Array.prototype.inArray = function(s)
{
	for (var i = 0, iL = this.length; i < iL; i++)
	{
		if (this[i] === s)
		{
			return true;
		}
	}
	return false;
};


/*
Array.prototype.add = function(s)
{
	if (!this.inArray(s))
	{
		this.push(s);
	}
	return this;
};
*/

//// Removes leading whitespaces
//function LTrim( value ) {
//
//	var re = /\s*((\S+\s*)*)/;
//	return value.replace(re, "$1");
//
//}
//
//// Removes ending whitespaces
//function RTrim( value ) {
//
//	var re = /((\s*\S+)*)\s*/;
//	return value.replace(re, "$1");
//
//}
//
//// Removes leading and ending whitespaces
//function trim( value ) {
//
//	return LTrim(RTrim(value));
//
//}