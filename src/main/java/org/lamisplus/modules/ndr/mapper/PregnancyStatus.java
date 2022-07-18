package org.lamisplus.modules.ndr.mapper;

import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.hiv.domain.entity.HivEnrollment;
import org.lamisplus.modules.hiv.repositories.HivEnrollmentRepository;
import org.lamisplus.modules.patient.domain.entity.Person;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PregnancyStatus {

    private final HivEnrollmentRepository hivEnrollmentRepository;

    public Map<String, Object> getPregnancyStatus(Person person) {
        Optional<HivEnrollment> hivEnrollmentOptional = hivEnrollmentRepository.getHivEnrollmentByPersonAndArchived (person, 0);
        Map<String, Object> map = new HashMap<> ();
        map.put ("status", "NK");   //If no clinic record is found the pregnancy status is Unknown
        map.put ("lmp", null);
        map.put ("edd", null);
        if (hivEnrollmentOptional.isPresent ()) {
            map.put ("status", "NP");  //If clinic record is found but no record with pregnancy status checked
            map.put ("lmp", null);
            map.put ("edd", null);
            if (hivEnrollmentOptional.get ().getPregnant ()) {
                map.put ("status", "P");
                map.put ("lmp", hivEnrollmentOptional.get ().getDateOfLpm ());
            }

        }
        return map;
    }
}
