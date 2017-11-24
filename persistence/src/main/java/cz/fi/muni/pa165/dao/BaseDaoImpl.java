package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.BaseEntity;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author jiritobias
 */
@Repository
public abstract class BaseDaoImpl<T extends BaseEntity> implements BaseDao<T> {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void create(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    public void update(T entity) {
        entityManager.merge(entity);
    }

    public T findByIdAndClass(Class<T> entityClass, Long id) {
        return entityManager.find(entityClass, id);
    }

    public void setEntitiManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
