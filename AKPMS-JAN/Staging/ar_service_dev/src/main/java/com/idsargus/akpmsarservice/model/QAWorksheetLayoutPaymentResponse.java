//package com.idsargus.akpmsarservice.model;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonIdentityInfo;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import com.idsargus.akpmsarservice.model.domain.QAProductivitySampling;
//import com.idsargus.akpmsarservice.model.domain.QcPoint;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.List;
//import java.util.Map;
//
//@Getter
//@Setter
//public class QAWorksheetLayoutPaymentResponse {
//    @JsonBackReference
//    private List<QAProductivitySampling>  qaProdSampleDataList;
//
//    @JsonManagedReference
//    private QaWorkSheetResponse qaWorksheet;
//    private String orderBy;
//    @JsonManagedReference
//    private  List<QcPoint> parentQcPoint;
//    @JsonManagedReference
//    private Map<Long, String> parentQCPointMap;
//    @JsonManagedReference
//    private List<QcPoint> qcPointChildrenOnly;
//
//    private String error;
//}
