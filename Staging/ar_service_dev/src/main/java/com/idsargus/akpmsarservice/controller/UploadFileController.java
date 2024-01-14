package com.idsargus.akpmsarservice.controller;

import com.idsargus.akpmsarservice.dto.BaseResponse;
import com.idsargus.akpmsarservice.dto.FileResponse;
import com.idsargus.akpmsarservice.model.domain.ArFiles;
import com.idsargus.akpmsarservice.model.domain.ProcessManual;
import com.idsargus.akpmsarservice.repository.FilesRepository;

;
import com.idsargus.akpmsarservice.repository.ProcessManualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.websocket.server.PathParam;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(value = "v1/arapi")
public class UploadFileController {

    @Autowired
    private ProcessManualRepository processManualRepository;
    @Autowired
    FilesRepository filesRepository;
    private final Path root = Paths.get("ArProductivty/Coding Correction/uploads");
    private final Path paymentPostingRoot = Paths.get("ArProductivty/Payment Posting log/uploads");

    private final Path rejectLogWorkFlowRoot = Paths.get("ArProductivty/Reject Log/uploads");
    private final Path refundRequestRoot = Paths.get("ArProductivty/Refund Request/uploads");

    private final Path processManualRoot = Paths.get("ArProductivty/Process Manual/uploads");

    public void init() {
        try {
            Files.createDirectories(root);
            Files.createDirectories(paymentPostingRoot);
            Files.createDirectories(rejectLogWorkFlowRoot);
            Files.createDirectories(refundRequestRoot);
            Files.createDirectories(processManualRoot);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @PostMapping("/upload/{codingCorrectionId}")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile[] files
            , @PathVariable String codingCorrectionId) {
        List<ArFiles> arFiles1 = new ArrayList<>();
        init();
        try {
            Arrays.asList(files).stream().forEach(file -> {
                ArFiles newFile = new ArFiles();

                Timestamp ts = new Timestamp(System.currentTimeMillis());
                newFile.setName(ts.getTime() + "_" + file.getOriginalFilename());
                newFile.setCodingCorrectionId(codingCorrectionId);
                newFile.setStatus(true);
                newFile.setDeleted(false);
                newFile.setUrl(this.root.toUri().getPath().substring(1) + "/" + file.getOriginalFilename() + "_" + ts.getTime());

                arFiles1.add(newFile);
                try {
                    Files.copy(file.getInputStream(), this.root.resolve(newFile.getName()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            if (!arFiles1.isEmpty()) {
                for (ArFiles arFiles2 : arFiles1) {
                    filesRepository.save(arFiles2);
                }
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("No file to be upload.");
            }
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A file of that name already exists.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(arFiles1);
    }


    @PostMapping("/upload/paymentpostingworkflow/{paymentPostingWorkFlowId}")
    public ResponseEntity<?> uploadFilePaymentPostingWorkFlow(
            @RequestParam("isEob") boolean isEob,
            @RequestParam("isCopyCancelCheck") boolean isCopyCancelCheck,
            @RequestParam("file") MultipartFile[] files
            , @PathVariable String paymentPostingWorkFlowId) {
        List<ArFiles> arFiles1 = new ArrayList<>();
        init();
        try {
            Arrays.asList(files).stream().forEach(file -> {
                ArFiles newFile = new ArFiles();
                Timestamp ts = new Timestamp(System.currentTimeMillis());
                newFile.setName(ts.getTime() + "_" + file.getOriginalFilename());
                newFile.setPaymentPostingWorkFlowId(paymentPostingWorkFlowId);
                newFile.setStatus(true);
                newFile.setCopyCancelCheck(isCopyCancelCheck);
                newFile.setEob(isEob);
                newFile.setDeleted(false);
                newFile.setUrl(this.paymentPostingRoot.toUri().getPath().substring(1) + "/" + file.getOriginalFilename() + "_" + ts.getTime());

                arFiles1.add(newFile);
                try {
                    Files.copy(file.getInputStream(), this.paymentPostingRoot.resolve(newFile.getName()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            if (!arFiles1.isEmpty()) {
                for (ArFiles arFiles2 : arFiles1) {
                    filesRepository.save(arFiles2);
                }
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("No file to be upload.");
            }
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A file of that name already exists.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(arFiles1);
    }

    @PostMapping("/upload/rejectLogs/{rejectLogId}")
    public ResponseEntity<?> uploadFileForRejectLog(@RequestParam("file") MultipartFile[] files
            , @PathVariable String rejectLogId) {
        List<ArFiles> arFiles1 = new ArrayList<>();
        init();
        try {
            Arrays.asList(files).stream().forEach(file -> {
                ArFiles newFile = new ArFiles();

                Timestamp ts = new Timestamp(System.currentTimeMillis());
                newFile.setName(ts.getTime() + "_" + file.getOriginalFilename());
                newFile.setRejectLogId(rejectLogId);
                newFile.setStatus(true);
                newFile.setDeleted(false);
                newFile.setUrl(this.rejectLogWorkFlowRoot.toUri().getPath().substring(1) + "/" + file.getOriginalFilename() + "_" + ts.getTime());

                arFiles1.add(newFile);
                try {
                    Files.copy(file.getInputStream(), this.rejectLogWorkFlowRoot.resolve(newFile.getName()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            if (!arFiles1.isEmpty()) {
                for (ArFiles arFiles2 : arFiles1) {
                    filesRepository.save(arFiles2);
                }
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("No file to be upload.");
            }
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A file of that name already exists.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(arFiles1);
    }


    @PostMapping("/upload/process_manual/{processManualId}")
    public ResponseEntity<?> uploadFileProcessManual(@RequestParam("file") MultipartFile[] files
            , @PathVariable String processManualId) {
        List<ArFiles> arFiles1 = new ArrayList<>();
        init();
        try {
            Arrays.asList(files).stream().forEach(file -> {
                ArFiles newFile = new ArFiles();
                newFile.setProcessManual(getProcessManualById(processManualId));
                Timestamp ts = new Timestamp(System.currentTimeMillis());
                newFile.setName(ts.getTime() + "_" + file.getOriginalFilename());
                //newFile.setProcessManualId(processManualId);
                newFile.setCreatedBy(getProcessManualById(processManualId).getCreatedBy());
                newFile.setModifiedBy(getProcessManualById(processManualId).getModifiedBy());
                newFile.setStatus(true);
                newFile.setDeleted(false);
                newFile.setUrl(this.processManualRoot.toUri().getPath().substring(1) + "/" + file.getOriginalFilename() + "_" + ts.getTime());

                arFiles1.add(newFile);
                try {
                    Files.copy(file.getInputStream(), this.processManualRoot.resolve(newFile.getName()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            if (!arFiles1.isEmpty()) {
                for (ArFiles arFiles2 : arFiles1) {
                    filesRepository.save(arFiles2);

                }
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("No file to be upload.");
            }
            // arFiles1.remove(getProcessManualById(processManualId));
//            for(ArFiles arFiles : arFiles1){
//                arFiles.setProcessManual(null);
//
//                arFiles1.add(arFiles);
//            }

        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A file of that name already exists.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        List<ArFiles> arFileListResponse = new ArrayList<>();

        for (ArFiles arFiles : arFiles1) {
            ArFiles arFiles2 = new ArFiles();
            arFiles2.setAttachedFile(arFiles.getAttachedFile());
            arFiles2.setId(arFiles.getId());
            arFiles2.setName(arFiles.getName());
            arFiles2.setType(arFiles.getType());
            arFiles2.setStatus(arFiles.isStatus());
            arFiles2.setRefundRequestId(arFiles.getRefundRequestId());
            arFiles2.setSystemName(arFiles.getSystemName());
            arFiles2.setUrl(arFiles.getUrl());
            arFiles2.setProcessManualId(processManualId);
            if(arFiles.getCreatedBy() != null) {
                arFiles2.setCreatedById(arFiles.getCreatedBy().getId().toString());
            } if(arFiles.getModifiedBy() != null){
                arFiles2.setModifiedById(arFiles.getModifiedBy().getId().toString());
            }
//            arFiles2.setCreatedBy(arFiles.getCreatedBy());
//            arFiles2.setModifiedBy(arFiles.getModifiedBy());
            if(arFiles.getCreatedOn() != null) {
                arFiles2.setCreatedOn(arFiles.getCreatedOn());
            } if(arFiles.getModifiedOn() != null){
                arFiles2.setModifiedOn(arFiles.getModifiedOn());
            }
            arFileListResponse.add(arFiles2);//arFiles1.add(arFiles2);
        }

        return ResponseEntity.status(HttpStatus.OK).body(arFileListResponse);
    }

    private ProcessManual getProcessManualById(String processManualId) {
        return processManualRepository.getProcessManualById(Integer.valueOf(processManualId), null);


    }

    @GetMapping("/process_manual/files/{processManualId}")
    public ResponseEntity<List<ArFiles>> getFileByProcessManualId(@PathVariable String processManualId) {

        List<ArFiles> allFiles = new ArrayList<>();
        filesRepository.findAll().forEach(allFiles::add);

        List<ArFiles> fileInfos = allFiles.stream().filter(check -> check.getProcessManual() != null)
                .filter(x -> x.getProcessManual().getId().toString().equals(processManualId))
                .map(path -> {
                    String filename = path.getName() != null ? path.getName().toString() : "";
                    Path file = this.processManualRoot.resolve(filename);
                    String url = file.toUri().getPath().toString();
                    path.setUrl(url.substring(1));
                    return path;
                }).collect(Collectors.toList());

        List<ArFiles> arFileResponse = new ArrayList<>();
        for(ArFiles arFiles : fileInfos){
            arFiles.setProcessManual(null);
            arFiles.setCreatedBy(null);
            arFileResponse.add(arFiles);
        }
        return ResponseEntity.status(HttpStatus.OK).body(arFileResponse);
    }

    @PostMapping("/upload/refundRequest/{refundRequestId}")
    public ResponseEntity<?> uploadRefundRequest(@RequestParam("file") MultipartFile[] files
            , @PathVariable String refundRequestId) {
        List<ArFiles> arFiles1 = new ArrayList<>();
        init();
        try {
            Arrays.asList(files).stream().forEach(file -> {
                ArFiles newFile = new ArFiles();

                Timestamp ts = new Timestamp(System.currentTimeMillis());
                newFile.setName(ts.getTime() + "_" + file.getOriginalFilename());
                newFile.setRefundRequestId(refundRequestId);
                newFile.setStatus(true);
                newFile.setDeleted(false);
                newFile.setUrl(this.refundRequestRoot.toUri().getPath().substring(1) + "/" + file.getOriginalFilename() + "_" + ts.getTime());

                arFiles1.add(newFile);
                try {
                    Files.copy(file.getInputStream(), this.refundRequestRoot.resolve(newFile.getName()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            if (!arFiles1.isEmpty()) {
                for (ArFiles arFiles2 : arFiles1) {
                    filesRepository.save(arFiles2);
                }
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("No file to be upload.");
            }
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A file of that name already exists.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(arFiles1);
    }


    @GetMapping("/files/{codingCorrectionId}")
    public ResponseEntity<List<ArFiles>> getFileById(@PathVariable String codingCorrectionId) {
        List<ArFiles> fileInfos = StreamSupport.stream(filesRepository.findAll().spliterator(),false)
                .filter(check -> check.getCodingCorrectionId() != null)
                .filter(x -> x.getCodingCorrectionId().toString().equals(codingCorrectionId))
                .map(path -> {

                    String filename = path.getName() != null ? path.getName().toString() : "";
                    Path file = this.root.resolve(filename);
                    String url = file.toUri().getPath().toString();
                    path.setUrl(url.substring(1));
                    return path;
                }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/files/paymentpostingworkflow/{paymentPostingWorkFlowId}")
    public ResponseEntity<List<ArFiles>> getPaymentPostingWorkFlowFileById(@RequestParam("isEob") boolean
                                                                                       isEob ,
                                                                           @RequestParam("isCopyCancelCheck") boolean
                                                                                   isCopyCancelCheck ,

            @PathVariable String paymentPostingWorkFlowId) {

        List<ArFiles> allFiles = new ArrayList<>();
        filesRepository.findAll().forEach(allFiles::add);
        List<ArFiles> fileInfos = new ArrayList<>();
        if(isCopyCancelCheck) {
            fileInfos = allFiles.stream()
                    .filter(check -> check.getPaymentPostingWorkFlowId() != null)
                    .filter(x -> x.getPaymentPostingWorkFlowId().toString().equals(paymentPostingWorkFlowId))
                    .filter(x -> x.isCopyCancelCheck())
                    .map(path -> {

                        String filename = path.getName() != null ? path.getName().toString() : "";
                        Path file = this.paymentPostingRoot.resolve(filename);
                        String url = file.toUri().getPath().toString();

                        path.setUrl(url.substring(1));
                        return path;
                    }).collect(Collectors.toList());
        } else if(isEob) {
            fileInfos = allFiles.stream()
                    .filter(check -> check.getPaymentPostingWorkFlowId() != null)
                    .filter(x -> x.getPaymentPostingWorkFlowId().toString().equals(paymentPostingWorkFlowId))
                    .filter(x -> x.isEob())
                    .map(path -> {

                        String filename = path.getName() != null ? path.getName().toString() : "";
                        Path file = this.paymentPostingRoot.resolve(filename);
                        String url = file.toUri().getPath().toString();

                        path.setUrl(url.substring(1));
                        return path;
                    }).collect(Collectors.toList());
        } else if( isCopyCancelCheck && isEob ) {
            fileInfos = allFiles.stream()
                    .filter(check -> check.getPaymentPostingWorkFlowId() != null)
                    .filter(x -> x.getPaymentPostingWorkFlowId().toString().equals(paymentPostingWorkFlowId))
                    .filter(x->x.isCopyCancelCheck())
                    .filter((x->x.isEob()))
                    .map(path -> {

                        String filename = path.getName() != null ? path.getName().toString() : "";
                        Path file = this.paymentPostingRoot.resolve(filename);
                        String url = file.toUri().getPath().toString();

                        path.setUrl(url.substring(1));
                        return path;
                    }).collect(Collectors.toList());
        } else {
            fileInfos = allFiles.stream()
                    .filter(check -> check.getPaymentPostingWorkFlowId() != null)
                    .filter(x -> x.getPaymentPostingWorkFlowId().toString().equals(paymentPostingWorkFlowId))
                    .map(path -> {
                        String filename = path.getName() != null ? path.getName().toString() : "";
                        Path file = this.paymentPostingRoot.resolve(filename);
                        String url = file.toUri().getPath().toString();

                        path.setUrl(url.substring(1));
                        return path;
                    }).collect(Collectors.toList());

        }

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }


    @GetMapping("/files/rejectLogs/{rejectLogId}")
    public ResponseEntity<List<ArFiles>> getRejectLogsFiles(@PathVariable String rejectLogId) {

        List<ArFiles> allFiles = new ArrayList<>();
        filesRepository.findAll().forEach(allFiles::add);
        List<ArFiles> fileInfos = allFiles.stream().filter(check -> check.getRejectLogId() != null)
                .filter(x -> x.getRejectLogId().toString().equals(rejectLogId))
                .map(path -> {

                    String filename = path.getName() != null ? path.getName().toString() : "";
                    Path file = this.rejectLogWorkFlowRoot.resolve(filename);
                    String url = file.toUri().getPath().toString();

                    path.setUrl(url.substring(1));
                    return path;
                }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/files/refundRequest/{refundRequestId}")
    public ResponseEntity<List<ArFiles>> getRefundRequestFile(@PathVariable String refundRequestId) {

        List<ArFiles> allFiles = new ArrayList<>();
        filesRepository.findAll().forEach(allFiles::add);
        List<ArFiles> fileInfos = allFiles.stream().filter(check -> check.getRefundRequestId() != null)
                .filter(x -> x.getRefundRequestId().toString().equals(refundRequestId))
                .map(path -> {

                    String filename = path.getName() != null ? path.getName().toString() : "";
                    Path file = this.refundRequestRoot.resolve(filename);
                    String url = file.toUri().getPath().toString();

                    path.setUrl(url.substring(1));
                    return path;
                }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/downloadfile")
    public ResponseEntity<FileResponse> downloadFile(@PathParam("url") String url) throws IOException {

        Resource resource;

        String fileBasePath = url;
        String base64Encode = null;
        FileResponse fileResponse = new FileResponse();
        Path path = Paths.get(fileBasePath);
        try {
            resource = new UrlResource(path.toUri());
            byte[] fileContent = Files.readAllBytes(resource.getFile().toPath());
            base64Encode = Base64.getEncoder().encodeToString(fileContent);
            fileResponse.setBase64(base64Encode);
            fileResponse.setUrl(resource.getFile().toPath().toString());
            fileResponse.setName(resource.getFilename().toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        String contentType = null;
        contentType = "application/octet-stream";
        fileResponse.setContentType(contentType);


        return ResponseEntity.ok()
                .body(fileResponse);

    }


    @GetMapping("/files/fileId/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    public Resource load(String filename) {
        try {
            Path file = this.root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/file/delete/{fileId}")
    public ResponseEntity<?> deleteFile(@PathVariable Integer fileId) {
        String message = "";
        BaseResponse baseResponse = new BaseResponse();
        try {
            ArFiles deleteFile = filesRepository.findById(fileId).get();
            if (!deleteFile.isDeleted()) {
                Path file = root.resolve(deleteFile.getName());
                Files.deleteIfExists(file);
                filesRepository.deleteById(deleteFile.getId());
                baseResponse.setMessage("File has been deleted " + deleteFile.getName());
                baseResponse.setDeleted(true);
                return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
            }

        } catch (Exception e) {

            message = "Could not delete the file: " + e.getMessage();
            baseResponse.setMessage(message);
            baseResponse.setDeleted(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseResponse);
        }
        baseResponse.setMessage("File Not Found");
        baseResponse.setDeleted(false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
    }
}
