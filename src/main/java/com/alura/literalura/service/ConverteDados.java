package com.alura.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> classes) {
        try {
            return mapper.readValue(json, classes);  //Deserialized
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
