package com.sis.server.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
  JPAQueryFactory queryFactory();

  <E> E saveEntity(E entity);

  <E> E findEntityById(Class<E> clazz, Object primaryKey);
}
