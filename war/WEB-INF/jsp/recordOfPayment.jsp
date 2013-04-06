<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script src="javascript/envelopeROP.js"></script>
<script src="javascript/recordOfPayment.js"></script>

<div id="record" class="details_block none">

	<div class="panel_tabbar_mask"></div>
	<div class="panel_tabbar">
		<%@ include file="proposalsTopBarModule.jsp" %>
	</div>
	<div class="tabbed_content">
        <div id="emptyProposal" class="panelMessage angled none top10">
            <img class="pin" src="images/Bg.Pin.gif"/><span></span>
        </div>
        <div id="date_block">
            <div id="date_div">
                <div class='detailsLabel'>Date:</div>
                <div class='detailsContent' id="date_value"></div>
            </div>
            <div id="new_renewal">
                <div class='detailsLabel'>New:</div>
                <div class='detailsContent'><input type="checkbox" id="check_new" disabled="true" /></div>
                <div class='detailsLabel'>Renewal:</div>
                <div class='detailsContent'><input type="checkbox" id="check_renewal" disabled="true" /></div>
            </div>
        </div>
        <div id="customer_block">

            <div id="customerInfoDiv" class="none">
                <div class='group_title'>
                    <a class="no_deco" id='rop_customerName'></a>
                </div>
                <div class='detailsLabel'>Address:<span class="mandatory">*</span></div>
                <div class='detailsContent'>
                    <input placeholder="Example: 123 Main St" class="customInput width99 validate baInfo" id="customer_address" value="" autocapitalize="off" autocorrect="off" prdCode="" type="text" />
                </div>

                <div class="detailsLabel">City:<span class="mandatory">*</span></div>
                <div id="customer_city" class="detailsContent2">
                    <input placeholder="Kinston" class="customInput width99 validate baInfo" id="customer_city_value" value="" autocapitalize="off" autocorrect="off" prdCode="" type="text" />
                </div>
                <div class="detailsLabel3">State:<span class="mandatory">*</span></div>
                <div id="customer_state" class="detailsContent2 lmc">
                    <select class="customInput width99 validate baInfo" id="customer_state_value" value="" autocapitalize="off" autocorrect="off" prdCode="" type="text">
                        <%@ include file="statesOptions.jsp" %>
                    </select>
                </div>
                <div class="detailsLabel3">Zip:</div>
                <div id="customer_zip" class="detailsContent2 lmc">
                    <input placeholder="12345" class="customInput width35 validate baInfo" id="customer_zip_5" value="" autocapitalize="off" autocorrect="off" prdCode="" type="text" />
                    <span class="mandatory">*</span>&nbsp;-&nbsp;
                    <input placeholder="6789" class="customInput width30 validate baInfo" id="customer_zip_4" value="" autocapitalize="off" autocorrect="off" prdCode="" type="text" />
                </div>

                <div class='group_devider1 fleft'>&nbsp;</div>

                <div class="detailsLabel">Local Marketing Consultant:</div>
                <div id="LMC_name" class="detailsContent2"></div>
                <div class="detailsLabel2">LMC ID#:</div>
                <div id="LMC_id" class="detailsContent2 lmc"></div>
                <div class="detailsLabel2">Office:</div>
                <div id="LMC_office" class="detailsContent2 lmc"></div>

                <div class='detailsLabel'>Add Products:<span class="mandatory none" id="add_products_asterisk">*</span></div>
                <div class='detailsContent_textarea'>
                    <input class="customInput" id="rop_prdInputString" placeholder="minimum 3 characters" value="" autocapitalize="off" autocorrect="off" prdCode="" type="search" />
                    <div id="rop_prdLookupBtn" class="fright ui-state-active ui-corner-all gadgetSearchBtn">
                        <span class="ui-icon ui-icon-search"></span>
                    </div>
                </div>
            </div>
        </div>

        <div id="rop_table" class="products_table fleft">
            <div class="table_header">
                <div class="fleft header two_lines type_payment">TYPE OF PAYMENT</div>
                <div class="fleft header two_lines">ACCOUNT NUMBER</div>
                <div class="fleft header two_lines phone_number">TELEPHONE NUMBER</div>
                <div class="fleft header prd_name">DIRECTORY NAME</div>
                <div class="fleft header two_lines">PRODUCT CODE</div>
                <div class="fleft header prd_date">ISSUE/START-END DATE</div>
                <div class="fleft header two_lines">MONTHLY AMOUNT</div>
                <div class="fleft header two_lines num_months"># OF MONTHS</div>
                <div class="fleft header two_lines">AMOUNT TO APPLY</div>
                <div class="fleft header_last">&nbsp;</div>
            </div>
            <div class="table_body" class="">
                <div class="body_content scroller_native">
                    <div class="fleft table_row empty_0">
                        <div class="fleft cell type_payment">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell phone_number">&nbsp;</div>
                        <div class="fleft cell prd_name">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell prd_date">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell num_months">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell_last">&nbsp;</div>
                    </div>
                    <div class="fleft table_row empty_1">
                        <div class="fleft cell type_payment">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell phone_number">&nbsp;</div>
                        <div class="fleft cell prd_name">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell prd_date">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell num_months">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell_last">&nbsp;</div>
                    </div>
                    <div class="fleft table_row empty_2">
                        <div class="fleft cell type_payment">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell phone_number">&nbsp;</div>
                        <div class="fleft cell prd_name">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell prd_date">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell num_months">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell_last">&nbsp;</div>
                    </div>
                    <div class="fleft table_row empty_3">
                        <div class="fleft cell type_payment">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell phone_number">&nbsp;</div>
                        <div class="fleft cell prd_name">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell prd_date">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell num_months">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell_last">&nbsp;</div>
                    </div>
                    <div class="fleft table_row empty_4">
                        <div class="fleft cell type_payment">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell phone_number">&nbsp;</div>
                        <div class="fleft cell prd_name">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell prd_date">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell num_months">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell_last">&nbsp;</div>
                    </div>
                    <div class="fleft table_row empty_5">
                        <div class="fleft cell type_payment">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell phone_number">&nbsp;</div>
                        <div class="fleft cell prd_name">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell prd_date">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell num_months">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell_last">&nbsp;</div>
                    </div>
                    <div class="fleft table_row empty_6">
                        <div class="fleft cell type_payment">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell phone_number">&nbsp;</div>
                        <div class="fleft cell prd_name">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell prd_date">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell num_months">&nbsp;</div>
                        <div class="fleft cell">&nbsp;</div>
                        <div class="fleft cell_last">&nbsp;</div>
                    </div>
                </div>
            </div>
            <div class="table_footer">
                <div class="fleft table_row">
                    <div class="fleft cell type_payment">&nbsp;</div>
                    <div class="fleft cell">&nbsp;</div>
                    <div class="fleft cell phone_number">&nbsp;</div>
                    <div class="fleft cell prd_name">&nbsp;</div>
                    <div class="fleft cell">&nbsp;</div>
                    <div class="fleft cell prd_date">&nbsp;</div>
                    <div class="fleft cell">&nbsp;</div>
                    <div class="fleft num_months tright">Total:</div>
                    <div class="fleft cell" id="total_toApply">&nbsp;</div>
                    <div class="fleft cell_last">&nbsp;</div>
                </div>
            </div>
        </div>

        <!-- CONTROLS FOOTER -->
        <div class="full_screen_panel_bottomControl" id="rop_footer">

            <div class="ui-state-highlight ui-corner-all4 btn-icon fright" id="cancel_ROP">
                <span class="ui-icon ui-icon-cancel"></span>Cancel
            </div>
            <div class="ui-state-active ui-corner-all4 btn-icon fleft mleft0 none" id="submit_ROP">
                <span class="ui-icon ui-icon-pencil"></span>Generate Record of Payment
            </div>
            <div class="ui-state-passive ui-corner-all4 fleft mleft0 btn-icon" id="submit_ROP_passive">
                <span class="ui-icon ui-icon-pencil"></span>Generate Record of Payment
            </div>
        </div>

    </div>
</div>