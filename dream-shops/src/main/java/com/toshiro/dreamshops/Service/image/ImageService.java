package com.toshiro.dreamshops.Service.image;

import com.toshiro.dreamshops.Model.Image;
import com.toshiro.dreamshops.Model.Product;
import com.toshiro.dreamshops.Repository.ImageRepo;
import com.toshiro.dreamshops.Service.product.iProductService;
import com.toshiro.dreamshops.dto.ImageDto;
import com.toshiro.dreamshops.exceptions.ResourcNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageService implements iImageService{
    @Autowired
    private ImageRepo imageRepo;
    @Autowired
    private iProductService service;

    @Override
    public Image getImageById(Long id) {
        return imageRepo.findById(id).orElseThrow(()->new ResourcNotFoundException("No image found with id: " +id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepo.findById(id).ifPresentOrElse(imageRepo :: delete, ()->{
            throw new ResourcNotFoundException("No image found with id:" +id);
        });
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {

        Image image = getImageById(imageId);

        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }


    }


    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {


        Product product = service.getProductById(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);


                String downloadUrl = "/image/download" + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage = imageRepo.save(image);

                savedImage.setDownloadUrl( "/image/download" + savedImage.getId());
                imageRepo.save(savedImage);
                ImageDto imageDto = new ImageDto();
                imageDto.setimageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDto.add(imageDto);



            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());

            }
        }
        return  savedImageDto;
    }




}