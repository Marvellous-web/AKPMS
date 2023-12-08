//package com.idsargus.akpmsarservice.repository;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//
//import com.idsargus.akpmscommonservice.entity.ArProductivityEntity;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class ArProductivityRepositoryImpl implements ArProductivityRepositoryCustom {
//
//	@PersistenceContext
//	private EntityManager em;
//
//	@Override
//	public List<ArProductivityEntity> search(String query, Integer page, Integer size) throws Exception {
//
////		TypedQuery<ArProductivityEntity> typedQuery = em.createQuery(
////				"SELECT u FROM ArProductivityEntity AS u LEFT JOIN u.departments d " + query,
////				ArProductivityEntity.class);
//
//		TypedQuery<ArProductivityEntity> typedQuery = em.createQuery("SELECT u FROM ArProductivityEntity AS u " + query,
//				ArProductivityEntity.class);
//
//		typedQuery.setFirstResult(page - 1);
//		typedQuery.setMaxResults(size);
//
//		log.info("PAGE: {}, SIZE: {}", page, size);
//
//		return typedQuery.getResultList();
//	}
//
//	@Override
//	public Long count(String query) throws Exception {
//
////		TypedQuery<Integer> typedQuery = em.createQuery(
////				"SELECT COUNT(*) FROM ArProductivityEntity AS u LEFT JOIN u.departments d" + query, Integer.class);
//
//		TypedQuery<Long> typedQuery = em.createQuery("SELECT COUNT(*) FROM ArProductivityEntity AS u" + query,
//				Long.class);
//
//		return typedQuery.getSingleResult().longValue();
//	}
//
//}
