
package msacoffeechainsample.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;


/* @FeignClient(name="stock", url="${api.stock.url}") */

@FeignClient(name="marketing", url="http://marketing:8080")
public interface MarketingService {

    @RequestMapping(method= RequestMethod.GET, path="/marketings/pointCancel")
    public void pointCancel(@RequestBody Marketing marketing);

}
