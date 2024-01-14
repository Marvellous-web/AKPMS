package com.idsargus.akpmsarservice.controller;

import com.idsargus.akpmsarservice.model.domain.ArProcessManual;
import com.idsargus.akpmsarservice.model.domain.Department;
import com.idsargus.akpmsarservice.model.domain.ProcessManual;
import com.idsargus.akpmsarservice.model.domain.ProcessManualDepartmentRelEntity;
import com.idsargus.akpmsarservice.repository.ArProcessManualRepository;
import com.idsargus.akpmsarservice.repository.ProcessManualDepartmentRel;
import com.idsargus.akpmsarservice.repository.UserDataRestRepository;
import com.idsargus.akpmscommonservice.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ArProcessManualController {

    @Autowired
    ArProcessManualRepository arProcessManualRepository;

    @Autowired
    UserDataRestRepository userDataRestRepository;


    ProcessManualDepartmentRel processManualDepartmentRel;
//    @PostMapping("/processmanual/add")
//    public void createProcessManual(@RequestBody ArProcessManual arProcessManual){
//
//        arProcessManual.setCreatedByName(3);
//
//       // UserEntity user = userDataRestRepository.findById(arProcessManual.getCreatedByName()).get();
//       ArProcessManual arProcessManual1 =  arProcessManualRepository.save(arProcessManual);
//       for(Department department : arProcessManual1.getDepartments()){
//           ProcessManualDepartmentRelEntity processManualDepartmentRel1 = new ProcessManualDepartmentRelEntity();
//           processManualDepartmentRel1.setProcessManualId(String.valueOf(arProcessManual1.getId()));
//           processManualDepartmentRel1.setDepartmentId(String.valueOf(department.getId()));
//           processManualDepartmentRel.save(processManualDepartmentRel1);
//       }
//
//    }

    @GetMapping("/processmanual/check/title")
    public ResponseEntity<?> isProcessManualTitleExistById(@RequestParam("id") String id,
                                                           @RequestParam("title") String title) {

        try{
           // List<ArProcessManual> processManuals = arProcessManualRepository.isProcessManualTitleExistById(title,id);
            List<ArProcessManual> processManualsOnlyTitle = arProcessManualRepository.isTitleExists(title);
            if(processManualsOnlyTitle.size() > 0){
                List<ArProcessManual> list =
                        processManualsOnlyTitle.stream().filter(pm->pm.getId().toString().equals(id)).collect(Collectors.toList());
                if (list.size() > 0) {
                    return ResponseEntity.ok().body("{\"Title Allowed\" : \"true\"}");
                } else {
                    return ResponseEntity.ok().body("{\"Title Allowed\" : \"false\"}");
                }
            } else{
                return ResponseEntity.ok().body("{\"Title Allowed\" : \"true\"}");
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body("");
    }

    @GetMapping("/processmanual/checktitle")
    public boolean isTitleExists(@RequestParam("title") String title){
        List<ArProcessManual> arProcessManualList = arProcessManualRepository.isTitleExists(title);
        if(arProcessManualList.size()>0){
            return true;
        } else{
            return false;
        }
    }
}
