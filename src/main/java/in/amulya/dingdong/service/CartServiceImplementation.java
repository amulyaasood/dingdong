package in.amulya.dingdong.service;

import in.amulya.dingdong.model.entity.CartEntity;
import in.amulya.dingdong.model.ioobject.CartRequest;
import in.amulya.dingdong.model.ioobject.CartResponse;
import in.amulya.dingdong.repository.CartRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartServiceImplementation implements CartService {
    CartRepo cartRepo;
    UserService userService;
    @Override
    public CartResponse addToCart(CartRequest request) {
        String loggedUser = userService.findBYUserId();
        Optional<CartEntity> cartOptional = cartRepo.findByUserId(loggedUser);
        CartEntity cartEntity = cartOptional.orElseGet(() -> new CartEntity(loggedUser , new HashMap<>()));
        Map<String ,Integer> items = cartEntity.getItems();
        String foodId = request.getFoodId();
        items.put(foodId, items.getOrDefault(foodId ,0) + 1);
        cartEntity.setItems(items);
        cartEntity = cartRepo.save(cartEntity);
        return convertToResponse(cartEntity);
    }

    @Override
    public CartResponse getCart() {
        String loggedUser = userService.findBYUserId();
        CartEntity cart = cartRepo.findByUserId(loggedUser).orElseGet(() -> new CartEntity(null ,loggedUser , new HashMap<>()));
        return convertToResponse(cart);

    }

    @Override
    public void clearCart() {
        String loggedUser = userService.findBYUserId();
        cartRepo.deleteByUserId(loggedUser);

    }

    @Override
    public CartResponse deleteFromCart(CartRequest cartRequest) {
        String loggedUser = userService.findBYUserId();
        CartEntity entity = cartRepo.findByUserId(loggedUser)
               .orElseThrow(()-> new RuntimeException("Cart not found!"));
        Map<String, Integer> items = entity.getItems();

        if(items.containsKey(cartRequest.getFoodId())) {
            int value = items.get(cartRequest.getFoodId());
            if(value > 1){
                items.put(cartRequest.getFoodId(), value - 1);
            } else {
                items.remove(cartRequest.getFoodId());
            }
        }
        entity.setItems(items);
        entity = cartRepo.save(entity);
        return convertToResponse(entity);

    }

    private CartResponse convertToResponse(CartEntity cartEntity) {
        return CartResponse.builder()
                .id(cartEntity.getId())
                .userId(cartEntity.getUserId())
                .items(cartEntity.getItems())
                .build();
    }
}
