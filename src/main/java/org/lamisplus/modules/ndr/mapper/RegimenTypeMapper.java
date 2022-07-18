package org.lamisplus.modules.ndr.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.hiv.domain.entity.ArtPharmacy;
import org.lamisplus.modules.hiv.domain.entity.Regimen;
import org.lamisplus.modules.hiv.repositories.ArtPharmacyRepository;
import org.lamisplus.modules.hiv.repositories.RegimenTypeRepository;
import org.lamisplus.modules.ndr.schema.CodedSimpleType;
import org.lamisplus.modules.ndr.schema.ConditionType;
import org.lamisplus.modules.ndr.schema.RegimenType;
import org.lamisplus.modules.ndr.service.NDRCodeSetResolverService;
import org.lamisplus.modules.ndr.utility.DateUtil;
import org.lamisplus.modules.patient.domain.entity.Person;
import org.lamisplus.modules.patient.repository.PersonRepository;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class RegimenTypeMapper {

    private final RegimenTypeRepository regimenTypeRepository;

    private final ArtPharmacyRepository artPharmacyRepository;

    private final PersonRepository personRepository;

    private final NDRCodeSetResolverService ndrCodeSetResolverService;


    public ConditionType regimenType(long patientId, ConditionType condition) {
        Optional<Person> optionalPerson = personRepository.findById (patientId);
        optionalPerson.ifPresent (person -> {
            Comparator<ArtPharmacy> artVisitDateComparator = Comparator.comparing (ArtPharmacy::getVisitDate);
            Set<ArtPharmacy> patientRegimens = artPharmacyRepository.findAll ()
                    .stream ().filter (artPharmacy -> artPharmacy.getPerson ().getUuid ().equals (person.getUuid ()))
                    .collect (Collectors.toSet ());
            LOG.info (person.getHospitalNumber () + "pharmacy visit is : {}", patientRegimens.size ());
            patientRegimens.forEach (artPharmacy -> {
                Set<Regimen> regimens = artPharmacy.getRegimens ();
                processAndSetPrescribeRegimen (artPharmacy, regimens, artVisitDateComparator, patientRegimens, condition);
            });
        });
        sortConditionRegimenType (condition);
        return condition;
    }


    private void processAndSetPrescribeRegimen(
            ArtPharmacy artPharmacy, Set<Regimen> regimens,
            Comparator<ArtPharmacy> artVisitDateComparator,
            Set<ArtPharmacy> patientRegimens,
            ConditionType conditionType
    ) {
        List<RegimenType> regimenTypeList = conditionType.getRegimen ();
        regimens.forEach (regimen -> {
            if (regimen.getRegimenType () != null) {
                RegimenType regimenType = new RegimenType ();
                processAndSetRegimenStartDate (regimenType, patientRegimens, artVisitDateComparator);
                regimenType.setVisitID (artPharmacy.getUuid ());
                processAndSetVisitDate (regimenType, artPharmacy.getVisitDate ());
                Integer refillPeriod = artPharmacy.getRefillPeriod ();
                if (refillPeriod > 180) refillPeriod = 180;
                if (refillPeriod < 1) refillPeriod = 1;
                regimenType.setPrescribedRegimenDuration (String.valueOf (refillPeriod));
                Map<Long, String> prescribedRegimenTypeMapper = prescribedRegimenTypeMapper ();
                String prescribedRegimenType = prescribedRegimenTypeMapper.get (regimen.getRegimenType ().getId ());
                regimenType.setPrescribedRegimenTypeCode (prescribedRegimenType);
                processAndSetPrescribedRegimenDispensedDate (regimenType, artPharmacy.getVisitDate ());
                String regimeLineCode = getRegimeLineCode (regimen, regimen.getId ());
                if (! regimeLineCode.isEmpty ()) {
                    Optional<String> regimenLine = ndrCodeSetResolverService.getNDRCodeSetCode ("REGIMEN_LINE", regimeLineCode);
                    regimenLine.ifPresent (regimenType::setPrescribedRegimenLineCode);
                }

                Optional<CodedSimpleType> regimenCodedSimpleType = ndrCodeSetResolverService.getRegimen (regimen.getDescription ());
                if (regimenCodedSimpleType.isPresent ()) {
                    regimenType.setPrescribedRegimen (regimenCodedSimpleType.get ());
                }
                if (prescribedRegimenType.equals ("OI")) {
                    String description = regimen.getDescription ();
                    LOG.info ("Regimen Cotrimazole: {}", description);
                    Optional<CodedSimpleType> regimenOI = ndrCodeSetResolverService.getNDRCodeSet ("OI_REGIMEN", description);
                    if (regimenOI.isPresent ()) {
                        LOG.info ("Regimen Cotrimazole Code: {}", regimenOI.get ().getCode ());
                        regimenType.setPrescribedRegimen (regimenOI.get ());
                    }
                }
                regimenTypeList.add (regimenType);
            }

        });
    }


    private void sortConditionRegimenType(ConditionType condition) {
        List<RegimenType> regimenTypeSet = new ArrayList<> (condition.getRegimen ());
        LOG.info ("regimen size: {}", regimenTypeSet.size ());
        List<RegimenType> regimenTypes = regimenTypeSet.stream ()
                .sorted (Comparator.comparing (RegimenType::getPrescribedRegimenDuration))
                .sorted ((r1, r2) -> r2.getPrescribedRegimenTypeCode ().compareTo (r1.getPrescribedRegimenTypeCode ()))
                .sorted ((r1, r2) -> r1.getVisitDate ().compare (r2.getVisitDate ()))
                .collect (Collectors.toList ());
        condition.getRegimen ().clear ();
        condition.getRegimen ().addAll (regimenTypes);
    }


    private void processAndSetVisitDate(RegimenType regimenType, LocalDate visitDate) {
        if (visitDate != null) {
            try {
                regimenType.setVisitDate (DateUtil.getXmlDate (Date.valueOf (visitDate)));
            } catch (DatatypeConfigurationException e) {
                e.printStackTrace ();
            }
        }
    }

    private void processAndSetPrescribedRegimenDispensedDate(RegimenType regimenType, LocalDate visitDate) {
        if (visitDate != null) {
            try {
                regimenType.setPrescribedRegimenDispensedDate (DateUtil.getXmlDate (Date.valueOf (visitDate)));
            } catch (DatatypeConfigurationException e) {
                e.printStackTrace ();
            }
        }
    }


    private void processAndSetRegimenStartDate(RegimenType regimenType, Set<ArtPharmacy> patientRegimens, Comparator<ArtPharmacy> artVisitDateComparator) {
        Optional<ArtPharmacy> firstRegimen = patientRegimens.stream ()
                .min (artVisitDateComparator);
        firstRegimen.ifPresent (artPharmacy -> {
            LocalDate visitDate = artPharmacy.getVisitDate ();
            try {
                regimenType.setDateRegimenStarted (DateUtil.getXmlDate (Date.valueOf (visitDate)));
            } catch (DatatypeConfigurationException e) {
                e.printStackTrace ();
            }
        });
    }


    private Map<Long, String> prescribedRegimenTypeMapper() {
        Map<Long, String> regimenTypeMapper = new HashMap<> ();
        regimenTypeRepository.findAll ().forEach (regimenType -> getRegimenTypeMapperFunction (regimenTypeMapper, regimenType));
        return regimenTypeMapper;
    }

    private void getRegimenTypeMapperFunction(Map<Long, String> regimenTypeMapper, org.lamisplus.modules.hiv.domain.entity.RegimenType regimenType) {
        String regimenTypeDescription = regimenType.getDescription ();
        if (regimenTypeDescription.contains ("ART") || regimenTypeDescription.contains ("Third Line")) {
            regimenTypeMapper.put (regimenType.getId (), "ART");
        }
        if (regimenTypeDescription.contains ("TB")) {
            regimenTypeMapper.put (regimenType.getId (), "TB");
        }

        if (regimenTypeDescription.contains ("PEP")) {
            regimenTypeMapper.put (regimenType.getId (), "PEP");
        }
        if (regimenTypeDescription.contains ("ARV")) {
            regimenTypeMapper.put (regimenType.getId (), "PMTCT");
        }
        if (regimenTypeDescription.contains ("CTX") || regimenTypeDescription.contains ("OI")) {
            regimenTypeMapper.put (regimenType.getId (), "OI");
        }
    }

    public String getRegimeLineCode(Regimen regimen, Long previousRegimenId) {
        if (regimen != null && regimen.getRegimenType () != null) {
            Long regimenId = regimen.getId ();
            if (regimen.getRegimenType ().getId () == 1 || regimen.getRegimenType ().getId () == 3) {
                //if (previousRegimenId != 0 && !(previousRegimenId .equals ( regimenId))) {
               // }
                return "First Line";
            }
            if (regimen.getRegimenType ().getId () == 2 || regimen.getRegimenType ().getId () == 4) {
                //if (previousRegimenId != 0 && previousRegimenId != regimenId) {
               // }
                return "Second Line";
            }
            if (regimen.getRegimenType ().getId () == 14) {
                return "Third Line";
            }
        }
        return "";
    }
}
