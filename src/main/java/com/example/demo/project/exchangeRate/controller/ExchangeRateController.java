package com.example.demo.project.exchangeRate.controller;

import com.example.demo.basic.exception.MyException;
import com.example.demo.project.exchangeRate.model.CRequestEntity;
import com.example.demo.project.exchangeRate.model.CResponseEntity;
import com.example.demo.project.exchangeRate.model.ErrorEntity;
import com.example.demo.project.exchangeRate.service.ExchangeRateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo/api/exchangeRate")
public class ExchangeRateController {

    @Autowired
    ExchangeRateServiceImpl exchangeRateService;

    @Scheduled(cron = "0 0 18 * * ?")
    public void getExchangeRate() {
        try{
            exchangeRateService.getExchangeRate();
            System.out.println("取得匯率成功");
        } catch (MyException e) {
            System.out.println("取得匯率失敗");
        }
    }

    @PostMapping("/getExchangeRateByDateAreaAndCurrency")
    public ResponseEntity<CResponseEntity> getExchangeRateByDateAreaAndCurrency(@RequestBody CRequestEntity request) {

        try{
            CResponseEntity cRequestEntity = exchangeRateService.getExchangeRateByDateAreaAndCurrency(request.getStartDate(), request.getEndDate(), request.getCurrency());
            return ResponseEntity.ok(cRequestEntity);
        } catch (MyException e) {
            CResponseEntity cResponseEntity = new CResponseEntity();
            ErrorEntity errorEntity = new ErrorEntity("E001", e.getLocalizedMessage());
            cResponseEntity.setError(errorEntity);
            return ResponseEntity.status(500).body(cResponseEntity);
        }
    }

}
