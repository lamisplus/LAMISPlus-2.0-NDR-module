package org.lamisplus.modules.ndr.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lamisplus.modules.base.module.ModuleService;
import org.lamisplus.modules.ndr.domain.dto.BiometricDto;
import org.lamisplus.modules.ndr.repositories.NDRCodeSetRepository;
import org.lamisplus.modules.ndr.schema.FingerPrintType;
import org.lamisplus.modules.ndr.schema.LeftHandType;
import org.lamisplus.modules.ndr.schema.RightHandType;
import org.lamisplus.modules.ndr.utility.DateUtil;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import java.sql.Date;
import java.util.Base64;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class BiometricTemplateMapper {

    private final ModuleService moduleService;

    private final NDRCodeSetRepository ndrCodeSetRepository;


    public FingerPrintType getFingerPrintTypeForPatient(String patientUuid) {
        boolean biometricModule = moduleService.exist ("biometricModule");
        LOG.info ("biometric install {}", biometricModule);
        if (biometricModule) {
            List<BiometricDto> biometrics = ndrCodeSetRepository.getPatientBiometricByPatientUuid (patientUuid);
            if (biometrics.size () > 2) {
                FingerPrintType fingerPrintType = new FingerPrintType ();
                RightHandType rightHandType = new RightHandType ();
                LeftHandType leftHandType = new LeftHandType ();
                biometrics.forEach (biometricDto -> {
                    setEnrollmentDate (biometricDto, fingerPrintType);
                    String type = biometricDto.getTemplateType ();
                    String template = Base64.getEncoder ().encodeToString (biometricDto.getTemplate ());
                    if (StringUtils.containsIgnoreCase (type, "RIGHT")) {
                        if (StringUtils.containsIgnoreCase (type, "Thumb")) {
                            rightHandType.setRightThumb (template);
                            rightHandType.setRightIndexQuality (null);
                        } else if (StringUtils.containsIgnoreCase (type, "Index")) {
                            rightHandType.setRightIndex (template);
                            rightHandType.setRightIndexQuality (null);
                        } else if (StringUtils.containsIgnoreCase (type, "Middle")) {
                            rightHandType.setRightMiddle (template);
                            rightHandType.setRightMiddleQuality (null);
                        } else if (StringUtils.containsIgnoreCase (type, "Little")) {
                            rightHandType.setRightSmall (template);
                            rightHandType.setRightSmallQuality (null);
                        } else {
                            rightHandType.setRightWedding (template);
                            rightHandType.setRightWeddingQuality (null);
                        }
                        fingerPrintType.setRightHand (rightHandType);
                    } else {
                        if (StringUtils.containsIgnoreCase (type, "Thumb")) {
                            leftHandType.setLeftThumb (template);
                            leftHandType.setLeftThumbQuality (null);
                        } else if (StringUtils.containsIgnoreCase (type, "Index")) {
                            leftHandType.setLeftIndex (template);
                            leftHandType.setLeftIndexQuality (null);
                        } else if (StringUtils.containsIgnoreCase (type, "Middle")) {
                            leftHandType.setLeftMiddle (template);
                            leftHandType.setLeftMiddleQuality (null);
                        } else if (StringUtils.containsIgnoreCase (type, "Little")) {
                            leftHandType.setLeftSmall (template);
                            leftHandType.setLeftSmallQuality (null);
                        } else {
                            leftHandType.setLeftWedding (template);
                            leftHandType.setLeftWeddingQuality (null);
                        }
                        fingerPrintType.setLeftHand (leftHandType);
                    }
                });
                return fingerPrintType;
            } else {
                return null;
            }
        }
        return null;
    }

    private void setEnrollmentDate(BiometricDto biometricDto, FingerPrintType fingerPrintType) {
        try {
            fingerPrintType.setDateCaptured (DateUtil.getXmlDate (Date.valueOf (biometricDto.getEnrollmentDate ())));
        } catch (DatatypeConfigurationException e) {
           e.printStackTrace ();
        }
    }
}
