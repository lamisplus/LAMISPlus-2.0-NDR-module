
package org.lamisplus.modules.ndr.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AddressType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AddressType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AddressTypeCode" type="{}CodeType"/>
 *         &lt;element name="WardVillage" type="{}StringType" minOccurs="0"/>
 *         &lt;element name="Town" type="{}StringType" minOccurs="0"/>
 *         &lt;element name="LGACode" type="{}CodeType" minOccurs="0"/>
 *         &lt;element name="StateCode" type="{}CodeType" minOccurs="0"/>
 *         &lt;element name="CountryCode" type="{}CodeType" minOccurs="0"/>
 *         &lt;element name="PostalCode" type="{}StringType" minOccurs="0"/>
 *         &lt;element name="OtherAddressInformation" type="{}StringType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressType", propOrder = {
    "addressTypeCode",
    "wardVillage",
    "town",
    "lgaCode",
    "stateCode",
    "countryCode",
    "postalCode",
    "otherAddressInformation"
})
public class AddressType {

    @XmlElement(name = "AddressTypeCode", required = true)
    protected String addressTypeCode;
    @XmlElement(name = "WardVillage")
    protected String wardVillage;
    @XmlElement(name = "Town")
    protected String town;
    @XmlElement(name = "LGACode")
    protected String lgaCode;
    @XmlElement(name = "StateCode")
    protected String stateCode;
    @XmlElement(name = "CountryCode")
    protected String countryCode;
    @XmlElement(name = "PostalCode")
    protected String postalCode;
    @XmlElement(name = "OtherAddressInformation")
    protected String otherAddressInformation;

    /**
     * Gets the value of the addressTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressTypeCode() {
        return addressTypeCode;
    }

    /**
     * Sets the value of the addressTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressTypeCode(String value) {
        this.addressTypeCode = value;
    }

    /**
     * Gets the value of the wardVillage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWardVillage() {
        return wardVillage;
    }

    /**
     * Sets the value of the wardVillage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWardVillage(String value) {
        this.wardVillage = value;
    }

    /**
     * Gets the value of the town property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTown() {
        return town;
    }

    /**
     * Sets the value of the town property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTown(String value) {
        this.town = value;
    }

    /**
     * Gets the value of the lgaCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLGACode() {
        return lgaCode;
    }

    /**
     * Sets the value of the lgaCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLGACode(String value) {
        this.lgaCode = value;
    }

    /**
     * Gets the value of the stateCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateCode() {
        return stateCode;
    }

    /**
     * Sets the value of the stateCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateCode(String value) {
        this.stateCode = value;
    }

    /**
     * Gets the value of the countryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the value of the countryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryCode(String value) {
        this.countryCode = value;
    }

    /**
     * Gets the value of the postalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the value of the postalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCode(String value) {
        this.postalCode = value;
    }

    /**
     * Gets the value of the otherAddressInformation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherAddressInformation() {
        return otherAddressInformation;
    }

    /**
     * Sets the value of the otherAddressInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherAddressInformation(String value) {
        this.otherAddressInformation = value;
    }

}
