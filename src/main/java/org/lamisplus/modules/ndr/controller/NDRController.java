package org.lamisplus.modules.ndr.controller;


import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.ndr.service.XMLTestService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

@RestController
@RequestMapping("api/ndr")
@RequiredArgsConstructor
public class NDRController {
    private  final XMLTestService xmlTestService;

//    @GetMapping("/message-header/{personId}")
//    public void generateMessageHeaderType(@PathVariable("personId") Long personId){
//        xmlTestService.shouldPrintMessageHeaderTypeXml (personId);
//    }
//
//    @GetMapping("/patient-demographic/{personId}")
//    public void generatePrintPatientDemographicsTypeXml(@PathVariable("personId") Long personId) {
//        xmlTestService.shouldPrintPatientDemographicsTypeXml (personId);
//    }
//
//    @GetMapping("/patient-address/{personId}")
//    public void generatePatientAddressTypeXml(@PathVariable("personId") Long personId){
//        xmlTestService.shouldPrintPatientAddressTypeXml (personId);
//    }
//
//    @GetMapping("/patient-common-question/{personId}")
//    public void generatePatientCommonQuestionXml(@PathVariable("personId") Long personId){
//        xmlTestService.shouldPrintPatientCommonQuestionsTypeXml (personId);
//    }
//@GetMapping("/patient-specific-question/{personId}")
//    public void generatePatientSpecificQuestionXml(@PathVariable("personId") Long personId){
//        xmlTestService.shouldPrintPatientConditionSpecificQuestionsTypeXml (personId);
//    }
//    @GetMapping("/patient-clinical_encouters/{personId}")
//    public void generatePatientClinicalEncounterXml(@PathVariable("personId") Long personId){
//        xmlTestService.shouldPrintPatientConditionEncountersTypeXml (personId);
//    }
    @GetMapping("/patient/{personId}")
    public void generatePatientXml(@PathVariable("personId") Long personId, @RequestParam("facility") Long facility){
        xmlTestService.shouldPrintPatientContainerXml (personId, facility);
    }

    @GetMapping("/{facilityId}")
    public void generateFacilityPatientXml(@PathVariable("facilityId") Long facilityId){
        xmlTestService.generateNDRXMLByFacility (facilityId);
    }


    @GetMapping("/download/{file}")
    public void downloadFile(@PathVariable String file, HttpServletResponse response) throws IOException {
        ByteArrayOutputStream baos = xmlTestService.downloadFile (file);
        response.setHeader ("Content-Type", "application/octet-stream");
        response.setHeader ("Content-Disposition", "attachment;filename=" + file + ".zip");
        response.setHeader ("Content-Length", Integer.valueOf (baos.size ()).toString ());
        OutputStream outputStream = response.getOutputStream ();
        outputStream.write (baos.toByteArray ());
        outputStream.close ();
        response.flushBuffer ();
    }

    @GetMapping("/list-files")
    public Collection<String> listFiles() {
        return xmlTestService.listFiles();
    }

}
