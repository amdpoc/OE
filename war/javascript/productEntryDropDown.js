// *** Created by okotlia 06/29/2011
// *** Updated by Mati Shahrabani 03/13/2013
var productEntryDropDown;

$(function() 
	{
		productEntryDropDown = new ProductEntryDropDown_Class();
	}
);

ProductEntryDropDown_Class = function()
{
    this.init();
};

ProductEntryDropDown_Class.prototype.init = function()
{
	//this.createDropDown();

};

ProductEntryDropDown_Class.prototype.setOptionsList = function(type)
{
	switch (type)
	{		
		case "ATTRIBUTE_SLV":  							
		  this.renderAttributeSlvOptions();	 
		  break; 
		  
		case "ATTRIBUTE_LOV":  							
		  this.renderAttributeLovOptions();	 
		  break;   
		   
		case "HEADING":  							
		  this.renderHeadingOptions();	 
		  break; 
		   		                   
		default:
			break;
	}
};

ProductEntryDropDown_Class.prototype.createDropDown = function(type)
{
	var aHTML = [];
	aHTML.push('<div id="drop_down" class="drop_down">')
	aHTML.push('	<div class="triangle-isosceles top">');
	aHTML.push('		<div id="dd_scroller" class="scroller_native height90 width900">');
	aHTML.push('			<div class="scroller_content"></div>');
	aHTML.push('		<div>');
	aHTML.push('	<div>');
	aHTML.push('<div>');

  //console.info("elActiveDropDown:" + this.elActiveDropDown);
	$(this.elActiveDropDown).append(aHTML.join(""));
	//this.hide();
};


ProductEntryDropDown_Class.prototype.renderAttributeSlvOptions = function()
{
	var aHTML = [];
	aHTML.push('<div id="slv_drop_down_list" class="slv_drop_down_list">');
	//console.info("Data length:" + this.aData.length);
	for (var i = 0, iL = this.aData.length; i < iL; i++)
	{
		aHTML.push('<div class="dd_item" val="' + this.aData[i].decodeColumnValue + '" populatedAttributeVal="'+ this.aData[i].populatedFieldValue + '">');
		aHTML.push(	'<div class="column1 fleft">' + this.aData[i].decodeColumnValue + '</div>');
		aHTML.push(	'<div class="column2 fleft">'+this.aData[i].populatedFieldValue +'</div>');
		aHTML.push(	'<div class="column3 fleft">' + this.aData[i].codeColumnValue + '</div>');
		aHTML.push('</div>');
	}
	aHTML.push('</div>');
	$("#dd_scroller .scroller_content").html(aHTML.join(""));
	this.setEvents();
};


//{valueContent":"NO","valueId":1,"valueDesc":"No"}
ProductEntryDropDown_Class.prototype.renderAttributeLovOptions = function()
{
	var aHTML = [];
	aHTML.push('<div id="lov_drop_down_list" class="lov_drop_down_list">');
	//console.info("Data length:" + this.aData.length);
	for (var i = 0, iL = this.aData.length; i < iL; i++)
	{
		aHTML.push('<div class="dd_item" val="' + this.aData[i].valueContent + '">');
		aHTML.push(	'<div class="column1 fleft">' + this.aData[i].valueContent + '</div>');
		aHTML.push(	'<div class="column2 fleft">'+this.aData[i].valueId +'</div>');
		aHTML.push(	'<div class="column3 fleft">' + this.aData[i].valueDesc + '</div>');
		aHTML.push('</div>');
	}
	aHTML.push('</div>');
	$("#dd_scroller .scroller_content").html(aHTML.join(""));
	this.setEvents();
};


ProductEntryDropDown_Class.prototype.renderHeadingOptions = function()
{
	var aHTML = [];
	aHTML.push('<div id="heading_drop_down_list" class="heading_drop_down_list">');
	//console.info("Data length:" + this.aData.length);
	for (var i = 0, iL = this.aData.length; i < iL; i++)
	{
		aHTML.push("<div class='dd_item' val='" + this.aData[i].headingCode + "'>" + this.aData[i].headingCode + "-" + this.aData[i].headingName + "</div>");
	}
	aHTML.push('</div>');
	$("#dd_scroller .scroller_content").html(aHTML.join(""));
	this.setEvents();
};


ProductEntryDropDown_Class.prototype.setParent = function(parent)
{
	this.parent = parent;	
};

ProductEntryDropDown_Class.prototype.setAutoPopulateElement = function(elem)
{
	this.autoPopulateElem = elem;
	
};

ProductEntryDropDown_Class.prototype.show = function(caller,type,callback,elActiveInput,elActiveDropDown,sType,aInData)
{

	//this.aData = this.getDataFromInput(caller); 
	//this.sType=$(caller).siblings("input").attr("dropDownType");
	 this.setData(aInData);
	 
	 this.sType=sType;
	 
	
	this.callback 									= callback ? callback : null;
	
	//this.elActiveInput 							= $(caller).siblings("input");
	//this.elActiveDropDown 					= $(caller).siblings("#elDropDown");
	this.elActiveInput=elActiveInput;
	this.elActiveDropDown=elActiveDropDown;
  
  console.info("this.sType : " + this.sType );
	this.createDropDown(this.sType);		
	this.setOptionsList(this.sType);

};

ProductEntryDropDown_Class.prototype.hide = function()
{
	$("#drop_down").detach();
};

ProductEntryDropDown_Class.prototype.isVisible = function()
{
	return $("#drop_down").is(":visible");
};

ProductEntryDropDown_Class.prototype.setEvents = function()
{
	//console.info("setEvents");
	var that = this;
	$(".dd_item")
		.mousedown(function()
			{
				console.info("setEvents - click");
				var sValue = $(this).attr("val");
				var sPopulatedAttributeValue =  $(this).attr("populatedAttributeVal");
				console.log("click :: value = ",sValue);
				console.log("click :: sPopulatedAttributeValue = ",sPopulatedAttributeValue);
				
				//$(that.elActiveInput).val(sValue);
				$(that.elActiveInput).attr("value",sValue);
				$(that.elActiveInput).attr("selectedVal",sValue);
                $(that.autoPopulateElem).val(sPopulatedAttributeValue);
				
				that.hide();
				if (that.callback)
				{
					that.callback(sPopulatedAttributeValue);
				}
			}
		);
};


ProductEntryDropDown_Class.prototype.setData = function(aInData)
{	  
  var aKeys = $(aInData[0]).children();
  var aData = [];
   
  for (i=0; i<aInData.length; i++)
  {
  	var aDataCell=[]; 
    for (j=0; j< aKeys.length; j++)
    {
    	var sKey=$(aKeys[j]).attr('id');
    	var sValue=$(aInData[i]).find ("#"+$(aKeys[j]).attr('id')).attr('value')
    	
    	//console.info ("Mati - setData ::: sKey: " + sKey + "  sValue: " + sValue);
   	 
      aDataCell[sKey] = sValue;
    }	    
  	
  	aData.push(aDataCell);
  }
  this.aData = aData;
}

