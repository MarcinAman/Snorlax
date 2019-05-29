package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.VMUserUsageStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface VMUserUsageStatisticsRepository extends JpaRepository<VMUserUsageStatistics, Long> {
    @Query(value = "select u.LOGIN as login, (select count(*) from reservation as r inner join pool as p on r.POOL_ID = p.POOL_ID and r.USER_ID = u.ID where r.DATE_TO < :to and r.DATE_FROM > :from) as times_used from jhi_user as u ", nativeQuery = true)
    List<VMUserUsageStatistics> findByPeriod(@Param("from") Date from, @Param("to") Date to);
}
