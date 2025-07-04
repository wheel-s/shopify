package com.toshiro.dreamshops.Service.product;

import com.toshiro.dreamshops.Model.Product;
import com.toshiro.dreamshops.Request.AddProductRequest;
import com.toshiro.dreamshops.Request.ProductUpdateRequest;
import com.toshiro.dreamshops.dto.ProductDto;

import java.util.List;

public interface iProductService {
    Product addProduct(AddProductRequest product);

    Product getProductById(Long id);
    Product updateProductById(ProductUpdateRequest product,  Long productId);
    void deleteProductById(Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductByCategoryAndBrand(String category, String brand);
    List<Product> getProductByBrandAndCategory(String brand, String category);
    List<Product> getProductsByName(String Name);
    List<Product> getProductByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);


    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);

}
