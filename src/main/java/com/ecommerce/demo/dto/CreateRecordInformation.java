package com.ecommerce.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreateRecordInformation {

    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;

}
