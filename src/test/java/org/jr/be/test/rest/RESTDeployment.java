package org.jr.be.test.rest;

import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jr.be.dto.AddressDTO;
import org.jr.be.model.Affiliate;
import org.jr.be.rest.AffiliateService;
import org.jr.be.test.JRDeployment;
import org.jr.be.util.JsonResponseMsg;


public class RESTDeployment {

    public static WebArchive deployment() {
        return JRDeployment.deployment()
        		//model package
                .addPackage(Affiliate.class.getPackage())
                //DTO package
                .addPackage(AddressDTO.class.getPackage())
                //Util package
                .addPackage(JsonResponseMsg.class.getPackage())
                //Service Package
                .addPackage(AffiliateService.class.getPackage());
    }

}