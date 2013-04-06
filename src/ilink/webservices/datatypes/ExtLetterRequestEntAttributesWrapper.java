package ilink.webservices.datatypes;

public class ExtLetterRequestEntAttributesWrapper  implements java.io.Serializable {
    private ActionValue action;
    private java.lang.String connectionName;
    private java.lang.String templateCode;
    private GDate effectiveDate;
    private java.lang.String templateSubType;
    private java.lang.String templateType;
    private java.lang.String operatorId;
    private java.lang.String userId;
    private java.lang.String customerId;
    private LetterDistributionsData distributionData;
    private Parameters parameters;

    public ExtLetterRequestEntAttributesWrapper() {
    }

    public ExtLetterRequestEntAttributesWrapper(
           ActionValue action,
           java.lang.String connectionName,
           java.lang.String templateCode,
           GDate effectiveDate,
           java.lang.String templateSubType,
           java.lang.String templateType,
           java.lang.String operatorId,
           java.lang.String userId,
           java.lang.String customerId,
           LetterDistributionsData distributionData,
           Parameters parameters) {
           this.action = action;
           this.connectionName = connectionName;
           this.templateCode = templateCode;
           this.effectiveDate = effectiveDate;
           this.templateSubType = templateSubType;
           this.templateType = templateType;
           this.operatorId = operatorId;
           this.userId = userId;
           this.customerId = customerId;
           this.distributionData = distributionData;
           this.parameters = parameters;
    }


    /**
     * Gets the action value for this ExtLetterRequestEntAttributesWrapper.
     * 
     * @return action
     */
    public ActionValue getAction() {
        return action;
    }


    /**
     * Sets the action value for this ExtLetterRequestEntAttributesWrapper.
     * 
     * @param action
     */
    public void setAction(ActionValue action) {
        this.action = action;
    }


    /**
     * Gets the connectionName value for this ExtLetterRequestEntAttributesWrapper.
     * 
     * @return connectionName
     */
    public java.lang.String getConnectionName() {
        return connectionName;
    }


    /**
     * Sets the connectionName value for this ExtLetterRequestEntAttributesWrapper.
     * 
     * @param connectionName
     */
    public void setConnectionName(java.lang.String connectionName) {
        this.connectionName = connectionName;
    }


    /**
     * Gets the templateCode value for this ExtLetterRequestEntAttributesWrapper.
     * 
     * @return templateCode
     */
    public java.lang.String getTemplateCode() {
        return templateCode;
    }


    /**
     * Sets the templateCode value for this ExtLetterRequestEntAttributesWrapper.
     * 
     * @param templateCode
     */
    public void setTemplateCode(java.lang.String templateCode) {
        this.templateCode = templateCode;
    }


    /**
     * Gets the effectiveDate value for this ExtLetterRequestEntAttributesWrapper.
     * 
     * @return effectiveDate
     */
    public GDate getEffectiveDate() {
        return effectiveDate;
    }


    /**
     * Sets the effectiveDate value for this ExtLetterRequestEntAttributesWrapper.
     * 
     * @param effectiveDate
     */
    public void setEffectiveDate(GDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }


    /**
     * Gets the templateSubType value for this ExtLetterRequestEntAttributesWrapper.
     * 
     * @return templateSubType
     */
    public java.lang.String getTemplateSubType() {
        return templateSubType;
    }


    /**
     * Sets the templateSubType value for this ExtLetterRequestEntAttributesWrapper.
     * 
     * @param templateSubType
     */
    public void setTemplateSubType(java.lang.String templateSubType) {
        this.templateSubType = templateSubType;
    }


    /**
     * Gets the templateType value for this ExtLetterRequestEntAttributesWrapper.
     * 
     * @return templateType
     */
    public java.lang.String getTemplateType() {
        return templateType;
    }


    /**
     * Sets the templateType value for this ExtLetterRequestEntAttributesWrapper.
     * 
     * @param templateType
     */
    public void setTemplateType(java.lang.String templateType) {
        this.templateType = templateType;
    }


    /**
     * Gets the operatorId value for this ExtLetterRequestEntAttributesWrapper.
     * 
     * @return operatorId
     */
    public java.lang.String getOperatorId() {
        return operatorId;
    }


    /**
     * Sets the operatorId value for this ExtLetterRequestEntAttributesWrapper.
     * 
     * @param operatorId
     */
    public void setOperatorId(java.lang.String operatorId) {
        this.operatorId = operatorId;
    }


    /**
     * Gets the userId value for this ExtLetterRequestEntAttributesWrapper.
     * 
     * @return userId
     */
    public java.lang.String getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this ExtLetterRequestEntAttributesWrapper.
     * 
     * @param userId
     */
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }


    /**
     * Gets the customerId value for this ExtLetterRequestEntAttributesWrapper.
     * 
     * @return customerId
     */
    public java.lang.String getCustomerId() {
        return customerId;
    }


    /**
     * Sets the customerId value for this ExtLetterRequestEntAttributesWrapper.
     * 
     * @param customerId
     */
    public void setCustomerId(java.lang.String customerId) {
        this.customerId = customerId;
    }


    /**
     * Gets the distributionData value for this ExtLetterRequestEntAttributesWrapper.
     * 
     * @return distributionData
     */
    public LetterDistributionsData getDistributionData() {
        return distributionData;
    }


    /**
     * Sets the distributionData value for this ExtLetterRequestEntAttributesWrapper.
     * 
     * @param distributionData
     */
    public void setDistributionData(LetterDistributionsData distributionData) {
        this.distributionData = distributionData;
    }


    /**
     * Gets the parameters value for this ExtLetterRequestEntAttributesWrapper.
     * 
     * @return parameters
     */
    public Parameters getParameters() {
        return parameters;
    }


    /**
     * Sets the parameters value for this ExtLetterRequestEntAttributesWrapper.
     * 
     * @param parameters
     */
    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ExtLetterRequestEntAttributesWrapper)) return false;
        ExtLetterRequestEntAttributesWrapper other = (ExtLetterRequestEntAttributesWrapper) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.action==null && other.getAction()==null) || 
             (this.action!=null &&
              this.action.equals(other.getAction()))) &&
            ((this.connectionName==null && other.getConnectionName()==null) || 
             (this.connectionName!=null &&
              this.connectionName.equals(other.getConnectionName()))) &&
            ((this.templateCode==null && other.getTemplateCode()==null) || 
             (this.templateCode!=null &&
              this.templateCode.equals(other.getTemplateCode()))) &&
            ((this.effectiveDate==null && other.getEffectiveDate()==null) || 
             (this.effectiveDate!=null &&
              this.effectiveDate.equals(other.getEffectiveDate()))) &&
            ((this.templateSubType==null && other.getTemplateSubType()==null) || 
             (this.templateSubType!=null &&
              this.templateSubType.equals(other.getTemplateSubType()))) &&
            ((this.templateType==null && other.getTemplateType()==null) || 
             (this.templateType!=null &&
              this.templateType.equals(other.getTemplateType()))) &&
            ((this.operatorId==null && other.getOperatorId()==null) || 
             (this.operatorId!=null &&
              this.operatorId.equals(other.getOperatorId()))) &&
            ((this.userId==null && other.getUserId()==null) || 
             (this.userId!=null &&
              this.userId.equals(other.getUserId()))) &&
            ((this.customerId==null && other.getCustomerId()==null) || 
             (this.customerId!=null &&
              this.customerId.equals(other.getCustomerId()))) &&
            ((this.distributionData==null && other.getDistributionData()==null) || 
             (this.distributionData!=null &&
              this.distributionData.equals(other.getDistributionData()))) &&
            ((this.parameters==null && other.getParameters()==null) || 
             (this.parameters!=null &&
              this.parameters.equals(other.getParameters())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAction() != null) {
            _hashCode += getAction().hashCode();
        }
        if (getConnectionName() != null) {
            _hashCode += getConnectionName().hashCode();
        }
        if (getTemplateCode() != null) {
            _hashCode += getTemplateCode().hashCode();
        }
        if (getEffectiveDate() != null) {
            _hashCode += getEffectiveDate().hashCode();
        }
        if (getTemplateSubType() != null) {
            _hashCode += getTemplateSubType().hashCode();
        }
        if (getTemplateType() != null) {
            _hashCode += getTemplateType().hashCode();
        }
        if (getOperatorId() != null) {
            _hashCode += getOperatorId().hashCode();
        }
        if (getUserId() != null) {
            _hashCode += getUserId().hashCode();
        }
        if (getCustomerId() != null) {
            _hashCode += getCustomerId().hashCode();
        }
        if (getDistributionData() != null) {
            _hashCode += getDistributionData().hashCode();
        }
        if (getParameters() != null) {
            _hashCode += getParameters().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ExtLetterRequestEntAttributesWrapper.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/AddDocumentRequestInput", "ExtLetterRequestEntAttributesWrapper"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("action");
        elemField.setXmlName(new javax.xml.namespace.QName("", "action"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/AddDocumentRequestInput", "actionValue"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("connectionName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "connectionName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("templateCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "templateCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/AddDocumentRequestInput", ">ExtLetterRequestEntAttributesWrapper>templateCode"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("effectiveDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "effectiveDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/common/ilinkCommon", "GDate"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("templateSubType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "templateSubType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("templateType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "templateType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operatorId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "operatorId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/common/ilinkCommon", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/common/ilinkCommon", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "customerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/common/ilinkCommon", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("distributionData");
        elemField.setXmlName(new javax.xml.namespace.QName("", "distributionData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/AddDocumentRequestInput", "LetterDistributionsData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parameters");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parameters"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/AddDocumentRequestInput", "parameters"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
