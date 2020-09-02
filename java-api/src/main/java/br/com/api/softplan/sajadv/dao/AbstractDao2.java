/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.api.softplan.sajadv.dao;

import java.io.Serializable;
import java.util.List;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author lucas
 */
public abstract class AbstractDao2<T> implements Serializable {

    protected EntityManager entityManager;

    @PersistenceContext
    abstract void setEntityManager(EntityManager entityManager);

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
        return (T) query.getSingleResult();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public T buscarEntidade(Class<T> entityClass, Object primaryKey) {
        return entityManager.find(entityClass, primaryKey);
    }

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

}
