package com.example.littleProject.model;

import com.example.littleProject.model.entity.HCMIO;
import com.example.littleProject.model.entity.HCMIOId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HCMIORepository extends JpaRepository<HCMIO, HCMIOId> {

    List<HCMIO> findByStock(String stock);

    @Query(value = "SELECT ABS(SUM(NetAmt)) FROM hcmio WHERE stock =?1  AND BsType = 'B'",
            nativeQuery = true)
    double SumOfNetAmt(String stock);
}
