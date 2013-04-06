package ilink.service;

import ilink.domain.GeneralResponse;
import ilink.domain.EsignEnvelope;
import ilink.utils.iLinkUtils;
import ilink.docuSign.DocuSignService;
import ilink.dao.EsignEnvelopeDao;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.ServletRequestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class EsignEnvelopeManagerImpl implements EsignEnvelopeManager {
    private EsignEnvelopeDao esignEnvelopeDao;
    protected final Log logger = LogFactory.getLog(getClass());

    public EsignEnvelopeDao getEsignEnvelopeDao() {
        return esignEnvelopeDao;
    }

    public void setEsignEnvelopeDao(EsignEnvelopeDao esignEnvelopeDao) {
        this.esignEnvelopeDao = esignEnvelopeDao;
    }

    public GeneralResponse getLastDocusignInfo(HttpServletRequest request) {

        GeneralResponse response = null;
        try {
            Double envelopeId = ServletRequestUtils.getDoubleParameter(request, "envelopeId");
            String docusignState = ServletRequestUtils.getStringParameter(request, "docusignState");
            if (envelopeId != null && envelopeId > 0) {
                EsignEnvelope envelope = iLinkUtils.getLastEsignEnvelopeFromSession(request.getSession());
                if (envelope != null && envelope.getEsignEnvelopeId() == envelopeId) {
                    response = iLinkUtils.prepareResponse(Long.valueOf(envelope.getCustomerId()));
                    response.setBusinessName(envelope.getBusinessName());
                    if (!iLinkUtils.isEmpty(docusignState)) {

                        if (!docusignState.equals(DocuSignService.DocusignServiceEnvelopeStatus.COMPLETE.toString())) {
                            iLinkUtils.setErrorResponse(response, iLinkUtils.getMessage(docusignState, new Object[]{}));
                            esignEnvelopeDao.updateEsignEnvelopeDocusignStatusWithError(envelopeId, EsignEnvelope.DocusignEnvelopeStatus.FAILED, response.getErrorMessage());
                        } else {
                            esignEnvelopeDao.updateEsignEnvelopeDocusignStatusWithError(envelopeId, EsignEnvelope.DocusignEnvelopeStatus.COMPLETED, " ");
                        }
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("########## EsignEnvelopeManagerImpl:getLastDocusignInfo - ERROR: " + ex.getMessage());
        }

        return response;
    }
}
