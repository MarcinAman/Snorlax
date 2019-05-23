package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Pool;
import com.mycompany.myapp.domain.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolRepository extends JpaRepository<Tool, String> {
    List<Tool> getAllByPoolIsNotIn(Pool pool);
}
