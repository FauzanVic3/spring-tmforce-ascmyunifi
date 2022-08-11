/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.com.fauzanvic3.spring.tmforce.ascmyunifi.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import my.com.fauzanvic3.spring.tmforce.ascmyunifi.AscmyunifiResource;
import my.com.fauzanvic3.spring.tmforce.ascmyunifi.service.ProgressUpdateService;

import my.com.tmrnd.tmforce.common.APIConstant;
import my.com.tmrnd.tmforce.common.api.json.ExceptionResponse;
import my.com.tmrnd.tmforce.common.api.json.model.dac.MyUnifiProgressUpdateMainRequest;
import my.com.tmrnd.tmforce.common.api.json.model.dac.MyUnifiProgressUpdateMainResponse;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Fauzan
 */
@Api(value = "Progress Update")
@RestController
public class ProgressUpdateResource extends AscmyunifiResource{

    @Autowired
    ProgressUpdateService service;

//    Client client;
//    SessionFactory sessionFactory;

    public static final String TMF_REQUEST = APIConstant.HEADER.TMF_REQUEST;

//    public ProgressUpdateResource() {
//        service = new ProgressUpdateService(client, sessionFactory);
//    }
    
    @PostMapping("/progressupdate")
//    @Path("/progressupdate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
//    @UnitOfWork
    @ApiOperation(value = "Get Progress Update")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = MyUnifiProgressUpdateMainResponse.class),
        @ApiResponse(code = 400, message = "Error 400, 404, 500", response = ExceptionResponse.class)
    })
    public MyUnifiProgressUpdateMainResponse progressUpdate(@HeaderParam(TMF_REQUEST) String requestId, @RequestBody MyUnifiProgressUpdateMainRequest request) throws WebApplicationException{

        MyUnifiProgressUpdateMainResponse response = service.progressUpdate(requestId, request);
           
        return response;
//        return new ResponseHandler(requestId).responseOk(service.progressUpdate(requestId, request));
    }
}
