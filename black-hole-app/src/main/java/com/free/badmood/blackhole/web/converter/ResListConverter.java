package com.free.badmood.blackhole.web.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.free.badmood.blackhole.web.entity.ArticleRes;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import java.util.List;

@Component
public class ResListConverter implements AttributeConverter<List<ArticleRes>, String> {

    private ObjectMapper mapper = new ObjectMapper();
    private String EMPTY = "[]";
    private TypeReference<List<ArticleRes>> typeReference = new TypeReference<List<ArticleRes>>(){};

    @Override
    public String convertToDatabaseColumn(List<ArticleRes> articleRes) {
        try {
            return mapper.writeValueAsString(articleRes);
        } catch (JsonProcessingException e) {
            return EMPTY;
        }
    }


    @Override
    public List<ArticleRes> convertToEntityAttribute(String json) {
        try {
            return mapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
