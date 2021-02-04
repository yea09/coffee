package msacoffeechainsample;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Marketing_table")
public class Marketing {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long orderId;
    private String process;
    private Double point;

    @PostPersist
    public void onPostPersist(){
        System.out.println(this.toString());
        System.out.println("**marketing persist 시작**");


        if(point > 0){

            PromoCompleted promoCompleted = new PromoCompleted();
            BeanUtils.copyProperties(this, promoCompleted);
            promoCompleted.publish();

        }
        else
            {
            PromoCancelled promoCancelled = new PromoCancelled();
            BeanUtils.copyProperties(this, promoCancelled);
            promoCancelled.publish();

        }
    }

    @PreUpdate
    public void onPreUpdate(){
        System.out.println("marketing update");
    }

    @PostRemove
    public void onPostRemove(){

        System.out.println("marketing remove");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    @Override
    public String toString() {
        return "Marketing{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", point=" + point +
                ", process=" + process +
                '}';
    }
}
