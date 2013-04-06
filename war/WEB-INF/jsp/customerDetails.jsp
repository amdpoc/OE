<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
<title><fmt:message key="title"/></title>
<meta charset="UTF-8"/>
<meta content="yes" name="apple-mobile-web-app-capable"/>
<meta name="apple-mobile-web-app-status-bar-style" content="black"/>
<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;"/>
<meta name="format-detection" content="telephone=no"/>
<link rel="apple-touch-startup-image" href="images/iLink_splash_new_icon01.png"/>
<link rel="apple-touch-icon" href="images/eSignIcon.png"/>

<!-- Change #Mati - Start - Added for Product Entry -->
<link href="css/productEntry.css" rel="stylesheet" media="screen">
<!-- Change #Mati - End -->
<link href="css/theme.css" rel="stylesheet" media="screen">
<link href="css/panels.css" rel="stylesheet" media="screen">
<link href="css/ui.css" rel="stylesheet" media="screen">
<link href="css/ui-local.css" rel="stylesheet" media="screen">
<link href="css/ui-responsive.css" rel="stylesheet">
<style type="text/css">
.navbar-fixed-top .container {
    width: auto;
    text-align: center;
}
</style>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="javascript/ui.js"></script>

<link type="text/css" rel="stylesheet" href="css/theme.css" />
<link type="text/css" rel="stylesheet" href="css/panels.css" />
<link type="text/css" rel="stylesheet" href="css/bubble.css" />
<link href="css/ui-lightness/jquery-ui-1.10.2.autocomplete.min.css" rel="stylesheet" type="text/css" />
<link href="css/ui-lightness/jquery-ui-1.8.5.custom.css" rel="stylesheet" type="text/css" />
<link href="css/ui-lightness/ui-local.css" rel="stylesheet" type="text/css" />

<!--script src="javascript/jquery-ui-1.8.5.custom.min.js" type="text/javascript"></script-->
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>

<script src="javascript/utilsClass.js" type="application/x-javascript"></script>
<script src="javascript/designPatterns.js"></script>
<script src="javascript/envelope.js"></script>
<script src="javascript/proposalsTopBarModule.js"></script>
<script src="javascript/modalDialog.js"></script>

<script src="dwr/engine.js" type="text/javascript"></script>
<script src="dwr/util.js" type="text/javascript"></script>
<script src="dwr/interface/DwrCustManager.js" type="text/javascript"></script>
<script src="dwr/interface/DwrCustNotesManager.js" type="text/javascript"></script>
<script src="dwr/interface/DwrProposalManager.js" type="text/javascript"></script>
<script src="dwr/interface/DwrPDFConverter.js" type="text/javascript"></script>
<script src="dwr/interface/DwrPdfGeneratorManager.js" type="text/javascript"></script>
<script src="dwr/interface/DwrSignatureManager.js" type="text/javascript"></script>
<!-- Change #Mati - Start - Added for Product Entry -->
<script src="javascript/productEntryGeneral.js"></script>
<script src="javascript/productEntryAttributes.js"></script>
<script src="javascript/productEntryAppearanceContent.js"></script>
<script src="javascript/productEntryAppearances.js"></script>
<script src="javascript/productEntryUtility.js"></script>
<script src="javascript/productEntryDropDown.js"></script>
<script src="javascript/productEntryInputData.js"></script>
<script src="javascript/productEntry.js"></script>
<!-- Change #Mati - End -->
<script type="text/javascript" charset="utf-8">

var showHome = false;
var prevPage = "${custDetailsModel.prevPage}";

var pageId = "${custDetailsModel.pageId}";
var panelId = "${custDetailsModel.panelId}";
var oPanel = new Object(${custDetailsModel.panelObj});
var proposalIDeSign = "${custDetailsModel.proposalIDeSign}";
var proposalIDrecord = "${custDetailsModel.proposalIDrecord}";
var custId = "${custDetailsModel.custId}";
var title = "${custDetailsModel.title}";
var contactEmail = "${custDetailsModel.contactEmail}"
var contactName = "${custDetailsModel.contactName}"
var enableTransFlag = ${custDetailsModel.enableTransFlag};
var readOnly = ${custDetailsModel.readOnly};
var generalInfo = new Object(${custDetailsModel.generalInfo});

console.log("**** generalInfo = ",generalInfo);
console.log("**** oPanel = ",oPanel);
console.log("**** panelId = ",panelId);

$(function()
	{
	    $('body').bind('turn', function(e, data)
	    	{
    	    	$('#orient').html('Orientation: ' + data.orientation);
    		}
	    );
	}
);
</script>
<script src="javascript/infoUtil.js"></script>

<script src="javascript/prdMultiLookUpClass.js" type="text/javascript"></script>
<script src="javascript/envelope.js" type="text/javascript"></script>
<script src="javascript/productList.js" type="text/javascript"></script>
<script src="javascript/shoppingCart.js" type="text/javascript"></script>
<script src="javascript/oe2.js" type="text/javascript"></script>
</head>

<body>
	<div id="loader" class="loader">&nbsp;</div>
	<div id="home" class="current">
		<div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <ul class="nav secondary-nav pull-right">
                        <li id="welcome" class="border-right">
                            <a href="javascript:void(0">Welcome <span id="user_name">User</span></a>
                        </li>
                        <li class="marginh10 marginv3">
                            <a class="btn btn-small" id="logout" href="javascript:void(0"><i class="icon-off icon-white"></i>  Sign out</a>
                        </li>
                      </ul>
                </div>
            </div>
        </div>

        <div class="container-fluid side-bar">
            <div class="row-fluid">
                <div id="leftPanelPedal" class="btn-group pull-left panel-pedal">
                    <a class="btn alone" href="#"><i class="icon-th"></i></a>
                </div>
                <div id="rightPanelPedal" class="btn-group pull-right panel-pedal">
                    <a class="btn alone" href="#"><i class="icon-th"></i></a>
                </div>

                <!-- LEFT PANEL -->
                <div id="leftPanel" class="span4 full-height left-panel mleft0">

                    <form id="shoppingCart" name="selectedProducts" class="form-horizontal pad10 cart-inner height85" selected="true">

                        <legend>
                            Selected Products
                        </legend>

                        <div class="control-group">
                            <label class="control-label">Group By</label>
                            <div class="controls pull-left">
                                <select id="groupBy">
                                    <option value="media">Media</option>
                                    <option value="productType">Product Type</option>
                                    <option value="categoryHeading">Category / Heading</option>
                                    <option value="location">Location</option>
                                    <option value="appearance">Appearance</option>
                                </select>
                            </div>
                        </div>
                        <hr>
                        <div class="height88 droppable" id="shoppingCartBody"></div>
                    </form>

                    <div id="shoppingFooter" class="pull-to-bottom cart-inner">
                    Total:
                    </div>
                </div>

                <!-- RIGHT PANEL -->
                <div id="rightPanel" class="span8 main-panel full-height pull-right pad10">

                    <div class="carousel-inner height95">

                        <div class="item height100 active" id="Step2">
                            <form id="searchForm2" name="searchForm2" class="form-horizontal pad10 form-inline height100" selected="true">

                                <div class="control-group" id="customerInfo">
                                    <label class="control-label">Customer Name:</label>
                                    <div class="controls pull-left title" id="customerName"></div>
                                </div>
                                <div class="control-group" id="oeInfo">
                                    <label class="control-label">Billing Account:</label>
                                    <div class="controls pull-left">
                                        <select id="billingAccountSelect">
                                            <option value="300390940">300390940</option>
                                            <option value="300391579">300391579</option>
                                        </select>
                                    </div>
                                </div>
                                <hr>

                                <!--
                                <ul class="nav nav-tabs nb" id="myTab">
                                  <li><a href="#current" class="left-tab" data-toggle="tab">Used by Current</a></li>
                                  <li><a href="#recommended" class="middle-tab" data-toggle="tab">Recommended</a></li>
                                  <li class="active"><a href="#all" class="right-tab" data-toggle="tab">All</a></li>
                                </ul>
                                -->
                                <div class="control-group">
                                    <label class="control-label">Search Product</label>
                                    <div class="controls pull-left">
                                        <input id="prdInputString" class="input-block-level width50" value="" autocorrect="off" type="text" autocapitalize="off">
                                        <a class="btn btn-small only-icon" id="prdLookupBtn" billing_account_id="0" href="javascript:void(0)"><i class="icon-search"></i></a>
                                    </div>

                                    <label class="control-label">Select Media</label>
                                    <div class="controls pull-left">
                                        <select id="mediaSelect">
                                            <option value="ALL">All</option>
                                        </select>
                                    </div>

                                    <div class="controls pull-right">
                                        <a class="btn btn-small" id="clearFilter" href="javascript:void(0)"><i class="icon-remove marginr5"></i>Clear Filters</a>
                                    </div>
                                </div>
                                <hr>

                                <div class="tab-content height70">
                                  <div class="tab-pane" id="currentProductsTab">Used by Current ...</div>
                                  <div class="tab-pane" id="recommendedProductsTab">Recommended ...</div>
                                  <div class="tab-pane active over-y-auto" id="allProductsTab"></div>
                                </div>

                            </form>
                        </div>

                    </div>

                </div>
            </div>
        </div>
	</div>
	  
    <!-- MASK -->
    <div class="mask" id="mask">&nbsp;</div>

    <!-- PRODUCT ATTRIBUTES -->
    <div id="prdAttributes" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
        <h4 id="modalHeader"></h4>
      </div>
      <div class="modal-body" id="modalBody">
        <div id="productEntryPage"></div>
      </div>
      <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
        <button id="addToCartBtn" class="btn btn-primary">Add to Cart</button>
      </div>
    </div>
    
    <!-- MODAL DIALOG -->
	<%@ include file="modalDialog.jsp" %>	        
   
    <!-- MAP -->
    <%@ include file="mapFrame.jsp" %>	        


</body>
</html>