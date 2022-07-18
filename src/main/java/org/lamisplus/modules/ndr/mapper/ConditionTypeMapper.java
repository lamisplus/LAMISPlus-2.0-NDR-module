package org.lamisplus.modules.ndr.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.ndr.schema.*;
import org.lamisplus.modules.ndr.service.NDRCodeSetResolverService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConditionTypeMapper {

    private final NDRCodeSetResolverService ndrCodeSetResolverService;

    private final AddressTypeMapper addressTypeMapper;

    private final CommonQuestionsTypeMapper commonQuestionsTypeMapper;

    private final ConditionSpecificQuestionsTypeMapper specificQuestionsTypeMapper;

    private final EncountersTypeMapper encountersTypeMapper;

    private final RegimenTypeMapper regimenTypeMapper;


    public ConditionType getConditionType(Long patientId) {
        ConditionType condition = new ConditionType ();
        Optional<String> conditionCode = ndrCodeSetResolverService.getNDRCodeSetCode ("CONDITION_CODE", "HIV_CODE");
        conditionCode.ifPresent (condition::setConditionCode);
        Optional<String> programAreaCode = ndrCodeSetResolverService.getNDRCodeSetCode ("PROGRAM_AREA", "HIV");
        ProgramAreaType programArea = new ProgramAreaType ();
        programAreaCode.ifPresent (programArea::setProgramAreaCode);
        condition.setProgramArea (programArea);

        //Address
        AddressType address = addressTypeMapper.getPatientAddress (patientId);
        if (address.getStateCode () != null && address.getLGACode () != null) {
            condition.setPatientAddress (address);
        }
        CommonQuestionsType common = commonQuestionsTypeMapper.getPatientCommonQuestion (patientId);
        if (common != null) {
            condition.setCommonQuestions (common);
        }
        ConditionSpecificQuestionsType disease = specificQuestionsTypeMapper.getConditionSpecificQuestionsType (patientId);
        if (disease != null) {
            condition.setConditionSpecificQuestions (disease);
        }
        EncountersType encounter = encountersTypeMapper.encounterType (patientId);
        condition.setEncounters (encounter);
        regimenTypeMapper.regimenType (patientId, condition);
        return condition;
    }

}
