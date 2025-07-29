package com.augusto0414.conta_mon_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(JsonInclude.Include.ALWAYS)
@NoArgsConstructor
@AllArgsConstructor
public class DefaultResponse<T> {
    private boolean error;
    private int code;
    private String message;
    private T body;
}