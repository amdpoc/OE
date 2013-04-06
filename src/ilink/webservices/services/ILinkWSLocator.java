package ilink.webservices.services;


public class ILinkWSLocator extends org.apache.axis.client.Service implements ILinkWSInterface {

    public ILinkWSLocator() {
    }

    public ILinkWSLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ILinkWSLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ILINK_MOBILE_WSPort
    private java.lang.String ilinkWSPortAddress;//= "http://hpux39:12000/ILINK_MOBILE_WS/services/ILINK_MOBILE";
    private java.lang.String wsPortName;// = "ILINK_MOBILE_WSPort";
    private java.lang.String wsServiceName;// = "ILINK_MOBILE_WS";
    private java.lang.String wsNamespaceUri;// = "http://amdocs/iam/ILINK_MOBILE_WS";
    private ILinkWSServiceBeanInterface wsStub;

    public ILinkWSServiceBeanInterface getWsStub() {
        return wsStub;
    }

    public void setWsStub(ILinkWSServiceBeanInterface wsStub) {
        this.wsStub = wsStub;
    }

    public String getWsPortName() {
        return wsPortName;
    }

    public void setWsPortName(String wsPortName) {
        this.wsPortName = wsPortName;
    }

    public String getWsServiceName() {
        return wsServiceName;
    }

    public void setWsServiceName(String wsServiceName) {
        this.wsServiceName = wsServiceName;
    }

    public String getWsNamespaceUri() {
        return wsNamespaceUri;
    }

    public void setWsNamespaceUri(String wsNamespaceUri) {
        this.wsNamespaceUri = wsNamespaceUri;
    }

    public void setIlinkWSPortAddress(java.lang.String address) {
        ilinkWSPortAddress = address;
    }
    public java.lang.String getIlinkWSPortAddress() {
        return ilinkWSPortAddress;
    }

    public ILinkWSServiceBeanInterface getILinkWSPort() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ilinkWSPortAddress);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getILinkWSPort(endpoint);
    }

    public ILinkWSServiceBeanInterface getILinkWSPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            wsStub.setStubEndPointUrl(portAddress);
            wsStub.setStubService(this);
            ((org.apache.axis.client.Stub) wsStub).setPortName(getWsPortName());
            return wsStub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if (getWsPortName().equals(inputPortName)) {
            return getILinkWSPort();
        } else {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName(getWsNamespaceUri() , getWsServiceName());
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName(getWsNamespaceUri() , getWsPortName()));
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

        if (getWsPortName().equals(portName)) {
            setIlinkWSPortAddress(address);
        } else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}

