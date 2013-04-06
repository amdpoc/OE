// *** Created by okotlia 01/03/2012

RecordOfPayment_Class = function(caller)
{
	this.caller = caller;
	this.init();
};

RecordOfPayment_Class.prototype.init = function()
{
	this.ROPEventsSet = false;
	this.prevPage = prevPage;
    this.oModalDialog = modalDialog;
    this.contactName = contactName;
    this.contactEmail = contactEmail;
    this.elMask = $("#mask");
    this.elLoader = $("#loader");
    this.elCustomerInfo = $("#customerInfoDiv");
    this.elEmptyProposal = $("#emptyProposal");
    this.bROPEventsSet = false;
    this.bBillingInfoEventsSet = false;
	this.bEmptyEnvelop = false;
	this.isPDFImagesRendered = false;
	this.iProposalId = this.caller.proposalIDrecord != 0 ? this.caller.proposalIDrecord : 1;
	this.isiPad = navigator.userAgent.match(/iPad/i) != null;

    this.utils = new UtilsClass();
    this.proposalsTopBarModule = new ProposalsTopBarModule_Class(this);
    this.oGeneralInfo = generalInfo;

    this.setEvents();

	this.oCallerData =
	{
		custId:parseInt(this.caller.custId,10),
		businessName:this.utils.decodeHTMLEntities(this.caller.businessName),
		contactEmail:contactEmail,
		contactTitle:this.caller.contactTitle,
		contactName:contactName
	}

	console.log("ROP_Class.prototype.init :: this.oCallerData=",this.oCallerData);
};

RecordOfPayment_Class.prototype.savePreviousProposalToSession = function(callbackFromCaller)
{
    var aPrevProposalProducts = ROP_Data.getProductsList(this.iProposalId);
    console.log("savePreviousProposalToSession :: this.proposalId = ",this.iProposalId,"this.custId = ",this.oCallerData.custId,"aPrevProposalProducts=",aPrevProposalProducts);

    if (this.iProposalId == 6 && aPrevProposalProducts.length == 0)
    {
        // *** NEW ENVELOPE CASE WITH NO PRODUCTS ADDED
        var oBA = ROP_Data.getNewEnvelopeBillingInfo();
        var callback = function(result)
        {
            if (callbackFromCaller)
            {
                callbackFromCaller.apply(this);
            }
            console.log("callback AFTER saveNewEnvelopeNoProductsToSessionDWR :: result = ",result);
        }
        DwrProposalManager.saveNewEnvelopeNoProductsToSessionDWR("record", this.oCallerData.custId,oBA,callback);
    }
    else
    {
        var callback = function(result)
        {
            if (callbackFromCaller)
            {
                callbackFromCaller.apply(this);
            }
            console.log("callback AFTER saveProposalProductsListToSessionDWR :: result = ",result);
        }
        DwrProposalManager.saveProposalProductsListToSessionDWR("record", this.oCallerData.custId,this.iProposalId,aPrevProposalProducts,callback);
    }
};

RecordOfPayment_Class.prototype.setEvents = function(type)
{
    var that = this;

    $("#cancelROP")
        .click(function()
        {
            var state = that.caller.getPageState();
            var sDelimiter = that.prevPage.indexOf("?") > 0 ? "&" : "?"
            var URL = that.prevPage + sDelimiter + "state=" + state;
            that.showConfirmCancelAction(URL);
        }
    );
};

RecordOfPayment_Class.prototype.renderROPTab = function(aData)
{
	if (aData)
	{
		console.log("renderROPTab :: aData = ",aData);
		this.renderProposal(aData)
	}
	else
	{
		this.renderProposal();
	}
};

RecordOfPayment_Class.prototype.highlightActiveTab = function(sType)
{
    $('.topTab').removeClass("activeTab");
    if (!sType)
    {
        console.log("................. highlightActiveTab .................");
        var sProposal =  this.iProposalId + "";
        switch (sProposal)
        {
            case "1":
                this.sType = "proposalA";
                break;
            case "2":
                this.sType = "proposalB";
                break;
            case "3":
                this.sType = "proposalC";
                break;
            case "4":
                this.sType = "proposalD";
                break;
            case "5":
                this.sType = "pendingPublish";
                break;
            case "6":
                this.sType = "newEnvelop";
                break;
            default:
                break;
        }
    }
    var sTypeID = sType ? sType : this.sType;
    $("a[type='" + sTypeID + "']").addClass("activeTab");
};

RecordOfPayment_Class.prototype.renderProposal = function(aData)
{
	this.caller.elLoader.show();
	var that = this;
    ROP_Data.setActiveProposal(this.iProposalId);
    console.log("&&&&&&&&&&&&& renderProposal :: this.iProposalId = ",this.iProposalId, "ROP_Data.getEnvelope()=",ROP_Data.getEnvelope(),"eSign_Data.getEnvelope()=",eSign_Data.getEnvelope());
    this.proposalsTopBarModule.init();
    this.proposalsTopBarModule.setProposalId(this.iProposalId)
	this.proposalsTopBarModule.highlightActiveTab();
	console.log("renderProposal :: aData=",aData);
	if (aData && this.caller.enableTransFlag)
	{
		console.log("renderProposal :: BEFORE createEnvelope ...");
		ROP_Data.setEnvelope(aData,this.oCallerData,"rop");

   		if (aData.length > 0)
   		{
   			that.renderProposalContent();
   		}
   		else
   		{
   			that.renderEmptyProposalMessage();
   			that.prdLookup.setMode("record");
   		}
	}
	else
	{
        var iActiveProposalIndex = this.iProposalId - 1;
		var oEnvelope = ROP_Data.getEnvelope();
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
					ROP_Data.setActiveProposal(that.iProposalId);
					console.log("callback :: that.oCallerData = ",that.oCallerData," ***** aResult = ",aResult);
					ROP_Data.setEnvelope(aResult,that.oCallerData,"rop");
		       		if (aResult.length > 0)
		       		{
		       			that.renderProposalContent();
		       		}
		       		else
		       		{
		       			that.renderEmptyProposalMessage();
		       			that.prdLookup.setMode("record");
		       		}
		        }
		    	else
		    	{
		            that.utils.setError("A system problem has occurred that will require you to contact the Business Support Center (BSC) for assistance.  Please reference this error: LOADING CUSTOMER BY ID." + iCustId);//error.customer.load
		            that.elLoader.fadeOut("fast");
		        }
		    };

            var iCustId = parseInt(that.caller.custId,10);
            var sType = "record";
		    if (this.caller.enableTransFlag)
		    {
			    if (this.sType == "pendingPublish")
			    {
			    	DwrProposalManager.getPendingPublishByCustomerId(sType, iCustId, callback);
			    }
			    else
			    {
			    	console.log("####### BEFORE getROPProposalByCustomerId.....");
                    DwrProposalManager.getROPProposalByCustomerId(sType, iCustId, this.iProposalId, callback);
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

RecordOfPayment_Class.prototype.renderNewEnvelopTab = function()
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
            ROP_Data.setActiveProposal(that.iProposalId);
            //console.log("callback :: that.oCallerData = ",that.oCallerData);
            var aResult = result;
            if (aResult && aResult.length > 0)
            {
                ROP_Data.setEnvelope(aResult,that.oCallerData,"rop");
                that.renderProposalContent();
            }
            else
            {
                var callbackBAInfo = function(resultBAInfo)
                {
                    console.log("callbackBAInfo :: result = ",resultBAInfo);

                    if (resultBAInfo)
                    {
                        ROP_Data.setEmptyEnvelope(that.oCallerData,resultBAInfo,"rop");
                        that.renderProposalContent();
                    }
                    else
                    {
                        console.log("renderNewEnvelopTab :: BEFORE rendering empty new envelop ...");
                        that.aEmptyRows = $("#rop_table div[class*='empty_']");
                        that.clearAllProductsFromScreen();
                        that.clearAccountInfo();
                        $("#customerName").html($(".panel_title a").html());
                        $("#envelop").show();
                        $("#billing_total_value").text("");
                        that.caller.elLoader.hide();
                        ROP_Data.setEmptyEnvelope(that.oCallerData,null,"rop");
                        that.toggleSubmitButton();
                        that.bEmptyEnvelop = true;

                        $("#rop_footer").show();

                        if (!that.bBillingInfoEventsSet)
                        {
                            that.setBillingInfoEvents();
                        }
                    }

                }
                DwrProposalManager.getNewEnvelopeBillingInfoFromSessionDWR("record", that.caller.custId, callbackBAInfo);
            }
        }
        DwrProposalManager.getProposalByCustomerId("record", this.caller.custId, 6, callback);
    }

    $("#accounts_content").empty();
    $("#emptyProposal").hide();
    //$("#billingAccounts").show();
    $("#rop_footer").show();

    console.log("renderNewEnvelopTab END ...");
};

RecordOfPayment_Class.prototype.renderEmptyProposalMessage = function(msg,options)
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
	this.setROPEvents();
};

RecordOfPayment_Class.prototype.hideProposalContent = function()
{
    //$("#billingAccounts").hide();
    $("#customer_block").hide();
    $("#rop_table").hide();
    $("#rop_footer").hide();
    this.elCustomerInfo.hide();
};

RecordOfPayment_Class.prototype.showProposalContent = function()
{
    //$("#billingAccounts").show();
    $("#customer_block").show();
    $("#rop_table").show();
    $("#rop_footer").show();
    this.elCustomerInfo.show();
};

RecordOfPayment_Class.prototype.renderProposalContent = function()
{
    this.aEmptyRows = $("#rop_table div[class*='empty_']");

    if (!this.ROPEventsSet)
    {
        this.setROPEvents();
    }
    this.prdLookup.setMode("record");
    console.log("renderProposalContent ...");

    this.renderTabContent();
    //this.toggleSubmitButton();
    this.elEmptyProposal.hide();
    this.showProposalContent();
};

RecordOfPayment_Class.prototype.setROPEvents = function()
{
    console.log("setROPEvents ... ");
    var that = this;

    if (!this.bROPEventsSet)
    {
        this.prdLookup = new PrdMultiLookUpClass(this);

        $('#rop_prdLookupBtn')
            .click(function()
                {
                    that.prdLookup.lookupPRD("record",that.activeBAId,that.iProposalId);
                }
            )
            .mouseup(function()
                {
                    return false;
                }
            );

        $("#submit_ROP")
            .click(function()
            {
                that.submitROP();
            }
        )
            .mouseup(function()
            {
                return false;
            }
        );

        this.bROPEventsSet = true;
    }

    var aProductRows = $("#rop_table div[mode='product_row']");
    for (var i = 0, iL = aProductRows.length; i < iL; i++)
    {
        var index = $(aProductRows[i]).attr("index");
        this.setProductsTableEvents(index);
    }
    this.ROPEventsSet = true;
};

RecordOfPayment_Class.prototype.submitROP = function()
{
    // *** CALL TO UPDATE CURRENT INFO FIELD TO WORKAROUND COPY/PASTE ISSUE
    //ROP_Data.updateEnvelopeFromCurrentInfo();

    var that = this;
    //$("#loader").show();
    var sCustId = this.custId || 0;

    var oTitleStyle = {"margin-top":"0px"};
    this.oModalDialog.renderTitle("Record of Payment",oTitleStyle);

    var aBodyHTML = [];
    aBodyHTML.push(	'<div id="modal_body">');

    aBodyHTML.push(		'<div id="status_div">' + this.utils.MESSAGES.generatingROP + '</div>');
    aBodyHTML.push(		'<div id="rop_pdf" class="pdf_div scroller_native loader">');
    aBodyHTML.push(			'<div id="img_div"></div>');
    aBodyHTML.push(		'</div>');

    aBodyHTML.push(		'<div id="rop_form">');
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
};

RecordOfPayment_Class.prototype.getPDFFromAdocs = function()
{
    var that = this;
    this.isPDFImagesRendered = false;
    var oEnvelop = ROP_Data.getEnvelope();
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

RecordOfPayment_Class.prototype.callGetContractPdfPathDWR = function(requestId, envelopeId)
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
                        $("#status_div").text(that.utils.MESSAGES.requestingROPImage);
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

RecordOfPayment_Class.prototype.callConvertPDF = function(url)
{
    var that = this;
    console.log("callConvertPDF :: url = ",url);
    var callback = function(result)
    {
        $("#status_div").text(that.utils.MESSAGES.loadingROPImage);
        aResult = eval('(' + result + ')');
        console.log("callConvertPDF callback :: aResult = ",aResult," length = ",aResult.length);

        if (aResult && aResult.length > 0)
        {
            for (var i = 0, iL = aResult.length; i < iL; i++)
            {
                var elImg = $('<img id="img_pdf" src="tmp/' + aResult[i] + '">').appendTo($("#img_div"));
            }

            $("#rop_pdf").removeClass("loader");
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

RecordOfPayment_Class.prototype.toggleSendToDocusignBtn = function()
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

RecordOfPayment_Class.prototype.showNotInPersonConfirmation = function(docuSignId)
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

RecordOfPayment_Class.prototype.setProductsTableEvents = function(i)
{
    var that = this;
    $('#rop_eraseBtn' + i)
        .click(function()
            {
                console.log("click ... trash ...");
                var index = $(this).attr("index");
                that.prdLookup.removePRDbyIndex(index);
                that.prdLookup.removeSelectedPrd(index);
                var iBA = $(this).attr("ba");
                ROP_Data.updateEnvelope($(this),iBA);
                that.toggleSubmitButton();
            }
        )
        .mouseup(function()
            {
                return false;
            }
        );

    $('#amount_toApply_' + i)
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
                    ROP_Data.updateEnvelope($(this),that.activeBAId);
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
            ROP_Data.updateEnvelope($(this),that.activeBAId);
            that.toggleSubmitButton();
        }
    );
};

RecordOfPayment_Class.prototype.renderTabContent = function()
{
     $("#envelop").hide();
    this.renderCustomerBlock();
    this.renderProductsTable();
};

RecordOfPayment_Class.prototype.renderCustomerBlock = function()
{
    var that = this;
    var elCustBlock = $("#customer_block");
    this.utils.showLoadingMask(elCustBlock);
    this.renderLMCBlock();
    elCustBlock.show();
    $("#customerInfoDiv").show();
    $("#rop_customerName").html($(".panel_title a").html());
    var callback = function(aResult)
    {
        console.log("DWR : renderCustomerBlock callback aResults = ",aResult);
        if (aResult)
        {
            that.renderCustAddressDetails(aResult);
            ROP_Data.setPrimaryBAAddress(aResult);
        }
        else
        {
            that.utils.setError("A system problem has occurred that will require you to contact the Business Support Center (BSC) for assistance.  Please reference this error: LOADING CUSTOMER BY ID." + that.custId);//error.customer.load
            that.elLoader.fadeOut("fast");
        }
    };
    DwrCustManager.getCustBillingDetails(that.oCallerData.custId, callback);
};

RecordOfPayment_Class.prototype.renderCustAddressDetails = function(oData)
{
    this.renderDateBlock(oData.nisdAmt);

    $("#customer_address").val(oData.street);
    $("#customer_city_value").val(oData.city);
    $("#customer_state_value").val(oData.state);
    $("#customer_zip_5").val(oData.zipFive);
    $("#customer_zip_4").val(oData.zipFour);

    this.utils.hideLoadingMask();
};

RecordOfPayment_Class.prototype.renderDateBlock = function(sNISD)
{
    console.log("renderDateBlock :: sNISD = ",sNISD);
    var d = new Date();
    var month = d.getMonth() + 1;
    var day = d.getDate();
    var year = d.getFullYear();

    $("#date_value").html("<strong>" + month + "/" + day + "/" + year + "</strong>");
    if (sNISD != "" && parseInt(sNISD) > 0)
    {
        $("#check_renewal").attr("checked","checked");
        $("#check_new").removeAttr("checked");
    }
    else
    {
        $("#check_new").attr("checked","checked");
        $("#check_renewal").removeAttr("checked");
    }
    $("#new_renewal").show();
};

RecordOfPayment_Class.prototype.renderLMCBlock = function()
{
    console.log("renderLMCBlock :: this.oGeneralInfo =", this.oGeneralInfo);
    $("#LMC_name").text(this.oGeneralInfo.employeeName);
    $("#LMC_id").text(this.oGeneralInfo.employeeId);
    $("#LMC_office").text(this.oGeneralInfo.officeName);
};

RecordOfPayment_Class.prototype.renderProductsTable = function()
{
    var aProducts = this.getProductsListFromEnvelope();
    console.log("renderProductsTable :: aProducts = ",aProducts);
    this.clearAllProductsFromScreen();
    var aWriteOff = [];
    var aPastDue = [];

    for (var i = 0, iL = aProducts.length; i < iL; i++)
    {
        var oProduct = aProducts[i];
        if (oProduct.paymentType == "A")
        {
            this.renderProductTableRow(oProduct,i);
        }
        else
        {
            if (oProduct.paymentType == "W")
            {
                aWriteOff.push(oProduct);
            }
            else
            {
                aPastDue.push(oProduct);
            }
        }
    }

    if (aWriteOff.length > 0)
    {
        this.renderAccountTableRows(aWriteOff,"writeOff");
    }

    if (aPastDue.length > 0)
    {
        this.renderAccountTableRows(aPastDue,"pastDue");
    }

    this.setTotalBillingValue();
    this.toggleSubmitButton();
    //this.caller.elLoader.hide();
    this.initPreselectedProducts();
};

RecordOfPayment_Class.prototype.renderAccountTableRows = function(aAccounts,type)
{
    console.log("renderAccountTableRows :: type = ",type, "aAccounts = ",aAccounts);
    for (var i = 0, iL = aAccounts.length; i < iL; i++)
    {
        this.renderProductTableRow(aAccounts[i],i,type);
    }
};

RecordOfPayment_Class.prototype.handleSelectedPRD = function(i,$el)
{
    console.log("handleSelectedPRD :: i = ", i , "$el = ",$el);

    var aRow = [];
    var sPrdName = $el.attr("prdName");
    var sPrdCode = $el.attr("prdCode");
    var sPrdValue = $el.attr("prdValue");
    var sIssueNum = $el.attr("productIssueNum");
    var sIssueRelatedInd = $el.attr("issueRelatedInd");
    var issueDate = this.utils.trim($el.attr("issueDate"));
    var issueDateDisplay = this.utils.trim($el.attr("issueDateDisplay"));
    var oPrimaryBA = ROP_Data.getPrimaryBA();
    console.log("handleSelectedPRD :: oPrimaryBA = ",oPrimaryBA);

    var oProduct =
    {
        endDate: ""
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

    var oProductRender = oProduct;
    oProductRender.accountNumber = oPrimaryBA.finBAccountId;
    oProductRender.telephoneNumber = oPrimaryBA.baTn ? oPrimaryBA.baTn + "-" + oPrimaryBA.baCop + "-" + oPrimaryBA.baLineNo : "";

    this.renderProductTableRow(oProduct,i);
    this.setTotalBillingValue();
    ROP_Data.updateEnvelope(null,null,"addProduct",oProductRender);
   //this.toggleSubmitButton();

};


RecordOfPayment_Class.prototype.renderProductTableRow = function(oProduct,i,type)
{
    var elTableBody = $("#rop_table .table_body .body_content");
    var aRow = [];
    var sAccountNumber = oProduct.accountNumber || "--";
    var sTelephoneNumber = oProduct.telephoneNumber || "--";
    var sPrdName = oProduct.productName == "x" ? "XXXXXXX" : oProduct.productName;
    var sPrdCode = oProduct.productCode == "x" ? "XXX" : oProduct.productCode;
    var sPrdIssueNum = oProduct.productIssueNum;
    var sIssueDate = this.utils.trim(oProduct.issueDate);
    var sIssueDateDisplay = oProduct.issueDateDisplay == "x" ? "XXXXXX" : this.utils.trim(oProduct.issueDateDisplay);
    var sIssueRelatedInd = oProduct.issueRelatedInd;
    var sMonthlyAmount = oProduct.monthlyBilling == "" ? "0.00" : (parseFloat(oProduct.monthlyBilling)).toFixed(2);
    var iNumberOfMonths = 1;
    var sMonthlyAmountToApply = sMonthlyAmount == "0.00" ? "0.00" : (parseFloat(sMonthlyAmount * iNumberOfMonths)).toFixed(2);
    var sStatus = oProduct.status;
    var sStatusColor = sStatus == "New" ? "green_color" : "warn_color";
    var sStartDate = this.utils.trim(oProduct.startDate);
    var sEndDate = this.utils.trim(oProduct.endDate);
    var sPrdValue = sPrdCode + "||" + sPrdIssueNum;

    var sID = type ? type + i : "prd" + i;
    var sMode = type ? type + "_row" : "product_row";
    var sPaymentType = i == 0 ? (type ? (type == "W" ? "W/O" : "Past Due") : "Advance") : "&nbsp;";

    var aProductRows = $("#rop_table div[mode='product_row']");
    var aWriteOffRows = $("#rop_table div[mode='writeOff_row']");
    var aPastDueRows = $("#rop_table div[mode='pastDue_row']");

    var iCorrectIndex = type ? (type == "writeOff" ? aProductRows.length + i : aProductRows.length + aWriteOffRows.length + i) : i;

    aRow.push(		'<div class="fleft table_row" id="' + sID + '" mode="' + sMode + '" index="' + i + '" prdValue="' + sPrdValue + '">');
    aRow.push(			'<div class="fleft cell type_payment"><strong>'+ sPaymentType + '</strong></div>');

    if (sStatus == "New")
    {
        aRow.push(			'<div class="fleft cell account_number">');
        aRow.push(				'<input type="text" class="customInput width95" value="' + sAccountNumber + '"/>');
        aRow.push(			'</div>');
        aRow.push(			'<div class="fleft cell phone_number">');
        aRow.push(				'<input placeholder="123-456-7890" type="text" class="customInput width97" value="' + sTelephoneNumber + '"/>');
        aRow.push(			'</div>');
    }
    else
    {
        aRow.push(			'<div class="fleft cell">' + sAccountNumber + '</div>');
        aRow.push(			'<div class="fleft cell phone_number">' + sTelephoneNumber + '</div>');
    }

    aRow.push(			'<div class="fleft cell prd_name">');
    aRow.push(				'<input type="text" class="productName_readonly" value="' + sPrdName + '"/>');
    aRow.push(			'</div>');

    if (type)
    {
        aRow.push(			'<div class="fleft cell fwbold">' + sPrdCode + '</div>');
        aRow.push(			'<div class="fleft cell prd_date start_end fwbold">XXXXXX</div>');
        aRow.push(			'<div class="fleft cell">XXX</div>');
        aRow.push(			'<div class="fleft cell num_months tcenter">XXX</div>');
    }
    else
    {
        aRow.push(			'<div class="fleft cell fwbold">' + sPrdCode + '<span class="fright ' + sStatusColor + '">' + sStatus + '</span></div>');

        if (sIssueRelatedInd == 'Y')
        {
            aRow.push(			'<div class="fleft cell prd_date start_end fwbold">' + sIssueDateDisplay + '</div>');
        }
        else
        {
            aRow.push(			'<div class="fleft cell prd_date start_end fwbold">');
            aRow.push(				'<input class="customInput width40" placeholder="01/01/2012" maxlength=10 prdValue="' + sPrdValue + '" id="start_date_' + i + '" value="' + sStartDate + '" autocapitalize="off" autocorrect="off" type="text" pattern="\d/\d/\d" customType="internet_date">');
            aRow.push(				' - ');
            aRow.push(				'<input class="customInput width40" placeholder="01/01/2012" maxlength=10 prdValue="' + sPrdValue + '" id="end_date_' + i + '" value="' + sEndDate + '" autocapitalize="off" autocorrect="off" type="text" customType="internet_date">');
            aRow.push(			'</div>');
        }

        aRow.push(			'<div class="fleft cell">$ ' + sMonthlyAmount + '</div>');
        aRow.push(			'<div class="fleft cell num_months">');
        aRow.push(				'<input class="customInput width50" prdValue="' + sPrdValue + '" id="numberMonths_' + i + '" index="' + i + '" value="'+ iNumberOfMonths + '" autocapitalize="on" autocorrect="off" prdcode="" type="number" customType="number_months" maxlength=2>');
        aRow.push(			'</div>');
    }

    aRow.push(			'<div class="fleft cell amount_toApply">');
    aRow.push(				'$ <input class="customInput width75" prdValue="' + sPrdValue + '" id="amount_toApply_' + iCorrectIndex + '" index="' + iCorrectIndex + '" value="' + sMonthlyAmountToApply + '" autocapitalize="on" autocorrect="off" prdcode="" type="number" customType="amount_toApply">');
    aRow.push(			'</div>');

    aRow.push(			'<div class="fleft cell_last">');

    if (!type)
    {
        aRow.push(				'<div id="rop_eraseBtn' + i + '" index="' + i + '" prdValue="' + sPrdValue + '" ba="' + sAccountNumber + '" class="fright ui-state-highlight ui-corner-all gadgetDeleteBtn" customType="erase_button">');
        aRow.push(					'<span class="ui-icon ui-icon-trash"></span>');
        aRow.push(				'</div>');
    }
    aRow.push(			'</div>');
    aRow.push(		'</div>');

    if (type)
    {
        switch (type)
        {
            case "writeOff":
                if (aWriteOffRows.length > 0)
                {
                    var elLastWriteOffRow = $("#rop_table div[mode='writeOff_row']:last");
                    elLastWriteOffRow.after(aRow.join(""));
                }
                else
                {
                    if (aProductRows.length == 0)
                    {
                        elTableBody.prepend(aRow.join(""));
                    }
                    else
                    {
                        var elLastProductRow = $("#rop_table div[mode='product_row']:last");
                        elLastProductRow.after(aRow.join(""));
                    }
                }
                break;
            case "pastDue":
                if (aPastDueRows.length > 0)
                {
                    var aLastPastDueRows = $("#rop_table div[mode='pastDue_row']:last");
                    aLastPastDueRows.after(aRow.join(""));
                }
                else
                {
                    if (aWriteOffRows.length > 0)
                    {
                        var elLastWriteOffRow = $("#rop_table div[mode='writeOff_row']:last");
                        elLastWriteOffRow.after(aRow.join(""));
                    }
                    else
                    {
                        if (aProductRows.length == 0)
                        {
                            elTableBody.prepend(aRow.join(""));
                        }
                        else
                        {
                            var elLastProductRow = $("#rop_table div[mode='product_row']:last");
                            elLastProductRow.after(aRow.join(""));
                        }
                    }
                }
                break;
            default:
                break;
        }
    }
    else
    {
        if (aProductRows.length == 0)
        {
            elTableBody.prepend(aRow.join(""));
        }
        else
        {
            var elLastProductRow = $("#rop_table div[mode='product_row']:last");
            elLastProductRow.after(aRow.join(""));
        }
    }

    this.toggleEmptyRow();
    this.setProductsTableEvents(iCorrectIndex);
};

RecordOfPayment_Class.prototype.initPreselectedProducts = function()
{
    var aProductRows = $("#rop_table div[mode='product_row']");
    this.prdLookup.setArrayForSelectedProducts(this.activeBAId,this.iProposalId);
    for (var i = 0, iL = aProductRows.length; i < iL; i++)
    {
        var sPrdValue = $(aProductRows[i]).attr("prdValue");
        this.prdLookup.initPreselectedProduct(sPrdValue);
    }
    console.log("initPreselectedProducts : this.prdLookup.oSelectedProducts=",this.prdLookup.oSelectedProducts);
};

RecordOfPayment_Class.prototype.setTotalBillingValue = function()
{
    var aAmountToApply = $("input[id^='amount_toApply_']");
    var aNumberMonths = $("input[id^='numberMonths_']");
    var iTotalValue = 0;
    var iL = aAmountToApply.length;

    if (iL > 0)
    {
        aAmountToApply
            .each(function(i)
            {
                var sValue = $(this).val();
                var iValue = sValue == "" || sValue == "." ? 0 : parseFloat(sValue);
                var sMonths = $(aNumberMonths[i]).val();
                var iMonths = sMonths ? (sMonths == "" || sMonths == "." ? 0 : parseInt(sMonths)) : 1;

                console.log("i :: ",i," : sValue = ",sValue," : iValue = ",iValue,"sMonths = ",sMonths," : iMonths = ",iMonths);
                iTotalValue += iMonths == 0 || iValue == 0 ? iValue : iValue * iMonths;
            }
        );
        $("#total_toApply").html("$" + iTotalValue.toFixed(2));
    }
    else
    {
        $("#billing_total_value").html("");
    }
};

RecordOfPayment_Class.prototype.getProductsListFromEnvelope = function()
{
    var oEnvelope = ROP_Data.getEnvelope();
    console.log("getProductsListFromEnvelope :: oEnvelop=",oEnvelope);

    var aProducts = [];
    var aBA = ROP_Data.getBAs();
    if (aBA.length > 0)
    {
        for (var i = 0, iL = aBA.length; i < iL; i++)
        {
            var aBAProductsList = aBA[i].baProductList;

            if (aBAProductsList.length > 0)
            {
                for (var j = 0, jL = aBAProductsList.length; j < jL; j++)
                {
                    var oProduct = {};
                    oProduct.accountNumber = aBA[i].finBAccountId;
                    oProduct.telephoneNumber = aBA[i].baTn + "-" + aBA[i].baCop + "-" + aBA[i].baLineNo;
                    oProduct.productName = aBAProductsList[j].productName;
                    oProduct.productCode = aBAProductsList[j].productCode;
                    oProduct.issueDate = aBAProductsList[j].issueDate;
                    oProduct.issueDateDisplay = aBAProductsList[j].issueDateDisplay;
                    oProduct.monthlyBilling = aBAProductsList[j].monthlyBilling;
                    oProduct.startDate = aBAProductsList[j].startDate;
                    oProduct.endDate = aBAProductsList[j].endDate;
                    oProduct.productIssueNum = aBAProductsList[j].productIssueNum;
                    oProduct.productSource = aBAProductsList[j].productSource;
                    oProduct.issueRelatedInd = aBAProductsList[j].issueRelatedInd;
                    oProduct.status = aBAProductsList[j].status;
                    oProduct.paymentType = aBAProductsList[j].paymentType;

                    aProducts.push(oProduct);
                }
            }
        }
    }

    return aProducts;
};

RecordOfPayment_Class.prototype.toggleSubmitButton = function()
{
    var bValidateMandatory = this.validateMandatoryFields();
    var bValidateBillingAmounts = this.validateBillingAmounts();
    var bValidateInternetProducts = this.validateInternetProducts();
    console.log("toggleSubmitButton :: bValidateBillingAmounts = ",bValidateBillingAmounts,"bValidateMandatory = ",bValidateMandatory,"bValidateInternetProducts=",bValidateInternetProducts);
    if (bValidateMandatory && bValidateBillingAmounts && bValidateInternetProducts)
    {
        $("#submit_ROP_passive").hide();
        $("#submit_ROP").show();
        return true;
    }
    else
    {
        $("#submit_ROP").hide();
        $("#submit_ROP_passive").show();
        return false;
    }
};

RecordOfPayment_Class.prototype.validateMandatoryFields = function()
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

RecordOfPayment_Class.prototype.validateBillingAmounts = function()
{
    var aProductRows = $("#rop_table div[mode='product_row']");
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

RecordOfPayment_Class.prototype.validateInternetProducts = function()
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
};

RecordOfPayment_Class.prototype.showOneEmptyRow = function()
{
    $("#rop_table div[class*='empty_']:hidden:first").show();
};

RecordOfPayment_Class.prototype.hideOneEmptyRow = function()
{
    $("#rop_table div[class*='empty_']:visible:first").hide();
};

RecordOfPayment_Class.prototype.showAllEmptyRows = function()
{
    $("#rop_table div[class*='empty_']:hidden").show();
};

RecordOfPayment_Class.prototype.toggleEmptyRow = function()
{
    var aProductRows = $("#rop_table div[mode='product_row']");
    var aWriteOffRows = $("#rop_table div[mode='writeOff_row']");
    var aPastDueRows = $("#rop_table div[mode='pastDue_row']");
    var totalFilledRows = aProductRows.length + aWriteOffRows.length + aPastDueRows.length;

    var aVisibleTableRows = $("#rop_table .table_body div.table_row:visible");
    //console.log("toggleEmptyRow :: aVisibleTableRows.length = ",aVisibleTableRows.length, " :: this.aEmptyRows.length = ",this.aEmptyRows.length)
    if (aVisibleTableRows.length < this.aEmptyRows.length)
    {
        this.showOneEmptyRow();
    }
    else
    {
        if (totalFilledRows <= this.aEmptyRows.length)
        {
            //console.log("toggleEmptyRow :: totalFilledRows = ",totalFilledRows, " :: this.aEmptyRows.length = ",this.aEmptyRows.length)
            this.toggleTableStyle("remove");
            this.hideOneEmptyRow();
        }
        else
        {
            this.toggleTableStyle("add");
        }
    }
};

RecordOfPayment_Class.prototype.toggleTableStyle = function(sType)
{
    var sClassName = this.isiPad ? "with_iPad_scroller" : "with_scroller";
    if (sType == "add")
    {
        // *** ADD STYLE
        $("#rop_table").addClass(sClassName);
    }
    else
    {
        // *** REMOVE STYLE
        $("#rop_table").removeClass(sClassName);
    }
};

RecordOfPayment_Class.prototype.clearAllProductsFromScreen = function()
{
    $("#rop_table div[mode$='_row']").remove();
    this.showAllEmptyRows();
    $("#rop_table").removeClass("with_scroller").show();
};

RecordOfPayment_Class.prototype.clearAllProducts = function()
{
    var aProductRows = $("#rop_table div[mode$='_row']");
    for (var i = 0, iL = aProductRows.length; i < iL; i++)
    {
        var index = $(aProductRows[i]).attr("index");
        this.prdLookup.removePRD(index);
    }
    this.showAllEmptyRows();
    $("#rop_table").show();
};



