package com.ecommerce.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse {

    private boolean success;
    private String message;
    private Object data;
    private int code;
}
