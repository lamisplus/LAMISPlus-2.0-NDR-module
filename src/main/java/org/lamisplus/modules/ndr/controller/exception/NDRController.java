package org.lamisplus.modules.ndr.controller.exception;


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
    @GetMapping("/{personId}")
    public void generateMessageHeaderType(@PathVariable("personId") Long personId){
        xmlTestService.shouldPrintMessageHeaderTypeXml (personId);
    }
}
