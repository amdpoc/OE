<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<div id="modal_dialog" class="none ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable">
	<div class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix">
		<div class="fleft ui-state-highlight ui-corner-all gadgetExpandBtn expand_icon none"><span class="ui-icon ui-icon-carat-2-n-s"></span></div>
    	<span id="ui-dialog-title-dialog" class="ui-dialog-title"></span>
     	<a class="ui-dialog-titlebar-close ui-corner-all" href="javascript:void(0);"><span class="ui-icon ui-icon-closethick">close</span></a>
  	</div>
 	<div class="ui-dialog-content ui-widget-content" id="dialog"></div>
 	<div class="loader none" id="dialog_loader"></div>
</div>
