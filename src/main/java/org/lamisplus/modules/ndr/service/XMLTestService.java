package org.lamisplus.modules.ndr.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.lamisplus.modules.base.domain.entities.OrganisationUnit;
import org.lamisplus.modules.base.service.OrganisationUnitService;
import org.lamisplus.modules.hiv.repositories.ARTClinicalRepository;
import org.lamisplus.modules.ndr.mapper.*;
import org.lamisplus.modules.ndr.schema.*;
import org.lamisplus.modules.ndr.utility.ZipUtility;
import org.lamisplus.modules.patient.domain.entity.Person;
import org.lamisplus.modules.patient.repository.PersonRepository;
import org.springframework.stereotype.Service;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class XMLTestService {

    private final MessageHeaderTypeMapper messageHeaderTypeMapper;

    private final PatientDemographicsMapper patientDemographicsMapper;

    private final AddressTypeMapper addressTypeMapper;

    private final CommonQuestionsTypeMapper commonQuestionsTypeMapper;

    private final ConditionSpecificQuestionsTypeMapper specificQuestionsTypeMapper;

    private final EncountersTypeMapper encountersTypeMapper;

    private final ConditionTypeMapper conditionTypeMapper;

    private final ARTClinicalRepository artClinicalRepository;

    private final OrganisationUnitService organisationUnitService;

    private final NDRCodeSetResolverService ndrCodeSetResolverService;

    private final PersonRepository personRepository;

    private static final String BASE_DIR = "runtime/ndr/transfer/";


    private final AtomicLong messageId = new AtomicLong (0);

    public void shouldPrintMessageHeaderTypeXml(Long id) {
        try {
            new MessageHeaderType ();
            MessageHeaderType messageHeaderType;
            JAXBContext jaxbContext = JAXBContext.newInstance (MessageHeaderType.class);
            messageHeaderType = messageHeaderTypeMapper.getMessageHeader (id);
            messageHeaderType.setMessageUniqueID (String.valueOf (id));
            messageHeaderType.setMessageStatusCode ("INITIAL");
            Marshaller jaxbMarshaller = getMarshaller (jaxbContext);
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

    public void shouldPrintPatientDemographicsTypeXml(Long id) {
        try {
            new PatientDemographicsType ();
            PatientDemographicsType patientDemographicsType;
            JAXBContext jaxbContext = JAXBContext.newInstance (PatientDemographicsType.class);
            patientDemographicsType = patientDemographicsMapper.getPatientDemographics (id);
            Marshaller jaxbMarshaller = getMarshaller (jaxbContext);
            jaxbMarshaller.setProperty ("com.sun.xml.bind.xmlHeaders", "\n<!-- This XML was generated from LAMISPlus application -->");
            jaxbMarshaller.setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty (Marshaller.JAXB_ENCODING, "UTF-8");
            String currentPath = System.getProperty ("user.dir");
            String fileName = "patient_demographics.xml";
            File file = new File (String.format ("%s/temp/%d/%s", currentPath, id, fileName));
            jaxbMarshaller.marshal (patientDemographicsType, file);
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    private Marshaller getMarshaller(JAXBContext jaxbContext) throws JAXBException {
        return jaxbContext.createMarshaller ();
    }


    public void shouldPrintPatientAddressTypeXml(Long personId) {
        try {
            new AddressType ();
            AddressType addressType;
            JAXBContext jaxbContext = JAXBContext.newInstance (AddressType.class);
            addressType = addressTypeMapper.getPatientAddress (personId);
            Marshaller jaxbMarshaller = getMarshaller (jaxbContext);
            jaxbMarshaller.setProperty ("com.sun.xml.bind.xmlHeaders", "\n<!-- This XML was generated from LAMISPlus application -->");
            jaxbMarshaller.setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty (Marshaller.JAXB_ENCODING, "UTF-8");
            String currentPath = System.getProperty ("user.dir");
            String fileName = "patient_address.xml";
            File file = new File (String.format ("%s/temp/%d/%s", currentPath, personId, fileName));
            jaxbMarshaller.marshal (addressType, file);
        } catch (Exception ignore) {
            ignore.printStackTrace ();
        }
    }

    public void shouldPrintPatientCommonQuestionsTypeXml(Long personId) {
        try {
            new AddressType ();
            CommonQuestionsType commonQuestionsType;
            JAXBContext jaxbContext = JAXBContext.newInstance (CommonQuestionsType.class);
            commonQuestionsType = commonQuestionsTypeMapper.getPatientCommonQuestion (personId);
            Marshaller jaxbMarshaller = getMarshaller (jaxbContext);
            jaxbMarshaller.setProperty ("com.sun.xml.bind.xmlHeaders", "\n<!-- This XML was generated from LAMISPlus application -->");
            jaxbMarshaller.setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty (Marshaller.JAXB_ENCODING, "UTF-8");
            String currentPath = System.getProperty ("user.dir");
            String fileName = "patient_common_question.xml";
            File file = new File (String.format ("%s/temp/%d/%s", currentPath, personId, fileName));
            jaxbMarshaller.marshal (commonQuestionsType, file);
        } catch (Exception ignore) {
            ignore.printStackTrace ();
        }
    }

    public void shouldPrintPatientConditionSpecificQuestionsTypeXml(Long personId) {
        try {
            new AddressType ();
            ConditionSpecificQuestionsType conditionSpecificQuestionsType;
            JAXBContext jaxbContext = JAXBContext.newInstance (ConditionSpecificQuestionsType.class);
            conditionSpecificQuestionsType = specificQuestionsTypeMapper.getConditionSpecificQuestionsType (personId);
            Marshaller jaxbMarshaller = getMarshaller (jaxbContext);
            jaxbMarshaller.setProperty ("com.sun.xml.bind.xmlHeaders", "\n<!-- This XML was generated from LAMISPlus application -->");
            jaxbMarshaller.setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty (Marshaller.JAXB_ENCODING, "UTF-8");
            String currentPath = System.getProperty ("user.dir");
            String fileName = "patient_specific_hiv_questions.xml";
            File file = new File (String.format ("%s/temp/%d/%s", currentPath, personId, fileName));
            jaxbMarshaller.marshal (conditionSpecificQuestionsType, file);
        } catch (Exception ignore) {
            ignore.printStackTrace ();
        }
    }

    public void shouldPrintPatientConditionEncountersTypeXml(Long personId) {
        try {
            new EncountersType ();
            EncountersType encountersType;
            JAXBContext jaxbContext = JAXBContext.newInstance (EncountersType.class);
            encountersType = encountersTypeMapper.encounterType (personId);
            Marshaller jaxbMarshaller = getMarshaller (jaxbContext);
            jaxbMarshaller.setProperty ("com.sun.xml.bind.xmlHeaders", "\n<!-- This XML was generated from LAMISPlus application -->");
            jaxbMarshaller.setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty (Marshaller.JAXB_ENCODING, "UTF-8");
            String currentPath = System.getProperty ("user.dir");
            String fileName = "patient_encounters.xml";
            File file = new File (String.format ("%s/temp/%d/%s", currentPath, personId, fileName));
            jaxbMarshaller.marshal (encountersType, file);
        } catch (Exception ignore) {
            ignore.printStackTrace ();
        }
    }

    public void shouldPrintPatientConditionTypeXml(Long personId) {
        try {
            new ConditionType ();
            ConditionType ConditionType;
            JAXBContext jaxbContext = JAXBContext.newInstance (ConditionType.class);
            ConditionType = conditionTypeMapper.getConditionType (personId);
            Marshaller jaxbMarshaller = getMarshaller (jaxbContext);
            jaxbMarshaller.setProperty ("com.sun.xml.bind.xmlHeaders", "\n<!-- This XML was generated from LAMISPlus application -->");
            jaxbMarshaller.setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty (Marshaller.JAXB_ENCODING, "UTF-8");
            String currentPath = System.getProperty ("user.dir");
            String fileName = "patient.xml";
            File file = new File (String.format ("%s/temp/%s", currentPath, fileName));
            jaxbMarshaller.marshal (ConditionType, file);
        } catch (Exception ignore) {
            ignore.printStackTrace ();
        }
    }

    public NDRStatus shouldPrintPatientContainerXml(Long personId, Long facilityId) {
        try {
            Optional<Person> optionalPerson = personRepository.findById (personId);
            long id = messageId.incrementAndGet ();
            Container container = new Container ();
            JAXBContext jaxbContext = JAXBContext.newInstance (Container.class);
            ConditionType conditionType = conditionTypeMapper.getConditionType (personId);
            IndividualReportType individualReportType = new IndividualReportType ();
            PatientDemographicsType patientDemographics = patientDemographicsMapper.getPatientDemographics (personId);
            individualReportType.setPatientDemographics (patientDemographics);
            individualReportType.getCondition ().add (conditionType);
            MessageHeaderType messageHeader = messageHeaderTypeMapper.getMessageHeader (personId);
            messageHeader.setMessageStatusCode ("INITIAL");
            messageHeader.setMessageUniqueID (Long.toString (id));
            container.setMessageHeader (messageHeader);
            container.setIndividualReport (individualReportType);
            Marshaller jaxbMarshaller = getMarshaller (jaxbContext);
            jaxbMarshaller.setProperty ("com.sun.xml.bind.xmlHeaders", "\n<!-- This XML was generated from LAMISPlus application -->");
            jaxbMarshaller.setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty (Marshaller.JAXB_ENCODING, "UTF-8");
            SchemaFactory sf = SchemaFactory.newInstance (XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema (getClass ().getClassLoader ().getResource ("NDR 1.6.2.xsd"));
            jaxbMarshaller.setSchema (schema);
            StringBuilder personUuid = new StringBuilder ();
            optionalPerson.ifPresent (person -> personUuid.append (person.getUuid ()));
            String identifier = messageHeaderTypeMapper.getTreatmentFacility (facilityId).getFacilityName () + "_" + personUuid;
            return processAndGenerateNDRFile (jaxbMarshaller, container, facilityId, identifier, id);
        } catch (Exception ignore) {
            ignore.printStackTrace ();
        }
        return null;
    }

    public void generateNDRXMLByFacility(Long facilityId) {
        List<Long> artPatientIds = artClinicalRepository.findAll ()
                .stream ()
                .filter (artClinical -> artClinical.getFacilityId ().equals (facilityId))
                .map (artClinical -> artClinical.getPerson ().getId ())
                .collect (Collectors.toList ());

        List<NDRStatus> ndrStatusList = artPatientIds.stream ().map (patientId -> shouldPrintPatientContainerXml (patientId, facilityId))
                .collect (Collectors.toList ());

        zipFiles (facilityId);
    }

    public NDRStatus processAndGenerateNDRFile(Marshaller jaxbMarshaller, Container container, Long facilityId, String identifier, Long id) throws JAXBException {
        String fileName = generateFileName (facilityId, identifier);
        File file = new File (BASE_DIR + "temp/" + facilityId + "/" + fileName);
        File dir = new File (BASE_DIR + "temp/" + facilityId);
        if (! dir.exists ()) {
            dir.mkdirs ();
        }
        jaxbMarshaller.marshal (container, file);
        return new NDRStatus (id, identifier, fileName);
    }

    private String generateFileName(Long facilityId, String identifier) {
        OrganisationUnit facility = organisationUnitService.getOrganizationUnit (facilityId);
        String stateSystem = facility.getParentParentOrganisationUnitName ();
        String lgaSystem = facility.getParentOrganisationUnitName ();
        ;
        String datim = facility.getName ();
        Optional<CodedSimpleType> stateNdr = ndrCodeSetResolverService.getNDRCodeSet ("STATES", stateSystem);
        StringBuilder state = new StringBuilder ();
        if (stateNdr.isPresent ()) {
            state.append (stateNdr.get ().getCode ());
        }
        Optional<CodedSimpleType> lgaNdr = ndrCodeSetResolverService.getNDRCodeSet ("LGA", lgaSystem);
        StringBuilder lga = new StringBuilder ();
        if (stateNdr.isPresent ()) {
            lga.append (lgaNdr.get ().getCode ());
        }
        Date date = new Date ();
        SimpleDateFormat dateFormat = new SimpleDateFormat ("ddMMyyyy");
//        String fileName = StringUtils.leftPad (state.toString (), 2, "0") +
//                StringUtils.leftPad (lga.toString (), 3, "0") +
//                "_" + datim + "_" + StringUtils.replace (identifier, "/", "-") + "_" + dateFormat.format (date) + ".xml";
//        fileName = RegExUtils.replaceAll (fileName, "/", "-");
        String fileName = facilityId + "_" + identifier + "_" + dateFormat.format (date) + ".xml";
        return fileName;
    }

    private void zipFiles(long facilityId) {
        OrganisationUnit facility = organisationUnitService.getOrganizationUnit (facilityId);
        Date date = new Date ();
        SimpleDateFormat dateFormat = new SimpleDateFormat ("ddMMyyyy");
        String fileName = facilityId + "_" + facility.getName () + "_" + dateFormat.format (date) + ".zip";
        try {
            String sourceFolder = BASE_DIR + "temp/" + facilityId + "/";
            String outputZipFile = BASE_DIR + "ndr/" + fileName;
            new File (BASE_DIR + "ndr").mkdirs ();
            new File (Paths.get (outputZipFile).toAbsolutePath ().toString ()).createNewFile ();
            List<File> files = new ArrayList<> ();
            try (Stream<Path> walk = Files.walk (Paths.get (sourceFolder))) {
                files = walk.filter (Files::isRegularFile)
                        .map (Path::toFile)
                        .collect (Collectors.toList ());
            } catch (IOException e) {
                e.printStackTrace ();
            }
            LOG.info ("Files: {}", files);

            long fifteenMB = FileUtils.ONE_MB * 15;
            ZipUtility.zip (files, Paths.get (outputZipFile).toAbsolutePath ().toString (), fifteenMB);
        } catch (Exception exception) {
            exception.printStackTrace ();
        }
    }

    @SneakyThrows
    public ByteArrayOutputStream downloadFile(String file) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream ();
        String folder = BASE_DIR + "ndr/";
        Optional<String> fileToDownload = listFilesUsingDirectoryStream (folder).stream ()
                .filter (f -> f.equals (file))
                .findFirst ();
        fileToDownload.ifPresent (s -> {
            try (InputStream is = new FileInputStream (folder + s)) {
                IOUtils.copy (is, baos);
            } catch (IOException ignored) {
            }
        });
        return baos;
    }


    private void cleanupFacility(Long facilityId) {
        String folder = BASE_DIR + "temp/" + facilityId + "/";
        try {
            if (Files.isDirectory (Paths.get (folder))) {
                FileUtils.deleteDirectory (new File (folder));
            }
        } catch (IOException ignored) {
        }
        String file = BASE_DIR + "ndr/";
        try {
            Files.list (Paths.get (BASE_DIR + "ndr/"))
                    .filter (path -> path.getFileName ().toString ().contains (file))
                    .forEach (path -> {
                        try {
                            Files.delete (path);
                        } catch (IOException e) {
                            e.printStackTrace ();
                        }
                    });
        } catch (IOException e) {
        }
    }

    private Set<String> listFilesUsingDirectoryStream(String dir) throws IOException {
        Set<String> fileList = new HashSet<> ();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream (Paths.get (dir))) {
            for (Path path : stream) {
                if (! Files.isDirectory (path)) {
                    fileList.add (path.getFileName ()
                                          .toString ());
                }
            }
        }
        return fileList;
    }

    @SneakyThrows
    public Set<String> listFiles() {
        String folder = BASE_DIR + "ndr";
        return listFilesUsingDirectoryStream (folder);
    }
}
