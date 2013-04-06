package ilink.webservices.datatypes;

public class ILinkMobileCreCstNoteParamsType implements java.io.Serializable {
    private EventType event;
    private ILinkMobileCreCstNoteParamsDataType DATA;

    public ILinkMobileCreCstNoteParamsType() {
    }

    public ILinkMobileCreCstNoteParamsType(
            EventType event,
            ILinkMobileCreCstNoteParamsDataType DATA) {
        this.event = event;
        this.DATA = DATA;
    }


    /**
     * Gets the event value for this ILinkMobileCreCstNoteParamsType.
     *
     * @return event
     */
    public EventType getEvent() {
        return event;
    }


    /**
     * Sets the event value for this ILinkMobileCreCstNoteParamsType.
     *
     * @param event
     */
    public void setEvent(EventType event) {
        this.event = event;
    }


    /**
     * Gets the DATA value for this ILinkMobileCreCstNoteParamsType.
     *
     * @return DATA
     */
    public ILinkMobileCreCstNoteParamsDataType getDATA() {
        return DATA;
    }


    /**
     * Sets the DATA value for this ILinkMobileCreCstNoteParamsType.
     *
     * @param DATA
     */
    public void setDATA(ILinkMobileCreCstNoteParamsDataType DATA) {
        this.DATA = DATA;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ILinkMobileCreCstNoteParamsType)) return false;
        ILinkMobileCreCstNoteParamsType other = (ILinkMobileCreCstNoteParamsType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.event == null && other.getEvent() == null) ||
                        (this.event != null &&
                                this.event.equals(other.getEvent()))) &&
                ((this.DATA == null && other.getDATA() == null) ||
                        (this.DATA != null &&
                                this.DATA.equals(other.getDATA())));
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
        if (getEvent() != null) {
            _hashCode += getEvent().hashCode();
        }
        if (getDATA() != null) {
            _hashCode += getDATA().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(ILinkMobileCreCstNoteParamsType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/ilinkCreCstNoteParams", "ILinkMobileCreCstNoteParamsType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("event");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Event"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/common/ilinkCommon", "EventType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DATA");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DATA"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/ilinkCreCstNoteParams", "ILinkMobileCreCstNoteParamsDataType"));
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
                new org.apache.axis.encoding.ser.BeanSerializer(
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
                new org.apache.axis.encoding.ser.BeanDeserializer(
                        _javaType, _xmlType, typeDesc);
    }


}