package com.example.littleProject.service;

import com.example.littleProject.controller.dto.request.CreateHCMIORequest;
import com.example.littleProject.controller.dto.request.TransactionRequest;
import com.example.littleProject.model.HCMIORepository;
import com.example.littleProject.model.TCNUDRepository;
import com.example.littleProject.model.entity.BSType;
import com.example.littleProject.model.entity.HCMIO;
import com.example.littleProject.model.entity.TCNUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TransactionService {
    @Autowired
    private HCMIORepository hcmioRepository;

    @Autowired
    private TCNUDRepository tcnudRepository;

    public String buyStock(TransactionRequest request) {
        // HCMIO 和 TCNUD 都新增一筆
        //    private String tradeDate;
        //    private String branchNo;
        //    private String custSeq;
        //    private String docSeq;
        //    private String stock;
        //    private Double price;
        //    private int qty;

        //確認資料都有收到
        if (request.getTradeDate().isBlank()) {
            return "Require TradeDate";
        }

        //新增明細 HCMIO
        HCMIO hcmio = addHCMIO(request);

        //新增TCNUD
        TCNUD tcnud = addTCNUD(hcmio);

        return "";
    }

    //新增HCMIO
    private HCMIO addHCMIO(TransactionRequest request) {
        HCMIO hcmio = new HCMIO();

        hcmio.setTradeDate(request.getTradeDate()); //dateNow //1
        hcmio.setBranchNo(request.getBranchNo()); //2
        hcmio.setCustSeq(request.getCustSeq()); //3
        hcmio.setDocSeq(request.getDocSeq()); //4
        hcmio.setStock(request.getStock()); //5
        hcmio.setPrice(request.getPrice()); //6
        hcmio.setBstype(BSType.B); //buy //7
        hcmio.setQty(request.getQty()); //8

        double amt = request.getQty() * request.getPrice();
        hcmio.setAmt(amt); //9
        hcmio.setFee(calcFee(amt)); //10
        hcmio.setTax(calcTax(hcmio.getBstype())); //11
        hcmio.setStintax(0); //固定為0 //12
        hcmio.setNetAmt(calcNetAmt(hcmio.getBstype(),
                hcmio.getAmt(), hcmio.getFee())); //13

        String[] now = dateTimeNow();
        String dateNow = now[0];
        String timeNow = now[1];
        hcmio.setModDate(dateNow); //14
        hcmio.setModTime(timeNow); //15
        hcmio.setModUser("HuaiChiu"); //16

        this.hcmioRepository.save(hcmio);


        return hcmio;
    }
    
    //新增 TCNUD
    private TCNUD addTCNUD(HCMIO hcmio) {
        TCNUD tcnud = new TCNUD();
        tcnud.setTradeDate(hcmio.getTradeDate()); //1
        tcnud.setBranchNo(hcmio.getBranchNo()); //2
        tcnud.setCustSeq(hcmio.getCustSeq()); //3
        tcnud.setDocSeq(hcmio.getDocSeq()); //4
        tcnud.setStock(hcmio.getStock()); //5
        tcnud.setPrice(hcmio.getPrice()); //6
        tcnud.setQty(hcmio.getQty()); //7

        TCNUD LatestTcnud = this.tcnudRepository.findLatestStock(hcmio.getStock());
        if(LatestTcnud == null){
            tcnud.setRemainQty(hcmio.getQty());
        }else{
            tcnud.setRemainQty(hcmio.getQty()+LatestTcnud.getRemainQty());
            //8 //要加上之前的(同 stock 最新一筆)
        }

        tcnud.setFee(hcmio.getFee()); //9
        tcnud.setCost(Math.abs(hcmio.getNetAmt())); //cost 一定為正 //10
        tcnud.setModDate(hcmio.getModDate()); //11
        tcnud.setModTime(hcmio.getModTime()); //12
        tcnud.setModUser(hcmio.getModUser()); //13
        this.tcnudRepository.save(tcnud);
        return tcnud;
    }


        //產生現在時間
    private String[] dateTimeNow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");
        String[] dateTime = LocalDateTime.now().format(formatter).split(" ");
        return dateTime;
    }

    //計算 fee
    private long calcFee(Double amt) {
        long fee = Math.round(amt * 0.001425);
        return fee;
    }

    private long calcTax(BSType bstype, Double... amt) {
        //先判斷買賣
        if (BSType.B.equals(bstype)) {
            return 0;
        } else {
            return Math.round(amt[0] * 0.003);
        }
    }

    private Double calcNetAmt(BSType bstype, Double amt, Long fee, Long... tax) {
        //先判斷買賣
        if (BSType.B.equals(bstype)) {
            return -(amt + fee);
        } else {
            return amt - fee - tax[0];
        }
    }


}
