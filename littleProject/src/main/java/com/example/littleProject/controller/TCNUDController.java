package com.example.littleProject.controller;

import com.example.littleProject.model.entity.TCNUD;
import com.example.littleProject.service.TCNUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tcnud")
public class TCNUDController {

    @Autowired
    private TCNUDService tcnudService;

    @GetMapping
    public List<TCNUD> getAllTCNUD(){
        List<TCNUD> tcnudList = this.tcnudService.getAllTCNUD();
        return tcnudList;
    }

    @GetMapping("/findByDocSeq")
    //測試用
    public TCNUD TCNUDfindByDocSeq(@RequestParam String docSeq){
        TCNUD tcnud = this.tcnudService.TCNUDfindByDocSeq(docSeq);
        return tcnud;
    }

    //測試用
    @GetMapping("/latestTCNUD")
    public TCNUD findLatestStock(@RequestParam String stock){
        TCNUD tcnud = this.tcnudService.findLatestStock(stock);
        return tcnud;
    }


}

