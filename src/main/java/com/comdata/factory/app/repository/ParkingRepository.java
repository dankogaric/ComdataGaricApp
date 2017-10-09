package com.comdata.factory.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.comdata.factory.app.domain.Parking;


/**
 * Spring Data JPA repository for the Parking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {
	
	
	List<Parking> findAllByOrderByAreaAsc();
	
	List<Parking> findByAreaGreaterThanOrderByAreaAsc(Integer area);
}
