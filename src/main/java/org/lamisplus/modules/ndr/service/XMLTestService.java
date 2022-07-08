package org.lamisplus.modules.ndr.service;

import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.ndr.mapper.MessageHeaderTypeMapper;
import org.lamisplus.modules.ndr.schema.MessageHeaderType;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;

@Service
@RequiredArgsConstructor
public class XMLTestService {

    private final MessageHeaderTypeMapper messageHeaderTypeMapper;

    public void shouldPrintMessageHeaderTypeXml(Long id) {
        try {
            new MessageHeaderType ();
            MessageHeaderType messageHeaderType;
            JAXBContext jaxbContext = JAXBContext.newInstance (MessageHeaderType.class);
            messageHeaderType = messageHeaderTypeMapper.getMessageHeader (id);
            messageHeaderType.setMessageUniqueID (String.valueOf (id));
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller ();
            jaxbMarshaller.setProperty ("com.sun.xml.bind.xmlHeaders", "\n<!-- This XML was generated from LAMISPlus application -->");
            jaxbMarshaller.setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty (Marshaller.JAXB_ENCODING, "UTF-8");
            String currentPath = System.getProperty ("user.dir");
            String fileName = "message-header.xml";
            File file = new File (String.format ("%s/temp/%d/%s", currentPath, id, fileName));
            jaxbMarshaller.marshal (messageHeaderType, file);
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }


}
