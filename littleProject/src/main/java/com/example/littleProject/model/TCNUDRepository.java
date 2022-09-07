package com.example.littleProject.model;

import com.example.littleProject.model.entity.HCMIO;
import com.example.littleProject.model.entity.TCNUD;
import com.example.littleProject.model.entity.TCNUDId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TCNUDRepository extends JpaRepository<TCNUD, TCNUDId> {
    TCNUD findByDocSeq(String docSeq);

    @Query(value = "SELECT * FROM tcnud WHERE stock=?1 ORDER BY modDate DESC , modTime Desc LIMIT 1",
            nativeQuery = true)
    TCNUD findLatestStock(String stock);
//    TCNUD findFirstByStockByModeDateAndmodTimeDesc(String stock);

}
