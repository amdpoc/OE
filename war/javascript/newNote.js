// *** Created by okotlia 03/17/2011
var newNote;

$(function() 
	{
		newNote = new NewNote_Class();
	}
);

NewNote_Class = function()
{
    this.init();
};

NewNote_Class.prototype.init = function()
{
    var that = this;
	this.elHome = $('#home').show();
    this.elLoader = $('#loader').hide();
	this.elPanelLoader = $("#loader");
	this.elEmptySummary = $("#emptySummary");
	this.elNoteText = $("#newNote_text");
	this.elCloseBtn = $("#closeNewNote");
	this.elCreateBtn = $("#createNewNote");
	this.elCreateBtnPassive = $("#createNewNotePassive");

	this.prevPage = prevPage;
    
    this.custNotesList = custNotesList;
    this.custId = custId;
    this.title = title;
    
    this.utils = new UtilsClass();
	this.utils.setDWR();
    this.renderPane();
    this.activateCreateButton();

	this.setEvents();
};

NewNote_Class.prototype.setEvents = function() 
{
	var that = this;
	$("#backBtn")
		.click(function()
			{
				that.elLoader.show();
				window.location = that.prevPage;
			}
		);
	
	$("#homeBtn")
		.click(function()
			{	
				that.elLoader.show();
				window.location.href = "customerList.htm";
			}
		);

    $('#logout')
		.click(function() 
			{
				that.elLoader.show();
				window.location.href = 'logout.htm';
	        }
	    );

	this.elNoteText
		.keyup(function()
			{
				that.activateCreateButton();
			}
		);
	
	this.elCloseBtn
		.click(function()
			{
				that.elLoader.show();
				window.location = that.prevPage;
			}
		);
	
	this.elCreateBtn
		.click(function()
			{
				that.elPanelLoader.show();
				var sText = that.elNoteText.val();
		    	
		    	var callback = function(res)
			    {
			    	console.log("callback ::::: *** ::::: result = ",res);
			    	if (res.errorCode == "0")   //success
			        {
			    		window.location = that.prevPage;
			        } 
			    	else 
			        {
			            alert(res.errorMessage);
				        that.elPanelLoader.hide();
			        }
			    };
			    DwrCustNotesManager.createNewCustNote(that.custId, sText, callback);
			}
		);
	
	/*
	$("#custTitle")
		.click(function()
			{
				that.elLoader.show();
				window.location.href = "customerDetails.htm?custId=" + that.custId;
			}
		);
	 */
};

NewNote_Class.prototype.renderPane = function()
{
	if (this.custNotesList.length > 0)
	{
		this.renderNotesList();
	}
	else
	{
		this.elEmptySummary.show();
	}
};

NewNote_Class.prototype.renderNotesList = function() 
{
	var aHTML = [];
	var aNotes = []
	for (var i = 0, iL = this.custNotesList.length; i < iL; i++)
	{
		var oNote = this.custNotesList[i];
        var sNoteText = oNote.noteText && oNote.noteText != "null" ? unescape(oNote.noteText) : "";
        sNoteText = sNoteText.replace(/\&gt;/g, ">");
        sNoteText = sNoteText.replace(/\&lt;/g, "<");
        aNotes.push(sNoteText);

		aHTML.push("<div id='" + oNote.noteId + "' class='opptsRow'>");
		aHTML.push("	<div class='detailsLabel'>Creation Date</div>");
		aHTML.push("	<div class='detailsContent'><span>" + oNote.creationDate + "</span></div>");
		aHTML.push("	<div class='detailsLabel'>Created By</div>");
		aHTML.push("	<div class='detailsContent'>" + oNote.repName + "</div>");
		aHTML.push("	<div class='detailsLabel'>Note Text</div>");
		aHTML.push("	<div class='detailsContent_textarea note' id='note_" + i + "' index=" + i + ">");
		//aHTML.push(			sNoteText);
		aHTML.push("	</div>");
		aHTML.push("</div>");		
	}
	console.log("sNoteText ::::::: aNotes=",aNotes);
	$("#notesListContent").html(aHTML.join(""));
	$("#notes").show();
	
	for (var j = 0, jL = this.custNotesList.length; j < jL; j++)
	{
		var nodeNote = document.getElementById("note_" + j);
		nodeNote.innerHTML = aNotes[j];
	}
	/*
	$("div.note")
		.each(function()
			{
				var i = $(this).attr("index");
				this.innerHTML(aNotes[i]);
			}
		);
	*/
	
};

NewNote_Class.prototype.activateCreateButton = function()
{
	if (this.elNoteText.val() != "")
	{
		this.elCreateBtnPassive.hide();
		this.elCreateBtn.show();
	}
	else
	{
		this.elCreateBtnPassive.show();
		this.elCreateBtn.hide();		
	}
};