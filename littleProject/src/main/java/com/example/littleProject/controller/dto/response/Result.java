package com.example.littleProject.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private String tradeDate; //1
    private String docSeq; //2
    private String stock; //3
    private String stockName; //4
    private Double buyprice; //5
    private Double nowprice; //6
    private int qty; //7
    private int remainQty; //8
    private long fee; //9
    private Double cost; //10
    private long marketValue; //11
    private int unrealProfit; //12
}
