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
   	this.elModalWindow = $("#prdAttributes");
   	this.aFilteredProducts = [];


    this.setCustomerInfo();
    this.renderMediaTypesSelect();
    this.getProductListObject();
	this.renderRightPanel();
    this.setEvents();
    this.createShoppingCart();
};

OrderEntry_Class.prototype.setCustomerInfo = function()
{
    console.log("*** setCustomerInfo ***");
    var that = this;
    if (this.oPageState && this.oPageState.title && this.oPageState.title != "")
    {
        $("#customerName").text(this.oPageState.title);
    }
    else
    {
        console.log("TO DO !! setCustomerInfo :: NO CUSTOMER DATA");
    }

    // *** BILLING ACCOUNTS

    //var callback = function(aResult)
      //  {
        //    console.log("******** renderProposal callback :: aResult = ",aResult, "custId = ", that.custId);
            /*
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
            */
        //};

    //var iCustId = parseInt(that.custId,10);
    //DwrProposalManager.getProposalByCustomerId("esign", iCustId, 0, callback);

};

OrderEntry_Class.prototype.renderMediaTypesSelect = function()
{
    console.log("*** renderMediaTypesSelect ***");

    var a = this.getMediaTypes();
    var aOptions = [];
    for (var i = 0, iL = a.length; i < iL; i++)
    {
        aOptions.push('<option value="' + a[i].mediaType + '">' + a[i].mediaTypeName + '</option>');
    }
    $("#mediaSelect").append(aOptions.join(""));
};

OrderEntry_Class.prototype.getProductListObject = function()
{
    this.oProductList = productList;
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

OrderEntry_Class.prototype.renderRightPanel = function()
{
    this.setSearchFilterEvents();
    this.setFilteredProductList();
    this.renderProducts();
};

OrderEntry_Class.prototype.setSearchFilterEvents = function()
{
    var that = this;
    $("#prdInputString")
        .autocomplete(
        {
            minLength: 0,
            source:function(request, response)
            {
                response($.ui.autocomplete.filter(that.getSourceForAutocomplete()), request.term)
            },
            response:function(event,ui)
            {
                var aFoundAutocomplete = ui.content;
                that.renderProductsFromAutocomplete(aFoundAutocomplete);
            },
            select:function(event,ui)
            {
                var aFoundAutocomplete = [];
                aFoundAutocomplete.push(ui.item);
                that.renderProductsFromAutocomplete(aFoundAutocomplete);
            }
        });

    $("#mediaSelect")
        .change(function()
        {
            var sSearchValue = $("#prdInputString").val();
            that.setFilteredProductList();
            if (sSearchValue == "")
            {
                that.renderProducts();
            }
            else
            {
                var sSearchValue = $("#prdInputString").val();
                console.log("onMediaSelect :: sSearchValue = ",sSearchValue);
                $("#prdInputString").autocomplete("search", sSearchValue);
                $("#prdInputString").autocomplete("close");
            }
            console.log("SELECTED :: value = ",$(this).val());
        });

    $("#clearFilter")
        .click(function()
        {
            $("#prdInputString").val("");
            $("#mediaSelect").val("ALL");
            that.setFilteredProductList();
            that.renderProducts();
        });
};

OrderEntry_Class.prototype.getSourceForAutocomplete = function()
{
    console.log("getSourceForAutocomplete :: that.getProductNamesArray(that.getProductsArray($('#mediaSelect').val())) = ",that.getProductNamesArray(that.getProductsArray($("#mediaSelect").val())));
    this.getProductNamesArray(this.getProductsArray($("#mediaSelect").val()))
};

OrderEntry_Class.prototype.renderProductsFromAutocomplete = function(aFoundAutocomplete)
{
    //console.log("### renderProductsFromAutocomplete response :: ui = ",aFoundAutocomplete);
    var aFoundProduct = this.getProductsArrayByNames(aFoundAutocomplete);
    console.log("BEFORE renderProductsList ... aFoundProduct = ",aFoundProduct);

    this.setFilteredProductList(aFoundProduct);
    this.renderProducts();
    this.setProductListEvents();
};

OrderEntry_Class.prototype.renderProducts = function()
{
    var aProductRows = [];
    var aProducts = this.aFilteredProducts;
    var iL = aProducts.length;
    if (iL > 0)
    {
        for (var i = 0; i < iL; i++)
        {
            var oProduct = aProducts[i];
            var sName = oProduct.productName;
            var sCode = oProduct.productCode;
            var sMediaType = oProduct.mediaType;
            var sID = sMediaType + "_" + sCode;
            var sIcon = this.getIcon(oProduct.mediaType);
            var productData = '{"name":"' + sName + '","attr":"' + sCode + '"}';

            //aProductRows.push('<div class="span12 product-row draggable mleft0" id="' + sID + '" pr-name="' + sName + '" attr="' + sCode +'">');
            //aProductRows.push('<div class="span12 product-row draggable mleft0" data-product={"name":"' + sName + '","attr":"' + sCode +'"}>');
            aProductRows.push("<div class='span12 product-row draggable mleft0' data-product='" + productData + "'>");
            aProductRows.push(  '<i class="' + sIcon + '"></i>');
            aProductRows.push(  sName);
            aProductRows.push(  '<i class="icon-plus fright product-plus"></i>');
            aProductRows.push(  '<i class="icon-minus fright product-minus none"></i> ');
            aProductRows.push(  '<i class="icon-edit fright product-edit none"></i> ');
            aProductRows.push('</div>');
        }
        //console.log("... aProductRows = ",aProductRows);
        $('#allProductsTab').empty().append(aProductRows.join(""));
        this.setProductListEvents();
    }
    else
    {
        $('#allProductsTab').empty();
    }
};

OrderEntry_Class.prototype.setFilteredProductList = function(aProducts)
{
    var that = this;
    var aMediaProducts = [];
    var sMedia =  $("#mediaSelect").val();
    aMediaProducts = this.getProductsArray(sMedia);
    if (aProducts)
    {
        this.aFilteredProducts = aProducts;
    }
    else
    {
        this.aFilteredProducts = aMediaProducts;
    }

    // *** RESET AUTOCOMPLETE SOURCE ATTRIBUTE
    $("#prdInputString").autocomplete("option","source",that.getProductNamesArray(aMediaProducts));
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

// *** SET EVENTS

OrderEntry_Class.prototype.setEvents = function()
{
    var that = this;

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
            that.renderProductsByMedia(sMedia);
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

    // *** MODAL WINDOW
    $("#addToCartBtn")
        .click(function()
        {
            console.log("**** addToCartBtn clicked :: callerElement = ",that.pe.getCallerElement());

            that.addProductAttributes(that.pe.getCallerElement());
            that.elModalWindow.modal('hide');
        });
};

OrderEntry_Class.prototype.setSearchFilterEvents = function()
{
    var that = this;
    $("#prdInputString")
        .autocomplete(
        {
            minLength: 0,
            source:function(request, response)
            {
                response($.ui.autocomplete.filter(that.getSourceForAutocomplete()), request.term)
            },
            response:function(event,ui)
            {
                var aFoundAutocomplete = ui.content;
                that.renderProductsFromAutocomplete(aFoundAutocomplete);
            },
            select:function(event,ui)
            {
                var aFoundAutocomplete = [];
                aFoundAutocomplete.push(ui.item);
                that.renderProductsFromAutocomplete(aFoundAutocomplete);
            }
        });

    $("#mediaSelect")
        .change(function()
        {
            var sSearchValue = $("#prdInputString").val();
            that.setFilteredProductList();
            if (sSearchValue == "")
            {
                that.renderProducts();
            }
            else
            {
                var sSearchValue = $("#prdInputString").val();
                console.log("onMediaSelect :: sSearchValue = ",sSearchValue);
                $("#prdInputString").autocomplete("search", sSearchValue);
                $("#prdInputString").autocomplete("close");
            }
            console.log("SELECTED :: value = ",$(this).val());
        });

    $("#clearFilter")
        .click(function()
        {
            $("#prdInputString").val("");
            $("#mediaSelect").val("ALL");
            that.setFilteredProductList();
            that.renderProducts();
        });
};

OrderEntry_Class.prototype.setProductListEvents = function()
{
    var that = this;
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

    $(".product-plus")
        .click(function()
            {
                //that.addProductAttributes($(this).parent());
                that.openProductAttributesWindow($(this).parent());
            });
};

OrderEntry_Class.prototype.createShoppingCart = function()
{
    this.sc = new ShoppingCart_Class();
};

OrderEntry_Class.prototype.openProductAttributesWindow = function(elProductDiv)
{
    this.pe = new ProductEntry_Class();
    this.pe.setCallerElement(elProductDiv);

    var sProductName = elProductDiv.data("product").name;
    $('#modalHeader').text("Fill Attributes for \"" + sProductName + "\":");
    this.elModalWindow.modal('show').addClass("modal-local");
};

OrderEntry_Class.prototype.addProductAttributes = function(elProductDivOrig)
{
    var elProductDiv = elProductDivOrig.clone(true);
    elProductDiv.children(".product-minus").removeClass("none");
    elProductDiv.children(".product-edit").removeClass("none");
    elProductDiv.children(".product-plus").addClass("none");
    $("#shoppingCartBody").append(elProductDiv);
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

OrderEntry_Class.prototype.getProductsArray = function(sMediaType)
{
    if (!$.isEmptyObject(this.oProductList))
    {
        if (sMediaType == "ALL")
        {
            var a = [];
            for (var type in this.oProductList)
            {
                var aType = this.oProductList[type];
                for (var i = 0, iL = aType.length; i < iL; i++)
                {
                    a.push(aType[i]);
                }
            }
            //console.log("getProductsArray :: a = ", a);
            return a;
        }
        else
        {
            return this.oProductList[sMediaType];
        }
    }
    return [];
};

OrderEntry_Class.prototype.getProductNamesArray = function(aProducts)
{
    var aProductNames = [];
    for (var i = 0, iL = aProducts.length; i < iL; i++)
    {
        aProductNames.push(aProducts[i].productName);
    }
    return aProductNames;
};

OrderEntry_Class.prototype.getProductsArrayByNames = function(aFoundAutocomplete)
{
    var aAllProducts = this.getProductsArray("ALL");
    var aFilteredProducts = [];
    var iFoundProducts = aFoundAutocomplete.length;

    for (var i = 0, iL = aAllProducts.length; i < iL; i++)
    {
        // *** BREAK THE LOOP IF ALL PRODUCTS FROM aFoundAutocomplete ALREADY PUSHED TO THE RESULTING ARRAY aFilteredProducts
        if (aFilteredProducts.length == iFoundProducts)
        {
            return aFilteredProducts;
        }

        var sName = aAllProducts[i].productName;
        for (var j = 0; j < iFoundProducts; j++)
        {
            if (sName == aFoundAutocomplete[j].value)
            {
                aFilteredProducts.push(aAllProducts[i]);
                break;
            }
        }
    }
    console.log("getProductsArrayByNames :: aFoundAutocomplete = ",aFoundAutocomplete, " : aFilteredProducts = ",aFilteredProducts);
    return aFilteredProducts;
};

