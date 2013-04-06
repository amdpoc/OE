// Product Appearances
// -------------------
// *** Created by Mati Shahrabani 03/14/2013


var productEntryAppearances;

$(function()
	{
		productEntryAppearances = new ProductEntryAppearances_Class();
	}
);

ProductEntryAppearances_Class = function()
{
	this.init();
}

ProductEntryAppearances_Class.prototype.init = function()
{

};

// Properties 
// ----------

 
	


// Functions
// ---------
ProductEntryAppearances_Class.prototype.handleData = function(aInData, sParentId, sGroupId, sGroupName)
{  
	this.parentId 					    = sParentId;
	this.groupId 					    = sGroupId;
	this.groupName 					    = sGroupName;
	this.classPanel 				    = this.groupId + "_Panel";
	this.classWrapper 				    = this.groupId + "_Wrapper";
	this.classDataWrapper 	    	    = this.groupId + "_DataWrapper";
	this.classAddDeleteBtn 			    = sGroupId + '_addDeleteBtn'
	this.classCheckBox		 		    = this.groupId + "_CheckBox";
	this.productAppearanceRow 		    = this.groupId + "_DataRow";
	this.productAppearanceContentRow    = this.groupId + "Content_DataRow";
	this.currentData					= [];

  //console.info("ProductEntryAppearances_Class.prototype.handleData  >> aInData.length: " + aInData.length);
  
	if (aInData.length > 0)
	{
		//console.info("ProductEntryAppearances_Class.prototype.handleData  >> aInData[0].appearance.length: " + aInData[0].appearance.length);
		// edit mode
		if (aInData[0].appearance.length > 0)
		{	
			this.product_appearance_init 	= aInData[0];
	
			this.addPanel(sParentId);
			this.setHandleDataEvent();
			this.currentData=aInData;
			this.displayProductAppearances();
		}
	}
	else
	{
		if (aInData.appearance.length > 0)
		{	
			this.product_appearance_init = aInData;	
			this.addPanel(sParentId);
			this.setHandleDataEvent();
		}
	}
}

ProductEntryAppearances_Class.prototype.addPanel = function(sParentId)
{
	var aHTML = [];
	
	aHTML.push("<div class = '" + this.classPanel + "'>");
	aHTML.push("		<div class='"+this.classCheckBox+ "'><legend>"+this.groupName+"</legend></div>");
	aHTML.push("</div>");
	
	$('#' + sParentId).append(aHTML.join(''));
	this.addWrapper();
	this.addDataWrapper();
	//this.addEmptyAppearancesTable();

}

ProductEntryAppearances_Class.prototype.addWrapper = function()
{
	// -----------------------------------------------------------------
	// Holds Product Appearances and Appearances Content data wrappers
	// -----------------------------------------------------------------
	var sParentId = this.classPanel;
	
	var aHTML = [];
	
	aHTML.push("<div class = '" + this.classWrapper + " notVisible'>"); 
	
	aHTML.push("</div>");
	
	$('.' + sParentId).append(aHTML.join(''));	
}


ProductEntryAppearances_Class.prototype.addDataWrapper = function()
{	
	// -----------------------------------------------------------------
	// Holds Product Appearances table
	// -----------------------------------------------------------------	
	var sParentId= this.classWrapper;
	   
	var aHTML = [];

	// Product Appearance Data Wrapper (this.classDataWrapper)
	// -------------------------------------------------------	
	aHTML.push("	<div class='"+ this.classDataWrapper + "'>");
	aHTML.push("	</div>");
	
		
	$('.' + sParentId).append(aHTML.join(''));

	
	// Add Delete buttons
	// ------------------
	this.addAppearanceButtons(sParentId);
}

ProductEntryAppearances_Class.prototype.setHandleDataEvent = function()
{
  //console.info ("Mati - ProductEntryAppearances_Class.prototype.setHandleDataEvent");
	
	var that=this;
	
	$('.'+ this.classCheckBox).click(function() 
	{
		that.handleDataEvent();
	});	
}

ProductEntryAppearances_Class.prototype.handleDataEvent = function()
{
	//console.info ("Mati - ProductEntryAppearances_Class.prototype.handleDataEvent");
	if ($('.'+ this.classWrapper).hasClass('notVisible'))          
  {
		$('.'+ this.classWrapper).removeClass('notVisible');
	}
	else
	{
		$('.'+ this.classWrapper).addClass('notVisible');
	}	
}


ProductEntryAppearances_Class.prototype.displayProductAppearances = function()
{
	console.info ("displayProductAppearances: this.currentData.length: " + this.currentData.length);
	for (var i=0; i<this.currentData.length;i++)
  { 	
  	this.addAppearanceSet(this.currentData[i]);
  	this.addAppearanceContentSet(this.currentData[i].appearanceContent)
	}
}



ProductEntryAppearances_Class.prototype.addAppearanceSet = function(aData)
{

	// var aData = $.extend(true, [], this.product_appearance_init);
	var aHTML = [];
	var aDropDownClasses = [];

	aHTML.push("<div class='appearanceDiv'>");
    aHTML.push("    <div class='appearanceSet fleft'>");
    aHTML.push("        <div class='appearanceCell fleft bottomMargin20'>");
    aHTML.push("            <div class = 'labelDiv ' > ");
    aHTML.push("		        <button class='deleteProductAppearanceButton'>Delete</button>");
    aHTML.push("		     <button class='showAppearanceContentButton'>Show Content</button>");
    aHTML.push("         </div>");   // close labelDiv
    aHTML.push("        </div>");

    for (var i=0; i<aData.appearance.length;i++)
    {
      	var aFieldClasses 		= productEntryUtility.setFieldClasses(aData.appearance[i].relationType);
        var sLabelClass 		= aFieldClasses.labelClass;
        var sMandatoryIconClass = aFieldClasses.mandatoryIconClass;
        this.sDataClass 	  	= aFieldClasses.dataClass;

        aHTML.push("        <div class='appearanceCell'>");
        aHTML.push("            <div class = 'labelDiv ' > ");
        aHTML.push("                <label class='attributeLabel " + sLabelClass + "'  title=\"" +  aData.appearance[i].attributeDescription + "\">" + aData.appearance[i].attributeName);
        aHTML.push("	                <span class='" + sMandatoryIconClass + "'>*</span>");
        aHTML.push("                </label>");
      	aHTML.push("            </div>");   // close labelDiv
      	aHTML.push(" 	        <div class = 'inputDiv '>");

	    // SLV
		// ---
        if ((typeof(aData.appearance[i].slvInfo) != "undefined") && (aData.appearance[i].slvInfo != null))
        {
    	    productEntryUtility.handleSLV(aData.appearance[i],aHTML,aDropDownClasses);
		}
		else
		{
			// LOV
			// ---
			if ((typeof(aData.appearance[i].values) != "undefined") && (aData.appearance[i].values != null))
			{
				productEntryUtility.handleLOV(aData.appearance[i],aHTML,aDropDownClasses);
			}
			else
			{
  	 		aHTML.push("	        <input id = '" + aData.appearance[i].attributeCode + "' class= 'attributeData" + this.sDataClass + "'  type ='"+ productEntryUtility.setInputType(aData.appearance[i]) +"' value=\"" + aData.appearance[i].defaultValue +"\"></input>");
			}
	    }
      	aHTML.push("            </div>");   // close inputDiv
      	aHTML.push("     </div>");       // close attributeCell
	} 
    aHTML.push("    </div>");

	aHTML.push("</div>");
	$('.'+this.classDataWrapper).append(aHTML.join(''));
	
	// Set events
	// ----------	
	if (typeof(aDropDownClasses) != "undefined")
	{
		for (var i=0 ; i < aDropDownClasses.length; i++)
		{
			this.setGadgetSelectBtnEvent(aDropDownClasses[i]); 
		} 
	}
	
	this.setAppearanceRowButtonsEvent();

}

ProductEntryAppearances_Class.prototype.addInitProductAppearance = function()
{ 
	var aClonedProductAppearanceInit = $.extend(true, [], this.product_appearance_init);
	this.addAppearanceSet(aClonedProductAppearanceInit);
	this.addAppearanceContentSet(aClonedProductAppearanceInit.appearanceContent)
}

ProductEntryAppearances_Class.prototype.addAppearanceContentSet = function(aAppearanceContentData)
{
	var aHTML = [];

	var appearanceContentParent		={};
	aHTML.push("<div class='appearanceContentSet notVisible fright'>");
	aHTML.push("</div>");

	$('.appearanceDiv').last().append(aHTML.join(''));
	appearanceContentParent	= $('.appearanceContentSet').last();
	productEntryAppearanceContent.handleData(appearanceContentParent,aAppearanceContentData, "productAppearanceContent","Content");
}
 
ProductEntryAppearances_Class.prototype.deleteProductAppearance = function(caller)
{
	 var selectedAppearanceRow			=$(caller).closest('.appearanceDiv');
	 var selectedAppearanceContentRow	=$(selectedAppearanceRow).next();

    $(selectedAppearanceRow).detach();
    $(selectedAppearanceContentRow).detach();
}

ProductEntryAppearances_Class.prototype.deleteAllProductAppearance = function()
{	 	  
    $('.appearanceDiv').empty();
}


// Appearance buttons
// ------------------
ProductEntryAppearances_Class.prototype.addAppearanceButtons = function(sParentId)
{
  var aHTML = [];
  
  aHTML.push("<br/>"); 
  aHTML.push("<div id='"+this.classAddDeleteBtn +"'>");  
  aHTML.push("	<button class='addProductAppearanceButton'>Add</button>");
  aHTML.push("	<button class='deleteAllProductAppearanceButton'>Delete All</button>");	
  aHTML.push("</div>");
	
	$('.' + sParentId).append(aHTML.join(''));
	
	this.setAppearanceButtonsEvent();
}

ProductEntryAppearances_Class.prototype.setAppearanceButtonsEvent = function()
{
	var that=this;
			
	$('.addProductAppearanceButton').click (function()
			{
				that.addInitProductAppearance();
			});
			
	$('.deleteAllProductAppearanceButton').click (function()
			{
				that.deleteAllProductAppearance();
			});	
}

ProductEntryAppearances_Class.prototype.setAppearanceRowButtonsEvent = function()
{
	console.info("setAppearanceRowButtonsEvent");
	
	var that=this;
	
  
	$('.deleteProductAppearanceButton')
		.unbind('click')
		.click (function()
			{
				console.info("deleteProductAppearanceButton - click");
				that.deleteProductAppearance(this);
			});
			
	
	$('.showAppearanceContentButton')
		.unbind('click')
		.click (function()
			{
				that.handleAppearanceContent(this);
			});				
}

ProductEntryAppearances_Class.prototype.handleAppearanceContent = function(caller)
{
	var selectedAppearanceRow		    = $(caller).closest('.appearanceSet');
	var selectedAppearanceContentRow    = $(selectedAppearanceRow).next();
	//console.info("handleAppearanceContent >> selectedAppearanceContentRow:  " + $(selectedAppearanceContentRow).attr('class'));
	
	//console.info("caller: " + $(caller).text());
	if ($(selectedAppearanceContentRow).hasClass('notVisible'))
	{
		$(selectedAppearanceContentRow).removeClass('notVisible');
		$(caller).text('Hide Content');
	}
	else
	{
			$(selectedAppearanceContentRow).addClass('notVisible');
			$(caller).text('Show Content');
	}
}


// Listeners
// ---------
ProductEntryAppearances_Class.prototype.setGadgetSelectBtnEvent = function(sClass)
{	  
  var that=this;
         
	//console.info ("setGadgetSelectBtnEvent ::: sClass: " +sClass);

        	
  $("." + sClass)
  	.unbind('click')
  	.click(function()
      {       
        //console.log("click ::::: productEntryDropDown.isVisible()=",productEntryDropDown.isVisible());
        productEntryDropDown.setParent('productAppearanceTable');

        if (productEntryDropDown.isVisible())
        {
        	productEntryDropDown.hide();
        }
        else
        {
        	var aData = $(this).siblings("span#dropDownList").toArray();
        	//console.info ('Mati - aData: ' +  aData);
        	//console.info ('Mati - aData.length: ' + aData.length);
        	var autoPopulatedAttributeCode 	= $(this).siblings("span#populatedField").attr("name");
        	//console.info("autoPopulatedAttributeCode: "+ autoPopulatedAttributeCode);
        	//console.info("that.productAppearanceRow: " + that.productAppearanceRow);
        	var autoPopulateElem = $(this).closest('.appearanceSet').find('#'+autoPopulatedAttributeCode);
        	productEntryDropDown.setAutoPopulateElement(autoPopulateElem);
        	productEntryDropDown.show(this,null,that.fnCallback,$(this).siblings("input"),$(this).siblings("#elDropDown"),$(this).attr("dropDownType"),aData);
        }
      }
    ); 
    
   
   $("." + sClass).blur(function() /// NOT DOING WHAT I WANT
    {       
      //console.log("click ::::: productEntryDropDown.isVisible()=",productEntryDropDown.isVisible());
      if (productEntryDropDown.isVisible())
      {
      	productEntryDropDown.hide();
      }
    }
  );                                                                                
}



ProductEntryAppearances_Class.prototype.fnCallback = function(sPopulatedAttributeValue)
{	
	//console.info("In ProductEntry_Class.prototype.fnCallback received value is: " + sPopulatedAttributeValue);

}

ProductEntryAppearances_Class.prototype.saveDataFromScreen = function ()
{
	var screenData = [];
	
	var aScreenInputAppearanceTables	= $('.appearanceDiv').find('.appearanceSet').toArray();
	//var aScreenInputAppearanceTables	= $('.productAppearanceTableMain').find('.productAppearanceTable').toArray();
	//console.info ("Mati - aScreenInputAppearanceTables.length: " + aScreenInputAppearanceTables.length);
	
  if (aScreenInputAppearanceTables.length > 0)
  {
    for (var i=0, ix1=aScreenInputAppearanceTables.length; i< ix1; i++)
    {
    	  var aClonedProductAppearanceInit = $.extend(true, [], this.product_appearance_init);
  			screenData.push(aClonedProductAppearanceInit); 
  			
    	// Appearance fields
    	// ------------------  	
    	var aScreenInputAppearance = $(aScreenInputAppearanceTables[i]).find('.attributeData').toArray();
  
    	for (var j=0, ix2=aScreenInputAppearance.length ; j< ix2; j++)
    	{ 		
    		screenData[i].appearance[j].defaultValue = $(aScreenInputAppearance[j]).val();      
    	}
    	// Appearance Content fields
    	// -------------------------
     	var aScreenInputAppearanceContent	= $(aScreenInputAppearanceTables[i]).next('.'+this.productAppearanceContentRow).find('.attributeData').toArray();
     	
     	//console.info ("aScreenInputAppearanceContent: " + aScreenInputAppearanceContent + " length: " + aScreenInputAppearanceContent.length);
     	for (var j=0, ix3=aScreenInputAppearanceContent.length ; j< ix3; j++)
     	{ 		
    		screenData[i].appearanceContent[j].defaultValue = $(aScreenInputAppearanceContent[j]).val();
     	}
    }
  }
  else
  {
  	var aClonedProductAppearanceInit = $.extend(true, [], this.product_appearance_init);
		screenData.push(aClonedProductAppearanceInit); 
  }
 

  return screenData;	  
}

ProductEntryAppearances_Class.prototype.getCurrentData = function ()
{
	return this.saveDataFromScreen();
}




