package in.amulya.dingdong.service;

import in.amulya.dingdong.model.ioobject.FoodRequest;
import in.amulya.dingdong.model.ioobject.FoodResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import in.amulya.dingdong.service.FileUploadService;

import java.util.List;

@Service
public interface FoodService {
    FoodResponse addFood(FoodRequest request , MultipartFile file);
    List<FoodResponse> getFoods();
}
