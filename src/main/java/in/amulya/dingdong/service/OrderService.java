package in.amulya.dingdong.service;

import com.razorpay.RazorpayException;
import in.amulya.dingdong.model.ioobject.OrderRequest;
import in.amulya.dingdong.model.ioobject.OrderResponse;

import java.util.List;
import java.util.Map;

public interface OrderService {

    OrderResponse createOrderWithPayment(OrderRequest request) throws RazorpayException;
    void verifyPayment(Map<String,String> paymentData , String status);

    List<OrderResponse> getUserOrders();

    void removeOrder(String OrderId);

    List<OrderResponse> getAllOrders();

    void updateOrderStatus(String orderId , String status);
}
