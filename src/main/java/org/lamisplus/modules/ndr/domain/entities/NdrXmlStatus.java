package org.lamisplus.modules.ndr.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ndr_xml_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NdrXmlStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "facility_id", nullable = false)
    private Long facilityId;

    @Column(name = "files", nullable = false)
    private Integer files;

    @Column(name = "file_name", nullable = false)
    private String  fileName;

    @Column(name = "last_modified", nullable = false)
    private LocalDateTime lastModified;

}
