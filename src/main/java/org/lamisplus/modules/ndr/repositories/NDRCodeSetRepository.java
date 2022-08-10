package org.lamisplus.modules.ndr.repositories;

import org.lamisplus.modules.ndr.domain.dto.BiometricDto;
import org.lamisplus.modules.ndr.domain.entities.NDRCodeSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NDRCodeSetRepository extends JpaRepository<NDRCodeSet, String> {
    Optional<NDRCodeSet> getNDRCodeSetByCodeSetNmAndSysDescription(String codeSetNm, String sysDescription);
    Optional<NDRCodeSet> getNDRCodeSetBySysDescription(String sysDescription);

    Optional<NDRCodeSet> getNDRCodeSetByCodeDescription(String codeDescription);

    @Query(value = "select r.regimen from hiv_regimen_resolver r where r.regimensys = ? limit 1", nativeQuery = true)
    Optional<String> getNDREquivalentRegimenUsingSystemRegimen(String systemRegimen);

    @Query(value = "select template_type as templateType, enrollment_date as enrollmentDate, template from biometric where person_uuid = :patientUuid" +
            " and biometric_type = 'FINGERPRINT' and archived = 0 and iso = true", nativeQuery = true)
    List<BiometricDto> getPatientBiometricByPatientUuid(String patientUuid);


}
