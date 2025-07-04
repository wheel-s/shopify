package com.toshiro.dreamshops.Controller;



import com.toshiro.dreamshops.Model.Product;
import com.toshiro.dreamshops.Request.AddProductRequest;
import com.toshiro.dreamshops.Request.ProductUpdateRequest;
import com.toshiro.dreamshops.Response.ApiResponse;

import com.toshiro.dreamshops.Service.product.iProductService;
import com.toshiro.dreamshops.dto.ProductDto;
import com.toshiro.dreamshops.exceptions.AlreadyExistsException;
import com.toshiro.dreamshops.exceptions.ResourcNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/product")
public class ProductController {
    @Autowired
    private iProductService service;

    @GetMapping("/products")
    public ResponseEntity<ApiResponse> getProducts(){
        List<Product> products = service.getAllProducts();
        List<ProductDto>  convertedProducts = service.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
    }
    @GetMapping ("/product/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        try {
            Product product = service.getProductById(productId);
            ProductDto productDto = service.convertToDto(product);
            return  ResponseEntity.ok(new ApiResponse("Success", productDto));
        } catch (ResourcNotFoundException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/Add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
        try{
            Product theProduct =  service.addProduct(product);
            return ResponseEntity.ok( new ApiResponse("Add product Success!",theProduct));
        }
        catch(AlreadyExistsException e){
            return  ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }

    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping ("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct( @RequestBody ProductUpdateRequest request, @PathVariable Long productId){
        try{
          Product  theProduct= service.updateProductById(request, productId);
            ProductDto  convertedProducts = service.convertToDto(theProduct);
          return  ResponseEntity.ok(new ApiResponse("update product success", convertedProducts));
        }
        catch(ResourcNotFoundException e){
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null)) ;
        }


    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<ApiResponse>  deleteProduct(@PathVariable long productId){
        try {
            service.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Deleted product successfully", productId));
        } catch (ResourcNotFoundException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null)) ;
        }

    }

    @GetMapping("/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductBYBrandAndName( @RequestParam String brandName, @RequestParam String productName){
        try {
            List<Product> products = service.getProductByBrandAndName(brandName, productName);
            List<ProductDto>  convertedProducts = service.getConvertedProducts(products);

            if (products.isEmpty()){
                return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null)) ;
            }

            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Success", convertedProducts)) ;
        } catch (Exception e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null)) ;
        }
    }


    @GetMapping("/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand( @RequestParam String category, @RequestParam String brand){
        try {
            List<Product> products = service.getProductByCategoryAndBrand(category, brand);
            List<ProductDto>  convertedProducts = service.getConvertedProducts(products);

            if (products.isEmpty()){
                return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null)) ;
            }

            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Success", convertedProducts)) ;
        } catch (Exception e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null)) ;
        }
    }
    @GetMapping ("/name/{name}")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
        try {
            List<Product> products = service.getProductsByName(name);
            List<ProductDto>  convertedProducts = service.getConvertedProducts(products);
            if (products.isEmpty()){
                return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null)) ;
            }
            return  ResponseEntity.ok(new ApiResponse("Success", convertedProducts));
        } catch (Exception e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }
    @GetMapping ("/brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand) {
        try {
            List<Product> products = service.getProductsByBrand(brand);
            List<ProductDto>  convertedProducts = service.getConvertedProducts(products);
            if (products.isEmpty()){
                return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null)) ;
            }
            return  ResponseEntity.ok(new ApiResponse("Success", convertedProducts));
        } catch (Exception e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }
    @GetMapping ("/category/{category}")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category) {
        try {
            List<Product> products = service.getProductsByCategory(category);
            List<ProductDto>  convertedProducts = service.getConvertedProducts(products);
            if (products.isEmpty()){
                return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null)) ;
            }
            return  ResponseEntity.ok(new ApiResponse("Success", convertedProducts));
        } catch (Exception e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }




}
