package io.bootify.my_app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.bootify.my_app.model.AmenityType;
import io.bootify.my_app.model.Reservation;
import io.bootify.my_app.model.Appuser;
import io.bootify.my_app.model.Capacity;
import io.bootify.my_app.repos.ReservationRepository;
import io.bootify.my_app.repos.AppuserRepository;
import io.bootify.my_app.repos.CapacityRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class MyAppApplication {

    private Map<AmenityType, Integer> initialCapacities = new HashMap<>() {
        {
            put(AmenityType.GYM, 20);
            put(AmenityType.POOL, 4);
            put(AmenityType.SAUNA, 1);
        }
    };

    public static void main(final String[] args) {
        SpringApplication.run(MyAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(AppuserRepository userRepository,
            ReservationRepository reservationRepository,
            CapacityRepository capacityRepository) {

        // UserService

        return (args) -> {
            Appuser user = userRepository.save(new Appuser("Max s",
                    "max",
                    bCryptPasswordEncoder().encode("12345")));

            userRepository.save(new Appuser("Max s2",
                    "max 2",
                    bCryptPasswordEncoder().encode("123456")));

            for (AmenityType amenityType : initialCapacities.keySet()) {
                capacityRepository.save(new Capacity(amenityType, initialCapacities.get(amenityType)));
            }

            Date date = new Date();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Reservation reservation = Reservation.builder()
                    .reservationDate(localDate)
                    .startTime(LocalTime.of(12, 00))
                    .endTime(LocalTime.of(13, 00))
                    .appuser(user)
                    .amenityType(AmenityType.POOL)
                    .build();

            reservationRepository.save(reservation);
        };
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
