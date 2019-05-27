package com.mycompany.myapp.service.statistics;

import com.mycompany.myapp.domain.VMUsageStatistics;
import com.mycompany.myapp.domain.VMUserUsageStatistics;
import com.mycompany.myapp.repository.VMUserUsageStatisticsRepository;
import com.mycompany.myapp.repository.VmUsageStatisticsRepository;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class StatisticsService {
    private final VMUserUsageStatisticsRepository vmUserUsageStatisticsRepository;
    private final VmUsageStatisticsRepository vmUsageStatisticsRepository;

    public StatisticsService(VMUserUsageStatisticsRepository vmUserUsageStatisticsRepository,
                             VmUsageStatisticsRepository vmUsageStatisticsRepository) {
        this.vmUserUsageStatisticsRepository = vmUserUsageStatisticsRepository;
        this.vmUsageStatisticsRepository = vmUsageStatisticsRepository;
    }

    public List<VMUsageStatistics> getVMUsageStatistics(Date from, Date to) {
        return vmUsageStatisticsRepository.findByPeriod(from, to);
    }

    public List<VMUserUsageStatistics> getVMUserUsageStatistics(Date from, Date to){
        return vmUserUsageStatisticsRepository.findByPeriod(from, to);
    }
}
