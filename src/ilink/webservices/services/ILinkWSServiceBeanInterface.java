package ilink.webservices.services;

import ilink.webservices.datatypes.*;


public interface ILinkWSServiceBeanInterface extends java.rmi.Remote {

    public void setStubEndPointUrl(java.net.URL endpointURL) throws org.apache.axis.AxisFault;

    public void setStubService(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault;

    public IlinkGeneralResponseType creCstNote(ILinkMobileCreCstNoteParamsType ROOT) throws java.rmi.RemoteException, WebServiceUALException;
    
    public AddDocumentRequestOutput addDocumentRequest(AddDocumentRequestInput ROOT) throws java.rmi.RemoteException, WebServiceUALException;

}
