package ru.skypro.avito.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class ResponseWrapper<T> {
    private Integer count;
    private Collection<T> results;
    public static <T> ResponseWrapper<T> of(Collection<T> results) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();

        if (results == null) {
            return responseWrapper;
        }

        responseWrapper.results = results;
        responseWrapper.count = results.size();

        return responseWrapper;
    }
}
