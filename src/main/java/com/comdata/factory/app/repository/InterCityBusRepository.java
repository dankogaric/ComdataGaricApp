package com.comdata.factory.app.repository;

import com.comdata.factory.app.domain.InterCityBus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the InterCityBus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InterCityBusRepository extends JpaRepository<InterCityBus, Long> {

}
