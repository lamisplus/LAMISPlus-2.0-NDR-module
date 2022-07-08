
package org.lamisplus.modules.ndr.schema;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for TestResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TestResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ScreeningTestResult">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="R"/>
 *               &lt;enumeration value="NR"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ScreeningTestResultDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="ConfirmatoryTestResult">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="R"/>
 *               &lt;enumeration value="NR"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ConfirmatoryTestResultDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="TieBreakerTestResult">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="R"/>
 *               &lt;enumeration value="NR"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="TieBreakerTestResultDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="FinalTestResult">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="Pos"/>
 *               &lt;enumeration value="Neg"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TestResultType", propOrder = {
    "screeningTestResult",
    "screeningTestResultDate",
    "confirmatoryTestResult",
    "confirmatoryTestResultDate",
    "tieBreakerTestResult",
    "tieBreakerTestResultDate",
    "finalTestResult"
})
public class TestResultType {

    @XmlElement(name = "ScreeningTestResult", required = true)
    protected String screeningTestResult;
    @XmlElement(name = "ScreeningTestResultDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar screeningTestResultDate;
    @XmlElement(name = "ConfirmatoryTestResult", required = true)
    protected String confirmatoryTestResult;
    @XmlElement(name = "ConfirmatoryTestResultDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar confirmatoryTestResultDate;
    @XmlElement(name = "TieBreakerTestResult", required = true)
    protected String tieBreakerTestResult;
    @XmlElement(name = "TieBreakerTestResultDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar tieBreakerTestResultDate;
    @XmlElement(name = "FinalTestResult", required = true)
    protected String finalTestResult;

    /**
     * Gets the value of the screeningTestResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScreeningTestResult() {
        return screeningTestResult;
    }

    /**
     * Sets the value of the screeningTestResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScreeningTestResult(String value) {
        this.screeningTestResult = value;
    }

    /**
     * Gets the value of the screeningTestResultDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getScreeningTestResultDate() {
        return screeningTestResultDate;
    }

    /**
     * Sets the value of the screeningTestResultDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setScreeningTestResultDate(XMLGregorianCalendar value) {
        this.screeningTestResultDate = value;
    }

    /**
     * Gets the value of the confirmatoryTestResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfirmatoryTestResult() {
        return confirmatoryTestResult;
    }

    /**
     * Sets the value of the confirmatoryTestResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfirmatoryTestResult(String value) {
        this.confirmatoryTestResult = value;
    }

    /**
     * Gets the value of the confirmatoryTestResultDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getConfirmatoryTestResultDate() {
        return confirmatoryTestResultDate;
    }

    /**
     * Sets the value of the confirmatoryTestResultDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setConfirmatoryTestResultDate(XMLGregorianCalendar value) {
        this.confirmatoryTestResultDate = value;
    }

    /**
     * Gets the value of the tieBreakerTestResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTieBreakerTestResult() {
        return tieBreakerTestResult;
    }

    /**
     * Sets the value of the tieBreakerTestResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTieBreakerTestResult(String value) {
        this.tieBreakerTestResult = value;
    }

    /**
     * Gets the value of the tieBreakerTestResultDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTieBreakerTestResultDate() {
        return tieBreakerTestResultDate;
    }

    /**
     * Sets the value of the tieBreakerTestResultDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTieBreakerTestResultDate(XMLGregorianCalendar value) {
        this.tieBreakerTestResultDate = value;
    }

    /**
     * Gets the value of the finalTestResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFinalTestResult() {
        return finalTestResult;
    }

    /**
     * Sets the value of the finalTestResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFinalTestResult(String value) {
        this.finalTestResult = value;
    }

}