package com.idsargus.akpmsarservice.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import javax.persistence.criteria.Order;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.idsargus.akpmsarservice.exception.AppException;
import com.idsargus.akpmsarservice.model.domain.ArFiles;
import com.idsargus.akpmsarservice.model.domain.ProcessManual;
import com.idsargus.akpmsarservice.util.AkpmsUtil;
import com.idsargus.akpmsarservice.util.Constants;

@Repository
@Transactional
public class ProcessManualRepositoryImpl implements ProcessManualRepository {
    private static final Logger log = LoggerFactory.getLogger(ProcessManualRepositoryImpl.class);
    @Autowired
    private EntityManager em;
    private static final String DELETE_MULTI_STATUS = " UPDATE ProcessManual as pm SET pm.modifiedBy = :modifiedBy, pm.modifiedOn = :modifiedOn, pm.deleted = 1 WHERE pm.id IN :ids";
    private static final String DELETE_SINGLE_STATUS = "UPDATE ProcessManual as pm SET pm.modifiedBy = :modifiedBy, pm.modifiedOn = :modifiedOn, pm.deleted = 1 WHERE pm.id = :id or pm.parent.id = :parent_id";
    private static final String CHANGE_STATUS_INACTIVE = "UPDATE ProcessManual as pm SET pm.modifiedBy = :modifiedBy, pm.modifiedOn = :modifiedOn, modificationSummary = null, pm.status = 0 WHERE pm.id= :id or pm.parent.id = :parent_id";
    private static final String CHANGE_STATUS_ACTIVE = "UPDATE ProcessManual as pm SET pm.modifiedBy = :modifiedBy, pm.modifiedOn = :modifiedOn, modificationSummary = null, pm.status = 1 WHERE pm.id= :id ";

    @Autowired
    private ArProcessManualListAllRepository arProcessManualListAllRepository;
    public ProcessManualRepositoryImpl() {
    }

    public ProcessManual getProcessManualById(Integer processManualId, boolean loadDependancies, Boolean activeOnly) throws AppException {
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery<ProcessManual> criteria = builder.createQuery(ProcessManual.class);
        Root<ProcessManual> from = criteria.from(ProcessManual.class);
        List<Predicate> predicateList = new ArrayList();
        predicateList.add(builder.and(new Predicate[]{builder.equal(from.get("deleted"), 0)}));
        predicateList.add(builder.and(new Predicate[]{builder.equal(from.get("id"), processManualId)}));
        if (activeOnly) {
            predicateList.add(builder.and(new Predicate[]{builder.equal(from.get("status"), Constants.BOOLEAN_ACTIVATE)}));
        }

        criteria.select(from).where((Predicate[])predicateList.toArray(new Predicate[predicateList.size()])).orderBy(new Order[]{builder.asc(from.get("position"))});
        TypedQuery<ProcessManual> query = this.em.createQuery(criteria);
        ProcessManual processManual = (ProcessManual)query.getSingleResult();
        if (processManual != null && loadDependancies) {
            List<Predicate> subPredicateList = new ArrayList();
            subPredicateList.add(builder.and(new Predicate[]{builder.equal(from.get("deleted"), 0)}));
            subPredicateList.add(builder.and(new Predicate[]{builder.equal(from.get("parent").get("id"), processManual.getId())}));
            if (activeOnly) {
                subPredicateList.add(builder.and(new Predicate[]{builder.equal(from.get("status"), Constants.BOOLEAN_ACTIVATE)}));
            }

            criteria.select(from).where((Predicate[])subPredicateList.toArray(new Predicate[subPredicateList.size()])).orderBy(new Order[]{builder.asc(from.get("position"))});
            List<ProcessManual> subProcessManualList = this.em.createQuery(criteria).getResultList();
            processManual.setProcessManualList(subProcessManualList);
            Hibernate.initialize(processManual.getUserList());
            Iterator var12 = processManual.getProcessManualList().iterator();

            while(var12.hasNext()) {
                ProcessManual subProcessManual = (ProcessManual)var12.next();
                Hibernate.initialize(subProcessManual.getFiles());
                Hibernate.initialize(subProcessManual.getUserList());
            }

            Hibernate.initialize(processManual.getDepartments());
            Hibernate.initialize(processManual.getFiles());
        }

        return processManual;
    }

    public ProcessManual getProcessManualById(Integer processManualId, List<String> loadDependancies) throws AppException {
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery<ProcessManual> criteria = builder.createQuery(ProcessManual.class);
        Root<ProcessManual> from = criteria.from(ProcessManual.class);
        criteria.select(from).where(builder.and(builder.equal(from.get("id"), processManualId), builder.equal(from.get("deleted"), 0))).orderBy(new Order[]{builder.asc(from.get("position"))});
        TypedQuery<ProcessManual> query = this.em.createQuery(criteria);
        ProcessManual processManual = (ProcessManual)query.getSingleResult();
        if (processManual != null && loadDependancies != null && !loadDependancies.isEmpty()) {
            Iterator var8 = loadDependancies.iterator();

            while(true) {
                while(var8.hasNext()) {
                    String dependency = (String)var8.next();
                    if (dependency.equalsIgnoreCase("departments")) {
                        Hibernate.initialize(processManual.getDepartments());
                    } else if (dependency.equalsIgnoreCase("users")) {
                        Hibernate.initialize(processManual.getUserList());
                    } else if (dependency.equalsIgnoreCase("uploads")) {
                        Hibernate.initialize(processManual.getFiles());
                    } else if (dependency.equalsIgnoreCase("subProcessManuals")) {
                        Hibernate.initialize(processManual.getProcessManualList());
                        Iterator var10 = processManual.getProcessManualList().iterator();

                        while(var10.hasNext()) {
                            ProcessManual subProcessManual = (ProcessManual)var10.next();
                            Hibernate.initialize(subProcessManual.getFiles());
                            Hibernate.initialize(subProcessManual.getUserList());
                        }
                    }
                }

                return processManual;
            }
        } else {
            return processManual;
        }
    }

    public List<ProcessManual> getAllProcessManuals(Boolean activeOnly, boolean loadDependancies) throws AppException {
        log.info("in list manual : model");
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery<ProcessManual> criteria = builder.createQuery(ProcessManual.class);
        Root<ProcessManual> from = criteria.from(ProcessManual.class);
        List<Predicate> predicateList = new ArrayList();
        predicateList.add(builder.and(new Predicate[]{builder.equal(from.get("deleted"), 0)}));
        predicateList.add(builder.and(new Predicate[]{builder.isNull(from.get("parent").get("id"))}));
//        if (activeOnly) {
//            predicateList.add(builder.and(new Predicate[]{builder.equal(from.get("status"), Constants.BOOLEAN_ACTIVATE)}));
//        }

        criteria.select(from).where((Predicate[])predicateList.toArray(new Predicate[predicateList.size()])).orderBy(new Order[]{builder.asc(from.get("id"))});
        List<ProcessManual> processManualList = this.em.createQuery(criteria).getResultList();
        if (processManualList != null) {
            Iterator var8 = processManualList.iterator();

            while(var8.hasNext()) {
                ProcessManual processManual = (ProcessManual)var8.next();
                List<Predicate> subPredicateList = new ArrayList();
                subPredicateList.add(builder.and(new Predicate[]{builder.equal(from.get("deleted"), 0)}));
                subPredicateList.add(builder.and(new Predicate[]{builder.equal(from.get("parent").get("id"), processManual.getId())}));
//                if (activeOnly) {
//                    subPredicateList.add(builder.and(new Predicate[]{builder.equal(from.get("status"), Constants.BOOLEAN_ACTIVATE)}));
//                }

                criteria.select(from).where((Predicate[])subPredicateList.toArray(new Predicate[subPredicateList.size()]))
                        .orderBy(new Order[]{builder.asc(from.get("position"))});
                List<ProcessManual> subProcessManualList = this.em.createQuery(criteria).getResultList();
                if (subProcessManualList != null && !subProcessManualList.isEmpty()) {
                    Iterator var12 = subProcessManualList.iterator();

                    while(var12.hasNext()) {
                        ProcessManual subProcessManual = (ProcessManual)var12.next();
                        Hibernate.initialize(subProcessManual.getCreatedBy());
                    }
                }

                processManual.setProcessManualList(subProcessManualList);
                if(processManual.getCreatedBy() != null){
                //    Hibernate.initialize(processManual.getCreatedBy());
                }

                Hibernate.initialize(processManual.getDepartments());
            }
        }

        log.info("result count:" + processManualList.size());
        log.info(processManualList.toString());
        return processManualList;
    }

    public List<ProcessManual> getAllProcessManuals(Boolean activeOnly, boolean loadDependancies, String keyword) throws AppException {
        log.info("in search manual : model");
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery<ProcessManual> criteria = builder.createQuery(ProcessManual.class);
        Root<ProcessManual> from = criteria.from(ProcessManual.class);
        List<Predicate> predicateList = new ArrayList();
        predicateList.add(builder.and(new Predicate[]{builder.equal(from.get("deleted"), 0)}));
        if (activeOnly) {
            predicateList.add(builder.and(new Predicate[]{builder.equal(from.get("status"), Constants.BOOLEAN_ACTIVATE)}));
        }

        if (keyword != null) {
            Predicate p = builder.or(builder.like(from.get("title"), "%" + keyword + "%"),
                    builder.like(from.get("content"), "%" + keyword + "%"));
            predicateList.add(builder.and(new Predicate[]{p}));
        }

        criteria.select(from).where((Predicate[])predicateList.toArray(new Predicate[predicateList.size()]));
        List<ProcessManual> processManualList = this.em.createQuery(criteria).getResultList();
        log.info("result count:" + processManualList.size());
        return processManualList;
    }

    public ProcessManual saveProcessManual(ProcessManual processManual) throws AppException {
        this.em.persist(processManual);
        return processManual;
    }

    public ProcessManual updateProcessManual(ProcessManual processManual) throws AppException {
       return  this.em.merge(processManual);
       // return processManual;
    }

    public int deleteProcessManuals(List<Integer> ids) throws AppException {
        Query query = this.em.createQuery(" UPDATE ProcessManual as pm SET pm.modifiedBy = :modifiedBy, pm.modifiedOn = :modifiedOn, pm.deleted = 1 WHERE pm.id IN :ids");
        query.setParameter("modifiedBy", AkpmsUtil.getLoggedInUser());
        query.setParameter("modifiedOn", new Date());
        query.setParameter("ids", ids);
        return query.executeUpdate();
    }

    public int updateProcessManualsPositons(Double position, Integer parentId) throws AppException {
        Query query = this.em.createNativeQuery("update process_manual as pm set pm.position = (pm.position+1) where pm.parent_id = " + parentId + " and pm.position > " + position);
        return query.executeUpdate();
    }

    public int updatePositionById(Double position, Integer Id) throws AppException {
        Query query = this.em.createNativeQuery("update process_manual as pm set pm.position = " + position + " where pm.id = " + Id);
        return query.executeUpdate();
    }

    public int deleteProcessManuals(Integer id) throws AppException {
        Query query = this.em.createQuery("UPDATE ProcessManual as pm SET pm.modifiedBy = :modifiedBy, pm.modifiedOn = :modifiedOn, pm.deleted = 1 WHERE pm.id = :id or pm.parent.id = :parent_id");
        query.setParameter("id", id);
        query.setParameter("parent_id", id);
        query.setParameter("modifiedBy", (Object)null);
        query.setParameter("modifiedOn", new Date());
        log.info("SQL:" + query.toString());
        return query.executeUpdate();
    }

    public int changeStatus(Integer id, Boolean status) throws AppException {
        log.info("in change status, process manual impl");
        Query query;
        if (!status) {
            query = this.em.createQuery("UPDATE ProcessManual as pm SET pm.modifiedBy = :modifiedBy, pm.modifiedOn = :modifiedOn, modificationSummary = null, pm.status = 0 WHERE pm.id= :id or pm.parent.id = :parent_id");
            query.setParameter("id", id);
            query.setParameter("parent_id", id);
            query.setParameter("modifiedBy", (Object)null);
            query.setParameter("modifiedOn", new Date());
            return query.executeUpdate();
        } else {
            query = this.em.createQuery("UPDATE ProcessManual as pm SET pm.modifiedBy = :modifiedBy, pm.modifiedOn = :modifiedOn, modificationSummary = null, pm.status = 1 WHERE pm.id= :id ");
            query.setParameter("id", id);
            query.setParameter("modifiedBy", (Object)null);
            query.setParameter("modifiedOn", new Date());
            return query.executeUpdate();
        }
    }

    public void saveAttachement(ArFiles file) throws AppException {
        this.em.persist(file);
    }

    public ArFiles getAttachedFile(Integer id) throws AppException {
        return (ArFiles)this.em.find(ArFiles.class, id);
    }

    public Long getTotalProcessManuals() {
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery<Long> cQuery = builder.createQuery(Long.class);
        Root<ProcessManual> from = cQuery.from(ProcessManual.class);
        CriteriaQuery<Long> select = cQuery.select(builder.count(from));
        select.where(builder.and(builder.equal(from.get("status"), 1), builder.equal(from.get("deleted"), 0)));
        return (Long)this.em.createQuery(select).getSingleResult();
    }

    public Long getTotalProcessManualReadByUser(Integer userId) {
        StringBuilder sb = new StringBuilder();
        sb.append(" Select count(*) from process_manual_user_rel ");
        sb.append(" left join process_manual pm on pm.id = process_manual_user_rel.process_manual_id ");
        sb.append(" where user_id=? and pm.is_deleted = 0 and pm.status =  1");
        Query query = this.em.createNativeQuery(sb.toString());
        query.setParameter(1, userId);
        return Long.parseLong(query.getSingleResult().toString());
    }

    public ProcessManual findByName(String name) throws AppException {
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery<ProcessManual> criteria = builder.createQuery(ProcessManual.class);
        Root<ProcessManual> processManual = criteria.from(ProcessManual.class);
        criteria.select(processManual).where(new Predicate[]{builder.equal(processManual.get("title"), name), builder.equal(processManual.get("deleted"), Constants.BOOLEAN_NON_DELETED)});
        return (ProcessManual)this.em.createQuery(criteria).getSingleResult();
    }

    public int deleteAttachedFile(Integer id) throws AppException {
        Query query = this.em.createQuery("UPDATE  Files  SET deleted = true WHERE id = " + id);
        return query.executeUpdate();
    }

    public List<Object[]> getProcessManualByDepartments(List<Integer> departmentIds) throws AppException {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        sb.append(" SELECT COUNT(*),parent_id from process_manual where process_manual.parent_id IN ");
        sb.append(" ( ");
        sb.append(" select distinct(process_manual.id) from process_manual ");
        sb.append(" left join process_manual_department_rel on process_manual.id = process_manual_department_rel.process_manual_id ");
        sb.append(" where process_manual_department_rel.department_id IN ");
        sb.append(" ( ");
        if (departmentIds.isEmpty()) {
            sb.append(" 0 ");
        } else {
            for(Iterator var4 = departmentIds.iterator(); var4.hasNext(); ++i) {
                Integer id = (Integer)var4.next();
                if (i == 0) {
                    sb.append(id);
                } else {
                    sb.append(" , " + id);
                }
            }
        }

        sb.append(" ) ");
        sb.append(" AND process_manual.parent_id is null and process_manual.is_deleted = 0 and process_manual.`status`=1) ");
        sb.append(" and process_manual.is_deleted = 0 and process_manual.`status`=1 group by process_manual.parent_id ");
        sb.append(" WITH ROLLUP ");
        Query query = this.em.createNativeQuery(sb.toString());
        return query.getResultList();
    }

    public <S extends ProcessManual> S save(S entity) {
        return null;
    }

    public <S extends ProcessManual> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    public Optional<ProcessManual> findById(Integer id) {
        return null;
    }

    public boolean existsById(Integer id) {
        return false;
    }

    public Iterable<ProcessManual> findAll() {
        return null;
    }

    public Iterable<ProcessManual> findAllById(Iterable<Integer> ids) {
        return null;
    }

    public long count() {
        return 0L;
    }

    public void deleteById(Integer id) {
    }

    public void delete(ProcessManual entity) {
    }

    public void deleteAll(Iterable<? extends ProcessManual> entities) {
    }

    public void deleteAll() {
    }



}

