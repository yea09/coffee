package msacoffeechainsample;

import msacoffeechainsample.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired
    MarketingRepository MarketingRepository;


    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPayCompleted_PayComplete(@Payload PayCompleted payCompleted){
        if(payCompleted.isMe()){

            Marketing marketing = new Marketing();
            marketing.setOrderId(payCompleted.getOrderId());
            marketing.setPoint((double) 300000);
            MarketingRepository.save(marketing);

            System.out.println("##### listener PayComplete : " + payCompleted.toJson());
        }
    }

}
