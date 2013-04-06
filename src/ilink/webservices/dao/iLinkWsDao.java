package ilink.webservices.dao;

import ilink.domain.GeneralResponse;
import ilink.utils.iLinkUtils;
import ilink.webservices.services.ILinkWSInterface;
import ilink.webservices.datatypes.WebServiceUALException;
import ilink.webservices.datatypes.IlinkGeneralResponseType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

public abstract class iLinkWsDao implements iLinkWSDaoInterface {

    protected GeneralResponse response = null;
    private Object wsStub;
    private ILinkWSInterface wsInterface;
    private String methodName;
    private String inputClassName;
    private String outputClassName;

    protected final Log logger = LogFactory.getLog(getClass());

    public iLinkWsDao() {
    }

    protected abstract Object prepareInputParams(Object params) throws Exception;

    public ILinkWSInterface getWsInterface() {
        return wsInterface;
    }

    public void setWsInterface(ILinkWSInterface wsInterface) {
        this.wsInterface = wsInterface;
    }

    public String getOutputClassName() {
        return outputClassName;
    }

    public void setOutputClassName(String outputClassName) {
        this.outputClassName = outputClassName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getInputClassName() {
        return inputClassName;
    }

    public void setInputClassName(String inputClassName) {
        this.inputClassName = inputClassName;
    }

    public GeneralResponse callService(Object params) {

        try {
            if (wsStub == null)
                wsStub = wsInterface.getILinkWSPort();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if (jre.getLinkedCause() != null)
                jre.getLinkedCause().printStackTrace();
            String errorMsg = iLinkUtils.getApplicationExceptionMessage(jre);
            logger.error("################ iLinkWsDao.callService JAX-RPC ServiceException caught :" + errorMsg);
            return iLinkUtils.handleError("ERROR_ACRM_DOWN_MESSAGE", null);
        }
        Object outObj = null;
        response = new GeneralResponse();
        try {
            logger.debug("################ iLinkWsDao before call invoke");
            Method ejbMethod = wsStub.getClass().getMethod(methodName, Class.forName(inputClassName));

            outObj = ejbMethod.invoke(wsStub, prepareInputParams(params));
            logger.debug("################ iLinkWsDao after call invoke");
        } catch (InvocationTargetException appex) {
            String errorMsg = iLinkUtils.getApplicationExceptionMessage(appex);
            logger.error("################ iLinkWsDao.callService exception occured :" + errorMsg);
            response = iLinkUtils.handleError("ERROR_BACKEND_SERVICE_UNACCESSABLE", null);
            response.setTrackingError(errorMsg);
            return response;
        } catch (WebServiceUALException ex) {
            String errorMsg = iLinkUtils.getExceptionMessage(ex);
            logger.error("################ iLinkWsDao.callService WebServiceUALException Exception caught:" + errorMsg);
            response = iLinkUtils.handleError("ERROR_BACKEND_SERVICE_EXCEPTION", null);
            response.setTrackingError(errorMsg);
            return response;
        } catch (Exception ex) {
            String errorMsg = iLinkUtils.getExceptionMessage(ex);
            logger.error("################ iLinkWsDao.callService exception occured :" + iLinkUtils.getExceptionMessage(ex));
            response = iLinkUtils.handleError("ERROR_BACKEND_SERVICE_EXCEPTION", null);
            response.setTrackingError(errorMsg);
            return response;
        }
        return prepareResponse(outObj);
    }

    protected GeneralResponse prepareResponse(Object outObj) {

        IlinkGeneralResponseType outputDetails = (IlinkGeneralResponseType) outObj;
        if (outputDetails != null) {

            if (outputDetails.getDATA().getCustomerId() != null)
                response.setCustomerId(outputDetails.getDATA().getCustomerId().longValue());
            String status = outputDetails.getDATA().getStatus();
            if (status.toUpperCase().contains("ERROR")) {
                response.setStatus(iLinkUtils.TransStatus.ERROR);
                String errorCode = outputDetails.getDATA().getErrorCode();
                if (errorCode.equals("-2")) {
                    response.setErrorCode(iLinkUtils.ERROR);
                    if (outputDetails.getDATA().getErrorDescription() != null) {
                        String msgDesc = iLinkUtils.getMessage("ERROR_BACKEND_SERVICE_RESPONSE",
                                new Object[]{outputDetails.getDATA().getErrorDescription()});
                        response.setErrorMessage(msgDesc);
                    }
                }
            } else {
                response.setStatus(iLinkUtils.TransStatus.SUCCESS);
                response.setErrorCode(iLinkUtils.SUCCESS);
            }
        }
        logger.debug("################ iLinkWsDao prepareResponse: setStatus:" + response.getStatus());
        logger.debug("################ iLinkWsDao prepareResponse: setErrorCode:" + response.getErrorCode());
        logger.debug("################ iLinkWsDao prepareResponse: setErrorMessage:" + response.getErrorMessage());
        return response;
    }
}  //end of class definition