package com.comdata.factory.app.repository;

import com.comdata.factory.app.domain.Cabrio;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Cabrio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CabrioRepository extends JpaRepository<Cabrio, Long> {

}
