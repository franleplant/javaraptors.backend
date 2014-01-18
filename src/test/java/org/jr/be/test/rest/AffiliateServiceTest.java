package org.jr.be.test.rest;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jr.be.dto.AffiliateDTO;
import org.jr.be.rest.AffiliateService;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class AffiliateServiceTest {

    @Deployment
    public static WebArchive deployment() {
        return RESTDeployment.deployment();
    }

    @Inject
    private AffiliateService affiliateService;

    @Test
    public void testGetVenueById() {

        // Test loading a single venue
        AffiliateDTO affiliateDTO = affiliateService.getOne(1l);
        assertNotNull(affiliateDTO);
        assertEquals("Jesus", affiliateDTO.getName());
        assertEquals("DeLaFerrere", affiliateDTO.getLastName());
    }
}
