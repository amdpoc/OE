package ilink.webservices.datatypes;

public class ActionValue implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ActionValue(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _SAVE = "SAVE";
    public static final java.lang.String _SUBMIT = "SUBMIT";
    public static final java.lang.String _CANCEL = "CANCEL";
    public static final java.lang.String _PREVIEW = "PREVIEW";
    public static final java.lang.String _RESEND = "RESEND";
    public static final java.lang.String _ROBIN = "ROBIN";
    public static final java.lang.String _RETRIVE = "RETRIVE";
    public static final ActionValue SAVE = new ActionValue(_SAVE);
    public static final ActionValue SUBMIT = new ActionValue(_SUBMIT);
    public static final ActionValue CANCEL = new ActionValue(_CANCEL);
    public static final ActionValue PREVIEW = new ActionValue(_PREVIEW);
    public static final ActionValue RESEND = new ActionValue(_RESEND);
    public static final ActionValue ROBIN = new ActionValue(_ROBIN);
    public static final ActionValue RETRIVE = new ActionValue(_RETRIVE);
    public java.lang.String getValue() { return _value_;}
    public static ActionValue fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ActionValue enumeration = (ActionValue)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ActionValue fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ActionValue.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/AddDocumentRequestInput", "actionValue"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
