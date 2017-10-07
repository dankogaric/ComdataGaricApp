package com.comdata.factory.app.repository;

import com.comdata.factory.app.domain.TankTruck;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TankTruck entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TankTruckRepository extends JpaRepository<TankTruck, Long> {

}
