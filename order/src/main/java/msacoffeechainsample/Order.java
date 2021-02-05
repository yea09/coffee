package msacoffeechainsample;

import javax.persistence.*;

import msacoffeechainsample.external.Marketing;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Order_table")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long productId;
    private String productName;
    private Integer qty;
    private String status;

    @PrePersist
    public void onPrePersist() {

        // Status 변화 후 Order insert
        this.setStatus("Requested");
    }

    @PostPersist
    public void onPostPersist(){

        // Event 객체 생성
        Ordered ordered = new Ordered();

        // Aggregate 값을 Event 객체로 복사
        BeanUtils.copyProperties(this, ordered);

        // pub/sub
        ordered.publishAfterCommit();
    }

    @PreUpdate
    public void onPreUpdate(){

        // 주문 취소의 경우
        if (this.getStatus().equals("Canceled")) {

            // Event 객체 생성
            OrderCanceled orderCanceled = new OrderCanceled();

            // Aggregate 값을 Event 객체로 복사
            BeanUtils.copyProperties(this, orderCanceled);

            msacoffeechainsample.external.Marketing marketing = new msacoffeechainsample.external.Marketing();
            //marketing.setId(orderCanceled.getProductId());
            marketing.setOrderId(orderCanceled.getProductId());
            marketing.setProcess(orderCanceled.getStatus());
            marketing.setPoint("0.0");

            // req/res
            OrderApplication.applicationContext.getBean(msacoffeechainsample.external.MarketingService.class)
                    .pointCancel(marketing);
        }
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQty() {
        return qty;
    }
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}
