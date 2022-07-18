package org.lamisplus.modules.ndr.mapper;


import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.base.domain.entities.OrganisationUnit;
import org.lamisplus.modules.base.service.OrganisationUnitService;
import org.lamisplus.modules.ndr.schema.AddressType;
import org.lamisplus.modules.ndr.service.NDRCodeSetResolverService;
import org.lamisplus.modules.patient.domain.entity.Person;
import org.lamisplus.modules.patient.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AddressTypeMapper {
    private final PersonRepository personRepository;
    private final OrganisationUnitService organisationUnitService;
    private final NDRCodeSetResolverService ndrCodeSetResolverService;


    public AddressType getPatientAddress(Long id) {

        Optional<Person> personOptional = personRepository.findById (id);

        if (personOptional.isPresent ()) {
            AddressType address = new AddressType ();
            address.setAddressTypeCode ("H");
            address.setCountryCode ("NGA");
            JsonNode patientAddress = personOptional.get ().getAddress ();
            processAndSetPatientCurrentAddress (address, patientAddress);
            return address;
        }
        return null;
    }


    private void processAndSetPatientCurrentAddress(AddressType addressType, JsonNode stateOfOriginJson) {
        if (stateOfOriginJson.hasNonNull ("address")) {
            JsonNode address = stateOfOriginJson.get ("address");
            if (address.isArray ()) {
                JsonNode addressObject = address.get (0);
                if (addressObject.hasNonNull ("stateId") && addressObject.hasNonNull ("district")) {
                    Long stateId = addressObject.get ("stateId").asLong ();
                    Long localId = addressObject.get ("district").asLong ();
                    OrganisationUnit state = organisationUnitService.getOrganizationUnit (stateId);
                    OrganisationUnit lga = organisationUnitService.getOrganizationUnit (localId);
                    Optional<String> stateCode = ndrCodeSetResolverService.getNDRCodeSetCode ("STATES", state.getName ());
                    Optional<String> lgaCode = ndrCodeSetResolverService.getNDRCodeSetCode ("LGA", lga.getName ());
                    stateCode.ifPresent (addressType::setStateCode);
                    lgaCode.ifPresent (addressType::setLGACode);
                }
                if (addressObject.hasNonNull ("city")) {
                    addressType.setTown (addressObject.get ("city").asText ());
                }

            }
        }
    }
}
