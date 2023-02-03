package com.ecommerce.demo.utility;

import com.ecommerce.demo.dto.CreateRecordInformation;
import com.ecommerce.demo.dto.ProductInventoryDto;
import com.ecommerce.demo.repository.ProductInventoryRepository;
import com.ecommerce.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.security.Principal;
import java.time.LocalDateTime;

@Configuration
public class RecordCreationUtility {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductInventoryRepository productInventoryRepository;

    public CreateRecordInformation putNewRecordInformation() {
        //Principal principal = SecurityContextHolder.getContext().getAuthentication();
        //if(principal == null || principal.getName().equals("anonymousUser"))
        return new CreateRecordInformation(LocalDateTime.now(), LocalDateTime.now());
        //return new CreateRecordInformation(true,userRepository.findUserIdByUserName(principal.getName()),LocalDateTime.now(),RandomString.make(30),RandomString.make(10));

    }
}
