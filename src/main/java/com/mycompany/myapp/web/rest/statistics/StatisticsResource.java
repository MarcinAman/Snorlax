package com.mycompany.myapp.web.rest.statistics;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.VMUsageStatistics;
import com.mycompany.myapp.domain.VMUserUsageStatistics;
import com.mycompany.myapp.service.statistics.StatisticsService;
import io.github.jhipster.web.util.ResponseUtil;
import org.joda.time.DateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/statistics/")
public class StatisticsResource {
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private final StatisticsService statisticsService;

    public StatisticsResource(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/vm-usage/{from}/{to}")
    @Timed
    public ResponseEntity<List<VMUsageStatistics>> getVmStatistics(
        @PathVariable("from") String from, @PathVariable("to") String to) throws ParseException {
        return ResponseUtil.wrapOrNotFound(
            Optional.ofNullable(
                statisticsService.getVMUsageStatistics(format.parse(from), format.parse(to))
            )
        );
    }

    @GetMapping("/vm-user-usage/{from}/{to}")
    @Timed
    public ResponseEntity<List<VMUserUsageStatistics>> getVmUserStatistics(
        @PathVariable("from") String from, @PathVariable("to") String to) throws ParseException {
        return ResponseUtil.wrapOrNotFound(
            Optional.ofNullable(
                statisticsService.getVMUserUsageStatistics(format.parse(from), format.parse(to))
            )
        );
    }
}
