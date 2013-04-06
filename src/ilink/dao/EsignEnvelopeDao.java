package ilink.dao;

import ilink.domain.EsignEnvelope;
import ilink.domain.EsignBillingAccount;
import ilink.domain.EsignProduct;


public interface EsignEnvelopeDao {

    public EsignEnvelope createEsignEnvelope(EsignEnvelope envelope) throws Exception;

    public void updateEsignEnvelopeStatus(double envelopeId, EsignEnvelope.EsignEnvelopeStatus status) throws Exception;

    public void updateEsignEnvelopeContactInfo(double envelopeId, String contactName, String contactEmail) throws Exception;
    
    public void updateEnvelopeDocusignInfo(double envelopeId, String docusignEnvelopeId, EsignEnvelope.DocusignEnvelopeStatus docusignStatus) throws Exception;

    public void updateEsignEnvelopeWithError(double envelopeId, String error) throws Exception;

    public void updateEsignEnvelopeStatusWithError(double envelopeId, EsignEnvelope.EsignEnvelopeStatus status, String error) throws Exception;

    public void updateEsignEnvelopeDocusignStatusWithError(double envelopeId, EsignEnvelope.DocusignEnvelopeStatus status, String error)  throws Exception;

    public void updateEnvelopeStatusAndPdfPath(double envelopeId, EsignEnvelope.EsignEnvelopeStatus status, String pdfPath) throws Exception;

    public EsignEnvelope getEsignEnvelope(String envelopeId);

    public double insertEsingBillingAccount(EsignBillingAccount baccount);

    public void insertEsingBAProduct(EsignProduct product);
}