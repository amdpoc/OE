package ilink.utils;

import ilink.domain.CreditApplicationEnvelope;
import ilink.domain.EsignEnvelope;
import ilink.domain.GeneralResponse;


public interface DocsignManager {
    public void init(javax.servlet.http.HttpSession session);
    public GeneralResponse executeDocuSignService(EsignEnvelope envelope);
    public GeneralResponse executeDocuSignTemplateService(CreditApplicationEnvelope envelope);
}
