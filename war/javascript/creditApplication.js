// *** Created by okotlia 01/30/2012

CreditApplication_Class = function(caller)
{
    this.caller = caller;
    this.init();
};

CreditApplication_Class.prototype.init = function()
{
    this.creditEventsSet = false;
    this.prevPage = prevPage;
    this.oModalDialog = modalDialog;
    this.contactName = contactName;
    this.contactEmail = contactEmail;
    this.elCustomerInfo = $("#customerInfoDiv");
    this.bCreditEventsSet = false;
    this.isiPad = navigator.userAgent.match(/iPad/i) != null;

    this.utils = new UtilsClass();
    this.oGeneralInfo = generalInfo;

    this.setEvents();

    this.oCallerData =
    {
        custId:parseInt(this.caller.custId,10),
        businessName:this.utils.decodeHTMLEntities(this.caller.businessName),
        contactEmail:contactEmail,
        contactTitle:this.caller.contactTitle,
        contactName:contactName
    };

    console.log("CreditApplication_Class.prototype.init :: this.oCallerData=",this.oCallerData);
};

CreditApplication_Class.prototype.setEvents = function()
{
    var that = this;
    $("#submit_Credit")
        .click(function()
        {
            that.utils.showLoadingMask();
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
                            console.log("sendTemplateRequestToSignDWR : callback :: sURL IS EMPTY :: result = ",result);
                            var sErrorMsg = result.errorMessage && result.errorMessage != "" ? result.errorMessage : "A system problem has occurred that will require you to contact the Business Support Center (BSC) for assistance.  Please reference this error: DOCUSIGN_ERROR.";
                            alert(sErrorMsg);
                            that.utils.hideLoadingMask();
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
                            console.log("sendTemplateRequestToSignDWR : callback :: docuSignId IS EMPTY :: result = ",result);
                            var sErrorMsg = result.errorMessage && result.errorMessage != "" ? result.errorMessage : "A system problem has occurred that will require you to contact the Business Support Center (BSC) for assistance.  Please reference this error: DOCUSIGN_ERROR.";
                            alert(sErrorMsg);
                            that.utils.hideLoadingMask();
                        }
                    }
                }
                else
                {
                    var sErrorMsg = result.errorMessage && result.errorMessage != "" ? result.errorMessage : "A system problem has occurred that will require you to contact the Business Support Center (BSC) for assistance.  Please reference this error: DOCUSIGN_ERROR.";
                    alert(sErrorMsg);
                    that.utils.hideLoadingMask();
                }
            }

            var requestId = that.requestId + "";
            var contactName = $("#credit_contactNameInput").val();
            var contactEmail = $("#credit_customerEmailInput").val();
            var inPersonInd = $("#credit_inPersonSignature").is(":checked");
            var isJoint = false;
            console.log("BEFORE sendTemplateRequestToSignDWR : requestId = ",requestId, "contactName = ",contactName,"contactEmail = ",contactEmail,"inPersonInd = ",inPersonInd);

            DwrSignatureManager.sendTemplateRequestToSignDWR(requestId, contactName, contactEmail, inPersonInd, isJoint, callback);
        }
    );

    $("#cancel_Credit")
        .click(function()
        {
            var state = that.caller.getPageState();
            var sDelimiter = that.prevPage.indexOf("?") > 0 ? "&" : "?"
            var sURL = that.prevPage + sDelimiter + "state=" + state;
            window.location = sURL;
        }
    );

};

CreditApplication_Class.prototype.renderCreditTab = function(oPanel)
{
    this.utils.showLoadingMask();
    console.log("CreditApplication_Class :: renderData ... TODO : oPanel = ",oPanel);
    this.renderLMCBlock();
    this.renderBusinessDataBlock();
};

CreditApplication_Class.prototype.renderLMCBlock = function()
{
    var d = new Date();
    var month = d.getMonth() + 1;
    var day = d.getDate();
    var year = d.getFullYear();

    $("#credit_date_value").html("<strong>" + month + "/" + day + "/" + year + "</strong>");

    $("#credit_LMC_name").text(this.oGeneralInfo.employeeName);
    $("#credit_LMC_id").text(this.oGeneralInfo.employeeId);
};

CreditApplication_Class.prototype.renderBusinessDataBlock = function()
{
    var that = this;
    var callback = function(aResult)
    {
        console.log("DWR : renderCustomerBlock callback aResults = ",aResult);
        if (aResult)
        {
            var sStreet = aResult.street;
            var sCity = aResult.city;
            var sState = aResult.state;
            var sZip = aResult.zipFive + (aResult.zipFour ? " - " + aResult.zipFour : "");
            var sContactName = aResult.contactName;
            var sContactTitle = aResult.contactTitle;
            var sContactPhone = aResult.contactPhoneNPA  && aResult.contactPhoneNPA != "" ? aResult.contactPhoneNPA + "-" + aResult.contactPhoneCOP + "-" + aResult.contactPhoneLineNo : "";
            var sContactFax = aResult.contactFaxNPA  && aResult.contactFaxNPA != "" ? aResult.contactFaxNPA + "-" + aResult.contactFaxCOP + "-" + aResult.contactFaxLineNo : "";

            $("#credit_customer_address").html("<strong>" + sStreet + "</strong>");
            $("#credit_customer_city_value").html(sCity);
            $("#credit_customer_state_value").html(sState);
            $("#credit_customer_zip").html(sZip);
            $("#credit_contact_phone").html(sContactPhone);
            $("#credit_contact_fax").html(sContactFax);
            $("#credit_contact_name").html(sContactName);
            $("#credit_contact_title").html(sContactTitle);

            $("#credit_customerEmailInput").val(that.contactEmail);
            $("#credit_contactNameInput").val(that.contactName);

            $("#submit_Credit_passive").hide();
            $("#submit_Credit").show();
            //that.setEvents();
        }
        else
        {
            that.utils.setError("A system problem has occurred that will require you to contact the Business Support Center (BSC) for assistance.  Please reference this error: LOADING CUSTOMER BILLING BY ID." + that.custId);//error.customer.load
        }
        that.utils.hideLoadingMask();
    };
    DwrCustManager.getCustBillingDetails(that.oCallerData.custId, callback);

};