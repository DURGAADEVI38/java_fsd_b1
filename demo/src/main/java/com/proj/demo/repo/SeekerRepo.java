package com.proj.demo.repo;

import com.proj.demo.model.Seeker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeekerRepo extends JpaRepository<Seeker,Integer> {
}
