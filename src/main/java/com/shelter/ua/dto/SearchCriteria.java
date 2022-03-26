package com.shelter.ua.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class SearchCriteria {
    @NotBlank
    private String field;
    @NotBlank
    private List<String> value;

    public static List<SearchCriteria> of(Map<String, String> params) {
        return params.entrySet()
                .stream()
                .map(x -> new SearchCriteria(x.getKey(), List.of(x.getValue().split(","))))
                .collect(Collectors.toList());
    }
}
