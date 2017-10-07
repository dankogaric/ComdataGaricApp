package com.comdata.factory.app.repository;

import com.comdata.factory.app.domain.TructorTruck;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TructorTruck entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TructorTruckRepository extends JpaRepository<TructorTruck, Long> {

}
