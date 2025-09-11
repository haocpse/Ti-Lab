package com.haocp.tilab.repository;

import com.haocp.tilab.entity.EmailQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailQueueRepository extends JpaRepository<EmailQueue, Long> {
}
