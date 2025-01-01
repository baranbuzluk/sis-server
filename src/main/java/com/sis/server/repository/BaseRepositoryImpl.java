package com.sis.server.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@NoRepositoryBean
public class BaseRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID>
    implements BaseRepository<T, ID> {
  private final JPAQueryFactory queryFactory;
  private final EntityManager entityManager;

  public BaseRepositoryImpl(
      JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
    super(entityInformation, entityManager);
    this.queryFactory = new JPAQueryFactory(entityManager);
    this.entityManager = entityManager;
  }

  @Override
  public final JPAQueryFactory queryFactory() {
    return queryFactory;
  }

  @Override
  @Transactional
  public final <E> E saveEntity(E entity) {
    Assert.notNull(entity, "Entity must not be null");
    try {
      entityManager.persist(entity);
      return entity;
    } catch (EntityExistsException e) {
      return entityManager.merge(entity);
    } finally {
      entityManager.flush();
    }
  }

  @Override
  public final <E> E findEntityById(Class<E> clazz, Object primaryKey) {
    return entityManager.find(clazz, primaryKey);
  }
}
