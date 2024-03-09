package com.idsargus.akpmsarservice.repository;

import com.idsargus.akpmsarservice.model.domain.AdjustmentLogWorkFlow;
import com.idsargus.akpmsarservice.model.domain.QAProductivitySampling;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = QAProductivitySamplingRepository.MODULE_NAME, collectionResourceRel = QAProductivitySamplingRepository.MODULE_NAME)
public interface QAProductivitySamplingRepository extends PagingAndSortingRepository<QAProductivitySampling, Integer> {

    public static final String MODULE_NAME = "QAProductivitySampling";

//	@CachePut
    @Override
    <S extends QAProductivitySampling> S save(S QAProductivitySampling);

//    @Query("SELECT *  FROM QAProductivitySampling i")
//    List<Integer> getProductivityIds();

    @RestResource(path = "findAllpaymentproductivity", rel = "findAllpaymentproductivity")
    @Query(value= "SELECT i FROM QAProductivitySampling i where " +
            " CONCAT(i.remarks, i.cptCount) LIKE %:query% AND" +
            " (:createdFrom is null or i.createdOn >= :createdFrom) AND " +
            " (:createdTo is null or i.createdOn <= :createdTo) AND " +
            " (:createdBy is null or i.createdBy.id = :createdBy) ")
    Page<QAProductivitySampling> findAllPaymentProductivity(@Param("query") String query, @Param("createdBy") String createdBy, @Param("createdFrom") String createdFrom,
                                                              @Param("createdTo") String createdTo, Pageable pageable);
}
