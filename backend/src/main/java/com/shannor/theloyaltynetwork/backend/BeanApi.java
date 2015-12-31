package com.shannor.theloyaltynetwork.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.shannor.theloyaltynetwork.backend.models.Bean;
import static com.shannor.theloyaltynetwork.backend.OfyService.ofy;
/**
 * Created by Shannor on 12/31/2015.
 */
@Api(
        name = "beanApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.theloyaltynetwork.shannor.com",
                ownerName = "backend.theloyaltynetwork.shannor.com",
                packagePath=""
        )

)
public class BeanApi {
    public Bean getBean(@Named("beanName") String beanName) {
        return ofy().load().type(Bean.class).id(beanName).now();
    }

    public void saveBean(Bean newBean) {
        ofy().save().entity(newBean).now();
    }

    public void saveBean2(@Named("beanName") String name)
    {
        Bean newBean = new Bean(name);
        ofy().save().entity(newBean).now();
    }

}
