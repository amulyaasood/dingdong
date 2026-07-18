package in.amulya.dingdong.controller;

import in.amulya.dingdong.model.ioobject.CartRequest;
import in.amulya.dingdong.model.ioobject.CartResponse;
import in.amulya.dingdong.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
public class CartController {

    CartService cartService;
    @PostMapping
    public CartResponse addToCart(@RequestBody CartRequest request){
        String foodId = request.getFoodId();
        if(foodId == null || foodId.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid food Id");
        }
       return cartService.addToCart(request);

    }

    @GetMapping
    public CartResponse getCart(){
        return cartService.getCart();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart(){
        cartService.clearCart();
    }

    @PutMapping
    public CartResponse deleteFromCart(@RequestBody CartRequest cartRequest){
        String foodId = cartRequest.getFoodId();
        if(foodId == null || foodId.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid food Id");
        }
        return cartService.deleteFromCart(cartRequest);
    }

}
