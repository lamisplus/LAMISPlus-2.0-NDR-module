
package org.lamisplus.modules.ndr.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PMTCTClinicalTBScreeningType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PMTCTClinicalTBScreeningType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CurrentlyCough" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="WeightLoss" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Fever" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="NightSweats" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ContactWithTBPositivePatient" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PMTCTClinicalTBScreeningType", propOrder = {
    "currentlyCough",
    "weightLoss",
    "fever",
    "nightSweats",
    "contactWithTBPositivePatient"
})
public class PMTCTClinicalTBScreeningType {

    @XmlElement(name = "CurrentlyCough")
    protected boolean currentlyCough;
    @XmlElement(name = "WeightLoss")
    protected boolean weightLoss;
    @XmlElement(name = "Fever")
    protected boolean fever;
    @XmlElement(name = "NightSweats")
    protected boolean nightSweats;
    @XmlElement(name = "ContactWithTBPositivePatient")
    protected boolean contactWithTBPositivePatient;

    /**
     * Gets the value of the currentlyCough property.
     * 
     */
    public boolean isCurrentlyCough() {
        return currentlyCough;
    }

    /**
     * Sets the value of the currentlyCough property.
     * 
     */
    public void setCurrentlyCough(boolean value) {
        this.currentlyCough = value;
    }

    /**
     * Gets the value of the weightLoss property.
     * 
     */
    public boolean isWeightLoss() {
        return weightLoss;
    }

    /**
     * Sets the value of the weightLoss property.
     * 
     */
    public void setWeightLoss(boolean value) {
        this.weightLoss = value;
    }

    /**
     * Gets the value of the fever property.
     * 
     */
    public boolean isFever() {
        return fever;
    }

    /**
     * Sets the value of the fever property.
     * 
     */
    public void setFever(boolean value) {
        this.fever = value;
    }

    /**
     * Gets the value of the nightSweats property.
     * 
     */
    public boolean isNightSweats() {
        return nightSweats;
    }

    /**
     * Sets the value of the nightSweats property.
     * 
     */
    public void setNightSweats(boolean value) {
        this.nightSweats = value;
    }

    /**
     * Gets the value of the contactWithTBPositivePatient property.
     * 
     */
    public boolean isContactWithTBPositivePatient() {
        return contactWithTBPositivePatient;
    }

    /**
     * Sets the value of the contactWithTBPositivePatient property.
     * 
     */
    public void setContactWithTBPositivePatient(boolean value) {
        this.contactWithTBPositivePatient = value;
    }

}
