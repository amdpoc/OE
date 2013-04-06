package ilink.webservices.datatypes;

public class GDate  implements java.io.Serializable {
    private java.lang.String dateTimeString;

    public GDate() {
    }

    public GDate(
           java.lang.String dateTimeString) {
           this.dateTimeString = dateTimeString;
    }


    /**
     * Gets the dateTimeString value for this GDate.
     * 
     * @return dateTimeString
     */
    public java.lang.String getDateTimeString() {
        return dateTimeString;
    }


    /**
     * Sets the dateTimeString value for this GDate.
     * 
     * @param dateTimeString
     */
    public void setDateTimeString(java.lang.String dateTimeString) {
        this.dateTimeString = dateTimeString;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GDate)) return false;
        GDate other = (GDate) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dateTimeString==null && other.getDateTimeString()==null) || 
             (this.dateTimeString!=null &&
              this.dateTimeString.equals(other.getDateTimeString())));
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
        if (getDateTimeString() != null) {
            _hashCode += getDateTimeString().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GDate.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/common/ilinkCommon", "GDate"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateTimeString");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dateTimeString"));
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
