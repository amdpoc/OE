package ilink.service;

import ilink.domain.GeneralResponse;

import javax.servlet.http.HttpServletRequest;


public interface EsignEnvelopeManager {
     public GeneralResponse getLastDocusignInfo( HttpServletRequest request );
}
