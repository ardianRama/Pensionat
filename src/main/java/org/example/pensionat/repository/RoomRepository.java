package org.example.pensionat.repository;

import org.example.pensionat.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    boolean existsByRoomNumber(int roomNumber);

    @Query("SELECT r FROM Room r WHERE r.id NOT IN (" +
            "SELECT b.room.id FROM Booking b WHERE b.checkOut > :checkIn AND b.checkIn < :checkOut)")
    List<Room> findRoomsAvailableInPeriod(@Param("checkIn") LocalDate checkIn,
                                          @Param("checkOut") LocalDate checkOut);
}
