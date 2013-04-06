package ilink.webservices.datatypes;

public class ILinkMobileCreCstNoteParamsDataType  implements java.io.Serializable {
    private long customerId;
    private java.lang.String creationDate;
    private java.lang.Integer repId;
    private java.lang.String noteText;
    private java.lang.String noteType;
    private java.lang.String officeType;

    public ILinkMobileCreCstNoteParamsDataType() {
    }

    public ILinkMobileCreCstNoteParamsDataType(
           long customerId,
           java.lang.String creationDate,
           java.lang.Integer repId,
           java.lang.String noteText,
           java.lang.String noteType,
           java.lang.String officeType) {
           this.customerId = customerId;
           this.creationDate = creationDate;
           this.repId = repId;
           this.noteText = noteText;
           this.noteType = noteType;
           this.officeType = officeType;
    }


    /**
     * Gets the customerId value for this ILinkMobileCreCstNoteParamsDataType.
     * 
     * @return customerId
     */
    public long getCustomerId() {
        return customerId;
    }


    /**
     * Sets the customerId value for this ILinkMobileCreCstNoteParamsDataType.
     * 
     * @param customerId
     */
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }


    /**
     * Gets the creationDate value for this ILinkMobileCreCstNoteParamsDataType.
     * 
     * @return creationDate
     */
    public java.lang.String getCreationDate() {
        return creationDate;
    }


    /**
     * Sets the creationDate value for this ILinkMobileCreCstNoteParamsDataType.
     * 
     * @param creationDate
     */
    public void setCreationDate(java.lang.String creationDate) {
        this.creationDate = creationDate;
    }


    /**
     * Gets the repId value for this ILinkMobileCreCstNoteParamsDataType.
     * 
     * @return repId
     */
    public java.lang.Integer getRepId() {
        return repId;
    }


    /**
     * Sets the repId value for this ILinkMobileCreCstNoteParamsDataType.
     * 
     * @param repId
     */
    public void setRepId(java.lang.Integer repId) {
        this.repId = repId;
    }


    /**
     * Gets the noteText value for this ILinkMobileCreCstNoteParamsDataType.
     * 
     * @return noteText
     */
    public java.lang.String getNoteText() {
        return noteText;
    }


    /**
     * Sets the noteText value for this ILinkMobileCreCstNoteParamsDataType.
     * 
     * @param noteText
     */
    public void setNoteText(java.lang.String noteText) {
        this.noteText = noteText;
    }


    /**
     * Gets the noteType value for this ILinkMobileCreCstNoteParamsDataType.
     * 
     * @return noteType
     */
    public java.lang.String getNoteType() {
        return noteType;
    }


    /**
     * Sets the noteType value for this ILinkMobileCreCstNoteParamsDataType.
     * 
     * @param noteType
     */
    public void setNoteType(java.lang.String noteType) {
        this.noteType = noteType;
    }


    /**
     * Gets the officeType value for this ILinkMobileCreCstNoteParamsDataType.
     * 
     * @return officeType
     */
    public java.lang.String getOfficeType() {
        return officeType;
    }


    /**
     * Sets the officeType value for this ILinkMobileCreCstNoteParamsDataType.
     * 
     * @param officeType
     */
    public void setOfficeType(java.lang.String officeType) {
        this.officeType = officeType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ILinkMobileCreCstNoteParamsDataType)) return false;
        ILinkMobileCreCstNoteParamsDataType other = (ILinkMobileCreCstNoteParamsDataType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.customerId == other.getCustomerId() &&
            ((this.creationDate==null && other.getCreationDate()==null) || 
             (this.creationDate!=null &&
              this.creationDate.equals(other.getCreationDate()))) &&
            ((this.repId==null && other.getRepId()==null) || 
             (this.repId!=null &&
              this.repId.equals(other.getRepId()))) &&
            ((this.noteText==null && other.getNoteText()==null) || 
             (this.noteText!=null &&
              this.noteText.equals(other.getNoteText()))) &&
            ((this.noteType==null && other.getNoteType()==null) || 
             (this.noteType!=null &&
              this.noteType.equals(other.getNoteType()))) &&
            ((this.officeType==null && other.getOfficeType()==null) || 
             (this.officeType!=null &&
              this.officeType.equals(other.getOfficeType())));
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
        _hashCode += new Long(getCustomerId()).hashCode();
        if (getCreationDate() != null) {
            _hashCode += getCreationDate().hashCode();
        }
        if (getRepId() != null) {
            _hashCode += getRepId().hashCode();
        }
        if (getNoteText() != null) {
            _hashCode += getNoteText().hashCode();
        }
        if (getNoteType() != null) {
            _hashCode += getNoteType().hashCode();
        }
        if (getOfficeType() != null) {
            _hashCode += getOfficeType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ILinkMobileCreCstNoteParamsDataType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/ilinkCreCstNoteParams", "ILinkMobileCreCstNoteParamsDataType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CustomerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creationDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CreationDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("repId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "RepId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("noteText");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NoteText"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("noteType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NoteType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("officeType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OfficeType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
