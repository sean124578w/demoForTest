package com.example.demo.project.exchangeRate.controller;

import com.example.demo.project.exchangeRate.model.CRequestEntity;
import com.example.demo.project.exchangeRate.model.ExchangeRate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExchangeRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ExchangeRateController exchangeRateController;

    @Test
    void getExchangeRate() {
        exchangeRateController.getExchangeRate();
    }

    @Test
    void getExchangeRateByDateAreaAndCurrency_ok() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String mvcResult = mockMvc.perform(post("/demo/api/exchangeRate/getExchangeRateByDateAreaAndCurrency")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new CRequestEntity(sdf.parse("2024/01/23"), sdf.parse("2024/01/25"), "usd")))).andReturn().getResponse().getContentAsString(Charset.defaultCharset());
        System.out.println(mvcResult);
    }

    @Test
    void getExchangeRateByDateAreaAndCurrency_outSideStartDate() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String mvcResult = mockMvc.perform(post("/demo/api/exchangeRate/getExchangeRateByDateAreaAndCurrency")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new CRequestEntity(sdf.parse("2020/01/23"), sdf.parse("2024/01/25"), "usd")))).andReturn().getResponse().getContentAsString(Charset.defaultCharset());
        System.out.println(mvcResult);
    }

    @Test
    void getExchangeRateByDateAreaAndCurrency_outSideEndDate() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String mvcResult = mockMvc.perform(post("/demo/api/exchangeRate/getExchangeRateByDateAreaAndCurrency")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new CRequestEntity(sdf.parse("2024/01/23"), sdf.parse("2024/01/29"), "usd")))).andReturn().getResponse().getContentAsString(Charset.defaultCharset());
        System.out.println(mvcResult);
    }

    @Test
    void getExchangeRateByDateAreaAndCurrency_voidCurrency() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String mvcResult = mockMvc.perform(post("/demo/api/exchangeRate/getExchangeRateByDateAreaAndCurrency")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new CRequestEntity(sdf.parse("2024/01/23"), sdf.parse("2024/01/25"), "aaa")))).andReturn().getResponse().getContentAsString(Charset.defaultCharset());
        System.out.println(mvcResult);
    }

    public String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}