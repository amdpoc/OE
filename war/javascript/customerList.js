// *** Created by okotlia 03/01/2011
var customerList;

$(function() 
	{
		customerList = new CustomerList_Class();
	}
);

CustomerList_Class = function()
{
    this.init();
};

CustomerList_Class.prototype.init = function()
{
    var that = this;
	this.elHome = $('#home').show();
    this.elLoader = $('#loader').hide();
    this.elPanelLoader = $("#rightPanel_loader");
    this.elSearchId = $("#customerId");
    this.elSearchName = $("#customerName");
    this.elSearchCOP = $("#custCop");
    this.elSearchNPA = $("#custNpa");
    this.elSearchLineNo = $("#custLineNo");
	this.elEmptySummary = $("#emptySummary");
	this.elCustomerListPaging = $('#paging');
	this.elCustomerList = $('#lookupCustomerList');
	this.elCustLookupScroller = $('#custLookupScroller');
    this.elSubmit = $("#submitSearch");
    this.elSubmitPassive = $("#submitSearch_passive");
    this.generalInfo = generalInfo;

    
    this.bActiveCriteria = false;
	this.prevPage = prevPage;
	this.searchCriteria = searchCriteria;
	
	this.oData = {
		prevPage:prevPage
		,totalPagesNum:totalPagesNum
		,currentPageNum:currentPageNum
		,pageSize:pageSize
		,totalRecordsNum:totalRecordsNum
		,maxRecordsNumber:maxRecordsNumber
		,resultList:resultList
	}
	console.log("init ::: this.oData = ",this.oData);
	console.log("&&&& generalInfo=",this.generalInfo);

    this.utils = new UtilsClass();
    this.utils.renderUserName(this.generalInfo.employeeName);
	this.utils.setDWR();
    this.utils.setFullHeight();

	if (this.oData.resultList.length > 0)
	{
		this.renderPagedResults(this.oData);
		this.renderSearchCriteria();
		this.disableOtherInputs();
	}
	this.setEvents();
};

CustomerList_Class.prototype.renderSearchCriteria = function()
{
    if (this.searchCriteria)
    {
        this.elSearchId.val(this.searchCriteria.custId);
        this.elSearchName.val(this.searchCriteria.custName);
        this.elSearchCOP.val(this.searchCriteria.custCOP);
        this.elSearchNPA.val(this.searchCriteria.custNPA);
        this.elSearchLineNo.val(this.searchCriteria.custLineNo);    	
    }
};

CustomerList_Class.prototype.setEvents = function() 
{
    var that = this;
    
	$("#backBtn")
		.click(function()
			{
				that.elLoader.show();
				window.location = that.prevPage;
			}
		);
	
	$("#homeBtn")
		.click(function()
			{
				that.elLoader.show();
				window.location.href = "customerList.htm";
			}
		);
	
    $('#logout')
		.click(function() 
			{
				that.elLoader.show();
				window.location.href = 'logout.htm';
	        }
	    );

    $('#clearCustIdSearch')
		.click(function() 
			{
				var sId = $("#customerId").val();
				if (sId != "")
				{
					$("#customerId").val("");
					that.enableAllInputs();
				}
			}
		);
    
    $('#clearNameSearch')
		.click(function() 
			{
				var sName = $("#customerName").val();
				if (sName != "")
				{
					$("#customerName").val("");
					that.enableAllInputs();
				}
			}
		);
    
    $('#clearPhoneSearch')
		.click(function() 
			{
				var sCOP = $("#custCop").val();
				var sNPA = $("#custNpa").val();
				var sLineNo = $("#custLineNo").val();
				if (sCOP != "" || sNPA != "" || sLineNo != "")
				{
					$("#custCop").val("");
					$("#custNpa").val("");
					$("#custLineNo").val("");
					that.enableAllInputs();
				}
			}
		);
    
    $('#clearAll')
		.click(function() 
			{
				$("#customerId").val("");
				$("#customerName").val("");
				$("#custCop").val("");
				$("#custNpa").val("");
				$("#custLineNo").val("");
				that.elCustomerListPaging.hide();
				that.elCustomerList.empty();
				that.elEmptySummary.show();
				that.enableAllInputs();
			}
		);
    
    this.elSearchId
		.keyup(function()
			{
				$(this).val(that.utils.checkNumber($(this).val()));
				that.disableOtherInputs("custId");
			}
		);   
    
    this.elSearchName
    	
    	.keyup(function()
			{
	        	that.disableOtherInputs("custName");
			}
		)
		/*
		.change(function(event)
			{
	        	alert("change :: event.keyCode = " + event.keyCode);
    			//that.disableOtherInputs("custName");
			}
		);
		 */
    
    $('.phone')
		.keypress(function(event)
			{
	        	var keyCode = event.keyCode;
	        	var keyChar = String.fromCharCode(keyCode);
				var id = $(this).attr("id");
				var maxLength = $(this).attr("maxLength");
	        	var isDigit = that.utils.isDigitFromChar(keyChar);
	        	var val = $(this).val() + "";
	        	console.log("keypress :: keyChar = " + keyChar + "\nisDigitFromChar = " + isDigit + "\nval = " + val + "\nlength = " + val.length);
	        	if (!isDigit || val.length >= maxLength)
	        	{
	        		return false;
	        	}
			}
		)
		.keyup(function(event)
			{
				var keyCode = event.keyCode;
				var keyChar = String.fromCharCode(keyCode);
				var isDigit = that.utils.isDigitFromChar(keyChar);
				var isRemoveKey = that.utils.isRemoveKey(keyCode);
				var val = $(this).val() + "";
				var maxLength = $(this).attr("maxLength");
	        	console.log("keyup :: keyChar = " + keyChar + "\nisDigitFromChar = " + isDigit + "\nisRemoveKey = " + isRemoveKey + "\nlength = " + val.length);
				if (isDigit || isRemoveKey)
				{
					if (val.length <= maxLength)
	        		{
	        			that.disableOtherInputs("phone");
	        		}
	        	}
				else
				{
	        		event.preventDefault();
	        		return false;					
				}
			}
		);
   
    $('#submitSearch')
    	.click(function() 
    		{
    			that.elLoader.show();
    			that.customerSearch();
    		}
    	);
    
    $('#custPrevBtn')
		.click(function()
			{
				that.getCustomersPage('PREV');
			}
		);
	
	$('#custNextBtn')
		.click(function()
			{
				that.getCustomersPage('NEXT');
			}
		);
};

CustomerList_Class.prototype.enableAllInputs = function() 
{
	this.elSearchId.attr("disabled",false);
	this.elSearchName.attr("disabled",false);
	this.elSearchCOP.attr("disabled",false);
	this.elSearchNPA.attr("disabled",false);
	this.elSearchLineNo.attr("disabled",false);	
	this.toggleSearchSubmitButton(false);
};

CustomerList_Class.prototype.disableOtherInputs = function(sType) 
{
	if (!sType)
	{
		for (var i in this.searchCriteria)
		{
			if (this.searchCriteria[i] != "")
			{
				sType = i == "custId" ? "custId" : (i == "custName" ? "custName" : "phone")
				break;
			}
		}
	}
	
    switch(sType)
	{
		case "custId":
			if (this.elSearchId.val() != "")
			{
				this.elSearchName.attr("disabled",true);
				this.elSearchCOP.attr("disabled",true);
				this.elSearchNPA.attr("disabled",true);
				this.elSearchLineNo.attr("disabled",true);
				this.toggleSearchSubmitButton(true);
			}
			else
			{
				this.enableAllInputs();
			}
			break;
		case "custName":
			if (this.elSearchName.val() != "")
			{
				this.elSearchId.attr("disabled",true);
				this.elSearchCOP.attr("disabled",true);
				this.elSearchNPA.attr("disabled",true);
				this.elSearchLineNo.attr("disabled",true);
				this.toggleSearchSubmitButton(true);
			}
			else
			{
				this.enableAllInputs();
			}
			break;
		case "phone":
			var sCOP = this.elSearchCOP.val();
			var sNAP = this.elSearchNPA.val();
			var sLineNo = this.elSearchLineNo.val();
			if (sCOP != "" || sNAP != "" || sLineNo != "")
			{
				this.elSearchId.attr("disabled",true);
				this.elSearchName.attr("disabled",true);
				//alert(sCOP + " ... " + sNAP + " ... " + sLineNo);
				var bSubmit = sCOP.length == 3 && sNAP.length == 3 && sLineNo.length == 4;
				this.toggleSearchSubmitButton(bSubmit);
			}
			else
			{
				this.enableAllInputs();
			}
			break;
		default:
			break;
	}
};

CustomerList_Class.prototype.toggleSearchSubmitButton = function(flag) 
{
    if (flag)
    {
    	//this.elSubmit.show();
    	this.elSubmit.removeClass("none");
    	this.elSubmitPassive.hide();
    }
    else
    {
    	//this.elSubmit.hide();
    	this.elSubmit.addClass("none");
    	this.elSubmitPassive.show();
    }
};

CustomerList_Class.prototype.setCustRowsEvent = function() 
{
	var that = this;
    $('li.custRow')
    	.click(function() 
    		{
    			var sCustId = $(this).attr("custid");
    			that.elLoader.show();
				window.location.href = "customerDetails.htm?custId=" + sCustId;
    		}
    	)
    	.mouseup(function()
    		{
    			return false;
    		}
    	);
};

CustomerList_Class.prototype.customerSearch = function() 
{
	var that = this;
	this.bMsgAlreadyShown = false;
	var callback = function(data)
		{	
			var oData = eval('(' + data + ')');    			
			that.renderPagedResults(oData); 
		}
	var sSearchName = $("#customerName").val() || "";
	var sSearchId = $("#customerId").val() || "";
	var sSearchNPA = $("#custNpa").val() || "";
	var sSearchCOP = $("#custCop").val() || "";
	var sSearchLineNo = $("#custLineNo").val() || "";
	console.log("BEFORE getCustListDWR: sSearchName = ",sSearchName);

    DwrCustListManager.getCustListDWR(sSearchName,sSearchId,sSearchNPA,sSearchCOP,sSearchLineNo, callback);
};

CustomerList_Class.prototype.getCustomersPage = function(pageInd)
{
	var that = this;
	var callback = function(data)
		{	
			var oData = eval('(' + data + ')');    			
			that.renderPagedResults(oData); 
		}
    DwrCustListManager.getCustListPageDWR(pageInd, callback);
};

CustomerList_Class.prototype.renderPagedResults = function(oData) 
{
	var that = this; 
	console.log("renderPagedResults callback :: oData = ",oData);
	if (oData) 
	{
		if (oData.resultList && oData.resultList.length > 0)
		{				
			that.searchResults = true;
			that.elEmptySummary.hide();
			var aRes = oData.resultList;
			var iCurrentPageSize = aRes.length;
			var iTotalPages = oData.totalPagesNum;
			var iMaxRecordsNumber = oData.maxRecordsNumber;
			var iCurrentPage = oData.currentPageNum;
			var iPageSize = oData.pageSize;
			var iStart = (iCurrentPage - 1) * iPageSize + 1;
			var iFinish = iStart + iCurrentPageSize - 1;
			var iTotalRecordsNum = oData.totalRecordsNum;

			console.log("getCustomers :: this.bMsgAlreadyShown = ",that.bMsgAlreadyShown,"iTotalRecordsNum = ",iTotalRecordsNum,"iMaxRecordsNumber = ",iMaxRecordsNumber);
			/*
			if (iTotalRecordsNum >= iMaxRecordsNumber && !that.bMsgAlreadyShown)
			{
				alert("More records exist in the database");
			}
			*/
			if (iTotalPages > 1)
			{
				console.log("iCurrentPage = ",iCurrentPage);
				if (iCurrentPage > 1)
				{
					$("#custPrevBtn").show();
				}
				else
				{
					$("#custPrevBtn").hide();
				}
				
				if (iCurrentPage < iTotalPages)
				{
					$("#custNextBtn").show();          					
				}
				else
				{
					$("#custNextBtn").hide(); 
				}
				
				var sMoreResults = "";
				if (iTotalRecordsNum >= iMaxRecordsNumber)
				{
					sMoreResults = "<br /><span>(There are more records than can be displayed. Only 500 will be shown.)</span>";
				}
				
				$("#custCurrent").html(iStart + " - " + iFinish + " of " + iTotalRecordsNum + " results" + sMoreResults).show();
				this.elCustLookupScroller.removeClass("no_paging");
				that.elCustomerListPaging.show();
			}
			else
			{
				that.elCustomerListPaging.hide();
				this.elCustLookupScroller.addClass("no_paging");
			}
			
			
			var aHTML = [];
			for (var i = 0, iL = aRes.length; i < iL; i++)
			{
				var sClass = i % 2 == 0 ? "bg_odd" : "bg_even";
				var oCust = aRes[i];
				var sCustName = (oCust.customerName && oCust.customerName != "null") ? oCust.customerName : "";
				var sContactName = (oCust.contactName && oCust.contactName != "null") ? oCust.contactName : "";
				
				var sCustId = (oCust.customerId && oCust.customerId != "null") ? oCust.customerId : "";
				var sCustAddress = (oCust.address && oCust.address != "null" && this.utils.trim(oCust.address) != "") ? oCust.address : "";
				var sCustPhone = (oCust.formattedPhone && oCust.formattedPhone != "null") ? oCust.formattedPhone : "";
				var sProspectCode = (oCust.prospectCode && oCust.prospectCode != "null") ? oCust.prospectCode : "";
				var sBots = (oCust.bots && oCust.bots != "null") ? oCust.bots : "";
				var sNisd = (oCust.nisd && oCust.nisd != "null") ? oCust.nisd : "";
				
				aHTML.push('<li class="custRow ' + sClass + '" custname="' + sCustName + '" custid="' + sCustId + '">');
				aHTML.push('	<div class="custName fleft">');
				aHTML.push('		<div class="fleft width100"><strong><span><a href="javascript:void(0);" class="no_deco">' + sCustId + '</a></span> - ' + sCustName + '</strong></div>');
				if (sCustAddress != "")
				{
					aHTML.push('	<div class="fleft width100">' + sCustAddress + '</div>');
				}
				if (sProspectCode != "")
				{
					aHTML.push('	<div class="fleft width100">' + sProspectCode  + '</div>');
				}
				aHTML.push('		<div class="fleft width100"><strong>BOTS:</strong> $' + this.utils.trim(sBots) + ' <strong>NISD:</strong> $' + this.utils.trim(sNisd) + '</div>');
				aHTML.push('	</div>');
				aHTML.push('	<div class="custPhone fright">' + sContactName + '<br /><span><a href="javascript:void(0);" class="no_deco">' + sCustPhone + '</a></span></div>');
				aHTML.push('</li>');
			}
			that.elCustomerList.html(aHTML.join(""));
			//$('#customerLookupTerm').val(that.prevCustSearch);
			
			//that.elPopOver.show();
			//that.rightScroller.setupScroller();
	        //that.rightScroller.scrollTo(0, 0);
	        that.setCustRowsEvent();
		}
		else
		{
			that.searchResults = false;
			that.elCustomerList.empty();
			that.elCustomerListPaging.hide();
			that.elEmptySummary.show();
			that.utils.hideMask();        				
		}
	} 
	else 
	{
		that.searchResults = false;
		that.elCustomerList.empty();
		that.elCustomerListPaging.hide();
		that.elEmptySummary.show();
		//alert('No records found!'); //search.empty
		that.utils.hideMask();
	}
	that.elLoader.hide();
};