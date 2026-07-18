package in.amulya.dingdong.controller;

import com.razorpay.RazorpayException;
import in.amulya.dingdong.model.ioobject.OrderRequest;
import in.amulya.dingdong.model.ioobject.OrderResponse;
import in.amulya.dingdong.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    OrderService orderService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrderWithPayment(@RequestBody OrderRequest orderRequest) throws RazorpayException {
       return orderService.createOrderWithPayment(orderRequest);
    }

    @GetMapping("/verify")
    public void verifyPayment(@RequestBody Map<String,String> paymentData) {
        orderService.verifyPayment(paymentData,"paid");
    }

    @GetMapping
    public List<OrderResponse> getUserOrders() {
       return orderService.getUserOrders();
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable String orderId)  {
        orderService.removeOrder(orderId);
    }

//    Admin panel endpoints
    @GetMapping("/all")
    public List<OrderResponse> getAllUserOrders() {
        return orderService.getAllOrders();
    }

    @PatchMapping("/status/{orderId}")
    public void updateOrderStatus(@PathVariable String orderId, @RequestParam String status) {
        orderService.updateOrderStatus(orderId,status);
    }

}
