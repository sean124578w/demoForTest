package com.example.demo.project.exchangeRate.service;

import com.example.demo.basic.exception.MyException;
import com.example.demo.basic.tools.ConnectAPITools;
import com.example.demo.project.exchangeRate.model.*;
import com.example.demo.project.exchangeRate.repository.ExchangeRateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService{

    @Autowired
    ExchangeRateRepository exchangeRateRepository;

    @Value("${daily_foreign_exchange_rates_url}")
    private String DAILY_FOREIGN_EXCHANGE_RATES_URL;

    public List<ExchangeRate> getExchangeRate() throws MyException {
            List<ExchangeRate> exchangeRateList = new ConnectAPITools().getWebApiData(DAILY_FOREIGN_EXCHANGE_RATES_URL, ExchangeRate.class);

            List<ExchangeRatePo> exchangeRatePoList = exchangeRateToPo(exchangeRateList);

            exchangeRateRepository.deleteAll();
            exchangeRateRepository.insert(exchangeRatePoList);
            return exchangeRateList;
    }

    private List<ExchangeRatePo> exchangeRateToPo(List<ExchangeRate> exchangeRateList) {
        List<ExchangeRatePo> exchangeRatePoList = new ArrayList<>();
        if (null != exchangeRateList && !exchangeRateList.isEmpty()) {
            for (ExchangeRate exchangeRate : exchangeRateList) {
                ExchangeRatePo exchangeRatePo = new ExchangeRatePo();
                BeanUtils.copyProperties(exchangeRate, exchangeRatePo);
                exchangeRatePoList.add(exchangeRatePo);
            }
        }
        return exchangeRatePoList;
    }

    private List<ExchangeRate> exchangeRatePoBack(List<ExchangeRatePo> exchangeRateListPo) {
        List<ExchangeRate> exchangeRateList = new ArrayList<>();
        if (null != exchangeRateListPo && !exchangeRateListPo.isEmpty()) {
            for (ExchangeRatePo exchangeRatePo : exchangeRateListPo) {
                ExchangeRate exchangeRate = new ExchangeRate();
                BeanUtils.copyProperties(exchangeRatePo, exchangeRate);
                exchangeRateList.add(exchangeRate);
            }
        }
        return exchangeRateList;
    }

    public CResponseEntity getExchangeRateByDateAreaAndCurrency(Date startDate, Date endDate, String currency) throws MyException {
        checkDate(startDate, endDate);
        CResponseEntity cResponseEntity = new CResponseEntity();

        List<ExchangeRatePo> exchangeRatePoList = exchangeRateRepository.findAllByDateBetween(startDate, endDate);

        String currencyTO = fillCurrencyTONtd(currency);

        List<CurrencyEntity> currencyEntityList = getCurrencyRate(currencyTO, exchangeRatePoList);
        ErrorEntity errorEntity;
        if (null != currencyEntityList) {
            cResponseEntity.setCurrency(currencyEntityList);
            errorEntity = new ErrorEntity("0000", "成功");
        }else{
            errorEntity = new ErrorEntity("0000", "無資料");
        }
        cResponseEntity.setError(errorEntity);


        return cResponseEntity;
    }

    private List<CurrencyEntity> getCurrencyRate(String currencyTO, List<ExchangeRatePo> exchangeRatePoList) throws MyException {
        if (!exchangeRatePoList.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<ExchangeRate> exchangeRateList = exchangeRatePoBack(exchangeRatePoList);
            List<CurrencyEntity> currencyEntityList = new ArrayList<>();
            for (ExchangeRate exchangeRate : exchangeRateList) {
                Map<String, String> map = objectMapper.convertValue(exchangeRate, Map.class);

                if (null == map.get(currencyTO)) {
                    throw new MyException("查無此種幣別");
                }

                CurrencyEntity currencyEntity = new CurrencyEntity(map.get("Date"), map.get(currencyTO));
                currencyEntityList.add(currencyEntity);
            }
            return currencyEntityList;
        }
        return null;
    }

    private String fillCurrencyTONtd(String currency){
        String currencyTO = "";
        if (!"".equals(currency) && null != currency) {
            currencyTO = currency.toUpperCase() + "/NTD";
        }
        return currencyTO;
    }

    private void checkDate(Date startDate, Date endDate) throws MyException {
        if (!isValidDateRange(startDate, endDate)) {
            throw new MyException("日期區間無效");
        }
    }

    public boolean isValidDateRange(Date startDate, Date endDate) {
        Date currentDate = new Date();
        Calendar oneYearAgoCalendar = Calendar.getInstance();
        oneYearAgoCalendar.setTime(currentDate);
        oneYearAgoCalendar.add(Calendar.YEAR, -1);
        Date oneYearAgo = oneYearAgoCalendar.getTime();

        Calendar yesterdayCalendar = Calendar.getInstance();
        yesterdayCalendar.setTime(currentDate);
        yesterdayCalendar.add(Calendar.DAY_OF_YEAR, -1);
        Date yesterday = yesterdayCalendar.getTime();

        return (startDate.equals(oneYearAgo) || !startDate.before(oneYearAgo)) && !endDate.after(yesterday) && !startDate.after(endDate);
    }
}
