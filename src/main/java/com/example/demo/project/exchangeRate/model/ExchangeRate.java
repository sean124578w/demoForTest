package com.example.demo.project.exchangeRate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ExchangeRate {

    @JsonProperty("Date")
    @JsonFormat(pattern="yyyyMMdd",timezone="GMT+8")
    private Date date;

    @JsonProperty("USD/NTD")
    private String usdToNtd;

    @JsonProperty("RMB/NTD")
    private String rmbToNtd;

    @JsonProperty("EUR/USD")
    private String eurToUsd;

    @JsonProperty("USD/JPY")
    private String usdToJpy;

    @JsonProperty("GBP/USD")
    private String gbpToUsd;

    @JsonProperty("AUD/USD")
    private String audToUsd;

    @JsonProperty("USD/HKD")
    private String usdToHkd;

    @JsonProperty("USD/RMB")
    private String usdToRmb;

    @JsonProperty("USD/ZAR")
    private String usdToZar;

    @JsonProperty("NZD/USD")
    private String nzdToUsd;
}