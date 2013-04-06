/**
 * LetterDistributionsData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package ilink.webservices.datatypes;

public class LetterDistributionsData  implements java.io.Serializable {
    private YesNo emailInd;
    private YesNo printerInd;
    private YesNo faxInd;
    private java.lang.String emailFrom;
    private java.lang.String emailSubject;
    private java.lang.String emailTo;
    private java.lang.String messageBody;
    private java.lang.String faxNo;
    private java.lang.String printOptions;
    private java.lang.String printerName;
    private java.lang.String printerQueueName;

    public LetterDistributionsData() {
    }

    public LetterDistributionsData(
           YesNo emailInd,
           YesNo printerInd,
           YesNo faxInd,
           java.lang.String emailFrom,
           java.lang.String emailSubject,
           java.lang.String emailTo,
           java.lang.String messageBody,
           java.lang.String faxNo,
           java.lang.String printOptions,
           java.lang.String printerName,
           java.lang.String printerQueueName) {
           this.emailInd = emailInd;
           this.printerInd = printerInd;
           this.faxInd = faxInd;
           this.emailFrom = emailFrom;
           this.emailSubject = emailSubject;
           this.emailTo = emailTo;
           this.messageBody = messageBody;
           this.faxNo = faxNo;
           this.printOptions = printOptions;
           this.printerName = printerName;
           this.printerQueueName = printerQueueName;
    }


    /**
     * Gets the emailInd value for this LetterDistributionsData.
     * 
     * @return emailInd
     */
    public YesNo getEmailInd() {
        return emailInd;
    }


    /**
     * Sets the emailInd value for this LetterDistributionsData.
     * 
     * @param emailInd
     */
    public void setEmailInd(YesNo emailInd) {
        this.emailInd = emailInd;
    }


    /**
     * Gets the printerInd value for this LetterDistributionsData.
     * 
     * @return printerInd
     */
    public YesNo getPrinterInd() {
        return printerInd;
    }


    /**
     * Sets the printerInd value for this LetterDistributionsData.
     * 
     * @param printerInd
     */
    public void setPrinterInd(YesNo printerInd) {
        this.printerInd = printerInd;
    }


    /**
     * Gets the faxInd value for this LetterDistributionsData.
     * 
     * @return faxInd
     */
    public YesNo getFaxInd() {
        return faxInd;
    }


    /**
     * Sets the faxInd value for this LetterDistributionsData.
     * 
     * @param faxInd
     */
    public void setFaxInd(YesNo faxInd) {
        this.faxInd = faxInd;
    }


    /**
     * Gets the emailFrom value for this LetterDistributionsData.
     * 
     * @return emailFrom
     */
    public java.lang.String getEmailFrom() {
        return emailFrom;
    }


    /**
     * Sets the emailFrom value for this LetterDistributionsData.
     * 
     * @param emailFrom
     */
    public void setEmailFrom(java.lang.String emailFrom) {
        this.emailFrom = emailFrom;
    }


    /**
     * Gets the emailSubject value for this LetterDistributionsData.
     * 
     * @return emailSubject
     */
    public java.lang.String getEmailSubject() {
        return emailSubject;
    }


    /**
     * Sets the emailSubject value for this LetterDistributionsData.
     * 
     * @param emailSubject
     */
    public void setEmailSubject(java.lang.String emailSubject) {
        this.emailSubject = emailSubject;
    }


    /**
     * Gets the emailTo value for this LetterDistributionsData.
     * 
     * @return emailTo
     */
    public java.lang.String getEmailTo() {
        return emailTo;
    }


    /**
     * Sets the emailTo value for this LetterDistributionsData.
     * 
     * @param emailTo
     */
    public void setEmailTo(java.lang.String emailTo) {
        this.emailTo = emailTo;
    }


    /**
     * Gets the messageBody value for this LetterDistributionsData.
     * 
     * @return messageBody
     */
    public java.lang.String getMessageBody() {
        return messageBody;
    }


    /**
     * Sets the messageBody value for this LetterDistributionsData.
     * 
     * @param messageBody
     */
    public void setMessageBody(java.lang.String messageBody) {
        this.messageBody = messageBody;
    }


    /**
     * Gets the faxNo value for this LetterDistributionsData.
     * 
     * @return faxNo
     */
    public java.lang.String getFaxNo() {
        return faxNo;
    }


    /**
     * Sets the faxNo value for this LetterDistributionsData.
     * 
     * @param faxNo
     */
    public void setFaxNo(java.lang.String faxNo) {
        this.faxNo = faxNo;
    }


    /**
     * Gets the printOptions value for this LetterDistributionsData.
     * 
     * @return printOptions
     */
    public java.lang.String getPrintOptions() {
        return printOptions;
    }


    /**
     * Sets the printOptions value for this LetterDistributionsData.
     * 
     * @param printOptions
     */
    public void setPrintOptions(java.lang.String printOptions) {
        this.printOptions = printOptions;
    }


    /**
     * Gets the printerName value for this LetterDistributionsData.
     * 
     * @return printerName
     */
    public java.lang.String getPrinterName() {
        return printerName;
    }


    /**
     * Sets the printerName value for this LetterDistributionsData.
     * 
     * @param printerName
     */
    public void setPrinterName(java.lang.String printerName) {
        this.printerName = printerName;
    }


    /**
     * Gets the printerQueueName value for this LetterDistributionsData.
     * 
     * @return printerQueueName
     */
    public java.lang.String getPrinterQueueName() {
        return printerQueueName;
    }


    /**
     * Sets the printerQueueName value for this LetterDistributionsData.
     * 
     * @param printerQueueName
     */
    public void setPrinterQueueName(java.lang.String printerQueueName) {
        this.printerQueueName = printerQueueName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LetterDistributionsData)) return false;
        LetterDistributionsData other = (LetterDistributionsData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.emailInd==null && other.getEmailInd()==null) || 
             (this.emailInd!=null &&
              this.emailInd.equals(other.getEmailInd()))) &&
            ((this.printerInd==null && other.getPrinterInd()==null) || 
             (this.printerInd!=null &&
              this.printerInd.equals(other.getPrinterInd()))) &&
            ((this.faxInd==null && other.getFaxInd()==null) || 
             (this.faxInd!=null &&
              this.faxInd.equals(other.getFaxInd()))) &&
            ((this.emailFrom==null && other.getEmailFrom()==null) || 
             (this.emailFrom!=null &&
              this.emailFrom.equals(other.getEmailFrom()))) &&
            ((this.emailSubject==null && other.getEmailSubject()==null) || 
             (this.emailSubject!=null &&
              this.emailSubject.equals(other.getEmailSubject()))) &&
            ((this.emailTo==null && other.getEmailTo()==null) || 
             (this.emailTo!=null &&
              this.emailTo.equals(other.getEmailTo()))) &&
            ((this.messageBody==null && other.getMessageBody()==null) || 
             (this.messageBody!=null &&
              this.messageBody.equals(other.getMessageBody()))) &&
            ((this.faxNo==null && other.getFaxNo()==null) || 
             (this.faxNo!=null &&
              this.faxNo.equals(other.getFaxNo()))) &&
            ((this.printOptions==null && other.getPrintOptions()==null) || 
             (this.printOptions!=null &&
              this.printOptions.equals(other.getPrintOptions()))) &&
            ((this.printerName==null && other.getPrinterName()==null) || 
             (this.printerName!=null &&
              this.printerName.equals(other.getPrinterName()))) &&
            ((this.printerQueueName==null && other.getPrinterQueueName()==null) || 
             (this.printerQueueName!=null &&
              this.printerQueueName.equals(other.getPrinterQueueName())));
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
        if (getEmailInd() != null) {
            _hashCode += getEmailInd().hashCode();
        }
        if (getPrinterInd() != null) {
            _hashCode += getPrinterInd().hashCode();
        }
        if (getFaxInd() != null) {
            _hashCode += getFaxInd().hashCode();
        }
        if (getEmailFrom() != null) {
            _hashCode += getEmailFrom().hashCode();
        }
        if (getEmailSubject() != null) {
            _hashCode += getEmailSubject().hashCode();
        }
        if (getEmailTo() != null) {
            _hashCode += getEmailTo().hashCode();
        }
        if (getMessageBody() != null) {
            _hashCode += getMessageBody().hashCode();
        }
        if (getFaxNo() != null) {
            _hashCode += getFaxNo().hashCode();
        }
        if (getPrintOptions() != null) {
            _hashCode += getPrintOptions().hashCode();
        }
        if (getPrinterName() != null) {
            _hashCode += getPrinterName().hashCode();
        }
        if (getPrinterQueueName() != null) {
            _hashCode += getPrinterQueueName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LetterDistributionsData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/webservices/AddDocumentRequestInput", "LetterDistributionsData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emailInd");
        elemField.setXmlName(new javax.xml.namespace.QName("", "emailInd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/common/ilinkCommon", "YesNo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("printerInd");
        elemField.setXmlName(new javax.xml.namespace.QName("", "printerInd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/common/ilinkCommon", "YesNo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("faxInd");
        elemField.setXmlName(new javax.xml.namespace.QName("", "faxInd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://amdocs/ilinkmobile/common/ilinkCommon", "YesNo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emailFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("", "emailFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emailSubject");
        elemField.setXmlName(new javax.xml.namespace.QName("", "emailSubject"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emailTo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "emailTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messageBody");
        elemField.setXmlName(new javax.xml.namespace.QName("", "messageBody"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("faxNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "faxNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("printOptions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "printOptions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("printerName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "printerName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("printerQueueName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "printerQueueName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
