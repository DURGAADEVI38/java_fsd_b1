package com.proj.demo.repo;

import com.proj.demo.model.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApplicationRepo extends JpaRepository<Application,Integer> {
    @Query("""
            select a
            from Application a
            where a.seeker.name=?1
            """)
    Page<Application> getByUsername(String name, Pageable pageable);
}
