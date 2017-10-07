package com.comdata.factory.app.repository;

import com.comdata.factory.app.domain.CityBus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CityBus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CityBusRepository extends JpaRepository<CityBus, Long> {

}
