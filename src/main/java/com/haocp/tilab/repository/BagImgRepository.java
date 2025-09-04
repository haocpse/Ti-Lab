package com.haocp.tilab.repository;

import com.haocp.tilab.entity.BagImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BagImgRepository extends JpaRepository<BagImg, Long> {

    Set<BagImg> findByBag_IdOrderByMainDesc(String bagId);

}
