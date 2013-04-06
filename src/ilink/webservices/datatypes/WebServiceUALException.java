/**
 * WebServiceUALException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package ilink.webservices.datatypes;

public class WebServiceUALException extends org.apache.axis.AxisFault {
    public java.lang.String description;
    public java.lang.String getDescription() {
        return this.description;
    }

    public WebServiceUALException() {
    }

    public WebServiceUALException(java.lang.Exception target) {
        super(target);
    }

    public WebServiceUALException(java.lang.String message, java.lang.Throwable t) {
        super(message, t);
    }

      public WebServiceUALException(java.lang.String description) {
        this.description = description;
    }

    /**
     * Writes the exception data to the faultDetails
     */
    public void writeDetails(javax.xml.namespace.QName qname, org.apache.axis.encoding.SerializationContext context) throws java.io.IOException {
        context.serialize(qname, null, description);
    }
}