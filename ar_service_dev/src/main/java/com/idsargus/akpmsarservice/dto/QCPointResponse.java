package com.idsargus.akpmsarservice.dto;

import com.idsargus.akpmsarservice.model.domain.QcPoint;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QCPointResponse {
      private List<QcPointRecord> qcPoint;
}
