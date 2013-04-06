// *** Created by Mati Shahrabani 03/14/2013


var productEntryGeneral;

$(function()
	{
		productEntryGeneral = new ProductEntryGeneral_Class();
	}
);

ProductEntryGeneral_Class = function()
{
	this.init();
}

ProductEntryGeneral_Class.prototype.init = function()
{
	

};

// Properties
// ----------

ProductEntryGeneral_Class.prototype.CURRENT_DATA=[];


// Functions
// ---------

ProductEntryGeneral_Class.prototype.handleData = function(aInData, sParentId, sGroupId, sGroupName)
{
	this.CURRENT_DATA = aInData;
	this.PARENT_ID 		= sParentId;
	this.GROUP_ID 		= sGroupId;
	this.GROUP_NAME 	= sGroupName;	
	
	// Add input fields to this.CURRENT_DATA
	// -------------------------------------
	if (typeof(this.CURRENT_DATA.selectedHeading) == "undefined")
	{ 
		this.CURRENT_DATA.selectedHeading = "";
	}	
	var aHTML = [];	

  aHTML.push("<div class = '" + this.GROUP_ID + "_Panel'>");
                                 
	aHTML.push("	<legend>" + this.GROUP_NAME  + "</legend>");

  // Start Data table
  // -----------------
  aHTML.push("	<div class='"+ this.GROUP_ID + "_DataWrapper'>");

	// Product Name
	// ------------
	this.addField(aHTML,"Product Name", this.CURRENT_DATA.productName,this.GROUP_ID + "_DataCol1" );
                       	                            
  // Product Description                            
  // --------------------   
  this.addField(aHTML,"Product Description", this.CURRENT_DATA.productDescription, this.GROUP_ID + "_DataCol2");
                                                        
  // productCode                             
  // ---------------	
  this.addField(aHTML,"Product Code", this.CURRENT_DATA.productCode, this.GROUP_ID + "_DataCol3");
                            
  // productStatus                             
  // --------------------  
  this.addField(aHTML,"Product Status", this.CURRENT_DATA.productStatus, this.GROUP_ID + "_DataCol1");

  // Heading                             
  // --------------------                             
	this.addField(aHTML,"Heading","slv", this.GROUP_ID + "_DataCol2");  
                      		 
  aHTML.push("	</div>");       // Wrapper End
  aHTML.push("</div>");         // Panel - End

  
    
  $('#'+ this.PARENT_ID).append(aHTML.join(''));
  this.setGadgetSelectBtnEvent("dropDown_HEADING"); 
}

ProductEntryGeneral_Class.prototype.addField = function(aHTML,sLabel,sData,sColumnClass)
{
    //aHTML.push("<div class='attributeCell fleft " + sColumnClass + "' >");

    aHTML.push("<div class='generalCell fleft' >");
    aHTML.push("    <div class='labelDiv'>");
    aHTML.push("        <label class='labelTd'>"+sLabel+"</label>");
    aHTML.push("    </div>");      // close labelDiv

    aHTML.push("    <div class='inputDiv'>");
	
	if(sLabel == "Heading")
	{
		if (this.CURRENT_DATA.headings.length > 1)
		{
			var selectedValue=this.CURRENT_DATA.selectedHeading;
            // *** OLEG START
            //aHTML.push("        <input id='heading' type='text'  selectedVal='" + selectedValue + "' value = '"+selectedValue+"'></input>");
            //aHTML.push("            <span dropDownType='HEADING' class ='dropDown_HEADING dropDownArrow'></span>");

            aHTML.push("        <input id='heading' type='text'  class='fleft' selectedVal='" + selectedValue + "' value = '"+selectedValue+"'></input>");
            aHTML.push(         '<div class="fleft btn-primary ui-corner-right gadgetSelectBtn dropDown_HEADING">');
            aHTML.push(             '<div class="ui-icon ui-icon-triangle-1-s"></div>');
		    aHTML.push(         '</div>');
		    // *** OLEG FINISH

  	        // Drop Down div
  	        // -------------
  		    aHTML.push ("           <div id ='elDropDown'></div>");
	
  	        // Drop Down data - hidden
 	        // -----------------------
  		    for (j=0;j  < this.CURRENT_DATA.headings.length;j++)
  		    {
  			    aHTML.push("	    <span id = 'dropDownList' class='hidden_element'>");
  			    aHTML.push("	        <span id = 'headingCode' 		value='" + this.CURRENT_DATA.headings[j].headingCode + "'>		</span>");
  			    aHTML.push("		    <span id = 'headingName' 		value='" + this.CURRENT_DATA.headings[j].headingName + "'>		</span>");
    		    aHTML.push("        </span>");
  		    }
  	    }
  	    else
  	    {
			aHTML.push("		<label id='heading' class='labelTd' selectedVal='"+this.CURRENT_DATA.headings[0].headingName+"'>" +this.CURRENT_DATA.headings[0].headingName+ "</label>");
  	    }
	}
	else
	{
		aHTML.push("			<label>" +sData + "</label>");
	}
 	aHTML.push("    </div>");    // close inputDiv
  	aHTML.push("</div>");        // close attributeCell
} 

// Listeners
// ---------
ProductEntryGeneral_Class.prototype.setGadgetSelectBtnEvent = function(sClass)
{	  
  var that=this;
         
	//console.info ("setGadgetSelectBtnEvent ::: sClass: " +sClass);

        	
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
        	//var autoPopulatedAttributeCode 	= $(this).siblings("label").attr("name");
        	//var autoPopulateElem = $(this).closest('.productAppearanceTable').find('#'+autoPopulatedAttributeCode);
        	//console.info("Heading: " + $(this).siblings("#heading").attr("id"));
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

ProductEntryGeneral_Class.prototype.fnCallback = function(sPopulatedAttributeValue)
{	
	//console.info("In ProductEntryGeneral_Class.prototype.fnCallback received value is: " + sPopulatedAttributeValue);

}

ProductEntryGeneral_Class.prototype.saveDataFromScreen = function ()
{
	this.CURRENT_DATA.selectedHeading = $('.'+ this.GROUP_ID + '_DataWrapper').find('#heading').attr('selectedVal');
	//this.CURRENT_DATA["selectedHeading"] = $('.'+ this.GROUP_ID + '_DataWrapper').find('#heading').attr('selectedVal');
	//console.info("Mati - Selected Heading attr val is: " + $('.'+ this.GROUP_ID + '_DataWrapper').find('#heading').attr('selectedVal'));

}

	


ProductEntryGeneral_Class.prototype.getCurrentData = function ()
{
	this.saveDataFromScreen();
	return this.CURRENT_DATA;
}