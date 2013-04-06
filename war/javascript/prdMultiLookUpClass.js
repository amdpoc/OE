PrdMultiLookUpClass = function(parent)
{
    this.prevPRDSearch = "";
    this.productListSize = 0;
    this.mode = "";
    this.productListCurrentIndex = 0;
    //this.prdArraySelected = new Array();
    this.oSelectedProducts = {};
    
    this.parent = parent;
    this.utils = this.parent.utils;
	this.searchResults = false;

    this.init();
};

PrdMultiLookUpClass.prototype.setMode = function(mode) 
{
	this.mode = mode;
};

PrdMultiLookUpClass.prototype.init = function() 
{
	this.elPopOver = $("#prdLookup");
	this.setEvents();
};

PrdMultiLookUpClass.prototype.setEvents = function() 
{
	var that = this;
	$('#closePrdLookup')
		.click(function() 
			{
				//console.log("save clicked...");
				//that.elPopOver.hide();
				that.elPopOver.modal('hide');
    			that.utils.hideMask();
    			that.parent.toggleSubmitButton();
			}
		);    
};

PrdMultiLookUpClass.prototype.lookupPRD = function(mode,baId,proposalId) 
{
    // *** CREATE SELECTED PRODUCTS ARRAY FOR EACH ACTIVE BILLING ACCOUNT
	console.log("lookupPRD :: baId=",baId,"proposalId=",proposalId);
	this.iActiveBA = baId;
	this.iActiveProposal = proposalId;
	this.sActiveArrayName = baId + "_" + proposalId;
	if (!this.oSelectedProducts[this.sActiveArrayName])
    {
    	this.oSelectedProducts[this.sActiveArrayName] = [];
    }
    
	var that = this;
	var inputString = "";
	this.mode = mode;
	switch(mode)
	{
		case "eSign":
            inputString = $('#prdInputString').val();
		    $('#prdInputString').blur();
            break;
        case "record":
            inputString = $('#rop_prdInputString').val();
            $('#rop_prdInputString').blur();
            break;
        default:
            inputString = $('#prdInputString_newCust').val();
		    $('#prdInputString_newCust').blur();
            break;
	}
	
    if (inputString.length < 3) 
    {
        alert('Search criteria has to be bigger than 3 characters!');  //search.validation1
    } 
    else 
    {
        //this.utils.showMask();
        if (this.prevPRDSearch == inputString) 
        {
        	if (this.searchResults)
        	{
            	//that.elPopOver.show();
            	that.elPopOver.modal ('show');
            	that.highlightSelectedProducts();
    	        setTimeout(function(){that.toggleClearAllButton()},50);
        	}
        	else
        	{
				alert('No products found!');//search.empty
    			that.utils.hideMask();
        	}
        } 
        else 
        {
        	$("#loader").show();
            this.prevPRDSearch = inputString;

            $.post('ProductList', 
            		{pdrQueryString: inputString}, 
            		function(data) 
            		{
            			var oData = eval('(' + data + ')');    			
            			console.log("getProductsList callback :: oData = ",oData);
            			if (oData && oData.length > 0) 
            			{
            				that.searchResults = true;

            				$('#lookupPRDList').children().remove();
            				var aHTML = [];
            				for (var i = 0, iL = oData.length; i < iL; i++)
            				{
	            				var sPrCode = oData[i].productCode;
	            				var sPrName = oData[i].productName;
	            				var sPrDisplay = oData[i].productDisplay;
	            				var sIssueDate = oData[i].issueDate;
	            				var sIssueDateDisplay = oData[i].issueDateDisplay;
	            				var sPrIssueNum = oData[i].productIssueNum;
	            				var sPrValue = sPrCode + "||" + sPrIssueNum;
	            				var sIssueRelInd = oData[i].issueRelatedInd;
	            				
	            				var oSelected = that.isAlreadySelected(sPrValue);
	            				//console.log("oSelected = ",oSelected);
	            				var sSelected = oSelected.selected ? " selected" : "";
	            				var iSelectedIndex = oSelected.index != 999 ? oSelected.index : "";
	            				
	            				aHTML.push('<li class="prdRow' + sSelected + '" customType="erase_button" index="' + iSelectedIndex + '" issueDateDisplay="' + sIssueDateDisplay + '" issueDate="' + sIssueDate + '" productIssueNum="' + sPrIssueNum + '" prdValue="' + sPrValue + '" prdCode="' + sPrCode + '" prdName="' + sPrName + '" issueRelatedInd="' + sIssueRelInd + '">');
	        					aHTML.push(sPrDisplay + '</li>');
            				}
            				$('#lookupPRDList').html(aHTML.join(""));
            				$('#modalHeader').text("Directories including \"" + inputString + "\":");
            				
            				//that.elPopOver.show();
            				that.elPopOver.modal('show');
            		        that.setPrdRowEvents();
            		        setTimeout(function(){that.toggleClearAllButton()},100);
            			} 
            			else 
            			{
            				that.searchResults = false;
            				alert('No products found!');   //search.empty
            				that.utils.hideMask();
            			}
            			$("#loader").hide();
            		}
            	);
        }
    }
};

PrdMultiLookUpClass.prototype.highlightSelectedProducts = function() 
{
	var that = this;
	var aSelProducts = this.oSelectedProducts[this.sActiveArrayName];
	var iL = aSelProducts.length;
	console.log("setSelectedProducts :: this.oSelectedProducts[" + this.sActiveArrayName + "] = ",aSelProducts);

	$("ul#lookupPRDList li")
		.each(function(i)
			{
				$(this).removeClass("selected");	
				//console.log("setSelectedProducts :: prdValue",$(this).attr("prdValue"));
				var oSelected = that.isAlreadySelected($(this).attr("prdValue"));
				if (oSelected.selected)
				{
					$(this).addClass("selected");
				}
			}
		);
};

PrdMultiLookUpClass.prototype.setPrdRowEvents = function() 
{
	var that = this;
	
	$("ul#lookupPRDList li")
		.click(function()
			{
				var index = $(this).attr("index");
				//console.log("click on li :: index = ",index);
				that.handlePRD(index,$(this));
			}
		)
		.mouseup(function()
			{
				return false;
			}
		);
	
	$("#clearPrdResults")
		.click(function()
			{
				that.clearAllSelectedProductsInPopover();
			}
		)
		.mouseup(function()
			{
				return false;
			}
		);
};

PrdMultiLookUpClass.prototype.handlePRD = function(index,$el) 
{
    var that = this;
	//console.log("handlePRD :: index = ",index);
    if (index == "") 
    {
    	$el.addClass('selected');
        $el.attr("index", this.productListCurrentIndex);
        var i = this.productListCurrentIndex;

        if (that.mode == "eSign" || that.mode == "record")
		{
			console.log("handlePRD ... BEFORE that.parent.handleSelectedPRD...");
        	that.parent.handleSelectedPRD(i,$el);
		}
        else
        {
        	var liTag = $('<li id="prd' + i + '"></li>');

	        $('<div class="selectedProduct fleft width80" id="selPrd' + i + '" prdCode="' + $el.attr("prdCode") + '">' + $el.attr("prdName") + '</div>').appendTo(liTag);
	
	        var divTag = $('<div id="eraseBtn' + i + '"class="fright ui-state-highlight ui-corner-all gadgetDeleteBtn"><span class="ui-icon ui-icon-trash"></span></div>');
	        divTag.attr('index', i);
	        liTag.append(divTag);
	
	        var elUList = this.mode == "newOppt" ? $("#productList") : $("#productList_newCust"); 
	        elUList.append(liTag);
	
	        $('#eraseBtn' + this.productListCurrentIndex)
	        	.click(function() 
	        		{
	        			var index = $(this).attr("index");
	        			that.removePRD(index);
	        			that.parent.toggleSubmitButton();
	        		}
	        	);
        }
        
    	var aSelProducts = this.oSelectedProducts[this.sActiveArrayName];
    	//aSelProducts[this.productListCurrentIndex] = $el.attr("prdValue");
    	aSelProducts.push($el.attr("prdValue"));
        this.productListSize = this.productListSize + 1;
        this.productListCurrentIndex = this.productListCurrentIndex + 1;
    } 
    else 
    {
        this.removePRDbyIndex(index);
        $el.removeClass('selected').attr("index","");
        if (this.mode == "eSign")
        {
            eSign_Data.updateEnvelope($(this),that.parent.activeBAId);
        }
        else
        {
            ROP_Data.updateEnvelope($(this));
        }
    };
    
    setTimeout(function()
    	{
    		that.toggleClearAllButton();
    	}
    	,100
    );
};

PrdMultiLookUpClass.prototype.setArrayForSelectedProducts = function(activeBAId, activeProposalId) 
{
	console.log("setArrayForSelectedProducts :: activeBAId=",activeBAId,"activeProposalId=",activeProposalId);
	this.iActiveBA = activeBAId;
	this.iActiveProposal = activeProposalId;
	this.sActiveArrayName = activeBAId + "_" + activeProposalId;
	if (!this.oSelectedProducts[this.sActiveArrayName])
	{
		this.oSelectedProducts[this.sActiveArrayName] = [];
	}
}
	
PrdMultiLookUpClass.prototype.initPreselectedProduct = function(prdValue) 
{
	//console.log("PrdMultiLookUpClass ::::::: initPreselectedProduct : prdValue=",prdValue,"this.oSelectedProducts=",this.oSelectedProducts);
	if (prdValue)
	{
		var aSelProducts = this.oSelectedProducts[this.sActiveArrayName];
		if (!aSelProducts.inArray(prdValue))
		{
			aSelProducts.push(prdValue);
		}
		this.productListSize = aSelProducts.length;
		this.productListCurrentIndex = this.productListCurrentIndex + 1;
	}
};

PrdMultiLookUpClass.prototype.toggleClearAllButton = function() 
{
    var aHighlighted = $("ul#lookupPRDList li.selected");
    //console.log("toggleClearAllButton :: aHighlighted = ",aHighlighted," size = ",aHighlighted.length);
	if (aHighlighted.length > 0)
    {
    	$("#clearPrdResults").show();
    }
    else
    {
    	$("#clearPrdResults").hide();
    }
};

PrdMultiLookUpClass.prototype.clearAllSelectedProductsInPopover = function() 
{
	var that = this;
	$("ul#lookupPRDList li.selected")
		.each(function()
			{
				var index = $(this).attr("index");
				that.removePRD(index);
				//that.parent.updateEnvelope($(this));
                if (this.mode == "eSign")
                {
                    eSign_Data.updateEnvelope($(this),that.parent.activeBAId);
                }
                else
                {
                    ROP_Data.updateEnvelope($(this));
                }
			}
		);
	this.toggleClearAllButton();
	//this.parent.toggleSubmitButton();
};

PrdMultiLookUpClass.prototype.removePRD = function(index) 
{
	this.removePRDbyIndex(index);
	this.removeSelectedPrd(index);
};

PrdMultiLookUpClass.prototype.removePRDbyIndex = function(index) 
{
    $("#prd" + index).remove();
	console.log("removePRDbyIndex :: this.oSelectedProducts = ",this.oSelectedProducts, "this.sActiveArrayName=",this.sActiveArrayName);
    var aSelProducts = this.oSelectedProducts[this.sActiveArrayName];
    aSelProducts[index] = "";
    this.productListSize = this.productListSize - 1;
    //console.log("removePRDbyIndex this.modeIndex :: this.mode = ",this.mode);
    if (this.mode == "eSign" || this.mode == "record")
    {
		this.parent.toggleSubmitButton();
    	this.parent.setTotalBillingValue();
    	this.parent.toggleEmptyRow();
    }
};

PrdMultiLookUpClass.prototype.removeSelectedPrd = function(index) 
{
    //console.log("removeSelectedPrd ... ");
	$('li.selected')
    	.each(function(i) 
    		{
    			if ($(this).attr("index") == index) 
    			{
    				$(this).removeClass('selected').attr("index","");
    			}
    		}
    	);
};

PrdMultiLookUpClass.prototype.isAlreadySelected = function(sProductValue) 
{
	var oSelected = {selected:false,index:999};
	//var iL = this.prdArraySelected.length;
	
	var aSelProducts = this.oSelectedProducts[this.sActiveArrayName];
	var iL = aSelProducts.length;
	console.log("isAlreadySelected :: this.oSelectedProducts[" + this.sActiveArrayName + "] = ",aSelProducts);
	for (var i = 0; i < iL; i++) 
    {
		//if (this.prdArraySelected[i] == sProductValue)
		if (aSelProducts[i] == sProductValue)
		{
			oSelected = {selected:true,index:i};
			return oSelected;
		}
    }
	return oSelected;
};

PrdMultiLookUpClass.prototype.getSelectedProducts = function() 
{
	var aSelProducts = this.oSelectedProducts[this.sActiveArrayName];
	var iL = aSelProducts.length;
	var prdStr = "";
	var aSelectedPrdArray = [];
    for (var i = 0; i < iL; i++) 
    {
    	prdStr = aSelProducts[i];
    	if (prdStr != "")
    	{
    		aSelectedPrdArray.push(prdStr);
    	}
    }
    return  aSelectedPrdArray;
};