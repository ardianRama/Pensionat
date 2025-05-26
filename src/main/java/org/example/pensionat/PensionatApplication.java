package org.example.pensionat;

import org.example.pensionat.models.Booking;
import org.example.pensionat.models.Customer;
import org.example.pensionat.models.Room;
import org.example.pensionat.models.RoomType;
import org.example.pensionat.repository.BookingRepository;
import org.example.pensionat.repository.CustomerRepository;
import org.example.pensionat.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class PensionatApplication {

    public static void main(String[] args) {
        SpringApplication.run(PensionatApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataCustomer(CustomerRepository customerRepository, RoomRepository roomRepository,
                                          BookingRepository bookingRepository){
        return args -> {
            Customer c1 = new Customer("Kalle","kalle@gmail.com","0704040728","Blåbärsstigen 12");
            Customer c2 = new Customer("Donald","donald@gmail.com","0704040768","Kullavägen 7");
            Customer c3 = new Customer("Macron","macron@gmail.com","0704040712","Solrosgatan 3");
            Customer c4 = new Customer("Doris","doris@gmail.com","0704040732","Måsgränd 4");
            customerRepository.save(c1);
            customerRepository.save(c2);
            customerRepository.save(c3);
            customerRepository.save(c4);

            Room r1 = new Room(101, RoomType.SINGLEROOM,1,0);
            Room r2 = new Room(102, RoomType.SINGLEROOM,1,0);
            Room r3 = new Room(103, RoomType.SINGLEROOM,1,0);
            Room r4 = new Room(104, RoomType.SINGLEROOM,1,0);
            Room r5 = new Room(105, RoomType.DOUBLEROOM,2,2);
            Room r6 = new Room(106, RoomType.DOUBLEROOM,2,2);
            Room r7 = new Room(107, RoomType.DOUBLEROOM,2,2);
            Room r8 = new Room(108, RoomType.DOUBLEROOM,2,2);
            roomRepository.save(r1);
            roomRepository.save(r2);
            roomRepository.save(r3);
            roomRepository.save(r4);
            roomRepository.save(r5);
            roomRepository.save(r6);
            roomRepository.save(r7);
            roomRepository.save(r8);

            Booking b1 = new Booking(LocalDate.of(2025,05,19),LocalDate.of(2025,05,22),0,1,c1,r1);
            Booking b2 = new Booking(LocalDate.of(2025,05,19),LocalDate.of(2025,05,20),2,4,c2,r4);
            bookingRepository.save(b1);
            bookingRepository.save(b2);

        };
    }

}
