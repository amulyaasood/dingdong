package in.amulya.dingdong.model.entity;

import in.amulya.dingdong.model.ioobject.OrderItem;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "orders")
@Data
@Builder
public class OrderEntity {
    @Id
    private String id;
    private String userId;
    private String userAddress;
    private String phone;
    private String email;
    private List<OrderItem> orderItems;
    private double amount;
    private String paymentStatus;
    private String razorpayOrderId;
    private String razorPaySignature;
    private String orderStatus;
    private String razorPayPaymentId;

}
