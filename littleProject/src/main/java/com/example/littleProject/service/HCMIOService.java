package com.example.littleProject.service;

import com.example.littleProject.controller.dto.request.CreateHCMIORequest;
import com.example.littleProject.model.HCMIORepository;
import com.example.littleProject.model.TCNUDRepository;
import com.example.littleProject.model.entity.HCMIO;
import com.example.littleProject.model.entity.TCNUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HCMIOService {
    @Autowired
    private HCMIORepository hcmioRepository;

    @Autowired
    private TCNUDRepository tcnudRepository;paid

    public List<HCMIO> getAllHCMIO() {
        List<HCMIO> hcmioList = this.hcmioRepository.findAll();
        return hcmioList;
    }

    public List<HCMIO> getHCMIOByStock(String stock){
        List<HCMIO> hcmioList = this.hcmioRepository.findByStock(stock);
        return hcmioList;
    }

    public double SumOfNetAmt(String stock){
        return this.hcmioRepository.SumOfNetAmt(stock);
    }

}
