PrdLookUpClass = function(parent)
{
    this.prevHBDSearch = "";
    this.parent = parent;
    this.utils = this.parent.utils;
    this.init();
};

PrdLookUpClass.prototype.init = function() 
{
	this.elPopOver = $("#hbdLookup");
	this.setEvents();
	this.searchResults = false;
};

PrdLookUpClass.prototype.setEvents = function()
{
	var that = this;
	var that = this;
	$('#hbdCancelBtn')
		.click(function() 
			{
				that.elPopOver.hide();
    			that.utils.hideMask();
			}
		);    
};
PrdLookUpClass.prototype.lookupHBD = function() 
{
    var that = this;
    $('#hbdInputString').blur();
	var inputString = $('#hbdInputString').val();
    
    if (inputString.length < 3) 
    {
        alert('Search criteria has to be bigger than 3 characters!');//search.validation1
    } 
    else 
    {
        this.utils.showMask();
        if (this.prevHBDSearch == inputString) 
        {
        	if (this.searchResults)
        	{
        		this.elPopOver.show();        		
        	}
        	else
        	{
				alert('No records found!');//search.empty
    			that.utils.hideMask();
        	}
        } 
        else 
        {
            this.prevHBDSearch = inputString;
            $("#loader").show();
            $.post('ProductList', 
            		{hbdQueryString: inputString}, 
            		function(data) 
            		{
            			var oData = eval('(' + data + ')');    			
            			console.log("getProductsList callback :: oData = ",oData);
            			if (oData && oData.length > 0) 
            			{
            				that.searchResults = true;
            				console.log("rtsgwstrgrtgergt werg wrtgwrt gwretg");
            				var aHTML = [];
            				for (var i = 0, iL = oData.length; i < iL; i++)
            				{
	            				var sProductCode = oData[i].productCode;
	            				var sProductName = oData[i].productName;
	            				var sProductIssueNum = oData[i].productIssueNum;
	            				var sProjectCode = oData[i].projectCode;
	            				var sProjectIssueNum = oData[i].projectIssueNum;
	            				var sProductValue = sProductCode + "||" + sProductIssueNum + "||" + sProjectCode + "||" + sProjectIssueNum;
	            				
	            				aHTML.push('<li class="prdRow" hbdvalue="' + sProductValue + '" hbdcode="' + sProductCode + '" hbdname="' + sProductName + '">');
	        					aHTML.push(sProductName + '</li>');
            				}
            				$('#lookupHBDList').html(aHTML.join(""));
        					$('#hbdLookupTerm').text(inputString);

            				that.elPopOver.show();
            				that.scroller.setupScroller();
            		        that.scroller.scrollTo(0, 0);
            		        that.setHBDRowEvents();
            			}
            			else 
            			{
            				that.searchResults = false;
            				alert('No records found!');//search.empty
                			that.utils.hideMask();
            			}
            			$("#loader").hide();
            		}
            	);
        }
    }
};
    
PrdLookUpClass.prototype.setHBDRowEvents = function() 
{
	var that = this;
	$(".prdRow")
		.click(function()
			{
				var index = $(this).attr("index");
				that.handleHBD($(this));
			}
		)
		.mouseup(function()
			{
				return false;
			}
		);
};

PrdLookUpClass.prototype.handleHBD = function($el) 
{
	$('#hbdInputString').val($el.attr("hbdname")).attr("hbdcode", $el.attr("hbdcode"));
	this.elPopOver.hide();
	this.parent.toggleSubmitButton();
	this.utils.hideMask();
};