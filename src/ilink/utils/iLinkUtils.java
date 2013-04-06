package ilink.utils;

import amdocs.mvcinfra.model.datatypes.message.SingleMessage;
import amdocs.mvcinfra.model.exceptions.ApplicationException;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import ilink.domain.*;
import ilink.security.AmdocsUserDetails;
import ilink.webservices.datatypes.WebServiceUALException;
import org.directwebremoting.WebContextFactory;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.web.context.ContextLoader;

import javax.jms.JMSException;
import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


public class iLinkUtils {

    public static final String CUST_LIST = (String) iLinkPropertiesLoader.getApplicationProperty("custList");
    public static final String CUST_DETAILS = (String) iLinkPropertiesLoader.getApplicationProperty("custDetails");
    public static final String NEW_NOTE = (String) iLinkPropertiesLoader.getApplicationProperty("newNote");

    public static final String DEFAULT_NOTE_TYPE = "N";
    public static final String DEFAULT_OFFICE_TYPE = "18";

    public static final int PANEL_0 = 0;
    public static final int PANEL_1 = 1;
    public static final int PANEL_2 = 2;
    public static final int PANEL_3 = 3;
    public static final int PANEL_4 = 4;
    public static final int PANEL_5 = 5;
    public static final int PANEL_6 = 6;
    public static final int PANEL_7 = 7;

    public static final int PROPOSAL_A = 1;
    public static final int PROPOSAL_B = 2;
    public static final int PROPOSAL_C = 3;
    public static final int PROPOSAL_D = 4;
    public static final int PENDING_PUBLISH = 5;
    public static final int NEW_ENVELOPE = 6;

    public static final String ILINK_APPL_ID = "ILNKIP";
    public static Vector VALID_SECURITY_PROFILES = (Vector) iLinkPropertiesLoader.getListApplicationProperty("validSecurityProfiles");
    public static Vector READ_ONLY_SECURITY_PROFILES = (Vector) iLinkPropertiesLoader.getListApplicationProperty("readOnlySecurityProfiles");

    public static String FIRST_DELIMETER = (String) iLinkPropertiesLoader.getApplicationProperty("fistDelimeter");
    public static String SECOND_DELIMETER = (String) iLinkPropertiesLoader.getApplicationProperty("secondDelimeter");
    public static String DATE_DELIMETER = (String) iLinkPropertiesLoader.getApplicationProperty("dateDelimeter");
    public static String STR_DELIMETER = (String) iLinkPropertiesLoader.getApplicationProperty("stringTokenizer");
    public static String STR_DELIMETER1 = (String) iLinkPropertiesLoader.getApplicationProperty("stringTokenizer1");
    public static String STATE_DELIMETER = (String) iLinkPropertiesLoader.getApplicationProperty("stateDelimeter");
    public static String PRODUCT_DELIMETER = (String) iLinkPropertiesLoader.getApplicationProperty("productDelimeter");
    public static int PRODUCT_PARSE_NUM = Integer.valueOf(iLinkPropertiesLoader.getApplicationProperty("productParseNumber"));
    public static String SUCCESS = "0";
    public static String ERROR = "1";
    public static String WARNING = "2";
    //pagination
    public static int CUST_LIST_ROW_NUM = new Integer(((String) iLinkPropertiesLoader.getApplicationProperty("custListRowNum"))).intValue();
    public static int CUST_LIST_PAGE_SIZE = new Integer(((String) iLinkPropertiesLoader.getApplicationProperty("custListPageSize"))).intValue();
    public static String CUST_LIST_PAGE_CTX = "CUST_LIST_PAGE_CTX";
    public static String NAVIGATION_HISTORY_HANDLER = "NAVIGATION_HISTORY_HANDLER";

    public static String TITLE_MANAGER = "TITLE_MANAGER";
    public static String NEXT_PAGE = "NEXT";
    public static String PREV_PAGE = "PREV";
    public static String CURRENT_PAGE = "CURRENT";

    public static enum TransStatus {
        SUCCESS, PARTIAL, WARNING, ERROR, INFO;

        @Override
        public String toString() {
            String output = name().toString(); // Example: SUCCESS
            output = output.charAt(0) + output.substring(1).toLowerCase();

            return output; // Output: Success
        }

        public boolean equals(String status) {
            return status.equals(this.toString());
        }

    }

    public static synchronized TitleManager getTitleManagerDWR() {
        return getTitleManager(WebContextFactory.get().getHttpServletRequest().getSession());
    }

    public static synchronized TitleManager getTitleManager(HttpSession session) {
        TitleManager titleManager = (TitleManager) session.getAttribute(TITLE_MANAGER);
        if (titleManager == null) {
            titleManager = (TitleManager) ContextLoader.getCurrentWebApplicationContext().getBean("titleManager");
            session.setAttribute(TITLE_MANAGER, titleManager);
        }
        return titleManager;
    }

    public static synchronized NavigationHistoryHandler getNavigationHistoryHandler(HttpSession session) {
        NavigationHistoryHandler nHadler = (NavigationHistoryHandler) session.getAttribute(NAVIGATION_HISTORY_HANDLER);
        if (nHadler == null) {
            nHadler = new NavigationHistoryHandler();
            session.setAttribute(NAVIGATION_HISTORY_HANDLER, nHadler);
        }
        return nHadler;
    }
    public static synchronized NavigationHistoryHandler getNavigationHistoryHandlerDWR() {
        return getNavigationHistoryHandler(getSessionFromDWR());
    }

    public static synchronized String getApplicationExceptionMessage(Exception ex) {
        String exceptionMsg = "Unknown exception";
        try {
            if (ex instanceof WebServiceUALException) {
                exceptionMsg = ((WebServiceUALException) ex).getDescription();
            } else {

                if (isEmpty(exceptionMsg) && (ex.getCause()) instanceof ApplicationException) {
                    ApplicationException cause = (ApplicationException) ex.getCause();
                    if (cause.m_singleVec.size() > 0) {
                        SingleMessage msg = (SingleMessage) cause.m_singleVec.elementAt(0);
                        exceptionMsg = "Application Exception: " + msg.m_messageCode + " : ";
                        if (msg.m_messageParams != null && msg.m_messageParams.length > 0)
                            exceptionMsg += msg.m_messageParams[0];
                    } else
                        exceptionMsg = ex.getCause().toString();
                } else
                    exceptionMsg = ex.getCause().toString();
            }
        } catch (Exception e) {
        }
        return exceptionMsg;
    }

    public static synchronized String getWSExceptionMessage(Exception ex) {
        String errorMessage = "";

        if (ex instanceof MalformedURLException) {
            errorMessage = "MalformedURLException: ";
        } else if (ex instanceof URISyntaxException) {
            errorMessage = "URISyntaxException: ";
        } else if (ex instanceof ParseException) {
            errorMessage = "ParseException: ";
        }
        errorMessage += getExceptionMessage(ex);
        return errorMessage;
    }

    public static synchronized String getExceptionMessage(Exception ex) {
        String exceptionMsg = "Unknown exception";
        try {
            if (ex instanceof WebServiceUALException) {
                exceptionMsg = ((WebServiceUALException) ex).getDescription();
            } if (ex instanceof JMSException )  {
                JMSException jex = (JMSException)ex;
                exceptionMsg =  " JMSException with errorCode: " + jex.getErrorCode();
                Exception linkedExp = jex.getLinkedException();
                if(linkedExp != null)
                   exceptionMsg += " with linkedException error: " +  linkedExp.getMessage();
                exceptionMsg += " with error message :" + ex.getMessage();
            }else {
                exceptionMsg = ex.getMessage();
                if (isEmpty(exceptionMsg)) {
                    exceptionMsg = ex.getCause().toString();
                }
                if (isEmpty(exceptionMsg)) {
                    exceptionMsg = ex.toString();
                }
            }
        } catch (Exception e) {
        }
        return exceptionMsg;
    }

    public static synchronized GeneralResponse handleException(Exception ex) {
        GeneralResponse response = iLinkUtils.prepareResponse(0);
        iLinkUtils.setErrorResponse(response, iLinkUtils.getMessage("exception.error",
                new Object[]{iLinkUtils.getExceptionMessage(ex)}));
        return response;
    }

    public static synchronized GeneralResponse handleException(String errorMsg) {
        GeneralResponse response = iLinkUtils.prepareResponse(0);
        iLinkUtils.setErrorResponse(response, iLinkUtils.getMessage("exception.error", new Object[]{errorMsg}));
        return response;
    }

    public static synchronized GeneralResponse handleError(String errorCode, Object[] params) {
        GeneralResponse response = iLinkUtils.prepareResponse(0);
        iLinkUtils.setErrorResponse(response, iLinkUtils.getMessage(errorCode, params));
        return response;
    }

    public static synchronized String getMessage(String messageCode, Object[] params) {

        return MessageLoader.getMessage(messageCode, params);
    }

    public static synchronized GeneralResponse prepareResponse(long customerId) {
        GeneralResponse response = new GeneralResponse();
        response.setCustomerId(customerId);
        response.setErrorCode(SUCCESS);
        response.setErrorMessage("");
        response.setStatus(iLinkUtils.TransStatus.SUCCESS);
        return response;
    }
    public static synchronized GeneralResponse prepareRequestResponse(double envelopeId, long requestId)
    {
        GeneralResponse response = new GeneralResponse();
        response.setEnvelopeId(envelopeId);
        response.setRequestId(requestId);
        response.setResult("");
        response.setErrorCode(SUCCESS);
        response.setErrorMessage("");
        response.setStatus(iLinkUtils.TransStatus.SUCCESS);
        return response;
    }

    public static synchronized void setErrorResponse(GeneralResponse response, String errorMessage) {
        response.setErrorCode(ERROR);
        response.setErrorMessage(errorMessage);
        response.setStatus(iLinkUtils.TransStatus.ERROR);
    }

    public static synchronized void setWarningResponse(GeneralResponse response, String errorMessage) {
        response.setErrorCode(WARNING);
        response.setErrorMessage(errorMessage);
        response.setStatus(iLinkUtils.TransStatus.WARNING);
    }

    public synchronized static AmdocsUserDetails getAmdocsUserDetailsFromDWR() {
        javax.servlet.http.HttpSession session = WebContextFactory.get().getHttpServletRequest().getSession();
        return getAmdocsUserDetailsFromSession(session);
    }

    public synchronized static AmdocsUserDetails getAmdocsUserDetailsFromSession(javax.servlet.http.HttpSession session) {

        return getAmdocsUserDetails(getSecurityContext(session));
    }

    public synchronized static SecurityContext getSecurityContext(javax.servlet.http.HttpSession session) {
        SecurityContext ctx = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        if (ctx == null) {
            ctx = SecurityContextHolder.getContext();
        }
        return ctx;
    }

    public synchronized static javax.servlet.http.HttpSession getSessionFromDWR() {

        return WebContextFactory.get().getHttpServletRequest().getSession();
    }

    public synchronized static String getServerName()
    {
        return WebContextFactory.get().getHttpServletRequest().getServerName();
    }

    public synchronized static DocsignManager getDocsignManagerFromDWR() {
        javax.servlet.http.HttpSession session = iLinkUtils.getSessionFromDWR();
        DocsignManager dsMng = (DocsignManager) session.getAttribute("DOCUSIGN_MANAGER");
        if (dsMng == null) {
            dsMng = (DocsignManager) ContextLoader.getCurrentWebApplicationContext().getBean("docsignManager");
            dsMng.init(session);
            session.setAttribute("DOCUSIGN_MANAGER", dsMng);
        }
        return dsMng;
    }

    public synchronized static TrackingManager getTrackingMngrFromDWR() {
        return getTrackingMngrFromSession(iLinkUtils.getSessionFromDWR());
    }
    public synchronized static TrackingManager getTrackingMngrFromSession(javax.servlet.http.HttpSession session) {
        TrackingManager trckMng = (TrackingManager) session.getAttribute("TRACKING_MANAGER");
        if (trckMng == null) {
            trckMng = (TrackingManager) ContextLoader.getCurrentWebApplicationContext().getBean("trackingManager");
            trckMng.init(session);
            session.setAttribute("TRACKING_MANAGER", trckMng);
        }
        return trckMng;
    }

    public synchronized static EsignEnvelope getLastEsignEnvelopeFromSessionDWR() {
           return getLastEsignEnvelopeFromSession(iLinkUtils.getSessionFromDWR());
    }

    public synchronized static EsignEnvelope getLastEsignEnvelopeFromSession(javax.servlet.http.HttpSession session) {
        return (EsignEnvelope) session.getAttribute("ESIGN_ENVELOPE");
    }
    public synchronized static void setLastEsignEnvelopeToSession(EsignEnvelope envelope) {
        javax.servlet.http.HttpSession session = iLinkUtils.getSessionFromDWR();
        session.setAttribute("ESIGN_ENVELOPE", envelope);
    }

    public synchronized static List<ProposalDataType> getProposalByCustomerIdFromSession(String type, long custId, int proposalId, HttpSession session)
    {
        javax.servlet.http.HttpSession session_ = session != null ? session : iLinkUtils.getSessionFromDWR();
        String sessionAtribute = "PROPOSAL_DATA" + "_" + type + "_" + Long.toString(custId) + "_" + Integer.toString(proposalId);
        return (List< ProposalDataType >) session_.getAttribute(sessionAtribute);
    }

    public synchronized static void setProposalByCustomerIdToSession(String type, long custId, int proposalId, List< ProposalDataType > proposalDataList, HttpSession session)
    {
        javax.servlet.http.HttpSession session_ = session != null ? session : iLinkUtils.getSessionFromDWR();
        String sessionAtribute = "ROP_PROPOSAL_DATA" + "_" + type + "_" + custId + "_" + proposalId;
        session_.setAttribute(sessionAtribute, proposalDataList);
    }

    public synchronized static List<ProposalDataTypeROP> getROPProposalByCustomerIdFromSession(String type, long custId, int proposalId, HttpSession session)
    {
        javax.servlet.http.HttpSession session_ = session != null ? session : iLinkUtils.getSessionFromDWR();
        String sessionAtribute = "PROPOSAL_DATA" + "_" + type + "_" + Long.toString(custId) + "_" + Integer.toString(proposalId);
        return (List< ProposalDataTypeROP >) session_.getAttribute(sessionAtribute);
    }

    public synchronized static void setROPProposalByCustomerIdToSession(String type, long custId, int proposalId, List< ProposalDataTypeROP > proposalDataList, HttpSession session)
    {
        javax.servlet.http.HttpSession session_ = session != null ? session : iLinkUtils.getSessionFromDWR();
        String sessionAtribute = "PROPOSAL_DATA" + "_" + type + "_" + custId + "_" + proposalId;
        session_.setAttribute(sessionAtribute, proposalDataList);
    }

    public synchronized static void setNewEnvelopeBillingInfoToSession(String type, long custId, EsignBillingAccount billingAccount)
    {
        javax.servlet.http.HttpSession session = iLinkUtils.getSessionFromDWR();
        String sessionAtribute = "NEW_ENVELOPE_BA_INFO" + "_" + type + "_" + custId;
        session.setAttribute(sessionAtribute, billingAccount);
    }

    public synchronized static EsignBillingAccount getNewEnvelopeBillingInfoFromSession(String type, long custId)
    {
        javax.servlet.http.HttpSession session = iLinkUtils.getSessionFromDWR();
        String sessionAtribute = "NEW_ENVELOPE_BA_INFO" + "_" + type + "_" + custId;
        return (EsignBillingAccount) session.getAttribute(sessionAtribute);
    }


    public synchronized static GeneralInfo getGeneralInfoFromSession(javax.servlet.http.HttpSession session) {
        GeneralInfo generalInfo = (GeneralInfo) session.getAttribute("GENERAL_INFO");
        if (generalInfo == null) {
            generalInfo = (GeneralInfo) ContextLoader.getCurrentWebApplicationContext().getBean("generalInfo");
            generalInfo.setEmployeeName(getEmployeeNameFromSession(session));
            generalInfo.setEmployeeId(getEmployeeIdFromSession(session));
            generalInfo.setOfficeName(getOfficeNameFromSession(session));
            generalInfo.setLogicalDate(iLinkUtils.getCurrentLogicalDate());
            session.setAttribute("GENERAL_INFO", generalInfo);
        }
        return generalInfo;
    }

    public synchronized static String getEmployeeNameFromDWR() {
        return getEmployeeNameFromSession(WebContextFactory.get().getHttpServletRequest().getSession());
    }

    public synchronized static String getEmployeeNameFromSession(javax.servlet.http.HttpSession session) {
        AmdocsUserDetails obj = getAmdocsUserDetails(getSecurityContext(session));
        return obj.getFirstName() + " " + obj.getLastName();
    }

    public synchronized static String getOfficeNameFromSession(javax.servlet.http.HttpSession session)
    {
        AmdocsUserDetails obj = getAmdocsUserDetails(getSecurityContext(session));
        return obj.getOfficeName();
    }

    public synchronized static String getEmployeeEmailFromSession(javax.servlet.http.HttpSession session) {
        AmdocsUserDetails obj = getAmdocsUserDetails(getSecurityContext(session));
        return obj.getEMail();
    }

    public synchronized static String getEmployeeIdFromDWR() {

        return getEmployeeIdFromSession(WebContextFactory.get().getHttpServletRequest().getSession());
    }

    public synchronized static String getEmployeeIdFromSession(javax.servlet.http.HttpSession session) {
        AmdocsUserDetails obj = getAmdocsUserDetails(getSecurityContext(session));
        return obj.getEmployeeNumber();
    }

    public synchronized static String getLoginNameFromSession(javax.servlet.http.HttpSession session) {
        AmdocsUserDetails obj = getAmdocsUserDetails(getSecurityContext(session));
        return obj.getUsername();
    }

    public synchronized static String getLoginPasswordFromSession(javax.servlet.http.HttpSession session) {
        AmdocsUserDetails obj = getAmdocsUserDetails(getSecurityContext(session));
        return obj.getPassword();
    }

    public synchronized static String getMailBoxFromSession(javax.servlet.http.HttpSession session) {

        AmdocsUserDetails obj = getAmdocsUserDetails(getSecurityContext(session));
        String mail = obj.getEMail();
        if (isEmpty(mail)) {
            mail = obj.getFirstName() + "." + obj.getLastName() + "@dexone.com";
        }
        /* if (!isEmpty(mail)) {
            mailBox = mail.substring(0, mail.indexOf("@"));
        } else {
            mailBox = obj.getFirstName() + "." + obj.getLastName();
      }  */
        return mail;
    }

    public synchronized static String getManagerEmailFromDWR() {
        return getManagerEmailFromSession(WebContextFactory.get().getHttpServletRequest().getSession());
    }

    public synchronized static String getManagerEmailFromSession(javax.servlet.http.HttpSession session) {
        AmdocsUserDetails obj = getAmdocsUserDetails(getSecurityContext(session));
        return obj.getManagerEmail();
    }

    public synchronized static boolean getReadOnlyFromSession(javax.servlet.http.HttpSession session) {
        AmdocsUserDetails obj = getAmdocsUserDetails(getSecurityContext(session));
        return obj.isReadOnly();
    }


    public synchronized static boolean isEmployeeProfileReadOnly(javax.servlet.http.HttpSession session) {
        Boolean isReadOnly = (Boolean) session.getAttribute("READ_ONLY");
        if (isReadOnly == null) {
            AmdocsUserDetails obj = getAmdocsUserDetails(getSecurityContext(session));
            isReadOnly = obj.isReadOnly();
            session.setAttribute("READ_ONLY", isReadOnly);
        }
        return isReadOnly;
    }

    public synchronized static String getEmployeeRoleFromDWR() {
        return getEmployeeRoleFromSession(WebContextFactory.get().getHttpServletRequest().getSession());
    }

    public synchronized static String getEmployeeRoleFromSession(javax.servlet.http.HttpSession session) {

        AmdocsUserDetails obj = getAmdocsUserDetailsFromSession(session);
        return obj.getRole();
    }

    public synchronized static String getEmployeeOfficeTypeFromDWR() {
        return getEmployeeOfficeTypeFromSession(WebContextFactory.get().getHttpServletRequest().getSession());
    }

    public synchronized static String getEmployeeOfficeNameFromDWR() {
        return getEmployeeOfficeNameFromSession(WebContextFactory.get().getHttpServletRequest().getSession());
    }

    public synchronized static String getEmployeeOfficeTypeFromSession(javax.servlet.http.HttpSession session) {

        AmdocsUserDetails obj = getAmdocsUserDetailsFromSession(session);
        return obj.getOfficeType();
    }
    public synchronized static String getEmployeeOfficeNameFromSession(javax.servlet.http.HttpSession session)
    {
        AmdocsUserDetails obj = getAmdocsUserDetailsFromSession(session);
        return obj.getOfficeName();
    }

    public synchronized static List<String> getEmployeePositionsFromDWR() {
        return getEmployeePositionsFromSession(WebContextFactory.get().getHttpServletRequest().getSession());
    }

    public synchronized static List<String> getEmployeePositionsFromSession(javax.servlet.http.HttpSession session) {

        List<String> activityCodeList = new ArrayList<String>();
        AmdocsUserDetails obj = getAmdocsUserDetailsFromSession(session);
        StringTokenizer activityCodeStrArray = new StringTokenizer(obj.getActivityCode(), STR_DELIMETER);
        while (activityCodeStrArray.hasMoreTokens()) {
            activityCodeList.add(activityCodeStrArray.nextToken());
        }
        return activityCodeList;
    }

    public synchronized static boolean isEmployeeHasSamePositionDWR(String position) {
        List<String> activityCodeList = getEmployeePositionsFromDWR();
        return isEmployeeHasSamePosition(activityCodeList, position);
    }

    public synchronized static boolean isEmployeeHasSamePosition(javax.servlet.http.HttpSession session, String position) {
        List<String> activityCodeList = getEmployeePositionsFromSession(session);
        return isEmployeeHasSamePosition(activityCodeList, position);
    }

    public synchronized static boolean isEmployeeHasSamePosition(List<String> activityCodeList, String position) {

        boolean status = false;
        if (activityCodeList != null && !activityCodeList.isEmpty()) {
            Iterator it = activityCodeList.iterator();
            while (it.hasNext()) {
                if ((it.next()).equals(position)) {
                    status = true;
                    break;
                }
            }
        }
        return status;
    }


    public synchronized static List<String> getSecurityProfilesListFromDWR() {

        return getSecurityProfilesListFromSession(WebContextFactory.get().getHttpServletRequest().getSession());
    }

    public synchronized static List<String> getSecurityProfilesListFromSession(javax.servlet.http.HttpSession session) {

        List<String> secProfileList = new ArrayList<String>();
        AmdocsUserDetails obj = getAmdocsUserDetailsFromSession(session);
        StringTokenizer secProfStrArray = new StringTokenizer(obj.getSecurityProfile(), STR_DELIMETER);
        while (secProfStrArray.hasMoreTokens()) {
            secProfileList.add(secProfStrArray.nextToken());
        }
        return secProfileList;
    }

    public synchronized static boolean checkSecurityProfile(String secProfile) {
        return (iLinkUtils.VALID_SECURITY_PROFILES.contains(secProfile));
    }

    public synchronized static boolean checkReadOnlySecurityProfile(String secProfile) {
        return (iLinkUtils.READ_ONLY_SECURITY_PROFILES.contains(secProfile));
    }

    public synchronized static boolean checkSecurityProfilesList(List<String> secProfileList) {
        boolean status = false;
        Iterator it = secProfileList.iterator();
        while (it.hasNext()) {
            if (checkSecurityProfile((String) it.next())) {
                status = true;
                break;
            }
        }
        return status;
    }


    public synchronized static boolean checkReadOnlySecurityProfilesList(List<String> secProfileList) {
        boolean status = false;
        Iterator it = secProfileList.iterator();
        while (it.hasNext()) {
            if (checkReadOnlySecurityProfile((String) it.next())) {
                status = true;
                break;
            }
        }
        return status;
    }

    public static String converListToString(List<String> inList, String delimeter) {
        String outStr = "";
        Iterator it = inList.iterator();
        while (it.hasNext()) {
            outStr += (it.next() + delimeter);
        }
        return outStr;
    }

    public synchronized static AmdocsUserDetails getAmdocsUserDetails(SecurityContext sctx) {
        Authentication auth = sctx.getAuthentication();

        if (auth.getPrincipal() instanceof AmdocsUserDetails) {
            return (AmdocsUserDetails) auth.getPrincipal();
        } else {
            return null;
        }
    }

    public synchronized static boolean isEmpty(String inStr) {
        return (inStr == null || inStr.trim().equals("") || inStr.equals("undefined"));
    }

    public synchronized static String trim(String inStr) {
        if (!isEmpty(inStr)) {
            inStr = inStr.replaceAll("\n", "");
            inStr = inStr.replaceAll("\r", "");
            inStr = inStr.trim();
        }
        return inStr;
    }

    public synchronized static boolean between(String date, String startDate, String endDate) {
        return (startDate.compareTo(date) <= 0 &&
                endDate.compareTo(date) >= 0);
    }

    public synchronized static String getCurrentLogicalDate() {

        return ReferenceTableLoader.getLogicalDate();
    }

    public synchronized static String getCreationDate() {
        Calendar calender = Calendar.getInstance(new Locale("en", "US"));
        SimpleDateFormat dateFrmtt = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFrmtt.format(calender.getTime());
    }

    public synchronized static String getLogicalCreationDate() {
        String dateStr;
        try {
            dateStr = formatDate8(ReferenceTableLoader.getLogicalDate());

            Calendar calender = Calendar.getInstance(new Locale("en", "US"));
            SimpleDateFormat timeFormatter = new SimpleDateFormat("HHmmss");
            dateStr += timeFormatter.format(calender.getTime());

        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            dateStr = getCreationDate();
        }
        return dateStr;
    }

    public synchronized static String formatDate8(String dateStr) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy"); //please notice the capital M
            Date date = formatter.parse(dateStr);
            SimpleDateFormat dateFrmtt_14 = new SimpleDateFormat("yyyyMMdd");
            dateStr = dateFrmtt_14.format(date);
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            dateStr = null;
        }
        return dateStr;
    }

    public synchronized static String formatStringToUnicode(String str) {
        if (!iLinkUtils.isEmpty(str)) {
            Charset charset = Charset.forName("UTF-16");
            CharsetDecoder decoder = charset.newDecoder();
            CharsetEncoder encoder = charset.newEncoder();

            try {
                // Convert a string to ISO-LATIN-1 bytes in a ByteBuffer
                // The new ByteBuffer is ready to be read.
                ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(str));
                //System.out.println("bbuf ->" + bbuf);

                // Convert ISO-LATIN-1 bytes in a ByteBuffer to a character ByteBuffer and then to a string.
                // The new ByteBuffer is ready to be read.
                CharBuffer cbuf = decoder.decode(bbuf);
                //System.out.println("cbuf ->" + cbuf);
                str = cbuf.toString();
            }
            catch (CharacterCodingException e) {
                System.out.println("setNoteText : exception -> " + e.getMessage());
                e.printStackTrace(System.out);
            }
        }
        return str;
    }

}
