package com.idsargus.akpmsarservice.controller;

import com.idsargus.akpmsarservice.dto.UserResponse;
import com.idsargus.akpmsarservice.model.domain.OffsetRecordEntity;
import com.idsargus.akpmsarservice.model.domain.RekeyRequestRecord;
import com.idsargus.akpmsarservice.repository.OffsetRecordRepository;
import com.idsargus.akpmsarservice.repository.RekeyRequestRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BulkRecordRequestController
{
    @Autowired
    RekeyRequestRecordRepository rekeyRequestRecordRepository;

    @Autowired
    OffsetRecordRepository offsetRecordRepository;

    @PostMapping("rekeyRequestWorkFlow/bulk/addCpt")
    public Iterable<RekeyRequestRecord> addBulkRecords(@RequestBody  List<RekeyRequestRecord> rekeyRequestRecordList){

       return  rekeyRequestRecordRepository.saveAll(rekeyRequestRecordList);

    }


    @PostMapping("offsetrecord/bulk/addCpt")
    public Iterable<OffsetRecordEntity> addBulkOffSetByCPT(@RequestBody  List<OffsetRecordEntity> offsetRecordEntitiesList){
            return offsetRecordRepository.saveAll(offsetRecordEntitiesList);

    }

    @DeleteMapping("offsetrecord/deleteCptByOffsetId")
    public ResponseEntity<?> offsetDeleteCptById(@RequestParam  Integer offsetRecordId){
        UserResponse userResponse = new UserResponse();
        try {
            offsetRecordRepository.deleteById(offsetRecordId);
            userResponse.setMsg("Record has been deleted");
            userResponse.setStatus(true);

        }catch(Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(userResponse);
    }
    @DeleteMapping("rekeyRequestWorkFlow/deleteCptById")
    public ResponseEntity<UserResponse> deleteCptById(@RequestParam  Integer rekeyId){

        UserResponse userResponse = new UserResponse();
        try {
            rekeyRequestRecordRepository.deleteById(rekeyId);
            userResponse.setMsg("Record has been deleted");
            userResponse.setStatus(true);
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(userResponse);

    }
}
