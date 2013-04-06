package ilink.service;

import ilink.domain.GeneralResponse;


public interface SignatureManager
{
    public GeneralResponse sendDocumentToSignDWR(String requestId, String contactName, String contactEmail, boolean inPersonInd);
    public GeneralResponse sendTemplateRequestToSignDWR(String requestId, String contactName, String contactEmail, boolean inPersonInd, boolean isJoint);
}
