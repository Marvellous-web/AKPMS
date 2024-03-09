//package com.idsargus.akpmsarservice.repository.productivity.workflow.requestForCheckTracer;
//
//import argus.domain.Files;
//import argus.domain.RequestCheckTracerWorkFlow;
//import argus.exception.ArgusException;
//
//import java.util.List;
//import java.util.Map;
//
//public interface RequestForCheckTracerDao
//{
//
//	/**
//    *
//    * @param id
//    * @return
//    */
//	RequestCheckTracerWorkFlow findById(Long id)  throws ArgusException;
//	/**
//	 *
//	 * @param name
//	 * @return
//	 */
//	RequestCheckTracerWorkFlow findByName(String name) throws ArgusException;
//
//    /**
//     *
//     * @param orderClauses key=(orderBy,sortBy,offset,limit)
//     * @param whereClauses
//     * @return
//     */
//
//    /**
//     * @param name It is the name of the Productivity to be searched
//     * @param id It is the id of the Productivity to be searched
//     */
//     List<RequestCheckTracerWorkFlow> findAll(Map<String,String> orderClauses, Map<String,String> whereClauses ,boolean dependancies)  throws ArgusException;
//
//    /**
//     *
//     * @param Productivity
//     */
//     void addCheckTracerWorkFlow(RequestCheckTracerWorkFlow checkTracerWorkFlow)  throws ArgusException;
//
//    /**
//     *
//     * @param Productivity
//     */
//     void updateCheckTracerWorkFlow(RequestCheckTracerWorkFlow checkTracerWorkFlow)  throws ArgusException;
//
//
//    /**
//     *
//     * @param conditionMap
//     * @return
//     */
//     int totalRecord(Map<String,String> conditionMap)  throws ArgusException;
//
//     /**
//      *
//      * @param arProdId
//      * @return
//      * @throws ArgusException
//      */
//     RequestCheckTracerWorkFlow getCheckTracerByArProductivityId(int arProdId) throws ArgusException;
//
//	/**
//	 *
//	 * @param file
//	 * @throws ArgusException
//	 */
//	void saveAttachement(Files file) throws ArgusException;
//
//	void updateAttachement(Files file) throws ArgusException;
//}
