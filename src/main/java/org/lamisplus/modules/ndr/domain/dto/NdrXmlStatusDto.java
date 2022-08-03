package org.lamisplus.modules.ndr.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class NdrXmlStatusDto implements Serializable {
    private  Integer id;
    private  Integer files;
    private  String fileName;
    private  String facility;
    private  LocalDateTime lastModified;
}
