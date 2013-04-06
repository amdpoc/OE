package ilink.webservices.services;


public interface ILinkWSInterface extends javax.xml.rpc.Service {

    public java.lang.String getIlinkWSPortAddress();
    public ILinkWSServiceBeanInterface getILinkWSPort() throws javax.xml.rpc.ServiceException;
    public ILinkWSServiceBeanInterface getILinkWSPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}

