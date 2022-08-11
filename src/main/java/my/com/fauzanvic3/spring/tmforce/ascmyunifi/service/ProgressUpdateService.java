/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.com.fauzanvic3.spring.tmforce.ascmyunifi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import my.com.fauzanvic3.spring.tmforce.ascmyunifi.util.utilities;
import my.com.tmrnd.tmforce.common.APIConstant;
import my.com.tmrnd.tmforce.common.api.json.model.dac.MyUnifiProgressUpdateMainRequest;
import my.com.tmrnd.tmforce.common.api.json.model.dac.MyUnifiProgressUpdateMainResponse;
import my.com.tmrnd.tmforce.common.api.json.model.dac.MyUnifiProgressUpdateReplyHeader;
import my.com.tmrnd.tmforce.common.api.json.model.dac.MyUnifiProgressUpdateResponse;
import static my.com.tmrnd.tmforce.common.model.ModelResource.TMF_REQUEST;
import static my.com.tmrnd.tmforce.common.model.ModelResource.TMF_SESSION;
import my.com.tmrnd.tmforce.common.model.ModelService;
import my.com.tmrnd.tmforce.common.model.Validator;
import my.com.tmrnd.tmforce.common.model.util.Utils;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author Fauzan
 */
@Service
public class ProgressUpdateService{
    
    private final Logger log = LoggerFactory.getLogger(getClass().getName());
 
    public MyUnifiProgressUpdateMainResponse progressUpdate(String requestId, MyUnifiProgressUpdateMainRequest request){
        
        Validator validator = new Validator(requestId);
        
        if(request == null){
            validator.addFailure("json request", "Request json error", Validator.MSG_NULL_EMPTY);
        }
        MyUnifiProgressUpdateMainResponse myUnifiProgressUpdateMainResponse = new MyUnifiProgressUpdateMainResponse();
        MyUnifiProgressUpdateResponse myUnifiProgressUpdateResponse = new MyUnifiProgressUpdateResponse();
        
        String jsonProgressUpdateRequest = "";
        
        try{
            ObjectMapper mapper = new ObjectMapper();
            jsonProgressUpdateRequest = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
        }catch(JsonProcessingException e){
            log.error("JSON processing error", new Exception(e));
        }
        
        String endpointPath = "";
        String endpoint = "";
//        endpointPath = getModelEndpoint(APIConstant.API.MODEL.DAC.CODE);
        if (Utils.isEmpty(endpointPath)) {
            endpointPath = "http://10.54.7.205:8203";
            log.info("Hardcode Case create endpoint =" + endpointPath);
        } else {
            log.info("Get from getModelEndpoint Case create endpoint =" + endpointPath);
        }
        endpoint = endpointPath + "/api/dac/myunifi/progressupdate";
        
        log.info("URL MyUnifi progress update =" + endpoint);
        log.info("JSON MyUnifi progress update =" + jsonProgressUpdateRequest);
        
        MultivaluedMap<String, Object> innerMapMutiValue = new MultivaluedHashMap<>();
        innerMapMutiValue.add(TMF_REQUEST, requestId);
        innerMapMutiValue.add(TMF_SESSION, "");
        
        Response response = utilities.triggerModelPost(endpoint, innerMapMutiValue, request);
        
        try{
            myUnifiProgressUpdateMainResponse = response.readEntity(MyUnifiProgressUpdateMainResponse.class);
            }catch(Exception e){
                log.error("Error in readEntity", new Exception(e));
            }
        
        log.info("response: \n"+myUnifiProgressUpdateMainResponse.toString());

        if(response.getStatus() != 200){
            MyUnifiProgressUpdateReplyHeader ReplyHeader = new MyUnifiProgressUpdateReplyHeader();
            ReplyHeader.setErrCd("2");
            ReplyHeader.setErrMsg("Business Error");
            myUnifiProgressUpdateResponse.setReplyHeader(ReplyHeader);
            myUnifiProgressUpdateMainResponse.setResponse(myUnifiProgressUpdateResponse);
        }

        return myUnifiProgressUpdateMainResponse;
    }
}
