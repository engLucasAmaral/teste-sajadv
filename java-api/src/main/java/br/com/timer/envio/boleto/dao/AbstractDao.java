/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.api.softplan.sajadv.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

/**
 *
 * @author lucas
 */
abstract class AbstractDao<T> implements Serializable {

    protected EntityManager entityManager;

    @PersistenceContext
    abstract void setEntityManager(EntityManager entityManager);

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public T insert(T t) {
        return entityManager.merge(t);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public T update(T t) {
        return entityManager.merge(t);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void remove(T t) {
        entityManager.remove(t);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public T buscarEntidade(String stringQuery, TypeQuery type, Object... params) {

        Query query;
        switch (type) {
            case NAMED_QUERY:
                query = entityManager.createNamedQuery(stringQuery);
                break;
            case NATIVE_QUERY:
                query = entityManager.createQuery(stringQuery);
                break;
            default:
                query = entityManager.createNamedQuery(stringQuery);
                break;
        }
        if (params.length > 0) {
            setParameters(query, type, params);
        }
        query.setMaxResults(1);
        try {
            return (T) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public T buscarEntidade(Class<T> entityClass, Object primaryKey) {
        return entityManager.find(entityClass, primaryKey);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<T> buscarLista(String stringQuery, TypeQuery type, Object... params) {

        Query query;
        switch (type) {
            case NAMED_QUERY:
                query = entityManager.createNamedQuery(stringQuery);
                break;
            case NATIVE_QUERY:
                query = entityManager.createQuery(stringQuery);
                break;
            default:
                query = entityManager.createNamedQuery(stringQuery);
                break;
        }
        if (params.length > 0) {
            setParameters(query, type, params);;
        }

        return query.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<T> buscarListaPaginada(String stringQuery, Integer startPosition, Integer maxResult, TypeQuery type, Object... params) {

        Query query;
        switch (type) {
            case NAMED_QUERY:
                query = entityManager.createNamedQuery(stringQuery);
                break;
            case NATIVE_QUERY:
                query = entityManager.createQuery(stringQuery);
                break;
            default:
                query = entityManager.createNamedQuery(stringQuery);
                break;
        }
        if (params.length > 0) {
            setParameters(query, type, params);
        }
        query.setFirstResult(startPosition);
        query.setMaxResults(maxResult);

        return query.getResultList();
    }

    private void setParameters(Query query, TypeQuery type, Object... params) {
        int index = 0;
        for (Object obj : params) {
            switch (type) {
                case NAMED_QUERY:
                    query.setParameter("p".concat(String.valueOf(index)), obj);
                    break;
                case NATIVE_QUERY:
                    query.setParameter(index, obj);
                    break;
            }
            index++;
        }
    }

    public int atualizarLote(Class<T> entidade, Map<String, Object> fieldsUpdate, List<Long> filtersUpdate) throws Exception {
        int numRowsAffected = 0;
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaUpdate<T> update = builder.createCriteriaUpdate(entidade);
            Root root = update.from(entidade);
            fieldsUpdate.entrySet().forEach(obj -> {
                update.set(obj.getKey(), obj.getValue());
            });
            update.where(root.in(filtersUpdate));
            Query query = entityManager.createQuery(update);
            numRowsAffected = query.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
        return numRowsAffected;
    }

    public int atualizarLote2(T domainClass, Map<String, Object> fieldsUpdate, List<Long> filtersUpdate) throws Exception {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaUpdate<T> q = criteriaBuilder.createCriteriaUpdate((Class<T>) domainClass);

        Root<T> root = q.from((Class<T>) domainClass);
        fieldsUpdate.entrySet().forEach(obj -> {
            q.set(obj.getKey(), obj.getValue());
        });

//         update.set(root.get("id"), true).where(root.get("id").in(filtersUpdate.values()));
//         q./*set(root.get("id"), true).*/where(root.get("id").in(filtersUpdate));// FUNCIONANDO
        q.where(root.in(filtersUpdate));
//        final List<Predicate> predicates = new ArrayList<Predicate>();
////        predicates.add(cb.equal(root.<ID>get("id"), 1));
////        predicates.add(cb.equal(root.in(filtersUpdate.values())));
//
////        update.where(root.in(filtersUpdate.values().toArray()));
////        update.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
//        update.where(root.in(predicates.toArray(new Predicate[predicates.size()])));
//        update.where(criteriaBuilder.in(root.get(""),filtersUpdate.values().toArray())));
        int numrow = entityManager.createQuery(q).executeUpdate();

        return numrow;
    }

}
