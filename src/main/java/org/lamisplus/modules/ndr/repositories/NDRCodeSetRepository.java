package org.lamisplus.modules.ndr.repositories;

import org.lamisplus.modules.ndr.domain.entities.NDRCodeSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NDRCodeSetRepository extends JpaRepository<NDRCodeSet, String> {
    Optional<NDRCodeSet> getNDRCodeSetByCodeSetNmAndSysDescription(String codeSetNm, String sysDescription);
    Optional<NDRCodeSet> getNDRCodeSetBySysDescription(String sysDescription);

    Optional<NDRCodeSet> getNDRCodeSetByCodeDescription(String codeDescription);

    @Query(value = "select r.regimen from hiv_regimen_resolver r where r.regimensys = ? limit 1", nativeQuery = true)
    Optional<String> getNDREquivalentRegimenUsingSystemRegimen(String systemRegimen);
}
