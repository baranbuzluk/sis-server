package com.sis.server.repository;

import com.sis.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  User findByEmail(String email);

  User findByStudentNumber(Integer studentNumber);
}
