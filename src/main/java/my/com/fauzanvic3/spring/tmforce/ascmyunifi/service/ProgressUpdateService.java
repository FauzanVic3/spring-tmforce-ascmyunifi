/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.com.fauzanvic3.spring.tmforce.ascmyunifi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import my.com.fauzanvic3.spring.tmforce.ascmyunifi.util.utilities;
import my.com.tmrnd.tmforce.common.APIConstant;
import my.com.tmrnd.tmforce.common.api.json.model.dac.MyUnifiProgressUpdateMainRequest;
import my.com.tmrnd.tmforce.common.api.json.model.dac.MyUnifiProgressUpdateMainResponse;
import my.com.tmrnd.tmforce.common.api.json.model.dac.MyUnifiProgressUpdateReplyHeader;
import my.com.tmrnd.tmforce.common.api.json.model.dac.MyUnifiProgressUpdateResponse;
import static my.com.tmrnd.tmforce.common.model.ModelResource.TMF_REQUEST;
import static my.com.tmrnd.tmforce.common.model.ModelResource.TMF_SESSION;
import my.com.tmrnd.tmforce.common.model.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author Fauzan
 */
@Service
@Slf4j
public class ProgressUpdateService {
    
//    private final Logger log = LoggerFactory.getLogger(getClass().getName());
 
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
        
        log.info(jsonProgressUpdateRequest);
        
        String endpointPath = "";
        String endpoint = "";
        
        String ttOrderFlag = utilities.checkTTOrder(jsonProgressUpdateRequest);
        
        if(ttOrderFlag == null){
            MyUnifiProgressUpdateReplyHeader ReplyHeader = new MyUnifiProgressUpdateReplyHeader();
            ReplyHeader.setErrCd("2");
            ReplyHeader.setErrMsg("Business Error: Submitted both TT and Order");
            myUnifiProgressUpdateResponse.setReplyHeader(ReplyHeader);
            myUnifiProgressUpdateMainResponse.setResponse(myUnifiProgressUpdateResponse);
            return myUnifiProgressUpdateMainResponse;
        }
        else {
            if(ttOrderFlag.equalsIgnoreCase("TT")){
                endpoint = "https://sit.tmforcedev.tm.com.my/eai/dac/myunifi/ws/progressupdate";
//                endpointPath = getModelEndpoint(APIConstant.API.MODEL.DAC.CODE);
//                endpoint = endpointPath + "/api/dac/myunifi/progressupdate?ctt-number="
//                        + request.getRequest().getSummary().getTTNumber();
            }
            if(ttOrderFlag.equalsIgnoreCase("ORDER")){
                endpoint = "https://sit.tmforcedev.tm.com.my/eai/dac/myunifi/ws/progressupdate";
//                endpointPath = getModelEndpoint(APIConstant.API.MODEL.FL_DAC.CODE);
//                endpoint = endpointPath + "/api/fl-dac/myunifi/progressupdate?order-number=" 
//                        + request.getRequest().getSummary().getAdditionalProperties().get("OrderNumber");
            }
        }
        
        log.info("URL MyUnifi progress update =" + endpoint);
        log.info("JSON MyUnifi progress update =" + jsonProgressUpdateRequest);
        
        MultivaluedMap<String, Object> innerMapMutiValue = new MultivaluedHashMap<>();
        innerMapMutiValue.add(TMF_REQUEST, requestId);
        innerMapMutiValue.add(TMF_SESSION, "");
        
        Response response = utilities.triggerModelPost(endpoint, innerMapMutiValue, request);
        
        if(response.getStatus() != 200){
            MyUnifiProgressUpdateReplyHeader ReplyHeader = new MyUnifiProgressUpdateReplyHeader();
            ReplyHeader.setErrCd("2");
            ReplyHeader.setErrMsg("Business Error");
            myUnifiProgressUpdateResponse.setReplyHeader(ReplyHeader);
            myUnifiProgressUpdateMainResponse.setResponse(myUnifiProgressUpdateResponse);
            return myUnifiProgressUpdateMainResponse;
        }
        
        try{
            if(ttOrderFlag.equalsIgnoreCase("TT")){
                myUnifiProgressUpdateMainResponse = response.readEntity(MyUnifiProgressUpdateMainResponse.class);
                return myUnifiProgressUpdateMainResponse;
            }
            if(ttOrderFlag.equalsIgnoreCase("ORDER")){
                myUnifiProgressUpdateMainResponse = response.readEntity(MyUnifiProgressUpdateMainResponse.class);
                return myUnifiProgressUpdateMainResponse;
            }
            
        }catch(Exception e){
            log.error("Error in readEntity", new Exception(e));
            MyUnifiProgressUpdateReplyHeader ReplyHeader = new MyUnifiProgressUpdateReplyHeader();
            ReplyHeader.setErrCd("1");
            ReplyHeader.setErrMsg("Internal Server Error: Parsing error");
            myUnifiProgressUpdateResponse.setReplyHeader(ReplyHeader);
            myUnifiProgressUpdateMainResponse.setResponse(myUnifiProgressUpdateResponse);
            return myUnifiProgressUpdateMainResponse;
        }

        if(response.getStatus() != 200){
            MyUnifiProgressUpdateReplyHeader ReplyHeader = new MyUnifiProgressUpdateReplyHeader();
            ReplyHeader.setErrCd("2");
            ReplyHeader.setErrMsg("Business Error");
            myUnifiProgressUpdateResponse.setReplyHeader(ReplyHeader);
            myUnifiProgressUpdateMainResponse.setResponse(myUnifiProgressUpdateResponse);
        }

        return myUnifiProgressUpdateMainResponse;
    }
    
    public String getModelEndpoint(String code) {
        return getApiEndPoint(code);
    }

    public String getApiEndPoint(String code) {
        String endpoint = System.getenv("TMF_API_" + code + "_ENDPOINT");

        if (endpoint == null) {
            log.error(code + " endpoint not found");
        }

        return endpoint;
    }
}
