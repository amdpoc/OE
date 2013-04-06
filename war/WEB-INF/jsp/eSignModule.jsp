<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script src="javascript/envelopeESign.js"></script>
<script src="javascript/eSignModule.js"></script>

<div id="eSign" class="details_block none">
	
	<div class="panel_tabbar_mask"></div>
	<div class="panel_tabbar">
		<%@ include file="proposalsTopBarModule.jsp" %>
	</div>
	<div class="tabbed_content">
			
		<div id="emptyProposal" class="panelMessage angled none top10">
			<img class="pin" src="images/Bg.Pin.gif"/><span></span>
		</div>
		
		<div id="billingAccounts">
			<div id="packages" class="none">Packages:
				<select class="fright">
					<option value="new">New</option>
				</select>
			</div>
			<div id="baTitle">Billing Accounts
				<!-- 
				<div id="addNewBa" class="fright ui-state-active ui-corner-all gadgetAddBtn">
					<span class="ui-icon ui-icon-plus"></span>
				</div>
				-->
			</div>
			<div id="bas" class="scroller_native">
				<div id="accounts_content"></div>
			</div>
		</div>
		<div id="billingAccount">
			
			<div id="baInfoDiv" class="none">
				<div class='group_title'>
					<a class="no_deco" id='customerName'></a>
				</div>			
				<div class='detailsLabel'>Billing Account Name:<span class="mandatory">*</span></div>
				<div class='detailsContent'>
					<input class="customInput width99 validate baInfo" id="baAccountName" value="" autocapitalize="off" autocorrect="off" prdCode="" type="text" />			           	
				</div>			
				<div class='detailsLabel'>Billing Phone Number:<span class="mandatory">*</span></div>
				<div class='detailsContent'>
			   		<input class="phone baInfo" id="baTn" type="text" pattern="\d*" size="3" autocorrect="off" autocapitalize="on" maxlength="3" value=""/>
			       	<input class="phone baInfo" id="baCop" type="text" pattern="\d*" size="3" autocorrect="off" autocapitalize="on" maxlength="3" value=""/>
					<input class="phone ph3 baInfo" id="baLineNo" type="text" pattern="\d*" size="4" autocorrect="off" autocapitalize="on" maxlength="4" value=""/>
				</div>
				<div class='detailsLabel'>Billing Address:<span class="mandatory">*</span></div>
				<div class='detailsContent'>
			    	<input placeholder="Example: 123 Main St Kinston NC 28888-3611" class="customInput width99 validate baInfo" id="baAddress" value="" autocapitalize="off" autocorrect="off" prdCode="" type="text" />
				</div>

                <div class='group_devider5 fleft'>&nbsp;</div>

                <div class='detailsLabel'>Add Products:<span class="mandatory none" id="add_products_asterisk">*</span></div>
				<div class='detailsContent_textarea'>
					<input class="customInput" id="prdInputString" placeholder="minimum 3 characters" value="" autocapitalize="off" autocorrect="off" prdCode="" type="search" />
					<div id="prdLookupBtn" class="fright ui-state-active ui-corner-all gadgetSearchBtn">
						<span class="ui-icon ui-icon-search"></span>
					</div>
				</div>
			</div>
		</div>
		<div id="esign_table" class="products_table fleft">
			<div class="table_header">
				<div class="fleft header prd_name">PRODUCT NAME </div>
       			<div class="fleft header">PRODUCT CODE</div>
       			<div class="fleft header prd_date">ISSUE/START-END DATE</div>
       			<div class="fleft header">MONTHLY BILLING</div>
       			<div class="fleft header_last">&nbsp;</div>
       		</div>
       		<div class="table_body">
       			<div class="body_content scroller_native">
		     		<div class="fleft table_row empty_0">
		     			<div class="fleft cell prd_name">&nbsp;</div>
		     			<div class="fleft cell">&nbsp;</div>
		     			<div class="fleft cell prd_date">&nbsp;</div>
		     			<div class="fleft cell">&nbsp;</div>
		     			<div class="fleft cell_last">&nbsp;</div>
		     		</div>
		     		<div class="fleft table_row empty_1">
		     			<div class="fleft cell prd_name">&nbsp;</div>
		     			<div class="fleft cell">&nbsp;</div>
		     			<div class="fleft cell prd_date">&nbsp;</div>
		     			<div class="fleft cell">&nbsp;</div>
		     			<div class="fleft cell_last">&nbsp;</div>
		     		</div>
		     		<div class="fleft table_row empty_2">
		     			<div class="fleft cell prd_name">&nbsp;</div>
		     			<div class="fleft cell">&nbsp;</div>
		     			<div class="fleft cell prd_date">&nbsp;</div>
		     			<div class="fleft cell">&nbsp;</div>
		     			<div class="fleft cell_last">&nbsp;</div>
		     		</div>
		     		<div class="fleft table_row empty_3">
		     			<div class="fleft cell prd_name">&nbsp;</div>
		     			<div class="fleft cell">&nbsp;</div>
		     			<div class="fleft cell prd_date">&nbsp;</div>
		     			<div class="fleft cell">&nbsp;</div>
		     			<div class="fleft cell_last">&nbsp;</div>
		     		</div>
		     		<div class="fleft table_row empty_4">
		     			<div class="fleft cell prd_name">&nbsp;</div>
		     			<div class="fleft cell">&nbsp;</div>
		     			<div class="fleft cell prd_date">&nbsp;</div>
		     			<div class="fleft cell">&nbsp;</div>
		     			<div class="fleft cell_last">&nbsp;</div>
		     		</div>
		     		<div class="fleft table_row empty_5">
		     			<div class="fleft cell prd_name">&nbsp;</div>
		     			<div class="fleft cell">&nbsp;</div>
		     			<div class="fleft cell prd_date">&nbsp;</div>
		     			<div class="fleft cell">&nbsp;</div>
		     			<div class="fleft cell_last">&nbsp;</div>
		     		</div>
		     		<div class="fleft table_row empty_6">
		     			<div class="fleft cell prd_name">&nbsp;</div>
		     			<div class="fleft cell">&nbsp;</div>
		     			<div class="fleft cell prd_date">&nbsp;</div>
		     			<div class="fleft cell">&nbsp;</div>
		     			<div class="fleft cell_last">&nbsp;</div>
		     		</div>
     			</div>
    		</div>
			<div class="table_footer">
				<div class="fleft table_row">
					<div class="fleft prd_name" id="billing_total">&nbsp;</div>
					<div class="fleft cell">&nbsp;</div>
					<div class="fleft cell prd_date border_right tright">TOTAL MONTHLY BILLING:</div>
					<div class="fleft cell" id="billing_total_value">&nbsp;</div>
					<div class="fleft cell_last">&nbsp;</div>
				</div>
			</div>
		</div>
	    
	    <!-- CONTROLS FOOTER -->
		<div class="full_screen_panel_bottomControl" id="esign_footer">
			
	   		<div class="ui-state-highlight ui-corner-all4 btn-icon fright" id="cancelESign">
	   			<span class="ui-icon ui-icon-cancel"></span>Cancel
			</div>
	       	<div class="ui-state-active ui-corner-all4 btn-icon fleft none" id="submit_eSign">
	   			<span class="ui-icon ui-icon-pencil"></span>Generate Billing Agreement
			</div>
			<div class="ui-state-passive ui-corner-all4 fleft btn-icon" id="submit_eSign_passive">
				<span class="ui-icon ui-icon-pencil"></span>Generate Billing Agreement
			</div>             	
			<!--
			<div class="ui-state-active ui-corner-all4 btn-icon fleft none" id="submit_remoteSign">
	   			<span class="ui-icon ui-icon-pencil"></span>Remote Sign
			</div>
			<div class="ui-state-passive ui-corner-all4 fleft btn-icon" id="submit_remoteSign_passive">
				<span class="ui-icon ui-icon-pencil"></span>Remote Sign
			</div>
			            	
	       	<div class="ui-state-active ui-corner-all4 btn-icon fleft none" id="save_draft">
	   				<span class="ui-icon ui-icon-disk"></span>Save Draft
			</div>
	       	<div class="ui-state-passive ui-corner-all4 btn-icon fleft" id="save_draft_passive">
	   				<span class="ui-icon ui-icon-disk"></span>Save Draft
			</div>
			--> 
		</div>
	</div> 
</div>