package com.m2s.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.m2s.entities.User;

@Repository
@Transactional("mysqlTransactionManager")
public interface UserRepository extends JpaRepository<User, Long>{
	
}
