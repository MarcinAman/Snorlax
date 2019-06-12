package com.mycompany.myapp.web.rest.statistics;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.UserTimeUsageStatistics;
import com.mycompany.myapp.domain.VirtualMachineTimeUsageStatistics;
import com.mycompany.myapp.service.statistics.TimeStatisticsService;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Optional;

@RestController
@RequestMapping("/api/statistics/time/")
public class TimeStatisticsResource {

    private final TimeStatisticsService timeStatisticsService;

    public TimeStatisticsResource(TimeStatisticsService timeStatisticsService) {
        this.timeStatisticsService = timeStatisticsService;
    }

    @GetMapping("/user-usage/{id}")
    @Timed
    public ResponseEntity<UserTimeUsageStatistics> getUserStatistics(
        @PathVariable("id") Long userId) throws ParseException {
        return ResponseUtil.wrapOrNotFound(
            Optional.ofNullable(
                timeStatisticsService.getUserStatistics(userId)
            )
        );
    }

    @GetMapping("/vm-usage/{id}")
    @Timed
    public ResponseEntity<VirtualMachineTimeUsageStatistics> getVmStatistics(
        @PathVariable("id") String poolId) throws ParseException {
        return ResponseUtil.wrapOrNotFound(
            Optional.ofNullable(
                timeStatisticsService.getVmStatistics(poolId)
            )
        );
    }
}
