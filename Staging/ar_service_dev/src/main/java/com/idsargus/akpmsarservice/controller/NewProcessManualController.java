package com.idsargus.akpmsarservice.controller;

import com.idsargus.akpmsarservice.dto.ProcessManualResponseDto;
import com.idsargus.akpmsarservice.model.domain.ArProcessManual;
import com.idsargus.akpmsarservice.model.domain.Department;
import com.idsargus.akpmsarservice.model.domain.ProcessManual;
import com.idsargus.akpmsarservice.model.domain.User;
import com.idsargus.akpmsarservice.repository.ArProcessManualRepository;
import com.idsargus.akpmsarservice.repository.DepartmentRepositoryCustom;
import com.idsargus.akpmsarservice.repository.ProcessManualRepository;
import com.idsargus.akpmscommonservice.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("v1/arapi/processmanuals")
public class NewProcessManualController {

    @Autowired
    private ProcessManualRepository processManualRepository;

    @Autowired
    private ArProcessManualRepository arProcessManualRepository;
    @Autowired
    private DepartmentRepositoryCustom departmentRepositoryCustom;
    @PostMapping("/add")
    public ResponseEntity<?> addNewProcessManual(@RequestBody ProcessManual processManual,
                                                 @RequestParam(value = "position", required = false) Double position,
                                                 @RequestParam(value = "parentId", required = false) Integer parentId) {

        ProcessManual parent = null;
        if (processManual.getTitle() != null && !processManual.getTitle().trim().equals("")) {

            // if user only need to change content but does not want to change  position
            if (position != null && processManual.getId() != null && position.equals(processManualRepository.getProcessManualById(processManual.getId(),
                    true, false).getPosition())) {
                ProcessManual pm = processManualRepository.getProcessManualById(processManual.getId(),
                        true, false);
                processManual.setPosition(pm.getPosition());
                pm.setId(pm.getParent().getId());
                processManual.setParent(pm);

                UserEntity userId = pm.getCreatedBy();
                processManual.setCreatedBy(userId);
                ProcessManual updatedPM = processManualRepository.updateProcessManual(processManual);
                ProcessManualResponseDto processManualResponseDto = new ProcessManualResponseDto();
                processManualResponseDto.setTitle(updatedPM.getTitle());
                processManualResponseDto.setPosition(updatedPM.getPosition());
                processManualResponseDto.setStatus(updatedPM.isStatus());
                processManualResponseDto.setNotification(updatedPM.isNotification());
                processManualResponseDto.setContent(updatedPM.getContent());
                if (updatedPM.getModifiedBy() != null) {
                    processManualResponseDto.setModifiedBy(updatedPM.getModifiedBy().getFirstName() + "" +
                            " " + updatedPM.getModifiedBy().getLastName());
                }
                if (updatedPM.getCreatedBy() != null) {
                    processManualResponseDto.setCreatedBy(updatedPM.getCreatedBy().getFirstName() + "" +
                            " " + updatedPM.getCreatedBy().getLastName());
                }
//processManual2.setDepartmentList(processManual.getDepartments());
                processManualResponseDto.setModificationSummary(updatedPM.getModificationSummary());
                processManualResponseDto.setId(updatedPM.getId());
                return ResponseEntity.ok(processManualResponseDto);
            }


            // update if user wants to move subprocessmanual(child) after parent process manual.
            else if (parentId == null && position != null) {
                Integer parentIdFromChild = processManualRepository
                        .getProcessManualById(processManual.getId(), true, false)
                        .getParent().getId();
                parent = processManualRepository.getProcessManualById(parentIdFromChild, true, false);
                List<ProcessManual> subProcessManual = parent.getProcessManualList();
                subProcessManual = subProcessManual.stream()
                        .sorted(Comparator.comparing(ProcessManual::getPosition))
                        .collect(Collectors.toList());

                if (subProcessManual.size() > 0) {
                    ProcessManual newSubProcessManual = null;
                    if (position != null) {
                        subProcessManual = subProcessManual.stream()
                                .filter(item -> item.getPosition() > position)
                                .collect(Collectors.toList());
                        for (ProcessManual processManualPo : subProcessManual) {
                            processManualPo.setPosition(processManualPo.getPosition() + 1);
                            ProcessManual remainingPosition = processManualRepository.updateProcessManual(processManualPo);
                            //   return newSubProcessManual;
                        }
                        processManual.setPosition(2.0);
                        parent.setId(parentIdFromChild);
                        processManual.setParent(parent);
                        newSubProcessManual = processManualRepository
                                .updateProcessManual(processManual);

                        ProcessManualResponseDto processManualResponseDto = new ProcessManualResponseDto();
                        processManualResponseDto.setPosition(newSubProcessManual.getPosition());
                        processManualResponseDto.setStatus(newSubProcessManual.isStatus());

                        processManualResponseDto.setContent(newSubProcessManual.getContent());
                        if (newSubProcessManual.getModifiedBy() != null) {
                            processManualResponseDto.setModifiedBy(newSubProcessManual.getModifiedBy().getFirstName() + "" +
                                    " " + newSubProcessManual.getModifiedBy().getLastName());
                        }
//processManual2.setDepartmentList(processManual.getDepartments());
                        processManualResponseDto.setModificationSummary(newSubProcessManual.getModificationSummary());
                        processManualResponseDto.setId(newSubProcessManual.getId());
                        processManualResponseDto.setTitle(newSubProcessManual.getTitle());

                        return ResponseEntity.ok(processManualResponseDto);
                        //return newSubProcessManual;
                    }

                } else {
                    // if first subprocess manual being added by user then first subprocess manual position will be 2
                    processManual.setPosition(2.0);
                    /// added on 26.9.23 from here
                    parent.setId(parentId);
                    processManual.setParent(parent);
                    /// upto here
                    ProcessManual newSubProcessManual = processManualRepository.saveProcessManual(processManual);
                    return ResponseEntity.ok(newSubProcessManual);
                }


            }
            // update process manual parent
//            else if(processManual.getId() != null && position == 1 &&
//                    processManual.getParent().getId() == null)
            //addding from  from 16.10
            else if( position == null && parentId == null && processManual.getId() != null)
            //addding upto here  16.10
            {

                ProcessManual pmCreatedBy = processManualRepository.getProcessManualById(processManual.getId(),
                        true, false);

                UserEntity user = pmCreatedBy.getCreatedBy();
                processManual.setCreatedBy(user);
                List<Integer> selectedDept = new ArrayList<>();
                Iterable<Department> iteratorDepartment = departmentRepositoryCustom.findAll();
                List<Department> listDepartment = StreamSupport.stream(iteratorDepartment.spliterator(), false).collect(Collectors.toList());
                if (processManual.getDepartments() != null) {
                    if (processManual.getDepartments().size() > 0) {
                        ArrayList<Department> departments = new ArrayList<>();
                        for (Department depart : processManual.getDepartments()) {
                            selectedDept.add(depart.getId());
                            Department department = listDepartment.stream().filter(dept -> dept.getId().equals(depart.getId())).findFirst().get();
                            departments.add(department);
                            processManual.setDepartments(departments);
                        }
                    }
                }

                ProcessManual processManualParentUpdate = processManualRepository.updateProcessManual(processManual);

                ProcessManualResponseDto processManualResponseDto = new ProcessManualResponseDto();
                processManualResponseDto.setTitle(processManualParentUpdate.getTitle());
                processManualResponseDto.setPosition(processManualParentUpdate.getPosition());
                processManualResponseDto.setStatus(processManualParentUpdate.isStatus());
                if(processManualParentUpdate.getCreatedBy()!=null) {
                    processManualResponseDto.setCreatedBy(processManualParentUpdate.getCreatedBy().getFirstName() + " " +
                            processManualParentUpdate.getCreatedBy().getLastName());
                }
                processManualResponseDto.setContent(processManualParentUpdate.getContent());
                if (processManualParentUpdate.getModifiedBy() != null) {
                    processManualResponseDto.setModifiedBy(processManualParentUpdate.getModifiedBy().getFirstName() + "" +
                            " " + processManualParentUpdate.getModifiedBy().getLastName());
                }
//processManual2.setDepartmentList(processManual.getDepartments());
                processManualResponseDto.setDepartmentList(selectedDept);
                processManualResponseDto.setModificationSummary(processManualParentUpdate.getModificationSummary());
                processManualResponseDto.setId(processManualParentUpdate.getId());
                return ResponseEntity.ok(processManualResponseDto);
            }
            // update sub process manual position
            else if (processManual.getId() != null) {
                if (position != null) {
                    Integer pMtoUpdateId = processManual.getId();
                    parent = processManualRepository.getProcessManualById(parentId, true, false);
                    List<ProcessManual> subProcessManual = parent.getProcessManualList();

                    subProcessManual = subProcessManual.stream()
                            .sorted(Comparator.comparing(ProcessManual::getPosition))
                            .collect(Collectors.toList());

                    if (subProcessManual.size() > 0) {
                        subProcessManual = subProcessManual.stream()
                                .filter(item -> item.getPosition() > position)
                                .collect(Collectors.toList());

                        for (ProcessManual pr : subProcessManual) {
                            pr.setPosition(pr.getPosition() + 1);
                            processManualRepository.updateProcessManual(pr);

                        }

                        ProcessManual actualPMUpdatePosition = parent.getProcessManualList().stream()
                                .filter(item -> item.getId().equals(pMtoUpdateId))
                                .findFirst().get();

                        if (actualPMUpdatePosition != null) {
                            processManual.setPosition(position + 1);
                            processManual.setParent(parent);
                            ProcessManual processManual1 = processManualRepository.updateProcessManual(processManual);
                            ProcessManualResponseDto processManualResponseDto = new ProcessManualResponseDto();
                            processManualResponseDto.setPosition(processManual.getPosition());
                            processManualResponseDto.setStatus(processManual.isStatus());

                            processManualResponseDto.setContent(processManual.getContent());
                            if (processManual.getModifiedBy() != null) {
                                processManualResponseDto.setModifiedBy(processManual.getModifiedBy().getFirstName() + "" +
                                        " " + processManual.getModifiedBy().getLastName());
                            }
//processManual2.setDepartmentList(processManual.getDepartments());
                            processManualResponseDto.setModificationSummary(processManual.getModificationSummary());
                            processManualResponseDto.setId(processManual.getId());
                            processManualResponseDto.setTitle(processManual.getTitle());

                            return ResponseEntity.ok(processManualResponseDto);
                        }
                    }
                }
            }
            // for add new process manual or subprocessmanual.
            // if user adding new parent
            else if (position == null && parentId == null) {
                processManual.setPosition(1.0);
                ProcessManual processManualParent = processManualRepository.saveProcessManual(processManual);
                return ResponseEntity.ok(processManualParent);
            } else {
                // if user adding sub process manual
                parent = processManualRepository.getProcessManualById(parentId, true, false);
                // if process Manual has sub process manual then sub process manual position will be 2 and so on.
                List<ProcessManual> subProcessManual = parent.getProcessManualList();
                subProcessManual = subProcessManual.stream()
                        .sorted(Comparator.comparing(ProcessManual::getPosition))
                        .collect(Collectors.toList());

                if (subProcessManual.size() > 0) {
                    ProcessManual newSubProcessManual = null;
                    if (position != null) {
                        subProcessManual = subProcessManual.stream()
                                .filter(item -> item.getPosition() > position)
                                .collect(Collectors.toList());
                        for (ProcessManual processManualPo : subProcessManual) {
                            processManualPo.setPosition(processManualPo.getPosition() + 1);
                            ProcessManual remainingPosition = processManualRepository.updateProcessManual(processManualPo);
                            //   return newSubProcessManual;
                        }
                        processManual.setPosition(position + 1);
                        parent.setId(parentId);
                        processManual.setParent(parent);
                        newSubProcessManual = processManualRepository.saveProcessManual(processManual);
                        //return newSubProcessManual;

                        ProcessManualResponseDto processManualResponseDto = new ProcessManualResponseDto();
                        processManualResponseDto.setTitle(newSubProcessManual.getTitle());
                        processManualResponseDto.setPosition(newSubProcessManual.getPosition());
                        processManualResponseDto.setStatus(newSubProcessManual.isStatus());
                        if(newSubProcessManual.getCreatedBy()!=null) {
                            processManualResponseDto.setCreatedBy(newSubProcessManual.getCreatedBy().getFirstName() + " " +
                                    newSubProcessManual.getCreatedBy().getLastName());
                        }
                        processManualResponseDto.setContent(newSubProcessManual.getContent());
                        if (newSubProcessManual.getModifiedBy() != null) {
                            processManualResponseDto.setModifiedBy(newSubProcessManual.getModifiedBy().getFirstName() + "" +
                                    " " + newSubProcessManual.getModifiedBy().getLastName());
                        }
//processManual2.setDepartmentList(processManual.getDepartments());
                       // processManualResponseDto.setDepartmentList(selectedDept);
                        processManualResponseDto.setModificationSummary(newSubProcessManual.getModificationSummary());
                        processManualResponseDto.setId(newSubProcessManual.getId());
                        return ResponseEntity.ok(processManualResponseDto);

                    }

                } else {
                    // if first subprocess manual being added by user then first subprocess manual position will be 2
                    processManual.setPosition(2.0);
                    /// added on 26.9.23 from here
                    parent.setId(parentId);
                    processManual.setParent(parent);
                    /// upto here
                    ProcessManual newSubProcessManual = processManualRepository.saveProcessManual(processManual);
                    ProcessManualResponseDto processManualResponseDto = new ProcessManualResponseDto();
                    processManualResponseDto.setTitle(newSubProcessManual.getTitle());
                    processManualResponseDto.setPosition(newSubProcessManual.getPosition());
                    processManualResponseDto.setStatus(newSubProcessManual.isStatus());
                    if(newSubProcessManual.getCreatedBy()!=null) {
                        processManualResponseDto.setCreatedBy(newSubProcessManual.getCreatedBy().getFirstName() + " " +
                                newSubProcessManual.getCreatedBy().getLastName());
                    }
                    processManualResponseDto.setContent(newSubProcessManual.getContent());
                    if (newSubProcessManual.getModifiedBy() != null) {
                        processManualResponseDto.setModifiedBy(newSubProcessManual.getModifiedBy().getFirstName() + "" +
                                " " + newSubProcessManual.getModifiedBy().getLastName());
                    }
//processManual2.setDepartmentList(processManual.getDepartments());
                    // processManualResponseDto.setDepartmentList(selectedDept);
                    processManualResponseDto.setModificationSummary(newSubProcessManual.getModificationSummary());
                    processManualResponseDto.setId(newSubProcessManual.getId());
                    return ResponseEntity.ok(processManualResponseDto);
                }
            }

        }

        return null;

    }
}
