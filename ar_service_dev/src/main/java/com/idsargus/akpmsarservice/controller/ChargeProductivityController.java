package com.idsargus.akpmsarservice.controller;

import com.idsargus.akpmsarservice.dto.ChargeProductivityResponse;
import com.idsargus.akpmsarservice.dto.PaymentProductivityResponse;
import com.idsargus.akpmsarservice.dto.RejectLogResponse;
import com.idsargus.akpmsarservice.model.BulkChargeProdRejTicket;
import com.idsargus.akpmsarservice.model.domain.ArChargeProdReject;
import com.idsargus.akpmsarservice.repository.ChargeProdRejectionRepository;
import com.idsargus.akpmsarservice.repository.ChargeProductivityRepository;
import com.idsargus.akpmsarservice.repository.PaymentProductivityRepository;
import com.idsargus.akpmscommonservice.entity.ChargeProductivity;
import com.idsargus.akpmscommonservice.entity.PaymentProductivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(value = "v1/arapi")
public class ChargeProductivityController {

    @Autowired
    ChargeProductivityRepository chargeProductivityRepository;
    @Autowired
    PaymentProductivityRepository paymentProductivityRepository;

    @Autowired
    ChargeProdRejectionRepository chargeProdRejectionRepository;
    @PatchMapping("/bulkUpdateByTicketNumber")
    public ResponseEntity<?> bulkUpdateByTicketNumber(@RequestBody BulkChargeProdRejTicket bulkChargeProdRejTicket){

        if(bulkChargeProdRejTicket.getDateOfFirstRequestToDoctorOffice() == null){
            bulkChargeProdRejTicket.setDateOfFirstRequestToDoctorOffice("");

        }
        if(bulkChargeProdRejTicket.getCompletedById() == null){
            bulkChargeProdRejTicket.setCompletedById("");
        }
        if(bulkChargeProdRejTicket.getResolvedOn() == null){
            bulkChargeProdRejTicket.setResolvedOn("");
        }

        if(bulkChargeProdRejTicket.getCompletedOn() == null){
            bulkChargeProdRejTicket.setCompletedOn("");
        }
        if ( bulkChargeProdRejTicket.getDateOfSecondRequestToDoctorOffice() == null){
            bulkChargeProdRejTicket.setDateOfSecondRequestToDoctorOffice("");
        }
        if(bulkChargeProdRejTicket.getModifiedBy() == null){
            bulkChargeProdRejTicket.setModifiedBy("");

        }
        if(bulkChargeProdRejTicket.getModifiedOn() == null){
            bulkChargeProdRejTicket.setModifiedOn("");

        }
        if(bulkChargeProdRejTicket.getDummyCpt() == null){
           bulkChargeProdRejTicket.setDummyCpt("");
        }
        if(bulkChargeProdRejTicket.getRemarks() == null){
            bulkChargeProdRejTicket.setRemarks("");
        }
        Integer countUpdate = chargeProdRejectionRepository.bulkUpdateByTicketNumber(
                bulkChargeProdRejTicket.getChargeProRejectIds(),
                bulkChargeProdRejTicket.getRemarks(),
                bulkChargeProdRejTicket.getResolution(),
                bulkChargeProdRejTicket.getStatus(),
                bulkChargeProdRejTicket.getResolvedBy(),
                bulkChargeProdRejTicket.getModifiedBy(),
                bulkChargeProdRejTicket.getModifiedOn(),
                bulkChargeProdRejTicket.getDateOfFirstRequestToDoctorOffice(),
                bulkChargeProdRejTicket.getDateOfSecondRequestToDoctorOffice(),
                bulkChargeProdRejTicket.getDummyCpt(),
                bulkChargeProdRejTicket.getCompletedOn(),
                bulkChargeProdRejTicket.getCompletedById(),
                bulkChargeProdRejTicket.getResolvedOn());
        if(countUpdate > 0) {
            return ResponseEntity.accepted().body("{\"updated\": \"Record has been updated.\"}");
        } else{

            return ResponseEntity.accepted().body("{\"updated\": \"No record found to update.\"}");
        }
    }
    @GetMapping("/getdetailsbytiketnumber")
    public ResponseEntity<?> findByTicketNumberAndProductivityType(@RequestParam("ticketNumber") String ticketNumber,
                                                                   @RequestParam("productivityType") String productivityType){

        List<ChargeProductivity> filterList = chargeProductivityRepository.getByTicketNumberAndProductivityType(ticketNumber,productivityType);



        ChargeProductivityResponse chargeProductivityResponse = new ChargeProductivityResponse();
        if(filterList.size()>0){
            chargeProductivityResponse.setTicketNumberExists(true);
            return ResponseEntity.status(HttpStatus.OK).body(chargeProductivityResponse);
        }
        else{
            chargeProductivityResponse.setTicketNumberExists(false);
            return ResponseEntity.status(HttpStatus.OK).body(chargeProductivityResponse);
        }
    }

//    @GetMapping("/paymentProductivity/getbybatchidandproductivitytype")
//    public ResponseEntity<?> getByBatchIdAndProductivityType(@RequestParam("paymentProductivityType") Integer paymentProductivityType,
//                                                                   @RequestParam("batchId") Integer batchId){
//
//        List<PaymentProductivity> filterList = paymentProductivityRepository.getByBatchIdAndType(paymentProductivityType,batchId);
//        PaymentProductivityResponse paymentProductivityResponse = new PaymentProductivityResponse();
//        if(filterList.size()>0){
//            paymentProductivityResponse.setBachIdExists(true);
//            return ResponseEntity.status(HttpStatus.OK).body(paymentProductivityResponse);
//        }
//        else{
//            paymentProductivityResponse.setBachIdExists(false);
//            return ResponseEntity.status(HttpStatus.OK).body(paymentProductivityResponse);
//        }
//    }



// above commented  instead following added


@GetMapping("/paymentProductivity/getbybatchidandproductivitytype")

public ResponseEntity<?> getByBatchIdAndProductivityType(

        @RequestParam("batchId") Integer batchId){

    List<PaymentProductivity> filterList = paymentProductivityRepository.getByBatchIdAndType(batchId);

    PaymentProductivityResponse paymentProductivityResponse = new PaymentProductivityResponse();

    if(filterList.size()>0){

        paymentProductivityResponse.setBachIdExists(true);

        return ResponseEntity.status(HttpStatus.OK).body(paymentProductivityResponse);

    }

    else{

        paymentProductivityResponse.setBachIdExists(false);

        return ResponseEntity.status(HttpStatus.OK).body(paymentProductivityResponse);

    }

}

    @GetMapping("/reject-log/findTicketNumberByPNameAndDOB")
    public ResponseEntity<?> findTicketNumberByPNameAndDOB(@RequestParam String pName,
                                                           @RequestParam(required = false)
                                                           String reasonToReject,
                                                           @RequestParam String tNumber,
                                                           @RequestParam(required = false)  String dob,
                                                           @RequestParam(required = false) String dateOfService){
        RejectLogResponse rejectLogResponse = new RejectLogResponse();
//        if(reasonToReject == null){
//            reasonToReject = "";
//        }
        if(dob == null){

        }
        Integer numberOfRecord = chargeProdRejectionRepository.findTicketNumberByPNameAndDOB(tNumber,
                pName,dob, dateOfService);

        if(numberOfRecord > 0){
            rejectLogResponse.setBachIdExists(true);
        }else{
            rejectLogResponse.setBachIdExists(false);
        }
        return ResponseEntity.ok().body(rejectLogResponse);
    }
}
