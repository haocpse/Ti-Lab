package com.haocp.tilab.repository;

import com.haocp.tilab.entity.Bag;
import com.haocp.tilab.entity.BagImg;
import com.haocp.tilab.enums.BagStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface BagImgRepository extends JpaRepository<BagImg, Long> {

    Set<BagImg> findByBag_IdOrderByMainDesc(String bagId);
    Set<BagImg> findByBag_Id(String bagId);
    Optional<BagImg> findByBag_IdAndMainIsTrue(String id);

}
