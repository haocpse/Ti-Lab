package com.haocp.tilab.repository;

import com.haocp.tilab.entity.Bag;
import com.haocp.tilab.enums.BagStatus;
import com.haocp.tilab.enums.BagType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BagRepository extends JpaRepository<Bag, String> {

    Page<Bag> findAllByStatusNot(BagStatus status, Pageable pageable);
    Page<Bag> findAllByStatusNotAndType(BagStatus status, BagType types, Pageable pageable);
    Page<Bag> findByCollection_Id(long id, Pageable pageable);
}
