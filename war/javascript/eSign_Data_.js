// *** Created by okotlia 10/24/2011
var eSign_Data = (function()
{
	// *** PRIVATE MEMBERS
	var aBA = [];
	var aProposals = [{id:1},{id:2},{id:3},{id:4},{id:5},{id:6}];
	var iActiveProposalIndex = 0;
	var iProposalId = 1;
	
	function setProposal(oEnvelope)
	{
        console.log("setProposal ::: oEnvelope = ",oEnvelope);
		aProposals[iActiveProposalIndex].oEnvelope = oEnvelope;
		console.log("setProposal ::: aProposals = ",aProposals);
	}
	
	function isBAExists(iBAId) 
	{
		console.log("isBAExists ::: iBAId = ",iBAId);
		
		var oBAExists = {bool:false,index:0};
        console.log("isBAExists :: aProposals",aProposals,"iActiveProposalIndex=",iActiveProposalIndex);
		var oActiveEnvelope = aProposals[iActiveProposalIndex].oEnvelope;
		if (oActiveEnvelope)
		{
			for (var j = 0, jL = oActiveEnvelope.billingAccounts.length; j < jL; j++)
			{
				if (iBAId == oActiveEnvelope.billingAccounts[j].finBAccountId)
				{
					return {bool:true,index:j};
				}
			}
		}
		return oBAExists;
	}
	
	return {
		
		// *** PUBLIC MEMBERS
		setActiveProposal: function(iProposalId)
		{
			iProposalId = iProposalId;
			iActiveProposalIndex = iProposalId - 1;
		},	
		
		getEnvelope: function()
		{
			console.log("getEnvelope :: iActiveProposalIndex = ",iActiveProposalIndex, "oEnvelope = ",aProposals[iActiveProposalIndex].oEnvelope);
			return aProposals[iActiveProposalIndex].oEnvelope;
		},

        getProductsList: function(proposalId)
		{
			console.log("getProductsList :: proposalId = ",proposalId, "oEnvelope = ",this.getProposal(proposalId - 1));
            var aProductsList = [];
			var oEnvelope = aProposals[proposalId - 1].oEnvelope;
            var aBillingAccounts = oEnvelope.billingAccounts ? oEnvelope.billingAccounts : [];
            var iL = aBillingAccounts.length;
            if (iL > 0)
            {
                for (var i = 0; i < iL; i++)
                {
                    var oBA = aBillingAccounts[i];
                    var aProducts = oBA.baProductList;
                    var jL = aProducts.length;
                    if (jL > 0)
                    {
                        for (var j = 0; j < jL; j++)
                        {
                            var oProduct = aProducts[j];
                            var oProductForList =
                                {
                                    //barcodePhoneNo: "(910) 429-0684",
                                    billingAccountId: oBA.finBAccountId,
                                    billingAccountName: oBA.baAccountName,
                                    billingAddress: oBA.baAddress,
                                    billingPhoneNo: oBA.baTn ? "(" + oBA.baTn + ") " + oBA.baCop + "-" + oBA.baLineNo : "",
                                    businessName: oEnvelope.businessName,
                                    customerId: oEnvelope.customerId,
                                    emailAddress: oBA.baContactEmail,
                                    internetEndDate: oProduct.endDate,
                                    internetStartDate: oProduct.startDate,
                                    issueRelatedInd: oProduct.issueRelatedInd,
                                    productBillingMonthly: oProduct.monthlyBilling,
                                    productCode: oProduct.productCode,
                                    productIssueDate: oProduct.issueDate,
                                    productIssueDateDisplay: oProduct.issueDateDisplay,
                                    productIssueNumber: oProduct.productIssueNum,
                                    productName: oProduct.productName,
                                    productStatus: oProduct.status,
                                    repId: oProduct.repId
                                };
                            aProductsList.push(oProductForList);
                        }
                    }
                }
            }
            return aProductsList;
		},

		setEnvelope: function(aProducts,oCallerData)
		{
			console.log("setEnvelope :: aProducts = ", aProducts);
			var oEnvelope = {};
			
			oEnvelope.esignEnvelopeId = 0;
			oEnvelope.inPersonInd = false;
			oEnvelope.customerId = oCallerData.custId;
			oEnvelope.businessName = oCallerData.businessName;
			oEnvelope.contactName = oCallerData.contactName;
			oEnvelope.contactTitle = oCallerData.contactTitle;
			oEnvelope.contactEmail = oCallerData.contactEmail;
			oEnvelope.signatureDate = "";
			oEnvelope.proposalId = iProposalId + "";
			oEnvelope.esignEnvelopeStatus = "NEW";
			
			oEnvelope.billingAccounts = [];
			console.log("setEnvelope :: oEnvelope",oEnvelope,"products=",aProducts);
			for (var i = 0, iL = aProducts.length; i < iL; i++)
			{
				var oProduct = aProducts[i];
				var iBAId = oProduct.billingAccountId && oProduct.billingAccountId != "null" ? oProduct.billingAccountId : 0;
				var oBAExists = isBAExists(iBAId);
				console.log("setEnvelope :: oBAExists = ",oBAExists);
		        var startDate = oProduct.internetStartDate && oProduct.internetStartDate != 'null' ? oProduct.internetStartDate : "";
		        var endDate = oProduct.internetEndDate && oProduct.internetEndDate != 'null' ? oProduct.internetEndDate : "";
		        var iProductBillingMonthly = (parseFloat(oProduct.productBillingMonthly)).toFixed(2);
		        var issueDate = oProduct.productIssueDate && oProduct.productIssueDate != "" ? oProduct.productIssueDate : "";
		        var issueDateDisplay = oProduct.productIssueDateDisplay && oProduct.productIssueDateDisplay != "" ? oProduct.productIssueDateDisplay : "";
				var paymentType = oProduct.paymentType ? oProduct.paymentType : "A";
                var oProductData =
					{
						esignEnvelopeId:0,
						productCode:oProduct.productCode,
						productName:oProduct.productName,
						issueDate:issueDate,
						issueDateDisplay:issueDateDisplay,
						startDate:startDate,
						endDate:endDate,
						monthlyBilling:iProductBillingMonthly,
			 			issueRelatedInd:oProduct.issueRelatedInd,
			 			productSource:"KGEN",
			 			productIssueNum:oProduct.productIssueNumber,
			 			status:oProduct.productStatus,
                        repId:oProduct.repId,
                        paymentType:paymentType
					}

				if (oBAExists.bool)
				{
					console.log("BEFORE  oEnvelope.billingAccounts[oBAExists.index].baProductList.push(oProductData) :: oEnvelope = ",oEnvelope, "oBAExists.index = ",oBAExists.index);

                    if (oBAExists.index == 0 && oEnvelope.billingAccounts.length == 0)
                    {
                        oEnvelope.billingAccounts[0] =
                            {
                                esignEnvelopeId:0,
                                esignBAccountId:0,
                                finBAccountId:iBAId,
                                baAccountName:oProduct.billingAccountName,
                                baContactName:"",
                                baContactTitle:"",
                                baContactEmail:"",
                                baCop:oProduct.billingPhoneNo.substr(6,3),
                                baTn:oProduct.billingPhoneNo.substr(1,3),
                                baLineNo:oProduct.billingPhoneNo.substr(10,4),
                                baAddress:oProduct.billingAddress,
                                baSource:sBaSource,
                                baProductList:[]
                            };
                        oEnvelope.billingAccounts[0].baProductList = [];
                    }

                    oEnvelope.billingAccounts[oBAExists.index].baProductList.push(oProductData);
                    console.log("AFTER  oEnvelope.billingAccounts[oBAExists.index].baProductList.push(oProductData)");
				}
				else
				{
					console.log("*** here ***");
                    var sBaSource = iBAId == 0 ? "ESGN" : "KGEN";
					var oBA =
						{
							esignEnvelopeId:0,
		                    esignBAccountId:0,
		                    finBAccountId:iBAId,
							baAccountName:oProduct.billingAccountName,
		                    baContactName:"",
		                    baContactTitle:"",
		                    baContactEmail:"",
							baCop:oProduct.billingPhoneNo.substr(6,3),
		                    baTn:oProduct.billingPhoneNo.substr(1,3),
							baLineNo:oProduct.billingPhoneNo.substr(10,4),
							baAddress:oProduct.billingAddress,
							baSource:sBaSource,
							baProductList:[]
						}
					oBA.baProductList.push(oProductData);
					oEnvelope.billingAccounts.push(oBA);
				}
				console.log("BEFORE setProposal call :: iProposalId = ",iProposalId);
                var iActiveProposalIndex = iProposalId - 1;
				setProposal(oEnvelope);
			}
		},
		
		setEmptyEnvelope: function(oCallerData,oBillingInfo)
		{
			var oEnvelope = {};
            var oBA = null;
			
			oEnvelope.esignEnvelopeId = 0;
			oEnvelope.inPersonInd = false;
			oEnvelope.customerId = oCallerData.custId;
			oEnvelope.businessName = oCallerData.businessName;
			oEnvelope.contactName = oCallerData.contactName;
			oEnvelope.contactTile = oCallerData.contactTitle;
			oEnvelope.contactEmail = oCallerData.contactEmail;
			oEnvelope.signatureDate = "";
			oEnvelope.proposalId = iProposalId + "";
			oEnvelope.esignEnvelopeStatus = "NEW";
			
			oEnvelope.billingAccounts = [];

            if (oBillingInfo)
            {
			    oBA = oBillingInfo;
            }
            else
            {
                oBA =
                {
                    esignEnvelopeId:0,
                    esignBAccountId:0,
                    finBAccountId:0,
                    baAccountName:"",
                    baContactName:"",
                    baContactTitle:"",
                    baContactEmail:"",
                    baCop:"",
                    baTn:"",
                    baLineNo:"",
                    baAddress:"",
                    baSource:"ESGN",
                    baProductList:[]
                }
            }
			oEnvelope.billingAccounts.push(oBA);
			aProposals[5].oEnvelope = oEnvelope;
		},
		
		updateEnvelopeFromCurrentInfo: function(activeBAId)
		{
			//console.log("updateEnvelopeFromCurrentInfo :: activeBAId = ",activeBAId,"iActiveProposalIndex = ",iActiveProposalIndex);
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
		},
				
		updateEnvelope: function($el,activeBAId,actionType,actionObject) 
		{
			console.log("updateEnvelope :: arguments=",arguments);
			var iBAForAction = activeBAId;
			if (arguments.length > 2)
			{
				switch (actionType) 
				{
					case "addProduct":
						//var iActiveProposalIndex = this.iProposalId - 1;
						var aBillingAccounts = aProposals[iActiveProposalIndex].oEnvelope.billingAccounts;
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
		},
		
		getBAs: function()
		{
			var oEnvelope = this.getEnvelope();
			if (oEnvelope)
			{
				return oEnvelope.billingAccounts;
			}
			else
			{
				return [];
			}
		},
		
		getProposals: function()
		{
			return aProposals;
		},

        getProposal: function(proposalId)
        {
            return aProposals[proposalId];
        },

        getNewEnvelopeBillingInfo: function()
        {
            var oBillingAccount = null;
            var oEnvelope = aProposals[5].oEnvelope || null;
            if (oEnvelope && oEnvelope.billingAccounts && oEnvelope.billingAccounts.length > 0)
            {
                oBillingAccount = oEnvelope.billingAccounts[0];
            }
            return oBillingAccount;
        }

	};
})();