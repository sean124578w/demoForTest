package com.example.demo.project.exchangeRate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document(collection = "exchange_rate")
public class ExchangeRatePo {

    private String id;

    @Field("Date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date date;

    @Field("USD/NTD")
    private String usdToNtd;

//    @Field("RMB/NTD")
//    private String rmbToNtd;
//
//    @Field("EUR/USD")
//    private String eurToUsd;
//
//    @Field("USD/JPY")
//    private String usdToJpy;
//
//    @Field("GBP/USD")
//    private String gbpToUsd;
//
//    @Field("AUD/USD")
//    private String audToUsd;
//
//    @Field("USD/HKD")
//    private String usdToHkd;
//
//    @Field("USD/RMB")
//    private String usdToRmb;
//
//    @Field("USD/ZAR")
//    private String usdToZar;
//
//    @Field("NZD/USD")
//    private String nzdToUsd;
}