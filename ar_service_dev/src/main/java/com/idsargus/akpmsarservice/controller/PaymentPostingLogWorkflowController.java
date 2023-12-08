package com.idsargus.akpmsarservice.controller;

import com.idsargus.akpmsarservice.model.BulkChargeProdRejTicket;
import com.idsargus.akpmsarservice.model.BulkPaymentPostingLogWorkflowRequest;
import com.idsargus.akpmsarservice.model.BulkPaymentProductivityOffsetRequest;
import com.idsargus.akpmsarservice.repository.PaymentPostingLogWorkFlowRepository;
import com.idsargus.akpmsarservice.repository.PaymentProductivityOffsetRepository;
import com.idsargus.akpmscommonservice.entity.PaymentProductivityOffset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "v1/arapi")
public class PaymentPostingLogWorkflowController {

    @Autowired
    PaymentPostingLogWorkFlowRepository paymentPostingLogWorkFlowRepository;

    @Autowired
    PaymentProductivityOffsetRepository paymentProductivityOffsetRepository;
    @PatchMapping("/paymentpostinglogworkflow/bulkUpdateByIds")
    public ResponseEntity<?> bulkUpdateByTicketNumber(@RequestBody BulkPaymentPostingLogWorkflowRequest bulkPaymentPostingLogWorkflowRequest){

        if(bulkPaymentPostingLogWorkflowRequest.getInsurance() == null){
            bulkPaymentPostingLogWorkflowRequest.setInsurance(-1);

        }
        if(bulkPaymentPostingLogWorkflowRequest.getTeam() == null){
            bulkPaymentPostingLogWorkflowRequest.setTeam(-1);
        }
        if(bulkPaymentPostingLogWorkflowRequest.getDoctor() == null){
            bulkPaymentPostingLogWorkflowRequest.setDoctor(-1);
        }

        if(bulkPaymentPostingLogWorkflowRequest.getStatus() == null){
            bulkPaymentPostingLogWorkflowRequest.setStatus("");
        }
        if (bulkPaymentPostingLogWorkflowRequest.getOffSet() == null){
            bulkPaymentPostingLogWorkflowRequest.setOffSet("");
        }

        Integer countUpdate = paymentPostingLogWorkFlowRepository.bulkUpdateByTicketNumber(
                bulkPaymentPostingLogWorkflowRequest.getPplIds(),
                bulkPaymentPostingLogWorkflowRequest.getStatus(),
                bulkPaymentPostingLogWorkflowRequest.getOffSet(),
                bulkPaymentPostingLogWorkflowRequest.getDoctor(),
                bulkPaymentPostingLogWorkflowRequest.getInsurance(),
                bulkPaymentPostingLogWorkflowRequest.getTeam());
        if(countUpdate > 0) {
            return ResponseEntity.accepted().body("{\"updated\": \"Record has been updated.\"}");
        } else{

            return ResponseEntity.accepted().body("{\"updated\": \"No record found to update.\"}");
        }
    }

    @PatchMapping("/paymentpostingoffsetreference/bulkUpdateByIds")
    public ResponseEntity<?> bulkUpdatePaymentPostingOffsetReference(@RequestBody BulkPaymentProductivityOffsetRequest paymentProductivityOffsetRequest){

        if(paymentProductivityOffsetRequest.getOffsetRemark() == null){
            paymentProductivityOffsetRequest.setOffsetRemark("");

        }
        if(paymentProductivityOffsetRequest.getRemark() == null){
            paymentProductivityOffsetRequest.setRemark("");
        }

        if(paymentProductivityOffsetRequest.getStatus() == null) {
            paymentProductivityOffsetRequest.setStatus("");
        }

        Integer countUpdate = paymentProductivityOffsetRepository.bulkUpdateByIds(
                paymentProductivityOffsetRequest.getIds(),
                paymentProductivityOffsetRequest.getStatus(),
                paymentProductivityOffsetRequest.getOffsetRemark(),
                paymentProductivityOffsetRequest.getRemark());
        if(countUpdate > 0) {
            return ResponseEntity.accepted().body("{\"updated\": \"Record has been updated.\"}");
        } else{

            return ResponseEntity.accepted().body("{\"updated\": \"No record found to update.\"}");
        }
    }

}
