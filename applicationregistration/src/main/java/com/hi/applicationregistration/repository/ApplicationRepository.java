package com.hi.applicationregistration.repository;

import com.hi.applicationregistration.entity.CitizenApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<CitizenApplicationEntity, Long> {

}
