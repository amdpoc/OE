<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<div id="mapFrame" class="mapFrame wrapper none">
	<div class="mainGradientMask"></div>
	<div class="toolbarTop toolbar">
	    <div class="fleft border_buttons">&nbsp;</div>
	   	<div class="topBarControls">
	    	<button class="ui-button ui-state-default ui-corner-left ui-button-text-icon-primary fleft" role="button" area-disabled="false" id="backMapBtn">
	    		<span class="ui-icon ui-icon-triangle-1-w"></span>
	    		<span class="ui-button-text">Back</span>
	    	</button>
	    	<button class="ui-button ui-state-default ui-corner-right ui-button-text-icon-primary fleft" role="button" area-disabled="false" id="directionsId">
	    		<span class="ui-icon ui-icon-arrow-4"></span>
	    		<span class="ui-button-text">Directions</span>	
	    	</button>
	   	</div>
	    <div class="subjectName fleft"></div>
	    <div class="fright border_buttons">&nbsp;</div>
	    <div class="destinationAddress fright"></div>
	</div>
	<div class="mapWrapper">        
		<div id="map_canvas" class="mapCanvas"></div>
	</div>
</div>

