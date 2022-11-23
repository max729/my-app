package io.bootify.my_app.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import io.bootify.my_app.model.AmenityType;
import io.bootify.my_app.model.Capacity;

public interface CapacityRepository extends JpaRepository<Capacity, Long> {

    Capacity findByAmenityType(AmenityType amenityType);

}