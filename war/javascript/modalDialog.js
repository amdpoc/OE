// *** Created by okotlia 07/25/2011
var modalDialog;

$(function() 
	{
		modalDialog = new ModalDialog_Class();
	}
);

ModalDialog_Class = function()
{
    this.init();
};

ModalDialog_Class.prototype.init = function()
{
	var that = this;
	this.elDialogLoader = $('#dialog_loader');
	this.elTitle = $('#ui-dialog-title-dialog');
	this.elExpandBtn = $('.gadgetExpandBtn');
	this.elClose = $('.ui-dialog-titlebar-close');
	
	this.elClose
		.click(function()
			{
				that.hideDialog();
			}
		);
	
	this.elExpandBtn
		.click(function()
			{
				that.toggleDialog();
			}
		);

	//this.bExpandEventsSet = false;
};

ModalDialog_Class.prototype.renderBody = function(aBodyHTML)
{
	$('#dialog').html(aBodyHTML.join(''));
};

ModalDialog_Class.prototype.renderTitle = function(sTitle,oStyle)
{
	this.elTitle.text(sTitle);
	if (oStyle)
	{
		this.elTitle.css(oStyle);
	}
};

ModalDialog_Class.prototype.showExpandButton = function()
{
	var that = this;
	this.elExpandBtn.show().addClass("ui-state-highlight").removeClass("ui-state-lowlight");
	/*
	if (!this.bExpandEventsSet)
	{
		this.elExpandBtn
			.click(function()
				{
					that.toggleDialog();
				}
			);
		this.bExpandEventsSet = true;
	}
	*/
};

ModalDialog_Class.prototype.toggleDialog = function()
{
	if (this.elExpandBtn.hasClass("ui-state-highlight"))
	{
		var oAnimationStyle = {height:"54px"};
	}
	else
	{
		var oAnimationStyle = {height:this.iHeight};
	}
	$('#modal_dialog').animate(oAnimationStyle,500);
	this.elExpandBtn.toggleClass("ui-state-highlight ui-state-lowlight");
};

ModalDialog_Class.prototype.hideDialog = function()
{
	$('#modal_dialog').fadeOut("fast");
	$('#mask').hide();
	$('.gadgetExpandBtn').hide();	
};

ModalDialog_Class.prototype.showDialog = function(oStyle)
{
	this.iHeight = oStyle.height;
	$('#modal_dialog').css(oStyle).fadeIn("fast");
	$('#mask').show();
};

ModalDialog_Class.prototype.hideLoader = function()
{
	this.elDialogLoader.hide();	
};

ModalDialog_Class.prototype.showLoader = function()
{
	this.elDialogLoader.show();	
};