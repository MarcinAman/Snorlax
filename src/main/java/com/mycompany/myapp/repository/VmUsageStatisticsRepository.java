package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.VMUsageStatistics;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface VmUsageStatisticsRepository extends JpaRepository<VMUsageStatistics, Long> {
    @Query(value = "select p.POOL_ID as pool_id, p.DISPLAY_NAME as name, (select count(*) from reservation as r where r.pool_id = p.pool_id and r.DATE_TO < :to and r.DATE_FROM > :from) as times_used from pool as p", nativeQuery = true)
    List<VMUsageStatistics> findByPeriod(@Param("from") Date from,@Param("to") Date to);
}
