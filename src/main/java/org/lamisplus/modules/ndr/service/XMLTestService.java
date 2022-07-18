package org.lamisplus.modules.ndr.service;

import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.ndr.mapper.*;
import org.lamisplus.modules.ndr.schema.*;
import org.springframework.stereotype.Service;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class XMLTestService {

    private final MessageHeaderTypeMapper messageHeaderTypeMapper;

    private final PatientDemographicsMapper patientDemographicsMapper;

    private final AddressTypeMapper addressTypeMapper;

    private  final CommonQuestionsTypeMapper commonQuestionsTypeMapper;

    private  final ConditionSpecificQuestionsTypeMapper specificQuestionsTypeMapper;

    private  final EncountersTypeMapper encountersTypeMapper;

    private final ConditionTypeMapper conditionTypeMapper;

    public void shouldPrintMessageHeaderTypeXml(Long id) {
        try {
            new MessageHeaderType ();
            MessageHeaderType messageHeaderType;
            JAXBContext jaxbContext = JAXBContext.newInstance (MessageHeaderType.class);
            messageHeaderType = messageHeaderTypeMapper.getMessageHeader (id);
            messageHeaderType.setMessageUniqueID (String.valueOf (id));
            messageHeaderType.setMessageStatusCode ("INITIAL");
            Marshaller jaxbMarshaller = getMarshaller (jaxbContext);
            jaxbMarshaller.setProperty ("com.sun.xml.bind.xmlHeaders", "\n<!-- This XML was generated from LAMISPlus application -->");
            jaxbMarshaller.setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty (Marshaller.JAXB_ENCODING, "UTF-8");
            String currentPath = System.getProperty ("user.dir");
            String fileName = "message-header.xml";
            File file = new File (String.format ("%s/temp/%d/%s", currentPath, id, fileName));
            jaxbMarshaller.marshal (messageHeaderType, file);
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    public void shouldPrintPatientDemographicsTypeXml(Long id) {
        try {
            new PatientDemographicsType ();
            PatientDemographicsType patientDemographicsType;
            JAXBContext jaxbContext = JAXBContext.newInstance (PatientDemographicsType.class);
            patientDemographicsType = patientDemographicsMapper.getPatientDemographics (id);
            Marshaller jaxbMarshaller = getMarshaller (jaxbContext);
            jaxbMarshaller.setProperty ("com.sun.xml.bind.xmlHeaders", "\n<!-- This XML was generated from LAMISPlus application -->");
            jaxbMarshaller.setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty (Marshaller.JAXB_ENCODING, "UTF-8");
            String currentPath = System.getProperty ("user.dir");
            String fileName = "patient_demographics.xml";
            File file = new File (String.format ("%s/temp/%d/%s", currentPath, id, fileName));
            jaxbMarshaller.marshal (patientDemographicsType, file);
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    private Marshaller getMarshaller(JAXBContext jaxbContext) throws JAXBException {
        return jaxbContext.createMarshaller ();
    }


    public void shouldPrintPatientAddressTypeXml(Long personId) {
        try{
            new AddressType ();
            AddressType addressType;
            JAXBContext jaxbContext = JAXBContext.newInstance (AddressType.class);
            addressType = addressTypeMapper.getPatientAddress (personId);
            Marshaller jaxbMarshaller = getMarshaller (jaxbContext);
            jaxbMarshaller.setProperty ("com.sun.xml.bind.xmlHeaders", "\n<!-- This XML was generated from LAMISPlus application -->");
            jaxbMarshaller.setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty (Marshaller.JAXB_ENCODING, "UTF-8");
            String currentPath = System.getProperty ("user.dir");
            String fileName = "patient_address.xml";
            File file = new File (String.format ("%s/temp/%d/%s", currentPath, personId, fileName));
            jaxbMarshaller.marshal (addressType, file);
        }catch (Exception ignore){
            ignore.printStackTrace ();
        }
    }

    public void shouldPrintPatientCommonQuestionsTypeXml(Long personId) {
        try{
            new AddressType ();
            CommonQuestionsType commonQuestionsType;
            JAXBContext jaxbContext = JAXBContext.newInstance (CommonQuestionsType.class);
            commonQuestionsType = commonQuestionsTypeMapper.getPatientCommonQuestion (personId);
            Marshaller jaxbMarshaller = getMarshaller (jaxbContext);
            jaxbMarshaller.setProperty ("com.sun.xml.bind.xmlHeaders", "\n<!-- This XML was generated from LAMISPlus application -->");
            jaxbMarshaller.setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty (Marshaller.JAXB_ENCODING, "UTF-8");
            String currentPath = System.getProperty ("user.dir");
            String fileName = "patient_common_question.xml";
            File file = new File (String.format ("%s/temp/%d/%s", currentPath, personId, fileName));
            jaxbMarshaller.marshal (commonQuestionsType, file);
        }catch (Exception ignore){
            ignore.printStackTrace ();
        }
    }

    public void shouldPrintPatientConditionSpecificQuestionsTypeXml(Long personId) {
        try{
            new AddressType ();
            ConditionSpecificQuestionsType conditionSpecificQuestionsType;
            JAXBContext jaxbContext = JAXBContext.newInstance (ConditionSpecificQuestionsType.class);
            conditionSpecificQuestionsType = specificQuestionsTypeMapper.getConditionSpecificQuestionsType (personId);
            Marshaller jaxbMarshaller = getMarshaller (jaxbContext);
            jaxbMarshaller.setProperty ("com.sun.xml.bind.xmlHeaders", "\n<!-- This XML was generated from LAMISPlus application -->");
            jaxbMarshaller.setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty (Marshaller.JAXB_ENCODING, "UTF-8");
            String currentPath = System.getProperty ("user.dir");
            String fileName = "patient_specific_hiv_questions.xml";
            File file = new File (String.format ("%s/temp/%d/%s", currentPath, personId, fileName));
            jaxbMarshaller.marshal (conditionSpecificQuestionsType, file);
        }catch (Exception ignore){
            ignore.printStackTrace ();
        }
    }
    public void shouldPrintPatientConditionEncountersTypeXml(Long personId) {
        try{
            new EncountersType ();
            EncountersType encountersType;
            JAXBContext jaxbContext = JAXBContext.newInstance (EncountersType.class);
            encountersType = encountersTypeMapper.encounterType (personId);
            Marshaller jaxbMarshaller = getMarshaller (jaxbContext);
            jaxbMarshaller.setProperty ("com.sun.xml.bind.xmlHeaders", "\n<!-- This XML was generated from LAMISPlus application -->");
            jaxbMarshaller.setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty (Marshaller.JAXB_ENCODING, "UTF-8");
            String currentPath = System.getProperty ("user.dir");
            String fileName = "patient_encounters.xml";
            File file = new File (String.format ("%s/temp/%d/%s", currentPath, personId, fileName));
            jaxbMarshaller.marshal (encountersType, file);
        }catch (Exception ignore){
            ignore.printStackTrace ();
        }
    }
    public void shouldPrintPatientConditionTypeXml(Long personId) {
        try{
            new ConditionType ();
            ConditionType ConditionType;
            JAXBContext jaxbContext = JAXBContext.newInstance (ConditionType.class);
            ConditionType = conditionTypeMapper.getConditionType (personId);
            Marshaller jaxbMarshaller = getMarshaller (jaxbContext);
            jaxbMarshaller.setProperty ("com.sun.xml.bind.xmlHeaders", "\n<!-- This XML was generated from LAMISPlus application -->");
            jaxbMarshaller.setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty (Marshaller.JAXB_ENCODING, "UTF-8");
            String currentPath = System.getProperty ("user.dir");
            String fileName = "patient.xml";
            File file = new File (String.format ("%s/temp/%s", currentPath, fileName));
            jaxbMarshaller.marshal (ConditionType, file);
        }catch (Exception ignore){
            ignore.printStackTrace ();
        }
    }

    public void shouldPrintPatientContainerXml(Long personId) {
        try{

            Container container = new Container ();
            JAXBContext jaxbContext = JAXBContext.newInstance (Container.class);
            ConditionType conditionType = conditionTypeMapper.getConditionType (personId);
            IndividualReportType individualReportType = new IndividualReportType ();
            PatientDemographicsType patientDemographics = patientDemographicsMapper.getPatientDemographics (personId);
            individualReportType.setPatientDemographics (patientDemographics);
            individualReportType.getCondition ().add (conditionType);
            MessageHeaderType messageHeader = messageHeaderTypeMapper.getMessageHeader (personId);
            messageHeader.setMessageStatusCode ("INITIAL");
            messageHeader.setMessageUniqueID (UUID.randomUUID ().toString ());
            container.setMessageHeader (messageHeader);
            container.setIndividualReport (individualReportType);
            Marshaller jaxbMarshaller = getMarshaller (jaxbContext);
            jaxbMarshaller.setProperty ("com.sun.xml.bind.xmlHeaders", "\n<!-- This XML was generated from LAMISPlus application -->");
            jaxbMarshaller.setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty (Marshaller.JAXB_ENCODING, "UTF-8");
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(getClass().getClassLoader().getResource("NDR 1.6.2.xsd"));
            jaxbMarshaller.setSchema(schema);
            String currentPath = System.getProperty ("user.dir");
            String fileName = "patient.xml";
            File file = new File (String.format ("%s/temp/%s", currentPath, fileName));
            jaxbMarshaller.marshal (container, file);
        }catch (Exception ignore){
            ignore.printStackTrace ();
        }
    }
}
