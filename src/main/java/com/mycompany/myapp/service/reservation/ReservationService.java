package com.mycompany.myapp.service.reservation;

import com.mycompany.myapp.domain.Pool;
import com.mycompany.myapp.domain.Reservation;
import com.mycompany.myapp.repository.PoolRepository;
import com.mycompany.myapp.repository.ReservationRepository;
import com.mycompany.myapp.service.MailService;
import com.mycompany.myapp.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Long reserve(String poolId, int count) throws Exception {
        Pool pool = poolRepository.getFullByIdWithReservation(poolId);
        int alreadyReserved =  getAlreadyReservedCount(pool.getReservations());
        if (count <= pool.getMaximumCount() - alreadyReserved && pool.isEnabled()) {
            Reservation z = reservationRepository.save(new Reservation(
                userService.getUserWithAuthorities().get(),
                pool,
                count
            ));
            return z.getId();
        } else {
            throw new Exception("unable to make reservation");
        }
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public List<Reservation> getAllByPoolId(String poolId) {
        Pool pool = poolRepository.getFullByIdWithReservation(poolId);
        return pool.getReservations();
    }

    public void sendToolsRequest(String poolId, List<String> selectedTools) throws Exception{
        mailService.sendToolsRequestEmail(userService.getUserWithAuthorities().get(), poolId, selectedTools);
    }

    private int getAlreadyReservedCount(List<Reservation> reservation) {
        return reservation.stream().map(r -> r.getCount()).reduce((r1, r2) -> r1 + r2).orElse(0);
    }

}
