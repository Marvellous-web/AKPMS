package com.idsargus.akpmsarservice.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PagingResult<T> {

	public Long total;

	public List<T> Data;
}
