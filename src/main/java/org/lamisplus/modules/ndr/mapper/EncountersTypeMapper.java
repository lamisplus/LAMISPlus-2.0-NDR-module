package org.lamisplus.modules.ndr.mapper;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.domain.dto.ApplicationCodesetDTO;
import org.lamisplus.modules.base.service.ApplicationCodesetService;
import org.lamisplus.modules.hiv.domain.dto.HivEnrollmentDto;
import org.lamisplus.modules.hiv.domain.entity.ARTClinical;
import org.lamisplus.modules.hiv.domain.entity.ArtPharmacy;
import org.lamisplus.modules.hiv.domain.entity.Regimen;
import org.lamisplus.modules.hiv.domain.entity.RegimenType;
import org.lamisplus.modules.hiv.repositories.ARTClinicalRepository;
import org.lamisplus.modules.hiv.repositories.ArtPharmacyRepository;
import org.lamisplus.modules.hiv.service.HivEnrollmentService;
import org.lamisplus.modules.ndr.schema.CodedSimpleType;
import org.lamisplus.modules.ndr.schema.EncountersType;
import org.lamisplus.modules.ndr.schema.HIVEncounterType;
import org.lamisplus.modules.ndr.service.NDRCodeSetResolverService;
import org.lamisplus.modules.ndr.utility.DateUtil;
import org.lamisplus.modules.patient.domain.entity.Person;
import org.lamisplus.modules.patient.repository.PersonRepository;
import org.lamisplus.modules.triage.domain.entity.VitalSign;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class EncountersTypeMapper {

    private final ArtPharmacyRepository pharmacyRepository;

    private final ARTClinicalRepository clinicalRepository;

    private final NDRCodeSetResolverService ndrCodeSetResolverService;
    private final PersonRepository personRepository;

    private final ApplicationCodesetService applicationCodesetService;

    private final HivEnrollmentService hivEnrollmentService;

    public EncountersType encounterType(Long patientId) {
        EncountersType encountersType = new EncountersType ();
        Optional<Person> personOptional = personRepository.findById (patientId);
        personOptional.ifPresent (person -> {
            List<HIVEncounterType> hivEncounter = encountersType.getHIVEncounter ();
            List<ARTClinical> clinicalList = clinicalRepository.findAllByPersonAndIsCommencementIsFalseAndArchived (person, 0);
            LOG.info ("encounter list= {} ", clinicalList.size ());
            clinicalList.forEach (
                    artClinical -> {
                        HIVEncounterType hivEncounterType = new HIVEncounterType ();
                        hivEncounterType.setVisitID (artClinical.getUuid ());
                        processAndSetVisitDate (artClinical, hivEncounterType);
                        processAndSetNextAppointment (artClinical, hivEncounterType);
                        VitalSign vitalSign = artClinical.getVitalSign ();
                        if (vitalSign != null) {
                            processAndSetWeightAndHeight (hivEncounterType, vitalSign);
                            processAndSetBloodPressure (hivEncounterType, vitalSign);
                        }
                        processAndSetWhoStageAndFunctionalStatus (artClinical, hivEncounterType);
                        processClinicalEncounterRegimens (person, artClinical, hivEncounterType);
                        //only thing missing  here CD4 and date of CD4
                        processAndSetTBStatus (person, hivEncounterType);
                        hivEncounter.add (hivEncounterType);
                    });

        });
        return encountersType;
    }

    private void processAndSetTBStatus(Person person, HIVEncounterType hivEncounterType) {
        Optional<HivEnrollmentDto> hivEnrollmentOptional = hivEnrollmentService.getHivEnrollmentByPersonIdAndArchived (person.getId ());
        hivEnrollmentOptional.ifPresent (hivEnrollment -> {
            Long tbStatusId = hivEnrollment.getTbStatusId ();
            if (tbStatusId != null) {
                ApplicationCodesetDTO tbStatus = applicationCodesetService.getApplicationCodeset (tbStatusId);
                if (tbStatus != null) {
                    String tbStatusDisplay = tbStatus.getDisplay ();
                    Optional<String> ndrTBStatus = ndrCodeSetResolverService.getNDRCodeSetCode ("TB_STATUS", tbStatusDisplay);
                    ndrTBStatus.ifPresent (hivEncounterType::setTBStatus);
                }
            }
        });
    }


    private void processClinicalEncounterRegimens(Person person, ARTClinical artClinical, HIVEncounterType hivEncounterType) {
        List<ArtPharmacy> pharmacies = pharmacyRepository.getArtPharmaciesByVisitIdAndPerson (artClinical.getVisitId (), person);
        LOG.info ("pharmacy list = {}", pharmacies.size ());
        List<Long> regimenTypeIds = new ArrayList<> (Arrays.asList (1l, 2l, 3l, 4l, 14l));
        List<Long> cotrimoxazoleTypeId = new ArrayList<> (Arrays.asList (8l));
        pharmacies.stream ().forEach (
                artPharmacy -> {
                    Set<Regimen> regimens = artPharmacy.getRegimens ();
                    LOG.info ("Regimen list = {}", regimens.size ());
                    regimens.stream ()
                            .filter (regimen -> regimenTypeIds.contains (regimen.getRegimenType ().getId ()))
                            .forEach (regimen -> {
                                LOG.info ("ndrRegimenSystemDescription {}", regimen.getDescription ());
                                Optional<CodedSimpleType> ndrCodeSet = ndrCodeSetResolverService.getRegimen (regimen.getDescription ());
                                if (ndrCodeSet.isPresent ()) {
                                    ndrCodeSet.ifPresent (hivEncounterType::setARVDrugRegimen);
                                } else {
                                    RegimenType regimenType = regimen.getRegimenType ();
                                    if (regimenType != null) {
                                        String others = "Others" + "_" + regimenType.getId ();
                                        LOG.info ("others {}", others);
                                        Optional<CodedSimpleType> ndrCodeSet2 = ndrCodeSetResolverService.getSimpleCodeSet (others);
                                        ndrCodeSet2.ifPresent (hivEncounterType::setARVDrugRegimen);

                                    }
                                }

                            });
                    regimens.stream ().filter (regimen -> cotrimoxazoleTypeId.contains (regimen.getRegimenType ().getId ()))
                            .forEach (regimen -> {
                                RegimenType regimenType = regimen.getRegimenType ();
                                String composition = regimen.getComposition ();
                                if (regimenType != null && composition != null) {
                                    String description = regimenType.getDescription ();
                                    LOG.info ("cotrimoxazole {}", description);
                                    Optional<CodedSimpleType> codedSimpleType = ndrCodeSetResolverService.getNDRCodeSet ("REGIMEN_TYPE", description);
                                    codedSimpleType.ifPresent (hivEncounterType::setCotrimoxazoleDose);
                                }
                            });
                }
        );
    }


    private void processAndSetVisitDate(ARTClinical artClinical, HIVEncounterType hivEncounterType) {
        LocalDate visitDate = artClinical.getVisitDate ();
        if (visitDate != null) {
            try {
                hivEncounterType.setVisitDate (DateUtil.getXmlDate (Date.valueOf (visitDate)));
            } catch (DatatypeConfigurationException e) {
                e.printStackTrace ();
            }
        }
    }


    private void processAndSetBloodPressure(HIVEncounterType hivEncounterType, VitalSign vitalSign) {
        double bloodPressure = vitalSign.getSystolic () / vitalSign.getDiastolic ();
        if (bloodPressure > 0) {
            int systolic = vitalSign.getSystolic ().intValue ();
            int diastolic = vitalSign.getDiastolic ().intValue ();
            String bloodPressureValue = systolic + "/" + diastolic;
            hivEncounterType.setBloodPressure (bloodPressureValue);

        }
    }

    private void processAndSetNextAppointment(ARTClinical artClinical, HIVEncounterType hivEncounterType) {
        LocalDate nextAppointment = artClinical.getNextAppointment ();
        if (nextAppointment != null) {
            try {
                hivEncounterType.setNextAppointmentDate (DateUtil.getXmlDate (Date.valueOf (nextAppointment)));
            } catch (DatatypeConfigurationException e) {
               e.printStackTrace ();
            }
        }
    }

    private void processAndSetWeightAndHeight(HIVEncounterType hivEncounterType, VitalSign vitalSign) {
        int bodyWeight = vitalSign.getBodyWeight ().intValue ();
        int height = vitalSign.getHeight ().intValue ();
        if (bodyWeight > 0) {
            hivEncounterType.setWeight (bodyWeight);
        }
        if (bodyWeight > 200) {
            hivEncounterType.setWeight (bodyWeight / 10);
        }
        if (height > 0) {
            hivEncounterType.setChildHeight (height);
        }
        if (height > 200) {
            hivEncounterType.setChildHeight (height / 10);
        }
    }


    private void processAndSetWhoStageAndFunctionalStatus(ARTClinical artClinical, HIVEncounterType hivEncounterType) {
        ApplicationCodesetDTO functionalStatus = applicationCodesetService.getApplicationCodeset (artClinical.getFunctionalStatusId ());
        if (functionalStatus != null) {
            Optional<String> functionalStatusCodeSet =
                    ndrCodeSetResolverService.getNDRCodeSetCode ("FUNCTIONAL_STATUS", functionalStatus.getDisplay ());
            functionalStatusCodeSet.ifPresent (hivEncounterType::setFunctionalStatus);
        }
        ApplicationCodesetDTO WHOStageCode = applicationCodesetService.getApplicationCodeset (artClinical.getWhoStagingId ());
        if (WHOStageCode != null) {
            Optional<String> whoStageCodeSet =
                    ndrCodeSetResolverService.getNDRCodeSetCode ("WHO_STAGE", WHOStageCode.getDisplay ());
            whoStageCodeSet.ifPresent (hivEncounterType::setWHOClinicalStage);
        }
    }

}
