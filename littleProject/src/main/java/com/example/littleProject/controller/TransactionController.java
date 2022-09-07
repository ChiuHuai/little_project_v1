package com.example.littleProject.controller;

import com.example.littleProject.controller.dto.request.TransactionRequest;
import com.example.littleProject.controller.dto.response.Result;
import com.example.littleProject.controller.dto.response.StatusResponse;
import com.example.littleProject.model.entity.MSTMB;
import com.example.littleProject.model.entity.TCNUD;
import com.example.littleProject.service.HCMIOService;
import com.example.littleProject.service.MSTMBService;
import com.example.littleProject.service.TCNUDService;
import com.example.littleProject.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private HCMIOService hcmioService;
    @Autowired
    private TCNUDService tcnudService;

    @Autowired
    private MSTMBService mstmbService;

    @PostMapping("/unreal/add")
    public StatusResponse addUnreal(@RequestBody TransactionRequest request) {
        String responseMessage = this.transactionService.buyStock(request);

        StatusResponse statusResponse = new StatusResponse();
        if (responseMessage.equals("")) {
            Result result = new Result();
            //找到相同書號，加入資料到ResultList
            TCNUD tcnud = this.tcnudService.TCNUDfindByDocSeq(request.getDocSeq());
            result.setTradeDate(tcnud.getTradeDate());
            result.setDocSeq(tcnud.getDocSeq());
            result.setStock(tcnud.getStock());
            result.setBuyprice(tcnud.getPrice());
            result.setQty(tcnud.getQty());
            result.setRemainQty(tcnud.getRemainQty());
            result.setFee(tcnud.getFee());
            result.setCost(tcnud.getCost());

            MSTMB mstmb = this.mstmbService.findByStock(request.getStock());
            Double NowPrice = mstmb.getCurPrice(); //要防沒資料
            result.setNowprice(NowPrice); // from MSTMB
            long marketValue = Math.round(NowPrice * tcnud.getRemainQty());
            result.setMarketValue(marketValue);
            result.setUnrealProfit(0);//

            //注意四捨五入


//
//            statusResponse.setResultList(
//                    result
//            );
            statusResponse.setResponseCode("000");
            statusResponse.setMessage(responseMessage);
        }


        return statusResponse;
    }

//    (現值股票單價*交易股數-手續費-交易稅)
//            - (買時股票單價*交易股數+手續費)

    public int calcUnrealProfit(String stock) {
        MSTMB mstmb = this.mstmbService.findByStock(stock); //股票資訊
        TCNUD tcnud = this.tcnudService.findLatestStock(stock);
        long totalPaid = Math.round(this.hcmioService.SumOfNetAmt(stock));
        long amt = Math.round(mstmb.getCurPrice() * tcnud.getRemainQty());
        long currentValue = amt - Math.round(amt * 0.001425) - Math.round(amt * 0.03);

        return 0;
    }

}
