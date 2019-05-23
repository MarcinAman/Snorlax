package com.mycompany.myapp.service.reservation;

import com.mycompany.myapp.domain.Pool;
import com.mycompany.myapp.domain.Reservation;
import com.mycompany.myapp.repository.PoolRepository;
import com.mycompany.myapp.repository.ReservationRepository;
import com.mycompany.myapp.service.MailService;
import com.mycompany.myapp.service.UserService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final PoolRepository poolRepository;

    private UserService userService;

    private MailService mailService;

    public ReservationService(ReservationRepository reservationRepository, PoolRepository poolRepository, UserService userService, MailService mailService){
        this.poolRepository = poolRepository;
        this.reservationRepository = reservationRepository;
        this.userService = userService;
        this.mailService = mailService;
    }

    public Reservation reserve(String poolId, int count, Date from, Date to) throws Exception {
        Pool pool = poolRepository.getFullByIdWithReservation(poolId);
        int alreadyReserved =  getAlreadyReservedCount(pool.getReservations(), from, to);
        if (count <= pool.getMaximumCount() - alreadyReserved && pool.isEnabled()) {
            return reservationRepository.save(new Reservation(
                userService.getUserWithAuthorities().get(),
                pool,
                count,
                from ,
                to
            ));
        } else {
            throw new Exception("unable to make reservation");
        }
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public List<Reservation> getAllByPoolId(String poolId) {
        Pool pool = poolRepository.getFullByIdWithReservation(poolId);
        if (pool == null) {
            return new ArrayList<>();
        }
        return pool.getReservations();
    }

    public void sendToolsRequest(String poolId, List<String> selectedTools) throws Exception{
        mailService.sendToolsRequestEmail(userService.getUserWithAuthorities().get(), poolId, selectedTools);
    }

    public int getActiveOrInFutureReservedCount(String poolId) {
        return getActiveOrInFutureReservedCount(getAllByPoolId(poolId), new Date());
    }

    private int getActiveOrInFutureReservedCount(List<Reservation> reservation, Date now) {
        return reservation
            .stream()
            .filter(r -> isActiveOrInFuture(r, now))
            .map(Reservation::getCount)
            .reduce(Integer::sum)
            .orElse(0);
    }

    private int getAlreadyReservedCount(List<Reservation> reservation, Date from, Date to) {
        return reservation
            .stream()
            .filter(r -> isOverlaping(r, from, to))
            .map(r -> r.getCount())
            .reduce((r1, r2) -> r1 + r2)
            .orElse(0);
    }

    private boolean isOverlaping(Reservation reservation, Date from, Date to) {
        return !(reservation.getFrom().compareTo(to) >= 0  || reservation.getTo().compareTo(from) < 0 );
    }

    private boolean isActiveOrInFuture(Reservation reservation, Date now) {
        return reservation.getTo().compareTo(now) >=0;
    }

}
