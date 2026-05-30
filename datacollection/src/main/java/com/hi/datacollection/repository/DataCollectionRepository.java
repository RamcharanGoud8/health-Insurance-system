package com.hi.datacollection.repository;

import com.hi.datacollection.entity.DataCollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataCollectionRepository extends JpaRepository<DataCollectionEntity, Long> {

}
