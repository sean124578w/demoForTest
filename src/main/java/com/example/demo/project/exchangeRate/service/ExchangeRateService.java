package com.example.demo.project.exchangeRate.service;

import com.example.demo.basic.exception.MyException;
import com.example.demo.project.exchangeRate.model.CResponseEntity;


import java.util.Date;

public interface ExchangeRateService {
    CResponseEntity getExchangeRateByDateAreaAndCurrency(Date startDate, Date endDate, String currency) throws MyException;

}
