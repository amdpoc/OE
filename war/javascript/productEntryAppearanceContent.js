// General Attribute Handler
// -----------------------------------------------
// *** Created by Mati Shahrabani 03/14/2013


var productEntryAppearanceContent;

$(function()
	{
		productEntryAppearanceContent = new ProductEntryAppearanceContent_Class();
	}
);

ProductEntryAppearanceContent_Class = function()
{
	this.init();
}

ProductEntryAppearanceContent_Class.prototype.init = function()
{
	
};

// Properties
// ----------

	
	

// Functions
// ----------

ProductEntryAppearanceContent_Class.prototype.handleData = function(sParent,aInData, sGroupId,sGroupName)
{
   // console.info("ProductEntryAppearanceContent_Class.prototype.handleData - aInData.length: " + aInData.length ) ;
	if (aInData.length > 0)
	{	
		this.parent					= sParent;
		console.info("ProductEntryAppearanceContent_Class.prototype.handleData >> this.parent: " + this.parent + "this.parent.length: " + this.parent.length);
		this.currentData 			= aInData;		
		this.groupId 				= sGroupId;
		this.groupName 				= sGroupName;
		this.classData 				= sGroupId + "_Data";
		this.addDataWrapper();	
	    this.addProductAppearanceContent();
	}
}

ProductEntryAppearanceContent_Class.prototype.addDataWrapper = function()
{
	this.classDataWrapper = this.groupId + "_DataWrapper";
	
	var aHTML = [];

	// Product Appearance Content Data Wrapper (this.classDataWrapper)
	// --------------------------------------------------------------------------------------
	aHTML.push("	<div class='"+ this.classDataWrapper + "'>");
	aHTML.push("	</div>");
		
	//this.appearanceContentWrapperObject = $(this.parent).append(aHTML.join(''));
	$(this.parent).append(aHTML.join(''));
}

ProductEntryAppearanceContent_Class.prototype.addProductAppearanceContent = function()
{	
	var aHTML = [];
	var aDropDownClasses = [];

    aHTML.push("<legend>Content</legend>");
	aHTML.push("<div class='appearanceContentAttributes'>");
    for (var i=0; i<this.currentData.length;i++)
    {
        this.buildAppContCell(this.currentData[i], aHTML, aDropDownClasses);
    }
    aHTML.push("</div>");

    aHTML.push("<legend>Address</legend>");
    aHTML.push("<div class='appearanceContentAddress'>");
    for (var i=0; i<this.currentData.address.length;i++)
    {
        this.buildAppContCell(this.currentData.address[i], aHTML, aDropDownClasses);
    }
    aHTML.push("</div>");

    aHTML.push("<legend>TL Address</legend>");
    aHTML.push("<div class='appearanceContentTlAddress'>");
    for (var i=0; i<this.currentData.tlAddress.length;i++)
    {
        this.buildAppContCell(this.currentData.tlAddress[i], aHTML, aDropDownClasses);
    }
    aHTML.push("</div>");

	$('.' + this.classDataWrapper ).last().append(aHTML.join(''));
	
	// Set events
	// ----------
	if (typeof(aDropDownClasses) != "undefined")
	{
  	    for (i=0 ; i < aDropDownClasses.length; i++)
  	    {
  		    this.setGadgetSelectBtnEvent(aDropDownClasses[i]);
  	    }
	}		
}

ProductEntryAppearanceContent_Class.prototype.buildAppContCell = function(aData,aHTML, aDropDownClasses)
{
    var aFieldClasses 		= productEntryUtility.setFieldClasses(aData.relationType);
    var sLabelClass 		= aFieldClasses.labelClass;
    var sMandatoryIconClass = aFieldClasses.mandatoryIconClass;
    var sDataClass 			= aFieldClasses.dataClass;

    aHTML.push(" 	<div class='appearanceContentCell fleft'>");
    aHTML.push("		<div class = 'labelDiv' > ");
    aHTML.push("			<label class='attributeLabel " + sLabelClass + "'  title=\"" +  aData.attributeDescription + "\">" + aData.attributeName );
    aHTML.push("			    <span class='" + sMandatoryIconClass + "'>*</span>");
    aHTML.push("            </label>");
    aHTML.push(" 		</div>");
    aHTML.push(" 		<div class = 'inputDiv'>");



    if ((typeof(aData.slvInfo) != "undefined") && (aData.slvInfo != null))
    {
        productEntryUtility.handleSLV(aData,aHTML,aDropDownClasses);
    }
    else
    {
        // LOV
        // ---
        if ((typeof(aData.values) != "undefined") && (aData.values != null))
        {
            productEntryUtility.handleLOV(aData,aHTML,aDropDownClasses);
        }
        else
        {
            aHTML.push("		<input class= 'attributeData " + sDataClass + "'  type ='"+ productEntryUtility.setInputType(aData) +"' value=\"" + aData.defaultValue +"\"></input>");
        }
    }
    aHTML.push(" 		</div>");   // close inputDiv
    aHTML.push(" 	</div>");       // close appearanceContentCell
}
// Listeners
// ---------

ProductEntryAppearanceContent_Class.prototype.setGadgetSelectBtnEvent = function(sClass)
{	  
  var that=this;
         
	//console.info ("setGadgetSelectBtnEvent ::: sClass: " +sClass);

        	
  $("." + sClass)
  	.unbind()
  	.click(function()
      {       
        //console.log("click ::::: productEntryDropDown.isVisible()=",productEntryDropDown.isVisible());
        productEntryDropDown.setParent(that);
        if (productEntryDropDown.isVisible())
        {
        	productEntryDropDown.hide();
        }
        else
        {
        	var aData = $(this).siblings("span#dropDownList").toArray();
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

ProductEntryAppearanceContent_Class.prototype.fnCallback = function(sPopulatedAttributeValue)
{	
	//console.info("In ProductEntryAppearanceContent_Class.prototype.fnCallback received value is: " + sPopulatedAttributeValue);

}

