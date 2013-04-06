package ilink.webservices.services;

import ilink.webservices.datatypes.*;


public class ILinkWSPortSoapBindingStub extends org.apache.axis.client.Stub implements ILinkWSServiceBeanInterface {

    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc[] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[2];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1() {
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("creCstNote");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/ilinkCreCstNoteParams", "ROOT"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/ilinkCreCstNoteParams", "ILinkMobileCreCstNoteParamsType"), ILinkMobileCreCstNoteParamsType.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/ilinkGeneralResponse", "IlinkGeneralResponseType"));
        oper.setReturnClass(IlinkGeneralResponseType.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/ilinkGeneralResponse", "ROOT"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("http://amdocs/iam/ILINK_MOBILE_WS", "WebServiceUALException"),
                "WebServiceUALException",
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"),
                false
        ));
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("addDocumentRequest");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/AddDocumentRequestInput", "ROOT"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/AddDocumentRequestInput", "AddDocumentRequestInput"), AddDocumentRequestInput.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/AddDocumentRequestOutput", "AddDocumentRequestOutput"));
        oper.setReturnClass(AddDocumentRequestOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/AddDocumentRequestOutput", "ROOT"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("http://amdocs/iam/ILINK_MOBILE_WS", "WebServiceUALException"),
                "ilink_WS.WebServiceUALException",
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"),
                false
        ));
        _operations[1] = oper;
    }

    public ILinkWSPortSoapBindingStub() throws org.apache.axis.AxisFault {
    }

    public ILinkWSPortSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        setStubService(service);
        super.cachedEndpoint = endpointURL;
    }

    public void setStubEndPointUrl(java.net.URL endpointURL) throws org.apache.axis.AxisFault {
        super.cachedEndpoint = endpointURL;
    }

    public void setStubService(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service) super.service).setTypeMappingVersion("1.1");
        java.lang.Class cls;
        javax.xml.namespace.QName qName;
        javax.xml.namespace.QName qName2;
        java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
        java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
        java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
        java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
        java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
        java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
        java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
        java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
        java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
        java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
        qName = new javax.xml.namespace.QName("http://amdocs/ilinkmobile/common/ilinkCommon", "EventType");
        cachedSerQNames.add(qName);
        cls = EventType.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://amdocs/ilinkmobile/common/ilinkCommon", "GDate");
        cachedSerQNames.add(qName);
        cls = GDate.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);


        qName = new javax.xml.namespace.QName("http://amdocs/ilinkmobile/common/ilinkCommon", "long");
        cachedSerQNames.add(qName);
        cls = java.lang.String.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(simplesf);
        cachedDeserFactories.add(simpledf);

        qName = new javax.xml.namespace.QName("http://amdocs/ilinkmobile/common/ilinkCommon", "YesNo");
        cachedSerQNames.add(qName);
        cls = YesNo.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(enumsf);
        cachedDeserFactories.add(enumdf);

        qName = new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/AddDocumentRequestInput", ">ExtLetterRequestEntAttributesWrapper>templateCode");
        cachedSerQNames.add(qName);
        cls = java.lang.String.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(simplesf);
        cachedDeserFactories.add(simpledf);

        qName = new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/AddDocumentRequestInput", "actionValue");
        cachedSerQNames.add(qName);
        cls = ActionValue.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(enumsf);
        cachedDeserFactories.add(enumdf);

        qName = new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/AddDocumentRequestInput", "AddDocumentRequestInput");
        cachedSerQNames.add(qName);
        cls = AddDocumentRequestInput.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/AddDocumentRequestInput", "ExtLetterRequestEntAttributesWrapper");
        cachedSerQNames.add(qName);
        cls = ExtLetterRequestEntAttributesWrapper.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/AddDocumentRequestInput", "LetterDistributionsData");
        cachedSerQNames.add(qName);
        cls = LetterDistributionsData.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/AddDocumentRequestInput", "parameterData");
        cachedSerQNames.add(qName);
        cls = ParameterData.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/AddDocumentRequestInput", "parameters");
        cachedSerQNames.add(qName);
        cls = Parameters.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/AddDocumentRequestOutput", "AddDocumentRequestOutput");
        cachedSerQNames.add(qName);
        cls = AddDocumentRequestOutput.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/AddDocumentRequestOutput", "DocumentOutput");
        cachedSerQNames.add(qName);
        cls = DocumentOutput.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/ilinkCreCstNoteParams", "ILinkMobileCreCstNoteParamsDataType");
        cachedSerQNames.add(qName);
        cls = ILinkMobileCreCstNoteParamsDataType.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/ilinkCreCstNoteParams", "ILinkMobileCreCstNoteParamsType");
        cachedSerQNames.add(qName);
        cls = ILinkMobileCreCstNoteParamsType.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);


        qName = new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/ilinkGeneralResponse", "IlinkGeneralResponseDataType");
        cachedSerQNames.add(qName);
        cls = IlinkGeneralResponseDataType.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/ilinkGeneralResponse", "IlinkGeneralResponseType");
        cachedSerQNames.add(qName);
        cls = IlinkGeneralResponseType.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                    cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                    cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        } else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                    cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                    cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public IlinkGeneralResponseType creCstNote(ILinkMobileCreCstNoteParamsType ROOT) throws java.rmi.RemoteException, WebServiceUALException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://amdocs/iam/ILINK_MOBILE_WS", "creCstNote"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[]{ROOT});

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (IlinkGeneralResponseType) _resp;
                } catch (java.lang.Exception _exception) {
                    return (IlinkGeneralResponseType) org.apache.axis.utils.JavaUtils.convert(_resp, IlinkGeneralResponseType.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            if (axisFaultException.detail != null) {
                if (axisFaultException.detail instanceof java.rmi.RemoteException) {
                    throw (java.rmi.RemoteException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof WebServiceUALException) {
                    throw (WebServiceUALException) axisFaultException.detail;
                }
            }
            throw axisFaultException;
        }
    }

    public AddDocumentRequestOutput addDocumentRequest(AddDocumentRequestInput ROOT) throws java.rmi.RemoteException, WebServiceUALException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://amdocs/iam/ILINK_MOBILE_WS", "AddDocumentRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[]{ROOT});

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (AddDocumentRequestOutput) _resp;
                } catch (java.lang.Exception _exception) {
                    return (AddDocumentRequestOutput) org.apache.axis.utils.JavaUtils.convert(_resp, AddDocumentRequestOutput.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            if (axisFaultException.detail != null) {
                if (axisFaultException.detail instanceof java.rmi.RemoteException) {
                    throw (java.rmi.RemoteException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof WebServiceUALException) {
                    throw (WebServiceUALException) axisFaultException.detail;
                }
            }
            throw axisFaultException;
        }
    }

}
