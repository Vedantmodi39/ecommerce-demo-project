package com.ecommerce.demo;

import com.ecommerce.demo.dto.ProductCategoryDto;
import com.ecommerce.demo.dto.ProductDto;
import com.ecommerce.demo.dto.ProductDtoWithCategoryAndInventory;
import com.ecommerce.demo.dto.ProductInventoryDto;
import com.ecommerce.demo.entity.Product;
import com.ecommerce.demo.entity.ProductCategory;
import com.ecommerce.demo.entity.ProductInventory;
import com.ecommerce.demo.exception.*;
import com.ecommerce.demo.mapstruct.MapStructMapper;
import com.ecommerce.demo.repository.ProductCategoryRepository;
import com.ecommerce.demo.repository.ProductRepository;
import com.ecommerce.demo.service.ProductService;
import com.ecommerce.demo.utility.RecordCreationUtility;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Mock
    private MapStructMapper mapStructMapper;

    @Mock
    private RecordCreationUtility recordCreationUtility;

    @InjectMocks
    private ProductService productService;//= new ProductService(productCategoryRepository,recordCreationUtility, mapStructMapper,productRepository);

    ProductCategory productCategory = ProductCategory.builder()
            .name("testcategory")
            .description("this is test category")
            .createdAt(LocalDateTime.now())
            .modifiedAt(LocalDateTime.now())
            .deletedAt(null).build();

    ProductInventory productInventory = ProductInventory.builder()
            .quantity(100)
            .createdAt(LocalDateTime.now())
            .modifiedAt(LocalDateTime.now())
            .deletedAt(null)
            .build();

    Product product = Product.builder()
            .name("testproduct")
            .description("this is test product")
            .sku("tst123")
            .productCategory(productCategory)
            .productInventory(productInventory)
            .price(100)
            .isDeleted(false)
            .createdAt(LocalDateTime.now())
            .modifiedAt(LocalDateTime.now())
            .deletedAt(null)
            .build();

    ProductDto productDto = ProductDto.builder()
            .name("testproduct")
            .description("this is test product")
            .price(100)
            .sku("tst123")
            .build();

    ProductCategoryDto productCategoryDto = ProductCategoryDto.builder().name("testcategory").build();

    ProductInventoryDto productInventoryDto = ProductInventoryDto.builder().quantity(10).createdAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).build();

    ProductDtoWithCategoryAndInventory productDtoWithCategoryAndInventory = ProductDtoWithCategoryAndInventory.builder()
            .productDto(productDto)
            .productCategoryDto(productCategoryDto).
            productInventoryDto(productInventoryDto)
            .build();

    @Test
    public void ProductService_addProduct_Throws_ProductAlreadyExistException(){

        when(productRepository.findBySku("tst123")).thenReturn(Optional.ofNullable(product));
//        when(productCategoryRepository.findByName("testcategory")).thenReturn(Optional.ofNullable(productCategory));
//        when(mapStructMapper.productDtoToProduct(productDto)).thenReturn(product);
//        when(mapStructMapper.ProductInventoryDtoToProductInventory(productInventoryDto)).thenReturn(productInventory);
//        when(productRepository.save(product)).thenReturn(product);

        assertThrows(ProductAlreadyExistException.class , () -> productService.addProduct(productDtoWithCategoryAndInventory));
//        Assertions.assertThat(savedProduct).isNotNull();

    }

    @Test
    public void ProductService_addProduct_Throws_CategoryNotExistException(){
        when(productRepository.findBySku("tst123")).thenReturn(Optional.ofNullable(null));
        assertThrows(CategoryNotExistException.class , () -> productService.addProduct(productDtoWithCategoryAndInventory));
    }

    @Test
    public void ProductService_addProduct_ReturnsProductDtoWithCategoryAndInventory(){

        when(productRepository.findBySku("tst123")).thenReturn(Optional.ofNullable(null));
        when(productCategoryRepository.findByName(anyString())).thenReturn(Optional.ofNullable(productCategory));
        when(mapStructMapper.productDtoToProduct(any())).thenReturn(product);
        when(mapStructMapper.ProductInventoryDtoToProductInventory(any())).thenReturn(productInventory);
        when(productRepository.save(product)).thenReturn(product);
        Object addedProduct = productService.addProduct(productDtoWithCategoryAndInventory);

        Assertions.assertThat(addedProduct).isNotNull();
    }

    @Test
    public void ProductService_getProduct_Throws_ProductIdNotFoundException(){
        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        assertThrows(ProductIdNotFoundException.class , () -> productService.getProduct(1));
    }

    @Test
    public void ProductService_getProduct_ProductDeletedException(){
        Product deletedProduct = Product.builder()
                .name("testproduct")
                .description("this is test product")
                .sku("tst123")
                .productCategory(productCategory)
                .productInventory(productInventory)
                .price(100)
                .isDeleted(true)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .deletedAt(LocalDateTime.now())
                .build();

        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(deletedProduct));
        assertThrows(ProductDeletedException.class, () -> productService.getProduct(1));
    }

    @Test
    public void ProductService_getProduct_Returns_ProductDtoWithCategoryAndInventory(){

        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(product));
        when(mapStructMapper.ProductToProductDto(product)).thenReturn(productDto);
        when(mapStructMapper.ProductInventoryToProductInventoryDto(productInventory)).thenReturn(productInventoryDto);
        when(mapStructMapper.ProductCategoryToProductCategoryDto(productCategory)).thenReturn(productCategoryDto);

        ProductDtoWithCategoryAndInventory fetchedProduct = productService.getProduct(1);

        Assertions.assertThat(fetchedProduct).isNotNull();

    }

    @Test
    public void ProductService_updateProduct_Throws_ProductIdNotFoundException(){

        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        assertThrows(ProductIdNotFoundException.class , () -> productService.updateProduct(productDtoWithCategoryAndInventory,1));
    }

    @Test
    public void ProductService_updateProduct_Throws_InvalidDataException(){

        ProductInventoryDto invalidInventory = ProductInventoryDto.builder().quantity(0).modifiedAt(LocalDateTime.now()).createdAt(LocalDateTime.now()).build();

        ProductDto invalidProduct = ProductDto.builder()
                .name("testproduct")
                .description("this is test product")
                .price(0)
                .sku("tst123")
                .build();

        ProductDtoWithCategoryAndInventory invallidProductDtoWithCategoryAndInventory = ProductDtoWithCategoryAndInventory.builder()
                .productDto(invalidProduct)
                .productInventoryDto(invalidInventory)
                .productCategoryDto(productCategoryDto)
                .build();

        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(product));
        assertThrows(InvalidDataException.class , () -> productService.updateProduct(invallidProductDtoWithCategoryAndInventory,1));
    }

    @Test
    public void ProductService_updateProduct_Throws_CategoryNotExistException(){

        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(product));
        when(productCategoryRepository.findByName(any())).thenReturn(Optional.ofNullable(null));

        assertThrows(CategoryNotExistException.class , () -> productService.updateProduct(productDtoWithCategoryAndInventory,1));
    }

    @Test
    public void ProductService_updateProduct_Returns_ProductDtoWithCategoryAndInventory(){

        ProductDto updateProductDto = ProductDto.builder()
                .name("updatedProduct")
                .description("this is test product")
                .price(10)
                .sku("updt123")
                .build();

        Product updatedproduct = Product.builder()
                .name("updatedProduct")
                .description("this is test product")
                .sku("updat123")
                .productCategory(productCategory)
                .productInventory(productInventory)
                .price(10)
                .isDeleted(false)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .deletedAt(LocalDateTime.now())
                .build();

        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(product));
        when(productCategoryRepository.findByName(any())).thenReturn(Optional.ofNullable(productCategory));
        when(mapStructMapper.updateProductFromDto(updateProductDto,product)).thenReturn(updatedproduct);
        when(mapStructMapper.updateProductInventoryFromDto(productInventoryDto,productInventory)).thenReturn(productInventory);
        when(productRepository.save(product)).thenReturn(product);

         ProductDtoWithCategoryAndInventory result = productService.updateProduct(productDtoWithCategoryAndInventory,1);
        Assertions.assertThat(result).isNotNull();
    }
}
