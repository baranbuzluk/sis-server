package com.sis.server.repository;

import com.sis.server.entity.LoginAttempt;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginAttemptRepository extends BaseRepository<LoginAttempt, Integer> {}
