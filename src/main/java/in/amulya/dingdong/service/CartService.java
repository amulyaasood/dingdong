package in.amulya.dingdong.service;

import in.amulya.dingdong.model.ioobject.CartRequest;
import in.amulya.dingdong.model.ioobject.CartResponse;

public interface CartService {
    CartResponse addToCart(CartRequest cartRequest);
    CartResponse getCart();
    void clearCart();
    CartResponse deleteFromCart(CartRequest cartRequest);

}
