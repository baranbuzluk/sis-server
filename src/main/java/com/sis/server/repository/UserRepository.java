package com.sis.server.repository;

import com.sis.server.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, Integer> {
  User findByUsername(String username);
}
