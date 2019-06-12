package com.mycompany.myapp.service.statistics;

import com.mycompany.myapp.domain.AmountAtTime;
import com.mycompany.myapp.domain.Reservation;
import com.mycompany.myapp.domain.UserTimeUsageStatistics;
import com.mycompany.myapp.domain.VirtualMachineTimeUsageStatistics;
import com.mycompany.myapp.service.reservation.ReservationService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeStatisticsService {

    private ReservationService reservationService;

    public TimeStatisticsService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public UserTimeUsageStatistics getUserStatistics(Long userId) {
        List<Reservation> reservations = reservationService.getAllByUserId(userId);
        return new UserTimeUsageStatistics(userId, getGraphFromReservations(reservations));
    }

    public VirtualMachineTimeUsageStatistics getVmStatistics(String poolId){
        List<Reservation> reservations = reservationService.getAllByPoolId(poolId);
        return new VirtualMachineTimeUsageStatistics(poolId, getGraphFromReservations(reservations));
    }

    private List<AmountAtTime> getGraphFromReservations( List<Reservation> reservations) {
        List<AmountAtTime> from = reservations.stream().map(res -> new AmountAtTime(res.getFrom(), res.getCount()))
            .collect(Collectors.toList());
        List<AmountAtTime> to = reservations.stream()
            .map(res -> new AmountAtTime(res.getFrom(), -1 * res.getCount()))
            .collect(Collectors.toList());
        from.addAll(to);
        List<AmountAtTime> graph = from.stream().sorted(Comparator.comparing(AmountAtTime::getDate))
            .collect(Collectors.toList());
        int agg = 0;
        for (AmountAtTime amount: graph){
            amount.setAmount(amount.getAmount() + agg);
            agg = amount.getAmount();
        }
        return graph;
    }
}
