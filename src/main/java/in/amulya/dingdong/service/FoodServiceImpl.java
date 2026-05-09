package in.amulya.dingdong.service;

import in.amulya.dingdong.model.ioobject.FoodRequest;
import in.amulya.dingdong.model.ioobject.FoodResponse;
import in.amulya.dingdong.model.entity.FoodEntity;
import in.amulya.dingdong.repository.FoodRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FoodServiceImpl implements FoodService {
    @Autowired
    FileUploadService fileUploadService;
    @Autowired
    FoodRepo  foodRepo;

    @Override
    public FoodResponse addFood(FoodRequest req , MultipartFile file){
        FoodEntity foodEntity = getFoodEntity(req);
        String imageURL = fileUploadService.upload(file);
        foodEntity.setImageUrl(imageURL);
        foodEntity= foodRepo.save(foodEntity);
        return getFoodResponse(foodEntity);

    }
    private FoodEntity getFoodEntity(FoodRequest req){
        return FoodEntity.builder().name(req.getName())
                .description(req.getDescription())
                .price(req.getPrice())
                .category(req.getCategory())
                .build();
    }
    private FoodResponse getFoodResponse(FoodEntity entity){
        return FoodResponse.builder().
                name(entity.getName()).
                description(entity.getDescription()).
                category(entity.getCategory()).
                price(entity.getPrice()).
                id(entity.getId()).
                imageUrl(entity.getImageUrl()).build();
    }
}
