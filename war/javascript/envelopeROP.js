// *** created by okotlia 01/10/2012
var oEnvelope_ROPBase = new Envelope_Class();
var ROP_Data = clone(oEnvelope_ROPBase);

ROP_Data.updateEnvelopeFromCurrentInfo = function(activeBAId)
{
    var aProposals = this.getProposals();
    var iActiveProposalIndex = this.getActiveProposalIndex();
    var aBillingAccounts = aProposals[iActiveProposalIndex].oEnvelope.billingAccounts;
    var oActiveBA = null;
    var bValid = true;
    for (var i = 0, iL = aBillingAccounts.length; i < iL; i++)
    {
        var oBA = aBillingAccounts[i];
        if (oBA.finBAccountId == activeBAId)
        {
            oActiveBA = oBA;
            break;
        }
    }

    if (oActiveBA)
    {
        var sAddress = $("#baAddress").val();
        var sAccountName = $("#baAccountName").val();
        var sCOP = $("#baCop").val();
        var sNAP = $("#baTn").val();
        var sLineNo = $("#baLineNo").val();

        if (sCOP.length < 3 || sNAP.length < 3 || sLineNo.length < 4 || sAddress == "" || sAccountName == "")
        {
            bValid = false;
        }
        else
        {
            oActiveBA.baAddress = sAddress;
            oActiveBA.baAccountName = sAccountName;
            oActiveBA.baTn = sNAP;
            oActiveBA.baCop = sCOP;
            oActiveBA.baLineNo = sLineNo;
        }
    }
    console.log("updateEnvelopeFromCurrentInfo :: aProposals[" + iActiveProposalIndex + "].oEnvelope = ",aProposals[iActiveProposalIndex].oEnvelope);
};

ROP_Data.getPrimaryBA = function()
{
    var iActiveProposalIndex = this.getActiveProposalIndex();
    var aProposals = this.getProposals();
    var aBillingAccounts = aProposals[iActiveProposalIndex].oEnvelope.billingAccounts;
    return aBillingAccounts[0];
};

ROP_Data.updateEnvelope = function($el,activeBAId,actionType,actionObject)
{
    console.log("updateEnvelope :: arguments=",arguments);

    var iActiveProposalIndex = this.getActiveProposalIndex();
    var aProposals = this.getProposals();

    console.log("updateEnvelope :: aProposals[" + iActiveProposalIndex + "].oEnvelope.billingAccounts = ",aProposals[iActiveProposalIndex].oEnvelope.billingAccounts);
    var iBAForAction = activeBAId ? activeBAId : 0;

    if (arguments.length > 2)
    {
        switch (actionType)
        {
            case "addProduct":
                // *** TEMPORARY USE FIRST BA AS A PRIMARY BA - TODO: TO GET REAL PRIMARY ACCOUNT
                var oPrimaryBA = this.getPrimaryBA();
                var aProductsList = oPrimaryBA.baProductList;
                aProductsList.push(actionObject);

                /*
                for (var i = 0, iL = aBillingAccounts.length; i < iL; i++)
                {
                    var oBA = aBillingAccounts[i];
                    if (oBA.finBAccountId == iBAForAction)
                    {
                        var aProductsList = oBA.baProductList;
                        aProductsList.push(actionObject);
                        break;
                    }
                }
                */
                break;
            default:
                break;
        }
    }
    else
    {
        var elId = $el.attr("id");
        var sActionGroup = "";
        if (/^removeBABtn/.test(elId))
        {
            sActionGroup = "removeBA";
            iBAForAction = parseInt($el.attr("baId"),10);
        }
        else
        {
            sActionGroup = $el.hasClass("baInfo") ? "baInfo" : "products";
        }
        console.log("updateEnvelope ::: sActionGroup = ",sActionGroup);
        //var iActiveProposalIndex = this.iProposalId - 1;
        var aBillingAccounts = aProposals[iActiveProposalIndex].oEnvelope.billingAccounts;
        console.log("updateEnvelope ::: aBillingAccounts = ",aBillingAccounts);

        for (var i = 0, iL = aBillingAccounts.length; i < iL; i++)
        {
            var oBA = aBillingAccounts[i];
            console.log("i = ",i," oBA = ",oBA,"sActionGroup = ",sActionGroup);

            if (oBA.finBAccountId == iBAForAction)
            {
                switch(sActionGroup)
                {
                    case "removeBA":
                        // *** REMOVE BILLING ACCOUNT
                        console.log("eSign_Data :: BEFORE removeBA");
                        aProposals[iActiveProposalIndex].oEnvelope.billingAccounts.splice(i,1);
                        break;

                    case "baInfo":
                        console.log("updateEnvelope ::::::::: elId = ",elId);
                        oBA[elId] = $el.val();
                        break;

                    case "products":
                        var aProductsList = oBA.baProductList;
                        //var sRowProductName = $el.attr("productName");
                        var sRowProductValue = $el.attr("prdValue");
                        var sCustomType = $el.attr("customType");
                        for (var j = 0, jL = aProductsList.length; j < jL; j++)
                        {
                            console.log("updateEnvelope :: products case :::::: aProductsList[" + j + "=",aProductsList[j]);
                            var productValue = aProductsList[j].productCode + "||" + aProductsList[j].productIssueNum;
                            console.log("updateEnvelope :: products case :::::: productValue=",productValue,"sRowProductValue=",sRowProductValue,"sCustomType=",sCustomType);
                            if (productValue == sRowProductValue)
                            {
                                switch (sCustomType)
                                {
                                    case "erase_button":
                                        // *** REMOVE PRODUCT
                                        aProductsList.splice(j,1);
                                        break;
                                    case "monthly_billing":
                                        // *** EDIT MONTHLY BILLING
                                        var val = $el.val();
                                        val = val == "." ? "0.00" : val;
                                        aProductsList[j].monthlyBilling = val;
                                        break;
                                    case "internet_date":
                                        // *** EDIT INTERNET DATE
                                        if (/^start_date_/.test(elId))
                                        {
                                            aProductsList[j].startDate = $el.val();
                                        }
                                        else
                                        {
                                            aProductsList[j].endDate = $el.val();
                                        }
                                        break;
                                    default:
                                        break;
                                }

                                break;
                            }
                        }
                        break;
                }
                break;
            }
        }
    }
    console.log("AFTER :::: updateEnvelope :: $el = ",$el," current account ID = ",activeBAId," aProposals[iActiveProposalIndex].oEnvelope = ",aProposals[iActiveProposalIndex].oEnvelope);
};
