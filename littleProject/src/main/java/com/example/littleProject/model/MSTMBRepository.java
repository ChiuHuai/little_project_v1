package com.example.littleProject.model;

import com.example.littleProject.model.entity.MSTMB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MSTMBRepository extends JpaRepository<MSTMB, String> {
    MSTMB findByStock(String stock);
}
