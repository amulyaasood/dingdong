package in.amulya.dingdong.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import in.amulya.dingdong.model.entity.OrderEntity;
import in.amulya.dingdong.model.ioobject.OrderRequest;
import in.amulya.dingdong.model.ioobject.OrderResponse;
import in.amulya.dingdong.repository.CartRepo;
import in.amulya.dingdong.repository.OrderRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    @Autowired
    UserService userService;
    @Autowired
    CartRepo  cartRepo;
    @Value("${razorpay_key}")
    private String RAZORPAY_KEY;
    @Value("${razorpay_secret}")
    private String RAZORPAY_SECRET;
    @Autowired
    OrderRepo orderRepo;
    @Override
    public OrderResponse createOrderWithPayment(OrderRequest request) throws RazorpayException {
       OrderEntity newOrder =  convertToEntity(request);
       newOrder = orderRepo.save(newOrder);

       // create razorpay payment order
        RazorpayClient razorpayClient = new RazorpayClient(RAZORPAY_KEY,RAZORPAY_SECRET);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount" , newOrder.getAmount() *100);
        orderRequest.put("currency" ,"INR");
        orderRequest.put("payment_capture",1);
        Order rpOrder = razorpayClient.orders.create(orderRequest);
        String rpOrderId= rpOrder.get("id");
        newOrder.setRazorpayOrderId(rpOrderId);
        String loggedUser =  userService.findBYUserId();
        newOrder.setUserId(loggedUser);
        newOrder = orderRepo.save(newOrder);
        return convertToresponse(newOrder);

    }

    @Override
    public void verifyPayment(Map<String, String> paymentData, String status) {
       String razorpayOrderId = paymentData.get("razorpay_order_id");
       OrderEntity existingOrder = orderRepo.findByRazorpayOrderId(razorpayOrderId)
               .orElseThrow(() -> new RuntimeException("Order Not Found"));
       existingOrder.setPaymentStatus(status);
       existingOrder.setRazorPaySignature(paymentData.get("razorpay_signature"));
       existingOrder.setRazorPayPaymentId(paymentData.get("razorpay_payment_id"));
       orderRepo.save(existingOrder);
       if ("paid".equalsIgnoreCase(status)) {
           cartRepo.deleteByUserId(existingOrder.getUserId());
       }
    }

    @Override
    public List<OrderResponse> getUserOrders() {
      List<OrderEntity> orders =  orderRepo.findByUserId(userService.findBYUserId());
      return orders.stream().map(this::convertToresponse).toList();

    }

    @Override
    public void removeOrder(String OrderId) {
        orderRepo.deleteById(OrderId);

    }

    @Override
    public List<OrderResponse> getAllOrders() {
      List<OrderEntity> orders =  orderRepo.findAll();
      return orders.stream().map(this::convertToresponse).toList();
    }

    @Override
    public void updateOrderStatus(String orderId, String status) {
       OrderEntity entity = orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException("Order Not Found"));
       entity.setOrderStatus(status);
        orderRepo.save(entity);
    }

    private OrderResponse convertToresponse(OrderEntity newOrder) {
        return OrderResponse.builder()
                .id(newOrder.getId())
                .amount(newOrder.getAmount())
                .userAddress(newOrder.getUserAddress())
                .userId(newOrder.getUserId())
                .razorpayOrderId(newOrder.getRazorpayOrderId())
                .paymentStatus(newOrder.getPaymentStatus())
                .orderStatus(newOrder.getOrderStatus())
                .email(newOrder.getEmail())
                .phone(newOrder.getPhone())
                .orderItems(newOrder.getOrderItems())
                .build();
    }

    private OrderEntity convertToEntity(OrderRequest request) {
        return OrderEntity.builder()
                .userAddress(request.getUserAddress())
                .amount(request.getAmount())
                .orderItems(request.getOrderItems())
                .email(request.getEmail())
                .phone(request.getPhoneNumber())
                .orderStatus(request.getOrderStatus())
                .build();
    }
}
