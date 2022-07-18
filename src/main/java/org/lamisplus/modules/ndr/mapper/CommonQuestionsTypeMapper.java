package org.lamisplus.modules.ndr.mapper;

import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.hiv.domain.entity.HivEnrollment;
import org.lamisplus.modules.hiv.repositories.HivEnrollmentRepository;
import org.lamisplus.modules.hiv.service.HIVStatusTrackerService;
import org.lamisplus.modules.ndr.schema.CommonQuestionsType;
import org.lamisplus.modules.ndr.schema.FacilityType;
import org.lamisplus.modules.ndr.utility.DateUtil;
import org.lamisplus.modules.patient.domain.entity.Person;
import org.lamisplus.modules.patient.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CommonQuestionsTypeMapper {
    private final PersonRepository personRepository;
    private final MessageHeaderTypeMapper messageHeaderTypeMapper;

   private final PregnancyStatus pregnancyStatus;

   private final HIVStatusTrackerService hivStatusTrackerService;

   private final HivEnrollmentRepository hivEnrollmentRepository;



    public CommonQuestionsType getPatientCommonQuestion(Long patientId) {
        try {
            Optional<Person> personOptional = personRepository.findById (patientId);
            if (personOptional.isPresent ()) {
                Person person = personOptional.get ();
                CommonQuestionsType common = new CommonQuestionsType ();
                FacilityType treatmentFacility = messageHeaderTypeMapper.getTreatmentFacility (person.getFacilityId ());
                common.setDiagnosisFacility (treatmentFacility);
                common.setHospitalNumber (person.getHospitalNumber ());
                int age = getAge (person.getDateOfBirth ());
                if (age > 0) {
                    common.setPatientAge (age);
                }
                if (person.getGender ().hasNonNull ("display")) {
                    String sex = person.getGender ().get ("display").asText ();
                    if (sex.contains ("F")) {
                        Map<String, Object> status = pregnancyStatus.getPregnancyStatus (person);
                        common.setPatientPregnancyStatusCode ((String) status.get ("status"));
                    }
                }
                String currentStatus = hivStatusTrackerService.getPersonCurrentHIVStatusByPersonId (patientId);
                if(currentStatus.equalsIgnoreCase ("KNOWN_DEATH")){
                common.setPatientDieFromThisIllness (true);
                }
                Optional<HivEnrollment> enrollment = hivEnrollmentRepository.getHivEnrollmentByPersonAndArchived (person, 0);
                if(enrollment.isPresent ()){
                    LocalDate dateOfRegistration = enrollment.get ().getDateOfRegistration ();
                    if(dateOfRegistration != null){
                    common.setDiagnosisDate (DateUtil.getXmlDate ( Date.valueOf (dateOfRegistration)));
                    }
                }
                return common;
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }

        return null;
    }

    private int getAge(LocalDate dateOfBirth) {
        LocalDate currentDate = LocalDate.now ();
        return Period.between (dateOfBirth, currentDate).getYears ();
    }

}
