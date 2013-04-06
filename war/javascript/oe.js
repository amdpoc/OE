// *** Created by okotlia 02/12/2013
var oe;

$(function()
	{
		oe = new OrderEntry_Class();
	}
);

OrderEntry_Class = function()
{
    this.init();
};

OrderEntry_Class.prototype.init = function()
{
    var that = this;
   	this.elPopOver = $("#prdLookup");
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
    this.generalInfo = generalInfo;
    this.mediaType = "";

    var currentProposalId = this.panelId == 5 ? this.proposalIDeSign : this.proposalIDrecord;
    this.oPageState = {pageId:this.pageId,panelId:this.panelId,custId:this.custId,proposalId:currentProposalId,panelObj:this.panelObj,title:this.title}
	console.log("init :: ...oPageState = ",this.oPageState);

    this.prevPage = prevPage;

    this.utils = new UtilsClass();
	this.utils.renderUserName(this.generalInfo.employeeName);
	this.utils.setDWR();
	this.utils.setFullHeight();
    //this.renderPane();
    //this.mapHandler = new GoogleMapClass();

    this.prdLookup = new PrdMultiLookUpClass(this);
	this.renderStep1();
    this.setEvents();
    this.getProductList();
};

OrderEntry_Class.prototype.getProductList = function()
{
    this.productList = productList;
};

OrderEntry_Class.prototype.MEDIA_TYPES =
[
    {"mediaType":"MOBILE","mediaTypeName":"Mobile","icon":"icon-signal"},
    {"mediaType":"MULTI","mediaTypeName":"Multiple Media for bundle","icon":"icon-th-large"},
    {"mediaType":"ONLINE","mediaTypeName":"Online","icon":"icon-refresh"},
    {"mediaType":"PRINT","mediaTypeName":"Print","icon":"icon-book"},
    {"mediaType":"SERVICE","mediaTypeName":"Services","icon":"icon-wrench"},
    {"mediaType":"SPECIAL","mediaTypeName":"Other Media","icon":"icon-bell"}
];

OrderEntry_Class.prototype.getMediaTypes = function()
{
    return this.MEDIA_TYPES;
}

OrderEntry_Class.prototype.renderStep1 = function()
{
    var aMT = this.getMediaTypes();
    var iL = aMT.length;
    var aHTML = [];
    if (iL > 0)
    {
        for (var i = 0; i < iL; i++ )
        {
            aHTML.push('<div class="span4 no-margin module" title="' + aMT[i].mediaTypeName + '">' + aMT[i].mediaType + '</div>');
        }
        //console.log(aHTML);
        $("#Step1").append(aHTML.join(""));
    }
};

OrderEntry_Class.prototype.getPageState = function()
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

OrderEntry_Class.prototype.setEvents = function()
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

    $('#prdLookupBtn')
        .click(function()
            {
                console.log("prdLookupBtn click");
                //that.prdLookup.lookupPRD("eSign",that.activeBAId,that.iProposalId);
                // *** NEW ENVELOPE CASE WITH NO PRODUCTS ADDED
                that.prdLookup.lookupPRD("eSign",0,6);
            }
        )
        .mouseup(function()
            {
                console.log("prdLookupBtn mouseup");
                return false;
            }
        );

    $('#chooseMediaBtn')
        .click(function()
        {
            that.setMediaFlow();
        });

    $('select#mediaType')
        .change(function()
        {
            that.setMediaFlow();
        });

    $('.panel-pedal')
        .click(function()
        {
            if ($("#leftPanel").is(":hidden"))
            {
                $("#rightPanel").switchClass("span12","span8",500,function()
                        {
                            $("#leftPanel").show().switchClass("span0","span4",500);
                            console.log("end of effect")
                        }
                    );
            }
            else
            {
                $("#leftPanel").switchClass("span4","span0",500).hide();
                $("#rightPanel").switchClass("span8","span12",500);
            }
        });

    // *** MEDIA TYPES
    $('#Step1 div.module')
        .click(function()
        {
            var sMedia = $(this).text();
            that.renderProductList(sMedia);
        });

    // *** CAROUSEL BUTTONS
    $('#stepsCarousel a[data-slide]')
        .click(function()
        {
            var sWhere = $(this).attr("data-slide");
            $('#stepsCarousel').carousel(sWhere);
            $('#stepsCarousel')
                .bind('slid',function()
                {
                    $(this).carousel('pause');
                });
        });
};

OrderEntry_Class.prototype.renderProductList = function(sMedia)
{
    var that = this;
    if (sMedia != "")
    {
        var aMediaProducts = that.productList[sMedia];
        //console.log("clicked Media type ... media = ",sMedia," :: aMediaProducts = ",aMediaProducts);
        var aProductRows = [];
        var iL = aMediaProducts.length;
        if (iL > 0)
        {
            for (var i = 0; i < iL; i++)
            {
                var oProduct = aMediaProducts[i];
                var sName = oProduct.productName;
                var sCode = oProduct.productCode;
                var sMediaType = oProduct.mediaType;
                var sID = sMediaType + "_" + sCode;
                var sIcon = that.getIcon(oProduct.mediaType);

                aProductRows.push('<div class="span12 product-row draggable" id="' + sID + '" attr="' + sCode +'">');
                aProductRows.push(  '<i class="' + sIcon + '"></i> ');
                aProductRows.push(  sName);
                aProductRows.push(  '<i class="icon-plus fright"></i>');
                aProductRows.push('</div>');
            }
            //console.log("... aProductRows = ",aProductRows);
            $('#allProductsTab').append(aProductRows.join(""));
            that.setProductListEvents();
        }
        that.moveToStep(1);
    }
    else
    {
        console.log("TO DO ::: renderProductList :: sMedia = ", sMedia);
    }

};

OrderEntry_Class.prototype.setProductListEvents = function()
{
    $(".draggable").draggable();
    $(".droppable")
        .droppable({
            drop: function(event, ui)
            {
                                alert( "dropped" );

                //var sProductRow()
                //$(this).append();
                console.log("Dropped :: draggable = ",ui.draggable);
                console.log("Dropped :: draggable class = ",ui.draggable.attr("class"));
                console.log("Dropped :: event = ",event);
            }
            //,accept:".product-row"
        });
};


OrderEntry_Class.prototype.getIcon = function(sMediaType)
{
    for (var i = 0, iL = this.MEDIA_TYPES.length; i < iL; i++)
    {
        if (this.MEDIA_TYPES[i].mediaType == sMediaType)
        {
            return this.MEDIA_TYPES[i].icon;
        }
    }
    return "";
};

OrderEntry_Class.prototype.moveToStep = function(iStep)
{
    $('#stepsCarousel').carousel(iStep);
    $('#stepsCarousel')
        .bind('slid',function()
        {
            $(this).carousel('pause');
        });
};

OrderEntry_Class.prototype.setMediaFlow = function()
{
    var that = this;
    //console.log("option : ", $(this).text()," :: value = ",$(this).val());
    var sMessage = "Digital Item Order Entry flow starts";
    $("select#mediaType option:selected")
        .each(function ()
            {
                switch ($(this).val())
                {
                    case "digital":
                        that.showFlowMessage(sMessage);
                        break;
                    case "tv":
                        sMessage = "TV Item Order Entry flow starts";
                        that.showFlowMessage(sMessage);
                        break;
                    case "print":
                        that.startPrintOrderEntry();
                        break;
                    case "bundle":
                        that.showFlowMessage(sMessage);
                        sMessage = "Bundle Item Order Entry flow starts";
                        break;
                    default:
                        break;
                }
            });
};

OrderEntry_Class.prototype.showFlowMessage = function(sMessage)
{
    console.log("showFlowMessage ... : ",sMessage);
    $('#printDirDiv').hide();
    $('#flowMessage').text(sMessage).show();
}

OrderEntry_Class.prototype.startPrintOrderEntry = function()
{
    console.log("startPrintOrderEntry ... ");
    $('#flowMessage').hide();
    $('#printDirDiv').show();
}

OrderEntry_Class.prototype.handleSelectedPRD = function(i,$el)
{
    var that = this;
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

    aRow.push(  'Directory: ' + sPrdName);
    aRow.push(  '<div class="pull-right">');

    aRow.push(      '<div class="btn-group" id="productSelect">');
    aRow.push(          '<a class="btn btn-small href="#">Choose Product</a>');
    aRow.push(          '<a class="btn btn-small dropdown-toggle" data-toggle="dropdown" href="#"><span class="caret"></span></a>');
    aRow.push(          '<ul class="dropdown-menu">');
    aRow.push(              '<li><a href="#" udac="DQCWP">DQCWP</a></li>');
    aRow.push(              '<li><a href="#" udac="BLN">BLN</a></li>');
    aRow.push(              '<li><a href="#" udac="ASL">ASL</a></li>');
    aRow.push(          '</ul>');
    aRow.push(      '</div>');
    aRow.push(  '</div>');
    $('.span8 header').html(aRow.join("")).show();

    that.elPopOver.modal('hide');
    that.setProductSelectEvents();
};

OrderEntry_Class.prototype.setProductSelectEvents = function()
{
    var that = this;
    $('#productSelect li a')
        .click(function()
        {
            var udacName = $(this).attr('udac');
            that.renderProductDetails2(udacName);
        });
};

OrderEntry_Class.prototype.renderProductDetails2 = function(udacName)
{
    var that = this;
    var aRow = [];
    aRow.push(  '<div class="span8" style="padding:20px;">');
    aRow.push(  '<fieldset  class="small"><legend>Product Details</legend>');
    aRow.push(  '<div class="span5" style="padding-top:20px;">');
    aRow.push(      '<form class="form-horizontal">');
    aRow.push(          '<div class="control-group">');
    aRow.push(              '<label class="control-label span4" for="inputHeading">Heading</label>');
    aRow.push(              '<div class="controls span8">');
    aRow.push(                  '<input class="span12" type="text" id="inputHeading" placeholder="Heading">');
    aRow.push(              '</div>');
    aRow.push(          '</div>')
    aRow.push(          '<div class="control-group">');
    aRow.push(              '<label class="control-label span4" for="inputProduct">Product</label>');
    aRow.push(              '<div class="controls span8">');
    aRow.push(                  '<input class="span12" disabled="disabled" type="text" id="inputProduct" placeholder="product" value="' + udacName + '">');
    aRow.push(              '</div>');
    aRow.push(          '</div>');
    aRow.push(          '<div class="control-group">');
    aRow.push(              '<label class="control-label span4" for="inputOffer">Offer</label>');
    aRow.push(              '<div class="controls span8">');
    aRow.push(                  '<input class="span12" type="text" id="inputOffer" placeholder="Offer">');
    aRow.push(              '</div>');
    aRow.push(          '</div>');
    aRow.push(      '</form>');
    aRow.push(  '</div>');
    aRow.push(  '<div class="span6" style="padding-top:20px;">');
    aRow.push(      '<form class="form-horizontal">');
    aRow.push(          '<div class="control-group">');
    aRow.push(              '<label class="control-label span4" for="inputBrand">Brand</label>');
    aRow.push(              '<div class="controls span8">');
    aRow.push(                  '<input class="span12" type="text" id="inputBrand" placeholder="Brand">');
    aRow.push(              '</div>');
    aRow.push(          '</div>')
    aRow.push(          '<div class="control-group">');
    aRow.push(              '<label class="control-label span6" for="inputBA">Billing Account</label>');
    aRow.push(              '<div class="controls span6">');
    //aRow.push(                  '<input class="span12" type="text" id="inputBA" placeholder="ba">');
    aRow.push(                  that.renderBASelect());
    aRow.push(              '</div>');
    aRow.push(          '</div>');
    aRow.push(      '</form>');
    aRow.push(  '</div>');
    aRow.push(  '</fieldset>');
    aRow.push(  '</div>');

    aRow.push(  '<div class="span4" style="padding:20px">');
    aRow.push(  '<fieldset class="small"><legend>Incentive Offers</legend>');
    aRow.push(  '<div class="span12" style="padding-top:20px;">');
    aRow.push(      '<form class="form-horizontal">');
    aRow.push(          '<div class="control-group">');
    aRow.push(              '<label class="control-label span4" for="inputOffer1">Offer #1</label>');
    aRow.push(              '<div class="controls span8">');
    aRow.push(                  '<input class="span11" type="text" id="inputOffer1" placeholder="Offer #1">');
    aRow.push(              '</div>');
    aRow.push(          '</div>')
    aRow.push(          '<div class="control-group">');
    aRow.push(              '<label class="control-label span4" for=inputOffer2">Offer #2</label>');
    aRow.push(              '<div class="controls span8">');
    aRow.push(                  '<input class="span11" type="text" id="inputOffer2" placeholder="Offer #2">');
    aRow.push(              '</div>');
    aRow.push(          '</div>')
    aRow.push(          '<div class="control-group">');
    aRow.push(              '<label class="control-label span4" for="inputOffer3">Offer #3</label>');
    aRow.push(              '<div class="controls span8">');
    aRow.push(                  '<input class="span11" type="text" id="inputOffer3" placeholder="Offer #3">');
    aRow.push(              '</div>');
    aRow.push(          '</div>')
    aRow.push(      '</form>');
    aRow.push(  '</div>');
    aRow.push(  '</fieldset>');
    aRow.push(  '</div>');
    aRow.push(  '<hr class="span11">');

    aRow.push(  '<div class="span11" style="padding:20px;">');
    aRow.push(  '<fieldset class="small"><legend>Product Specific Details</legend>');

    aRow.push(  '<div id="myCarousel" class="carousel slide">');
    aRow.push(      '<ol class="carousel-indicators">');
    aRow.push(          '<li data-target="#myCarousel" data-slide-to="0" class="active"></li>');
    aRow.push(          '<li data-target="#myCarousel" data-slide-to="1"></li>');
    aRow.push(          '<li data-target="#myCarousel" data-slide-to="2"></li>');
    aRow.push(      '</ol>');
    // *** Carousel items  ');
    aRow.push(      '<div class="carousel-inner">');
    aRow.push(          '<div class="active item">Price and Mandatory Fields</div>');
    aRow.push(          '<div class="item">Product Content</div>');
    aRow.push(          '<div class="item">Additional Attributes</div>');
    aRow.push(      '</div>');
    // *** Carousel nav
    aRow.push(      '<a class="carousel-control left" href="#myCarousel" data-slide="prev">&lsaquo;</a>');
    aRow.push(      '<a class="carousel-control right" href="#myCarousel" data-slide="next">&rsaquo;</a>');
    aRow.push(  '</div>');
    aRow.push(  '</fieldset>');
    aRow.push(  '</div>');

    $('#productItemBody').html(aRow.join("")).show();

    var aRowF = [];
    aRowF.push('<a class="btn btn-small btn-primary pull-right" href="#"><i class="icon-shopping-cart icon-white"></i> Add Product</a>');
    $('.span8 footer').html(aRowF.join("")).show();
};

OrderEntry_Class.prototype.renderBASelect = function()
{
    var that = this;
    var aRow = [];
    var aBA = this.panelObj;
    var iL = this.panelObj.length;

    if (iL > 0)
    {
        aRow.push('<select id="inputBA" class="span12">');
        for (var i = 0; i < iL; i++)
        {
            aRow.push('<option value="' + aBA[i].billingAccountId + '">' + aBA[i].billingAccountId + '</option>');
        }
        aRow.push('</select>');
    }
    else
    {
        aRow.push('<input class="span12" type="text" id="inputBA" placeholder="ba">');
    }
    return aRow.join("");
};

OrderEntry_Class.prototype.renderProductDetails1 = function(udacName)
{
    var that = this;
    var aRow = [];

    aRow.push('<div class="accordion" id="accordion2">');
    aRow.push(  '<div class="accordion-group">');
    aRow.push(      '<div class="accordion-heading">');
    aRow.push(          '<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseOne">');
    aRow.push(              'General');
    aRow.push(          '</a>');
    aRow.push(      '</div>');
    aRow.push(      '<div id="collapseOne" class="accordion-body collapse">');
    aRow.push(          '<div class="accordion-inner">');
    aRow.push(              '<div class="span4">');
    aRow.push(                  '<form class="form-horizontal">');
    aRow.push(                      '<div class="control-group">');
    aRow.push(                          '<label class="control-label span6" for="inputBrand">Brand</label>');
    aRow.push(                          '<div class="controls span6">');
    aRow.push(                              '<input class="span10" type="text" id="inputBrand" placeholder="Brand">');
    aRow.push(                          '</div>');
    aRow.push(                      '</div>')
    aRow.push(                      '<div class="control-group">');
    aRow.push(                          '<label class="control-label span6" for="inputOffer">Offer</label>');
    aRow.push(                          '<div class="controls span6">');
    aRow.push(                              '<input class="span10" type="text" id="inputOffer" placeholder="Offer">');
    aRow.push(                          '</div>');
    aRow.push(                      '</div>');
    aRow.push(                      '<div class="control-group">');
    aRow.push(                          '<label class="control-label span6" for="inputRT">Relation Type</label>');
    aRow.push(                          '<div class="controls span6">');
    aRow.push(                              '<input class="span10" type="text" id="inputRT" placeholder="">');
    aRow.push(                          '</div>');
    aRow.push(                      '</div>');
    aRow.push(                      '<div class="control-group">');
    aRow.push(                          '<label class="control-label span6" for="inputPS">Product Status</label>');
    aRow.push(                          '<div class="controls span6">');
    aRow.push(                              '<input class="span10" type="text" id="inputPS" placeholder="">');
    aRow.push(                          '</div>');
    aRow.push(                      '</div>');
    aRow.push(                      '<div class="control-group">');
    aRow.push(                          '<label class="control-label span6" for="inputRSD">Req. Start Date</label>');
    aRow.push(                          '<div class="controls span6">');
    aRow.push(                              '<input class="span10" type="text" id="inputRSD" placeholder="">');
    aRow.push(                          '</div>');
    aRow.push(                      '</div>');
    aRow.push(                      '<div class="control-group">');
    aRow.push(                          '<label class="control-label span6" for="inputASD">Actual Start Date</label>');
    aRow.push(                          '<div class="controls span6">');
    aRow.push(                              '<input class="span10" type="text" id="inputASD" placeholder="">');
    aRow.push(                          '</div>');
    aRow.push(                      '</div>');
    aRow.push(                   '</form>');
    aRow.push(              '</div>');

    aRow.push(              '<div class="span4">');
    aRow.push(                  '<form class="form-horizontal">');
    aRow.push(                      '<div class="control-group">');
    aRow.push(                          '<label class="control-label span6" for="inputHeading">Heading</label>');
    aRow.push(                          '<div class="controls span6">');
    aRow.push(                              '<input class="span10" type="text" id="inputHeading" placeholder="">');
    aRow.push(                          '</div>');
    aRow.push(                      '</div>')
    aRow.push(                      '<div class="control-group">');
    aRow.push(                          '<label class="control-label span6" for="inputBA">Billing Account</label>');
    aRow.push(                          '<div class="controls span6">');
    aRow.push(                              '<input class="span10" type="text" id="inputBA" placeholder="">');
    aRow.push(                          '</div>');
    aRow.push(                      '</div>');
    aRow.push(                      '<div class="control-group">');
    aRow.push(                          '<label class="control-label span6" for="inputDuration">Duration</label>');
    aRow.push(                          '<div class="controls span6">');
    aRow.push(                              '<input class="span10" type="text" id="inputDuration" placeholder="">');
    aRow.push(                          '</div>');
    aRow.push(                      '</div>');
    aRow.push(                      '<div class="control-group">');
    aRow.push(                          '<label class="control-label span6" for="inputOI">Order ID</label>');
    aRow.push(                          '<div class="controls span6">');
    aRow.push(                              '<input class="span10" type="text" id="inputOI" placeholder="">');
    aRow.push(                          '</div>');
    aRow.push(                      '</div>');
    aRow.push(                      '<div class="control-group">');
    aRow.push(                          '<label class="control-label span6" for="inputRED">Requested End Date</label>');
    aRow.push(                          '<div class="controls span6">');
    aRow.push(                              '<input class="span10" type="text" id="inputRED" placeholder="">');
    aRow.push(                          '</div>');
    aRow.push(                      '</div>');
    aRow.push(                      '<div class="control-group">');
    aRow.push(                          '<label class="control-label span6" for="inputAED">Actual End Date</label>');
    aRow.push(                          '<div class="controls span6">');
    aRow.push(                              '<input class="span10" type="text" id="inputAED" placeholder="">');
    aRow.push(                          '</div>');
    aRow.push(                      '</div>');
    aRow.push(                   '</form>');
    aRow.push(              '</div>');

    aRow.push(              '<div class="span4">');
    aRow.push(                  '<form class="form-horizontal">');
    aRow.push(                      '<div class="control-group">');
    aRow.push(                          '<label class="control-label span6" for="inputHeading">Product</label>');
    aRow.push(                          '<div class="controls span6">');
    aRow.push(                              '<strong>Bold List. for WP</strong>');
    aRow.push(                          '</div>');
    aRow.push(                      '</div>')
    aRow.push(                      '<div class="control-group">');
    aRow.push(                          '<label class="control-label span6" for="inputRP">Related Product</label>');
    aRow.push(                          '<div class="controls span6">');
    aRow.push(                              '<input class="span10" type="text" id="inputRP" placeholder="">');
    aRow.push(                          '</div>');
    aRow.push(                      '</div>');
    aRow.push(                      '<div class="control-group">');
    aRow.push(                          '<label class="control-label span6" for="inputTU">TermTU Unit</label>');
    aRow.push(                          '<div class="controls span6">');
    aRow.push(                              '<input class="span10" type="text" id="input" placeholder="">');
    aRow.push(                          '</div>');
    aRow.push(                      '</div>');
    aRow.push(                      '<div class="control-group">');
    aRow.push(                          '<label class="control-label span6" for="inputStatus">Order Status</label>');
    aRow.push(                          '<div class="controls span6">');
    aRow.push(                              '<input class="span10" type="text" id="inputStatus" placeholder="">');
    aRow.push(                          '</div>');
    aRow.push(                      '</div>');
    aRow.push(                      '<div class="control-group">');
    aRow.push(                          '<label class="control-label span6" for="inputRCD">Req. Cancel Date</label>');
    aRow.push(                          '<div class="controls span6">');
    aRow.push(                              '<input class="span10" type="text" id="inputRCD" placeholder="">');
    aRow.push(                          '</div>');
    aRow.push(                      '</div>');
    aRow.push(                      '<div class="control-group">');
    aRow.push(                          '<label class="control-label span6" for="inputSD">Seniority Date</label>');
    aRow.push(                          '<div class="controls span6">');
    aRow.push(                              '<input class="span10" type="text" id="inputSD" placeholder="">');
    aRow.push(                          '</div>');
    aRow.push(                      '</div>');
    aRow.push(                   '</form>');
    aRow.push(              '</div>');

    aRow.push(          '</div>');
    aRow.push(      '</div>');
    aRow.push(  '</div>');

    aRow.push(  '<div class="accordion-group">');
    aRow.push(      '<div class="accordion-heading">');
    aRow.push(          '<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">');
    aRow.push(              'Price Parameters');
    aRow.push(          '</a>');
    aRow.push(      '</div>');
    aRow.push(      '<div id="collapseTwo" class="accordion-body collapse">');
    aRow.push(          '<div class="accordion-inner">');
    aRow.push(              'Price Parameters data...');
    aRow.push(          '</div>');
    aRow.push(      '</div>');
    aRow.push(  '</div>');
    aRow.push(  '<div class="accordion-group">');
    aRow.push(      '<div class="accordion-heading">');
    aRow.push(          '<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseThree">');
    aRow.push(              'Product Appearance');
    aRow.push(          '</a>');
    aRow.push(      '</div>');
    aRow.push(      '<div id="collapseThree" class="accordion-body collapse">');
    aRow.push(          '<div class="accordion-inner">');
    aRow.push(              'Product Appearance data...');
    aRow.push(          '</div>');
    aRow.push(      '</div>');
    aRow.push(  '</div>');
    aRow.push(  '<div class="accordion-group">');
    aRow.push(      '<div class="accordion-heading">');
    aRow.push(          '<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseFour">');
    aRow.push(              'Product Attributes');
    aRow.push(          '</a>');
    aRow.push(      '</div>');
    aRow.push(      '<div id="collapseFour" class="accordion-body collapse">');
    aRow.push(          '<div class="accordion-inner">');
    aRow.push(              'Product Attributes data...');
    aRow.push(          '</div>');
    aRow.push(      '</div>');
    aRow.push(  '</div>');
    aRow.push(  '<div class="accordion-group">');
    aRow.push(      '<div class="accordion-heading">');
    aRow.push(          '<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseFive">');
    aRow.push(              'Assignment Info');
    aRow.push(          '</a>');
    aRow.push(      '</div>');
    aRow.push(      '<div id="collapseFive" class="accordion-body collapse">');
    aRow.push(          '<div class="accordion-inner">');
    aRow.push(              'Assignment Info data...');
    aRow.push(          '</div>');
    aRow.push(      '</div>');
    aRow.push(  '</div>');
    aRow.push('</div>');

    $('#productItemBody').html(aRow.join("")).show();


    var aRowF = [];
    aRowF.push('<a class="btn btn-small btn-primary pull-right" href="#"><i class="icon-shopping-cart icon-white"></i> Add Product</a>');
    $('.span8 footer').html(aRowF.join("")).show();
};

OrderEntry_Class.prototype.setProductsTableEvents = function(i)
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



OrderEntry_Class.prototype.showConfirmBackAction = function(sURL)
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

OrderEntry_Class.prototype.showActiveView = function(sType)
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

OrderEntry_Class.prototype.renderESign = function(oPanel)
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

OrderEntry_Class.prototype.renderROP = function(oPanel)
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

OrderEntry_Class.prototype.renderCredit = function(oPanel)
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

OrderEntry_Class.prototype.showActivePanel = function(sTab)
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

OrderEntry_Class.prototype.renderCustDetails = function(oPanel)
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


OrderEntry_Class.prototype.renderCustNotes = function(aResult)
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

OrderEntry_Class.prototype.setCustNotesEvents = function()
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

OrderEntry_Class.prototype.toggleNoteContent = function(el)
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

OrderEntry_Class.prototype.loadMap = function(sAddr)
{
    this.elLoader.show();
    console.log("loadMap :: BEFORE loadGoogleMap --- .. sAddr = ",sAddr,"this.iLatitude=",this.iLatitude,"this.iLongitude=",this.iLongitude);
    var iLatitude = this.iLatitude ? this.iLatitude : 0;
    var iLongitude = this.iLongitude ? this.iLongitude : 0;
    this.mapHandler.loadGoogleMap(sAddr, iLatitude, iLongitude);
};

OrderEntry_Class.prototype.loadMapSubject = function()
{
	$(".subjectName").html(this.title);
}


