//package com.idsargus.akpmsarservice.validation;
//
//
//import com.idsargus.akpmsarservice.repository.ArProductivityRepository;
//import com.idsargus.akpmsarservice.repository.ArProductivityRepositoryTest;
//import com.idsargus.akpmsarservice.service.ArProductivityServiceImpl;
//import com.idsargus.akpmscommonservice.entity.ArProductivityEntity;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.StreamSupport;
//
//@Component("beforeCreateArProductivityValidator")
//public class ArProductivityValidator implements org.springframework.validation.Validator {
////	@Autowired
////    ValidatorEventRegister validatorEventRegister;
//
//    @Autowired
//    private ArProductivityRepository reposiotry;
//    @Autowired
//    ArProductivityServiceImpl arProductivityService;
//    public boolean supports(Class<?> clazz) {
//        return ArProductivityEntity.class.equals(clazz);
//    }
//
//    @Override
//    public void validate(Object obj, Errors errors) {
//        ArProductivityEntity user = (ArProductivityEntity) obj;
//        
////
////        Iterable<ArProductivityEntity> listArPro = reposiotry.findAll();
////        List<ArProductivityEntity> actualList = StreamSupport
////                .stream(listArPro.spliterator(), false)
////                .collect(Collectors.toList());
////
////
////        boolean isExists = (actualList.stream()
////                .filter(row -> row.getPatientAccountNumber().equals(user.getPatientAccountNumber())
////                        && row.getPatientAccountNumber().contains(user.getPatientAccountNumber())).count() == 0);
////
//        
//        //commented today
////        if (!arProductivityService.findAll(user.getPatientAccountNumber())) {
////            errors.rejectValue("patientAccountNumber", "PatientAccountNumber.AlreadyExists", "Patient Account Number should be Unique");
////        }
//
//    }
//}
