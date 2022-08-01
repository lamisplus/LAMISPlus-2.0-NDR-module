package org.lamisplus.modules.ndr.repositories;

import org.lamisplus.modules.ndr.domain.entities.NdrMessageLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NdrMessageLogRepository extends JpaRepository<NdrMessageLog, Integer> {
}
