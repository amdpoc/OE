// *** Created by Mati Shahrabani 03/08/2013


//var productEntry;

//$(function()
//	{
//		productEntry = new ProductEntry_Class();
//	}
//);

ProductEntry_Class = function()
{
	this.init();
    var elCallerDiv;

	this.getCallerElement = function()
	{
        console.log("getCallerElement ::: elCallerDiv = ",elCallerDiv);
	    return elCallerDiv;
	}

	this.setCallerElement = function(elCaller)
    {
        console.log("setCallerElement ::: elCallerDiv = ",elCallerDiv);
        elCallerDiv = elCaller;
    };
};

ProductEntry_Class.prototype.init = function()
{
    this.clearScreen ();
	this.loadData();	
	this.renderForm();
}
	


// Properties
// ----------






// Functions
// ---------

ProductEntry_Class.prototype.loadData = function()
{  
	this.productData = productEntryInputData.getNewData();
}

ProductEntry_Class.prototype.renderForm = function()
{  
	
// debug - start
//  console.info("-------------------------------------------------------------------------------------------");
//	console.info("renderForm - this.productData.attributes.length:  " + this.productData.attributes.length);
//	console.info("renderForm - this.productData.appearances.length:  " + this.productData.appearances.length);
//	console.info("renderForm - this.productData.general.selectedHeading: " + this.productData.general.selectedHeading);
	//console.info("renderForm - this.productData.appearances.appearance.length:  " + this.productData.appearances.appearance.length);
	//console.info("renderForm - this.productData.appearances.appearanceContent.length:  " + this.productData.appearances.appearanceContent.length);
	
// debug - end
	
// Render Form
// -----------
	
	productEntryGeneral.handleData			(this.productData.general,"productEntryPage","general","General");
	this.addSpace();
	productEntryAppearances.handleData	(this.productData.appearances,"productEntryPage","productEntryAppearances","Product Appearance");	
	this.addSpace();
	productEntryAttributes.handleData	(this.productData.attributes,"productEntryPage","productEntryAttributes","Attributes");
	

	//this.handleButtons();	
}

//  ProductEntry_Class.prototype.handleButtons = function()
//  {	
//  	var aHTML = [];
//  	
//  	aHTML.push("<br/>");
//  	aHTML.push("<div>");
//  	aHTML.push("	<input class='saveButton' type='submit'  value='Save'/>");
//  	aHTML.push("	<input class='addToCartButton' type='submit'  value='Add To Cart'/>");
//  	aHTML.push("</div>");
//  	
//    
//    $('#productEntryPage').append(aHTML.join(''));
//    this.productEntryButtonstEvent();
//  }

ProductEntry_Class.prototype.addEditButton = function()
{	
	var aHTML = [];
	
	aHTML.push("<br/>");
	aHTML.push("<div>");
	aHTML.push("	<input class='editButton' type='submit'  value='Edit'/>");
	aHTML.push("</div>");
	
  
  $('.productEntryPage').append(aHTML.join(''));
  this.productEntryButtonstEvent();
}

ProductEntry_Class.prototype.addSpace = function()
{
	var aHTML = [];	
	
	aHTML.push("<br/>");		
	
  $('.productEntryPage').append(aHTML.join(''));
}

ProductEntry_Class.prototype.eraseDate = function(o)
{
	$(o).val("");
}

ProductEntry_Class.prototype.addToCart = function(caller)
{
	
	this.save();	

}

ProductEntry_Class.prototype.save = function()
{		
	this.productData["appearances"] = [];
	this.productData["attributes"] 	= [];

	// Get General Data
	// ----------------
	this.productData["general"] = productEntryGeneral.getCurrentData();
		
	// Get appearance data
	// -------------------	
	this.productData["appearances"] = productEntryAppearances.getCurrentData();
		
	// Get attributes data
	// --------------------
	this.productData["attributes"] = productEntryAttributes.getCurrentData();


		
	productEntryInputData.setEditData(this.productData);
};

/*
ProductEntry_Class.prototype.setCallerElement = function(elCallerDiv)
{
    elCallerDiv = elCallerDiv;
};

ProductEntry_Class.prototype.getCallerElement = function()
{
    return elCallerDiv;
};
*/

ProductEntry_Class.prototype.productEntryButtonstEvent = function()
{
	that=this;
	
	$(".saveButton")
		.click(function()
		{
			that.save();
			alert("The product has been SAVED");
		});
	
	$(".addToCartButton")
		.click(function()
		{
			that.addToCart();	
			that.clearScreen ();
			that.addEditButton();
			alert("The product has been ADDED To CART");		
		});
	
	$(".editButton")
		.click(function()
		{
			that.edit();
			alert("Editing");		
		});

};


	
ProductEntry_Class.prototype.getProductData = function()
{
	return this.productData;
}
	
ProductEntry_Class.prototype.clearScreen = function()
{
    //console.info("Emptying modalBody");
    //$('#modalBody').empty();
    console.info("Emptying productEntryPage");
	$('#productEntryPage').empty();
}	

ProductEntry_Class.prototype.initScreen = function()
{
	$('#productEntryPage').empty();
	this.loadData();	
	this.renderForm();	 
}	
	
ProductEntry_Class.prototype.edit = function()
{
	$('.productEntryPage').empty();
	this.productData = productEntryInputData.getEditData();

	this.renderForm();
}	





    
