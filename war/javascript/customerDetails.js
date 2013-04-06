// *** Created by okotlia 03/08/2011
var customerDetails;

$(function() 
	{
		customerDetails = new CustomerDetails_Class();
	}
);

CustomerDetails_Class = function()
{
    this.init();
};

CustomerDetails_Class.prototype.init = function()
{
    var that = this;
	this.elHome = $('#home').show();
    this.elLoader = $('#loader').hide();
	this.elPanelLoader = $("#panel_loader");
	this.elEmptySummary = $("#emptySummary");
	this.elNewNoteIcon = $("#newNote");
	this.elNewOpptIcon = $("#newOpptHref");
	this.customerEmail = "";
	this.contactName = "";
	this.contactTitle = "";
	this.businessName = title.replace(/^\d{10} - /,"");
    this.oModalDialog = modalDialog;    
    
    this.bMsgAlreadyShown = false;
	this.prevPage = prevPage;
    
    this.custId = custId;
    this.title = title;
    this.pageId = pageId;
    this.panelId = panelId;
    this.proposalIDeSign = proposalIDeSign;
    this.proposalIDrecord = proposalIDrecord;
    this.panelObj = oPanel;
    this.readOnly = readOnly;
    this.enableTransFlag = enableTransFlag || enableTransFlag == "true" ? true : false;
    this.eSignModule = null;
    this.oRecordModule = null;

    var currentProposalId = this.panelId == 5 ? this.proposalIDeSign : this.proposalIDrecord;
    this.oPageState = {pageId:this.pageId,panelId:this.panelId,custId:this.custId,proposalId:currentProposalId,panelObj:this.panelObj,title:this.title}
	console.log("init :: ...oPageState = ",this.oPageState);

    this.prevPage = prevPage;

    this.utils = new UtilsClass();
	this.utils.renderUserName(this.generalInfo.employeeName);
	this.utils.setDWR();
	this.utils.setFullHeight();
    //this.renderPane();
    this.mapHandler = new GoogleMapClass();

	this.setEvents();
};

CustomerDetails_Class.prototype.getPageState = function() 
{
	var pageId = this.pageId;
	var panelId = this.panelId;
	var custId = this.custId;	
	var title = this.title == "" ? " " : this.title;
	console.log("getPageState :: this.title = '" + this.title + "' ... title = '" + title + "'");
	var proposalId = panelId == 5 ? this.proposalIDeSign : this.proposalIDrecord;
    var opptId = 0;
	var activityId = 0;
	var date = this.date ? this.date : " ";
	var day = this.day ? this.day : " ";
	var title = this.currentTitle ? this.currentTitle : " ";
	
	return pageId + "|" + panelId + "|" + custId + "|" + proposalId + "|" + opptId + "|" + activityId + "|" + date + "|" + day + "|" + title + "|" + encodeURIComponent(title);
};

CustomerDetails_Class.prototype.renderPane = function()
{
	$('.sideTab').removeClass("activeTab");
    $("#custTitle").html(this.title);

	var iPanelId = this.panelId + "";
	var oPanel = this.panelObj;
	var sType = "";
	switch (iPanelId)
	{
		case "1":
			sType = "cust";
			this.renderCustDetails(oPanel);
			break;
		case "3":
			sType = "notes";
			this.renderCustNotes(oPanel);
			break;
		case "5":
			sType = "eSign";
			this.renderESign(oPanel);
			break;
		case "6":
			sType = "record";
			this.renderROP(oPanel);
			break;
		case "7":
			sType = "credit";
			this.renderCredit(oPanel);
			break;
		default:
			break;
	}
	console.log("renderPane ::: BEFORE toggleToolBarControls(",sType,")");
	this.toggleToolBarControls(sType);
};

CustomerDetails_Class.prototype.toggleToolBarControls = function(sType) 
{
	console.log("this.enableTransFlag = ",this.enableTransFlag,"this.readOnly = ",this.readOnly);
    if (sType == "eSign" || sType == "record" || sType == "credit")
	{
		$("#panel_toolbar_mask").hide();
		$("#panel_toolbar").hide();
	}
	else
	{
        $("#panel_toolbar_mask").show();
        $("#panel_toolbar").show();
        if (this.enableTransFlag && !this.readOnly)
        {
            console.log("..... BRFORE this.elNewNoteIcon.show()...");
            this.elNewNoteIcon.show();
        }
        else
        {
            this.elNewNoteIcon.hide();
        }
	}
};

CustomerDetails_Class.prototype.setEvents = function() 
{
    var that = this;
    
	$("#backBtn")
		.click(function()
			{
                console.log("BACK clicked *** that.panelId = ",that.panelId);
                var state = that.getPageState();
                var sDelimiter = that.prevPage.indexOf("?") > 0 ? "&" : "?";
                var sURL = that.prevPage + sDelimiter + "state=" + state;

                if (that.enableTransFlag)
                {
                    if (that.panelId == 5)
                    {
                        that.eSignModule.savePreviousProposalToSession(function()
                            {
                                console.log(" !!!!!!!!!!!!!!! called from savePreviousProposalToSession .... ");
                                that.elLoader.show();
                                window.location = sURL;
                            }
                        );
                    }
                    else if (that.panelId == 6)
                    {
                        that.oRecordModule.savePreviousProposalToSession(function()
                            {
                                console.log(" !!!!!!!!!!!!!!! called from savePreviousProposalToSession .... ");
                                that.elLoader.show();
                                window.location = sURL;
                            }
                        );
                    }
                    else
                    {
                        that.elLoader.show();
                        window.location = sURL;
                    }
                }
                else
                {
                    that.elLoader.show();
                    window.location = sURL;
                }
		    }
		);
	
	$("#homeBtn")
		.click(function()
			{
                var state = that.getPageState();
                var sURL = "customerList.htm?custId=" + that.custId + "&state=" + state;

                if (that.enableTransFlag)
                {
                    if (that.panelId == 5)
                    {
                        that.eSignModule.savePreviousProposalToSession(function()
                            {
                                console.log(" !!!!!!!!!!!!!!! called from savePreviousProposalToSession .... ");
                                that.elLoader.show();
                                window.location = sURL;
                            }
                        );
                    }
                    else if (that.panelId == 6)
                    {
                        that.oRecordModule.savePreviousProposalToSession(function()
                            {
                                console.log(" !!!!!!!!!!!!!!! called from savePreviousProposalToSession .... ");
                                that.elLoader.show();
                                window.location = sURL;
                            }
                        );
                    }
                    else
                    {
                        that.elLoader.show();
                        window.location = sURL;
                    }
                }
                else
                {
                    that.elLoader.show();
                    window.location = sURL;
                }
			}
		);

    $('#logout')
		.click(function() 
			{
				that.elLoader.show();
				window.location.href = 'logout.htm';
	        }
	    );
	
    $('.sideTab')
		.click(function()
			{
				var sType = $(this).attr("type");
	            that.showActiveView(sType);                
			}
		);
    
    this.elNewNoteIcon
		.click(function()
			{
				var state = that.getPageState();
				that.elLoader.show();
				window.location.href = "newCustNote.htm?custId=" + that.custId + "&state=" + state;
			}
		);
    
	$("#newOpptHref")
		.click(function()
			{
				var state = that.getPageState();
				that.elLoader.show();
				window.location = "newOpportunity.htm?opptId=0&custId=" + that.custId + "&state=" + state;
			}
		);
    
	$('#mapFrame')
	 	.bind('pageAnimationStart', function(e, info) 
	 		{
	 			//console.log("JQT pageAnimationStart for mapFrame :: $(this).data('referrer')[0].innerText = ",$(this).data('referrer')[0].innerText);
	 			if (info.direction === 'in') 
	 			{
	 				var sAddress = $(this).data('referrer')[0].innerText;
	 				that.loadMap(sAddress);
	 				that.loadMapSubject();
	 			}
	 		}
	 	);
	
	$('#directionsId')
		.click(function() 
			{
				that.mapHandler.showDirections();
			}
		);
	
	$('#backMapBtn')
		.click(function() 
			{
            	that.oModalDialog.hideDialog();
				jQT.goBack();
            }
		);
};

CustomerDetails_Class.prototype.showConfirmBackAction = function(sURL)
{
	var that = this;
	var sPanelId = this.panelId + "";
	var sTabName = "";
	switch (sPanelId)
	{
		case "1":
			sTabName = "Customer"
			break;
		case "3":
			sTabName = "Customer Notes"
			break;
		case "5":
			sTabName = "eSign"
			break;
		default:
			break;
	}
	

	this.oModalDialog.renderTitle("Leaving " + sTabName + " Tab",{});
	var sMsg = this.utils.MESSAGES.changesLost + "<br/><br/>Do you want to proceed?"
	var aBodyHTML = [];
	aBodyHTML.push(	'<div id="modal_body">');
	aBodyHTML.push(		'<div id="warn_text">' + sMsg + '</div>');
	aBodyHTML.push(		'<div id="footer">');
	aBodyHTML.push(			'<div class="ui-state-active ui-corner-all4 fleft btn-icon" id="back_ok">');
	aBodyHTML.push(				'<span class="ui-icon ui-icon-check"></span> OK</div>');
	aBodyHTML.push(			'<div class="ui-state-highlight ui-corner-all4 fleft btn-icon" id="back_cancel">');
	aBodyHTML.push(				'<span class="ui-icon ui-icon-cancel"></span>Cancel</div>');
	aBodyHTML.push(		'</div>');
	
	aBodyHTML.push(	'</div>');
	this.oModalDialog.renderBody(aBodyHTML);
	
	var oDialogStyle = {top:"260px",left:"310px",width:"400px",height:"auto",zIndex:5};
	this.oModalDialog.showDialog(oDialogStyle);
	
	$('#back_ok')
		.click(function()
			{
				that.elLoader.show();
				window.location = sURL;
			}
		);
	
	$('#back_cancel')
		.click(function()
			{
				that.oModalDialog.hideDialog();
			}
		);
};

CustomerDetails_Class.prototype.showActiveView = function(sType)
{
	var that = this;
	console.log("showActiveView :: sType = ",sType,"this.panelId=",this.panelId);
	this.elLoader.show();
	this.elEmptySummary.hide();
	$('.sideTab').removeClass("activeTab");

    // *** SAVE LAST eSign CHANGES TO JSESSION
    if (this.panelId == 5)
    {
        if (that.enableTransFlag)
        {
            this.eSignModule.savePreviousProposalToSession();
        }
    }
    else if (this.panelId == 6)
    {
        if (that.enableTransFlag)
        {
            this.oRecordModule.savePreviousProposalToSession();
        }
    }

	var callback = function()
		{
			console.log("the case is not correct...");
		};
	
	switch (sType)
	{
		case "cust":
			this.panelId = 1;
			callback = function(aResult) 
		    { 
				console.log("DWR : getCustDetails callback aResults = ",aResult);
				if (aResult) 
		    	{
		        	that.renderCustDetails(aResult);
		        } 
		    	else 
		    	{	
		            that.utils.setError("A system problem has occurred that will require you to contact the Business Support Center (BSC) for assistance.  Please reference this error: LOADING CUSTOMER BY ID." + that.custId);//error.customer.load
		            that.elLoader.fadeOut("fast");
		        }        
		    };
		    DwrCustManager.getCustDetails(that.custId, callback);		
			break;
		case "notes":
			this.panelId = 3;
			callback = function(aResult) 
		    {
		        if (aResult)
		        {
	        		console.log("custNotes for custId ",that.custId," :: calback aResult = ",aResult);
	        		that.renderCustNotes(aResult);
		        }
		        else
		        {
		            that.utils.setError("A system problem has occurred that will require you to contact the Business Support Center (BSC) for assistance.  Please reference this error: LOADING CUSTOMER NOTES." + that.custId);//error.custNotes.load
		            that.elLoader.fadeOut("fast");		        	
		        }
		    };
		    DwrCustNotesManager.getCustNotesByCustId(that.custId, callback);
			break;
		case "eSign":
			this.panelId = 5;
    		this.renderESign();
			break;
		case "record":
			this.panelId = 6;
    		this.renderROP();
			break;
		case "credit":
			this.panelId = 7;
    		this.renderCredit();
			break;
		default:
			break;
	}
	console.log("showActiveView ::: BEFORE toggleToolBarControls(",sType,")");
	this.toggleToolBarControls(sType);
};

CustomerDetails_Class.prototype.renderESign = function(oPanel) 
{
	var oPanel = oPanel ? oPanel : null;
	$(".panel_title").hide();
	this.showActivePanel("eSign");
	if (this.eSignModule)
	{
		this.eSignModule.renderESignTab(oPanel);
	}
	else
	{
		this.eSignModule = new eSign_Class(this);
		this.eSignModule.renderESignTab(oPanel);
	}
};

CustomerDetails_Class.prototype.renderROP = function(oPanel)
{
	var oPanel = oPanel ? oPanel : null;
	$(".panel_title").hide();
	this.showActivePanel("record");

    if (this.oRecordModule)
	{
		this.oRecordModule.renderROPTab(oPanel);
	}
	else
	{
		this.oRecordModule = new RecordOfPayment_Class(this);
		this.oRecordModule.renderROPTab(oPanel);
	}
};

CustomerDetails_Class.prototype.renderCredit = function(oPanel)
{
    var oPanel = oPanel ? oPanel : null;
    $(".panel_title").show();
    this.showActivePanel("credit");

    if (this.oCreditModule)
    {
        this.oCreditModule.renderCreditTab(oPanel);
    }
    else
    {
        this.oCreditModule = new CreditApplication_Class(this);
        this.oCreditModule.renderCreditTab(oPanel);
    }
};

CustomerDetails_Class.prototype.showActivePanel = function(sTab) 
{
	console.log("showActivePanel :: sTab = ",sTab);
	$('.sideTab').removeClass("activeTab");

	$('.details_block').hide();
	$("div[type='" + sTab + "']").addClass("activeTab");
	this.elLoader.hide();
	$('#' + sTab).show();
	if (sTab != "eSign" && sTab != "record")
	{
		$(".panel_title").show();
	}
};

CustomerDetails_Class.prototype.renderCustDetails = function(oPanel)
{
	console.log("renderCustDetails :: oPanel = ",oPanel);
	var aHTML = [];
	if (oPanel)
	{
		var o = oPanel;
		this.iLatitude = o.latitude && o.latitude != "null" ? o.latitude : 0;
		this.iLongitude = o.longitude && o.longitude != "null" ? o.longitude : 0;
		
		var sContactName = o.contactName && o.contactName != "null" ? unescape(o.contactName) : "";
		this.contactName = sContactName;
		this.contactTitle = o.contactTitle && o.contactTitle != " " ? o.contactTitle : "";
		this.businessName = o.businessName && o.businessName != " " ? o.businessName : "";
		
		var sContactPhoneHREF = o.contactPhone && o.contactPhone != "null" ? o.contactPhone : "";
		var sContactPhone = o.formattedContactPhone && o.formattedContactPhone != "null" ? o.formattedContactPhone : "";
		var sPhoneHREF = o.listingPhone && o.listingPhone != "null" ? o.listingPhone : "";
		var sPhone = o.formattedListingPhone && o.formattedListingPhone != "null" ? o.formattedListingPhone : "";
		if (sPhone == "")
		{
			sPhone = sContactPhone != "" ? sContactPhone : "";
			sPhoneHREF = sContactPhoneHREF != "" ? sContactPhoneHREF : ""; 
		}
		var sAddress = o.formattedAddress && o.formattedAddress != "null" ? unescape(o.formattedAddress) : "";
		this.address = sAddress;
		var sLockout = o.lockoutInd && o.lockoutInd != 'N' ? "Yes" : "No";
		var sHeadingName = o.headingName && o.headingName != "null" ? unescape(o.headingName) : "";
		var sProspectCode = o.prospectCode && o.prospectCode != "null" ? o.prospectCode : "";
		var sHBD = o.hbd && o.hbd != "null" ? o.hbd : "";
		var sCreditClass = o.creditClass && o.creditClass != "null" ? o.creditClass : "";
		var sNISD = (o.nisdAmt && o.nisdAmt != "null") || typeof(o.nisdAmt) == "number" ? this.utils.formatMoney(o.nisdAmt) : "";
		var sBOTS = o.botsAmt && o.botsAmt != "null" || typeof(o.botsAmt) == "number" ? this.utils.formatMoney(o.botsAmt) : "";
		var sDeptAge = o.oldestDeptAge && o.oldestDeptAge != "null" ? o.oldestDeptAge : "";
		var sPastDueAmt = o.pastDueAmt && o.pastDueAmt != "null" || typeof(o.pastDueAmt) == "number" ? this.utils.formatMoney(o.pastDueAmt) : "";
		var sNote = o.latestNote && o.latestNote != "null" ? unescape(o.latestNote) : "";
		//this.setActiveView("cust");
		
		aHTML.push('<div class="detailsLabel">Customer Id</div>');
		aHTML.push('<div class="detailsContent" id="customerId">' + this.custId + '</div>');
		aHTML.push('<div class="detailsLabel">Contact</div>');
		aHTML.push('<div class="detailsContent" id="opptContactName">' + sContactName + '</div>');
		aHTML.push('<div class="detailsLabel">Telephone</div>');
		aHTML.push('<div class="detailsContent">');
		aHTML.push('	<a target="_blank" id="opptContactPhone" href="javascript:void(0);" class="no_deco">' + sPhone + '</a>');
		aHTML.push('</div>');
		aHTML.push('<div class="detailsLabel">Email</div>');
		aHTML.push('<div class="detailsContent">');
		
		if(o.emailAddress && o.emailAddress != "null")
	    {
			var sEmail = o.emailAddress;
			if (this.utils.validateEmail(o.emailAddress))
			{
				this.customerEmail = sEmail;
				var sMailto = "mailto:" + sEmail;
				aHTML.push('	<a target="_blank" id="opptEmailId" href="' + sMailto + '">' + sEmail + '</a>');
			}
			else
			{
				aHTML.push(		sEmail);
			}
	    }		
		
		aHTML.push('</div>');
		aHTML.push('<div class="detailsLabel">Website</div>');
		aHTML.push('<div class="detailsContent">');
		
	    if(o.internetAddress && o.internetAddress != "null")
	    {
	        var sInternetAddress = o.internetAddress;
	    	var sUrl = sInternetAddress.replace("http:\/\/","");
			aHTML.push('	<a target="_blank" id="opptWebAddressId" href="http://' + sUrl + '">' + sUrl + '</a>');
	    }

		aHTML.push('</div>');
		aHTML.push('<div class="detailsLabel">Address</div>');
		aHTML.push('<div class="detailsContent">');
		aHTML.push('	<a id="opptAddressId" class="flip" href="#mapFrame">' + sAddress +'</a>');
		aHTML.push('</div>');
		aHTML.push('<div class="detailsLabel">Lockout</div>');
		aHTML.push('<div class="detailsContent" id="opptLockoutInd">'+ sLockout + '</div>');
		aHTML.push('<div class="detailsLabel">MM Heading</div>');
		aHTML.push('<div class="detailsContent" id="opptHeadingName">'+ sHeadingName + '</div>');
		aHTML.push('<div class="detailsLabel">Prospect</div>');
		aHTML.push('<div class="detailsContent" id="opptProspect">' + sProspectCode + '</div>');
		aHTML.push('<div class="detailsLabel">HBD</div>');
		aHTML.push('<div class="detailsContent" id="opptHBD">' + sHBD + '</div>');
		aHTML.push('<div class="detailsLabel">Credit Class</div>');
		aHTML.push('<div class="detailsContent" id="opptCredit">' + sCreditClass + '</div>');
		aHTML.push('<div class="detailsLabel">Revenue</div>');
		aHTML.push('<div class="detailsContent" id="nisd_bots">');
		aHTML.push('	<strong>BOTS: </strong><span id="custBOTS">' + sBOTS + '</span>');
		aHTML.push('	<strong> NISD: </strong><span id="custNISD">' + sNISD + '</span>');
		aHTML.push('</div>');
		aHTML.push('<div class="detailsLabel">Oldest Debt Age</div>');
		aHTML.push('<div class="detailsContent" id="opptDebt">' + sDeptAge + '</div>');
		aHTML.push('<div class="detailsLabel">Past Due Amount</div>');
		aHTML.push('<div class="detailsContent" id="opptPastDue">' + sPastDueAmt + '</div>');
		aHTML.push('<div class="detailsLabel">Latest Cust Note</div>');
		aHTML.push('<div class="detailsContent_textarea" id="opptNotes">' + sNote + '</div>');
		
		//$("#custDetailsContent").html(aHTML.join(""));
		$("#scroller_custDetails").html(aHTML.join(""));
		
		this.showActivePanel("cust");
		//this.toggleToolBarControls("cust");
	}
};


CustomerDetails_Class.prototype.renderCustNotes = function(aResult) 
{
	console.log("renderCustNotes :: aResult = ",aResult);
	//$("#custNotesContent").empty();
	$("#scroller_custNotes").empty();
	var aContent = [];

	if (aResult)
	{
		if (aResult.length > 0)
		{
			for (var i = 0, iL = aResult.length; i < iL; i++)
			{
				var oNote = aResult[i];
				var sLabelNote = "";
				var sNote = "";

				aContent.push("<div id='opptsRow_" + oNote.noteId + "' class='opptsRow'>");
				
				for (var keyLabel in this.utils.NOTE)
				{
					if (this.utils.NOTE[keyLabel])
					{
						if (keyLabel == "noteText")
						{
							sLabelNote = "<div class='detailsLabel'>" + this.utils.NOTE[keyLabel] + "</div>";
							if (oNote[keyLabel] && oNote[keyLabel] != "null")
							{
								var sNoteText = unescape(oNote[keyLabel]);
                                sNote = "<div class='detailsContent_textarea'>" + sNoteText + "</div>";
							}
							else
							{
								sNote = "<div class='detailsContent'></div>";
							}
						}
						else
						{
							var sLabel = "<div class='detailsLabel'>" + this.utils.NOTE[keyLabel] + "</div>";
							
							if (!oNote[keyLabel] || oNote[keyLabel] == "null")
							{
								var sValue = "";
							}
							else
							{
								var sValue = oNote[keyLabel];
							}
							
							var sDetail = "<div class='detailsContent'>" + sValue + "</div>";
							aContent.push(sLabel,sDetail);				
						}
					}
				}
				
				if (sNote != "")
				{
					aContent.push(sLabelNote,sNote);
				}
				aContent.push("</div>");			
			}
			//$("#custNotesContent").html(aContent.join(""));
			$("#scroller_custNotes").html(aContent.join(""));
		}
		else
		{
       		this.elEmptySummary.show().find("span").html("No Customer Notes found.");
		}
	}
	this.showActivePanel("notes");
	//this.setCustNotesEvents();
};

CustomerDetails_Class.prototype.setCustNotesEvents = function() 
{
	var that = this;
	$('.log_expand')
		.click(function(e)
			{	
				that.toggleNoteContent(this);
			}
		)
		.mouseup(function()
			{
				return false;
			}
		);	
};

CustomerDetails_Class.prototype.toggleNoteContent = function(el) 
{
   	var that = this;
	var iNoteId = $(el).attr("noteId");
	$(el).find("div.expand_icon").toggleClass("ui-state-highlight ui-state-lowlight");
	var elDetails = $("#note_" + iNoteId);
	if (elDetails.is(":visible"))
	{
		elDetails.hide();
	}
	else
	{
		elDetails.show();
	}
};

CustomerDetails_Class.prototype.loadMap = function(sAddr)
{
    this.elLoader.show();
    console.log("loadMap :: BEFORE loadGoogleMap --- .. sAddr = ",sAddr,"this.iLatitude=",this.iLatitude,"this.iLongitude=",this.iLongitude);
    var iLatitude = this.iLatitude ? this.iLatitude : 0;
    var iLongitude = this.iLongitude ? this.iLongitude : 0;
    this.mapHandler.loadGoogleMap(sAddr, iLatitude, iLongitude);
};

CustomerDetails_Class.prototype.loadMapSubject = function()
{
	$(".subjectName").html(this.title);
}


