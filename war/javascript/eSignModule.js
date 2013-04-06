// *** Created by okotlia 06/27/2011

eSign_Class = function(caller)
{
	this.caller = caller;
	this.init();
};

eSign_Class.prototype.init = function() 
{
	this.eSignEventsSet = false;
	this.prevPage = prevPage;
    this.oModalDialog = modalDialog;
    this.contactName = contactName;
    this.contactEmail = contactEmail;
    this.elMask = $("#mask");
    this.elLoader = $("#loader");
    this.elBAInfo = $("#baInfoDiv");
    this.elEmptyProposal = $("#emptyProposal");
    this.bESignEventsSet = false;
    this.bBillingInfoEventsSet = false;
	this.bEmptyEnvelop = false;
	this.isPDFImagesRendered = false;
	this.iProposalId = this.caller.proposalIDeSign != 0 ? this.caller.proposalIDeSign : 1;
	this.isiPad = navigator.userAgent.match(/iPad/i) != null;

    this.utils = new UtilsClass();
    this.proposalsTopBarModule = new ProposalsTopBarModule_Class(this);
    this.setEvents();
    
	this.oCallerData = 
	{
		custId:parseInt(this.caller.custId,10),
		businessName:this.utils.decodeHTMLEntities(this.caller.businessName),
		contactEmail:contactEmail,
		contactTitle:this.caller.contactTitle,
		contactName:contactName
	}
	console.log("eSign_Class.prototype.init :: this.oCallerData=",this.oCallerData);
};

eSign_Class.prototype.savePreviousProposalToSession = function(callbackFromCaller)
{
    var aPrevProposalProducts = eSign_Data.getProductsList(this.iProposalId);
    console.log("savePreviousProposalToSession :: this.proposalId = ",this.iProposalId,"this.custId = ",this.oCallerData.custId,"aPrevProposalProducts=",aPrevProposalProducts);

    if (this.iProposalId == 6 && aPrevProposalProducts.length == 0)
    {
        // *** NEW ENVELOPE CASE WITH NO PRODUCTS ADDED
        var oBA = eSign_Data.getNewEnvelopeBillingInfo();
        var callback = function(result)
        {
            if (callbackFromCaller)
            {
                callbackFromCaller.apply(this);
            }
            console.log("callback AFTER saveNewEnvelopeNoProductsToSessionDWR :: result = ",result);
        }
        DwrProposalManager.saveNewEnvelopeNoProductsToSessionDWR("esign", this.oCallerData.custId,oBA,callback);
    }
    else
    {
        var callback = function(result)
        {
            if (callbackFromCaller)
            {
                callbackFromCaller.apply(this);
            }
            console.log("callback AFTER saveNewEnvelopeNoProductsToSessionDWR :: result = ",result);
        }
        DwrProposalManager.saveProposalProductsListToSessionDWR("esign", this.oCallerData.custId,this.iProposalId,aPrevProposalProducts,callback);
    }
};

eSign_Class.prototype.setEvents = function(type) 
{
    var that = this;

	$("#cancelESign")
		.click(function()
			{
				var state = that.caller.getPageState();
				var sDelimiter = that.prevPage.indexOf("?") > 0 ? "&" : "?"
				var sURL = that.prevPage + sDelimiter + "state=" + state;
                eSign_Data.updateEnvelopeFromCurrentInfo(that.activeBAId);
                window.location = sURL;
			}
		);	
};

eSign_Class.prototype.renderESignTab = function(aData) 
{
	if (aData)
	{
		console.log("renderESignTab :: aData = ",aData);
		this.renderProposal(aData)
	}
	else
	{
		this.renderProposal();		
	}
};

eSign_Class.prototype.renderProposal = function(aData) 
{
	this.caller.elLoader.show();		
	var that = this;
    eSign_Data.setActiveProposal(this.iProposalId);
    console.log("&&&&&&&&&&&&& renderProposal :: this.iProposalId = ",this.iProposalId);
	this.proposalsTopBarModule.init();
    this.proposalsTopBarModule.setProposalId(this.iProposalId)
	this.proposalsTopBarModule.highlightActiveTab();
	console.log("renderProposal :: aData=",aData);
	if (aData && this.caller.enableTransFlag)
	{
		console.log("renderProposal :: BEFORE createEnvelope ...");
		eSign_Data.setEnvelope(aData,this.oCallerData,"esign");

   		if (aData.length > 0)
   		{
   			that.renderProposalContent();
   		}
   		else
   		{
   			that.renderEmptyProposalMessage();
   			that.prdLookup.setMode("eSign");
   		}	
	}
	else
	{
        var iActiveProposalIndex = this.iProposalId - 1;
		var oEnvelope = eSign_Data.getEnvelope();
		console.log("renderProposal :: oEnvelope = ",oEnvelope);
		if (oEnvelope)
		{	
			if (oEnvelope.billingAccounts.length > 0)
			{
				that.renderProposalContent();
			}
			else
			{
				that.renderEmptyProposalMessage("There are no Billing Accounts.");
			}
		}
		else
		{
			var callback = function(aResult) 
		    { 
				console.log("******** renderProposal callback :: aResult = ",aResult, "custId = ", that.caller.custId);
				if (aResult) 
		    	{
					//that.createEnvelope(aResult);
					eSign_Data.setActiveProposal(that.iProposalId);
					console.log("callback :: that.oCallerData = ",that.oCallerData);
					eSign_Data.setEnvelope(aResult,that.oCallerData,"esign");
		       		if (aResult.length > 0)
		       		{
		       			that.renderProposalContent();
		       		}
		       		else
		       		{
		       			that.renderEmptyProposalMessage();
		       			that.prdLookup.setMode("eSign");
		       		}
		        } 
		    	else 
		    	{	
		            that.utils.setError("A system problem has occurred that will require you to contact the Business Support Center (BSC) for assistance.  Please reference this error: LOADING CUSTOMER BY ID." + iCustId);//error.customer.load
		            that.elLoader.fadeOut("fast");
		        }       
		    };
		    var iCustId = parseInt(that.caller.custId,10);
            var sType = "esign";
		    if (this.caller.enableTransFlag)
		    {
			    if (this.sType == "pendingPublish")
			    {
			    	DwrProposalManager.getPendingPublishByCustomerId(sType, iCustId, callback);
			    }
			    else
			    {
			    	DwrProposalManager.getProposalByCustomerId(sType, iCustId, this.iProposalId, callback);
			    }
		    }
		    else
		    {
		    	//var msg = that.utils.MESSAGES.custNotAssignedToRep; 
		    	var msg = "Customer " + that.caller.custId + " is not assigned to you.<br>Please contact the Solution Center for assignment<br>or enter another customer."
		    	var options = {height:"170px"}; 
		    	that.renderEmptyProposalMessage(msg,options);
		    }
		}
	}
};

eSign_Class.prototype.renderEmptyProposalMessage = function(msg,options) 
{
	$("#accounts_content").empty();
	this.hideProposalContent();
	var sMsg = msg ? msg : this.utils.MESSAGES.noKGenData; 
	if (options)
	{
		this.elEmptyProposal.css(options);
	}
	this.elEmptyProposal.show().find("span").html(sMsg);
	this.elLoader.hide();
	this.setESignEvents();
};	

eSign_Class.prototype.hideProposalContent = function() 
{
	$("#billingAccounts").hide();
	$("#billingAccount").hide();
	$("#esign_table").hide();
	$("#esign_footer").hide();	
	this.elBAInfo.hide();
};

eSign_Class.prototype.showProposalContent = function() 
{
	$("#billingAccounts").show();
	$("#billingAccount").show();
	$("#esign_table").show();
	$("#esign_footer").show();	
	this.elBAInfo.show();
};

eSign_Class.prototype.renderProposalContent = function() 
{
	//console.log("renderProposalContent...");
	this.aEmptyRows = $("#esign_table div[class*='empty_']");
	
	if (!this.eSignEventsSet)
	{
		this.setESignEvents();
	}
	this.prdLookup.setMode("eSign");
	
	this.renderTabContent();
	this.toggleSubmitButton();
	this.elEmptyProposal.hide();
	this.showProposalContent();
	this.caller.elLoader.hide();		
};

eSign_Class.prototype.renderTabContent = function() 
{
	/*
    if (this.sType == "newEnvelop")
	{
		//$('#addNewBa').hide();
		this.renderNewEnvelopTab();
	}
	else
	{
	*/
		//$('#addNewBa').show();
		$("#envelop").hide();
		this.renderBillingAccounts();
		this.renderBillingAccount();
	//}
};

eSign_Class.prototype.renderBillingAccounts = function() 
{
	var iActiveProposalIndex = this.iProposalId - 1;
	var aData = eSign_Data.getBAs();
	console.log("renderBillingAccounts :: aData = ",aData,"iActiveProposalIndex = ", iActiveProposalIndex);
	var aHTML = [];
	var iL = aData.length;
	if (iL > 0)
	{
		for (var i = 0; i < iL; i++)
		{
			var sBA = aData[i].finBAccountId && (aData[i].finBAccountId != "null" && aData[i].finBAccountId != 0) ? aData[i].finBAccountId : "New Billing Account";
			var iBA = sBA == "New Billing Account" ? 0 : sBA; 
			var sClass = i == 0 ? " activeBA" : "";
			aHTML.push('<div class="ba' + sClass + '">');
			aHTML.push(		'<a class="underline" id="ba_' + i + '" index="' + i + '">' + sBA + '</a> ');
			//aHTML.push(		' (<span class="warn_color">' + aData[i].source + '</span>)');
			
			if (iL > 1)
			{
				aHTML.push(		'<div id="removeBABtn' + i + '" index="' + i + '" baId="' + iBA + '" class="fright ui-state-highlight ui-corner-all gadgetDeleteBtn">');
				aHTML.push(			'<span class="ui-icon ui-icon-minus"></span>');
				aHTML.push(		'</div>');
			}
			
			aHTML.push(	'</div>');
		}
	}
	
	$("#accounts_content").html(aHTML.join(""));
	$("#bas").show();
		
	this.setBillingAccountsEvents();
};

eSign_Class.prototype.setBillingAccountsEvents = function() 
{
	var that = this;
	$("a[id^='ba_']")
		.click(function(e)
			{
				e.preventDefault();
				console.log("... --- billing account clicked --- ...");
				that.caller.elLoader.show();
				var index = $(this).attr("index");
				$("div.ba").removeClass("activeBA");
				$(this).parent().addClass("activeBA");
				that.renderBillingAccount(index);
			}
		);
	
	$("div[id^='removeBABtn']")
		.click(function()
			{
				eSign_Data.updateEnvelope($(this),that.activeBAId);
				
				//var iActiveProposalIndex = that.iProposalId - 1;
				var aBAs = eSign_Data.getBAs();
				//if (that.aProposals[iActiveProposalIndex].oEnvelope.billingAccounts.length == 0)
				if (aBAs.length == 0)
				{
					that.renderEmptyProposalMessage("There are no Billing Accounts.");
				}
				else
				{
					console.log("BEFORE BA removal this = ",$(this));
					$(this).parent("div.ba").hide().remove();
					that.renderBillingAccounts();
					that.renderBillingAccount();

				}
			}
		);
	
};

eSign_Class.prototype.renderBillingAccount = function(index) 
{
	var iActiveProposalIndex = this.iProposalId - 1;
	var oEnvelope = eSign_Data.getEnvelope();
	var aData = oEnvelope.billingAccounts;
	console.log("renderBillingAccount :: aData = ",aData);
	if (aData.length > 0)
	{
		var oBA = index ? aData[index] : aData[0];
		console.log("+++++++ renderBillingAccount :: oBA = ",oBA);
		this.renderBillingInfo(oBA);
		this.renderAccountProducts(oBA);
	}
	else
	{
		this.renderEmptyProposalMessage("There are no Billing Accounts.");
	}
};

eSign_Class.prototype.renderBillingInfo = function(oAccount) 
{
	var iBillingAccountId = oAccount.finBAccountId && oAccount.finBAccountId != "null" ? oAccount.finBAccountId : 0;
	this.activeBAId = iBillingAccountId;
	console.log("renderBillingInfo :: iBillingAccountId = ",iBillingAccountId);
	
	$("#customerName").html($(".panel_title a").html());
	
	var sPhone1 = oAccount.baTn;
	var sPhone2 = oAccount.baCop;
	var sPhone3 = oAccount.baLineNo;
	var sName = oAccount.baAccountName && oAccount.baAccountName != "null" ? oAccount.baAccountName : "";
	var sAddress = oAccount.baAddress && oAccount.baAddress != "null" ? oAccount.baAddress : "";
	$("#baAccountName").val(sName);
	$("#baTn").val(sPhone1);
	$("#baCop").val(sPhone2);
	$("#baLineNo").val(sPhone3);
	$("#baAddress").val(this.utils.trim(sAddress));
	$("#prdLookupBtn").attr("billing_account_id",iBillingAccountId);
	//$("#billingAccount").show();
	if (!this.bBillingInfoEventsSet)
	{
		this.setBillingInfoEvents();
	}
};

eSign_Class.prototype.setBillingInfoEvents = function() 
{
    var that = this;
	$('.phone')
		.keypress(function(event)
			{
				console.log("keypress :: event=",event);
				var keyCode = event.keyCode;
				//alert("keyCode=" + keyCode);
				var isDigit = that.utils.isDigit(keyCode);
				//var isDot = keyCode == "46";
				//var isFunctionalKey = that.utils.isFunctionalKey(keyCode);
				console.log("keypress :: isDigit = ",isDigit);
				if (isDigit)
				{
					eSign_Data.updateEnvelope($(this),that.activeBAId);
				}
				else
				{
					event.preventDefault();
					return;					
				}
			}
		)
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
						eSign_Data.updateEnvelope($(this),that.activeBAId);
						that.toggleSubmitButton();
	        		}
	        	}
				else
				{
	        		event.preventDefault();
	        		return false;					
				}
			}
		);

	$('.validate')	
		.keyup(function(event)
			{
				that.toggleSubmitButton();
				eSign_Data.updateEnvelope($(this),that.activeBAId);
			}
		);
	
	this.bBillingInfoEventsSet = true;
};

eSign_Class.prototype.clearAccountInfo = function() 
{
	$("div.ba").removeClass("activeBA");
	$("#baNumber").val("");
	$("#baAccountName").val("");
	$("#baTn").val("");
	$("#baCop").val("");
	$("#baLineNo").val("");
	$("#baAddress").val("");
	//$("#baInfoDiv").show();
};

eSign_Class.prototype.renderAccountProducts = function(oAccount) 
{
	console.log("renderAccountProducts :: oAccount = ",oAccount);
	this.clearAllProductsFromScreen();
	var aProducts = oAccount.baProductList;
	var elTableBody = $("#esign_table .table_body .body_content");
	
	for (var i = 0, iL = aProducts.length; i < iL; i++)
	{
		var oProduct = aProducts[i];
		var aRow = [];
		var sPrdName = oProduct.productName;
		var sPrdCode = oProduct.productCode;
		var sPrdIssueNum = oProduct.productIssueNum;
		var sIssueDate = this.utils.trim(oProduct.issueDate);
		var sIssueDateDisplay = this.utils.trim(oProduct.issueDateDisplay);
		var sIssueRelatedInd = oProduct.issueRelatedInd;
		var sBilling = oProduct.monthlyBilling == "" ? "0.00" : (parseFloat(oProduct.monthlyBilling)).toFixed(2);
		var sStatus = oProduct.status;
		var sStatusColor = sStatus == "New" ? "green_color" : "warn_color";
		var sStartDate = this.utils.trim(oProduct.startDate);
		var sEndDate = this.utils.trim(oProduct.endDate);
		var sPrdValue = sPrdCode + "||" + sPrdIssueNum;
		
		aRow.push(		'<div class="fleft table_row" id="prd' + i + '" mode="product_row" index="' + i + '" prdValue="' + sPrdValue + '">');
		aRow.push(			'<div class="fleft cell prd_name">');
		aRow.push(				'<input type="text" class="productName_readonly" value="' + sPrdName + '"/>');
		aRow.push(			'</div>');
		aRow.push(			'<div class="fleft cell fwbold">' + sPrdCode + '<span class="fright ' + sStatusColor + '">' + sStatus + '</span></div>');
		
		if (sIssueRelatedInd == 'Y')
		{
			aRow.push(			'<div class="fleft cell prd_date fwbold">' + sIssueDateDisplay + '</div>');
		}
		else
		{
			aRow.push(				'<input class="customInput width40" placeholder="01/01/2012" maxlength=10 prdValue="' + sPrdValue + '" id="start_date_' + i + '" value="' + sStartDate + '" autocapitalize="off" autocorrect="off" type="text" pattern="\d/\d/\d" customType="internet_date">');
			aRow.push(				' - ');
			aRow.push(				'<input class="customInput width40" placeholder="01/01/2012" maxlength=10 prdValue="' + sPrdValue + '" id="end_date_' + i + '" value="' + sEndDate + '" autocapitalize="off" autocorrect="off" type="text" customType="internet_date">');
			aRow.push(			'</div>');
		}
		
		aRow.push(			'<div class="fleft cell">');
		aRow.push(				'$ <input class="customInput width80" prdValue="' + sPrdValue + '" id="monthlyBilling_' + i + '" index="' + i + '" value="' + sBilling + '" autocapitalize="on" autocorrect="off" prdcode="" type="number" customType="monthly_billing">');
		aRow.push(			'</div>');
		aRow.push(			'<div class="fleft cell_last">');
		aRow.push(				'<div id="eraseBtn' + i + '" index="' + i + '" prdValue="' + sPrdValue + '" class="fright ui-state-highlight ui-corner-all gadgetDeleteBtn" customType="erase_button">');
		aRow.push(					'<span class="ui-icon ui-icon-trash"></span>');
		aRow.push(				'</div>');
		aRow.push(			'</div>');
		aRow.push(		'</div>');
		
		var aProductRows = $("#esign_table div[mode='product_row']");
		//console.log("aProductRows.length = ",aProductRows.length);
		if (aProductRows.length == 0)
		{
			elTableBody.prepend(aRow.join(""));		
		}
		else
		{
			var elLastProductRow = $("#esign_table div[mode='product_row']:last");
			elLastProductRow.after(aRow.join(""));
		}
		
		this.toggleEmptyRow();
		this.setProductsTableEvents(i);
	}
	this.setTotalBillingValue();
	this.toggleSubmitButton();
	this.caller.elLoader.hide();
	this.initPreselectedProducts();
};

eSign_Class.prototype.initPreselectedProducts = function() 
{
	var aProductRows = $("#esign_table div[mode='product_row']");
	this.prdLookup.setArrayForSelectedProducts(this.activeBAId,this.iProposalId);
	for (var i = 0, iL = aProductRows.length; i < iL; i++)
	{
		var sPrdValue = $(aProductRows[i]).attr("prdValue");
		this.prdLookup.initPreselectedProduct(sPrdValue);
	}
	console.log("initPreselectedProducts : this.prdLookup.oSelectedProducts=",this.prdLookup.oSelectedProducts);

	this.setTotalBillingValue();
};

eSign_Class.prototype.setESignEvents = function() 
{	
	console.log("setESignEvents ... ");
	var that = this;
	
	if (!this.bESignEventsSet)
	{
		this.prdLookup = new PrdMultiLookUpClass(this);

		$('#prdLookupBtn')
			.click(function()
				{
					that.prdLookup.lookupPRD("eSign",that.activeBAId,that.iProposalId);
				}
			)
			.mouseup(function()
				{
					return false;
				}
			);
		
		$("#submit_eSign")
			.click(function()
				{
					that.submitESign();
				}	
			)
			.mouseup(function()
				{
					return false;
				}
			);
	
		/*
		$("#addNewBa")
			.click(function()
				{
					that.addNewAccount();
				}	
			)
			.mouseup(function()
				{
					return false;
				}
			);
		*/
		
		this.bESignEventsSet = true;
	}
	
	var aProductRows = $("#esign_table div[mode='product_row']");
	for (var i = 0, iL = aProductRows.length; i < iL; i++)
	{
		var index = $(aProductRows[i]).attr("index");
		this.setProductsTableEvents(index);
	}
	this.eSignEventsSet = true;
};

eSign_Class.prototype.addNewAccount = function() 
{
	this.clearAllProductsFromScreen();
	this.clearAccountInfo();
	this.elEmptyProposal.hide();
	this.showProposalContent();
};

eSign_Class.prototype.renderNewEnvelopTab = function() 
{
	var that = this;
    this.activeBAId = 0;
	if (this.bEmptyEnvelop)
	{	
		console.log("renderNewEnvelopTab :: BEFORE renderBillingAccount ...");
		this.renderBillingAccount();
	}
	else
	{
        var callback = function(result)
        {
            console.log("----- renderNewEnvelopTab :: calback from DwrProposalManager.getProposalByCustomerId :: result = ",result,"that.iProposalId=",that.iProposalId);
            eSign_Data.setActiveProposal(that.iProposalId);
            //console.log("callback :: that.oCallerData = ",that.oCallerData);
            var aResult = result;
            if (aResult && aResult.length > 0)
            {
                eSign_Data.setEnvelope(aResult,that.oCallerData,"esign");
                that.renderProposalContent();
            }
            else
            {
                var callbackBAInfo = function(resultBAInfo)
                {
                    console.log("callbackBAInfo :: result = ",resultBAInfo);

                    if (resultBAInfo)
                    {
                        eSign_Data.setEmptyEnvelope(that.oCallerData,resultBAInfo,"esign");
                        that.renderProposalContent();
                    }
                    else
                    {
                        console.log("renderNewEnvelopTab :: BEFORE rendering empty new envelop ...");
                        that.aEmptyRows = $("#esign_table div[class*='empty_']");
                        that.clearAllProductsFromScreen();
                        that.clearAccountInfo();
                        $("#customerName").html($(".panel_title a").html());
                        $("#envelop").show();
                        $("#billing_total_value").text("");
                        that.caller.elLoader.hide();
                        eSign_Data.setEmptyEnvelope(that.oCallerData,null,"esign");
                        that.toggleSubmitButton();
                        that.bEmptyEnvelop = true;

                        $("#esign_footer").show();

                        if (!that.bBillingInfoEventsSet)
                        {
                            that.setBillingInfoEvents();
                        }
                    }

                }
                DwrProposalManager.getNewEnvelopeBillingInfoFromSessionDWR("esign", that.caller.custId, callbackBAInfo);
            }
        }
        DwrProposalManager.getProposalByCustomerId("esign", this.caller.custId, 6, callback);
	}
	
	$("#accounts_content").empty();
	$("#emptyProposal").hide();
	$("#billingAccounts").show();
	$("#billingAccount").show();
	$("#baInfoDiv").show();
	$("#esign_footer").show();

	console.log("renderNewEnvelopTab END ...");
};

eSign_Class.prototype.renderEnvelopSelect = function(sEnvelopName) 
{
	this.elEnvelopSelect = $("#envelop select");
	var aEnvelopData = this.aEnvelopData;
	var aOptionsHTML = [];
	var sSelected = sEnvelopName == "new" ? " selected='selected'" : "";
	aOptionsHTML.push("<option value='new'" + sSelected + ">New</option>");

	$.each(aEnvelopData, function(i, oEnvelop)
		{
			var sValue = oEnvelop.id;
			var sText = oEnvelop.name;
			var sDate = oEnvelop.date;
			sSelected = sEnvelopName == sText ? " selected='selected'" : "";
			aOptionsHTML.push("<option value='" + sValue + "'" + sSelected + ">" + sText + " - " + sDate + "</option>");
		}
	);
	this.elEnvelopSelect.empty().append(aOptionsHTML.join(''));
};

eSign_Class.prototype.setEnvelopEvents = function() 
{
	var that = this;
	this.elEnvelopSelect
		.change(function()
			{
				var sEnvelopId = $(this).val();
				console.log("change :::: sEnvelopId = ",sEnvelopId);
				that.sEnvelopId = sEnvelopId;
				that.renderEnvelopData(sEnvelopId);
			}
		);
};

eSign_Class.prototype.renderEnvelopData = function(sEnvelopId) 
{
	console.log("renderEnvelopData :::::: this.oData = ",this.oData);
	if (sEnvelopId == "new")
	{
		this.clearAllProductsFromScreen();
		this.clearAccountInfo();
		$("#accounts_content").empty();		
	}
	else
	{
		var aSelectedEnvelopData = []; 
		for (var i = 0, iL = this.aEnvelopData.length; i < iL; i++)
		{
			if (this.aEnvelopData[i].id == sEnvelopId)
			{
				aSelectedEnvelopData = this.aEnvelopData[i].bas;
			}
			continue;
		}
		this.oData = aSelectedEnvelopData;
		this.renderBillingAccounts();
		this.renderBillingAccount();
	}
};

eSign_Class.prototype.clearAllProductsFromScreen = function() 
{
	$("#esign_table div[mode='product_row']").remove();
	this.showAllEmptyRows();
	$("#esign_table").removeClass("with_scroller").show();
};

eSign_Class.prototype.clearAllProducts = function() 
{
	var aProductRows = $("#esign_table div[mode='product_row']");
	for (var i = 0, iL = aProductRows.length; i < iL; i++)
	{
		var index = $(aProductRows[i]).attr("index");
		this.prdLookup.removePRD(index);
	}
	this.showAllEmptyRows();
	$("#esign_table").show();
};

eSign_Class.prototype.showAllEmptyRows = function() 
{
	$("#esign_table div[class*='empty_']:hidden").show();
};

eSign_Class.prototype.showBADataNotValid = function(aBA)
{
	var that = this;
	this.oModalDialog.renderTitle("Data not completed",{});
	var sSuffics = aBA && aBA.length <= 1 ? "" : "s";
	var sMsg = "Please fill all mandatory data for Billing Account" + sSuffics + ": <strong>" + aBA.join(", ") + "</strong>";
	var aBodyHTML = [];
	aBodyHTML.push(	'<div id="modal_body">');
	aBodyHTML.push(		'<div id="warn_text">' + sMsg + '</div>');
	aBodyHTML.push(		'<div id="footer">');
	aBodyHTML.push(			'<div class="ui-state-active ui-corner-all4 fleft btn-icon" id="modal_ok">');
	aBodyHTML.push(				'<span class="ui-icon ui-icon-check"></span> OK</div>');
	aBodyHTML.push(		'</div>');
	
	aBodyHTML.push(	'</div>');
	this.oModalDialog.renderBody(aBodyHTML);
	
	var oDialogStyle = {top:"260px",left:"310px",width:"400px",height:"auto",zIndex:5};
	this.oModalDialog.showDialog(oDialogStyle);
	
	$('#modal_ok')
		.click(function()
			{
				that.oModalDialog.hideDialog();
			}
		);	
};

eSign_Class.prototype.validateAllBAs = function() 
{
	var oValidateAllBAs = {bool:true,aBA:[],aBADisplay:[]};
	var aBAs = eSign_Data.getBAs();
	for (var i = 0, iL = aBAs.length; i < iL; i++)
	{
		var oBA = aBAs[i];
		var aProducts = oBA.baProductList;
		var jL = aProducts.length;
		var validInternetDates = this.validateInternetDates(oBA);
		var validMonthlyBilling = true;
		if (jL > 0)
		{
			for (var j = 0; j < jL; j++)
			{
				if (aProducts[j].monthlyBilling === "")
				{
					console.log("aProducts[" + j + "].monthlyBilling = ",aProducts[j].monthlyBilling);
					validMonthlyBilling = false;
					continue;
				}
			}
		}
		console.log("........ oBA = ",oBA,"validInternetDates=",validInternetDates );
		
		// *** PHONE NUMBER ||  ACCOUNT ADDRESS || ACCOUNT NAME || PRODUCTS
		if (oBA.baCop.length < 3 || oBA.baTn.length < 3 || oBA.baLineNo.length < 4 || oBA.baAddress == "" || oBA.baAccountName == "" || jL == 0 || !validMonthlyBilling || !validInternetDates)
		{
			console.log("validMonthlyBilling=",validMonthlyBilling);
			oValidateAllBAs.bool = false;
			var sBADisplay = oBA.finBAccountId == 0 ? "New Billing Account" : oBA.finBAccountId;
			oValidateAllBAs.aBA.push(oBA.finBAccountId);
			oValidateAllBAs.aBADisplay.push(sBADisplay);
		}
	}
	return oValidateAllBAs;
};

eSign_Class.prototype.submitESign = function() 
{
    // *** CALL TO UPDATE CURRENT INFO FIELD TO WORKAROUND COPY/PASTE ISSUE
	eSign_Data.updateEnvelopeFromCurrentInfo(this.activeBAId);
	var oValidateAllBAs = this.validateAllBAs();
    if (!oValidateAllBAs.bool)
    {
		this.showBADataNotValid(oValidateAllBAs.aBADisplay);    	
    }
    else
    {
		var that = this;
	    //$("#loader").show();
	    var sCustId = this.custId || 0;
	   
		var oTitleStyle = {"margin-top":"0px"};
		this.oModalDialog.renderTitle("eSign",oTitleStyle);
	
		var aBodyHTML = [];
		aBodyHTML.push(	'<div id="modal_body">');
		
		aBodyHTML.push(		'<div id="status_div">' + this.utils.MESSAGES.generatingContract + '</div>');
		aBodyHTML.push(		'<div id="esign_pdf" class="pdf_div scroller_native loader">');
		aBodyHTML.push(			'<div id="img_div"></div>');
		aBodyHTML.push(		'</div>');
	
		aBodyHTML.push(		'<div id="esign_form">');
		aBodyHTML.push(			'<div class="row"><div class="label">In-person Signature</div><div class="value"><input type="checkbox" checked id="inPersonSignature" /></div></div>');
		aBodyHTML.push(			'<div class="row">');
		aBodyHTML.push(				'<div class="label">Contact Email <span class="mandatory">*</span></div>');
		aBodyHTML.push(				'<div class="value">');
		aBodyHTML.push(					'<input type="email" class="width70" id="customerEmailInput" value="' + this.contactEmail + '" />');
		aBodyHTML.push(				'</div>');
		aBodyHTML.push(			'</div>');
		aBodyHTML.push(			'<div class="row">');
		aBodyHTML.push(				'<div class="label">Contact Name <span class="mandatory">*</span></div>');
		aBodyHTML.push(				'<div class="value">');
		aBodyHTML.push(					'<input type="text" class="width70" id="contactNameInput" value="' + this.contactName + '" />');
		aBodyHTML.push(				'</div>');
		aBodyHTML.push(			'</div>');
		aBodyHTML.push(		'</div>');
	
		aBodyHTML.push(		'<div id="footer">');
		aBodyHTML.push(			'<div class="ui-state-active ui-corner-all4 fleft btn-icon none" id="modal_ok">');
		aBodyHTML.push(				'<span class="ui-icon ui-icon-check"></span>Send to DocuSign</div>');
		aBodyHTML.push(			'<div class="ui-state-passive ui-corner-all4 fleft btn-icon" id="modal_ok_passive">');
		aBodyHTML.push(				'<span class="ui-icon ui-icon-check"></span>Send to DocuSign</div>');
		aBodyHTML.push(			'<div class="ui-state-highlight ui-corner-all4 fleft btn-icon none" id="modal_cancel">');
		aBodyHTML.push(				'<span class="ui-icon ui-icon-cancel"></span>Cancel</div>');
		aBodyHTML.push(			'<div class="ui-state-passive ui-corner-all4 fleft btn-icon" id="modal_cancel_passive">');
		aBodyHTML.push(				'<span class="ui-icon ui-icon-cancel"></span>Cancel</div>');
		aBodyHTML.push(		'</div>');
		
		aBodyHTML.push(	'</div>');
		this.oModalDialog.renderBody(aBodyHTML);
	
		var sModalW = this.isiPad ? "641px" : "657px";
		var oDialogStyle = {top:"50px",left:"190px",width:sModalW,height:"auto",zIndex:5};
		this.oModalDialog.showDialog(oDialogStyle);
		
		$("#customerEmailInput, #contactNameInput")
			.keyup(function() 
				{
					that.toggleSendToDocusignBtn();
				}
			);
			
		
		$('#modal_ok')
			.click(function()
				{
					that.elLoader.show();
					var callback = function(result)
					{
						console.log("result = ",result,"inPersonInd = ",inPersonInd);
						var status = result.status;
						if (status == "Success")
						{
							if (inPersonInd)
							{
								var sURL = result.result;
								if (sURL != "")
								{
									window.location.href = sURL;
								}	
								else
								{
									console.log("sendDocumentToSignDWR : callback :: sURL IS EMPTY :: result = ",result);
                                    var sErrorMsg = result.errorMessage && result.errorMessage != "" ? result.errorMessage : "A system problem has occurred that will require you to contact the Business Support Center (BSC) for assistance.  Please reference this error: DOCUSIGN_ERROR.";
                                    alert(sErrorMsg);
                                    that.oModalDialog.hideDialog();
                                    that.elLoader.hide();
								}
							}
							else
							{
								var docuSignId = result.result;
								if (docuSignId != "")
								{
									that.showNotInPersonConfirmation(docuSignId);
								}
								else
								{
									console.log("sendDocumentToSignDWR : callback :: docuSignId IS EMPTY :: result = ",result);
                                    var sErrorMsg = result.errorMessage && result.errorMessage != "" ? result.errorMessage : "A system problem has occurred that will require you to contact the Business Support Center (BSC) for assistance.  Please reference this error: DOCUSIGN_ERROR.";
                                    alert(sErrorMsg);
                                    that.oModalDialog.hideDialog();
                                    that.elLoader.hide();
								}
							}
						}
                        else
                        {
                            var sErrorMsg = result.errorMessage && result.errorMessage != "" ? result.errorMessage : "A system problem has occurred that will require you to contact the Business Support Center (BSC) for assistance.  Please reference this error: DOCUSIGN_ERROR.";
                            alert(sErrorMsg);
                            that.oModalDialog.hideDialog();
                            that.elLoader.hide();
                        }
					}
					
					var requestId = that.requestId + "";
					var contactName = $("#contactNameInput").val();
					var contactEmail = $("#customerEmailInput").val();
					var inPersonInd = $("#inPersonSignature").is(":checked");
					console.log("BEFORE SendToDocusign : requestId = ",requestId, "contactName = ",contactName,"contactEmail = ",contactEmail,"inPersonInd = ",inPersonInd);
					
					DwrSignatureManager.sendDocumentToSignDWR(requestId, contactName, contactEmail, inPersonInd, callback);
				}
			);
	
		$('#modal_cancel')
			.click(function()
				{
					that.oModalDialog.hideDialog();
				}
			);
		
		this.getPDFFromAdocs();
    }
};

eSign_Class.prototype.getPDFFromAdocs = function() 
{
	var that = this;
	this.isPDFImagesRendered = false;
	var oEnvelop = eSign_Data.getEnvelope();
	console.log("getPDFFromAdocs :: BEFORE call for generateContractDWR ::: oEnvelop = ",oEnvelop);
	var callback = function(result)
	{
		if (result)
		{
			console.log("getPDFFromAdocs callback :: result = ", result);
			that.requestId = result.requestId;
			if (result.status == "Error")
			{
				alert(result.errorMessage);
				that.oModalDialog.hideDialog();
			}
			else
			{
				var sPDFUrl = result.result;
				if (sPDFUrl == "")
				{
					console.log("getPDFFromAdocs callback :: result.result = ", sPDFUrl);
					that.callGetContractPdfPathDWR(result.requestId, result.envelopeId);
				}
				else
				{
					that.callConvertPDF(sPDFUrl);				
				}
			}
		}
		else
		{
			console.log("getPDFFromAdocs :: callback : no result");
		}
	}
	DwrPdfGeneratorManager.generateContractDWR(oEnvelop,callback);	
};

eSign_Class.prototype.callGetContractPdfPathDWR = function(requestId, envelopeId) 
{
	var that = this;
	var iAttempts = 0;
    var iMaxAttempts = this.utils.ADOCS.iMaxAttempts;
    var iInterval = this.utils.ADOCS.iInterval;
	//var dNow = new Date();
	console.log("callGetContractPdfPathDWR :: BEFORE call to DwrPdfGeneratorManager.getContractPdfPathDWR");
	var interval = window.setInterval(
		function()
		{
			var callback = function(result)
			{
				if (result.status == "Error")
                {
                    window.clearInterval(interval);
                    alert(that.utils.MESSAGES.generateContractError);
                    that.oModalDialog.hideDialog();
                }
                else
                {
                    var sPDFUrl = result.result;
                    if (sPDFUrl == "")
                    {
                        if (iAttempts < iMaxAttempts)
                        {
                            iAttempts++;
                            console.log("callGetContractPdfPathDWR attepmpt = ",iAttempts," callback :: result.result = ", sPDFUrl);
                            return;
                        }
                        else
                        {
                            window.clearInterval(interval);
                            alert(that.utils.MESSAGES.generateContractError);
                            that.oModalDialog.hideDialog();
                        }
                    }
                    else
                    {
                        $("#status_div").text(that.utils.MESSAGES.requestingImage);
                        window.clearInterval(interval);
                        that.callConvertPDF(sPDFUrl);
                    }
                }
			}
			DwrPdfGeneratorManager.getContractPdfPathDWR(requestId, envelopeId, callback);
		}
		,iInterval
	);
};

eSign_Class.prototype.callConvertPDF = function(url) 
{
	var that = this;
	console.log("callConvertPDF :: url = ",url);
	var callback = function(result)
	{
		$("#status_div").text(that.utils.MESSAGES.loadingImage);
		aResult = eval('(' + result + ')');
		console.log("callConvertPDF callback :: aResult = ",aResult," length = ",aResult.length);
		
		if (aResult && aResult.length > 0)
		{
			for (var i = 0, iL = aResult.length; i < iL; i++)
			{
				var elImg = $('<img id="img_pdf" src="tmp/' + aResult[i] + '">').appendTo($("#img_div"));
			}
			
			$("#esign_pdf").removeClass("loader");	
			$("#modal_cancel").show();
			$("#modal_cancel_passive").hide();
			that.isPDFImagesRendered = true;
			setTimeout(function()
					{
						$("#esign_pdf").removeClass("loader");
						$("#status_div").hide();
						setTimeout(function()
							{
								//$("#img_pdf").css({width:"921px",height:"421px"});
								//that.er_pdf = new TouchScroll(document.querySelector("#esign_pdf"), {}); 
								//that.scroller_pdf.setupScroller();
						        //that.scroller_pdf.scrollTo(0, 0);
						        that.toggleSendToDocusignBtn();
							},10);
					}
					,1000);
		}
		else
		{
			alert(that.utils.MESSAGES.convertPDFError);
			that.oModalDialog.hideDialog();
		}
	}
		
	//DwrPDFConverter.convertPDF("pdf/2011/9/27/10652791.pdf",callback);
	DwrPDFConverter.convertPDF(url,callback);
};

eSign_Class.prototype.toggleSendToDocusignBtn = function() 
{
	var bValid = false;
	// *** EMAIL VALIDATION
	var eMail = $("#customerEmailInput").val();
	bValidEMail = eMail != "" && this.utils.validateEmail(eMail)
	
	// *** CUSTOMER NAME
	var contactName = $("#contactNameInput").val();
	bValidCustomer = contactName != "";

	bValid = bValidEMail && bValidCustomer && this.isPDFImagesRendered;
	if (bValid)
	{
		$("#modal_ok_passive").hide();
		$("#modal_ok").show();
		//$("#modal_cancel_passive").hide();
		//$("#modal_cancel").show();
	}
	else
	{
		$("#modal_ok_passive").show();
		$("#modal_ok").hide();		
	}
};

eSign_Class.prototype.showNotInPersonConfirmation = function(docuSignId)
{
	this.oModalDialog.hideDialog();
	var that = this;
	this.oModalDialog.renderTitle("DocuSign",{});
	var sMsg = this.utils.MESSAGES.notInPersonDocuSignConfirm;

	var aBodyHTML = [];
	aBodyHTML.push(	'<div id="modal_body">');
	aBodyHTML.push(		'<div id="warn_text">' + sMsg + '</div>');
	aBodyHTML.push(		'<div id="footer">');
	aBodyHTML.push(			'<div class="ui-state-active ui-corner-all4 fleft btn-icon" id="confirm_ok">');
	aBodyHTML.push(				'<span class="ui-icon ui-icon-check"></span>OK </div>');
	aBodyHTML.push(		'</div>');
	
	aBodyHTML.push(	'</div>');
	this.oModalDialog.renderBody(aBodyHTML);
	var oDialogStyle = {top:"150px",left:"295px",width:"550px",height:"auto",zIndex:5};
	
	that.oModalDialog.showDialog(oDialogStyle);
	that.elLoader.hide();
	
	$('#confirm_ok')
		.click(function()
			{
				that.oModalDialog.hideDialog();
				var state = that.caller.getPageState();
				window.location.href = "customerList.htm?state=" + state;
			}
		);		
};

eSign_Class.prototype.submitRemoteSign = function() 
{
    var that = this;
    //$("#loader").show();
    var sCustId = this.custId || 0;
    var selectedProducts = this.prdLookup.getSelectedProducts();
    console.log("submitRemoteSign TODO :: selectedProducts = ",selectedProducts)	
    window.location.href = "https://www.docusign.net/member/MemberLogin.aspx";
};

eSign_Class.prototype.handleSelectedPRD = function(i,$el)
{
    //console.log("handleSelectedPRD :: i = ", i , "$el = ",$el);
    var aRow = [];
    var sPrdName = $el.attr("prdName");
    var sPrdCode = $el.attr("prdCode");
    var sPrdValue = $el.attr("prdValue");
    var sIssueNum = $el.attr("productIssueNum");
    var sIssueRelatedInd = $el.attr("issueRelatedInd");
    var issueDate = this.utils.trim($el.attr("issueDate"));
    var issueDateDisplay = this.utils.trim($el.attr("issueDateDisplay"));

    var oProduct =
    {
        endDate: ""
        ,esignBAccountId: 0
        ,esignEnvelopeId: 0
        ,issueDate: issueDate
        ,issueDateDisplay: issueDateDisplay
        ,issueRelatedInd: sIssueRelatedInd
        ,productIssueNum:sIssueNum
        ,monthlyBilling: 0
        ,productCode: sPrdCode
        ,productName: sPrdName
        ,productSource: "ESGN"
        ,repId: ""
        ,startDate: ""
        ,status: "New"
    }

    var elTableBody = $("#esign_table .table_body .body_content");

    aRow.push(		'<div class="fleft table_row" id="prd' + i + '" mode="product_row" index="' + i + '" prdValue="' + sPrdValue + '">');
    aRow.push(			'<div class="fleft cell prd_name">');
    aRow.push(				'<input type="text" class="productName_readonly" value="' + sPrdName + '"/>');
    aRow.push(			'</div>');
    aRow.push(			'<div class="fleft cell">' + sPrdCode + '<span class="fright green_color">New</span></div>');

    if (sIssueRelatedInd == 'Y')
    {
        aRow.push(		'<div class="fleft cell prd_date">' + issueDateDisplay + '</div>');
    }
    else
    {
        //aRow.push(			'<div class="fleft cell prd_date">' + sStartDate + ' - ' + sEndDate + '</div>');
        aRow.push(		'<div class="fleft cell prd_date">');
        aRow.push(			'<input class="customInput width40" placeholder="01/01/2012" maxlength=10 prdValue="' + sPrdValue + '" id="start_date_' + i + '" value="" autocapitalize="off" autocorrect="off" type="text" pattern="[0-9]*" customType="internet_date">');
        aRow.push(			' - ');
        aRow.push(			'<input class="customInput width40" placeholder="02/01/2012" maxlength=10 prdValue="' + sPrdValue + '" id="end_date_' + i + '" value="" autocapitalize="off" autocorrect="off" type="text" customType="internet_date">');
        aRow.push(		'</div>');
    }

    aRow.push(			'<div class="fleft cell">');
    aRow.push(				'$ <input class="customInput width80" prdValue="' + sPrdValue + '" id="monthlyBilling_' + i + '" index="' + i + '" value="0.00" autocapitalize="off" autocorrect="off" prdcode="" type="number" customType="monthly_billing">');
    aRow.push(			'</div>');
    aRow.push(			'<div class="fleft cell_last">');
    aRow.push(				'<div id="eraseBtn' + i + '" prdValue="' + sPrdValue + '" index="' + i + '" class="fright ui-state-highlight ui-corner-all gadgetDeleteBtn" customType="erase_button">');
    aRow.push(					'<span class="ui-icon ui-icon-trash"></span>');
    aRow.push(				'</div>');
    aRow.push(			'</div>');
    aRow.push(		'</div>');

    var aProductRows = $("#esign_table div[mode='product_row']");
    //console.log("aProductRows.length = ",aProductRows.length);
    if (aProductRows.length == 0)
    {
        elTableBody.prepend(aRow.join(""));
    }
    else
    {
        var elLastProductRow = $("#esign_table div[mode='product_row']:last");
        elLastProductRow.after(aRow.join(""));
    }

    this.toggleEmptyRow();
    this.setProductsTableEvents(i);
    this.setTotalBillingValue();
    eSign_Data.updateEnvelope(null,this.activeBAId,"addProduct",oProduct);
    this.toggleSubmitButton();
};

eSign_Class.prototype.toggleEmptyRow = function()
{
	var aProductRows = $("#esign_table div[mode='product_row']");
	var aVisibleTableRows = $("#esign_table .table_body div.table_row:visible");
	console.log("toggleEmptyRow :: aVisibleTableRows.length = ",aVisibleTableRows.length, " :: this.aEmptyRows.length = ",this.aEmptyRows.length)
	if (aVisibleTableRows.length < this.aEmptyRows.length)
	{
		this.showOneEmptyRow();
	}
	else
	{
		if (aProductRows.length <= this.aEmptyRows.length)
		{
			console.log("toggleEmptyRow :: aProductRows.length = ",aProductRows.length, " :: this.aEmptyRows.length = ",this.aEmptyRows.length)
			this.toggleTableStyle("remove");
			this.hideOneEmptyRow();
		}
		else
		{
            this.toggleTableStyle("add");
        }
    }
};

eSign_Class.prototype.toggleTableStyle = function(sType)
{
    var sClassName = this.isiPad ? "with_iPad_scroller" : "with_scroller";
    if (sType == "add")
    {
        // *** ADD STYLE
        $("#esign_table").addClass(sClassName);
    }
    else
    {
        // *** REMOVE STYLE
        $("#esign_table").removeClass(sClassName);
    }
};

eSign_Class.prototype.showOneEmptyRow = function()
{
	console.log("showOneEmptyRow ......... ");
	$("#esign_table div[class*='empty_']:hidden:first").show();
};

eSign_Class.prototype.hideOneEmptyRow = function()
{
	$("#esign_table div[class*='empty_']:visible:first").hide();
};

eSign_Class.prototype.setProductsTableEvents = function(i)
{
    var that = this;
	$('#eraseBtn' + i)
		.click(function() 
			{
				console.log("click ... trash ...");
				var index = $(this).attr("index");
				that.prdLookup.removePRDbyIndex(index);
				that.prdLookup.removeSelectedPrd(index);
				eSign_Data.updateEnvelope($(this),that.activeBAId);
				that.toggleSubmitButton();
			}
		)
		.mouseup(function()
			{
				return false;
			}
		);	
	
	$('#monthlyBilling_' + i)
		.keypress(function(event)
			{
	        	var keyCode = event.keyCode;
	        	var keyChar = String.fromCharCode(keyCode);
				var id = $(this).attr("id");
				var maxLength = $(this).attr("maxLength");
				var sVal = $(this).val() + "";
				//var isDigit = that.utils.isDigit(keyCode);
	        	var isDigit = that.utils.isDigitFromChar(keyChar);
	        	var isDot = keyChar == ".";
        		aDots = sVal.match(/\./g);
	        	console.log("keypress :: keyChar = " + keyChar + "\nisDigitFromChar = " + isDigit + "\nval = " + sVal + "\nlength = " + sVal.length);
	        	if (!isDigit)
	        	{
	        		if (!isDot || (isDot && (aDots && aDots.length > 0)))
	        		{
	        			return false;
	        		}
	        	}
			}
		)

		/*
		.keydown(function(event) 
			{
        		var keyCode = event.keyCode;
        		var sVal = $(this).val();
        		var isDotExists = /\./.test(sVal);
				
        		var isDigit = that.utils.isDigit(keyCode);
        		var isDot = keyCode == 190 && !isDotExists;
        		var isFunctionalKey = that.utils.isFunctionalKey(keyCode);
        		if (!isDigit && !isDot && !isFunctionalKey)
				{
        			console.log("keydown :: false - isDot=",isDot);
        			event.preventDefault();
					return;
				}
			}
		)
		*/
		.keyup(function(event) 
			{
        		var keyCode = event.keyCode;
        		var keyChar = String.fromCharCode(keyCode);
        		var sVal = $(this).val();
        		//console.log("keyup :: sVal = ",sVal)
        		var isDot = keyChar == ".";
        		aDots = sVal.match(/\./g);
        		var isOneDot = aDots && aDots.length == 1;
				
        		//var isDigit = that.utils.isDigit(keyCode);
        		var isDigit = that.utils.isDigitFromChar(keyChar);
        		var isRemoveKey = that.utils.isRemoveKey(keyCode);
        		
        		//var isFunctionalKey = that.utils.isFunctionalKey(keyCode);
        		if (isDigit || isRemoveKey || isOneDot)
				{
					console.log("keyup :: true - isOneDot=",isOneDot);
					eSign_Data.updateEnvelope($(this),that.activeBAId);
					that.setTotalBillingValue();
					that.toggleSubmitButton();
				}
				else
				{
					//console.log("keyup :: false - isOneDot=",isOneDot);
					event.preventDefault();
					return;
				}
			}
		);
	
	$("input[customType='internet_date']")
		.keyup(function() 
			{
				eSign_Data.updateEnvelope($(this),that.activeBAId);
				that.toggleSubmitButton();
			}
		);
};

eSign_Class.prototype.setTotalBillingValue = function()
{
	var aMonthlyBilling = $("input[id^='monthlyBilling_']");
	var iTotalValue = 0;
	var iL = aMonthlyBilling.length;
	
	if (iL > 0)
	{
		aMonthlyBilling
			.each(function(i)
				{
					var sValue = $(this).val();
					var iValue = sValue == "" || sValue == "." ? 0 : parseFloat(sValue);
					console.log("i :: ",i," : sValue = ",sValue," : iValue = ",iValue);
					iTotalValue += iValue;
				}
			);
		$("#billing_total_value").html("$" + iTotalValue.toFixed(2));
	}
	else
	{
		$("#billing_total_value").html("");
	}	
};

eSign_Class.prototype.toggleSubmitButton = function()
{
	var bValidateMandatory = this.validateMandatoryFields();
	var bValidateBillingAmounts = this.validateBillingAmounts();
	var bValidateInternetProducts = this.validateInternetProducts();
	console.log("toggleSubmitButton :: bValidateBillingAmounts = ",bValidateBillingAmounts,"bValidateMandatory = ",bValidateMandatory,"bValidateInternetProducts=",bValidateInternetProducts);
	if (bValidateMandatory && bValidateBillingAmounts && bValidateInternetProducts)
	{
		$("#submit_eSign_passive").hide();
		$("#submit_eSign").show();
		return true;
	}
	else
	{
		$("#submit_eSign").hide();
		$("#submit_eSign_passive").show();
		return false;
	}	
};

eSign_Class.prototype.validateMandatoryFields = function()
{
	// *** PHONE NUMBER
	var sCOP = $("#baCop").val();
	var sNAP = $("#baTn").val();
	var sLineNo = $("#baLineNo").val();
	if (sCOP.length < 3 || sNAP.length < 3 || sLineNo.length < 4)
	{
		return false;
	}

	// *** ACCOUNT ADDRESS & NAME 
	if ($("#baAddress").val() == "" || $("#baAccountName").val() == "")
	{
		return false;
	}
	
	return true;
};

eSign_Class.prototype.validateBillingAmounts = function()
{
	var aProductRows = $("#esign_table div[mode='product_row']");
	var iL = aProductRows.length;
	if (iL > 0)
	{
		$("#add_products_asterisk").hide();
		for (var i = 0; i < iL; i++)
		{
			var sIndex = $(aProductRows[i]).attr("index");
			var sBillingValue = $("#monthlyBilling_" + sIndex).val();
			
			if (sBillingValue == "")
			{
				return false;
			}
		}
	}
	else
	{
		$("#add_products_asterisk").show();
		return false;
	}
	return true;
};

eSign_Class.prototype.validateInternetDates = function(oBA)
{
	// *** DATE VALIDATION
	var reDateFormat = /\d{2}\/\d{2}\/\d{4}/;
	var bDate = true;
	var aProducts = oBA.baProductList;
	var iL = aProducts.length;
	if (iL > 0)
	{
		for (var i = 0; i < iL; i++)
		{
			var oProduct = aProducts[i];
			var isInternetProduct = oProduct.issueRelatedInd == "N";
			var startDate = oProduct.startDate;
			var endDate = oProduct.endDate;
			if (isInternetProduct)
			{
				if (startDate == "" || endDate == "" || !reDateFormat.test(startDate) || !reDateFormat.test(endDate))
				{
					return false;
				}
			}
		}
	}
	return bDate;
};

eSign_Class.prototype.validateInternetProducts = function()
{
	// *** DATE VALIDATION
	var reDateFormat = /\d{2}\/\d{2}\/\d{4}/;
	
	var bStartDate = true;
	$("input[customType='internet_date']")
		.each(function(i)
			{
				var val = $(this).val();
				if (val == "" || !reDateFormat.test(val))
				{
					bStartDate = false;
					return false;
				}
			}
		);
	return bStartDate;
	//return $("input[id^='start_date_']").val() != "" && $("input[id^='end_date_']").val() != "";
};

