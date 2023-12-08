package com.idsargus.akpmsarservice.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.idsargus.akpmsarservice.dto.*;
import com.idsargus.akpmsarservice.model.domain.*;
import com.idsargus.akpmsarservice.repository.*;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.idsargus.akpmsarservice.exception.AppException;
import com.idsargus.akpmsarservice.util.AkpmsUtil;
import com.idsargus.akpmsarservice.util.Constants;
import com.idsargus.akpmsarservice.util.EmailUtil;
import com.idsargus.akpmsarservice.util.ProcessManualAuditJsonData;
import com.idsargus.akpmsarservice.util.SubProcessManualJsonData;
import com.idsargus.akpmscommonservice.entity.DepartmentEntity;
import com.idsargus.akpmscommonservice.entity.EmailTemplateEntity;
import com.idsargus.akpmscommonservice.entity.ProcessManualAudit;
import com.idsargus.akpmscommonservice.entity.UserEntity;
import com.idsargus.akpmscommonservice.repository.EmailTemplateRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("v1/arapi/processmanuals")
@Slf4j
public class ProcessManualController {

    @Autowired
    private ProcessManualRepository processManualRepository;

    @Autowired
    UserDataRestRepository userDataRestRepository;
    @Autowired
    private ArProcessManualListAllRepository arProcessManualListAllRepository;
    @Autowired
    private DepartmentRepositoryCustom departmentRepositoryCustom;

    @Autowired
    ProcessManualAuditCustom processManualAuditCustom;
    @Autowired
    private ProcessManualAuditRepository processManualAuditRepository;

    /// @Autowired
    // private ProcessManualAudit processManualAudit;

    // @Autowired
    // private ProcessManualAuditJsonData processManualAuditJsonData;

    // @Autowired
    // private ProcessManualVaildator processManualVaildator;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Autowired
    private MessageSource messageSource;
    @Autowired
    ProcessManualDepartmentRel processManualDepartmentRel;

    // @Autowired
    // private EmailUtil emailUtil;/


    @GetMapping(path = "/list")
    public ResponseEntity<?> processManualList(@RequestParam(name = "kwd", required = false) String keyWord) {
        List<ProcessManual> processManualList = new ArrayList<>();


        try {
            if (null != keyWord && keyWord.trim().length() > Constants.ZERO) {
                // if (AkpmsUtil
                // .checkPermission(Constants.PERMISSION_DOCUMENT_MANAGER)) {
                // processManualList = processManualRepository.getAllProcessManuals(
                // false, true, keyWord);
                // } else {
                processManualList = processManualRepository.getAllProcessManuals(true, true, keyWord);
                // }
            } else {
                // if (AkpmsUtil
                // .checkPermission(Constants.PERMISSION_DOCUMENT_MANAGER)) {
                // processManualList = processManualRepository.getAllProcessManuals(
                // false, true);
                // } else {
                processManualList = processManualRepository.getAllProcessManuals(true, true);
                //  processManualList = processManualRepository.findAll().;
                // }
            }
        } catch (Exception e) {
            log.error(Constants.ERROR, e);
            return new ResponseEntity<AppException>(new AppException(e.getMessage(), e.getCause()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<ProcessManualResponseDto> processManualResponses = extractResult(processManualList);
        log.info("Exit processManualList");
        return new ResponseEntity<>(processManualResponses, HttpStatus.OK);
    }

    private List<ProcessManualResponseDto> extractResult(List<ProcessManual> processManualList) {
        List<ProcessManualResponseDto> processManualResponses = new ArrayList<>();
        for (ProcessManual parentManual : processManualList) {
            List<Integer> listDeptParent = new ArrayList<>();
            List<ProcessManualResponseDto> pCManualList = null;
            ProcessManualResponseDto pPManualResponseDto = new ProcessManualResponseDto();

            // set id for parent
            pPManualResponseDto.setId(parentManual.getId());
            pPManualResponseDto.setCreatedOn(parentManual.getCreatedOnName());

            if (parentManual.getCreatedByName() != null) {
                UserEntity userCreatedByName = userDataRestRepository.findByIdCustom(parentManual.getCreatedByName());
                if (userCreatedByName != null)
                    pPManualResponseDto.setCreatedBy(userCreatedByName.getFirstName() + " " + userCreatedByName.getLastName());
            } else {
                pPManualResponseDto.setCreatedBy("null");
            }
            if (parentManual.getModifiedByName() != null) {
                UserEntity userModifiedByName = userDataRestRepository.findByIdCustom(parentManual.getModifiedByName());
                if (userModifiedByName != null)
                    pPManualResponseDto.setModifiedBy(userModifiedByName.getFirstName() + " " + userModifiedByName.getLastName());
            } else {
                pPManualResponseDto.setModifiedBy("null");
            }

            for (Department dept : parentManual.getDepartments()) {

                listDeptParent.add(dept.getId());
            }

            pPManualResponseDto.setModifiedOn(parentManual.getModifiedOnDate());
            pPManualResponseDto.setModificationSummary(parentManual.getModificationSummary());
            pPManualResponseDto.setStatus(parentManual.isStatus());
            pPManualResponseDto.setTitle(parentManual.getTitle());
            pPManualResponseDto.setPosition(parentManual.getPosition());
            pPManualResponseDto.setDepartmentList(listDeptParent);

            // set createdOn for parent
            // pPManualResponseDto.setCreatedOn(parentManual.getCreatedOn());

            // set content for parent
            pPManualResponseDto.setContent(parentManual.getContent());

            // set createdBy for parent
            // pPManualResponseDto.setCreatedBy(parentManual.getCreatedBy());


            if (!CollectionUtils.isEmpty(parentManual.getProcessManualList())) {
                pCManualList = new ArrayList<>();

                for (ProcessManual childManual : parentManual.getProcessManualList()) {
                    List<Integer> listDeptChild = new ArrayList<>();
                    ProcessManualResponseDto pCManualResponseDto = new ProcessManualResponseDto();
                    // set id for child

                    pCManualResponseDto.setStatus(childManual.isStatus());
                    pCManualResponseDto.setId(childManual.getId());
                    pCManualResponseDto.setTitle(childManual.getTitle());
                    pCManualResponseDto.setPosition(childManual.getPosition());

                    for (Department dept : childManual.getDepartments()) {
                        listDeptChild.add(dept.getId());
                    }
                    pCManualResponseDto.setDepartmentList(listDeptChild);
                    // set createdOn for child
                    pCManualResponseDto.setCreatedOn(childManual.getCreatedOnName());
                    pCManualResponseDto.setModificationSummary(childManual.getModificationSummary());

                    if (childManual.getCreatedByName() != null) {
                        UserEntity puserCreatedByName = userDataRestRepository.findByIdCustom(childManual.getCreatedByName());
                        if (puserCreatedByName != null)
                            pCManualResponseDto.setCreatedBy(puserCreatedByName.getFirstName() + " " + puserCreatedByName.getLastName());
                    } else {
                        pCManualResponseDto.setCreatedBy("null");
                    }

                    if (childManual.getModifiedByName() != null) {
                        UserEntity puserModifiedByName = userDataRestRepository.findByIdCustom(childManual.getModifiedByName());
                        if (puserModifiedByName != null)
                            pCManualResponseDto.setModifiedBy(puserModifiedByName.getFirstName() + " " + puserModifiedByName.getLastName());
                    } else {
                        pCManualResponseDto.setModifiedBy("null");
                    }
                    pCManualResponseDto.setModifiedOn(childManual.getModifiedOnDate());

                    // set content for child
                    pCManualResponseDto.setContent(childManual.getContent());

                    // added from here
                    pCManualResponseDto.setPosition(childManual.getPosition());
                    // added upto here

                    pCManualList.add(pCManualResponseDto);

                }
            }
            pPManualResponseDto.setChildProcessList(pCManualList);
            processManualResponses.add(pPManualResponseDto);
        }
        return processManualResponses;
    }


    private List<ProcessManualResponseDto> parentChildRelationshipSearch(List<ProcessManual>
                                                                                 processManualList) {


        List<ProcessManualResponseDto> parentProcessManualList = new ArrayList<>();
        List<ProcessManualResponseDto> childProcessManualList = new ArrayList<>();

        for (ProcessManual processManual : processManualList) {
            ProcessManualResponseDto parentProcessManualResponseDto = new ProcessManualResponseDto();
            ProcessManualResponseDto childProcessManual = new ProcessManualResponseDto();
            if (processManual.getParent() != null) {
                ProcessManual parentInfo = processManual.getParent();
                parentProcessManualResponseDto.setId(parentInfo.getId());
                parentProcessManualResponseDto.setTitle(parentInfo.getTitle());
                parentProcessManualResponseDto.setContent(parentInfo.getContent());

                // added from here
                parentProcessManualResponseDto.setPosition(parentInfo.getPosition());
                // added upto here

                parentProcessManualResponseDto.setStatus(processManual.isStatus());
                List<Integer> parentListDeptParent = new ArrayList<>();
                for (Department dept : parentInfo.getDepartments()) {

                    parentListDeptParent.add(dept.getId());
                }
                if (processManual.getCreatedBy() != null) {
                    parentProcessManualResponseDto.setCreatedBy(processManual.getCreatedBy().getFirstName()
                            + " " + processManual.getCreatedBy().getLastName());
                }

                if (processManual.getModifiedBy() != null) {
                    parentProcessManualResponseDto.setModifiedBy(processManual.getModifiedBy().getFirstName()
                            + " " + processManual.getModifiedBy().getLastName());
                }
                parentProcessManualResponseDto.setDepartmentList(parentListDeptParent);
                parentProcessManualResponseDto.setModificationSummary(parentInfo.getModificationSummary());
                parentProcessManualResponseDto.setCreatedOn(parentInfo.getCreatedOn());


                childProcessManual.setId(processManual.getId());
                childProcessManual.setTitle(processManual.getTitle());
                childProcessManual.setContent(processManual.getContent());
                childProcessManual.setStatus(processManual.isStatus());

                // added from here
                childProcessManual.setPosition(processManual.getPosition());
                // added upto here


                List<Integer> childListDeptParent = new ArrayList<>();
                for (Department dept : processManual.getDepartments()) {

                    childListDeptParent.add(dept.getId());
                }
                childProcessManual.setDepartmentList(childListDeptParent);
                childProcessManual.setModificationSummary(processManual.getModificationSummary());
                childProcessManual.setCreatedOn(processManual.getCreatedOn());
                if (processManual.getCreatedBy() != null) {
                    childProcessManual.setCreatedBy(processManual.getCreatedBy().getFirstName()
                            + " " + processManual.getCreatedBy().getLastName());
                }

                if (processManual.getModifiedBy() != null) {
                    childProcessManual.setModifiedBy(processManual.getModifiedBy().getFirstName()
                            + " " + processManual.getModifiedBy().getLastName());
                }
                childProcessManualList.add(childProcessManual);
                parentProcessManualResponseDto.setChildProcessList(childProcessManualList);
                parentProcessManualList.add(parentProcessManualResponseDto);
            } else {

                parentProcessManualResponseDto.setId(processManual.getId());
                parentProcessManualResponseDto.setTitle(processManual.getTitle());
                parentProcessManualResponseDto.setContent(processManual.getContent());
                List<Integer> parentListDeptParent = new ArrayList<>();
                for (Department dept : processManual.getDepartments()) {

                    parentListDeptParent.add(dept.getId());
                }
                if (processManual.getCreatedBy() != null) {
                    parentProcessManualResponseDto.setCreatedBy(processManual.getCreatedBy().getFirstName()
                            + " " + processManual.getCreatedBy().getLastName());
                }

                if (processManual.getModifiedBy() != null) {
                    parentProcessManualResponseDto.setModifiedBy(processManual.getModifiedBy().getFirstName()
                            + " " + processManual.getModifiedBy().getLastName());
                }
                parentProcessManualResponseDto.setDepartmentList(parentListDeptParent);
                parentProcessManualResponseDto.setPosition(processManual.getPosition());
                parentProcessManualResponseDto.setStatus(processManual.isStatus());
                parentProcessManualResponseDto.setModificationSummary(processManual.getModificationSummary());
                parentProcessManualResponseDto.setCreatedOn(processManual.getCreatedOn());
                parentProcessManualList.add(parentProcessManualResponseDto);

            }
        }

        Map<Integer, List<ProcessManualResponseDto>> groupMap = parentProcessManualList.stream()
                .collect(Collectors.groupingBy(ProcessManualResponseDto::getId));

        List<ProcessManualResponseDto> removeDuplicate =
                groupMap.values().stream()
                        .filter(list -> list.size() > 1)
                        .flatMap(list -> list.stream()
                                .filter(parentFilter -> parentFilter.getChildProcessList() == null))
                        .collect(Collectors.toList());
        parentProcessManualList.removeAll(removeDuplicate);

        List<ProcessManualResponseDto> newList = parentProcessManualList.stream()
                .filter(forNullChild -> forNullChild.getChildProcessList() == null)
                .flatMap(forNullChild -> {
                    List<ProcessManual> processManuals = arProcessManualListAllRepository.findAllChildListByParent(forNullChild.getId());
                    List<ProcessManualResponseDto> listOfchilds = processManuals.stream()
                            .filter(Objects::nonNull)
                            .map(pr -> {
                                List<Integer> allChilds = pr.getDepartments().stream().map(Department::getId)
                                        .collect(Collectors.toList());
                                if (pr.getCreatedByName() != null) {
                                    pr.setCreatedBy(userDataRestRepository
                                            .findByIdCustom(pr.getCreatedByName()));
                                }
                                if (pr.getModifiedByName() != null)
                                    pr.setModifiedBy(userDataRestRepository
                                            .findByIdCustom(pr.getModifiedByName()));
                                ProcessManualResponseDto processManualResponseDtoChilds = new ProcessManualResponseDto();
                                processManualResponseDtoChilds.setDepartmentList(allChilds);
                                processManualResponseDtoChilds.setId(pr.getId());
                                processManualResponseDtoChilds.setStatus(pr.isStatus());
                                List<Integer> childListDeptParent = new ArrayList<>();
                                for (Department dept : pr.getDepartments()) {

                                    childListDeptParent.add(dept.getId());
                                }
                                processManualResponseDtoChilds.setDepartmentList(childListDeptParent);
                                processManualResponseDtoChilds.setPosition(pr.getPosition());
                                processManualResponseDtoChilds.setContent(pr.getContent());
                                processManualResponseDtoChilds.setTitle(pr.getTitle());
                                processManualResponseDtoChilds.setModificationSummary(pr.getModificationSummary());
                                processManualResponseDtoChilds.setCreatedOn(pr.getCreatedOn());
                                if (pr.getCreatedBy() != null) {
                                    processManualResponseDtoChilds.setCreatedBy(pr.getCreatedBy().getFirstName()
                                            + " " + pr.getCreatedBy().getLastName());
                                }

                                if (pr.getModifiedBy() != null) {
                                    processManualResponseDtoChilds.setModifiedBy(pr.getModifiedBy().getFirstName()
                                            + " " + pr.getModifiedBy().getLastName());
                                }
                                return processManualResponseDtoChilds;
                            }).collect(Collectors.toList());
                    forNullChild.setChildProcessList(listOfchilds);
                    return Stream.of(forNullChild);
                })
                .collect(Collectors.toList());


        //parentProcessManualList.addAll(newList);

        if (newList.size() == 0) {
            return parentProcessManualList;
        } else {
            return newList;
        }
    }


    public List<ProcessManualResponseDto>  parentChildById(List<ProcessManual>
                                                                   processManualList) {


        List<ProcessManualResponseDto> parentProcessManualList = new ArrayList<>();
        List<ProcessManualResponseDto> childProcessManualList = new ArrayList<>();

        for (ProcessManual processManual : processManualList) {
            ProcessManualResponseDto parentProcessManualResponseDto = new ProcessManualResponseDto();

            if (processManual.getParent() != null) {

                    ProcessManual parentInfo = processManual.getParent();
                    parentProcessManualResponseDto.setId(parentInfo.getId());
                    parentProcessManualResponseDto.setTitle(parentInfo.getTitle());
                    parentProcessManualResponseDto.setContent(parentInfo.getContent());
                    parentProcessManualResponseDto.setNotification(parentInfo.isNotification());
                // added from here
                parentProcessManualResponseDto.setPosition(parentInfo.getPosition());
                // added upto here

                    parentProcessManualResponseDto.setStatus(processManual.isStatus());
                    List<Integer> parentListDeptParent = new ArrayList<>();
                    for (Department dept : parentInfo.getDepartments()) {

                        parentListDeptParent.add(dept.getId());
                    }
                    if (processManual.getCreatedBy() != null) {
                        parentProcessManualResponseDto.setCreatedBy(processManual.getCreatedBy().getFirstName()
                                + " " + processManual.getCreatedBy().getLastName());
                    }

                    if (processManual.getModifiedBy() != null) {
                        parentProcessManualResponseDto.setModifiedBy(processManual.getModifiedBy().getFirstName()
                                + " " + processManual.getModifiedBy().getLastName());
                    }
                    parentProcessManualResponseDto.setDepartmentList(parentListDeptParent);
                    parentProcessManualResponseDto.setModificationSummary(parentInfo.getModificationSummary());
                    parentProcessManualResponseDto.setCreatedOn(parentInfo.getCreatedOn());


                   for(ProcessManual processManualChild : processManual.getProcessManualList()) {
                       ProcessManualResponseDto childProcessManual = new ProcessManualResponseDto();
                       childProcessManual.setId(processManualChild.getId());
                       childProcessManual.setTitle(processManualChild.getTitle());
                       childProcessManual.setContent(processManualChild.getContent());
                       childProcessManual.setNotification(processManualChild.isNotification());
                       // added from here
                       childProcessManual.setPosition(processManualChild.getPosition());
                       // added upto here

                       childProcessManual.setStatus(processManualChild.isStatus());
                       List<Integer> childListDeptParent = new ArrayList<>();
                       for (Department dept : processManualChild.getDepartments()) {

                           childListDeptParent.add(dept.getId());
                       }
                       if (processManualChild.getCreatedByName() != null) {
                           UserEntity userEntity = userDataRestRepository
                                   .findByIdCustom(processManualChild.getCreatedByName());
                           processManual.setCreatedBy(userEntity);
                       } else {
                           processManual.setCreatedBy(null);
                       }

                       if (processManualChild.getCreatedByName() != null) {
                           UserEntity userEntity = userDataRestRepository
                                   .findByIdCustom(processManualChild.getCreatedByName());
                           processManualChild.setCreatedBy(userEntity);
                       } else {
                           processManualChild.setCreatedBy(null);
                       }
                       if (processManualChild.getModifiedByName() != null) {
                           UserEntity userEntity = userDataRestRepository
                                   .findByIdCustom(processManualChild.getModifiedByName());
                           processManualChild.setModifiedBy(userEntity);
                       } else {
                           processManualChild.setModifiedBy(null);
                       }
                       childProcessManual.setDepartmentList(childListDeptParent);
                       childProcessManual.setModificationSummary(processManualChild.getModificationSummary());
                       childProcessManual.setCreatedOn(processManualChild.getCreatedOn());
                       if (processManualChild.getCreatedBy() != null) {
                           childProcessManual.setCreatedBy(processManualChild.getCreatedBy().getFirstName()
                                   + " " + processManualChild.getCreatedBy().getLastName());
                       }

                       if (processManualChild.getModifiedBy() != null) {
                           childProcessManual.setModifiedBy(processManualChild.getModifiedBy().getFirstName()
                                   + " " + processManualChild.getModifiedBy().getLastName());
                       }
                       childProcessManualList.add(childProcessManual);

                   }
                parentProcessManualResponseDto.setChildProcessList(childProcessManualList);
                parentProcessManualList.add(parentProcessManualResponseDto);
                }
             else {

                parentProcessManualResponseDto.setId(processManual.getId());
                parentProcessManualResponseDto.setTitle(processManual.getTitle());
                parentProcessManualResponseDto.setContent(processManual.getContent());
                parentProcessManualResponseDto.setNotification(processManual.isNotification());


                // added from here
                parentProcessManualResponseDto.setPosition(processManual.getPosition());
                // added upto here


                List<Integer> parentListDeptParent = new ArrayList<>();
                for (Department dept : processManual.getDepartments()) {

                    parentListDeptParent.add(dept.getId());
                }
                if (processManual.getCreatedBy() != null) {
                    parentProcessManualResponseDto.setCreatedBy(processManual.getCreatedBy().getFirstName()
                            + " " + processManual.getCreatedBy().getLastName());
                }

                if (processManual.getModifiedBy() != null) {
                    parentProcessManualResponseDto.setModifiedBy(processManual.getModifiedBy().getFirstName()
                            + " " + processManual.getModifiedBy().getLastName());
                }
                parentProcessManualResponseDto.setDepartmentList(parentListDeptParent);
                parentProcessManualResponseDto.setPosition(processManual.getPosition());

                parentProcessManualResponseDto.setStatus(processManual.isStatus());
                parentProcessManualResponseDto.setModificationSummary(processManual.getModificationSummary());
                parentProcessManualResponseDto.setCreatedOn(processManual.getCreatedOn());
                parentProcessManualList.add(parentProcessManualResponseDto);

            }
        }

        Map<Integer, List<ProcessManualResponseDto>> groupMap = parentProcessManualList.stream()
                .collect(Collectors.groupingBy(ProcessManualResponseDto::getId));

        List<ProcessManualResponseDto> removeDuplicate =
                groupMap.values().stream()
                        .filter(list -> list.size() > 1)
                        .flatMap(list -> list.stream()
                                .filter(parentFilter -> parentFilter.getChildProcessList() == null))
                        .collect(Collectors.toList());
        parentProcessManualList.removeAll(removeDuplicate);

        List<ProcessManualResponseDto> newList = parentProcessManualList.stream()
                .filter(forNullChild -> forNullChild.getChildProcessList() == null)
                .flatMap(forNullChild -> {
                    List<ProcessManual> processManuals = arProcessManualListAllRepository.findAllChildListByParent(forNullChild.getId());
                    List<ProcessManualResponseDto> listOfchilds = processManuals.stream()
                            .filter(Objects::nonNull)
                            .map(pr -> {
                                List<Integer> allChilds = pr.getDepartments().stream().map(Department::getId)
                                        .collect(Collectors.toList());
                                if (pr.getCreatedByName() != null) {
                                    pr.setCreatedBy(userDataRestRepository
                                            .findByIdCustom(pr.getCreatedByName()));
                                }
                                if (pr.getModifiedByName() != null)
                                    pr.setModifiedBy(userDataRestRepository
                                            .findByIdCustom(pr.getModifiedByName()));
                                ProcessManualResponseDto processManualResponseDtoChilds = new ProcessManualResponseDto();
                                processManualResponseDtoChilds.setDepartmentList(allChilds);
                                processManualResponseDtoChilds.setId(pr.getId());
                                processManualResponseDtoChilds.setStatus(pr.isStatus());
                                List<Integer> childListDeptParent = new ArrayList<>();
                                for (Department dept : pr.getDepartments()) {

                                    childListDeptParent.add(dept.getId());
                                }
                                processManualResponseDtoChilds.setDepartmentList(childListDeptParent);
                                processManualResponseDtoChilds.setPosition(pr.getPosition());
                                processManualResponseDtoChilds.setContent(pr.getContent());
                                processManualResponseDtoChilds.setTitle(pr.getTitle());
                                processManualResponseDtoChilds.setNotification(pr.isNotification());
                                processManualResponseDtoChilds.setModificationSummary(pr.getModificationSummary());
                                processManualResponseDtoChilds.setCreatedOn(pr.getCreatedOn());
                                if (pr.getCreatedBy() != null) {
                                    processManualResponseDtoChilds.setCreatedBy(pr.getCreatedBy().getFirstName()
                                            + " " + pr.getCreatedBy().getLastName());
                                }

                                if (pr.getModifiedBy() != null) {
                                    processManualResponseDtoChilds.setModifiedBy(pr.getModifiedBy().getFirstName()
                                            + " " + pr.getModifiedBy().getLastName());
                                }
                                return processManualResponseDtoChilds;
                            }).collect(Collectors.toList());
                    forNullChild.setChildProcessList(listOfchilds);
                    return Stream.of(forNullChild);
                })
                .collect(Collectors.toList());


        //parentProcessManualList.addAll(newList);

        if (newList.size() == 0) {
            return parentProcessManualList;
        } else {
            return newList;
        }
    }
    @GetMapping(path = "/page/list")
    public ResponseEntity<?> processManualAllList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(name = "id", required = false) Integer processManualId,
            @RequestParam(name = "title", required = false) String title
    ) {

        try {

            if (processManualId != null && processManualId != 0) {
                List<ProcessManual> processManualList =
                        arProcessManualListAllRepository.findByIdIsDeleted(processManualId);

                for (ProcessManual processManual : processManualList) {
                    if(processManual.getParent() == null) {
                        List<ProcessManual> subProcessManualList = arProcessManualListAllRepository
                                .getChildProcessManualList(processManual.getId());
                        processManual.setProcessManualList(subProcessManualList);
                        processManual.setParent(processManual.getParent());

                        // added from here
                        processManual.setPosition(processManual.getPosition());
                        // added upto here
                        if (processManual.getCreatedByName() != null) {
                            UserEntity userEntity = userDataRestRepository
                                    .findByIdCustom(processManual.getCreatedByName());
                            processManual.setCreatedBy(userEntity);
                        } else {
                            processManual.setCreatedBy(null);
                        }
                        if (processManual.getModifiedByName() != null) {
                            UserEntity userEntity = userDataRestRepository
                                    .findByIdCustom(processManual.getModifiedByName());
                            processManual.setModifiedBy(userEntity);
                        } else {
                            processManual.setModifiedBy(null);
                        }

                    } else{
                        List<ProcessManual> subProcessManualList = arProcessManualListAllRepository
                                .getChildProcessManualList(processManual.getParent().getId());
                        processManual.setProcessManualList(subProcessManualList);
                        processManual.setParent(processManual.getParent());
                        // added from here
                        processManual.setPosition(processManual.getPosition());
                        // added upto here
                        if (processManual.getCreatedByName() != null) {
                            UserEntity userEntity = userDataRestRepository
                                    .findByIdCustom(processManual.getCreatedByName());
                            processManual.setCreatedBy(userEntity);
                        } else {
                            processManual.setCreatedBy(null);
                        }
                        if (processManual.getModifiedByName() != null) {
                            UserEntity userEntity = userDataRestRepository
                                    .findByIdCustom(processManual.getModifiedByName());
                            processManual.setModifiedBy(userEntity);
                        } else {
                            processManual.setModifiedBy(null);
                        }
                    }
                }
                List<ProcessManualResponseDto> processManualResponses = parentChildById(processManualList);
                Pageable pageable = PageRequest.of(page, processManualResponses.size());
                PaginatedResponse<ProcessManualResponseDto> pagebleResult
                        = convertListToPage(processManualResponses, pageable, processManualResponses.size());
                return ResponseEntity.ok(pagebleResult);
            }
            if (title != null && !title.trim().isEmpty() && !title.equals("")) {
                List<ProcessManual> processManualList =
                        arProcessManualListAllRepository.findProcessManualBySearchTitle(title);
                for (ProcessManual processManual : processManualList) {
                    List<ProcessManual> subProcessManualList = arProcessManualListAllRepository
                            .getProcessManualIsDeletedNullByProcessManual(processManual.getId(), title);
                    processManual.setProcessManualList(subProcessManualList);
                    processManual.setParent(processManual.getParent());
                    if (processManual.getCreatedByName() != null) {
                        UserEntity userEntity = userDataRestRepository
                                .findByIdCustom(processManual.getCreatedByName());
                        processManual.setCreatedBy(userEntity);
                    } else {
                        processManual.setCreatedBy(null);
                    }
                    if (processManual.getModifiedByName() != null) {
                        UserEntity userEntity = userDataRestRepository
                                .findByIdCustom(processManual.getModifiedByName());
                        processManual.setModifiedBy(userEntity);
                    } else {
                        processManual.setModifiedBy(null);
                    }
                }
                List<ProcessManualResponseDto> processManualResponses = parentChildRelationshipSearch(processManualList);
                // checking response is coming as per user requested
                if(processManualResponses.size() > 0){
                Pageable pageable = PageRequest.of(page, processManualResponses.size());
                PaginatedResponse<ProcessManualResponseDto> pagebleResult
                        = convertListToPage(processManualResponses, pageable, processManualResponses.size());
                return ResponseEntity.ok(pagebleResult);
                } else {
                    UserResponse userResponse = new UserResponse();
                    userResponse.setMsg("Title not found.");
                    userResponse.setStatus(false);
                    return ResponseEntity.ok(userResponse);
                }
            } else {
                Pageable pageable = PageRequest.of(page, size);
                List<ProcessManual> processManualList = findProcessManualOnPage(pageable);
                PaginatedResponse<ProcessManualResponseDto> PagebleResult
                        = convertListToPage(extractResult(processManualList), pageable,
                        arProcessManualListAllRepository.getTotalElements());
                return ResponseEntity.ok(PagebleResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public PaginatedResponse<ProcessManualResponseDto> convertListToPage(
            List<ProcessManualResponseDto> list,
            Pageable pageable,
            int searchTotalElements) {
        int number = pageable.getPageNumber();
        int totalElements = searchTotalElements;
        int pageSize = pageable.getPageSize();
        int totalPage = (int) Math.ceil((double) totalElements / pageSize);
        PaginationInfo paginationInfo = new PaginationInfo(number, totalElements, totalPage, pageSize);
        List<ProcessManualResponseDto> pagedProcess;

        int startIndex = pageable.getPageNumber() * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalElements);
        //   pagedProcess = list.subList(number, endIndex);

        PaginatedResponse<ProcessManualResponseDto> paginatedResponse =
                new PaginatedResponse<>(list, paginationInfo);
        return paginatedResponse;
    }

    // customized code for Pagination on existing functionality
    private List<ProcessManual> findProcessManualOnPage(Pageable pageable) throws AppException {
        List<ProcessManual> processManualList = new ArrayList<>();
        if (pageable != null) {
            processManualList = arProcessManualListAllRepository.getProcessManualIsDeletedNull(pageable).getContent();

        } else {
            processManualList = arProcessManualListAllRepository.getProcessManualIsDeletedNullWithoutPagination();
        }
        if (processManualList != null) {
            for (ProcessManual processManual : processManualList) {
                List<ProcessManual> subProcessManualList = arProcessManualListAllRepository
                        .getProcessManualIsDeletedNullByProcessManualParentId(processManual.getId(), null).getContent();
                processManual.setProcessManualList(subProcessManualList);
            }
        }
        return processManualList;
    }

    //add name /page/list
//    @GetMapping("/page/list/search")
//    public ResponseEntity<?> findByTile(@Param("title") String title) {
//        List<ProcessManual> processManualList =
//                arProcessManualListAllRepository.findProcessManualBySearchTitle(title);
//        if (processManualList != null) {
//            for (ProcessManual processManual : processManualList) {
//                // if(processManual.getId() )
//                List<ProcessManual> subProcessManualList = arProcessManualListAllRepository
//                        .getProcessManualIsDeletedNullByProcessManual(processManual.getId(), title);
//                processManual.setProcessManualList(subProcessManualList);
//                processManual.setParent(processManual.getParent());
//            }
//        }
//
//        List<ProcessManualResponseDto> processManualResponses =
//                parentChildRelationshipSearch(processManualList);
//        return new ResponseEntity<>(processManualResponses, HttpStatus.OK);
//        //   return new ResponseEntity<>(processManualList, HttpStatus.OK);
//
//    }

    // fpllowing Post API /addobsolete  not tobe used in future
    @PostMapping(value = "/addold", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<?> processProcessManual(@RequestParam(value = "position", required = false) Double position,
                                                  @RequestParam(value = "parentId", required = false)
                                                  Integer parentId,
                                                  @RequestBody ProcessManual processManual) {
        if (processManual.getTitle() != null && !processManual.getTitle().trim().equals("")) {
            boolean isNew = true;
            Double positionDouble = 0.0;
            if (position != null) {
                positionDouble = position;
            }

            ProcessManual parent = null;
            if (parentId != null && parentId != 0) {
                try {
                    // Fetching parent/child record based on parentId
                    parent = this.processManualRepository.getProcessManualById(parentId, true, false);
                    // positionDouble = parentId+positionDouble;
                    int nextIndex = 0, currentIndex = 0;
                    // fetched child of processmanual which is sublist
                    List<ProcessManual> subList = parent.getProcessManualList();

                    //loop to check which index that position exists
                    for (int i = 0; i <= subList.size() - 1; i++) {
                        if (subList.get(i).getPosition().equals(positionDouble)) {
                            currentIndex = i;
                            nextIndex = i + 1;
                            break;
                        }
                    }
                    ProcessManual currentSubProcessManual = null;
                    ProcessManual nextSubProcessManual = null;
                    double newPossitionSubProcessMaual = 0.0;

                    if (parent.getPosition().equals(1.0) && position.equals(1.0)) {
                        if (subList.size() > 0) {
                            newPossitionSubProcessMaual = (parent.getPosition() + subList.get(0).getPosition()) / 2;
                        } else {
                            newPossitionSubProcessMaual = (0.1 + parent.getPosition()) / 2;
                            //  newPossitionSubProcessMaual = (1 + subList.get(0).getPosition()) / 2;
                            // newPossitionSubProcessMaual = (0.1 + parent.getPosition()) ;
                        }
                    } else {
                        if (nextIndex < subList.size() - 1) {
                            currentSubProcessManual = subList.get(currentIndex);
                            nextSubProcessManual = subList.get(nextIndex);
                            newPossitionSubProcessMaual = (currentSubProcessManual.getPosition() + nextSubProcessManual.getPosition()) / 2;
                        } else {
                            BigDecimal b1 = BigDecimal.valueOf(positionDouble);
                            BigDecimal b2 = BigDecimal.valueOf(0.1);
                            BigDecimal d = b1.add(b2);
                            newPossitionSubProcessMaual = d.doubleValue();
                        }
                    }
//                    if (null == processManual.getId()) {
//                        BigDecimal b1 = BigDecimal.valueOf(positionDouble);
//                        BigDecimal b2 = BigDecimal.valueOf(0.1);
//                        BigDecimal d = b1.add(b2);
//                        processManual.setPosition(d.doubleValue());
//                    } else {

//                        BigDecimal b1 = BigDecimal.valueOf(positionDouble);
//                        BigDecimal b2 = BigDecimal.valueOf(0.1);
//                        BigDecimal d = b1.add(b2);
                    processManual.setPosition(newPossitionSubProcessMaual);
                    //    this.processManualRepository.updateProcessManualsPositons(positionDouble, parentId);
                    // processManual.setPosition(positionDouble);
                    // }
                } catch (Exception var15) {
                    log.error("Exception : ", var15);
                    return new ResponseEntity(new AppException(var15.getMessage(), var15.getCause()),
                            HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {

                // added for setting parent position on 9/13
                processManual.setPosition(1.0);
            }
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
//processManual.setDepartments(parent.getDepartments());
            processManual.setParent(parent);
            processManual.setTitle(StringEscapeUtils.escapeHtml3(processManual.getTitle()));

            if (null != processManual.getId()) {
                try {
                    if (!processManual.isStatus()) {
                        this.processManualRepository.changeStatus(processManual.getId(), false);
                    }

                    if (processManual.getModificationSummary() != null) {
                        processManual.setModificationSummary(
                                StringEscapeUtils.escapeHtml3(processManual.getModificationSummary()));
                    }

                    if(processManual.getModifiedBy() != null){
                        UserEntity userEntity = userDataRestRepository.findById(processManual.getModifiedBy().getId()).get();
                        UserEntity user = new UserEntity();
                        user.setId(userEntity.getId());
                        user.setAddress(userEntity.getAddress());
                        user.setLastName(userEntity.getLastName());
                        user.setFirstName(userEntity.getFirstName());
                                user.setContact(userEntity.getContact());

                        processManual.setModifiedBy(user);
                    }

                    processManual = this.processManualRepository.updateProcessManual(processManual);
                    isNew = false;
                } catch (Exception var14) {
                    log.error("Exception : ", var14);
                    return new ResponseEntity(new AppException(var14.getMessage(), var14.getCause()),
                            HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                try {
                    processManual = this.processManualRepository.saveProcessManual(processManual);
                    if (processManual.getDepartments() != null) {
                        for (Department department : processManual.getDepartments()) {
                            ProcessManualDepartmentRelEntity processManualDepartmentRel1 = new ProcessManualDepartmentRelEntity();
                            processManualDepartmentRel1.setProcessManualId(String.valueOf(processManual.getId()));
                            processManualDepartmentRel1.setDepartmentId(String.valueOf(department.getId()));
                            processManualDepartmentRel.save(processManualDepartmentRel1);
                        }
                    }
                    List<ProcessManual> subProcessManualList = new ArrayList<>();
                    Set<Double> nonDuplicate = new HashSet();
                    if (parent != null) {
                        subProcessManualList = parent.getProcessManualList();
                        nonDuplicate.add(processManual.getPosition());
                        for (int i = 0; i < subProcessManualList.size(); ++i) {
                            double valPosition = ((ProcessManual) subProcessManualList.get(i)).getPosition();
                            if (nonDuplicate.contains(valPosition)) {
                                do {
                                    BigDecimal b1 = BigDecimal.valueOf(valPosition);
                                    BigDecimal b2 = BigDecimal.valueOf(0.1);
                                    valPosition = b1.add(b2).doubleValue();
                                } while (nonDuplicate.contains(valPosition));

                                nonDuplicate.add(valPosition);
                                this.processManualRepository.updatePositionById(valPosition,
                                        ((ProcessManual) subProcessManualList.get(i)).getId());
                            }
                        }
                    }

                } catch (Exception var17) {
                    log.info("ERROR: " + var17.getMessage());
                    return new ResponseEntity(new AppException(var17.getMessage(), var17.getCause()),
                            HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

//            try {
//                if (processManual.isNotification()) {
//                    Optional<EmailTemplateEntity> processManualMailTemplate = this.emailTemplateRepository
//                            .findById(Constants.PROCESS_MANUAL_MODIFICATION_MAIL_TEMPLATE);
//                    if (processManualMailTemplate.isPresent()) {
//                        EmailTemplateEntity emailTemplateEntity = (EmailTemplateEntity) processManualMailTemplate.get();
//                        if (emailTemplateEntity.getStatus()) {
//                            List<UserEntity> evaluationSubscriberUsers = emailTemplateEntity.getUsers();
//                            if (evaluationSubscriberUsers != null) {
//                                List<String> to = new ArrayList();
//                                Iterator 4var11 = evaluationSubscriberUsers.iterator();
//
//                                while (var11.hasNext()) {
//                                    UserEntity userToMail = (UserEntity) var11.next();
//                                    if (userToMail.getEnabled() && !userToMail.getDeleted()) {
//                                        to.add(userToMail.getEmail());
//                                    }
//                                }
//
//                                log.info("Users count who will get mail:" + to.isEmpty());
//                                if (!to.isEmpty()) {
//                                    String emailTemplateContent = emailTemplateEntity.getContent();
//                                    String subject;
//                                    if (processManual.getParent() != null) {
//                                        subject = processManual.getParent().getTitle();
//                                        emailTemplateContent = emailTemplateContent.replace("PROCESS_MANUAL",
//                                                subject + " > " + processManual.getTitle());
//                                    } else {
//                                        emailTemplateContent = emailTemplateContent.replace("PROCESS_MANUAL",
//                                                processManual.getTitle());
//                                    }
//
//                                    subject = emailTemplateEntity.getName();
//                                    if (isNew) {
//                                        emailTemplateContent = emailTemplateContent.replace("ADDED_MODIFIED", "added");
//                                    } else {
//                                        emailTemplateContent = emailTemplateContent.replace("ADDED_MODIFIED",
//                                                "modified");
//                                    }
//
//                                    EmailUtil emailUtil = new EmailUtil();
//                                    emailUtil.setSubject(subject);
//                                    emailUtil.setContent(emailTemplateContent);
//                                    emailUtil.setType("html");
//                                    emailUtil.setTo(to);
//                                    emailUtil.sendEmail();
//                                } else {
//                                    log.info("There is no recipient available");
//                                }
//                            }
//                        }
//                    }
//                }
//            } catch (Exception var16) {
//                log.error("Exception : ", var16);
//                return new ResponseEntity(new AppException(var16.getMessage(), var16.getCause()),
//                        HttpStatus.INTERNAL_SERVER_ERROR);
//            }

            List<ProcessManualResponseDto> pCManualList = null;
            ProcessManualResponseDto pPManualResponseDto = new ProcessManualResponseDto();
            pPManualResponseDto.setId(processManual.getId());
            pPManualResponseDto.setTitle(processManual.getTitle());
            pPManualResponseDto.setPosition(processManual.getPosition());
            pPManualResponseDto.setContent(processManual.getContent());
            pPManualResponseDto.setStatus(processManual.isStatus());
            pPManualResponseDto.setCreatedOn(processManual.getCreatedOn());
            pPManualResponseDto.setModifiedOn(processManual.getModifiedOn());
            pPManualResponseDto.setModificationSummary(processManual.getModificationSummary());
            pPManualResponseDto.setDepartmentList(selectedDept);
            if (!CollectionUtils.isEmpty(processManual.getProcessManualList())) {
                pCManualList = new ArrayList();
                Iterator var26 = processManual.getProcessManualList().iterator();

                while (var26.hasNext()) {
                    ProcessManual childManual = (ProcessManual) var26.next();
                    ProcessManualResponseDto pCManualResponseDto = new ProcessManualResponseDto();
                    pCManualResponseDto.setId(childManual.getId());
                    pCManualResponseDto.setTitle(childManual.getTitle());
                    pCManualResponseDto.setPosition(childManual.getPosition());
                    pCManualResponseDto.setContent(childManual.getContent());
                    pPManualResponseDto.setDepartmentList(selectedDept);
                    pCManualResponseDto.setCreatedOn(childManual.getCreatedOn());
                    pCManualResponseDto.setModifiedOn(childManual.getModifiedOn());
                    pCManualResponseDto.setStatus(childManual.isStatus());
                    pCManualResponseDto.setModificationSummary(childManual.getModificationSummary());
                    pCManualList.add(pCManualResponseDto);
                }
            }

            pPManualResponseDto.setChildProcessList(pCManualList);
            return new ResponseEntity(pPManualResponseDto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(new AppException("processManualTitle.required"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping(path = "/add")
    public ResponseEntity<?> loadProcessProcessManual(@RequestParam(value = "id") Integer id) {

        try {
            Map<String, List<Department>> departmentMap = departmentParentChildFormation(
                    departmentRepository.getDepartmentsWithParentIdAndChildCount());
            // model.addAttribute("departments", departmentMap);
        } catch (Exception e) {
            log.error(Constants.EXCEPTION, e);
        }
        ProcessManual processManual = null;
        if (null != id) {
            try {
                List<String> loadDependency = new ArrayList<String>();
                loadDependency.add(Constants.DEPENDANCY_DEPARTMENTS);

                processManual = processManualRepository.getProcessManualById(id, loadDependency);

                /* modification summary should be blank every time */
                processManual.setModificationSummary(null);
                processManual.setTitle(StringEscapeUtils.unescapeHtml3(processManual.getTitle()));
            } catch (Exception e) {
                log.error(Constants.EXCEPTION, e);
            }
            // model.addAttribute("processManual", processManual);
            // model.addAttribute("mode", "Edit");
            // map.put("operationType", "Edit");
            // map.put("buttonName", "Update");
        } else {
            // model.addAttribute("processManual", new ProcessManual());
            // model.addAttribute("mode", "Add");
            // map.put("operationType", "Add");
            // map.put("buttonName", "Save");
        }

        return new ResponseEntity<ProcessManual>(processManual, HttpStatus.CREATED);
    }

    /**
     * function to change ProcessManual status
     *
     * @param id
     * @param status (1: activate. 0: inactivate)
     * @return
     */
    @PostMapping(value = "/changeStatus")
    public boolean setStatus(@RequestParam(value = "id") Integer id, @RequestParam(value = "status") Integer status) {

        log.info("in changeStatus method");
        log.info("id = " + id);
        log.info("status = " + status);

        boolean response = false;

        try {
            boolean bStatus = false;
            if (status == Constants.ONE) {
                bStatus = true;
            }

            int updateCount = processManualRepository.changeStatus(id, bStatus);
            log.info("update Count:: " + updateCount);

            if (updateCount > Constants.ZERO) {
                response = true;
            }

        } catch (Exception e) {
            log.error(Constants.EXCEPTION, e);
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    @GetMapping(path = {"/departmentList"})
    public ResponseEntity<?> deparmentList() {
        List<DepartmentListResponseDto> departmentMap = null;

        try {
            departmentMap = getAllDepartments(this.departmentRepository.getDepartmentsWithParentIdAndChildCount());
        } catch (Exception var3) {
            log.error("Exception : ", var3);
            return new ResponseEntity(new AppException(var3.getMessage(), var3.getCause()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(departmentMap, HttpStatus.OK);
    }

    public static List<DepartmentListResponseDto> getAllDepartments(List<Department> deptList) {
        List<DepartmentListResponseDto> departmentListResponseList = new LinkedList();
        if (deptList != null && deptList.size() > 0) {
            List<Department> tempList = new ArrayList();
            tempList.addAll(deptList);
            log.info("Getting List of departments");
            Iterator var3 = deptList.iterator();

            while (var3.hasNext()) {
                Department dept = (Department) var3.next();
                DepartmentListResponseDto departmentListResponseDto = new DepartmentListResponseDto();
                departmentListResponseDto.setName(dept.getName());
                departmentListResponseDto.setId(dept.getId());
                departmentListResponseDto.setParentId(dept.getParentId());
                if (departmentListResponseDto.getId() != null && departmentListResponseDto.getName() != null) {
                    departmentListResponseList.add(departmentListResponseDto);
                }
            }
        }

        log.info("OUT Department List");
        List<DepartmentListResponseDto> distictDepartmentList = (List) departmentListResponseList.stream().distinct()
                .collect(Collectors.toList());
        return distictDepartmentList;
    }

    /**
     * function to set ProcessManual as deleted (is_deleted =1)
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete")
    public boolean deleteProcessManual(@RequestParam(value = "id") Integer id) {
        log.info("in deleteProcessManual method :: id" + id);

        boolean response = false;

        try {
            int updateCount = Constants.ZERO;

            try {
                updateCount = processManualRepository.deleteProcessManuals(id);
            } catch (Exception e) {
                // ARC added print stack Trace in log
                e.printStackTrace();
            }

            if (updateCount > Constants.ZERO) {
                response = true;
            }

        } catch (Exception e) {
            log.error(Constants.EXCEPTION, e);
        }
        return response;
    }

    @GetMapping(path = "/detail")
    public String processManualDetail(@RequestParam(value = "id") Integer id) {

        if (id != null) {
            try {
                ProcessManual processManual = null;
                // if (AkpmsUtil
                // .checkPermission(Constants.PERMISSION_DOCUMENT_MANAGER)) {
                // processManual = processManualRepository.getProcessManualById(
                // Long.parseLong(request.getParameter("id")), true,
                // false);
                //// } else {
                processManual = processManualRepository.getProcessManualById(id, true, true);
                // }

                if (processManual != null) {
                    // String success = (String) session
                    // .getAttribute(Constants.SUCCESS_UPDATE);
                    // if (success != null) {
                    // map.put("success",
                    // messageSource.getMessage(success, null,
                    // Locale.ENGLISH).trim());
                    // }
                    // session.removeAttribute(Constants.SUCCESS_UPDATE);
                    log.info("got Process manual Object. object Id = " + processManual);
                    UserEntity loggedInUser = AkpmsUtil.getLoggedInUser();

                    List<Integer> userDeptList = new ArrayList<Integer>();
                    for (DepartmentEntity dept : loggedInUser.getDepartments()) {
                        userDeptList.add(dept.getId());
                    }
                    for (Department dept : processManual.getDepartments()) {
                        if (userDeptList.contains(dept.getId())) {
                            processManual.setShowReadAndUnderstood(true);
                            break;
                        }
                    }
                    /* Role id 4 : Trainee */
                    if (loggedInUser.getRole().getId() == Constants.TRAINEE_ROLE_ID) {
                        Integer loggedInUserId = loggedInUser.getId();
                        for (UserEntity user : processManual.getUserList()) {
                            if (loggedInUserId == user.getId()) {
                                processManual.setReadAndUnderstood(true);
                            }
                        }
                        for (ProcessManual subProcessManual : processManual.getProcessManualList()) {
                            for (UserEntity user : subProcessManual.getUserList()) {
                                if (loggedInUserId == user.getId()) {
                                    subProcessManual.setReadAndUnderstood(true);
                                }
                            }
                        }
                    }

                    // map.put("section", processManual);
                    // model.addAttribute("processManual", new ProcessManual());
                    // model.addAttribute("newFile", new Files());
                } else {
                    return "redirect:/processmanual";
                }

            } catch (Exception e) {
                if (e.getMessage().equalsIgnoreCase(Constants.NO_ENTRY_FOUND_FOR_QUERY)) {
                    return "redirect:/processmanual";
                }
                log.error(Constants.EXCEPTION, e);
            }
        } else {
            log.info("Request Object or Id in request Object is coming null");
        }

        return "processManualDetail";
    }

    // @RequestMapping(value = "/fileupload", method = RequestMethod.POST)
    @PostMapping(value = "/fileupload")
    public String addFile(@ModelAttribute("newFile") ArFiles newFile, BindingResult result, Model model,
                          Map<String, Object> map, HttpSession session) {
        log.info(" In addFile method ");
        Integer parentProcessManualId = 1;
        try {
            if (newFile != null) {
                parentProcessManualId = newFile.getProcessManual().getId();
                MultipartFile file = newFile.getAttachedFile();
                if (null != file && file.getSize() > Constants.ZERO) {

                    byte[] fileData = file.getBytes();
                    String originalFileName = file.getOriginalFilename();
                    Long timeMili = System.currentTimeMillis();

                    StringBuilder systemName = new StringBuilder();
                    if (newFile.getProcessManual() != null) {
                        parentProcessManualId = newFile.getProcessManual().getId();
                        systemName.append(newFile.getProcessManual().getId());
                        systemName.append("_");
                        if (newFile.getSubProcessManualId() != null) {
                            systemName.append(newFile.getSubProcessManualId());
                        } else {
                            systemName.append("0");
                        }
                        systemName.append("_");
                    } else {
                        log.info(" Process Manual not found in files object");
                    }

                    systemName.append(timeMili);
                    systemName.append("_");
                    systemName.append(originalFileName);

                    String realPath = messageSource.getMessage("attachments.storage.space", null, Locale.ENGLISH)
                            .trim();

                    log.info("real Path = " + realPath);

                    File dir = new File(realPath);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File fileNamePath = new File(dir, systemName.toString());
                    OutputStream outputStream = new FileOutputStream(fileNamePath);
                    outputStream.write(fileData);
                    outputStream.close();

                    if (newFile.getSubProcessManualId() != null && newFile.getSubProcessManualId() != Constants.ZERO) {
                        ProcessManual processManual = new ProcessManual();
                        processManual.setId(newFile.getSubProcessManualId());
                        newFile.setProcessManual(processManual);
                    }

                    newFile.setName(originalFileName);
                    newFile.setSystemName(systemName.toString());

                    processManualRepository.saveAttachement(newFile);
                    /* set message here */
                    session.setAttribute(Constants.SUCCESS_UPDATE, "processmanual.fileAttachedSuccessfully");
                    return "redirect:/processmanual/detail?id=" + parentProcessManualId;
                } else {
                    log.info("there is no attachment coming in file object");
                    return "redirect:/processmanual/detail?id=" + parentProcessManualId;
                }
            } else {
                log.info("Attached file Object is coming null");
                return "redirect:/processmanual/detail?id=" + parentProcessManualId;
            }
        } catch (Exception e) {
            log.error(Constants.EXCEPTION, e);
            return "redirect:/processmanual/detail?id=" + parentProcessManualId;
        }
    }

    // @RequestMapping(value = "/fileDownload", method = RequestMethod.GET)
    @GetMapping(path = "/fileDownload")
    public void downloadAttachment(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam("id") Integer id, HttpSession session) {
        log.info("In download method. Id = " + id);
        try {
            if (id != null) {
                ArFiles file = processManualRepository.getAttachedFile(id);

                String realPath = messageSource.getMessage("attachments.storage.space", null, Locale.ENGLISH).trim();

                File systemFile = new File(realPath, file.getSystemName());
                InputStream is = new FileInputStream(systemFile);

                response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
                IOUtils.copy(is, response.getOutputStream());
                response.flushBuffer();
            }
        } catch (Exception e) {
            log.error(Constants.EXCEPTION, e);
        }
    }

    // @RequestMapping(value = "/fileDelete", method = RequestMethod.POST)
    @PostMapping(value = "/fileDelete")
    @ResponseBody
    public boolean deleteAttachment(@RequestParam("id") Integer id) {
        log.info("IN Delete Attachment menthod");
        boolean succeed = false;
        try {
            if (id != null) {
                int updatecount = processManualRepository.deleteAttachedFile(id);
                if (updatecount > Constants.ZERO) {
                    log.info("Attachment has deleted successfully");
                    succeed = true;
                }
                return succeed;
            } else {
                log.info("File id is coming null");
            }
        } catch (Exception e) {
            log.error(Constants.EXCEPTION, e);
        }
        return succeed;
    }

    /**
     * function to get modification summary for selected section (process manual)
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/modificationsummary/json", produces = "application/json")
    public @ResponseBody List<ProcessManualAuditJsonData> listModificationSummary(@RequestParam(value = "id") Long id) {
        log.info("process manual::in modificationsummary json method");

        try {
            List<ProcessManualAudit> rows = processManualAuditRepository.getProcessManualModificationById(id);

            return getProcessManualAuditJsonData(rows);

        } catch (Exception e) {
            log.error(e.toString());
        }

        return null;
    }

    /**
     * this method set value in ProcessManualAuditJsonData list
     *
     * @param rows
     * @return
     */
    private List<ProcessManualAuditJsonData> getProcessManualAuditJsonData(List<ProcessManualAudit> rows) {
        log.info("in getProcessManualAuditJsonData method");
        List<ProcessManualAuditJsonData> processmanualAuditJsonData = new ArrayList<ProcessManualAuditJsonData>();

        log.info("row size:" + rows.size());
        if (rows != null && rows.size() > Constants.ZERO) {
            for (ProcessManualAudit processManualAudit : rows) {
                ProcessManualAuditJsonData pmajd = new ProcessManualAuditJsonData();
                if (processManualAudit.getModifiedBy() != null) {
                    pmajd.setId(processManualAudit.getId());
                    pmajd.setModificationSummary(processManualAudit.getModificationSummary());
                    pmajd.setModifiedBy(processManualAudit.getModifiedBy().getFirstName() + " "
                            + processManualAudit.getModifiedBy().getLastName());
                    pmajd.setModifiedOn(AkpmsUtil.akpmsDateFormat(processManualAudit.getModifiedOn(), null));

                    processmanualAuditJsonData.add(pmajd);
                }
            }
        }

        return processmanualAuditJsonData;
    }

    // @RequestMapping(value = "/readUnderstand/json", method = RequestMethod.POST,
    // produces = "application/json")
    @PostMapping(value = "/readUnderstand/json", produces = "application/json")
    @ResponseBody
    public boolean readAndUnderstand(@RequestParam(value = "id", required = false) Integer processManualId,
                                     WebRequest request) {
        boolean isRead = false;
        try {
            if (processManualId != null) {
                ProcessManual processManual = processManualRepository.getProcessManualById(processManualId, true,
                        false);
                UserEntity user = AkpmsUtil.getLoggedInUser();
                processManual.getUserList().add(user);
                processManualRepository.updateProcessManual(processManual);
                isRead = true;
                return isRead;
            }
        } catch (Exception e) {
            log.error(Constants.EXCEPTION, e);
            return isRead;
        }
        return isRead;
    }

    // @RequestMapping(value = "/subSection/json", method = RequestMethod.GET)
    @GetMapping(path = "/subSection/json")
    @ResponseBody
    public SubProcessManualJsonData getSubSection(@RequestParam(value = "subSectionId") Integer subSectionId) {
        log.info("IN SubSection method");
        SubProcessManualJsonData subProcessManualJsonData = null;
        List<String> dependencies = new ArrayList<String>();

        try {
            if (subSectionId != null) {
                ProcessManual processManual = processManualRepository.getProcessManualById(subSectionId, null);
                ProcessManual tempProcessManual = processManualRepository
                        .getProcessManualById(processManual.getParent().getId(), true, false);
                List<ProcessManual> pml = tempProcessManual.getProcessManualList();
                double previousPosition = 0;
                for (int i = 0; i < pml.size(); i++) {
                    ProcessManual pm = pml.get(i);
                    if (pm.getId().equals(subSectionId)) {
                        if (i == 0) {
                            System.out.println("This is the first subsection , there is no subSection before this. ");
                            previousPosition = processManual.getParent().getPosition();
                        } else {
                            previousPosition = pml.get(i - 1).getPosition();
                        }
                    }
                }

                if (previousPosition != 0) {
                    processManual.setPosition(previousPosition);
                }

                subProcessManualJsonData = this.getSubProcessManualJsonData(processManual);
                log.info("OUT SubSection");
                return subProcessManualJsonData;
            } else {
                log.info("OUT SubSection");
                return subProcessManualJsonData;
            }
        } catch (Exception e) {
            log.error(Constants.EXCEPTION, e);
            return subProcessManualJsonData;
        }

    }

    /**
     * function to set edit subsction popup data this is functional as wrapper
     *
     * @param processManual
     * @return
     */
    private SubProcessManualJsonData getSubProcessManualJsonData(ProcessManual processManual) {
        SubProcessManualJsonData subProcessManualJsonData = null;
        try {

            if (processManual != null) {
                subProcessManualJsonData = new SubProcessManualJsonData();
                subProcessManualJsonData.setId(processManual.getId());
                subProcessManualJsonData.setTitle(StringEscapeUtils.unescapeHtml3(processManual.getTitle()));
                subProcessManualJsonData.setContent(processManual.getContent());
                subProcessManualJsonData.setNotification(processManual.isNotification());
                subProcessManualJsonData.setStatus(processManual.isStatus());
                subProcessManualJsonData.setParentId(processManual.getParent().getId());
                subProcessManualJsonData.setCreatedBy(Integer.valueOf(processManual.getCreatedByName()));
                subProcessManualJsonData.setCreatedOn(processManual.getCreatedOn());
                subProcessManualJsonData.setPosition(processManual.getPosition());
                return subProcessManualJsonData;
            } else {
                return subProcessManualJsonData;
            }

        } catch (Exception e) {
            log.error(Constants.EXCEPTION, e);
            return subProcessManualJsonData;
        }
    }
//	 @GetMapping(
//		        path = {"/loadDepartments"}
//		    )
//		    public ResponseEntity<?> loadDepartments() {
//		        List<DepartmentResponseDto> departmentMap = null;
//
//		        try {
//		            departmentMap = departmentParentChildResponse(this.departmentRepository.getDepartmentsWithParentIdAndChildCount());
//		        } catch (Exception var3) {
//		            log.error("Exception : ", var3);
//		            return new ResponseEntity(new AppException(var3.getMessage(), var3.getCause()), HttpStatus.INTERNAL_SERVER_ERROR);
//		        }
//
//		        return new ResponseEntity(departmentMap, HttpStatus.OK);
//		    }

//	public static List<DepartmentResponseDto> departmentParentChildResponse(List<Department> deptList) {
//		List<DepartmentResponseDto> departmentResponseDtoList = new LinkedList();
//		if (deptList != null && deptList.size() > 0) {
//			List<Department> tempList = new ArrayList();
//			tempList.addAll(deptList);
//			log.info("IN Department List for ParentChild formation");
//			Iterator var3 = deptList.iterator();
//		
//				DepartmentResponseDto departmentResponseDto = new DepartmentResponseDto();
//				Department dept = new Department();
//				do {
//					if (!var3.hasNext()) {
//						break;
//					}
//
//					dept = (Department)var3.next();
//					departmentResponseDto = new DepartmentResponseDto();
//				}while(dept.getParentId() != 0L);
//
//				if (dept.getChildCount() > 0L) {
//
//					departmentResponseDto.setName(dept.getName());
//					departmentResponseDto.setId(dept.getId());
//					departmentResponseDtoList.add(departmentResponseDto);
//					List<DepartmentResponseDto> distictDepartmentResponse = (List)departmentResponseDtoList.stream().distinct().collect(Collectors.toList());
//					
//				}
//        
//			   if (dept.isStatus() && !dept.isDeleted() && departmentResponseDto.getId() != null && (dept.getName().equalsIgnoreCase("Coding and Charge Entry Department") || dept.getName().equalsIgnoreCase("Accounts Receivable Department") || dept.getName().equalsIgnoreCase("Payments Department") || dept.getName().equalsIgnoreCase("Accounting Department"))) {
//					departmentResponseDtoList.add(departmentResponseDto);
//				}
//			
//			
//	List<DeparmentChildDto> deparmentChildList = new ArrayList();
//	Iterator var9 = tempList.iterator();
//
//	while(var9.hasNext())
//	{
//		Department childDept = (Department) var9.next();
//		if (childDept.getParentId() == Long.valueOf((long) dept.getId())) {
//			DeparmentChildDto deparmentChildDto = new DeparmentChildDto();
//			deparmentChildDto.setId(childDept.getId());
//			deparmentChildDto.setName(childDept.getName());
//			deparmentChildDto.setParentId(childDept.getParentId());
//			deparmentChildList.add(deparmentChildDto);
//			List<DeparmentChildDto> distinctDeparmentChildDtoList = (List) deparmentChildList.stream().distinct()
//					.collect(Collectors.toList());
//			departmentResponseDto.setChild(distinctDeparmentChildDtoList);
//			if (childDept.isStatus() && !dept.isDeleted()) {
//			}
//		}
//	
//	}while(departmentResponseDto.getId()==null);
//			
////			if(!dept.getName().equalsIgnoreCase("Coding and Charge Entry Department")&&!dept.getName().equalsIgnoreCase("Accounts Receivable Department")&&!dept.getName().equalsIgnoreCase("Payments Department")&&!dept.getName().equalsIgnoreCase("Accounting Department")) {
////				
////
////	                    departmentResponseDtoList.add(departmentResponseDto);
//			}
//		
//		
//	log.info("OUT Department List for ParentChild formation");
//	List<DepartmentResponseDto> distictDepartmentsList = (List) departmentResponseDtoList.stream().distinct()
//			.collect(Collectors.toList());
//	     return distictDepartmentsList;
//	
//		}

    public static Map<String, List<Department>> departmentParentChildFormation(List<Department> deptList) {
        Map<String, List<Department>> deptMap = new HashMap<String, List<Department>>();
        if (deptList != null && deptList.size() > Constants.ZERO) {
            List<Department> tempList = new ArrayList<Department>();
            tempList.addAll(deptList);

            log.info("IN Department List for ParentChild formation");
            for (Department dept : deptList) {
                if (dept.getParentId() == Constants.ZERO) {
                    if (dept.getChildCount() > Constants.ZERO) {
                        List<Department> list = new ArrayList<Department>();
                        if (dept.isStatus() && !dept.isDeleted()) {

                            list.add(dept);
                        } else {
                            continue;
                        }
                        for (Department childDept : tempList) {
                            if (childDept.getParentId() == dept.getId()) {
                                if (childDept.isStatus() && !dept.isDeleted()) {
                                    list.add(childDept);
                                }
                            }
                        }

                        deptMap.put(dept.getName(), list);
                    } else {
                        List<Department> list = new ArrayList<Department>();
                        if (dept.isStatus() && !dept.isDeleted()) {

                            list.add(dept);
                        } else {
                            continue;
                        }
                        deptMap.put(dept.getName(), list);
                    }
                }
            }
        }

        log.info("OUT Department List for ParentChild formation");
        return deptMap;
    }

    @GetMapping("/history/{processManualId}")
    public List<com.idsargus.akpmsarservice.model.domain.ArProcessManualAudit> getProcessManualHistory(@PathVariable Long processManualId) {


        List<ArProcessManualAudit> arProcessManualAuditsList = processManualAuditCustom.findByProcessManualId(processManualId);


        for (ArProcessManualAudit pma : arProcessManualAuditsList) {
            if (pma.getUserId() != null) {
                UserEntity record = userDataRestRepository.findById(Integer.valueOf(pma.getUserId())).get();
                pma.setModifiedBy(record.getFirstName() + " " + record.getLastName());
            }
        }
        return arProcessManualAuditsList;
    }

}
