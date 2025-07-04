package com.toshiro.dreamshops.Service.image;

import com.toshiro.dreamshops.Model.Image;
import com.toshiro.dreamshops.dto.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface iImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
   List<ImageDto> saveImages(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile files, Long imageId);


}
