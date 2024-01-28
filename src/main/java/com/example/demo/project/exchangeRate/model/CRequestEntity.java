package com.example.demo.project.exchangeRate.model;

import lombok.Data;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

@Data
public class CRequestEntity {
    public CRequestEntity(Date startDate, Date endDate, String currency){
        setStartDate(startDate);
        setEndDate(endDate);
        this.currency = currency;
    }

//    @JsonFormat(pattern="yyyy/MM/dd",timezone="GMT+8")
    private Date startDate;
//    @JsonFormat(pattern="yyyy/MM/dd",timezone="GMT+8")
    private Date endDate;

    private String currency;

    public void setStartDate(Date startDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        this.startDate = calendar.getTime();
    }

    public void setEndDate(Date endDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        this.endDate = calendar.getTime();
    }
}
