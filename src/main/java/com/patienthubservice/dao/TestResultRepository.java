package com.patienthubservice.dao;

import com.patienthubservice.entities.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestResultRepository extends JpaRepository<TestResult, Integer> {

}
