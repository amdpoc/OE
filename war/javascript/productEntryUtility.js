// Utility functions
// -----------------
// *** Created by Mati Shahrabani 03/14/2013


var productEntryUtility;

$(function()
	{
		productEntryUtility = new ProductEntryUtility_Class();
	}
);

ProductEntryUtility_Class = function()
{
	this.init();
}

ProductEntryUtility_Class.prototype.init = function()
{
	

};

ProductEntryUtility_Class.prototype.setInputType = function(elem)
{
  var sInputType= "";	
  var str=elem.fieldType;  	

  if (elem.definitionType == 'I')
  {
  	sInputType='checkbox';
  }
  else
  {
  	switch (str)
  	{
  		case "TE":
  		  sInputType="text";
  			break;
  		case "NU":
  		  sInputType="number";
  			break;
  		case "DA":
  		  sInputType="date";
  			break;
  		case "TI":
  			sInputType="time";
  			break;
  		case "TN":
  			sInputType="tel";
  			break;
  		default:
  			sInputType="text";			
  	}
	}
	
	return sInputType;
}

ProductEntryUtility_Class.prototype.setFieldClasses = function(relationType)
{
	var aClasses=[];
	aClasses.dataClass = "updateInput";
	
	if (relationType=="MO")
  {
  	aClasses.labelClass="mandatoryLabelTd";
  	aClasses.mandatoryIconClass="mandatoryIconShow";
  }
  else
  {
 		aClasses.labelClass="labelTd";
  	aClasses.mandatoryIconClass="mandatoryIconHide";   			
  }

	if (relationType=="RO")
  {
  	aClasses.dataClass = "readOnlyInput";
  }  
  
  return aClasses;
}

//----------------------------------------------------------------------------------
// 	"slvInfo":
//		{"columnValues":[
//	 		{"codeColumnValue":"10","decodeColumnValue":"Brussel","populatedFieldValue":"14"}, 
//		 
//----------------------------------------------------------------------------------
ProductEntryUtility_Class.prototype.handleSLV = function(aData,aHTML,aDropDownClasses)
{ 
	// SLV Input
    // ----------
    var sClass="dropDown_" + aData.attributeCode;
    aDropDownClasses.push(sClass);
  
    //aHTML.push("<input id = '" + aData.attributeCode + "' class= 'attributeData " + sClass + "' dropDownType='ATTRIBUTE_SLV' type ='"+ this.setInputType(aData.fieldType) +"' value=\"" + aData.defaultValue +"\"></input>");

    aHTML.push("<input id = '" + aData.attributeCode + "' class= 'attributeData' type ='"+ this.setInputType(aData.fieldType) +"' value=\"" + aData.defaultValue +"\"></input>");
   	aHTML.push("<span dropDownType='ATTRIBUTE_SLV' class ='dropDownArrow " + sClass + "'></span>");
    aHTML.push("<span id ='populatedField' name = '" + aData.slvInfo.populatedFieldAttribute + "' class='hidden_element'></span>");

    	 
    // SLV data - hidden
    // -----------------
    for (var i=0;i  < aData.slvInfo.columnValues.length;i++)
    {
  	    aHTML.push("<span id = 'dropDownList' class='hidden_element'>");
  	    aHTML.push("	<span id = 'codeColumnValue' 	     	value='" + aData.slvInfo.columnValues[i].codeColumnValue + "'>		</span>");
  	    aHTML.push("	<span id = 'decodeColumnValue' 	     	value='" + aData.slvInfo.columnValues[i].decodeColumnValue + "'>	</span>");
  	    aHTML.push("	<span id = 'populatedFieldValue'     	value='" + aData.slvInfo.columnValues[i].populatedFieldValue + "'>	</span>");
        aHTML.push("</span>");
    }
  
    // SLV div
    // -------
    aHTML.push ("<div id ='elDropDown'></div>");
}


//----------------------------------------------------------------------------------
// 	"values":[
//		{valueContent":"NO","valueId":1,"valueDesc":"No"}
//----------------------------------------------------------------------------------
ProductEntryUtility_Class.prototype.handleLOV = function(aData,aHTML,aDropDownClasses)
{   
	console.info("ProductEntryUtility_Class.prototype.handleLOV - attribute: " + aData.attributeCode  + " aData.values.length: " + aData.values.length); 	
  // LOV Input
  // ----------
  switch (aData.values.length)
  {
  	case 0:  // Change 0  to 2 to see radio button when two values in LOV 
  	  var sClass="radioButton_" + aData.attributeCode;
  	  
  	  console.info ("Radio Button: " + aData.attributeCode);
  	  for (var i=0; i<aData.values.length; i++)
  	  {
  	  	aHTML.push("<input type='radio' name='" + aData.attributeCode + "' value=' " + aData.values[i].valueContent + "'>" + aData.values[i].valueDesc + "</input>");
  	  }
  		break;
  			
  	default:
 			var sClass="dropDown_" + aData.attributeCode;
 			aDropDownClasses.push(sClass);
 			
 			//aHTML.push("<input id = '" + aData.attributeCode + "' class= 'attributeData " + sClass + "' dropDownType='ATTRIBUTE_LOV' type ='"+ this.setInputType(aData.fieldType) +"' value=\"" + aData.defaultValue +"\"></input>");
 			aHTML.push("<input id = '" + aData.attributeCode + "' class= 'attributeData fleft' type ='"+ this.setInputType(aData.fieldType) +"' value=\"" + aData.defaultValue +"\"></input>");
 			aHTML.push("<span dropDownType='ATTRIBUTE_LOV' class ='dropDownArrow fleft " + sClass + "'></span>");

 			// LOV data - hidden
 			// -----------------
 			for (var i=0; i< aData.values.length; i++)
 			{
 				aHTML.push("<span id = 'dropDownList' class='hidden_element'>");
 				aHTML.push("	<span id = 'valueContent' value='" + aData.values[i].valueContent + "'>	</span>");
 				aHTML.push("	<span id = 'valueId' 			value='" + aData.values[i].valueId + "'>			</span>");
 				aHTML.push("	<span id = 'valueDesc' 		value='" + aData.values[i].valueDesc + "'>		</span>");
 			 	aHTML.push("</span>");
 			}
 			
 			// SLV div
 			// -------
 			aHTML.push ("<div id ='elDropDown'></div>");	
  
  }
}