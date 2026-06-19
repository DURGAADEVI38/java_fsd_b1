package com.proj.demo.repo;

import com.proj.demo.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerRepo extends JpaRepository<Employer,Integer> {
}
