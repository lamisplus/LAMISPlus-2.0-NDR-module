package org.lamisplus.modules.ndr.mapper;


import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.domain.entities.OrganisationUnit;
import org.lamisplus.modules.base.service.OrganisationUnitService;
import org.lamisplus.modules.ndr.schema.FacilityType;
import org.lamisplus.modules.ndr.schema.FingerPrintType;
import org.lamisplus.modules.ndr.schema.PatientDemographicsType;
import org.lamisplus.modules.ndr.service.NDRCodeSetResolverService;
import org.lamisplus.modules.ndr.utility.DateUtil;
import org.lamisplus.modules.patient.domain.entity.Person;
import org.lamisplus.modules.patient.repository.PersonRepository;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;


@RequiredArgsConstructor
@Slf4j
@Service
public class PatientDemographicsMapper {
    private final PersonRepository personRepository;

    private final MessageHeaderTypeMapper messageHeaderTypeMapper;

    private final OrganisationUnitService organisationUnitService;

    private  final NDRCodeSetResolverService ndrCodeSetResolverService;

    private  final  BiometricTemplateMapper biometricTemplateMapper;
    private  final  String DISPLAY = "display";
    public PatientDemographicsType getPatientDemographics(Long patientId) {

        PatientDemographicsType patientDemographics = new PatientDemographicsType ();
        Optional<Person> optionalPerson = personRepository.findById (patientId);
        if (optionalPerson.isPresent ()) {
            try {
                Person person = optionalPerson.get ();
                FacilityType treatmentFacility = messageHeaderTypeMapper.getTreatmentFacility (person.getFacilityId ());
                String identifier = treatmentFacility.getFacilityID () + "_" + person.getUuid ();
                patientDemographics.setPatientIdentifier (identifier);
                patientDemographics.setTreatmentFacility (treatmentFacility);
                processAndSetDateOFBirth (patientDemographics, person.getDateOfBirth ());
                processAndSetSex (patientDemographics, person.getSex ());
                processAndSetEducationLevelCode (patientDemographics, person.getEducation ());
                processAndSetMaritalStatusCode (patientDemographics, person.getMaritalStatus ());
                processAndSetStateOfOrigin(patientDemographics, person.getAddress ());
                processAndSetOccupationalStatusCode(patientDemographics, person.getEmploymentStatus ());
                FingerPrintType fingerPrintTypeForPatient = biometricTemplateMapper.getFingerPrintTypeForPatient (person.getUuid ());
                if(fingerPrintTypeForPatient != null){
                patientDemographics.setFingerPrints (fingerPrintTypeForPatient);
                }
                return patientDemographics;
            } catch (Exception e) {
               e.printStackTrace ();
            }
        }

        return null;
    }

    private void processAndSetDateOFBirth(PatientDemographicsType patientDemographics, LocalDate dateOfBirth) throws DatatypeConfigurationException {
        if (dateOfBirth != null) {
            patientDemographics.setPatientDateOfBirth (DateUtil.getXmlDate (Date.valueOf (dateOfBirth)));
        }
    }

    private void processAndSetSex(PatientDemographicsType patientDemographics, String sex) {
            Optional<String> sexCode = ndrCodeSetResolverService.getNDRCodeSetCode ("SEX", sex);
            sexCode.ifPresent (patientDemographics::setPatientSexCode);

    }


    private void processAndSetEducationLevelCode(PatientDemographicsType demographicsType, JsonNode education) {
        if (education.hasNonNull (DISPLAY)) {
            String educationLevel = education.get (DISPLAY).asText ();
            Optional<String> educationalLevelCode = ndrCodeSetResolverService.getNDRCodeSetCode ("EDUCATIONAL_LEVEL", educationLevel);
            educationalLevelCode.ifPresent (demographicsType::setPatientEducationLevelCode);
        }
    }

    private void processAndSetMaritalStatusCode(PatientDemographicsType demographicsType, JsonNode maritalStatusJson) {
        if (maritalStatusJson.hasNonNull (DISPLAY)) {
            String maritalStatus = maritalStatusJson.get (DISPLAY).asText ();
            LOG.info ("maritalStatus: lamis {}", maritalStatus);
            Optional<String> maritalStatusCode = ndrCodeSetResolverService.getNDRCodeSetCode ("MARITAL_STATUS", maritalStatus);
            maritalStatusCode.ifPresent (demographicsType::setPatientMaritalStatusCode);
        }
    }

    private void processAndSetStateOfOrigin(PatientDemographicsType demographicsType, JsonNode stateOfOriginJson) {
        if (stateOfOriginJson.hasNonNull ("address")) {
            JsonNode address = stateOfOriginJson.get ("address");
            if (address.isArray ()) {
                JsonNode addressObject = address.get (0);
                if (addressObject.hasNonNull ("stateId") && addressObject.hasNonNull ("district")) {
                    Long stateId = addressObject.get ("stateId").asLong ();
                    OrganisationUnit organizationUnit = organisationUnitService.getOrganizationUnit (stateId);
                    Optional<String> state = ndrCodeSetResolverService.getNDRCodeSetCode ("STATES", organizationUnit.getName ());
                    state.ifPresent (demographicsType::setStateOfNigeriaOriginCode);
                }
            }
        }
    }

    private void processAndSetOccupationalStatusCode(PatientDemographicsType demographicsType, JsonNode occupationStatusJson) {
        if (occupationStatusJson.hasNonNull (DISPLAY)) {
            String occupation = occupationStatusJson.get (DISPLAY).asText ();
            Optional<String> occupationCode = ndrCodeSetResolverService.getNDRCodeSetCode ("OCCUPATION_STATUS", occupation);
            occupationCode.ifPresent (demographicsType::setPatientOccupationCode);
        }
    }


}
