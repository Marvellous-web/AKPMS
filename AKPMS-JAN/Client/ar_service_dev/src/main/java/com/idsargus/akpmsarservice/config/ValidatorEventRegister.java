//package com.idsargus.akpmsarservice.config;
//
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
//import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Validator;
//
//import com.idsargus.akpmsarservice.repository.ArProductivityRepository;
//import com.idsargus.akpmsarservice.validation.ArProductivityValidator;
//import com.idsargus.akpmscommonservice.entity.ArProductivityEntity;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//import java.util.stream.StreamSupport;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//
//@Configuration
//@Component
//public class ValidatorEventRegister  implements InitializingBean{
//
//    @Autowired
//    ValidatingRepositoryEventListener validatingRepositoryEventListener;
//
//    @Autowired
//    private Map<String, Validator> validators;
//
//   // private static final ValidatorEventRegister holder = new ValidatorEventRegister();
//
////    @Bean
////    public static ValidatorEventRegister bean(ArProductivityRepository repo) {
////    	holder.reposiotry = repo;
////    	return holder;
////    }
//
//    @Autowired
//   private ArProductivityRepository reposiotry;
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//     //  Iterable<ArProductivityEntity> ch =  reposiotry.findAll();
//        List<String> events = Arrays.asList("beforeSave");
//        for (Map.Entry<String, Validator> entry : validators.entrySet()) {
//            events.stream()
//                    .filter(p -> entry.getKey().startsWith(p))
//                    .findFirst()
//                    .ifPresent(
//                            p -> validatingRepositoryEventListener
//                                    .addValidator(p, entry.getValue()));
//        }
//    }
//
//
////	@Override
////	public boolean isValid(Object value, ConstraintValidatorContext context) {
////		 Iterable<ArProductivityEntity> listArPro = holder.reposiotry.findAll();
////        List<ArProductivityEntity> actualList = StreamSupport
////                .stream(listArPro.spliterator(), false)
////                .collect(Collectors.toList());
////
////
////        boolean isExists = (actualList.stream()
////                .filter(row -> row.getPatientAccountNumber().equals(value)).count() == 0);
////        return isExists;
////	}
//}