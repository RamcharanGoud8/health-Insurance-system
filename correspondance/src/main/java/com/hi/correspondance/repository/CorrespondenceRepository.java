package com.hi.correspondance.repository;

import com.hi.correspondance.entity.Correspondence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorrespondenceRepository extends JpaRepository<Correspondence, Integer> {
}
