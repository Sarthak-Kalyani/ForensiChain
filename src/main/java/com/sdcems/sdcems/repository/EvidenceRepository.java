package com.sdcems.sdcems.repository;

import com.sdcems.sdcems.model.Evidence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvidenceRepository extends JpaRepository<Evidence, Integer> {

    List<Evidence> findByCaseId(Integer caseId);

}