//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-661 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.01.28 at 06:41:14 PM EST 
//


package logger.xml.defInstance;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "ref")
public class Ref {

    @XmlAttribute
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String ecuparam;
    @XmlAttribute
    @XmlIDREF
    protected Object parameter;

    /**
     * Gets the value of the ecuparam property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEcuparam() {
        return ecuparam;
    }

    /**
     * Sets the value of the ecuparam property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEcuparam(String value) {
        this.ecuparam = value;
    }

    /**
     * Gets the value of the parameter property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getParameter() {
        return parameter;
    }

    /**
     * Sets the value of the parameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setParameter(Object value) {
        this.parameter = value;
    }

}