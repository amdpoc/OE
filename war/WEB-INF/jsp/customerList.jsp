<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

<link href="css/theme.css" rel="stylesheet" media="screen">
<link href="css/panels.css" rel="stylesheet" media="screen">
<link href="css/formElements.css" rel="stylesheet" media="screen">

<link href="css/ui.css" rel="stylesheet" media="screen">
<link href="css/ui-local.css" rel="stylesheet" media="screen">
<link href="css/ui-responsive.css" rel="stylesheet">
<!--link href="css/ui-lightness/jquery-ui-1.8.5.custom.css" rel="stylesheet" type="text/css" />
<link href="css/ui-lightness/ui-local.css" rel="stylesheet" type="text/css" /-->
<style type="text/css">
.navbar-fixed-top .container {
    width: auto;
    text-align: center;
}
</style>

<script src="dwr/engine.js" type="text/javascript"></script>
<script src="dwr/util.js" type="text/javascript"></script>
<script src="dwr/interface/DwrCustListManager.js" type="text/javascript"></script>

<!--script src="http://maps.google.com/maps/api/js?sensor=true&region=USA" type="text/javascript"></script-->
<!--script src="javascript/jquery-1.4.2.min.js" type="text/javascript" charset="utf-8"></script-->

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="javascript/ui.js"></script>

<!--script src="javascript/jquery-ui-1.8.5.custom.min.js" type="text/javascript"></script-->
<!--script src="javascript/googleMapClass.js" type="application/x-javascript" charset="utf-8"></script-->

<script src="javascript/utilsClass.js" type="application/x-javascript" charset="utf-8"></script>
<script src="javascript/modalDialog.js"></script>
<script src="javascript/infoUtil.js"></script>

<script type="text/javascript" charset="utf-8">

var prevPage = "${custListModel.prevPage}";
var totalPagesNum = new Number('${custListModel.totalPagesNum}');
var currentPageNum = new Number('${custListModel.currentPageNum}');
var pageSize = new Number('${custListModel.pageSize}');
var totalRecordsNum = new Number('${custListModel.totalRecordsNum}');
var maxRecordsNumber = new Number('${custListModel.maxRecordsNumber}');
var resultList = new Object(${custListModel.resultList});
var searchCriteria = ${custListModel.searchCriteria};
var docusignResponse = new Object(${custListModel.docusignResponse});
var generalInfo = new Object(${custListModel.generalInfo});

console.log("docusignResponse = ",docusignResponse);
console.log("prevPage = ",prevPage,"totalPagesNum=",totalPagesNum,"currentPageNum=",currentPageNum,"pageSize=",pageSize,"totalRecordsNum=",totalRecordsNum,"maxRecordsNumber=",maxRecordsNumber,"resultList=",resultList);
console.log("**** generalInfo = ",generalInfo);

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
<script src="javascript/customerList.js"></script>
<script src="javascript/eSignResponseModule.js"></script>
</head>

<body>

	<div id="loader" class="loader none">&nbsp;</div>
	<div id="home" class="current">

		<div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <ul class="nav secondary-nav pull-right">
                        <li id="welcome" class="border-right">
                            <a href="javascript:void(0);">Welcome <span id="user_name">User</span></a>
                        </li>
                        <li class="marginh10 marginv3">
                            <a class="btn btn-small" id="logout" href="javascript:void(0);"><i class="icon-off icon-white"></i>  Sign out</a>
                        </li>
                      </ul>
                </div>
            </div>
        </div>

		<div class="container-fluid side-bar">
            <div class="row-fluid">
                <div class="span4">
                    <form id="searchForm" name="searchForm" class="loginForm" selected="true">
                        <fieldset>
                            <legend>Customer Search</legend>
                            <label>Customer ID</label>
                            <input id="customerId" class="input-block-level" value="" autocorrect="off" type="text" autocapitalize="off" pattern="\d*" placeholder="Customer ID">
                            <a class="btn only-icon pull-right" id="clearCustIdSearch" href="javascript:void(0);"><i class="icon-refresh"></i></a>
                            <label>Customer Name</label>
                            <input id="customerName" class="input-block-level" value="" autocorrect="off" type="text" autocapitalize="off" placeholder="Customer Name">
                            <a class="btn only-icon pull-right" id="clearNameSearch" href="javascript:void(0);"><i class="icon-refresh"></i></a>
                            <label>Phone</label>
                            <input class="phone input-block-level" id="custNpa" type="tel" size="3" autocorrect="off" maxlength="3">
                            <input class="phone input-block-level" id="custCop" type="tel" size="3" autocorrect="off" maxlength="3">
                            <input class="phone input-block-level" id="custLineNo" type="tel" size="4" autocorrect="off" maxlength="4">
                            <a class="btn only-icon pull-right" id="clearPhoneSearch" href="javascript:void(0);"><i class="icon-refresh"></i></a>

                            <hr>
                            <a class="btn btn-primary none" id="submitSearch" href="javascript:void(0);"><i class="icon-search icon-white"></i> Search</a>
                            <a class="btn btn-primary disabled" id="submitSearch_passive" href="javascript:void(0);">Search</a>
                            <a class="btn pull-right" id="clearAll" href="javascript:void(0);"><i class="icon-refresh"></i> Clear All</a>
                        </fieldset>
                    </form>
                </div>
                <div class="span8 main-panel full-height">
                    <div class="none paging" id="paging">
                        <div class="section left" id="custPrev">
                            <a class="btn btn-primary only-icon pull-left" id="custPrevBtn" href="javascript:void(0);"><i class="icon-backward icon-white"></i> </a>
                        </div>
                        <div class="section center" id="custCurrent"></div>
                        <div class="section right" id="custNext">
                            <a class="btn btn-primary only-icon pull-right" id="custNextBtn" href="javascript:void(0);"><i class="icon-forward icon-white"></i></a>
                        </div>
                    </div>

                    <div id="custLookupScroller" class="scroller_native paged_content">
                        <div class="scroller_content">
                            <ul class="suggestionList width100" id="lookupCustomerList"></ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- MASK -->
    <div class="mask" id="mask">&nbsp;</div>

    <!-- MODAL DIALOG -->
	<%@ include file="modalDialog.jsp" %>
</body>
</html>
