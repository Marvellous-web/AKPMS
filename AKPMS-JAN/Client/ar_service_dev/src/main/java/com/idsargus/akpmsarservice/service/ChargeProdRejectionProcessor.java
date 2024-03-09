//package com.idsargus.akpmsarservice.service;
//
//import com.idsargus.akpmsarservice.model.domain.ArChargeProdReject;
//import com.idsargus.akpmsarservice.model.domain.ArProductivity;
//import com.idsargus.akpmsarservice.repository.ChargeProdRejectionRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//@Configuration
//@EnableScheduling
//public class ChargeProdRejectionProcessor {
//
//    @Autowired
//    ChargeProdRejectionRepository chargeProdRejectionRepository;
//
//    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
//    public void scheduleFixedRateTask() {
//        Date test = new Date();
//
//        Iterable<ArChargeProdReject> chargeProdRejectList = chargeProdRejectionRepository.findAll();
//        for (ArChargeProdReject arChargeProdReject : chargeProdRejectList) {
//            Date obj = arChargeProdReject.getCreatedOn();
//
//            List<Date> dates = new ArrayList<>();
//
//            Calendar startDate = Calendar.getInstance();
//            startDate.setTime(obj);
//            Calendar endDate = Calendar.getInstance();
//            endDate.setTime(test);
//
//            while (startDate.getTime().before(endDate.getTime()) || startDate.getTime().equals(endDate.getTime())) {
//                Date date = startDate.getTime();
//                int dayOfWeek = startDate.get(Calendar.DAY_OF_WEEK);
//
//                if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
//                    dates.add(date);
//                }
//            }
//            int totalDays = dates.size();
////            long dateBeforeInMs = arChargeProdReject.getCreatedOn().getTime();
////            long dateAfterInMs = test.getTime();
////
////            long timeDiff = Math.abs(dateAfterInMs - dateBeforeInMs);
//
//            //long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
//            if (totalDays > 4 && totalDays < 9) {
//                arChargeProdReject.setDateOfFirstRequestToDoctorOffice(test);
//            }
//
//            if (totalDays > 14 && totalDays < 19) {
//                arChargeProdReject.setDateOfSecondRequestToDoctorOffice(test);
//            }
//            chargeProdRejectionRepository.save(arChargeProdReject);
//        }
//
//
//    }
//}
