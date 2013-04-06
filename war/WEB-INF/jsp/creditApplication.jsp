<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script src="javascript/creditApplication.js"></script>

<div id="credit" class="details_block none">
    <div class="scroller_native height88">
        <div id="credit_application_content" class="listContent">
            <div class="block_div">
                <div class="detailsLabel">Local Marketing Consultant:</div>
                <div id="credit_LMC_name" class="detailsContent2"></div>
                <div class="detailsLabel2">LMC ID#:</div>
                <div id="credit_LMC_id" class="detailsContent2 lmc"></div>
                <div class='detailsLabel2'>Date:</div>
                <div class='detailsContent2 mleft1pct' id="credit_date_value"></div>
                <div class='group_devider5 fleft'>&nbsp;</div>
            </div>
            <div id="credit_business_data" class="block_div">
                <div class='detailsLabel'>Address:</div>
                <div class='detailsContent' id="credit_customer_address"></div>
                <div class="detailsLabel">City:</div>
                <div id="credit_customer_city_value" class="detailsContent2"></div>
                <div class="detailsLabel2">State:</div>
                <div id="credit_customer_state_value" class="detailsContent2 width5 mleft1pct"></div>
                <div class="detailsLabel2">Zip:</div>
                <div id="credit_customer_zip" class="detailsContent2 mleft1pct"></div>
                <div class="detailsLabel">Phone No:</div>
                <div id="credit_contact_phone" class="detailsContent2"></div>
                <div class="detailsLabel2">Fax No:</div>
                <div id="credit_contact_fax" class="detailsContent2"></div>
                <div class="detailsLabel">Owner/Principal Name:</div>
                <div id="credit_contact_name" class="detailsContent2"></div>
                <div class="detailsLabel2">Title:</div>
                <div id="credit_contact_title" class="detailsContent2 mleft1pct"></div>
                <div class='group_devider5 fleft'>&nbsp;</div>
            </div>
        </div>

        <div id="credit_form" class="block_div">
            <div class="detailsLabel">In-person Signature</div>
            <div class="detailsContent"><input type="checkbox" checked id="credit_inPersonSignature" /></div>
            <div class="detailsLabel">Contact Email <span class="mandatory">*</span></div>
            <div class="detailsContent">
                <input type="email" class="width70" id="credit_customerEmailInput" value="" />
            </div>
            <div class="detailsLabel">Contact Name <span class="mandatory">*</span></div>
            <div class="detailsContent">
                <input type="text" class="width70" id="credit_contactNameInput" value="" />
            </div>
        </div>

    </div>
    
    <!-- CONTROLS FOOTER -->
    <div class="full_screen_panel_bottomControl" id="credit_footer">

        <div class="ui-state-highlight ui-corner-all4 btn-icon fright" id="cancel_Credit">
            <span class="ui-icon ui-icon-cancel"></span>Cancel
        </div>
        <div class="ui-state-active ui-corner-all4 btn-icon fleft mleft0 none" id="submit_Credit">
            <span class="ui-icon ui-icon-pencil"></span>Send to DocuSign
        </div>
        <div class="ui-state-passive ui-corner-all4 fleft mleft0 btn-icon" id="submit_Credit_passive">
            <span class="ui-icon ui-icon-pencil"></span>Send to DocuSign
        </div>
    </div>
</div>