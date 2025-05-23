package org.example.pensionat.repository;

import org.example.pensionat.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    //hanterar utcheckningsdatumet som ledigt
    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId AND b.checkOut > :checkIn AND b.checkIn < :checkOut")
    List<Booking> findOverlappingBookings(@Param("roomId") Long roomId,
                                          @Param("checkIn") LocalDate checkIn,
                                          @Param("checkOut") LocalDate checkOut);

}
