package com.mycompany.myapp.service.reservation;

import com.mycompany.myapp.domain.PeriodicReservation;
import com.mycompany.myapp.domain.Pool;
import com.mycompany.myapp.domain.Reservation;
import com.mycompany.myapp.domain.ReservationLeftOut;
import com.mycompany.myapp.repository.PeriodicReservationRepository;
import com.mycompany.myapp.repository.PoolRepository;
import com.mycompany.myapp.repository.ReservationLeftOutRepository;
import com.mycompany.myapp.repository.ReservationRepository;
import com.mycompany.myapp.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PeriodicReservationService {

    private final PeriodicReservationRepository periodicReservationRepository;

    private final ReservationLeftOutRepository leftOutRepository;

    private final ReservationRepository reservationRepository;

    private final PoolRepository poolRepository;

    private final UserService userService;

    public PeriodicReservationService(PeriodicReservationRepository periodicReservationRepository,
                                      ReservationLeftOutRepository leftOutRepository,
                                      ReservationRepository reservationRepository,
                                      PoolRepository poolRepository, UserService userService) {
        this.periodicReservationRepository = periodicReservationRepository;
        this.leftOutRepository = leftOutRepository;
        this.reservationRepository = reservationRepository;
        this.poolRepository = poolRepository;
        this.userService = userService;
    }

    @Transactional
    public PeriodicReservation reserve(String poolId, int count, LocalDate fromPeriod, LocalDate toPeriod,
                                       LocalDateTime fromTime, LocalDateTime toTime, List<LocalDate> leftOuts) throws Exception {
        Pool pool = poolRepository.getFullByIdWithPeriodicReservation(poolId);
        PeriodicReservation pr = new PeriodicReservation(userService.getUserWithAuthorities().get(),
            pool, count, fromPeriod, toPeriod, fromTime, toTime);

        pr.setLeftOuts(leftOuts.stream()
            .map(e -> new ReservationLeftOut(pr, e))
            .collect(Collectors.toList()));
        int periodCount = getPeriodicReservedCount(pool.getPeriodicReservations(), fromTime, toTime, fromPeriod, toPeriod);
        int basicCount = getBasicReservedCount(pool.getReservations(), fromTime, toTime, fromPeriod, toPeriod);
        if (pool.isEnabled() && count <= pool.getMaximumCount() - periodCount - basicCount) {
            return periodicReservationRepository.save(pr);
        }
        else {
            throw new Exception("Unable to reserve");
        }

    }

    private int getPeriodicReservedCount(List<PeriodicReservation> reservations, LocalDateTime fromTime, LocalDateTime toTime,
                                         LocalDate fromPeriod, LocalDate toPeriod) {
        return reservations
            .stream()
            .filter(reservation -> isPeriodOverlap(reservation, fromPeriod, toPeriod))
            .filter(reservation -> isTimeOverlap(reservation, fromTime, toTime))
            .map(PeriodicReservation::getCount)
            .reduce((r1, r2) -> r1 + r2)
            .orElse(0);
    }

    private boolean isPeriodOverlap(PeriodicReservation reservation, LocalDate from, LocalDate to) {
        return !(from.isAfter(reservation.getToPeriod()) || to.isBefore(reservation.getFromPeriod()));
    }

    private boolean isTimeOverlap(PeriodicReservation reservation, LocalDateTime from, LocalDateTime to) {
        if (from.getDayOfWeek() != reservation.getFromTime().getDayOfWeek()) {
            return false;
        } else if (from.getHour() > reservation.getToTime().getHour() || to.getHour() < reservation.getFromTime().getHour()) {
            return false;
        } else if (
            (from.getHour() == reservation.getToTime().getHour() && from.getMinute() > reservation.getToTime().getMinute())
                || (to.getHour() == reservation.getFromTime().getHour() && to.getMinute() < reservation.getFromTime().getMinute())) {
            return false;
        } else {
            return true;
        }
    }

    private int getBasicReservedCount(List<Reservation> reservations, LocalDateTime fromTime, LocalDateTime toTime,
                                      LocalDate fromPeriod, LocalDate toPeriod) {
        Map<LocalDate, Integer> maxCount= new HashMap<>();
        for (Reservation r : reservations) {
            LocalDateTime rFrom = r.getFrom().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime rTo = r.getTo().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            if (rFrom.isAfter(fromPeriod.atStartOfDay()) && rTo.isBefore(toPeriod.plusDays(1).atStartOfDay())
                && rFrom.getDayOfWeek().equals(fromTime.getDayOfWeek())) {
                if (!(fromTime.getHour() > rTo.getHour() || toTime.getHour() < rFrom.getHour())){
                    if (!(fromTime.getHour() == rTo.getHour() && fromTime.getMinute() > rTo.getMinute()
                    || toTime.getHour() == rFrom.getHour() && toTime.getMinute() < rFrom.getMinute())) {
                        if (maxCount.containsKey(rFrom.toLocalDate())) {
                            maxCount.put(rFrom.toLocalDate(), maxCount.get(rFrom.toLocalDate()) + r.getCount());
                        } else {
                            maxCount.put(rFrom.toLocalDate(), r.getCount());
                        }
                    }
                }
            }
        }
        if (maxCount.values().isEmpty()) {
            return 0;
        }
        return Collections.max(maxCount.values());
    }

}
