package com.example.demo.basic.tools;

import com.example.demo.basic.exception.MyException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.bson.json.JsonParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ConnectAPITools {

    private Document getAPI(String strURL) throws IOException {
        MyTrustManager.disableSSL();
        Document document = Jsoup.connect(strURL).ignoreContentType(true).get();
        return document;

    }

    public <T> List<T> getWebApiData(String strURL, Class<T> cls) throws MyException {
        try {
            Document document = getAPI(strURL);
            String responseStr = document.body().toString().replace("<body>", "").replace("</body>", "").trim();
//            System.out.println(responseStr);
            List<T> jsonList = jsonToList(responseStr, cls);
            return jsonList;
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            throw new MyException("外部API發生錯誤: " + strURL);
        }
    }

    public <T> List<T> jsonToList(@NonNull String jsonString, Class<T> cls) throws MyException {
        try{
            ObjectMapper objectMapper=new ObjectMapper();
            return objectMapper.readValue(jsonString,getCollectionType(List.class,cls));
        }catch(JsonParseException e){
            throw new MyException(e.getLocalizedMessage());
        }catch(JsonMappingException e){
            throw new MyException(e.getLocalizedMessage());
        }catch(JsonProcessingException e){
            throw new MyException(e.getLocalizedMessage());
        }
    }

    private JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses){
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
