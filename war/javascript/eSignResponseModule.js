// *** Created by okotlia 09/30/2011
var eSignResponse;

$(function() 
	{
		eSignResponse = new eSignResponse_Class();
	}
);

eSignResponse_Class = function()
{
	this.init();
};

eSignResponse_Class.prototype.init = function() 
{
	if (docusignResponse && docusignResponse.mainEvent)
	{
	    this.utils = new UtilsClass();
		this.oMainEvent = docusignResponse.mainEvent;
		this.oModalDialog = modalDialog;
	    this.elMask = $("#mask");
	    this.elLoader = $("#loader");
	    this.showMessageDialog();
	}
};

eSignResponse_Class.prototype.showMessageDialog = function() 
{
	console.log("showMessageDialog ... ");
	var that = this;
	var status = this.oMainEvent.status;
	var sBusinessName = this.oMainEvent.businessName;
	var msg = (status == "Success" ? this.utils.MESSAGES.inPersonDocuSignSuccess : this.utils.MESSAGES.inPersonDocuSignDecline) + "<br><br><strong>" + this.oMainEvent.customerId + "</strong> - " + sBusinessName;
	var oTitleStyle = {"margin-top":"0px"};
	this.oModalDialog.renderTitle("DocuSign Response: " + status,oTitleStyle);

	var aBodyHTML = [];
	aBodyHTML.push(	'<div id="modal_body">');
	aBodyHTML.push(		'<div id="modal_message">' + msg + '</div>');	
	aBodyHTML.push(		'<div id="footer">');
	aBodyHTML.push(			'<div class="ui-state-active ui-corner-all4 fleft btn-icon" id="modal_ok">');
	aBodyHTML.push(				'<span class="ui-icon ui-icon-check"></span>OK</div>');
	aBodyHTML.push(		'</div>');
	
	aBodyHTML.push(	'</div>');
	this.oModalDialog.renderBody(aBodyHTML);

	var oDialogStyle = {top:"260px",left:"310px",width:"400px",height:"auto",zIndex:5};
	this.oModalDialog.showDialog(oDialogStyle);
	
	$('#modal_ok')
		.click(function()
			{
				that.oModalDialog.hideDialog();
			}
		);
};