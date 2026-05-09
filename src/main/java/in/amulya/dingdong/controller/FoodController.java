package in.amulya.dingdong.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.amulya.dingdong.model.ioobject.FoodRequest;
import in.amulya.dingdong.model.ioobject.FoodResponse;
import in.amulya.dingdong.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @PostMapping
    public FoodResponse addFood(@RequestPart("food") String foodString,
                                @RequestPart("file") MultipartFile image){
        ObjectMapper objectMapper = new ObjectMapper();

        FoodRequest foodRequest = null;
        try {
            foodRequest = objectMapper.readValue(foodString ,FoodRequest.class);
        }catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid food JSON");
        }
        return foodService.addFood(foodRequest,image);
    }
    @GetMapping
    public List<FoodResponse> getFoods(){
        return foodService.getFoods();
    }

}
