package com.example.demo.project.exchangeRate.repository;

import com.example.demo.project.exchangeRate.model.ExchangeRatePo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ExchangeRateRepository extends MongoRepository<ExchangeRatePo, String> {
    List<ExchangeRatePo> findAllByDateBetween(Date startDate, Date endDate);
//    saveAll(List<ExchangeRate> exchangeRateList)
}
