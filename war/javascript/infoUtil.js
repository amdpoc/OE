// *** Created by okotlia 06/20/2011
var infoUtil;

$(function() 
	{
		infoUtil = new InfoUtil_Class();
	}
);

InfoUtil_Class = function()
{
    this.init();
};

InfoUtil_Class.prototype.init = function()
{
	var that = this;
    this.oModalDialog = modalDialog;
    this.oGeneralInfo = generalInfo;
    
    this.bModalEventsSet = false;
    
    $("#infoBtn")
		.click(function()
			{
				that.displayInfo();
			}
		);
};

InfoUtil_Class.prototype.displayInfo = function()
{
	var that = this;
	var oTitleStyle = {"margin-top":"0px"};
	this.oModalDialog.renderTitle(that.oGeneralInfo.employeeName,oTitleStyle);
	var aBodyHTML = [];
	
	console.log("this.generalInfo=",this.generalInfo);
	aBodyHTML.push(	'<div id="modal_body">');
	aBodyHTML.push(		'<div class="label">CC Version:</div><div class="value">' + this.oGeneralInfo.currentCCVersion + '</div>');
	aBodyHTML.push(		'<div class="label">Dex One Version:</div><div class="value">' + this.oGeneralInfo.currentDEXVersion + '</div>');
	aBodyHTML.push(		'<div class="label">Environment:</div><div class="value">' + this.oGeneralInfo.serverName + '</div>');
	aBodyHTML.push(		'<div class="label">Logical Date:</div><div class="value">' + this.oGeneralInfo.logicalDate + '</div>');
	aBodyHTML.push(		'<div class="label">DB:</div><div class="value">' + this.oGeneralInfo.dataBase + '</div>');
	aBodyHTML.push(	'</div>');
	
	this.oModalDialog.renderBody(aBodyHTML);
	var oDialogStyle = {top:"14px",right:"200px",left:"auto",width:"300px",height:"160px",zIndex:5};
	this.oModalDialog.showDialog(oDialogStyle);
    this.setModalEvents();    
};

InfoUtil_Class.prototype.hideInfo = function()
{
	this.elModalDialog.fadeOut("fast");
	this.elMask.hide();
	$('.gadgetExpandBtn').hide();
};

InfoUtil_Class.prototype.setModalEvents = function() 
{
	var that = this;
	if (!this.bModalEventsSet)
	{		
		$('#warning_cancel')
			.click(function()
				{
					that.oModalDialog.hideDialog();
				}
			);
		
		this.bModalEventsSet = true;
	}
};
