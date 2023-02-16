package com.ecommerce.demo;

import com.ecommerce.demo.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {


@Autowired
MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();
    @MockBean
   ProductService productService;

    ProductDto productDto=new ProductDto("xxxx","yyyy","bbb",1231,1,1);
    ProductInventoryDto productInventoryDto=new ProductInventoryDto(1,"2023-02-02T17:25:09.071675","2023-02-02T17:25:09.071675");
    ProductCategoryDto productCategoryDto=new ProductCategoryDto("phone");
    ProductDtoWithCategoryAndInventory productDtoWithCategoryAndInventory=new ProductDtoWithCategoryAndInventory(productDto,productCategoryDto,productInventoryDto);


    @Test
    public void getProduct() throws Exception {

        GenericResponse genericResponse = new GenericResponse(true, "Fetched Product Successfully", productDtoWithCategoryAndInventory, HttpStatus.OK.value());

        Mockito.when(productService.getProduct(1)).thenReturn(productDtoWithCategoryAndInventory);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/products/getProduct/"+1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(genericResponse)));
    }

    @Test
    public void addProduct() throws Exception
    {
        GenericResponse genericResponse = new GenericResponse(true, "Product Added Successfully", productDtoWithCategoryAndInventory, HttpStatus.OK.value());

        String content=objectWriter.writeValueAsString(productDtoWithCategoryAndInventory);

        Mockito.when(productService.addProduct(productDtoWithCategoryAndInventory)).thenReturn(productDtoWithCategoryAndInventory);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/products/addProduct")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                       .andExpect(status().isOk());
    }

    @Test
    public void updateProduct() throws Exception
    {
        GenericResponse genericResponse = new GenericResponse(true, "Product Updated Successfully", productDtoWithCategoryAndInventory, HttpStatus.OK.value());

        String content=objectWriter.writeValueAsString(productDtoWithCategoryAndInventory);

        Mockito.when(productService.addProduct(productDtoWithCategoryAndInventory)).thenReturn(productDtoWithCategoryAndInventory);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/products/updateProduct/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProduct() throws Exception
    {
        GenericResponse genericResponse = new GenericResponse(true, "Product Deleted Successfully", new EmptyJsonBody(), HttpStatus.OK.value());
        String content=objectWriter.writeValueAsString(1);
        int a=1;
        Mockito.doNothing().when(productService).deleteProduct(1);
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/products/deleteProduct/1")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }


}
