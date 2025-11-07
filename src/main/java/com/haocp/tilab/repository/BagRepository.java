package com.haocp.tilab.repository;

import com.haocp.tilab.entity.Bag;
import com.haocp.tilab.enums.BagStatus;
import com.haocp.tilab.enums.BagType;
import com.haocp.tilab.repository.Projection.BestSellingBag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BagRepository extends JpaRepository<Bag, String> {

    Page<Bag> findAllByStatusNot(BagStatus status, Pageable pageable);
    Page<Bag> findAllByStatusNotAndType(BagStatus status, BagType types, Pageable pageable);
    Page<Bag> findByCollection_Id(long id, Pageable pageable);
    Page<Bag> findAllByNameContainsIgnoreCaseAndStatusNot(String name, BagStatus status, Pageable pageable);
    Page<Bag> findAllByNameContainsIgnoreCase(String name, Pageable pageable);
    Set<Bag> findByCollection_Id(Long id);

    int countByStatus(BagStatus status);
    int countByTypeAndStatusNot(BagType type, BagStatus status);
    int countByType(BagType type);

    @Query(value = """
    SELECT
        COUNT(od.id) AS total,
        b.id AS bagId,
        b.name AS bagName,
        bi.url AS imageUrl
    FROM bag b
    JOIN bag_img bi 
        ON bi.bag_id = b.id 
       AND bi.main = 1
    JOIN order_detail od 
        ON od.bag_id = b.id
    JOIN orders o 
        ON o.id = od.order_id
    WHERE b.status <> 'DELETED'
      AND o.created_at BETWEEN :fromDate AND :toDate
    GROUP BY b.id, b.name, bi.url
    ORDER BY total DESC
    LIMIT 3
    """, nativeQuery = true)
    List<BestSellingBag> findTop3BestSellingBags(
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate);

}
