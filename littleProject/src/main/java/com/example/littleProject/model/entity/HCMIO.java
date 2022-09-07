package com.example.littleProject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(HCMIOId.class)
@Table(name = "hcmio")
public class HCMIO {

    @Id
    @Column(name = "TradeDate", nullable = false, length = 8)
    private String tradeDate;

    @Id
    @Column(name = "BranchNo", nullable = false, length = 4)
    private String branchNo;

    @Id
    @Column(name = "CustSeq", nullable = false, length = 7)
    private String custSeq;

    @Id
    @Column(name = "DocSeq", nullable = false, length = 5)
    private String docSeq;

    @Column(name = "Stock", nullable = false, length = 6)
    private String stock;


    @Enumerated(EnumType.STRING)
    @Column(name = "BsType", nullable = false, length = 1)
    private BSType bstype;

    @Column(name = "Price")
    private Double price;

    @Column(name = "Qty")
    private int qty;

    @Column(name = "Amt")
    private Double amt;
    //private BigDecimal amt

    @Column(name = "Fee")
    private long fee; //long

    @Column(name = "Tax")
    private long tax; //long

    @Column(name = "StinTax")
    private int stintax;

    @Column(name = "NetAmt")
    private Double netAmt;

    @Column(name = "ModDate", length = 8)
    private String modDate;

    @Column(name = "ModTime", length = 6)
    private String modTime;

    @Column(name = "ModUser", length = 10)
    private String modUser;


}
