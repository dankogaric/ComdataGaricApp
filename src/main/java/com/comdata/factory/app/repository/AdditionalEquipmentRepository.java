package com.comdata.factory.app.repository;

import com.comdata.factory.app.domain.AdditionalEquipment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AdditionalEquipment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdditionalEquipmentRepository extends JpaRepository<AdditionalEquipment, Long> {

}
