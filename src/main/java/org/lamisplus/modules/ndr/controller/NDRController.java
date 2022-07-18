package org.lamisplus.modules.ndr.controller;


import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.ndr.service.XMLTestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/ndr")
@RequiredArgsConstructor
public class NDRController {
    private  final XMLTestService xmlTestService;
    @GetMapping("/message-header/{personId}")
    public void generateMessageHeaderType(@PathVariable("personId") Long personId){
        xmlTestService.shouldPrintMessageHeaderTypeXml (personId);
    }

    @GetMapping("/patient-demographic/{personId}")
    public void generatePrintPatientDemographicsTypeXml(@PathVariable("personId") Long personId) {
        xmlTestService.shouldPrintPatientDemographicsTypeXml (personId);
    }

    @GetMapping("/patient-address/{personId}")
    public void generatePatientAddressTypeXml(@PathVariable("personId") Long personId){
        xmlTestService.shouldPrintPatientAddressTypeXml (personId);
    }

    @GetMapping("/patient-common-question/{personId}")
    public void generatePatientCommonQuestionXml(@PathVariable("personId") Long personId){
        xmlTestService.shouldPrintPatientCommonQuestionsTypeXml (personId);
    }
@GetMapping("/patient-specific-question/{personId}")
    public void generatePatientSpecificQuestionXml(@PathVariable("personId") Long personId){
        xmlTestService.shouldPrintPatientConditionSpecificQuestionsTypeXml (personId);
    }
    @GetMapping("/patient-clinical_encouters/{personId}")
    public void generatePatientClinicalEncounterXml(@PathVariable("personId") Long personId){
        xmlTestService.shouldPrintPatientConditionEncountersTypeXml (personId);
    }
    @GetMapping("/patient/{personId}")
    public void generatePatientXml(@PathVariable("personId") Long personId){
        xmlTestService.shouldPrintPatientContainerXml (personId);
    }

}
