package com.comdata.factory.app.repository;

import com.comdata.factory.app.domain.Parking;
import com.comdata.factory.app.domain.Vehicle;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Vehicle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
	Page<Vehicle> findByParkingId(Long id, Pageable pageable);
}
