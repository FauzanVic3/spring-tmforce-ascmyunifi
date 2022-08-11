/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.com.fauzanvic3.spring.tmforce.ascmyunifi.swagger;

import javax.ws.rs.Path;
import my.com.tmrnd.force.forceswagger.ForceSwaggerConfig;
import static my.com.tmrnd.force.forceswagger.ForceSwaggerConstant.SWAGGER_UI_PATH;
import my.com.tmrnd.force.forceswagger.SwaggerUIResource;
/**
 *
 * @author Fauzan
 */
@Path("/dac/myunifi"+SWAGGER_UI_PATH)
public class MyUnifiSwaggerUIResource extends SwaggerUIResource{
    
    public MyUnifiSwaggerUIResource(ForceSwaggerConfig forceSwaggerConfig) {
        super(forceSwaggerConfig);
    }
    
}
