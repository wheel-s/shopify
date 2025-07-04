package com.toshiro.dreamshops.Service.product;


import com.toshiro.dreamshops.Model.Category;
import com.toshiro.dreamshops.Model.Image;
import com.toshiro.dreamshops.Model.Product;
import com.toshiro.dreamshops.Repository.CategoryRepo;
import com.toshiro.dreamshops.Repository.ImageRepo;
import com.toshiro.dreamshops.Repository.ProductRepo;
import com.toshiro.dreamshops.Request.AddProductRequest;
import com.toshiro.dreamshops.Request.ProductUpdateRequest;
import com.toshiro.dreamshops.dto.ImageDto;
import com.toshiro.dreamshops.dto.ProductDto;
import com.toshiro.dreamshops.exceptions.AlreadyExistsException;
import com.toshiro.dreamshops.exceptions.ProductNotFoundException;
import com.toshiro.dreamshops.exceptions.ResourcNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements iProductService{
    @Autowired
    private   ProductRepo productRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ImageRepo imageRepo;
    @Autowired
    private  ModelMapper modelmapper;


    @Override
    public Product addProduct(AddProductRequest request) {


        if(productExists(request.getName(), request.getBrand())){
            throw new AlreadyExistsException(request.getBrand()+ " " +request.getName() + " already exist, you may update this product instead!!");
        }

        Category category = Optional.ofNullable(categoryRepo.findByName(request.getCategory().getName()))
                .orElseGet(()-> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepo.save(newCategory);
                });
        request.setCategory(category);
        return productRepo.save(createProduct(request,category));

    }

    private boolean productExists(String name, String brand){
        return productRepo.existsByNameAndBrand(name, brand);
    }




    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getInventory(),
                request.getPrice(),
                request.getDescription(),
                category

        );

    }


    @Override
    public Product getProductById(Long id) {
        return productRepo.findById(id).orElseThrow(()->new ResourcNotFoundException("Product not found!"));
    }

    @Override
    public Product updateProductById(ProductUpdateRequest request, Long productId) {
        return productRepo.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepo :: save)
                .orElseThrow(()->new ResourcNotFoundException("Product not found"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepo.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return  existingProduct;


    }





    @Override
    public void deleteProductById(Long id) {
        productRepo.findById(id).ifPresentOrElse(productRepo::delete,
                 ()->{throw new ProductNotFoundException("Product not found!");} );

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepo.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepo.findByBrand(brand);
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return productRepo.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductByBrandAndCategory(String brand, String category) {
        return productRepo.findByBrandAndCategoryName(brand, category);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepo.findByName(name);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String name) {
        return productRepo.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return 0L;
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products){
        return  products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product){
        ProductDto productDto = modelmapper.map(product, ProductDto.class);
        List<Image> images = imageRepo.findByproductId(product.getId());
        List<ImageDto> imageDto = images.stream()
                .map(image->modelmapper.map(image, ImageDto.class))
                .toList();
        productDto.setImages(imageDto);
        return productDto;
    }

}