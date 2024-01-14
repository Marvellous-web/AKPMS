package com.idsargus.akpmsarservice.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.idsargus.akpmscommonservice.entity.ArProductivityEntity;
import com.idsargus.akpmsarservice.dto.ArProductivityRequestDto;
import com.idsargus.akpmsarservice.dto.ArProductivityResponseDto;
import com.idsargus.akpmsarservice.dto.request.ArProductivityRequest;
import com.idsargus.akpmsarservice.exception.ResourceNotFoundException;
import com.idsargus.akpmsarservice.util.SearchCriteria;

public interface ArProductivityService {

	ArProductivityEntity add(ArProductivityEntity entity) throws Exception;

	ArProductivityEntity update(ArProductivityEntity entity) throws Exception;

	ArProductivityEntity delete() throws Exception;

	ArProductivityEntity getById(Integer userId) throws ResourceNotFoundException;

	List<ArProductivityEntity> list(String orderBy, String direction, Integer page, Integer size,
			List<SearchCriteria> searchCriterias) throws Exception;
	
	Long count(List<SearchCriteria> searchCriterias) throws Exception;

	Map<String, String> validation(ArProductivityEntity entity) throws Exception;

	ArProductivityEntity enrichEntity(ArProductivityRequestDto requestDto, ArProductivityEntity entity)
			throws Exception;
	
	public Page<ArProductivityResponseDto> getarProductivityList(ArProductivityRequest arProductivityRequest, Pageable pageable )throws Exception;
	
	public List<Object> getDashboardCountList() throws Exception;
}
