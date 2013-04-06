// General Attribute Handler
// -----------------------------------------------
// *** Created by Mati Shahrabani 03/14/2013


var productEntryAttributes;

$(function()
	{
		productEntryAttributes = new ProductEntryAttributes_Class();
	}
);

ProductEntryAttributes_Class = function()
{
	this.init();
}

ProductEntryAttributes_Class.prototype.init = function()
{
	
};


// Properties
// ----------


 
// Functions
// ---------

ProductEntryAttributes_Class.prototype.handleData = function(aInData, sParentId, sGroupId,sGroupName)
{
	if (aInData.length > 0)
	{	
		this.currentData 		= aInData;
		this.parentId 			= sParentId;
		this.groupId 			= sGroupId;
		this.groupName 			= sGroupName;
		
		this.classPanel 		= this.groupId + "_Panel";
		this.classDataWrapper   = this.groupId + "_DataWrapper";
		this.classCheckBox	    = this.groupId + "_CheckBox";

	
		this.addWrapper();
		//this.createAttributesTable();
		this.createAttributesDiv();
		this.setHandleDataEvent();
	}

}
ProductEntryAttributes_Class.prototype.addWrapper = function()
{	
	var aHTML = [];
		
	aHTML.push("<div class = '" + this.classPanel + "'>");
	aHTML.push("	<div class='"+ this.classDataWrapper + "'>");
	aHTML.push("		<div class='"+this.classCheckBox+ "'><legend>"+this.groupName+"</legend></div>");
	aHTML.push("	</div>");
	aHTML.push("</div>");
			
	$('#'+this.parentId).append(aHTML.join(''));			

}

ProductEntryAttributes_Class.prototype.setHandleDataEvent = function()
{
	var that=this;
	
	$('.'+this.classCheckBox).click(function()
			{
					that.handleDataEvent();
			});				
}


ProductEntryAttributes_Class.prototype.handleDataEvent = function()
{
 console.info ("Mati - handleDataEvent");
    if ($('.attributesDiv').hasClass('notVisible'))
    {
  	    $('.attributesDiv').removeClass('notVisible');
    }
	    else
	    {
		    $('.attributesDiv').addClass('notVisible');
	    }
}

ProductEntryAttributes_Class.prototype.createAttributesDiv = function()
{
    var aHTML = [];
    var aDropDownClasses = [];

    aHTML.push("<div class='attributesDiv notVisible'>");

    for (i=0; i<this.currentData.length;i++)
    {
        var aFieldClasses 		= productEntryUtility.setFieldClasses(this.currentData[i].relationType);
        var sLabelClass 		= aFieldClasses.labelClass;
        var sMandatoryIconClass = aFieldClasses.mandatoryIconClass;
        var sDataClass 			= aFieldClasses.dataClass;


  	    aHTML.push(" 	<div class='attributeCell fleft'>");
        aHTML.push("		<div class = 'labelDiv' > ");
        aHTML.push("			<label class='attributeLabel " + sLabelClass + "'  title=\"" +  this.currentData[i].attributeDescription + "\">" + this.currentData[i].attributeName );
        aHTML.push("			    <span class='" + sMandatoryIconClass + "'>*</span>");
        aHTML.push("            </label>");
        aHTML.push(" 		</div>");
        aHTML.push(" 		<div class = 'inputDiv'>");

  	    if ((typeof(this.currentData[i].slvInfo) != "undefined") && (this.currentData[i].slvInfo != null))
        {
    	    productEntryUtility.handleSLV(this.currentData[i],aHTML,aDropDownClasses);
        }
        else
  	    {
    	    // LOV
    	    // ---
    	    if ((typeof(this.currentData[i].values) != "undefined") && (this.currentData[i].values != null))
    	    {
    		    productEntryUtility.handleLOV(this.currentData[i],aHTML,aDropDownClasses);
    	    }
            else
            {
       	        aHTML.push("		<input class= 'attributeData " + sDataClass + "'  type ='"+ productEntryUtility.setInputType(this.currentData[i]) +"' value=\"" + this.currentData[i].defaultValue +"\"></input>");
            }
        }
        aHTML.push(" 		</div>");   // close inputDiv
        aHTML.push(" 	</div>");       // close attributeCell
    }
    aHTML.push("</div>");             // close attributesDiv

    $('.'+ this.classDataWrapper).append(aHTML.join(''));

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

ProductEntryAttributes_Class.prototype.createAttributesTable = function()
{  	
	var aHTML = [];
	var aDropDownClasses = [];
	var iNumberOfDisplayedAttributes=0;

	
  aHTML.push("<table class='productAttributesTable notVisible'>");	

  for (i=0; i<this.currentData.length;i++)
  {
    var aFieldClasses 			= productEntryUtility.setFieldClasses(this.currentData[i].relationType);
    var sLabelClass 				= aFieldClasses.labelClass;
    var sMandatoryIconClass = aFieldClasses.mandatoryIconClass;
    var sDataClass 					= aFieldClasses.dataClass;
    
    
    
    if (iNumberOfDisplayedAttributes> 0 && iNumberOfDisplayedAttributes%5 == 0)
    {
    	aHTML.push("</tr>");
  		aHTML.push("<tr>");
  	}
  	iNumberOfDisplayedAttributes++;
  	
  	aHTML.push(" 	<td class='attributeCell'>");
    aHTML.push("		<div class = 'labelColumnxx' > ");    
    
    aHTML.push("			<label class='attributeLabel " + sLabelClass + "'  title=\"" +  this.currentData[i].attributeDescription + "\">" + this.currentData[i].attributeName );
    aHTML.push("			    <span class='" + sMandatoryIconClass + "'>*</span>");
    aHTML.push("            </label>");
    aHTML.push(" 		</div>");
    aHTML.push(" 		<div class = 'inputColumnxx'>");   			
  
  	if ((typeof(this.currentData[i].slvInfo) != "undefined") && (this.currentData[i].slvInfo != null))
    {
    	productEntryUtility.handleSLV(this.currentData[i],aHTML,aDropDownClasses);
    }
    else
  	{
    	// LOV
    	// ---
    	if ((typeof(this.currentData[i].values) != "undefined") && (this.currentData[i].values != null))
    	{
    		productEntryUtility.handleLOV(this.currentData[i],aHTML,aDropDownClasses);
    	}
      else
      {
       	aHTML.push("		<input class= 'attributeData " + sDataClass + "'  type ='"+ productEntryUtility.setInputType(this.currentData[i]) +"' value=\"" + this.currentData[i].defaultValue +"\"></input>");   	
      }
    }
    aHTML.push(" 		</div>");    	   	
    aHTML.push(" 	</td>"); 
  }   
  aHTML.push(" 		</div>"); 	  
  aHTML.push("	</tr>");
	aHTML.push("</table>");
	
	$('.'+ this.classDataWrapper).append(aHTML.join(''));
	
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


// Listeners
// ---------
ProductEntryAttributes_Class.prototype.setGadgetSelectBtnEvent = function(sClass)
{	  
  var that=this;
         
	//console.info ("ProductEntryAttributes_Class - setGadgetSelectBtnEvent ::: sClass: " +sClass);

        	
  $("." + sClass).click(function()
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
        	//console.info ('Mati - aData.length: ' + aData.length); 
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

ProductEntryAttributes_Class.prototype.fnCallback = function(sPopulatedAttributeValue)
{	
	//console.info("In ProductEntryAttributes_Class.prototype.fnCallback received value is: " + sPopulatedAttributeValue);

}


ProductEntryAttributes_Class.prototype.getCurrentData = function ()
{
	this.saveDataFromScreen();
	return this.currentData;
}

ProductEntryAttributes_Class.prototype.saveDataFromScreen = function()
{
	var aScreenInputs	= $('.attributesDiv').find('.attributeData').toArray();
	
	for (var i=0; i<aScreenInputs.length; i++)
	{
		this.currentData[i].defaultValue = $(aScreenInputs[i]).val() ;
	}
}

