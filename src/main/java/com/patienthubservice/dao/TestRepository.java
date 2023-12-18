package com.patienthubservice.dao;

import com.patienthubservice.entities.DiagnosticTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<DiagnosticTest, Integer>{
	DiagnosticTest findBytestName(String testName);
}
