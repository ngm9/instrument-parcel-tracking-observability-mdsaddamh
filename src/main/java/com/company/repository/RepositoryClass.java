package com.company.repository;

import com.company.model.ParcelTrackingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryClass extends JpaRepository<ParcelTrackingEntity, Long> {

    Optional<ParcelTrackingEntity> findByParcelId(String parcelId);
}
