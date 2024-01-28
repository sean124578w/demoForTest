package com.example.demo.project.exchangeRate.model;

import lombok.Data;

import java.util.List;

@Data
public class CResponseEntity {

    private ErrorEntity error;
    private List<CurrencyEntity> currency;
}
