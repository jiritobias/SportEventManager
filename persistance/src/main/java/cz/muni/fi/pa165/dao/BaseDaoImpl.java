package cz.muni.fi.pa165.dao;

import org.springframework.stereotype.Repository;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public abstract class BaseDaoImpl<T> implements BaseDao<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public void create(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    public T findById(Class<T> entityClass, Long id) {
        return entityManager.find(entityClass, id);
    }

    public void setEntitiManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
