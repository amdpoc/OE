ProposalsTopBarModule_Class = function(caller)
{
    this.caller = caller;
};

ProposalsTopBarModule_Class.prototype.init = function()
{
   this.setEvents();
};

ProposalsTopBarModule_Class.prototype.setEvents = function(type)
{
    var that = this;
	$('.topTab')
        .unbind()
		.click(function()
			{
				if (that.caller.caller.enableTransFlag)
                {
                    that.caller.savePreviousProposalToSession();
                }

                var sType = $(this).attr("type");
				that.caller.sType = sType;
				that.highlightActiveTab(sType);
				that.caller.caller.elLoader.show();

				switch (sType)
				{
					case "proposalA":
						that.caller.iProposalId = 1;
                        that.caller.caller.proposalId = 1;
						break;
					case "proposalB":
						that.caller.iProposalId = 2;
                        that.caller.caller.proposalId = 2;
						break;
					case "proposalC":
						that.caller.iProposalId = 3;
                        that.caller.caller.proposalId = 3;
						break;
					case "proposalD":
						that.caller.iProposalId = 4;
                        that.caller.caller.proposalId = 4;
						break;
					case "pendingPublish":
						that.caller.iProposalId = 5;
                        that.caller.caller.proposalId = 5;
						break;
					case "newEnvelop":
						that.caller.iProposalId = 6;
                        that.caller.caller.proposalId = 6;
						break;
					default:
						break;
				}

				console.log("ProposalsTopBarModule_Class :::: that.caller = ",that.caller);
                //eSign_Data.setActiveProposal(that.caller.iProposalId);

				if (sType == "newEnvelop")
				{
					that.caller.renderNewEnvelopTab();
				}
				else
				{
					that.caller.renderProposal();
				}
			}
		);
};

ProposalsTopBarModule_Class.prototype.setProposalId = function(id)
{
    this.iProposalId = id;
};

ProposalsTopBarModule_Class.prototype.highlightActiveTab = function(sType)
{
    $('.topTab').removeClass("activeTab");
    if (!sType)
    {
        console.log("................. highlightActiveTab .................");
        var sProposal =  this.iProposalId + "";
        switch (sProposal)
        {
            case "1":
                this.sType = "proposalA";
                break;
            case "2":
                this.sType = "proposalB";
                break;
            case "3":
                this.sType = "proposalC";
                break;
            case "4":
                this.sType = "proposalD";
                break;
            case "5":
                this.sType = "pendingPublish";
                break;
            case "6":
                this.sType = "newEnvelop";
                break;
            default:
                break;
        }
    }
    var sTypeID = sType ? sType : this.sType;
    $("a[type='" + sTypeID + "']").addClass("activeTab");
};
