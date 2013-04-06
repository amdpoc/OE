<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="UTF-8"/>
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<link rel="apple-touch-startup-image" href="images/iLink_splash_new_icon01.png"/>
<link rel="apple-touch-icon" href="images/eSignIcon.png"/>

<title>Mobile eSign</title>
<style type="text/css" media="screen">@import "css/jqtouch.css";</style>
<style type="text/css" media="screen">@import "css/theme.css";</style>
<style type="text/css" media="screen">@import "css/panels.css";</style>
<style type="text/css" media="screen">@import "css/bubble.css";</style>
<style type="text/css" media="screen">@import "css/formElements.css";</style>
<link href="css/ui-lightness/jquery-ui-1.8.5.custom.css" rel="stylesheet" type="text/css" />
<link href="css/ui-lightness/ui-local.css" rel="stylesheet" type="text/css" />

<script src="javascript/jquery-1.4.2.min.js" type="text/javascript" charset="utf-8"></script>
<script src="javascript/jquery-ui-1.8.5.custom.min.js" type="text/javascript"></script>
<script src="javascript/jqtouch.js" type="text/javascript"></script>
<script src="dwr/engine.js" type="text/javascript"></script>
<script src="dwr/interface/DwrCustNotesManager.js" type="text/javascript"></script>
<script src="javascript/modalDialog.js"></script>
<script src="javascript/utilsClass.js" type="application/x-javascript"></script>

<script type="text/javascript" charset="utf-8">
var jQT = new $.jQTouch(
		{
            addGlossToIcon: false,
            statusBar: 'black'
		}
	);
	
var prevPage = "${entityDetails.prevPage}";
var custNotesList = new Object(${entityDetails.custNotesList})
var custId = new Number("${entityDetails.custId}");
var title = "${entityDetails.title}";

console.log("prevPage = ",prevPage,"custId = ",custId,"title = ",title,"custNotesList = ",custNotesList);

$(function() {
    // *** Orientation callback event
    $('body')
    	.bind('turn', function(e, data)
    		{
        		$('#orient').html('Orientation: ' + data.orientation);
    		}
    	);
	}
);
</script>
<script src="javascript/newNote.js"></script>
</head>

<body>
<div id="jqt">
	
	<div id="loader" class="loader">&nbsp;</div>
	<div id="home" class="current">
		<div class="toolbarTop toolbar">
            <div class="fleft border_buttons">&nbsp;</div>
           	<div class="topBarControls">
	           	<button class="ui-button ui-state-default ui-corner-left ui-button-text-icon-primary fleft" role="button" area-disabled="false" id="homeBtn" href="javascript:void(0);">
	           		<span class="ui-icon ui-icon-home"></span>
	           		<span class="ui-button-text">Home</span>	
	           	</button>
	           	<button class="ui-button ui-state-default ui-corner-right ui-button-text-icon-primary fleft" role="button" area-disabled="false" id="backBtn" href="javascript:void(0);">
	           		<span class="ui-icon ui-icon-triangle-1-w"></span>
	           		<span class="ui-button-text">Back</span>
	           	</button>
	        </div>
            <div class="subjectName fleft">New Note</div>
            <div class="fright border_buttons">&nbsp;</div>            
            <div class="topBarRightControls">
	           	<button class="ui-button ui-state-default ui-corner-left ui-corner-right ui-button-text-icon-primary fright" target="_webapp" role="button" area-disabled="false" id="logout" href="javascript:void(0);">
	           		<span class="ui-icon ui-icon-eject"></span>
	           		<span class="ui-button-text">Log out</span>	
	           	</button>
            </div>           
        </div>
	           
 	    <div id="wrapperId" class="full_screen_tab_wrapper">
    		
    		<div class="full_screen_tab_mask angled right2degrees"></div>
			<div class="full_screen_tab_mask angled left2degrees"></div>
			<div class="full_screen_tab_mask active_mask"></div>
			
			<div id="custDetails" class="full_screen_tab_panel_bar detailsFrame height84">

				<div class="panel_title">
				    <a  id="custTitle" class="underline">${entityDetails.title}</a>
                </div>
				<div id="emptySummary" class="panelMessage angled none top10">
					<img class="pin" src="images/Bg.Pin.gif"/><span>No Customer Notes found.</span>
				</div>
	
				<!-- NOTES LIST -->
				<div id="notes" class="details_block none">
				    <div id="scroller_notesList" class="scroller_native height75 width99">
				        <div id="notesListContent" class="scroller_content"></div>
				    </div>
				</div>
				
				<!-- NEW NOTE -->
				<div id="newNoteDiv" class="bottom listRow">
	                   <div class='detailsLabel groupLabel'>New Note Text:</div>
					<div class="detailsContent_textarea">
						<textarea id="newNote_text" class="width100" autocapitalize="on" maxlength="4000"></textarea>    
					</div>
				</div>								
			</div>
									
			<div class="full_screen_panel_bottomControl">
               	<div class="ui-state-highlight ui-corner-all4 fright btn-icon" id="closeNewNote">
					<span class="ui-icon ui-icon-cancel"></span>Close
				</div>
                <div class="ui-state-active ui-corner-all4 fright btn-icon" id="createNewNote">
					<span class="ui-icon ui-icon-disk"></span>Create
				</div>
                <div class="ui-state-passive ui-corner-all4 fright btn-icon" id="createNewNotePassive">
					<span class="ui-icon ui-icon-disk"></span>Create
				</div>
           	</div>			

		</div>
    
    	<!-- MASK -->
    	<div class="mask" id="mask">&nbsp;</div>
	</div>
</body>
</html>
