/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.com.fauzanvic3.spring.tmforce.ascmyunifi.swagger;

import io.swagger.jaxrs.listing.ApiListingResource;
import javax.ws.rs.Path;
import static my.com.tmrnd.force.forceswagger.ForceSwaggerConstant.SWAGGER_PATH;
/**
 *
 * @author Fauzan
 */
@Path("/dac/myunifi/res" + SWAGGER_PATH)
public class MyUnifiApiListingResource extends ApiListingResource{
    
}
