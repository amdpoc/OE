package ilink.service;

import ilink.domain.GeneralResponse;
import ilink.domain.EsignEnvelope;


public interface PdfGeneratorManager {

     public GeneralResponse generateContractDWR(EsignEnvelope envelope);
     public GeneralResponse getContractPdfPathDWR(long requestId, double envelopeId);
}
