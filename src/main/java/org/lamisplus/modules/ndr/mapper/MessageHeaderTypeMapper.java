package org.lamisplus.modules.ndr.mapper;


import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.lamisplus.modules.base.domain.entities.OrganisationUnit;
import org.lamisplus.modules.base.domain.entities.OrganisationUnitIdentifier;
import org.lamisplus.modules.base.service.OrganisationUnitService;
import org.lamisplus.modules.hiv.domain.dto.HivEnrollmentDto;
import org.lamisplus.modules.hiv.service.HivEnrollmentService;
import org.lamisplus.modules.ndr.schema.FacilityType;
import org.lamisplus.modules.ndr.schema.MessageHeaderType;
import org.lamisplus.modules.ndr.utility.DateUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MessageHeaderTypeMapper {
    private final HivEnrollmentService hivEnrollmentService;
    private final OrganisationUnitService organisationUnitService;


    public MessageHeaderType getMessageHeader(Long patientId) {
        MessageHeaderType header = new MessageHeaderType ();
        Optional<HivEnrollmentDto> hivEnrollmentOptional =
                hivEnrollmentService.getHivEnrollmentByPersonIdAndArchived (patientId);
        if (hivEnrollmentOptional.isPresent ()) {
            Long facilityId = hivEnrollmentOptional.get ().getFacilityId ();
            try {
                header.setMessageCreationDateTime (DateUtil.getXmlDateTime (new Date ()));
                header.setMessageSchemaVersion (new BigDecimal ("1.6"));
                FacilityType sendingOrganization = getTreatmentFacility (facilityId);
                header.setMessageSendingOrganization (sendingOrganization);
                return header;
            } catch (Exception exception) {
                exception.printStackTrace ();
            }
        }
        return null;
    }


    public FacilityType getTreatmentFacility(long facilityId) {
        FacilityType facility = new FacilityType ();
        facility.setFacilityTypeCode ("FAC");
        OrganisationUnit ndrFacility = organisationUnitService.getOrganizationUnit (facilityId);
        Optional<String> datimCode = getDatimCode (facilityId);
        //remove it in production
        facility.setFacilityID (ndrFacility.getName ());
        facility.setFacilityName (ndrFacility.getName ());
        datimCode.ifPresent (facility::setFacilityID);
        return facility;
    }

    @NotNull
    public Optional<String> getDatimCode(Long facilityId) {
        OrganisationUnit ndrFacility = organisationUnitService.getOrganizationUnit (facilityId);
        return ndrFacility.getOrganisationUnitIdentifiers ()
                .stream ()
                .filter (identifier -> identifier.getName ().equalsIgnoreCase ("DATIM_CODE"))
                .map (OrganisationUnitIdentifier::getCode)
                .findFirst ();
    }

}
