package com.comdata.factory.app.repository;

import com.comdata.factory.app.domain.Car;
import com.comdata.factory.app.domain.ClassicCar;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ClassicCar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassicCarRepository extends JpaRepository<ClassicCar, Long> {

}
