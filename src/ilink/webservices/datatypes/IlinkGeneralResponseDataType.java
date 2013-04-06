package ilink.webservices.datatypes;

public class IlinkGeneralResponseDataType  implements java.io.Serializable {
    private java.lang.Double customerId;
    private java.lang.Double contactId;
    private java.lang.Double opportunityId;
    private java.lang.Double activityId;
    private java.lang.Double noteId;
    private java.lang.String status;
    private java.lang.String errorCode;
    private java.lang.String errorDescription;

    public IlinkGeneralResponseDataType() {
    }

    public IlinkGeneralResponseDataType(
           java.lang.Double customerId,
           java.lang.Double contactId,
           java.lang.Double opportunityId,
           java.lang.Double activityId,
           java.lang.Double noteId,
           java.lang.String status,
           java.lang.String errorCode,
           java.lang.String errorDescription) {
           this.customerId = customerId;
           this.contactId = contactId;
           this.opportunityId = opportunityId;
           this.activityId = activityId;
           this.noteId = noteId;
           this.status = status;
           this.errorCode = errorCode;
           this.errorDescription = errorDescription;
    }


    /**
     * Gets the customerId value for this IlinkGeneralResponseDataType.
     * 
     * @return customerId
     */
    public java.lang.Double getCustomerId() {
        return customerId;
    }


    /**
     * Sets the customerId value for this IlinkGeneralResponseDataType.
     * 
     * @param customerId
     */
    public void setCustomerId(java.lang.Double customerId) {
        this.customerId = customerId;
    }


    /**
     * Gets the contactId value for this IlinkGeneralResponseDataType.
     * 
     * @return contactId
     */
    public java.lang.Double getContactId() {
        return contactId;
    }


    /**
     * Sets the contactId value for this IlinkGeneralResponseDataType.
     * 
     * @param contactId
     */
    public void setContactId(java.lang.Double contactId) {
        this.contactId = contactId;
    }


    /**
     * Gets the opportunityId value for this IlinkGeneralResponseDataType.
     * 
     * @return opportunityId
     */
    public java.lang.Double getOpportunityId() {
        return opportunityId;
    }


    /**
     * Sets the opportunityId value for this IlinkGeneralResponseDataType.
     * 
     * @param opportunityId
     */
    public void setOpportunityId(java.lang.Double opportunityId) {
        this.opportunityId = opportunityId;
    }


    /**
     * Gets the activityId value for this IlinkGeneralResponseDataType.
     * 
     * @return activityId
     */
    public java.lang.Double getActivityId() {
        return activityId;
    }


    /**
     * Sets the activityId value for this IlinkGeneralResponseDataType.
     * 
     * @param activityId
     */
    public void setActivityId(java.lang.Double activityId) {
        this.activityId = activityId;
    }


    /**
     * Gets the noteId value for this IlinkGeneralResponseDataType.
     * 
     * @return noteId
     */
    public java.lang.Double getNoteId() {
        return noteId;
    }


    /**
     * Sets the noteId value for this IlinkGeneralResponseDataType.
     * 
     * @param noteId
     */
    public void setNoteId(java.lang.Double noteId) {
        this.noteId = noteId;
    }


    /**
     * Gets the status value for this IlinkGeneralResponseDataType.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this IlinkGeneralResponseDataType.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }


    /**
     * Gets the errorCode value for this IlinkGeneralResponseDataType.
     * 
     * @return errorCode
     */
    public java.lang.String getErrorCode() {
        return errorCode;
    }


    /**
     * Sets the errorCode value for this IlinkGeneralResponseDataType.
     * 
     * @param errorCode
     */
    public void setErrorCode(java.lang.String errorCode) {
        this.errorCode = errorCode;
    }


    /**
     * Gets the errorDescription value for this IlinkGeneralResponseDataType.
     * 
     * @return errorDescription
     */
    public java.lang.String getErrorDescription() {
        return errorDescription;
    }


    /**
     * Sets the errorDescription value for this IlinkGeneralResponseDataType.
     * 
     * @param errorDescription
     */
    public void setErrorDescription(java.lang.String errorDescription) {
        this.errorDescription = errorDescription;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IlinkGeneralResponseDataType)) return false;
        IlinkGeneralResponseDataType other = (IlinkGeneralResponseDataType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.customerId==null && other.getCustomerId()==null) || 
             (this.customerId!=null &&
              this.customerId.equals(other.getCustomerId()))) &&
            ((this.contactId==null && other.getContactId()==null) || 
             (this.contactId!=null &&
              this.contactId.equals(other.getContactId()))) &&
            ((this.opportunityId==null && other.getOpportunityId()==null) || 
             (this.opportunityId!=null &&
              this.opportunityId.equals(other.getOpportunityId()))) &&
            ((this.activityId==null && other.getActivityId()==null) || 
             (this.activityId!=null &&
              this.activityId.equals(other.getActivityId()))) &&
            ((this.noteId==null && other.getNoteId()==null) || 
             (this.noteId!=null &&
              this.noteId.equals(other.getNoteId()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.errorCode==null && other.getErrorCode()==null) || 
             (this.errorCode!=null &&
              this.errorCode.equals(other.getErrorCode()))) &&
            ((this.errorDescription==null && other.getErrorDescription()==null) || 
             (this.errorDescription!=null &&
              this.errorDescription.equals(other.getErrorDescription())));
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
        if (getCustomerId() != null) {
            _hashCode += getCustomerId().hashCode();
        }
        if (getContactId() != null) {
            _hashCode += getContactId().hashCode();
        }
        if (getOpportunityId() != null) {
            _hashCode += getOpportunityId().hashCode();
        }
        if (getActivityId() != null) {
            _hashCode += getActivityId().hashCode();
        }
        if (getNoteId() != null) {
            _hashCode += getNoteId().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getErrorCode() != null) {
            _hashCode += getErrorCode().hashCode();
        }
        if (getErrorDescription() != null) {
            _hashCode += getErrorDescription().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IlinkGeneralResponseDataType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/ilinkGeneralResponse", "IlinkGeneralResponseDataType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CustomerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contactId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ContactId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("opportunityId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OpportunityId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activityId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ActivityId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("noteId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NoteId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ErrorCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ErrorDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
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
